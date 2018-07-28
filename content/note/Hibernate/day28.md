# 框架和CRM项目的整体介绍
## 什么是CRM
1. CRM(Customer Relationship Management)客户关系管理，是利用响应的信息技术以及互联网技术来协调企业与顾客在销售、营销和服务上的交互，向客户提供创新式的个性化的客户交互和服务的过程
2. 其最终目标是将面向客户的各项信息和活动收集起来，组建一个客户为中心的企业，实现面向客户的全面管理

## CRM的模块
1. CRM系统实现了对企业销售、营销、服务等各阶段的客户信息、客户活动进行统一管理
2. CRM系统功能涵盖企业销售、营销、用户服务等各个业务流程、业务流程与客户相关活动都会在CRM系统统一管理
3. 下列列出一些基本的功能模块，包括
    - 客户信息管理
    - 联系人管理
    - 商机管理
    - 统计分析等

![](../note/01-CRM模块.bmp)
   
## 模块的具体功能
### 客户信息管理
- 对客户信息统一维护，客户是指存量用户或拟营销的客户，通过员工录入形成公司的“客户库”是公司重要的数据资源。

### 联系人管理
- 对客户的联系人信息统一管理，联系人是指客户企业的联系人，即企业的业务人员和客户的哪些人在打交道

### 客户摆放管理
- 业务员要开发客户需要去拜访客户，客户拜访信息记录了业务员与客户沟通交流方面的不足，采取的策略不当、有待改进的地方或值得分享的的沟通技巧等方面的信息

### 综合查询
- 客户相关信息查询，包括：客户信息查询、联系人信息查询、商机信息查询等

### 统计分析
- 按分类统计客户信息，包括：客户信息来源统计、按行业统计客户、客户发展数量统计等

### 系统管理
- 系统管理属于CRM系统基础功能模块，包括：数据字典、账户管理、角色管理、权限管理、操作日志管理等

![](../note/02-SSH框架.bmp)

# Hibernate框架
## 概述
1. Hibernate是一个开放源代码对象关系映射(ORM)框架，它对JDBC进行了非常轻量级的对象封装，使得Java程序员可以随心所欲的使用对象编程思想来操纵数据库。
2. Hibernate可以应用在任何使用JDBC场合，既可以在Java的客户端程序使用，也可以在Servlet/JSP的Web应用中使用。
3. Hibernate是轻量级的JavaEE应用的持久层解决方案，是一个关系数据ORM框架
4. 总结：HIbernate是一个持久层的ORM框架！

## 什么是ORM(对象关系映射)
1. ORM映射：Object Relational Mapping
    - O：面向对象领域的Object(JavaBean对象)
    - R：关系数据领域的Relational(表的结构)
    - M：映射Mapping(XML的配置文件)
    
![](../note/04-ORM概述.bmp)
    
## Hibernate的优点
1. Hibernate对JDBC访问数据库的代码做了封装，大大简化了数据访问层繁琐重复性代码
2. Hibernate是一个基于JDBC的主流持久化框架，是一个优秀的ORM实现，它很大程度的简化了DAO层的编码工作
3. Hibernate的性能非常好，因为它是一个轻量级框架。映射的灵活性很出色。它支持很多关系型数据库，从一对一到多对多的各种复杂关系

## 学习路线
1. 第一天：框架的入门，自己动手搭建框架，完成曾删改查的操作
2. 第二天：主要学习一级缓存，事务管理和基本的查询
3. 第三天：主要学习一对多和多对多的操作等
4. 第四天：基本查询和查询的优化

![](../note/03-Hibernate的开发位置.bmp)


# HIbernate的使用入门
## 第一步：下载Hibernate5的运行环境
1. 下载相应的jar包等
	- `http://sourceforge.net/projects/hibernate/files/hibernate-orm/5.0.7.Final/hibernate-release-5.0.7.Final.zip/download`
2. 解压后对目录结构有一定的了解

## 第二步：创建表结构
```mysql
Create database hibernate_day01;
Use hibernate_day01;

CREATE TABLE `cst_customer` (
	`cust_id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '客户编号(主键)',
	`cust_name` varchar(32) NOT NULL COMMENT '客户名称(公司名称)',
	`cust_user_id` bigint(32) DEFAULT NULL COMMENT '负责人id',
	`cust_create_id` bigint(32) DEFAULT NULL COMMENT '创建人id',
	`cust_source` varchar(32) DEFAULT NULL COMMENT '客户信息来源',
	`cust_industry` varchar(32) DEFAULT NULL COMMENT '客户所属行业',
	`cust_level` varchar(32) DEFAULT NULL COMMENT '客户级别',
	`cust_linkman` varchar(64) DEFAULT NULL COMMENT '联系人',
	`cust_phone` varchar(64) DEFAULT NULL COMMENT '固定电话',
	`cust_mobile` varchar(16) DEFAULT NULL COMMENT '移动电话',
	PRIMARY KEY (`cust_id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8;
```

## 第三步：搭建HIbernate的开发环境
1. 创建WEB工程，引入Hibernate开发所需的jar包
	- MySQL的驱动jar包
	- Hibernate开发需要的jar包（开发工具/hibernate-release-5.0.7.Final/lib/required/所有jar包）
	- 日志jar包（开发工具/jar包/log4j/所有jar包）

## 第四步：编写JavaBean实体类
```java
package com.devinkin.domain;

/**
 * 客户的JavaBean
 * @author king
 */
public class Customer {
    /**
     *
     `cust_id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '客户编号(主键)',
     `cust_name` varchar(32) NOT NULL COMMENT '客户名称(公司名称)',
     `cust_user_id` bigint(32) DEFAULT NULL COMMENT '负责人id',
     `cust_create_id` bigint(32) DEFAULT NULL COMMENT '创建人id',
     `cust_source` varchar(32) DEFAULT NULL COMMENT '客户信息来源',
     `cust_industry` varchar(32) DEFAULT NULL COMMENT '客户所属行业',
     `cust_level` varchar(32) DEFAULT NULL COMMENT '客户级别',
     `cust_linkman` varchar(64) DEFAULT NULL COMMENT '联系人',
     `cust_phone` varchar(64) DEFAULT NULL COMMENT '固定电话',
     `cust_mobile` varchar(16) DEFAULT NULL COMMENT '移动电话',
     */
    //以后使用包装类，默认值是null
    private Long cust_id;
    private String cust_name;
    private Long cust_user_id;
    private Long cust_create_id;
    private String cust_source;
    private String cust_industry;
    private String cust_level;
    private String cust_linkman;
    private String cust_phone;
    private String cust_mobile;

    public Long getCust_id() {
        return cust_id;
    }

    public void setCust_id(Long cust_id) {
        this.cust_id = cust_id;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public Long getCust_user_id() {
        return cust_user_id;
    }

    public void setCust_user_id(Long cust_user_id) {
        this.cust_user_id = cust_user_id;
    }

    public Long getCust_create_id() {
        return cust_create_id;
    }

    public void setCust_create_id(Long cust_create_id) {
        this.cust_create_id = cust_create_id;
    }

    public String getCust_source() {
        return cust_source;
    }

    public void setCust_source(String cust_source) {
        this.cust_source = cust_source;
    }

    public String getCust_industry() {
        return cust_industry;
    }

    public void setCust_industry(String cust_industry) {
        this.cust_industry = cust_industry;
    }

    public String getCust_level() {
        return cust_level;
    }

    public void setCust_level(String cust_level) {
        this.cust_level = cust_level;
    }

    public String getCust_linkman() {
        return cust_linkman;
    }

    public void setCust_linkman(String cust_linkman) {
        this.cust_linkman = cust_linkman;
    }

    public String getCust_phone() {
        return cust_phone;
    }

    public void setCust_phone(String cust_phone) {
        this.cust_phone = cust_phone;
    }

    public String getCust_mobile() {
        return cust_mobile;
    }

    public void setCust_mobile(String cust_mobile) {
        this.cust_mobile = cust_mobile;
    }
}

```

## 第五步：创建类与表结构的映射
1. 在JavaBean所在的包下创建映射的配置文件
    1. 默认的命名规则为：`实体类.hbm.xml`
    
2. 如果不能上网，编写配置文件是没有提示的，需要自己来配置
    1. 先复制http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd --> window --> preferences --> 搜索xml --> 选择xml catalog --> 点击add --> 现在URI --> 粘贴复制的地址 --> 选择location，选择本地的DTD的路径
    	
3. 编写映射的配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">

<hibernate-mapping>
    <!-- 配置类和表结构的映射 -->
    <class name="com.devinkin.domain.Customer" table="cst_customer">
        <!-- 配置id，主键 -->
        <!-- 见到name属性，JavaBean的属性
             见到column的属性，是表结构的字段 -->
        <id name="cust_id" column="cust_id">
            <!-- 主键的生成策略 -->
            <generator class="native"/>
        </id>

        <!-- 配置其他的属性 -->
        <property name="cust_name" column="cust_name"/>
        <property name="cust_user_id" column="cust_user_id"/>
        <property name="cust_create_id" column="cust_create_id"/>
        <property name="cust_source" column="cust_source"/>
        <property name="cust_industry" column="cust_industry"/>
        <property name="cust_level" column="cust_level"/>
        <property name="cust_linkman" column="cust_linkman"/>
        <property name="cust_phone" column="cust_phone"/>
        <property name="cust_mobile" column="cust_mobile"/>
    </class>
</hibernate-mapping>
```

## 第六步：编写Hibernate核心的配置文件
- 在src目录下，创建名为`hibernate.cfg.xml`的配置文件
- 在XML中引入DTD约束
```xml
<!DOCTYPE hibernate-configuration PUBLIC
	"-//Hibernate/Hibernate Configuration DTD 3.0//EN"
	"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
```
- 打开：开发工具/资料/hibernate-release-5.0.7.Final/project/etc/hibernate.properties，可以查看具体的配置信息
    - 必须配置的四大参数
        1. `#hibernate.connection.driver_class com.mysql.jdbc.Driver`
        2. `#hibernate.connection.url jdbc:mysql:///test`
        3. `#hibernate.connection.username gavin`
        4. `#hibernate.connection.password`
        
    - 数据库的方言(必须配置的)
        - `#hibernate.dialect org.hibernate.dialect.MySQLDialect`
    
    - 可选的配置
        1. `#hibernate.show_sql true`
        2. `#hibernate.format_sql true`
        3. `#hibernate.hbm2ddl.auto update`
        
    - 引入映射配置文件(一定要注意，要引入映射文件，框架需要加载映射文件)
        - `<mapping resource="com/devinkin/domain/Customer.hbm.xml"/>`
        
- 具体的配置如下：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
	"-//Hibernate/Hibernate Configuration DTD 3.0//EN"
	"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
	<!-- 记住：先配置SessionFactory标签，一个数据库对应一个SessionFactory标签 -->
	<session-factory>
        <!-- 必须要配置的参数，有5个，四大参数，数据库的方言 -->
        <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
        <property name="hibernate.connection.url">jdbc:mysql:///hibernate_day01</property>
        <property name="hibernate.connection.username">dbuser</property>
        <property name="hibernate.connection.password">secret</property>

        <!-- 数据库的方言 -->
        <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>

		<!-- 可选配置 -->

        <!-- 映射配置文件，需要引入映射的配置文件 -->
        <mapping resource="/com/devinkin/domain/Customer.hbm.xml"/>
	</session-factory>
</hibernate-configuration>
```

## 第七步：编写Hibernate入门代码
1. 具体代码如下
```java
package com.devinkin.test;

import com.devinkin.domain.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 * 测试HIbernate框架
 * @author king
 */
public class Demo1 {

    /**
     * 测试保存客户
     */
    @Test
    public void testSave() {
        /**
         * 1. 先加载配置文件
         * 2. 创建SessionFactory对象，生成Session对象
         * 3. 创建Session对象
         * 4. 开启事务
         * 5. 编写保存的代码
         * 6. 提交事务
         * 7. 释放资源
         */

        //1. 先加载配置文件
        Configuration config = new Configuration();
        //默认加载src目录下hibernate.cfg.xml的配置文件
        config.configure();

        //2. 创建SessionFactory对象，生成Session对象
        SessionFactory factory = config.buildSessionFactory();

        //3. 创建Session对象
        Session session = factory.openSession();

        //4. 开启事务
        Transaction tr = session.beginTransaction();

        //5. 编写保存的代码
        Customer customer = new Customer();
        //customer.setCust_id();    主键是自动递增了
        customer.setCust_name("测试");
        customer.setCust_level("2");
        customer.setCust_phone("110");

        //保存数据，操作对象就相当于操作数据库的表结构
        session.save(customer);

        //6. 提交事务
        tr.commit();

        //7. 释放资源
        session.close();
        factory.close();
    }
}
```


# Hibernate配置文件之映射配置文件
- 映射配置文件，即`Customer.hbm.xml`的配置文件
1. `<class>标签`：用来将类与数据库表建立映射关系
    - `name`：类的全路径
    - `table`：表名.(类名与表名一致，那么table属性也可以省略)
    - `catalog`：数据库的名称，基本上都会省略不写
    
2. `<id>标签`：用来将类中的属性与表中的主键建立映射，id标签就是用来配置主键的。
    - `name`：类中的属性名
    - `column`： 表中的字段名.(如果类中的属性名与表中的字段名一致，那么column可以省略。)
    - `length`：字段的长度，如果数据库已经创建好了，那么length可以不写，如果没有创建好，生成表结构时，length最好指定。
    
3. `<property>标签`：用来将类中的普通属性与表中的字段建立映射
    - `name`：类中的属性名
    - `column`： 表中的字段名.(如果类中的属性名与表中的字段名一致，那么column可以省略。)
    - `length`：数据长度
    - `type`：数据类型，(一般不需要编写，如果写需要按照规则来编写)
        - Hibernate的数据类型：`type="string"`
        - Java的数据类型：`type=java.lang.String`
        - 数据库字段的数据类型：`<column name="name" sql-type="varchar"/>`
   
   
   
# Hibernate配置文件之核心配置文件
## 核心配置文件配置的两种方式
1. 第一种方式是属性文件的形式，即properties的配置文件
    - `hibernate.properties`
        - `hibernate.connection.driver_class=com.mysql.jdbc.Driver`
    - 缺点：不能加载映射的配置文件，需要手动编写代码去加载

2. 第二种方式是XML文件的形式，开发基本都会选择这种方式
    - `hibernate.cfg.xml`
        - `<property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>`
    - 优点
        1. 格式比较清晰
        2. 编写有提示
        3. 可以在该配置文件中加载映射的配置文件(最主要的)

## 关于hibernate.cfg.xml的配置文件方式
### 必须有的配置
1， 数据库的连接信息：
    - `hibernate.connection.driver_class`：连接数据库驱动程序
    - `hibernate.connection.url`：连接数据库URL
    - `hibernate.connection.username`：数据库用户名
    - `hibernate.connection.password`：数据库密码
    
2. 方言：
    - `hibernate.dialect`：操作数据库方言
    
### 可选的配置
1. `hibernate.show_sql`：在控制台显示sql
2. `hibernate.format_sql`：格式化sql
3. `hibernate.hbm2ddl.auto`：通过映射转换成DDL语句
    - `create`：每次都会创建一个新的表，测试的时候使用
    - `create-drop`：每次都会创建一个新的表，当执行结束之后，将创建的这个表删除，测试的时候使用
    - `update`：如果有表，使用原来的表，没有表，创建一个新的表，同时更新表结构
    - `validate`：如果有表，使用原来的表，同时校验映射文件与表中字段是否一致，如果不一致则报错
   
### 加载映射
1. 如果XML方式：`<mapping resource="cn/devinkin/hibernate/domain/Customer.hbm.xml"/>`


# Hibernate相关类和接口
## COnfiguration类和作用
1. Configuration类的作用
    - Configuration对象用于配置并且启动Hibernate。
    - Hibernate应用通过该对象来获取对象-关系映射文件的元数据，以及动态配置HIbernate的属性，然后创建SessionFactory对象。
    - 简单一句话：加载Hibernate的配置文件，可以获取SessionFactory对象。
  
2. Configuration类的其他应用(了解)
    - 加载配置文件的种类，HIbernate支持xml和properties类型的配置文件，在开发中基本都是用XML配置文件的方式
        - 如果采用的是properties的配置文件，那么通过`Configuration configuration = new Configuration();`就可以加载配置文件
            - 但是需要自己手动添加映射文件
            - 如：`config.addResource("com/devinkin/Customer.hbm.xml");`
        - 如果采用的是XML的配置文件，通过`Configuration configuration = new Configuration().configure();`加载配置文件

## SessionFactory类和作用
1. SessionFactory是工厂类，是生成Session对象的工厂类
2. 总结：一般应用使用一个SessionFactory，最好是应用启动时就完成初始化

![](../note/05-SessionFactory.bmp)

### SessionFactory类的特点
1. 由Configuration通过加载配置文件创建该对象
2. SessionFactory对象中保存了当前的数据库配置信息和所有映射关系以及预定义的SQL语句。同事SessionFactory还负责维护Hibernate的二级缓存。
    - 预定义的SQL语句
        - 使用Configuration类创建了SessionFactory对象时，已经在SessionFactory对象中缓存了一些SQL语句。
        - 常见的SQL语句是增删改查(通过主键来查询)
        - 这样做的目的是提高效率
        
3. 一个SessionFactory实例对应一个数据库，应用从对象中获取Session实例。
4. SessionFactory是线程安全的，意味着它的一个实例可以被应用的多个线程共享。
5. SessionFactory是重量级的，意味着不能随意创建或销毁它的实例。如果只访问一个数据库，只需要创建一个SessionFactory实例，且在应用初始化的时候完成。
6. SessionFactory需要一个较大的缓存，用来存放于定义的SQL语句以及实体的映射信息。另外可以配置一个缓存插件，这个插件被称为Hibernate的二级缓存，被多线程所共享

### 编写HibernateUtils的工具类
```java
package com.devinkin.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate框架的工具类
 */
public class HibernateUtils {

    private static final Configuration CONFIG;
    private static final SessionFactory FACTORY;

    // 编写静态代码块，类在加载的时候执行该代码
    static {
        //加载XML的配置文件
        CONFIG = new Configuration().configure();
        //构造工厂
        FACTORY = CONFIG.buildSessionFactory();
    }

    /**
     * 从工厂中获取Session对象
     * @return Session
     */
    public static Session getSession() {
        return FACTORY.openSession();
    }
}
```

## Session接口
### 概述
1. Session是在Hibernate中使用最频繁的接口。也被称为持久化管理器。它提供了和持久化有关的操作，比如添加、修改、删除、加载和查询实体对象
2. Session是应用程序与数据库之间交互操作的一个单线程对象，是Hibernate运作的中心
3. Session是线程不安全的
4. 所有持久化对象必须在session的管理下才可以进行持久化操作
5. Session对象有一个一级缓存，显式执行flush之前，所有的持久化操作的数据都缓存在session对象处。
6. 持久化类与Session关联起来后就具有了持久化的能力

### 特点
1. 不是线程安全的。应避免多个线程同事使用同一个Session实例
2. Session是轻量级的，它的创建和销毁不会消耗太多的资源。应为每次客户请求分配独立的Session实例
3. Session有一个缓存，被称之为Hibernate的一级缓存。每个Session实例都有自己的缓存。

### 常用方法
1. save(Object obj)
2. delete(Object obj)
3. get(Class, id)
4. update(Object obj)
5. saveOrUpdate(Object obj)：保存或者修改(如果没有数据，保存数据，如果有，修改数据)
6. createQuery()：HML语句的查询方式

## Transcation接口
1. Transcation是事务的接口
2. 常用的方法
    - `commit()`：提交事务
    - `rollback()`：回滚事务
   
3. 特点
    - Hibernate框架默认情况下事务不自动提交，需要手动提交事务
    - 如果没有开启事务，那么每个Session的操作，都相当于一个独立的事务


# 案例一：完成客户的CURD的操作
## 需求分析
1. ORM系统中客户信息管理模块的功能包括
    - 新增客户信息
    - 客户信息查询
    - 修改客户信息
    - 删除客户信息

## 开发步骤
1. 准备环境
    - 在`开发工具/crm/ui`