/*
 * MybatisPlusConfig.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.xinyuow.frame.common.constant.MybatisPlusConstant;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus配置
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Configuration
public class MybatisPlusConfig implements MetaObjectHandler {

    /**
     * MyBatis-Plus 插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 防止无条件 UPDATE / DELETE
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    /**
     * 新增数据执行
     * 配置公共字段自动填充功能  @TableField(..fill = FieldFill.INSERT)
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName(MybatisPlusConstant.CREATE_DATE, now, metaObject);
        this.setFieldValByName(MybatisPlusConstant.MODIFY_DATE, now, metaObject);
    }

    /**
     * 更新数据执行
     * 配置公共字段自动填充功能  @TableField(..fill = FieldFill.INSERT_UPDATE)
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(MybatisPlusConstant.MODIFY_DATE, LocalDateTime.now(), metaObject);
    }
}
