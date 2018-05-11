package com.devinkin.servlet;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Demo {
    @Test
    public void func1() throws Exception {
        /**
         * 调用helloMyServlet的方法
         */
        Class clazz = Class.forName("com.devinkin.servlet.HelloServlet");

        //通过字节码对象创建一个实例对象
        HelloServlet a = (HelloServlet) clazz.newInstance();
        a.add();

//        Method method =  clazz.getMethod("add");
        Method method =  clazz.getMethod("add", int.class, int.class);
        method.invoke(a, 3,4);
    }

}
