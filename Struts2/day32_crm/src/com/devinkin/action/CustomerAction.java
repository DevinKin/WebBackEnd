package com.devinkin.action;

import com.devinkin.domain.Customer;
import com.devinkin.service.CustomerService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{

    private Customer customer = new Customer();

    /**
     * 保存客户
     * @return
     */
    public String save() {
        // 保存客户
        new CustomerService().saveCustomer(customer);
        return SUCCESS;
    }


    @Override
    public Customer getModel() {
        return customer;
    }


    /**
     * 查询所有客户
     * @return
     */
    public String list() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
        HttpServletRequest request = ServletActionContext.getRequest();
        String cust_name = request.getParameter("cust_name");
        if (cust_name != null && !cust_name.trim().isEmpty()) {
            criteria.add(Restrictions.like("cust_name","%" + cust_name + "%"));
        }
        List<Customer> clist = new CustomerService().findAll(criteria);

        // 把clist压入的奥值栈中
        // 压栈，默认的规范，压入的是集合，一般使用set方法，压入是对象，使用对象
        ValueStack vs = ActionContext.getContext().getValueStack();
        vs.set("clist", clist);
        return SUCCESS;
    }
}
