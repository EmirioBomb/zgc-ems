package com.zgc.zhaopin.business.vo;

import com.zgc.zhaopin.framework.object.BaseConditionVO;
import com.zgc.zhaopin.persistence.beans.SysEmployee;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmployeeConditionVO extends BaseConditionVO {
    private SysEmployee sysEmployee;
}