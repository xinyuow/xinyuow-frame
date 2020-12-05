package com.xinyuow.frame.common.mybatis;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

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
    private final static String DB_URL = "jdbc:mysql://localhost:3306/hive?characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
    private final static String DB_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private final static String DB_USERNAME = "root";
    private final static String DB_PASSWORD = "123456";

    // 表前缀
    private final static String TABLE_PREFIX = "";
    // 自定义controller父类
    private final static String SUPER_CONTROLLER_CLASS = "BaseController";

    // 模板路径
    private final static String ENTITY_TEMPLATE_PATH_WITHOUT_SUFFIX = "/templates/entity.java";
    private final static String MAPPER_XML_TEMPLATE_PATH = "/templates/mapper.xml.vm";
    // 自定义 *Mapper.xml 的类路径
    private final static String MAPPER_XML_PATH = "/src/main/resources/mapper/";

    /**
     * 生成基础代码
     * 注意：新生成的代码会覆盖原有代码！！！
     */
    public static void main(String[] args) {
        // 待生成的表名
        String tableName = "";

        // 调用基础代码生成方法
        generateCode(tableName);
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
        gc.setOpen(false);
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        gc.setFileOverride(true);   // 覆盖已有文件
        gc.setDateType(DateType.TIME_PACK); // 时间类型，使用 java.time 包下的新时间类型
        gc.setSwagger2(true);
        gc.setEnableCache(false);   // XML二级缓存
        gc.setIdType(IdType.ASSIGN_ID); // 雪花算法生成ID
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
        pc.setParent("com.xinyuow.frame");
        pc.setController("controller");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setEntity("model");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setCapitalMode(true);    // 驼峰命名
        sc.setEntityLombokModel(true);  // 实体类是否带有lombok风格
        sc.setEntityTableFieldAnnotationEnable(true);   // 允许字段自动注解
        sc.setEntitySerialVersionUID(true); // 实体生成serialVersionUID
        sc.setRestControllerStyle(true);    // rest风格controller
        sc.setChainModel(true); // 实体类是否为链式模型 @Accessors(chain = true)
        sc.setControllerMappingHyphenStyle(true); // 驼峰转连字符
        sc.setTablePrefix(TABLE_PREFIX);    // 表前缀
        sc.setNaming(NamingStrategy.underline_to_camel);    // 表名生成策略(下划线转驼峰命名)
        sc.setColumnNaming(NamingStrategy.underline_to_camel);  // 列名生成策略(下划线转驼峰命名)
        sc.setSuperControllerClass(SUPER_CONTROLLER_CLASS); // 自定义controller父类
        sc.setInclude(tableName.split(","));   // 修改替换成需要包含的表名
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
