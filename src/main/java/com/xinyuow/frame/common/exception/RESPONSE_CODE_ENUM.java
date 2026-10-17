package com.xinyuow.frame.common.exception;

/**
 * 响应码枚举
 *
 * @author mxy
 * @date 2020/11/12
 */
public enum RESPONSE_CODE_ENUM {

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
    REQUEST_ADDRESS_ERROR("1100", "请求地址错误"),
    REQUEST_PARAMS_ERROR("1101", "参数异常"),
    CALL_INTERFACE_API_FAILED("1102", "调用接口失败"),

    /********************* 登录权限 **********************/
    USER_NAME_REPEAT("1000", "账户已存在"),
    LOGIN_INCORRECT("1001", "登录异常"),
    LOGIN_NOT_EXISTS("1002", "账户不存在"),
    LOGIN_PWD_INCORRECT("1003", "账户或密码不正确"),
    NOT_LOGIN_ERROR("1004", "重新登录"),
    OLD_PASSWORD_INCORRECT("1005", "旧密码不正确"),

    ACCOUNT_HAS_BEEN_DISABLED("1006", "此账号已被禁用"),
    ACCOUNT_IS_LOCKED("1007", "此账号已被锁定，请稍后再试"),
    ACCOUNT_AUTHENTICATION_FAILED("1008", "账号认证失败"),
    UNAUTHORIZED("1009", "权限不足"),
    ROLE_CODE_REPEAT("1010", "角色编码已存在"),
    SYS_ROLE_DELETE_FAIL("1011", "系统角色不允许删除"),
    SYS_MENU_DELETE_FAIL("1012", "还存在子菜单，不能直接删除"),

    QR_CODE_LOGIN_ERROR("1020", "二维码登录失败"),
    QR_CODE_IS_INVALID("1021", "无效的登录二维码"),
    QR_CODE_IS_TIMED_OUT("1022", "登录二维码已过期"),
    QR_CODE_TEMP_TOKEN_ERROR("1023", "请重新扫码登录"),

    /********************* 操作成功 **********************/
    REQUEST_SUCCESS("200", "操作成功");

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
