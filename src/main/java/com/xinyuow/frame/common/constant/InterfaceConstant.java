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

    @Override
    public void afterPropertiesSet() {
        LOGIN_INFO_INIT_COUNT = loginInfoInitCount;
        LOGIN_INFO_MAX_FAIL_COUNT = loginInfoMaxFailCount;
        LOGIN_INFO_LOCK_TIME = loginInfoLockTime;
    }
}
