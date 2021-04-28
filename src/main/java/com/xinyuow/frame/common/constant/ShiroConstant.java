package com.xinyuow.frame.common.constant;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Shiro常量配置
 *
 * @author mxy
 * @date 2021/4/21
 */
@Component
public class ShiroConstant implements InitializingBean {

    @Value("${shiro.redis.session.key-name}")
    private String shiroRedisSessionKeyName;

    @Value("${shiro.redis.session.expire}")
    private int shiroRedisSessionExpire;

    @Value("${shiro.redis.realm.key-name}")
    private String shiroRedisRealmKeyName;

    @Value("${shiro.redis.realm.expire}")
    private int shiroRedisRealmExpire;

    @Value("${shiro.session.cookie_name}")
    private String shiroSessionCookieName;

    /**
     * Shiro Session的redis-key
     */
    public static String SHIRO_REDIS_SESSION_KEY_NAME;

    /**
     * Shiro Session的过期时间，单位：秒
     */
    public static int SHIRO_REDIS_SESSION_EXPIRE;

    /**
     * Shiro Realm的redis-key
     */
    public static String SHIRO_REDIS_REALM_KEY_NAME;

    /**
     * Shiro Realm的超时时间，单位：秒
     */
    public static int SHIRO_REDIS_REALM_EXPIRE;

    /**
     * 自定义cookie的名称，避免和跟原来的session的id值重复的
     */
    public static String SHIRO_SESSION_COOKIE_NAME;

    @Override
    public void afterPropertiesSet() {
        SHIRO_REDIS_SESSION_KEY_NAME = shiroRedisSessionKeyName;
        SHIRO_REDIS_SESSION_EXPIRE = shiroRedisSessionExpire;
        SHIRO_REDIS_REALM_KEY_NAME = shiroRedisRealmKeyName;
        SHIRO_REDIS_REALM_EXPIRE = shiroRedisRealmExpire;
        SHIRO_SESSION_COOKIE_NAME = shiroSessionCookieName;
    }
}
