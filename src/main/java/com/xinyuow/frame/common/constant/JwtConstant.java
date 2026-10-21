/*
 * JwtConstant.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.common.constant;

/**
 * JWT常量类
 * 主要定义：JWT声明中的字段，用于存储客户端类型、用户ID、手机号等信息
 *
 * @author xinyuow
 * @date 2026-10-19
 */
public final class JwtConstant {

    // JWT声明中的客户端类型字段
    public static final String JWT_CLAIM_CLIENT_TYPE = "clientType";

    // JWT声明中的用户ID字段 - WEB端
    public static final String JWT_CLAIM_WEB_USERID = "userId";

    // JWT声明中的手机号字段 - APP端
    public static final String JWT_CLAIM_APP_PHONE = "phone";

    // JWT令牌类型
    public static final String JWT_TOKEN_TYPE = "Bearer";
}
