/*
 * MenuMapper.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyuow.frame.web.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单表 Mapper接口
 *
 * @author xinyuow
 * @date 2026-10-17
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
