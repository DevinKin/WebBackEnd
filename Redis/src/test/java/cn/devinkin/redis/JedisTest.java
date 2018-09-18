package cn.devinkin.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTest {
    @Test
    public void testJedis1() throws Exception {
        // 创建和Redis的连接
        Jedis jedis = new Jedis("172.16.19.160", 6379);

        // 存入
        jedis.set("key2", "2");

        // 取出
        System.out.println(jedis.get("key2"));
        // 关闭
        jedis.close();
    }

    @Test
    public void testJedisPool() throws Exception {
        // 创建Jedis连接池
        JedisPool jedisPool = new JedisPool("172.16.19.160", 6379);

        // 获取Jedis连接
        Jedis jedis = jedisPool.getResource();

        // 存入
        jedis.set("key3", "aaa");

        // 取出
        System.out.println(jedis.get("key3"));

        // 使用连接时,连接使用完后一定要关闭,关闭后连接会自动归还到连接池
        jedis.close();

        // 关闭连接池
        jedisPool.close();
    }

}
