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
