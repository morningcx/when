package com.morningcx.when.oper;

/**
 * Operation
 *
 * @author gcx
 * @date 2024/6/4
 */
public interface Operation {
    
    default String getType() {
        return getClass().getSimpleName();
    }
}
