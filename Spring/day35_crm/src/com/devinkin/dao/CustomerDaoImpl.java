package com.devinkin.dao;

public class CustomerDaoImpl implements CustomerDao{

    @Override
    public void save() {
        System.out.println("持久层：保存客户....");
    }
}
