# JDBC
1. Java操作数据库，jdbc是oracle公司指定的一套接口

2. 驱动：jdbc实现类，由数据库厂商提供

## JDBC的作用
1. 连接数据库
2. 发送sql语句
3. 处理结果

## JDBC操作步骤
1. 导入驱动jar包
2. 编码：
    1. 注册驱动
    2. 获取连接
    3. 编写sql
    4. 创建预编译的语句执行者
    5. 设置参数
    6. 执行sql语句
    7. 处理结果
    8. 释放资源

# JDBC-api
## DriverManager：管理了一组jdbc的操作的一个类
### 常用方法
1. 注册驱动`static void registerDriver(Driver driver)`
2. 获取连接`static Connection getConnection(String url, String user, String password)`
    1. url：访问数据库的路径
        1. 格式：协议:数据库类型:子协议 参数
        2. mysql：`jdbc:mysql://localhost:3306/数据库名称`
        3. oracle：`jdbc:oracle:thin@localhost:1521@实例`
    2. user:账户名
    3. password：密码

## Connection:连接接口
### 常用方法
1. 获取语句执行者
    1. `Statement createStatement()`：获取普通的语句执行者，会出现sql注入
    2. `PreparedStatement prepareStatement(String sql)`：获取预编译语句执行者
    3. `CallableStatement prepareCall(String sql)`：获取调用存储过程的语句执行者
2. 事务相关方法
    1. `setAutoCommit(false)`：手动开启事务
    2. `commit()`：提交事务
    3. `rollback()`：事务回滚
    
## Statement：语句执行者接口
## PreparedStatement：预编译语句执行者接口
1. 常用方法
    1. `setXXX(int pos, Object arg)`;
    2. `ResultSet executeQuery()`：执行r语句，返回值：结果集
    3. `int executeUpdate()`：执行cud语句，返回值：影响的行数
    
## ResultSet：结果集接口
1. 常用方法
    1.`boolean next()`：判断是否有下一条记录，若有返回true并移动光标到下一行，光标一开始在第一行的前面
    2. `getXXX(int pos)`：获取第n列的内容
    3. `getXXX(String 字段名)`：获取字段名所在列的内容

# 将数据库连接信息写入配置文件
## 常见配置文件格式
1. `.properties`
    1. 里面存放key=value
2. xml

## 快速读入properties配置文件
1. 如果我们的配置文件为properties，并且放在src目录下
2. 我们可以通过ResourceBundle工具快速获取里面的配置信息
3. 使用步骤
    1. 获取ResourceBundle对象`static ResourceBundle getBundle(String 不带后缀名的文件名)`
    2. 通过ResourceBundle对象获取配置信息`String getString(String key)`：通过指定key获取指定的value

# com.mysql.jdbc.Driver
1. com.mysql.jdbc.Deiver的源代码中，有如下代码
```java
//类被加载到内存中会执行静态代码块，并且只执行一次
//现在只需要将类加载到内存中即可：
    //方式1：Class.forName("全限定名");    //包名+类名 com.mysql.jdbc.Driver
    //方式2：类名.class;
    //方式3：对象.getClass();
static {
    try {
        DriverManager.registerDriver(new Driver());
    } catch (SQLException var1) {
        throw new RuntimeException("Can't register driver!");
    }
}
```

# 连接池概述
1. 管理数据库的连接
2. 连接池的作用
    1. 提高项目的性能
3. 就是在连接池初始化的时候存入一定数量的连接，用的时候通过方法获取，不用的时候归还连接

## 连接池规范
1. 所有的连接池必须实现一个接口：`java.sql.DataSource`接口
2. 获取连接的方法：`Connection getConnection()`
3. 归还连接的方法：`Connection.close()`

## 常用连接池
1. DBCP
    1. apache公司开发的
    2. dbcp没有自动回收空闲连接的功能
2. C3P0
    1. c3p0有自动回收空闲连接的功能

# DBCP使用步骤
1. 导入jar包
    1. commons-dbcp-#.#.jar
    2. commons-pool-#.#.#.jar
2. 使用api
    1. 硬编码：`new BasicDataSource`
        
    2. 配置文件
        1. 实现编写一个properties文件
        2. `new BasicDataSourceFactory().createDataSource(Properties properties)`
3. 例子
(1)硬编码  
```java
    public void func1()  {
        //创建连接池
        BasicDataSource bds = new BasicDataSource();

        //配置信息
        bds.setDriverClassName("com.mysql.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/day07");
        bds.setUsername("dbuser");
        bds.setPassword("secret");

        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into category values(?,?)";
        try {
            conn = bds.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "c006");
            pstmt.setString(2, "手机");
            int i = pstmt.executeUpdate();
            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
```

(2)配置文件
```java
    public void func2() throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("src/dbcp.properties"));
        //创建连接池
        DataSource ds = new BasicDataSourceFactory().createDataSource(prop);
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into category values(?,?)";
        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "c007");
            pstmt.setString(2, "电脑");
            int i = pstmt.executeUpdate();
            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

```

# C3P0
## 简介
1. hibernate和spring两个框架在使用
2. 有自动回收空闲连接的功能
## 使用步骤
1. 导入jar包
    1. c3p0-#.#.#.#.#.jar
2. 使用api
    1. 硬编码
        1. `ComboPoolDataSource ds = new ComboPooledDataSource();`
        2. `ds.setDriverClass();`        
        3. `ds.setJdbcUrl();`
        4. `ds.setUser();`
        5. `ds.setPassword();`
    2. 使用配置文件
        1. 配置文件的名称：`c3p0.properties` 或者 `c3p0-config.xml`
        2. 配置文件的路径：src下
        3. 然后使用`ComboPoolDataSource ds = new ComboPooledDataSource();`或`ComboPoolDataSource ds = new ComboPooledDataSource("named-config");`即可

### 配置文件例
1. c3p0.properties
```properties
c3p0.driverClass=com.mysql.jdbc.Driver
c3p0.jdbcUrl=jdbc:mysql://localhost/day07
c3p0.user=dbuser
c3p0.password=secret
```

2. c3p0-config.xml
```xml
<c3p0-config>
    <!--默认配置，如果没有指定则使用这个配置 -->
    <default-config>
        <!--基本配置-->
        <property name="driverClass">com.mysql.jdbc.Driver</property>
        <property name="jdbcUrl">jdbc:mysql://127.0.0.1:3306/day07</property>
        <property name="user">dbuser</property>
        <property name="password">secret</property>

        <!--扩展配置-->
        <property name="checkoutTimeout">30000</property>
        <property name="idleConnectionTestPeriod">30</property>
        <property name="initialPoolSize">10</property>
        <property name="maxIdleTime">30</property>
        <property name="maxPoolSize">100</property>
        <property name="minPoolSize">10</property>
        <property name="maxStatements">200</property>
    </default-config>

    <!--命名的配置-->
    <named-config name="devinkin">
        <property name="driverClass">com.mysql.jdbc.Driver</property>
        <property name="jdbcUrl">jdbc:mysql://127.0.0.1:3306/day07</property>
        <property name="user">dbuser</property>
        <property name="password">secret</property>

        <!--如果池中了数据连接不够时一次增长多少个-->
        <property name="acquireIncrement">5</property>
        <property name="initialPoolSize">20</property>
        <property name="maxIdleTime">10</property>
        <property name="maxPoolSize">40</property>
        <property name="minPoolSize">20</property>
        <property name="maxStatements">5</property>
    </named-config>
</c3p0-config>

```
# 增强方法
## 增强方法的方式
1. 继承
2. 装饰者模式(静态代理)
3. 动态代理

## 装饰者模式
1. 使用要求
    1. 装饰者和被装饰者要实现同一个接口或继承同一个类
    2. 装饰者中要有被装饰者的引用
    3. 对需要增强的方法进行加强
    4. 对不需要加强的方法调用原来的方法即可


# dbutils核心类和接口
## QueryRunner：(类)
1. 作用：操作sql语句
2. 构造器：`QueryRunner qr = new QueryRunner(DataSource ds);`
3. 常用方法
    1. `query();`
    2. `update();`
4. 底层实现了创建连接，创建语句执行者，释放资源的方法

## DbUtils：(类)释放资源，控制资源
1. 常用方法
    1. `closeQueitly(Connection conn)`：内部处理了异常
    2. `commitAndClose(Connection conn)`：提交事务并释放连接
   
## ResultSetHandler：封装结果集
1. 有9个实现类
    1. ArrayHandler：将查询结果的第一条件记录封装成数组，返回Object[]
    2. ArrayListHandler：将查询结果的每一条记录封装成数组，将每一个数组放入`List<Object []>`中返回
    3. BeanHandler：将查询结果的第一条记录封装成指定的JavaBean对象，然后返回JavaBean对象
    4. BeanListHandler：将查询结果的每一条记录封装成指定的JavaBean对象，然后放入`List<Object>`中返回
    5. ColumnListHandler：将查询结果指定的一列放入List中返回
    6. KevedHandler
    7. MapHandler：将查询结果的第一条记录封装成`Map<字段名,字段值>`返回
    8. MapListHandler：将查询结果的每一条记录封装成一个map集合，将每个map放入`List<Map<String,Object>>`中返回
    9. ScalarHandler：返回一个单行单列的结果

# 案例1-通过jdbc完成单表的curd操作
## 需求
1. 对分类表完成操作
## 技术分析
1. jdbc

# 案例2-通过数据库连接池优化我们的操作
## 需求
1. 使用jdbc的时候，每操作一次都需要获取连接(创建)用完之后把连接释放掉，通过连接池来优化curd操作

## 技术分析
1. 数据库连接池

# 案例3-通过dbutils完成curd操作
## 技术分析
1. dbutils

## dbutils
1. dbutils是Apache组织的一个工具类，jdbc的框架，更方便我们使用
2. 使用步骤
    1. 导入jar包(commons-dbutils-1.4.jar)
    2. 创建一个queryrunner类`QueryRunner qr = new QueryRunner(DataSource ds);`
        1. queryrunner作用：操作sql语句
    3. 编写sql
    4. 执行sql，用queryrunner的方法执行
        1. update() 
        2. query()
