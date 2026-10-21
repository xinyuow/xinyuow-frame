/*
 * WebMvcConfiguration.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.config;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import com.alibaba.fastjson2.writer.ObjectWriter;
import com.alibaba.fastjson2.writer.ObjectWriters;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.http.converter.autoconfigure.ServerHttpMessageConvertersCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    /**
     * 配置消息转换器
     * 使用Ali开源的FastJson替换默认的Jackson
     */
    @Bean
    public ServerHttpMessageConvertersCustomizer fastJsonCustomizer() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        FastJsonConfig config = createConfig();

        // Long、long 转字符串
        JSON.register(Long.class, ObjectWriters.ofToString(Object::toString));
        JSON.register(Long.TYPE, ObjectWriters.ofToString(Object::toString));
        // BigInteger 转字符串
        JSON.register(BigInteger.class, ObjectWriters.ofToString(Object::toString));
        // LocalDateTime 格式化
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);
        JSON.register(LocalDateTime.class, (ObjectWriter<LocalDateTime>) (
                jsonWriter, object, fieldName, fieldType, features) -> {
            if (object == null) {
                jsonWriter.writeNull();
                return;
            }
            LocalDateTime dateTime = (LocalDateTime) object;
            jsonWriter.writeString(formatter.format(dateTime));
        });

        converter.setFastJsonConfig(config);
        converter.setSupportedMediaTypes(
                List.of(MediaType.APPLICATION_JSON)
        );
        return builder -> builder.withJsonConverter(converter);
    }

    /**
     * 创建FastJson配置 - 配置FastJson的全局行为
     *
     * @return FastJson配置
     */
    private static @NonNull FastJsonConfig createConfig() {
        FastJsonConfig config = new FastJsonConfig();
        config.setCharset(StandardCharsets.UTF_8);
        config.setDateFormat(DatePattern.NORM_DATETIME_PATTERN);
        config.setWriterFeatures(
                // JSON 格式化
                JSONWriter.Feature.PrettyFormat,
                // 输出 null 字段
                // JSONWriter.Feature.WriteNulls,
                // 将值为 null 的字段按照字段类型，输出为对应的默认值
                JSONWriter.Feature.NullAsDefaultValue
        );
        return config;
    }
}
