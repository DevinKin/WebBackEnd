package com.devinkin.service;

import com.devinkin.domain.User;

public interface UserService {
    User checkCode(String user_code);

    void save(User user);

    User login(User user);
}
