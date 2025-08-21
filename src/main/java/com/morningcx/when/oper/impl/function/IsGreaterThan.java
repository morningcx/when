package com.morningcx.when.oper.impl.function;

import com.morningcx.when.oper.Operation;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;

/**
 * Equals
 *
 * @author gcx
 * @date 2024/6/4
 */
@Data
@Accessors(chain = true)
public class IsGreaterThan implements Operation {
    private List<Operation> items = new LinkedList<>();

    public IsGreaterThan add(Operation operation) {
        items.add(operation);
        return this;
    }
}