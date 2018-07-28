package com.devinkin.test;


import com.devinkin.domain.Customer;
import com.devinkin.domain.Linkman;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * 测试一对多
 * @author king
 */
public class Demo1 {

    /**
     * cascade和inverse的区别
     */
    @Test
    public void run12() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //级联保存
        //保存客户和联系人的数据
        Customer c1 = new Customer();
        c1.setCust_name("美美");

        // 创建两个联系人
        Linkman l1 = new Linkman();
        l1.setLkm_name("熊大");
        Linkman l2 = new Linkman();
        l2.setLkm_name("熊二");

        //单向关联
//        c1.getLinkmans().add(l1);
//        c1.getLinkmans().add(l2);

        l1.setCustomer(c1);
        l2.setCustomer(c1);

        // 保存数据
        session.save(l1);
        session.save(l2);

//        session.save(c1);

        tr.commit();
    }

    /**
     * 放弃外键的维护
     * 需求：让熊达昂联系人属于小风客户
     */
    @Test
    public void run11() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //先获取小风的客户
        Customer c2 = session.get(Customer.class, 2L);

        Linkman l1 = session.get(Linkman.class, 1L);

        //做双向关联
        c2.getLinkmans().add(l1);
        l1.setCustomer(c2);
        //不用修改，持久化对象

        tr.commit();
    }


    /**
     * 解除关系：从集合中删除联系人
     */
    @Test
    public void run10() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //先获取到客户
        Customer c1 = session.get(Customer.class, 1L);
        //获取联系人
        Linkman l1 = session.get(Linkman.class, 1L);

        //解除客户与联系人的关系
        c1.getLinkmans().remove(l1);

        tr.commit();
    }

    /**
     * 测试级联删除，删除联系人，级联删除客户
     */
    @Test
    public void run9() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        Linkman linkman = session.get(Linkman.class, 1L);
        session.delete(linkman);

        tr.commit();
    }

    /**
     * 测试级联删除，删除联系人，级联删除客户
     */
    @Test
    public void run8() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        Linkman linkman = session.get(Linkman.class, 1L);
        session.delete(linkman);

        tr.commit();
    }

    /**
     * 测试级联删除，删除客户，级联客户下的联系人
     */
    @Test
    public void run7() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        Customer c1 = session.get(Customer.class, 1L);
        session.delete(c1);

        tr.commit();
    }

    /**
     * 测试：删除客户，客户下有2个联系人
     */
    @Test
    public void run6() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        Customer c1 = session.get(Customer.class, 1L);
        session.delete(c1);

        tr.commit();
    }

    /**

    /**
     * 级联保存，保存联系人，级联客户
     */
    @Test
    public void run5() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //保存客户和联系人的数据
        Customer c1 = new Customer();
        c1.setCust_name("美美");

        //创建两个联系人
        Linkman l1 = new Linkman();
        l1.setLkm_name("熊昂达");

        Linkman l2 = new Linkman();
        l2.setLkm_name("熊二");

        // 使用联系人关联客户，联系人配置了级联
        l1.setCustomer(c1);
        // 使用客户关联联系人，客户配置了级联
        c1.getLinkmans().add(l2);
        //保存数据
        session.save(l1);

        tr.commit();
    }

    /**
     * 级联保存，保存联系人，级联客户
     */
    @Test
    public void run4() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //保存客户和联系人的数据
        Customer c1 = new Customer();
        c1.setCust_name("美美");

        //创建两个联系人
        Linkman l1 = new Linkman();
        l1.setLkm_name("熊昂达");

        Linkman l2 = new Linkman();
        l2.setLkm_name("熊二");

        // 使用联系人关联客户
        l1.setCustomer(c1);
        l2.setCustomer(c1);


        //保存数据
        session.save(l1);
        //没有保存2号联系人
        session.save(l2);

        tr.commit();
    }

    /**
     * 级联保存，保存客户，级联联系人
     */
    @Test
    public void run3() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //保存客户和联系人的数据
        Customer c1 = new Customer();
        c1.setCust_name("美美");

        //创建两个联系人
        Linkman l1 = new Linkman();
        l1.setLkm_name("熊昂达");

        Linkman l2 = new Linkman();
        l2.setLkm_name("熊二");

        // 使用客户关联联系人
        c1.getLinkmans().add(l1);
        c1.getLinkmans().add(l2);


        //保存数据
        session.save(c1);

        tr.commit();
    }

    /**
     * 单向关联，如果不配置级联保存，程序出现异常
     */
    @Test
    public void run2() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //保存客户和联系人的数据
        Customer c1 = new Customer();
        c1.setCust_name("美美");

        //创建两个联系人
        Linkman l1 = new Linkman();
        l1.setLkm_name("熊昂达");

        Linkman l2 = new Linkman();
        l2.setLkm_name("熊二");

        // 演示单向关联
        c1.getLinkmans().add(l1);
        c1.getLinkmans().add(l2);


        //保存数据
        session.save(c1);

        tr.commit();

    }

    /**
     * 最麻烦的双向关联的方式，保存数据
     */
    @Test
    public void run1() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        //保存客户和联系人的数据
        Customer c1 = new Customer();
        c1.setCust_name("美美");

        //创建两个联系人
        Linkman l1 = new Linkman();
        l1.setLkm_name("熊昂达");

        Linkman l2 = new Linkman();
        l2.setLkm_name("熊二");

        // 演示双向关联
        c1.getLinkmans().add(l1);
        c1.getLinkmans().add(l2);

        l1.setCustomer(c1);
        l2.setCustomer(c1);

        //保存数据
        session.save(c1);
        session.save(l1);
        session.save(l2);

        tr.commit();
    }
}
