/*
 * SecurityConfig.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-21
 */
package com.xinyuow.frame.common.security;

import com.xinyuow.frame.common.properties.SwaggerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 安全认证配置
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AppJwtAuthenticationConverter appJwtAuthenticationConverter;
    private final WebJwtAuthenticationConverter webJwtAuthenticationConverter;
    private final JsonAuthenticationEntryPoint authenticationEntryPoint;
    private final SwaggerProperties properties;

    /**
     * WEB端安全认证配置
     */
    @Bean
    @Order(1)
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) {
        http.securityMatcher("/api/web/**")
                .authorizeHttpRequests(auth -> auth
                        // 放行指定接口
                        .requestMatchers(
                                "/api/web/v1/captcha",
                                "/api/web/v1/login"
                        ).permitAll()
                        // 其他WEB接口必须认证
                        .anyRequest().authenticated()
                )
                // 关闭Spring Security默认登录页面
                .formLogin(AbstractHttpConfigurer::disable)
                // 关闭CSRF防护
                .csrf(AbstractHttpConfigurer::disable)
                // 不使用HttpSession保存登录状态
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 认证失败时，返回JSON格式的错误信息
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                // 收到Authorization: Bearer xxx后，用JWT方式认证，并且交给自定义的WebJwtAuthenticationConverter进行后续转换
                .oauth2ResourceServer(oauth2 -> oauth2
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(webJwtAuthenticationConverter))
                );
        return http.build();
    }

    /**
     * APP端安全认证配置
     */
    @Bean
    @Order(2)
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) {
        http.securityMatcher("/api/app/**")
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                // 关闭Spring Security默认登录页面
                .formLogin(AbstractHttpConfigurer::disable)
                // 关闭CSRF防护
                .csrf(AbstractHttpConfigurer::disable)
                // 不使用HttpSession保存登录状态
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 认证失败时，返回JSON格式的错误信息
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                // 收到Authorization: Bearer xxx后，用JWT方式认证，并且交给自定义的AppJwtAuthenticationConverter进行后续转换
                .oauth2ResourceServer(oauth2 -> oauth2
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(appJwtAuthenticationConverter))
                );
        return http.build();
    }

    /**
     * 其他请求的安全配置
     */
    @Bean
    @Order(3)
    public SecurityFilterChain otherSecurityFilterChain(HttpSecurity http) {
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
                    auth.requestMatchers("/api/health").permitAll();
                    // 未明确配置的其他路由默认拒绝
                    auth.anyRequest().denyAll();
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
        return http.build();
    }
}
