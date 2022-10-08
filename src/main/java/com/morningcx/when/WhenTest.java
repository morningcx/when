package com.morningcx.when;

import javassist.ClassPool;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Mnemonic;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * WhenTest
 *
 * @author MorningStar
 * @date 2022/10/6
 */
public class WhenTest {
    public static void main(String[] args) throws Throwable {

//        System.out.println(CompileUtils.decompile(GetBytecode.getClassFile(WhenTest.class)));


        int iii = 0;
        int aaa = 0;

        for (int i = 0; i < 10; i++) {


            Condition<Role> rolePredicate2 = r1 -> {
                System.out.println(iii);
                return true;
            };


            Condition<Role> rolePredicate = r -> {
                System.out.println(aaa);
                return true;
            };

            Function<Role, Map> function = (r) -> {
                rolePredicate.test(new Role());
                return Collections.singletonMap("123123", iii);
            };



            printCode(lambda(rolePredicate));
            printCode(lambda(rolePredicate2));
            printCode(lambda(function));



//            System.out.println(CompileUtils.decompile(GetBytecode.getClassFile(WhenTest.class)));

        }


//        if (print("a", false) || print("b", false) && print("c", false) || print("d", true)) {
//            if (print("a", false) || print("b", false) && print("c", false) || print("d", true)) {
//                System.out.println("111111111");
//            }
//        }

        if (print("a", false) || print("b", true) && print("c", false) || print("d", false)) {
            System.out.println("111111111");
        }


        System.out.println("=============================================");

        When<Role> then2 = when().and(t -> false).or(t -> false);
        boolean test1 = then2.test(new Role());
        System.out.println(test1);


        System.out.println("=============================================");


        When<Role> then = when()
                .or(r -> print("a", false))
                .or(r -> print("b", false))
                .and(r -> print("c", false))
                .or(r -> print("d", true))
                .then(when()
                        .or(r -> print("a", false))
                        .or(r -> print("b", false))
                        .and(r -> print("c", false))
                        .or(r -> print("d", true))
                        .then(when()
                                .or(r -> print("a", false))
                                .or(r -> print("b", true))
                                .and(r -> print("c", false))
                                .or(r -> print("d", true))
                                .then(r -> System.out.println("111111111"))));
        boolean test = then.test(new Role());
        System.out.println(test);

        System.out.println("=============================================");

        When<Role> then1 = when().then(role -> System.out.println("11111111111111111"));

        System.out.println(then1.test(null));

    }

    private static boolean print(String word, boolean b) {
        System.out.println(word);
        return b;
    }


    private static SerializedLambda lambda(Object lambda) throws Throwable {
        Method method = lambda.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(true);
        SerializedLambda serializedLambda = (SerializedLambda) method.invoke(lambda);

        System.out.println(serializedLambda);
//        System.out.println(JSON.toJSONString(serializedLambda));
        return serializedLambda;

    }


    private static void printCode(SerializedLambda lambda) throws Throwable {
        ClassPool classPool = ClassPool.getDefault();
        CtMethod method = classPool.getMethod(lambda.getImplClass().replace("/", "."), lambda.getImplMethodName());
        MethodInfo methodInfo = method.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        CodeIterator ci = codeAttribute.iterator();

        List<String> operations = new LinkedList<>();
        while (ci.hasNext()) {
            int index = ci.next();
            int op = ci.byteAt(index);
            operations.add(Mnemonic.OPCODE[op]);
        }
        System.out.println(operations);
    }

    private static When<Role> when() {
        return new When<>();
    }
}
