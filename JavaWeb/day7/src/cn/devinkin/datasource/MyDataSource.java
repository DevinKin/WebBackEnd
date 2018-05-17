package cn.devinkin.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * 简易的连接池
 * @author king
 */
public class MyDataSource {
    private static final String DRIVERCLASS;
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;
    //增删块，查询慢
    private static LinkedList<Connection> pool = new LinkedList<Connection>();


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
//    static {
//        //初始化的时候需要放入3个连接
//        for (int i=0; i < 3; i++) {
//            pool.addLast(getConnection());
//        }
//    }

    public static Connection getConnection() {
        //获取连接的时候需要来判断list是否为空
        if (pool.isEmpty()) {
            //再添加2个连接进去
            for (int i = 0; i < 3; i++) {
                try {
                    pool.addLast(DriverManager.getConnection(URL,USER,PASSWORD));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("从连接池中获取连接");
        //将conn进行包装
        Connection conn =  pool.removeFirst();
        ConnectionWrapper conn1 = new ConnectionWrapper(conn, pool);
        return conn1;
    }

//    /**
//     * 归还连接
//     * @param conn
//     */
//    public static void addBack(Connection conn) {
//        //将conn放到list的最后即可
//        pool.addLast(conn);
//        System.out.println("已经归还连接");
//    }
}
