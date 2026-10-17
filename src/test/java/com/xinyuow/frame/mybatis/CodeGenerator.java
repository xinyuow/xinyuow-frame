/*
 * CodeGenerator.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2020-11-13
 */
package com.xinyuow.frame.mybatis;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import org.apache.ibatis.type.JdbcType;

import java.util.Collections;

/**
 * MyBatis-Plus 代码生成器。版本：MyBatis-Plus 3.5.17
 * 使用方式：
 * 1.修改数据库连接信息
 * 2.修改TABLE_PREFIX
 * 3.修改INCLUDE_TABLES
 * 4.运行main方法
 *
 * @author xinyuow
 * @date 2020-11-13
 */
public class CodeGenerator {

    /**
     * 项目根目录
     */
    private static final String PROJECT_PATH = "/Users/xinyuow/IdeaProjects/xinyuow-frame";

    /**
     * 作者
     */
    private static final String AUTHOR = "xinyuow";

    /**
     * 数据库配置信息
     */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/xinyuow?characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "123456789";

    /**
     * 表前缀
     */
    private static final String TABLE_PREFIX = "a_";

    /**
     * 包前缀
     */
    private static final String PACKAGE_PREFIX = "app";

    /**
     * 父类控制器
     */
    private static final String BASE_CONTROLLER = "AppBaseController";

    /**
     * 要生成的表
     */
    private static final String[] INCLUDE_TABLES = {""};

    /**
     * 基础包
     */
    private static final String PARENT_PACKAGE = "com.xinyuow.frame";

    /**
     * Controller生成的位置
     */
    private static final String CONTROLLER_PACKAGE = PACKAGE_PREFIX + "controller";

    /**
     * Service生成的位置
     */
    private static final String SERVICE_PACKAGE = PACKAGE_PREFIX + "service";

    /**
     * ServiceImpl生成的位置
     */
    private static final String SERVICE_IMPL_PACKAGE = PACKAGE_PREFIX + "service.impl";

    /**
     * Mapper生成的位置
     */
    private static final String MAPPER_PACKAGE = PACKAGE_PREFIX + "mapper";

    /**
     * Entity生成的位置
     */
    private static final String ENTITY_PACKAGE = PACKAGE_PREFIX + "entity";

    /**
     * Mapper XML生成的位置
     */
    private static final String MAPPER_XML_PATH = PROJECT_PATH + "/src/main/resources/mapper/" + PACKAGE_PREFIX;

    /**
     * 自定义Controller模板
     */
    private static final String CONTROLLER_TEMPLATE = "/templates/controller.java.vm";

    /**
     * 自定义Service模板
     */
    private static final String SERVICE_TEMPLATE = "/templates/service.java.vm";

    /**
     * 自定义ServiceImpl模板
     */
    private static final String SERVICE_IMPL_TEMPLATE = "/templates/serviceImpl.java.vm";

    /**
     * 自定义Entity模板
     */
    private static final String ENTITY_TEMPLATE = "/templates/entity.java";

    /**
     * 自定义Mapper模板
     */
    private static final String MAPPER_TEMPLATE = "/templates/mapper.java.vm";

    /**
     * Controller父类
     */
    private static final String SUPER_CONTROLLER_CLASS = "com.xinyuow.frame.common.controller." + BASE_CONTROLLER;

    /**
     * 代码生成器主方法
     */
    public static void main(String[] args) {
        FastAutoGenerator.create(DB_URL, DB_USERNAME, DB_PASSWORD)
                // 数据源配置
                .dataSourceConfig(builder -> builder
                        .typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            // MySQL tinyint -> Integer
                            if (JdbcType.TINYINT == metaInfo.getJdbcType()) {
                                return DbColumnType.INTEGER;
                            }
                            // 其他类型保持 MyBatis-Plus 默认转换规则
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                // 全局配置
                .globalConfig(builder -> builder
                        .author(AUTHOR)
                        .outputDir(PROJECT_PATH + "/src/main/java")
                        .dateType(DateType.TIME_PACK)
                        .disableOpenDir()
                )
                // 包配置
                .packageConfig(builder -> builder
                        .parent(PARENT_PACKAGE)
                        .entity(ENTITY_PACKAGE)
                        .service(SERVICE_PACKAGE)
                        .serviceImpl(SERVICE_IMPL_PACKAGE)
                        .mapper(MAPPER_PACKAGE)
                        .controller(CONTROLLER_PACKAGE)
                        .pathInfo(Collections.singletonMap(OutputFile.xml, MAPPER_XML_PATH))
                )
                // 策略配置
                .strategyConfig(builder -> builder
                        .addInclude(INCLUDE_TABLES)
                        .addTablePrefix(TABLE_PREFIX)

                        // Entity
                        .entityBuilder()
                        .idType(IdType.ASSIGN_ID)
                        .enableLombok()
                        .enableTableFieldAnnotation()
                        .naming(NamingStrategy.underline_to_camel)
                        .columnNaming(NamingStrategy.underline_to_camel)
                        .javaTemplate(ENTITY_TEMPLATE)
                        .addTableFills(
                                new Column("create_date", FieldFill.INSERT),
                                new Column("modify_date", FieldFill.INSERT_UPDATE)
                        )

                        // Controller
                        .controllerBuilder()
                        .superClass(SUPER_CONTROLLER_CLASS)
                        .enableRestStyle()
                        .template(CONTROLLER_TEMPLATE)

                        // Service
                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                        .serviceTemplate(SERVICE_TEMPLATE)
                        .formatServiceImplFileName("%sServiceImpl")
                        .serviceImplTemplate(SERVICE_IMPL_TEMPLATE)

                        // Mapper
                        .mapperBuilder()
                        .enableBaseResultMap()
                        .enableBaseColumnList()
                        .mapperTemplate(MAPPER_TEMPLATE)
                )

                // Velocity
                .templateEngine(new VelocityTemplateEngine())
                // 执行
                .execute();
    }
}
