package cn.devinkin.case1.dao;

import cn.devinkin.case1.domain.Account;
import cn.devinkin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public void moneyOut(Connection conn, String fromUser, BigDecimal bdmoney) {
        PreparedStatement pstmt = null;

        String sql = "update account set money=money-? where username=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setBigDecimal(1,bdmoney);
            pstmt.setString(2,fromUser);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        Object[] params = {bdmoney, fromUser};
//        qr.update(sql, params);
    }

    public void moneyIn(Connection conn, String toUser, BigDecimal bdmoney) throws SQLException {
        PreparedStatement pstmt = null;
        String sql = "update account set money=money+? where username=?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setBigDecimal(1,bdmoney);
            pstmt.setString(2,toUser);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        Object[] params = {bdmoney, toUser};
//        qr.update(sql, params);
    }

    public BigDecimal getMoneyByUser(String fromUser) throws SQLException {
        String sql = "select money from account where username=?";
        return (BigDecimal)qr.query(sql,new ScalarHandler(), fromUser);
    }
}
