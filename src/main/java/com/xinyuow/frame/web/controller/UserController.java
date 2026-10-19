/*
 * UserController.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyuow.frame.common.controller.WebBaseController;
import com.xinyuow.frame.common.model.ResponseInfo;
import com.xinyuow.frame.web.service.UserService;
import com.xinyuow.frame.web.vo.req.UserInfoQueryReqVO;
import com.xinyuow.frame.web.vo.req.UserListQueryReqVO;
import com.xinyuow.frame.web.vo.res.UserResVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serial;

/**
 * Web端用户控制器
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/web/v1/user")
@Tag(name = "Web端用户操作接口")
public class UserController extends WebBaseController {
    @Serial
    private static final long serialVersionUID = -4959616940232368282L;

    private final UserService userService;

    /**
     * 查询用户列表
     *
     * @param userQueryReq 用户列表查询对象
     * @return 操作结果
     */
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('frame:web:user:list')")
    @Operation(summary = "获取用户列表", description = "获取用户列表")
    public ResponseInfo<Page<UserResVO>> list(@Validated @RequestBody UserListQueryReqVO userQueryReq) {
        return getResult(userService.list(userQueryReq.getPageNum(),
                userQueryReq.getPageSize(), userQueryReq.getUsername()));
    }

    /**
     * 查询指定用户详情
     *
     * @param userInfoQueryReq 用户详情查询对象
     * @return 操作结果
     */
    @PostMapping("/info")
    @PreAuthorize("hasAuthority('frame:web:user:info')")
    @Operation(summary = "获取用户详情", description = "获取用户详情")
    public ResponseInfo<UserResVO> info(@Validated @RequestBody UserInfoQueryReqVO userInfoQueryReq) {
        return getResult(userService.info(userInfoQueryReq.getUserId()));
    }
}
