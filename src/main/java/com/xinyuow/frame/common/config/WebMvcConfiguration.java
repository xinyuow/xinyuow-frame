/*
 * WebMvcConfiguration.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC配置
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * 跨域设置 - 过滤器方式先于拦截器生效
     * 允许所有的域
     * 允许所有的请求头
     * 允许所有的方法
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(Boolean.TRUE);
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }

    /**
     * 配置自定义静态文件访问资源映射
     * addResourceHandler() 是对外暴露的访问路径
     * addResourceLocations() 是文件放置的路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 放行项目icon
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/");
    }
}
