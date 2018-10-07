# ActiveMQ
1. 市面上流行的MQ
    - ActiveMQ
    - RabbitMQ
    - kafka
## ActiveMQ简介
### 什么是ActiveMQ
1. ActiveMQ 是Apache出品，最流行的，能力强劲的开源消息总线。ActiveMQ 是一个完全支持JMS1.1和J2EE 1.4规范的 JMS Provider实现,尽管JMS规范出台已经是很久的事情了,但是JMS在当今的J2EE应用中间仍然扮演着特殊的地位。
2. 主要特点：
    - 多种语言和协议编写客户端。语言: Java, C, C++, C#, Ruby, Perl, Python, PHP。应用协议: OpenWire,Stomp REST,WS Notification,XMPP,AMQP
    - 完全支持JMS1.1和J2EE 1.4规范 (持久化,XA消息,事务)
    - 对Spring的支持,ActiveMQ可以很容易内嵌到使用Spring的系统里面去,而且也支持Spring2.0的特性
    -  通过了常见J2EE服务器(如 Geronimo,JBoss 4, GlassFish,WebLogic)的测试,其中通过JCA 1.5 resource adaptors的配置,可以让ActiveMQ可以自动的部署到任何兼容J2EE 1.4 商业服务器上
    -  支持多种传送协议:in-VM,TCP,SSL,NIO,UDP,JGroups,JXTA
    - 支持通过JDBC和journal提供高速的消息持久化
    - 从设计上保证了高性能的集群,客户端-服务器,点对点
    - 支持Ajax
    - 支持与Axis的整合
    - 可以很容易得调用内嵌JMS provider,进行测试

### ActiveMQ的消息形式
1. 对于消息的传递有两种类型：
    - 一种是点对点的，即一个生产者和一个消费者一一对应；
    - 另一种是发布/订阅模式，即一个生产者产生消息并进行发送后，可以由多个消费者进行接收。
2. JMS定义了五种不同的消息正文格式，以及调用的消息类型，允许你发送并接收以一些不同形式的数据，提供现有消息格式的一些级别的兼容性。
    - StreamMessage: Java原始值的数据流
    - MapMessage: 一套名称-值对
    - TextMessage: 一个字符串对象
    - ObjectMessage: 一个序列化的 Java对象
    - BytesMessage: 一个字节的数据流

## ActiveMQ的安装
1. 进入`http://activemq.apache.org/`下载ActiveMQ

### 安装环境
1. jdk1.7
2. Centos7

### 安装步骤
1. 第一步: 把ActiveMQ 的压缩包上传到Linux系统。
2. 第二步: 解压缩。
3. 第三步: 启动。

## ActiveMQ的使用方法
### Queue
1. 持久化到服务端
#### Producer
1. 生产者：生产消息，发送端
```java
    // Queue
    @Test
    public void testQueueProducer() throws Exception {
        // 1. 创建一个连接工厂ConnectionFactory对象,需要指定这个mq服务的ip和端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.85.138:61616");
        // 2. 使用ConnectionFactory创建一个连接Connection对象
        Connection connection = connectionFactory.createConnection();
        // 3. 开启连接,调用Conneciton对象的start方法
        connection.start();
        // 4. 使用Connection对象穿件一个Session对象
        // 第一个参数boolean:是否开启事务.分布式事务,一般不使用事务,保证数据的最终一致,可以使用消息队列实现
        // 如果第一个参数为true,第二个参数自动忽略
        // 如果第一个参数为false,第二个参数为消息的应答模式,一般设置为自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5. 使用Session对象创建一个Destination对象,两种形式,queue,topic,现在使用queue
        // 参数:消息队列的名称
        Queue queue = session.createQueue("test-queue");
        // 6. 使用Session对象创建一个Producer对象
        MessageProducer producer = session.createProducer(queue);
        // 7. 创建一个TextMessage消息对象
//        TextMessage textMessage = new ActiveMQTextMessage();
        TextMessage textMessage = session.createTextMessage();
        textMessage.setText("hello activemq");
        // 8. 发送消息
        producer.send(textMessage);
        // 9. 关闭资源,Producer,Destination,Connection
        producer.close();
        session.close();
        connection.close();
    }
```

#### Consumer
1. 消费者：接收消息
```java
    // Producer
    @Test
    public void testQueue() throws Exception {
        // 创建一个连接工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.85.138:61616");
        // 使用连接工厂对象创建一个连接
        Connection connection = connectionFactory.createConnection();
        // 开启连接
        connection.start();
        // 使用连接对象创建一个Session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 使用Session创建一个Destinaction,Destinaction应该和消息的发送端一致
        Queue queue = session.createQueue("test-queue");
        // 使用Session创建一个消费者Consumer对象
        MessageConsumer consumer = session.createConsumer(queue);
        // 向Consumer对象中设置一个MessageListener对象,用来接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                // 取出消息的内容
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        String text = textMessage.getText();
                        // 打印消息内容
                        System.out.println(text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        // 系统等待接收消息
//        while (true) {
//            Thread.sleep(100);
//        }
        System.in.read();
        // 关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

```

### Topic
1. 不持久化到服务端
#### Producer
```java
    // topic
    // Producer
    @Test
    public void testTopicProducer() throws Exception {
        // 创建一个连接工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.85.138:61616");
        // 创建连接
        Connection connection = connectionFactory.createConnection();
        // 开启连接
        connection.start();
        // 创建Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建Destination,应该使用topic
        Topic topic = session.createTopic("test-topic");
        // 创建一个Producer对象
        MessageProducer producer = session.createProducer(topic);
        // 创建一个TextMessage对象
        TextMessage textMessage = session.createTextMessage("hello activemq topic");
        // 发送消息
        producer.send(textMessage);
        // 关闭资源
        producer.close();
        session.close();
        connection.close();
    }

```
#### Consumer


# ActiveMQ域Spring整合
## 发送消息
1. 第一步: 初始化一个spring容器
2. 第二步: 从容器中获得JMSTemplate对象。
3. 第三步: 从容器中获得一个Destination对象
4. 第四步: 使用JMSTemplate对象发送消息，需要知道Destination

### 导入相关jar包
```xml
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-jms</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-context-support</artifactId>
</dependency>
```

### 编写spring配置文件
1. 配置ConnectionFactory
2. 配置生产者
3. 配置消费者队列Destination
4. 配置消费者主题Destination
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:p="http://www.springframework.org/schema/p"
       xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
        http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-4.0.xsd">

    <!-- 真正可以产生Connection的ConnectionFactory,由对应的服务厂商提供 -->
    <bean id="targetConnectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
        <property name="brokerURL" value="tcp://192.168.85.138:61616"/>
    </bean>

    <!-- Spring用于管理的真正的ConnectionFactory的ConnectionFactory -->
    <bean id="connectionFactory" class="org.springframework.jms.connection.SingleConnectionFactory">
        <!-- 目标ConnectionFactory对应真是的可以产生JMS Connection的ConnectionFactory -->
        <property name="targetConnectionFactory" ref="targetConnectionFactory"/>
    </bean>

    <!-- 配置生产者 -->
    <!-- Spring JmsTemplate对象,发送消息 -->
    <bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
        <!-- 这个connectionFactory对应的是我们定义的Spring提供的ConnectionFactory -->
        <property name="connectionFactory" ref="connectionFactory"/>
    </bean>

    <!-- 配置队列的Destination,点对点 -->
    <bean id="queueDestination" class="org.apache.activemq.command.ActiveMQQueue">
        <constructor-arg name="name" value="spring-queue"/>
    </bean>

    <!-- 配置主题的Destination,一对多的 -->
    <bean id="topicDestination" class="org.apache.activemq.command.ActiveMQTopic">
        <constructor-arg name="name" value="spring-topic"/>
    </bean>
</beans>
```

### 使用JMSTemplate发送消息
```java
package cn.devinkin.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;


/**
 * @author devinkin
 * <p>Title: TestSpringActiveMq</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 17:24 2018/9/26
 */
public class TestSpringActiveMq {

    // 使用jmsTemplate发送消息
    @Test
    public void testJmsTemplate() throws Exception {
        // 初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        // 从容器中获得JmsTemplate
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        // 从容器中获得Destination
        Destination destination = (Destination) applicationContext.getBean("queueDestination");
        // 发送消息
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage("spring activemq send queue message");
                return message;
            }
        });
    }
}

```

## 接收消息
1. 第一步: 把Activemq相关的jar包添加到工程中
2. 第二步: 创建一个MessageListener的实现类
3. 第三步: 配置spring和Activemq整合
4. 第四步: 测试代码
### 创建一个MessageListener的实现类
```java
package cn.devinkin.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author devinkin
 * <p>Title: MyMessageListener</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 19:45 2018/9/26
 */
public class MyMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            // 接收到消息
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

```
