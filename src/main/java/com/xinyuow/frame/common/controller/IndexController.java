/*
 * IndexController.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.controller;

import com.xinyuow.frame.common.exception.ResponseCodeEnum;
import com.xinyuow.frame.common.model.ResponseInfo;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serial;

/**
 * 默认路径控制器
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Slf4j
@Hidden
@RestController
public class IndexController extends BaseController implements ErrorController {
    @Serial
    private static final long serialVersionUID = 1005009387199673043L;

    /**
     * 默认路径接口
     *
     * @return 访问默认路径，返回响应信息
     */
    @RequestMapping("/")
    public ResponseInfo<Object> index() {
        return getFailResult(ResponseCodeEnum.REQUEST_ADDRESS_ERROR);
    }

    /**
     * 处理服务的健康检查请求
     *
     * @return 操作结果
     */
    @RequestMapping("/api/health")
    public ResponseInfo<Object> checkHealth() {
        return getSuccessResult();
    }

    /**
     * 处理错误请求，返回Json格式的响应信息
     *
     * @param request 请求对象
     * @return 响应信息
     */
    @RequestMapping("/error")
    public ResponseInfo<Object> error(HttpServletRequest request) {
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (null == statusCode) {
            return getFailResult(ResponseCodeEnum.SERVER_ERROR);
        }
        return getFailResult(ResponseCodeEnum.getHttpResponseCode(Integer.valueOf(statusCode.toString())));
    }
}
