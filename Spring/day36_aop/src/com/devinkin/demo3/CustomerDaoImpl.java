package com.devinkin.demo3;

public class CustomerDaoImpl implements CustomerDao{
    @Override
    public void save() {
        // 模拟异常
//        int a = 10 / 0;
        System.out.println("保存客户...");
    }

    @Override
    public void update() {
        System.out.println("更新客户...");
    }
}
