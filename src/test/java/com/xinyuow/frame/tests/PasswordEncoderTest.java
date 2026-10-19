/*
 * PasswordEncoderTest.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.tests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 密码编码器测试类
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Slf4j
public class PasswordEncoderTest {

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    void testPasswordEncodeAndMatches() {
        // 模拟用户设置的明文密码
        String rawPassword = "123456";

        // 生成编码后的密码
        String encodedPassword = passwordEncoder.encode(rawPassword);
        log.info("\r\n Encoded Password: {}", encodedPassword);

        // 验证编码结果
        assertNotNull(encodedPassword);
        assertTrue(encodedPassword.startsWith("{bcrypt}"));

        // 验证正确密码
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));

        // 验证错误密码
        assertFalse(passwordEncoder.matches("654321", encodedPassword));
    }

    @Test
    void shouldGenerateDifferentHashesForSamePassword() {
        String password = "123456";
        String encodedPassword1 = passwordEncoder.encode(password);
        String encodedPassword2 = passwordEncoder.encode(password);

        // 随机盐使两次编码结果不同
        assertNotEquals(encodedPassword1, encodedPassword2);

        // 两个编码结果都应能验证原始密码
        assertTrue(passwordEncoder.matches(password, encodedPassword1));
        assertTrue(passwordEncoder.matches(password, encodedPassword2));
    }
}
