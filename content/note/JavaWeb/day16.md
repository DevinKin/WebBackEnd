# listener(监听器)
1. listener全部是接口

## 作用
1. 监听web中的域对象(ServletContext、ServletRequest、HttpSession)

## 监听内容
1. 监听三个对象的创建和销毁
2. 监听三个对象属性的变化
3. 监听session中javabean的状态

## 监听三个对象的创建和销毁
### ServletContextListener
1. 创建：服务器启动的时候，或者项目运行时候会为每一个项目都创建一个ServletContext
2. 销毁：服务器关闭的时候，或者项目被移除的时候销毁ServletContext
3. 配置方式
```xml
    <!--在web.xml中-->
    <listener>
        <listener-class>cn.devinkin.listener.web.listener.MyServletContextListener</listener-class>
    </listener>
```
或者添加`@WebListener`标注

4. ServletContextListener用于读取配置文件

### ServletRequestListener
1. 创建：请求到达的时候创建request对象
2. 销毁：响应生成的时候销毁request对象
### HttpSessionListener
1. 创建：Java中第一次调用request.getSession()时候创建Session对象
2. 销毁：
    1. 设置超时
    2. 手动销毁session
    3. 服务器非正常关闭

## 监听三个对象属性的变化(添加、替换、删除)
### ServletContextAttributeListener
### ServletRequestAttributeListener
### HttpSessionAttributeListener

## 监听session中javabean的状态
1. 这两个接口需要javabean去实现，是让javabean感知到自己的状态
### HttpSessionBindingListener(绑定和解绑)
1. 检测javabean是否添加到session中或从session中移除

### HttpSessionActivationListener(钝化和活化)
1. 钝化：javabean从session中序列化到磁盘上
2. 活化：javabean从磁盘上加载到(session)内存中
3. 实现钝化活化，javabean必须实现Serializable接口
4. 可以通过配置文件修改javabean什么时候钝化，什么时候活化
```xml
<!--context.xml进行全局配置-->
<!--每个项目下/META-INF目录下创建一个context.xml-->
<!--内容如下-->
<Context>
    <!--
        maxIdleSwap：1分钟，如果session不适用就会序列化到硬盘
        directory：devinkin，序列化到硬盘的文件存放位置
    -->
    <Manager className="org.apache.catalina.session.PersistentManager"
             maxIdleSwap="1">
        <Store className="org.apache.catalina.session.FileStore"
            directory="devinkin"/>
    </Manager>
</Context>
```

## listener使用步骤
1. 编写一个类，实现接口
2. 重写方法
3. 编写配置文件(大部分都要做)

# filter(过滤器)
1. 过滤请求和响应
2. Filter是一个接口

## 作用
1. 实现自动登录
2. 统一编码
3. 过滤关键字

## 编写filter步骤
1. 编写一个类
    1. 实现filter接口
    2. 重写方法
2. 编写配置文件
    1. 注册filter
    2. 绑定路径
3. 测试

## filter配置方式
1. 使用`@WebFilter`标注：`@WebFilter(filterName="xxx",urlPatterns="/xxx")`
2. 在web.xml中配置
```xml
    <filter>
        <filter-name>HelloFilter</filter-name>
        <filter-class>cn.devinkin.filter.web.filter.HelloFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>HelloFilter</filter-name>
        <url-pattern>/Demo1Servlet</url-pattern>
    </filter-mapping>
```

### filter-mapping子标签
1. servlet-name：匹配哪个servlet，该标签的值写的是servlet标签中servlet-name的值
    1. 不要在同一个filter中重复匹配多个servlet
2. dispatcher：匹配哪种请求(默认是REQUEST)
    1. `REQUEST`：从浏览器发送过来的请求
    2. `FORWARD`：转发过来的请求
    3. `ERROR`：因服务器错误而发送过来的请求
    4. `INCLUDE`：包含过来的请求
    
### error-page
1. 用于显示错误页面
2. 子标签
    1. error-code
    2. location

## Filter接口的方法
1. `void init(FilterConfig)`：初始化操作
2. `void doFilter(ServletRequest,ServletResponse,FilterChain)`：处理业务逻辑
3. `void destroy()`：销毁操作

## Filter生命周期
1. filter是単实例多线程的
2. filter在服务器启动的时候创建，调用init方法
3. 请求来的时候，创建一个线程，根据路径调用doFilter方法，执行业务逻辑
4. 当filter被移除的时候或者服务器正常关闭的时候，调用destroy方法，执行销毁操作

## FilterChain(过滤链)
1. 通过chain的doFilter方法，可以将请求放行到下一个过滤器，直到最后一个过滤器放行才可以访问到servlet或jsp
2. doFilter()放行方法

### url-patterns配置
1. 完全匹配：必须以`/`开始
2. 目录匹配：必须以`/`开始，以`*`结束
3. 后缀名匹配：以`*`开始，以后缀名结束

### 多个filter执行顺序
1. 一个资源有可能被多个过滤器匹配成功，多个过滤器的执行顺序是按照`web.xml`中`filter-mapping`的执行顺序执行的

## FilterConfig
1. 过滤器的配置对象

### 作用
1. 获取Servlet上下文(ServletContext)
2. 获取当前filter的名称
3. 获取当前filter的初始化参数
4. 获取当前filter的初始化参数名称数组
### 常用方法
1. ServletContext getServletContext()
2. String getFilterName()
3. Enumeration getInitParameter(String name)
4. ServletContext getInitParameterNames() 

# 案例1-自动登录
## 需求
1. 登录的时候，勾选自动登录，登录成功之后，关闭浏览器，下一次访问网站的时候完成登录操作(自动登录)
## 技术分析
1. filter
2. cookie

## 步骤分析
1. 数据库和表
2. jar包，工具类，配置文件
3. 新建一个登录页面，提交表单
4. 表单提交到LoginServlet中
5. LoginServlet 
    1. 接收用户名和密码
    2. 调用service完成登录操作，返回值为User
    3. 判断User是否为空
        1. 若不为空，将User放入session中
            1. 判断是否勾选了自动登录
                1. 若勾选了，需要将用户名和密码写回浏览器
6. 下次访问网站的时候
    1. 过滤器拦截任意请求，判断有没有指定cookie
        1. 有cookie，获取用户名和密码，调用service完成登录操作，返回User，将User放入session中
7. 当我们换用账号登录的时候发现登录不了
    1. 自动登录只需要登录一次：当session中没有用户的时候
    2. 访问有些资源是不需要登录的：和登录还有注册相关的资源
    3. 修改filter的逻辑
        1. 首先判断session中是否有user
            1. 如果没有user
                1. 若没有，并且访问的路径不是和登录注册相关的时候，获取指定的cookie


# 案例2-统一字符编码
## 需求
1. 以前我们开发的时候若有参数，第一步都是设置编码，才不会出现乱码，通过过滤器设置，到servlet或者jsp上的时候就已经没有乱码问题了

## 技术分析
1. filter配置路径`/*`，放在第一个过滤器的位置
2. 加强request.getParameter()方法

## 方法加强方式
1. 继承(获取构造器)
2. 装饰者模式(静态代理)
3. 动态代理

## 装饰者书写步骤
1. 要求装饰者和被装饰者实现同一个接口或继承同一个类
2. 装饰者中要有被装饰者的引用
3. 对需要加强的方法进行加强
4. 对不需要加强的方法调用原来的方法即可

## 加强request.getParameter(String key)
1. 首先请求的方式不同，处理的方式也不同
    1. 获取请求的方法
    2. 若是get请求：`new String(value.getBytes("iso8859-1"),"utf-8")`
    3. 若是post请求：`request.setCharacterEncoding("utf-8")`
2. 将包装过的request对象(EncodedRequest)调包