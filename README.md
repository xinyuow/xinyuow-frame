# xxx项目

### 功能描述
xxx项目的功能描述

### 开发相关信息
- Java JDK1.8
- MySQL 8.0
- Springboot 2.3.4
- MybatisPlus 3.3.2
- Druid 1.1.21
- FastJson 1.2.72
- Lombok 1.16.20
- Swagger2 2.9.2
- Hutool 5.3.8

### 开发一览
- 安装Lombok
- 修改application.yml的数据源信息
- 使用 FrameApplication.java 项目启动类进行启动
- *MybatisPlus 生成基础代码需要调整数据源连接信息
- 注意：生成基础代码时，需要调整项目路径、调整数据库信息。
- 注意：生成基础代码时，会覆盖项目中同名文件

### 项目结构简介
````
xinyuow-frame
├── src  
│    ├── main   
│    │    ├── java  
│    │    │     └── com.xinyuow.frame  
│    │    │             ├── aspect                  // 切面模块    
│    │    │             ├── common                  // 公共模块  
│    │    │             │     ├── config            // 相关配置模块
│    │    │             │     ├── constant          // 相关常量模块   
│    │    │             │     ├── controller        // BaseController  
│    │    │             │     ├── exception         // 异常配置模块  
│    │    │             │     └── mybatis           // ORM框架配置模块(MybatisPlus)  
│    │    │             ├── controller              // 业务控制模块   
│    │    │             ├── mapper                  // Mapper接口模块  
│    │    │             ├── model                   // 实体类模块  
│    │    │             ├── service                 // 业务处理模块  
│    │    │             ├── VO                      // VO，主要包括本项目接口涉及的请求/响应类  
│    │    │             └── FrameApplication.java   // 项目启动类  
│    │    └── resources  
│    │          ├── mapper                          // *Mapper.xml模块  
│    │          ├── templates                       // 基础代码生成模板  
│    │          └── application.yml                 // 项目配置信息  
│    └── test                                       // 项目单元测试   
│         └── java  
│               └── com.xinyuow.frame  
│                       └── FrameApplicationTests.java  
├── .gitignore  
├── pom.xml  
└── README.md   
````
