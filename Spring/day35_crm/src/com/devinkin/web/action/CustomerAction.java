package com.devinkin.web.action;

import com.devinkin.service.CustomerService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * 客户的Action
 * @author king
 */
public class CustomerAction extends ActionSupport{

    /**
     * 保存客户
     * @return
     */
    public String save() {
        System.out.println("WEB层：保存客户...");

        // 使用工厂
//        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        ServletContext servletContext = ServletActionContext.getServletContext();
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        CustomerService cs = (CustomerService) context.getBean("customerService");
        cs.save();
        return NONE;
    }
}
