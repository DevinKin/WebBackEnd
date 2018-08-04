package com.devinkin.action1;


import com.opensymphony.xwork2.Action;

/**
 * 实现Action接口，Action是框架提供的接口
 * @author king
 */
public class Demo2Action implements Action{

    @Override
    public String execute() throws Exception {
        System.out.println("Demo2Action实现了Action接口");
        // return "success"
        return LOGIN;
    }
}
