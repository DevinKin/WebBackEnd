package cn.devinkin.case1.test;

import cn.devinkin.case1.annotation.MyTest;

public class AnnotationTest {
    @MyTest
    public void f1() {
        System.out.println("f1方法执行了");
    }
    public void f2() {
        System.out.println("f2方法执行了");
    }

    @MyTest
    public void f3() {
        System.out.println("f3方法执行了");
    }
}
