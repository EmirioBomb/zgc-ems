package com.zgc.zhaopin.framework.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.zgc.zhaopin.framework.property.DruidProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Druid Monitor 配置
 *
 * @author emirio
 * @version 1.0
 * @date 2019/9/16 16:26
 * @since 1.0
 */
@Configuration
public class DruidConfig {

    @Autowired
    private DruidProperties druidProperties;

    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), druidProperties.getServletPath());

        // IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        List<String> denyIps = druidProperties.getDenyIps();
        if(!CollectionUtils.isEmpty(denyIps)){
            bean.addInitParameter("deny", StringUtils.collectionToDelimitedString(denyIps, ","));
        }

        // IP白名单
        List<String> allowIps = druidProperties.getAllowIps();
        if(!CollectionUtils.isEmpty(allowIps)){
            bean.addInitParameter("allow", StringUtils.collectionToDelimitedString(allowIps, ","));
        }

        // 登录查看信息的账号密码.
        bean.addInitParameter("loginUsername", druidProperties.getUsername());
        bean.addInitParameter("loginPassword", druidProperties.getPassword());
        // 禁用HTML页面上的"Reset All"功能（默认false）
        bean.addInitParameter("resetEnable", String.valueOf(druidProperties.getResetEnable()));
        return bean;
    }

    /**
     * Druid的StatFilter
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());
        // 添加过滤规则
        bean.addUrlPatterns("/*");
        // 排除的url
        bean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return bean;
    }
}
