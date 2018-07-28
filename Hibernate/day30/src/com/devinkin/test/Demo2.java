package com.devinkin.test;

import com.devinkin.domain.Role;
import com.devinkin.domain.User;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * 多对多的测试
 * @author king
 */
public class Demo2 {

    /**
     * 现在：张三用户，有两个角色，经理和演员
     * 让张三没有演员角色
     */
    @Test
    public void run3() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        // 查询张三用户
        User user = session.get(User.class, 1L);
        // 查询角色
        Role role = session.get(Role.class, 2L);
        user.getRoles().remove(role);
        tr.commit();
    }


    /**
     * 级联保存
     */
    @Test
    public void run2() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        // 模拟多对多，双向关联
        User u1 = new User();
        u1.setUsername("张三");

        User u2 = new User();
        u2.setUsername("赵四");

        // 创建角色
        Role r1 = new Role();
        r1.setName("经理");
        Role r2 = new Role();
        r2.setName("演员");

        //单向关联
        u1.getRoles().add(r1);
        u1.getRoles().add(r2);

        u2.getRoles().add(r1);

        //保存数据
        session.save(u1);
        session.save(u2);

        tr.commit();
    }

    @Test
    public void run1() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        // 模拟多对多，双向关联
        User u1 = new User();
        u1.setUsername("张三");

        User u2 = new User();
        u2.setUsername("赵四");

        // 创建角色
        Role r1 = new Role();
        r1.setName("经理");
        Role r2 = new Role();
        r2.setName("演员");

        // 关联
        u1.getRoles().add(r1);
        u1.getRoles().add(r2);
        r1.getUsers().add(u1);
        r2.getUsers().add(u1);

        u2.getRoles().add(r1);
        r1.getUsers().add(u2);

        session.save(u1);
        session.save(u2);
        session.save(r1);
        session.save(r2);

        tr.commit();
    }
}
