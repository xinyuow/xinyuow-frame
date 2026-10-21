/*
 * JwtAppAuthenticationConverter.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.security;

import com.xinyuow.frame.common.component.JwtAppComponent;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * APP的JWT认证转换器
 * Spring Security完成JWT签名、exp等基础校验之后，再检查Redis中的JWT Session是否存在
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Component
@RequiredArgsConstructor
public class JwtAppAuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {
    private final JwtAppComponent jwtAppComponent;

    @Override
    public JwtAuthenticationToken convert(@NonNull Jwt jwt) {
        return new JwtAuthenticationToken(jwt, Collections.emptyList(),
                String.valueOf(jwtAppComponent.get(jwt).getUserId()));
    }
}
