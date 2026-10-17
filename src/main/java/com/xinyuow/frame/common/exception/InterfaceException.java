/*
 * InterfaceException.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.exception;

import lombok.Getter;

import java.io.Serial;

/**
 * 自定义接口异常对象
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Getter
public class InterfaceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -3104161012391467728L;

    // 错误编码
    private final String errorCode;

    // 错误信息
    private final String errorMsg;

    /**
     * 应用接口有参构造函数
     *
     * @param errorCode 错误编码
     * @param errorMsg  错误信息
     */
    public InterfaceException(String errorCode, String errorMsg) {
        super("errorCode:" + errorCode + "  errorMsg:" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * 应用接口有参构造函数
     *
     * @param responseCode 响应编码枚举
     */
    public InterfaceException(ResponseCodeEnum responseCode) {
        super("errorCode:" + responseCode.getCode() + "  errorMsg:" + responseCode.getMsg());
        this.errorCode = responseCode.getCode();
        this.errorMsg = responseCode.getMsg();
    }
}
