package cn.devinkin.case2.dao;

import cn.devinkin.case2.domain.Account;
import cn.devinkin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.SQLException;

public class AccountDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

    public boolean ifUserExist(String user) throws SQLException {
        String sql = "select * from account where username=?";
        Account account = qr.query(sql, new BeanHandler<Account>(Account.class), user);
        if (account != null)
            return true;
        else
            return false;
    }

    public void moneyOut(String fromUser, BigDecimal bdmoney) {
        try {
            String sql = "update account set money=money-? where username=?";
            Object[] params = {bdmoney, fromUser};
            qr.update(DataSourceUtils.getConnection(),sql, params);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void moneyIn(String toUser, BigDecimal bdmoney) throws SQLException {
        try {
            String sql = "update account set money=money+? where username=?";
            Object[] params = {bdmoney, toUser};
            qr.update(DataSourceUtils.getConnection(),sql, params);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public BigDecimal getMoneyByUser(String fromUser) throws SQLException {
        String sql = "select money from account where username=?";
        return (BigDecimal)qr.query(sql,new ScalarHandler(), fromUser);
    }
}
