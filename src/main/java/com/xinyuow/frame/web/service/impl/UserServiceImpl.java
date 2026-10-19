/*
 * UserServiceImpl.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.xinyuow.frame.common.enums.DelFlagEnum;
import com.xinyuow.frame.web.entity.User;
import com.xinyuow.frame.web.mapper.UserMapper;
import com.xinyuow.frame.web.service.UserService;
import com.xinyuow.frame.web.vo.res.UserResVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户表 业务实现类
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    /**
     * 分页查询用户列表
     *
     * @param pageNum  页码，从1开始。默认为起始页(第一页)
     * @param pageSize 每页显示数，默认为10
     * @param username 查询条件 - 用户名称
     * @return 分页查询结果
     */
    @Override
    public Page<UserResVO> list(Integer pageNum, Integer pageSize, String username) {
        return userMapper.getPageList(new Page<>(pageNum, pageSize), username);
    }

    /**
     * 查询指定用户详情
     *
     * @param id 用户ID
     * @return 用户信息响应对象
     */
    @Override
    public UserResVO info(Long id) {
        User user = userMapper.selectOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getId, id)
                        .eq(User::getDelFlag, DelFlagEnum.FALSE.getValue())
        );
        if (null == user) {
            return null;
        }
        return BeanUtil.copyProperties(user, UserResVO.class);
    }
}
