package cn.devinkin.pro2.service;

import cn.devinkin.pro2.dao.UserDao;
import cn.devinkin.pro2.domain.User;

import java.sql.SQLException;

public class UserService {
    private UserDao userDao = new UserDao();

    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        return userDao.getUserByUsernameAndPassword(username,password);
    }
}
