/*
 * WebLoginController.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.web.controller;

import com.xinyuow.frame.common.controller.WebBaseController;
import com.xinyuow.frame.common.model.ResponseInfo;
import com.xinyuow.frame.common.security.WebJwtSession;
import com.xinyuow.frame.web.service.LoginService;
import com.xinyuow.frame.web.vo.req.WebLoginReqVO;
import com.xinyuow.frame.web.vo.res.WebCaptchaResVO;
import com.xinyuow.frame.web.vo.res.WebLoginResVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;

/**
 * Web端登录控制器
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/web/v1")
@Tag(name = "Web端登录操作接口")
public class WebLoginController extends WebBaseController {
    @Serial
    private static final long serialVersionUID = 3308896132110195648L;

    private final LoginService loginService;

    /**
     * 获取图片验证码
     *
     * @return 操作结果
     */
    @GetMapping("/captcha")
    @Operation(summary = "获取图片验证码-登录", description = "Web端用户登录时获取图片验证码接口")
    public ResponseInfo<WebCaptchaResVO> getCaptcha() {
        return getResult(loginService.getCaptcha());
    }

    /**
     * Web用户登录
     *
     * @param loginReq Web端用户登录请求对象
     * @return 操作结果
     */
    @PostMapping("/login")
    @Operation(summary = "登录", description = "Web端用户登录接口")
    public ResponseInfo<WebLoginResVO> login(@Validated @RequestBody WebLoginReqVO loginReq) {
        return getResult(loginService.login(loginReq.getLoginName(), loginReq.getPassword(),
                loginReq.getCaptchaKey(), loginReq.getCaptchaCode()));
    }

    /**
     * 退出登录
     *
     * @return 操作结果
     */
    @GetMapping("/logout")
    @Operation(summary = "退出登录", description = "Web端用户退出登录接口")
    public ResponseInfo<Object> logout() {
        WebJwtSession session = super.getCurrentJwtSession();
        loginService.logout(session.getUserId(), session.getJti());
        return getSuccessResult();
    }
}
