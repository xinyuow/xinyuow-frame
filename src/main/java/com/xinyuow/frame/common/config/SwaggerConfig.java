package com.xinyuow.frame.common.config;

import com.xinyuow.frame.common.constant.InterfaceConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Swagger2配置类
 *
 * @author mxy
 * @date 2020/11/12
 */
@Configuration
@EnableSwagger2
@DependsOn("interfaceConstant")
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xinyuow.frame.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .apiInfo(new ApiInfoBuilder()
                        .title("这是一个项目名称")
                        .description("基于Springboot的后端项目框架")
                        .version("1.0")
                        .contact(new Contact("技术部", "", ""))
                        .license("xxx公司")
                        .licenseUrl("http://www.xinyuow.com/")
                        .build());
    }

    /**
     * 配置接口认证参数
     */
    private List<ApiKey> securitySchemes() {
        return newArrayList(
                new ApiKey("Authorization", InterfaceConstant.AUTH_PARAM_KEY, "header"));
    }

    /**
     * 配置拦截的路由
     */
    private List<SecurityContext> securityContexts() {
        return newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("/.*"))
                        .build()
        );
    }

    /**
     * 配置默认认证
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("Authorization", authorizationScopes));
    }

}
