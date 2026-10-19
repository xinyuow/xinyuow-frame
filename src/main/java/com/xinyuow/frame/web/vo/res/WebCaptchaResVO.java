/*
 * WebCaptchaResVO.java
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
 * Web端获取图片验证码信息响应VO类
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "Web端获取图片验证码信息的响应参数")
public class WebCaptchaResVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5586067146367031031L;

    @Schema(description = "验证码唯一标识")
    private String captchaKey;

    @Schema(description = "验证码图片，Base64格式")
    private String captchaImage;
}
