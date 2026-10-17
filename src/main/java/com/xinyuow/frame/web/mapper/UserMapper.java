/*
 * UserMapper.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyuow.frame.web.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表 Mapper接口
 *
 * @author xinyuow
 * @date 2026-10-17
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
