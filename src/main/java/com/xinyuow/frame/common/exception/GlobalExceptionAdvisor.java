package com.xinyuow.frame.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
 * @author mxy
 * @date 2020/11/12
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

    /**
     * 捕获全局异常，处理所有不可知的异常
     *
     * @param exception 异常对象
     */
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception exception) {
        log.error("\r\n ********* 操作出现异常：{}", ExceptionUtils.getStackTrace(exception));
        Class<?> eClass = exception.getClass();
        Map<String, Object> map = new HashMap<>();
        if (eClass.equals(HttpRequestMethodNotSupportedException.class)) {
            addResCodeToMap(RESPONSE_CODE_ENUM.METHOD_NOT_SUPPORTED, map);
        } else if (eClass.equals(HttpMediaTypeNotAcceptableException.class)) {
            addResCodeToMap(RESPONSE_CODE_ENUM.MEDIA_TYPE_NOT_ACCEPT, map);
        } else if (eClass.equals(HttpMediaTypeNotSupportedException.class)) {
            addResCodeToMap(RESPONSE_CODE_ENUM.MEDIA_TYPE_NOT_SUPPORTED, map);
        } else if (eClass.equals(NoHandlerFoundException.class)){
            addResCodeToMap(RESPONSE_CODE_ENUM.RESOURCE_NOT_FOUND, map);
        } else if (eClass.equals(ConversionNotSupportedException.class)) {
            addResCodeToMap(RESPONSE_CODE_ENUM.SERVER_ERROR, map);
        } else if (eClass.equals(HttpMessageNotWritableException.class)) {
            addResCodeToMap(RESPONSE_CODE_ENUM.SERVER_ERROR, map);
        } else {
            addResCodeToMap(RESPONSE_CODE_ENUM.SERVER_ERROR, map);
        }
        return map;
    }

    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> validationBodyException(MethodArgumentNotValidException exception) {
        Map<String, Object> map = new HashMap<>();

        BindingResult result = exception.getBindingResult();
        String msg = null;
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError objectError : errors) {
                FieldError fieldError = (FieldError) objectError;
                if (fieldError.getDefaultMessage() != null) {
                    log.error("\r\n ********* 参数校验失败。请求对象【{}】,请求参数【{}】,错误信息【{}】", fieldError.getObjectName(),
                            fieldError.getField(), fieldError.getDefaultMessage());
                    msg = fieldError.getDefaultMessage();
                }
            }
        }
        map.put(RESPONSE_CODE, RESPONSE_CODE_ENUM.REQUEST_PARAMS_ERROR.getCode());
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
        Map<String, Object> map = new HashMap<>();
        map.put(RESPONSE_CODE, RESPONSE_CODE_ENUM.CALL_INTERFACE_API_FAILED.getCode());
        map.put(RESPONSE_MSG, RESPONSE_CODE_ENUM.CALL_INTERFACE_API_FAILED.getMsg());
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
        Map<String, Object> map = new HashMap<>();
        map.put(RESPONSE_CODE, RESPONSE_CODE_ENUM.CALL_INTERFACE_API_FAILED.getCode());
        map.put(RESPONSE_MSG, RESPONSE_CODE_ENUM.CALL_INTERFACE_API_FAILED.getMsg());
        return map;
    }

    /**
     * 捕获业务异常
     *
     * @param exception 自定义接口异常类
     * @return 返回的异常信息map
     */
    @ExceptionHandler(InterfaceException.class)
    public Map<String, Object> handleInterfaceException(InterfaceException exception) {
        Map<String, Object> map = new HashMap<>();
        map.put(RESPONSE_CODE, exception.getErrorCode());
        map.put(RESPONSE_MSG, exception.getErrorMsg());
        return map;
    }

    /**
     * 捕获Shiro无权限异常 未授权异常
     *
     * @param exception 无权限异常
     * @return 返回的异常信息map
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Object handleUnauthorizedException(UnauthorizedException exception) {
        // 无权限
        Map<String, Object> map = new HashMap<>();
        addResCodeToMap(RESPONSE_CODE_ENUM.UNAUTHORIZED, map);
        return map;
    }

    /**
     * 捕获Shiro无权限异常 未认证异常
     *
     * @param exception 无权限异常
     * @return 返回的异常信息map
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public Object handleUnauthenticatedException(UnauthenticatedException exception) {
        // 无权限
        Map<String, Object> map = new HashMap<>();
        addResCodeToMap(RESPONSE_CODE_ENUM.UNAUTHORIZED, map);
        return map;
    }

    /**
     * 添加异常信息到map中
     *
     * @param responseCodeEnum 错误响应编码枚举类对象
     * @param map              响应对象
     */
    private void addResCodeToMap(RESPONSE_CODE_ENUM responseCodeEnum, Map<String, Object> map) {
        map.put(RESPONSE_CODE, responseCodeEnum.getCode());
        map.put(RESPONSE_MSG, responseCodeEnum.getMsg());
    }
}
