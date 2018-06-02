package cn.devinkin.case1.dao;

import cn.devinkin.case1.domain.User;
import cn.devinkin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

    public User getUserByUsernameAndPwd(String username, String password) throws SQLException {
        String sql = "select * from user where username=? and password=?";
        Object[] params = {username,password};
        return qr.query(sql, new BeanHandler<>(User.class), params);
    }
}
