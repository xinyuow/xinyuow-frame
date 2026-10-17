/*
 * RoleServiceImpl.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.web.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.xinyuow.frame.web.entity.Role;
import com.xinyuow.frame.web.mapper.RoleMapper;
import com.xinyuow.frame.web.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 角色表 业务实现类
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
