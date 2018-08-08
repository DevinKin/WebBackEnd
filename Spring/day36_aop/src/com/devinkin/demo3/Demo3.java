package com.devinkin.demo3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 测试AOP功能
 * @author king
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:applicationContext.xml")
//@ContextConfiguration("classpath:applicationContext2.xml")
@ContextConfiguration("classpath:applicationContext3.xml")
public class Demo3 {
    @Resource(name = "customerDao")
    private CustomerDao customerDao;

    @Test
    public void run1() {
        customerDao.save();
//        customerDao.update();
    }

}
