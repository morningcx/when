package com.morningcx.when.invoke;

import cn.hutool.core.lang.ClassScanner;
import com.morningcx.when.invoke.factory.InvokerFactory;
import com.morningcx.when.oper.Operation;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class InvokerRegister {
    private static Map<Type, Map<Type, InvokerFactory>> invokerFactoryMap = new HashMap<>();

    static {
        Set<Class<?>> classes = ClassScanner.scanPackage(InvokerFactory.class.getPackage().getName(),
                clazz -> InvokerFactory.class.isAssignableFrom(clazz) && clazz != InvokerFactory.class);
        classes.forEach(clazz -> {
            try {
                register((InvokerFactory) clazz.getConstructor().newInstance());
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }


    public static void register(InvokerFactory invokerFactory) {
        Class<? extends InvokerFactory> invokerFactoryClass = invokerFactory.getClass();
        for (Type type : invokerFactoryClass.getGenericInterfaces()) {
            if (type instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) type;
                if (pt.getRawType() == InvokerFactory.class) {
                    Type[] actualTypeArguments = pt.getActualTypeArguments();
                    Type operationType = actualTypeArguments[0];
                    Type factType = actualTypeArguments[1];
                    log.info("registering - {}<{}, {}>", invokerFactoryClass.getName(), operationType.getTypeName(), factType.getTypeName());
                    invokerFactoryMap.computeIfAbsent(operationType, k -> new HashMap<>())
                            .put(factType, new InvokerFactory() {
                                @Override
                                public Invoker getInvoker(Operation operation, Class factClass) {
                                    // todo 这里调试使用，后续删除
                                    log.info("get invokerFactory [{}] operation [{}] factClass [{}]", invokerFactoryClass.getSimpleName(), operation, factClass.getSimpleName());
                                    return invokerFactory.getInvoker(operation, factClass);
                                }
                            });
                    return;
                }
            }
        }
    }


    public static Invoker getInvoker(Operation operation, Class<?> factType) {
        Map<Type, InvokerFactory> typeInvokerFactoryMap = invokerFactoryMap.get(operation.getClass());
        if (typeInvokerFactoryMap != null) {
            Class<?> originFactType = factType;
            while (factType != null) {
                InvokerFactory invokerFactory = typeInvokerFactoryMap.get(factType);
                if (invokerFactory != null) {
                    return invokerFactory.getInvoker(operation, originFactType);
                }
                for (Class<?> interfaceClass : factType.getInterfaces()) {
                    invokerFactory = typeInvokerFactoryMap.get(interfaceClass);
                    if (invokerFactory != null) {
                        return invokerFactory.getInvoker(operation, originFactType);
                    }
                }
                factType = factType.getSuperclass();
            }
        }
        return null;
    }

}
