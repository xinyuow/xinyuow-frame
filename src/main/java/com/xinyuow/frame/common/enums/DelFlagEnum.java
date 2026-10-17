/*
 * DelFlagEnum.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.enums;

import lombok.Getter;

/**
 * 删除标识枚举类
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Getter
public enum DelFlagEnum {

    FALSE(0, "没有删除"),
    TRUE(1, "已经删除");

    private final Integer value;
    private final String name;

    DelFlagEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
