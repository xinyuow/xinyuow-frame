package com.xinyuow.frame.common.enums;

/**
 * 角色类型枚举类
 *
 * @author mxy
 * @date 2021/4/22
 */
public enum RoleTypeEnum {

    SYSTEM_ROLE(0, "系统角色"),
    ORDINARY_ROLE(1, "普通角色");

    /**
     * 角色类型值
     */
    private Integer value;

    /**
     * 角色类型说明
     */
    private String name;

    /**
     * 角色类型枚举有参构造函数
     *
     * @param value 角色类型值
     * @param name  角色类型说明
     */
    RoleTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 获取角色类型值
     */
    public Integer getValue() {
        return value;
    }

    /**
     * 获取角色类型说明
     */
    public String getName() {
        return name;
    }
}
