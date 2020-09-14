package com.zgc.zhaopin.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zgc.zhaopin.business.entity.Role;
import com.zgc.zhaopin.business.enums.ResponseStatus;
import com.zgc.zhaopin.business.service.ShiroService;
import com.zgc.zhaopin.business.service.SysRoleResourcesService;
import com.zgc.zhaopin.business.service.SysRoleService;
import com.zgc.zhaopin.business.vo.RoleConditionVO;
import com.zgc.zhaopin.framework.excel.ExcelUtils;
import com.zgc.zhaopin.framework.excel.SpringContextUtil;
import com.zgc.zhaopin.framework.object.PageResult;
import com.zgc.zhaopin.framework.object.ResponseVO;
import com.zgc.zhaopin.persistence.beans.SysRole;
import com.zgc.zhaopin.util.ResultUtil;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统角色管理
 *
 * @author emirio
 * @version 1.0
 * @date 2019/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/roles")
public class RestRoleController {
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysRoleResourcesService roleResourcesService;
    @Autowired
    private ShiroService shiroService;

    @RequiresPermissions("roles")
    @PostMapping("/list")
    public PageResult getAll(RoleConditionVO vo) {
        PageInfo<Role> pageInfo = roleService.findPageBreakByCondition(vo);
        // PageHelper.clearPage();
        return ResultUtil.tablePage(pageInfo);
    }

    @RequiresPermissions("user:allotRole")
    @PostMapping("/rolesWithSelected")
    public ResponseVO<List<Role>> rolesWithSelected(Integer uid) {
        return ResultUtil.success(null, roleService.queryRoleListWithSelected(uid));
    }

    @RequiresPermissions("role:allotResource")
    @PostMapping("/saveRoleResources")
    public ResponseVO saveRoleResources(Long roleId, String resourcesId) {
        if (StringUtils.isEmpty(roleId)) {
            return ResultUtil.error("error");
        }
        roleResourcesService.addRoleResources(roleId, resourcesId);
        // 重新加载所有拥有roleId的用户的权限信息
        shiroService.reloadAuthorizingByRoleId(roleId);
        return ResultUtil.success("成功");
    }

    @RequiresPermissions("role:add")
    @PostMapping(value = "/add")
    public ResponseVO add(Role role) {
        roleService.insert(role);
        return ResultUtil.success("成功");
    }

    @RequiresPermissions(value = {"role:batchDelete", "role:delete"}, logical = Logical.OR)
    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            roleService.removeByPrimaryKey(id);
            roleResourcesService.removeByRoleId(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 个角色");
    }

    @RequiresPermissions("role:edit")
    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.roleService.getByPrimaryKey(id));
    }

    @RequiresPermissions("role:edit")
    @PostMapping("/edit")
    public ResponseVO edit(Role role) {
        try {
            roleService.updateSelective(role);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("角色修改失败！");
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

    @GetMapping("/export")
    public void exportExcel() {
        roleService = SpringContextUtil.getBean(SysRoleService.class);
        ExcelUtils.exportExcel(SysRole.class, roleService.listAll());
    }

    @GetMapping("/exportTemplate")
    public void exportTemplateExcel() {
        roleService = SpringContextUtil.getBean(SysRoleService.class);
        ExcelUtils.downloadTemplate(SysRole.class);    
    }

    @PostMapping("/import")
    public JSONObject importExcel(HttpServletRequest request) {
        List<SysRole> list = ExcelUtils.importExcel(SysRole.class, ExcelUtils.getFileInputStream(request));
        JSONObject json = roleService.insertListWithChecked(list, roleService);
        return json;
    }

}
