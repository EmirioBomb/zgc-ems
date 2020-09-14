package com.zgc.zhaopin.persistence.mapper;

import com.zgc.zhaopin.business.entity.User;
import com.zgc.zhaopin.business.vo.UserConditionVO;
import com.zgc.zhaopin.persistence.beans.SysUser;
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
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUser> findPageBreakByCondition(UserConditionVO vo);

    List<SysUser> listByRoleId(Long roleId);

    Long checkCurrentMaxID();

    Long insertUser(User user);

}
