package com.devinkin.demo2;

import org.junit.Test;

public class Demo2 {

    @Test
    public void run1() {
        // 目标对象
        BookDaoImpl bookDao = new BookDaoImpl();
        bookDao.save();
        bookDao.update();
        System.out.println("=======================");
        // 使用CGLIB方式生成代理对象
        BookDaoImpl proxy = MyCglibUtils.getProxy();
        proxy.save();
        proxy.update();
    }
}
