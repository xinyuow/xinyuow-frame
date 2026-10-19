/*
 * Menu.java
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
 * 菜单表 实体类
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("s_menu")
@Schema(description = "菜单表")
public class Menu implements Serializable {
    @Serial
    private static final long serialVersionUID = 4373137987996432726L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "父级菜单ID")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "菜单名称")
    @TableField("name")
    private String name;

    @Schema(description = "前端菜单路由")
    @TableField("path")
    private String path;

    @Schema(description = "授权编码; web:user:list")
    @TableField("code")
    private String code;

    @Schema(description = "类型;0:菜单 1:接口")
    @TableField("type")
    private Integer type;

    @Schema(description = "菜单图标")
    @TableField("icon")
    private String icon;

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;

    @Schema(description = "创建时间")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @Schema(description = "修改时间")
    @TableField(value = "modify_date", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyDate;
}
