/*
 * PageReqVO.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 分页查询请求基类
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "分页查询请求参数")
public class PageReqVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8199645697771347900L;

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码必须大于0")
    @Schema(name = "页码", description = "从1开始，默认为起始页(第一页)", example = "1")
    private Integer pageNum = 1;

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "每页条数必须大于0")
    @Max(value = 100, message = "每页最多查询100条")
    @Schema(name = "每页条数", description = "默认为10", example = "10")
    private Integer pageSize = 10;
}
