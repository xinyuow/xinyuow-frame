package com.xinyuow.frame.controller.core;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinyuow.frame.common.controller.BaseController;
import com.xinyuow.frame.model.core.User;
import com.xinyuow.frame.service.core.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 用户操作控制器
 *
 * @author mxy
 * @date 2021/4/28
 */
@Slf4j
@Api(tags = {"用户操作接口"})
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {
    private static final long serialVersionUID = 2215735897501500140L;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    /**
     * 获取用户列表
     *
     * @param userName 用户名称 - 查询条件
     * @return 操作结果
     */
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "Authorization", value = "token认证", dataType = "String", paramType = "header", required = true)
    })
    @GetMapping("/getList")
    @RequiresPermissions({"user:list"})
    public Map<String, Object> getUserList(String userName) {
        log.info("\r\n ***** get user list, operator={}, data={}", getCurrentUserId(), userName);
        // 查询用户列表
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getDelFlag, Boolean.FALSE);
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.lambda().like(User::getUserName, userName);
        }
        queryWrapper.lambda().orderByDesc(User::getCreateDate);
        return getResult(userService.list(queryWrapper));
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 操作结果
     */
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户ID", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "Authorization", value = "token认证", dataType = "String", paramType = "header", required = true)
    })
    @GetMapping("/{uid}")
    @RequiresPermissions({"user:getUser"})
    public Map<String, Object> getById(@PathVariable("uid") Long userId) {
        log.info("\r\n ***** get user by id, operator={}, data={}", getCurrentUserId(), userId);
        return getResult(userService.getById(userId));
    }

}
