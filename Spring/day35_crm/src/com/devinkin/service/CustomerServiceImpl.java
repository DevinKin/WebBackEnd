package com.devinkin.service;

import com.devinkin.dao.CustomerDao;

public class CustomerServiceImpl implements CustomerService{

    private CustomerDao customerDao;

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }
    @Override
    public void save() {
        System.out.println("业务层：保存客户...");
        customerDao.save();
    }
}
