package com.zgc.zhaopin.persistence.beans;

import com.zgc.zhaopin.framework.object.AbstractDO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author emirio
 * @version 1.0
 * @date 2019/4/16 16:26
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRoleResources extends AbstractDO {
    private Long roleId;
    private Long resourcesId;
}
