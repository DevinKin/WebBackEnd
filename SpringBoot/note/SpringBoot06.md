# Spring Boot与数据访问

- JDBC
- MyBatis
- Spring Data JPA

- SpringBoot默认采用整合SpringData的方式进行统一处理，添加大量自动配置，屏蔽了很多设置。

## 整合基本JDBC与数据源

- 引入starter

  - `spring-boot-starter-jdbc`

- 配置`application.yml`

- 测试

- 高级配置

  - 引入druid
  - 配置属性

- 配置druid数据源监控

- pom文件引入的依赖

  ```xml
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-jdbc</artifactId>
      </dependency>
  
      <dependency>
          <groupId>mysql</groupId>
          <artifactId>mysql-connector-java</artifactId>
          <scope>runtime</scope>
      </dependency>
  ```

- 访问数据库的配置

  ```yaml
  spring:
    datasource:
      username: root
      password: 123456
      url: jdbc:mysql://192.168.8.129:3306/jdbc
      driver-class-name: com.mysql.jdbc.Driver
  ```

### JDBC与数据源的自动配置原理

- 数据源的相关配置都是在`DataSourceProperties`

- 自动配置原理在包`org.springframework.boot.autoconfigure.jdbc`

- 根据`DataSourceConfiguration`配置创建数据源，默认使用Tomcat连接池，可以使用`spring.datasource.type`指定自定义的数据源类型。

- 自定义数据源类型

  ```java
  @ConditionalOnMissingBean({DataSource.class})
  @ConditionalOnProperty(
      name = {"spring.datasource.type"}
  )
  static class Generic {
      Generic() {
      }
  
      @Bean
      public DataSource dataSource(DataSourceProperties properties) {
  		// 根据DataSourceBuilder创建数据源，利用反射创建相应type的数据源，冰倩绑定相关属性。
          return properties.initializeDataSourceBuilder().build();
      }
  }
  ```

- `DataSourceInitializer`继承了`ApplicationListener`，作用

  - `runSchemaScripts()`运行建表语句。

  - `runDataScripts()`运行插入数据的语句

  - 默认只需要将这些文件命名为`schema-*.sql`，`data-*.sql`

  - 默认规则是`schema.sql`或`schema-all.sql`

  - 可以在yaml文件中指定特定的sql文件位置

    ```yaml
    spring:
      datasource:
          schema:
            - classpath:department.sql
    ```

- SpringBoot操作数据，自动配置了`jdbcTemplate`操作数据库。

### 整合druid数据源

- 先引入druid数据源

  ```xml
  <!-- 引入druid数据源-->
  <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.1.8</version>
  </dependency>
  ```

- 在yaml文件中修改数据源类型

  ```yaml
  spring:
    datasource:
      type: com.alibaba.druid.pool.DruidDataSource
  ```

- 运行测试类检查数据源是否更换

  ```java
  public class Springboot06JdbcApplicationTests {
  
      @Autowired
      DataSource dataSource;
  
      @Test
      public void contextLoads() throws SQLException {
          System.out.println(dataSource.getClass());
          Connection connection = dataSource.getConnection();
          System.out.println(connection);
          connection.close();
      }
  }
  ```

- 编写Config类引入更多druid的配置

  ```java
  package com.devinkin.springboot.config;
  
  import com.alibaba.druid.pool.DruidDataSource;
  import com.alibaba.druid.support.http.StatViewServlet;
  import com.alibaba.druid.support.http.WebStatFilter;
  import org.springframework.boot.context.properties.ConfigurationProperties;
  import org.springframework.boot.web.servlet.FilterRegistrationBean;
  import org.springframework.boot.web.servlet.ServletRegistrationBean;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  
  import java.util.Arrays;
  import java.util.HashMap;
  import java.util.Map;
  
  @Configuration
  public class DruidConfig {
      @ConfigurationProperties(prefix = "spring.datasource")
      @Bean
      public DruidDataSource druid() {
          return new DruidDataSource();
      }
  
      // 配置Druid的监控
      // 1. 配置一个管理后台的Servlet
      @Bean
      public ServletRegistrationBean StatViewServlet() {
          ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
          Map<String, String> initParams = new HashMap<>();
          initParams.put("loginUsername","admin");
          initParams.put("loginPassword","123456");
          // 默认允许所有
          initParams.put("allow","");
  //        initParams.put("deny","192.168.8.139");
          bean.setInitParameters(initParams);
          return bean;
      }
      // 2. 配置一个监控的Filter
      @Bean
      public FilterRegistrationBean webStatFilter() {
          FilterRegistrationBean bean = new FilterRegistrationBean();
          bean.setFilter(new WebStatFilter());
          Map<String, String> initParams = new HashMap<>();
          bean.setUrlPatterns(Arrays.asList("/*"));
          initParams.put("exclusions", "*.js,*.css,/druid/*");
          bean.setInitParameters(initParams);
          return bean;
      }
  }
  ```

## SpringBoot整合MyBatis

### 步骤

- 引入`mybatis-spring-boot-starter`

  ```xml
  <dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-starter</artifactId>
      <version>1.3.3</version>
  </dependency>
  ```

- 配置Druid数据源

- 给数据库建表

- 创建对应的JavaBean

### 注解模式

- 编写一个mapper

  ```java
  package com.devinkin.springboot06datamybatis.mapper;
  
  import com.devinkin.springboot06datamybatis.bean.Department;
  import org.apache.ibatis.annotations.*;
  
  
  // 指定这是一个操作数据库的mapper
  @Mapper
  public interface DepartmentMapper {
      @Select("SELECT * FROM department WHERE id=#{id}")
      Department getDeptById(Integer id);
  
      @Delete("DELETE FROM department WHERE id=#{id}")
      int deleteDeptById(Integer id);
  
      @Options(useGeneratedKeys = true, keyProperty = "id")
      @Insert("INSERT INTO department(departmentName) values(#{departmentName})")
      int insertDept(Department department);
  
      @Update("UPDATE department SET departmentName=#{departmentName} WHERE id=#{id}")
      int updateDept(Department department);
  }
  ```

- 自定义MyBatis的配置规则，给容器中添加一个`ConfigurationCustomizer`

  ```java
  package com.devinkin.springboot06datamybatis.config;
  
  import org.apache.ibatis.session.Configuration;
  import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
  import org.springframework.context.annotation.Bean;
  
  
  @org.springframework.context.annotation.Configuration
  public class MyBatisConfig {
      @Bean
      public ConfigurationCustomizer configurationCustomizer() {
          return new ConfigurationCustomizer() {
              @Override
              public void customize(Configuration configuration) {
                  // 启动驼峰命名法规则
                  configuration.setMapUnderscoreToCamelCase(true);
              }
          };
      }
  }
  ```

- 在`SpringBoot06DataMybatisApplication`或者`MyBatisConfig`添加`@MapperScan("xxx")`批量扫描mapper组件。

### 配置文件模式

- 在yaml文件中指定全局配置文件的位置和mapper的位置

  ```yaml
  mybatis:
    config-location: classpath:mybatis/mybatis-config.xml
    mapper-locations: classpath:mybatis/mapper/*.xml
  ```

- 新建`mybatis-config.xml`

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
      <settings>
          <setting name="mapUnderscoreToCamelCase" value="true"/>
      </settings>
      <environments default="development">
          <environment id="development">
              <transactionManager type="JDBC"/>
              <dataSource type="POOLED">
                  <property name="driver" value="${driver}"/>
                  <property name="url" value="${url}"/>
                  <property name="username" value="${username}"/>
                  <property name="password" value="${password}"/>
              </dataSource>
          </environment>
      </environments>
  </configuration>
  ```

- 新建`EmployeeMapper.xml`

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <mapper namespace="com.devinkin.springboot06datamybatis.mapper.EmployeeMapper">
      <select id="getEmployeeById" resultType="com.devinkin.springboot06datamybatis.bean.Employee">
          SELECT * FROM employee WHERE id = #{id}
      </select>
  
      <insert id="insertEmp">
          INSERT INTO employee(lastName, email, gender, d_id) VALUES(#{lastName}, #{email}, #{gender}, #{dId})
      </insert>
  </mapper>
  ```

- 编写mapper接口

  ```java
  package com.devinkin.springboot06datamybatis.mapper;
  
  
  import com.devinkin.springboot06datamybatis.bean.Employee;
  
  public interface EmployeeMapper {
  
      Employee getEmployeeById(Integer id);
  
      void insertEmp(Employee employee);
  }
  ```

## 整合JPA

### 整合步骤

- 引入`spring-boot-starter-data-jpa`
- 配置文件打印SQL语句
- 创建`Entity`标注JPA注解
- 创建`Repository`接口继承`JpaRepository`
- 测试方法



