package cn.devinkin.utils;

import java.sql.*;
import java.util.ResourceBundle;

public class JdbcUtils {
    private static final String DRIVERCLASS;
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;
    static {
        //获取ResourceBundle
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        //获取指定内容
        DRIVERCLASS = bundle.getString("driverClass");
        URL  = bundle.getString("url");
        USER = bundle.getString("user");
        PASSWORD = bundle.getString("password");
    }

    static {
        //注册驱动
        try {
            Class.forName(DRIVERCLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
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
