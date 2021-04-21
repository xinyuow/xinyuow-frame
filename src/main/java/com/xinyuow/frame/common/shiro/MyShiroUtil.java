//package com.xinyuow.frame.common.shiro;
//
//import org.apache.shiro.authc.Authenticator;
//import org.apache.shiro.authc.LogoutAware;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.SimplePrincipalCollection;
//import org.apache.shiro.subject.support.DefaultSubjectContext;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.crazycake.shiro.RedisSessionDAO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//
///**
// * Shiro权限工具类
// *
// * @author mxy
// * @date 2021/4/21
// */
//@Component
//public class MyShiroUtil {
//
//    @Autowired
//    private RedisSessionDAO redisSessionDAO;
//
//    /**
//     * 删除用户缓存信息
//     *
//     * @param loginName       用户名称
//     * @param isRemoveSession isRemoveSession 是否删除Session，删除后用户需重新登录
//     */
//    public void deleteCache(String loginName, boolean isRemoveSession) {
//        // 从缓存中获取目标session
//        Session session = null;
//        // 获取当前已登录的用户session列表
//        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
//        User sysUserEntity;
//        Object attribute = null;
//
//        // 遍历session列表，找到该用户名称对应的session
//        for (Session sessionInfo : sessions) {
//            attribute = sessionInfo.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
//            if (attribute == null) {
//                continue;
//            }
//            sysUserEntity = (User) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
//            if (sysUserEntity == null) {
//                continue;
//            }
//            if (Objects.equals(sysUserEntity.getLoginName(), loginName)) {
//                session = sessionInfo;
//                break;
//            }
//        }
//
//        if (session == null || attribute == null) {
//            return;
//        }
//        // 删除session
//        if (isRemoveSession) {
//            // 清除该用户以前登录时保存的session，强制退出 -> 单用户登录处理
//            redisSessionDAO.delete(session);
//        }
//        // 删除Cache，再访问受限接口时会重新授权
//        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
//        Authenticator authc = securityManager.getAuthenticator();
//        ((LogoutAware) authc).onLogout((SimplePrincipalCollection) attribute);
//    }
//}
