package com.xinyuow.frame.common.exception;

/**
 * 响应码枚举
 *
 * @author mxy
 * @date 2020/11/12
 */
public enum RESPONSE_CODE_ENUM {
    /********************* 操作成功 **********************/
    REQUEST_SUCCESS("200", "操作成功"),

    /********************* 系统异常 **********************/
    MIS_REQ_PARAM("400", "请求参数丢失"),
    NO_AUTH_REQUEST("401", "未授权"),
    REJECT_REQUEST("403", "未授权"),
    RESOURCE_NOT_FOUND("404", "请求的资源不存在"),
    METHOD_NOT_SUPPORTED("405", "不支持的请求方法"),
    MEDIA_TYPE_NOT_ACCEPT("406", "无法接受请求中的媒体类型"),
    REQUEST_TIME_OUT("408", "无法接受请求中的媒体类型"),
    MEDIA_TYPE_NOT_SUPPORTED("415", "不支持的媒体类型"),
    SERVER_ERROR("500", "获取数据异常"),

    /********************* 业务自定义异常 *****************/
    REQUEST_ADDRESS_ERROR("1000", "请求地址错误"),
    REQUEST_PARAMS_ERROR("1001", "参数异常"),
    CALL_INTERFACE_API_FAILED("1002", "调用接口失败"),
    INSUFFICIENT_PERMISSIONS_ERROR("1003", "权限不足");

    /**
     * 错误编码
     */
    public String code;

    /**
     * 错误编码信息
     */
    public String msg;

    /**
     * 构造函数
     *
     * @param code 编码
     * @param msg  编码信息
     */
    RESPONSE_CODE_ENUM(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取编码信息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置编码信息
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
