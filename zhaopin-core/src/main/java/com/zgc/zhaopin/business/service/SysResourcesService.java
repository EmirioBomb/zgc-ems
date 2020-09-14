package com.zgc.zhaopin.business.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zgc.zhaopin.business.entity.Resources;
import com.zgc.zhaopin.business.vo.ResourceConditionVO;
import com.zgc.zhaopin.framework.object.AbstractService;
import com.zgc.zhaopin.persistence.beans.SysResources;

import java.util.List;
import java.util.Map;

/**
 * 系统资源
 *
 * @author emirio
 * @version 1.0
 * @date 2019/4/16 16:26
 * @since 1.0
 */
public interface SysResourcesService extends AbstractService<Resources, Long> {

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    PageInfo<Resources> findPageBreakByCondition(ResourceConditionVO vo);

    /**
     * 获取用户的资源列表
     *
     * @param map
     * @return
     */
    List<Resources> listUserResources(Map<String, Object> map);

    /**
     * 获取ztree使用的资源列表
     *
     * @param rid
     * @return
     */
    List<Map<String, Object>> queryResourcesListWithSelected(Long rid);

    /**
     * 获取资源的url和permission
     *
     * @return
     */
    List<Resources> listUrlAndPermission();

    /**
     * 获取所有可用的菜单资源
     *
     * @return
     */
    List<Resources> listAllAvailableMenu();

    /**
     * 获取父级资源下所有menu资源
     *
     * @return
     */
    List<Map<String, Object>> listChildMenuByPid(Long pid);

    /**
     * 获取用户关联的所有资源
     *
     * @param userId
     * @return
     */
    List<Resources> listByUserId(Long userId);

    public JSONObject insertListWithChecked(List<SysResources> sysDepts, SysResourcesService deptService);

    Long checkCurrentMaxID();
}
