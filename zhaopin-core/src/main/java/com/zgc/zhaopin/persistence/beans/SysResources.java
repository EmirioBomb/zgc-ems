package com.zgc.zhaopin.persistence.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

import com.zgc.zhaopin.framework.excel.annotation.Excel;
import com.zgc.zhaopin.framework.object.AbstractDO;

import java.util.List;

/**
 * @author emirio
 * @version 1.0
 * @date 2019/4/16 16:26
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Excel("资源数据")
public class SysResources extends AbstractDO {
   
    @Excel("资源父级")
    private Long parentId;
    @Excel("资源名称")
    private String name;
    @Excel("资源类型")
    private String type;
    @Excel("资源地址")
    private String url;
    @Excel("资源权限")
    private String permission;
    

    @Excel("排序")
    private Integer sort;
    @Excel("外部资源")
    private Boolean external;
    @Excel("可用")
    private Boolean available;
    @Excel("资源图标")
    private String icon;

    @Transient
    private String checked;
    @Transient
    private SysResources parent;
    @Transient
    private List<SysResources> nodes;
}
