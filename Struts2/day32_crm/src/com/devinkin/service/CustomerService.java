package com.devinkin.service;

import com.devinkin.dao.CustomerDao;
import com.devinkin.domain.Customer;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.junit.Test;

import java.util.List;

public class CustomerService {

    /**
     * 保存客户
     * @param customer 客户
     */
    public void saveCustomer(Customer customer) {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        // 调用Dao层
        new CustomerDao().save(customer);

        tr.commit();
    }


    /**
     * 查询所有的客户
     * @param criteria
     */
    public List<Customer> findAll(DetachedCriteria criteria) {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();
        // 调用业务层
        List<Customer> list = new CustomerDao().findAll(criteria);
        tr.commit();
        return list;
    }


    @Test
    public void run1() {
        Customer customer = new Customer();
        customer.setCust_name("测试");
        this.saveCustomer(customer);
    }
}
