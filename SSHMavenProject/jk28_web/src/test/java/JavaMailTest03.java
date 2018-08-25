import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.File;

public class JavaMailTest03 {
    @Test
    public void testJavaMail() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext-mail.xml");

        // 得到邮件的发送对象，专用于邮件发送
        JavaMailSender sender = (JavaMailSender) ac.getBean("mailSender");

        // 发送带有图片附件的邮件
        // 创建一封允许待图片，同时带有附件的邮件对象
        MimeMessage mimeMessage = sender.createMimeMessage();

        // 为了更好的操作MimeMessage对象，借用一个工具类来操作它
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        // 设置发送者
        helper.setFrom("webstorepro@163.com");
        helper.setTo("devinkin@163.com");

        // 通过工具类，设置主题，内容，以及附件图片
        helper.setSubject("这是来自Spring+附件的邮件");
        // 第二个参数说明内容要解析为html代码
        helper.setText("<html><head><body><h1>hello!!spring image html mail</h1>" +
                "<a href='http://www.baidu.com'>baidu</a>" + "<img src=cid:image/>" +
                "</body></head></html>",true);

        // 从磁盘中加载图片
        FileSystemResource img = new FileSystemResource(new File("/home/king/Pictures/Wallpapers/224783.jpg"));
        FileSystemResource zip = new FileSystemResource(new File("/home/king/Downloads/intellij-haskell.zip"));
        // 添加图片
        helper.addInline("image", img);
        helper.addAttachment("intellij-haskell.zip", zip);
        sender.send(mimeMessage);
    }
}
