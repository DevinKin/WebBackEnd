import org.junit.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.util.Properties;

public class JavaMailTest01 {
    @Test
    public void testJavaMail() throws MessagingException {
        Properties props = new Properties();
        // 指定邮件发送服务器地址
        props.put("mail.smtp.host", "smtp.163.com");
        // 服务器是否要验证用户的身份信息
        props.put("mail.smtp.auth", "true");

        // 创建验证器
//        Authenticator auth = new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("webstorepro", "webstorepro123");
//            }
//        };

        // 得到Session
//        Session session = Session.getInstance(props,auth);
        Session session = Session.getInstance(props);
        // 代表debug模式，可以在控制台输出smtp协议应答过程
        session.setDebug(true);

        // 创建一个MimeMessage格式的邮件
        MimeMessage message = new MimeMessage(session);

        // 设置发送者
        Address fromAddress = new InternetAddress("webstorepro@163.com");
        message.setFrom(fromAddress);

        // 设置接受者
        Address toAddress = new InternetAddress("devinkin@163.com");
        message.setRecipient(Message.RecipientType.TO, toAddress);

        // 设置邮件的主题
        message.setSubject("测试邮件，jk28");
        // 设置邮件的内容
        message.setText("我是管理员,cgx");

        // 保存邮件
        message.saveChanges();

        // 得到发送邮件的对象
        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.163.com", "webstorepro", "webstorepro123");
        transport.sendMessage(message, message.getAllRecipients());
        // 关闭通道
        transport.close();

//        Transport.send(message);

    }
}
