package com.xinyuow.frame.controller.core;

import com.xinyuow.frame.common.controller.BaseController;
import com.xinyuow.frame.common.exception.InterfaceException;
import com.xinyuow.frame.common.exception.RESPONSE_CODE_ENUM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author mxy
 * @date 2021/4/26
 */
@Api(tags = {"登录操作接口"})
@RestController
@RequestMapping("/api")
public class LoginController extends BaseController {
    private static final long serialVersionUID = 5384395022621567777L;

    /**
     * 登录接口
     *
     * @param loginName 登录名称
     * @param password  登录密码
     * @return 操作结果
     */
    @ApiOperation(value = "用户登录", notes = "用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "登录名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "登录密码", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/login")
    public Map<String, Object> login(@RequestParam(value = "username") String loginName, String password) {
        // 参数校验
        if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
            throw new InterfaceException(RESPONSE_CODE_ENUM.REQUEST_PARAMS_ERROR);
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
            // 调用doGetAuthenticationInfo
            subject.login(token);

            Map<String, Object> map = new HashMap<>();
            map.put("username", loginName);
            map.put("token", subject.getSession().getId());
            return getResult(map);
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            // 账号不存在或密码错误
            return getFailResult(RESPONSE_CODE_ENUM.LOGIN_PWD_INCORRECT);
        } catch (LockedAccountException e) {
            // 账号被锁定
            return getFailResult(RESPONSE_CODE_ENUM.ACCOUNT_IS_LOCKED);
        } catch (DisabledAccountException e) {
            // 账号被禁用
            return getFailResult(RESPONSE_CODE_ENUM.ACCOUNT_HAS_BEEN_DISABLED);
        } catch (AuthenticationException e) {
            // 认证失败
            return getFailResult(RESPONSE_CODE_ENUM.ACCOUNT_AUTHENTICATION_FAILED);
        }
    }

    /**
     * 退出登录
     *
     * @return 操作结果
     */
    @ApiOperation(value = "用户登出", notes = "退出登录接口")
    @GetMapping(value = "/logout")
    public Map<String, Object> logout() {
        // 注销登录
        SecurityUtils.getSubject().logout();
        return getSuccessResult();
    }

    /**
     * 未登录
     *
     * @return 操作结果
     */
    @ApiIgnore()
    @GetMapping(value = "/un_auth")
    public Map<String, Object> unAuth() {
        return getFailResult(RESPONSE_CODE_ENUM.NOT_LOGIN_ERROR);
    }
}
