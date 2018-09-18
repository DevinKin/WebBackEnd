package cn.devinkin.redis;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisSpringTest {

    private ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        String configLocation = "classpath:applicationContext.xml";
        applicationContext = new ClassPathXmlApplicationContext(configLocation);
    }

    @Test
    public void testJedisSpring() throws Exception {
        // 获取连接池
        JedisPool jedisPool = (JedisPool) applicationContext.getBean("jedisPool");

        // 获取连接
        Jedis jedis = jedisPool.getResource();

        // 存入
        jedis.set("key4", "bbb");

        // 取出
        System.out.println(jedis.get("key4"));

    }
}
