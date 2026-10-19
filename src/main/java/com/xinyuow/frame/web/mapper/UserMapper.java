/*
 * UserMapper.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyuow.frame.web.entity.User;
import com.xinyuow.frame.web.vo.res.UserResVO;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表 Mapper接口
 *
 * @author xinyuow
 * @date 2026-10-17
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询用户列表 - 分页
     *
     * @param page     分页对象
     * @param username 用户名称
     * @return 查询结果
     */
    Page<UserResVO> getPageList(Page<UserResVO> page, @Param("username") String username);
}
