package com.xinyuow.frame.common.constant;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 接口常量属性
 * 统一管理@Value注解，避免散落到项目各处
 *
 * @author mxy
 * @date 2020/11/18
 */
@Component
public class InterfaceConstant implements InitializingBean {

    @Value("${xinyuow.frame.login-info.init-count}")
    private Integer loginInfoInitCount;

    @Value("${xinyuow.frame.login-info.max-fail-count}")
    private Integer loginInfoMaxFailCount;

    @Value("${xinyuow.frame.login-info.lock-time}")
    private Integer loginInfoLockTime;

    @Value("${xinyuow.frame.login-info.qr-code-width}")
    private Integer loginInfoQrCodeWidth;

    @Value("${xinyuow.frame.login-info.qr-code-height}")
    private Integer loginInfoQrCodeHeight;

    @Value("${xinyuow.frame.login-info.qr-code-redis.key-name}")
    private String loginInfoQrCodeRedisKeyName;

    @Value("${xinyuow.frame.login-info.qr-code-redis.expire}")
    private Integer loginInfoQrCodeRedisExpire;

    /**
     * 登录 - 登录初始计数
     */
    public static Integer LOGIN_INFO_INIT_COUNT;

    /**
     * 登录 - 最大登录失败次数
     */
    public static Integer LOGIN_INFO_MAX_FAIL_COUNT;

    /**
     * 登录 - 锁定时间，单位：分钟
     */
    public static Integer LOGIN_INFO_LOCK_TIME;

    /**
     * 二维码登录 - 二维码的宽度，单位：px
     */
    public static Integer LOGIN_INFO_QR_CODE_WIDTH;

    /**
     * 二维码登录 - 二维码的高度，单位：px
     */
    public static Integer LOGIN_INFO_QR_CODE_HEIGHT;

    /**
     * 二维码登录 - 存储二维码信息的Redis的key
     */
    public static String LOGIN_INFO_QR_CODE_REDIS_KEY_NAME;

    /**
     * 二维码登录 - 存储二维码信息的Redis的超时时间，单位：秒
     */
    public static Integer LOGIN_INFO_QR_CODE_REDIS_EXPIRE;

    @Override
    public void afterPropertiesSet() {
        LOGIN_INFO_INIT_COUNT = loginInfoInitCount;
        LOGIN_INFO_MAX_FAIL_COUNT = loginInfoMaxFailCount;
        LOGIN_INFO_LOCK_TIME = loginInfoLockTime;
        LOGIN_INFO_QR_CODE_WIDTH = loginInfoQrCodeWidth;
        LOGIN_INFO_QR_CODE_HEIGHT = loginInfoQrCodeHeight;
        LOGIN_INFO_QR_CODE_REDIS_KEY_NAME = loginInfoQrCodeRedisKeyName;
        LOGIN_INFO_QR_CODE_REDIS_EXPIRE = loginInfoQrCodeRedisExpire;
    }
}
