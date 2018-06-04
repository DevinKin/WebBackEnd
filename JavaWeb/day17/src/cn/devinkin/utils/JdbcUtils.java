package cn.devinkin.utils;

import cn.devinkin.case2.annotation.JDBCInfo;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
    @JDBCInfo(url = "jdbc:mysql://localhost:3306/day16")
    public static Connection getConnection() throws NoSuchMethodException, ClassNotFoundException, SQLException {
        /**
         * 1. 获取字节码文件
         * 2. 获取getConnection
         * 3. 判断该方法是否有JDBCInfo注解，若有的话获取
         * 4. 获取注解的四个属性
         * 5. 注册驱动
         * 6. 获取连接
         */
        Class clazz = JdbcUtils.class;
        Method method =  clazz.getMethod("getConnection", null);

        if (method.isAnnotationPresent(JDBCInfo.class)) {
            JDBCInfo jdbcInfo = method.getAnnotation(JDBCInfo.class);
            String driverClass = jdbcInfo.driverClass();
            String url = jdbcInfo.url();
            String username = jdbcInfo.username();
            String password = jdbcInfo.password();
            Class.forName(driverClass);
            return DriverManager.getConnection(url,username,password);
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            System.out.println(getConnection());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
