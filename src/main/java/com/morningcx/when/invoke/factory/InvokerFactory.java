package com.morningcx.when.invoke.factory;

import com.morningcx.when.invoke.Invoker;
import com.morningcx.when.oper.Operation;

// todo 支持batch 的使用Collection<O extends Operation>
public interface InvokerFactory<O extends Operation, F> {

    Invoker<F> getInvoker(O operation, Class<F> factClass);
}
