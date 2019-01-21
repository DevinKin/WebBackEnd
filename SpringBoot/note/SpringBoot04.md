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

  