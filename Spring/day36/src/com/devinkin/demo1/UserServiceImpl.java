package com.devinkin.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * 组件注解，标记类
 * <bean id="userService" class="com.devinkin.demo1.UserServiceImpl"></bean>
 * @author king
 */
//@Component(value = "userService")
@Service(value = "userService")
//@Scope(value = "prototype")
public class UserServiceImpl implements UserService {

    // 给name属性注入美美的字符串，setName方法还可以省略不写
    @Value(value = "美美")
    private String name;

    // @Authowired 按类型自动装配
//    @Autowired
//    @Qualifier(value = "ud")
//    @Qualifier(value = "userDao")
    @Resource(name = "userDao")
    private UserDao userDao;

//    public void setName(String name) {
//        this.name = name;
//    }

    @Override
    public void sayHello() {
        System.out.println("Hello Spring!!" + name);
        userDao.save();
    }

    @PostConstruct
    public void init() {
        System.out.println("初始化...");
    }

    @PreDestroy
    public void destory() {
        System.out.println("销毁了...");
    }
}
