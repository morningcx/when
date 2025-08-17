package com.morningcx.when;

import com.morningcx.when.invoke.Invoker;
import com.morningcx.when.invoke.InvokerRegister;
import com.morningcx.when.oper.Operation;
import com.morningcx.when.oper.impl.expression.Field;
import com.morningcx.when.oper.impl.function.Equals;
import com.morningcx.when.pojo.Role;
import com.morningcx.when.pojo.SuperRole;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OperationTest {

    public static void main(String[] args) throws Throwable {
        SuperRole superRole = new SuperRole();
        superRole.setId(22);
        superRole.setName("Super Role");
        superRole.setAge(22);


        Role role = new Role();
        role.setId(123);
        role.setName("role");
        role.setAge(18);


        // todo 搞个new InvokerInfo，里面携带执行器的factClass等等，避免每次InvokerRegister.getInvoker都要传递fact.getClass，每次都传很难受

        equalsTest(superRole);




    }

    private static void equalsTest(Object fact) throws Throwable {
        Operation operation = new Equals()
                .add(new Field().setKey("id"))
//                .add(new Field().setKey("name"))
                .add(new Field().setKey("age"));

        Invoker invoker = InvokerRegister.getInvoker(operation, fact.getClass());
        log.info("{}", invoker.invoke(fact));
    }

    private static void fieldTest(Object fact) throws Throwable {
        Operation operation = new Field().setKey("age");
        Invoker invoker = InvokerRegister.getInvoker(operation, fact.getClass());
        log.info("{}", invoker.invoke(fact));
    }


}
