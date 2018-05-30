package cn.devinkin.case1.dao;

import cn.devinkin.case1.domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import cn.devinkin.utils.DataSourceUtils;

import java.sql.SQLException;

public class UserDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

    /**
     * 通过用户名查找用户
     * @param username 用户名
     * @return 用户
     * @throws SQLException
     */
    public User findUserByName(String username) throws SQLException {
        String sql = "select * from user where username=? limit 1";
        return qr.query(sql,new BeanHandler<>(User.class), username);
    }
}
