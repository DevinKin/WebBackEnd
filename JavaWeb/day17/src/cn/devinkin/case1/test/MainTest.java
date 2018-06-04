package cn.devinkin.case1.test;

import cn.devinkin.case1.annotation.MyTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainTest {
//    public static void main(String[] args) {
//        //运行这个类的时候需要将测试类中带有@mytest所有方法执行
//
//        /**
//         * 1. 获取字节码对象
//         */
//        Class clazz = AnnotationTest.class;
//
//        /**
//         * 1. 获取所有的方法
//         * 2. 让方法执行
//         */
//        Method[] methods = clazz.getMethods();
//
//        for (Method m : methods) {
//            //打印所有方法名
//            System.out.println(m.getName());
//        }
//    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        /**
         * 1. 获取字节码对象
         */
        Class clazz = AnnotationTest.class;

        //获取所有的方法
        Method[] methods = clazz.getMethods();

        //让带有注解的方法执行
        for (Method method : methods) {
            //获取有注解的方法

            //判断方法是否有指定的注解
            if (method.isAnnotationPresent(MyTest.class)) {
                System.out.println(method.getName());
                method.invoke(clazz.newInstance(),null);
            }
        }
    }
}
