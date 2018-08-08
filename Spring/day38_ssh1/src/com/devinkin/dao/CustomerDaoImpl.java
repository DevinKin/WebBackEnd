package com.devinkin.dao;


import com.devinkin.domain.Customer;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

/**
 * 持久层
 * @author king
 */
public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDao{

//    private HibernateTemplate hibernateTemplate;
//
//    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
//        this.hibernateTemplate = hibernateTemplate;
//    }

    /**
     * 保存客户
     * @param customer 客户对象
     */
    @Override
    public void save(Customer customer) {
        System.out.println("持久层：保存客户...");
        System.out.println(customer);

        // 报数据保存到数据库中
        this.getHibernateTemplate().save(customer);
    }

    /**
     * 修改客户
     * @param customer 客户对象
     */
    @Override
    public void update(Customer customer) {
        this.getHibernateTemplate().update(customer);
    }

    /**
     * 通过id查询客户
     * @param id 主键id
     * @return
     */
    @Override
    public Customer getById(Long id) {
        return this.getHibernateTemplate().get(Customer.class, id);
    }


    /**
     * 查询所有客户
     * @return 客户列表
     */
    @Override
    public List<Customer> findAll() {
        return (List<Customer>) this.getHibernateTemplate().find("from Customer");
    }


    /**
     * 通过QBC查询所有客户
     * @return 客户列表
     */
    @Override
    public List<Customer> findAllByQBC() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
        // 设置查询条件
        List<Customer> list = (List<Customer>) this.getHibernateTemplate().findByCriteria(criteria);
        return list;
    }


    /**
     * 演示延迟加载查询某一客户
     * @param id 客户id
     * @return
     */
    @Override
    public Customer loadById(long id) {
        return this.getHibernateTemplate().load(Customer.class, id);
    }
}
