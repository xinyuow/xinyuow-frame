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
import com.xinyuow.frame.common.properties.JwtWebProperties;
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
    private final JwtWebProperties jwtWebProperties;
    private final RedisComponent redisComponent;

    /**
     * 创建JWT会话
     *
     * @param userId         用户ID
     * @param permissionList 用户拥有的接口权限列表
     * @return JWT会话
     */
    public WebJwtSession create(Long userId, List<String> permissionList) {
        // 计算JWT有效时间
        long now = System.currentTimeMillis();
        long expiresAt = now + jwtProperties.getExpiration().toMillis();

        // 生成JWT的唯一ID
        String jti = UUID.randomUUID().toString();
        // 构建JWT声明，用于返回给客户端进行后续认证
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

        // 构造JWT用户会话
        WebJwtSession session = WebJwtSession.builder()
                .userId(userId)
                .jti(jti)
                .token(jwt.getTokenValue())
                .clientType(ClientEnum.WEB.name())
                .issuedAt(now)
                .expiresAt(expiresAt)
                .loginTime(now)
                .permissionList(permissionList)
                .build();

        // 保存JWT用户会话到Redis
        this.save(session);
        return session;
    }

    /**
     * 判断JWT用户会话是否存在
     *
     * @param userId 用户ID
     * @param jti    JWT的唯一ID
     * @return 是否存在
     */
    public boolean exists(Long userId, String jti) {
        return redisComponent.hasKey(this.buildSessionKey(userId, jti));
    }

    /**
     * 查询JWT用户会话
     *
     * @param userId 用户ID
     * @param jti    JWT的唯一ID
     * @return JWT用户会话
     */
    public WebJwtSession get(Long userId, String jti) {
        String session = redisComponent.get(this.buildSessionKey(userId, jti));
        if (!StringUtils.hasText(session)) {
            return null;
        }
        return JSON.parseObject(session, WebJwtSession.class);
    }

    /**
     * 获取当前登录用户的JWT用户会话
     *
     * @return JWT用户会话
     */
    public WebJwtSession getCurrentSession() {
        // 获取当前请求的认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken)) {
            throw new InterfaceException(ResponseCodeEnum.NOT_LOGIN_ERROR);
        }
        // 从JWT中获取Web的JWT用户会话
        return this.get(jwtAuthenticationToken.getToken());
    }

    /**
     * 根据JWT获取JWT用户会话
     *
     * @param jwt JWT对象
     * @return JWT用户会话
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

        // 获取JWT用户会话
        WebJwtSession session = this.get(userId, jti);
        if (session == null) {
            log.error("\r\n ********* WEB JWT【{}】的会话不存在。*********", jwt);
            throw new InterfaceException(ResponseCodeEnum.NOT_LOGIN_ERROR);
        }
        return session;
    }

    /**
     * 删除指定JWT用户会话 - 用户退出登录时调用
     *
     * @param userId 用户ID
     * @param jti    JWT的唯一ID
     */
    public void remove(Long userId, String jti) {
        // 删除指定JWT用户会话
        redisComponent.delete(this.buildSessionKey(userId, jti));
        // 在用户会话索引中删除指定JTI
        redisComponent.setRemove(this.buildIndexKey(userId), jti);
    }

    /**
     * 删除指定用户全部JWT会话 - 踢用户下线时调用
     *
     * @param userId 用户ID
     */
    public void remove(Long userId) {
        // 获取指定用户的所有JTI
        String indexKey = this.buildIndexKey(userId);
        Set<String> jtiSet = redisComponent.setMembers(indexKey);
        if (jtiSet != null) {
            for (String jti : jtiSet) {
                // 删除指定JWT用户会话
                redisComponent.delete(this.buildSessionKey(userId, jti));
            }
        }
        // 删除用户会话索引
        redisComponent.delete(indexKey);
    }

    /**
     * 更新用户的接口权限
     * 仅在角色权限变更，不需要用户重新登录时调用。
     * 但是不建议如此定义业务，因为角色变更后，前端展示的页面也需要更新。仅更新接口权限不满足业务变动需求。
     *
     * @param userId      用户ID
     * @param permissions 接口权限集合
     */
    public void updatePermissions(Long userId, List<String> permissions) {
        // 获取指定用户的所有JTI
        String indexKey = this.buildIndexKey(userId);
        Set<String> jtiSet = redisComponent.setMembers(indexKey);
        if (jtiSet != null) {
            for (String jti : jtiSet) {
                WebJwtSession session = this.get(userId, jti);
                if (null != session) {
                    // 更新用户会话的接口权限
                    session.setPermissionList(permissions);
                    redisComponent.set(this.buildSessionKey(session.getUserId(), jti),
                            JSON.toJSONString(session), jwtWebProperties.getExpiration());
                }
            }
        }
    }

    /**
     * 保存JWT用户会话到Redis中 - 私有方法
     * 一个用户可多端登录，每次登录都有一个唯一的JTI
     * 一个JTI对应一个用户会话，一个用户可有多个JTI
     *
     * @param session JWT用户会话
     */
    private void save(WebJwtSession session) {
        // 保存本次登录的JWT会话到Redis中
        redisComponent.set(this.buildSessionKey(session.getUserId(), session.getJti()),
                JSON.toJSONString(session), jwtWebProperties.getExpiration());

        // 将登录后的JWT会话添加到用户会话索引中。每一个用户可以多端登录，所以需要添加到索引中，方便后续删除
        redisComponent.setAdd(this.buildIndexKey(session.getUserId()), session.getJti());
    }

    /**
     * 构建JWT用户会话键 - 私有方法
     * Key-Value类型
     *
     * @param userId 用户ID
     * @param jti    JWT的唯一ID
     * @return Redis Key
     */
    private String buildSessionKey(Long userId, String jti) {
        return String.format(jwtWebProperties.getSessionKey(), userId, jti);
    }

    /**
     * 构建JWT用户会话索引键 - 私有方法
     * Set类型
     *
     * @param userId 用户ID
     * @return Redis Key
     */
    private String buildIndexKey(Long userId) {
        return String.format(jwtWebProperties.getIndexKey(), userId);
    }
}
