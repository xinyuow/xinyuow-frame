package com.xinyuow.frame.common.exception;

/**
 * 自定义接口异常对象
 *
 * @author mxy
 * @date 2020/11/12
 */
public class InterfaceException extends RuntimeException {
    private static final long serialVersionUID = -3104161012391467728L;

    // 错误编码
    private String errorCode;

    // 错误编码信息
    private String errorMsg;

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
    public InterfaceException(RESPONSE_CODE_ENUM responseCode) {
        super("errorCode:" + responseCode.getCode() + "  errorMsg:" + responseCode.getMsg());
        this.errorCode = responseCode.getCode();
        this.errorMsg = responseCode.getMsg();
    }

    /**
     * 获取错误编码
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 设置错误编码
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 获取异常编码
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置异常编码
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
