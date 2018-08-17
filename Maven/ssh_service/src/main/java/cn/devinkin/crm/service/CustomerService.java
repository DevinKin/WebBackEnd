package cn.devinkin.crm.service;

import cn.devinkin.crm.domain.Customer;
import cn.devinkin.crm.domain.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface CustomerService {
    public void save(Customer customer);

    PageBean<Customer> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria);

    Customer findById(Long cust_id);

    void delete(Customer customer);

    void update(Customer customer);

    List<Customer> findAll();

    List<Object[]> findBySource();

    List<Object[]> findByIndustry();
}
