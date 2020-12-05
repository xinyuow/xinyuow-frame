package com.xinyuow.frame.common.constant;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 接口常量属性
 *
 * @author mxy
 * @date 2020/11/18
 */
@Component
public class InterfaceConstant implements InitializingBean {

    @Value("${interface.permission-control.request-header.auth-param-key}")
    private String authParamKey;

    /**
     * 接口权限控制，请求头中认证参数key
     */
    public static String AUTH_PARAM_KEY;

    @Override
    public void afterPropertiesSet() {
        AUTH_PARAM_KEY = authParamKey;
    }
}
