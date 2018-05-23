package cn.devinkin.pro2.dao;

import cn.devinkin.pro2.domain.User;
import cn.devinkin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());


    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE username=? AND password=?";
        Object[] params = {username, password};
        return qr.query(sql, new BeanHandler<User>(User.class), params);
    }
}
