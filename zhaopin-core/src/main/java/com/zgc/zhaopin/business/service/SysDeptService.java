package com.zgc.zhaopin.business.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zgc.zhaopin.business.entity.Dept;
import com.zgc.zhaopin.business.vo.DeptConditionVO;
import com.zgc.zhaopin.framework.object.AbstractService;
import com.zgc.zhaopin.persistence.beans.SysDept;

/**
 * @author emirio
 * @version 1.0
 * @date 2020/4/23
 * @since 1.0
 */
public interface SysDeptService extends AbstractService<Dept, Long> {
    
    /**
     * 分页查询部门信息
     * @param vo
     * @return PageInfo<Dept>
     */
    PageInfo<Dept> findPageBreakbyCondition(DeptConditionVO vo);

    /**
     * 通过部门名称查询
     * @param deptName
     * @return Dept object
     */
    Dept getByDeptName(String deptName);

    /**
     * 获取JSON格式的部门对象集合
     * @return
     */
    public String getDeptTree();

    public JSONObject insertListWithChecked(List<SysDept> sysDept, SysDeptService deptService);
    
    public Long insertDept(Dept dept);

    public Long checkCurrentMaxID();
}