package com.zgc.zhaopin.persistence.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import com.zgc.zhaopin.framework.excel.annotation.Excel;
import com.zgc.zhaopin.framework.object.AbstractDO;

/**
 * @author emirio
 * @version 1.0
 * @date 2019/4/16 16:26
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Excel("用户数据")
public class SysUser extends AbstractDO {
    @Excel("用户名")
    private String username;
    private String password;
    @Excel("用户匿名")
    private String nickname;
    @Excel("电话号码")
    private String mobile;
    @Excel("邮箱地址")
    private String email;
    @Excel("QQ")
    private String qq;
    @Excel("生日")
    private Date birthday;

    // private String deptName;
    private Integer gender;
    private String avatar;
    private String userType;
    private String regIp;
    @Excel("最后登陆IP")
    private String lastLoginIp;
    @Excel("最后登陆时间")
    private Date lastLoginTime;
    @Excel("登陆次数")
    private Integer loginCount;
    private String remark;
    private Integer status;
}
