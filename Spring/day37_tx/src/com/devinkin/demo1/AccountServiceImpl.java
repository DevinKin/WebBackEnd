package com.devinkin.demo1;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

public class AccountServiceImpl implements AccountService {

    // 注入事务的模板类
    @Resource(name = "transactionTemplate")
    private TransactionTemplate transactionTemplate;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

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
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            // 事务的执行，如果没有问题，提交。如果出现了异常，回滚
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                // 先转出
                accountDao.outMoney(out, money);

                // 模拟异常
                int a = 10 / 0;

                // 再转入
                accountDao.inMoney(in, money);
            }
        });
    }
}
