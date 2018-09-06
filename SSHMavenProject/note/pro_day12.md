# WebService概述
1. WebService是一个Web服务,可以跨平台跨语言的异构系统数据机交互的技术,即跨平台远程调用技术

## WebService规范及三要素
### 规范
1. JAX-WS
2. JAXM&SAAJ
3. JAX-RS

### 三要素
1. SOPA
2. WSDL
3. 

#### SOAP
1. soap协议是webservice的传输协议,即简单对象访问协议.
2. Soap协议是xml格式,理解为给予http传输xml数据.Soap=http+xml
3. 特点
    - 跨平台
    - 跨语言
    - w3c指定的标准
4. 各个开发语言都按照相同的标准实现webservice的开发

#### WSDL
1. WSDL是webservice的使用说明是
2. WSDL指网络服务描述语言(Web Services Description Language),是一种使用XML编写的文档,用于描述网络服务,也可以用于定位网络服务.


#### UDDI
1. 统一描述,发现和集成协议是一个目录服务,存储了全球的webservice地址
2. UDDI旨在将全球的webservice资源进行共享

# CXF框架

## 什么是CXF框架
1. CXF,apache下的webservice的开源框架
2. 支持多种协议,SOAP,XML/HTTP,REST HTTP或者CORBA
3. 灵活的部署:可以运行由Tomcat,Jboss,weblogic,Jetty(内置)上面

## WebService服务端
1. 建立工程,导入jar包
2. 创建服务类,添加注解`@WebService`
```java
package cn.devinkin.webservice;

import javax.jws.WebService;

@WebService  // 它作为一个WebService标志
public class IWeatherServiceImpl implements IWeatherService{
    @Override
    public String getWeatherByCityName(String cityName) {
        if ("北京".equals(cityName)) {
            return "晴";
        } else if  ("成都".equals(cityName)) {
            return "小雨";
        }
        return "查询失败";
    }
}
```
3. 发布webservice
```java
package cn.devinkin.publisher;


import cn.devinkin.webservice.IWeatherServiceImpl;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class PublisherTest {
    public static void main(String[] args) {
        // 1. 创建一个工厂类
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();

        // 2. 设置服务的地址
        factory.setAddress("http://localhost:12345/weather");

        // 3. 设置背后工作的实现类(设置服务类)
        factory.setServiceBean(new IWeatherServiceImpl());

        // 4. 创建webservice服务
        factory.create();
    }
}
```

4. 测试访问地址`http://localhost:12345/weather`

## WebService客户端
1. 建立工程,导入jar包

2. 使用`wsimport`生成本地代码,该命令在jdk的bin目录下
```
wsimport -s F:\WebBackEnd\SSHMavenProject\ws2802\src http://localhost:12345/weather
```

3. 调用远程服务
```java
package cn.devinkin.webservice.invoking;

import cn.devinkin.webservice.IWeatherServiceImpl;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Test;

public class WeatherServiceInvokingClient {
    @Test
    public void testWeather(){
        // 1. 生成一个客户代理工厂
        JaxWsProxyFactoryBean client = new JaxWsProxyFactoryBean();

        // 2.设置服务端的访问地址
        client.setAddress("http://localhost:12345/weather?wsdl");

        // 3. 设置服务端的接口
        client.setServiceClass(IWeatherServiceImpl.class);

        // 4. 创建客户端对象
       IWeatherServiceImpl iws = (IWeatherServiceImpl) client.create();

       // 5. 调用远程的服务端提供的方法
        String result = iws.getWeatherByCityName("北京");
        System.out.println(result);
    }
}
```

# Spring和CXF整合

## 服务端创建步骤
1. 创建WEB工程,添加JAR包

2. 修改web.xml,添加spring监听
```xml
    <!-- spring 监听器 -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <!-- 加载spring的配置文件 -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>
```

3. 修改web.xml,添加CXFServlet
```xml
    <!-- CXF的核心控制器的配置 -->
    <servlet>
        <servlet-name>cxfServlet</servlet-name>
        <servlet-class>org.apache.cxf.transport.servlet.CXFServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>cxfServlet</servlet-name>
        <url-pattern>/ws/*</url-pattern>
    </servlet-mapping>
```

4. 添加spring配置文件

5. 编写服务类

6. 修改spring配置文件, 添加bean和发布服务
```xml
    <!-- 服务类-->
    <bean id="iWeatherService" class="cn.devinkin.webservice.IWeatherServiceImpl">
    </bean>

    <!-- 发布服务 -->
    <jaxws:server address="/weather">
        <jaxws:serviceBean>
            <ref bean="iWeatherService"/>
        </jaxws:serviceBean>
    </jaxws:server>
```

## 客户端创建步骤
1. 创建java工程,添加jar包
2. wsimport生成本地代码
```xml
wsimport -s F:\WebBackEnd\SSHMavenProject\ws2804\src http://localhost:8080/ws2803/ws/weather?wsdl
```
3. 编写spring配置文件
```java
    <!-- 配置客户端 -->
    <jaxws:client id="weatherBean" address="http://localhost/ws2803/ws/weather?wsdl"
                serviceClass="cn.devinkin.webservice.IWeatherServiceImpl">
    </jaxws:client>
```
4. 获取portType调用方法
```java
package cn.devinkin.webservice.test;

import cn.devinkin.webservice.IWeatherServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringCxfTest {
    @Test
    public void test1() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        IWeatherServiceImpl iws = (IWeatherServiceImpl) ac.getBean("weatherClient");
        String result = iws.getWeatherByCityName("成都");
        System.out.println(result);
    }
}
```
