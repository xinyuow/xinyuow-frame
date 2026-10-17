/*
 * SwaggerProperties.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Swagger配置属性
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Data
@Component
@ConfigurationProperties(prefix = "xinyuow.swagger")
public class SwaggerProperties {

    /**
     * 是否启用 Swagger
     */
    private boolean enable = false;

    /**
     * Swagger账号
     */
    private String username;

    /**
     * Swagger密码
     */
    private String password;

    /**
     * Swagger角色
     */
    private String role;
}
