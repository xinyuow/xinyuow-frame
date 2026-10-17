# xxx项目

### 功能描述
xxx项目的功能描述

### 开发相关信息
- Java JDK 17
- MySQL 26.7.0
- Springboot 4.1.1
- MybatisPlus 3.5.17
- Druid 1.2.28
- FastJson2 2.0.60
- SpringDoc OpenAPI 3.1.1
- HuTool 5.8.41

### 开发一览
- 在 src 文件夹下创建 `.env` 文件，用于配置环境变量信息
  ```
  # 环境配置信息，写入自己本地环境的信息
  
  # MySQL
  MYSQL_HOST=
  MYSQL_PORT=
  MYSQL_DATABASE=
  MYSQL_USERNAME=
  MYSQL_PASSWORD=
  
  # Redis
  REDIS_HOST=
  REDIS_PORT=
  REDIS_PASSWORD=
  
  # Swagger
  SWAGGER_USERNAME=swagger
  SWAGGER_PASSWORD=swagger123456
  SWAGGER_ROLE=swagger
  
  # JWT签名密钥，JWT密钥长度不能小于32字节
  JWT_SECRET=""
  ```
- 使用 FrameApplication.java 项目启动类进行启动
- 在测试包中使用 CodeGenerator 生成基础代码，生成前需要调整数据源连接信息
  - 注意：生成基础代码时，需要调整项目路径、调整数据库信息。
  - 注意：生成基础代码时，会覆盖项目中同名文件

### 部署启动
- IDEA启动
  - 在IDEA中编辑 'FrameApplication'，选中 'Enable EnvFile'
  - 再点击下方 + 号，添加项目下的 .env 文件
  - Apply 保存即可
- jar包启动
  - 使用Maven工具打包，会在项目下的 target 文件夹下生成 jar 包
  - 上传 jar 包、.env文件、application-prod.yml 到指定位置
  - 创建下面的sh文件，并执行即可启动项目
    ```shell
    ENV_FILE=.env
    
    set -a
    source "$ENV_FILE"
    set +a
    
    nohup java -jar xinyuow-frame.jar --spring.profiles.active=prod > xinyuow-frame.log 2>&1 &
    ```

### 项目结构简介
````
xinyuow-frame
├── src  
│    ├── main   
│    │    ├── java  
│    │    │     └── com.xinyuow.frame  
│    │    │             ├── app                         // APP端业务相关模块 
│    │    │             │     ├── controller            // 业务控制模块 
│    │    │             │     ├── entity                // 业务实体类模块 
│    │    │             │     ├── mapper                // 业务Mapper接口模块 
│    │    │             │     ├── service               // 业务处理模块 
│    │    │             │     └── vo                    // VO类模块 
│    │    │             ├── common                      // 公共模块  
│    │    │             │     ├── component             // 组件模块
│    │    │             │     ├── config                // 配置模块   
│    │    │             │     ├── controller            // 基础控制类模块  
│    │    │             │     ├── enums                 // 枚举类模块  
│    │    │             │     ├── exception             // 异常配置模块  
│    │    │             │     ├── model                 // 公共模型类模块
│    │    │             │     ├── properties            // 配置类模块 
│    │    │             │     ├── security              // 安全模块  
│    │    │             │     └── validator             // 校验模块
│    │    │             ├── web                         // WEB端业务相关模块 
│    │    │             │     ├── controller            // 业务控制模块 
│    │    │             │     ├── entity                // 业务实体类模块 
│    │    │             │     ├── mapper                // 业务Mapper接口模块 
│    │    │             │     ├── service               // 业务处理模块 
│    │    │             │     └── vo                    // VO类模块 
│    │    │             └── FrameApplication.java       // 项目启动类  
│    │    └── resources  
│    │          ├── db                                  // 数据库脚本模块
|    |          │    └── migration                      // 数据迁移脚本模块  
│    │          ├── mapper                              // *Mapper.xml模块
|    |          │    ├── app                            // APP端Mapper.xml模块  
|    |          │    └── web                            // Web端Mapper.xml模块  
│    │          ├── static                              // 静态资源模块  
│    │          ├── templates                           // Mybatis-Plus代码生成模板  
│    │          ├── application.yml                     // 项目配置基础信息  
│    │          ├── application-dev.yml                 // 项目配置测试环境信息  
│    │          ├── application-prod.yml                // 项目配置生产环境信息  
│    │          └── logback-spring.xml                  // 日志配置信息  
│    └── test
│         └── java  
│               └── com.xinyuow.frame  
│                       ├── mybatis
│                       │      └── CodeGenerator.java   // MyBatis-Plus代码生成器
│                       ├── tests                       // 项目单元测试模块
│                       └── FrameApplicationTests.java
├── .env  
├── .gitignore
├── LICENSE
├── pom.xml  
└── README.md   
````

### 注意事项
1.Java环境不是Java17，jar包启动时，使用下面的shell文件
```shell
ENV_FILE=.env
# 替换成 Java17 的路径
JAVA_BIN=/usr/local/Cellar/openjdk@17/17.0.20/libexec/openjdk.jdk/Contents/Home/bin/java

set -a
source "$ENV_FILE"
set +a

nohup "$JAVA_BIN" -jar xinyuow-frame.jar --spring.profiles.active=prod > xinyuow-frame.log 2>&1 &
```