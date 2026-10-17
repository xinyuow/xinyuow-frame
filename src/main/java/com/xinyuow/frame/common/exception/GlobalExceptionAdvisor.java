/*
 * GlobalExceptionAdvisor.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局异常处理
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvisor {

    // 响应码key
    public static final String RESPONSE_CODE = "code";

    // 响应信息key
    public static final String RESPONSE_MSG = "msg";

    // 响应传输信息key
    public static final String RESPONSE_BODY = "body";

    // 响应时间戳key
    public static final String RESPONSE_TIMESTAMP = "timestamp";

    /**
     * 捕获全局异常，处理所有不可知的异常
     *
     * @param exception 异常对象
     * @return 错误信息
     */
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception exception) {
        log.error("\r\n ********* 操作出现异常", exception);

        Map<String, Object> map = new HashMap<>(16);
        Class<?> eClass = exception.getClass();

        if (eClass.equals(HttpRequestMethodNotSupportedException.class)) {
            addResCodeToMap(ResponseCodeEnum.METHOD_NOT_SUPPORTED, map);
        } else if (eClass.equals(HttpMediaTypeNotAcceptableException.class)) {
            addResCodeToMap(ResponseCodeEnum.MEDIA_TYPE_NOT_ACCEPT, map);
        } else if (eClass.equals(HttpMediaTypeNotSupportedException.class)) {
            addResCodeToMap(ResponseCodeEnum.MEDIA_TYPE_NOT_SUPPORTED, map);
        } else if (eClass.equals(NoHandlerFoundException.class)) {
            addResCodeToMap(ResponseCodeEnum.RESOURCE_NOT_FOUND, map);
        } else if (eClass.equals(ConversionNotSupportedException.class)
                || eClass.equals(HttpMessageNotWritableException.class)) {
            addResCodeToMap(ResponseCodeEnum.SERVER_ERROR, map);
        } else {
            addResCodeToMap(ResponseCodeEnum.SERVER_ERROR, map);
        }
        return map;
    }

    /**
     * 处理请求参数校验异常
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> validationBodyException(MethodArgumentNotValidException exception) {
        Map<String, Object> map = new HashMap<>(16);

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String msg = null;

        for (FieldError fieldError : fieldErrors) {
            if (fieldError.getDefaultMessage() != null) {
                log.error("\r\n ********* 参数校验失败。请求对象【{}】,请求参数【{}】,错误信息【{}】", fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
                msg = fieldError.getDefaultMessage();
            }
        }

        map.put(RESPONSE_CODE, ResponseCodeEnum.REQUEST_PARAMS_ERROR.getCode());
        map.put(RESPONSE_MSG, msg);
        return map;
    }

    /**
     * 参数类型转换错误
     *
     * @param exception 错误
     * @return 错误信息
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public Map<String, Object> httpMessageConversionException(HttpMessageConversionException exception) {
        log.error("\r\n ********* 参数类型转换错误，异常信息如下：{}", exception.getCause().getLocalizedMessage());
        Map<String, Object> map = new HashMap<>(16);
        map.put(RESPONSE_CODE, ResponseCodeEnum.CALL_INTERFACE_API_FAILED.getCode());
        map.put(RESPONSE_MSG, ResponseCodeEnum.CALL_INTERFACE_API_FAILED.getMsg());
        return map;
    }

    /**
     * 请求参数不可读异常
     *
     * @param exception 错误
     * @return 错误信息
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, Object> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error("\r\n ********* 请求参数不可读异常，异常信息如下：{}", exception.getLocalizedMessage());
        Map<String, Object> map = new HashMap<>(16);
        map.put(RESPONSE_CODE, ResponseCodeEnum.REQUEST_PARAMS_ERROR.getCode());
        map.put(RESPONSE_MSG, ResponseCodeEnum.REQUEST_PARAMS_ERROR.getMsg());
        return map;
    }

    /**
     * 捕获业务异常
     *
     * @param exception 自定义接口异常类
     * @return 返回的异常信息
     */
    @ExceptionHandler(InterfaceException.class)
    public Map<String, Object> handleInterfaceException(InterfaceException exception) {
        Map<String, Object> map = new HashMap<>(16);

        map.put(RESPONSE_CODE, exception.getErrorCode());
        map.put(RESPONSE_MSG, exception.getErrorMsg());
        return map;
    }

    /**
     * 添加异常信息到map中
     *
     * @param responseCodeEnum 错误响应编码枚举类对象
     * @param map              响应对象
     */
    private void addResCodeToMap(ResponseCodeEnum responseCodeEnum, Map<String, Object> map) {
        map.put(RESPONSE_CODE, responseCodeEnum.getCode());
        map.put(RESPONSE_MSG, responseCodeEnum.getMsg());
    }
}