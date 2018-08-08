package com.devinkin.demo2;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyCglibUtils {
    /**
     * 使用CGLIB方式生成代理对象
     * @return 代理对象
     */
    public static BookDaoImpl getProxy() {
        Enhancer enhancer = new Enhancer();
        // 设置父类
        enhancer.setSuperclass(BookDaoImpl.class);
        // 设置回调函数
        enhancer.setCallback(new MethodInterceptor() {
            // 代理对象的方法执行，回调函数就会执行
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
//                System.out.println("aaa");
                if ("save".equals(method.getName())) {
                    System.out.println("记录日志...");
                }
//                正常执行
                return methodProxy.invokeSuper(o, args);
            }
        });
        // 生成代理对象
        BookDaoImpl proxy = (BookDaoImpl) enhancer.create();
        return  proxy;
    }
}
