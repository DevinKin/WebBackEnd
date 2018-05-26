package cn.devinkin.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSourceUtils {
    private static ComboPooledDataSource ds = new ComboPooledDataSource();
    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    /**
     * 获取连接池
     * @return
     */
    public static DataSource getDataSource() {
        return ds;
    }

    /**
     * 从当前线程获取连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = tl.get();
        if (conn != null) {
            //开启过事务的连接，事务专用连接
            return conn;
        }
        //普通连接
        return ds.getConnection();
    }


    /**
     * 释放所有资源
     * @param conn 连接
     * @param st 语句执行者
     * @param rs 结果集
     */
    public static  void closeResource(Connection conn, Statement st, ResultSet rs) throws SQLException {
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
    public static void closeConnection(Connection conn) throws SQLException {
        //没有事务
        Connection connection = tl.get();
        if (connection == null) {
            //普通连接关闭
            if (conn != null) {
                conn.close();
            }
        }

        //有事务，判断conn是否为事务连接，如果不是，则关闭
        if (conn != connection)
            conn.close();

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

    /**
     * 开启事务
     * @throws SQLException
     */
    public static void beginTransaction() throws SQLException {
        Connection conn = tl.get();
        if (conn != null) {
            throw new SQLException("已经开启过事务了，不要重复开启事务");
        }
        conn = getConnection();
        conn.setAutoCommit(false);
        tl.set(conn);
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() throws SQLException {
        Connection conn = tl.get();
        if (conn == null)
            throw new SQLException("你还没有开启事务");
        conn.commit();
        conn.close();
        tl.remove();
    }

    public static void rollbackTransaction() throws SQLException {
        Connection conn = tl.get();
        if (conn == null)
            throw new SQLException("你还没有开启事务");
        conn.rollback();
        conn.close();
        tl.remove();
    }

}
