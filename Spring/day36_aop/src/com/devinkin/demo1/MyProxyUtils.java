package com.devinkin.demo1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 使用JDK的方式生成代理对象
 */
public class MyProxyUtils {
    public static UserDao getProxy(final UserDao dao) {
        // 使用Proxy类生成代理对象
        UserDao proxy = (UserDao) Proxy.newProxyInstance(dao.getClass().getClassLoader(), dao.getClass().getInterfaces(), new InvocationHandler() {
            // 代理对象方法一执行，invoke方法就执行

            /**
             * 使用代理对象的方法时调用invoke
             * @param proxy 代理对象
             * @param method 代理对象的方法
             * @param args 方法的参数
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                if ("save".equals(method.getName())) {
                    System.out.println("记录日志执行");
                }

                // 让dao类的save或update方法正常执行
                method.invoke(dao, args);
                return null;
            }
        });

        // 返回代理对象
        return proxy;
    }
}
