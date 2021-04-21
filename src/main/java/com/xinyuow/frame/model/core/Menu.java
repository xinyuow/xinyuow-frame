/*
 * Menu.java
 * Copyright(C) xinyuow
 * All rights reserved.
 * -----------------------------------------------
 * 2021-04-21 Created by mxy
 */
package com.xinyuow.frame.model.core;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 菜单表 实体类
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
@TableName("s_menu")
@ApiModel(value = "Menu对象", description = "菜单表")
public class Menu implements Serializable {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("父级菜单ID")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("菜单名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("权限路径")
    @TableField("url")
    private String url;

    @ApiModelProperty("授权(多个用逗号分隔，如：user:list,user:create)")
    @TableField("perms")
    private String perms;

    @ApiModelProperty("类型 0：目录 1：菜单")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("菜单图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("菜单备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("创建时间")
    @TableField("create_date")
    private LocalDateTime createDate;

    @ApiModelProperty("修改时间")
    @TableField("modify_date")
    private LocalDateTime modifyDate;

}
