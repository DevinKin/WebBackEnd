package cn.devinkin.case5.dao;

import cn.devinkin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import java.sql.SQLException;
import java.util.List;

public class KeyWordDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

    public List<Object> getKeyWords4Ajax(String kw) throws SQLException {
        String sql = "select * from keyword where kw like ?";
        kw = "%" + kw + "%";
        return qr.query(sql, new ColumnListHandler("kw"), kw);
    }
}
