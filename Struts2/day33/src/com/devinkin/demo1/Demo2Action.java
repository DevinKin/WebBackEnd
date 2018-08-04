package com.devinkin.demo1;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 原生ServletAPI方式
 */
public class Demo2Action extends ActionSupport{
    @Override
    public String execute() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("msg","小东东");
        request.getSession().setAttribute("msg", "美美");
        request.getServletContext().setAttribute("msg", "小风");

        HttpServletResponse response = ServletActionContext.getResponse();
        // 使用输出流，输出内容

        return SUCCESS;
    }
}
