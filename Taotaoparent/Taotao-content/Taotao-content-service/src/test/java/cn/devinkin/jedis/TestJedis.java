package cn.devinkin.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author devinkin
 * <p>Title: TestJedis</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 21:46 2018/9/22
 */
public class TestJedis {
    @Test
    public void testJedis() {
        // 创建一个jedis对象,需要指定服务ip和端口号
        Jedis jedis = new Jedis("192.168.85.136",6379);
        // 直接操作数据库
        jedis.set("TestJedis-key", "1234");
        System.out.println(jedis.get("TestJedis-key"));
        // 关闭jedis
        jedis.close();
    }

    @Test
    public void testJedisPool() {
        // 创建数据库连接池对象,需要指定服务ip和端口号
        JedisPool jedisPool = new JedisPool("192.168.85.136",6379);
        // 从连接池中获得连接
        Jedis jedis = jedisPool.getResource();
        // 使用jedis操作数据库
        System.out.println(jedis.get("TestJedis-key"));
        // 一定要归还jedis连接
        jedis.close();
        // 系统管理连接池
        jedisPool.close();
    }

    @Test
    public void testJedisCluster() throws Exception{
        // 创建一个JedisCluster对象,构造参数为Set类型,每个集合中每个元素是HostAndPort
        Set<HostAndPort> nodes = new HashSet<>();
        // 向集合中添加节点
        nodes.add(new HostAndPort("192.168.85.136", 7001));
        nodes.add(new HostAndPort("192.168.85.136", 7002));
        nodes.add(new HostAndPort("192.168.85.136", 7003));
        nodes.add(new HostAndPort("192.168.85.136", 7004));
        nodes.add(new HostAndPort("192.168.85.136", 7005));
        nodes.add(new HostAndPort("192.168.85.136", 7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        // 直接使用JedisCluster操作redis,自带连接池.jedisCluster可以使单例的
        jedisCluster.set("cluster-test", "hello jedis cluster");
        System.out.println(jedisCluster.get("cluster-test"));
        // 系统关闭JedisCluster
        jedisCluster.close();
    }
}
