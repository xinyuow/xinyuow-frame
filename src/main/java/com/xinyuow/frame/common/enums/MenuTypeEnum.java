/*
 * MenuTypeEnum.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.common.enums;

import lombok.Getter;

/**
 * 菜单类型枚举类
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Getter
public enum MenuTypeEnum {

    MENU(0, "菜单"),
    API(1, "接口");

    private final Integer value;
    private final String name;

    MenuTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
