package cn.devinkin.regist.dao;

import cn.devinkin.regist.domain.User;
import cn.devinkin.utils.DataSourceUtils;
import cn.itcast.commons.CommonUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Date;
import java.sql.SQLException;

public class UserDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

    public int regist(User user) {
        String sql = "insert into user values(?,?,?,?,?,?,?,?)";
        Date date = Date.valueOf(user.getBirthday());
        Object[] params = {null, user.getUsername(), user.getPassword(),user.getEmail(),
                user.getName(),user.getSex(),date,user.getHobby()};
        try {
            return qr.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
