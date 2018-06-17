# 数据库类型
## 关系型数据库
1. 存放实体与实体之间的关系的数据库

### 常见关系型数据库
1. mysql oracle 开源的数据库
2. oracle oracle 大型的收费数据库
3. DB2   IBM    大型的收费数据库
4. sqlserver 微软 中大型的收费数据库
5. sybase sybase 

## 非关系型数据库
1. 存放的是对象(redis)

2. No-sql(not only sql)

# SQL:结构化查询语句
1. 作用：管理数据库
## sql的分类
### DDL：数据定义语言(Data Definition Language)
1. 操作对象：数据库和表
2. 创建：create
    1. 创建数据库:`create database databaseName`
    2. 创建表:`create table 表名 (字段描述,字段描述);`
        1. 字段描述： 字段名称 字段类型 [约束]
3. 修改：alter
    1. 修改表:`alter table 表名 ...`
        1. 修改表名:`alter table 旧表名 rename to 新表名`
        2. 添加字段:`alter tale 表名 add 字段描述`
        3. 修改字段名:`alter table 表名 change 字段名称 新字段描述`
        4. 修改字段描述:`alter table user modify 字段名称 字段类型 [约束]`
        5. 删除字段: `alter table 表名 drop 字段名`
4. 删除：drop
    1. 删除数据库:`drop database databaseName`
    2. 删除表:`drop table 表名`
### DML：数据操作语言(Data Management Language)
1. 操作对象：记录(行)
2. 插入:
    1. 插入所有字段:`insert into 表名 values(字段值1,字段值2,...)` 
    2. 插入指定字段:`insert into 表名(字段1,字段2) values(字段值1,字段值2...)`
3. 修改
    1. `update 表名 set 字段名=字段值, 字段名1=字段值1... [where 条件]`
4. 删除
    1. `delete from 表名 [where 条件]`
### DQL：数据查询语言(Data Query Language)
1. 查询
    1. `select [distinct 字段名] 字段名1,字段名2 from 表名 [where 条件] [group by 分组字段] [having 条件] [order by 排序字段(asc|desc)] `
    2. 执行顺序
        1. 确定数据来自哪张表：from
        2. 是否需要筛选：where
        3. 是否需要分组：group by
        4. 分组后是否需要筛选：having
        5. 是否需要排序：order by
        6. 确定显示哪些数据：select
    3. distinct关键字去重复
2. 给列起别名:`字段名 as 别名`
3. 模糊匹配
    1. 格式：字段名 like "匹配规则"
    2. 匹配内容： %，表示匹配任意字符任意次
    3. 匹配个数： _，一个下划线表示一个字符
4. in在某个范围内获取值
5. between 较小值 and 较大值，在什么范围内

6. 聚合函数，对一列进行计算，返回值是一个，忽略掉null值
    1. sum()
    2. avg()
    3. max()
    4. min()
    5. count()
7. 四舍五入函数:round(值,保留小数位)
8.WHERE和HAVING的区别
    1. where是对分组前的数据进行过滤
    2. having是对分组后的数据进行过滤
    3. where后面不能使用聚合函数
    4. having后面可以使用聚合函数
### DCL：数据控制语言(Data Control Language)
1. 操作对象：用户 事务 权限


# 数据类型
1.Java和MySQL数据类型对比

Java|MySQL|
----|-----|
byte|tinyint|
short|smallint|
int|int|
long|bigint|
char，String|varchar,char|
boolean|tinyint,int|
float,double|float,double(长度,小数长度)|
java.sql.Date|date|
java.sql.Time|time|
java.sql.Timestamp|timestamp|
java.sql.Clob(长文本)|text|
java.sql.Blob(二进制)|blob|

# 约束
1. 作用：为了保证数据的有效性和完整性
## mysql中常用的约束
### 主键约束(primary key)
1. 被修饰过的字段唯一非空
2. 一张表只能有一个主键，这个主键可以包含多个字段
3. 主键约束的使用方法
    1. 创建表同时添加约束，格式： `字段名称 字段类型 primary key`
    2. 创建表的同时在约束区域添加约束，格式： `primary key(字段1, 字段2...)`
    3. 创建表之后，通过修改表结构添加约束，格式：`alter table 表名 add primary key(字段1,字段2...)`
4. 方式2和方式3添加联合唯一
5. 联合主键
    1. 主键拥有多个字段的时候组成联合主键
    2. 插入数据时候，需要主键包含的多个字段值相同才会报主键唯一的错误
### 唯一约束(unique)
1. 被修饰过的字段唯一，对null不起作用
2. 唯一约束的使用方法
    1. 创建表同时添加约束，格式： `字段名称 字段类型 unique`
    2. 创建表的同时在约束区域添加约束，格式： `unique(字段1, 字段2...)`
    3. 创建表之后，通过修改表结构添加约束，格式：`alter table 表名 add unique(字段1,字段2...)`
3. 方式2和方式3添加联合唯一
4. unique对null值不起作用
 
### 非空约束(not null)
1. 特点：被修饰过的字段非空
2. 方式：在创建表时添加非空约束，格式：`字段名称 字段类型 NOT NULL`
### 外键约束(foreign key)
1. 为了保证数据的有效性和完整性，添加约束(外键约束)
2. 在多表的一方添加外键约束，主表为一对多的一放，多表是多方的表
3. 格式1：`alter table 多表名称 add foreign key(外键字段) references 主表名称(主键)`
4. 格式2：`alter table 多表名称 add [constraint [外键名称]] foreign key(外键字段) references 主表名称(主键);`
5. 添加了外键约束之后有如下特点
    1. 主表中不能删除从表中已引用的数据
    2. 从表中不能添加主表中不存在的数据
6. 开发中处理一对多
    1. 在多表中添加一个外键，名称为主表的名称_id，字段类型和主表的主键类型保持一直
    2. 为了保证数据的有效性和完整性，在多表的外键上添加外键约束即可
   

# 其他MySQL语句
## truncate 清空表
1. 格式：`truncate 表名`
2. 作用：清除表，重新创建一张空表
3. delete属于DML语句，属于DDL语句

## auto_increment 自增
1. 被修饰的字段类型支持自增，一般是int
2. 被修饰的字段必须是一个key，一般是primary key

## 给表其别名
1. 格式：`表 [as] 别名`


# 多表查询
## 内连接
1. 格式1：显式的内连接
```sql
SELECT a.*,b.* FROM a [INNER ] JOIN  b ON ab的条件;
```
2. 格式2：隐式的内连接
```sql
SELECT a.*,b.* FROM a,b WHERE ab的连接条件
```

## 外连接
### 左外连接
1. 意义：先展示左表(a)的所有数据，根据条件关联查询join右边的表(b)，符合条件则展示，不符合以null值展示。
```sql
SELECT a.*,b.* FROM a LEFT [OUTER ] JOIN b ON 连接条件;
```
### 右外链接
1. 意义：先展示join右表(a)的所有数据，根据条件关联查询join左边的表(b)的数据，符合条件则展示，不符合以null值展示。
```sql
SELECT a.*,b.* FROM b RIGHT [OUTER ] JOIN a ON 连接条件;
```

# 子查询
1. 一个查询以来另一个查询
2. 是将一个查询的结果作为一张临时表

# 练习
## 简单查询
1. 查询所有的商品
```sql
select * from products;
```

2. 查询商品名和商品价格
```sql
select pname,price from products;
```

3. 查询所有商品都有哪些价格
```sql
SELECT * FROM products DISTINCT price;
```

4. 将所有商品价格+10元进行显示.(别名)
```sql
SELECT price+10 FROM products;
```

## 条件查询
1. 查询商品名称为十三香的商品所有信息
```sql
SELECT * FROM WHERE pname="十三香";
```

2. 查询商品价格>60元的所有的商品信息
```sql
SELECT * from produces WHERE price>60;
```

3. 查询商品名称中包含“新”的商品
```sql
SELECT * FROM pname LIKE "%新%";
```

4. 查询价格为38,68,98的商品
```sql
SELECT * FROM products WHERE price=38 OR price=68 OR price=98;
SELECT * FROM produces WHERE price IN (38,68,98);
```

## 排序查询
1. 查询所有的商品，按价格进行排序.(asc-升序，desc-降序)
```sql
SELECT * FROM products ORDER BY price;
SELECT * FROM products ORDER BY price DESC;
```

2. 查询名称有"新"的商品的信息并且按价格降序排序
```sql
SELECT * FROM products WHERE pname LIKE "%新%" ORDER BY price DESC;
```

## 聚合函数函数
1. 获取所有商品的价格的总和
```sql
SELECT sum(price) FROM products;
```
2. 获得价格表中价格的平均数
```sql
SELECT AVG(price) FROM products;
```
3. 获取商品表中有多少条记录
```sql
SELECT COUNT(*) FROM products;
```

## 分组：使用group by
1. 根据cno字段分组，分组后统计商品的个数
```sql
SELECT cno,COUNT(*) FROM products GROUP BY cno;
```
2. 根据cno分组，分组统计每组商品的总数量,并且总数量`>200`
```sql
SELECT cno, sum(pnum) FROM products  GROUP BY cno HAVING sum(pnum) > 200;
```

## 多表查询
1. 查询用户的订单，没有订单的用户不显示
```sql
--隐式链接
SELECT user.*, orders.* from user, orders WHERE user.id=orders.user_id;
--显式的内连接
SELECT user.*, orders.* FROM user INNER JOIN orders ON user.id = orders.user_id;
```
2. 查询所有用户的订单详情
```sql
SELECT user.*,orders.* FROM user LEFT OUTER JOIN orders ON user.id=orders.user_id;
```
3. 查询所有订单用户的详情
```sql
SELECT orders.*,user.* FROM user RIGHT OUTER JOIN orders ON user.id=orders.user_id;
```

## 子查询
1. 查看用户为张三的订单详情
```sql
SELECT * FROM orders WHERE user_id=(SELECT id FROM user WHERE username='张三');
```
2. 查询所有订单价格大于300的所有用户信息
```sql
SELECT * FROM user WHERE id IN (SELECT user_id FROM orders WHERE price > 300 AND price);
```
3. 查询订单价格大于300的订单信息及相关用户的信息<br>
```sql
内连接
SELECT orders.*, user.* from orders, user WHERE orders.user_id = user.id AND orders.price > 300;
```
<br>
```sql
子查询
SELECT user.*,tmp.* FROM user,(SELECT * FROM orders WHERE price>300) AS tmp WHERE user.id=tmp.user_id;
```


# 案例1-单表的curd操作
## 需求
1. 对一张表中的数据进行增删改查操作
    1. C:create 创建
    2. U:update 修改
    3. R:read 读取|索引 查询
    4. delete 删除

## 技术分析
1. 数据库
2. sql语句

# 案例2-创建多表，可以描述出表与表之间的关系
## 需求
1. 把网上商城里面用的实体创建成表，并且将他们之间建立关系.

## 网上商城的实体
### 关系
1. 一对多
    1. 用户和订单
    ```sql
    CREATE TABLE user(
     id INT PRIMARY KEY AUTO_INCREMENT,
     username VARCHAR(20)
    );
 
    CREATE TABLE orders(
     id INT PRIMARY KEY AUTO_INCREMENT,
     totalprice DOUBLE,
     user_id INT 
    );
    ```
    2. 分类和商品
2. 多对多
    1. 订单和商品
    ```sql
    CREATE TABLE products(
     id INT PRIMARY KEY auto_increment,
     name VARCHAR(20),
     price DOUBLE
    );
    
    CREATE TABLE orderitem(
     oid INT,
     pid INT 
    );
    
    ALTER TABLE orderitem ADD FOREIGN KEY(oid) REFERENCES orders(id);
    ALTER TABLE orderitem ADD FOREIGN KEY(pid) REFERENCES products(id);
    ```
    2. 引入一张中间表，存放两张表的主键，一般会将这两个字段设置为联合主键，这样就可以将多对多关系拆分成两个一对多了
    3. 为了保证数据的有效性和完整性
        1. 需要在中间表上添加两个外键约束即可
    
3. 一对一
    1. 丈夫和妻子

4. ER(Entity Relationship)图可以描述实体与实体之间的关系 
    1. 实体用矩形表示
    2. 属性用椭圆表示
    3. 关系用菱形表示
    
    
# 案例3-多表查询
## 技术分析
1. 内连接
2. 外链接
3. 子查询

## 笛卡尔积
1. 多张表无条件的联合查询，没有任何意义
   
