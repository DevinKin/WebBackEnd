# mvc思想
1. servlet缺点：生成html内容太麻烦
2. jsp缺点：阅读起来不方便，维护比较困难

## jsp的model1
1. jsp+javabean
2. jsp：接受请求，展示数据
3. javabean：和数据打交道

## jsp的model2
1. jsp+javabean+servlet
2. jsp：展示数据(v)
3. javabean：和数据打交道(m)
4. servlet：接受请求，处理业务逻辑(c)

## MVC模式
1. 将业务逻辑，代码，显示相分离的一种思想
2. M：model，模型，作用：主要是封装数据，封装对数据的访问
3. V：view，视图，作用：主要是用来展示数据
4. C：controler，控制器，作用：接受请求，找到响应的javabean完成业务逻辑

# 分层：javaee的三层架构
## web层
### 作用
1. 展示数据---jsp
2. 接受请求---servlet
3. 找到对应的service，调用方法，完成逻辑操作---servlet
4. 信息生成或者页面跳转---servlet
## service层(业务层)
### 作用
1. 完成业务操作
2. 调用dao
## dao(data access object)数据访问对象层
### 作用
1. 对数据库的curd操作

# jsp设计模式1(model1)
1. javabean+jsp

## javabean在model1使用
1. `<jsp:useBean id='' property=''>`
2. `<jsp:setProperty name='' property=''>`
3. `<jsp:getProperty name="" property=""/>`


# jsp设计模式2(model2)
1. jsp+servlet+javabean

## javabean在model2中使用
1. BeanUtils：拥有封装数据功能的一个工具类
    1. 使用步骤
        1. 导入jar包
        2. 使用BeanUtils.populate(Object bean, Map map);

# 事务
1. 就是一件完整的事情，包含多个操作单元，这些操作要么全部成功，要么全部失败。

## 事务的四大特性(ACID)
### 原子性(Atomicity)
1. 原子性是指事务包含的所有操作要么全部成功，要么全部失败回滚，因此事务的操作如果成功就必须要完全应用到数据库，如果操作失败则不能对数据库有任何影响。
### 一致性(Consistency)
1. 一致性是指事务必须使数据库从一个一致性状态变换到另一个一致性状态，也就是说一个事务执行之前和执行之后都必须处于一致性状态。
### 隔离性(Isolation)
1. 隔离性是当多个用户并发访问数据库时，比如操作同一张表时，数据库为每一个用户开启的事务，不能被其他事务的操作所干扰，多个并发事务之间要相互隔离。
### 持久性(Durability)
1. 持久性是指一个事务一旦被提交了，那么对数据库中的数据的改变就是永久性的，即便是在数据库系统遇到故障的情况下也不会丢失提交事务的操作。

## 不考虑隔离性会出现的读问题
1. 脏读：在一个事务中读取到另一个事务没有提交的数据
2. 不可重复读：在一个事务中，两次查询的结果不一致(针对update操作)
3. 虚读(幻读)：在一个事务中，两次查询的结果不一致(针对的insert操作)
### 通过数据库的隔离级别来避免上面的问题
|隔离级别|说明|避免的问题|
|---|---|---|
|read uncommited|读未提交|不能避免上面所有问题|
|read commited|读已提交|避免脏读的发生|
|repeatable read|可重复读|避免脏读和不可重复读的发生|
|serializable|串行化|避免所有问题，但出现锁表现象|

### mysql设置隔离级别的操作
1. 将数据库的隔离级别设置成读未提交：`set session transaction isolation level read uncommitted;`
2. 将数据库的隔离级别设置成读已提交：`set session transaction isolation level read committed;`
3. 将数据库的隔离级别设置成可重复读：`set session transaction isolation level repeatable read;`
3. 将数据库的隔离级别设置成串行化：`set session transaction isolation level serializable;`
4. 查看数据库的隔离级别：`select @@tx_isolation;`

### 四种隔离级别的效率
1. `read uncommitted > read committed > repeatable read > serializable `

### 四种隔离级别的安全性
1. `read uncommitted < read committed < repeatable read < serializable `

### 各大数据库的默认隔离级别
1. mysql中默认级别：`repeatable read`
2. oracle中默认级别：`read committed`

### java中控制隔离级别
1. Connection#void setTransactionIsolation(int level)
    1. TRANSACTION_NONE
    2. TRANSACTION_READ_COMMITTED
    3. TRANSACTION_READ_UNCOMMITTED
    4. TRANSACTION_REPEATABLE_READ
    5. TRANSACTION_SERIALIZABLE


## mysql中的事务
1. mysql中事务默认是自动提交的，一条sql语句就是一个事务
2. oracle中事务默认是手动
### mysql手动开启事务的方式
1. 关闭自动事务：`SET AUTOCOMMIT = 0;`

2. 手动开启一个事务：`START TRANSACTION;`
    1. 事务提交：`commit;` 
    2. 事务回滚：`rollback;`
## java中的事务
1. 设置还原点
    1. `Savepoint setSavepoint()`
2. 还原到事务的某个点
    1. Connection#void rollback(Savepoint savepoint)
    2. 回滚到某一个还原点

### java手动开启事务
1. Connection接口的API：
    1. 手动开启事务：`setAutoCommit(false);`
    2. 事务提交：`commit()`
    3. 事务回滚：`rollback()`


# 案例1-使用mvc思想完成转账操作
## 需求
1. 在一个页面上有汇款人，收款人，转账金额，一旦转账之后，汇款人金额减少，收款人金额增多，使用事务来控制起来

## 步骤分析
1. 数据库和表
2. 导入jar包和工具类
    1. 驱动
    2. jdbcUtils
    3. c3p0及其配置文件和工具类
    4. dbutils
3. 新建一个account.jsp，里面包含表单
4. AccountServlet
    1. 接收3个参数，
    2. 调用AccountService#account()完成转账操作
    3. 打印信息
5. acount方法
    1. 使用jdbc，不考虑事务
    2. 调用dao完成转出操作
    3. 调用dao完成转入操作

## 技术分析
1. mvc思想
2. 事务
