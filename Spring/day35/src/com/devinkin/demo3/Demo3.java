package com.devinkin.demo3;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo3 {

    @Test
    public void run2() {
        // 创建工厂，加载配置文件
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        CustomerServiceImpl cs = (CustomerServiceImpl) ac.getBean("customerService");
        cs.save();
    }


    /**
     * 原始方式
     */
    @Test
    public void run1() {
        CustomerServiceImpl csi = new CustomerServiceImpl();
        csi.save();
    }
}
