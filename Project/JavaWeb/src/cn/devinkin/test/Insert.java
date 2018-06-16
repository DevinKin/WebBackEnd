package cn.devinkin.test;

import cn.devinkin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

public class Insert {
    public static void main(String[] args) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
//        INSERT INTO `product` VALUES ('1','小米 4c 标准版',1399,1299,'products/1/c_0001.jpg','2015-11-02',1,'小米 4c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',0,'1'),
        for (int i = 51; i < 200; i++) {
            String s = "" + i;
            Object[] params = {s,"小米 4c 标准版",1399,1299,"products/1/c_0001.jpg","2015-11-02",1,"小米 4c 标准版 全网通 白色 移动联通电信4G手机 双卡双待",0,"1"};
            qr.update(sql, params);
        }
    }
}
