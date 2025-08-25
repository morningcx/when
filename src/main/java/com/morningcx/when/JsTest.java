package com.morningcx.when;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.morningcx.when.oper.Operation;
import com.morningcx.when.utils.OperationUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
public class JsTest {
    public static void main(String[] args) throws Throwable {
        Operation operation = OperationUtils.parseOperationFromJs("sc.js");
        log.info(JSON.toJSONString(operation, SerializerFeature.PrettyFormat));
        depends(Collections.singletonList(operation), "$");
    }

    private static void depends(List<Operation> operations, String path) throws Throwable {
        for (Operation operation : operations) {
            List<Operation> dependencies = OperationUtils.parseDependencies(operation);
            depends(dependencies, path + "." + operation.getType());
            log.info("path: {}, dependencies: {}", path + "." + operation.getType(), dependencies);
        }
    }
}
