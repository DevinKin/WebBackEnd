package com.devinkin.demo1;

import org.springframework.stereotype.Repository;

/**
 * UserDaoImpl交给IOC的容器
 * @author king
 */
@Repository(value = "userDao")
public class UserDaoImpl implements UserDao{

    @Override
    public void save() {
        System.out.println("保存用户...");
    }
}
