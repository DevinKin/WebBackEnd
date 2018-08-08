package com.devinkin.demo2;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {
    // 模板注入
//    private JdbcTemplate template;
//
//    public void setTemplate(JdbcTemplate template) {
//        this.template = template;
//    }

    /**
     * 转出金额
     * @param out 转出人
     * @param money 金额
     */
    @Override
    public void outMoney(String out, double money) {
        this.getJdbcTemplate().update("UPDATE t_account SET money = money - ? WHERE name = ?", money, out);
    }

    /**
     * 转入金额
     * @param in 转入人
     * @param money 金额
     */
    @Override
    public void inMoney(String in, double money) {
        this.getJdbcTemplate().update("UPDATE t_account SET money = money + ? WHERE name = ?", money, in);
    }
}
