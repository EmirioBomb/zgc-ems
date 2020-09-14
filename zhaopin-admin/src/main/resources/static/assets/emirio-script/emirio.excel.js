/**
 * Excel upload and download utils
 * there're still lots of work need to be improved...
 * 
 * Export: 
 *  1. export all data as excel format from database
 *  2. export field template
 * 
 *  To be added new features
 *  1. export select items
 *  2. export more format like csv/txt/json
 * 
 * Import:
 *  1. import excel(*.xlsx) into database
 *  2. check if *.xlsx is correct
 * 
 * Notice:
 *  1. sweetalert2 must before sweetalert.js
 * 
 * @author emirio
 * @version 1.3
 * @date 2020/5/21
 * @since v1.0
 */

// set customUrl as global url
 var customUrl;

(function ($) {
    $.extend({
        /**
         * Usage: 
         *  add code as follows to dept.list/user.list...
         *  $.init(options.importUrl);
         * 
         * @param {optionsUrl} importUrl 
         */
        init: function(optionUrl) {
            customUrl = optionUrl;
        }
    })

    /**
        Export excel
        -> 
        the best method to export excel by <a> element

        <a href="/dept/export">Export excel</a>

        ->
        $("#export-excel-btn").on('click',function() {
            location.href = "/dept/export";
        });
    */


    /**
        bind import-excel-btn to trigger input-file function

        Description:
        
        -> 
        bind a new button cover input-file style
        
        -> 
        if bind click event, bugs will occurred

        @author emirio
        @date 2020/5/21
        @version v1.3
    */
    $("#import-excel-btn").on('click', function() {
        // it must be trigger, not 'click'
        $("#open-file").trigger('click');
    })


    /** 
        Transfer formdata to back-end

        Description and Usage:

        1. 
        ("#open-file").on("change"), must be on("change"), not "click" event

        2. add code to dept.list/user.list...
        <input type="file" style="display: none;" id="open-file" name="file" 
        accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"> 
            Description:
                1. accept: filter the file as xlsx format
                2. name: identify as "file" in formData
        
        2.1 add to dept.list/user.list...
        $.init(options.importUrl);

        3. 
        Maybe <form> is convinent
        <form id="formId" method="POST" enctype="multipart/form-data">
            <input type="file" id="file"></input>
        </form>

        4 
        Notice: <input type="xxxfile" name="xxx">
        <name> is needed when transfer DataForm

        ---------------------------------Another Format----------------------------------------
        const settings = {
            "async": true,
            "crossDomain": true,
            "url": customUrl,
            "method": "POST",
            "processData": false,
            "contentType": false,
            "mimeType": "multipart/form-data",
            "data": formData,
        };
        $.ajax(settings).done(function (response) {
            console.log('Response: ',response);
        });
        --------------------------------Transfer file path-------------------------------------    
        -> Front-End Code:

        var formData = new FormData();
        formData.append("file", $("#open-file")[0].files[0]);

        var filePath= "/Users/xxx/xxx.xlsx";
        filePath= encodeURIComponent(filePath);
        $.ajax({
            type: "POST,
            url: "/dept/import",
            data: {'filePath': filePath},
            success: fucnction() {
                console.log("success");
            }
        });

        -> Back-End Code:

        @PostMapping("/import")
        public String importExcel(String filePath) {
            filePath = URLDecoder.decde(filePath, "UTF-8");
            ...
        } 
        
        @author emirio
        @date 2020/5/21
        @version v1.3
    */
    $("#open-file").on("change", function (e) {
        // get file list
        // const fileList = e.target.files || e.dataTransfer.files;
        
        // create FormData 
        var formData = new FormData();
        formData.append("file", $("#open-file")[0].files[0]);

        // transfer formdata to back-end
        $.ajax({
            async: true,
            crossDomain: true,
            url: customUrl,
            type: "post",
            data: formData,
            processData: false,
            contentType: false,
            success: function(data) {
                console.log(data);
                if(data.match == "fail") {
                    Swal.fire({
                        icon: 'error',
                        title: '数据导入失败',
                        html:
                            '您导入的文件中数据类型与系统要求的不匹配，请检查文件后重试！',
                        confirmButtonText: '确定',   
                        allowEscapeKey: false,
                        allowOutsideClick: false             
                    }).then((result) => {
                        if(result.value) {
                            location.reload();
                        }
                    }); 
                } else if((data.insert === 0) && (data.update === 0) && (data.fail === 0)){
                    Swal.fire({
                        icon: 'info',
                        title: '无数据导入',
                        html:
                            '您导入的文件是空的，请添加有数据的文件！',
                        confirmButtonText: '确定',   
                        allowEscapeKey: false,
                        allowOutsideClick: false             
                    }).then((result) => {
                        if(result.value) {
                            location.reload();
                        }
                    });
                } else if((data.insert === 'undefined') && (data.update === 'undefined') && (data.fail === 'undefined')){
                    Swal.fire({
                        icon: 'info',
                        title: '数据导入出错',
                        html:
                            '数据导入出错，请检查后台代码！',
                        confirmButtonText: '确定',   
                        allowEscapeKey: false,
                        allowOutsideClick: false             
                    }).then((result) => {
                        if(result.value) {
                            location.reload();
                        }
                    });
                } else {
                    Swal.fire({
                        icon: 'success',
                        title: '数据导入成功',
                        html:
                            '成功插入数据: <span style="color: blue">' + data.insert + '</span>条<br>' +
                            '成功更新数据: <span style="color: green">' + data.update + '</span>条<br>' +
                            '数据导入失败: <span style="color: red">' + data.fail + '</span>条',
                        confirmButtonText: '确定',  
                        allowEscapeKey: false,
                        allowOutsideClick: false              
                    }).then((result) => {
                        if(result.value) {
                            location.reload();
                        }
                    });
                }
            },
            error: function(data) {
                console.log(data);
                Swal.fire({
                    icon: 'error',
                    title: '数据发送失败',
                    text: '请检查网络！',
                    confirmButtonText: '确定',
                    allowEscapeKey: false,
                    allowOutsideClick: false
                }).then((result) => {
                    if(result.value) {
                        location.reload();
                    }
                })
            }
        });
    });
 
})(jQuery)



// var reader = new FileReader();
    // reader.onload = function (e) {
    //     try {
    //         var data = e.target.result;
    //         var workbook = XLSX.read(data, { type: 'binary' });
    //         var sheetNames = workbook.SheetNames; // 工作表名称集合
    //         var worksheet = workbook.Sheets[sheetNames[0]]; // 只读取第一张sheet
    //         var html = XLSX.utils.sheet_to_html(worksheet);//解析成html
    //         console.log(html)
    //         $("#tablelist").html(html)//将解析的html放入表格
    //     } catch (err) {
    //         console.log(err)
    //         Anim.msg("文件类型不正确");
    //         return false;
    //     }

    // };
    // reader.readAsBinaryString(ev.target.files[0]);



    

   
