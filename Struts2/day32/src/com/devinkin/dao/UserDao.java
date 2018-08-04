package com.devinkin.dao;

import com.devinkin.domain.User;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class UserDao {
    /**
     * 通过用户名和密码查询数据库
     * @param user
     * @return
     */
    public User findByNameAndPwd(User user) {
        // 先获取Session
        Session session = HibernateUtils.getCurrentSession();
        // 使用用户名和密码进行查询
        Query query = session.createQuery("from User where username = ? and password = ?");
        query.setParameter(0,user.getUsername());
        query.setParameter(1,user.getPassword());
        List<User> list = query.list();
        if (list.size() > 0) {
            // 有数据
            return list.get(0);
        }
        return null;
    }
}
