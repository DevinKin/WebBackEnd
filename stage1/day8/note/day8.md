# xml
## 简介
1. 可扩展的标签语言
2. 标签自定义
## 作用
1. 存储数据(配置文件)

## 书写规范
1. 区分大小写
2. 应该有一个根标签
3. 标签必须关闭
4. 属性必须用引号引起来
5. 标签体中的空格或者换行或者制表符等内容都是作为数据内容存放的
6. 特殊字符必须转义

## xml的组成部分
1. 声明
    1. 作用：告诉别人这是一个xml文件
    2. 格式`<?xml ...... ?>`
    3. 例如`<?xml version='1.0' encoding='utf-8' standalone='yes|no'?>`
    4. 必须放在xml的第一行
    5. 必须顶格写
2. 元素(标签)
    1. 必须关闭
    2. 标签不能使用 xml Xml XML 等等开头
    3. 标签名中不能出现" "或者":"等特殊字符
3. 属性
    1. 属性必须用引号引起来
4. 注释
    1. 和html一样`<!--注释内容-->`
5. CDATA
    1. xml文件的特殊字符必须转义
    2. 通过cdata保证数据原样输出`<![CDATA[原样输出的内容]]>`


# xml解析
## 常用解析器
1. JAXP：sun公司提供支持DOM和SAX开发包
2. JDom：dom4j兄弟
3. jsoup：一种处理HTML特定解析开发包
4. dom4j：比较常用的解析开发包，hibernate底层采用

## 解析方式
### sax解析
1. 特点：逐行解析，只能查询，不支持增删改操作

## dom解析
1. 特点：一次性将文档加载到内存中形成一棵dom树，可以对dom进行增(C)改(U)查(R)删(D)操作


## dom4j技术进行查询操作
### 使用步骤
1. 导入jar包
    1. dom4j-1.6.1.jar
2. 创建一个核心对象`SAXReader`
    1. `SAXReader saxReader = new SAXReader();`
3. 将xml文档加载到内存形成一棵dom树
    1. `Document doc = saxReader.read(文件);`
4. 获取根节点
    1. `Element root = doc.getRootElement()`
5. 通过根节点获取其他节点(文本节点、属性节点、元素节点)
    1. 获取所有的子元素`List<Element> list = root.elements()`
    2. 获取元素的指定属性内容`String value = root.attributeValue("属性名称")`
    3. 获取子标签的标签体内容：遍历每一个list，获取到每一个子元素
        1. `String text = ele.elementText("子标签名称")`

## xpath解析技术
1. 依赖于dom4j
### 使用步骤
1. 导入jar包
    1. dom4j-1.6.1.jar
    2. jaxen-1.1-beta-6.jar
2. 创建一个核心对象`SAXReader`
    1. `SAXReader saxReader = new SAXReader();`
3. 将xml文档加载到内存形成一棵dom树
    1. `Document doc = saxReader.read(文件);`
4. 使用api
    1. `selectNode("xpath")`
    2. `selectSingleNode("xpath")`

### xpath语法
|表达式|描述|
|-----|---|
|nodename|选取此节点的所有子节点|
|/|从根节点选取|
|//nodename|从匹配选择的当前节点选择文档中的所有节点，而不考虑他们的位置|
|.|选取当前节点|
|..|选取当前节点的父节点|
|@[attname='attvalue']|选取属性|

# xml约束
## xml约束的作用
1. 规定xml中可以出现哪些元素以及哪些属性，以及他们出现的顺序

## 约束分类
1. DTD约束
    1. structs hiebernate中使用到
2. SCHEMA约束
    1. tomcat spring使用到
    
## DTD约束
1. 一个xml文档只能添加一个DTD约束
2. 文件后缀名`.dtd`
### DTD约束和xml的关联
1. 内部关联
2. 外部关联-系统关联
3. 外部关联-公共关联

### 内部关联
1. 格式`<!DOCTYPE 根元素名 [dtd语法]>`

### 外部关联-系统关联
1. 格式`<!DOCTYPE 根元素名 SYSTEM "约束文件的位置">`
2. 例如`<!DOCTYPE web-app SYSTEM "web-app_2_3.dtd">`

### 外部关联-公共关联
1. 格式`<!DOCTYPE 根元素名 PUBLIC "约束文件的名称" "约束文件的位置">`

### dtd语法
1. 元素`<!ELEMENT 元素名称 数据类型|包含内容>`
2. 数据类型

|数据类型|含义|
|------|---|
|#PCDATA|普通文本，使用的时候一般用()括起来|
3. 包含内容：该元素下可以出现哪些元素，用()括起来
4. 符号

|符号|含义|
|---|---|
|*|出现任意次|
|?|出现1次或0次|
|+|出现至少1次|
|或符号|或者|
|()|分组|
|,|顺序|

5. 属性：`<!ATTLIST 元素名 属性名 属性类型 属性是否必须出现>`
    1. 属性类型
        1. ID：唯一
        2. CDATA：普通文本
    2. 属性是否出现
        1. REQUIRED：必须出现
        2. IMPLIED：可以不出现，可选的
        

## SCHEMA约束
1. 一个xml文档可以添加多个schema约束
2. 文件后缀名`.xsd`
3. schema约束也是xml文件
### xml和shcema的关联
1. 格式：`<根标签 xmlns="..." ...>`
2. 名称空间
    1. 关联约束文件
    2. 规定元素来源于哪个约束文件的
3. 在一个xml文件中只能有一个不起别名的约束

## 例
```xml
	<web-app xmlns="http://www.example.org/web-app_2_5" 
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://www.example.org/web-app_2_5 web-app_2_5.xsd"
			version="2.5">
```
1. `xmlns="http://www.example.org/web-app_2_5"`：表示引入一个名称空间，约束文件的名称为web-app_2_5
2. 实例和约束
    1. `xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"`表示引入的是一个xml实例文件
    2. `xmlns:xsd="http://www.w3.org/2001/XMLSchema"`：表示是一个schema约束文件
3. `xsi:schemaLocation="http://www.example.org/web-app_2_5 web-app_2_5.xsd"`：指明约束文件的位置 

# 常见的web服务器
|服务器名称|厂商|特点|
|--------|---|---|
|weblogic|oracle|大型的收费的支持javaee所有规范的服务器|
|webspere|IBM|大型的收费的支持javaee所有规范的服务器|
|tomcat|apache组织|中小型的开源免费的支持servlet和jsp规范的服务器|

# tomcat目录结构
1. bin：存放可执行程序
2. conf：存放配置文件
3. lib：存放的是tomcat和项目运行时需要的jar包
4. logs：日志，注意catalina目录
5. temp：存放临时文件
6. webapps：存放项目的目录
7. work：存放jsp文件在运行时产生的java和class文件

# 常用的项目发布方式(虚拟目录映射)
1. 将项目放到tomcat/webapps下
2. 修改`tomcat/conf/server.xml`
    1. 在host标签下，添加如下代码`<Context path="/项目名" docBase="项目的磁盘目录"/>`
3. 在tomcat/conf/Catalina/主机名 新建一个xml文件，文件名称就是项目名，文件的内容如下：`<Context path="/项目名" docBase="项目的磁盘目录"/>`

# 反射
## 获取对应的class对象
1. `Class clazz=Class.forName("全限定名")`
2. `Class clazz=类名.class`
3. `Class clazz=对象.getClass()`

## 通过class对象创建一个实例对象
1. `Object clazz.newInstance()`

## 通过class对象获取一个方法
1. `clazz.getMethod("MethodName", Class ...paramType)`
2. `method.invoke(Object, Object ...param)`



# 案例1-编写配置文件，编写一个服务器软件
## 需求
1. 按照指定的全限定名，根据路径，让服务器创建这个对象，调用指定的方法

2. 例
```xml
<a1>
    <c>hello</c>
    <d>com.devinkin.HelloServlet</d>
</a1>
<b1>
    <c>hello</c>
    <e>/hello</e>
</b1>
```

## 技术分析
1. xml
2. 解析xml
3. 根据我们的全限定名创建一个对象，调用方法

