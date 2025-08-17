package com.morningcx.when.oper.impl.feature;

import com.morningcx.when.oper.Operation;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * StreamCubeFeature
 *
 * @author gcx
 * @date 2024/6/4
 */
@Data
@Accessors(chain = true)
public class StreamCubeFeature implements Operation {
    
    private String ns;
    private String dim;
    private String name;
    private String udf;
    private Operation key;
    private Operation dur;
    private Operation ref;
    private Operation current;
    
 
    
}
