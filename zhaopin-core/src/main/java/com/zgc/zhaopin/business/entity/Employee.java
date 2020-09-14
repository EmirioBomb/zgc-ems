package com.zgc.zhaopin.business.entity;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.Date;

import com.zgc.zhaopin.persistence.beans.SysEmployee;

/** 
 * create Employee entity
 * 
 * @author emirio
 * @version 1.0
 * @date 2020/6/15
 * @since 1.0
 */
public class Employee implements Serializable {

    /**
     * set serialVersionUID for employee entity
     */
    // private static final long serialVersionUID = 1L;
    
    private SysEmployee sysEmployee;

    public Employee() {
        this.sysEmployee = new SysEmployee();
    }

    public Employee(SysEmployee sysEmployee) {
        this.sysEmployee = sysEmployee;
    }

    public Employee(String name) {
        this();
        setName(name);
    }

    public SysEmployee getSysEmployee() {
        return this.sysEmployee;
    }

    public void setSysEmployee(SysEmployee sysEmployee) {
        this.sysEmployee = sysEmployee;
    }

    public long getId() {
        return this.sysEmployee.getId();
    }

    public void setId(long id) {
        this.sysEmployee.setId(id);
    }

    public String getName() {
        return this.sysEmployee.getName();
    }

    public void setName(String name) {
        this.sysEmployee.setName(name);
    }

    public String getSex() {
        return this.sysEmployee.getSex();
    }

    public void setSex(String sex) {
        this.sysEmployee.setSex(sex);
    }

    public Date getBirthday() {
        return this.sysEmployee.getBirthday();
    }

    public void setBirthday(Date birthday) {
        this.sysEmployee.setBirthday(birthday);
    }

    public int getAge() {
        return this.sysEmployee.getAge();
    }

    public void setAge(int age) {
        this.sysEmployee.setAge(age);
    }

    public String getPhone() {
        return this.sysEmployee.getPhone();
    }

    public void setPhone(String phone) {
        this.sysEmployee.setPhone(phone);
    }

    public String getAddress() {
        return this.sysEmployee.getAddress();
    }

    public void setAddress(String address) {
        this.sysEmployee.setAddress(address);
    }

    public Long getSalary() {
        return this.sysEmployee.getSalary();
    }
    
    public void setSalary(Long salary) {
        this.sysEmployee.setSalary(salary);
    }

    public String getEmail() {
        return this.sysEmployee.getEmail();
    }

    public void setEmail(String email) {
        this.sysEmployee.setEmail(email);
    }

    public String getJob() {
        return this.sysEmployee.getJob();
    }

    public void setJob(String job) {
        this.sysEmployee.setJob(job);
    }

    public Byte getEmployeStatus() {
        return this.sysEmployee.getStatus();
    }

    public void setStatus(Byte status) {
        this.sysEmployee.setStatus(status);
    }
    
    public Long getDeptId() {
        return this.sysEmployee.getDeptId();
    }

    public void setDeptId(Long id) {
        this.sysEmployee.setDeptId(id);
    }

    public Date getEntryTime() {
        return this.sysEmployee.getEntryTime();
    }

    public void setEntryTime(Date entryTime) {
        this.sysEmployee.setEntryTime(entryTime);
    }

    public Date getDepartureTime() {
        return this.sysEmployee.getDepartureTime();
    }

    public void setDepartureTime(Date departureTime) {
        this.sysEmployee.setDepartureTime(departureTime);
    }

    public Date getModifyTime() {
        return this.sysEmployee.getModifyTime();
    }

    public void setModifyTime(Date modifyTime) {
        this.sysEmployee.setModifyTime(modifyTime);
    }

    public String getRemark() {
        return this.sysEmployee.getRemark();
    }

    public void setRemark(String remark) {
        this.sysEmployee.setRemark(remark);
    }
}