package com.zgc.zhaopin.persistence.mapper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import java.time.YearMonth;

import com.zgc.zhaopin.business.vo.EmployeeConditionVO;
import com.zgc.zhaopin.persistence.beans.SysEmployee;
import com.zgc.zhaopin.plugin.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface SysEmployeeMapper extends BaseMapper<SysEmployee> {

    List<SysEmployee> findPageBreakbyCondition(EmployeeConditionVO vo);

    List<HashMap<String, Long>> listSexByGroup();

    List<HashMap<String, Long>> listSexByAge();

    List<HashMap<String, Long>> listSexByRange();

    List<HashMap<String, Long>> listByEmployee();

    List<HashMap<String, Long>> listByDept();

    List<HashMap<String, Long>> listByArea();

    List<HashMap<String, Long>> listBySalary();

    List<LinkedHashMap<String, Object>> listByAnnuallySalary();

    Long checkCurrentMaxID();
}