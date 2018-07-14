# 子查询
1. 在一个查询内部还包括另一个查询，则此查询称为子查询，SQL的任何位置都可以插入子查询
2. 子查询(内查询)主查询之前一次执行完成
3. 子查询的结果被主查询(外查询)使用

## 子查询的语法
```oracle
SELECT select_list
FROM table
WHERE expr operator
(SELECT select_list
FROM table);
```

## 子查询注意的问题
1. 合理的书写风格
2. 括号
3。 可以在主查询的where、select、HAVING、from后面使用子查询
4. 不可以在group by后面使用子查询
5. 强调from后面的子查询
6. 主查询和子查询可以不是同一张表，只要子查询满足的结果主查询可以使用即可
7. 一般不在子查询排序，但top-n分析问题中，必须对子查询排序
8. 一般先执行子查询，再执行主查询，但相关子查询例外
9. 单行子查询只能使用单行操作符，多行子查询只能使用多行操作符
10. 子查询的空值问题

## 子查询的使用
查询工资比SCOTT高的员工信息
```oracle
---1. SCOTT的工资
select sal from emp where ename='SCOTT';
---2. 查询工资高于3000的员工
SELECT * FROM emp sal > 3000;
---=
SELECT * FROM emp
WHERE sal > (SELECT sal FROM emp WHERE ename='SCOTT');
```

### 可以在主查询的where、select、HAVING、from后面使用子查询
select后的子查询只能是单行子查询
```oracle
SELECT empno, ename, sal, (SELECT job FROM emp WHERE empno=7839) 第四列;
```

having后面使用子查询
```oracle
SELECT deptno, MIN(sal)
FROM emp
GROUP BY deptno
HAVING min(sal)>0
(SELECT min(sal)
FROM emp
WHERE deptno=10);
```

### 强调from后面的子查询
查询员工信息：员工号 姓名 月薪
```oracle
SELECT * 
FROM (SELECT empno, ename, sal FROM emp);
```
查询员工信息：员工号，姓名，月薪，年薪
```oracle
SELECT *
FROM (SELECT empno, ename, sal, sal*12 annsal FROM emp)
```

### 主查询和子查询可以不是同一张表，只要子查询满足的结果主查询可以使用即可
查询部门名称是SALES的员工
```oracle
SELECT *
FROM emp
WHERE deptno=(SELECT deptno FROM deptno WHERE dname='SALES');
```

### 一般不在子查询排序，但top-n分析问题中，必须对子查询排序

### 一般先执行子查询，再执行主查询，但相关子查询例外

### 单行子查询只能使用单行操作符，多行子查询只能使用多行操作符

## 单行子查询
|操作符|含义|
|---|---|
|=|Equal to|
|>|Greater than|
|>=|Greater than or equal to|
|<|Less than|
|<=|Less than or equal to|
|<>|Not equal to|
1. 只返回一条记录，即一行
2. 使用单行比较操作符

例：
```oracle
SELECT ename, job, sal
FROM emp
WHERE job = (SELECT job FROM emp WHERE empo=7566)
AND sal > (SELECT sal FROM emp WHERE empno=7782);
```

## 多行子查询
|操作符|含义|
|---|---|
|IN|等于列表中的任何一个|
|ANY|和子查询返回的任意一个值比较|
|ALL|和子查询返回的所有值比较|
1. 返回多行
2. 使用多行比较操作符

### 多行子查询中的null
查询不是老板的员工
```oracle
---如果mgr列包含空值，则不返回任何列
SELECT *
FROM emp
WHERE emp NOT IN (SELECT mgr FROM emp WHERE mgr IS NOT NULL );
```

### IN的使用
查询部门名称是SALES和ACCOUNTING的员工
```oracle
SELECT * FROM emp
WHERE deptno IN (SELECT deptno FROM dept WHERE dname='SALES' OR dname='ACCOUNTING');
```

#### ANY的使用
查询工资比30部门任意一个员工高的员工信息
```oracle
SELECT * FROM emp WHERE sal > ANY (SELECT sal FROM emp WHERE deptno=30);
```

#### ALL的使用
查询工资比30号部门所有员工工资高的员工信息
```oracle
SELECT * FROM emp WHERE sal > ALL(select sal from emp where deptno=30);
```

## 相关子查询
1. 定义：将主查询中的值作为参数传递给子查询
2. 例：查询员工信息：员工工资高于部门平均工资的员工信息
```oracle
SELECT empno, ename, sal, (SELECT avg(sal) FROM emp WHERE deptno=e.deptno) avgsal
FROM emp e
WHERE sal > (SELECT avg(sal) FROM emp WHERE deptno=e.deptno);
```

## 使用子查询创建表
1. 使用`AS subquery`选项，将创建表和插入数据结合起来
2. 指定的列和子查询中的列要一一对应
3. 通过列名和默认值定义列
```oracle
CREATE  TABLE table 
    [(column, column...)]
AS subquery;
```
### 创建表：保存20号部门的员工
```oracle
CREATE TABLE emp20
AS 
SELECT * FROM emp WHERE deptno=20;
```

### 创建表：员工，姓名，月薪，年薪，部门名称
```oracle
CREATE TABLE empinfo 
AS 
SELECT e.empno, e.ename, e.sal, e.sal*12 annsal, d.dname
FROM emp e, dept d
WHERE e.deptno = d.deptno;
```

## 子查询所有解决的问题
1. 不能一步求解

# 课堂练习
## 找到员工表中工资最高的前三名
1. 使用伪列rownum

### 关于ROWNUM使用的问题
1. rownum永远按照默认的顺序生成
2. rownum只能使用`<`或者`<=`，不能使用`>`或`>=`

### rownum行号
```oracle
SELECT ROWNUM, empno, ename, sal FROM emp;
```

rownum永远从1开始，所以大于和大于等于无法查找到结果，`>1或>=1除外`
```oracle
SELECT ROWNUM,empno, ename, sal FROM emp
WHERE ROWNUM <=8
```
可以用此方法代替，注意，r不是e2表的伪列，是e2表的第一列
```oracle
SELECT e2.* 
FROM (SELECT ROWNUM r, e1.*
FROM (SELECT * FROM emp ORDER BY  sal desc) e1
WHERE ROWNUM<=8) e2
WHERE r>=5;
```

### 答案
```oracle
SELECT ROWNUM, empno, ename, sal 
FROM 
(SELECT empno, ename, sal FROM emp ORDER BY sal DESC )
WHERE ROWNUM >=3;
```

## 找到员工表中薪水大于本部门平均薪水的员工
### 答案
```oracle
SELECT e.empno, e.ename,e.sal, d.avgsal
FROM emp e, (SELECT deptno, avg(sal) avgsal FROM emp ORDER BY deptno) d
WHERE e.dpetno = d.deptno AND e.sal > d.avgsal
```
使用相关子查询
```oracle
SELECT empno, ename, sal, (SELECT avg(sal) FROM emp WHERE deptno=e.deptno) avgsal
FROM emp e 
WHERE sal > (SELECT avg(sal) FROM emp WHERE deptno=e.deptno);
```

## 统计每年入职的员工的个数(不能使用子查询)
### 答案
```oracle
SELECT 
count(*) Total, 
sum(decode(to_char(hiredate,'YYYY'), '1980',1,0)) "1980", 
sum(decode(to_char(hiredate,'YYYY'), '1981',1,0)) "1981", 
sum(decode(to_char(hiredate,'YYYY'), '1982',1,0)) "1982", 
sum(decode(to_char(hiredate,'YYYY'), '1987',1,0)) "1987" 
FROM emp
```

# oracle的标准表、索引表、临时表
## 临时表
### 创建方式
1. 手动创建: `create global temporary table *****`
2. 自动创建：排序

### 特点
1. 当事务或对话结束的时候，表中的数据自动删除

### 创建基于事务的临时表
```oracle
CREATE GLOBAL TEMPORARY TABLE temptest1
(tid NUMBER, tname VARCHAR2(20))
ON COMMIT DELETE ROWS;
```

### 创建基于会话的临时表
exit后该表被删除
```oracle
CREATE GLOBAL TEMPORARY TABLE temptest2
(tid NUMBER, tname VARCHAR2(20))
ON COMMIT PRESERVE ROWS;
```

# 行转列函数
1. 格式：`wm_concat(varchar2)`
2. 是组函数，没有被包含在该函数中的列，必须在group by语句后
3. 例：
```oracle
SELECT deptno, WM_CONCAT(ename) namelist
FROM emp
GROUP BY deptno;
```

# 集合操作
## 集合操作符
|集合|操作符|
|---|---|
|并集并去重复|UNION|
|并集|UNION ALL|
|交集|INTERSECT|
|差集|MINUS|

## 关于运算的的问题
1. 参与运算的各个集合必须列数相同且类型一致
2. 采用第一个集合作为最后的表头
3. `ORDER BY`永远在最后
4. 括号改变执行的顺序

## 查询部门号为10和20的员工信息
```oracle
SELECT * FROM emp WHERE deptno=10
UNION 
SELECT * FROM emp WHERE deptno=20
```

## 通过部门号和工作分组查询员工信息
```oracle
SELECT deptno,job,sum(sal) FROM emp GROUP BY deptno,job
UNION 
SELECT deptno, to_char(NULL), sum(sal) FROM emp GROUP BY deptno,job 
UNION 
SELECT to_number(NULL ), to_char(NULL), sum(sal) FROM emp GROUP BY deptno,job ;
```

# SQL的类型
1. DML(Data Manipulation Language)：数据操作语言(可以回滚)
    1. insert
    2. update
    3. delete
    4. selct
2. DDL(Data Definition Language)：数据定义语言(不可以回滚)
    1. create table
    2. alter table
    3. drop table
    4. truncate table
    5. create/drop view,sequence,index,synonym
3. DCL(Data Control Language)：数据控制语言 
    1. GRANT：授权
    2. REVOKE：撤销权限
    
    
    
    
    
    
# INSERT语句
1. 语法：`INSERT INTO 表名[(列名1，列名2，...)] VALUES(值1，值2...)`

## &地址符
1. 可以通过读取输入来插入数据
```oracle
INSERT INTO emp(empno, ename, sal, deptno) VALUES (&empno, &ename, &sal, &deptno);
```

## 从其他表中拷贝数据
1. 不必书写VALUES子句
2. 子查询中的值列表应与INSERT子句中的列名对应
一次性将emp中所有10号部门的员工插入到emp10中
```oracle
INSERT INTO sales_reps(id,name,salary,commission_pct)
    SELECT employee_id, last_name, salary, commission_pct
    FROM employees
    WHERE job_id LIKE '%REP%';
```

## oracle插入海量数据
1. 数据泵(PLSQL程序：dbms_datapump)
2. SQL*Loader工具
3. 外部表

# UPDATE语句
1. 语法
```oracle
UPDATE TABLE 
SET  column=value[, column = value,...]
[WHERE condition]
```

2. 可以一次更新多条数据

# DELETE语句
1. 语法：
```oracle
DELETE FROM table
[WHERE condition];
```

## delte和truncate的区别
1. delete逐条删除
2. truncate先摧毁表，再重建
3. DELETE是DML语句(可以回滚)，TRUNCATE是DDL语句(不可以回滚)(清空表+commit)
4. delte不会释放空间，truncate会释放空间
5. delete语句可以闪回(flashback)，truncate不可以闪回(flashback)，提交事务后仍可以撤销事务
6. delete会产生碎片，truncate不会产生碎片

## oracle去掉内存碎片
1. `alter table 表名 move;`
2. 数据的导出和导入
    1. 导出的命令:`exp 和 expdp`
    2. 导入命令：`imp 和 imdp`
    
    
    
    
# 数据库事务
## 数据库事务由以下的部分组成
1. 一个或多个DML语句
2. 一个DDL语句
3. 一个DCL语句

## Oracle中的事务标志
1. 起始标志：事务中第一条DML语句
2. 结束标志：
    1. 提交
        1. 显式提交：commit
        2. 隐式提交：正常退出exit，DDL，DCL
    2. 回滚
        1. 显式回滚：rollback
        2. 隐式回滚：非正常退出，掉电，宕机
        
### Oracle数据库的隔离级别
1. COMMITED
2. SERIALIZABLE
3. READ ONLY
4. 默认的隔离级别为READ COMMITED
        

# Oracle的SQL执行时间的开关
1. 默认是关闭的
2. 使用命令：`set timing on` 打开

# Oracle表的数据类型
|数据类型|描述|
|---|---|
|VARCHAR2(size)|可变长字符数据|
|CHAR(size)|定长字符数据|
|NUMBER(p,s)|可变长数值数据|
|DATE|日期型数据|
|LONG|可变长字符数据，最大可达到2G|
|CLOB|字符数据，最大可达到4G|
|RAW and LONG RAW|原始的二进制数据|
|BLOB|二进制数据，最大可达到4G|
|BFILE|存储外部文件的二进制数据，最大可达到4G|
|ROWID|行地址|

# 创建表和管理表

## 创建表
1. 必须具备
    1. CREATE TABLE权限
    2. 存储空间
2. 必须指定
    1. 表名
    2. 列名，数据类型，数据类型和大小
3. 语法
```oracle
CREATE TABLE [schema.]table
             (column datetype [DEFAULT expr][, ...]);
```

## 使用子查询创建表
1. 使用`AS subquery`选项，将创建表和插入数据结合起来
2. 指定的列和子查询中的列要一一对应
3. 通过列名和默认值定义列
```oracle
CREATE  TABLE table 
    [(column, column...)]
AS subquery;
```
### 创建表：保存20号部门的员工
```oracle
CREATE TABLE emp20
AS 
SELECT * FROM emp WHERE deptno=20;
```

### 创建表：员工，姓名，月薪，年薪，部门名称
```oracle
CREATE TABLE empinfo 
AS 
SELECT e.empno, e.ename, e.sal, e.sal*12 annsal, d.dname
FROM emp e, dept d
WHERE e.deptno = d.deptno;
```

## 修改表(ALTER TABLE语句)
1. 追加新的列
2. 修改现有的列
3. 删除一个列

追加列
```oracle
---增加photo保存图片
ALTER TABLE test1 ADD photo blob;
```

修改列
```oracle
ALTER TABLE test1 MODIFY tname VARCHAR2(40);
```

删除列
```oracle
ALTER TABLE tes1 DROP COLUMN photo;
```

重命名列
```oracle
ALTER TABLE test1 RENAME COLUMN tname TO username;
```

## 重命名表
```oracle
RENAME test1 to test2;
```

## 删除表
1. 数据和结构都被删除
2. 所有正在运行的相关事务被提交
3. 所有相关索引被删除
4. `DROP TABLE`语句不能回滚，但是可以闪回

1. 格式：
```oracle
DROP TABLE table_name [PURGE ];
```
`PURGE`关键字能删除表而不经过回收站，无法恢复表


### Oracle的回收站
1. 管理员没有回收站
#### 查看回收站
```oracle
SHOW RECYCLEBIN;
```
#### 清空回收站
```oracle
PURGE RECYCLEBIN;
```

#### 查看回收站的表
```oracle
SELECT * FROM "BIN$cGoOQYkXzWrgUBKsAgABtA==$0";
```

#### 把数据表从回收站中回复
```oracle
---闪回删除
FLASHBACK [TABLE|DATABASE] testsavepoint TO BEFORE DROP RENAME TO xxx;
FLASHBACK [TABLE|DATABASE] "BIN$cGoOQYkZzWrgUBKsAgABtA==$0" TO BEFORE DROP RENAME TO xxx;
```

# 约束
1. 约束是表一级的限制
2. 如果存在以来关系，约束可以防止错误的删除数据
3. 约束的类型
    1. NOT NULL
    2. UNIQUE
    3. PRIMARY KEY
    4. FOREIGN KEY
    5. CHECK
    
## CHECK约束
1. 检查性约束
2. 定义每一行记录所必须满足的条件
3. 下面的表达式可以使用在check约束中
    1. 引用`CURRVAL,NEXTVAL,LEVEL和ROWNUM`
    2. 调用`SYSDATE,UID,USER,和USERENV函数`
    3. 另一个表查询记录
```oracle
CREATE TABLE test3 (
  tid NUMBER,
  tname VARCHAR2(20),
  gender VARCHAR2(8) CHECK (gender IN ('男','女','male', 'female')),
  sal NUMBER CHECK (sal > 0)
);
insert into test3 values(2,'Mike','gender',3000)
*
ERROR at line 1:
ORA-02290: check constraint (SCOTT.SYS_C0011281) violated
```

## FOREIGN KEY外键约束
1. `FOREIGN KEY`：在子表中，定义了一个表级的约束
2. `REFERENCES`：指定表和父表中的列
3. `ON DELETE CASCADE`：当删除父表时，级联删除字表记录
4. `ON DELETE SET NULL`：将字表的相关依赖记录d额外键值置为null

例：
```oracle
create table student
(
        sid number constraint student_pk primary key,
        sname varchar2(20) constraint student_name_notnull not null,
        gender varchar2(10) constraint student_gender_check check(gender in ('male','female')),
        email varchar2(40) constraint student_email_unique unique
                           constraint student_email_notnull not null,
        deptno number constraint student_fk references dept(deptno) on delete set null
)

```

# Oracle常见的数据库对象
|对象|描述|
|---|---|
|表|基本的数据存储集合，由行和列组成|
|视图|从表中抽出逻辑相关的数据集合|
|序列|提供有规律的数值|
|索引|提高查询的效率|
|同义词|给对象其别名|

## 视图
1. 视图是一种虚表，视图也是一张表
2. 视图建立在已有表的基础上，视图赖以建立的这些表称为基表
3. 向视图提供数据内容的语句为`SELECT`语句，可以将视图理解为存储起来的`SELECT`语句
4. 视图向用户提供基表数据的另一种表现形式
5. 创建视图必须要有创建视图的权限，普通用户一般没有，管理员有

### 创建视图
1. 语法格式：
```oracle
CREATE [OR REPLACE] [FORCE|NOFORCE] VIEW view
    [(alias[, alias]...)]
    AS subquery
    [WITH CHECK OPTION [CONSTRAINT constraint]]
    [WITH READ ONLY [CONSTRAINT constraint]]
```
1. `FORCE`：子查询不一定存在
2. `NOFORCE`：子查询存在(默认)
3. `WITH READ ONLY`：只能做查询操作
4. 视图只能创建，删除，替换，不能修改
5. 子查询可以是复杂的SELECT语句
```oracle
CREATE OR REPLACE VIEW empinfoview
AS 
SELECT e.empno, e.ename, e.sal, e.sal*12 annsal, dname
FROM emp e, dept d
WHERE e.deptno = d.deptno
WITH READ ONLY;
```

### 视图的优点
1. 限制数据访问
2. 简化复杂查询
3. 提供数据的相互独立
4. 同样的数据，可以有不同的显示方式
5. 但视图不能提高性能

### 简单视图和复杂视图
|特性|简单视图|复杂视图|
|---|---|---|
|表的数量|一个|一个或多个|
|函数|没有|有|
|分组|没有|有|
|DML操作|可以|有时可以|
1. 注意，不建议通过视图对表进行修改

### 视图中使用DML的规定
1. 可以在简单视图中执行DML操作
2. 当视图定义中包含以下元素之一时不能使用delete
    1. 组函数
    2. GROUP BY子句
    3. DINSTINCT关键字
    4. ROWNUM伪列

### 视图屏蔽DML操作
1. 可以使用`WITH READ ONLY`选项屏蔽对视图的DML操作
2. 任何DML操作都会返回一个Oracle Server错误

### 删除视图
1. 删除视图只是删除视图的定义，并不会删除基表的书
```oracle
DROP VIEW view;

DROP VIEW empvu80;
```

## 序列
1. 可供多个用户用来产生唯一数值的数据库对象
2. 自动提供唯一的数值
3. 共享对象
4. 主要用户提供主键值
5. 将序列值装入内存可提高访问效率

## 创建序列
1. 语法：
```oracle
CREATE SEQUENCE sequenct 
    [INCREMENT BY n]
    [START WITH n]
    [{MAXVALUE n | NOMAXVALUE}]
    [{MINVALUE n | NOMINVALUE}]
    [{CYCLE | NOCYCLE}]
    [{CACHE n | NOCACHE}]
```

### 创建序列实例
1. 创建序列DEPT_DEPTID_SEQ表为DEPARTMENTS提供主键
2. 不使用CYCLE选项
```oracle
CREATE SEQUENCE dept_deptid_seq
    INCREMENT BY 10
    START WITH 120
    MAXVALUE 9999
    NOCACHE 
    NOCYCLE;
```

## NEXTVAL 和 CURRVAL伪列
1. NEXTVAL返回序列中下一个有效的值，任何用户都可以引用
2. CURRVAL中存放序列的当前值
3. NEXTVAL应在CURRVAL之前指定，二者应同时有效
4. 使用方式：`序列名.NEXTVAL或序列名.CURRVAL`

## 使用序列注意的要点
1. 将序列值装入内存可提高访问效率
2. 序列在下列情况下出现裂痕
    1. 回滚
    2. 系统异常
    3. 多个表同时使用同一序列
4. 如果不将序列装入内存(NOCACHE)，可以使用表`USER_SEQUENCES`查看序列当前的有效值

## 修改序列
1. 修改序列的增量，最大值，最小值，循环选项，或是否装入内存
```oracle
ALTER SEQUENCE dept_deptid_seq 
    INCREMENT BY 20
    MAXVALUE 999999
    NOCACHE 
    NOCYCLE;
```

## 删除序列
1. 使用`DROP SEQUENCE`语句删除序列
2. 删除之后，序列不能再次被引用
```oracle
DROP SEQUENCE dept_deptid_seq;
```

## 索引
1. 一种独立于表的模式对象，可以存储在与表不同的磁盘或表空间中
2. 索引被删除或损坏，不会对表产生影响，其影响只是查询的速度
3. 索引一旦建立，Oracle管理系统会对其进行 自动维护，而且Oracle管理系统决定何时使用索引，用户不在用查询语句中指定使用哪个索引
4. 在删除一个表时，所有基于该表的索引都会自动被删除
5. 通过指针加速Oracle服务器的查询速度
6. 通过快速定位数据的方法，减少磁盘I/O
7. 索引由伪列`ROWID`(表中某行的内存地址)，和索引字段组成

## 索引的创建
1. 自动创建：在定义`PRIMARY KEY`或`UNIQUE`约束后系统自动在响应的列上创建唯一性索引
2. 手动创建：用户可以在其他列上创建非唯一的索引，以加速查询
3. 语法：
```oracle
CREATE INDEX index
ON table (column[,column]...);
```

例子：
```oracle
CREATE INDEX emp_last_name_idx
ON employees(last_name);
```

### Oracle中的索引类型
1. B树索引(默认)
2. 位图索引(矩阵)

### 以下情况下创建索引
1. 列中数据值分布范围很广
2. 列经常在WHERE子句或连接条件中出现
3. 表经常被访问而且数据量很大，访问的数据大概占数据总量的2%到4%

### 下列情况不要创建索引
1. 表很小
2. 列不经常作为连接条件或出现在WHERE子句中
3. 查询的数据大于2%到4%
4. 表经常更新

## 同义词
1. 使用同义词访问相同的对象
    1. 方便访问其他用户的对象
    2. 缩短对象名字的长度
    3. PUBLIC为公有同义词，所有用户都可以使用
2. 语法：
```oracle
CREATE [PUBLIC] SYNONYM synonym
FOR object;
```

为视图DEPT_SUM_VU创建同义词
```oracle
CREATE SYNONYM d_sum
FOR dept_sum_vu;
```

删除同义词
```oracle
DROP SYNONYM d_sum;
```
