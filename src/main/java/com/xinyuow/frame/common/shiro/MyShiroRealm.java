//package com.xinyuow.frame.common.shiro;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.Permission;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.*;
//
///**
// * 自定义认证域
// *
// * @author mxy
// * @date 2021/4/21
// */
//@Component
//public class MyShiroRealm extends AuthorizingRealm {
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private MenuMapper menuMapper;
//
//    @Autowired
//    private MyShiroUtil myShiroUtil;
//
//    /**
//     * 登录认证
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        // 存放登录信息
//        UsernamePasswordToken authToken = (UsernamePasswordToken) authenticationToken;
//        // 获取登录名、密码
//        String loginName = authToken.getUsername();
//        String password = new String(authToken.getPassword());
//        // 判断登录信息
//        if (loginName != null) {
//            // 获取登录用户信息
//            User user = userMapper.getByLoginName(loginName);
//
//            // 判断用户是否存在
//            if (user == null || user.getDelFlag()) {
//                throw new UnknownAccountException();
//            }
//
//            // 判断是否可用
//            if (user.getStatus().equals(User.STATUS_ENUM.DISABLE.getValue())) {
//                throw new DisabledAccountException();
//            }
//
//            // 判断是否锁定
//            if (user.getLockFlag()) {
//                // 账号锁定分钟数
//                Date lockedDate = user.getLockedDate();
//                Date unlockedDate = DateUtils.addMinutes(lockedDate, 10);
//
//                // 判断锁定时间是否已过
//                if (new Date().after(unlockedDate)) {
//                    // 更新锁定信息
//                    user.setLoginFailCnt(0);
//                    user.setLockFlag(false);
//                    user.setLockedDate(null);
//                    user.setModifyDate(new Date());
//                    userMapper.update(user);
//                } else {
//                    throw new LockedAccountException();
//                }
//            }
//
//            // 密码错误
//            if (!DigestUtils.md5Hex(password).equals(user.getLoginPwd())) {
//                // 设置失败次数+1
//                int loginFailCount = user.getLoginFailCnt() + 1;
//                // 如果失败次数大于等于指定次数，则锁定账户。规定时间后登录会自动解除锁定。
//                if (loginFailCount >= 5) {
//                    user.setLockFlag(true);
//                    user.setLockedDate(new Date());
//                }
//                user.setLoginFailCnt(loginFailCount);
//                user.setModifyDate(new Date());
//                userMapper.update(user);
//                throw new IncorrectCredentialsException();
//            }
//
//            // 更新用户信息，并清空之前失败的次数
//            user.setLoginFailCnt(0);
//            user.setModifyDate(new Date());
//            userMapper.update(user);
//
//            /*
//                更新权限缓存。单点登录。需要设置isRemoveSession为true。
//                目前已设置的位置：新增/编辑用户、删除用户和保存权限控制等四处。
//             */
//            myShiroUtil.deleteCache(loginName, false);
//            return new SimpleAuthenticationInfo(new Principal(user.getId(), loginName), password, getName());
//        } else {
//            // 未知用户，认证失败
//            throw new AuthenticationException();
//        }
//    }
//
//    /**
//     * 权限认证
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        Principal loginUser = (Principal) principalCollection.getPrimaryPrincipal();
//        if (loginUser == null) {
//            throw new UnknownAccountException("用户不存在");
//        } else {
//            // 使用SimpleAuthorizationInfo做授权
//            SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
//
//            // 获取User对象，并级联查询对应的角色信息集合
//            User user = userMapper.getUserCascadeRole(loginUser.getId());
//
//            // 获取用户已启用的角色信息
//            Set<String> roleSet = new HashSet<>();
//            if (null != user && CollectionUtils.isNotEmpty(roleSet)) {
//                for (Role role : user.getRoles()) {
//                    if (Role.STATUS_ENUM.ENABLE.getValue().equals(role.getStatus())) {
//                        roleSet.add(role.getRoleCode());
//                    }
//                }
//            }
//
//
//            // 通过用户ID查询对应的菜单权限集合
//            List<Menu> menuList = menuMapper.findOrdinaryMenu(loginUser.getId());
//
//            // 获取用户不为空的菜单权限集合
//            Set<String> menuSet = new HashSet<>();
//            if (CollectionUtils.isNotEmpty(menuList)) {
//                for (Menu menu : menuList) {
//                    if (StringUtils.isNotBlank(menu.getPerms())) {
//                        menuSet.add(menu.getPerms());
//                    }
//                }
//            }
//
//            // 将角色和权限放入授权对象中
//            authInfo.addRoles(roleSet);
//            authInfo.addStringPermissions(menuSet);
//            return authInfo;
//        }
//    }
//
//    /**
//     * 鉴权时调用
//     *
//     * @param principals 身份集合
//     * @param permission 访问的权限
//     * @return 鉴权结果
//     */
//    @Override
//    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
//        AuthorizationInfo info = getAuthorizationInfo(principals);
//        Collection<Permission> perms = getPermissions(info);
//        if (CollectionUtils.isEmpty(perms)) {
//            return false;
//        }
//        // 鉴权
//        for (Permission perm : perms) {
//            if (perm.implies(permission)) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
