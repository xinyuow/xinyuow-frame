-- 用户表
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `login_name` varchar(50) DEFAULT NULL COMMENT '登录名称',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `login_pwd` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `status` int DEFAULT NULL COMMENT '用户状态 0:启动   1:禁用',
  `login_fail_cnt` int DEFAULT '0' COMMENT '登录失败次数',
  `lock_flag` tinyint DEFAULT NULL COMMENT '是否锁定   1:是   0:否',
  `locked_date` datetime DEFAULT NULL COMMENT '锁定时间',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint NOT NULL COMMENT '是否删除   1:是   0:否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 初始化用户表的基础信息
INSERT INTO `s_user` VALUES ('1', 'superadmin', '超级管理员', 'e10adc3949ba59abbe56e057f20f883e', '0', '0', '0', '2019-12-18 10:10:57', '2019-12-15 21:59:41', '2021-04-28 17:08:52', '0'), ('2', 'xinyuow', '普通人员', 'e10adc3949ba59abbe56e057f20f883e', '0', '0', '0', '2019-12-19 19:03:23', '2019-12-19 13:51:24', '2021-04-28 11:32:59', '0');


-- 角色表
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(50) DEFAULT NULL COMMENT '角色编码',
  `status` int DEFAULT NULL COMMENT '角色状态 0:启动   1:禁用',
  `role_type` int DEFAULT NULL COMMENT '角色类型 0:系统角色  1:普通角色',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 初始化角色表的基础信息
INSERT INTO `s_role` VALUES ('1', '超级管理员角色', 'admin_role', '0', '1', '2019-12-16 16:52:46', '2019-12-16 16:52:48'), ('2', '普通用户角色', 'user_role', '0', '0', '2019-12-19 13:52:08', '2019-12-19 13:52:10');


-- 菜单表
DROP TABLE IF EXISTS `s_menu`;
CREATE TABLE `s_menu` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_id` bigint DEFAULT NULL COMMENT '父级菜单ID',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '权限路径',
  `perms` varchar(200) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int DEFAULT NULL COMMENT '类型 0：目录 1：菜单',
  `icon` varchar(200) DEFAULT NULL COMMENT '菜单图标',
  `sort` int DEFAULT NULL COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '菜单备注',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- 初始化菜单表的基础信息
INSERT INTO `s_menu` VALUES ('1', null, '用户管理', '', null, '0', null, '1', '用户管理模块', '2019-12-16 16:54:44', '2019-12-16 16:54:46'), ('2', '1', '用户管理列表', 'user:list', 'user:list', '1', null, '1', '用户列表', '2019-12-19 13:53:14', '2019-12-19 13:53:16'), ('3', '1', '用户管理获取用户', 'user:getUser', 'user:getUser', '1', null, '2', '获取单个用户信息', '2019-12-19 13:54:09', '2019-12-19 13:54:11');


-- 用户角色关系表
DROP TABLE IF EXISTS `s_user_role`;
CREATE TABLE `s_user_role` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- 初始化用户角色表的基础信息
INSERT INTO `s_user_role` VALUES ('1', '1', '1', '2019-12-16 16:54:10', '2019-12-16 16:54:12'), ('2', '2', '2', '2019-12-19 13:52:27', '2019-12-19 13:52:29');


-- 角色菜单表
DROP TABLE IF EXISTS `s_role_menu`;
CREATE TABLE `s_role_menu` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint DEFAULT NULL COMMENT '菜单ID',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

-- 初始化角色菜单表的基础信息
INSERT INTO `s_role_menu` VALUES ('1', '1', '1', '2019-12-16 17:00:26', '2019-12-16 17:00:28'), ('2', '1', '2', '2019-12-19 13:55:02', '2019-12-19 13:55:04'), ('3', '1', '3', '2019-12-19 13:55:13', '2019-12-19 13:55:14'), ('4', '2', '2', '2019-12-19 13:55:29', '2019-12-19 13:55:31');