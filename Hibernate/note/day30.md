# 案例一：完成CRM的联系人的保存操作

## 需求分析
1. 因为客户和联系人是一对多的关系，在有客户的情况下，完成联系人的添加保存操作

## 技术分析之Hibernate的关联关系映射之一对多映射
### JavaWEB中一对多的涉及及其建表原则
1. 导入SQL的建表语句
    - 创建今天的数据库：`create database hibernate_day03;`
    - 在sql中找到客户和联系人的SQL脚本

### 编写客户和联系人的JavaBean程序(注意一对多的编写规则)
- 客户的JavaBean如下：
```java
package com.devinkin.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 客户的JavaBean
 * @author king
 */
public class Customer {
    /**
     *
     `cust_id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '客户编号(主键)',
     `cust_name` varchar(32) NOT NULL COMMENT '客户名称(公司名称)',
     `cust_user_id` bigint(32) DEFAULT NULL COMMENT '负责人id',
     `cust_create_id` bigint(32) DEFAULT NULL COMMENT '创建人id',
     `cust_source` varchar(32) DEFAULT NULL COMMENT '客户信息来源',
     `cust_industry` varchar(32) DEFAULT NULL COMMENT '客户所属行业',
     `cust_level` varchar(32) DEFAULT NULL COMMENT '客户级别',
     `cust_linkman` varchar(64) DEFAULT NULL COMMENT '联系人',
     `cust_phone` varchar(64) DEFAULT NULL COMMENT '固定电话',
     `cust_mobile` varchar(16) DEFAULT NULL COMMENT '移动电话',
     */
    //以后使用包装类，默认值是null
    private Long cust_id;
    private String cust_name;
    private Long cust_user_id;
    private Long cust_create_id;
    private String cust_source;
    private String cust_industry;
    private String cust_level;
    private String cust_linkman;
    private String cust_phone;
    private String cust_mobile;

    // Hibernate框架默认胡集合椒set集合，集合必须自己手动初始化
    private Set<Linkman> linkmans = new HashSet<>();

    public Set<Linkman> getLinkmans() {
        return linkmans;
    }

    public void setLinkmans(Set<Linkman> linkmans) {
        this.linkmans = linkmans;
    }

    //    private String info;
//
//    public String getInfo() {
//        return info;
//    }
//
//    public void setInfo(String info) {
//        this.info = info;
//    }

    public Long getCust_id() {
        return cust_id;
    }

    public void setCust_id(Long cust_id) {
        this.cust_id = cust_id;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public Long getCust_user_id() {
        return cust_user_id;
    }

    public void setCust_user_id(Long cust_user_id) {
        this.cust_user_id = cust_user_id;
    }

    public Long getCust_create_id() {
        return cust_create_id;
    }

    public void setCust_create_id(Long cust_create_id) {
        this.cust_create_id = cust_create_id;
    }

    public String getCust_source() {
        return cust_source;
    }

    public void setCust_source(String cust_source) {
        this.cust_source = cust_source;
    }

    public String getCust_industry() {
        return cust_industry;
    }

    public void setCust_industry(String cust_industry) {
        this.cust_industry = cust_industry;
    }

    public String getCust_level() {
        return cust_level;
    }

    public void setCust_level(String cust_level) {
        this.cust_level = cust_level;
    }

    public String getCust_linkman() {
        return cust_linkman;
    }

    public void setCust_linkman(String cust_linkman) {
        this.cust_linkman = cust_linkman;
    }

    public String getCust_phone() {
        return cust_phone;
    }

    public void setCust_phone(String cust_phone) {
        this.cust_phone = cust_phone;
    }

    public String getCust_mobile() {
        return cust_mobile;
    }

    public void setCust_mobile(String cust_mobile) {
        this.cust_mobile = cust_mobile;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cust_id=" + cust_id +
                ", cust_name='" + cust_name + '\'' +
                ", cust_user_id=" + cust_user_id +
                ", cust_create_id=" + cust_create_id +
                ", cust_source='" + cust_source + '\'' +
                ", cust_industry='" + cust_industry + '\'' +
                ", cust_level='" + cust_level + '\'' +
                ", cust_linkman='" + cust_linkman + '\'' +
                ", cust_phone='" + cust_phone + '\'' +
                ", cust_mobile='" + cust_mobile + '\'' +
                '}';
    }
}
```

- 联系人的JavaBean如下：
```java
package com.devinkin.domain;

public class Linkman {
    /**
     * `lkm_id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '联系人编号(主键)',
  `lkm_name` varchar(16) DEFAULT NULL COMMENT '联系人姓名',
  `lkm_cust_id` bigint(32) NOT NULL COMMENT '客户id',
  `lkm_gender` char(1) DEFAULT NULL COMMENT '联系人性别',
  `lkm_phone` varchar(16) DEFAULT NULL COMMENT '联系人办公电话',
  `lkm_mobile` varchar(16) DEFAULT NULL COMMENT '联系人手机',
  `lkm_email` varchar(64) DEFAULT NULL COMMENT '联系人邮箱',
  `lkm_qq` varchar(16) DEFAULT NULL COMMENT '联系人qq',
  `lkm_position` varchar(16) DEFAULT NULL COMMENT '联系人职位',
  `lkm_memo` varchar(512) DEFAULT NULL COMMENT '联系人备注',
     *
     */

    private Long lkm_id;

    private String lkm_name;
    private String lkm_gender;
    private String lkm_phone;
    private String lkm_mobile;
    private String lkm_email;
    private String lkm_qq;
    private String lkm_position;
    private String lkm_memo;

    // 编写一个对象，不需要自己去new
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getLkm_id() {
        return lkm_id;
    }

    public void setLkm_id(Long lkm_id) {
        this.lkm_id = lkm_id;
    }

    public String getLkm_name() {
        return lkm_name;
    }

    public void setLkm_name(String lkm_name) {
        this.lkm_name = lkm_name;
    }

    public String getLkm_gender() {
        return lkm_gender;
    }

    public void setLkm_gender(String lkm_gender) {
        this.lkm_gender = lkm_gender;
    }

    public String getLkm_phone() {
        return lkm_phone;
    }

    public void setLkm_phone(String lkm_phone) {
        this.lkm_phone = lkm_phone;
    }

    public String getLkm_mobile() {
        return lkm_mobile;
    }

    public void setLkm_mobile(String lkm_mobile) {
        this.lkm_mobile = lkm_mobile;
    }

    public String getLkm_email() {
        return lkm_email;
    }

    public void setLkm_email(String lkm_email) {
        this.lkm_email = lkm_email;
    }

    public String getLkm_qq() {
        return lkm_qq;
    }

    public void setLkm_qq(String lkm_qq) {
        this.lkm_qq = lkm_qq;
    }

    public String getLkm_position() {
        return lkm_position;
    }

    public void setLkm_position(String lkm_position) {
        this.lkm_position = lkm_position;
    }

    public String getLkm_memo() {
        return lkm_memo;
    }

    public void setLkm_memo(String lkm_memo) {
        this.lkm_memo = lkm_memo;
    }
}

```

### 编写客户和联系人的配置文件(注意一对多的编写规则)
- 客户的映射配置文件如下：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">

<hibernate-mapping>
    <!-- 配置类和表结构的映射 -->
    <class name="com.devinkin.domain.Customer" table="cst_customer">
        <!-- 配置id，主键 -->
        <!-- 见到name属性，JavaBean的属性
             见到column的属性，是表结构的字段 -->
        <id name="cust_id" column="cust_id">
            <!-- 主键的生成策略 -->
            <generator class="native"/>
        </id>

        <!-- 配置其他的属性 -->
        <property name="cust_name" column="cust_name"/>
        <property name="cust_user_id" column="cust_user_id"/>
        <property name="cust_create_id" column="cust_create_id"/>
        <property name="cust_source" column="cust_source"/>
        <property name="cust_industry" column="cust_industry"/>
        <property name="cust_level" column="cust_level"/>
        <property name="cust_linkman" column="cust_linkman"/>
        <property name="cust_phone" column="cust_phone"/>
        <property name="cust_mobile" column="cust_mobile"/>
        <!--<property name="info" column="info"/>-->

        <!-- 配置一方 -->
        <!--
            set 标签name属性：表示集合的名称
        -->
        <!--<set name="linkmans" cascade="save-update">-->
        <!--<set name="linkmans" >-->
        <set name="linkmans" cascade="save-update">
            <!-- 需要出现的子标签 -->
            <!-- 外键的字段 -->
            <key column="lkm_cust_id"></key>
            <one-to-many class="com.devinkin.domain.Linkman"/>
        </set>
    </class>
</hibernate-mapping>
```
- 联系人的映射配置文件如下：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">

<hibernate-mapping>
    <!-- 配置类和表结构的映射 -->
    <class name="com.devinkin.domain.Linkman" table="cst_linkman">
        <!-- 配置id，主键 -->
        <!-- 见到name属性，JavaBean的属性
             见到column的属性，是表结构的字段 -->
        <id name="lkm_id" column="lkm_id">
            <!-- 主键的生成策略 -->
            <generator class="native"/>
        </id>

        <!-- 配置其他的属性 -->
        <property name="lkm_name" column="lkm_name"/>
        <property name="lkm_gender" column="lkm_gender"/>
        <property name="lkm_phone" column="lkm_phone"/>
        <property name="lkm_mobile" column="lkm_mobile"/>
        <property name="lkm_email" column="lkm_email"/>
        <property name="lkm_qq" column="lkm_qq"/>
        <property name="lkm_position" column="lkm_position"/>
        <property name="lkm_memo" column="lkm_memo"/>

        <!-- 先配置多方
            name：当前JavaBean中的属性
            class：当前类的全路径
            column：外键的字段
        -->
        <many-to-one name="customer" class="com.devinkin.domain.Customer" column="lkm_cust_id" cascade="save-update"/>
    </class>
</hibernate-mapping>

```

## 技术分析之级联保存
1. 测试：如果现在代码只插入其中的一方的数据
    - 如果只保存其中的一方数据，那么程序会抛出异常。
    - 如果想完成只保存一方数据，并把相关联的数据保存到数据库中，那么需要配置级联！！
    - 级联保存是方向性的
 
2. 级联保存效果
    - 级联保存：保存一方同时可以把级联的对象也保存到数据库中！！
    - 映射配置文件使用`cascade=save-upudate`属性

## 技术分析之级联删除
1. 含有外键的删除客户功能，那么SQL语句会报错
    - 例如：`delete from customers where cid = 1;`

2. 如果使用Hibernate框架之间删除客户的时候，测试发现是可以删除的

3. 上述的删除是普通的删除，那么也可以使用级联删除，注意：级联删除也是有方向性的！！
    - `<many-to-one cascade="delete" />`
    
    
## 技术分析之级联的取值(cascade的取值)和孤儿删除
1. 需要掌握的取值如下
    - `none`：不用级联
    - `save-update`：级联保存或更新
    - `delete`：级联删除
    - `delete-orphan`：孤儿删除(注意：只能应用在一对多关系)
    - `all`：除了`delete-orphan`的所有情况。(包含save-update、delete)
    - `all-delete-orphan`：包含了`delete-orphan`的所有情况。(包括save-update、delete、delete-orphan)
   
2. 孤儿删除(孤子删除)，只有在一对多的环境才有孤儿删除
    - 在一对多的关系中，可以将一方认为是父方，将多方认为是子方，孤儿删除，在解除了父子关系的时候，将子方记录就直接删除
    - `<many-to-one cascade="delete-orphan"/>`
    
    
## 技术分析之让某一方放弃外键的维护，为多对多做准备
1. 先测试双方都维护外键的时候，会产生多于的SQL语句
    - 向修改客户和联系人的关系，进行双向关联，双方都会维护外键，会产生多余的SQL语句。
    - 产生的原因：session的一级缓存中的快照机制，会让双方都更新数据库，产生了多余的SQL语句。
    
2. 如果不想产生多余的SQL语句，那么需要一方来放弃外键的维护！
    - 在`<set>`标签上配一个`inverse="true"`。true为放弃，false为不放弃，默认值是false。
    - `<inverse="true">`
    
## 技术分析之cascade和inverse的区别
1. cascade用来级联操作(保存、修改和删除)
2. inverse用来维护外键的，只能在一方放弃外键维护

## 技术分析之多对多的建表原则
1. 两个数据表和一个映射表
2. Hibernate框架，只要编写2个JavaBean，编写两个映射的配置文件，中间表会自动生成的。

## 技术分析之多对多JavaBean的编写
1. 多对多进行双向关联的时候：必须有一方放弃外键维护的权利

- 用户的JavaBean
```java
package com.devinkin.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 系统用户
 * @author king
 */
public class User {
    private Long uid;
    private String username;
    private String password;

    // 编写都是集合
    private Set<Role> roles = new HashSet<Role>();

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

```

- 角色的JavaBean
```java
package com.devinkin.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 系统角色
 * @author king
 */
public class Role {
    private Long rid;
    private String name;

    private Set<User> users = new HashSet<User>();

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}

```

## 技术分析之多对多映射配置文件的编写
- 用户的映射配置文件如下
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">

<hibernate-mapping>
    <!-- 配置类和表结构的映射 -->
    <class name="com.devinkin.domain.User" table="sys_user">
        <!-- 配置id，主键 -->
        <!-- 见到name属性，JavaBean的属性
             见到column的属性，是表结构的字段 -->
        <id name="uid" column="uid">
            <!-- 主键的生成策略 -->
            <generator class="native"/>
        </id>

        <!-- 配置其他的属性 -->
        <property name="username" column="username"/>
        <property name="password" column="password"/>

        <!-- 配置多对多
            name 集合的名称
            table 中间表的名称
        -->
        <set name="roles" table="sys_user_role">
            <!-- 当前对象在中间表的外键的名称 -->
            <key column="uid"></key>
            <!--
                class 集合中存入对象，对象的全路径
                column 集合中对象在中间表的外键的名称
            -->
            <many-to-many class="com.devinkin.domain.Role" column="rid"/>
        </set>

    </class>
</hibernate-mapping>

```

- 角色的映射配置文件如下
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">

<hibernate-mapping>
    <!-- 配置类和表结构的映射 -->
    <class name="com.devinkin.domain.Role" table="sys_role">
        <!-- 配置id，主键 -->
        <!-- 见到name属性，JavaBean的属性
             见到column的属性，是表结构的字段 -->
        <id name="rid" column="rid">
            <!-- 主键的生成策略 -->
            <generator class="native"/>
        </id>

        <!-- 配置其他的属性 -->
        <property name="name" column="name"/>

        <!-- 配置多对多
            name 集合的名称
            table 中间表的名称
            多对多的一方必须要有一方放弃外键的维护
        -->
        <set name="users" table="sys_user_role" inverse="true">
            <key column="rid"></key>
            <many-to-many class="com.devinkin.domain.User" column="uid"/>
        </set>
    </class>
</hibernate-mapping>
```

## 技术分析之多对多的级联保存
1. 级联保存：`<set cascade="save-update">`

## 级联删除(在多对多中很少使用的)
1. 级联删除：`<set cascade="delete">`
