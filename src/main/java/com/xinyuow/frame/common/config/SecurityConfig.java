/*
 * SecurityConfig.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.config;

import com.xinyuow.frame.common.properties.SwaggerProperties;
import com.xinyuow.frame.common.security.AppJwtAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AppJwtAuthenticationConverter appJwtAuthenticationConverter;
    private final SwaggerProperties properties;

    /**
     * Spring Security 过滤器链配置
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests(auth -> {
                    // 判断是否开启Swagger
                    if (properties.isEnable()) {
                        auth.requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).hasRole(properties.getRole());
                    } else {
                        auth.requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).denyAll();
                    }

                    // 放行指定接口
                    auth.requestMatchers(
                            "/api/health",
                            "/api/web/v1/captcha",
                            "/api/web/v1/login"
                    ).permitAll();

                    // 其他接口必须认证
                    auth.anyRequest().authenticated();
                })
                // 关闭Spring Security默认登录页面
                .formLogin(AbstractHttpConfigurer::disable)
                // 关闭CSRF防护
                .csrf(AbstractHttpConfigurer::disable)
                // 不使用HttpSession保存登录状态
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        if (properties.isEnable()) {
            // Swagger开启HTTP Basic认证
            http.httpBasic(Customizer.withDefaults());
        }

        // 收到Authorization: Bearer xxx后，用JWT方式认证，并且交给自定义的AppJwtAuthenticationConverter进行后续转换
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(
                jwt -> jwt.jwtAuthenticationConverter(appJwtAuthenticationConverter))
        );
        return http.build();
    }
}
