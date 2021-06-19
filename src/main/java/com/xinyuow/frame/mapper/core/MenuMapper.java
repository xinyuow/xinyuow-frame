/*
 * MenuMapper.java
 * Copyright(C) xinyuow
 * All rights reserved.
 * -----------------------------------------------
 * 2021-04-21 Created by mxy
 */
package com.xinyuow.frame.mapper.core;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyuow.frame.model.core.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单表 Mapper接口
 *
 * @author mxy
 * @date 2021-04-21
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过用户ID查询对应的菜单权限集合
     *
     * @param userId 用户ID
     * @return 菜单权限集合
     */
    List<Menu> findOrdinaryMenu(@Param("userId") Long userId);

}
