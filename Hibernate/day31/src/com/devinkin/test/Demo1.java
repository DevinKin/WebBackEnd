package com.devinkin.test;

import com.devinkin.domain.Customer;
import com.devinkin.domain.Linkman;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * 演示对象导航的方式
 * @author king
 */
public class Demo1 {

    /**
     * 查询联系人，属于某一个客户
     */
    @Test
    public void run2() {
        // 先查询1号客户
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();
        // 先使用唯一标识OID查询客户
        Linkman linkman = session.get(Linkman.class, 1L);
        System.out.println("=========================");
        // 查看该客户下的联系人的集合
        System.out.println(linkman.getCustomer().getCust_name());
        tr.commit();
    }


    /**
     * 对象导航的方式
     */
    @Test
    public void run1() {
        // 先查询1号客户
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();
        // 先使用唯一标识OID查询客户
        Customer c = session.get(Customer.class, 1L);
        System.out.println("=========================");
        // 查看该客户下的联系人的集合
        System.out.println(c.getLinkmans().size());
        tr.commit();
    }
}
