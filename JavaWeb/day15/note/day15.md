# Ajax(Asynchronous JavaScript And Xml)
1. 异步的JavaScript和Xml,是指一种创建交互式网页应用的网页开发技术，通过在后台与服务器进行少量数据交换，AJAX可以使网页实现异步更新，在不重新加载整个页面的情况下，对网页的某部分进行更新

## 使用步骤
1. 创建一个核心对象`XMLHttpRequest`
2. 编写一个回调函数`onreadystatechange`
3. 编写请求方式和请求的路径(open操作)`open(method,url,是否异步)`
4. 发送请求(send操作)

## Ajax-api
### 常用属性
1. onreadystatechange：检测readyStatus状态改变的时候
2. readyState：ajax核心对象的状态
    1. `0：核心对象已经创建`
    2. `1：核心对象调用了open方法`
    3. `2：核心对象调用了send方法`
    4. `3：部分响应已经生成(没有意义)`
    5. `4：响应已经完成`
3. status：Http状态码
    1. 200
    2. 302
    3. 404
    4. 500
4. responseText：响应回来的文本

### 常用方法
1. `open("请求方式","请求路径",[,"是否异步“, "验证用户名","验证密码"])`：设置请求方式和请求路径
2. `send(["参数"])`：用于发送请求，请求方式为post时候才需要设置参数
3. `setRequestHeader("content-type","form表单enctype属性")`：设置post请求的参数类型，必须放在send方法之前


# Jquery中的AJax
## 四种方法
1. `对象.load(url,params,function(arg){body},type)`
2. `$.get(url,params,function(arg){body},type)`：发送get请求的ajax
    1. url：请求的路径
    2. params：请求的参数，参数为key/value形式
    3. function：回调函数,参数就是服务器发送回来的数据
    4. type：返回信息的格式，xml，html，script，json，text，_default
3. `$.post(url,params,function(arg){body},type)`：发送post请求的ajax
    1. 若结果为json格式，打印返回值的时候是一个对象
4. `$.ajax([选项])`
    1. 选项的可选值：
        1. url：
        2. type：请求方式
        3. data：发送到服务器的数据
        4. success:fn：成功以后的回调函数
        5. error:fn：以后的回调函数
        6. dataType：返回的内容格式，经常使用json
        7. async：设置是否为异步请求

# Json
1. Json(JavaScript Object Notation)是一种轻量级的数据交换格式
2. 它基于ECMAScrpt的一个子集

## Json格式
1. 格式1：`{"key":value,"key1":value1}`
    1. value为任意值
2. 格式2：`[e1,e2]`
    1. e1为任意值
## jsonlib工具类，可以使对象转换成json数据
1. 导入jar包
    1. commons-beanutils.jar
    2. commons-collections.jar
    3. commons-lang.jar
    4. commons-loggingjar
    5. ezmorph.jar
    6. json-lib.jar
    
## 使用api
1. `JSONArray.fromObject(对象)`：数组和list转换为json
2. `JSONObject.fromObject(对象)`：bean和map转换为json

# 案例1-使用原生的ajax判断用户名是否占用
## 需求
1. 当我们在注册页面输入用户名之后，点击下一个地方，去数据库中查询有无该用户名，然后提示信息
## 技术分析
1. ajax
## 步骤分析
1. 数据库和表
2. 新建项目
    1. 导入jar包，工具类，配置文件
3. 新建一个注册页面，表单，在用户名上输入用户名，失去焦点之后发送ajax请求，将输入的值发送到servlet
4. checkUsername4AjaxServlet
    1. 接收用户名
    2. 调用service完成查询操作，返回User对象
    3. 判断user是否为空
        1. 若为空：写1，可以使用
        2. 若不为空：写0，不可以使用
5. 在表单接收响应的数据，判断
    1. 若为0，提示用户名已被注册，表单不可以提交

# 案例2-使用jquery的ajax判断女用户是否被占用
## 技术分析
1. jquery中的ajax
## 步骤分析
1. 将原生ajax修改成jquery的ajax即可


# 案例3-模仿百度搜索
## 需求
1. 在一个文本框中输入一段内容，keyup事件发送一个ajax请求，去数据库查找相应的内容，在页面上展示

## 步骤分析
1. 数据库和表
2. 页面
3. 在文本框输入内容，keyup事件发送ajax请求，将输入的值传入后台
4. 将返回的数据展示


# 案例4-省市联动
## 需求
1. 先有一个省份的下拉选，根据选择省份，从而动态的市下拉选中加载所有的市

## 步骤分析
1. 数据库和表
2. 页面上有两个下拉选，省份的下拉选一般是固定的，页面加载的时候读取所有的省份
3. 当省份改变的时候，获取省份的信息，发送一个ajax请求，去市的表中查询相应省份的所有市，将他们加载到市的下拉选中
4. SelectProServlet
5. SelectCityServlet 