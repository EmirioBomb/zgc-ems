package com.zgc.zhaopin.framework.excel;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Http Servlet Utils
 * 
 * @author emirio
 * @date 2020/5/18
 * @version v1.1
 * @since v1.0
 */
public class HttpServletUtils {

   /**
    * Get servlet request attributes
    * @return servlet request attributes
    */
    public static ServletRequestAttributes getServletRequest(){
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * Get http servlet request
     * @return http servlet request
     */
    public static HttpServletRequest getRequest(){
        return getServletRequest().getRequest();
    }

    /**
     * Get http servlet response
     * @return http servlet response
     */
    public static HttpServletResponse getResponse(){
        return getServletRequest().getResponse();
    }

    /**
     * Get parameter
     * @param param parameter
     * @return request parameter
     */
    public static String getParameter(String param){
        return getRequest().getParameter(param);
    }

    /**
     * Get parameter with default value
     * @param param
     * @param defaultValue
     * @return value of request parameter
     */
    public static String getParameter(String param, String defaultValue){
        String parameter = getRequest().getParameter(param);
        return StringUtils.isEmpty(parameter) ? defaultValue : parameter;
    }

    /**
     * Get parameter in Integer type
     * @param param parameter
     * @return Integer object
     */
    public static Integer getParameterInt(String param){
        return Integer.valueOf(getRequest().getParameter(param));
    }

    /**
     * Get parameter's default value in Integer type
     * @param param parameter
     * @param defaultValue parameter default value
     * @return Integer object
     */
    public static Integer getParameterInt(String param, Integer defaultValue){
        return Integer.valueOf(getParameter(param, String.valueOf(defaultValue)));
    }
}
