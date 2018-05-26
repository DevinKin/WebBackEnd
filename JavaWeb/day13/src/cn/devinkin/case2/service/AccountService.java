package cn.devinkin.case2.service;

import cn.devinkin.case2.dao.AccountDao;
import cn.devinkin.utils.DataSourceUtils;

import java.math.BigDecimal;
import java.sql.SQLException;

public class AccountService {
    private AccountDao accountDao = new AccountDao();
    public boolean ifUserExist(String user) throws SQLException {
        return accountDao.ifUserExist(user);
    }

    public void transfer(String fromUser, String toUser, BigDecimal bdmoney) throws SQLException {
        /**
         * 1. 开启事务
         * 2. 转出
         * 3. 转入
         * 4. 事务提交
         * 5. 异常，事务回滚
         */
        try {
            //1
            DataSourceUtils.beginTransaction();
            accountDao.moneyIn(toUser, bdmoney);
            int i = 1/0;
            accountDao.moneyOut(fromUser, bdmoney);
            DataSourceUtils.commitTransaction();
        }  catch (SQLException e) {
            DataSourceUtils.rollbackTransaction();
            e.printStackTrace();
        }
    }

    public BigDecimal getMoneyByUser(String fromUser) throws SQLException {
        return accountDao.getMoneyByUser(fromUser);
    }
}
