package com.xinyuow.frame.common.shiro;

import com.xinyuow.frame.common.constant.RedisConfigConstant;
import com.xinyuow.frame.common.constant.ShiroConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;

/**
 * Shiro配置类
 *
 * @author mxy
 * @date 2021/4/21
 */
@Slf4j
@Configuration
public class ShiroConfig {

    /**
     * 配置Shiro的过滤器工厂，拦截请求并交给SecurityManager处理
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        log.info("\r\n ********* Shiro过滤器配置开始 *********");

        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置securityManager
        bean.setSecurityManager(manager);

        // 配置登录请求的url和无权限的url
        bean.setLoginUrl("/api/un_auth");                                   // 未登录

        // 配置拦截器有序集合
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/api/login", "anon");                 // 登录

        filterChainDefinitionMap.put("/swagger-ui/**", "anon");             // 放行swagger2
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");      // 放行swagger2
        filterChainDefinitionMap.put("/v3/**", "anon");                     // 放行swagger2

        filterChainDefinitionMap.put("/static/**", "anon");                 // 放行静态页面

        filterChainDefinitionMap.put("/**", "authc,perms");                 // 所有URL必须经过认证才可访问
        filterChainDefinitionMap.put("/api/logout", "logout");              // 退出

        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        log.info("\r\n ********* Shiro过滤器配置完成 *********");
        return bean;
    }

    /**
     * 配置核心安全事务管理器
     *
     * @param authRealm 自定义的认证域
     * @return SecurityManager
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager(MyShiroRealm authRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义的认证Realm
        securityManager.setRealm(authRealm);
        // 不设置【记住我】
        securityManager.setRememberMeManager(null);
        // 会话管理
        securityManager.setSessionManager(sessionManager());
        // 缓存管理
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    /**
     * 自定义sessionManager
     *
     * @return SessionManager
     */
    @Bean
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setSessionIdCookie(cookie());
        mySessionManager.setSessionDAO(redisSessionDAO());
        mySessionManager.setSessionIdUrlRewritingEnabled(false);  // 去除登录时携带的sessionID
        return mySessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis, 使用的是shiro-redis开源插件
     *
     * @return RedisSessionDAO
     */
    @Bean
    @DependsOn("shiroConstant")
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        redisSessionDAO.setKeyPrefix(ShiroConstant.SHIRO_REDIS_SESSION_KEY_NAME);   // sessionID前缀
        redisSessionDAO.setExpire(ShiroConstant.SHIRO_REDIS_SESSION_EXPIRE);        // sessionID存在时间
        return redisSessionDAO;
    }

    /**
     * cacheManager 缓存 redis实现, 使用的是shiro-redis开源插件
     *
     * @return RedisCacheManager
     */
    @Bean
    @DependsOn("shiroConstant")
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        // 必须要设置主键名称，shiro-redis 插件通过这个缓存用户信息
        redisCacheManager.setPrincipalIdFieldName("id");
        redisCacheManager.setKeyPrefix(ShiroConstant.SHIRO_REDIS_REALM_KEY_NAME);   // 权限前缀
        redisCacheManager.setExpire(ShiroConstant.SHIRO_REDIS_REALM_EXPIRE);        // 权限存在时间
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager, 使用的是shiro-redis开源插件
     *
     * @return RedisManager
     */
    @Bean
    @DependsOn({"redisConfig", "redisConfigConstant"})
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(RedisConfigConstant.HOST + ":" + RedisConfigConstant.PORT);
        redisManager.setPassword(RedisConfigConstant.PASSWORD);
        redisManager.setTimeout(RedisConfigConstant.TIMEOUT);
        redisManager.setDatabase(RedisConfigConstant.DATABASE_ID);
        return redisManager;
    }

    /**
     * 开启Shiro的AOP注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 自动创建代理类，若不添加，Shiro的注解可能不会生效。
     * 扫描上下文，获取所有的Advisor(通知器)。并将这些Advisor应用到所有符合切入点的Bean中。
     *
     * @DependsOn 保证创建DefaultAdvisorAutoProxyCreator 之前先创建 LifecycleBeanPostProcessor
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 配置Shiro生命周期处理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * Session ID 生成器
     *
     * @return JavaUuidSessionIdGenerator
     */
    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * 这里需要设置一个cookie的名称，避免和跟原来的session的id值重复的
     *
     * @return SimpleCookie
     */
    @Bean
    @DependsOn("shiroConstant")
    public SimpleCookie cookie() {
        // cookie的name,对应的默认是 JSESSIONID
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setHttpOnly(true);
        simpleCookie.setName(ShiroConstant.SHIRO_SESSION_COOKIE_NAME);
        return simpleCookie;
    }
}