/*
 * RoleServiceImpl.java
 * Copyright(C) xinyuow
 * All rights reserved.
 * -----------------------------------------------
 * 2021-04-21 Created by mxy
 */
package com.xinyuow.frame.service.core.impl;

import com.xinyuow.frame.model.core.Role;
import com.xinyuow.frame.service.core.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyuow.frame.mapper.core.RoleMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 角色表 业务实现类
 *
 * @author mxy
 * @date 2021-04-21
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
