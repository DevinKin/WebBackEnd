package com.devinkin.service;

import com.devinkin.dao.UserDao;
import com.devinkin.domain.User;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserService {

    public void save(User u1, User u2) {
        //获取session
        //开启事务
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();

        try {
            UserDao dao = new UserDao();

            dao.save1(u1);

//            int a = 10/0;

            dao.save2(u2);
            //提交事务
            tr.commit();
        } catch (Exception e) {
            //出现问题，回滚事务
            tr.rollback();
            e.printStackTrace();
        } finally {
            //自己释放资源，现在，session不用关闭，线程结束了，自动关闭的！！！
            //session.close();
        }

    }
}
