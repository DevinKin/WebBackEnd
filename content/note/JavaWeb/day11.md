# 会话技术
## 简介
1. 当用户打开浏览器的时候，访问不同的资源，直到用户将浏览器关闭，可以认为是一次会话
## 作用
1. 因为http协议是一个无状态的协议，它不会记录上次访问的内容，用户在访问过程中难免会产生一些数据，通过会话技术就可以将数据保存起来
## 会话技术分类
1. Cookie：浏览器端的会话技术
2. Session：服务器端会话技术

# Cookie
## 什么是Cookie
1. cookie是由服务器生成，通过response将cookie写回浏览器(set-cookie)，保留在浏览器上。
2. 下次访问，浏览器根据一定的规则携带不同的cookie(通过request的头 cookie)，服务器可以接受cookie

## 与cookie相关的api
1. `Cookie cookie = new Cookie(String key, String value)`：构造cookie
2. `response.addCookie(Cookie cl)`：服务器发送cookie到浏览器
3. `Cookie[] request.getCookies()`：服务器获取cookie

## Cookie的常用方法
1. `getName()`：获取cookie的key(名称)
2. `getValue()`：获取cookie指定的值
3. `setMaxAge(int second)`：设置cookie在浏览器端的最大生存时间，以秒为单位
    1. 若设置成0：删除该cookie(前提必须与路径一致)
4. `setPath(String path)`：设置cookie的路径
    1. 当我们访问的路径中包含此cookie的path，则携带
    2. cookie默认路径，从“/项目名称”开始，到最后一个“/”结束，访问路径为"/day11/a/b/hello"，则cookie路径为"/day11/a/b/"

## Cookie相关注意事项
1. cookie不能跨浏览器
2. cookie不支持中文

# Session
## 什么是Session
1. 服务器端会话技术
2. 当我们第一次访问的服务器的时候，服务器获取id
    1. 能获取id
        1. 要拿着这个id去服务器中查找有无此session
            1. 若查找到有session：直接使用该session，将数据保存，需要将当前session的id返回浏览器
            2. 若查找不到session：创建一个session，将你的数据保存到这个session中，将当前的session的id通过cookie返回给浏览器
    2. 不能获取id
        1. 创建一个session，将你的数据保存到这个session中，将当前的session的id通过cookie返回给浏览器
3. session是一个域对象
4. session存放的是私有的数据，一个浏览器只有一个

## 获取session
1. `HttpSession request.getSession()`：获取一个session

## 常用方法(域对象)
1. `xxxAttribute()`：设置，获取，删除session
2. `setMaxInactiveInterval(int second)`：手动设置session超时
3. `invalidate()`：使此会话无效，然后取消对任何绑定到它的对象的绑定

## session生命周期
1. 创建：第一次调用request.getSession()创建
2. 销毁
    1. 服务器非正常关闭
    2. 超时，默认超时时间为30分钟，在服务器上设置(web.xml)
    3. 手动销毁session

# Jsp
## 什么是JSP
1. Java Server pages(java服务器页面)
2. 本质上jsp就是一个servlet，在html代码中嵌套java代码
3. 运行在服务器端，处理请求，生成动态的内容
4. 对应的java和class文件在tomcat目录下的work目录
5. 后缀名`.jsp`

## 执行流程
1. 浏览器发送请求，访问jsp页面
2. 服务器接受请求，jspServlet会帮我们查找对应的jsp文件
3. 服务器将jsp页面翻译成java文件
4. jvm会将java文件`.class`文件
5. 服务器运行class文件，生成动态的内容
6. 将内容发送给服务器
7. 服务器组装成响应信息，发送给浏览器
8. 浏览器接收响应，展示页面

## jsp脚本
1. `<%...%>`：java程序片段
    1. 生成在jsp的service方法中
2. `<%=...%>`：输出表达式
    1. 生成在jsp的service方法中，相当于在java中调用了out.print(...)方法
3. `<%!...%>`：声明成员，类的字段
    1. 生成在JspServlet类的成员位置上


# 案例1-记录用户上次访问事件
## 需求
1. 当用户第一次登录的时候，提示：你是第一次访问，且记录该次访问事件
2. 下一次访问的时候，获取上一次访问事件且展示出来

## 技术分析：
1. 会话技术
2. cookie
3. jsp

## 步骤分析
1. 创建一个RemServlet
2. 在servlet中
    1. 获取指定cookie：lastAccessTime，使用`request.getCookies()`
    2. 判断cookie是否为空
        1. 若为空：提示信息，第一次访问
        2. 若不为空
            1. 获取cookie的value
            2. 展示信息：你上次访问的时间是....
    3. 将这次的访问时间记录下来，写回浏览器
    
    
# 案例2-记录用户浏览记录
## 需求
1. 当用户访问一个商品的时候，需要将该商品保留在浏览记录中

## 技术分析
1. cookie

## 步骤分析
1. product_list.html转换成jsp
2. 点击一个商品，展示该商品的信息，将该商品id记录到cookie中
    1. 获取之前的浏览记录，例如名称：ids
    2. 判断cookie是否为空
        1. 若为空，将当前商品的id起个名称ids放入cookie中 
        2. 若不为空，获取值，例如ids='2-1'，使用“-”分隔商品id，当前访问的id=1
            1. 判断之前的历史记录中有无该商品
                1. 若有，将当前的id放到前面，结果为'1-2'
                2. 若没有，继续判断长度是否>=3
                    1. 若`>=3`，移除最后一个，将当前id放入最前面
                    2. 若`<3`，直接将当前的id放入最前面
3. 再次回到product_list.jsp页面，需要将之前访问的商品展示在记录中
    1. 获取ids，如值为ids=2-3-1，切割

## 扩展：删除浏览记录
### 技术分析
1. `cookie.setMaxAge(0)`

### 步骤分析
1. 在浏览记录中添加一个超链接`<a href="day1101/clearHistory">清空浏览记录</a>`
2. 创建ClearHIstoryServlet
    1. 创建一个cookie，名称和路径保持一致，`setMaxAge(0)`，写回浏览器
3. 页面跳转
    1. 重定向到product_list.jsp
    
    
# 案例3-添加到购物车
## 需求
1. 在商品详情页面(product_info.htm)有一个添加购物车，点击则将该商品添加到购物车，点击加入购物车，在cart.htm中展示

## 技术分析
1. Httpsession

## 步骤分析
1. product_info.htm页面中点击添加到购物车的时候，发送请求提交到add2CartServlet，需要将商品名称携带过去

2. add2CartServlet中操作
    1. 获取商品名称
    2. 将商品添加到购物车，购物车结构`Map<String name, Integer num>`
    3. 提示信息，你的xxx已经添加到购物车中

3. 将商品添加到购物车
    1. 获取购物车
    2. 判断购物车是否为空
        1. 若为空，第一次添加商品，创建一个购物车，将当前商品添加到购物车，将购物车放入session中
        2. 若不为空：继续判断购物车中是否有该商品
            1. 若有：取出该商品的数量，数量+1，将商品再次放入购物车
            2. 若没有，将当前商品再次放入购物车中，数量为1
   
4. 点击购物车链接(cart.jsp)
    1. 从session获取购物车
        1. 有购物车：遍历购物车
        2. 没有购物车：提示“你还没有添加商品到购物车呢”

## 清空购物车
### 思路1
1. 将购物车移除

### 思路2
1. 将session删除

### 步骤分析
1. 在cart.jsp添加一个超链接，清空购物车`<a href='/ClearCartServlet></a>`
2. ClearCartServlet
    1. 调用session.invalidate()
    2. 重定向到购物车页面