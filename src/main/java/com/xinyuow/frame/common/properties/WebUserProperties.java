/*
 * WebUserProperties.java
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
 * Web端用户配置属性
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Data
@Component
@ConfigurationProperties(prefix = "xinyuow.web.user")
public class WebUserProperties {

    /**
     * 初始登录失败次数
     */
    private int initFailCount = 0;

    /**
     * 最大登录失败次数
     */
    private int maxFailCount = 5;

    /**
     * 锁定时间，单位：分钟
     */
    private Duration lockTime = Duration.ofMinutes(10);
}
