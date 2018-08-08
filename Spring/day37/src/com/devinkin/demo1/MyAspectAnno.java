package com.devinkin.demo1;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * 注解方式的切面类
 * @author king
 */
@Aspect
public class MyAspectAnno {

    /**
     * 通知类型：@Before前置通知(切入点的表达式)
     */
    @Before(value = "MyAspectAnno.fn()")
    public void log() {
        System.out.println("记录日志...");
    }

    /**
     * 最终通知：方法执行成功或者有异常，都会执行
     */
    @After(value = "MyAspectAnno.fn()")
    public void after() {
        System.out.println("最终通知");
    }

    /**
     * 环绕通知
     */
    @Around(value = "MyAspectAnno.fn()")
    public void around(ProceedingJoinPoint joinPoint) {
        System.out.println("环绕通知1...");
        try {
            // 让目标对象的方法执行
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("环绕通知2...");
    }



    /**
     * 自己定义切入点
     * @Pointcut
     */
    @Pointcut(value = "execution(* *..*DaoImpl.save*(..))")
    public void fn() {

    }

}
