package com.devinkin.demo1;

public class CustomerDaoImpl implements CustomerDao{
    @Override
    public void save() {
        System.out.println("保存客户...");
    }

    @Override
    public void update() {
        System.out.println("修改客户...");
    }
}
