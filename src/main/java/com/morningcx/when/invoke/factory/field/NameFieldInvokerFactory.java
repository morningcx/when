//package com.morningcx.when.invoke.factory.field;
//
//import com.morningcx.when.invoke.Invoker;
//import com.morningcx.when.invoke.factory.InvokerFactory;
//import com.morningcx.when.oper.impl.expression.Field;
//import com.morningcx.when.pojo.HasName;
//
///**
// * todo field的key为id、fact为rule的时候也会匹配到HasName这个InvokerFactory
// */
//public class NameFieldInvokerFactory implements InvokerFactory<Field, HasName> {
//    @Override
//    public Invoker<HasName> getInvoker(Field field, Class<HasName> factClass) {
//        String key = field.getKey();
//        if (key.equals("name")) {
//            return (fact, args) -> fact.getName();
//        }
//        return Invoker.nullInvoker();
//    }
//}
