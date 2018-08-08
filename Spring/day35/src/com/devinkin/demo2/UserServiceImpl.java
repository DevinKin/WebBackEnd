package com.devinkin.demo2;

public class UserServiceImpl implements UserService{

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void sayHello() {
        System.out.println("Hello Spring!!" + name);
    }

    public void init() {
        // 编写任何代码
        System.out.println("初始化...");
    }

    public void destory() {
        System.out.println("销毁...");
    }
}
