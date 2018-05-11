package cn.devinkin.utils;

import java.sql.*;

public class JdbcUtils_1 {
    //获取连接
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
                //注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        //获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/day07", "dbuser", "secret");
        return conn;
    }

    /**
     * 释放所有资源
     * @param conn 连接
     * @param st 语句执行者
     * @param rs 结果集
     */
    public static  void closeResource(Connection conn, Statement st, ResultSet rs) {
        closeResultSet(rs);
        closeStatement(st);
        closeConnection(conn);
    }


    /**
     * 释放statement
     * @param st
     */
    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            st = null;
        }
    }

    /**
     * 释放连接
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }

    }

    /**
     * 关闭结果集合
     * @param rs
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }
    }
}
