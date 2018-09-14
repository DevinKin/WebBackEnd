# http协议
1. 超文本传输协议
2. 浏览器往服务器发送请求
3. 服务器往浏览器发送响应

## 请求(request)
### 组成部分
1. 请求行：请求信息的第一行
2. 请求头：请求信息的第二行到空行结束
3. 请求体：空行一下的内容

### 请求行
1. 格式：请求方式 访问的资源 协议/版本
2. 如`GET / HTTP/1.1`
3. 请求方式GET和POST的区别
    1. get会把参数放在url后面，post不会
    2. get参数大小有限制，post请求没有限制
    3. get请求没有请求体，post请求有请求体，请求参数放在请求体中

### 请求头
1. 格式：key-value(value可以是多值)
```http request
Host: localhost:8080
Connection: keep-alive
Cache-Control: max-age=0
User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36
Upgrade-Insecure-Requests: 1
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
Accept-Encoding: gzip, deflate, br
Accept-Language: zh-CN,zh;q=0.9
Cookie: Idea-6422c823=814b299b-5317-4c98-b4f1-df67f782bbbf; JSESSIONID=7C0B9784A796BA9171166FE58A3F67FF
```
常见的请求头

|请求头|说明|
|-----|---|
|Accept: text/html, image/*|支持的(MIME)数据类型|
|Accept-CharSet: ISO-8859-1|字符集|
|Accept-ENcoding: gzip|支持压缩|
|Accept-Language:zh-cn|语言环境|
|Host: www.itcast.cn:80|访问主机|
|If-Modified-Sine: Tue, 11, Jul 2000 18:23:51 GMT|缓存文件的最后修改时间|
|Referer: http://www.itcast.com/index.jsp|来自哪个页面、防盗链|
|User-Agent: Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0)|用户代理|
|Cookie|浏览器缓存|
|Connection: close/Keep-Alive|链接状态|
|Date: Tue, 11 Jul 2000 18:23:51 GMT|时间|

## 响应(response)
### 组成部分
1. 响应行：响应信息的第一行
2. 响应头：响应信息的第二行到空行结束
3. 响应体：空行一下的内容

### 响应行
1. 格式：协议/版本 状态码 状态码说明
2. 如：HTTP/1.1 200 OK
3. 状态码说明

|状态码|说明|
|----|----|
|200|正常响应成功|
|302|重定向|
|304|读缓存，检查If-Modified-Sine，相同，则请求本地缓存的页面即可(Not Modified)|
|404|用户访问的资源不存在，用户操作错误|
|500|服务器内部异常|

### 响应头
1. key/value值(value可以是多值)
2. 常见的头信息

|响应头|说明|
|----|---|
|Location: http://www.it315.org/index.jsp|跳转方向，与302一起使用的|
|Server: apache tomcat|服务器型号|
|Content-Encoding: gzip|数据压缩类型|
|Content-Length|数据压缩后长度为多少|
|Content-Language: zh-cn|语言环境|
|Content-Type: text/html; charset=GB2312;|数据(MIME)类型|
|LastModified: Tue, 11 Jul 2000 18:23:51 GMT|最后修改事件
|Refresh: 1;url=http://www.it315.org|定时刷新|
|Content-Disposition: attachment; filename=aaa.zip|文件下载|
|Set-Cookie: SS=Q0=5Lb_nQ; path=/search||
|Expires: -1|缓存|
|Cache-Control: no-cache|缓存|
|Pragma: no-cache|缓存|
|Connection: close/Keep-Alive|连接|


### 响应体
1. 页面上展示的内容

# Servlet
1. 动态的web开发技术，本质就是一个类，运行在服务器端的一个java小程序
2. 处理业务逻辑，生成动态的web内容

## Servlet的体系结构
Servlet(接口)->GenericServlet(抽象类)->HttpServlet(抽象类)->自定义Servlet

## servlet常用方法
### servlet生命周期方法
1. `void init(ServletConfig config)`：初始化
2. `void service(ServletRequest request, ServletResponse response)`：服务，处理业务逻辑
3. `void destroy()`：销毁

### 其他方法
1. `ServletConfig getServletConfig()`：获取当前Servlet配置对象
2. `String getServletInfo()`：获取servlet的信息

## GenericServlet常用方法
1. 除了service方法没有显示，其他都实现了
2. 空参的init()，若我们向对servlet进行初始化操作，重写这个init()即可

## HttpServlet常用方法
1. service做了实现，把参数强转，调用了重载的service方法
2. 重载的service方法获取请求的方式，跟去请求方式的不同调用相应doXXX()方法

## Servlet生命周期
### 初始化
1. `void init(ServletConfig config)`：初始化
     1. 初始化方法
     2. 执行者：服务器
     3. 执行次数：1次
     4. 执行时间：默认第一次访问的时候

### 服务
1. `void service(ServletRequest request, ServletResponse response)`：服务，处理业务逻辑
     1. 服务
     2. 执行者：服务器
     3. 执行次数：请求一次执行一次
     4. 执行时机：请求到达的时候

### 销毁
1. `void destroy()`：销毁
     2. 执行者：服务器
     3. 执行次数：1次
     4. 执行时间：当Servlet被移除或者服务器正常关闭的时候

## ServletContext
1. ServletContext(上下文，全局管理者)
2. 一个项目的引用，代表了当前项目
3. 当项目启动的时候，服务器为每一个web项目创建一个ServletContext对象。
4. 当项目被移除的时候或服务器关闭的时候，ServletContext被销毁。
### 作用
1. 获取全局的初始化参数
2. 共享资源(xxxAttribute)
3. 获取文件资源
4. 其他操作

### 常用方法
1. `setAttribute(String key, Object value)`
2. `Object getAttribute(String key)`
3. `removeAttribute(String key)`
4. `String getInitParameter(String key)`：通过名称获取指定的全局初始化参数值
5. `Enumeration getInitParameterNames()`：获取所有的全局初始化参数名称
    1. 在根标签下一个context-param子标签，用来存放全局初始化参数
6. `String getRealPath(String path)`：获取文件部署到tomcat上的真实路径
7. `InputStream getResourceAsStream(String path)`：以流的形式返回一个文件
8. `String getMimeType(String path)`：返回文件的MIME类型(大类型/小类型)
### 作用
### 获取ServletContext
1. `getServletConfig.getServletContext()`
2. `getServletContext()`


## ServletConfig
1. servlet配置对象
2. ServletConfig是由服务器创建的，在创建servlet的同时也创建了它，通过servlet的init(ServletConfig config)方法将config对象传递给servlet，由servlet的getServletConfig方法获取
1. 获取当前Servlet的名称
2. 获取servlet初始化参数
3. 获取ServletContext(上下文对象)

### ServletConfig常用方法
1. `String getServletName()`：获取当前Servlet的名称(web.xml配置的servletname)
2. `String getInitParameter(String key)`：通过名称获取指定的初始化参数值
    1. 初始化参数是放在web.xml文件的servlet标签下的子标签init-param
3. `Enumeration getInitParameterNames()`：获取所有的初始化参数名称
4. `ServletContext getServletContext()`：获取Servlet上下文对象

## servlet是单实例多线程的类
1. 默认第一次访问的时候，服务器创建servlet，并调用init实现初始化操作，并调用一次service方法
2. 每当请求到达的时候，服务器创建一个线程，调用service方法执行自己的业务逻辑
3. 当servlet被移除或者服务器正常关闭的时候，服务器调用servlet#destroy()方法实现销毁

# servlet配置标签
## load-on-startup
1. 作用：用来修改servlet的初始化时机
2. 取值：正整数，值越大优先级越低


## 编写servlet的步骤
### 编写一个类
    1. 要继承HttpServlet
    2. 重写方法doGet或doPost方法
### 编写配置文件(WEB-INF/web.xml)
1. 注册servlet
2. 绑定路径
```xml
    <servlet>
        <servlet-name>AServlet</servlet-name>
        <servlet-class>cn.devinkin.test.servlet.AServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>AServlet</servlet-name>
        <url-pattern>/AServlet</url-pattern>
    </servlet-mapping>
```
### 访问servlet
1. http://主机/端口/项目名/路径

# 域对象
1. ServletContext

# 获取文件的路径
1. 通过类加载器获取文件：`类.class.getClassLoader().getResource("login.htm").getPath()`
    1. 通过类加载器获取的目录是classes下的目录

# 案例1-完成登录案例
## 需求
1. 在页面上输入用户名和密码，提交到服务器上，服务器拿用户名和密码去数据库查找此用户
    1. 若有用户，提示登录成功
    2. 若无此用户，则提示用户不存在
    3. 若密码错误，则提示密码错误
## 技术分析
1. 表单
2. servlet
3. 请求(request)
4. 响应(response)

## 步骤分析
1. 数据库和表
2. 复制页面
    1. 修改login.html
        1. 给用户和密码添加name属性
        2. 修改表单的action属性
        3. 添加method属性`method="post"`
3. 创建servlet
4. 导入jar包
    1. 驱动
    2. dbutils
    3. c3p0
5. 导入工具类和配置文件
    1. datasourceUtils
    2. c3p0-config.xml
6. 创建servlet(LoginServlet: 路径 /login)
    1. 接收用户名和密码
    2. 调用service层(UserService)完成登录操作
    3. 提示信息
7. UserService
    1. login(username, password)调用UserDao
   
8. UserDao
    1. 通过用户名和密码查询数据库
    
    
    
# 案例2-登录失败，提示"用户名密码不匹配"，3秒后跳转到登录页面
## 技术分析
1. 定时刷新
## 常见的响应头-refresh
1. 响应头格式：`refresh:秒数;url=跳转的路径`
2. 设置响应头：
    1. `response.setHeader(String key, String value)`：设置字符串形式的响应头
    2. `response.addHeader(String key, String value)`：追加响应头，若之前设置过这个响应头，则往后追加响应头，若没设置，则设置该响应头
3. 设置定时刷新：`response.setHeader("refresh", "3;url=/login.htm)`

## 步骤分析
1. 登录失败之后，修改业务逻辑，打印之后添加一个头信息即可

# 案例3-统计登录成功的总人数
## 需求
1. 在一个用户登录成功之后，获取之前登录成功的总人次，将次数+1，在访问另一个servlet的时候，显示登录成功的总人次数

## 技术分析
1. ServletContext#getServletContext()

## 步骤分析
1. 在项目启动的时候，初始化登录次数
    1. 在loginServlet的init()方法中获取全局管理者，将值初始化为0，放入ServletContext中
2. 登录成功之后，在LoginServlet中获取全局管理者，获取登录成功的总次数
3. 将次数+1，然后将值设置回去
4. 当访问showServlet的时候，获取全局管理者，获取登录成功的总次数，然后在页面中打印出来