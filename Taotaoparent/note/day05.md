# Redis集群的搭建
1. Redis集群中至少应该有三个节点。要保证集群的高可用，需要每个节点有一个备份机。
2. Redis集群至少需要6台服务器。
3. 搭建伪分布式。可以使用一台虚拟机运行6个redis实例。需要修改redis的端口号7001-7006

## 搭建流程
### 使用ruby脚本搭建集群。需要ruby的运行环境
```
yum install ruby
yum install rubygems
```

### 配置redis.conf
1. 打开注释:`cluster-enabled yes`
2. 修改端口号:7001-7006

### 编写启动和关闭脚本
1. 启动每个redis实例
#### 启动集群的脚本
```
cd /usr/local/redis-cluster

cd redis01
./redis-server redis.conf
cd ..

cd redis02
./redis-server redis.conf
cd ..

cd redis03
./redis-server redis.conf
cd ..

cd redis04
./redis-server redis.conf
cd ..

cd redis05
./redis-server redis.conf
cd ..

cd redis06
./redis-server redis.conf
cd ..
```

#### 关闭集群的脚本
```
cd /usr/local/redis-cluster
redis-cli -p 7001 shutdown
redis-cli -p 7002 shutdown
redis-cli -p 7003 shutdown
redis-cli -p 7004 shutdown
redis-cli -p 7005 shutdown
redis-cli -p 7006 shutdown

```

### 使用ruby脚本搭建集群
```
cp ~/package/redis-4.0.11/src/redis-trib.rb /usr/local/redis-cluster
./redis-trib.rb create --replicas 1 192.168.85.136:7001 192.168.85.136:7002 192.168.85.136:7003 192.168.85.136:7004 192.168.85.136:7005 192.168.85.136:7006 
```

## Redis集群的使用
1. 查看当前集群信息:`cluster info`
1. 查看当前集群节点:`cluster nodes`
### redis-cli连接集群
1. -c代表连接到集群
```
redis-cli -p 7002 -c
```

### 使用Jedis连接集群
```java
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
```

## JedisClient连接单机版
### applicationContext-redis.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:p="http://www.springframework.org/schema/p"
       xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
        http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-4.0.xsd http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd">

    <!-- 开启注解扫描 -->
    <context:annotation-config/>
    <!-- redis单机版 -->
    <bean id="jedisPool" class="redis.clients.jedis.JedisPool">
        <constructor-arg name="host" value="192.168.85.136"/>
        <constructor-arg name="port" value="6379"></constructor-arg>
    </bean>

    <bean id="jedisClientPool" class="cn.devinkin.cn.devinkin.service.jedis.JedisClientPool">
    </bean>
</beans>
```
### 测试代码
```java
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

```

## JedisCluster连接集群
### applicationContext-redis.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:p="http://www.springframework.org/schema/p"
       xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
        http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-4.0.xsd http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd">

    <!-- 开启注解扫描 -->
    <context:annotation-config/>
    <!-- redis单机版 -->
    <!--<bean id="jedisPool" class="redis.clients.jedis.JedisPool">-->
        <!--<constructor-arg name="host" value="192.168.85.136"/>-->
        <!--<constructor-arg name="port" value="6379"></constructor-arg>-->
    <!--</bean>-->

    <!--<bean id="jedisClientPool" class="cn.devinkin.cn.devinkin.service.jedis.JedisClientPool">-->

    <!-- redis集群版 -->
    <bean id="jedisCluster" class="redis.clients.jedis.JedisCluster">
        <constructor-arg>
            <set>
                <bean class="redis.clients.jedis.HostAndPort">
                    <constructor-arg name="host" value="192.168.85.136"/>
                    <constructor-arg name="port" value="7001"/>
                </bean>
                <bean class="redis.clients.jedis.HostAndPort">
                    <constructor-arg name="host" value="192.168.85.136"/>
                    <constructor-arg name="port" value="7002"/>
                </bean>
                <bean class="redis.clients.jedis.HostAndPort">
                    <constructor-arg name="host" value="192.168.85.136"/>
                    <constructor-arg name="port" value="7003"/>
                </bean>
                <bean class="redis.clients.jedis.HostAndPort">
                    <constructor-arg name="host" value="192.168.85.136"/>
                    <constructor-arg name="port" value="7004"/>
                </bean>
                <bean class="redis.clients.jedis.HostAndPort">
                    <constructor-arg name="host" value="192.168.85.136"/>
                    <constructor-arg name="port" value="7005"/>
                </bean>
                <bean class="redis.clients.jedis.HostAndPort">
                    <constructor-arg name="host" value="192.168.85.136"/>
                    <constructor-arg name="port" value="7006"/>
                </bean>
            </set>
        </constructor-arg>
    </bean>
    
    <bean id="jedisClientCluster" class="cn.devinkin.cn.devinkin.service.jedis.JedisClientCluster">
    </bean>
</beans>

```
### 测试代码
```java
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

```

