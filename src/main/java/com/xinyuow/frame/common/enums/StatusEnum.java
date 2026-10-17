/*
 * StatusEnum.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.enums;

import lombok.Getter;

/**
 * 状态枚举类
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Getter
public enum StatusEnum {

    ENABLE(0, "启用"),
    DISABLE(1, "禁用");

    private final Integer value;
    private final String name;

    StatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
