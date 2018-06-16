package cn.devinkin.user.service;

import cn.devinkin.user.domain.User;

public interface UserService {
    void regist(User user) throws Exception;

    String active(String code) throws Exception;

    User login(String username, String password) throws Exception;

    User findUserByName(String username) throws Exception;

    User findUserByEmail(String email) throws Exception;
}
