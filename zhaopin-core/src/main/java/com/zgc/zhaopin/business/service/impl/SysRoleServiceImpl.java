package com.zgc.zhaopin.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zgc.zhaopin.business.entity.Role;
import com.zgc.zhaopin.business.service.SysRoleService;
import com.zgc.zhaopin.business.vo.RoleConditionVO;
import com.zgc.zhaopin.persistence.beans.SysRole;
import com.zgc.zhaopin.persistence.mapper.SysRoleMapper;

/**
 * 角色
 *
 * @author emirio
 * @version 1.0
 * @date 2019/4/16 16:26
 * @since 1.0
 */
@Service
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * 获取ztree使用的角色列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> queryRoleListWithSelected(Integer userId) {
        List<SysRole> sysRole = roleMapper.queryRoleListWithSelected(userId);
        if (CollectionUtils.isEmpty(sysRole)) {
            return null;
        }
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for (SysRole role : sysRole) {
            map = new HashMap<String, Object>(3);
            map.put("id", role.getId());
            map.put("pId", 0);
            map.put("checked", role.getSelected() != null && role.getSelected() == 1);
            map.put("name", role.getDescription());
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    @Override
    public PageInfo<Role> findPageBreakByCondition(RoleConditionVO vo) {
        PageHelper.startPage(vo.getPageNumber(), vo.getPageSize());
        List<SysRole> sysRoles = roleMapper.findPageBreakByCondition(vo);
        if (CollectionUtils.isEmpty(sysRoles)) {
            return null;
        }
        List<Role> roles = new ArrayList<>();
        for (SysRole r : sysRoles) {
            roles.add(new Role(r));
        }
        PageInfo bean = new PageInfo<SysRole>(sysRoles);
        bean.setList(roles);
        return bean;
    }

    /**
     * 获取用户的角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> listRolesByUserId(Long userId) {
        List<SysRole> sysRoles = roleMapper.listRolesByUserId(userId);
        if (CollectionUtils.isEmpty(sysRoles)) {
            return null;
        }
        List<Role> roles = new ArrayList<>();
        for (SysRole r : sysRoles) {
            roles.add(new Role(r));
        }
        return roles;
    }

    public JSONObject insertListWithChecked(List<SysRole> sysRoles, SysRoleService roleService) {
        JSONObject json = new JSONObject();

        if (sysRoles == null) {
            json.put("match", "fail");
            return json;
        } 
        List<Role> roleList = new ArrayList<>();
        int updateCounter = 0;
        int insertCounter = 0;
        int failedCounter = 0;
        
        Assert.notNull(sysRoles, "sysRoles不能为空");
        for(SysRole sysRole : sysRoles) {
            if(sysRole.getId() == null) {
                continue;
            }            
            roleList.add(new Role(sysRole));
        }

        for(Role role : roleList) {
            if(getByPrimaryKey(role.getId()) == null) {
                if(getByName(role) == true) {
                    roleService.insert(role);
                    insertCounter++;
                } else {
                    log.info("角色已存在，本次添加失败！");
                    failedCounter++;
                }
            } else {
                roleService.update(role);
                updateCounter++;
            }
        }

        json.put("insert", insertCounter);
        json.put("update", updateCounter);
        json.put("fail", failedCounter);

        return json;
    }

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity
     * @return
     */
    @Override
    public Role insert(Role entity) {
        Assert.notNull(entity, "Role不可为空！");
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        roleMapper.insert(entity.getSysRole());
        return entity;
    }

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含id属性并且必须为自增列
     *
     * @param entities
     */
    @Override
    public void insertList(List<Role> entities) {
        Assert.notNull(entities, "entities不可为空！");
        List<SysRole> sysRole = new ArrayList<>();
        for (Role role : entities) {
            role.setUpdateTime(new Date());
            role.setCreateTime(new Date());
            sysRole.add(role.getSysRole());
        }
        roleMapper.insertList(sysRole);
    }

    /**
     * check if role name exist
     * @param role
     * @return
     */
    public boolean getByName(Role role) {
        for (Role roleTmp: listAll()) {
            if(role.getName() == roleTmp.getName()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param primaryKey
     * @return
     */
    @Override
    public boolean removeByPrimaryKey(Long primaryKey) {
        return roleMapper.deleteByPrimaryKey(primaryKey) > 0;
    }

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param entity
     * @return
     */
    @Override
    public boolean update(Role entity) {
        Assert.notNull(entity, "Role不可为空！");
        entity.setUpdateTime(new Date());
        return roleMapper.updateByPrimaryKey(entity.getSysRole()) > 0;
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity
     * @return
     */
    @Override
    public boolean updateSelective(Role entity) {
        Assert.notNull(entity, "Role不可为空！");
        entity.setUpdateTime(new Date());
        return roleMapper.updateByPrimaryKeySelective(entity.getSysRole()) > 0;
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param primaryKey
     * @return
     */
    @Override
    public Role getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        SysRole sysRole = roleMapper.selectByPrimaryKey(primaryKey);
        return null == sysRole ? null : new Role(sysRole);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果时抛出异常，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    public Role getOneByEntity(Role entity) {
        Assert.notNull(entity, "User不可为空！");
        SysRole sysRole = roleMapper.selectOne(entity.getSysRole());
        return null == sysRole ? null : new Role(sysRole);
    }

    /**
     * 查询全部结果，listByEntity(null)方法能达到同样的效果
     *
     * @return
     */
    @Override
    public List<Role> listAll() {
        List<SysRole> sysRole = roleMapper.selectAll();
        return getRole(sysRole);
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    public List<Role> listByEntity(Role entity) {
        Assert.notNull(entity, "Role不可为空！");
        List<SysRole> sysRole = roleMapper.select(entity.getSysRole());
        return getRole(sysRole);
    }

    private List<Role> getRole(List<SysRole> sysRole) {
        if (CollectionUtils.isEmpty(sysRole)) {
            return null;
        }
        List<Role> roleList = new ArrayList<>();
        for (SysRole r : sysRole) {
            roleList.add(new Role(r));
        }
        return roleList;
    }
}
