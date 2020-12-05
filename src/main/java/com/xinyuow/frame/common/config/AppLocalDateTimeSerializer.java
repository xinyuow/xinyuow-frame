package com.xinyuow.frame.common.config;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime序列化时格式化工具类
 *
 * @author mxy
 * @date 2020/11/12
 */
public class AppLocalDateTimeSerializer implements ObjectSerializer {

    private final String pattern;

    AppLocalDateTimeSerializer(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public void write(JSONSerializer jsonSerializer, Object object, Object fieldName, Type fieldType, int i) {
        if (object == null) {
            jsonSerializer.out.writeNull();
            return;
        }
        if (object instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) object;
            String format = localDateTime.format(DateTimeFormatter.ofPattern(pattern));
            jsonSerializer.write(format);
        }
    }

}
