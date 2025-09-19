package com.morningcx.when.utils;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.morningcx.when.oper.Operation;
import com.morningcx.when.oper.OperationDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class OperationUtils {

    static {
        ParserConfig.getGlobalInstance().putDeserializer(Operation.class, new OperationDeserializer());
    }

    public static Operation parseOperationFromJs(String file) throws Throwable {
        long t0 = System.currentTimeMillis();
        URL resource = JsUtils.class.getClassLoader().getResource(file);
        List<String> lines = FileUtil.readUtf8Lines(resource);
        long t1 = System.currentTimeMillis();
        String result = JsUtils.invokeJs(String.join("\n", lines));
        long t2 = System.currentTimeMillis();
        Operation operation = JSON.parseObject(result, Operation.class);
        long t3 = System.currentTimeMillis();
        // todo 删除
        JSONObject resultJson = JSON.parseObject(result);
        JSONObject operationJson = JSON.parseObject(JSON.toJSONString(operation));
        boolean equals = resultJson.equals(operationJson);
        if (equals) {
            log.info("file : [{}], compare json : {}", file, true);
        } else {
            log.error("file : [{}], compare json : {}", file, false);
        }
        long t4 = System.currentTimeMillis();
        log.info("parse operation file: [{}], took: [{}]ms, read: [{}]ms, invoke: [{}]ms, parse: [{}]ms, compare: [{}]ms",
                file, t4 - t0, t1 - t0, t2 - t1, t3 - t2, t4 - t3);
        return operation;
    }

    public static List<Operation> parseDependencies(Operation operation) throws Throwable {
        List<Operation> result = new ArrayList<>();
        Field[] declaredFields = operation.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (Operation.class.isAssignableFrom(declaredField.getType())) {
                result.add((Operation) declaredField.get(operation));
            }
            if (Collection.class.isAssignableFrom(declaredField.getType())) {
                Type genericType = declaredField.getGenericType();
                if (genericType instanceof ParameterizedType parameterizedType) {
                    Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
                    if (Operation.class.isAssignableFrom((Class<?>) actualTypeArgument)) {
                        result.addAll((Collection) declaredField.get(operation));
                    }
                }
            }
        }
        return result;
    }

}
