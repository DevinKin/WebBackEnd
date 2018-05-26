package cn.devinkin.case1.service;

import cn.devinkin.case1.dao.AccountDao;
import cn.devinkin.utils.JdbcUtils;

import java.math.BigDecimal;
import java.sql.Connection;
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
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            //1
            conn.setAutoCommit(false);
            accountDao.moneyIn(conn,toUser, bdmoney);
//            int i = 1/0;
            accountDao.moneyOut(conn,fromUser, bdmoney);
            conn.commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
        }
    }

    public BigDecimal getMoneyByUser(String fromUser) throws SQLException {
        return accountDao.getMoneyByUser(fromUser);
    }
}
