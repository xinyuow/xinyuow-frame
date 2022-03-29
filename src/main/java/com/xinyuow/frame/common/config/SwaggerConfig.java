package com.xinyuow.frame.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger2配置类
 *
 * @author mxy
 * @date 2020/11/12
 */
@Configuration
@EnableOpenApi
@DependsOn("interfaceConstant")
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .pathMapping("/")
                .enable(Boolean.TRUE)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    /**
     * Swagger页面上展示的基础信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("这是一个项目名称")
                .description("基于Springboot的后端项目框架")
                .version("1.0")
                .contact(new Contact("技术部", "", ""))
                .license("xxx公司")
                .licenseUrl("https://www.xinyuow.com/")
                .build();
    }

}
