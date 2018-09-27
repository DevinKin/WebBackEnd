package cn.devinkin.test.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author devinkin
 * <p>Title: testSpringQueueConsumer</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 19:59 2018/9/26
 */
public class testSpringQueueConsumer {

    @Test
    public void testQueueConsumer() throws Exception {
        // 初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        // 等待
        System.in.read();
    }

}
