package com.zgc.zhaopin.framework.config;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

/**
 *
 * @author emirio
 * @version 1.0
 * @date 2019/4/16 16:26
 * @since 1.0
 */
@Component
@MapperScan("com.zgc.zhaopin.persistence.mapper")
public class MybatisConfig {
}
