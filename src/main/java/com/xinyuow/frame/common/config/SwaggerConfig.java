/*
 * SwaggerConfig.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.config;

import com.xinyuow.frame.common.properties.SwaggerProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

/**
 * Swagger配置
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Configuration
@Profile("dev")
@OpenAPIDefinition(
        info = @Info(
                title = "xinyuow平台",
                description = "xinyuow-frame",
                version = "1.0版本"
        ),
        security = {
                @SecurityRequirement(name = "Authorization")
        }
)
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {

    /**
     * Swagger专用账号密码配置
     */
    @Bean
    public UserDetailsManager userDetailsManager(SwaggerProperties properties, PasswordEncoder passwordEncoder) {
        UserDetails swaggerUser = User.withUsername(properties.getUsername())
                .password(passwordEncoder.encode(properties.getPassword()))
                .roles(properties.getRole()).build();
        return new InMemoryUserDetailsManager(swaggerUser);
    }

    /**
     * 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}