package com.morningcx.when.utils;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.morningcx.when.oper.Operation;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.nashorn.api.scripting.NashornScriptEngine;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.openjdk.nashorn.api.scripting.ScriptObjectMirror;

import java.net.URL;
import java.util.List;


@Slf4j
public class JsUtils {


    public static String invokeJs(String content) throws Throwable {
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        NashornScriptEngine engine = (NashornScriptEngine) factory.getScriptEngine();
        engine.eval(content);
        Object result = engine.invokeFunction("main");
        String jsonStr;
        if (result instanceof ScriptObjectMirror) {
            jsonStr = JSON.toJSONString(ScriptObjectMirror.wrapAsJSONCompatible(result, null));
        } else if (result instanceof String) {
            jsonStr = (String) result;
        } else {
            throw new RuntimeException("unexpected result: " + result);
        }
        return jsonStr;
    }



    public static Operation parseOperationFromJs(String file) throws Throwable  {
        URL resource = JsUtils.class.getClassLoader().getResource(file);
        List<String> lines = FileUtil.readUtf8Lines(resource);
        String result = invokeJs(String.join("\n", lines));
        Operation operation = JSON.parseObject(result, Operation.class);
        // todo 删除
        JSONObject resultJson = JSON.parseObject(result);
        JSONObject operationJson = JSON.parseObject(JSON.toJSONString(operation));
        boolean equals = resultJson.equals(operationJson);
        if (equals) {
            log.info("file : [{}], compare json : {}", file, true);
        } else {
            log.error("file : [{}], compare json : {}", file, false);

        }
        return operation;
    }
}
