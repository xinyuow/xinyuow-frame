/*
 * JwtWebProperties.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * WEB中JWT的Redis配置
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Data
@Component
@ConfigurationProperties(prefix = "xinyuow.jwt.redis.web")
public class JwtWebProperties {

    /**
     * JWT会话键(key-value类型)
     * 第一个参数：用户ID
     * 第二个参数：jti
     */
    private String sessionKey;

    /**
     * JWT会话索引键(set类型)
     * 参数：用户ID
     */
    private String indexKey;

    /**
     * JWT过期时间
     */
    private Duration expiration = Duration.ofHours(2);
}
