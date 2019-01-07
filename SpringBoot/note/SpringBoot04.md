# SpringBoot与Web开发

- 使用SpringBoot：
  - 创建SpringBoot应用，选中我们需要的模块。
  - SpringBoot已经默认将这些场景配置好了。只需要在配置文件中指定少量配置就可以运行起来。
  - 自己编写业务代码。

##  SpringBoot对静态资源的映射规则

- `WebMvcAutoConfiguration`对静态资源配置的代码如下

```java
public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!this.resourceProperties.isAddMappings()) {
            logger.debug("Default resource handling disabled");
        } else {
            Integer cachePeriod = this.resourceProperties.getCachePeriod();
            if (!registry.hasMappingForPattern("/webjars/**")) {
               this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"}).setCachePeriod(cachePeriod));
                }

            String staticPathPattern = this.mvcProperties.getStaticPathPattern();
            if (!registry.hasMappingForPattern(staticPathPattern)) {
                    this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{staticPathPattern}).addResourceLocations(this.resourceProperties.getStaticLocations()).setCachePeriod(cachePeriod));
                }

            }
        }
        @Bean
        public WebMvcAutoConfiguration.WelcomePageHandlerMapping welcomePageHandlerMapping(ResourceProperties resourceProperties) {
            return new WebMvcAutoConfiguration.WelcomePageHandlerMapping(resourceProperties.getWelcomePage(), this.mvcProperties.getStaticPathPattern());
        }
```

### webjars目录下的资源映射

- 所有`/webjars/**`，都去`classpath:/META-INF/resources/webjars`找资源。

  - `webjar`：以jar包方式引入静态资源。

  - http://www.webjars.org/

  - 引入jquery模块

    ```xml
            <!-- 引入jquery-webjar模块 -->
            <dependency>
                <groupId>org.webjars</groupId>
                <artifactId>jquery</artifactId>
                <version>3.3.1</version>
            </dependency>
    ```

  - 访问webjars下的资源：`http://localhost:8080/webjars/jquery/3.3.1/jquery.js`

  - `ResourceProperties`类可以设置和静态资源相关的参数，缓存时间等。

  ![](E:\JavaCode\WebBackEnd\SpringBoot\note\img\webjar.png)

### 当前项目下的任何资源

- 在代码块`this.resourceProperties.getStaticLocations()`中找到下列静态资源路径。

- `/**`访问的当前项目的任何资源（静态资源的文件夹）

  ```java
  //WebMvcProperties
  this.staticPathPattern = "/**";
  ```

  ```java
  //ResourcesProperties
  this.staticLocations = RESOURCE_LOCATIONS;
  private static final String[] CLASSPATH_RESOURCE_LOCATIONS = new String[]{"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"};
  ```


### 配置欢迎页（首页）映射

- 欢迎页：静态资源文件夹下的所有`index.html`页面。`resourceProperties.getWelcomePage()`。
- 欢迎页：被`/**`映射。`this.mvcProperties.getStaticPathPattern()`。

### 配置网页的图标

- 示例代码块

  ```java
  // WebMvcProperties
          @Configuration
          @ConditionalOnProperty(
              value = {"spring.mvc.favicon.enabled"},
              matchIfMissing = true
          )
          public static class FaviconConfiguration {
              private final ResourceProperties resourceProperties;
  
              public FaviconConfiguration(ResourceProperties resourceProperties) {
                  this.resourceProperties = resourceProperties;
              }
  
              @Bean
              public SimpleUrlHandlerMapping faviconHandlerMapping() {
                  SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
                  mapping.setOrder(-2147483647);
                  mapping.setUrlMap(Collections.singletonMap("**/favicon.ico", this.faviconRequestHandler()));
                  return mapping;
              }
  
              @Bean
              public ResourceHttpRequestHandler faviconRequestHandler() {
                  ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
                  requestHandler.setLocations(this.resourceProperties.getFaviconLocations());
                  return requestHandler;
              }
          }
  ```

- 所有的`**/favicon.ico`都是在静态资源文件夹下找。

### 在配置文件中自定义静态资源文件夹的路径

```yaml
spring:
  resources:
    static-locations: classpath:/hello/,classpath:/devinkin/
```

## 模板引擎

- 常用的模板引擎
  - JSP
  - Velocity
  - Freemarker
  - Thymeleaf
- SpringBoot推荐使用Thymeleaf模板引擎，语法更简单，功能更强大。

### SpringBoot中使用thymeleaf

- 引入thymeleaf依赖

  ```xml
  <properties>
  	<thymeleaf.version>3.0.2.RELEASE</thymeleaf.version>
      <!-- 布局功能的支持程序 thymeleaf3主程序使用layout2以上版本，thymeleaf2主程序使用layout1以上的版本。 -->
      <thymeleaf-layout-dialect.version>2.1.1</thymeleaf-layout-dialect.version>
  </properties>
  <dependency>
  	<groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-thymeleaf</artifactId>
  </dependency>
  ```

### Thymeleaf使用

```java
public class ThymeleafProperties {
    private static final Charset DEFAULT_ENCODING = Charset.forName("UTF-8");
    private static final MimeType DEFAULT_CONTENT_TYPE = MimeType.valueOf("text/html");
    public static final String DEFAULT_PREFIX = "classpath:/templates/";
    public static final String DEFAULT_SUFFIX = ".html";
	...
}
```

- 只要把html页面放在类路径下的templates，thymeleaf就能自动渲染了。

- 导入thymeleaf名称空间

  ```xml
  <html lang="en" xmlns:th="http://www.thymeleaf.org">
  ```

### Thymeleaf语法规则

- `th:text`：改变当前元素里面的文本内容。

- `th:html任意属性`：替换原生属性的值。

- 优先级从1-9，从高到低

  | Order |                       Feature                       |
  | :---: | :-------------------------------------------------: |
  |   1   |            Fragment inclusion(片段包含)             |
  |   2   |            Fragment iteration(片段迭代)             |
  |   3   |          Conditional evaluation(条件判断)           |
  |   4   |       Local variable definition(局部变量定义)       |
  |   5   |    General attribute modification(任意属性修改)     |
  |   6   | Specific attribute modification(修改指定属性默认值) |
  |   7   |               Text（修改标签体内容）                |
  |   8   |         Fragment specification（片段声明）          |
  |   9   |            Fragment removal（片段移除）             |

- 表达式语法

  - Simple Expressions
    - Variable Expressions：`${...}`，获取变量值，OGNL表达式。
      - 获取对象的属性，调用方法
      - 使用内置的基本对象
        - `#ctx`：the context object
        - `#vars`：the context variables
        - `#locale`：the context locale
        - `#request`：(only in Web Contexts) the HttpServletRequest object
        - `#response`：(only in Web Contexts) the HttpServletResponse object
        - `#session`：(only in Web Contexts) the HttpSession object
        - `#servletContext`：(only in WebContext) the ServletContext object
      - 内置的一些工具对象（具体看thymeleaf官方文档）
    - Section Variable Expressions：`*{...}`，选择表达式。功能上和`${...}`是 一样的，新增了配合`th:object=${session.user}`，获取user的属性，则可以使用`*{name}`即可。
    - Message Expression：`#{...}`，获取国际化内容。
    - Link URL Expressions：`@{...}`，定义URL连接。`@{/order/process(execId=${execId},execType='FAST')}`
    - Fragment Expression：`~{...}`，片段引用表达式。`<div th:insert="~{commons :: main}">...</div>`
  - Literals（字面量）
    - Text literals：'one text'，'Another one!'，...
    - Number literals：0，34，3.0，12.3，...
    - Boolean literals：true，false
    - Null literal：null
    - Literal tokens：one，sometext，main，...
  - Text operations（文本操作）：
    - String concatenation：+
    - Literal substitutions：`|The name is ${name}|`
  - Arithmetic operations（数学运算）：
    - Binary operators：+，-，*，/，%
    - Minus sign(unary operator)：-
  - Boolean operations（布尔运算）：
    - Binary operators：and，or
    - Boolean negation(unary operator)：!，not
  - Comparisons and equality（比较运算）：
    - Comparators：>，<，>=，<=，（gt，lt，ge，le）
    - Equality operators：==，!=，（eq ne）
  - Conditional operators（条件操作符）：
    - If-then：(if)?(then)
    - If-then-else：(if)?(then):else
    - Default：(value)?:(defaultvalue)

### Thymeleaf使用示例

- 测试代码

  ```java
      @RequestMapping("/success")
      public String success(Map<String,Object> map) {
          // classpath:/templates/success.html
          map.put("hello", "<h1>你好</h1>");
          map.put("users", Arrays.asList("zhangsan", "lisi", "wangwu"));
          return "success";
      }
  ```

  ```html
  <!DOCTYPE html>
  <html lang="en" xmlns:th="http://www.thymeleaf.org">
  <head>
      <meta charset="UTF-8">
      <title>Title</title>
  </head>
  <body>
  <h1>成功</h1>
  <!-- th:text 将div里面的文本内容设置为指定的值 -->
  <div id="div01" class="myDiv" th:id="${hello}" th:class="${hello}" th:text="${hello}">这是显示欢迎信息!</div>
  
  <div th:text="${hello}"></div>
  <div th:utext="${hello}"></div>
  <hr/>
  <!-- th:each 每次遍历都会生成当前这个标签 -->
  <h4 th:text="${user}" th:each="user : ${users}"></h4>
  <hr/>
  <h4>
      <span th:each="user : ${users}">[[${user}]]</span>
  </h4>
  </body>
  </html>
  ```

## SpringBoot与SpringMVC自动配置



  ​																																						