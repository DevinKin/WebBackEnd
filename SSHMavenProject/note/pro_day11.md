# 跨部门跨人员的的权限
1. 在部门表和员工表中添加中间表USER_DEPT_P,用于部门与员工多对多的关系
    -  字段说明
        - uid为用户id
        - id可以是部门id,也可以是用户id
        - type表示的是员工,或者部门
2. 查询代码如下,使用sql语句查询
```java
if (user.getUserInfo().getDegree() == 1) {
    // 说明是副经理,sql语句中的id可以是用户id,也可以是部门id
    String sql = "select * from Contract_c where" +
     "create_by in (select id from USER_DEPT_P" +
      "where type='员工' and uid = " + user.getId() + ")" +
       "or" +
        "create_dept in (select id from USER_DEPT_P" +
         "where type='部门' and uid = " + user.getId() + ")";
}
```

# 使用JFreeChart实现图表制作

## 导入jar包
1. Maven工程的坐标
```xml
    <!-- jfreechart -->
    <dependency>
            <groupId>jfree</groupId>
            <artifactId>jcommon</artifactId>
            <version>1.0.16</version>
    </dependency>
    
    <dependency>
            <groupId>jfree</groupId>
            <artifactId>jfreechart</artifactId>
            <version>1.0.13</version>
    </dependency>
```
2. 没有网络的情况下把已有的jar包安装到本地仓库中
```
mvn install:install-file -DgroupId=jfree -DaritifactId=jcommon -Dversion=1.0.16 -Dpackaging=jar -Dfile jcommon-1.0.16.jar
mvn install:install-file -DgroupId=jfree -DaritifactId=jfreechart -Dversion=1.0.13 -Dpackaging=jar -Dfile jfreechart-1.0.13.jar
```


## amChart+Flash动画实现图形报表
1. 早期amChar是使用xml作为数据源的,新版本使用json作为数据源

