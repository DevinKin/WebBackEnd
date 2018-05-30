package cn.devinkin.case1.service;

import cn.devinkin.case1.dao.UserDao;
import cn.devinkin.case1.domain.User;

import java.sql.SQLException;

public class UserService {
    private UserDao userDao = new UserDao();


    /**
     * 通过用户名查找用户
     * @param username 用户名
     * @return 用户
     * @throws SQLException
     */
    public User findUserByName(String username) throws SQLException {
        return userDao.findUserByName(username);
    }
}
