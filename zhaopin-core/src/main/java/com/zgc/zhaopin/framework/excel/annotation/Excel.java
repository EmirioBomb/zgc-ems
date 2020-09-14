package com.zgc.zhaopin.framework.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zgc.zhaopin.framework.excel.enumtype.ExcelType;

/**
 * Custom Annotation @Excel
 * <p> get declared fields with @Excel annotation
 * 
 * @author emirio
 * @date 2020/5/20
 * @version v1.1
 * @since v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface Excel {
    // field default value
    public String value();

    // Excel default Type
    public ExcelType type() default ExcelType.ALL;
}