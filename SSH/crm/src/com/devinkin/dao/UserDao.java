package com.devinkin.dao;

import com.devinkin.domain.User;

public interface UserDao extends BaseDao<User>{
    User checkCode(String user_code);

    User login(User user);
}
