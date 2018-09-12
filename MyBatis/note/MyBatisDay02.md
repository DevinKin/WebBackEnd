# 输入映射和输出映射
## 输入参数映射
1. 传递简单类型
2. 传递pojo对象
3. 传递pojo包装对象

### 传递pojo对象
1. Mybatis使用ognl表达式解析对象字段的值,`#{}`或者`${}`括号中的值为pojo属性名称

### 传递pojo包装对象
1. 开发中通过pojo传递查询条件，查询条件是综合的查询条件，不仅包括用户查询条件还包括其它的查询条件（比如将用户购买商品信息也作为查询条件），这时可以使用包装对象传递输入参数。
2. Pojo类中包含pojo。

#### QueryVo
1. 根据用户名查询用户信息，查询条件放到QueryVo的user属性中。

2. 映射配置文件
```xml
    <select id="findUserByVo" parameterType="cn.devinkin.mypojo.QueryVo" resultType="cn.devinkin.mypojo.User">
        select * from user where username like '%${user.username}%' and sex = #{user.sex}
    </select>
```

3. 接口方法
```java
    public List<User> findUserByVo(QueryVo queryVo);
```

4. 测试代码
```java
    @Test
    public void testFindUserByVo() {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        QueryVo queryVo = new QueryVo();
        User user = new User();
        user.setSex("2");
        user.setUsername("王");
        queryVo.setUser(user);
        List<User> userList = userMapper.findUserByVo(queryVo);
        System.out.println(userList);
        openSession.commit();

```

## 返回值(输出)映射
1. 输出简单类型
2. 输出pojo对象(都是使用pojo对象作为resultType的值)
3. 输出pojo列表(都是使用pojo对象作为resultType的值)

###  输出简单类型
1. mapper配置文件
```xml
    <!-- 只有返回结果为一行一列的时候,那么返回值类型才可以指定为基本类型 -->
    <select id="findUserCount" resultType="java.lang.Integer">
        select count(*) from user
    </select>
```

2. 接口方法
```java
    public Integer findUserCount();
```

3. 测试代码
````java
    @Test
    public void testFindUserCount() {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        Integer userCount = userMapper.findUserCount();
        System.out.println(userCount);
        openSession.commit();
    }

````

# 动态SQL
## if
1. 作用:条件判断

## where
1. 作用:
    - 会自动向sql语句中添加where关键字
    - 会去掉第一个条件的and关键字
## foreach
1. 作用:遍历集合
2. foreach:循环传入的集合参数
3. collection:传入的集合的变量名称
4. item:每次循环将循环出的数据放入这个变量中
5. open:循环开始拼接的字符串
6. close:循环结束拼接的字符串
7. separator:循环中拼接的分隔符

## Sql片段
1. 使用:`<sql id="xxx"></sql>`标签对sql进行封装
2. 使用:`<include refid="xxx"/>`标签对sql进行引用

## 测试实例
### if,where,sql,includde
1. mapper映射文件
```xml
    <!-- 封装sql条件,封装后,可以重用
     id:是这个sql条件的唯一标识
     -->
    <sql id="user_where">
        <where>
            <if test="username != null and username != ''">
                and username like '%${username}%'
            </if>
            <if test="sex != null and sex != ''">
                and sex = #{sex}
            </if>
        </where>
    </sql>

    <select id="findUseByUsernameAndSex" parameterType="cn.devinkin.mypojo.User" resultType="cn.devinkin.mypojo.User">
        select * from user
        <!-- where标签的作用:
            1. 会自动向sql语句中添加where关键字
            2. 会去掉第一个条件的and关键字 -->
        <!-- 调用sql封装的条件 -->
        <include refid="user_where"/>
        <!--<where>-->
            <!--<if test="username != null and username != ''">-->
                <!--and username like '%${username}%'-->
            <!--</if>-->
            <!--<if test="sex != null and sex != ''">-->
                <!--and sex = #{sex}-->
            <!--</if>-->
        <!--</where>-->
    </select>
```

2. 接口方法
```java
    public List<User> findUseByUsernameAndSex(User user);
```

3. 测试代码
```java
    @Test
    public void testFindUserByUsernameAndSex() {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        User user = new User();
        user.setSex("2");
        user.setUsername("王");
        List<User> userList = userMapper.findUseByUsernameAndSex(user);
        System.out.println(userList);
        openSession.commit();
    }
```

### foeach
1. mapper文件
```xml
    <select id="findUserByIds" parameterType="cn.devinkin.mypojo.QueryVo" resultType="cn.devinkin.mypojo.User">
        select * from user
        <where>
            <if test="ids != null">
                <!--
                foreach:循环传入的集合参数
                collection:传入的集合的变量名称
                item:每次循环将循环出的数据放入这个变量中
                open:循环开始拼接的字符串
                close:循环结束拼接的字符串
                separator:循环中拼接的分隔符
                -->
                <foreach collection="ids" item="id" open="id in (" close=")" separator=",">
                    #{id}
                </foreach>
            </if>
        </where>
    </select>
```

2. 接口方法
```java
    public List<User> findUserByIds(QueryVo queryVo);
```

3. 测试代码
```java
    @Test
    public void testFindUserByIds() {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        QueryVo queryVo = new QueryVo();
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(16);
        ids.add(31);
        ids.add(22);
        queryVo.setIds(ids);
        List<User> userList = userMapper.findUserByIds(queryVo);
        System.out.println(userList);
        openSession.commit();
    }
```



# 关联查询
## 一对一关联
### 一对一自动映射
1. mapper映射文件
```xml
    <!-- 一对一自动映射 -->
    <select id="findOrdersAndUser1" resultType="CustomOrders">
        select a.*, b.id uid, username, birthday, sex, address
        from orders a, user b
        where a.user_id = b.id
    </select>
```

2. 接口方法
```java
    public List<CustomOrders> findOrdersAndUser1();
```

3. 对应的pojo类
```java
package cn.devinkin.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class CustomOrders extends Orders{
    private Integer id;
    // 用户名
    private String username;
    // 性别
    private String sex;
    // 生日
    private Date birthday;
    // 地址
    private String address;
}
```

4. 测试代码
```java
    @Test
    public void testFindOrdersAndUser() throws Exception {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        List<CustomOrders> customOrdersList = userMapper.findOrdersAndUser1();
        System.out.println(customOrdersList);
        openSession.commit();
    }
```

### 一对一手动映射
1. mapper映射文件
```xml
    <!-- 一对一手动映射 -->
    <!-- resultMap属性说明:
    id:resultMap的唯一标识
    type:将查询出的数据放入指定的对象中
    注意,手动映射需要指定数据库表的字段名与pojo类的属性名称的对应关系
    -->
    <resultMap id="orderAndUserResultMap" type="Orders">
        <!-- id 标签指定主键字段对应关系
        column: 指定数据库表中的字段
        property: 对应pojo类的属性名称
        -->
        <id column="id" property="id"/>
        <!-- result 标签指定非主键字段的对应关系
        column: 指定数据库表中的字段
        property: 对应pojo类的属性名称
        -->
        <result column="user_id" property="userId"/>
        <result column="number" property="number"/>
        <result column="createtime" property="createtime"/>
        <result column="note" property="note"/>

        <!-- association 标签指定单个对象的对应关系
        property: 指定将数据放入pojo类中的成员类的属性中
        javaType: pojo类的成员类全路径名
         -->
        <association property="user" javaType="User">
            <!--
            column: 指定数据库表中的字段
            property: 对应pojo类的属性名称
            -->
            <id column="uid" property="id"/>
            <result column="username" property="username"/>
            <result column="birthday" property="birthday"/>
            <result column="sex" property="sex"/>
            <result column="address" property="address"/>
        </association>
    </resultMap>

    <select id="findOrdersAndUser2" resultMap="orderAndUserResultMap">
        select a.*, b.id uid, username, birthday, sex, address
        from orders a, user b
        where a.user_id = b.id
    </select>
```

2. 接口方法
```java
    public List<CustomOrders> findOrdersAndUser2();
```

3. pojo类
```java
package cn.devinkin.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Orders {
    private Integer id;
    private Integer userId;
    private String number;
    private Date createtime;
    private String note;

    private User user;
}
```

4. 测试代码
```java
    @Test
    public void testFindOrdersAndUser2() throws Exception {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        List<CustomOrders> customOrdersList = userMapper.findOrdersAndUser2();
        System.out.println(customOrdersList);
        openSession.commit();
    }
```


## 一对多关联
1. mapper文件
```xml
    <resultMap id="userAndOrdersResultMap" type="User">
        <id column="id" property="id"/>
        <result column="username" property="username"/>
        <result column="birthday" property="birthday"/>
        <result column="sex" property="sex"/>
        <result column="address" property="address"/>

        <!-- 指定对应的集合对象关系映射
        property: pojo类中的对象集合的属性
        ofType: 对象集合属性中的泛型类型
        -->
        <collection property="ordersList" ofType="Orders">
            <id column="oid" property="id"/>
            <result column="user_id" property="userId"/>
            <result column="number" property="number"/>
            <result column="createtime" property="createtime"/>
            <result column="note" property="note"/>
        </collection>
    </resultMap>
    <select id="findUserAndOrders" resultMap="userAndOrdersResultMap">
        select a.*, b.id oid, user_id, number, createtime,note
        from user a, orders b where a.id = b.user_id;
    </select>
```

2. 接口方法
```java
    public List<User> findUserAndOrders();
```

3. pojo实现类
```java
package cn.devinkin.pojo;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {
    private int id;
    // 用户名
    private String username;
    // 性别
    private String sex;
    // 生日
    private Date birthday;
    // 地址
    private String address;

    // 用户与订单,一对多
    private List<Orders> ordersList;

}
```

4. 测试代码
```java
    @Test
    public void testFindUserAndOrders() throws Exception {
        SqlSession openSession = factory.openSession();
        // 通过getMapper方法来实例化接口
        UserMapper userMapper = openSession.getMapper(UserMapper.class);
        List<User> userList = userMapper.findUserAndOrders();
        System.out.println(userList);
        openSession.commit();
    }
```

# Mybatis整合sping
1. dataSource需要被Spring管理
2. SqlSessionFactoryBean需要被Spring管理
3. UserDao需要被Spring管理

## 所需的配置文件
1. applicationContext.xml
2. SqlMapConfig.xml
3. log4j.properties
4. db.properties

### applicationContext.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:p="http://www.springframework.org/schema/p"
        xmlns:aop="http://www.springframework.org/schema/aop"
        xmlns:tx="http://www.springframework.org/schema/tx"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
        http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-4.0.xsd">

    <!-- 加载配置文件 -->
    <context:property-placeholder location="classpath:db.properties"/>

    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <property name="maxActive" value="10"/>
        <property name="maxIdle" value="5"/>
    </bean>

    <!-- 整合后会话工厂归spring管理 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="configLocation" value="classpath:SqlMapConfig.xml"/>
        <!-- 指定会话工厂使用的数据源 -->
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!-- 配置原生Dao实现 class必须指定dao实现类的全路径名称 -->
    <bean id="userDao" class="cn.devinkin.dao.UserDaoImpl">
        <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
    </bean>
</beans>

```
### SqlMapConfig.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <properties resource="db.properties"/>

    <typeAliases>
        <!-- 定义单个pojo类别名:
        type:类的全路径名称
        alias:别名
        -->
        <!--<typeAlias type="cn.devinkin.mypojo.User" alias="user"/>-->

        <!-- 使用包扫描的方式批量定义别名
        定义后,别名等于类名,不区分大小写,但建议按照java明明规则来,首字母小写,以后每个单词的首字母大写
        -->
        <package name="cn.devinkin.pojo" />
    </typeAliases>


    <mappers>
        <mapper resource="User.xml"/>

        <!--
 使用class属性引入接口的全路径名称:
 使用规则
     1. 接口名称和映射文件名称除扩展名外要完全一致
     2. 接口和映射文件要放在同一个目录下
 -->
        <!--<mapper class="UserMapper.xml"/>-->
        <!--<mapper class="cn.devinkin.mapper.UserMapper"/>-->

        <!-- 使用包扫描的方式批量引入mapper接口
        使用规则:
            1. 接口名称和映射文件名称除扩展名外要完全一致
            2. 接口和映射文件要放在同一个目录下
         -->
        <package name="cn.devinkin.mapper"/>

    </mappers>

</configuration>

```
### log4j.properties
```properties
# Global logging configuration
log4j.rootLogger=DEBUG, stdout
# Console output...
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n

```
### db.properties
```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8
jdbc.username=dbuser
jdbc.password=111111
```

## 整合原生Dao
### 测试代码
```java
package cn.devinkin.test;

import cn.devinkin.dao.UserDao;
import cn.devinkin.mypojo.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDaoTest {
    private ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        String configLocation = "ApplicationContext.xml";
        applicationContext = new ClassPathXmlApplicationContext(configLocation);
    }


    @Test
    public void testFindUserById() throws Exception{
        // 获取UserDao对象,getBean中的字符串是在ApplicationContext.xml中声明的
        UserDao userDao = (UserDao) applicationContext.getBean("userDao");

        User user = userDao.findUserById(1);
        System.out.println(user);

    }
}

```

## 整合Mapper接口代理实现
### SqlMapConfig.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <properties resource="db.properties"/>

    <typeAliases>
        <!-- 定义单个pojo类别名:
        type:类的全路径名称
        alias:别名
        -->
        <!--<typeAlias type="cn.devinkin.mypojo.User" alias="user"/>-->

        <!-- 使用包扫描的方式批量定义别名
        定义后,别名等于类名,不区分大小写,但建议按照java明明规则来,首字母小写,以后每个单词的首字母大写
        -->
        <package name="cn.devinkin.pojo" />
    </typeAliases>


    <mappers>
        <mapper resource="User.xml"/>

        <!--
 使用class属性引入接口的全路径名称:
 使用规则
     1. 接口名称和映射文件名称除扩展名外要完全一致
     2. 接口和映射文件要放在同一个目录下
 -->
        <!--<mapper class="UserMapper.xml"/>-->
        <!--<mapper class="cn.devinkin.mapper.UserMapper"/>-->

        <!-- 使用包扫描的方式批量引入mapper接口
        使用规则:
            1. 接口名称和映射文件名称除扩展名外要完全一致
            2. 接口和映射文件要放在同一个目录下
         -->
    </mappers>

</configuration>

```
### applicationContext.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:p="http://www.springframework.org/schema/p"
        xmlns:aop="http://www.springframework.org/schema/aop"
        xmlns:tx="http://www.springframework.org/schema/tx"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
        http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-4.0.xsd">

    <!-- 加载配置文件 -->
    <context:property-placeholder location="classpath:db.properties"/>

    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <property name="maxActive" value="10"/>
        <property name="maxIdle" value="5"/>
    </bean>

    <!-- 整合后会话工厂归spring管理 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="configLocation" value="classpath:SqlMapConfig.xml"/>
        <!-- 指定会话工厂使用的数据源 -->
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!-- 配置原生Dao实现 class必须指定dao实现类的全路径名称 -->
    <bean id="userDao" class="cn.devinkin.dao.UserDaoImpl">
        <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
    </bean>

    <!-- Mapper 接口代理实现 -->
    <!--<bean id="userMapper" class="org.mybatis.spring.mapper.MapperFactoryBean">-->
        <!--&lt;!&ndash; mapperInterface 配置的是mapper接口的全路径名称 &ndash;&gt;-->
        <!--<property name="mapperInterface" value="cn.devinkin.mapper.UserMapper"/>-->
        <!--<property name="sqlSessionFactory" ref="sqlSessionFactory"/>-->
    <!--</bean>-->
    <!-- 使用包扫描的方式批量引入Mapper
     扫描后引用的时候,可以使用类名,首字母小写
     -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!-- 指定要扫描的包名,如果有多个包,用英文状态下的逗号进行分隔-->
        <property name="basePackage" value="cn.devinkin.mapper"/>
    </bean>
</beans>

```


# MyBatis逆向工程
## MyBatis逆向工程搭建步骤
1. 创建工程
2. 导入jar包
3. 创建generator.xml文件
3. 生成mapper和pojo类

### 导入jar包
1. 使用Maven工程,提供如下pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>MyBatisGenerator</groupId>
    <artifactId>MyBatisgenerator</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.6</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.3.7</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.46</version>
        </dependency>
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>

        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>


            <resource>
                <directory>src/test/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/test/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
            </resource>
        </resources>

        <plugins>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.7</version>
            </plugin>
        </plugins>
    </build>

</project>
```

### 创建generator.xml文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <context id="testTables" targetRuntime="MyBatis3">
        <commentGenerator>
            <!-- 是否去除自动生成的注释 true：是 ： false:否 -->
            <property name="suppressAllComments" value="true" />
        </commentGenerator>
        <!--数据库连接的信息：驱动类、连接地址、用户名、密码 -->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/mybatis?useSSL=false" userId="dbuser"
                        password="111111">
        </jdbcConnection>
        <!-- <jdbcConnection driverClass="oracle.jdbc.OracleDriver"
            connectionURL="jdbc:oracle:thin:@127.0.0.1:1521:yycg"
            userId="yycg"
            password="yycg">
        </jdbcConnection> -->

        <!-- 默认false，把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer，为 true时把JDBC DECIMAL 和
            NUMERIC 类型解析为java.math.BigDecimal -->
        <javaTypeResolver>
            <property name="forceBigDecimals" value="false" />
        </javaTypeResolver>

        <!-- targetProject:生成PO类的位置 -->
        <javaModelGenerator targetPackage="cn.devinkin.pojo"
                            targetProject=".\src\main\java\">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
            <!-- 从数据库返回的值被清理前后的空格 -->
            <property name="trimStrings" value="true" />
        </javaModelGenerator>
        <!-- targetProject:mapper映射文件生成的位置 -->
        <sqlMapGenerator targetPackage="cn.devinkin.mapper"
                         targetProject=".\src\main\java">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
        </sqlMapGenerator>
        <!-- targetPackage：mapper接口生成的位置 -->
        <javaClientGenerator type="XMLMAPPER"
                             targetPackage="cn.devinkin.mapper"
                             targetProject=".\src\main\java">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
        </javaClientGenerator>
        <!-- 指定数据库表 -->
        <table schema="" tableName="user"></table>
        <table schema="" tableName="orders"></table>

        <!-- 有些表的字段需要指定java类型
         <table schema="" tableName="">
            <columnOverride column="" javaType="" />
        </table> -->
    </context>
</generatorConfiguration>

```

### 生成mapper和pojo类
1. 如果使用mybatis官方的类生成,java代码如下
```java
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StartServer {
    public void generator() throws Exception {

        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //指定 逆向工程配置文件
        File configFile = new File("F:\\WebBackEnd\\MyBatisgenerator\\src\\main\\resources\\generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);

    }

    public static void main(String[] args) throws Exception {
        try {
            StartServer startServer = new StartServer();
            startServer.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
```

2. 亦可使用mybatis-generator插件进行生成,但是需要在generatorConfig.xml文件中必须提供如下配置,以指定数据库驱动的路径
```xml
<classPathEntry location="/Program Files/IBM/SQLLIB/java/db2java.zip" />
```