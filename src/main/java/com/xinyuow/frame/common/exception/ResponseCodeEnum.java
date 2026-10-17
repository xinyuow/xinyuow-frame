/*
 * ResponseCodeEnum.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.exception;

import lombok.Getter;

/**
 * 响应码枚举
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Getter
public enum ResponseCodeEnum {

    /********************* 系统异常 **********************/
    MIS_REQ_PARAM("400", "请求参数丢失", 0),
    NO_AUTH_REQUEST("401", "未认证", 0),
    REJECT_REQUEST("403", "禁止访问", 0),
    RESOURCE_NOT_FOUND("404", "请求的资源不存在", 0),
    METHOD_NOT_SUPPORTED("405", "不支持的请求方法", 0),
    MEDIA_TYPE_NOT_ACCEPT("406", "无法接受请求中的媒体类型", 0),
    REQUEST_TIME_OUT("408", "请求超时", 0),
    MEDIA_TYPE_NOT_SUPPORTED("415", "不支持的媒体类型", 0),
    SERVER_ERROR("500", "获取数据异常", 0),

    /********************* APP端登录、用户权限异常 **********************/
    APP_USER_DISABLE("1000", "当前用户已被禁用", 1),
    APP_USER_NOT_LOGIN("1001", "当前用户未登录", 1),
    APP_USER_NOT_FOUND("1002", "用户不存在", 1),

    /********************* APP端业务自定义异常 **********************/
    APP_REQUEST_PARAMS_ERROR("1100", "请求参数异常", 1),

    /********************* Web端登录、用户权限异常 **********************/
    USER_NAME_REPEAT("2000", "账户已存在", 2),
    LOGIN_INCORRECT("2001", "登录异常", 2),
    LOGIN_NOT_EXISTS("2002", "账户不存在", 2),
    LOGIN_PWD_INCORRECT("2003", "账户或密码不正确", 2),
    NOT_LOGIN_ERROR("2004", "重新登录", 2),
    OLD_PASSWORD_INCORRECT("2005", "旧密码不正确", 2),
    ACCOUNT_HAS_BEEN_DISABLED("2006", "此账号已被禁用", 2),
    ACCOUNT_IS_LOCKED("2007", "此账号已被锁定，请稍后再试", 2),
    ACCOUNT_AUTHENTICATION_FAILED("1008", "账号认证失败", 2),
    UNAUTHORIZED("2009", "权限不足", 2),
    ROLE_CODE_REPEAT("2010", "角色编码已存在", 2),
    SYS_ROLE_DELETE_FAIL("2011", "系统角色不允许删除", 2),
    SYS_MENU_DELETE_FAIL("2012", "还存在子菜单，不能直接删除", 2),

    /********************* Web端业务自定义异常 *****************/
    REQUEST_ADDRESS_ERROR("2100", "请求地址错误", 2),
    REQUEST_PARAMS_ERROR("2101", "参数异常", 2),
    CALL_INTERFACE_API_FAILED("2102", "调用接口失败", 2),

    /********************* 操作成功 **********************/
    REQUEST_SUCCESS("200", "操作成功", 3);

    /**
     * 错误编码
     */
    private final String code;

    /**
     * 错误编码信息
     */
    private final String msg;

    /**
     * 错误编码类型
     * 0：系统异常
     * 1：APP端异常
     * 2：Web端异常
     * 3：操作成功
     */
    private final Integer type;

    /**
     * 构造函数
     *
     * @param code 编码
     * @param msg  编码信息
     * @param type 编码类型
     */
    ResponseCodeEnum(String code, String msg, Integer type) {
        this.code = code;
        this.msg = msg;
        this.type = type;
    }

    /**
     * 根据错误编码获取响应码枚举 - 只获取系统异常响应码(HTTP)
     *
     * @param code 错误编码
     * @return 响应码枚举
     */
    public static ResponseCodeEnum getHttpResponseCode(String code) {
        for (ResponseCodeEnum responseCode : ResponseCodeEnum.values()) {
            if (0 == responseCode.getType() && code.equals(responseCode.getCode())) {
                return responseCode;
            }
        }
        return ResponseCodeEnum.SERVER_ERROR;
    }
}
