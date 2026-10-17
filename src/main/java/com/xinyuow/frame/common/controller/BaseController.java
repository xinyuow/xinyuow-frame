/*
 * BaseController.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.controller;

import com.xinyuow.frame.common.exception.ResponseCodeEnum;
import com.xinyuow.frame.common.model.ResponseInfo;

import java.io.Serial;
import java.io.Serializable;

/**
 * 基类控制器
 *
 * @author xinyuow
 * @date 2026-10-17
 */
public abstract class BaseController implements Serializable {
    @Serial
    private static final long serialVersionUID = 633761073272414212L;

    /**
     * 添加数据到结果对象中
     *
     * @param body 封装接口实体内容
     * @return 接口响应对象
     */
    protected <T> ResponseInfo<T> getResult(T body) {
        return ResponseInfo.<T>builder().code(ResponseCodeEnum.REQUEST_SUCCESS.getCode())
                .msg(ResponseCodeEnum.REQUEST_SUCCESS.getMsg()).body(body).build();
    }

    /**
     * 返回成功
     *
     * @return 接口响应对象
     */
    protected <T> ResponseInfo<T> getSuccessResult() {
        return ResponseInfo.<T>builder().code(ResponseCodeEnum.REQUEST_SUCCESS.getCode())
                .msg(ResponseCodeEnum.REQUEST_SUCCESS.getMsg()).build();
    }

    /**
     * 返回失败 - 自定义返回结果（根据枚举类）
     *
     * @param responseCodeEnum 特定状态码枚举
     * @return 接口响应对象
     */
    protected <T> ResponseInfo<T> getFailResult(ResponseCodeEnum responseCodeEnum) {
        return ResponseInfo.<T>builder().code(responseCodeEnum.getCode()).msg(responseCodeEnum.getMsg()).build();
    }

    /**
     * 返回失败 - 自定义返回结果（根据状态码和失败原因）
     *
     * @param code 状态码
     * @param msg  失败原因
     * @return 接口响应对象
     */
    protected <T> ResponseInfo<T> getFailResult(String code, String msg) {
        return ResponseInfo.<T>builder().code(code).msg(msg).build();
    }

    /**
     * 返回失败 - 默认返回结果（服务器错误）
     *
     * @return 接口响应对象
     */
    protected <T> ResponseInfo<T> getFailResult() {
        return ResponseInfo.<T>builder().code(ResponseCodeEnum.SERVER_ERROR.getCode())
                .msg(ResponseCodeEnum.SERVER_ERROR.getMsg()).build();
    }
}
