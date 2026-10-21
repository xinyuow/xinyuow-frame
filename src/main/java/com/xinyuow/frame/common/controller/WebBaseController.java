/*
 * WebBaseController.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.controller;

import com.xinyuow.frame.common.component.WebJwtComponent;
import com.xinyuow.frame.common.security.WebJwtSession;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;

/**
 * Web端基础控制器
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Slf4j
public abstract class WebBaseController extends BaseController {
    @Serial
    private static final long serialVersionUID = 624841049563451448L;

    @Resource
    private WebJwtComponent webJwtComponent;

    /**
     * 获取当前登录的JWT会话
     *
     * @return 当前JWT会话
     */
    protected WebJwtSession getCurrentJwtSession() {
        return webJwtComponent.getCurrentSession();
    }

    /**
     * 获取当前登录的用户ID
     *
     * @return 登录用户ID
     */
    protected Long getCurrentUserId() {
        return this.getCurrentJwtSession().getUserId();
    }
}
