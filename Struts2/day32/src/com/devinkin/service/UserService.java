package com.devinkin.service;

import com.devinkin.dao.UserDao;
import com.devinkin.domain.User;
import com.devinkin.utils.HibernateUtils;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * 用户业务层
 * @author king
 */
public class UserService {
    /**
     * 处理登录功能
     * @param user 用户
     * @return 用户
     */
    public User login(User user) {
        // 使用事务
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();
        User existUser = null;
        try {
            // 调用持久层，查询数据
            existUser = new UserDao().findByNameAndPwd(user);
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        } finally {
        }
        return existUser;
    }

    @Test
    public void run() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("12345");
        User existUser = this.login(user);
        if (existUser != null)
            System.out.println(existUser);
    }
}
