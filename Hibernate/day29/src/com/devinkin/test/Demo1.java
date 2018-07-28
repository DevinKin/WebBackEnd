package com.devinkin.test;

import com.devinkin.domain.Person;
import com.devinkin.domain.User;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.io.Serializable;

public class Demo1 {


    /**
     * 演示持久类的对象的三个状态
     */
    @Test
    public void run3() {
        //获取Session
        Session session = HibernateUtils.getSession();

        Transaction tr = session.beginTransaction();

        //持久User的对象
        //瞬时态：没有OID的值，没有被session管理，此时user对象是瞬时态对象
        User user = new User();
        user.setName("小泽老师");
        user.setAge(48);

        // 使用session保存用户
        // user对象已经存在id的值，默认情况下，把user对象也保存到session的缓存中
        Serializable id = session.save(user);
        System.out.println("主键的值： " + id);
        // user是持久态对象

        tr.commit();
        // session销毁，缓存存在的user没有了
        session.close();
        // 打印
        // user对象存在id值，session销毁了，缓存不存在，session不管理user对象
        // user是脱管态对象
        System.out.println(user.getId());
        System.out.println(user.getName());
    }

    @Test
    public void run1() {
        //获取Session
        Session session = HibernateUtils.getSession();

        Transaction tr = session.beginTransaction();

        //保存用户
        User user = new User();
        user.setName("小风");
        user.setAge(28);

        session.save(user);
        tr.commit();
        session.close();
    }

    /**
     * 测试uuid主键的生成策略
     */
    @Test
    public void run2() {
        //获取Session
        Session session = HibernateUtils.getSession();

        Transaction tr = session.beginTransaction();

        //保存用户
        Person person = new Person();
        person.setPid("1231");
        person.setPname("子啊");

        session.save(person);
        tr.commit();
        session.close();
    }
}
