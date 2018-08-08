package com.devinkin.demo3;

public class CustomerServiceImpl {

    // 提供成员属性，提供set方法
    private CustomerDaoImpl customerDao;

    public void setCustomerDao(CustomerDaoImpl customerDao) {
        this.customerDao = customerDao;
    }

    public void save() {
        System.out.println("我是业务层的service...");
        // 原来编写的方式
//        new CustomerDaoImpl().save();

        //Spring的方式
        customerDao.save();
    }
}
