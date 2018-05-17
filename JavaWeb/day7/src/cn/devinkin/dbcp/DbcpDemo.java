package cn.devinkin.dbcp;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DbcpDemo {
    @Test
    public void func1()  {
        //创建连接池
        BasicDataSource bds = new BasicDataSource();

        //配置信息
        bds.setDriverClassName("com.mysql.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/day07");
        bds.setUsername("dbuser");
        bds.setPassword("secret");

        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into category values(?,?)";
        try {
            conn = bds.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "c006");
            pstmt.setString(2, "手机");
            int i = pstmt.executeUpdate();
            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void func2() throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("src/dbcp.properties"));
        //创建连接池
        DataSource ds = new BasicDataSourceFactory().createDataSource(prop);
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into category values(?,?)";
        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "c007");
            pstmt.setString(2, "电脑");
            int i = pstmt.executeUpdate();
            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
