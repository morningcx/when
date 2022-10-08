package com.morningcx.when.decompile;

import org.benf.cfr.reader.api.CfrDriver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * CompileUtils
 *
 * @author MorningStar
 * @date 2021/7/7
 */
public class CompileUtils {
    private CompileUtils() {
    }


    public static String decompile(byte[] bytes) {
        StringBuilder builder = new StringBuilder();

        Map<String, String> options = new HashMap<>(2);
        // 字符型switch不使用语法糖美化，否则有些字符型switch会反编译失败
        options.put("decodestringswitch", "false");
        // lambda表达式不反编译
        options.put("decodelambdas", "false");
        String className = "";
        // 反编译
        CfrDriver cfrDriver = new CfrDriver.Builder()
                // 输入
                .withClassFileSource(new AssetsClassFileSource(Collections.singletonMap(className, bytes)))
                // 输出
                .withOutputSink(new AssetsOutputSinkFactory(builder))
                .withOptions(options)
                .build();
        cfrDriver.analyse(Collections.singletonList(className));
        return builder.toString();
    }
}
