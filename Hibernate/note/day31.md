# 案例一：使用Hibernate完成查询所有联系人功能

## 需求分析
1. 完成所有的联系人的查询

## 技术分析之Hibernate框架的查询方式
1. 唯一标识OID的检索方式
    - `session.get(对象.class,OID)`
    
2. 对象的导航方式

3. HQL的检索方式
    - Hibernate Query Language：Hibernate的查询语言
   
4. QBC的检索方式
    - Query By Criteria：条件查询
    
5. SQL检索方式(了解)
    - 本地的SQL检索
    
    
    
## 技术分析之HQL的查询方式概述
1. HQL的介绍 
    - HQL(Hibernate Query Language)是面向对象的查询语言，它和SQL查询语言有些相似
    - 在HIbernate提供的各种检索方式中，HQL是使用最广的一种检索方式
    
2. HQL与SQL的关系
    - HQL查询语句是面向对象的。Hibernate负责解析HQL查询语句，然后根据对象-关系映射文件中的映射信息，把HQL查询语句翻译成相应的SQL语句
    - HQL查询语句中的主体是域模型中的类及类的属性
    - SQL查询语句是与关系数据库绑定在一起的，SQL查询语句中的主体是数据库表及表的字段
    
    
    
## 技术分析之HQL的查询演示
### HQL基本的查询格式
1. 支持方法链的编程，即直接调用list()方法
2. 简单的代码如下：`session.createQuery("from Customer).list();`

### 使用别名的方式
1. 可以使用别名的方式
    - `session.createQuery("from Customer c").list();`
    - `session.createQuery("select c from Customer c").list()`
    
### 排序查询
1. 排序查询和SQL语句中的排序的语法是一样的
    - 升序：`session.createQuery("from Customer order by cust_id).list();`
    - 降序：`session.createQuery("from Customer order by cust_id desc).list();`
    
### 分页查询
1. Hibernate框架提供了分页的方法
2， 两个方法如下：
    - `setFIrstResult(a)`：从哪条记录开始，如果查询是从第一条开启，值是0
    - `setMaxResult(b)`：每页查询的记录条数
3. 演示代码如下：
    - `List<LinkMan> list = session.createQuery("from LinkMan).setFirstResult(0).setMaxResults().list()`
    
### 带条件的查询
1. `setParameter("?号的位置，默认从0开始", "参数的值");`：不用考虑参数的具体类型
2. 按位置绑定参数的条件查询(指定下标值，默认从0开始)
3. 按名称绑定参数的条件查询(HQL语句中的？号换成：名称的方式)
4. 示例代码如下：
```java
Query query = session.createQuery("from Linkman where lkm_name like ? order by lkm_id desc");
query.setFirstResult(0).setMaxResults(3);
query.setParameter(0,"%熊%");
List<Linkman> list = query.list();
for (Linkman linkman : list) {
    System.out.println(linkman);
}
```

## 技术分析之HQL的投影查询
1. 投影查询就是查询某一字段的值或者某几个字段的值
2. 如果查询多个字段，例如下面这种方式
```java
List<Object[]> list = session.createQuery("select c.cust_name,c.cust_level, from Customer c").list();
for (Object[] objects : list ) {
    System.out.println(Arrays.toString(objects));
}
```
3. 如果查询两个字段，也可以把两个字段封装到对象中，先在持久化类中提供对应字段的构造方法
```java
List<Customer> list = session.createQuery("select new Customer(c.cust_name, c.cust_level) from Customer c").list();
for (Customer customer : list) {
    System.out.println(customer);
}
```

## 技术分析之聚合函数查询
- 获取总的记录数
```java
Session session = HIbernateUtils.getCurrentSession();
Transaction tr = session.beginTransaction();
List<Number> list = session.createQuery("select count(c) from Customer c").list();
Long count = list.get(0).longValue();
System.out.println(count);
tr.commit();
```

- 获取某一列数据的和
```java
Session session = HIbernateUtils.getCurrentSession();
Transaction tr = session.beginTransaction();
List<Number> list = session.createQuery("select sum(c.cust_id) from Customer c").list();
Long count = list.get(0).longValue();
System.out.println(count);
tr.commit();
```

## 技术分析之QBC检索方式
1. QBC：Query By Criteria，按条件进行查询

### 简单查询
1. 简单查询，使用的是Criteria接口
```java
List<Customer> list = session.createCriteria(Customer.class).list();
for (Customer customer : list) {
    System.out.println(customer);
}
```

### 排序查询
1. 需要使用addOrder()方法来设置参数，参数使用`org.hibernate.criterion.Order`对象
2. 具体代码如下
```java
Session session = HibernateUtils.getCurrentSession();
Transaction tr = session.beginTransaction();
Criteria criteria = session.createCriteria(Linkman.class);
// 设置排序
criteria.addOrder(Order.desc("lkm_id"));
List<Linkman> list = criteria.list();
for (Linkman linkman : list) {
    System.out.println(linkman);
}
tr.commit();
```

### 分页查询
1. QBC的分页查询也是使用两个方法
    - `setFirstResult();`
    - `setMaxResults();`
    
2. 示例代码如下
```java
Session session = HibernateUtils.getCurrentSession();
Transaction tr = session.beginTransaction();
Criteria criteria = session.createCriteria(Linkman.class);
// 设置排序
criteria.addOrder(Order.desc("lkm_id"));
criteria.setFirstResult(0);
criteria.setMaxResults(3);
List<Linkman> list = criteria.list();
for (Linkman linkman : list) {
    System.out.println(linkman);
}
tr.commit();
```

### 条件查询
1. 条件查询使用Criteria接口的add方法，用来传入条件
2. 使用Restrictions的添加条件方法来添加条件
#### Restrictions常用条件方法
|方法|条件|
|---|---|
|Restrictions.eq|相等|
|Restrictions.gt|大于|
|Restrictions.ge|大于等于|
|Restrictions.lt|小于|
|Restrictions.le|小于等于|
|Restrictions.between|在...之间|
|Restrictions.like|模糊查询|
|Restrictions.in|范围|
|Restrictions.and|并且|
|Restrictions.or|或者|
|Restrictions.isNull|该字段的属性是否为空|
|Restrictions.isnotNull|该字段的属性是否不为空|

- 测试代码如下
```java
Session session = HibernateUtils.getCurrentSession();
Transaction tr = session.beginTransaction();
Criteria criteria = session.createCriteria(Linkman.class);
// 设置排序
criteria.addOrder(Order.desc("lkm_id"));
// 设置查询条件
criteria.add(Restrictions.or(Restrictions.eq("lkm_gender", "男"), Restrictions.gt("lkm_id", 3L)));
List<Linkman> list = criteria.list();
for (Linkman linkman : list) {
    System.out.println(linkman);
}
tr.commit();
```

### 聚合查询查询
1. Projection的聚合函数的接口，而Projections是Hibernate提供的工具类，使用该工具类设置聚合函数查询
2. 具体代码如下
```java
Session session = HibernateUtils.getCurrentSession();
Transaction tr = session.beginTransaction();
Criteria criteria = session.createCriteria(Linkman.class);
criteria.setProjection(Projections.rowCount());
//不需要的时候设置为null
criteria.setProjection(null);
List<Number> list = criteria.list();
Long count = list.get(0).longValue();
System.out.println(count);
tr.commit();
```

## 技术分析之离线条件查询
1. 离线条件查询使用的是`DetachedCriteria`接口进行查询，离线条件查询对象在创建的时候，不需要使用Session对象，只是在查询的时候使用Session对象即可。
2. 创建离线条件查询对象：`DetachedCriteria criteria = DetachedCriteria.forClass(Linkman.class);`
3. 具体代码如下
```java
Session session = HibernateUtils.getCurrentSession();
Transaction tr = session.beginTransaction();
		
DetachedCriteria criteria = DetachedCriteria.forClass(Linkman.class);
// 设置查询条件
criteria.add(Restrictions.eq("lkm_gender", "男"));
// 查询数据
List<Linkman> list = criteria.getExecutableCriteria(session).list();
for (Linkman linkman : list) {
    System.out.println(linkman);
}
tr.commit();
```

## 技术分析之SQL查询方式(了解)
1. 基本语法
```java
Session session = HibernateUtils.getCurrentSession();
Transaction tr = session.beginTransaction();
		
SQLQuery sqlQuery = session.createSQLQuery("select * from cst_linkman where lkm_gender = ?");
sqlQuery.setParameter(0,"男");
sqlQuery.addEntity(Linkman.class);
List<Linkman> list = sqlQuery.list();
System.out.println(list);
tr.commit();
```

## 技术分析之HQL多表查询

### 多表查询使用HQL语句来查询

#### 内连接查询
1. 显式内连接查询：`select * from customers c inner join orders o on c.cid = o.cno;`
2. 隐式内连接：`select * from customers c, orders o where c.cid = o.cno;`

#### 外连接查询
1. 左外连接：`select * from customers c left join orders o on c.cid = o.cno;`
2. 右外连接：`select * from customers c right join orders o on c.cid = o.cno`

### HQL的多表查询
1. 迫切和非迫切
    1. 非迫切返回的结果是Object[]
    2. 迫切连接返回的结果是对象，把客户的信息封装到客户的对象中，把订单的信息封装到客户的Set集合中
    
### 内连接查询
1. 内连接使用`inner join`，默认返回的是Object数组
```java
Session session = HibernateUtils.getCurrentSession();
Transaction tr = session.beginTransaction();
List<Object[]> list = session.createQuery("from Customer c inner join c.linkmans").list();
for (Object[] objects : list) {
    System.out.println(Arrays.toString(objects));
}
tr.commit();
```

2. 迫切内连接：`inner join fetch`，返回的是实体对象
```java
Session session = HibernateUtils.getCurrentSession();
Transaction tr = session.beginTransaction();
List<Customer> list = session.createQuery("from Customer c inner join fetch c.linkmans").list();
Set<Customer> set = new HashSet<Customer>(list);
for (Customer customer : set) {
    System.out.println(customer);
}
tr.commit();
```

### 左外连接查询
1. 左外连接：封装成`List<Object[]>`
2. 迫切左外连接
```java
Session session = HibernateUtils.getCurrentSession();
Transaction tr = session.beginTransaction();
List<Customer> list = session.createQuery("from Customer c left join fetch c.linkmans").list();
Set<Customer> set = new HashSet<Customer>(list);
for (Customer customer : set) {
    System.out.println(customer);
}
tr.commit();
```

# 案例二：对查询功能的优化

## 需求分析
1. 对Hibernate框架的查询进行优化

## 技术分析之延迟加载
1. 延迟加载先获取到代理对象，当真正使用到该对象中的属性的时候，才会发送SQL语句，是Hibernate框架提升性能的方式
2. 类级别的延迟加载
    - Session对象的load方法默认就是延迟加载
    - `Customer c = session.load(Customer.class, 1L)`：没有发送SQL语句，当使用该对象的属性时，才发送SQL语句
    - 使用类级别的延迟加载失效
        - 在`<class>`标签上配置`lazy="false`
        - `Hibernate.initialize(Object proxy);`
3. 关联级别的延迟加载(查询某个客户，当查看该客户下的所有联系人是否为延迟加载)，默认是延迟加载
```java
Session session = HibernateUtils.getCurrentSession();
Transaction tr = session.beginTransaction();
Customer c = session.get(Customer.class, 1L);
System.out.println("=============");
System.out.println(c.getLinkmans().size());
tr.commit();
```

## 技术分析之Hibernate框架的查询策略
1. 查询策略：使用Hibernate查询一个对象的时候，查询其关联对象，应如何查询，是Hibernate的一种优化手段
2. Hibernate框架的检索策略解决的问题
    - 查询的时机
        - `Customer c1 = (Customer) session.get(Customer.class, 1)`
        - `System.out.println(c1.getLinkmans().size())`
        - lazy属性解决查询的时机的问题，需要配置是否采用延时加载！！！
        
    - 查询的语句形式
        ```
        List<Customer> list = session.createQuery("from Customer).list()
        for (Customer c : list) {
            System.out.println(c.getLinkmans());
        }
        ```
        - fetch属性就可以解决查询语句的形式的问题

## 技术分析之在set标签上配置策略
1. 在`<set>`标签上使用fetch和lazy属性
2. set标签上的默认值是`fetch="select`和`lazy="true"`
3. fetch属性用于控制SQL语句生成的格式
4. lazy属性用于查找关联对象的时候是否采用延迟
### fetch的取值
|fetch取值|含义|
|---|---|
|select|默认值，发送查询语句|
|join|连接查询，发送的是一条迫切左外连接。配置了join，lazy属性就失效了|
|subselect|子查询，发送一条子查询查询其关联对象。(需要使用list()方法进行测试)|

### lazy的取值
|fetch取值|含义|
|---|---|
|true|默认，延迟|
|false|不延迟|
|extra|极其懒惰|

## 技术分析之在many-to-one标签上配置策略
1. 在`<many-to-one>`标签上使用fetch和lazy属性
2. 在`<many-to-one>`标签上的默认值是`fetch="select"`和proxy

### fetch的取值
|fetch取值|含义|
|---|---|
|select|默认值，发送基本select语句查询|
|join|发送迫切左外连接查询|

### lazy的取值
|fetch取值|含义|
|---|---|
|false|不采用延迟|
|proxy|默认值，dialing，现在是否采用延迟|

1. 由另一端的`<class>`上的lazy确定，如果这端的class上的`lazy="true"`，proxy的值就是true(延迟加载)
2. 如果class上`lazy="false"`，proxy的值就是false(不采用延迟)