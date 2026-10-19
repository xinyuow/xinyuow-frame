/*
 * UserService.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.IService;
import com.xinyuow.frame.web.entity.User;
import com.xinyuow.frame.web.vo.res.UserResVO;

/**
 * 用户表 业务接口类
 *
 * @author xinyuow
 * @date 2026-10-17
 */
public interface UserService extends IService<User> {

    /**
     * 分页查询用户列表
     *
     * @param pageNum  页码，从1开始。默认为起始页(第一页)
     * @param pageSize 每页显示数，默认为10
     * @param username 查询条件 - 用户名称
     * @return 分页查询结果
     */
    Page<UserResVO> list(Integer pageNum, Integer pageSize, String username);

    /**
     * 查询指定用户详情
     *
     * @param id 用户ID
     * @return 用户信息响应对象
     */
    UserResVO info(Long id);
}
