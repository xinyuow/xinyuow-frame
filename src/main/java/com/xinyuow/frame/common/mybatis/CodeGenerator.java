package com.xinyuow.frame.common.mybatis;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成类
 *
 * @author mxy
 * @date 2020/11/13
 */
public class CodeGenerator {

    // 生成文件存储路径，当前配置工程路径。直接生成到工程下
    private final static String PROJECT_PATH = "/Users/xinyuow/IdeaProjects/xinyuow-frame";
    // 开发人员
    private final static String AUTHOR = "mxy";
    // 生成文件的输出目录
    private final static String OUTPUT_DIR = PROJECT_PATH + "/src/main/java";

    // 数据源信息
    private final static String DB_URL = "jdbc:mysql://localhost:3306/xinyuow_frame?characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
    private final static String DB_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private final static String DB_USERNAME = "root";
    private final static String DB_PASSWORD = "123456";

    // 表前缀
    private final static String TABLE_PREFIX = "s_";
    // 自定义controller父类
    private final static String SUPER_CONTROLLER_CLASS = "com.xinyuow.frame.common.controller.BaseController";

    // 模板路径
    private final static String ENTITY_TEMPLATE_PATH_WITHOUT_SUFFIX = "/templates/entity.java";
    private final static String MAPPER_XML_TEMPLATE_PATH = "/templates/mapper.xml.vm";

    // 自定义包名路径前缀
    private final static String PACKAGE_PARENT_PATH = "com.xinyuow.frame";
    // 自定义 *Controller.java 的类路径
    private final static String CONTROLLER_PATH = "controller";
    // 自定义 *Service.java 的类路径
    private final static String SERVICE_PATH = "service.app";
    // 自定义 *ServiceImpl.java 的类路径
    private final static String SERVICE_IMPL_PATH = "service.app.impl";
    // 自定义 *Mapper.java 的类路径
    private final static String MAPPER_PATH = "mapper.app";
    // 自定义 *Mapper.xml 的类路径
    private final static String MAPPER_XML_PATH = "/src/main/resources/mapper/app/";
    // 自定义 model.java 的类路径
    private final static String ENTITY_PATH = "model.app";

    // 自动填充的表字段名称-创建时间
    private final static String TABLE_FIELD_FILL_CREATE_DATE = "create_date";
    // 自动填充的表字段名称-修改时间
    private final static String TABLE_FIELD_FILL_MODIFY_DATE = "modify_date";


    /**
     * 生成基础代码
     * 需要修改的内容：
     * 1.代码生成位置：{@link CodeGenerator#PROJECT_PATH}
     * 2.开发人员名称：{@link CodeGenerator#AUTHOR}
     * 3.MySQL的链接信息：{@link CodeGenerator#DB_URL}
     * 4.通用表前缀：{@link CodeGenerator#TABLE_PREFIX}
     * 5.自动填充的表字段名称-创建时间：{@link CodeGenerator#TABLE_FIELD_FILL_CREATE_DATE}
     * 6.自动填充的表字段名称-修改时间：{@link CodeGenerator#TABLE_FIELD_FILL_MODIFY_DATE}
     * <p>
     * 注意：新生成的代码会覆盖原有代码！！！
     */
    public static void main(String[] args) {
        // 待生成的表名
        String tableName = "";

        if (StringUtils.isNotBlank(tableName)) {
            // 调用基础代码生成方法
            generateCode(tableName);
        }
    }

    /**
     * 生成相关代码
     *
     * @param tableName 数据表名称，多个用英文逗号隔开
     */
    private static void generateCode(String tableName) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(OUTPUT_DIR);
        gc.setAuthor(AUTHOR);
        gc.setOpen(Boolean.FALSE);
        gc.setBaseColumnList(Boolean.TRUE);
        gc.setBaseResultMap(Boolean.TRUE);
        gc.setFileOverride(Boolean.TRUE);   // 覆盖已有文件
        gc.setDateType(DateType.TIME_PACK); // 时间类型，使用 java.time 包下的新时间类型
        gc.setSwagger2(Boolean.TRUE);
        gc.setEnableCache(Boolean.FALSE);   // XML二级缓存
        gc.setIdType(IdType.ASSIGN_ID);     // 雪花算法生成ID
        // 自定义文件命名，%s会自动填充表实体属性
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(DB_URL);
        dsc.setDriverName(DB_DRIVER_NAME);
        dsc.setUsername(DB_USERNAME);
        dsc.setPassword(DB_PASSWORD);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PACKAGE_PARENT_PATH);
        pc.setController(CONTROLLER_PATH);
        pc.setMapper(MAPPER_PATH);
        pc.setService(SERVICE_PATH);
        pc.setServiceImpl(SERVICE_IMPL_PATH);
        pc.setEntity(ENTITY_PATH);
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setCapitalMode(Boolean.TRUE);                        // 驼峰命名
        sc.setEntityLombokModel(Boolean.TRUE);                  // 实体类是否带有lombok风格
        sc.setEntityTableFieldAnnotationEnable(Boolean.TRUE);   // 允许字段自动注解
        sc.setEntitySerialVersionUID(Boolean.TRUE);             // 实体生成serialVersionUID
        sc.setRestControllerStyle(Boolean.TRUE);                // rest风格controller
        sc.setChainModel(Boolean.TRUE);                         // 实体类是否为链式模型 @Accessors(chain = true)
        sc.setControllerMappingHyphenStyle(Boolean.TRUE);       // 驼峰转连字符
        sc.setTablePrefix(TABLE_PREFIX);                        // 表前缀
        sc.setNaming(NamingStrategy.underline_to_camel);        // 表名生成策略(下划线转驼峰命名)
        sc.setColumnNaming(NamingStrategy.underline_to_camel);  // 列名生成策略(下划线转驼峰命名)
        sc.setSuperControllerClass(SUPER_CONTROLLER_CLASS);     // 自定义controller父类
        sc.setInclude(tableName.split(StrUtil.COMMA));          // 修改替换成需要包含的表名

        // 自定义需要填充的字段 数据库中的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill(TABLE_FIELD_FILL_CREATE_DATE, FieldFill.INSERT));
        tableFillList.add(new TableFill(TABLE_FIELD_FILL_MODIFY_DATE, FieldFill.INSERT_UPDATE));
        // 自动填充设置
        sc.setTableFillList(tableFillList);
        mpg.setStrategy(sc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };

        // 自定义生成文件的输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(MAPPER_XML_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 指定 *Mapper.xml 所在的路径
                return PROJECT_PATH + MAPPER_XML_PATH + tableInfo.getXmlName() + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板，不要携带 .vm 后缀。会根据使用的模板自动识别
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        templateConfig.setEntity(ENTITY_TEMPLATE_PATH_WITHOUT_SUFFIX);
        mpg.setTemplate(templateConfig);

        mpg.execute();
    }
}
