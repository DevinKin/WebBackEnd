package commons_dbutils;

import cn.devinkin.utils.DataSourceUtils;
import domain.Category;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ResultSetHandlerDemo {
    @Test
    public void arrayHandler() throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

        String sql = "select * from category";

        Object[] query = qr.query(sql, new ArrayHandler());
        System.out.println(Arrays.toString(query));
    }

    @Test
    public void arrayListHandler() throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

        String sql = "select * from category";

        Object obj = qr.query(sql, new ArrayListHandler());
        List<Object[]> objList = (List<Object[]>) obj;
        for (Object[] row : objList) {
            System.out.println(Arrays.toString(row));
        }
    }

    @Test
    public void BeanHandler() throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

        String sql = "select * from category";

        Category category = qr.query(sql, new BeanHandler<Category>(Category.class));
        System.out.println(category);
    }

    @Test
    public void BeanListHandler() throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

        String sql = "select * from category";

        List<Category> categoryList = qr.query(sql, new BeanListHandler<Category>(Category.class));
        for (Category category : categoryList) {
            System.out.println(category);
        }
    }

    @Test
    public void mapHandler() throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

        String sql = "select * from category";

        Map<String,Object> colMap = qr.query(sql, new MapHandler());
        System.out.println(colMap);
    }

    @Test
    public void mapListHandler() throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

        String sql = "select * from category";

        List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
        for (Map<String, Object> map : mapList) {
            System.out.println(map);
        }
    }

    @Test
    public void ScalarHandler() throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

        String sql = "select COUNT(*) from category";
        Object obj = qr.query(sql, new ScalarHandler());
        System.out.println(obj);
        System.out.println(obj.getClass().getName());
    }
}
