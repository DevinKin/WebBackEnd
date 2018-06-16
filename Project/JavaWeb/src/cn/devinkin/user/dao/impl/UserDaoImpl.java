package cn.devinkin.user.dao.impl;

import cn.devinkin.user.dao.UserDao;
import cn.devinkin.user.domain.User;
import cn.devinkin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

    /**
     * @param user
     */
    @Override
    public void addUser(User user) throws SQLException {
        String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {user.getUid(), user.getUsername(), user.getPassword(),
                user.getName(), user.getEmail(), user.getTelephone(),
                user.getBirthday(), user.getSex(), user.getState(),
                user.getCode()};
        qr.update(sql, params);
    }

    /**
     * 通过激活码查找对应用户
     * @param code 激活码
     * @return 用户
     */
    @Override
    public User getUserByCode(String code) throws SQLException {
        String sql = "select * from user where code = ?";
        return qr.query(sql, new BeanHandler<>(User.class), code);
    }

    /**
     * 用户激活
     * @param code 激活码
     */
    @Override
    public void active(String code) throws SQLException {
        String sql = "update user set state = 1 where code = ?";
        qr.update(sql, code);
    }

    @Override
    public User login(String username, String password) throws SQLException {
        String sql = "select * from user where username=? and password=? limit 1";
        Object[] params =  {username, password};
        return qr.query(sql, new BeanHandler<>(User.class),params);
    }

    @Override
    public User findUserByName(String username) throws Exception {
        String sql = "select * from user where username=?";
        return qr.query(sql, new BeanHandler<>(User.class) ,username);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        String sql = "select * from user where email=?";
        return qr.query(sql, new BeanHandler<>(User.class), email);
    }
}
