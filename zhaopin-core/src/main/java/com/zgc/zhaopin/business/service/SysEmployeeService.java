package com.zgc.zhaopin.business.service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zgc.zhaopin.business.entity.Employee;
import com.zgc.zhaopin.business.vo.EmployeeConditionVO;
import com.zgc.zhaopin.framework.object.AbstractService;

public interface SysEmployeeService extends AbstractService<Employee, Long>{

    /**
     * find page break by condition
     * @param vo
     * @return
     */
    PageInfo<Employee> findPageBreakbyCondition(EmployeeConditionVO vo);

    /**
     * list sex by group
     * @return Map<String, Integer>
     */
    List<HashMap<String, Long>> listSexByGroup();

    List<HashMap<String, Long>> listSexByAge();

    List<HashMap<String, Long>> listSexByRange();

    List<HashMap<String, Long>> listByEmployee();

    List<HashMap<String, Long>> listByDept();

    List<HashMap<String, Long>> listByArea();

    List<HashMap<String, Long>> listBySalary();

    List<LinkedHashMap<String, Object>> listByAnnuallySalary();

    Map<String, ArrayList<Long>> listByAnnuallySalaryByMonth();

    Long checkCurrentMaxID();

}