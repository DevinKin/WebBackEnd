package com.devinkin.web.action;

import com.devinkin.domain.Customer;
import com.devinkin.service.CustomerService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 客户的控制层
 * @author king
 */
public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{

    // 提供service成员属性，提供service的set方法，供Spring框架注入
    private CustomerService customerService;

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * 保存客户的方法
     * @return
     */
    public String add() {
        System.out.println("WEB层：保存客户...");
//        System.out.println(customer);
//        WEB的工厂
//        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
//        CustomerService cs = (CustomerService) context.getBean("customerService");
//        cs.save();

        customerService.save(customer);
        return NONE;
    }


    /**
     * 演示延迟加载的问题
     * @return
     */
    public String loadById() {
        Customer c = customerService.loadById(2L);
        // 打印客户的名称
        System.out.println(c.getCust_name());
        return NONE;
    }


    // 手动创建模型驱动对象
    private Customer customer = new Customer();
    @Override
    public Customer getModel() {
        return customer;
    }
}
