package com.devinkin.demo1;

public interface AccountDao {
    // 转出
    public void outMoney(String out, double money);
    // 转入
    public void inMoney(String in, double money);
}