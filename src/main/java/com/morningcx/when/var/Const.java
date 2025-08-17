package com.morningcx.when.var;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.function.Function;

/**
 * ScVar
 *
 * @author gcx
 * @date 2023/5/11
 */
@Data
@Accessors(fluent = true)
public class Const<T> implements Function<T, Object> {
    
    private Object value;
    
    @Override
    public Object apply(T t) {
        return value;
    }
}
