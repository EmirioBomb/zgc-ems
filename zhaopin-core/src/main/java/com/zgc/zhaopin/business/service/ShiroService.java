package com.zgc.zhaopin.business.service;

import java.util.Map;

import com.zgc.zhaopin.business.entity.User;

/**
 * Shiro-权限相关的业务处理
 *
 * @author emirio
 * @version 1.0
 * @date 2019/4/25 14:37
 * @since 1.0
 */
public interface ShiroService {

    /**
     * 初始化权限
     */
    Map<String, String> loadFilterChainDefinitions();

    /**
     * 重新加载权限
     */
    void updatePermission();

    /**
     * 重新加载用户权限
     *
     * @param user
     */
    void reloadAuthorizingByUserId(User user);

    /**
     * 重新加载所有拥有roleId角色的用户的权限
     *
     * @param roleId
     */
    void reloadAuthorizingByRoleId(Long roleId);

}
