/*
 * AppBaseController.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.controller;

import com.xinyuow.frame.common.component.JwtAppComponent;
import com.xinyuow.frame.common.security.AppJwtSession;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;

/**
 * App端基础控制器
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Slf4j
public abstract class AppBaseController extends BaseController {
    @Serial
    private static final long serialVersionUID = -5244617590297088599L;

    @Resource
    private JwtAppComponent jwtAppComponent;

    /**
     * 获取当前登录的JWT会话
     *
     * @return 当前JWT会话
     */
    protected AppJwtSession getCurrentJwtSession() {
        return jwtAppComponent.getCurrentSession();
    }

    /**
     * 获取当前登录的用户ID
     *
     * @return 登录用户ID
     */
    protected Long getCurrentUserId() {
        return this.getCurrentJwtSession().getUserId();
    }

    /**
     * 获取当前登录的用户手机号码
     *
     * @return 登录用户的手机号码
     */
    protected String getCurrentPhone() {
        return this.getCurrentJwtSession().getPhone();
    }
}
