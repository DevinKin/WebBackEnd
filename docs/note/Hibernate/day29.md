# Hibernate持久化对象的状态
## 什么是持久化类
1. 持久化类：就是一个Java类，这个Java类与表建立了映射关系就可以称为是持久化类。
2. 持久化类 = JavaBean + xxx.hbm.xml

## 持久化类的编写规则
1. 提供一个无参， public访问控制符的构造器(底层需要反射)
2. 提供一个标识属性，映射数据表主键字段(唯一标识OID，数据库中通过主键，Java对象通过地址确定对象，持久化类通过唯一标识OID确定记录)
3. 所有属性提供public访问控制符的`set`或者`get`方法
4. 标识属性应尽量使用基本数据类型的包装类型

## 区分自然主键和代理主键
1. 创建表的时候
    - 自然主键： 对象本身是一个属性，创建一个人员表，每个人都有一个身份证号，(唯一的)使用身份证号作为表的主键，称为自然主键(开发中不会使用这种方式)
    - 代理主键：不是对象本身的一个属性，创建一个人员表，为每个人员单独创建一个字段，用这个字段作为主键，称为代理主键。(开发中推荐使用这种方式)
2. 创建表的时候尽量使用代理主键创建表

## 主键生成策略
1. `increment`：适用于short、int、long作为主键，不是使用的数据库自动增长机制
    - Hibernate中提供的一种增长机制
        - 先进行查询：`select max(id) from user;`
        - 在进行插入：获得最大值+1作为新的记录的主键
    - 问题：不能在集群环境下或者有并发访问的情况下使用

2. identity：适用于short、int、long作为主键，但是这个必须使用在有自动增长数据库中，采用的是数据库底层的自动增长机制。
    - 底层使用的是数据库的自动增长(auto_increment)，像Oracle数据库没有自动增长。
    
3. sequence：适用于short、int、long作为主键，底层使用的是序列的增长方式
    - Oracle数据库底层没有自动增长，想自动增长需要使用序列。
    
4. uuid：适用于char、varchar类型的作为主键
    - 使用随机的字符串作为主键
    
5. native：本地策略。根据底层的数据库不同，自动选择适用于该种数据库的生成策略(short、int、long)
    - 如果底层使用MySQL数据库，相当于identity
    - 如果底层使用Oracle数据库，相当于sequence
    
6. assigned：主键的生成不用Hibernate管理了，必须手动设置主键。

## 持久化对象的状态 
###Hibernate的持久化类
1. 持久化类：Java类与数据库的某个表建立了映射关系。这个类就成为持久化类
2. 持久化类 = Java类 + hbm的配置文件

###Hibernate的持久化类的状态
1. Hibernate为了管理持久化类：将持久化类分成了三个状态
    1. 瞬时态：Transient Object
        - 没有持久化标志OID，没有被纳入到Session对象的管理
    2. 持久态：Persistent Object
        - 持久化标识OID，已经被纳入到Session对象的管理
    3. 脱管态：Detached Object
        - 有持久化标志OID，没有被纳入到Session对象的管理

## Hibernate持久化对象的状态的转换
1. 注意：持久态对象有自动更新数据库的能力

### 瞬时态
1. 没有持久化标志OID，没有被纳入到Session对象的管理

#### 获得顺势太的对象
1. `User user = new User()`

#### 瞬时态对象转换为持久态
1. `save() / saveOrUPdate()`

#### 顺势太对象转换为脱管态
1. `user.setId(1)`


### 持久态
1. 有持久化标志OID，已经被纳入到Session对象的管理
#### 获取持久态的对象
1. `get()/load()`

#### 持久态转换为瞬时态对象
1. `delete();`：比较有争议的，进入特殊的状态(删除态：Hibernate不建议使用的)

#### 持久态对象转换为脱管态对象
1. session的`close()/evict()/clear();`

### 脱管态
1. 有持久化标识OID，没有被纳入到Session对象的管理

#### 获得脱管态对象
1. 不建议直接获得脱管态的对象
```java
User user = new User();
user.setId(1);
```

#### 脱管态对象转换为持久态对象
1. `update();saveOrUpdate()/lock();`

#### 脱管态对象转换成瞬时态对象
1. `user.setId(null)`

![](../note/01-三个状态转换.bmp)

# Hibernate的一级缓存
## 什么是缓存
1. 其实就是一块内存空间，将数据源(数据库或者文件)中的数据存放到缓存中。再次获取的时候，直接从换从中获取。可提升程序的性能

## Hibernate框架提供了两种缓存
1. 一级缓存：自带的不可卸载的，一级缓存的生命周期与session一致，一级缓存称为session级别的缓存
2. 二级缓存：默认没有开启，需要手动配置才可以使用的，二级缓存可以在多个session中共享数据，二级缓存称为是SessionFactory级别的缓存。

## Session对象的缓存概述
1. Session接口中，有一系列的java集合，这些java集合构成了Session级别的缓存(一级缓存)。将对象存入到一级缓存中，session没有结束声明周期，那么对象在session中存放着。
2. 内存中包含着Session实例，Session的缓存(一些集合)，集合中包含的就是缓存对象！

## 证明一级缓存的存在
1. 在同一个Session对象中两次查询，可以证明使用了缓存
```java
    @Test
    public void run2() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        // 获取上一个持久对象
        User user = new User();
        user.setName("哈哈");
        user.setAge(20);

        //保存用户，user已经存入到session的缓存中
        Serializable id = session.save(user);
        System.out.println(id);

        //获取对象，不会看到SQL语句，说明从缓存中取出User对象
        User user2 = session.get(User.class, id);
        System.out.println(user2.getName());

        tr.commit();
        session.close();
    }
```

## Hibernate框架如何做到数据发生变化时进行同步操作呢？
1. 使用get方法查询User对象
2. 然后设置User对象的一个属性，注意：没有update操作。发现，数据库中的记录也改变了。
3. 利用快照机制来完成的(SnapShot)

![](../note/02-Session的快照机制.bmp)

## 控制Session的一级缓存(了解)
1. 学习Session接口中的一级缓存相关的方法
    1. `Session.clear()`：清空缓存
    2. `Session.evict(Object entity)`：从一级缓存中清除指定的实体对象
    3. `Session.flush()`：刷除缓存
    
    
# 事务相关的概念
## 什么是事务
1. 事务就是逻辑上的一组操作，组成事务的各个执行单元，操作要么全部成功，要么全部失败。

## 事务的特性
1. 原子性：事务不可分割
2. 一致性：事务执行的前后数据的完整性保持一致。
3. 隔离性：一个事务执行的过程中，不应该收到其他的事务的干扰
4. 持久性：事务一旦提交，数据就永久保持到数据库中。

## 不考虑隔离性可能引发的读问题
1. 脏读：一个事务读到了另一个事务未提交的数据
2. 不可重复读：一个事务读到了另一个事务已经提交的update数据，导致多次查询结果不一致
3. 虚读：一个事务读到了另一个事务已经提交的insert数据，导致多次查询结果不一致

## 通过设置数据库的隔离级别来解决上述读的问题
1. 未提交读：以上的读问题都有可能发生
2. 已提交读：避免脏读，但是不可重复读，虚读都有可能发生
3. 可重复读：避免脏读，不可重复读，但是虚读还是有可能发生的
4. 串行化：以上读的情况都可以避免。

## Hibernate框架中设置隔离级别
1. 如果向在Hibernate的框架中来设置隔离级别，需要在`hibernate.cfg.xml`的配置文件中通过标签来配置
    1. 通过：`hibernate.connection.isolation = 4`来配置
2. 取值如下
    - 1-Read uncommitted isolation
    - 2-Read committed isolation
    - 4-Repeatable read isolation
    - 8-Serializable isolation
    
## 丢失更新的问题
1. 如果不考虑隔离性，也会产生写入数据的问题，这一类问题叫丢失更新的问题
2. 例如：两个事务同事对某一条记录做修改，就会引发丢失更新的问题
    - A事务和B事务同事获取一条记录，同时再做修改
    - 如果A事务修改完成后，提交了事务
    - B事务修改完成后，不管是提交还是回滚，如果不做处理，都会对数据产生影响

### 解决方案
1. 悲观锁
2. 乐观锁
#### 悲观锁
1. 采用的是数据库提供的一种锁机制，如果采用做了这种机制，在SQL语句的后面添加`for update`子句
    - 当A事务在操作该条记录时，会把该条记录锁起来，其他事务是不能操作这条记录的
    - 只有当A事务提交后，锁释放了，其他事务才能操作这条记录
#### 乐观锁
1. 采用版本号的机制来解决。会给表结构添加一个字段`version=0`，默认值是0
    - 当A事务在操作完该条记录，提交事务时，会先检查版本号，如果发生版本号的值相同时，才可以提交事务。同时版本号`version=1`
    - 当B事务操作完该条记录时，提交事务时，会先检查版本号，如果发现版本号不同时，程序会发生错误。
    
### 使用Hibernate框架解决丢失更新的问题
1. 悲观锁：使用`session.get(Customer.class,1,LockMode.UPGRADE);`方法
2. 乐观锁
    1. 在对应的JavaBean中添加一个属性，名称可以是任意的。例如：`private Integer version;`，并提供get和set方法
    2. 在映射的配置文件中，提供`<version name="version"/>`标签即可
    
![](../note/03-丢失更新.bmp)

# 绑定本地的Session
1. 在Hibernate框架中，使用session对象开启事务，所以需要来传递session对象，框架提供了ThreadLocal的方式
    - 需要在`hibernate.cfg.xml`的配置文件中提供配置
        - `<property name="hibernate.current_session_context_class">thread</property>`
        
    - 重新编写HibernateUtils工具类，使用SessionFactory类的getCurrentSession()方法，获取当前的Session对象。并且该Session对象不用手动关闭，线程结束了，会自动关闭。
    ```java
    public static Session getCurrentSession() {  
       return factory.getCurrentSession();
    }
    ``` 
2. 注意：想使用getCurrentSession()方法，必须先要配置才能使用

# Hibernate框架的基本查询

## Query查询接口(HQL的查询)
1. 具体的查询代码如下：
```java
    @Test
    public void run2() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        // 查询的方式 HQL语句 from User where 属性条件
        // SQL：select * from t_user where 字段 条件
        Query query = session.createQuery("from User where age > ?");
        //设置值
        query.setInteger(0, 39);

        List<User> list = query.list();
        for (User user : list) {
            System.out.println(user);
        }

        tr.commit();
        session.close();
    }
    
    @Test
    public void run3() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        // 查询的方式 HQL语句 from User where 属性条件
        // SQL：select * from t_user where 字段 条件
        Query query = session.createQuery("from User where name like ?");
        //设置值
        query.setString(0, "%小%");

        List<User> list = query.list();
        for (User user : list) {
            System.out.println(user);
        }
        tr.commit();
        session.close();
    }
    
    @Test
    public void run4() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        // 查询的方式 HQL语句 from User where 属性条件
        // SQL：select * from t_user where 字段 条件
        Query query = session.createQuery("from User where age > :aaa");
        //设置值
        query.setInteger("aaa", 18);

        List<User> list = query.list();
        for (User user : list) {
            System.out.println(user);
        }
        tr.commit();
        session.close();
    }
```

## Criteria查询接口(QBC查询，做条件查询非常合适)
1. 完全面向对象查询
2. 具体代码如下：
```java
    /**
     * 按条件查询，写法很麻烦
     */
    @Test
    public void run6() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        // 先获取Criteria接口
        Criteria criteria = session.createCriteria(User.class);
        // 添加查询的条件 select * from t_user where age > 18
        // Criterion 是 Hibernate提供条件查询的对象，想传入条件的使用的工具类

        // Restrictions提供静态方法，拼接查询的条件
        criteria.add(Restrictions.gt("age", 20));
        criteria.add(Restrictions.like("name", "%小%"));

        List<User> list = criteria.list();
        System.out.println(list);
        tr.commit();
        session.close();
    }


    /**
     * Criteria接口：非常适合条件查询
     */
    @Test
    public void run5() {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        // 先获取Criteria接口
        Criteria criteria = session.createCriteria(User.class);
        // 没有添加条件，查询所有的数据
        List<User> list = criteria.list();
        System.out.println(list);

        tr.commit();
        session.close();
    }

```