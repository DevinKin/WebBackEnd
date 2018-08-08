package com.devinkin.demo2;


import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * 测试IOC的程序
 */
public class Demo2 {

    /**
     * 演示依赖注入
     */
    @Test
    public void run6() {
        // 创建工厂，加载核心配置文件
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 从工厂中获取到对象
        UserServiceImpl usi = (UserServiceImpl) ac.getBean("userService");

        // 调用对象的方法执行
        usi.sayHello();

        // 关闭工厂，工厂关闭了，对象都会销毁
        ac.close();

    }

    /**
     * 演示销毁的方法
     */
    @Test
    public void run5() {
        // 创建工厂，加载核心配置文件
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 从工厂中获取到对象
        UserServiceImpl usi = (UserServiceImpl) ac.getBean("userService");

        // 调用对象的方法执行
        usi.sayHello();

        // 关闭工厂，工厂关闭了，对象都会销毁
        ac.close();

    }

    /**
     * 老的工厂版本BeanFactory
     */
    @Test
    public void run4() {
        BeanFactory factory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
        UserService us = (UserService) factory.getBean("userService");
        us.sayHello();
    }

    /**
     * 使用接口的方式
     */
    @Test
    public void run3() {
        // 创建工厂，加载核心配置文件
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 从工厂中获取到对象
        UserService us = (UserService) ac.getBean("userService");

        // 调用对象的方法执行
        us.sayHello();
    }

    /**
     * 使用Spring框架的方式
     */
    @Test
    public void run2() {
        // 创建工厂，加载核心配置文件
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 从工厂中获取到对象
        UserServiceImpl usi = (UserServiceImpl) ac.getBean("userService");
//        usi.setName("美美");

        // 调用对象的方法执行
        usi.sayHello();
    }

    /**
     * 原来的方式
     */
    @Test
    public void run1() {
        // 创建实现类
//        UserServiceImpl usi = new UserServiceImpl();
        UserService usi = new UserServiceImpl();
        usi.sayHello();
    }
}
