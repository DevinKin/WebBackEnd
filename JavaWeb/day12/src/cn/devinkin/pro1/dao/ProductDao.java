package cn.devinkin.pro1.dao;

import cn.devinkin.pro1.domain.Product;
import cn.devinkin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductDao {
    QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
    public List<Product> findAll() throws SQLException {
        String sql = "SELECT * FROM product";

        return qr.query(sql, new BeanListHandler<>(Product.class));
    }
}
