package cn.devinkin.web.action;

import com.opensymphony.xwork2.ActionSupport;

public class HelloAction extends ActionSupport{
    @Override
    public String execute() throws Exception {
        System.out.println("Hello Maven and Struts2");
        return SUCCESS;
    }
}
