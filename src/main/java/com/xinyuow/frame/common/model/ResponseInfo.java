/*
 * ResponseInfo.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 接口响应VO类
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "接口响应实体类")
public class ResponseInfo<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -3012997048071229435L;

    @Schema(description = "响应状态码")
    private Integer code;

    @Schema(description = "响应信息")
    private String msg;

    @Schema(description = "响应时间戳")
    private Long timestamp;

    @Schema(description = "响应传输信息")
    private T body;
}