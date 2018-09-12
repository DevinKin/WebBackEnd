package cn.devinkin.test;

import cn.devinkin.mapper.UserMapper;
import cn.devinkin.pojo.CustomOrders;
import cn.devinkin.pojo.Orders;
import cn.devinkin.pojo.QueryVo;
import cn.devinkin.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserMapperTest {
    private SqlSessionFactory factory;

    // 作用,在测试方法前执行该方法
    @Before
    public void setUp() throws Exception {
        String resource = "SqlMapConfig.xml";
        // 通过流将核心配置文件读取进来
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 通过核心配置文件输入流来创建会话工厂
        factory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testFindUserById() throws Exception {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        User user = userMapper.findUserById(1);
        System.out.println(user);
    }

    @Test
    public void testFindUserByUserName() throws Exception {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        List<User> userList = userMapper.findUserByUserName("王");
        System.out.println(userList);
    }

    @Test
    public void testInsertUser() {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        User user1 = new User();
        user1.setUsername("拉拉仔");
        user1.setAddress("广东湛江");
        user1.setSex("女");
        user1.setBirthday(new Date());
        userMapper.insertUser(user1);
        openSession.commit();
    }

    @Test
    public void testDelUser() {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        userMapper.delUser(30);
        openSession.commit();
    }

    @Test
    public void testUpdateUserById() {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(31);
        user.setUsername("小拉拉");
        userMapper.updateUser(user);
        openSession.commit();
    }

    @Test
    public void testFindUserByVo() {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        QueryVo queryVo = new QueryVo();
        User user = new User();
        user.setSex("2");
        user.setUsername("王");
        queryVo.setUser(user);
        List<User> userList = userMapper.findUserByVo(queryVo);
        System.out.println(userList);
        openSession.commit();
    }

    @Test
    public void testFindUserCount() {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        Integer userCount = userMapper.findUserCount();
        System.out.println(userCount);
        openSession.commit();
    }

    @Test
    public void testFindUserByUsernameAndSex() {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        User user = new User();
        user.setSex("2");
        user.setUsername("王");
        List<User> userList = userMapper.findUseByUsernameAndSex(user);
        System.out.println(userList);
        openSession.commit();
    }

    @Test
    public void testFindUserByIds() {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        QueryVo queryVo = new QueryVo();
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(16);
        ids.add(31);
        ids.add(22);
        queryVo.setIds(ids);
        List<User> userList = userMapper.findUserByIds(queryVo);
        System.out.println(userList);
        openSession.commit();
    }

    @Test
    public void testFindOrdersAndUser() throws Exception {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        List<CustomOrders> customOrdersList = userMapper.findOrdersAndUser1();
        System.out.println(customOrdersList);
        openSession.commit();
    }

    @Test
    public void testFindOrdersAndUser2() throws Exception {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        List<Orders> customOrdersList = userMapper.findOrdersAndUser2();
        System.out.println(customOrdersList);
        openSession.commit();
    }

    @Test
    public void testFindUserAndOrders() throws Exception {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        List<User> userList = userMapper.findUserAndOrders();
        System.out.println(userList);
        openSession.commit();
    }
}
