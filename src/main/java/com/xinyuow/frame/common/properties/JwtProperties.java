/*
 * JwtProperties.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * JWT配置属性
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Data
@Component
@ConfigurationProperties(prefix = "xinyuow.jwt")
public class JwtProperties {

    /**
     * JWT签名密钥
     */
    private String secret;

    /**
     * JWT签发者
     */
    private String issuer;

    /**
     * JWT有效时间
     */
    private Duration expiration;
}
