package com.zgc.zhaopin.business.entity;

import java.io.Serializable;

import com.zgc.zhaopin.framework.object.AbstractBO;
import com.zgc.zhaopin.persistence.beans.SysDept;

/**
 * @author emirio
 * @version 1.0
 * @date 2020/4/23
 * @since 1.0
 */
public class Dept implements Serializable {

    private SysDept sysDept;

    public Dept() {
        this.sysDept = new SysDept();
    }

    public Dept(SysDept sysDept) {
        this.sysDept = sysDept;
    }

    public Dept(String deptName) {
        this();
        setDeptName(deptName);
    }

    public SysDept getSysDept() {
        return this.sysDept;
    }

    public void setSysDept(SysDept sysDept) {
        this.sysDept = sysDept;
    }

    public long getDeptId() {
        return this.sysDept.getDeptId();
    }

    public void setDeptId(long deptId) {
        this.sysDept.setDeptId(deptId);
    }

    public String getDeptName() {
        return this.sysDept.getDeptName();
    }

    public void setDeptName(String deptName) {
        this.sysDept.setDeptName(deptName);
    }

    public String getLevelId() {
        return this.sysDept.getLevelId();
    }

    public void setLevelId(String levelId) {
        this.sysDept.setLevelId(levelId);
    }

    public long getParentId() {
        return this.sysDept.getParentId();
    }

    public void setParentId(long parentId) {
        this.sysDept.setParentId(parentId);
    }
    
}