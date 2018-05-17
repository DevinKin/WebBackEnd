package com.devinkin.servlet;

public class HelloServlet {
    public void add() {
        System.out.println("空参的add方法");
    }

    public void add(int i, int j) {
        System.out.println("带有两个参数的add方法");
        System.out.println(i+j);
    }

    public int add(int i) {
        return i + 10;
    }
}
