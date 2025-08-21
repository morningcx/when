package com.morningcx.when.invoke;

/**
 * 针对不同的Operation以及不同的fact，可以有不同的invoker
 * 同一个Operation可以有不同的invoker
 *
 * @param <F>
 */
public interface Invoker<F> {

    @SuppressWarnings("rawtypes")
    Invoker NULL_INVOKER = (fact, args) -> null;

    @SuppressWarnings("unchecked")
    static <T> Invoker<T> nullInvoker() {
        return NULL_INVOKER;
    }


    /**
     * 执行
     *
     * @param fact
     * @param args
     * @return
     */
    Object invoke(F fact, Object... args) throws Throwable;

    default boolean isBatch() {
        // todo 判断F是否是Collection
        return false;
    }
}
