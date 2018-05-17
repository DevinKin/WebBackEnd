package cn.devinkin.regist.service;

import cn.devinkin.regist.dao.UserDao;
import cn.devinkin.regist.domain.User;

public class UserService {
    private UserDao userDao = new UserDao();

    public int regist(User user) {
        return userDao.regist(user);
    }
}
