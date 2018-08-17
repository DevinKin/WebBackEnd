package cn.devinkin.crm.service;

import cn.devinkin.crm.domain.User;

public interface UserService {
    User checkCode(String user_code);

    void save(User user);

    User login(User user);
}
