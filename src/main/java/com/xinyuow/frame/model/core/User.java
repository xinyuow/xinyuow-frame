/*
 * User.java
 * Copyright(C) xinyuow
 * All rights reserved.
 * -----------------------------------------------
 * 2021-04-21 Created by mxy
 */
package com.xinyuow.frame.model.core;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户表 实体类
 *
 * @author mxy
 * @date 2021-04-21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("s_user")
@ApiModel(value = "User对象", description = "用户表")
public class User implements Serializable {
    private static final long serialVersionUID = 7896405527543127360L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("登录名称")
    @TableField("login_name")
    private String loginName;

    @ApiModelProperty("用户名称")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("登录密码")
    @TableField("login_pwd")
    private String loginPwd;

    @ApiModelProperty("用户状态 0:启动   1:禁用")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("登录失败次数")
    @TableField("login_fail_cnt")
    private Integer loginFailCnt;

    @ApiModelProperty("是否锁定   1:是   0:否")
    @TableField("lock_flag")
    private Boolean lockFlag;

    @ApiModelProperty("锁定时间")
    @TableField("locked_date")
    private LocalDateTime lockedDate;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @ApiModelProperty("修改时间")
    @TableField(value = "modify_date", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyDate;

    @ApiModelProperty("是否删除   1:是   0:否")
    @TableField("del_flag")
    private Boolean delFlag;

    /* ******************** 扩展属性 开始 **************************/

    @ApiModelProperty(name = "用户对应的角色信息集合 - 登录时查询使用", hidden = true)
    @TableField(exist = false)
    private List<Role> roles;

    /* ******************** 扩展属性 结束 **************************/

}
