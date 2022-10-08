package com.morningcx.when.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

/**
 * GetBytecode
 *
 * @author gcx
 * @date 2022/10/8
 */
public class GetBytecode implements ClassFileTransformer {

    private static Instrumentation inst;

    public static synchronized void agentmain(String args, Instrumentation inst) {
        GetBytecode.inst = inst;
    }

    public static synchronized <T> byte[] getClassFile(Class<T> cls) throws UnmodifiableClassException {
        Instrumentation inst = GetBytecode.inst;
        if (inst == null) {
            throw new IllegalStateException("Agent has not been loaded");
        }

        GetBytecode transformer = new GetBytecode();
        inst.addTransformer(transformer, true);
        inst.retransformClasses(cls);
        inst.removeTransformer(transformer);
        return transformer.classFile;
    }

    private byte[] classFile;

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain pd, byte[] classFile) {
        if (classBeingRedefined != null) {
            this.classFile = classFile;
        }
        return null;
    }
}