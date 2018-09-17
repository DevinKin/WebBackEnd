# Solr

## 什么是Solr
1. Solr 是Apache下的一个顶级开源项目，采用Java开发，它是基于Lucene的全文搜索服务器。Solr提供了比Lucene更为丰富的查询语言，同时实现了可配置、可扩展，并对索引、搜索性能进行了优化。 
2. Solr可以独立运行，运行在Jetty、Tomcat等这些Servlet容器中，Solr 索引的实现方法很简单，用 POST 方法向 Solr 服务器发送一个描述 Field 及其内容的 XML 文档，Solr根据xml文档添加、删除、更新索引 。Solr 搜索只需要发送 HTTP GET 请求，然后对 Solr 返回Xml、json等格式的查询结果进行解析，组织页面布局。Solr不提供构建UI的功能，Solr提供了一个管理界面，通过管理界面可以查询Solr的配置和运行情况。


## Lucene和Solr的区别
1. Lucene是一个开放源代码的全文检索引擎工具包，它不是一个完整的全文检索引擎，Lucene提供了完整的查询引擎和索引引擎，目的是为软件开发人员提供一个简单易用的工具包，以方便的在目标系统中实现全文检索的功能，或者以Lucene为基础构建全文检索引擎。
2. Solr的目标是打造一款企业级的搜索引擎系统，它是一个搜索引擎服务，可以独立运行，通过Solr可以非常快速的构建企业的搜索引擎，通过Solr也可以高效的完成站内搜索功能。

## Solr安装及配置
1. 从官网:`http://lucene.apache.org/solr/`下载压缩包
2. Solr使用指南可参考：`https://wiki.apache.org/solr/FrontPage`

### Solr目录结构
1. bin：solr的运行脚本
2. contrib：solr的一些软件/插件，用于增强solr的功能。
3. dist：该目录包含build过程中产生的war和jar文件，以及相关的依赖文件。
4. docs：solr的API文档
5. example：solr工程的例子目录：
    - example/solr：该目录是一个包含了默认配置信息的Solr的Core目录。
    - example/multicore：该目录包含了在Solr的multicore中设置的多个Core目录。 
    - example/webapps：该目录中包括一个solr.war，该war可作为solr的运行实例工程。
6. licenses：solr相关的一些许可信息

#### SolrHome与SolrCore
1. solrHome:solrHome就是最核心的目录,一个solrHome总可以有多个solr实例
2. solrCore:一个SolrCore就是一个solr实例,solr实例与实例之间他们的索引库和文档库是相互隔离的,每个实例对外单独的提供索引和文档的增删改查服务,默认实例是collection1

### Solr与Tomcat整合
1. 安装tomcat
2. 将solr.war解压复制到tomcat的webapp目录下
3. 创建一个solrhome目录,可以在solr的example目录下复制标准的solrhome
4. 把solr目录改名为solrhome(不是必须)
5. 修改webapp下的solr项目的web.xml
```xml
    <env-entry>
       <env-entry-name>solr/home</env-entry-name>
       <env-entry-value>F:\solrhome</env-entry-value>
       <env-entry-type>java.lang.String</env-entry-type>
    </env-entry>
```
6. 启动tomcat

## Solr后台管理
1. 文档和索引的增加和修改必须要有id,主键域,没有会报错
2. 域名和类型必须先定义后使用,如果没有定义就使用,会报错

## 配置中文分词器
1. 是否存储和是否索引无关,索引后就能查询,不索引就不能根据这个域搜索,存储后就能取出里面的内容,不存储就取不出这个域的内容
### Schema.xml
1. schema.xml，在SolrCore的conf目录下，它是Solr数据表配置文件，它定义了加入索引的数据的数据类型的。主要包括FieldTypes、Fields和其他的一些缺省设置。

#### FieldType域类型定义
1. text_general是Solr默认提供的FieldType，通过它说明FieldType定义的内容
2. fieldType子结点包括：name,class,positionIncrementGap等一些参数
    - name:是这个fieldType的名称
    - class:是Solr提供的包solr.TextField，solr.TextField 允许用户通过分析器来定制索引和查询，分析器包括一个分词器（tokenizer）和多个过滤器（filter）
    - positionIncrementGap:可选属性，定义在同一个文档中此类型数据的空白间隔，避免短语匹配错误，此值相当于Lucene的短语查询设置slop值，根据经验设置为100
    
3. 在FieldType定义的时候最重要的就是定义这个类型的数据在建立索引和进行查询的时候要使用的分析器analyzer,包括分词和过滤
   - 索引分析器中：使用solr.StandardTokenizerFactory标准分词器，solr.StopFilterFactory停用词过滤器，solr.LowerCaseFilterFactory小写过滤器。
   - 搜索分析器中：使用solr.StandardTokenizerFactory标准分词器，solr.StopFilterFactory停用词过滤器，这里还用到了solr.SynonymFilterFactory同义词过滤器。

### Field定义

#### 域的分类
1. 普通域:string,long等
2. 动态域:起到模糊匹配的效果,可以匹配没有定义过的域名
    - 如:xxxx这个域名没有定义,但是xxxx_s这个域名模糊了`*_s`这个域,所以相当于`xxxx_s`这个域定义了
3. 主键域:`<uniqueKey>id</uniqueKey>`,一般主键域就用默认的这个就可以不需要更改或者添加
4. 复制域:`copyField`,复制域用于查询的时候,从多个域中进行查询,这样可以将多个域复制到某一个统一的域中,然后搜索的时候从这个统一的域中进行查询,就相当于从多个域中查询了

### 安装中文分词器
1. 把IKAnalyzer2012FF_u1.jar添加到solr/WEB-INF/lib目录下。
2. 复制IKAnalyzer的配置文件和自定义词典和停用词词典到solr的classpath下,classes文件夹
3. 在schema.xml中添加一个自定义的fieldType，使用中文分析器。
```xml
    <!-- IKAnalyzer FiledType -->
    <fieldType name="text_ik" class="solr.TextField">
      <analyzer class="org.wltea.analyzer.lucene.IKAnalyzer"/>
    </fieldType>
```
4. 定义Field,指定field的type属性为text_ik
```xml
   <!--IKAnalyzer Field-->
   <field name="title_ik" type="text_ik" indexed="true" stored="true" />
   <field name="content_ik" type="text_ik" indexed="true" stored="false" multiValued="true"/>
```
5. 重启tomcat

## Filed域的改造
```java
// 是否分词:要,因为它要索引,并且它不是一个整体,分词有意义,所以要分词
// 是否索引:要索引,因为要通过文件名进行搜索
// 是否存储:要,因为要直接在页面上显示
TextField nameField = new TextField("fileName", fileName, Field.Store.YES);
// 是否分词:要,因为要根据文件内容进行索引,并且它分词有意义
// 是否索引:要,因为要根据文件内容进行搜索
// 是否存储:可以要,也可以不要,不存储,搜索完内容就提取不出来
TextField contextField = new TextField("fileContext", fileContext, Field.Store.NO);
// 是否分词:不要,大小索引没有意义,因为大小的字符串不需要分割
// 是否索引:要,因为要根据文件大小进行搜索
// 是否存储:要,因为要显示文档大小
// NumericDocValuesField提供索引,评分,排序功能,但不提供存储功能,需要额外添加StoredField进行存储
NumericDocValuesField numericDocValuesField = new NumericDocValuesField("fileSize", fileSize);
// 要存储值,必须要添加一个同名的文件大小字段
StoredField storedField = new StoredField("fileSize", fileSize.toString());
```

## 维护索引(增删改)

### 批量导入数据
1. 使用dataimport插件批量导入数据。
2. 把dataimport插件依赖的jar包,mysql驱动包添加到solrcore（collection1\lib）中
    - solr-dataimporthandler-4.10.3.jar
    - solr-dataimporthandler-extras-4.10.3.jar
    - mysql-connector-java-5.1.25.jar
3. 配置solrconfig.mxl文件，添加一个requestHandler。
```xml
 <requestHandler name="/dataimport" class="org.apache.solr.handler.dataimport.DataImportHandler">
    <lst name="defaults">
      <str name="config">data-config.xml</str>
    </lst>
 </requestHandler> 
```
4. 创建一个data-config.xml，保存到collection1\conf\目录下
```xml
<?xml version="1.0" encoding="UTF-8" ?>  
<dataConfig>   
    <dataSource type="JdbcDataSource"   
		    driver="com.mysql.jdbc.Driver"   
		    url="jdbc:mysql://localhost:3306/solr"   
		    user="dbuser"   
		    password="111111"/>   
    <document>   
	    <entity name="product" query="SELECT pid,name,catalog_name,price,description,picture FROM products ">
		    <field column="pid" name="id"/> 
		    <field column="name" name="product_name"/> 
		    <field column="catalog_name" name="product_catalog_name"/> 
		    <field column="price" name="product_price"/> 
		    <field column="description" name="product_description"/> 
		    <field column="picture" name="product_picture"/> 
	    </entity>   
    </document>   
</dataConfig>
```
5. 在schema.xml中添加域
```xml
  <!--product-->
   <field name="pid" type="long" indexed="true" stored="true"/>
   <field name="product_name" type="text_ik" indexed="true" stored="true"/>
   <field name="product_price"  type="float" indexed="true" stored="true"/>
   <field name="product_description" type="text_ik" indexed="true" stored="false" />
   <field name="product_picture" type="string" indexed="false" stored="true" />
   <field name="product_catalog_name" type="string" indexed="true" stored="true" />

   <field name="product_keywords" type="text_ik" indexed="true" stored="false" multiValued="true"/>
   <copyField source="product_name" dest="product_keywords"/>
   <copyField source="product_description" dest="product_keywords"/>
```
5. 重启tomcat
6. 点入Dataimport进行导入 

### 删除文档
#### 删除指定ID的索引
```xml
<delete>
    <id>1</id>
</delete>
```
#### 删除查询到的索引数据
```xml
<delete>
    <query>product_catalog_name:幽默杂货</query>
</delete>
```
#### 删除所有索引数据
```xml
<delete>
    <query>*:*</query>
</delete>
```

## 查询索引
1. 通过/select搜索索引，Solr制定一些参数完成不同需求的搜索:
2. q,为界面输入框的q,用于查询字符串
3. fq,（filter query）过滤查询，作用：在q查询符合结果中同时是fq查询符合的
    - 例:`product_price:[1 TO 100]`
4. sort,排序，格式:`sort=<field name>+<desc|asc>[,<field name>+<desc|asc>]… `
    - 例:`product_price desc`
5. start,分页显示使用,开始记录下标,从0开始
6. rows,指定返回结果最多有多少条记录，配合start来实现分页
7. fl,指定返回哪些域内容，用逗号或空格分隔多个
    - 例:`id,product_name,product_price`
8. df,指定一个默认搜索Field
    - 也可以在SolrCore目录中conf/solrconfig.xml文件中指定默认搜索Field，指定后就可以直接在“q”查询条件中输入关键字
    - 例:`product_keywords`
9. wt,(writer type)指定输出格式,可以有xml, json, php, phps, 后面 solr 1.3增加的，要用通知我们，因为默认没有打开
10. hl是否高亮,设置高亮Field,设置格式前缀和后缀
    - 例:`product_name`,需要在底部查看是否含有高亮标签

## 使用SolrJ管理索引库
1. solrj是访问Solr服务的java客户端，提供索引和搜索的请求方法，SolrJ通常在嵌入在业务系统中，通过SolrJ的API接口操作Solr服务
2. 依赖的jar包
    - solr-solrj-4.10.3.jar
    - commons-io-2.3.jar
    - httpclient-4.3.1.jar
    - httpcore-4.3.jar
    - httpmime-4.3.1.jar
    - noggit-0.5.jar
    - slf4j-api-1.7.6.jar
    - wstx-asl-3.2.7.jar
    - zookeeper-3.4.6.jar
    - 日志包(exapmle/lib/ext)
        - slf4j-log4j12-1.7.6.jar
        - log4j-1.2.17.jar
        - jul-to-slf4j-1.7.6.jar
        - jcl-over-slf4j-1.7.6.jar
3. 和Solr服务器建立连接,HttpSolrServer对象建立连接
4. 创建一个SolrInputDocument对象，然后添加域
5. 将SolrInputDocument添加到索引库
6. 提交

### 索引增删改测试代码
```java
package cn.devinkin.solrj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class IndexManagerTest {
    @Test
    public void testIndexCreate() throws Exception {
        // 创建和Solr服务端连接
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
        // 创建solr文档对象
        SolrInputDocument doc = new SolrInputDocument();
        // 域要先定义后使用,还要注意要有id主键域
        doc.addField("id", "a001");
        // 会报错,域要先定义后提交
//        doc.addField("", "台灯");
        // solr中没有专用的修改方法,会自动根据id进行查询,如果找到了则删除原来的,加入新值,如果没找到,直接加入新值
        doc.addField("product_name", "台灯111");
        doc.addField("product_price", "12.5");
        // 将文档加入solrServer对象中
        solrServer.add(doc);
        // 提交
        solrServer.commit();
    }

    @Test
    public void testIndexDel() throws Exception {
        // 创建和Solr服务端连接
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");

        // 根据主键id进行删除
//        solrServer.deleteById("a001");
        // 根据查询删除,*:*删除所有
        solrServer.deleteByQuery("*:*");
        // 提交
        solrServer.commit();
    }
}

```

### 索引查询测试代码
```java
package cn.devinkin.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class IndexSearchTest {

    @Test
    public void testIndexSearch1() throws Exception {
        // 创建和Solr服务端连接
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");

        // 创建solr查询条件对象
        SolrQuery solrQuery = new SolrQuery();
        // 查询所有
        solrQuery.setQuery("*:*");
        // 查询并获取查询响应对象
        QueryResponse queryResponse = solrServer.query(solrQuery);
        // 从查询响应中获取查询结果集对象
        SolrDocumentList results = queryResponse.getResults();
        System.out.println("======count======");
        // 打印一共查询多少条记录,也就是记录总数
        System.out.println(results.getNumFound());
        System.out.println("======count======");
        // 遍历查询结果集
        for (SolrDocument doc : results) {
            // 通过域名查找域值
            System.out.println("id: " +doc.get("id"));
            System.out.println("product_name: " +doc.get("product_name"));
            System.out.println("product_price: " +doc.get("product_price"));
            System.out.println("====================================");
        }
    }

    @Test
    public void testIndexSearch2() throws Exception {
        // 创建和Solr服务端连接
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");

        // =====创建solr查询条件对象=====
        SolrQuery solrQuery = new SolrQuery();
        // =====创建solr查询条件对象=====

        // =====查询关键字=====
        solrQuery.setQuery("台灯");
        // =====查询关键字=====

        // =====设置默认搜索域=====
        solrQuery.set("df", "product_keywords");
        // =====设置默认搜索域=====

        // =====设置过滤查询=====
        solrQuery.addFilterQuery("product_price:[1 TO 100]");
        // =====设置过滤查询=====

        // =====设置排序=====
        solrQuery.setSort("product_price", SolrQuery.ORDER.desc);
        // =====设置排序=====

        // =====设置分页,从第0条开始,查询50条=====
        // 起始条数
        solrQuery.setStart(0);
        // 查询的条数
        solrQuery.setRows(50);
        // =====设置分页,从第0条开始,查询50条=====

        // =====设置高亮显示=====
        // 高亮默认是关闭的,所以要手动开启
        solrQuery.setHighlight(true);
        // 设置需要高亮显示的域
        solrQuery.addHighlightField("product_name");
        // 设置高亮前缀
        solrQuery.setHighlightSimplePre("<span style=\"color:red\">");
        // 设置高亮后缀
        solrQuery.setHighlightSimplePost("</span>");
        // =====设置高亮显示=====

        // ========================查询并获取查询响应对象========================
        QueryResponse queryResponse = solrServer.query(solrQuery);
        // 从查询响应中获取查询结果集对象
        SolrDocumentList results = queryResponse.getResults();
        System.out.println("======count======");
        // 打印一共查询多少条记录,也就是记录总数
        System.out.println(results.getNumFound());
        System.out.println("======count======");
        // 遍历查询结果集
        for (SolrDocument doc : results) {
            // 通过域名查找域值
            System.out.println("id: " +doc.get("id"));
            // =====获取高亮内容=====
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            List<String> list = highlighting.get(doc.get("id")).get("product_name");
            if (list != null && list.size() > 0) {
                String hlName = list.get(0);
                System.out.println("high lighting: " + hlName);
            }
            // =====获取高亮内容=====
            System.out.println("product_name: " +doc.get("product_name"));
            System.out.println("product_price: " +doc.get("product_price"));
            System.out.println("====================================");
        }
        // ========================查询并获取查询响应对象========================
    }
}

```