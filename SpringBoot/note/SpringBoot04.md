# SpringBoot与Web开发

- 使用SpringBoot：
  - 创建SpringBoot应用，选中我们需要的模块。
  - SpringBoot已经默认将这些场景配置好了。只需要在配置文件中指定少量配置就可以运行起来。
  - 自己编写业务代码。

## SpringBoot对静态资源的映射规则

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

- 以下是SpringBoot对SpringMVC的默认自动配置

  - Inclusion of`ContentNegotiatingViewResolver`and`BeanNameViewResolver`beans。

    - 自动配置了ViewResolver（视图解析器：根据方法的返回值得到视图对象（View），视图对象决定如何渲染（转发/重定向））
    - `ContentNegotiatingViewResolver`组合了所有的视图解析器。
    - 如何定制：可以给容器中添加一个视图解析器组件`@Bean`，自动的将其组合起来。

  - Support for serving static resources, including support for Webjars。

    - 静态资源文件夹路径，webjars。

  - Static`index.html` support。

    - 静态首页访问

  - Custom`Favicon` support

  - `Converter`，`GenericConverter`，`Formatter`beans。

    - 自动注册了转化器，格式化器，

    - 转换器：用于类型转换。

    - 格式化器：格式化日期等。在配置文件中配置日期的格式。

      ```java
              @Bean
              @ConditionalOnProperty(
                  prefix = "spring.mvc",
                  name = {"date-format"}
              )
              public Formatter<Date> dateFormatter() {
                  return new DateFormatter(this.mvcProperties.getDateFormat());
              }
      ```

    - 自己添加的格式化器和转换器，我们只需放在容器中即可。

  - Support for `HttpMessageConverters`

    - SpringMVC用来转换Http请求和响应的：User-json。
    - `HttpMessageConverters`是从容器中确定的，获取所有的HttpMessageConverter。
    - 自定义`HttpMessageConverter`，只需要将自己的组件注册到容器中。`@Bean，@Component`

  - Automatic registration of `Message<CodesResolver`

    - 定义错误代码生成规则。

  - Automatic use of a `ConfigurableWebBindingInitializer` bean

    - 可以配置一个`ConfigurableWebBindingInitializer`来替换默认的（添加到容器中）。

  - web的所有自动配置场景：`org.springframework.boot.autoconfigure.web`

### 扩展SpringMVC

- 添加拦截器

  ```xml
  <mvc:view-controller path="/hello" view-name="success"/>
  <mvc:interceptors>
  	<mvc:interceptor>
      	<mvc:mapping path="/hello"/>
          <bean></bean>
      </mvc:interceptor>
  </mvc:interceptors>
  ```

- 编写一个配置类（`@Configuration`），是WebMvcConfigurerAdapter类型，不能标注`@EnableWebMvc`。

- 既保留了所有的自动配置，也能使用扩展的配置

  ```java
  package com.devinkin.springboot.config;
  
  import org.springframework.context.annotation.Configuration;
  import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
  import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
  
  // 使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
  @Configuration
  public class MyMvcConfig extends WebMvcConfigurerAdapter {
      @Override
      public void addViewControllers(ViewControllerRegistry registry) {
          // 浏览器发送/devinkin请求，来到/success
          registry.addViewController("/devinkin").setViewName("success");
      }
  }
  ```

- 原理

  - `WebMvcAutoConfiguration`是SpringMVC的自动配置类。

  - 在做其他自动配置时会导入：`@Import(EnableWebMvcConfiguration.class)`

    ```java
        @Configuration
        public static class EnableWebMvcConfiguration extends DelegatingWebMvcConfiguration {
            
        //DelegatingWebMvcConfiguration：从容器中获取所有的WebMvcConfigurer
        @Autowired(
            required = false
        )
        public void setConfigurers(List<WebMvcConfigurer> configurers) {
            if (!CollectionUtils.isEmpty(configurers)) {
                this.configurers.addWebMvcConfigurers(configurers);
            }
    
        }
    ```

### 全面接管SpringMVC

- SpringBoot对SpringMVC的自动配置不需要了，所有都是我们自己配置。

- 我们需要在配置类中添加`@EnableWebMvc`即可。

  ```java
  package com.devinkin.springboot.config;
  
  import org.springframework.context.annotation.Configuration;
  import org.springframework.web.servlet.config.annotation.EnableWebMvc;
  import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
  import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
  
  // 使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
  @EnableWebMvc
  @Configuration
  public class MyMvcConfig extends WebMvcConfigurerAdapter {
      @Override
      public void addViewControllers(ViewControllerRegistry registry) {
          // 浏览器发送/devinkin请求，来到/success
          registry.addViewController("/devinkin").setViewName("success");
      }
  }
  ```

- `@EnableWebMvc`原理：

  - 第一步

    ```java
    @Import({DelegatingWebMvcConfiguration.class})
    public @interface EnableWebMvc {
    ```

  - 第二步

    ```java
    @Configuration
    public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {
    ```

  - 第三步

    ```java
    @Configuration
    @ConditionalOnWebApplication
    @ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurerAdapter.class})
    // 容器中没有这个组件的时候，这个自动配置类才生效。
    // 因为@EnableWebMvc导入了DelegatingWebMvcConfiguration，而
    // DelegatingWebMvcConfiguration继承了WebMvcConfigurationSupport,
    // 即已经导入了WebMvcConfigurationSupport这个类
    // 即在自动配置类在此处判断失效，自动配置失效。
    // 导入的WebMvcConfigurationSupport只是SpringMVC最基本的功能。
    @ConditionalOnMissingBean({WebMvcConfigurationSupport.class})
    @AutoConfigureOrder(-2147483638)
    @AutoConfigureAfter({DispatcherServletAutoConfiguration.class, ValidationAutoConfiguration.class})
    public class WebMvcAutoConfiguration {
    ```

    

### 如何修改SpringBoot的默认配置

- 模式
  - SpringBoot在自动配置很多组件的时候，先看容器中有没有用户自己配置的（@Bean，@Component）如果有，就用用户配置的，如果没有，才自动配置。如果有些组件可以有多个（ViewResolver），将用户配置的和自己默认的组合起来。
- 在SpringBoot中会有非常多的`xxxConfigurer`帮助我们进行扩展配置。
- 在SpringBoot中会有很多的`xxxCustomizer`帮助我们进行定制配置。

## RestfulCRUD

- 默认访问到首页																													

### 国际化

- 编写国际化配置文件。
- 使用ResourceBundleMessageSource管理国际化资源文件。
- 在页面使用`fmt:message`取出国际化内容。

#### 步骤

- 编写国际化配置文件，抽取页面需要显示的国际化消息。

- SpringBoot自动配置好了管理国际化资源文件的组件。

  ```java
  @ConfigurationProperties(
      prefix = "spring.messages"
  )
  public class MessageSourceAutoConfiguration {
      // 我们的配置文件可以直接放在类路径下，叫messages.properties
      private String basename = "messages";
      	@Bean
  	public MessageSource messageSource() {
  		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
  		if (StringUtils.hasText(this.basename)) {
  	// 设置国际化资源文件的基础名（去掉语言国家代码）	
              messageSource.setBasenames(StringUtils.commaDelimitedListToStringArray(
  					StringUtils.trimAllWhitespace(this.basename)));
  		}
  		if (this.encoding != null) {
  			messageSource.setDefaultEncoding(this.encoding.name());
  		}
  		messageSource.setFallbackToSystemLocale(this.fallbackToSystemLocale);
  		messageSource.setCacheSeconds(this.cacheSeconds);
  		messageSource.setAlwaysUseMessageFormat(this.alwaysUseMessageFormat);
  		return messageSource;
  	}
  ```

- 去页面获取国际化的值。

  - 修改Idea的File Encoding配置。

国际化原理

- 国际化Locale（区域信息对象）：LocaleResolver（获取区域信息对象）

  ```java
          @Bean
          @ConditionalOnMissingBean
          @ConditionalOnProperty(
              prefix = "spring.mvc",
              name = {"locale"}
          )
          public LocaleResolver localeResolver() {
              if (this.mvcProperties.getLocaleResolver() == org.springframework.boot.autoconfigure.web.WebMvcProperties.LocaleResolver.FIXED) {
                  return new FixedLocaleResolver(this.mvcProperties.getLocale());
              } else {
                  AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
                  localeResolver.setDefaultLocale(this.mvcProperties.getLocale());
                  return localeResolver;
              }
          }
  
  public Locale resolveLocale(HttpServletRequest request) {
      ...
      if (defaultLocale != null && request.getHeader("Accept-Language") == null) {
      return defaultLocale;
  }
  ```

- 默认的解析器根据请求头带来的区域信息获取Locale进行国际化。

- 自己编写解析器，实现点击链接切换国际化信息。

  ```java
  package com.devinkin.springboot.component;
  
  import org.springframework.web.servlet.LocaleResolver;
  import org.thymeleaf.util.StringUtils;
  
  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;
  import java.util.Locale;
  
  /**
   * 可以在链接上携带区域信息
   */
  public class MyLocaleResolver implements LocaleResolver {
      @Override
      public Locale resolveLocale(HttpServletRequest httpServletRequest) {
          String l = httpServletRequest.getParameter("l");
          Locale locale = Locale.getDefault();
          if (!StringUtils.isEmpty(l)) {
              String[] s = l.split("_");
              locale = new Locale(s[0], s[1]);
          }
          return locale;
      }
  
      @Override
      public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {
  
      }
  }
  
  // 在MyMvcConfig类中添加组件
      @Bean
      public LocaleResolver localeResolver() {
          return new MyLocaleResolver();
      }
  ```

### 登录

- 开发时模板引擎页面修改以后，要实时生效。

  - 禁用模板引擎的缓存。

    ```yaml
    spring:
      thymeleaf:
        cache: false
    ```

    

  - 页面修改完成后，按`Ctrl+F9`重新编译。

- 登录错误消息的显示

  ```html
  <p style="color: red" th:text="${msg}" th:if="${not #strings.isEmpty(msg)}"></p>
  ```

### 拦截器进行登录检查

- 编写拦截器组件

  ```java
  package com.devinkin.springboot.component;
  
  import org.springframework.web.servlet.HandlerInterceptor;
  import org.springframework.web.servlet.ModelAndView;
  
  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;
  
  
  /**
   * 登录检查：
   */
  public class LoginHandlerInterceptor implements HandlerInterceptor {
      // 目标方法执行之前
      @Override
      public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
          Object user = httpServletRequest.getSession().getAttribute("loginUser");
          if (user == null) {
              // 未登录，返回登录页面
              httpServletRequest.setAttribute("msg", "请先登录!");
              httpServletRequest.getRequestDispatcher("/index.html").forward(httpServletRequest,httpServletResponse);
              return false;
          } else {
              // 已登录，放行请求
              return true;
          }
      }
  
      @Override
      public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
  
      }
  
      @Override
      public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
  
      }
  }
  ```

- 在Config类中添加拦截器组件

  ```java
      // 所有的WebMvcConfigurerAdapter组件都会一起起作用
      @Bean       // 将组件注册到容器中
      public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
          WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
              @Override
              public void addViewControllers(ViewControllerRegistry registry) {
                  registry.addViewController("/").setViewName("login");
                  registry.addViewController("/index.html").setViewName("login");
                  registry.addViewController("/main.html").setViewName("dashboard");
              }
  
              @Override
              public void addInterceptors(InterceptorRegistry registry) {
  //                super.addInterceptors(registry);
                  // 静态资源：*.css，*.js
                  // SpringBoot已经做好了静态资源映射
                  registry.addInterceptor(new LoginHandlerInterceptor())
                          .addPathPatterns("/**")
                          .excludePathPatterns("/index.html", "/", "/user/login");
              }
          };
          return adapter;
      }
  ```

### CRUD-员工列表

- RestfulCRUD：CRUD满足Rest风格
  - URI：`/资源名称/资源表示`，HTTP请求方式区分对资源CRUD操作

|      | 普通CRUD(uri来区分操作) |   RestfulCRUD   |
| :--: | :---------------------: | :-------------: |
| 查询 |         getEmp          |    emp--GET     |
| 添加 |       addEmp?xxx        |    emp--POST    |
| 修改 | updateEmp?Id=xxx&xxx=xx |  emp/{id}--PUT  |
| 删除 |     deleteEmp?id=1      | emp{id}--DELETE |

- 实验的请求架构

|                        | 请求URI  | 请求方式 |
| :--------------------: | :------: | :------: |
|      查询所有员工      |   emps   |   GET    |
|      查询某个员工      | emp/{id} |   GET    |
|      来到添加页面      |   emp    |   GET    |
|        添加员工        |   emp    |   POST   |
| 来到修改页面(信息回显) | emp/{id} |   GET    |
|        修改员工        |   emp    |   PUT    |
|        删除员工        | emp/{id} |  DELETE  |

- 员工列表：使用thymeleaf公共页面元素抽取

  - 抽取公共片段

    ```html
    <th:fragment="topbar">
    ```

  - 引入公共片段

    ```html
    <!-- 引入抽取的topbar -->
    <!-- 模板名：会使用thymeleaf的前后缀配置规则进行解析 -->
    <div th:insert="~{dashboard::topbar}"></div>
    ```

  - 三种引入功能片段的th属性

    - `th:insert`：将公共片段整个插入到声明引入的元素中。
    - `th:replace`：将声明引入的元素替换为公共片段。
    - `th:include`：将被引入的片段的内容包含到这个标签中。

- 引入片段的时候传入参数

  ```html
  <div th:replace="~{commons/bar::#sidebar(activeUri='emps')}"></div>
  <div th:replace="~{commons/bar::#sidebar(activeUri='main.html')}"></div>
  ```


- 提交的数据格式不对，需要自己编写

  - 日期类型的格式化与类型转换。

  ```yaml
  spring:  
    mvc:
      date-format: yyyy-MM-dd
  ```

## SpringBoot错误处理机制

### SpringBoot默认的错误处理机制

- 如果是浏览器，返回一个默认的错误页面。

- 如果是其他客户端，默认响应一个json数据。

- 原理：参考`WebMvcAutoConfiguration`，错误处理的自动配置。

  - 给容器中添加一下组件
    - `DefaultErrorAttributes`
    - `BasicErrorController`
    - `ErrorPageCustomizer`
    - `DefaultErrorViewResolver`

- `ErrorPageCustomizer`定制错误的响应规则。系统出现错误后来到error请求进行处理（web.xml注册的错误页面规则）

  ```java
  public class ErrorProperties {	
  	...
  	@Value("${error.path:/error}")
  	private String path = "/error";
  }
  ```

- `BasicErrorController`处理默认`/error`请求

  ```java
  @Controller
  @RequestMapping("${server.error.path:${error.path:/error}}")
  public class BasicErrorController extends AbstractErrorController {
      ...
      @RequestMapping(produces = "text/html")		//产生html类型数据，处理浏览器发送的请求
  	public ModelAndView errorHtml(HttpServletRequest request,
  			HttpServletResponse response) {
  		HttpStatus status = getStatus(request);
  		Map<String, Object> model = Collections.unmodifiableMap(getErrorAttributes(
  				request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
  		response.setStatus(status.value());
          // 去哪个页面作为错误页面，包含页面地址和页面内容
  		ModelAndView modelAndView = resolveErrorView(request, response, status, model);
  		return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
  	}
  
  	@RequestMapping		
  	@ResponseBody		// 产生json数据，处理其他客户端发送的请求。
  	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
  		Map<String, Object> body = getErrorAttributes(request,
  				isIncludeStackTrace(request, MediaType.ALL));
  		HttpStatus status = getStatus(request);
  		return new ResponseEntity<Map<String, Object>>(body, status);
  	}
  }
  ```

  - 响应页面

    ```java
    protected ModelAndView resolveErrorView(HttpServletRequest request,
    		HttpServletResponse response, HttpStatus status, Map<String, Object> model) {
        // 所有的ErrorViewResolver得到ModelAndView
    	for (ErrorViewResolver resolver : this.errorViewResolvers) {
    		ModelAndView modelAndView = resolver.resolveErrorView(request, status, model);
    		if (modelAndView != null) {
    			return modelAndView;
    		}
    	}
    	return null;
    }
    ```

- `DefaultErrorViewResolver`决定去哪个错误页面

  ```java
  @Override
  public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status,Map<String, Object> model) {
  	ModelAndView modelAndView = resolve(String.valueOf(status), model);
  	if (modelAndView == null && SERIES_VIEWS.containsKey(status.series())) {
  		modelAndView = resolve(SERIES_VIEWS.get(status.series()), model);
  	}
  	return modelAndView;
  }
  
  private ModelAndView resolve(String viewName, Map<String, Object> model) {
      // 默认SpringBoot可以去找到一个页面 error/404
  	String errorViewName = "error/" + viewName;
      // 模板引擎可以解析这个页面地址就用模板引擎解析
  	TemplateAvailabilityProvider provider = this.templateAvailabilityProviders.getProvider(errorViewName, this.applicationContext);
  	if (provider != null) {
          // 模板引擎可用的情况下返回到errorViewName指定的视图地址
  		return new ModelAndView(errorViewName, model);
  	}
      // 模板引擎不可用，就在静态资源文件夹下找errorViewName对应的页面
  	return resolveResource(errorViewName, model);
  }
  ```

- `DefaultErrorAttributes`帮我们在错误页面共享信息

  - 能获取的信息
    - timestamp：时间戳
    - status：状态码
    - error：错误提示
    - exception：异常对象
    - errors：JSR303数据校验的错误

  ```java
  @Override
  public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes,boolean includeStackTrace) {
  	Map<String, Object> errorAttributes = new LinkedHashMap<String, Object>();
  	errorAttributes.put("timestamp", new Date());
  	addStatus(errorAttributes, requestAttributes);
  	addErrorDetails(errorAttributes, requestAttributes, includeStackTrace);
  	addPath(errorAttributes, requestAttributes);
  	return errorAttributes;
  }
  ```

### 如何定制错误响应

#### 如何定制错误的页面

- 有模板引擎的情况下，在模板引擎文件夹下的error文件夹放入错误页面，命名为`错误状态码.html`。发生此状态码的错误就会来到对应的页面。可以使用4xx和5xx作为错误页面的文件名来匹配这种类型的所有错误，精确优先（优先寻找精确的状态码.html）
- 模板引擎找不到错误页面，在静态资源文件夹下找。
- 以上都没有错误页面，就是默认来到SpringBoot默认的错误提示页面。（在BaseErrorController中找到对应的逻辑）

#### 如何定制错误的json数据

- 自定义异常处理，返回固定的json数据。没有自适应效果，即没有错误页面显示。

  ```java
  package com.devinkin.springboot.controller;
  
  import com.devinkin.springboot.exception.UserNotExistException;
  import org.springframework.web.bind.annotation.ControllerAdvice;
  import org.springframework.web.bind.annotation.ExceptionHandler;
  import org.springframework.web.bind.annotation.ResponseBody;
  
  import java.util.HashMap;
  import java.util.Map;
  
  @ControllerAdvice
  @ResponseBody
  public class MyExceptionHandler {
      @ResponseBody
      @ExceptionHandler(UserNotExistException.class)
      public Map<String, Object> handleException(Exception e) {
          Map<String, Object> map = new HashMap<>();
          map.put("code", "user.not exist");
          map.put("message", e.getMessage());
          return map;
      }
  }
  ```

- 请求转发到error进行自适应响应效果处理

  ```java
      @ExceptionHandler(UserNotExistException.class)
      public String handleException(Exception e, HttpServletRequest request) {
          Map<String, Object> map = new HashMap<>();
          // 传入我们自己的错误状态码，默认是200
          /**
           * Integer statusCode = (Integer)request
           * .getAttribute("javax.servlet.error.status_code");
           */
          request.setAttribute("javax.servlet.error.status_code", 500);
          map.put("code", "user.not exist");
          map.put("message", e.getMessage());
          // 转发到error
          return "forward:/error";
      }
  ```

- 请求转发到error并进行自适应效果，并且将我们定制的数据携带出去。

  - 出现错误以后，会来到`/error`请求，会被`BasicErrorController`处理，响应出去可以获取的数据是由`getErrorAttributes()`方法得到的。该方法是`AbstractErrorController`提供的。
  - 完全编写一个ErrorController实现类（或者是编写AbstractErrorController子类），放在容器中。
  - 页面上能用的数据或者是json返回的能用的数据，都是通过`errorAttributes.getErrorAttributes()`获得的。容器中默认使用`DefaultErrorAttributes::getErrorAttributes()`默认进行数据处理的。

  ```java
  package com.devinkin.springboot.component;
  
  import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
  import org.springframework.web.context.request.RequestAttributes;
  
  import java.util.Map;
  
  // 给容器中加入我们自己定义的ErrorAttributes
  public class MyErrorAttributes extends DefaultErrorAttributes {
      // 返回值的map就是页面和json能获取的所有字段
      @Override
      public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
          Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
          errorAttributes.put("company", "devinkin");
          // 我们的异常处理器携带的数据
          Map<String, Object> ext = (Map<String, Object>) requestAttributes.getAttribute("ext", 0);
          errorAttributes.put("ext", ext);
          return errorAttributes;
      }
  }
  ```


## 配置嵌入式Servlet容器

- SpringBoot默认使用Tomcat作为嵌入式的Servlet容器。

### 定制和修改Servlet容器相关配置

- 修改与server相关的配置（ServerProperties）

```yaml
server:
	port: 8081
server:
	context:
		path: /crud
# 通用的Servlet容器设置
server:
	xxx:
# Tomcat的设置
server:
	tomcat:
    	uri-encoding: UTF-8:
```

- 编写一个`EmbeddedServletContainerCustomizer`嵌入式Servlet容器的定制器，来修改Servlet容器的配置。

  ```java
  @Bean
  public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
  	// 定制嵌入式的Servlet容器相关的规则
  	return new EmbeddedServletContainerCustomizer() {
  	@Override
  	public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
  		configurableEmbeddedServletContainer.setPort(8083);
  		}
  	};
  }
  ```

### SpringBoot支持的Servlet容器

- 注册三大组件
  - 注册Servlet：`ServletRegistrationBean`
  - 注册Filter：`FilterRegistrationBean`
  - 注册Listener：`ServletListenerRegistrationBean`

#### 注册Servlet

- 由于SpringBoot默认是以 jar包的方式启动嵌入式的Servlet容器来启动SpringBoot的web应用，没有`web.xml`文件，注册三大组件用以下方式。

```java
package com.devinkin.springboot.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyServlet extends HttpServlet {
    // 处理get请求
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    // 处理post请求

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       resp.getWriter().write("Hello MyServlet");
    }
}
```



```java
@Configuration
public class MyServerConfig {
    ...
    // 注册三大组件
    @Bean
    public ServletRegistrationBean myServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new MyServlet(), "/myServlet");
        return registrationBean;
    }
}
```

#### 注册Filter

```java
package com.devinkin.springboot.filter;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MyFilter process...");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
```

```java
    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new MyFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/hello", "/myServlet"));
        return registrationBean;
    }
```

#### 注册Listerner

```java
package com.devinkin.springboot.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("contextInitialized...web应用启动");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("contextDestroyed...当前web项目销毁");
    }
}
```

```java
@Bean
public ServletListenerRegistrationBean myListener() {
    return new ServletListenerRegistrationBean<MyListener>(new MyListener());
}
```

### 使用其他Servlet容器

- SpringBoot默认支持的容器有
  - Tomcat（默认使用）
  - Jetty
  - Undertow
- 在pom文件中去掉tomcat的依赖再切换为其他servlet容器的starter

#### 使用Jetty

- Jetty常用在常连接的场景。点对点聊天。

```xml
        <!-- 引入web模块 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                    <groupId>org.springframework.boot</groupId>
                </exclusion>
            </exclusions>
        </dependency>
        <!-- 引入其他的Servlet容器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jetty</artifactId>
        </dependency>
```

#### 使用Undertow

- Undertow不支持JSP，并发性能好。

```xml
        <!-- 引入web模块 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                    <groupId>org.springframework.boot</groupId>
                </exclusion>
            </exclusions>
        </dependency>
        <!-- 引入其他的Servlet容器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-undertow</artifactId>
        </dependency>
```

## 嵌入式Servlet容器自动配置原理

- EmbeddedServletContainerAutoConfiguration：配置嵌入式Servlet的容器自动配置

  ```java
  @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
  @Configuration
  @ConditionalOnWebApplication
  // 导入了BeanPostProcessorsRegistrar，作用：给容器中导入一些组件
  // 导入了EmbeddedServletContainerCustomizerBeanPostProcessor
  // 后置处理器：bean初始化前后（刚创建完对象，还没给属性赋值）执行初始化工作。
  @Import(BeanPostProcessorsRegistrar.class)
  public class EmbeddedServletContainerAutoConfiguration {
     	@Configuration
      // 判断当前是否引入了tomcat依赖
  	@ConditionalOnClass({ Servlet.class, Tomcat.class })
      // 判断当前容器没有用户自己定义EmbeddedServletContainerFactory，嵌入式的Servlet容器工厂，作用：创建嵌入式的Servlet容器。
  	@ConditionalOnMissingBean(value = EmbeddedServletContainerFactory.class, search = SearchStrategy.CURRENT)
  	public static class EmbeddedTomcat {
  
  		@Bean
  		public TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() {
  			return new TomcatEmbeddedServletContainerFactory();
  		}
  
  	}
  ```

- `EmbeddedServletContainerFactory`嵌入式Servlet容器工厂

  ```java
  public interface EmbeddedServletContainerFactory {
      // 获取嵌入式的Servlet容器
      EmbeddedServletContainer 	getEmbeddedServletContainer(ServletContextInitializer... var1);
  }
  ```

  ![continer](.\img\continer.png)

- `EmbeddServletContainer`嵌入式的Servlet容器

  ![](.\img\factory.png)

- 以`TomcatEmbeddedServletContainerFactory`为例

  ```java
  public EmbeddedServletContainer getEmbeddedServletContainer(ServletContextInitializer... initializers) {
      // 创建一个Tomcat
      Tomcat tomcat = new Tomcat();
      // 配置Tomcat的基本环境
      File baseDir = this.baseDirectory != null ? this.baseDirectory : this.createTempDir("tomcat");
      tomcat.setBaseDir(baseDir.getAbsolutePath());
      Connector connector = new Connector(this.protocol);
      tomcat.getService().addConnector(connector);
      this.customizeConnector(connector);
      tomcat.setConnector(connector);
      tomcat.getHost().setAutoDeploy(false);
      this.configureEngine(tomcat.getEngine());
      Iterator var5 = this.additionalTomcatConnectors.iterator();
  
      while(var5.hasNext()) {
          Connector additionalConnector = (Connector)var5.next();
          tomcat.getService().addConnector(additionalConnector);
      }
  
      this.prepareContext(tomcat.getHost(), initializers);
      // 将配置好的Tomcat传入，返回一个EmbeddedServletContainer，并且启动tomcat服务器
      return this.getTomcatEmbeddedServletContainer(tomcat);
  }
  ```

- 我们对嵌入式容器配置的修改生效追踪

  - 修改配置的方式有

    - ServerProperties
    - EmbeddedServletContainerCustomizer

  - `EmbeddedServletContainerCustomizer`定制器帮我们修改了Servlet容器的配置。

  - `ServerProperties`继承了`EmbeddedServletContainerCustomizer`，也是定制器。

  - 容器中导入了`EmbeddedServletContainerCustomizerBeanPostProcessor`

    ```java
    //初始化之前
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        // 如果当前初始化是一个ConfigurableEmbeddedServletContainer类型的组件
        if (bean instanceof ConfigurableEmbeddedServletContainer) {
    postProcessBeforeInitialization((ConfigurableEmbeddedServletContainer) bean);
        }
        return bean;
    }
    
    private void postProcessBeforeInitialization(
            ConfigurableEmbeddedServletContainer bean) {
        // 获取所有的定制器，调用每一个定制器的customizes方法来给Servlet容器进行属性赋值
        for (EmbeddedServletContainerCustomizer customizer : getCustomizers()) {
            customizer.customize(bean);
        }
    }
    
    private Collection<EmbeddedServletContainerCustomizer> getCustomizers() {
        if (this.customizers == null) {
            // Look up does not include the parent context
            // 从容器中获取所有这个类型的组件：EmbeddedServletContainerCustomizer
            this.customizers = new ArrayList<EmbeddedServletContainerCustomizer>(
                    this.beanFactory
                            .getBeansOfType(EmbeddedServletContainerCustomizer.class,
                                    false, false)
                            .values());
            Collections.sort(this.customizers, AnnotationAwareOrderComparator.INSTANCE);
            this.customizers = Collections.unmodifiableList(this.customizers);
        }
        return this.customizers;
    }
    ```

- 对嵌入式容器修改配置的流程

  - SpringBoot根据导入的依赖情况，给容器中添加相应的`EmbeddedServletContainerFactory`，默认使用`TomcatEmbeddedServletContainerFactory`
  - 容器中某个组件要创建对象就会使用后置处理器`EmbeddedServletContainerCustomizerBeanPostProcessor`，只要是嵌入式的Servlet容器工厂，后置处理器就工作。
  - 后置处理器，从容器中获取所有的`EmbeddedServletContainerCustomizer`定制器，调用定制器的定制方法

## 嵌入式Servlet容器启动原理

### 什么时候创建嵌入式Servlet容器工厂

- 获取嵌入式的Servlet容器工厂

  - SpringBoot应用启动运行run方法

  - `refreshContext(context)`SpringBoot刷新IOC容器（创建IOC容器对象，并初始化容器，创建容器中的每一个组件），如果是Web应用，创建的是`AnnotationConfigEmbeddedWebApplicationContext`，否则创建`AnnotationConfigApplicationContext`

  - `refresh(context)`刷新刚才创建好的IOC容器

    ```java
    public void refresh() throws BeansException, IllegalStateException {
        synchronized(this.startupShutdownMonitor) {
            this.prepareRefresh();
            ConfigurableListableBeanFactory beanFactory = this.obtainFreshBeanFactory();
            this.prepareBeanFactory(beanFactory);
    
            try {
                this.postProcessBeanFactory(beanFactory);
                this.invokeBeanFactoryPostProcessors(beanFactory);
                this.registerBeanPostProcessors(beanFactory);
                this.initMessageSource();
                this.initApplicationEventMulticaster();
                this.onRefresh();
                this.registerListeners();
                this.finishBeanFactoryInitialization(beanFactory);
                this.finishRefresh();
            } catch (BeansException var9) {
                if (this.logger.isWarnEnabled()) {
                    this.logger.warn("Exception encountered during context initialization - cancelling refresh attempt: " + var9);
                }
    
                this.destroyBeans();
                this.cancelRefresh(var9);
                throw var9;
            } finally {
                this.resetCommonCaches();
            }
    
        }
    }
    ```

  - `onRefresh()`web的IOC容器重写了`onRefresh()`方法

  - web的IOC容器会创建嵌入式的Servlet容器`createEmbeddedServletContainer();`

    - 获取嵌入式Servlet容器工厂`EmbeddedServletContainerFactory containerFactory = getEmbeddedServletContainerFactory();`
    - 从IOC容器中获取`EmbeddedServletContainerFactory `组件，`TomcatEmbeddedServletContainerFactory`创建对象，后置处理器会获取所有的定制器来先定制Servlet容器的相关配置。

  - 使用容器工厂获取嵌入式的Servlet容器：`this.embeddedServletContainer=containerFactory.getEmbeddedServletContainer(getSelfInitializer());`

  - 嵌入式的Servlet容器创建对象并启动Servlet容器。

- 先启动嵌入式的Servlet容器，再将IOC容器中剩下没有创建出来的对象获取出来。

- IOC容器创建嵌入式Servlet容器

## 使用外置的Servlet容器

- 嵌入式Servlet容器的优缺点（应用打包成可执行的jar包）

  - 优点
    - 简单，便捷。
  - 缺点
    - 默认不支持JSP，优化定制比较复杂（使用定制器ServerProperties，自定义EmbeddedServletContainerCustomizer，或者编写嵌入式Servlet容器的创建工厂）。

- 外置的Servlet容器：外置安装Tomcat——应用war包的方式打包。

- 使用外置Servlet容器的步骤

  - 必须创建一个war项目。（利用idea创建好目录结构）

  - 将嵌入式的Tomcat指定为provided。

    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
        <scope>provided</scope>
    </dependency>
    ```

  - 必须编写一个`SpringBootServletInitializer`的子类，并调用configure方法。

    ```java
    public class ServletInitializer extends SpringBootServletInitializer {
    
        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
            // 传入SpringBoot应用的主程序
            return application.sources(SpringBoot04WebJspApplication.class);
        }
    
    }
    ```

- 启动外置Servlet服务器就可以使用。

## 使用外部Servlet容器启动SpringBoot应用的原理

- jar包：执行SpringBoot主类的main方法，启动IOC容器，创建嵌入式的Servlet容器。

- war包：启动服务器，==服务器启动SpringBoot应用（SpringBootServletInitializer）==，启动IOC容器。

- Servlet3.0（Spring注解版）：8.2.4章

  - 规则：服务器启动（web应用启动）会创建当前web应用里面每一个jar包里的`ServletContainerInitializer`实例。
  - `ServletContainerInitializer`实例放在jar包的`META-INF/services`文件夹下，有一个名为`javax.servlet.ServletContainerInitializer`的文件，内容就是`ServletContainerInitializer`的实现类的全类名。
  - 还可以使用`@HandlesType`注解，在应用启动的时候加载我们感兴趣的类。

- 外部Servlet容器启动SpringBoot应用原理流程

  - 启动Tomcat

  - `spring-web\5.1.4.RELEASE\spring-web-5.1.4.RELEASE.jar!\META-INF\services\javax.servlet.ServletContainerInitializer`

  - Spring的web模块里面有这个文件：`org.springframework.web.SpringServletContainerInitializer`

  - `ServletContainerInitializer`将`@HandlesTypes({WebApplicationInitializer.class})`标注的所有这个类型的类传入到`onStartup()`方法的`Set<Class<?>>`，为`WebApplicationInitializer`类型的类创建实例。

  - 每一个`WebApplicationInitializer`都调用自己的`onStartup()`

  - 相当于`SpringBootServletInitializer`的类会被创建对象，并执行`onStartup()`方法

  - `SpringBootServletInitializer`实例执行`onStartup()`的时候会执行`createRootApplicationContext()`方法创建容器。

    ```java
    protected WebApplicationContext createRootApplicationContext(ServletContext servletContext) {
        // 1. 创建SpringBootApplicationBuilder
        SpringApplicationBuilder builder = this.createSpringApplicationBuilder();
        builder.main(this.getClass());
        ApplicationContext parent = this.getExistingRootWebApplicationContext(servletContext);
        if (parent != null) {
            this.logger.info("Root context already created (using as parent).");
            servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, (Object)null);
            builder.initializers(new ApplicationContextInitializer[]{new ParentContextApplicationContextInitializer(parent)});
        }
    
        builder.initializers(new ApplicationContextInitializer[]{new ServletContextApplicationContextInitializer(servletContext)});
        builder.contextClass(AnnotationConfigServletWebServerApplicationContext.class);
        // 调用configure方法，子类重写了这个方法，将SpringBoot的主程序类传入进来
        builder = this.configure(builder);
        builder.listeners(new ApplicationListener[]{new SpringBootServletInitializer.WebEnvironmentPropertySourceInitializer(servletContext)});
        // 使用builder创建一个Spring应用
        SpringApplication application = builder.build();
        if (application.getAllSources().isEmpty() && AnnotationUtils.findAnnotation(this.getClass(), Configuration.class) != null) {
            application.addPrimarySources(Collections.singleton(this.getClass()));
        }
    
        Assert.state(!application.getAllSources().isEmpty(), "No SpringApplication sources have been defined. Either override the configure method or add an @Configuration annotation");
        if (this.registerErrorPageFilter) {
            application.addPrimarySources(Collections.singleton(ErrorPageFilterConfiguration.class));
        }
    
        // 执行Spring应用
        return this.run(application);
    }
    ```

  - Spring的应用就启动，并且创建了IOC容器。