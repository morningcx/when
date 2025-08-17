package com.morningcx.when.invoke.factory.feature;

import com.morningcx.when.invoke.Invoker;
import com.morningcx.when.invoke.factory.InvokerFactory;
import com.morningcx.when.oper.Operation;
import com.morningcx.when.oper.impl.feature.StreamCubeFeature;

public class ObjectStreamCubeFeatureInvokerFactory implements InvokerFactory<StreamCubeFeature, Object> {

    @Override
    public Invoker<Object> getInvoker(StreamCubeFeature streamCubeFeature, Class<Object> factClass) {
        String ns = streamCubeFeature.getNs();
        String dim = streamCubeFeature.getDim();
        String name = streamCubeFeature.getName();

        Operation key = streamCubeFeature.getKey();
        Operation dur = streamCubeFeature.getDur();
        Operation ref = streamCubeFeature.getRef();
        Operation current = streamCubeFeature.getCurrent();


        return null;
    }
}
