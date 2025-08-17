package com.morningcx.when.invoke.factory;

import com.morningcx.when.invoke.Invoker;
import com.morningcx.when.oper.Operation;

public interface InvokerFactory<O extends Operation, F> {

    Invoker<F> getInvoker(O operation, Class<F> factClass);
}
