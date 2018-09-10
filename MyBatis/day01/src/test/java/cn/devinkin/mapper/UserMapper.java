package cn.devinkin.mapper;

import cn.devinkin.pojo.User;

import java.util.List;

public interface UserMapper {
    public User findUserById(Integer id);

    public List<User> findUserByUserName(String username);

    public void insertUser(User user);

    public void delUser(Integer id);

    public void updateUser(User user);
}
