package com.zgc.zhaopin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zgc.zhaopin.business.entity.User;
import com.zgc.zhaopin.business.enums.ResponseStatus;
import com.zgc.zhaopin.business.service.SysUserRoleService;
import com.zgc.zhaopin.business.service.SysUserService;
import com.zgc.zhaopin.business.vo.UserConditionVO;
import com.zgc.zhaopin.framework.excel.ExcelUtils;
import com.zgc.zhaopin.framework.excel.SpringContextUtil;
import com.zgc.zhaopin.framework.object.PageResult;
import com.zgc.zhaopin.framework.object.ResponseVO;
import com.zgc.zhaopin.persistence.beans.SysUser;
import com.zgc.zhaopin.util.PasswordUtil;
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

/**
 * 用户管理
 *
 * @author emirio
 * @version 1.0
 * @date 2019/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/user")
public class RestUserController {
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysUserRoleService userRoleService;

    @RequiresPermissions("users")
    @PostMapping("/list")
    public PageResult list(UserConditionVO vo) {
        PageInfo<User> pageInfo = userService.findPageBreakByCondition(vo);
        PageHelper.clearPage();
        return ResultUtil.tablePage(pageInfo);
    }

    /**
     * 保存用户角色
     *
     * @param userId
     * @param roleIds
     *         用户角色
     *         此处获取的参数的角色id是以 “,” 分隔的字符串
     * @return
     */
    @RequiresPermissions("user:allotRole")
    @PostMapping("/saveUserRoles")
    public ResponseVO saveUserRoles(Long userId, String roleIds) {
        if (StringUtils.isEmpty(userId)) {
            return ResultUtil.error("error");
        }
        userRoleService.addUserRole(userId, roleIds);
        return ResultUtil.success("成功");
    }

    @RequiresPermissions("user:add")
    @PostMapping(value = "/add")
    public ResponseVO add(User user) {
        User u = userService.getByUserName(user.getUsername());
        long id = userService.checkCurrentMaxID();
        if (u != null) {
            return ResultUtil.error("该用户名[" + user.getUsername() + "]已存在！请更改用户名");
        }
        try {
            user.setId(id);
            user.setPassword(PasswordUtil.encrypt(user.getPassword(), user.getUsername()));
            userService.insert(user);
            return ResultUtil.success("成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("error");
        }
    }

    @RequiresPermissions(value = {"user:batchDelete", "user:delete"}, logical = Logical.OR)
    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            userService.removeByPrimaryKey(id);
            userRoleService.removeByUserId(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 个用户");
    }

    @RequiresPermissions("user:edit")
    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.userService.getByPrimaryKey(id));
    }

    @RequiresPermissions("user:edit")
    @PostMapping("/edit")
    public ResponseVO edit(User user) {
        try {
            userService.updateSelective(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("用户修改失败！");
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }


    @GetMapping("/export")
    public void exportExcel() {
        userService = SpringContextUtil.getBean(SysUserService.class);
        ExcelUtils.exportExcel(SysUser.class, userService.listAll());
    }

    @GetMapping("/exportTemplate")
    public void exportTemplateExcel() {
        userService = SpringContextUtil.getBean(SysUserService.class);
        ExcelUtils.downloadTemplate(SysUser.class);    
    }

    @PostMapping("/import")
    public JSONObject importExcel(HttpServletRequest request) {
        List<SysUser> list = ExcelUtils.importExcel(SysUser.class, ExcelUtils.getFileInputStream(request));
        JSONObject json = userService.insertListWithChecked(list, userService);
        return json;
    }

}
