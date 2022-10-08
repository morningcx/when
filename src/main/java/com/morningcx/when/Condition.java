package com.morningcx.when;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Condition
 *
 * @author MorningStar
 * @date 2022/10/4
 */
@FunctionalInterface
public interface Condition<T> extends Consumer<T>, Serializable {

    boolean test(T t);

    @Override
    default void accept(T t) {
        test(t);
    }

    default Condition<T> and(Condition<? super T> and) {
        Objects.requireNonNull(and);
        return t -> test(t) && and.test(t);
    }


    default Condition<T> or(Condition<? super T> or) {
        Objects.requireNonNull(or);
        return t -> test(t) || or.test(t);
    }

    /**
     * @param then
     * @return
     */
    default Condition<T> then(Condition<? super T> then) {
        return and(then);
    }

    default Condition<T> negate() {
        return t -> !test(t);
    }

}
