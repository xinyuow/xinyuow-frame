/*
 * Role.java
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

/**
 * 角色表 实体类
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("s_role")
@Schema(description = "角色表")
public class Role implements Serializable {
    @Serial
    private static final long serialVersionUID = 1840816750098604737L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "角色名称")
    @TableField("role_name")
    private String roleName;

    @Schema(description = "角色编码")
    @TableField("role_code")
    private String roleCode;

    @Schema(description = "角色状态;0:启动 1:禁用")
    @TableField("status")
    private Integer status;

    @Schema(description = "角色类型;0:系统角色 1:普通角色")
    @TableField("role_type")
    private Integer roleType;

    @Schema(description = "创建时间")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @Schema(description = "修改时间")
    @TableField(value = "modify_date", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyDate;
}
