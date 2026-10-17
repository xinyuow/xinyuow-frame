/*
 * EnumValue.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.validator.annotation;

import com.xinyuow.frame.common.validator.EnumValueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义枚举校验注解
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Documented
@Constraint(validatedBy = EnumValueValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface EnumValue {

    /**
     * 校验失败提示信息
     */
    String message() default "枚举不合规范";

    /**
     * 校验分组
     */
    Class<?>[] groups() default {};

    /**
     * 校验负载
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 目标枚举类
     */
    Class<?> target() default Class.class;

    /**
     * 是否忽略空值
     */
    boolean ignoreEmpty() default true;
}
