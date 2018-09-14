# response对象
## 作用
1. 往浏览器写东西

## 组成部分
1. 响应行
2. 响应头
3. 响应体

## 操作响应行
1. 格式： 协议/版本 状态码 状态码说明
2. 状态码

|状态码|说明|
|-----|---|
|1xx|已发送请求|
|2xx|已完成响应|
|200|正常响应|
|3xx|还需要对浏览器进一步操作|
|302|重定向，配合响应头location|
|304|读缓存|
|4xx|用户操作错误|
|404|用户操作错误|
|405|访问的方法不存在|
|5xx|服务器错误|
|500|服务器内部异常|

### 常用方法
1. `setStatus(int status)`：设置状态码，针对1xx,2xx,3xx
2. `sendError(int status)`：发送错误码，针对4xx和5xx
3. `sendRedirect(String path)`：重定向到path


## 操作响应头
1. 格式：key/value(value可以是多个值)

### 常用的方法
1. `setHeader(String key, String value)`：设置字符串形式的响应头
2. `setIntHeader(String key, String value)`：设置整型的响应头
3. `setDateHeader(String key, String value)`：设置时间的响应头
4. `addHeader(String key, String value)`：追加响应头，若之前有该响应头，继续添加值
5. `addIntHeader(String key, String value)`：追加响应头，若之前有该响应头，继续添加值
6. `addDateHeader(String key, String value)`：追加响应头，若之前有该响应头，继续添加值

### 常用的响应头
1. location：重定向
    1. `response.setRedirect(String path)`
    2. `response.setStatus(302)`
    3. `response.setHeader("Location", String path)`
2. refresh：定时刷新
    1. `response.setHeader("refresh")`
    2. http的meta标签:`<meta http-equiv="refresh" content="3;url=/refresh2.html">`
3. content-type：设置文件的MIME类型设置响应流的编码及告诉浏览器用什么编码打开
4. content-disposition：文件下载

## 操作响应体
1. 页面上要展示的内容

### 常用方法
1. `Writer getWriter()`：获取字符流
2. `ServletOutputStream getOutPutStream()`：获取字节流
    1. 字符流和字节流互斥，当响应完成之后，服务器会判断一下流是否已经关闭，如果没有关闭，服务器会帮我们关闭(底层使用缓冲流)
3. `SetContentType("text/html;charset=utf-8)`：处理响应中文乱码
4. `setHeader("Content-Type","text/html;charset=utf-8")`：处理响应中文乱码

# request对象
## 作用
1. 获取浏览器发送过来的数据

## 操作请求行
1. 格式：请求方式 请求资源 协议/版本

### HttpServletRequest常用方法
1. `String getMethod()`：获取请求方式
2. `String getRequestURI()`：获取的资源绝对路径，从项目名到参数之前，例`/day10/regist`
3. `StringBuffer getRequestURL()`：获取的是带协议的完整路径，例`http://localhost/day10/regist/`
4. `String getQueryString()`：获取Get请求的所有参数，例`username=tom&password=123`
5. `String getProtocol()`：获取协议和版本
6. `String getRemoteAddr()`：获取ip地址
7. `String getContextPath()`：在java代码中项目名称(/day10)

## 操作请求头
1. 格式：key/value(value可以是多个值)
### 常用方法：
1. `String getHeader(String key)`：通过key获取单个指定的value
2. `Enumeration getHeaders(String name)`：通过key获取所有的value
3. `Enumeration getHeaderNames()`：获取所有的请求头的名称
4. `int getIntHeader(String key)`：获取值为整型的请求头
5. `long getDateHeader(String key)`：获取值为时间的请求头

### 重要的请求头
1. user-agent：浏览器内核
2. referer：页面从哪里来，防盗链
   

## 操作请求参数
### 常用方法
1. `String getParameter(String key)`：获取一个请求参数
2. `String[] getParameterValues(String key)`：通过一个key获取多个值
3. `Map<String, String[]> getPamaterMap()`：获取所有的参数名称和值

## 请求中文乱码
1. 对于get请求：参数追加到地址栏，会使用utf-8，服务器(Tomcat7)接收到请求之后，使用iso-8859-1编码，所以会出现乱码
2. 对于post请求，参数是放在请求体中，服务器(Tomcat)获取请求的时候使用iso-8859-1进行解码，也会出现乱码

### 解决请求乱码的通用方法
1. 方法1：`new String(参数.getBytes("ISO-8859-1"),"utf-8")`

2. 针对post请求：只需将请求流的编码设置成utf-8即可`request.setCharacterEncoding("utf-8")`

# 文件下载
## 下载方式
### 超链接下载
1. `<a href="/day10/download/day10.txt">下载</a>`
2. 若浏览器能解析该资源的MIME类型，则打开，若不能，则下载
### 编码下载(两头一流)
1. 通过servlet完成
2. 步骤
    1. 设置文件的MIME类型：`response.setContentType(context.getMimeType(filename))`
    2. 设置下载头信息：`response.setHeader("content-disposition","attachment;filename=" + 文件名称)`
    3. 提供流：
        1. `ServletOutputStream response.getOutputStream()`
        2. `InputStream context.getResourceAsStream(String path)`
    4. 拷贝流，使用byte[]
    
## 文件下载中文乱码问题
1. 中文名称的文件名下载的时候会出现问题
2. 常见的浏览器需要提供文件名称的utf-8编码
3. 对火狐，需要提供文件名称的base64编码

### 文件下载中文乱码解决方案
1. 使用编码解码的方式：`new String(filename.getByte("gbk"), "iso8859-1")`
2. 通过工具类编码
```java
    /**
     * 解决中文乱码问题
     * @param agent 客户浏览器
     * @param filename 文件名
     * @return 返回encode过的文件名
     */
    public static String getFileName(String agent, String filename) throws UnsupportedEncodingException {
        if (agent.contains("MSIE")) {
            //IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if(agent.contains("Firefox")) {
            //火狐浏览器
            BASE64Encoder base64Encoder = new BASE64Encoder();
            filename = "=?utf-8?B?" + base64Encoder.encode(filename.getBytes("utf-8")) + "?=";
        } else {
            //其他浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
```

# 域对象
## request
1. 创建：一次请求来的时候
2. 销毁：响应生成的时候
3. 作用：一次请求里面的数据

## request请求转发(请求链，请求串)
1. `request.getRequestDispatcher("内部路径").forward(request,response)`

# 案例1-文件下载案例
## 技术分析
1. response
2. 文件下载



# 案例2-完成用户注册操作
## 需求
1. 在一个表单页面上填写用户数据，点击提交，将所有的数据提交到服务器上，通过java代码最终把数据到数据库中
## 技术分析
1. 表单
2. request对象

## 步骤分析
1. 数据库和表
2. 表单页面
    1. 给每个字段添加name属性
    2. 修改表单提交的路径
3. 表单提交到Servlet(RegistServlet)
    1. 接收数据，封装成一个user
    2. 调用UserServlet#boolean regist(User user)完成保存操作
    3. 判断结果，相应的提示信息在当前的Servlet不做处理，将信息转发到另一个Servlet展示(MsgServlet)
4. UserService
    1. 调用UserDao#regist(User user)
5. UserDao在数据库插入一条记录