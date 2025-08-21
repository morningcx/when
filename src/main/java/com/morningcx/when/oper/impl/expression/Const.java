package com.morningcx.when.oper.impl.expression;

import com.morningcx.when.oper.Operation;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Const implements Operation {
    private Object value;
}
