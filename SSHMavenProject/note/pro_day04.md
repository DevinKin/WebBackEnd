# Shrio
## 什么是Shrio
1. 它是一个安全框架，用于解决系统的认证和授权问题，同事提供了会话管理，数据加密机制

2. Shrio可以帮助我们完成：认证、授权、加密、会话管理、与WEB集成、缓存等


## Shrio内部组织结构
1. Authentication：身份认证/登录，验证用户是不是拥有相应的身份

2. Authorization：授权，即权限验证，验证某个已认证的用户是否拥有某个权限；即判断用户是否能做事情，常见如：验证摸个用户是否拥有某个角色。或者细粒度的验证某个用户对某个资源是否具有某个权限；

3. SessionManager：会话管理，即用户登录后就是一次会话，在没有退出之前，它的所有信息都在会话中；会话可以是JavaSE环境的，也可以是如Web环境的。

4. Cryptography：加密，保护数据的安全性，如密码加密存储到数据库，而不是明文加密

5. Web Support：Web支持，可以非常容易的集成到Web环境；

6. Caching：缓存，比如用户登录后，其用户信息、拥有的角色/权限不必每次去查，这样可以提高效率

7. Concurrenct：shiro支持多线程应用的并发验证，即如在一个线程中开启另一个线程，能把权限自动传播过去

8. Testing：提供测试支持

9. Run As：允许一个用户假装成另一个用户(如果他们允许)的身份进行访问；

10. Remember Me：记住我，这是非常常见的功能，即一次登录后，下次再来的话，不用登录了。


## Shrio框架的使用
### 客户端程序员关注的部分
1. 如何获得subject
2. 如何定义一个符合规定的Realm域(密码比较器的定义也是程序员要做的)


### Subject
1. Subject：主体，代表了当前“用户”，这个用户不一定是一个具体的人，与当前应用交互的任何东西都是Subject，如网络爬虫，机器人等；即一个抽象概念；所有Subject都绑定到SecurityManager，与Subject的所有交互都会委托给SecurityManager；可以把Subject认为是一个门面；SecurityManager才是实际的执行者；
2. SecurityManager：安全管理器；即所有与安全有关的操作都会与SecurityManager交互；且它管理着所有Subject；可以看出它是Shiro的核心，它负责与后边介绍的其他组件进行交互，如果学习过SpringMVC，你可以把它看成DispatcherServlet前端控制器；
3. Realm：域，Shiro从Realm获取安全数据（如用户、角色、权限），就是说SecurityManager要验证用户身份，那么它需要从Realm获取相应的用户进行比较以确定用户身份是否合法；也需要从Realm得到用户相应的角色/权限进行验证用户是否能进行操作；可以把Realm看成DataSource，即安全数据源。

### Shrio的过滤器
1. 虽然Shrio提供了10个过滤器，但使用时只需要配置一个核心过滤器就可以了，相当于配置了10个过滤器
```xml

```


### Shrio的使用步骤
1. 导入jar包
2. 过滤器的配置
3. 在spring applicationContext.xml中记载shiro配置文件,放在事务管理器之前配置
4. 添加专门配置shiro的配置文件
5. 添加ehcache支持ehcache-shiro.xml
#### 导入jar包
```xml
<dependencies>
    <dependency>
        <gorupId>org.apache.shiro</gorupId>
        <artifactId>shiro-all</artifactId>
        <version>1.2.3</version>
    </dependency>
</dependencies>
```

#### 过滤器的配置
1. 要放到struts2过滤器之前
```xml
<!-- Shiro Security filter filter-name这个名字的值将来还会在Spring中用到 -->
<!-- 注意：shiro的filter必须在struts2的filter之前，否则action无法创建 -->
    <filter>
        <filter-name>shiroFilter</filter-name>
        <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
        <init-param>
            <param-name>targetFilterLifecycle</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>shiroFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

```

#### 在spring applicationContext.xml中记载shiro配置文件,放在事务管理器之前配置
```xml
<aop:aspectj-autoproxy proxy-target-class="true" />
```

#### 添加专门配置shiro的配置文件
1. 在applicationContext.xml中导入applicationContext-shiro.xml
```xml
<import resource="spring/applicationContext-shiro.xml"/>
```

## Shrio执行流程
1. ApplicationCode -> Subject -> SecurityManager -> Realm域