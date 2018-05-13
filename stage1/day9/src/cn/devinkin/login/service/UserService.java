package cn.devinkin.login.service;

import cn.devinkin.login.dao.UserDao;
import cn.devinkin.login.domain.User;

public class UserService {
    private UserDao userDao = new UserDao();

    /**
     * 用户登录
     * @param username 用户名
     * @return 用户
     */
    public User login(String username) {
        return userDao.findUserByName(username);
    }
}
