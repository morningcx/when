package com.morningcx.when.oper;

import cn.hutool.core.lang.ClassScanner;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class OperationDeserializer implements ObjectDeserializer {

    private static final Map<String, Class<? extends Operation>> TYPE_MAP = new HashMap<>();

    static {
        Set<Class<?>> classes = ClassScanner.scanPackage(Operation.class.getPackage().getName(),
                clazz -> Operation.class.isAssignableFrom(clazz) && clazz != Operation.class);
        classes.forEach(clazz -> {
            try {
                String type = ((Operation) clazz.getConstructor().newInstance()).getType();
                TYPE_MAP.put(type, (Class) clazz);
                log.info("registering Operation - type: [{}], class: [{}]", type, clazz.getName());
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type objType, Object fieldName) {
        JSONObject jsonObject = parser.parseObject();
        String type = jsonObject.getString("type");
        Class<? extends Operation> clazz = TYPE_MAP.get(type);
        if (clazz == null) {
            throw new IllegalArgumentException("Unknown operation type: " + type + " in content: " + jsonObject.toJSONString());
        }
        return (T) jsonObject.toJavaObject(clazz);
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
