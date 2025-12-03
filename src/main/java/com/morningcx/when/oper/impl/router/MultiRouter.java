package com.morningcx.when.oper.impl.router;

import com.morningcx.when.oper.Operation;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class MultiRouter implements Operation {
    private List<Branch> branches;
}
