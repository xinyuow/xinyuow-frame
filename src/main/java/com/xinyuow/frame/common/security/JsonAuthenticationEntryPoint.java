/*
 * JsonAuthenticationEntryPoint.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-21
 */
package com.xinyuow.frame.common.security;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.xinyuow.frame.common.exception.ResponseCodeEnum;
import com.xinyuow.frame.common.model.ResponseInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * JSON认证入口点
 * 主要处理：未认证时，返回符合业务规范的错误信息
 *
 * @author xinyuow
 * @date 2026-10-21
 */
@Slf4j
@Component
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(@NonNull HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        log.error("\r\n ********* 未认证异常，异常信息：{}", authException.getMessage());
        response.getWriter().write(JSON.toJSONString(ResponseInfo.<String>builder()
                .code(ResponseCodeEnum.NOT_LOGIN_ERROR.getCode())
                .msg(ResponseCodeEnum.NOT_LOGIN_ERROR.getMsg())
                .timestamp(DateUtil.current()).body("").build()));
    }
}
