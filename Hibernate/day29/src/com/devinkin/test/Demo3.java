package com.devinkin.test;

import com.devinkin.domain.User;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

public class Demo3 {

    @Test
    public void run1() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        User user = session.get(User.class, 1);

        user.setName("隔壁老王");
        tr.commit();
        session.close();
    }


    @Test
    public void run2() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        User user = session.get(User.class, 1);

        user.setAge(88);
        tr.commit();
        session.close();
    }

}
