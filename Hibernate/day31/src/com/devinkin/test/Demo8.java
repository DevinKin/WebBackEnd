package com.devinkin.test;

import com.devinkin.domain.Linkman;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

public class Demo8 {

    /**
     * fetch: join
     * lazy: 值是什么效果都一样
     */
    @Test
    public void run4() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();
        // 查询联系人
        Linkman l1 = session.get(Linkman.class, 1L);
        // 联系人的客户的名称
        System.out.println(l1.getCustomer().getCust_name());

        tr.commit();
    }

    /**
     * fetch: select
     * lazy: proxy
     */
    @Test
    public void run3() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();
        // 查询联系人
        Linkman l1 = session.get(Linkman.class, 1L);
        // 联系人的客户的名称
        System.out.println(l1.getCustomer().getCust_name());

        tr.commit();
    }

    /**
     * fetch: select
     * lazy: false,不是延迟加载
     */
    @Test
    public void run2() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();
        // 查询联系人
        Linkman l1 = session.get(Linkman.class, 1L);
        // 联系人的客户的名称
        System.out.println(l1.getCustomer().getCust_name());

        tr.commit();
    }

    /**
     * 默认值
     * fetch: select
     * lazy: 是延迟加载
     */
    @Test
    public void run1() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();
        // 查询联系人
        Linkman l1 = session.get(Linkman.class, 1L);
        // 联系人的客户的名称
        System.out.println(l1.getCustomer().getCust_name());

        tr.commit();
    }
}
