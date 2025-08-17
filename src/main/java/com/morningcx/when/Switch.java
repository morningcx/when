package com.morningcx.when;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Switch
 *
 * @author MorningStar
 * @date 2022/10/16
 */
public class Switch<T> implements Predicate<T> {
    private Map<Object, Predicate<T>> caseMap = new HashMap<>();
    private Function<? super T, ?> function;
    private List<Object> casesList = new ArrayList<>();
    private Predicate<T> defaultThen;


    public Switch(Function<? super T, ?> function) {
        this.function = function;
    }

    public Switch<T> cases(Object case1, Object... cases) {
        casesList.add(case1);
        if (cases != null) {
            casesList.addAll(Arrays.asList(cases));
        }
        return this;
    }

    /**
     * @param then
     * @return
     */
    public Switch<T> then(Consumer<? super T> then) {
        Predicate<T> predicate = t -> {
            then.accept(t);
            return true;
        };
        if (casesList.isEmpty()) {
            defaultThen = predicate;
        } else {
            for (Object c : casesList) {
                caseMap.put(c, predicate);
            }
            casesList = new ArrayList<>();
        }
        return this;
    }


    @Override
    public boolean test(T t) {
        Object key = function.apply(t);
        Predicate<T> predicate = caseMap.getOrDefault(key, defaultThen);
        return predicate != null && predicate.test(t);
    }

}
