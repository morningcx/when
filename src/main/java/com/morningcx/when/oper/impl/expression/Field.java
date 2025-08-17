package com.morningcx.when.oper.impl.expression;

import com.morningcx.when.oper.Operation;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Field
 *
 * @author gcx
 * @date 2024/6/4
 */
@Data
@Accessors(chain = true)
public class Field implements Operation {
    private String key;
}
