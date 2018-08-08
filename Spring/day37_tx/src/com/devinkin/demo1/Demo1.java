package com.devinkin.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Demo1 {
    @Resource(name = "accountService")
    private AccountService accountService;

    @Test
    public void run1() {
        // 调用支付的方法
        accountService.pay("冠希", "拉拉", 1000);
    }
}
