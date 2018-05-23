# Jsp
1. Java Server Page，java服务器页面
## 作用
1. 将内容的生成和信息的展示相分离
2. 运行在服务器端，本质上就是一个servlet，产生的java文件和classes文件保留在tomcat的word目录下

## Jsp脚本
1. `<%...%>`：java程序片段
2. `<%=...%>`：输出表达式
3. `<!...%>`：声明成员

## Jsp的指令
### 作用
1. 声明jsp页面的一些属性和动作
2. 格式：`<%@ 指令名称 属性="值1 值2..."%>`

### jsp指令的分类
1. page：主要声明jsp页面的一些属性
2. include：静态包含
3. taglib：导入标签库
4. 注意：一个页面中可以出现多个指令，指令可以放在任意位置，一般放在jsp页面的开头

### page指令
1. 重要属性(3个)
    1. `contentType`：设置响应流的编码，及通知浏览器用什么编码，设置文件的mimetype
    2. `pageEncoding`：设置页面的编码
    3. `import`：导入所需要的jar包或类
    4. contentType和pageEncoding联系
        1. 若两者都出现的时候，各自使用各自的编码
        2. 若只出现一者，两个都是用出现的编码
        3. 若两者都不出现，使用服务器默认的编码
2. 了解的属性
    1. language：当前jsp页面里面可以嵌套的语言，目前只有java
    2. buffer：设置jsp页面的流的缓冲区的大小
    3. autoFlush：是否自动刷新
    4. extends：声明当前jsp页面继承于哪个类，必须继承的是httpServlet及其子类
    5. session：设置jsp页面是否可以使用session内置对象，默认为true
    6. isELIgnored：是否忽略EL表达式，默认为false
    7. errorPage：当前jsp页面出现异常的时候要跳转到的jsp页面
    8. isErrorPage：当前页面是否是一个错误页面
        1. 若值为true，可以使用jsp页面的一个内置对象exception

### include指令
1. 静态包含，就是将其他jsp页面或者servlet的内容包含进来，一起进行编译运行，生成一个java文件

2. 格式：`<%@ include file="path">`

### taglib指令
1. 导入标签库

2. 格式：`<%@ taglib prefix="前椎名" uri="名称空间"`

3. 若导入之后，就可以通过`<前椎名:标签>`使用

## Jsp的内置对象(9大内置对象)
1. 在jsp页面上可以直接使用的对象

### 9大内置对象
内置对象|类型|
------|---|
out|JspWriter|
request|HttpSErvletRequest|
response|HttpServletResponse|
session|HttpSession|
exception|Throwable|
page|HttpServlet|
config|ServletConfig|
application|ServletContext|
pageContext|PageContext|

## Jsp的域对象
1. application：整个项目中可以使用
2. request：一次会话
3. session：一次请求
4. pageContext：一个页面


### pageContext的作用
1. `xxxAttribute()`：域对象方法
2. `xxxAttribute(xxx, int scope)：操作其他域对象
    1. scope取值
        1. APPLICATION_SCOPE
        2. SESSION_SCOPE
        3. REQUEST_SCOPE
        4. PAGE_SCOPE
3. 获取其他的内置对象
    1. `getXxx()`
    2. `HttpRequest getRequest()`：获取request内置对象
4. `String findAttribute(String key)`：依次从pageContext，request，session，application这四个域中查找相应的属性，若查找到了，返回值并结束此查找，查找不到，返回null
   
   
    
    
    
    
## Jsp的动作标签
1. 格式`<jsp:动作标签名>`

### 常用标签
1. `<jsp:forward page="path">`：请求转发，相当于java中request.getRequestDispatcher(..).forward(..);
2. `<jsp:include>`：动态包含，就是将被包含页面或者servlet的运行结果包含到当前页面中

## el(内置)表达式
1. jsp的内置表达式语言，从jsp2.0开始
2. 用来替代`<%=...%>`

### 作用
1. 获取域中的数据
2. 执行运算
3. 获取常见的web对象
4. 调用java的方法

### 格式
1. `${el表达式}`

### 获取域中数据
1. 获取简单数据
    1. `${pageScope|requestScope|sessionScope|application.属性名}`
    2. `${属性名}`：依次从pageContext，request，session，application域中查找指定的属性，若查找到，返回值。
2. 获取复杂数据
    1. 获取数组中的数据
        1. 格式：`${域中的名称[index]}`
    2. 获取list中的数据
        1. 格式：`${域中的名称[index]}`
    3. 获取map中的数据
        1. 格式：`${域中名称.键名}`
    4. 若属性名中出现了"."|"+"|"-"等特殊符号，使用该方式`${requestScope["arr.age"]}`
    
3. javabean导航
    1. 格式:`${域中javabean名称.bean属性}`

## el的内置对象
1. el表达式中有11个内置对象
### 内置对象
|el内置对象|类型|
|---|---|
|pageScope|Map<String,Object>|
|requestScope|Map<String,Object>|
|sessionScope|Map<String,Object>|
|applicationScope|Map<String,Object>|
|param|Map<String,String>|
|paramValues|Map<String,String[]>|
|header|Map<String,String>|
|headerValues|Map<String,String[]>|
|initParam|Masp<String,String>|
|cookie|Map<String,Cookie>|
|pageContext|PageContext|

1. 除了pageContext其余对象获取的全是map集合

# jstl(JavaSErver Pages Standard Tag Library)
1. jsp标准的标签库语言
2. 属于apache组织的
3. 用来替代java脚本

## 使用步骤
1. 导入jar包(jstl.jar,standard.jar)
2. 在页面上导入标签库：`<%@ taglib prefix="" uri=""%>`

## jstl的分类
1. core：核心类库(主要掌握)
2. fmt：格式化|国际化
3. xml：与xml相关的标签库(过时)
4. sql：与sql相关的标签库(过时)
5. 函数库：很少使用


## core核心类库
1. `<c:if test="${el表达式}" scope="把结果放到哪个域" var="结果起一个变量名"></c:if>`：判断标签
2. `<c:forEach begin="" end="" var="循环变量名" varStatus="循环状态变量">`
    1. varStatus：用来记录循环的状态
        1. count：记录次数
        2. current：当前遍历的内容
3. `<c:set var="变量名" value="值" scope="把结果放到哪个域"></c:set>`：往域中设置值
4. `c:choose`
5. `c:when`
6. `c:otherwise`
7. `<c:url value=''>`

## 执行运算
1. 四则运算、关系运算、逻辑运算
2. +：只能进行加运算，字符串形式的数字可以进行加法运算
3. empty：判断一个容器的长度是否为0(array,collection,set,list,map)，还可以判断一个对象是否为空
    1. 格式：`${empty 域中对象的名称}`
4. 三元运算符
    1. 格式：`${expression?:}`

# javaBean
1. java语言编写的一个可重用的组件
## javabean规范
1. 必须是一个公共的具体的类
2. 提供私有的字段
3. 提供公共访问字段的方法：get|set|is方法
4. 提供一个无参的构造器
5. 一般实现序列化接口：serializable


# 案例1-在页面中展示所有的商品信息
## 需求分析
1. 不使用jsp的脚本

## 技术分析
1. jsp/el/jstl

## 步骤分析
1. 数据库和表，导入jar包
    1. 驱动
    2. dbutils
    3. c3p0
    4. jstl
    5. c3p0配置文件和工具类
2. index.jsp中添加一个链接展示所有商品
3. FindAllServlet
    1. 调用ProductService#findAll()，返回一个`List<Product>`集合
    2. 将list放入request域中
    3. 转发到product_list.jsp
4. ProductService#findAll()调用ProductDao#findAll()方法
5. ProductDao#findAll()使用beanListHandler进行查询
6. 在product_list.jsp展示所有商品
    1. 使用c:forEach
    2. 使用javabean导航获取里面的数据
    
    
# 案例2-重写登录案例
## 需求
1. 在页面上填写用户名和密码以及验证码，点击提交，先校验验证码输入是否正确，再去查找数据库，顺便记住用户名

## 技术分析
1. 表单
2. 验证码
3. servlet
4. request
5. session
6. cookie

## 步骤分析
1. 数据库和表
2. 项目环境
    1. 包结构
    2. jar包
    3. 工具类和配置文件
3. 表单 login.jsp
4. 表单提交到LoginServlet
5. LoginServlet
    1. 获取验证码(从前台传过来和session中的)
        1. 判断两个验证码是否一致
            1. 若不一致，填写错误信息，请求转发到login.jsp中
        2. 若一致，获取用户名和密码
        3. 调用userSerice方法getUserByUsernameAndPassword()，返回Bean对象
            1. 判断返回值是否为null
                1. 若为空：填写错误信息，请求转发到login.jsp
                2. 继续判断是否勾选了记住用户名
                    1. 若勾选：创建cookie，将用户名放入cookie写回浏览器，将user对象放入session中，页面重定向到index.jsp，展示xxx,欢迎回来
