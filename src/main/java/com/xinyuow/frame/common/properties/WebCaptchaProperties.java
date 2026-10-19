/*
 * WebCaptchaProperties.java
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
 * Web端验证码配置属性
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Data
@Component
@ConfigurationProperties(prefix = "xinyuow.web.login.captcha")
public class WebCaptchaProperties {

    /**
     * 验证码图片宽度
     */
    private int width = 120;

    /**
     * 验证码图片高度
     */
    private int height = 40;

    /**
     * 验证码字符数量
     */
    private int codeCount = 4;

    /**
     * 干扰线数量
     */
    private int lineCount = 50;

    /**
     * 验证码Redis键
     */
    private String redisKey;

    /**
     * 验证码Redis过期时间
     */
    private Duration redisExpiration = Duration.ofMinutes(5);
}
