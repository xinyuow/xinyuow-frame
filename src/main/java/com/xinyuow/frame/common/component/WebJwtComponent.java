/*
 * WebJwtComponent.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.common.component;

import com.alibaba.fastjson2.JSON;
import com.xinyuow.frame.common.constant.JwtConstant;
import com.xinyuow.frame.common.enums.ClientEnum;
import com.xinyuow.frame.common.exception.InterfaceException;
import com.xinyuow.frame.common.exception.ResponseCodeEnum;
import com.xinyuow.frame.common.properties.JwtProperties;
import com.xinyuow.frame.common.properties.WebJwtProperties;
import com.xinyuow.frame.common.security.WebJwtSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * WEB的JWT组件
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebJwtComponent {

    private final JwtEncoder jwtEncoder;
    private final JwtProperties jwtProperties;
    private final WebJwtProperties webJwtProperties;
    private final RedisComponent redisComponent;

    /**
     * 创建JWT会话
     *
     * @param userId 用户ID
     * @return JWT会话
     */
    public WebJwtSession create(Long userId) {
        // 计算JWT有效时间
        long now = System.currentTimeMillis();
        long expiresAt = now + jwtProperties.getExpiration().toMillis();

        String jti = UUID.randomUUID().toString();
        // 构建JWT声明
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtProperties.getIssuer())
                .subject(String.valueOf(userId))
                .id(jti)
                .claim(JwtConstant.JWT_CLAIM_WEB_USERID, userId)
                .claim(JwtConstant.JWT_CLAIM_CLIENT_TYPE, ClientEnum.WEB.name())
                .issuedAt(Instant.ofEpochMilli(now))
                .expiresAt(Instant.ofEpochMilli(expiresAt))
                .build();
        // 生成JWT，使用HS256对JWT进行签名
        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims));

        // 构造JWT会话
        WebJwtSession session = WebJwtSession.builder()
                .userId(userId)
                .jti(jti)
                .token(jwt.getTokenValue())
                .clientType(ClientEnum.WEB.name())
                .issuedAt(now)
                .expiresAt(expiresAt)
                .loginTime(now)
                .build();

        // 保存JWT会话到Redis
        this.save(session);
        return session;
    }

    /**
     * 判断JWT的会话是否存在
     *
     * @param userId 用户ID
     * @param jti    JWT的唯一ID
     * @return 是否存在
     */
    public boolean exists(Long userId, String jti) {
        return redisComponent.hasKey(this.buildSessionKey(userId, jti));
    }

    /**
     * 查询JWT会话
     *
     * @param userId 用户ID
     * @param jti    JWT的唯一ID
     * @return JWT会话
     */
    public WebJwtSession get(Long userId, String jti) {
        String value = redisComponent.get(this.buildSessionKey(userId, jti));
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return JSON.parseObject(value, WebJwtSession.class);
    }

    /**
     * 获取当前登录的JWT会话
     *
     * @return JWT会话
     */
    public WebJwtSession getCurrentSession() {
        // 获取当前请求的认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken)) {
            throw new InterfaceException(ResponseCodeEnum.NOT_LOGIN_ERROR);
        }

        // 从JWT中获取Web的JWT会话
        return this.get(jwtAuthenticationToken.getToken());
    }

    /**
     * 从JWT中获取Web的JWT会话
     *
     * @param jwt JWT
     * @return JWT会话
     */
    public WebJwtSession get(Jwt jwt) {
        // 防止APP JWT被当作WEB JWT使用
        if (!ClientEnum.WEB.name().equals(jwt.getClaimAsString(JwtConstant.JWT_CLAIM_CLIENT_TYPE))) {
            log.error("\r\n ********* APP JWT【{}】被当作WEB JWT使用。*********", jwt);
            throw new InterfaceException(ResponseCodeEnum.NOT_LOGIN_ERROR);
        }

        String jti = jwt.getId();
        if (null == jwt.getSubject() || jwt.getSubject().isBlank() || null == jti || jti.isBlank()) {
            log.error("\r\n ********* WEB JWT【{}】中没有用户ID或JTI。*********", jwt);
            throw new InterfaceException(ResponseCodeEnum.NOT_LOGIN_ERROR);
        }

        long userId;
        try {
            userId = Long.parseLong(jwt.getSubject());
        } catch (NumberFormatException e) {
            log.error("\r\n ********* WEB JWT【{}】中用户ID不是数字。*********", jwt);
            throw new InterfaceException(ResponseCodeEnum.NOT_LOGIN_ERROR);
        }

        // 获取JWT会话
        WebJwtSession session = this.get(userId, jti);
        if (session == null) {
            log.error("\r\n ********* WEB JWT【{}】的会话不存在。*********", jwt);
            throw new InterfaceException(ResponseCodeEnum.NOT_LOGIN_ERROR);
        }
        return session;
    }

    /**
     * 保存用户的接口权限。在登录成功、角色权限变更时调用
     *
     * @param userId      用户ID
     * @param permissions 接口权限集合
     */
    public void savePermissions(Long userId, List<String> permissions) {
        redisComponent.set(this.buildPermissionKey(userId), JSON.toJSONString(permissions));
    }

    /**
     * 获取用户的接口权限
     *
     * @param userId 用户ID
     * @return 接口权限集合
     */
    public List<String> getPermissions(Long userId) {
        String value = redisComponent.get(this.buildPermissionKey(userId));
        if (!StringUtils.hasText(value)) {
            return List.of();
        }
        return JSON.parseArray(value, String.class);
    }

    /**
     * 删除指定JWT会话
     *
     * @param userId 用户ID
     * @param jti    JWT的唯一ID
     */
    public void remove(Long userId, String jti) {
        redisComponent.delete(this.buildSessionKey(userId, jti));
        redisComponent.setRemove(this.buildIndexKey(userId), jti);
    }

    /**
     * 删除指定用户全部JWT会话
     *
     * @param userId 用户ID
     */
    public void remove(Long userId) {
        String indexKey = this.buildIndexKey(userId);
        Set<String> jtiSet = redisComponent.setMembers(indexKey);
        if (jtiSet != null) {
            for (String jti : jtiSet) {
                redisComponent.delete(this.buildSessionKey(userId, jti));
            }
        }
        redisComponent.delete(indexKey);
    }

    /**
     * 删除指定用户接口权限缓存
     *
     * @param userId 用户ID
     */
    public void removePermissions(Long userId) {
        redisComponent.delete(this.buildPermissionKey(userId));
    }

    /**
     * 保存JWT会话到Redis中 - 私有方法
     *
     * @param session JWT会话
     */
    private void save(WebJwtSession session) {
        redisComponent.set(this.buildSessionKey(session.getUserId(), session.getJti()),
                JSON.toJSONString(session), webJwtProperties.getExpiration()
        );

        if (0 == redisComponent.setAdd(this.buildIndexKey(session.getUserId()), session.getJti())) {
            log.error("\r\n ********* 保存WEB JWT会话到Redis失败，会话信息：{}", JSON.toJSONString(session));
        }
    }

    /**
     * 构建JWT会话键 - 私有方法
     *
     * @param userId 用户ID
     * @param jti    JWT的唯一ID
     * @return Redis Key
     */
    private String buildSessionKey(Long userId, String jti) {
        return String.format(webJwtProperties.getSessionKey(), userId, jti);
    }

    /**
     * 构建JWT会话索引键 - 私有方法
     *
     * @param userId 用户ID
     * @return Redis Key
     */
    private String buildIndexKey(Long userId) {
        return String.format(webJwtProperties.getIndexKey(), userId);
    }

    /**
     * 构建接口权限键 - 私有方法
     *
     * @param userId 用户ID
     * @return Redis Key
     */
    private String buildPermissionKey(Long userId) {
        return String.format(webJwtProperties.getPermissionKey(), userId);
    }
}
