package com.devinkin.service;

import com.devinkin.dao.CustomerDao;
import com.devinkin.domain.Customer;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CustomerServce {

    /**
     * 添加客户
     * @param c 客户对象
     */
    public void addCustomer(Customer c) {
        new CustomerDao().add(c);
    }


    /**
     * 带条件获取客户列表
     * @return List<Customer> 客户列表
     * @param custName
     */
    public List<Customer> showCustomers(String custName) {
        return new CustomerDao().show(custName);
    }

    /**
     * 通过id查找客户
     * @param id id值
     * @return Customer 客户
     */
    public Customer findCustomerById(Long id) {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();
        Customer customer = new CustomerDao().findById(id);
        tr.commit();
        return customer;
    }

    /**
     * 编辑客户信息
     * @param c 客户信息
     * @param id 客户id
     */
    public void editCustomer(Customer c, Long id) {
        new CustomerDao().edit(c, id);
    }

    /**
     * 删除客户
     * @param id 客户id
     */
    public void deleteCustomer(Long id) {
        new CustomerDao().delete(id);
    }
}
