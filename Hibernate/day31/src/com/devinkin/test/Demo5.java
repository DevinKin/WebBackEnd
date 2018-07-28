package com.devinkin.test;

import com.devinkin.domain.Customer;
import com.devinkin.domain.Linkman;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * HQL多表查询
 * @author king
 */
public class Demo5 {

    /**
     * 数据的重复的问题
     */
    @Test
    public void run3() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //内连接的写法
        Query query = session.createQuery("from Customer c inner join fetch c.linkmans");
        List<Customer> list = query.list();
        //手动解决，编程都使用这种方式解决重复的问题
        Set<Customer> set = new HashSet<Customer>(list);
        for (Customer customer : set) {
            System.out.println(customer);
        }
        tr.commit();
    }

    /**
     * 数据默认返回的是数组，把数据封装到对象中
     * 提供关键字：fetch 迫切连接
     */
    @Test
    public void run2() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //内连接的写法
        Query query = session.createQuery("from Customer c inner join fetch c.linkmans");
        List<Customer> list = query.list();
        for (Customer customer : list) {
            System.out.println(customer);
        }
        tr.commit();
    }

    /**
     * 查询的客户，客户的联系人有关联
     * select * from cst_customer c, cst_linkman l where c.cid = l.cid
     */
    @Test
    public void run1() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //内连接的写法
        Query query = session.createQuery("from Customer c inner join c.linkmans");
        List<Object[]> list = query.list();
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
        tr.commit();
    }
}
