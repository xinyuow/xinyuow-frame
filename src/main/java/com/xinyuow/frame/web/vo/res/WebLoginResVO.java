/*
 * WebLoginResVO.java
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
import java.util.List;

/**
 * Web端用户登录信息响应VO类
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "Web端用户登录信息的响应参数")
public class WebLoginResVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5586067146367031031L;

    @Schema(description = "登录凭证")
    private String token;

    @Schema(description = "Token类型")
    private String tokenType = "Bearer";

    @Schema(description = "Token有效期，单位秒")
    private Long expiresIn;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名称")
    private String username;

    @Schema(description = "菜单列表")
    private List<WebLoginMenuResVO> menuList;
}
