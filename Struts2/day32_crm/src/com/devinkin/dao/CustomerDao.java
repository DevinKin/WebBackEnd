package com.devinkin.dao;

import com.devinkin.domain.Customer;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public class CustomerDao {
    /**
     * 保存客户
     * @param customer
     */
    public void save(Customer customer) {
        Session session = HibernateUtils.getCurrentSession();
        session.save(customer);
    }

    /**
     * 查询所有客户
     * @param criteria
     */
    public List<Customer> findAll(DetachedCriteria criteria) {
        Session session = HibernateUtils.getCurrentSession();
        Criteria criteria1 = criteria.getExecutableCriteria(session);
        return criteria1.list();
    }
}
