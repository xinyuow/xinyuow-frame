/*
 * WebLoginMenuResVO.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.web.vo.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * Web端用户登录菜单列表响应VO类
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "Web端用户登录菜单列表的响应参数")
public class WebLoginMenuResVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4677640273776146264L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "父级菜单ID")
    private Long parentId;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "前端菜单路由")
    private String path;

    @Schema(description = "类型;0:菜单")
    private Integer type;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "排序")
    private Integer sort;
}
