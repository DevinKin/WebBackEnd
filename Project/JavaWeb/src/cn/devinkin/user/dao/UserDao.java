package cn.devinkin.user.dao;

import cn.devinkin.user.domain.User;

import java.sql.SQLException;

public interface UserDao {
    void addUser(User user) throws Exception;

    User getUserByCode(String code) throws Exception;

    void active(String code) throws Exception;

    User login(String username, String password) throws Exception;

    User findUserByName(String username) throws Exception;

    User findUserByEmail(String email) throws Exception;
}
