package com.zgc.zhaopin.controller;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zgc.zhaopin.business.entity.Employee;
import com.zgc.zhaopin.business.service.SysEmployeeService;
import com.zgc.zhaopin.business.vo.EmployeeConditionVO;
import com.zgc.zhaopin.framework.excel.ExcelUtils;
import com.zgc.zhaopin.framework.excel.SpringContextUtil;
import com.zgc.zhaopin.framework.object.PageResult;
import com.zgc.zhaopin.framework.object.ResponseVO;
import com.zgc.zhaopin.persistence.beans.SysEmployee;
import com.zgc.zhaopin.util.ResultUtil;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/employee")
// @ControllerAdvice
public class RestEmployeeController {

    @Autowired
    private SysEmployeeService employeeService;

    @InitBinder
    protected void InitBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); 
    }


    @RequiresPermissions("employee")
    @PostMapping("/list")
    public PageResult list(EmployeeConditionVO vo) {
        PageInfo<Employee> pageInfo = employeeService.findPageBreakbyCondition(vo);
        // PageHelper.clearPage();
        return ResultUtil.tablePage(pageInfo);
    }
   
    @RequiresPermissions("employee:info")
    @GetMapping("/info")
    public ModelAndView info() {
        return ResultUtil.view("employee/info");
    }

    @RequiresPermissions("employee:edit")
    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.employeeService.getByPrimaryKey(id));
    }

    @RequiresPermissions("employee:edit")
    @PostMapping("/edit")
    public ResponseVO edit(Employee employee) {
        try {
            employeeService.updateSelective(employee);
        } catch (Exception e) {
            return ResultUtil.error("员工修改失败！");
        }

        return ResultUtil.success("成功修改员工信息！");
    }

    @RequiresPermissions("employee:add")
    @PostMapping("/add")
    public ResponseVO add(SysEmployee sysEmployee) {
        Employee employee = new Employee(sysEmployee);
        Date date = sysEmployee.getBirthday();
        LocalDate birthDate = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        Period diff = Period.between(birthDate, LocalDate.now());
        int age = diff.getYears();
        Long employeeId = employeeService.checkCurrentMaxID();
        employee.setId(employeeId);
        employee.setAge(age);
        try {
            employeeService.insert(employee);
            return ResultUtil.success("成功添加员工信息！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("添加员工信息失败！");
        }
    }

    @RequiresPermissions(value = {"employee:batchDelete", "employee:delete"}, logical = Logical.OR)
    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            employeeService.removeByPrimaryKey(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 个用户");
    }


    @GetMapping("/export")
    public void exportExcel() {
        employeeService = SpringContextUtil.getBean(SysEmployeeService.class);
        ExcelUtils.exportExcel(SysEmployee.class, employeeService.listAll());
    }

    @GetMapping("/exportTemplate")
    public void exportTemplateExcel() {
        employeeService = SpringContextUtil.getBean(SysEmployeeService.class);
        ExcelUtils.downloadTemplate(SysEmployee.class); 
    }
    
    @PostMapping("/import")
    public JSONObject importExcel(HttpServletRequest request) {
        List<SysEmployee> list = ExcelUtils.importExcel(SysEmployee.class, ExcelUtils.getFileInputStream(request));
        System.out.println("Import Data is:" + list.toArray().toString());;
        return null;
    }

    @RequiresPermissions("employee")
    @GetMapping("/groupBySex")
    public String groupBySex() {
        String json= JSON.toJSONString(employeeService.listSexByGroup());
        return json;
    }

    @RequiresPermissions("employee")
    @GetMapping("/groupByAge")
    public String groupByAge() {
        String json= JSON.toJSONString(employeeService.listSexByAge());
        return json;
    }

    @RequiresPermissions("employee")
    @GetMapping("/groupByRange")
    public String groupByRange() {
        String json= JSON.toJSONString(employeeService.listSexByRange());
        return json;
    }

    @RequiresPermissions("employee")
    @GetMapping("/groupByEmployee")
    public String groupByEmployee() {
        String json= JSON.toJSONString(employeeService.listByEmployee());
        return json;
    }

    @RequiresPermissions("employee")
    @GetMapping("/groupByDept")
    public String groupByDept() {
        String json= JSON.toJSONString(employeeService.listByDept());
        return json;
    }

    @RequiresPermissions("employee")
    @GetMapping("/groupByArea")
    public String groupByArea() {
        String json= JSON.toJSONString(employeeService.listByArea());
        return json;
    }

    @RequiresPermissions("employee")
    @GetMapping("/groupBySalary")
    public String groupBySalary() {
        String json= JSON.toJSONString(employeeService.listBySalary());
        return json;
    }

    @RequiresPermissions("employee")
    @GetMapping("/groupByAnnual")
    public String groupByAnnual() {
        String json= JSON.toJSONString(employeeService.listByAnnuallySalaryByMonth());
        return json;
    }
    
}