import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class JavaMailTest02 {
    @Test
    public void testJavaMail() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext-mail.xml");

        // 加载简单邮件对象
        SimpleMailMessage message = (SimpleMailMessage) ac.getBean("mailMessage");

        // 设置简单邮件对象的属性
        message.setSubject("spring与javamail的测试");
        message.setText("hello, spring and javamail");
        message.setTo("devinkin@163.com");

        // 得到邮件的发送对象，专用于邮件发送
        JavaMailSender sender = (JavaMailSender) ac.getBean("mailSender");

        sender.send(message);
    }
}
