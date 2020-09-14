package com.zgc.zhaopin.business.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zgc.zhaopin.business.entity.User;
import com.zgc.zhaopin.business.vo.UserConditionVO;
import com.zgc.zhaopin.framework.object.AbstractService;
import com.zgc.zhaopin.persistence.beans.SysUser;

import java.util.List;

/**
 * 用户
 *
 * @author emirio
 * @version 1.0
 * @date 2019/4/16 16:26
 * @since 1.0
 */
public interface SysUserService extends AbstractService<User, Long> {

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    PageInfo<User> findPageBreakByCondition(UserConditionVO vo);

    /**
     * 更新用户最后一次登录的状态信息
     *
     * @param user
     * @return
     */
    User updateUserLastLoginInfo(User user);

    /**
     * 根据用户名查找
     *
     * @param userName
     * @return
     */
    User getByUserName(String userName);

    /**
     * 通过角色Id获取用户列表
     *
     * @param roleId
     * @return
     */
    List<User> listByRoleId(Long roleId);

    public JSONObject insertListWithChecked(List<SysUser> sysUsers, SysUserService userService);

    public Long checkCurrentMaxID();


}
