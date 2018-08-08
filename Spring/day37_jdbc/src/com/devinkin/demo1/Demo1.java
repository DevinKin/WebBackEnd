package com.devinkin.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 演示JDBC模板类
 * @author king
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class Demo1 {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate template;

    /**
     * 测试查询，查询所有数据
     */
    @Test
    public void run8() {
        List<Account> list =  template.query("select * from t_account",new BeanMapper());
        System.out.println(list);
    }

    /**
     * 测试查询，通过主键查询一条记录
     */
    @Test
    public void run7() {
        Account ac =  template.queryForObject("select * from t_account where id = ?",new BeanMapper(),1);
        System.out.println(ac);
    }

    /**
     * 删除测试
     */
    @Test
    public void run6() {
        template.update("DELETE FROM t_account WHERE id = ?", "4");
    }

    /**
     * update(String sql, Object ... params) 完成增删改操作
     */
    @Test
    public void run5() {
        template.update("UPDATE  t_account SET name = ? WHERE id = ?", "熊大","4");
    }

    /**
     * 使用C3P0连接池
     */
    @Test
    public void run4() {
        template.update("INSERT INTO t_account VALUES (NULL , ? , ?)", "拉c3p0","12324");
    }


    /**
     * 使用DBCP连接池
     */
    @Test
    public void run3() {
        template.update("INSERT INTO t_account VALUES (NULL , ? , ?)", "拉dbcp","32124");
    }


    /**
     * 使用SpringIOC的方式
     */
    @Test
    public void run2() {
        template.update("INSERT INTO t_account VALUES (NULL , ? , ?)", "拉拉","12345");
    }

    /**
     * 演示模板类
     */
    @Test
    public void run1() {
        // Spring框架提供了内置的连接池，不想使用内置的连接池，整合其他的连接池
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql:///spring_day03");
        dataSource.setUsername("dbuser");
        dataSource.setPassword("secret");

        // 创建模板类
        JdbcTemplate template = new JdbcTemplate();

        // 设置连接池
        template.setDataSource(dataSource);

        template.update("INSERT INTO t_account VALUES (NULL , ? , ?)", "冠希", "10000");
    }
}


/**
 * 自己手动封装数据(一行一行封装数据)
 */
class BeanMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {
        Account ac = new Account();
        ac.setId(resultSet.getInt("id"));
        ac.setName(resultSet.getString("name"));
        ac.setMoney(resultSet.getDouble("money"));
        return ac;
    }
}
