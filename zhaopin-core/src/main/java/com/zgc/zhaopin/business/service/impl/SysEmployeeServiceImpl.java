package com.zgc.zhaopin.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zgc.zhaopin.business.entity.Employee;
import com.zgc.zhaopin.business.service.SysEmployeeService;
import com.zgc.zhaopin.business.vo.EmployeeConditionVO;
import com.zgc.zhaopin.persistence.beans.SysEmployee;
import com.zgc.zhaopin.persistence.mapper.SysEmployeeMapper;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class SysEmployeeServiceImpl implements SysEmployeeService {

    @Autowired
    private SysEmployeeMapper sysEmployeeMapper;

    @Override
    public PageInfo<Employee> findPageBreakbyCondition(EmployeeConditionVO vo) {
        PageHelper.startPage(vo.getPageNumber(), vo.getPageSize());
        List<SysEmployee> sysEmployee = sysEmployeeMapper.findPageBreakbyCondition(vo);

        if (CollectionUtils.isEmpty(sysEmployee)) {
            return null;
        }

        List<Employee> employeeList = new ArrayList<>();
        for (SysEmployee emp : sysEmployee) {
            employeeList.add(new Employee(emp));
        }

        PageInfo bean = new PageInfo<SysEmployee>(sysEmployee);
        bean.setList(employeeList);

        return bean;
    }

    @Override
    public Employee getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "主键不能为空！");
        SysEmployee sysEmployee = sysEmployeeMapper.selectByPrimaryKey(primaryKey);
        return null == sysEmployee ? null : new Employee(sysEmployee);
    }

    @Override
    public Employee getOneByEntity(Employee entity) {
        Assert.notNull(entity, "主键不能为空！");
        SysEmployee sysEmployee = sysEmployeeMapper.selectOne(entity.getSysEmployee());
        return null == sysEmployee ? null : new Employee(sysEmployee);
    }

    @Override
    public Employee insert(Employee entity) {
        Assert.notNull(entity, "员工对象不能为空！");
        sysEmployeeMapper.insertSelective(entity.getSysEmployee());
        return entity;
    }

    @Override
    public void insertList(List<Employee> entities) {
        Assert.notNull(entities, "员工对象们不能为空！");
        List<SysEmployee> employeeList = new ArrayList<>();
        for (Employee employee : entities) {
            employeeList.add(employee.getSysEmployee());
        }
        sysEmployeeMapper.insertList(employeeList);
    }

    @Override
    public List<Employee> listAll() {
        List<SysEmployee> list = sysEmployeeMapper.selectAll();

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        List<Employee> empList = new ArrayList<>();
        for (SysEmployee employee : list) {
            empList.add(new Employee(employee));
        }
        return empList;
    }

    @Override
    public List<Employee> listByEntity(Employee entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByPrimaryKey(Long primaryKey) {
        return sysEmployeeMapper.deleteByPrimaryKey(primaryKey) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Employee entity) {
        Assert.notNull(entity, "要更新的员工对象不能为空！");
        return sysEmployeeMapper.updateByPrimaryKey(entity.getSysEmployee()) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSelective(Employee entity) {
        Assert.notNull(entity, "选中的员工对象不能空！");
        return sysEmployeeMapper.updateByPrimaryKeySelective(entity.getSysEmployee()) > 0;
    }

    @Override
    public List<HashMap<String, Long>> listSexByGroup() {
        List<HashMap<String, Long>> employeeList = sysEmployeeMapper.listSexByGroup();
        if (CollectionUtils.isEmpty(employeeList)) {
            return null;
        }
        return employeeList;
    }

    @Override
    public List<HashMap<String, Long>> listSexByAge() {
        List<HashMap<String, Long>> employeeList = sysEmployeeMapper.listSexByAge();
        if (CollectionUtils.isEmpty(employeeList)) {
            return null;
        }

        return employeeList;
    }

    @Override
    public List<HashMap<String, Long>> listSexByRange() {
        List<HashMap<String, Long>> employeeList = sysEmployeeMapper.listSexByRange();
        if (CollectionUtils.isEmpty(employeeList)) {
            return null;
        }
        return employeeList;
    }

    @Override
    public List<HashMap<String, Long>> listByEmployee() {
        List<HashMap<String, Long>> employeeList = sysEmployeeMapper.listByEmployee();
        if (CollectionUtils.isEmpty(employeeList)) {
            return null;
        }
        return employeeList;
    }

    @Override
    public List<HashMap<String, Long>> listByDept() {
        List<HashMap<String, Long>> employeeList = sysEmployeeMapper.listByDept();
        if (CollectionUtils.isEmpty(employeeList)) {
            return null;
        }
        return employeeList;
    }

    @Override
    public List<HashMap<String, Long>> listByArea() {
        List<HashMap<String, Long>> employeeList = sysEmployeeMapper.listByArea();
        if (CollectionUtils.isEmpty(employeeList)) {
            return null;
        }
        for (HashMap<String,Long> hashMap : employeeList) {
            if(!hashMap.containsKey("province")) {
                hashMap.put("province", hashMap.get("total"));
            }
        }
        return employeeList;
    }

    @Override
    public List<HashMap<String, Long>> listBySalary() {
        List<HashMap<String, Long>> employeeList = sysEmployeeMapper.listBySalary();
        if (CollectionUtils.isEmpty(employeeList)) {
            return null;
        }
        return employeeList;
    }

    @Override
    public List<LinkedHashMap<String, Object>> listByAnnuallySalary() {
        List<LinkedHashMap<String, Object>> employeeList = sysEmployeeMapper.listByAnnuallySalary();
        if (CollectionUtils.isEmpty(employeeList)) {
            return null;
        }
        return employeeList;
    }

    /**
     * list by annually salary by each year-month
     */
    public Map<String, ArrayList<Long>> listByAnnuallySalaryByMonth() {
        // get all every year-month total salary
        List<LinkedHashMap<String, Object>> tmpList = sysEmployeeMapper.listByAnnuallySalary();

        // map data as [year: value], value=[data1, data2, data3]
        Map<String, ArrayList<Long>> employeeMap = new LinkedHashMap<>();

        // List<Map<String, ArrayList<Long>>> employeeList = new ArrayList<>();

        // iterate all the basic data from tmpList
        for (LinkedHashMap<String, Object> hashMap : tmpList) {
            // initialize counter
            int count = 1;

            // temporary key and value, store key:'year',value:[1,2,3,5];
            String tmpKey = "";
            Long tmpValue = 0l;

            // store every month total salary
            ArrayList<Long> valueList = new ArrayList<>();

            for (String key : hashMap.keySet()) {
                count++;
                if (key.equals("year")) {
                    tmpKey = String.valueOf(String.valueOf(hashMap.get(key)));
                } else {
                    tmpValue = Long.valueOf(String.valueOf(hashMap.get(key)));
                    // store all values without "year" key
                    valueList.add(tmpValue);
                }

                // set map like 2015: [1, 2, 3...]
                if (count % tmpList.get(0).size() == 0) {
                    employeeMap.put(tmpKey, valueList);
                }
            }
        }
        return employeeMap;
    }

    @Override
    public Long checkCurrentMaxID() {
        Long maxId = sysEmployeeMapper.checkCurrentMaxID();
        return maxId;
    }

    
    
}