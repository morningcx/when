package com.morningcx.when.agent;

import com.sun.tools.attach.VirtualMachine;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.security.ProtectionDomain;

/**
 * GetBytecode
 *
 * @author gcx
 * @date 2022/10/8
 */
public class GetBytecode implements ClassFileTransformer {

    private volatile static Instrumentation inst;

    public static void agentmain(String args, Instrumentation inst) {
        GetBytecode.inst = inst;
    }

    public static synchronized <T> byte[] getClassFile(Class<T> cls) throws Throwable {
        Instrumentation inst = getInstrumentation();
        GetBytecode transformer = new GetBytecode();
        inst.addTransformer(transformer, true);
        inst.retransformClasses(cls);
        inst.removeTransformer(transformer);
        return transformer.classFile;
    }

    public static Instrumentation getInstrumentation() throws Throwable {
        if (inst == null) {
            synchronized (GetBytecode.class) {
                String agentJar = "E:\\Program\\Project\\when\\src\\main\\resources\\when-agent.jar";
                VirtualMachine vm = null;
                try {
                    String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
                    vm = VirtualMachine.attach(pid);
                    vm.loadAgent(agentJar);
                } finally {
                    if (vm != null) {
                        vm.detach();
                    }
                }
                if (inst == null) {
                    throw new IllegalStateException("Agent has not been loaded");
                }
            }
        }
        return inst;
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