/*
 * JwtAppProperties.java
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
 * APP中JWT的Redis配置
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Data
@Component
@ConfigurationProperties(prefix = "xinyuow.jwt.redis.app")
public class JwtAppProperties {

    /**
     * JWT会话键
     * 第一个参数：phone
     * 第二个参数：jti
     */
    private String sessionKey;

    /**
     * JWT所有会话键
     */
    private String sessionAllKey;

    /**
     * JWT索引键
     * 参数：phone
     */
    private String indexKey;

    /**
     * JWT所有索引键
     */
    private String indexAllKey;

    /**
     * JWT过期时间
     */
    private Duration expiration = Duration.ofHours(2);
}
