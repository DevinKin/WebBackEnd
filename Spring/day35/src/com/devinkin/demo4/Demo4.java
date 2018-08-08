package com.devinkin.demo4;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo4 {

    /**
     * 测试加载多个配置文件
     */
    @Test
    public void run5() {
        // 创建工厂
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml", "com/devinkin/demo4/applicationContext2.xml");
        User user = (User) ac.getBean("user");
        System.out.println(user);
    }

    /**
     * 测试注入集合
     */
    @Test
    public void run4() {
        // 创建工厂
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User) ac.getBean("user");
        System.out.println(user);
    }

    @Test
    public void run3() {
        // 创建工厂
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        Car2 car2 = (Car2) ac.getBean("car2");
        System.out.println(car2);

    }

    @Test
    public void run2() {
        // 创建工厂
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        Person person = (Person) ac.getBean("person");
        System.out.println(person);

    }


    @Test
    public void run1() {
        // 创建工厂
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        Car1 c1 = (Car1) ac.getBean("car1");
        System.out.println(c1);

    }
}
