# 注解
1. jdk5之后提供了一个特性，和类、接口同级
2. 定义注解格式：`类修饰符 @interface 注解名 类名`

## 作用
1. 编译检查
2. 替代配置文件
3. 定义注解(元注解：注解上的注解)
4. 分析代码(用到反射)

## java中的3个注解
1. @Override：声明该方法是从父类上继承过来的，执行编译期的检查
2. @SuppressWarnings("unused")：抑制警告，有很多值，all，unused等
3. @Deprecated：声明该方法不赞成使用

## 自定义注解
1. 注解本质就是一个接口，接口中可以有常量和抽象方法
2. 抽象方法在注解中就称之为注解属性
### 注解属性 
1. 注解属性类型：
    1. 基本类型
    2. String
    3. Class
    4. Enum：枚举
    5. 以上类型对应的一维数组
2. 一旦注解有属性了，使用注解的时候必须赋值(除非这个注解属性有默认值)
3. 赋值的格式：`@注解名(属性名=属性值)`
4. 若注解类型为数组，且只有一个值的时候，可以有两种写法\
    1. 方式1：`属性名={"值","值"}`
    2. 方式2：`属性名=属性值`
5. 若属性名为`value`的时候，且字需要为这个value属性赋值的时候，这个value可以省略

## 元注解
1. 定义在注解上的注解

### 具体的元注解
1. @Retention：规定注解保留到什么阶段，注解的声明周期   
    1. RetentionPolicy.SOURCE：只在代码中保留，在字节码文件中就删除了
    2. RetentionPolicy.CLASS：在代码和字节码文件中保留
    3. RetentionPolicy.RUNTIME：所有阶段都保留
2. @Target：规定注解作用在什么上面
    1. ElementType.TYPE：作用在类，接口上面
    2. ElementType.METHOD：作用在方法上面
    3. ElementType.FIELD：作用在字段上面

# servlet3.0
1. 支持注解开发，没有web.xml这个文件了
2. 内嵌了文件上传功能

# 文件上传
## 浏览器端的要求
1. 表单的提价方式必须是post
2. 必须有一个文件上传组件`<input type="file" name=""/>`
3. 必须设置表单的`enctype=multipart/form-data`属性

## 服务器端的要求
1. servlet3.0中 
    1. 需要在servlet中添加注解`@MultipartConfig`
    2. 接收普通上传文件(除了文件上传组件)：`request.getParameter()`
    3. 接收文件上传组件：`request.getPart(name属性的值)`
### Part常用方法
1. String getName()：获取name属性值
2. String getHeader()：获取Part头信息
3. String getInputStream()：获取文件的流
4. void delete()：删除临时文件

## 文件上传注意的问题
1. 名字重复，使用随机名称解决
    1. 在数据库中提供两个字段
        1. 一个字段用来存放文件的真是名称
        2. 另一个字段用来存放文件存放路径
    2. 随机名称
        1. uuid
        2. 时间戳
2. 文件安全
    1. 重要的文件存放在`WEB-INF`或者`META-INF`或者服务器创建的一个目录下
    2. 不是很重要的文件，放在项目下
    
3. 文件存放目录
    1. 方式1：按照日期创建文件夹
    2. 方式2：按用户创建文件夹
    3. 方式3：按每个文件夹中文件个数
    4. 方式4：随机目录
    

# 类加载器
1. 我们编写的.java文件，jvm会将其变成.class文件，该文件要想运行，必须加载到内存当中，然后会生成一个Class对象

## 类加载器层次结构
1. 引导类加载器
    1. 用于加载rt.jar(runtime的jar包)
2. 扩展类加载器
    1. 用于加载ext/*.jar的jar包
3. 应用类加载器
    1. 我们自己编写的类

## 全盘负责委托机制
1. 当一个类运行的时候，有可能有其他类，应用类加载器询问扩展类加载器是否加载过某个类，扩展类加载器向上问引导类加载器是否加载过某个类，引导类加载器再去查询

# 动态代理
1. 在项目运行的时候才创建一个代理对象，对方法进行控制

## 动态代理的实现方式
1. jdk中的Proxy类，前提，必须实现接口
2. sping中cglib，前提，必须继承类

## 动态代理方法
1. 动态的在内存中创建一个代理对象：`Object Proxy.newProxyInstance(ClassLoader loader, Class[] interfaces, InvocationHandler h)`
    1. ClassLoader：代理对象的类加载器，一般我们使用的是被代理对象的类加载器
    2. Class[]：代理对象需要实现的接口，一般我们使用的是被代理对象的所有接口
    3. InvocationHandler：执行处理类，在这里面对方法进行加强
2. InvocationHandler中只有一个方法：`Object invoke(Object proxy, Method method, Object[] args)`
    1. proxy：代理对象
    2. method：当前执行的方法
    3. args：当前方法执行的时候所需要的参数
    4. 返回值：当前method对象执行的返回值

## 静态代理(装饰者模式)的书写步骤
1. 要求被装饰者和装饰者实现同一个接口或继承同一个类
2. 装饰者中要有被装饰者的引用
3. 对需要加强的方法进行加强
4. 对不需要加强的方法调用原来的方法

## 动态代理的书写步骤


# 案例1-模拟junit测试
## 需求
1. 在一个类的方法中添加一个@MyTest，在另一个类的main方法中就可以执行带有@MyTest的方法
## 技术分析
1. 注解
2. 反射

## 步骤分析
1. 定义MyTest注解
2. 在一个测试类AnootationTest上添加几个方法
    1. 在方法上面添加@MyTest
3. 在另一个有主方法的类上添加main方法
    1. 运行main方法的时候，需要将带有@MyTest注解的方法执行

# 案例2-获取连接的工具类
## 需求
1. 获取连接的工具类，用来配置四个参数

## 步骤分析
1. 自定义一个注解`@JDBCInfo`
    1. 添加元注解：
        1. 在程序运行的是可以使用：`@Retention(RetentionPolicy.RUNTIME)`
        2. 只能作用在方法上：`@Target(ElementType.METHOD)`
    2. 添加注解属性
        1. String driverClass() default "com.mysql.jdbc.Driver";
        2. String url();
        3. String username();
        4. String password();
2. 在jdbcutils工具类中提供一个getConnection方法，在方法上面添加一个注解`@JDBCInfo(...)`
    1. getConnection方法需要进行的操作
        1. 获取我们注解上的四个属性值
            1. 获取字节码文件
            2. 获取该方法上的注解
            3. 获取注解的值
3. 运行的时候，可以通过getConnection方法获取一个连接

# 案例3-完成文件上传
## 技术分析
1. 文件上传方式
    1. servlet3.0
    2. commons-fileupload
    3. 框架
    
# 案例4-统一编码
## 需求
1. 使用动态代理，在过滤器中统一编码

## 技术分析
1. 过滤器，doFilter(HttpServletRequest, HttpServletResponse)
2. 将代理request传递过去：doFilter(RequestPoxy, HttpServletResponse)
