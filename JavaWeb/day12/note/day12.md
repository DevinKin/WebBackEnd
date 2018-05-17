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
## Jsp的内置对象

## Jsp的域对象

## Jsp的动作标签



# 案例1-在页面中展示所有的商品信息
## 需求分析
1. 不使用jsp的脚本

## 技术分析
1. jsp/el/jstl
