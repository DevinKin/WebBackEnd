# 高级参数绑定

## 数组
1. Controller方法中可以用String[]接收，或者pojo的String[]属性接收。两种方式任选其一即可。


## List
1. 如果批量修改,可以用`List<pojo>`来接收,页面上的input框的name属性=`vo中接收的集合属性名称[list下标].list泛型的属性名称.属性`

# RequestMapping注解的使用
1. 在class上添加
2. 在方法上添加
3. 请求路径=类上的RequestMappingURL+方法上的RequestMappingURL

## URL路径映射
1. @RequestMapping(value="/item")或@RequestMapping("/item）,value的值是数组，可以将多个url映射到同一个方法


## 窄化请求映射
1. 在class上添加@(url)指定通用请求前缀， 限制此类下的所有方法请求url必须以请求前缀开头，通过此方法对url进行分类管理。

2. 好处:防止controller方法映射路径重名,所以相当于在url中多加了一层目录,防止重名

## 请求方法限定
1. RequestMappingURL的method属性

2. 限定GET方法
    - `@RequestMapping(method = RequestMethod.GET)`

3. 限定POST方法
    - `@RequestMapping(method = RequestMethod.POST)`

4. 不限定GET或者POST
    - `@RequestMapping(method={RequestMethod.GET,RequestMethod.POST})`

# Controller方法返回值(返回的页面,返回到页面的数据)
1. ModelAndView
2. void
3. 返回字符串

## 返回ModelAndView
1. controller方法中定义ModelAndView对象并返回，对象中可添加model数据、指定view。
2. modelAndView.setViewName():设置视图
3. modelAndView.setObject():设置模型

## 返回void
1. 可以使用`request.setAttribute`来给页面返回数据
2. 可以使用`request.getRequestDispatcher().forward)`来指定返回的页面
3. 如果controller返回值为void,则不走springMVC的组件,所以要写页面的完整路径名称

## 返回字符串

### 逻辑视图名
1. controller方法返回字符串可以指定逻辑视图名，通过视图解析器解析为物理视图地址。

### redirect重定向
1. 重定向:浏览器的url改变,request域中的数据不能被带到转发后的方法中
2. 在处理器方法中返回`redirect:路径`即可,路径可以为相对路径和绝对路径


### forward转发
1. 请求转发:浏览器中的url不变,request域中的数据能被带到转发后的方法中
2. 在处理器方法中返回`forward:路径`即可,路径可以为相对路径和绝对路径

# Springmvc中异常处理
1. springmvc在处理请求过程中出现异常信息交由异常处理器进行处理，自定义异常处理器可以实现一个系统的异常处理逻辑。

## 自定义异常类
1. 为了区别不同的异常通常根据异常类型自定义异常类，这里我们创建一个自定义系统异常，如果controller、service、dao抛出此类异常说明是系统预期处理的异常信息。
```java
package cn.devinkin.exception;

public class CustomException extends Exception{
    // 异常信息
    private String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
```

## 自定义异常处理器
```java
package cn.devinkin.exception.resolver;

import cn.devinkin.exception.CustomException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        e.printStackTrace();

        CustomException customException = null;

        // 如果抛出的是系统自定义异常则直接转换
        if (e instanceof CustomException) {
            customException = (CustomException) e;
        } else {
            // 如果跑出的不是系统自定义异常则重新构造一个系统错误异常
            customException = new CustomException("系统错误,请与管理员联系!");
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", customException.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}

```

## 错误页面
```jsp
<%--
  Created by IntelliJ IDEA.
  User: devinkin
  Date: 2018/9/13
  Time: 23:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>错误页面</title>
</head>
<body>
您的操作出现错误如下:<br/>
${message}

</body>
</html>

```

## 异常处理器配置
1. 在springMVC.xml中添加
```xml
	<!-- 异常处理器-->
	<bean id="customExceptionResolver" class="cn.devinkin.exception.resolver.CustomExceptionResolver"/>
```

## 异常测试
1. 修改商品信息，id输入错误提示商品信息不存在。

2. 修改controller方法“editItem”，调用service查询商品信息，如果商品信息为空则抛出异常：
```java
        // 调用service查询商品信息
		Items item = itemService.findItemById(id);
		
		if(item == null){
			throw new CustomException("商品信息不存在!");
		}
```

# 图片上传处理
## 配置虚拟目录
1. 在tomcat上配置图片虚拟目录，在tomcat下conf/server.xml中添加：
```xml
   <Context docBase="F:\develop\upload\temp" path="/pic" reloadable="false"/>
```
2. 访问http://localhost:8080/pic即可访问F:\develop\upload\temp下的图片。

## 文件上传的jar包
1. commons-fileupload-1.2.2.jar
2. commons-io-2.4.jar

## 配置文件上传解析器
```xml
<!-- 文件上传 -->
	<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
		<!-- 设置上传文件的最大尺寸为5MB -->
        <property name="maxUploadSize">
        	<value>5242880</value>
        </property>
	</bean>
```
## 测试代码
```java
    public String updateItem(MultipartFile pictureFile, Items items, Model model, HttpServletRequest request) throws Exception {
        // 1. 获取图片的完整名称
        String fileStr = pictureFile.getOriginalFilename();
        // 2. 使用随机生成的字符串+原图片扩展名组成新的图片名称,防止图片重名
        String newFileName = UUID.randomUUID().toString() + fileStr.substring(fileStr.lastIndexOf("."));

        // 3. 将图片保存到硬盘
        pictureFile.transferTo(new File("F:\\SpringMVC\\upload\\temp\\" + newFileName));

        // 4. 将图片名称保存到数据库
        items.setPic(newFileName);

        items.setCreatetime(new Date());
        itemsService.updateItems(items);
        // 请求转发:浏览器中的url不变,request域中的数据能被带到转发后的方法中
        model.addAttribute("id", items.getId());
        // springMVC中请求转发:返回的字符串以forward:开头的都是请求转发
        // 后面表示相对路径,相对于当前类指定的路径,当前路径下可以任意跳转到当前类的任意一个方法
        // forward:以/开头,表示绝对路径,绝对路径从项目名开始算
        return "forward:/items/itemEdit.action"
    }
```

# Json数据交互
## RequestBody注解
1. @RequestBody注解用于读取http请求的内容(字符串)，通过springmvc提供的HttpMessageConverter接口将读到的内容转换为json、xml等格式的数据并绑定到controller方法的参数上。
2. 在处理器参数中加入该注解即可将json转换为pojo对象

## ResponseBody注解
1. 作用：该注解用于将Controller的方法返回的对象，通过HttpMessageConverter接口转换为指定格式的数据如：json,xml等，通过Response响应给客户端
2. 在处理器方法中加入该注解即可将pojo对象转换为json字符串返回到页面

## 请求json，响应json实现
### springMVC默认的jar包
1. Springmvc默认用MappingJacksonHttpMessageConverter对json数据进行转换，需要加入jackson的包，如下
    1. jackson-core-asl-1.9.11.jar
    2. jackson-mapper-asl-1.9.11.jar
    
### 注解驱动配置
```xml
<mvc:annotation-driven/>
```

### 测试代码
```java
    // 导入jacksonjar包,在controller的方法中,可以使用@RequestBody让SpringMVC将json格式的字符串自动转换成Java中的pojo
    // 页面json的key要等于java中pojo的属性名称
    // controller方法范围pojo类型的对象并且用@RequestResponse注解,springMVC会自动将pojo对象转换成json格式字符串
    @RequestMapping("/sendJson")
    @ResponseBody
    public Items json(@RequestBody Items items) throws Exception {
        System.out.println(items);
        return items;
    }
```

# Springmvc实现Restful
1. Restful就是一个资源定位及资源操作的风格。不是标准也不是协议，只是一种风格，是对http协议的诠释。

2. Url格式:`http://blog.csdn.net/beat_the_world/article/details/45621673`,资源操作：使用put、delete、post、get，使用不同方法对资源进行操作。分别对应添加、删除、修改、查询。一般使用时还是post和get。Put和Delete几乎不使用。

3. @PathVariable标注在处理器参数中,可以接受url传过来的参数

4. 例:
    - `@RequestMapping("itemEdit/{id}")`中接收参数使用大括号加上变量名名称
    - `@PathVariable`中变量名称要和RequestMapping中的变量名称一致


# 拦截器

## 实现拦截器的代码
1. 实现HandlerInterceptor接口
```java
package cn.devinkin.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interceptor1 implements HandlerInterceptor {
    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return 布尔值,true为方形,false则拦截住
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("========Interceptor1======preHandle=====");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("========Interceptor1======postHandle=====");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("========Interceptor1======afterCompletion=====");
    }
}

```

## 配置拦截器
```xml
    <!-- 配置拦截器 -->
    <mvc:interceptors>
        <mvc:interceptor>
            <!-- 拦截请求的路径,要拦截所有必须配置成 /** -->
            <mvc:mapping path="/**"/>
            <!-- 指定拦截器类的路径 -->
            <bean id="interceptor1" class="cn.devinkin.interceptor.Interceptor1"></bean>
        </mvc:interceptor>
    </mvc:interceptors>
```

## HandlerInterceptor接口方法

### preHandle方法
1. 执行时机:
    - controller方法没有被执行,ModelAndView没有被返回的时候执行
2. 使用场景:
    - 权限验证
3. 返回值:
    - true:不拦截,放行
    - false:拦截,不放行
    
### postHandle方法
1. 执行时机:
    - controller方法已经执行,ModelAndView没有返回
2. 使用场景:
    - 可以在此方法中设置全局的数据处理业务

### afterCompletion方法
1. 执行时机:
    - controller已经执行,ModelAndView已经返回
2. 使用场景:
    - 记录操作日志,记录登录用户的ip,时间等
    

## 拦截器应用(登录权限验证)
### 步骤
1. 编写登录的Controller
    - 编写跳转到登录页面的方法
    - 编写登录验证方法
    
2.  编写登录页面

3. 编写拦截器

### 运行流程
1. 访问随意一个页面,拦截器会拦截请求,会验证session中是否有登录信息
    - 如果已登录,放行
    - 如果未登录,跳转到登录页面
    - 在登录页面中输入用户名,密码,点击登录按钮

2. 在登录页面中输入用户名,密码,点击登录按钮,拦截器会拦截请求
    - 如果是登录页面路径,放行
    - 在controller方法中判断用户名密码是否正确,如果正确则将登录信息放入session中