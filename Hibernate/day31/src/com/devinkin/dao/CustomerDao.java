package com.devinkin.dao;

import com.devinkin.domain.Customer;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CustomerDao {

    /**
     * 添加客户
     *
     * @param c 客户对象
     */
    public void add(Customer c) {
        //先获取Session
        Session session = HibernateUtils.getSession();

        //开启事务
        Transaction tr = session.beginTransaction();
        try {
            //添加用户
            session.save(c);
            //提交事务
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        } finally {
            //释放资源
            session.close();
        }
    }

    /**
     * 带条件查找所有Customer
     *
     * @return List<Customer> 客户列表
     * @param custName
     */
    public List<Customer> show(String custName) {
        //先获取Session
        Session session = HibernateUtils.getSession();

        //开启事务
        Transaction tr = session.beginTransaction();
        //获取query对象
//        Query query = session.createQuery("from Customer");
//        List<Customer> customerList = query.list();
        // QBC查询
        Criteria criteria = session.createCriteria(Customer.class);

        // 添加查询的条件
        if (custName != null && !custName.trim().isEmpty()) {
            //添加查询条件
            criteria.add(Restrictions.like("cust_name","%" + custName + "%"));
        }
        List<Customer> customerList = criteria.list();

        tr.commit();
        //释放资源
        session.close();
        return customerList;
    }

    /**
     * 通过id查找客户
     *
     * @param id 客户id
     * @return customer 客户
     */
    public Customer findById(Long id) {
        Session session = HibernateUtils.getCurrentSession();

        return session.get(Customer.class, id);
    }

    /**
     * 编辑客户信息
     *
     * @param c  客户
     * @param id id
     */
    public void edit(Customer c, Long id) {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();
        Customer customer = findById(id);
        try {
            if (customer != null) {
                customer.setCust_name(c.getCust_name());
                customer.setCust_level(c.getCust_level());
                customer.setCust_source(c.getCust_source());
                customer.setCust_linkman(c.getCust_linkman());
                customer.setCust_phone(c.getCust_phone());
                customer.setCust_mobile(c.getCust_mobile());
            }
            session.update(customer);
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
        } finally {
            session.close();
        }
    }

    /**
     * 删除客户
     *
     * @param id 删除客户
     */
    public void delete(Long id) {
        Session session = HibernateUtils.getSession();

        Transaction tr = session.beginTransaction();

        Customer c = null;
        try {
            c = session.get(Customer.class, id);
            if (c != null) {
                session.delete(c);
                tr.commit();
            }
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
