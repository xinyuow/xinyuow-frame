/*
 * WebCaptchaComponent.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.common.component;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.StrUtil;
import com.xinyuow.frame.common.properties.WebCaptchaProperties;
import com.xinyuow.frame.web.dto.WebCaptchaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.UUID;

/**
 * Web端验证码组件
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebCaptchaComponent {

    private final RedisComponent redisComponent;
    private final WebCaptchaProperties webCaptchaProperties;

    /**
     * 生成验证码
     *
     * @return 验证码DTO对象
     */
    public WebCaptchaDTO create() {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(
                webCaptchaProperties.getWidth(),
                webCaptchaProperties.getHeight(),
                webCaptchaProperties.getCodeCount(),
                webCaptchaProperties.getLineCount()
        );

        // 生成验证码
        String captchaKey = UUID.randomUUID().toString();
        String captchaCode = captcha.getCode().toUpperCase(Locale.ROOT);

        // 保存验证码到Redis
        redisComponent.set(String.format(webCaptchaProperties.getRedisKey(), captchaKey),
                captchaCode, webCaptchaProperties.getRedisExpiration());

        return WebCaptchaDTO.builder()
                .captchaKey(captchaKey)
                .captchaImage(captcha.getImageBase64Data())
                .build();
    }

    /**
     * 校验并消费验证码：一次验证码只能使用一次
     *
     * @param captchaKey  验证码的唯一标识
     * @param captchaCode 验证码字符
     * @return 是否校验通过
     */
    public boolean verify(String captchaKey, String captchaCode) {
        if (StrUtil.isBlank(captchaKey) || StrUtil.isBlank(captchaCode)) {
            return false;
        }

        // 一次性读取并删除
        String cachedCode = redisComponent.getAndDelete(String.format(webCaptchaProperties.getRedisKey(), captchaKey));
        if (StrUtil.isBlank(cachedCode)) {
            return false;
        }
        // 忽略大小写比较
        return StrUtil.equalsIgnoreCase(cachedCode, captchaCode.trim());
    }
}
