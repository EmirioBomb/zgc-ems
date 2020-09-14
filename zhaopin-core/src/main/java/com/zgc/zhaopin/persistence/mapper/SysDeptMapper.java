package com.zgc.zhaopin.persistence.mapper;

import java.util.List;

import com.zgc.zhaopin.business.entity.Dept;
import com.zgc.zhaopin.business.vo.DeptConditionVO;
import com.zgc.zhaopin.persistence.beans.SysDept;
import com.zgc.zhaopin.plugin.BaseMapper;

import org.springframework.stereotype.Repository;


/**
 * @author emirio
 * @version 1.0
 * @date 2020/4/23
 * @since 1.0
 */
@Repository
public interface SysDeptMapper extends BaseMapper<SysDept> {
    
    List<SysDept> findPageBreakbyCondition(DeptConditionVO vo);

    Long insertDept(Dept dept);

    Long checkCurrentMaxID();



}