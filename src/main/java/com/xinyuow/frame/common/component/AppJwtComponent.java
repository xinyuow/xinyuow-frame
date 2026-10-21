/*
 * AppJwtComponent.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.component;

import com.alibaba.fastjson2.JSON;
import com.xinyuow.frame.common.constant.JwtConstant;
import com.xinyuow.frame.common.enums.ClientEnum;
import com.xinyuow.frame.common.exception.InterfaceException;
import com.xinyuow.frame.common.exception.ResponseCodeEnum;
import com.xinyuow.frame.common.properties.JwtAppProperties;
import com.xinyuow.frame.common.properties.JwtProperties;
import com.xinyuow.frame.common.security.AppJwtSession;
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
import java.util.Set;
import java.util.UUID;

/**
 * APP的JWT组件
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AppJwtComponent {

    private final JwtEncoder jwtEncoder;
    private final JwtProperties jwtProperties;
    private final JwtAppProperties jwtAppProperties;
    private final RedisComponent redisComponent;

    /**
     * 创建JWT会话
     *
     * @param userId 用户ID
     * @param phone  手机号码
     * @return JWT会话
     */
    public AppJwtSession create(Long userId, String phone) {
        // 计算JWT有效时间
        long now = System.currentTimeMillis();
        long expiresAt = now + jwtProperties.getExpiration().toMillis();

        String jti = UUID.randomUUID().toString();
        // 构建JWT声明
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtProperties.getIssuer())
                .subject(String.valueOf(userId))
                .id(jti)
                .claim(JwtConstant.JWT_CLAIM_APP_PHONE, phone)
                .claim(JwtConstant.JWT_CLAIM_CLIENT_TYPE, ClientEnum.APP.name())
                .issuedAt(Instant.ofEpochMilli(now))
                .expiresAt(Instant.ofEpochMilli(expiresAt))
                .build();
        // 生成JWT，使用HS256对JWT进行签名
        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims));

        // 构造JWT会话
        AppJwtSession session = AppJwtSession.builder()
                .userId(userId)
                .phone(phone)
                .jti(jti)
                .token(jwt.getTokenValue())
                .clientType(ClientEnum.APP.name())
                .issuedAt(now)
                .expiresAt(expiresAt)
                .loginTime(now)
                .build();

        // 保存JWT会话到Redis
        this.save(session);
        return session;
    }

    /**
     * 刷新JWT会话
     * 修改Redis中的TTL，并且重新签发一个新的JWT
     *
     * @param oldSession 旧JWT会话
     * @return 新JWT会话
     */
    public AppJwtSession refresh(AppJwtSession oldSession) {
        if (oldSession == null) {
            throw new InterfaceException(ResponseCodeEnum.APP_USER_NOT_LOGIN);
        }
        if (!this.exists(oldSession.getPhone(), oldSession.getJti())) {
            throw new InterfaceException(ResponseCodeEnum.APP_USER_NOT_LOGIN);
        }
        // 计算JWT有效时间
        long now = System.currentTimeMillis();
        long expiresAt = now + jwtProperties.getExpiration().toMillis();

        String newJti = UUID.randomUUID().toString();
        // 构建JWT声明
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtProperties.getIssuer())
                .subject(String.valueOf(oldSession.getUserId()))
                .id(newJti)
                .claim(JwtConstant.JWT_CLAIM_APP_PHONE, oldSession.getPhone())
                .claim(JwtConstant.JWT_CLAIM_CLIENT_TYPE, ClientEnum.APP.name())
                .issuedAt(Instant.ofEpochMilli(now))
                .expiresAt(Instant.ofEpochMilli(expiresAt))
                .build();
        // 生成JWT，使用HS256对JWT进行签名
        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims));

        // 构造JWT会话
        AppJwtSession newSession = AppJwtSession.builder()
                .userId(oldSession.getUserId())
                .phone(oldSession.getPhone())
                .jti(newJti)
                .token(jwt.getTokenValue())
                .clientType(ClientEnum.APP.name())
                .issuedAt(now)
                .expiresAt(expiresAt)
                .loginTime(oldSession.getLoginTime())
                .build();

        // 删除旧Session
        this.remove(oldSession.getPhone(), oldSession.getJti());
        // 保存新Session
        this.save(newSession);
        return newSession;
    }

    /**
     * 更换手机号后更新JWT会话
     * 创建新手机号的会话，并移除旧手机号的所有会话
     *
     * @param userId   用户ID
     * @param oldPhone 旧手机号码
     * @param newPhone 新手机号码
     * @return 新JWT会话
     */
    public AppJwtSession refreshPhone(Long userId, String oldPhone, String newPhone) {
        if (userId == null || oldPhone == null || oldPhone.isBlank() || newPhone == null || newPhone.isBlank()) {
            throw new IllegalArgumentException("用户ID或手机号码不能为空");
        }
        if (oldPhone.equals(newPhone)) {
            throw new IllegalArgumentException("新旧手机号码不能相同");
        }
        // 创建并保存新手机号对应的会话
        AppJwtSession newSession = this.create(userId, newPhone);
        // 删除旧手机号下的所有会话
        this.remove(oldPhone);
        // 返回新会话
        return newSession;
    }

    /**
     * 判断JWT的会话是否存在
     *
     * @param phone 手机号码
     * @param jti   JWT的唯一ID
     * @return 是否存在
     */
    public boolean exists(String phone, String jti) {
        return redisComponent.hasKey(this.buildSessionKey(phone, jti));
    }

    /**
     * 查询JWT会话
     *
     * @param phone 手机号码
     * @param jti   JWT的唯一ID
     * @return JWT会话
     */
    public AppJwtSession get(String phone, String jti) {
        String value = redisComponent.get(this.buildSessionKey(phone, jti));
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return JSON.parseObject(value, AppJwtSession.class);
    }

    /**
     * 根据手机号获取一个有效JWT会话
     * 注意：由于Redis Set本身没有顺序，所以这里返回的是任意一个有效会话
     *
     * @param phone 手机号码
     * @return JWT会话
     */
    public AppJwtSession get(String phone) {
        String indexKey = this.buildIndexKey(phone);
        Set<String> jtiSet = redisComponent.setMembers(indexKey);
        if (jtiSet == null || jtiSet.isEmpty()) {
            return null;
        }

        for (String jti : jtiSet) {
            AppJwtSession session = this.get(phone, jti);
            if (session != null) {
                return session;
            }
            // Redis Session已经过期, 从索引中移除
            redisComponent.setRemove(indexKey, jti);
        }
        return null;
    }

    /**
     * 获取当前登录的JWT会话
     *
     * @return JWT会话
     */
    public AppJwtSession getCurrentSession() {
        // 获取当前请求的认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken)) {
            throw new InterfaceException(ResponseCodeEnum.APP_USER_NOT_LOGIN);
        }

        // 从JWT中获取APP的JWT会话
        return this.get(jwtAuthenticationToken.getToken());
    }

    /**
     * 从JWT中获取APP的JWT会话
     *
     * @param jwt JWT
     * @return JWT会话
     */
    public AppJwtSession get(Jwt jwt){
        // 防止WEB JWT被当作APP JWT使用
        if (!ClientEnum.APP.name().equals(jwt.getClaimAsString(JwtConstant.JWT_CLAIM_CLIENT_TYPE))) {
            log.error("\r\n ********* WEB JWT【{}】被当作APP JWT使用。*********", jwt);
            throw new InterfaceException(ResponseCodeEnum.APP_USER_NOT_LOGIN);
        }

        String phone = jwt.getClaimAsString(JwtConstant.JWT_CLAIM_APP_PHONE);
        String jti = jwt.getId();
        if (phone == null || phone.isBlank() || jti == null || jti.isBlank()) {
            log.error("\r\n ********* APP JWT【{}】中没有用户手机号码或JTI。*********", jwt);
            throw new InterfaceException(ResponseCodeEnum.APP_USER_NOT_LOGIN);
        }

        // 获取JWT会话
        AppJwtSession session = this.get(phone, jti);
        if (session == null) {
            log.error("\r\n ********* APP JWT【{}】的会话不存在。*********", jwt);
            throw new InterfaceException(ResponseCodeEnum.APP_USER_NOT_LOGIN);
        }
        return session;
    }

    /**
     * 删除指定JWT会话
     *
     * @param phone 手机号码
     * @param jti   JWT的唯一ID
     */
    public void remove(String phone, String jti) {
        if (!redisComponent.delete(this.buildSessionKey(phone, jti))) {
            log.error("\r\n ********* 删除指定手机号码:【{}】的会话:【{}】失败。*********", phone, jti);
        }
        if (0 == redisComponent.setRemove(this.buildIndexKey(phone), jti)) {
            log.error("\r\n ********* 删除指定会话:【{}】失败。*********", jti);
        }
    }

    /**
     * 删除指定用户全部JWT会话
     *
     * @param phone 手机号码
     */
    public void remove(String phone) {
        String indexKey = this.buildIndexKey(phone);
        Set<String> jtiSet = redisComponent.setMembers(indexKey);
        if (jtiSet == null || jtiSet.isEmpty()) {
            redisComponent.delete(indexKey);
            return;
        }

        for (String jti : jtiSet) {
            redisComponent.delete(this.buildSessionKey(phone, jti));
        }
        redisComponent.delete(indexKey);
    }

    /**
     * 删除全部APP用户的JWT会话
     */
    public void removeAll() {
        this.deleteByPattern(jwtAppProperties.getSessionAllKey());
        this.deleteByPattern(jwtAppProperties.getIndexAllKey());
    }

    /**
     * 保存JWT会话到Redis中 - 私有方法
     *
     * @param session JWT会话
     */
    private void save(AppJwtSession session) {
        redisComponent.set(this.buildSessionKey(session.getPhone(), session.getJti()),
                JSON.toJSONString(session), jwtAppProperties.getExpiration());
        if (0 == redisComponent.setAdd(this.buildIndexKey(session.getPhone()), session.getJti())) {
            log.error("\r\n ********* 保存JWT会话到Redis失败，会话信息：{}", JSON.toJSONString(session));
        }
    }

    /**
     * 根据Pattern扫描删除Redis Key - 私有方法
     * 使用SCAN，不使用KEYS，避免生产环境阻塞Redis
     *
     * @param pattern Redis Key Pattern
     */
    private void deleteByPattern(String pattern) {
        Set<String> keys = redisComponent.scan(pattern, 500);
        for (String key : keys) {
            if (!redisComponent.unlink(key)) {
                log.error("\r\n ********* 删除Redis Key失败，Key:{}", key);
            }
        }
    }

    /**
     * 构造JWT会话Key - 私有方法
     *
     * @param phone 手机号码
     * @param jti   JWT唯一ID
     * @return Redis Key
     */
    private String buildSessionKey(String phone, String jti) {
        return String.format(jwtAppProperties.getSessionKey(), phone, jti);
    }

    /**
     * 构造JWT索引Key - 私有方法
     *
     * @param phone 手机号码
     * @return Redis Key
     */
    private String buildIndexKey(String phone) {
        return String.format(jwtAppProperties.getIndexKey(), phone);
    }
}
