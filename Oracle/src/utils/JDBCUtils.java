package utils;

import java.sql.*;

public class JDBCUtils {
    private static String driver = "oracle.jdbc.OracleDriver";
    private static String url = "jdbc:oracle:thin:@172.17.41.93:1521/orcl";
    private static String user = "scott";
    private static String password = "tiger";

    static {
        //注册驱动
        try {
            Class.forName(driver);
            //DriverManager.registerDriver(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void release(Connection conn, Statement st, ResultSet rs) {
       if (rs != null) {
           try {
               rs.close();
           } catch (SQLException e) {
               e.printStackTrace();
           } finally {
               rs = null;
           }
       }

       if (st != null) {
           try {
               st.close();
           } catch (SQLException e) {
               e.printStackTrace();
           } finally {
               st = null;
           }
       }

       if (conn != null) {
           try {
               conn.close();
           } catch (SQLException e) {
               e.printStackTrace();
           } finally {
               conn = null;
           }
       }
    }
}
