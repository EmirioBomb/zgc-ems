package com.zgc.zhaopin.persistence.beans;

import java.io.Serializable;

import javax.persistence.Id;

import com.zgc.zhaopin.framework.excel.annotation.Excel;
import com.zgc.zhaopin.framework.object.AbstractDO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 创建一个可序列化的SysDept
 * @author emirio
 * @version 1.0
 * @date 2020/4/23
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Excel("部门数据")
public class SysDept implements Serializable {

    /**
     * <p>提醒：如果实体类中没有一个标记 @Id 的字段，当你使用带有 ByPrimaryKey 的方法时，所有的字段会作为联合主键来使用，也就会出现类似 where id = ? and countryname = ? and countrycode = ? 的情况。</p>
     * <a href="https://github.com/abel533/Mapper/wiki/2.1-simple">Mapper注意点</a>
     * 
     */
    @Excel("部门父级ID")
    private Long parentId;
    @Excel("部门名称")
    private String deptName;

    @Excel("部门层级")
    private String levelId;
   
    @Id
    @Excel("部门ID")
    private Long deptId;
}