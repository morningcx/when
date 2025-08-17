package com.morningcx.when.invoke.factory.equals;

import com.morningcx.when.invoke.Invoker;
import com.morningcx.when.invoke.InvokerRegister;
import com.morningcx.when.invoke.factory.InvokerFactory;
import com.morningcx.when.oper.Operation;
import com.morningcx.when.oper.impl.function.Equals;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectEqualsInvokerFactory implements InvokerFactory<Equals, Object> {

    @Override
    public Invoker<Object> getInvoker(Equals equals, Class<Object> factClass) {
        List<Invoker<Object>> invokers = new ArrayList<>();
        for (Operation operation : equals.getItems()) {
            // todo 遇到批量执行的invoker怎么办
            invokers.add(InvokerRegister.getInvoker(operation, factClass));
        }
        return (fact, args) -> {
            if (invokers.isEmpty()) {
                return true;
            }
            // todo 对象放在外面还能优化，2个是常见的，可以单独对2个item的特殊处理invoker
            Object first = invokers.get(0).invoke(fact, args);
            // 从第二个元素开始遍历比较
            for (int i = 1; i < invokers.size(); i++) {
                if (!Objects.equals(first, invokers.get(i).invoke(fact, args))) {
                    return false; // 发现不一致立即返回
                }
            }
            return true; // 全部一致
        };
    }
}
