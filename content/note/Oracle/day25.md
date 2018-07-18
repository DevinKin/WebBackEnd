# 基本命令
1. oracle 中表是属于用户的，用户属于数据库的
## 清屏
1. windows：`host cls`
2. linux`host clear`

## 当前用户
1. `show user`

## 查看当前用户的表
1. `select * from tab;`

## 查看表结构
1. `desc table_name`


## 查看和设置行、列宽
### 行宽操作
1. 查看行宽：`show linesize`
2. 设置行宽：`set linesize 150`
### 列宽操作
1. 设置列宽：`col col_name (format/for) width`
    1. width可以为：
        1. `a8`：a表示为字符串，8为字符串的长度
        2. `9999`：9表示1位数字，4个9表示4位数字 
2. 例子：`col namelist for a60`

## 执行上一条sql语句
1. `/`

## 查询历史sql
1. `#`代表数字，查询上一条sql语句的第几行

## 修改上一条错误的sql语句
### 修改方式
1. c命令(change)：`c /form/from`
    1. 按`/`执行上一条sql语句
2. ed命令：`ed`
    1. 将sql语句放入系统默认的编辑器中编辑

## 显示记录数的限制
1. `break on num`：只显示num列的值，相同值只显示一次

## skip x
1. 不同的列跳过两行

## 取消break的设置
1. `break on null`

## 查询某条语句的执行计划
```oracle
EXPLAIN PLAN FOR SELECT * FROM emp WHERE deptno=10;
```

## 查看该表的执行计划
```oracle
SELECT * FROM TABLE(dbms_xplan.display);
```

## 开启oracle系统输出
```oracle
set serveroutput on;
```

# 查询语句
1. 以scott用户的表为例

## sql语句的格式
```sql
SELECT *|{[DISTINCT ] column|expression [alias],...}
FROM table;
```

## 查询所有员工信息
1. `select * from emp;`

## 通过列名查询
1. `select empno,ename,job,mgr,hiredate,sal,comm,deptno from emp`

## 查询员工信息：员工号 姓名 月薪 年薪
1. `select empno,ename,sal,sal*12 from emp`


## 查询员工信息：员工号 姓名 月薪 年薪 奖金 年收入
1. `select empno,ename,sal,sal*12, comm, sal*12+nvl(comm,0) from emp`


## 查询奖金为空的员工
1. `select * from emp where comm is null`

## distinct消除重复列
1. distinct作用于后面所有的列
### 查看员工表中所有的部门号
1. `select distinct deptno from emp`
### 查看员工表中所有的部门号和工作
1. `select distinct deptno,job from emp`

# 过滤和排序
## 查询10号部门的员工
```oracle
SELECT * FROM emp WHERE deptno=10;
```

## 排序的语法
1. 语法：`SELECT *|列名 FROM 表名 {WHERE 查询条件} ORDER BY 列名1 ASC|DESC, 列名2、表达式、别名、序号 ... ASC|DESC`
2. 默认是升序排序
### 查询员工信息，按照月薪排序
```oracle
SELECT * FROM emp ORDER BY sal;
```
### 查询员工信息，按照年薪排序
```oracle
SELECT * FROM emp ORDER BY sal*12 DESC ;
```

### 查询员工信息，按第四列降序排序，即年薪
```oracle
SELECT empno, ename, sal, sal*12 nsal FROM emp ORDER BY 4 desc;
```

### 多个列排序
order by作用于所有列，先对deptno升序排序，然后对sal升序排序
```oracle
SELECT * from emp ORDER BY deptno,sal;
```
desc降序之作用于离desc关键字最近的一个列
```oracle
SELECT * FROM emp ORDER BY deptno,sal DESC ;
```
结果不一致
```oracle
SELECT * FROM emp ORDER BY sal,deptno DESC ;
```

### 空值排序
查询员工信息，按照奖金排序
```oracle
SELECT * from emp ORDER BY comm DESC NULLS LAST ;
```
oracle中空值最大

# oracle的比较运算符
|操作符|含义|
|---|---|
|=|等于(不是==)|
|>|大于|
|<|小于|
|>=|大于等于|
|<=|小于等于|
|<>|不等于(也可以是!=)|
|BETWEEN...AND...|在两个值之间(包含边界),小值在前，大值在后|
|IN(set)|是否被包含在值列表set中|
|LIKE|模糊查询|
|IS NULL|是否为空值|

1. 赋值使用`:=`符号
2. 在模糊查询中，可以使用ESCAPE标识符选择`%`和`_`符号
    1. `%`：可以匹配任意长度的内容
    2. `_`：可以匹配一个长度的内容
## 例子
### 查询薪水在1000-2000之间的员工
```oracle
SELECT * FROM emp WHERE sal BETWEEN 1000 AND 2000;
```
### 查询部门号是10和20的员工
```oracle
SELECT * FROM emp WHERE deptno IN (10,20);
```

### 查询部门号不是10和20的员工
```oracle
SELECT * FROM emp WHERE deptno NOT IN (10,20);
```

### 查询名字以S开头的员工
```oracle
SELECT * FROM emp where ename LIKE 'S%';
```

### 查询名字是4个字的员工
```oracle
SELECT * FROM emp where ename LIKE '____';
```

### 查询名字中含有下划线的员工
没法查询得到
```oracle
SELECT * FROM emp WHERE ename LIKE '%'%;
```
通过转移字符将`_`转义
```oracle
SELECT * FROM emp WHERE ename LIKE '%\_%' ESCAPE '\';
```

# oracle字符串和日期
1. 字符串和日期要包含在单引号中
2. 字符大小写敏感，日期格式敏感
3. 默认的日期格式是 `DD-MON-RR`

## 字符串大小写敏感
### 查询名叫KING的员工
可以执行
```oracle
SELECT * FROM emp WHERE ename='KING';
```
不可以执行
```oracle
SELECT * FROM emp WHERE ename='King';
```

## 日期格式敏感
### 查询入职日期是17-11月-81年的员工
可以查询
```oracle
SELECT * FROM emp WHERE hiredate='17-NOV-81';
```
不可以查询
```oracle
SELECT * FROM emp WHERE hiredate='1981-11-17';
```

## 修改日期格式
### 查询日期格式
```oracle
SELECT * FROM v$nls_parameters;
```

### 更改日期格式
1. 修改的类型有两种
    1. 当前会话：`session`，当前用户可修改
    2. 系统：`system`，修改全局的，只能管理员去修改
```oracle
ALTER SESSION SET NLS_DATE_FORMAT='yyyy-mm-dd';
```

# oracle的逻辑运算符
|操作符|含义|
|---|---|
|AND|逻辑并|
|OR|逻辑或|
|NOT|逻辑否|

# oracle中的null值
1. 包含null值的表达式都为null
2. null永远！=null
3. 如果集合中有null，不能使用`not in`，但可以使用`in`
    1. `not in`操作符的作用等同于`<> all`不等于所有，即`a not in (10,20,null)`与`a!=b, a!=20, a!=null`等效
    2. `in`操作符等效于`any`操作符
    3. 原因:`not in`是与运算，`in`是或运算
4. 组函数(多行函数)自动过滤NULL值；嵌套滤空函数(NVL)来屏蔽他的滤空的功能
    1. `SELECT SUM(comm)/COUNT(NVL(comm,0)) from emp;`

# SQL优化的原则
1. 尽量使用列名，不需要解析`*`
2. where解析的顺序，从右往左解析的
    1. and优化的时候为假的放在右边
    2. or优化的时候为真的放在右边
3. WHERE和HAVING尽量使用WHERE
    1. WHERE先过滤后分组
    2. having是先分组后过滤
4. 子查询和多表查询功能相同时，尽量使用多表查询
5. UNION和UNION ALL，尽量使用UNION ALL
    1. `UNION=UNION ALL + DISTINCT`
6. 尽量不要使用集合运算

# oracle中的连接符
1. `||`：与mysql的concat类似，拼接字符串
    1. `select 'hello' || 'world' hel from dual`
## 字符串
1. 字符串可以使select列表中的一个字符，数字，日期
2. 日期和字符只能在单引号中出现
3. 每当返回一行时，字符串被输出一次 

# oracle的dual表
1. 伪表
2. 伪列

# SQL语句与SQL*Plus命令
## SQL
1. 一种语言
2. ANSI标准
3. 关键字不能缩写
4. 使用语句控制数据库中的表的定义信息和表的数据

### 常用语句
1. select
2. delete
3. insert
4. update

## SQL*PLUS
1. 一种环境
2. Oracle的特性之一
3. 关键字可以缩写
4. 命令不能改变数据库中的数据的值
5. 集中运行

### 常用语句
1. describe
2. change
3. edit
4. format
5. column 

# mysql事务和oracle
1. mysql需要手动开启事务
2. oracle自动开启事务

# oracle函数
1. 目的：简化操作

## 单行函数
1. 对单行进行变换产生结果

### 通用函数
这些函数适用于任何数据类型，同样也适用于空值
1. `NVL(expr1,expr2)`：如果expr1为null，返回expr2
2. `NVL2(expr1,expr2,expr3)`：当expr1为null的时候，返回expr3，否则返回expr2
3. `NULLIF(expr1,expr2)`：判断expr1和expr2是否相等，相等，返回NULL，否则返回expr1
4. `COALESCE(expr1,expr2,...,exprn)`：在行中从左到右找到第一个不为null的值

NVL2的使用
```oracle
SELECT sal*12+nvl2(comm, comm, 0) FROM emp;
```

NULLIF的使用
```oracle
SELECT nullif('abc','abc') FROM dual;
```

COALESCE的使用
```oracle
SELECT comm,sal,coalesce(comm,'sal') first_not_null FROM emp;
```

### 字符函数
#### 大小写控制函数
1. LOWER
2. UPPER
3. INITCAP(单词首字母大写)

```oracle
SELECT lower('Hello WOrld') lower, upper('hello world') upper, initcap('hello world') ic from dual;
```

#### 字符控制函数
1. CONCAT
2. SUBSTR
3. LENGTH/LENGTHB
4. INSTR
5. LPAD | RPAD(左填充，右填充)
6. TRIM(去掉前后指定的字符)
7. REPLACE

SUBSTR的使用
```oracle
---取第三位后的子字符串
SELECT substr('Hello World',3) substring FROM dual;
---取第三位后的子字符串，长度为4
SELECT substr('Hello World',3, 4) substring FROM dual;
```

LENGTH和LENGTHB的使用
```oracle
SELECT length('Hello WOrld') zifu, length('Hello world') zijie FROM dual;
SELECT length('北京') zifu, length('北京') zijie FROM dual;
```

INSTR的使用
```oracle
SELECT instr('Hello World', 'll') pos FROM dual;
```

LPAD和RPAD的使用
```oracle
SELECT lpad('abcd', 10, '*') leftpad, rpad('abcd', 10, '*') FROM dual;
```

TRIM的使用
```oracle
SELECT trim('H' FROM 'Hello WorldH') FROM dual;
```

REPALCE的使用
```oracle
SELECT replace('Hello World', 'l', '*') FROM dual;
```

### 数值函数
1. ROUND(四舍五入)
2. TRUNC(截断)
3. MOD(求余)

ROUND的使用
```oracle
---45.93 45.9 46 50 0
SELECT round(45.926,2) one,round(45.926,1) two,round(45.926,0) three, round(45.926,-1) four,round(45.926,-2) five FROM dual;   
```
TRUNC的使用
```oracle
---45.92 45.9 45 40 0
SELECT trunc(45.926,2) one,trunc(45.926,1) two,trunc(45.926,0) three, trunc(45.926,-1) four,trunc(45.926,-2) five FROM dual;   
```

### 转换函数
#### 数据类型转换
##### 隐式转换
###### Oracle自动完成下列转换
|源数据类型|目标数据类型|
|---|---|
|VARCHAR2 or CHAR|NUMBER|
|VARCHAR2 or CHAR|DATE|
|NUMBER|VARCHAR2|
|DATE|VARCHAR2|

##### 显式转换
|类型|目标类型|转换的函数|
|---|---|---|---|
|CHARACTER|NUMBER|TO_NUMBER|
|CHARACTER|DATE|TO_DATE|
|NUMBER|CHARACTER|TO_CHAR|
|DATE|CHARACTER|TO_CHAR|
1. TO_CHAR函数的格式
    1. 语法：`TO_CHAR(date,'format_model)`
    2. 必须包含在单引号中而且大小写敏感
    3. 可以包含任意的有效日期格式
    4. 日期之间用逗号隔开
    5. 语法：`TO_CHAR(number,'format_model)`
2. TO_NUMBER函数的格式
    1. 语法：`TO_NUMBER(char[, 'format_model'])`
3. TO_DATE函数将字符转换为日期
    1. `TO_DATE(char[, 'format_model'])`
4. TO_CHAR函数中常用的格式
    1. 9：数字
    2. 0：零
    3. $：美元符
    4. L：本地货币符号
    5. .：小数点
    6. ,：千位符

TO_CHAR函数的使用
```oracle
---2018-07-6 16:16:12 今天是星期五
SELECT to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss "今天是"DAY') FROM dual;
```  
查询员工薪水：两位小数，千位符，本地货币代码
```oracle
SELECT to_char(sal, 'L9,9999.99') FROM emp;
```

##### 日期格式的元素
|格式|说明|举例|
|---|---|---|
|YYYY|Full year numbers|2011|
|YEAR|Year spelled out(年的英文全称|Twenty eleven|
|MM|Two-digit value of month(两位数字的月份|04|
|MONTH|Full name of the month(月的全称)|4月|
|DY|Three-letter abbreviation of the day of the week(星期几)|星期一|
|DAY|Full name of the day of the week|星期一|
|DD|Numeric day of the month|02|

### 日期函数
#### 当前时间
```oracle
SELECT sysdate FROM dual;
```

#### 格式化时间
1. 格式: `SELECT to_char(sysdate, '格式') FROM dual;`
```oracle
SELECT to_char(sysdate, 'yyyy-mm-dd hh24') FROM dual;
SELECT to_char(sysdate, 'yyyy-mm-dd hh12') FROM dual;
SELECT to_char(sysdate, 'yyyy-mm-dd hh24:mi:ss') FROM dual;
```

#### 日期的数学运算
1. 在日期上加上或减去一个数字(单位是天)结果仍为日期
2. 两个日期相减返回日期之间相差的天数
3. 可以用数字除24来向日期中加上或减去小时

显示昨天，今天，明天
```oracle
SELECT (sysdate - 1) yesterday, sysdate today, (sysdate + 1) tomorrow FROM dual;
```
计算员工的工龄
```oracle
SELECT ename, hiredate, (sysdate - hiredate) day, (sysdate - hiredate)/7 week, (sysdate - hiredate)/30 mon FROM emp;
```

#### 常用日期函数
1. MONTHS_BETWEEN：两个日期相差的月数
2. ADD_MONTHS：向指定日期中加上若干月数
3. NEXT_DAY：指定日期的下一个日期
4. LAST_DAY：本月的最后一天
5. ROUND：日期四舍五入
6. TRUNC：日期截断

MONTHS_BETWEEN的使用
```oracle
SELECT ename,hiredate,(sysdate-hiredate)/30 one, months_between(sysdate,hiredate) two FROM emp;
```
ADD_MONTHS的使用
```oracle
SELECT add_months(sysdate,53) FROM dual;
```
LAST_DAY的使用
```oracle
SELECT last_day(sysdate) FROM dual;
```
NEXT_DAY的使用，next_day的应用，每个星期一自动备份表中的数据
```oracle
---下一个星期五
SELECT next_day(sysdate, 'Fri') FROM dual;
```
ROUND的使用
```oracle
SELECT round(sysdate, 'month') mon, round(sysdate, 'year') year FROM dual;
```

### 条件表达式
#### CASE表达式
格式
```oracle
CASE expr WHEN comparison_expr THEN return_expr
          [WHEN comparison_expr1 THEN return_expr1
           WHEN comparison_expr2 THEN return_expr2
           WHEN comparison_expr3 THEN return_expr3
           ELSE else_expr]
END
```
例子：
```oracle
---总裁涨工资1000，经理涨工资800，其他员工涨工资400
SELECT empno, ename, job, sal before_raise,
    CASE job WHEN 'PRESIDENT' then sal+1000
      WHEN 'MANAGER' THEN sal+800
      ELSE sal+400
    END after_raise
FROM emp;
```

#### DECODE函数
格式
```oracle
DECODE (col|expression, search1, result1
                        [, search2, result2,...,]
                        [, default])
```

例子：
```oracle
---总裁涨工资1000，经理涨工资800，其他员工涨工资400
select empno,ename,job,sal before_raise,
        decode (job, 'PRESIDENT', sal+1000,
                        'MANAGER', sal+800,
                        sal+400) after_raise
from emp
```
```oracle
---根据10号部门员工的工资，显示税率
SELECT ename, sal,
    decode(trunc(sal/2000, 0),
                     0, 0.00,
                     1, 0.09,
                     2, 0.20,
                     3, 0.30,
                     4, 0.40,
                     5, 0.42,
                     6, 0.44,
                        0.45) TAX_RATE;
```

## 多行函数(分组函数|组函数)
1. 对多行进行变换产生结果
### 什么是分组函数
1. 分组函数作用于一组数据，并对一组数据返回一个值

### 常用的分组函数
1. AVG
2. COUNT
3. MAX
4. MIN
5. SUM
6. WM_CONCAT(varchar2)

计算工资总额，SUM函数的使用
```oracle
SELECT sum(sal) FROM emp;
```
计算总人数，COUNT函数的使用
```oracle
SELECT count(*) FROM emp;
```
计算平均工资
```oracle
SELECT sum(sal)/count(*) one, avg(sal) two FROM emp;
```
计算平均奖金
```oracle
SELECT sum(comm)/count(*) one, sum(comm)/count(comm) two, avg(comm) three FROM emp;
```

## 分组数据
分组数据：`GROUP BY`子句语法
```oracle
SELECT column, group_function(column)
FROM table
[WHERE condition]
[GROUP BY group_by_expression]
[ORDER BY column];
```
在select语句中，所有没有包含在组函数中的列，都必须包含在GROUP BY子句的后面
```oracle
SELECT deptno,job, avg(sal)
FROM emp
GROUP BY deptno,job;
```
包含在GROUP BY子句中的列不必包含在SELECT列表中
```oracle
SELECT avg(sal)
FROM emp
GROUP BY deptno;
```
GROUP BY分组先按第一列分组，再按第二列分组，以此类推

### GORUP BY语句的增强
按照部门，不同的职位的统计员工工资的总和
```oracle
SELECT deptno, job, sum(sal) 
FROM emp 
GROUP BY ROLLUP(deptno, job);
 
--作用是以下三句sql一致
SELECT deptno, job, sum(sal) FROM emp GROUP BY deptno,job;
+
SELECT deptno, sum(sal) FROM emp GROUP BY deptno;
+
SELECT sum(sal) FROM emp;
```
其中，抽象的理解方式
```oracle
GROUP BY rollup(a,b)
=
group by a,b
+
group by a
+
group by null;(没有分组)
```


### 求每个部门的平均工资
```oracle
SELECT deptno, avg(sal)
FROM emp
GROUP BY deptno;
```

### 多个列的分组
按照部门，不同的职位的统计员工工资的总和
```oracle
SELECT deptno, job, sum(sal)
FROM emp
GROUP BY deptno,job
ORDER BY 1;
```

## 过滤分组数据
1. 使用HAVING过滤分组的条件
    1. 行已经被分组
    2. 使用了组函数
    3. 满足HAVING子句中条件的分组将被显示
    
### where和having的区别
1. where后面不能使用多行函数
    
### 求平均工资大于2000的部门
```oracle
SELECT deptno, avg(sal) 
FROM emp
GROUP BY deptno
HAVING avg(sal) > 2000;
```

### 求10号部门的平均工资
使用HAVING
```oracle
SELECT deptno, avg(sal)
FROM emp
GROUP BY deptno
HAVING deptno = 10;
```
使用where
```oracle
SELECT deptn,avg(sal)
FROM emp
WHERE deptno=10
GROUP BY deptno;
```

# 多表查询
1. 必须要有连接条件，连接条件为N-1个，N为多表查询中的表数
## 多表连接基本查询
语法：
```oracle
SELECT {DISTINCT } *|列名 FROM 表名 别名, 表名1 别名1
       {WHERE 限制条件 ORDER BY 排序字段 ASC|DESC ...}
```

## Oracle的连接
1. 等值连接
2. 不等值连接
3. 外连接
4. 自连接

## 等值连接
查询员工信息：员工号，姓名，月薪，部门名称
```oracle
SELECT e.empno, e.ename,e.sal, d.dname
from emp e, dept d
WHERE e.deptno=d.deptno;
```

## 不等值连接
查询员工信息：员工号，姓名，月薪，工资级别
```oracle
SELECT  e.empno, e.ename, e.sal, s.grade
FROM emp e, salgrade s
WHERE e.sal >= s.losal AND e.sal <= s.hisal;
```

## 外连接
按部门统计员工信息：部门号，部门名称，人数
```oracle
SELECT d.deptno deptno, d.dname dname, count(e.empno)
FROM emp e, dept d
WHERE e.deptno = d.deptno
GROUP BY d.deptno, d.dname;
```
此处会出现错误，部门号40在emp表中不存在，但是在dept表中存在，所以结果中不包含40号部门，所以结果错误<br>
希望，对于某些不成立的记录(40号部门)，仍然希望包含在最后的结果中

### 左外连接
1. 当条件不成立的时候，左表仍然被包含在最后的结果中
2. 写法：`where e.deptno=d.deptno(+)`
### 右外连接
1. 当条件不成立的时候，右表仍然被包含在最后的结果中
2. 写法：`where e.deptno(+)=d.deptno`

按部门统计员工信息：部门号，部门名称，人数
```oracle
SELECT d.deptno deptno, d.dname dname, count(e.empno)
FROM emp e, dept d
WHERE e.deptno(+) = d.deptno
GROUP BY d.deptno, d.dname;
```

## 自连接
1. 自连接不适合操作大表，列数多的表
查询员工信息：员工姓名，老板姓名
```oracle
SELECT e.ename employee, b.ename boss
FROM emp e, emp b
WHERE e.mgr = b.empno;
```

# 层次查询
1. 单表查询
2. 使用`CONNECT BY PRIOR`关键字
3. 使用`START WITH`起始条件
4. `level`是伪列
5. 语法格式
```oracle
SELECT ***
FROM emp 
CONNECT BY PRIOR 上一层的员工=当前层的老板
START WITH 起始条件
```
例子：
```oracle
SELECT level, empno, ename, mgr
FROM emp
CONNECT BY PRIOR empno=mgr
--- START WITH mgr IS NULL;
START WITH empno=7839
ORDER BY 1;
```


