package com.zgc.zhaopin.framework.excel;

import com.zgc.zhaopin.framework.excel.enumtype.ExcelType;
import com.zgc.zhaopin.framework.excel.annotation.Excel;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import lombok.extern.slf4j.Slf4j;

/**
 * Excel Utilities for import and export data from excel
 * 
 * Requirements: 1. poi-ooxml dependency -> v4.1.2 2. ExcelType.java -> Enum
 * type 3. Excel.java -> Custom annotation @Excel 4. HttpServletUtils.java ->
 * Http Servlet utils 5. SpringContextUtil.java -> Application context utils 6.
 * emirio.excel.js -> Front-End ajax functions
 * 
 * Usage: 1. import ExcelUtils packages 2. Add @Excel at entity and
 * property @Excel("xxx") public class entity(){ @Excel("property name") private
 * long id; } 3. <Front-end> settings: Export: add
 * <a href="/xx/export">Export</a> Import: details in emirio.excel.js 4.
 * <Back-end> settings: Export: @GetMapping("/export") public void exportExcel()
 * { deptService = SpringContextUtil.getBean(SysDeptService.class);
 * ExcelUtils.exportExcel(SysDept.class, deptService.listAll()); }
 *
 * Import: List<SysDept> list = ExcelUtils.importExcel(SysDept.class,
 * ExcelUtils.getFileInputStream(request));
 *
 * <p>
 * Custom annotation @Excel as entity identifier.
 * <p>
 * Methods List are as follows:
 * 
 * get default cell style 1. getDefaultCellStyle(XSSFWorkbook workbook)
 * 
 * get header row style 2. getHeaderCellStyle(XSSFWorkbook workbook,
 * XSSFCellStyle cellStyle)
 * 
 * get date format cell style 3. getDateCellStyle(XSSFWorkbook workbook,
 * XSSFCellStyle cellStyle)
 * 
 * get all the other cell style 4. getCellStyle(String sheetTitle, List<Field>
 * fields)
 * 
 * get all entity fields 5. getExcelList(Class<?> entity, ExcelType type)
 * 
 * get fields name 6. getFieldName(List<Field> fields)
 * 
 * get sheet title 7. getSheetTitle(Class<?> entity)
 * 
 * get excel file name 8. getExcelName(String fileName)
 * 
 * download template 9. downloadTemplateExcel(Class<?> entity)
 * 
 * download excel file 10. downloadExcel(XSSFWorkbook workbook, String fileName)
 * 
 * close stream 11. closeStream(OutputStream resp, XSSFWorkbook workbook)
 * 
 * export excel 12. exportExcel(Class<?> entity, List<T> list, String
 * sheetTitle)
 * 
 * get file input stream from request 13. getInputStream(HttpServletRequest
 * request)
 * 
 * get row data 14. getRowData(Row row)
 * 
 * check if row data match to entity property 15. checkFormat(Class<T> entity,
 * String[] rowData, List<T> list)
 * 
 * @author emirio
 * @date 2020/5/24
 * @version v1.3
 * @since v1.0
 */
@Slf4j
public class ExcelUtils {

    // set start index to read data from excel
    private final static int DATAROW = 1;

    // set header row font height
    private final static int FONT_HEIGHT = 13;

    // store field property counter annotated by @Excel
    private static int fieldCount = 0;

    /**
     * Get default cell style
     * 
     * @param XSSFWorkbook workbook
     * @return cellStyle
     */
    private static XSSFCellStyle getDefaultCellStyle(XSSFWorkbook workbook) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        XSSFFont font = workbook.createFont();
        font.setFontName("Microsoft YaHei UI");
        cellStyle.setFont(font);

        return cellStyle;
    }

    /**
     * Get header cell style
     * 
     * @param workbook  XSSFWorkbook
     * @param cellStyle getDefaultCell()
     * @return headStyle
     */
    private static XSSFCellStyle getHeaderCellStyle(XSSFWorkbook workbook, XSSFCellStyle cellStyle) {
        XSSFCellStyle headStyle = cellStyle;
        headStyle.cloneStyleFrom(cellStyle);
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
        XSSFFont headFont = workbook.createFont();
        headFont.setFontName(cellStyle.getFont().getFontName());
        headFont.setBold(true);
        headFont.setColor(IndexedColors.WHITE.getIndex());
        headFont.setFontHeight(FONT_HEIGHT);
        headStyle.setFont(headFont);

        return headStyle;
    }

    /**
     * Get date cell style
     * 
     * @param workbook  XSSFWorkbook object
     * @param cellStyle XSSFCellStyle object
     * @return date cell style
     */
    private static XSSFCellStyle getDateCellStyle(XSSFWorkbook workbook, XSSFCellStyle cellStyle) {
        XSSFCellStyle dateStyle = workbook.createCellStyle();
        dateStyle.cloneStyleFrom(cellStyle);
        XSSFDataFormat dateFormat = workbook.createDataFormat();
        // dateStyle.setDataFormat(dateFormat.getFormat("yyyy-MM-dd HH:mm:ss"));
        dateStyle.setDataFormat(dateFormat.getFormat("yyyy-MM-dd"));

        return dateStyle;
    }

    /**
     * Get workbook cell style
     * 
     * @param sheetTitle sheet title
     * @param fields     entity fields
     * @return workbook
     */
    private static XSSFWorkbook getCellStyle(String sheetTitle, List<Field> fields) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetTitle);

        // set column width as autosize for chinese characters
        for (int i = 0; i < fields.size(); i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 25 / 10);
        }

        // set cell style
        XSSFCellStyle cellStyle = getDefaultCellStyle(workbook);
        XSSFRow headRow = sheet.createRow(0);
        for (int i = 0; i < fields.size(); i++) {
            Excel excel = fields.get(i).getAnnotation(Excel.class);
            XSSFCell head = headRow.createCell(i);
            head.setCellValue(excel.value());
            head.setCellStyle(getHeaderCellStyle(workbook, cellStyle));
        }

        return workbook;
    }

    /**
     * Get all entity properties declared with @Excel custom annotation
     * 
     * @param entity entity class
     * @param type   ExcelType
     * @return field list
     */
    private static List<Field> getExcelList(Class<?> entity, ExcelType type) {
        List<Field> list = new ArrayList<>();

        // entity.getDeclaredFields() get current properties and methods
        // Field[] fields = entity.getDeclaredFields();

        // FiledUtils.getAllFields ge all of fields
        // This utils will get all parents and child properties and methods 

        Field[] fields = FieldUtils.getAllFields(entity);

        for (Field field : fields) {
            if (field.isAnnotationPresent(Excel.class)) {
                ExcelType fieldType = field.getAnnotation(Excel.class).type();
                if (fieldType.equals(ExcelType.ALL) || fieldType.equals(type)) {
                    list.add(field);
                }
            }
        }
        // change header and tail data, because of ID is list at the last in excel
        java.util.Collections.swap(list, 0, list.size() - 1);
        return list;
    }

    /**
     * Get all fields name
     * 
     * @param fields entity fields list
     * @return fields list
     */
    private static List<String> getFieldName(List<Field> fields) {
        List<String> list = new ArrayList<>();
        for (Field field : fields) {
            list.add(field.getName());
        }
        return list;
    }

    /**
     * Get sheet title
     * 
     * @param entity beans
     * @return sheet title
     */
    private static String getSheetTitle(Class<?> entity) {
        String sheetTitle = "";
        Excel excel = entity.getAnnotation(Excel.class);
        if (excel != null) {
            sheetTitle = excel.value();
        } else {
            sheetTitle = entity.getSimpleName();
        }
        return sheetTitle;
    }

    /**
     * Get export excel file name
     * 
     * @param fileName file name
     * @return file name
     */
    private static String getExcelName(String fileName) {
        try {
            if (fileName.contains("Template")) {
                fileName = URLEncoder.encode(fileName + ".xlsx", "UTF-8");
                return fileName;
            } else {
                SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
                fileName = URLEncoder.encode(fileName + date.format(new Date()) + ".xlsx", "UTF-8");
            }
        } catch (UnsupportedEncodingException encodingException) {
            encodingException.printStackTrace();
        }
        return fileName;
    }

    /**
     * Download template
     * 
     * @param entity     entity
     * @param sheetTitle sheet title
     */
    public static void downloadTemplate(Class<?> entity) {
        String sheetTitle = getSheetTitle(entity);
        XSSFWorkbook workbook = getCellStyle(sheetTitle, getExcelList(entity, ExcelType.IMPORT));
        downloadExcel(workbook, sheetTitle + "Template");
    }

    /**
     * Dwonload excel
     * 
     * @param workbook workbook
     * @param fileName excel file name
     */
    private static void downloadExcel(XSSFWorkbook workbook, String fileName) {
        // response can be declared as follows, if you do not want to import the HttpServletUtils
        // Do not forget import ServletRequestAttributes and RequestContextHolder package
        // HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        HttpServletResponse response = HttpServletUtils.getResponse();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + getExcelName(fileName));

        // create a temporary byteOutput stream to store workbook,
        // if not, it will throw pipe broken exception
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        OutputStream respOutput = null;
        try {
            respOutput = response.getOutputStream();
            workbook.write(byteOutput);
            byteOutput.writeTo(respOutput);
        } catch (IOException e) {
            log.info("Failed to download excel, please check download()!");
            e.printStackTrace();
        } finally {
            closeStream(respOutput, workbook);
        }
    }

    /**
     * Close workbook and output stream
     * 
     * @param resp     output stream
     * @param workbook workbook stream
     */
    private static void closeStream(OutputStream resp, XSSFWorkbook workbook) {
        if (resp != null) {
            try {
                resp.close();
            } catch (Exception e) {
                log.info("Failed to close response stream, check closeStream()!");
                e.printStackTrace();
            }
        }
        if (workbook != null) {
            try {
                workbook.close();
            } catch (Exception e) {
                log.info("Failed to close workbook stream, please check closeStream()!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Export excel file
     *      export fields which annoated by @Excel
     * 
     * @param <T>        entity type
     * @param entity     beans
     * @param list       beans list
     * @param sheetTitle sheet title
     */
    public static <T> void exportExcel(Class<?> entity, List<T> list) {
        List<Field> fields = getExcelList(entity, ExcelType.EXPORT);
        List<String> fNames = getFieldName(fields);
        String sheetTitle = getSheetTitle(entity);

        XSSFWorkbook workbook = getCellStyle(sheetTitle, fields);
        XSSFSheet sheet = workbook.getSheet(sheetTitle);

        for (int i = 0; i < list.size(); i++) {
            XSSFRow row = sheet.createRow(i + DATAROW);
            T item = list.get(i);

            try {
                final BeanInfo bean = Introspector.getBeanInfo(item.getClass());
                for (final PropertyDescriptor pd : bean.getPropertyDescriptors()) {
                    if (fNames.contains(pd.getName())) {
                        Object value = pd.getReadMethod().invoke(item, (Object[]) null);
                        int index = fNames.indexOf(pd.getName());
                        XSSFCell cell = row.createCell(index);
                        if (value == null) {
                            value = "";
                        } else if (value instanceof Number) {
                            cell.setCellValue((Double.valueOf(String.valueOf(value))));
                        } else if (value instanceof Date) {
                            cell.setCellValue(String.valueOf(value));
                            cell.setCellValue((Date) value);
                            XSSFCellStyle dateStyle = getDateCellStyle(workbook, getDefaultCellStyle(workbook));
                            cell.setCellStyle(dateStyle);
                            continue;
                        } else {
                            cell.setCellValue(String.valueOf(value));
                        }
                        cell.setCellStyle(getDefaultCellStyle(workbook));
                    }
                }
            } catch (Exception e) {
                log.info("Failed to export excel, please check exportExcel()!");
                e.printStackTrace();
            }
        }
        downloadExcel(workbook, sheetTitle);
    }

    /**
     * Get File input stream from HttpServletRequest
     * <p>
     * If transfer InputStream to workbook, it will cause file empty error, should
     * cast InputStream to FileInputStream to avoid this issue.
     * 
     * Upload file from front-end is stored as temporary file, which located in
     * E:\var\tmp\website-app\work\Tomcat\localhost\ROOT\xxxxxxx.tmp
     * 
     * @param request http servlet request
     * @return file input stream
     */
    public static FileInputStream getFileInputStream(HttpServletRequest request) {
        // set multiRequest as null is to prevent file is empty transfer by front-end,
        // if not, error occured
        MultipartHttpServletRequest multiRequest = null;
        if (request instanceof MultipartHttpServletRequest) {
            multiRequest = (MultipartHttpServletRequest) request;
        }

        // Must declare as FileInputStream, not InputStream, workbook can't resolve
        // InputStream can't be stored in workbook, thus FileInputStream is needed
        FileInputStream fileStream = null;
        try {
            /**
             * MultipartFile file = multiRequest.getFile("file"); String fileName =
             * file.getOriginalFilename(); String fileContent = file.getContentType();
             * FileInputStream newos = (FileInputStream)file.getInputStream();
             */
            fileStream = (FileInputStream) multiRequest.getFile("file").getInputStream();

        } catch (IOException e) {
            log.info("Failed to get input stream from HttpServletRequest,please check getInputStream()!");
            e.printStackTrace();
        }

        return fileStream;
    }

    /**
     * Get current row data
     * 
     * @param row Row object
     * @return all of the current row data
     */
    private static String[] getRowData(Row row) {
        // get the last cell number
        // int lastCellNum = row.getLastCellNum();
        int lastCellNum = fieldCount;

        // create rowData[] to store rows data
        String[] rowData = new String[lastCellNum];

        for (int i = 0; i < lastCellNum; i++) {
            Cell cell = row.getCell(i);
            if (cell != null) {
                if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    rowData[i] = String.valueOf(date.getTime());
                } else {
                    cell.setCellType(CellType.STRING);
                    rowData[i] = cell.getStringCellValue();
                }
            } else {
                rowData[i] = null;
            }
        }
        return rowData;
    }

    /**
     * Check if row data match to entity property
     * 
     * @param <T>     entity type
     * @param entity  entity
     * @param rowData rowData[]
     * @param list    entity list
     * @return entity list
     */
    private static <T> List<T> checkFormat(Class<T> entity, String[] rowData, List<T> list) {
        // get field name list
        List<String> fieldName = getFieldName(getExcelList(entity, ExcelType.IMPORT));

        try {
            // T newInstance = entity.newInstance(); // deprecated method
            T newInstance = entity.getDeclaredConstructor().newInstance();

            final BeanInfo bi = Introspector.getBeanInfo(entity);
            for (final PropertyDescriptor pd : bi.getPropertyDescriptors()) {
                if (fieldName.contains(pd.getName())) {
                    Method writeMethod = pd.getWriteMethod();
                    if (writeMethod != null) {
                        if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                            writeMethod.setAccessible(true);
                        }
                        String value = rowData[fieldName.indexOf(pd.getName())];
                        if (!StringUtils.isEmpty(value)) {
                            Class<?> propertyType = pd.getPropertyType();
                            if (String.class == propertyType) {
                                writeMethod.invoke(newInstance, value);
                            } else if (Integer.class == propertyType) {
                                writeMethod.invoke(newInstance, Integer.valueOf(value));
                            } else if (Boolean.class == propertyType) {
                                writeMethod.invoke(newInstance, Boolean.valueOf(value));
                            } else if (Long.class == propertyType) {
                                writeMethod.invoke(newInstance, Double.valueOf(value).longValue());
                            } else if (Float.class == propertyType) {
                                writeMethod.invoke(newInstance, Float.valueOf(value));
                            } else if (Short.class == propertyType) {
                                writeMethod.invoke(newInstance, Short.valueOf(value));
                            } else if (Double.class == propertyType) {
                                writeMethod.invoke(newInstance, Double.valueOf(value));
                            } else if (Character.class == propertyType) {
                                if ((value != null) && (value.length() > 0)) {
                                    writeMethod.invoke(newInstance, Character.valueOf(value.charAt(0)));
                                }
                            } else if (Date.class == propertyType) {
                                writeMethod.invoke(newInstance, new Date(Long.parseLong(value)));
                            } else if (BigDecimal.class == propertyType) {
                                writeMethod.invoke(newInstance, new BigDecimal(value));
                            }
                        }
                    }
                }
            }
            // add new instance to list
            list.add(newInstance);
        } catch (Exception e) {
            // if field matches failed, then set list as null, then return to front-end web
            // maybe unsafe, to be improved at next version release
            log.info("Failed to match data field to current entity, please check checkFormat()!");
            return null;
        }
        return list;
    }

    /**
     * Import excel and get data
     * 
     * @param <T>         entity type
     * @param entity      entity
     * @param inputStream File input stream
     * @return entity list
     */
    public static <T> List<T> importExcel(Class<T> entity, InputStream inputStream) {
        List<T> list = new ArrayList<>();
        XSSFWorkbook workbook = null;
        try {
            ZipSecureFile.setMinInflateRatio(-1.0d);
            workbook = new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            log.info("Failed to import excel, please check importExcel()!");
            e.printStackTrace();
        }
        Assert.notNull(workbook, "Failed to create workbook, please check importExcel()!");

        XSSFSheet sheet = workbook.getSheetAt(0);
        Assert.notNull(sheet, "Failed to load sheet, please check importExcel()!");

        int flag = 0;
        for (Row row : sheet) {
            // skip row header part, DATAROW as new flag
            if (flag < DATAROW) {
                flag++;
                fieldCount = row.getLastCellNum();
                continue;
            }
            // check if field matched the pattern
            if (checkFormat(entity, getRowData(row), list) == null) {
                return null;
            }
        }
        return list;
    }
}
