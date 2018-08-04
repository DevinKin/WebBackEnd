package com.devinkin.demo2;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 属性驱动方式，把数据封装到JavaBean的对象中
 */
public class Regist2Action extends ActionSupport{
    //注意二：属性驱动的方式，现在要提供的是get和set方法
    private User user = new User();

    public User getUser() {
        System.out.println("getUser...");
        return user;
    }

    public void setUser(User user) {
        System.out.println("setUser...");
        this.user = user;
    }


    @Override
    public String execute() throws Exception {
        System.out.println(user);
        return NONE;
    }
}
