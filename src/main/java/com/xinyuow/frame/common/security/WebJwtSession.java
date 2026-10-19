/*
 * WebJwtSession.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.common.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Web的JWT会话
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebJwtSession implements Serializable {
    @Serial
    private static final long serialVersionUID = -4313101231689195110L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * JWT唯一ID
     */
    private String jti;

    /**
     * JWT
     */
    private String token;

    /**
     * 客户端类型
     */
    private String clientType;

    /**
     * JWT签发时间
     */
    private Long issuedAt;

    /**
     * JWT过期时间
     */
    private Long expiresAt;

    /**
     * 登录时间
     */
    private Long loginTime;
}
