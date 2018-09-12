package cn.devinkin.dao;

import cn.devinkin.mypojo.User;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {

    @Override
    public User findUserById(Integer id) {
        // sqlSession是线程不安全的,所以它的最佳使用范围在方法体内
        SqlSession openSession = this.getSqlSession();
        User user = openSession.selectOne("test.findUserById",id);
        return user;
    }

    @Override
    public List<User> findUserByUserName(String username) {
        // sqlSession是线程不安全的,所以它的最佳使用范围在方法体内
        SqlSession openSession = this.getSqlSession();
        List<User> userList = openSession.selectList("test.findUserByUserName", username);
        return userList;
    }
}
