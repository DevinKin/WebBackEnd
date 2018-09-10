package cn.devinkin.test;

import cn.devinkin.pojo.User;
import cn.devinkin.pojo.User2;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class UserTest {

    @Test
    public void testFindUserById() throws Exception{
        String resource = "SqlMapConfig.xml";
        // 通过流将核心配置文件读取进来
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 通过核心配置文件输入流来创建会话工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 通过工厂创建会话
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 第一个参数:所调用的sql语句=namespace+sql的ID
        User user = sqlSession.selectOne("test.findUserById",1);
        System.out.println(user);
        sqlSession.close();
    }

    @Test
    public void testFindUserByUserName() throws Exception {
        String resource = "SqlMapConfig.xml";
        // 通过流将核心配置文件读取进来
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 通过核心配置文件输入流来创建会话工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 通过工厂创建会话
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 第一个参数:所调用的sql语句=namespace+sql的ID
        List<Object> selectList = sqlSession.selectList("test.findUserByUserName", "王");
        System.out.println(selectList);
    }

    @Test
    public void testInsertUser() throws Exception {
        String resource = "SqlMapConfig.xml";
        // 通过流将核心配置文件读取进来
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 通过核心配置文件输入流来创建会话工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 通过工厂创建会话
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setUsername("小明");
        user.setBirthday(new Date());
        user.setSex("男");
        user.setAddress("广东佛山");
        sqlSession.insert("test.insertUser", user);
        // 提交事务(mybatis会自动开启事务,但不知道什么时候提交,需要手动提交事务)
        sqlSession.commit();

        System.out.println(user);
    }

    @Test
    public void testInsertUser2() throws Exception {
        String resource = "SqlMapConfig.xml";
        // 通过流将核心配置文件读取进来
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 通过核心配置文件输入流来创建会话工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 通过工厂创建会话
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User2 user = new User2();
        user.setUsername("小明");
        user.setBirthday(new Date());
        user.setSex("男");
        user.setAddress("广东佛山");
        sqlSession.insert("test2.insertUser2", user);
        // 提交事务(mybatis会自动开启事务,但不知道什么时候提交,需要手动提交事务)
        sqlSession.commit();

        System.out.println(user);
    }

    @Test
    public void testDelUser() throws Exception {
        String resource = "SqlMapConfig.xml";
        // 通过流将核心配置文件读取进来
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 通过核心配置文件输入流来创建会话工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 通过工厂创建会话
        SqlSession sqlSession = sqlSessionFactory.openSession();

        sqlSession.delete("test.delUser",29);

        // 提交事务(mybatis会自动开启事务,但不知道什么时候提交,需要手动提交事务)
        sqlSession.commit();
    }

    @Test
    public void testUpdateUser() throws Exception {
        String resource = "SqlMapConfig.xml";
        // 通过流将核心配置文件读取进来
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 通过核心配置文件输入流来创建会话工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 通过工厂创建会话
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setId(26);
        user.setUsername("瓜娃子");
        sqlSession.update("test.updateUserById", user);

        // 提交事务(mybatis会自动开启事务,但不知道什么时候提交,需要手动提交事务)
        sqlSession.commit();
    }
}
