//package com.morningcx.when.invoke.factory.field;
//
//import com.morningcx.when.invoke.Invoker;
//import com.morningcx.when.invoke.factory.InvokerFactory;
//import com.morningcx.when.oper.impl.expression.Field;
//import com.morningcx.when.pojo.Role;
//
//public class RoleFieldInvokerFactory implements InvokerFactory<Field, Role> {
//
//    @Override
//    public Invoker<Role> getInvoker(Field field, Class<Role> factClass) {
//        String key = field.getKey();
//        if (key.equals("name")) {
//            return (fact, args) -> fact.getName();
//        } else if (key.equals("id")) {
//            return (fact, args) -> fact.getId();
//        }
//        return Invoker.nullInvoker();
//    }
//}
