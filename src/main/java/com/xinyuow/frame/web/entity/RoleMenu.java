/*
 * RoleMenu.java
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
 * 角色菜单表 实体类
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("s_role_menu")
@Schema(description = "角色菜单表")
public class RoleMenu implements Serializable {
    @Serial
    private static final long serialVersionUID = -4244920903976785268L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "角色ID")
    @TableField("role_id")
    private Long roleId;

    @Schema(description = "菜单ID")
    @TableField("menu_id")
    private Long menuId;

    @Schema(description = "创建时间")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @Schema(description = "修改时间")
    @TableField(value = "modify_date", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyDate;
}
