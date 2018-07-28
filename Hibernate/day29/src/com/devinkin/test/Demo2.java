package com.devinkin.test;

import com.devinkin.domain.User;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.io.Serializable;

public class Demo2 {

    /**
     * Session.evict()：清空指定对象
     */
    @Test
    public void run7() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        User user1 = session.get(User.class, 1);
        user1.setName("隔壁拉拉");

        //自动刷新缓存
        session.flush();

        tr.commit();
        session.close();
    }

   /**
     * Session.evict()：清空指定对象
     */
    @Test
    public void run6() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        User user1 = session.get(User.class, 1);
        System.out.println(user1.getName());

        //清空缓存
        session.evict(user1);

        User user2 = session.get(User.class, 1);
        System.out.println(user2.getName());

        tr.commit();
        session.close();
    }

    /**
     * Session.clear()：清空缓存
     */
    @Test
    public void run5() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        User user1 = session.get(User.class, 1);
        System.out.println(user1.getName());

        //清空缓存
        session.clear();

        User user2 = session.get(User.class, 1);
        System.out.println(user2.getName());

        tr.commit();
        session.close();
    }

    /**
     * 快照机制
     */
    @Test
    public void run4() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        // 获取上一个持久对象
        User user = session.get(User.class, 1);
        user.setName("隔壁老拉");

        tr.commit();
        session.close();
    }

    @Test
    public void run3() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        //最简单的证明，查询两次
        User user1 = session.get(User.class, 1);
        System.out.println(user1.getName());

        User user2 = session.get(User.class, 1);
        System.out.println(user2.getName());
        tr.commit();
        session.close();
    }

    /**
     * 证明一级缓存是存在的
     */
    @Test
    public void run2() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        // 获取上一个持久对象
        User user = new User();
        user.setName("哈哈");
        user.setAge(20);

        //保存用户，user已经存入到session的缓存中
        Serializable id = session.save(user);
        System.out.println(id);

        //获取对象，不会看到SQL语句，说明从缓存中取出User对象
        User user2 = session.get(User.class, id);
        System.out.println(user2.getName());

        tr.commit();
        session.close();
    }

    /**
     * 持久态对象有自动更新数据库的能力
     * session的一级缓存！！！
     */
    @Test
    public void run1() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        // 获取上一个持久对象
        User user =  session.get(User.class, 1);
        //user是持久态，有自动更新数据库的能力
        System.out.println(user.getName());

        //重新设置新的名称
        user.setName("隔壁老王");

        // 正常编写代码
        session.update(user);

        tr.commit();
        session.close();
    }
}
