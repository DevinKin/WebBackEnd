package com.devinkin.test;

import com.devinkin.domain.Customer;
import com.devinkin.domain.Linkman;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * QBC的查询
 * @author king
 */
public class Demo3 {

    /**
     * 演示离线条件对象
     */
    @Test
    public void run10() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        // 创建离线条件查询的对象
        DetachedCriteria criteria = DetachedCriteria.forClass(Linkman.class);
        // 设置查询条件
        criteria.add(Restrictions.eq("lkm_gender", "女"));

        // 查询
        List<Linkman> list = criteria.getExecutableCriteria(session).list();
        for (Linkman linkman : list) {
            System.out.println(linkman);
        }

        tr.commit();
    }

    /**
     * 强调问题：select count(*) from 表,又想查 select * from 表，存在问题
     */
    @Test
    public void run8() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        // 创建QBC查询接口
        Criteria criteria = session.createCriteria(Linkman.class);
        // 设置聚合函数的方式
        List<Number> list = criteria.setProjection(Projections.count("lkm_id")).list();
        System.out.println(list.get(0).longValue());

        // 继续查询所有的联系人
        // 在设置一遍setProjection方法
        criteria.setProjection(null);
        List<Linkman> mans = criteria.list();
        for (Linkman linkman : mans) {
            System.out.println(linkman);
        }
        tr.commit();
    }

    /**
     * 聚合函数的查询
     */
    @Test
    public void run7() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        // 创建QBC查询接口
        Criteria criteria = session.createCriteria(Linkman.class);
        // 设置聚合函数的方式
        List<Number> list = criteria.setProjection(Projections.count("lkm_id")).list();
        System.out.println(list.get(0).longValue());
        tr.commit();
    }

    /**
     * 判断值是否为空
     */
    @Test
    public void run6() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        // 创建QBC查询接口
        Criteria criteria = session.createCriteria(Linkman.class);
        // 使用方法添加条件，默认使用and拼接条件
        criteria.add(Restrictions.isNotNull("lkm_email"));

        List<Linkman> list = criteria.list();
        for (Linkman l : list) {
            System.out.println(l);
        }
        tr.commit();
    }

    /**
     * QBC的条件查询，or方法
     */
    @Test
    public void run5() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        // 创建QBC查询接口
        Criteria criteria = session.createCriteria(Linkman.class);
        // 使用方法添加条件，默认使用and拼接条件
        // SQL: select * from cst_likman where lkm_gender = '女' or lkm_id > 3
        criteria.add(Restrictions.or(Restrictions.eq("lkm_gender","女"),
                Restrictions.gt("lkm_id", 3L)));
        List<Linkman> list = criteria.list();
        for (Linkman l : list) {
            System.out.println(l);
        }
        tr.commit();
    }

    /**
     * QBC的条件查询
     */
    @Test
    public void run4() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        // 创建QBC查询接口
        Criteria criteria = session.createCriteria(Linkman.class);
        // 使用方法添加条件，默认使用and拼接条件
//        criteria.add(Restrictions.eq("lkm_gender", "男"));
//        criteria.add(Restrictions.gt("lkm_id", 3L));
//        criteria.add(Restrictions.between("lkm_id", 2L, 5L));
        // 使用in方法查询
        List<Long> params = new ArrayList<>();
        params.add(1L);
        params.add(2L);
        params.add(7L);
        criteria.add(Restrictions.in("lkm_id", params));
        List<Linkman> list = criteria.list();
        for (Linkman l : list) {
            System.out.println(l);
        }
        tr.commit();
    }

    /**
     * QBC的分页查询
     */
    @Test
    public void run3() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        // 创建QBC查询接口
        Criteria criteria = session.createCriteria(Linkman.class);
        criteria.addOrder(Order.desc("lkm_id"));
        criteria.setFirstResult(0);
        criteria.setMaxResults(3);
        List<Linkman> list = criteria.list();
        for (Linkman l : list) {
            System.out.println(l);
        }
        tr.commit();
    }

    /**
     * QBC的排序查询
     */
    @Test
    public void run2() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        // 创建QBC查询接口
        Criteria criteria = session.createCriteria(Linkman.class);
        criteria.addOrder(Order.desc("lkm_id"));
        List<Linkman> list = criteria.list();
        for (Linkman l : list) {
            System.out.println(l);
        }
        tr.commit();
    }

    /**
     * QBC的基本入门查询
     */
    @Test
    public void run1() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        // 创建QBC查询接口
        Criteria criteria = session.createCriteria(Customer.class);
        List<Customer> list = criteria.list();
        for (Customer c : list) {
            System.out.println(c);
        }
        tr.commit();
    }
}
