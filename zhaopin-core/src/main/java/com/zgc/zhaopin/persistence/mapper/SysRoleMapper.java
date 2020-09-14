package com.zgc.zhaopin.persistence.mapper;

import com.zgc.zhaopin.business.vo.RoleConditionVO;
import com.zgc.zhaopin.persistence.beans.SysRole;
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
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    List<SysRole> findPageBreakByCondition(RoleConditionVO vo);

    /**
     *
     * @param userId
     * @return
     */
    List<SysRole> queryRoleListWithSelected(Integer userId);

    List<SysRole> listRolesByUserId(Long userId);
}
