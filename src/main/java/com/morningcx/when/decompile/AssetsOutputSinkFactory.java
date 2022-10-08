package com.morningcx.when.decompile;

import cn.hutool.core.exceptions.ExceptionUtil;
import org.benf.cfr.reader.api.OutputSinkFactory;
import org.benf.cfr.reader.api.SinkReturns.Decompiled;
import org.benf.cfr.reader.api.SinkReturns.ExceptionMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * AssetsOutputSinkFactory
 *
 * @author gcx
 * @date 2022/6/1
 */
public class AssetsOutputSinkFactory implements OutputSinkFactory {

    private final StringBuilder builder;

    public AssetsOutputSinkFactory(StringBuilder builder) {
        this.builder = builder;
    }

    @Override
    public List<SinkClass> getSupportedSinks(SinkType sinkType, Collection<SinkClass> available) {
        return new ArrayList<>(available);
    }

    @Override
    public <T> Sink<T> getSink(SinkType sinkType, SinkClass sinkClass) {
        return sinkable -> {
            if (sinkable instanceof ExceptionMessage) {
                builder.append(ExceptionUtil.getMessage(((ExceptionMessage) sinkable).getThrownException()));
            } else if (sinkable instanceof Decompiled) {
                builder.append(((Decompiled) sinkable).getJava());
            } else {
                builder.append(sinkable);
            }
            builder.append("\n");
        };
    }
}
