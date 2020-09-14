package com.zgc.zhaopin.persistence.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

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
@Excel("角色数据")
public class SysRole extends AbstractDO {
    
    @Excel("角色描述")
    private String description;
    @Excel("角色名")
    private String name;
    @Excel("是否可用")
    private Boolean available;

    @Transient
    private Integer selected;
}
