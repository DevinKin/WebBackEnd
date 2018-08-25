# Oracle搭建开发环境
1. 创建表空间
2. 创建用户
3. 授权
4. 切换用户(使用该用户来建表)

## 创建表空间
```oracle
CREATE TABLESPACE devinkin

autoextend on
next 10M
```

## 创建用户
```oracle
CREATE USER king
identified by 111111
default tablespace devinkin
```


## 授权
```oracle
GRANT DBA TO king
GRANT CONNECT , RESOURCE TO king
```


