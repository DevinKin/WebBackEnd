package com.devinkin.service;

import com.devinkin.dao.CustomerDao;
import com.devinkin.domain.Customer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客户的业务层
 * @author king
 */
@Transactional
public class CustomerServiceImpl implements CustomerService{

    private CustomerDao customerDao;

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    /**
     * 保存客户
     */
    @Override
    public void save(Customer customer) {
        System.out.println("业务层：保存客户...");
        customerDao.save(customer);
    }

    /**
     * 修改客户
     * @param customer 客户对象
     */
    @Override
    public void update(Customer customer) {
        customerDao.update(customer);
    }

    /**
     * 查询客户
     * @param id 客户id主键
     * @return
     */
    @Override
    public Customer getCustomerById(Long id) {
        return customerDao.getById(id);
    }

    /**
     * 查询所有客户
     * @return 所有客户对象
     */
    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }


    /**
     * 通过QBC查询所有客户
     * @return 客户列表
     */
    @Override
    public List<Customer> findAllByQBC() {
        return customerDao.findAllByQBC();
    }


    /**
     * 延迟加载查询客户
     * @param id 客户id
     * @return
     */
    @Override
    public Customer loadById(long id) {
        return customerDao.loadById(id);
    }
}
