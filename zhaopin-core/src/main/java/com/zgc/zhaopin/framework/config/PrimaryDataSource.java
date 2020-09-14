package com.zgc.zhaopin.framework.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.zgc.zhaopin.resourcew")
public class PrimaryDataSource {
    
}