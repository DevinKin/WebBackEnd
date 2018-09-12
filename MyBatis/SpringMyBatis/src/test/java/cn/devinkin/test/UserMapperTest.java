package cn.devinkin.test;

import cn.devinkin.mapper.UserMapper;
import cn.devinkin.pojo.User;
import cn.devinkin.pojo.UserExample;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class UserMapperTest {
    private ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        String configLocation = "ApplicationContext.xml";
        applicationContext = new ClassPathXmlApplicationContext(configLocation);
    }

    @Test
    public void testFindUserById() throws Exception {
        UserMapper userMapper = (UserMapper) applicationContext.getBean("userMapper");
//        User user = userMapper.findUserById(1);
//        System.out.println(user);
    }

    @Test
    public void testFindUserById2() throws Exception {
        UserMapper userMapper = (UserMapper) applicationContext.getBean("userMapper");
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user);
    }

    @Test
    public void testFindUserAndSex() throws Exception {
        UserMapper userMapper = (UserMapper) applicationContext.getBean("userMapper");
        // 创建UserExample对象
        UserExample userExample = new UserExample();
//         通过UserExample对象创建查询条件封装对象(Criteria中是封装的查询条件)
        UserExample.Criteria criteria = userExample.createCriteria();
//         加入查询条件
        criteria.andUsernameLike("%王%");
        criteria.andSexEqualTo("2");
        List<User> userList = userMapper.selectByExample(userExample);
        System.out.println(userList);
    }
}
