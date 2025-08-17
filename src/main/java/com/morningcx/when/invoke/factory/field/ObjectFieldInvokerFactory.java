package com.morningcx.when.invoke.factory.field;

import cn.hutool.core.util.StrUtil;
import com.morningcx.when.invoke.Invoker;
import com.morningcx.when.invoke.factory.InvokerFactory;
import com.morningcx.when.oper.impl.expression.Field;
import com.morningcx.when.pojo.HasId;
import com.morningcx.when.pojo.HasName;

import java.lang.reflect.Method;

public class ObjectFieldInvokerFactory implements InvokerFactory<Field, Object> {

    @Override
    public Invoker<Object> getInvoker(Field field, Class<Object> factClass) {
        // todo 需要知道factClass，提前把反射或者instanceof判断好，另外factClass可能是context的class，需要拿到context里面fact的class
        String key = field.getKey();
        if ("id".equals(key)) {
            if (HasId.class.isAssignableFrom(factClass)) {
                return (fact, args) -> ((HasId) fact).getId();
            }
        } else if ("name".equals(key)) {
            if (HasName.class.isAssignableFrom(factClass)) {
                return (fact, args) -> ((HasName) fact).getName();
            }
        }


        try {
            String getMethodName = StrUtil.upperFirstAndAddPre(key, "get");
            Method method = factClass.getMethod(getMethodName);
            return (fact, args) -> method.invoke(fact);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
