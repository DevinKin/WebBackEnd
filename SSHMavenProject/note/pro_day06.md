# JavaMail环境搭建
1. 引入JavaMail，在pom.xml中添加如下依赖
```xml
        <dependency>
            <groupId>javax.mail</groupId>
            <artifactId>mail</artifactId>
            <version>1.4.4</version>
        </dependency>
```

2. 为了让Sring与JavaMail集成，在pom.xml添加如下依赖
```xml
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
            <version>${spring.version}</version>
        </dependency>
```

## 传统的邮件开发
```java
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
```

## Spring提供的JavaMail的API

### Spring整合JavaMail的配置文件的编写

#### 在src目录下提供mail.properties
```properties
mail.smtp.host=smtp.163.com
mail.smtp.auth=true
mail.username=webstorepro
mail.password=webstorepro123
mail.from=webstorepro@163.com
```

#### 使用spring配置
```xml
    <description>JavaMail配置文件</description>

    <!-- 加载mail.properties -->
    <context:property-placeholder location="classpath:mail.properties"/>

    <!-- 配置一个简单的邮件对象 -->
    <bean id="mailMessage" class="org.springframework.mail.SimpleMailMessage">
        <property name="from" value="${mail.from}"></property>
    </bean>

    <!-- 邮件的发送对象 -->
    <bean id="mailSender" class="org.springframework.mail.javamail.JavaMailSenderImpl">
        <property name="host" value="${mail.smtp.host}"></property>
        <property name="username" value="${mail.username}"></property>
        <property name="password" value="${mail.password}"></property>
        <property name="defaultEncoding" value="UTF-8"></property>

        <!-- 邮件发送相关的配置信息 -->
        <property name="javaMailProperties">
            <props>
                <prop key="mail.smtp.auth">${mail.smtp.auth}</prop>
                <prop key="mail.debug">true</prop>
                <prop key="mail.smtp.timeout">0</prop>
            </props>
        </property>
    </bean>
```

### 测试代码
```java
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
```

### 发送带有图片的邮件，以嵌入HTML的方式
```java
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

```
