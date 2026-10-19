/*
 * WebCaptchaDTO.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * Web端验证码DTO类
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WebCaptchaDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 9151229317698795767L;

    /**
     * 验证码的唯一标识，用于后续验证验证码
     */
    private String captchaKey;

    /**
     * 验证码图片，Base64格式
     */
    private String captchaImage;
}
