package com.morningcx.when.var;

import com.morningcx.when.pojo.Role;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;
import java.util.function.Function;

/**
 * ScVar
 *
 * @author gcx
 * @date 2023/5/11
 */
@Data
@Accessors(fluent = true)
public class EqualsFunction<T> implements Function<T, Boolean> {
    public static void main(String[] args) {
        EqualsFunction<Role> equalsFunction = 
                new EqualsFunction<Role>()
                        .left(i -> "1")
                        .right(Role::getName);
        
    }
    
    
    private Function<T, ?> left;
    private Function<T, ?> right;
    

    @Override
    public Boolean apply(T t) {
        Object apply = left.apply(t);
        Object apply1 = right.apply(t);
        return Objects.equals(apply, apply1);
    }
}
