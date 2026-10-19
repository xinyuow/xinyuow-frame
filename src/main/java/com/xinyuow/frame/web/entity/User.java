/*
 * User.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.web.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户表 实体类
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("s_user")
@Schema(description = "用户表")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 7896405527543127360L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "登录名称")
    @TableField("login_name")
    private String loginName;

    @Schema(description = "登录密码")
    @TableField("login_password")
    private String loginPassword;

    @Schema(description = "用户名称")
    @TableField("username")
    private String username;

    @Schema(description = "用户状态;0:启动 1:禁用")
    @TableField("status")
    private Integer status;

    @Schema(description = "登录失败次数")
    @TableField("login_fail_count")
    private Integer loginFailCount;

    @Schema(description = "是否锁定;0:否 1:是")
    @TableField("lock_flag")
    private Boolean lockFlag;

    @Schema(description = "锁定时间")
    @TableField("locked_date")
    private LocalDateTime lockedDate;

    @Schema(description = "创建时间")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @Schema(description = "修改时间")
    @TableField(value = "modify_date", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyDate;

    @Schema(description = "是否删除;0:否 1:是")
    @TableField("del_flag")
    private Boolean delFlag;
}
