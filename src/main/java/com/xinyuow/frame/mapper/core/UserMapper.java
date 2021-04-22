/*
 * UserMapper.java
 * Copyright(C) xinyuow
 * All rights reserved.
 * -----------------------------------------------
 * 2021-04-21 Created by mxy
 */
package com.xinyuow.frame.mapper.core;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyuow.frame.model.core.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表 Mapper接口
 *
 * @author mxy
 * @date 2021-04-21
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据登录名称查询用户
     *
     * @param loginName 登录名称
     * @return 用户对象
     */
    User getByLoginName(@Param("loginName") String loginName);

    /**
     * 查询用户并级联查询用户角色
     *
     * @param userId 用户ID
     * @return 用户对象
     */
    User getUserCascadeRole(@Param("userId") Long userId);

}
