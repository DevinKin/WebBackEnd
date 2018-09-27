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
            System.out.println(text);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
