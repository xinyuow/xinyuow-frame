package com.xinyuow.frame.common.enums;

/**
 * 状态枚举类
 *
 * @author mxy
 * @date 2021/4/22
 */
public enum StatusEnum {

    ENABLE(0, "启用"),
    DISABLE(1, "禁用");

    /**
     * 状态值
     */
    private Integer value;

    /**
     * 状态说明
     */
    private String name;

    /**
     * 状态枚举有参构造函数
     *
     * @param value 状态值
     * @param name  状态说明
     */
    StatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 获取状态值
     */
    public Integer getValue() {
        return value;
    }

    /**
     * 获取状态说明
     */
    public String getName() {
        return name;
    }
}
