package com.zgc.zhaopin.business.vo;

import com.zgc.zhaopin.business.entity.Role;
import com.zgc.zhaopin.framework.object.BaseConditionVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author emirio
 * @version 1.0
 * @date 2019/4/16 16:26
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleConditionVO extends BaseConditionVO {
    private Role role;
}

