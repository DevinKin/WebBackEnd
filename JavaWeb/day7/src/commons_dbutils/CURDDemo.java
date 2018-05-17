package commons_dbutils;

import cn.devinkin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import java.sql.SQLException;

public class CURDDemo {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
    @Test
    public void insert() throws SQLException {
        /**
         * 1. 创建QueryRunner类
         * 2. 编写sql
         * 3. 执行sql
         */
        String sql = "insert into category values(?,?)";
        Object[] params = {"c008","农药"};
        qr.update(sql, params);
    }


}
