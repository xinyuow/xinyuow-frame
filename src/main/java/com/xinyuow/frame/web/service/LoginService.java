/*
 * LoginService.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.web.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.xinyuow.frame.web.entity.User;
import com.xinyuow.frame.web.vo.res.WebCaptchaResVO;
import com.xinyuow.frame.web.vo.res.WebLoginResVO;

/**
 * Web端登录业务接口类
 *
 * @author xinyuow
 * @date 2026-10-19
 */
public interface LoginService extends IService<User> {

    /**
     * 获取验证码
     *
     * @return 验证码VO对象
     */
    WebCaptchaResVO getCaptcha();

    /**
     * 用户登录
     *
     * @param loginName   登录账号
     * @param password    登录密码
     * @param captchaKey  验证码标识
     * @param captchaCode 用户输入的验证码
     * @return 登录结果VO对象
     */
    WebLoginResVO login(String loginName, String password, String captchaKey, String captchaCode);

    /**
     * 退出登录
     *
     * @param userId 当前登录用户ID
     */
    void logout(Long userId);
}
