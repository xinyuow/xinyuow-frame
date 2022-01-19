package com.xinyuow.frame.common.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xinyuow.frame.common.mybatis.CodeGenerator;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 配置公共字段自动填充功能  @TableField(..fill = FieldFill.INSERT)
 *
 * @author mxy
 * @date 2021/11/4
 */
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

    /**
     * 新增数据执行
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName(StrUtil.toCamelCase(CodeGenerator.TABLE_FIELD_FILL_CREATE_DATE), LocalDateTime.now(), metaObject);
        this.setFieldValByName(StrUtil.toCamelCase(CodeGenerator.TABLE_FIELD_FILL_MODIFY_DATE), LocalDateTime.now(), metaObject);
    }

    /**
     * 更新数据执行
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(StrUtil.toCamelCase(CodeGenerator.TABLE_FIELD_FILL_MODIFY_DATE), LocalDateTime.now(), metaObject);
    }
}
