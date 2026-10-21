/*
 * GlobalExceptionAdvisor.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.exception;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.xinyuow.frame.common.model.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

/**
 * 全局异常处理
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvisor {

    /**
     * 捕获全局异常，处理所有不可知的异常
     *
     * @param exception 异常对象
     * @return 错误信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseInfo<String> handleException(Exception exception) {
        log.error("\r\n ********* 操作出现异常：{}", ExceptionUtils.getStackTrace(exception));
        Class<?> eClass = exception.getClass();
        if (eClass.equals(HttpRequestMethodNotSupportedException.class)) {
            return this.addResCodeToMap(ResponseCodeEnum.METHOD_NOT_SUPPORTED.getCode(),
                    ResponseCodeEnum.METHOD_NOT_SUPPORTED.getMsg());
        } else if (eClass.equals(HttpMediaTypeNotAcceptableException.class)) {
            return this.addResCodeToMap(ResponseCodeEnum.MEDIA_TYPE_NOT_ACCEPT.getCode(),
                    ResponseCodeEnum.MEDIA_TYPE_NOT_ACCEPT.getMsg());
        } else if (eClass.equals(HttpMediaTypeNotSupportedException.class)) {
            return this.addResCodeToMap(ResponseCodeEnum.MEDIA_TYPE_NOT_SUPPORTED.getCode(),
                    ResponseCodeEnum.MEDIA_TYPE_NOT_SUPPORTED.getMsg());
        } else if (eClass.equals(NoHandlerFoundException.class)) {
            return this.addResCodeToMap(ResponseCodeEnum.RESOURCE_NOT_FOUND.getCode(),
                    ResponseCodeEnum.RESOURCE_NOT_FOUND.getMsg());
        } else if (eClass.equals(MissingRequestHeaderException.class)) {
            return this.addResCodeToMap(ResponseCodeEnum.REQUEST_PARAMS_ERROR.getCode(),
                    ResponseCodeEnum.REQUEST_PARAMS_ERROR.getMsg());
        } else if (eClass.equals(ConversionNotSupportedException.class)) {
            return this.addResCodeToMap(ResponseCodeEnum.SERVER_ERROR.getCode(),
                    ResponseCodeEnum.SERVER_ERROR.getMsg());
        } else if (eClass.equals(HttpMessageNotWritableException.class)) {
            return this.addResCodeToMap(ResponseCodeEnum.SERVER_ERROR.getCode(),
                    ResponseCodeEnum.SERVER_ERROR.getMsg());
        } else {
            return this.addResCodeToMap(ResponseCodeEnum.SERVER_ERROR.getCode(),
                    ResponseCodeEnum.SERVER_ERROR.getMsg());
        }
    }

    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseInfo<String> validationBodyException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        String msg = null;
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError objectError : errors) {
                FieldError fieldError = (FieldError) objectError;
                if (fieldError.getDefaultMessage() != null) {
                    log.error("\r\n ********* 参数校验失败。请求对象【{}】,请求参数【{}】,错误信息【{}】",
                            fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
                    msg = fieldError.getDefaultMessage();
                }
            }
        }
        return this.addResCodeToMap(ResponseCodeEnum.REQUEST_PARAMS_ERROR.getCode(), msg);
    }

    /**
     * 参数类型转换错误
     *
     * @param exception 错误
     * @return 错误信息
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseInfo<String> httpMessageConversionException(HttpMessageConversionException exception) {
        log.error("\r\n ********* 参数类型转换错误，异常信息如下：{}", ExceptionUtil.getMessage(exception));
        return this.addResCodeToMap(ResponseCodeEnum.CALL_INTERFACE_API_FAILED.getCode(),
                ResponseCodeEnum.CALL_INTERFACE_API_FAILED.getMsg());
    }

    /**
     * 请求参数不可读异常
     *
     * @param exception 错误
     * @return 错误信息
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseInfo<String> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error("\r\n ********* 请求参数不可读异常，异常信息如下：{}", ExceptionUtil.getMessage(exception));
        return this.addResCodeToMap(ResponseCodeEnum.REQUEST_PARAMS_ERROR.getCode(),
                ResponseCodeEnum.REQUEST_PARAMS_ERROR.getMsg());
    }

    /**
     * 捕获业务异常
     *
     * @param exception 自定义接口异常类
     * @return 返回的异常信息
     */
    @ExceptionHandler(InterfaceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseInfo<String> handleInterfaceException(InterfaceException exception) {
        log.error("\r\n ********* 捕获业务异常，异常信息如下：{}", ExceptionUtil.getMessage(exception));
        return this.addResCodeToMap(exception.getErrorCode(), exception.getErrorMsg());
    }

    /**
     * 捕获Spring Security无权限异常
     *
     * @param exception 无权限异常
     * @return 返回的异常信息map
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseInfo<String> handleAuthorizationDeniedException(AuthorizationDeniedException exception) {
        log.error("\r\n ********* 无权限异常，异常信息：{}", ExceptionUtil.getMessage(exception));
        return this.addResCodeToMap(ResponseCodeEnum.UNAUTHORIZED.getCode(),
                ResponseCodeEnum.UNAUTHORIZED.getMsg());
    }

    /**
     * 添加异常信息到返回对象
     *
     * @param code 响应状态码
     * @param msg  响应信息
     * @return 响应对象
     */
    protected ResponseInfo<String> addResCodeToMap(Integer code, String msg) {
        return ResponseInfo.<String>builder().code(code).msg(msg).timestamp(DateUtil.current()).body("").build();
    }
}