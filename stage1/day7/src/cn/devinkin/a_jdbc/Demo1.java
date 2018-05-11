package cn.devinkin.a_jdbc;

import cn.devinkin.utils.JdbcUtils;
import cn.devinkin.utils.JdbcUtils_1;
import org.junit.Test;

import java.sql.*;

public class Demo1 {
    @Test
    public void func1() throws Exception {
        //注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        //获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/day07", "dbuser", "secret");

        //编写sql
        String sql = "select * from category";

        //创建语句执行者
        PreparedStatement st = conn.prepareStatement(sql);

        //设置参数

        //执行sql
        ResultSet rs = st.executeQuery();

        //处理结果
        while(rs.next()) {
            System.out.println(rs.getString("cid") + "::" + rs.getString("cname"));
        }

        //释放资源
        rs.close();
        st.close();
        conn.close();
    }

    @Test
    public void func2() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils_1.getConnection();

            String sql = "insert into category values(?,?)";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, "c006");
            pstmt.setString(2,"测试");

            int i = pstmt.executeUpdate();

            if (i == 1) {
                System.out.println("success");
            } else {
                System.out.println("fail");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils_1.closeResource(conn, pstmt, rs);
        }
    }

    @Test
    public void func3() throws Exception {
        //获取连接
        Connection conn = JdbcUtils.getConnection();

        //编写sql
        String sql = "select * from category";

        //创建语句执行者
        PreparedStatement st = conn.prepareStatement(sql);

        //设置参数

        //执行sql
        ResultSet rs = st.executeQuery();

        //处理结果
        while(rs.next()) {
            System.out.println(rs.getString("cid") + "::" + rs.getString("cname"));
        }

        //释放资源
        rs.close();
        st.close();
        conn.close();
    }

    @Test
    public void func4() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();
            String sql = "update category set cname = ? where cid=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"手机");
            pstmt.setString(2,"c006");
            int i = pstmt.executeUpdate();
            if (i == 1) {
                System.out.println("success");
            } else {
                System.out.println("failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(conn, pstmt, rs);
        }
    }

    @Test
    public void func5() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();
            String sql = "DELETE FROM category WHERE cid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,  "c006");
            int i = pstmt.executeUpdate();
            if (i == 1) {
                System.out.println("success");
            } else {
                System.out.println("failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(conn, pstmt, rs);
        }

    }
}
