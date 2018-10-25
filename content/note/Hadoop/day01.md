# Hadoop
1. 第一天
    - hadoop基本概念
    - 伪分布式hadoop集群安装
    - hdfs mapreduce演示

2. 第二天
    - hdfs的原理和使用操作,编程

3. 第三天
    - mapreduce的原理和编程

4. 第四天
    - 常见mr算法实现和shuffle的机制
    
5. 第五天
    - hadoop2.x中HA机制的原理和全分布式集群安装部署及维护

6. 第六天
    - hbase,hive

7. 第七天
    - storm+kafka
    
8. 第八天
    - 实战项目
    
## Hadoop基本概念
1. 解决的问题
    - 海量的存储(HDFS)
    - 海量的数据分析(MapReduce)
    - 资源管理调度(YARN)

## Hadoop安装
1. 环境
    - Centos7-64位
    - jdk-7u65-linux-x64.tar.gz
    - hadoop2.4.1
    
### 网络相关配置
1. 修改主机名,编辑`/etc/sysconfig/network`
```
NETWORKING=yes
HOSTNAME=itcast
```

2. 修改ip地址,使用静态分配ip,修改`/etc/sysconfig/network-script/ifcfg-enp0s8`
```
TYPE=Ethernet
PROXY_METHOD=none
BROWSER_ONLY=no
BOOTPROTO=static
DEFROUTE=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_FAILURE_FATAL=no
IPV6_ADDR_GEN_MODE=stable-privacy
NAME=enp0s8
UUID=7b27f213-eec0-4945-8156-552c01e3ac04
DEVICE=enp0s8
ONBOOT=yes
GATEWAY=192.168.56.2
IPADDR=192.168.56.3
NETMASK=255.255.255.0

```

3. 修改主机名和IP的映射关系`/etc/hosts`
```
192.168.56.3 devinkin
```

4. 关闭防火墙
```
service iptables stop
chconfig iptables --list
chkconfig iptables off
```

5. 重启linux:`reboot`

### 安装JDK
1. 解压jdk:`tar xfv jdk-7u65-linux-x64.tar.gz `

2. 移动到`/opt/jvm/`目录下:`mv jdk1.7.0_65/ /opt/jvm/`

3. 配置环境变量
```
export JAVA_HOME=/opt/jvm/jdk1.7.0_65
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:${JRE_HOME}/bin:${PATH}
```

4. 刷新配置文件:`source /etc/profile`

### 安装Hadoop
1. 解压hadoop:`tar xfv hadoop-2.4.1.tar.gz`

#### 配置Hadoop
1. 配置`hadoop-env.sh`
```
export JAVA_HOME=${JAVA_HOME}
```

2. 配置`core-site.xml`
```
        <!-- 指定HADOOP所使用的文件系统schema（URI），HDFS的老大（NameNode）的地址 -->
        <property>
                <name>fs.defaultFS</name>
                <value>hdfs://devinkin:9000/</value>
        </property>
        <!-- 指定hadoop运行时产生文件的存储目录 -->
        <property>
                <name>hadoop.tmp.dir</name>
                <value>/opt/hadoop-2.4.1/data</value>
        </property>
```

3. 配置`hdfs-site.xml`
```
    <!-- 指定HDFS副本的数量 -->
    <property>
        <name>dfs.replication</name>
        <value>1</value>
    </property>
```

4. 配置`mapred-site.xml`:`cp mapred-site.xml.template mapred-site.xml`
```
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property>
```

5. 配置`yarn-site.xml`
```
    <!-- 指定YARN的老大（ResourceManager）的地址 -->
    <property>
        <name>yarn.resourcemanager.hostname</name>
        <value>devinkin</value>
    </property>
    <!-- reducer获取数据的方式 -->
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
```


#### 将hadoop添加到环境变量
1. `vim /etc/profile`
```
export HADOOP_HOME=/opt/hadoop-2.4.1/
export PATH=${HADOOP_HOME}/bin:${HADOOP_HOME}/sbin:${PATH}
```

#### 格式化namenode
1. 对namenode进行初始化:`hdfs namenode -format`

#### 安装hadoop64位本地库
1. 可以去网站：`http://dl.bintray.com/sequenceiq/sequenceiq-bin/`下载对应的编译版本

2. 将准备好的64位的lib包解压到已经安装好的hadoop安装目录的lib/native和lib目录下
```
tar -xvf hadoop-native-64-2.4.1.tar -C /opt/hadoop-2.4.1/lib/native/
tar -xvf hadoop-native-64-2.4.1.tar -C /opt/hadoop-2.4.1/lib/
```

3. 在`/etc/profile/`中添加如下配置
```
export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
export HADOOP_OPTS="-Djava.library.path=$HADOOP_HOME/lib"
```

4. 使环境变量生效`source /etc/profile`

5. 自检`hadoop checknative –a`指令检查


#### 启动hadoop
1. 先启动HDFS:`sbin/start-dfs.sh`

2. 再启动YARN:`sbin/start-yarn.sh`



#### 验证是否启动成功
1. 使用`jps`命令验证Hadoop是否启动成功

2. http://192.168.56.3:50070 （HDFS管理界面）

3. http://192.168.56.3:8088 （MR管理界面）

4. 向hadoop上传文件:`hadoop fs -put jdk-7u65-linux-i586.tar.gz hdfs://devinkin:9000/`

5. 向hadoop下载文件:`hadoop fs -get hdfs://devinkin:9000/jdk-7u65-linux-i586.tar.gz`


#### 运行Hadoop例程
1. 运行hadoop计算圆周率例程:`hadoop jar hadoop-mapreduce-examples-2.4.1.jar pi 5 5`

2. 运行hadoop统计单词例程
    - 新建目录:`hadoop fs -mkdir /wordcount`
    - 新建目录:`hadoop fs -mkdir /wordcount/input`
    - 上传统计单词的数据:`hadoop fs -put test.txt /worldcount/input`
    - 运行worldcount程序:`hadoop jar hadoop-mapreduce-examples-2.4.1.jar wordcount /wordcount/input /wordcount/output`
    - 查看是否生成output文件:`hadoop fs -ls /worlcount/output`
    - 查看output文件:`hadoop fs -cat /wordcount/output/part-r-00000`

#### 配置ssh免登陆
1. 生成ssh免登陆密钥
2. 进入到我的home目录:`cd ~/.ssh`
	- `ssh-keygen -t rsa （四个回车）`
	- 执行完这个命令后，会生成两个文件id_rsa（私钥）、id_rsa.pub（公钥）
	- 将公钥拷贝到要免登陆的机器上`ssh-copy-id localhost`
	
![](../pic/ssh工作原理.png)
	

## HDFS的实现思想
1. hdfs是通过分布式集群来存储文件,为客户端提供了一个便捷的访问方式,就是一个虚拟的目录结构
2. 文件存储到hdfs集群中的时候是被切分成block的
3. 文件的block存放在若干台datanode节点上
4. hdfs文件系统中的文件与真实的block之间有映射关系,由namenode管理
5. 每个block在集群中会存储多个副本,好处是可以提高数据的可靠性,还可以提高访问的吞吐量


![](../pic/hdfs实现思想.png)
	
## Hadoop基本操作
1. 查看hadoop fs的所d有选项:`hadoop fs`
2. 新建目录:`hadoop fs -mkdir /wordcount`
3. 查看文件列表:`hadoop fs -ls /worlcount/output`
4. 查看文件内容:`hadoop fs -cat /wordcount/output/part-r-00000`
5. 修改文件所属用户和组:`hadoop fs -chown baby:mygirls /jdk-7u65-linux-i586.tar.gz`
6. 修改文件权限:`hadoop fs -chmod 777 /jdk-7u65-linux-i586.tar.gz`
7. 将本地文件拷贝到hadoop中:`hadoop fs -copyFromLocal ./hadoop-mapreduce-client-app-2.4.1.jar /`
8. 查看文件系统空间大小:`hadoop fs -df -h /`
9. 查看根目录下每个文件的大小:`hadoop fs -du -s -h hdfs://devinkin:9000/*`
10. 删除目录:`hadoop fs -rm -r /aa`
11. 查看文件尾部信息:`fs -tail -f /worldcount/input/test.txt`


