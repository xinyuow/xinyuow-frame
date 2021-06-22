package com.xinyuow.frame.common.enums;

/**
 * 二维码状态枚举类
 *
 * @author mxy
 * @date 2021/6/21
 */
public enum QrCodeStatusEnum {

    CREATED("已创建"),
    SCANNED("已扫描"),
    CONFIRMED("已确认"),
    INVALID("已无效"),
    TIMED_OUT("已超时");

    /**
     * 二维码状态说明
     */
    private String name;

    /**
     * 二维码状态枚举有参构造函数
     *
     * @param name 二维码状态说明
     */
    QrCodeStatusEnum(String name) {
        this.name = name;
    }

    /**
     * 获取二维码状态说明
     */
    public String getName() {
        return name;
    }
}
