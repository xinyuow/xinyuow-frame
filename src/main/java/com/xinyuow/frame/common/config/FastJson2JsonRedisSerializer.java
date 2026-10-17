/*
 * FastJson2JsonRedisSerializer.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.config;

import com.alibaba.fastjson2.JSON;
import org.jspecify.annotations.Nullable;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * FastJson2 序列化 Redis 的值
 *
 * @author mxy
 * @date 2026-10-17
 */
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {

    private final Class<T> clazz;

    public FastJson2JsonRedisSerializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(@Nullable T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }

        try {
            return JSON.toJSONBytes(t);
        } catch (Exception e) {
            throw new SerializationException(
                    "Could not serialize: " + e.getMessage(), e
            );
        }
    }

    @Override
    public @Nullable T deserialize(byte @Nullable [] bytes)
            throws SerializationException {

        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            return JSON.parseObject(bytes, clazz);
        } catch (Exception e) {
            throw new SerializationException(
                    "Could not deserialize: " + e.getMessage(), e
            );
        }
    }
}