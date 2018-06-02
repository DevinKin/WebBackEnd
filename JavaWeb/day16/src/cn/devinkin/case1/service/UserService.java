package cn.devinkin.case1.service;

import cn.devinkin.case1.dao.UserDao;
import cn.devinkin.case1.domain.User;

import java.sql.SQLException;

public class UserService {
    private UserDao userDao = new UserDao();

    public User Login(String username, String password) {
        try {
            return userDao.getUserByUsernameAndPwd(username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
