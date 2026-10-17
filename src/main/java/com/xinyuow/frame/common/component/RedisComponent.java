/*
 * RedisComponent.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.component;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis组件
 *
 * @author xinyuow
 * @date 2026-10-17
 */
@Component
@RequiredArgsConstructor
public class RedisComponent {

    private final StringRedisTemplate redisTemplate;

    /**
     * 保存字符串
     *
     * @param key   Redis Key
     * @param value Redis Value
     */
    public void set(String key, String value) {
        this.checkKey(key);
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 保存字符串并设置过期时间
     *
     * @param key     Redis Key
     * @param value   Redis Value
     * @param timeout 过期时间，单位秒
     */
    public void set(String key, String value, Duration timeout) {
        this.checkKey(key);
        if (timeout == null || timeout.isZero() || timeout.isNegative()) {
            throw new IllegalArgumentException("过期时间必须大于0");
        }
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    /**
     * 获取字符串
     *
     * @param key Redis Key
     * @return Redis Value
     */
    public String get(String key) {
        this.checkKey(key);
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 判断Redis Key是否存在
     *
     * @param key Redis Key
     * @return 是否存在
     */
    public boolean hasKey(String key) {
        this.checkKey(key);
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 删除单个Redis Key
     *
     * @param key Redis Key
     * @return 是否删除成功
     */
    public boolean delete(String key) {
        this.checkKey(key);
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 批量删除Redis Key
     *
     * @param keys Redis Key集合
     * @return 删除成功的Redis Key数量
     */
    public long delete(Collection<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return 0;
        }

        Long count = redisTemplate.delete(keys);
        return count == null ? 0 : count;
    }

    /**
     * 设置Redis Key过期时间
     *
     * @param key     Redis Key
     * @param timeout 过期时间，单位秒
     * @return 是否设置成功
     */
    public boolean expire(String key, Duration timeout) {
        this.checkKey(key);
        if (timeout == null || timeout.isZero() || timeout.isNegative()) {
            throw new IllegalArgumentException("过期时间必须大于0");
        }
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout));
    }

    /**
     * 获取剩余过期时间，单位秒
     *
     * @param key Redis Key
     * @return 剩余过期时间，单位秒。-1:表示永不过期，-2:表示Redis Key不存在
     */
    public long getExpire(String key) {
        this.checkKey(key);
        Long ttl = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return ttl == null ? -2 : ttl;
    }

    /**
     * Redis Key不存在时才保存，常用于限流
     *
     * @param key     Redis Key
     * @param value   Redis Value
     * @param timeout 过期时间，单位秒
     * @return 是否设置成功
     */
    public boolean setIfAbsent(String key, String value, Duration timeout) {
        this.checkKey(key);
        if (timeout == null || timeout.isZero() || timeout.isNegative()) {
            throw new IllegalArgumentException("过期时间必须大于0");
        }
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, timeout));
    }

    /**
     * 获取Redis中指定Key的值，并同时删除这个Redis Key
     * 原子操作：先获取值，再删除Key
     * 适合一次性消费的业务凭证，确保在使用后立即删除，避免重复使用
     * 需要Redis 6.2+支持“GETDEL”指令
     *
     * @param key Redis Key
     * @return Redis Value
     */
    public String getAndDelete(String key) {
        this.checkKey(key);
        return redisTemplate.opsForValue().getAndDelete(key);
    }

    /**
     * 让Redis中指定key对应的数字加1，并返回加1后的结果。
     * 使用场景：通常用于计数，例如短信发送次数、接口请求次数、登录失败次数等
     *
     * @param key Redis Key
     * @return 递增后的新值
     */
    public long increment(String key) {
        this.checkKey(key);
        Long result = redisTemplate.opsForValue().increment(key);
        if (result == null) {
            throw new IllegalStateException("Redis 自增操作失败");
        }
        return result;
    }

    /**
     * 让Redis中指定key对应的数字加1，并返回加1后的结果。并在首次创建时设置过期时间
     *
     * @param key         Redis Key
     * @param firstExpire 首次过期时间，单位秒
     * @return 递增后的新值
     * @see RedisComponent#increment(String)
     */
    public long increment(String key, Duration firstExpire) {
        this.checkKey(key);
        if (firstExpire == null || firstExpire.isZero() || firstExpire.isNegative()) {
            throw new IllegalArgumentException("过期时间必须大于0");
        }

        Long result = redisTemplate.opsForValue().increment(key);
        if (result == null) {
            throw new IllegalStateException("Redis 自增操作失败");
        }

        if (result == 1) {
            redisTemplate.expire(key, firstExpire);
        }
        return result;
    }

    /**
     * 向Redis Set集合添加元素
     *
     * @param key   Redis Key
     * @param value Redis Value
     * @return 添加成功的元素数量
     */
    public long setAdd(String key, String value) {
        this.checkKey(key);
        Long result = redisTemplate.opsForSet().add(key, value);
        return result == null ? 0 : result;
    }

    /**
     * 获取Redis Set集合的全部元素
     *
     * @param key Redis Key
     * @return Redis Set集合的全部元素
     */
    public Set<String> setMembers(String key) {
        this.checkKey(key);
        Set<String> result = redisTemplate.opsForSet().members(key);
        return result == null ? Collections.emptySet() : result;
    }

    /**
     * 从Redis Set集合移除元素
     *
     * @param key   Redis Key
     * @param value Redis Value
     * @return 移除成功的元素数量
     */
    public long setRemove(String key, String value) {
        this.checkKey(key);
        Long result = redisTemplate.opsForSet().remove(key, value);
        return result == null ? 0 : result;
    }

    /**
     * 使用UNLINK删除Redis Key
     *
     * @param key Redis Key
     * @return 是否删除成功
     */
    public boolean unlink(String key) {
        this.checkKey(key);
        return Boolean.TRUE.equals(redisTemplate.unlink(key));
    }

    /**
     * 根据Key匹配模式扫描Redis Key
     *
     * @param pattern Key匹配模式
     * @param count   每次扫描的提示数量
     * @return 匹配的Redis Key集合
     */
    public Set<String> scan(String pattern, int count) {
        if (!StringUtils.hasText(pattern)) {
            throw new IllegalArgumentException("匹配模式不能为空");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("扫描数量必须大于0");
        }

        Set<String> keys = new HashSet<>();
        ScanOptions options = ScanOptions.scanOptions().match(pattern).count(count).build();
        try (Cursor<String> cursor = redisTemplate.scan(options)) {
            while (cursor.hasNext()) {
                keys.add(cursor.next());
            }
        }
        return keys;
    }

    /**
     * 验证Redis Key是否为空 - 私有方法
     *
     * @param key Redis Key值
     */
    private void checkKey(String key) {
        if (!StringUtils.hasText(key)) {
            throw new IllegalArgumentException("Redis Key 不能为空");
        }
    }
}
