package com.devinkin.demo1;

import com.opensymphony.xwork2.ActionSupport;

public class Demo3Action extends ActionSupport{
    /**
     *
     */
    public String save() {
        System.out.println("save...");
        return SUCCESS;
    }

    public String update() {
        System.out.println("update...");
        return SUCCESS;
    }
}
