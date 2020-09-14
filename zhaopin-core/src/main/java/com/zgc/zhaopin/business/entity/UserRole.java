package com.zgc.zhaopin.business.entity;

import java.util.Date;

import com.zgc.zhaopin.persistence.beans.SysUserRole;

/**
 * @author emirio
 * @version 1.0
 * @date 2019/4/16 16:26
 * @since 1.0
 */
public class UserRole {

    private SysUserRole sysUserRole;

    public UserRole() {
        this.sysUserRole = new SysUserRole();
    }

    public UserRole(SysUserRole sysUserRole) {
        this.sysUserRole = sysUserRole;
    }

    public SysUserRole getSysUserRole() {
        return this.sysUserRole;
    }

    public Long getUserId() {
        return this.sysUserRole.getUserId();
    }

    public void setUserId(Long userId) {
        this.sysUserRole.setUserId(userId);
    }

    public Long getRoleId() {
        return this.sysUserRole.getRoleId();
    }

    public void setRoleId(Long roleId) {
        this.sysUserRole.setRoleId(roleId);
    }

    public Date getCreateTime() {
        return this.sysUserRole.getCreateTime();
    }

    public void setCreateTime(Date regTime) {
        this.sysUserRole.setCreateTime(regTime);
    }

    public Date getUpdateTime() {
        return this.sysUserRole.getUpdateTime();
    }

    public void setUpdateTime(Date updateTime) {
        this.sysUserRole.setUpdateTime(updateTime);
    }
}
