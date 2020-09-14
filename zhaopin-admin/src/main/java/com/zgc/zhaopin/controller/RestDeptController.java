package com.zgc.zhaopin.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.zxing.Result;
import com.zgc.zhaopin.business.entity.Dept;
import com.zgc.zhaopin.business.entity.User;
import com.zgc.zhaopin.business.entity.UserRole;
import com.zgc.zhaopin.business.enums.ResponseStatus;
import com.zgc.zhaopin.business.service.SysDeptService;
import com.zgc.zhaopin.business.service.SysUserService;
import com.zgc.zhaopin.business.vo.DeptConditionVO;
import com.zgc.zhaopin.business.vo.UserConditionVO;
import com.zgc.zhaopin.framework.excel.ExcelUtils;
import com.zgc.zhaopin.framework.excel.SpringContextUtil;
import com.zgc.zhaopin.framework.object.PageResult;
import com.zgc.zhaopin.framework.object.ResponseVO;
import com.zgc.zhaopin.persistence.beans.SysDept;
import com.zgc.zhaopin.persistence.beans.SysUser;
import com.zgc.zhaopin.util.ResultUtil;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

// import jdk.internal.jline.internal.Log;
import lombok.extern.slf4j.Slf4j;

/**
 * 部门管理
 * 
 * @author emirio
 * @version 1.0
 * @date 2020/4
 * @siince 1.0
 */
@RestController
@RequestMapping("/dept")
public class RestDeptController {

    /**
     * add deptartment service
     */
    @Autowired
    private SysDeptService deptService;

    @RequiresPermissions("dept")
    @PostMapping("/list")
    public PageResult list(DeptConditionVO vo) {
        PageInfo<Dept> pageInfo = deptService.findPageBreakbyCondition(vo);
        // PageHelper.clearPage();
        return ResultUtil.tablePage(pageInfo);
    }

    /**
     * 新增部门
     * 
     * @param dept
     * @return ResponseVO
     */
    @RequiresPermissions("dept:add")
    @PostMapping(value = "/add")
    public ResponseVO add(Dept dept) {       
        try {
            // mysql数据库就解开下面的注释，注释掉deptService.insertDept(dept);
            // deptService.insert(dept);

            // oracle执行下列语句
            deptService.insertDept(dept);
            
            return ResultUtil.success("添加部门成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("添加部门失败，请重试！");
        }
    }

    @RequiresPermissions(value = { "dept:batchDelete", "dept:delete" }, logical = Logical.OR)
    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条部门记录！");
        }

        if (ids.length == 1) {
            deptService.removeByPrimaryKey(ids[0]);
            return ResultUtil.success("成功删除部门信息！");
        } else {
            for (Long id : ids) {
                deptService.removeByPrimaryKey(id);
            }
        }

        return ResultUtil.success("成功删除 " + ids.length + "个部门信息！");
    }

    @RequiresPermissions("dept:edit")
    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.deptService.getByPrimaryKey(id));
    }

    @RequiresPermissions("dept:edit")
    @PostMapping("/edit")
    public ResponseVO edit(Dept dept) {
        try {
            deptService.updateSelective(dept);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("部门修改失败！");
        }

        // return ResultUtil.success(ResponseStatus.SUCCESS);
        return ResultUtil.success("成功修改部门信息");
    }

    @RequiresPermissions("dept:tree")
    @PostMapping("getDeptTree")
    public String getDeptTree() {
        return deptService.getDeptTree();
    }

    @GetMapping("/export")
    public void exportExcel() {
        deptService = SpringContextUtil.getBean(SysDeptService.class);
        ExcelUtils.exportExcel(SysDept.class, deptService.listAll());
    }

    @GetMapping("/exportTemplate")
    public void exportTemplateExcel() {
        deptService = SpringContextUtil.getBean(SysDeptService.class);
        ExcelUtils.downloadTemplate(SysDept.class);    
    }

    @PostMapping("/import")
    public JSONObject importExcel(HttpServletRequest request) {
        List<SysDept> list = ExcelUtils.importExcel(SysDept.class, ExcelUtils.getFileInputStream(request));
        JSONObject json = deptService.insertListWithChecked(list, deptService);
        return json;
    }

}