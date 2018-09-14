# SpringMVC入门
## SpringMVC框架图
![](../img/springmvc01.png)

## SpringMVC简介
1. SpringMVC是一个表现层框架
2. 作用:就是从请求中接收传入的参数,将处理后的结果数据返回给页面展示


# SpringMVC入门案例
1. 创建工程
2. 导入jar包
3. 创建jsp页面
4. 创建springMVC.xml
5. 创建log4j日志(用于显示错误信息和debug)
6. 配置前端控制器

## 创建jsp页面
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查询商品列表</title>
</head>
<body>
<form action="${pageContext.request.contextPath }/item/queryitem.action" method="post">
    查询条件：
    <table width="100%" border=1>
        <tr>
            <td><input type="submit" value="查询"/></td>
        </tr>
    </table>
    商品列表：
    <table width="100%" border=1>
        <tr>
            <td>商品名称</td>
            <td>商品价格</td>
            <td>生产日期</td>
            <td>商品描述</td>
            <td>操作</td>
        </tr>
        <c:forEach items="${itemList }" var="item">
            <tr>
                <td>${item.name }</td>
                <td>${item.price }</td>
                <td><fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${item.detail }</td>

                <td><a href="${pageContext.request.contextPath }/itemEdit.action?id=${item.id}">修改</a></td>

            </tr>
        </c:forEach>

    </table>
</form>
</body>

</html>

```

## 创建springMVC.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xmlns:mvc="http://www.springframework.org/schema/mvc" xmlns:contex="http://www.springframework.org/schema/jee"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd
        http://code.alibabatech.com/schema/dubbo
        http://code.alibabatech.com/schema/dubbo/dubbo.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context-4.0.xsd
        http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee.xsd">


    <!-- 配置@Controller注解扫描 -->
    <context:component-scan base-package="cn.devinkin.controller"/>

    <!-- 配置视图解析器: 如何把 handler 方法返回值解析为实际的物理视图 -->
    <!--<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">-->
        <!--<property name="prefix" value="/WEB-INF/jsp/"></property>-->
        <!--<property name="suffix" value=".jsp"></property>-->
    <!--</bean>-->

</beans>
```

## 创建log4j日志(debug很重要)
```properties
# Global logging configuration
log4j.rootLogger=DEBUG, stdout
# Console output...
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
```

## 配置前端控制器
1. 在web.xml中
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
  <servlet>
    <servlet-name>SprintMVC</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>

    <!-- 如果没有指定springMvc核心配置文件那么默认会去找/WEB-INF/+<servlet-name>中的内容 +   -servlet.xml配置文件 -->
    <!-- 指定springMvc核心配置文件位置 -->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:SpringMvc.xml</param-value>
    </init-param>

    <!-- tomcat启动的时候就加载这个servlet -->
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>SprintMVC</servlet-name>
    <url-pattern>*.action</url-pattern>
  </servlet-mapping>

</web-app>
```

# SpringMVC架构
1. SpringMVC三大组件
    - HandlerMapping(处理器映射器):url到请求方法的映射
    - HandlerAdapter(处理器适配器):根据不同的处理器找到不同的处理器适配器去执行
    - ViewResolver(视图解析器):根据不同类型的视图去解析视图
## 架构图
![](../img/springmvc02.png)

## SpringMVC的组件及配置

### 处理器映射器和处理器适配器的配置
1. 配置的原因:
    - 如果没有显式的配置处理器映射器和处理器适配器,那么springMVC会去默认的dispatcherServlet.properties去查找对应的处理器映射器和处理器适配器去使用,每个请求都要扫描一次它的默认配置文件,效率非常低,会降低访问速度,所以要显式的配置处理器映射器和处理器适配器
2. 在SpringMVC.xml中,指定如下配置
```xml   
    <!-- 如果没有显式的配置处理器映射器和处理器适配器,那么springMVC会去默认的
    dispatcherServlet.properties去查找对应的处理器映射器和处理器适配器去使用,每个请求都要扫描
    一次它的默认配置文件,效率非常低,会降低访问速度,所以要显式的配置处理器映射器和处理器适配器
    -->
    <!-- 注解形式的处理器映射器 -->
    <!--<bean class="org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping"></bean>-->
    <!-- 注解形式的处理器适配器 -->
    <!--<bean class="org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter"></bean>-->

    <!-- 配置最新版的处理器映射器 -->
    <!--<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping"></bean>-->
    <!-- 配置最新版的处理器适配器 -->
    <!--<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter"></bean>-->

    <!-- 注解驱动:
    作用:替我们自动配置最新版的注解的处理器映射器和处理器适配器
    -->
    <mvc:annotation-driven/>
```

### 视图解析器的配置
1. 作用:在controller中指定页面路径的时候就不用写页面的完整路径了,可以直接写页面的BaseName即可 -->
```xml
    <!-- 配置视图解析器:
    作用:在controller中指定页面路径的时候就不用写页面的完整路径了,可以直接写页面的BaseName即可 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!-- 真正的页面路径 = 前缀 + 去掉后缀名的页面 + 后缀 -->
        <!-- 前缀-->
        <property name="prefix" value="/WEB-INF/jsp/"></property>
        <!-- 后缀 -->
        <property name="suffix" value=".jsp"></property>
    </bean>
```

# SSM的整合
1. Dao层
    - pojo和映射文件以及接口,使用逆向工程生成
    - SqlMapConfig.xml,mybatis核心配置文件
    - ApplicationContext-dao.xml,整合后spring在dao层的配置
        - 数据源
        - 会话工厂
        - 扫描Mapper
2. Service层
    - 事务,applicationContext-trans.xml
    - @Service注解扫描,ApplicationContext-service.xml
3. controller层
    - SpringMVC.xml
        - 注解扫描,扫描@Controller
        - 注解驱动,替我们显式的配置了最新版的处理器映射器和处理器适配器
        - 视图解析器,显式的配置是为了在Controller中不用每个方法都写页面的全路径
4. web.xml 
    - SpringMVC前端控制器
    - Spring监听器
5. applicationContext.xml
    - 用于整合各个模块的applicationContext文件
    
## Dao层
### SqlMapConfig.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
</configuration>
```
### applicationContext-dao.xml
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

    <!-- 加载配置文件 -->
    <context:property-placeholder location="classpath:db.properties"/>
    <!-- 数据库连接池 -->
    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource"
          destroy-method="close">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <property name="maxActive" value="10"/>
        <property name="maxIdle" value="5"/>
    </bean>
    <!-- mapper配置 -->
    <!-- 让spring管理sqlsessionfactory 使用mybatis和spring整合包中的 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!-- 数据库连接池 -->
        <property name="dataSource" ref="dataSource"/>
        <!-- 加载mybatis的全局配置文件 -->
        <property name="configLocation" value="classpath:SqlMapConfig.xml"/>
    </bean>
    <!-- 配置Mapper扫描器 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="cn.devinkin.dao"/>
    </bean>
</beans>
```

## Service层
### applicationContext-service.xml
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

    <!-- @Service扫描 -->
    <context:component-scan base-package="cn.devinkin.service"></context:component-scan>
</beans>
```

### applicationContext-trans.xml
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


    <!-- 事务管理器 -->
    <bean id="transactionManager"
          class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!-- 数据源 -->
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!-- 通知 -->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <!-- 传播行为 -->
            <tx:method name="save*" propagation="REQUIRED"/>
            <tx:method name="insert*" propagation="REQUIRED"/>
            <tx:method name="delete*" propagation="REQUIRED"/>
            <tx:method name="update*" propagation="REQUIRED"/>
            <tx:method name="find*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="get*" propagation="SUPPORTS" read-only="true"/>
        </tx:attributes>
    </tx:advice>
    <!-- 切面:切入点和通知 -->
    <aop:config>
        <aop:advisor advice-ref="txAdvice"
                     pointcut="execution(* cn.devinkin.service.*.*(..))"/>
    </aop:config>
</beans>
```

## Controller层
### SpringMVC.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:dubbo="http://code.alibabatech.com/schema/dubbo" xmlns:mvc="http://www.springframework.org/schema/mvc"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd
        http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">


    <!-- @Controller注解扫描 -->
    <context:component-scan base-package="cn.devinkin.controller"></context:component-scan>

    <context:annotation-config></context:annotation-config>
    
    <!-- 注解驱动:
    		替我们显示的配置了最新版的注解的处理器映射器和处理器适配器 -->
    <!--<mvc:annotation-driven conversion-service="conversionService"></mvc:annotation-driven>-->
    <mvc:annotation-driven></mvc:annotation-driven>
    
    <!-- 配置视图解析器 
	作用:在controller中指定页面路径的时候就不用写页面的完整路径名称了,可以直接写页面去掉扩展名的名称
	-->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<!-- 真正的页面路径 =  前缀 + 去掉后缀名的页面名称 + 后缀 -->
		<!-- 前缀 -->
		<property name="prefix" value="/WEB-INF/jsp/"></property>
		<!-- 后缀 -->
		<property name="suffix" value=".jsp"></property>
	</bean>
	
	<!-- 配置自定义转换器 
	注意: 一定要将自定义的转换器配置到注解驱动上
	-->
	<!--<bean id="conversionService"-->
		<!--class="org.springframework.format.support.FormattingConversionServiceFactoryBean">-->
		<!--<property name="converters">-->
			<!--<set>-->
				<!--&lt;!&ndash; 指定自定义转换器的全路径名称 &ndash;&gt;-->
				<!--<bean class="cn.devinkin.controller.converter.CustomGlobalStrToDateConverter"/>-->
			<!--</set>-->
		<!--</property>-->
	<!--</bean>-->
</beans>
```

## web.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
  <display-name>ssm0523</display-name>
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
  
  <!-- 加载spring容器 -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<!--<param-value>classpath:applicationContext-service.xml,classpath:applicationContext-trans.xml, classpath:applicationContext-dao.xml</param-value>-->
		<param-value>classpath:applicationContext.xml</param-value>
	</context-param>
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
  
  
  <!-- springmvc前端控制器 -->
  <servlet>
  	<servlet-name>springMvc</servlet-name>
  	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
  	<init-param>
  		<param-name>contextConfigLocation</param-name>
  		<param-value>classpath:SpringMvc.xml</param-value>
  	</init-param>
  	<!-- 在tomcat启动的时候就加载这个servlet -->
  	<load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
  	<servlet-name>springMvc</servlet-name>
  	<url-pattern>*.action</url-pattern>
  </servlet-mapping>
  
  <!-- 配置Post请求乱码 -->
  <filter>
		<filter-name>CharacterEncodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>utf-8</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>CharacterEncodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
  
</web-app>

```

## applicationContext.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans-3.0.xsd    
	http://www.springframework.org/schema/aop    
	http://www.springframework.org/schema/aop/spring-aop-3.0.xsd    
	http://www.springframework.org/schema/tx    
	http://www.springframework.org/schema/tx/spring-tx-3.0.xsd    
	http://www.springframework.org/schema/context    
	http://www.springframework.org/schema/context/spring-context-3.0.xsd">

    <import resource="classpath:applicationContext-service.xml"></import>
    <import resource="classpath:applicationContext-trans.xml"></import>
    <import resource="classpath:applicationContext-dao.xml"></import>
</beans>
```

# 参数绑定
1. 绑定简单数据类型
2. 绑定pojo类型
3. 绑定包装pojo(VO)
4. 自定义参数绑定

## 处理器默认支持的形参
1. HttpServletRequest
2. HttpServletResponse
3. HttpSession
4. Model/ModelMap
    - ModelMap是Model接口的实现类,通过Model或ModelMap向页面传递数据
    
## 绑定简单数据类型
1. 当请求的参数名称和处理器形参名称一致时,会将请求参数与形参进行绑定,
```java
    // SpringMVC可以直接接收基本数据类型,包括String,SpringMVC能够自动进行类型转换
    // Controller方法接收的参数的变量名称必须要等于页面上input框的name属性的值
    @RequestMapping("/updateitem")
    public String updateItem(Integer id, String name, Float price, String detail) throws Exception {
        Items items = new Items();
        items.setId(id);
        items.setName(name);
        items.setPrice(price);
        items.setDetail(detail);
        items.setCreatetime(new Date());
        itemsService.updateItems(items);
        return "success";
    }
```

2. 处理post请求中文乱码,在web.xml中
```xml
  <!-- 配置Post请求乱码 -->
  <filter>
		<filter-name>CharacterEncodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>utf-8</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>CharacterEncodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
   </filter>
```

## 绑定pojo类型
1. SpringMVC可以直接接收pojo类型,要求页面上input的name属性名称必须与pojo类的属性名相同
```java
    @RequestMapping("/updateitem")
         public String updateItem(Items items) throws Exception {
             items.setCreatetime(new Date());
             itemsService.updateItems(items);
             return "success";
         }
```

## 绑定包装pojo(VO)
1. 如果Controller中接收的是Vo,那么页面上的input的name属性值要等于`vo的属性.属性`
```java
    @RequestMapping("/search")
    public String search(QueryVo vo) throws Exception {
        System.out.println(vo);
        return "";
    }
```

## 自定义参数绑定
1. 编写转换器
2. 配置SpringMVC.xml,添加自定义转换器配置和注解驱动

### 编写转换器
```java
package cn.devinkin.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * S - source
 * T - target
 */
public class CustomGlobalStrToDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
```

### 配置SpringMVC.xml
1. 配置自定义转换器
```xml
	<!-- 配置自定义转换器 
	注意: 一定要将自定义的转换器配置到注解驱动上
	-->
	<bean id="conversionService"
		class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
		<property name="converters">
			<set>
				<!-- 指定自定义转换器的全路径名称 -->
				<bean class="cn.devinkin.converter.CustomGlobalStrToDateConverter"/>
			</set>
		</property>
	</bean>
```

2. 将自定义转换器配置到注解驱动中
```xml
    <mvc:annotation-driven conversion-service="conversionService"></mvc:annotation-driven>
```

# SpringMVC和Struts2的区别
1. springmvc的入口是一个servlet即前端控制器，而struts2入口是一个filter过虑器。
2. springmvc是基于方法开发(一个url对应一个方法)，请求参数传递到方法的形参，可以设计为单例或多例(建议单例)，struts2是基于类开发，传递参数是通过类的属性，只能设计为多例。
3. Struts采用值栈存储请求和响应的数据，通过OGNL存取数据， springmvc通过参数解析器是将request请求内容解析，并给方法形参赋值，将数据和视图封装成ModelAndView对象，最后又将ModelAndView中的模型数据通过reques域传输到页面。Jsp视图解析器默认使用jstl。
