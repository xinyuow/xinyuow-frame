/*
 * UserServiceImpl.java
 * Copyright(C) xinyuow
 * All rights reserved.
 * -----------------------------------------------
 * 2021-04-21 Created by mxy
 */
package com.xinyuow.frame.service.core.impl;

import com.xinyuow.frame.model.core.User;
import com.xinyuow.frame.service.core.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyuow.frame.mapper.core.UserMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户表 业务实现类
 *
 * @author mxy
 * @date 2021-04-21
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
