package cn.devinkin.login.dao;

import cn.devinkin.login.domain.User;
import cn.devinkin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
    public User findUserByName(String username) {
        String sql = "select * from user where username=?";
        try {
            return qr.query(sql, new BeanHandler<User>(User.class), username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
