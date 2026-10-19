/*
 * WebJwtAuthenticationConverter.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.common.security;

import com.xinyuow.frame.common.component.WebJwtComponent;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * WEB的JWT认证转换器
 * Spring Security完成JWT签名、exp等基础校验之后，再设置用户权限
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Component
@RequiredArgsConstructor
public class WebJwtAuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {
    private final WebJwtComponent webJwtComponent;

    @Override
    public JwtAuthenticationToken convert(@NonNull Jwt jwt) {
        // 从JWT中获取Web的JWT会话
        WebJwtSession session = webJwtComponent.get(jwt);

        // 从Redis获取当前用户的接口权限
        List<String> permissions = webJwtComponent.getPermissions(session.getUserId());

        // 将权限字符串转换为Spring Security权限对象
        List<GrantedAuthority> authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .map(GrantedAuthority.class::cast)
                .toList();
        // 构建认证对象，并设置权限
        return new JwtAuthenticationToken(jwt, authorities, String.valueOf(session.getUserId()));
    }
}
