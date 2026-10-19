/*
 * LoginServiceImpl.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.web.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.xinyuow.frame.common.component.WebCaptchaComponent;
import com.xinyuow.frame.common.enums.DelFlagEnum;
import com.xinyuow.frame.common.enums.StatusEnum;
import com.xinyuow.frame.common.exception.InterfaceException;
import com.xinyuow.frame.common.exception.ResponseCodeEnum;
import com.xinyuow.frame.common.properties.WebUserProperties;
import com.xinyuow.frame.web.dto.WebCaptchaDTO;
import com.xinyuow.frame.web.entity.User;
import com.xinyuow.frame.web.mapper.UserMapper;
import com.xinyuow.frame.web.service.LoginService;
import com.xinyuow.frame.web.vo.res.WebCaptchaResVO;
import com.xinyuow.frame.web.vo.res.WebLoginResVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Web端登录业务实现类
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements LoginService {

    private final UserMapper userMapper;
    private final WebUserProperties webUserProperties;
    private final WebCaptchaComponent webCaptchaComponent;
    private final PasswordEncoder passwordEncoder;

    /**
     * 获取验证码
     *
     * @return 验证码VO对象
     */
    @Override
    public WebCaptchaResVO getCaptcha() {
        WebCaptchaDTO captchaDTO = webCaptchaComponent.create();
        return WebCaptchaResVO.builder()
                .captchaKey(captchaDTO.getCaptchaKey())
                .captchaImage(captchaDTO.getCaptchaImage())
                .build();
    }

    /**
     * 用户登录
     *
     * @param loginName   登录账号
     * @param password    登录密码
     * @param captchaKey  验证码标识
     * @param captchaCode 用户输入的验证码
     * @return 登录结果VO对象
     */
    @Override
    public WebLoginResVO login(String loginName, String password, String captchaKey, String captchaCode) {
        if (StrUtil.isBlank(loginName)) {
            throw new InterfaceException(ResponseCodeEnum.LOGIN_PWD_INCORRECT);
        }

        // 判断验证码
        if (!webCaptchaComponent.verify(captchaKey, captchaCode)){
            throw new InterfaceException(ResponseCodeEnum.CAPTCHA_INCORRECT);
        }

        // 获取登录用户信息
        User user = userMapper.selectOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getLoginName, loginName)
                        .eq(User::getDelFlag, DelFlagEnum.FALSE.getValue())
        );
        if (null == user) {
            throw new InterfaceException(ResponseCodeEnum.LOGIN_PWD_INCORRECT);
        }

        // 判断是否可用
        if (StatusEnum.DISABLE.getValue().equals(user.getStatus())) {
            throw new InterfaceException(ResponseCodeEnum.ACCOUNT_HAS_BEEN_DISABLED);
        }

        // 判断是否锁定
        if (Boolean.TRUE.equals(user.getLockFlag())) {
            // 判断锁定时间是否已过
            if (LocalDateTime.now().isAfter(user.getLockedDate().plus(webUserProperties.getLockTime()))) {
                // 更新锁定信息
                user.setLoginFailCount(webUserProperties.getInitFailCount());
                user.setLockFlag(Boolean.FALSE);
                user.setLockedDate(null);
                userMapper.updateById(user);
            } else {
                throw new InterfaceException(ResponseCodeEnum.ACCOUNT_IS_LOCKED);
            }
        }

        // 判断密码是否正确
        if (!passwordEncoder.matches(password, user.getLoginPassword())) {
            // 设置失败次数+1
            int loginFailCount = user.getLoginFailCount() + 1;
            // 如果失败次数大于等于指定次数，则锁定账户。规定时间后登录会自动解除锁定。
            if (loginFailCount >= webUserProperties.getMaxFailCount()) {
                user.setLockFlag(Boolean.TRUE);
                user.setLockedDate(LocalDateTime.now());
            }
            user.setLoginFailCount(loginFailCount);
            userMapper.updateById(user);
            throw new InterfaceException(ResponseCodeEnum.LOGIN_PWD_INCORRECT);
        }

        // 更新用户信息，并清空之前失败的次数
        user.setLoginFailCount(webUserProperties.getInitFailCount());
        userMapper.updateById(user);

        // TODO：权限信息
        // TODO：签发JWT
        // TODO：返回登录结果结果
        return null;
    }
}
