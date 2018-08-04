package com.devinkin.demo3;

import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport{
    @Override
    public String execute() throws Exception {
        System.out.println("我是Action，我正常执行...");
        return NONE;
    }
}
