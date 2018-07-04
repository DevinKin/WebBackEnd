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

## 执行上一条sql语句
1. `/`

## 修改上一条错误的sql语句
## 查询历史sql
1. `#`代表数字，查询上一条sql语句的第几行

## 修改方式
1. c命令(change)：`c /form/from`
    1. 按`/`执行上一条sql语句
2. ed命令：`ed`
    1. 将sql语句放入系统默认的编辑器中编辑


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


# SQL中的null值
1. 包含null值的表达式都为null
2. null永远！=null

# SQL优化的原则
1. 尽量使用列名，不需要解析`*`


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