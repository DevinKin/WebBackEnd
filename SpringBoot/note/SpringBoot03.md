# SpringBoot与日志

- 市面上的日志框架
  - JUL（java.util.logging）
  - JCL（Apache Commons Logging）
  - Jboss-logging
  - Logback
  - Log4j
  - Log4j2
  - SLF4j
- 日志抽象层
  - JCL（Jakarta Commons Logging）
  - SLF4j（Simple Logging Facde for Java）
  - jboss-logging
- 日志实现
  - Log4j
  - JUL
  - Log4j2  
  - Logback
- 日志抽象层选择SLF4j，日志实现选择Logback
- Spring框架默认使用JCL日志抽象层。
- SpringBoot选用SLF4j日志抽象层和Logback日志实现。

## SLF4j使用

#### 如何再系统中使用SLF4j

- 以后开发的时候，日志记录方法的调用，不应该直接调用日志的实现类，而是调用日志抽象层的方法。

- 导入slf4j的jar包和logback的jar包

  ```java
  import org.slf4j.Logger;
  import org.slf4j.LoggerFactory;
  
  public class HelloWorld {
      public static void main(String[] args) {
          Logger logger = LoggerFactory.getLogger(HelloWorld.class);
          logger.info("Hello World");
      }
  }
  ```

- SLF4j使用图示

  ![](E:\JavaCode\WebBackEnd\SpringBoot\note\img\log1.png)

- 每一个日志的实现框架都有自己的配置文件。使用slf4j以后，**配置文件还是要根据日志实现框架本身需要的配置文件。**

## 遗留问题

- 不同框架使用不同的日志实现和日志抽象层，需要重新统一使用slf4j作为日志记录。

  ![](E:\JavaCode\WebBackEnd\SpringBoot\note\img\legacy.png)

- 如何让系统中的所有的日志都统一到slf4j：
  - 将系统中其他日志框架先排除出去。
  - 用中间包来替换原有的日志框架。
  - 导入slf4j其他的实现。

## SpringBoot日志关系

- SpringBoot使用`spring-boot-starter-logging`来做日志功能的。

- 使用logback作为日志实现。

- 总结

  - SpringBoot底层也是使用`slf4j+logback`的方式进行日志记录。

  - SpringBoot也把其他的日志都替换成了slf4j替换包。LogFactory被替换了

    ```java
    public abstract class LogFactory {
        static String UNSUPPORTED_OPERATION_IN_JCL_OVER_SLF4J = "http://www.slf4j.org/codes.html#unsupported_operation_in_jcl_over_slf4j";
        static LogFactory logFactory = new SLF4JLogFactory();
     	...
     }
    ```


  ![](E:\JavaCode\WebBackEnd\SpringBoot\note\img\log2.png)

- SpringBoot能自动适配所有的日志，而且底层使用`slf4j+logback`的方式记录日志，引入其他框架的时候，只需要把这个框架依赖的日志框架去除即可。

## 日志的使用

### 默认配置

- SpringBoot默认配置好了`slf4j+logback`。

- 日志的级别：trace<debug<info<warning<error。

- SpringBoot默认使用的是info级别的。不会输出trace和debug日志。

- 可在`application.yml`中调整输出日志级别。

  ```yaml
  logging:
    level:
      com.devinkin: trace
  ```

- 测试代码如下

  ```java
  package com.devinkin.springboot;
  
  import org.junit.Test;
  import org.junit.runner.RunWith;
  import org.slf4j.Logger;
  import org.slf4j.LoggerFactory;
  import org.springframework.boot.test.context.SpringBootTest;
  import org.springframework.test.context.junit4.SpringRunner;
  
  @RunWith(SpringRunner.class)
  @SpringBootTest
  public class SpringBoot03LoggingApplicationTests {
  
      //记录器
      Logger logger = LoggerFactory.getLogger(getClass());
      @Test
      public void contextLoads() {
          //日志的级别：可以进行调整
          // trace<debug<info<warn<error
          logger.trace("这是trace日志...");
          logger.debug("这是debug日志...");
          logger.info("这是info日志...");
          logger.warn("这是warn日志...");
          logger.error("这是error日志...");
      }
  
  }
  ```

### 指定日志输出的路径

- 在`application.yml`中配置`logging.path=xxx`中指定日志文件输出的路径。
- 默认使用`spring.log`作为默认的输出日志文件名。

### 指定日志输出的文件名

- 默认在当前项目的根目录下生成。
- 在`application.yml`中配置`logging.file=springboot.log`来指定输出日志文件名。

### 指定日志输出的格式

- 指定控制台输出的日志格式：`logging.pattern.console=%d{yyyy-MM-dd} [%thread] %-5level %logger{50} - %msg%n`

- 指定文件中日志输出的格式：`logging.pattern.file=%d{yyyy-MM-dd} [%thread] %-5level %logger{50} - %msg%n`

- 日志输出格式

  - `%d`表示日期时间。
  - `%thread`表示线程名。
  - `%-5level`级别从左显示5个字符宽度。
  - `%logger{50}`表示logger名字最长50个字符，否则按照句点分割。
  - `%msg`日志消息。
  - `%n`换行符。

### 使用示例

```yaml
logging:
  level:
    com.devinkin: trace
  path: E:\JavaCode\WebBackEnd\SpringBoot\spring-boot-03-logging\log2
  # 在控制台输出的日志的格式
  pattern:
    console: '%d{yyyy-MM-dd} [%thread] %-5level %logger{50} - %msg%n'
    file: '%d{yyyy-MM-dd} ==== [%thread] ==== %-5level ==== %logger{50} - %msg%n'
#  file: E:\JavaCode\WebBackEnd\SpringBoot\spring-boot-03-logging\log\springboot.log

```

## 指定日志配置文件

- 给类路径下放每个日志框架自己的配置文件即可。SpringBoot就会加载对应的日志配置文件。

|      Logging System      |                       Customization                       |
| :----------------------: | :-------------------------------------------------------: |
|         Logback          | logback-spring.xml，logback-spring.groovy，logback.groovy |
|          Log4j2          |                    log4j2-spring.xml，                    |
| JUL（Java Util Logging） |                    logging.properties                     |

- `logback.xml`直接被日志框架识别了，直接加载。

- `logback-spring.xml`日志框架就不直接加载日志的配置项，由SpringBoot加载该日志配置项。

  - 可以使用springboot的高级`springProfile`特性。在不同的配置环境下使用不同的配置。
  - 使用示例如下

  ```xml
  <springProfile name="dev">
       <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} ---> [%thread] ---> %-5level ---> %logger{50} - %msg%n</pattern>
  </springProfile>
  <springProfile name="!dev">
       <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} ==== [%thread] ==== %-5level ==== %logger{50} - %msg%n</pattern>
  </springProfile>
  ```

- SpringBoot设置环境为开发环境

  - 在`application.properties`中设置`spring.properties.active=dev`
  - 在命令行参数中指定`--spring.profiles.active=dev`

## 切换日志框架

### 切换为log4j日志框架

- 按照slf4j的日志是配图，进行相关的切换。

- slf4j+log4j的方式

  ```xml
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-web</artifactId>
              <exclusions>
                  <exclusion>
                      <artifactId>logback-classic</artifactId>
                      <groupId>ch.qos.logback</groupId>
                  </exclusion>
                  <exclusion>
                      <artifactId>log4j-over-slf4j</artifactId>
                      <groupId>org.slf4j</groupId>
                  </exclusion>
              </exclusions>
          </dependency>
  
          <dependency>
              <groupId>org.slf4j</groupId>
              <artifactId>slf4j-log4j12</artifactId>
          </dependency>
          
  ```

### 切换为logfj4日志框架

- 直接使用`spring-boot-starter-log4j2`依赖即可。

  ```xml
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-log4j2</artifactId>
          </dependency>
  ```
