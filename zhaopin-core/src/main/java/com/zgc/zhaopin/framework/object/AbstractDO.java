package com.zgc.zhaopin.framework.object;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.zgc.zhaopin.framework.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * @author emirio
 * @version 1.0
 * @date 2019/4/16 16:26
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractDO implements Serializable {
    /**
     * @fieldName: serialVersionUID
     * @fieldType: long
     */
    private static final long serialVersionUID = 5088697673359856350L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Excel("ID")
    private Long id;

    

    private Date createTime;
    private Date updateTime;

}