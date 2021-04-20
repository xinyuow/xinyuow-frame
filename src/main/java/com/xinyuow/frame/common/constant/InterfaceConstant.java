package com.xinyuow.frame.common.constant;

import org.springframework.beans.factory.InitializingBean;
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

    @Override
    public void afterPropertiesSet() {

    }
}
