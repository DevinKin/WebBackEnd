# jar包冲突问题的解决
1. 通过添加`exclusions`标签解决冲突
2. 使用版本锁定实现冲突解决

## 通过添加exclusions标签解决冲突
1. 在父工程中引入了`struts-core`，`hibernate-core`，就发现jar包是有冲突的
2. 使用`exclusions`标签去除冲突的jar包
3. 格式
```xml
<exclusions>
    <exclusion>
        <groupId>xxx</groupId>
        <artifactId>xxx</artifactId>
    </exclusion>
</exclusions>
```

## 使用版本锁定实现冲突解决
1. struts2依赖`spring-beans-3.0.5`，spring-context依赖`spring-beans-4.2.4`
2. 格式
```xml
    <dependencyManagement>
        <dependencies>
            <!-- 这里锁定版本为4.2.4 -->
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-beans</artifactId>
                <version>4.2.4.RELEASE</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-context</artifactId>
                <version>4.2.4.RELEASE</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
```
3. 在dependency中继续引入，但不需要提供版本号


## 依赖调节原则
1. 第一声明优先原则
2. 路径近者优先原则

## 使用常量方式配置版本信息
1. 格式
```xml
<properties>
    <mysql.version>5.1.6</mysql.version>
</properties>
```

# 工程模块整合
1. 通过dependency标签进行整合，格式如下
```xml
    <dependencies>
        <dependency>
            <groupId>Maven</groupId>
            <artifactId>ssh_dao</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
```

# maven私服搭建
1. 下载Nexus，Nexus是Maven仓库管理器，通过nexus可以搭建maven仓库，同时，Nexus还提供强大的仓库管理功能，构建搜索功能等。
    - 下载地址：`https://blog.sonatype.com/`
    
2. 进入bin目录，启动nexus
    - `nexus start`
    
3. 登录
    - 账号：`admin`
    - 密码：`admin123`
    
4. 配置
    - 进入Administration-Server进行配置
  
5. 仓库类型
    - hosted：宿主仓库，部署自己的jar到这个类型的仓库，包括releases和snapshot两部分，Release为公司内部发布仓库、Snapshot为公司内部测试版本仓库
    - proxy：代理仓库，用于代理远程的公共仓库，如maven中央仓库，用户连接私服，私服自动去中央仓库下载jar包或者插件
    - group：仓库组，用来合并多个hosted/proxy仓库，通常我们配置自己的maven仓库组
    - virtual(虚拟)：兼容Maven1版本的jar或者插件
    
## 配置maven连接私服
1. 配置settings.xml，在servers标签下添加如下内容
```xml
<server>
    <id>releases</id>
    <username>admin</username>
    <password>admin123</password>
</server>
<server>
    <id>snapshots</id>
    <username>admin</username>
    <password>admin123</password>
</server>
```

2. 配置项目的`pom.xml`，上传项目
```xml
<distributionManagement>
    <repository>
        <id>releases</id>
        <url>http://localhost:8081/repositories/releases/</url>
    </repository>
    <snapshotRepository>
        <id>snapshots</id>

        <url>http://localhost:8081/content/repositories/snapshots/</url>
    </snapshotRepository>
</distributionManagement>
```

3. 将项目模块打包成jar包发布到私服
    - 启动nexus
    - 对dao工程执行delpoy命令
    
4. 配置settings.xml配置文件
- 在servers标签下使用server标签配置nexus用户名和密码
```xml
        <server>
            <id>releases</id>
            <username>admin</username>
            <password>admin123</password>
        </server>
        <server>
            <id>snapshots</id>
            <username>admin</username>
            <password>admin123</password>
        </server>
```
- 在profiles标签下使用profile标签配置仓库路径
```xml
        <profile>
	        <!--profile的id-->
            <id>dev</id>
            <repositories>
                <repository>
		            <!--仓库id，repositories可以配置多个仓库，保证id不重复-->
                    <id>nexus</id>
		            <!--仓库地址，即nexus仓库组的地址-->
                    <url>http://localhost:8081/nexus/content/groups/public/</url>
		            <!--是否下载releases构件-->
                    <releases>
                        <enabled>true</enabled>
                    </releases>
		            <!--是否下载snapshots构件-->
                    <snapshots>
                        <enabled>true</enabled>
                    </snapshots>
                </repository>
            </repositories>
	        <pluginRepositories>
    	        <!-- 插件仓库，maven的运行依赖插件，也需要从私服下载插件 -->
                <pluginRepository>
        	        <!-- 插件仓库的id不允许重复，如果重复后边配置会覆盖前边 -->
                    <id>public</id>
                    <name>Public Repositories</name>
                    <url>http://localhost:8081/nexus/content/groups/public/</url>
                </pluginRepository>
            </pluginRepositories>
        </profile>
```