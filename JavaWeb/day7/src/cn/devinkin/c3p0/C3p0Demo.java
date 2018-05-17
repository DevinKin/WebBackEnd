package cn.devinkin.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class C3p0Demo {
    /**
     * 编码
     */
    @Test
    public void func1() {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //编写sql
        String sql = "select * from category";

        //设置参数
        try {
            ds.setDriverClass("com.mysql.jdbc.Driver");
            ds.setJdbcUrl("jdbc:mysql://localhost/day07");
            ds.setUser("dbuser");
            ds.setPassword("secret");
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            //处理结果
            while(rs.next()) {
                System.out.println(rs.getString("cid") + "::" + rs.getString("cname"));
            }
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstmt.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 使用c3p0.properties
     */
    @Test
    public void func2() {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //编写sql
        String sql = "select * from category";

        //设置参数
        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            //处理结果
            while(rs.next()) {
                System.out.println(rs.getString("cid") + "::" + rs.getString("cname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstmt.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用c3p0.xml
     */
    @Test
    public void func3() {
        ComboPooledDataSource ds = new ComboPooledDataSource("devinkin");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //编写sql
        String sql = "select * from category";

        //设置参数
        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            //处理结果
            while(rs.next()) {
                System.out.println(rs.getString("cid") + "::" + rs.getString("cname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstmt.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
