package com.zgc.zhaopin.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zgc.zhaopin.business.entity.Resources;
import com.zgc.zhaopin.business.enums.ResponseStatus;
import com.zgc.zhaopin.business.service.ShiroService;
import com.zgc.zhaopin.business.service.SysResourcesService;
import com.zgc.zhaopin.business.vo.ResourceConditionVO;
import com.zgc.zhaopin.framework.excel.ExcelUtils;
import com.zgc.zhaopin.framework.excel.SpringContextUtil;
import com.zgc.zhaopin.framework.object.PageResult;
import com.zgc.zhaopin.framework.object.ResponseVO;
import com.zgc.zhaopin.persistence.beans.SysResources;
import com.zgc.zhaopin.util.ResultUtil;

/**
 * 系统资源管理
 *
 * @author emirio
 * @version 1.0
 * @date 2019/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/resources")
public class RestResourcesController {

    @Autowired
    private SysResourcesService resourcesService;
    @Autowired
    private ShiroService shiroService;

    @RequiresPermissions("resources")
    @PostMapping("/list")
    public PageResult getAll(ResourceConditionVO vo) {
        PageInfo<Resources> pageInfo = resourcesService.findPageBreakByCondition(vo);
        // PageHelper.clearPage();
        return ResultUtil.tablePage(pageInfo);
    }

    @RequiresPermissions("role:allotResource")
    @PostMapping("/resourcesWithSelected")
    public ResponseVO resourcesWithSelected(Long rid) {
        return ResultUtil.success(null, resourcesService.queryResourcesListWithSelected(rid));
    }

    @RequiresPermissions("resource:add")
    @PostMapping(value = "/add")
    public ResponseVO add(Resources resources) {
        resources.setId(resourcesService.checkCurrentMaxID());
        resourcesService.insert(resources);
        //更新权限
        shiroService.updatePermission();
        return ResultUtil.success("成功");
    }

    @RequiresPermissions(value = {"resource:batchDelete", "resource:delete"}, logical = Logical.OR)
    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            resourcesService.removeByPrimaryKey(id);
        }

        //更新权限
        shiroService.updatePermission();
        return ResultUtil.success("成功删除 [" + ids.length + "] 个资源");
    }

    @RequiresPermissions("resource:edit")
    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.resourcesService.getByPrimaryKey(id));
    }

    @RequiresPermissions("resource:edit")
    @PostMapping("/edit")
    public ResponseVO edit(Resources resources) {
        try {
            resourcesService.updateSelective(resources);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("资源修改失败！");
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }


    @GetMapping("/export")
    public void exportExcel() {
        resourcesService = SpringContextUtil.getBean(SysResourcesService.class);
        ExcelUtils.exportExcel(SysResources.class, resourcesService.listAll());
    }

    @GetMapping("/exportTemplate")
    public void exportTemplateExcel() {
        resourcesService = SpringContextUtil.getBean(SysResourcesService.class);
        ExcelUtils.downloadTemplate(SysResources.class);  
    }

    @PostMapping("/import")
    public JSONObject importExcel(HttpServletRequest request) {
        List<SysResources> list = ExcelUtils.importExcel(SysResources.class, ExcelUtils.getFileInputStream(request));
        JSONObject json = resourcesService.insertListWithChecked(list, resourcesService);
        return json;
    } 
}
