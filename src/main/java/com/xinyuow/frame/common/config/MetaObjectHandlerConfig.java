/*
 * MetaObjectHandlerConfig.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 配置公共字段自动填充功能  @TableField(..fill = FieldFill.INSERT)
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

    /**
     * 新增数据执行
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName("createDate", now, metaObject);
        this.setFieldValByName("modifyDate", now, metaObject);
    }

    /**
     * 更新数据执行
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("modifyDate", LocalDateTime.now(), metaObject);
    }
}
