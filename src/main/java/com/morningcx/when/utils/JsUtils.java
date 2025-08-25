package com.morningcx.when.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.nashorn.api.scripting.NashornScriptEngine;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.openjdk.nashorn.api.scripting.ScriptObjectMirror;


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

}
