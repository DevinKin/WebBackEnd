package com.devinkin.test;

import com.devinkin.domain.Customer;
import com.devinkin.domain.Linkman;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 演示HQL的基本查询
 * @author king
 */
public class Demo2 {

    /**
     * 聚合函数：求数量
     */
    @Test
    public void run10() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //查询联系人
        Query query = session.createQuery("select sum(lkm_id) from Linkman");

        //查询所有的联系人的数量
        List<Number> list = query.list();
        System.out.println(list.get(0).longValue());
    }

    /**
     * 聚合函数：count() sum() avg() max() min()
     */
    @Test
    public void run9() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //查询联系人
//        Query query = session.createQuery("select count(l) from Linkman l");
        Query query = session.createQuery("select count(*) from Linkman");

        //查询所有的联系人的数量
        List<Number> list = query.list();
        System.out.println(list.get(0).longValue());
    }

    /**
     * 投影查询：只查询几个字段，不是所有的字段
     * 第一步：需要在JavaBean类提供对应的构造方法
     * 第二步：HQL语句需要发生变化
     */
    @Test
    public void run8() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //查询联系人
        Query query = session.createQuery("select new Linkman(lkm_name, lkm_gender) :21,from Linkman");

        List<Linkman> list = query.list();
        for (Linkman linkman: list) {
            System.out.println(linkman);
        }
    }

    /**
     * 投影查询：只查询几个字段，不是所有的字段
     */
    @Test
    public void run7() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //查询联系人
        Query query = session.createQuery("select lkm_name, lkm_gender from Linkman");

        List<Object[]> list = query.list();
        for (Object[] objects: list) {
            System.out.println(Arrays.toString(objects));
        }
    }

    /**
     * 按条件进行查询
     */
    @Test
    public void run6() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //查询联系人
//        Query query = session.createQuery("from Linkman l where l.lkm_gender = ?");
//        Query query = session.createQuery("from Linkman l where l.lkm_gender = :gender");
        Query query = session.createQuery("from Linkman l where l.lkm_id > ? and lkm_gender = :4?");
        // 传入值
//        query.setString(0,"男");
//        query.setString("gender", "女");
//        query.setLong(0, 2L);
        //通用的方法，就不用再判断具体的类型
        query.setParameter(0,3L);
        query.setParameter(1,"女");

        // 分页查询，调用方法，查询第一页的数据1-3条
        List<Linkman> list = query.list();
        for (Linkman linkman : list) {
            System.out.println(linkman);
        }
    }

    /**
     * HQL分页查询的两个方法
     * setFirstResult(a)
     * setMaxResults(b)
     */
    @Test
    public void run5() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //查询联系人
        Query query = session.createQuery("from Linkman l order by l.lkm_id desc");

        // 分页查询，调用方法，查询第一页的数据1-3条
        query.setFirstResult(3);
        query.setMaxResults(3);
        List<Linkman> list = query.list();
        for (Linkman linkman : list) {
            System.out.println(linkman);
        }
    }

    /**
     * 排序查询
     * SQL：order by 字段 asc/desc
     * HQL：关键字是一样的，都是有order by 属性
     */
    @Test
    public void run4() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //查询联系人
        List<Linkman> list = session.createQuery("from Linkman l order by l.lkm_id desc").list();
        for (Linkman linkman : list) {
            System.out.println(linkman);
        }


    }

    /**
     * 是有别名的方式
     * select * from cst_customer c as c
     * select * from Customer 是错误的
     */
    @Test
    public void run3() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        // 创建HQL的查询接口
        List<Customer> list = session.createQuery("select c from Customer c").list();
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    /**
     * 支持方法链的编程风格
     */
    @Test
    public void run2() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        // 创建HQL的查询接口
        List<Customer> list = session.createQuery("from Customer").list();
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    /**
     * 基本的演示
     */
    @Test
    public void run1() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        // 创建HQL的查询接口
        Query query = session.createQuery("from Customer");
        // 调用list
        List<Customer> list =  query.list();
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }
}
