package com.zgc.zhaopin.business.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zgc.zhaopin.business.entity.Role;
import com.zgc.zhaopin.business.vo.RoleConditionVO;
import com.zgc.zhaopin.framework.object.AbstractService;
import com.zgc.zhaopin.persistence.beans.SysRole;

import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 * @author emirio
 * @version 1.0
 * @date 2019/4/16 16:26
 * @since 1.0
 */
public interface SysRoleService extends AbstractService<Role, Long> {

    /**
     * 获取ztree使用的角色列表
     *
     * @param uid
     * @return
     */
    List<Map<String, Object>> queryRoleListWithSelected(Integer uid);

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    PageInfo<Role> findPageBreakByCondition(RoleConditionVO vo);

    /**
     * 获取用户的角色
     *
     * @param userId
     * @return
     */
    List<Role> listRolesByUserId(Long userId);

    JSONObject insertListWithChecked(List<SysRole> sysRoles, SysRoleService roleService);
}
