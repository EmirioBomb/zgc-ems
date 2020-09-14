package com.zgc.zhaopin.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

import com.zgc.zhaopin.business.entity.Dept;
import com.zgc.zhaopin.business.service.SysDeptService;
import com.zgc.zhaopin.business.vo.DeptConditionVO;
import com.zgc.zhaopin.persistence.beans.SysDept;
import com.zgc.zhaopin.persistence.mapper.SysDeptMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 部门service
 * 
 * @author emirio
 * @version 1.0
 * @date 2020/4/23
 * @since 1.0
 */
@Service
@Slf4j
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    /**
     * 分页查询部门信息
     */
    @Override
    public PageInfo<Dept> findPageBreakbyCondition(DeptConditionVO vo) {
        // PageHelper.startPage(vo.getPageNumber(), vo.getPageSize(), false);
        PageHelper.startPage(vo.getPageNumber(), vo.getPageSize());
        List<SysDept> sysDept = sysDeptMapper.findPageBreakbyCondition(vo);

        if (CollectionUtils.isEmpty(sysDept)) {
            return null;
        }
        List<Dept> dept = new ArrayList<>();
        for (SysDept sd : sysDept) {
            dept.add(new Dept(sd));
        }
        PageInfo bean = new PageInfo<SysDept>(sysDept);
        bean.setList(dept);
        return bean;
    }

    @Override
    public Dept getByDeptName(String deptName) {
        Dept dept = new Dept(deptName);
        return getOneByEntity(dept);
    }

    /**
     * 添加部门
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Dept insert(Dept dept) {
        Assert.notNull(dept, "Dept不可为空");
        sysDeptMapper.insertSelective(dept.getSysDept());
        // Long test = sysDeptMapper.insertDept(dept.getSysDept());
        // System.out.println(test.longValue());
        return dept;
    }

    public boolean checkDept(Dept dept) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertList(List<Dept> depts) {
        Assert.notNull(depts, "Depts不可为空");
        List<SysDept> sysDepts = new ArrayList<>();
        for (Dept dept : depts) {
            sysDepts.add(dept.getSysDept());
        }
        sysDeptMapper.insertList(sysDepts);
    }

    public JSONObject insertListWithChecked(List<SysDept> sysDepts, SysDeptService deptService) {
        JSONObject json = new JSONObject();

        if (sysDepts == null) {
            json.put("match", "fail");
            return json;
        }
        List<Dept> deptList = new ArrayList<>();
        int updateCounter = 0;
        int insertCounter = 0;
        int failedCounter = 0;

        Assert.notNull(sysDepts, "sysDepts不能为空");
        // 1. convert sysDepts to deptList
        for (SysDept sysDept : sysDepts) {
            if (sysDept.getDeptId() == null) {
                continue;
            }
            deptList.add(new Dept(sysDept));
        }

        // 2. check if dept exist by primarykey and name
        for (Dept dept : deptList) {
            if (getByPrimaryKey(dept.getDeptId()) == null) {
                if (getByDeptName(dept.getDeptName()) == null) {
                    deptService.insert(dept);
                    insertCounter++;
                } else if (getByDeptName(dept.getDeptName()) != null) {
                    log.info("该部门已存在！");
                    failedCounter++;
                }
            } else if (getByPrimaryKey(dept.getDeptId()) != null) {
                deptService.update(dept);
                updateCounter++;
            }
        }

        json.put("insert", insertCounter);
        json.put("update", updateCounter);
        json.put("fail", failedCounter);

        return json;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByPrimaryKey(Long primaryKey) {
        return sysDeptMapper.deleteByPrimaryKey(primaryKey) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Dept dept) {
        Assert.notNull(dept, "Dept不可为空！");
        dept.setParentId(dept.getParentId());
        return sysDeptMapper.updateByPrimaryKey(dept.getSysDept()) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSelective(Dept dept) {
        Assert.notNull(dept, "Dept不可为空！");
        dept.setParentId((dept.getParentId()));
        // if(!StringUtils.isEmpty(dept.getDeptName())) {
        // dept.setParentId(0);
        // }
        return sysDeptMapper.updateByPrimaryKeySelective(dept.getSysDept()) > 0;
    }

    @Override
    public Dept getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(primaryKey);
        return null == sysDept ? null : new Dept(sysDept);
    }

    @Override
    public Dept getOneByEntity(Dept entity) {
        Assert.notNull(entity, "Dept不可为空！");
        SysDept sysDept = sysDeptMapper.selectOne(entity.getSysDept());
        return null == sysDept ? null : new Dept(sysDept);
    }

    /**
     * 获取所有部门对象信息
     */
    @Override
    public List<Dept> listAll() {
        List<SysDept> sysDept = sysDeptMapper.selectAll();

        if (CollectionUtils.isEmpty(sysDept)) {
            return null;
        }

        List<Dept> deptList = new ArrayList<>();
        for (SysDept depts : sysDept) {
            deptList.add(new Dept(depts));
        }
        return deptList;
    }

    /**
     * Get dept tree info and return by JSONArray 1. get parentId from database 2.
     * store all the dept objs to JSONArray 3. JSONArray.toString, retrun DeptTree
     * with json datatype
     * 
     * @return jsonString
     */
    public String getDeptTree() {
        List<SysDept> sysDept = sysDeptMapper.selectAll();

        JSONArray deptArray = new JSONArray();
        for (SysDept dept : sysDept) {
            deptArray.add(dept);
        }
        return deptArray.toJSONString();
    }

    @Override
    public List<Dept> listByEntity(Dept dept) {
        // Assert.notNull(dept, "Dept不可为空！");
        // // List<SysDept> sysDept = sysDeptMapper.select(dept.getSysDept());
        // if(CollectionUtils.isEmpty(sysDept)) {
        // return null;
        // }

        // List<Dept> depts = new ArrayList<>();
        // for(SysDept sd : sysDept) {
        // depts.add(new Dept(sd));
        // }
        // return depts;
        return null;
    }

    @Override
    public Long insertDept(Dept dept) {
        long parentId = dept.getParentId();
        String levelId = "";
        
        if (parentId == 0) {
            // 1. get default ID
            Long deptId = sysDeptMapper.checkCurrentMaxID();
            // 2. set levelID = id + 0
            levelId = deptId + ",0";
            dept.setLevelId(levelId);
        } else {
            // 1. get Object
            SysDept sysDept = sysDeptMapper.selectByPrimaryKey(dept.getParentId());

            // 2. set levelId = object.levelID + parentId
            levelId = sysDept.getLevelId() + "," + parentId;
            dept.setLevelId(levelId);
        }
        Long test = sysDeptMapper.insertDept(dept);
        System.out.println(test.longValue());

        return null;
    }

    @Override
    public Long checkCurrentMaxID() {
        Long maxId = sysDeptMapper.checkCurrentMaxID();
        return maxId;
    }


    

    
}