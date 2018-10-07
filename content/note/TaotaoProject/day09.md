# Freemarker

## Freemarker简介
1. FreeMarker是一个用Java语言编写的模板引擎，它基于模板来生成文本输出。FreeMarker与Web容器无关，即在Web运行时，它并不知道Servlet或HTTP。它不仅可以用作表现层的实现技术，而且还可以用于生成XML，JSP或Java 等。
2. 原理图

![](../pic/freemarker.png)

## Freemarker使用方法
1. 把freemarker的jar包添加到工程中

### 测试代码
1. 模板文件内容: `${hello}`
```java
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author devinkin
 * <p>Title: TestFreeMarker</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 21:20 2018/9/27
 */
public class TestFreeMarker {

    @Test
    public void testFreemarker() throws Exception {
        // 1. 创建一个模板文件
        // 2. 创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 3. 设置模板所在的路径,所在目录
        configuration.setDirectoryForTemplateLoading(new File("D:\\WebBackEnd\\Taotaoparent\\Taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        // 4. 设置模板的字符集,一般是utf-8
        configuration.setDefaultEncoding("UTF-8");
        // 5. 使用Configuration对象加载一个模板文件,需要指定模板文件的文件名
        Template template = configuration.getTemplate("hello.ftl");
        // 6. 创建一个数据集,可以是pojo也可以是map,推荐使用map
        Map data = new HashMap<>();
        data.put("hello", "hello freemarker");
        // 7. 创建一个Writer对象,指定输出文件的路径及文件名
        Writer out = new FileWriter(new File("D:\\WebBackEnd\\Taotaoparent\\Taotao-item-web\\src\\main\\webapp\\out\\hello.html"));
        // 8. 使用模板对象的process方法来输出文件
        template.process(data, out);
        // 9. 关闭流
        out.close();
    }
}

```

## Freemarker语法
1. 访问map中的key
    - `${key}`
2. 访问pojo中的属性
    - `${key.property}`
3. 取集合中的数据
4. 判断
5. 日期类型格式化
6. Null值的处理
7. Include标签
    - 格式:`<#include “模板名称”>`,相当于jstl的包含

### 取集合中的数据
```injectedfreemarker
<#list studentList as student>
${student.id}/${studnet.name}
</#list>
```
循环使用格式:
```injectedfreemarker
<#list 要循环的数据 as 循环后的数据>
</#list>
```
取循环中的下标
```injectedfreemarker
<#list studentList as student>
	${student_index}
</#list>
```

### 判断
```injectedfreemarker
<#if student_index % 2 == 0>
        <tr bgcolor="#faebd7">
<#else>
        <tr bgcolor="#7fffd4">
</#if>
```

### 日期类型格式化
1. 直接取值:${date}(date是属性名)如果传来的是一个Date型数据会报错
2. 常见格式:
    - `${date?date} 2016-9-13`
    - `${date?time} 17:53:55`
    - `${date?datetime} 2016-9-13 17:53:55`
    - 自定义格式:`${date?string("yyyy/MM/dd HH:mm:sss")}`


### Null值的处理
1. 如果直接取一个不存在的值(值为null)时会报异常
    - `${aaa}`
2. 处理: 
    - `${aaa!”默认值”}`:Null值的默认值
    - `${aaa! }`:代表空字符串
3. 使用if判断null值
```injectedfreemarker
    使用if判断null值:
    <#if val??>
        val是有值的...
    <#else>
        val值为null...
    </#if>
```

## Freemarker整合Spring

### 整合的配置文件
```xml
    <!-- freemarker的配置 -->
    <bean id="freeMarkerConfigurer" class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer">
        <!-- 模板文件目录 -->
        <property name="templateLoaderPath" value="/WEB-INF/ftl"/>
        <!-- 默认字符集 -->
        <property name="defaultEncoding" value="UTF-8"/>
    </bean>

```

### 测试代码
```java
package cn.devinkin.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author devinkin
 * <p>Title: HtmlGenContrller</p>
 * <p>Description: 网页静态化处理Controller</p>
 * @version 1.0
 * @see
 * @since 22:25 2018/9/27
 */
@Controller
public class HtmlGenContrller {
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping("/genhtml")
    @ResponseBody
    public String genHtml() throws IOException, TemplateException {
        // 生成静态页面
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("hello.ftl");
        Map data = new HashMap<>();
        data.put("hello", "spring freemarker test");
        Writer out = new FileWriter(new File("D:\\WebBackEnd\\Taotaoparent\\Taotao-item-web\\src\\main\\webapp\\out\\hello.txt"));
        template.process(data,out);
        // 返回结果
        return "OK";
    }
}

```