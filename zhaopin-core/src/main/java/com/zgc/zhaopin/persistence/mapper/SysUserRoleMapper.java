package com.zgc.zhaopin.persistence.mapper;

import com.zgc.zhaopin.persistence.beans.SysUserRole;
import com.zgc.zhaopin.plugin.BaseMapper;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author emirio
 * @version 1.0
 * @date 2019/4/16 16:26
 * @since 1.0
 */
@Repository
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    List<Integer> findUserIdByRoleId(Integer roleId);
}
