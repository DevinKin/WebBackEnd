package cn.devinkin.dao;

import cn.devinkin.mypojo.User;

import java.util.List;

public interface UserDao {
    public User findUserById(Integer id);

    public List<User> findUserByUserName(String username);
}