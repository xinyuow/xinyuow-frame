/*
 * AppJwtAuthenticationConverter.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.security;

import com.xinyuow.frame.common.component.AppJwtComponent;
import com.xinyuow.frame.common.enums.ClientEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
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
public class AppJwtAuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {
    private final AppJwtComponent appJwtComponent;

    @Override
    public JwtAuthenticationToken convert(Jwt jwt) {
        // 获取客户端类型
        String clientType = jwt.getClaimAsString("clientType");

        // 只允许APP JWT进入APP认证链
        if (!ClientEnum.APP.name().equals(clientType)) {
            throw new InvalidBearerTokenException("JWT客户端类型不正确");
        }

        String phone = jwt.getClaimAsString("phone");
        String jti = jwt.getId();
        String subject = jwt.getSubject();
        if (phone == null || jti == null || subject == null || subject.isBlank()) {
            throw new InvalidBearerTokenException("JWT缺少必要信息");
        }

        // JWT本身合法还不够，还需要检查Redis中是否存在Session。（因为用户可能已经退出登录或者被强制下线）
        if (!appJwtComponent.exists(phone, jti)) {
            throw new InvalidBearerTokenException("登录状态已失效");
        }
        return new JwtAuthenticationToken(jwt, Collections.emptyList(), subject);
    }
}
