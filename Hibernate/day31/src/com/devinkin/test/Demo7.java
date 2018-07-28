package com.devinkin.test;

import com.devinkin.domain.Customer;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.List;

/**
 * 查询的策略
 *
 * @author king
 */
public class Demo7 {

    /**
     * 默认值：fetch：subselect，子查询
     * lazy：false，不延迟加载的
     */
    @SuppressWarnings("all")
    @Test
    public void run6() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();
        List<Customer> list = session.createQuery("from Customer").list();
        for (Customer customer : list) {
            System.out.println(customer.getLinkmans().size());
        }
        tr.commit();
    }


    /**
     * 默认值：fetch：subselect，子查询
     * lazy：true，默认是延迟加载的
     */
    @SuppressWarnings("all")
    @Test
    public void run5() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();
        List<Customer> list = session.createQuery("from Customer").list();
        for (Customer customer : list) {
            System.out.println(customer.getLinkmans().size());
        }
        tr.commit();
    }


    /**
     * 默认值：fetch：join，采用的是迫切左连接进行的查询
     * lazy：什么值，都是一样的效果
     */
    @Test
    public void run4() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();
        // 先查询1号客户
        Customer c1 = session.get(Customer.class, 1L);
        //查看客户下所有的联系人
        System.out.println(c1.getLinkmans().size());
        tr.commit();
    }

    /**
     * 默认值：fetch：select，默认的SQL语句的格式
     * lazy：extra，极其懒惰的
     */
    @Test
    public void run3() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();
        // 先查询1号客户
        Customer c1 = session.get(Customer.class, 1L);
        //查看客户下所有的联系人
        System.out.println(c1.getLinkmans().size());
        tr.commit();
    }

    /**
     * 默认值：fetch：select，默认的SQL语句的格式
     * lazy：false：不延迟加载
     */
    @Test
    public void run2() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();
        // 先查询1号客户
        Customer c1 = session.get(Customer.class, 1L);
        //查看客户下所有的联系人
        System.out.println(c1.getLinkmans().size());
        tr.commit();
    }

    /**
     * 默认值：fetch：select,lazy：true
     */
    @Test
    public void run1() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();
        // 先查询1号客户
        Customer c1 = session.get(Customer.class, 1L);
        //查看客户下所有的联系人
        System.out.println(c1.getLinkmans().size());
        tr.commit();
    }
}
