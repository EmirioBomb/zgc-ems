package com.zgc.zhaopin.framework.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {
    
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                            .apiInfo(apiInfo())
                            .select()
                            // .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                            .apis(RequestHandlerSelectors.basePackage("com.zgc.zhaopin.controller"))
                            .paths(PathSelectors.any())
                            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                    .title("Project Docs")
                    .description("Project API Documents")
                    .termsOfServiceUrl("https://github.com/emiriobomb")
                    .contact(new Contact("EmirioBomb", "https://github.com/EmirioBomb/zgc-ems", ""))
                    .version("1.0")
                    .build();
    }
}