/*
 * Role.java
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
 * 角色表 实体类
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
@TableName("s_role")
@ApiModel(value = "Role对象", description = "角色表")
public class Role implements Serializable {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("角色名称")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty("角色编码")
    @TableField("role_code")
    private String roleCode;

    @ApiModelProperty("角色状态 0:启动   1:禁用")
    @TableField("status")
    private String status;

    @ApiModelProperty("是否系统角色-系统角色不允许删除    1:是   0:否")
    @TableField("is_sys")
    private Integer isSys;

    @ApiModelProperty("创建时间")
    @TableField("create_date")
    private LocalDateTime createDate;

    @ApiModelProperty("修改时间")
    @TableField("modify_date")
    private LocalDateTime modifyDate;

}
