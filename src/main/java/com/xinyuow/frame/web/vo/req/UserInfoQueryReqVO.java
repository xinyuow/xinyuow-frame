/*
 * UserInfoQueryReqVO.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.web.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户详情查询请求VO类
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "用户详情查询请求参数")
public class UserInfoQueryReqVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4905206649087048566L;

    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", example = "1")
    private Long userId;
}
