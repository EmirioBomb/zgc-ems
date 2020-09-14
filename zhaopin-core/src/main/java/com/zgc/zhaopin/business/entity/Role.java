package com.zgc.zhaopin.business.entity;

import java.util.Date;

import com.zgc.zhaopin.persistence.beans.SysRole;

/**
 * @author emirio
 * @version 1.0
 * @date 2019/4/16 16:26
 * @since 1.0
 */
public class Role {
    private SysRole sysRole;

    public Role() {
        this.sysRole = new SysRole();
    }

    public Role(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    public SysRole getSysRole() {
        return this.sysRole;
    }

    public Long getId() {
        return this.sysRole.getId();
    }

    public void setId(Long id) {
        this.sysRole.setId(id);
    }

    public String getName() {
        return this.sysRole.getName();
    }

    public void setName(String name) {
        this.sysRole.setName(name);
    }


    public String getDescription() {
        return this.sysRole.getDescription();
    }

    public void setDescription(String description) {
        this.sysRole.setDescription(description);
    }

    public String getAvailable() {
        boolean value = this.sysRole.getAvailable();
        return value ? "true" : "false";
    }

    public void setAvailable(boolean available) {
        this.sysRole.setAvailable(available);
    }

    public Date getCreateTime() {
        return this.sysRole.getCreateTime();
    }

    public void setCreateTime(Date regTime) {
        this.sysRole.setCreateTime(regTime);
    }

    public Date getUpdateTime() {
        return this.sysRole.getUpdateTime();
    }

    public void setUpdateTime(Date updateTime) {
        this.sysRole.setUpdateTime(updateTime);
    }

}

