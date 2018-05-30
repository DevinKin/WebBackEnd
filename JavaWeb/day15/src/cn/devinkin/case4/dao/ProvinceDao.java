package cn.devinkin.case4.dao;

import cn.devinkin.case4.domain.Province;
import cn.devinkin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class ProvinceDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

    public List<Province> findAllPro() throws SQLException {
        String sql = "select * from Province";
        return qr.query(sql, new BeanListHandler<>(Province.class));
    }
}
