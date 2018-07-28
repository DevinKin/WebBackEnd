package com.devinkin.test;

import com.devinkin.domain.Linkman;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * SQL的查询方式
 */
public class Demo4 {

    /**
     * 把数据封装到对象中
     */
    @Test
    public void run2() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        // 创建的是SQL的查询接口
        SQLQuery sqlQuery = session.createSQLQuery("select *  from cst_linkman");
        sqlQuery.addEntity(Linkman.class);
        // 查询数据
        List<Linkman> list = sqlQuery.list();
        for (Linkman linkman : list) {
            System.out.println(linkman);
        }
    }

    /**
     * 测试SQL语句的查询
     */
    @Test
    public void run1() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        // 创建的是SQL的查询接口
        SQLQuery sqlQuery = session.createSQLQuery("select *  from cst_linkman");
        // 查询数据
        List<Object[]> list = sqlQuery.list();
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }
}

