package com.morningcx.when;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.morningcx.when.oper.Operation;
import com.morningcx.when.oper.OperationDeserializer;
import com.morningcx.when.utils.JsUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsTest {

    static {
        ParserConfig.getGlobalInstance().putDeserializer(Operation.class, new OperationDeserializer());
    }
    public static void main(String[] args) throws Throwable {

        Operation operation = JsUtils.parseOperationFromJs("sc.js");

        log.info(JSON.toJSONString(operation, SerializerFeature.PrettyFormat));


    }


}
