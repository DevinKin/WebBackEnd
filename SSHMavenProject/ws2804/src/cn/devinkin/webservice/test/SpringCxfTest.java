package cn.devinkin.webservice.test;

import cn.devinkin.webservice.IWeatherServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringCxfTest {
    @Test
    public void test1() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        IWeatherServiceImpl iws = (IWeatherServiceImpl) ac.getBean("weatherClient");
        String result = iws.getWeatherByCityName("成都");
        System.out.println(result);
    }
}
