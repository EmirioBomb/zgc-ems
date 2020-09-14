package com.zgc.zhaopin.framework.excel;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Get ApplicationContext from Spring in a static way
 * 
 * @author emirio
 * @date 2020/5/17
 * @version v1.1
 * @since v1.0
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    // static application context
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * Get Application Context
     * @return application context
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Get bean by name from applicaiton context
     * @param name the name of the bean to retrieve
     * @return an instance of bean
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**
     * Get Bean by class from application context
     * @param <T> required type
     * @param clazz class of the bean
     * @return an instance of single bean matching the required type
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * Get specified Bean by name and class
     * @param <T> required type
     * @param name the name of the bean to retrieve
     * @param clazz class of the bean
     * @return bean
     */
    public static <T> T getBean(String name, Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * Get Environment property by key
     * @param key the property name to resolve
     * @return propery value
     */
    public static String getEnvironmentProperty(String key){
        return getApplicationContext().getEnvironment().getProperty(key);
    }
}
