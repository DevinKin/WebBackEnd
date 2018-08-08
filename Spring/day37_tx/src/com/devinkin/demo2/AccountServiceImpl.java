package com.devinkin.demo2;

import javax.annotation.Resource;

public class AccountServiceImpl implements AccountService {


    @Resource(name = "accountDao")
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * 转账的方法
     *
     * @param out   转出
     * @param in    转入
     * @param money 金额
     */
    @Override
    public void pay(String out, String in, double money) {
        // 事务的执行，如果没有问题，提交。如果出现了异常，回滚
        // 先转出
        accountDao.outMoney(out, money);

        // 模拟异常
//        int a = 10 / 0;

        // 再转入
        accountDao.inMoney(in, money);
    }
}
