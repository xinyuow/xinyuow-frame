-- 用户表
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user`
(
    `id`               bigint   NOT NULL COMMENT '主键ID',
    `login_name`       varchar(50) DEFAULT NULL COMMENT '登录名称',
    `login_password`   varchar(50) DEFAULT NULL COMMENT '登录密码',
    `username`         varchar(50) DEFAULT NULL COMMENT '用户名称',
    `status`           int         DEFAULT '0' COMMENT '用户状态;0:启动 1:禁用',
    `login_fail_count` int         DEFAULT '0' COMMENT '登录失败次数',
    `lock_flag`        tinyint     DEFAULT NULL COMMENT '是否锁定;0:否 1:是',
    `locked_date`      datetime    DEFAULT NULL COMMENT '锁定时间',
    `create_date`      datetime NOT NULL COMMENT '创建时间',
    `modify_date`      datetime    DEFAULT NULL COMMENT '修改时间',
    `del_flag`         tinyint  NOT NULL COMMENT '是否删除;0:否 1:是',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 初始化用户表的基础信息
INSERT INTO `s_user`
VALUES ('1', 'superadmin', '超级管理员', 'e10adc3949ba59abbe56e057f20f883e', '0', '0', '0', '2026-10-19 17:21:00',
        '2026-10-19 17:21:00', '2026-10-19 17:21:00', '0'),
       ('2', 'xinyuow', '普通人员', 'e10adc3949ba59abbe56e057f20f883e', '0', '0', '0', '2026-10-19 17:21:00',
        '2026-10-19 17:21:00', '2026-10-19 17:21:00', '0');


-- 角色表
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role`
(
    `id`          bigint   NOT NULL COMMENT '主键ID',
    `role_name`   varchar(50) DEFAULT NULL COMMENT '角色名称',
    `role_code`   varchar(50) DEFAULT NULL COMMENT '角色编码',
    `status`      int         DEFAULT NULL COMMENT '角色状态;0:启动 1:禁用',
    `role_type`   int         DEFAULT NULL COMMENT '角色类型;0:系统角色 1:普通角色',
    `create_date` datetime NOT NULL COMMENT '创建时间',
    `modify_date` datetime    DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 初始化角色表的基础信息
INSERT INTO `s_role`
VALUES ('1', '管理员角色', 'admin_role', '0', '0', '2026-10-19 17:21:00', '2026-10-19 17:21:00'),
       ('2', '普通用户角色', 'user_role', '0', '1', '2026-10-19 17:21:00', '2026-10-19 17:21:00');


-- 菜单表
DROP TABLE IF EXISTS `s_menu`;
CREATE TABLE `s_menu`
(
    `id`          bigint   NOT NULL COMMENT '主键ID',
    `parent_id`   bigint       DEFAULT NULL COMMENT '父级菜单ID',
    `name`        varchar(50)  DEFAULT NULL COMMENT '菜单名称',
    `path`        varchar(200) DEFAULT NULL COMMENT '前端菜单路由',
    `code`        varchar(200) DEFAULT NULL COMMENT '授权编码; web:user:list',
    `type`        int          DEFAULT NULL COMMENT '类型;0:菜单 1:接口',
    `icon`        varchar(200) DEFAULT NULL COMMENT '菜单图标',
    `sort`        int          DEFAULT NULL COMMENT '排序',
    `remark`      varchar(200) DEFAULT NULL COMMENT '备注',
    `create_date` datetime NOT NULL COMMENT '创建时间',
    `modify_date` datetime     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- 初始化菜单表的基础信息
INSERT INTO `s_menu`
VALUES ('1', NULL, '用户管理', NULL, NULL, '0', NULL, '1', '用户管理模块', '2026-10-19 17:21:00',
        '2026-10-19 17:21:00'),
       ('2', '1', '用户管理列表', NULL, 'frame:web:user:list', '1', NULL, '1', '用户列表', '2026-10-19 17:21:00',
        '2026-10-19 17:21:00'),
       ('3', '1', '用户管理获取用户', NULL, 'frame:web:user:info', '1', NULL, '2', '获取单个用户信息',
        '2026-10-19 17:21:00', '2026-10-19 17:21:00');


-- 用户角色关系表
DROP TABLE IF EXISTS `s_user_role`;
CREATE TABLE `s_user_role`
(
    `id`          bigint   NOT NULL COMMENT '主键ID',
    `user_id`     bigint   DEFAULT NULL COMMENT '用户ID',
    `role_id`     bigint   DEFAULT NULL COMMENT '角色ID',
    `create_date` datetime NOT NULL COMMENT '创建时间',
    `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- 初始化用户角色表的基础信息
INSERT INTO `s_user_role`
VALUES ('1', '1', '1', '2026-10-19 17:21:00', '2026-10-19 17:21:00'),
       ('2', '2', '2', '2026-10-19 17:21:00', '2026-10-19 17:21:00');


-- 角色菜单表
DROP TABLE IF EXISTS `s_role_menu`;
CREATE TABLE `s_role_menu`
(
    `id`          bigint   NOT NULL COMMENT '主键ID',
    `role_id`     bigint   DEFAULT NULL COMMENT '角色ID',
    `menu_id`     bigint   DEFAULT NULL COMMENT '菜单ID',
    `create_date` datetime NOT NULL COMMENT '创建时间',
    `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

-- 初始化角色菜单表的基础信息
INSERT INTO `s_role_menu`
VALUES ('1', '1', '1', '2026-10-19 17:21:00', '2026-10-19 17:21:00'),
       ('2', '1', '2', '2026-10-19 17:21:00', '2026-10-19 17:21:00'),
       ('3', '1', '3', '2026-10-19 17:21:00', '2026-10-19 17:21:00'),
       ('4', '2', '1', '2026-10-19 17:21:00', '2026-10-19 17:21:00'),
       ('5', '2', '2', '2026-10-19 17:21:00', '2026-10-19 17:21:00');
