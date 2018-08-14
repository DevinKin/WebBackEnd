package com.devinkin.service;

import com.devinkin.dao.CustomerDao;
import com.devinkin.domain.Customer;
import com.devinkin.domain.PageBean;
import org.hibernate.criterion.DetachedCriteria;
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
        customerDao.save(customer);
    }


    /**
     * 分页查询客户
     * @param pageCode 当前页码
     * @param pageSize 每页数量
     * @param criteria 离线条件查询对象
     * @return
     */
    @Override
    public PageBean<Customer> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria) {
        return customerDao.findByPage(pageCode,pageSize,criteria);
    }


    /**
     * 通过主键，查询客户
     * @param cust_id 主键
     * @return
     */
    @Override
    public Customer findById(Long cust_id) {
        return customerDao.findById(cust_id);
    }


    /**
     * 删除客户
     * @param customer 客户
     */
    @Override
    public void delete(Customer customer) {
        customerDao.delete(customer);
    }


    /**
     * 更新客户
     * @param customer 客户对象
     */
    @Override
    public void update(Customer customer) {
        customerDao.update(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }


    /**
     * 查找客户来源人数
     * @return
     */
    @Override
    public List<Object[]> findBySource() {
        return customerDao.findBySource();
    }


    /**
     * 查找客户行业人数
     * @return
     */
    @Override
    public List<Object[]> findByIndustry() {
        return customerDao.findByIndustry();
    }

}
