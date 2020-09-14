package com.zgc.zhaopin.business.vo;

import com.zgc.zhaopin.business.entity.Dept;
import com.zgc.zhaopin.framework.object.BaseConditionVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DeptCondition VO
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeptConditionVO extends BaseConditionVO {

    private Dept dept;
}