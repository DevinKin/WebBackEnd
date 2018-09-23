package cn.devinkin.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author devinkin
 * <p>Title: TestJedisSpring</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 0:20 2018/9/23
 */
public class TestJedisSpring {
    @Test
    public void testJedisClientPool() throws Exception {
        // 初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        // 从容器中获得JedisClient对象
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        // 使用JedisClient对象操作redis
        jedisClient.set("jedisClient", "mytest");
        System.out.println(jedisClient.get("jedisClient"));
    }
}
