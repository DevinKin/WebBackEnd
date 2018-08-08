package com.devinkin.demo1;

import org.junit.Test;

public class Demo1 {
    @Test
    public void run1() {
        // 目标对象
        UserDao userDao = new UserDaoImpl();
        userDao.save();
        userDao.update();
        System.out.println("===============================");
        // 使用工具类，获取到代理对象
        // 代理对象
        UserDao proxy = MyProxyUtils.getProxy(userDao);
        // 调用代理对象的方法
        proxy.save();
        proxy.update();
    }
}
