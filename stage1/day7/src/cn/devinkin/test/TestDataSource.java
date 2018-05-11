package cn.devinkin.test;

import cn.devinkin.datasource.MyDataSource;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDataSource {
    @Test
    public void func1() throws SQLException {
        //先创建连接池
        MyDataSource myDataSource = new MyDataSource();
        Connection conn = myDataSource.getConnection();

        System.out.println(conn);

        //归还连接
        conn.close();
    }
}
