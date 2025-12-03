package com.morningcx.when.oper.impl.router;

import com.morningcx.when.oper.Operation;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BinaryRouter implements Operation {
    private Operation condition;
    private Operation trueAction;
    private Operation falseAction;
}
