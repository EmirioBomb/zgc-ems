package com.zgc.zhaopin.persistence.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zgc.zhaopin.framework.excel.annotation.Excel;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 创建可序列化的员工
 * @author emirio
 * @version v1.0
 * @date 2020/6/15
 * @since v1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Excel("员工信息")
public class SysEmployee implements Serializable {
    
    /**
     *  default serialVersionUID
     */
    private static final long serialVersionUID = 100990L;

    @Excel("备注")
    private String remark;

    @Excel("员工姓名")
    private String name;

    @Excel("员工性别")
    private String sex;

    @Excel("出生日期")
    // @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // private LocalDate birthday;
    private Date birthday;

    @Excel("员工年龄")
    private Integer age;

    @Excel("电话号码")
    private String phone;

    @Excel("住址")
    private String address;

    @Excel("工资")
    private Long salary;

    @Excel("电子邮箱")
    private String email;

    @Excel("岗位")
    private String job;

    @Excel("状态")
    private Byte status;

    @Excel("部门编号")
    private Long deptId;

    @Excel("入职日期")
    // @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // private LocalDate entryTime;
    private Date entryTime;

    @Excel("离职日期")
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date departureTime;

    @Excel("修改日期")
    // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    
    @Id
    @Excel("员工编号")
    private Long id;

   
    

}