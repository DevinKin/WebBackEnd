package cn.devinkin.dao;

import cn.devinkin.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private SqlSessionFactory sqlSessionFactory;

    // 通过构造方法注入
    public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public User findUserById(Integer id) {
        // sqlSession是线程不安全的,所以它的最佳使用范围在方法体内
        SqlSession openSession = sqlSessionFactory.openSession();
        User user = openSession.selectOne("test.findUserById",id);
        return user;
    }

    public List<User> findUserByUserName(String username) {
        // sqlSession是线程不安全的,所以它的最佳使用范围在方法体内
        SqlSession openSession = sqlSessionFactory.openSession();
        List<User> userList = openSession.selectList("test.findUserByUserName", username);
        return userList;
    }
}
