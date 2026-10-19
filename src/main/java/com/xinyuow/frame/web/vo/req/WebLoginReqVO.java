/*
 * WebLoginReqVO.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.web.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * Web端登录信息请求VO类
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "Web端登录信息的请求参数")
public class WebLoginReqVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 7654011769779728014L;

    @NotBlank(message = "登录账号不能为空")
    @Schema(description = "登录账号")
    private String loginName;

    @NotBlank(message = "登录密码不能为空")
    @Schema(description = "登录密码")
    private String password;

    @NotBlank
    @Schema(description = "验证码标识")
    private String captchaKey;

    @NotBlank
    @Schema(description = "用户输入的验证码", example = "1234")
    private String captchaCode;
}
