# Solr集群

## SolrCloud简介
1. SolrCloud(solr 云)是Solr提供的分布式搜索方案，当你需要大规模，容错，分布式索引和检索能力时使用 SolrCloud。当一个系统的索引数据量少的时候是不需要使用SolrCloud的，当索引量很大，搜索请求并发很高，这时需要使用SolrCloud来满足这些需求。
2. SolrCloud是基于Solr和Zookeeper的分布式搜索方案，它的主要思想是使用Zookeeper作为集群的配置信息中心。
3. 它有几个特色功能：
    - 集中式的配置信息
    - 自动容错
    - 近实时搜索
    - 查询时自动负载均衡
    
## Solr集群的系统架构
![](../pic/solrcloud.png)

## 安装步骤
### Zookeeper集群搭建
1. 第一步: 需要安装jdk环境。
2. 第二步: 把zookeeper的压缩包上传到服务器。
3. 第三步: 解压缩。
4. 第四步: 把zookeeper复制三份。
5. 第五步: 在每个zookeeper目录下创建一个data目录。
6. 第六步: 在data目录下创建一个myid文件，文件名就叫做“myid”。内容就是每个实例的id。例如1、2、3
7. 第七步: 修改配置文件。把conf目录下的zoo_sample.cfg文件改名为zoo.cfg
```
dataDir=/opt/solr-cloud/zookeeper01/data
clientPort=2181
-----------ip:server_port:voke_port--------------
server.1=192.168.85.137:2881:3881
server.2=192.168.85.137:2882:3882
server.3=192.168.85.137:2883:3883
```
8. 第八步: 启动每个zookeeper实例。
9. 第九步: 查看zookeeper的状态

### Solr集群搭建
1. 第一步: 创建四个tomcat实例。每个tomcat运行在不同的端口。8180、8280、8380、8480
2. 第二步: 部署solr的war包。把单机版的solr工程复制到集群中的tomcat中。
3. 第三步: 为每个solr实例创建一个对应的solrhome。使用单机版的solrhome复制四份。
4. 第四步: 需要修改solr的web.xml文件。把solrhome关联起来。
5. 第五步: 配置solrCloud相关的配置。每个solrhome下都有一个solr.xml，把其中的ip及端口号配置好。
```xml
<solrcloud>
    <str name="host">192.168.85.137</str>
    <int name="hostPort">8480</int>
</solrcloud>
```
6. 第六步: 让zookeeper统一管理配置文件。需要把solrhome/collection1/conf目录上传到zookeeper。上传任意solrhome中的配置文件即可。
```
$ ./zkcli.sh -zkhost 192.168.85.137:2181 192.168.85.137:2182 192.168.85.137:2183 -cmd upconfig -confdir /opt/solr-cloud/solrhome01/collection1/conf/ -confname myconf
$ cd /opt/solr-cloud/zookeeper01/bin/
$ ./zkCli.sh -server 192.168.85.137:2182
$ ls /
$ ls /configs/myconf
```
7. 第七步: 修改tomcat/bin目录下的catalina.sh 文件，关联solr和zookeeper。
```
JAVA_OPTS="-DzkHost=192.168.85.137:2181,192.168.85.137:2182,192.168.85.137:2183"
```

8. 第八步：启动每个tomcat实例。要包装zookeeper集群是启动状态。
9. 第九步：访问集群
10. 第十步：创建新的Collection进行分片处理。在浏览器输入如下地址
```
http://192.168.85.137:8180/solr/admin/collections?action=CREATE&name=collection2&numShards=2&replicationFactor=2

http://192.168.85.137:8380/solr/admin/collections?action=DELETE&name=collection1
```

## 使用SolrJ管理集群

