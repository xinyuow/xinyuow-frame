/*
 * MenuServiceImpl.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.web.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.xinyuow.frame.web.entity.Menu;
import com.xinyuow.frame.web.mapper.MenuMapper;
import com.xinyuow.frame.web.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 菜单表 业务实现类
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
