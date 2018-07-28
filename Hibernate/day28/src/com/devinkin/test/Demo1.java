package com.devinkin.test;

import com.devinkin.domain.Customer;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import java.util.List;

/**
 * 测试HIbernate框架
 * @author king
 */
public class Demo1 {

    /**
     * 测试保存客户
     */
    @Test
    public void testSave() {
        /**
         * 1. 先加载配置文件
         * 2. 创建SessionFactory对象，生成Session对象
         * 3. 创建Session对象
         * 4. 开启事务
         * 5. 编写保存的代码
         * 6. 提交事务
         * 7. 释放资源
         */

        //1. 先加载配置文件
        Configuration config = new Configuration();
        //默认加载src目录下hibernate.cfg.xml的配置文件
        config.configure();
        // 手动加载配置文件
        //config.addResource("com/devinkin/domain/Customer.hbm.xml");

        //2. 创建SessionFactory对象，生成Session对象
        SessionFactory factory = config.buildSessionFactory();

        //3. 创建Session对象
        Session session = factory.openSession();

        //4. 开启事务
        Transaction tr = session.beginTransaction();

        //5. 编写保存的代码
        Customer customer = new Customer();
        //customer.setCust_id();    主键是自动递增了
        customer.setCust_name("测试");
        customer.setCust_level("2");
        customer.setCust_phone("110");
//        customer.setInfo("info");

        //保存数据，操作对象就相当于操作数据库的表结构
        session.save(customer);

        //6. 提交事务
        tr.commit();

        //7. 释放资源
        session.close();
        factory.close();
    }

    @Test
    public void testSave2() {
        //加载配置文件，获取工厂对象，获取Session
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        Customer c = new Customer();
        c.setCust_name("小风");
        session.save(c);
        tr.commit();
        session.close();
    }


    /**
     * 测试get()方法，获取查询，通过主键来查询一条记录
     */
    @Test
    public void testGet() {
        Session session = HibernateUtils.getSession();

        Transaction tr = session.beginTransaction();

        //测试查询的方法，两个参数：arg0查询JavaBean的class对象，arg1为主键的值
        Customer c = session.get(Customer.class, 7L);
        System.out.println(c);
        tr.commit();
        session.close();
    }

    /**
     * 测试删除的方法
     * 注意：删除或修改，先查询再删除或者修改
     */
    @Test
    public void testDelete() {
        Session session = HibernateUtils.getSession();

        Transaction tr = session.beginTransaction();

        Customer c = session.get(Customer.class, 7L);

        session.delete(c);

        tr.commit();
        session.close();
    }

    /**
     * 测试修改的方法
     * 注意：先查询，后修改
     */
    @Test
    public void testMod() {
        Session session = HibernateUtils.getSession();

        Transaction tr = session.beginTransaction();

        Customer c = session.get(Customer.class, 6L);
        c.setCust_name("小苍");
        c.setCust_level("3");

        session.update(c);

        tr.commit();
        session.close();
    }


    /**
     * 测试添加或修改
     */
    @Test
    public void testAddOrSave() {
        Session session = HibernateUtils.getSession();

        Transaction tr = session.beginTransaction();

        //演示错误
//        Customer c = new Customer();

//        c.setCust_id(10L); //千万不能自己设置
//        c.setCust_name("测试");

        Customer c = session.get(Customer.class, 6L);
        c.setCust_name("小泽");

        //保存或者修改
        session.saveOrUpdate(c);


        tr.commit();
        session.close();
    }


    /**
     * 测试查询的方法
     */
    @Test
    public void testQuery() {
        Session session = HibernateUtils.getSession();

        Transaction tr = session.beginTransaction();

        //创建查询的接口
        Query query = session.createQuery("from Customer");
        //查询所有的数据select * from 表
        List<Customer> list = query.list();
        for (Customer customer : list) {
            System.out.println(customer);
        }

        tr.commit();
        session.close();
    }


    @Test
    public void testSave3() {
        Session session = null;
        Transaction tr = null;
        try {
            //获取session
            session = HibernateUtils.getSession();
            //开启事务
            tr = session.beginTransaction();
            //执行代码
            Customer c = new Customer();
            c.setCust_name("哈哈");
            //保存
            session.save(c);
            //提交事务
            tr.commit();
        } catch (Exception e) {
            //事务回滚
            tr.rollback();
            e.printStackTrace();
        } finally {
            //释放资源
            session.close();
        }
    }
}
