package com.devinkin.demo2;

import com.opensymphony.xwork2.ActionSupport;

public class Regist1Action extends ActionSupport{
    private String username;
    private String password;
    private Integer age;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String execute() throws Exception {
        System.out.println(username + " " + password + " " + age);
        return NONE;
    }
}
