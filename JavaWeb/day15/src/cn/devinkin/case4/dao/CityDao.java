package cn.devinkin.case4.dao;

import cn.devinkin.case4.domain.City;
import cn.devinkin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CityDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

    public List<City> findCitiesByPid(String pid) throws SQLException {
        String sql = "select * from City where ProvinceId=?";
        return qr.query(sql, new BeanListHandler<>(City.class), pid);
    }
}
