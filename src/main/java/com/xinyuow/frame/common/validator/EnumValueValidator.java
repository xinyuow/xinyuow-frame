/*
 * EnumValueValidator.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.validator;

import com.xinyuow.frame.common.validator.annotation.EnumValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 自定义枚举校验器
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Slf4j
public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {

    /**
     * 枚举校验注解
     */
    private EnumValue annotation;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Class<?> targetClass = annotation.target();
        // target不是枚举，直接校验失败
        if (!targetClass.isEnum()) {
            return false;
        }

        // 空值处理交由 ignoreEmpty 控制
        if (StringUtils.isBlank(value)) {
            return annotation.ignoreEmpty();
        }

        Object[] enumConstants = targetClass.getEnumConstants();
        for (Object enumConstant : enumConstants) {
            Enum<?> enumValue = (Enum<?>) enumConstant;
            if (enumValue.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
