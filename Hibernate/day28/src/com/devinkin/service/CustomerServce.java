package com.devinkin.service;

import com.devinkin.dao.CustomerDao;
import com.devinkin.domain.Customer;

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
    public Customer findCustomerById(String id) {
        return new CustomerDao().findById(id);
    }

    /**
     * 编辑客户信息
     * @param c 客户信息
     * @param id 客户id
     */
    public void editCustomer(Customer c, String id) {
        new CustomerDao().edit(c, id);
    }

    /**
     * 删除客户
     * @param id 客户id
     */
    public void deleteCustomer(String id) {
        new CustomerDao().delete(id);
    }
}
