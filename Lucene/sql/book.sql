/*
SQLyog v10.2 
MySQL - 5.1.72-community 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `book` (
	`id` int (11),
	`name` varchar (192),
	`price` float ,
	`pic` varchar (96),
	`description` text 
); 
insert into `book` (`id`, `name`, `price`, `pic`, `description`) values('1','java 编程思想','71.5','23488292934.jpg','作者简介　　Bruce Eckel，是MindView公司的总裁，该公司向客户提供软件咨询和培训。他是C++标准委员会拥有表决权的成员之一，拥有应用物理学学士和计算机工程硕士学位。除本书外，他还是《C++编程思想》的作者，并与人合著了《C++编程思想第2卷》。\r\n\r\n《计算机科学丛书：Java编程思想（第4版）》赢得了全球程序员的广泛赞誉，即使是最晦涩的概念，在BruceEckel的文字亲和力和小而直接的编程示例面前也会化解于无形。从Java的基础语法到最高级特性（深入的面向对象概念、多线程、自动项目构建、单元测试和调试等），本书都能逐步指导你轻松掌握。\r\n　　从《计算机科学丛书：Java编程思想（第4版）》获得的各项大奖以及来自世界各地的读者评论中，不难看出这是一本经典之作。本书的作者拥有多年教学经验，对C、C++以及Java语言都有独到、深入的见解，以通俗易懂及小而直接的示例解释了一个个晦涩抽象的概念。本书共22章，包括操作符、控制执行流程、访问权限控制、复用类、多态、接口、通过异常处理错误、字符串、泛型、数组、容器深入研究、JavaI/O系统、枚举类型、并发以及图形化用户界面等内容。这些丰富的内容，包含了Java语言基础语法以及高级特性，适合各个层次的Java程序员阅读，同时也是高等院校讲授面向对象程序设计语言以及Java语言的绝佳教材和参考书。\r\n　　《计算机科学丛书：Java编程思想（第4版）》特点：\r\n　　适合初学者与专业人员的经典的面向对象叙述方式，为更新的JavaSE5/6增加了新的示例和章节。\r\n　　测验框架显示程序输出。\r\n　　设计模式贯穿于众多示例中：适配器、桥接器、职责链、命令、装饰器、外观、工厂方法、享元、点名、数据传输对象、空对象、代理、单例、状态、策略、模板方法以及访问者。\r\n　　为数据传输引入了XML，为用户界面引入了SWT和Flash。\r\n　　重新撰写了有关并发的章节，有助于读者掌握线程的相关知识。\r\n　　专门为第4版以及JavaSE5/6重写了700多个编译文件中的500多个程序。\r\n　　支持网站包含了所有源代码、带注解的解决方案指南、网络日志以及多媒体学习资料。\r\n　　覆盖了所有基础知识，同时论述了高级特性。\r\n　　详细地阐述了面向对象原理。\r\n　　在线可获得Java讲座CD，其中包含BruceEckel的全部多媒体讲座。\r\n　　在网站上可以观看现场讲座、咨询和评论。\r\n　　专门为第4版以及JavaSE5/6重写了700多个编译文件中的500多个程序。\r\n　　支持网站包含了所有源代码、带注解的解决方案指南、网络日志以及多媒体学习资料。\r\n　　覆盖了所有基础知识，同时论述了高级特性。\r\n　　详细地阐述了面向对象原理。\r\n\r\n\r\n');
insert into `book` (`id`, `name`, `price`, `pic`, `description`) values('2','apache lucene','66.0','77373773737.jpg','lucene是apache的开源项目，是一个全文检索的工具包。\r\n# Apache Lucene README file\r\n\r\n## Introduction\r\n\r\nLucene is a Java full-text search engine.  Lucene is not a complete\r\napplication, but rather a code library and API that can easily be used\r\nto add search capabilities to applications.\r\n\r\n * The Lucene web site is at: http://lucene.apache.org/\r\n * Please join the Lucene-User mailing list by sending a message to:\r\n   java-user-subscribe@lucene.apache.org\r\n\r\n## Files in a binary distribution\r\n\r\nFiles are organized by module, for example in core/:\r\n\r\n* `core/lucene-core-XX.jar`:\r\n  The compiled core Lucene library.\r\n\r\nTo review the documentation, read the main documentation page, located at:\r\n`docs/index.html`\r\n\r\nTo build Lucene or its documentation for a source distribution, see BUILD.txt');
insert into `book` (`id`, `name`, `price`, `pic`, `description`) values('3','mybatis','55.0','88272828282.jpg','MyBatis介绍\r\n\r\nMyBatis 本是apache的一个开源项目iBatis, 2010年这个项目由apache software foundation 迁移到了google code，并且改名为MyBatis。 \r\nMyBatis是一个优秀的持久层框架，它对jdbc的操作数据库的过程进行封装，使开发者只需要关注 SQL 本身，而不需要花费精力去处理例如注册驱动、创建connection、创建statement、手动设置参数、结果集检索等jdbc繁杂的过程代码。\r\nMybatis通过xml或注解的方式将要执行的statement配置起来，并通过java对象和statement中的sql进行映射生成最终执行的sql语句，最后由mybatis框架执行sql并将结果映射成java对象并返回。\r\n');
insert into `book` (`id`, `name`, `price`, `pic`, `description`) values('4','spring','56.0','83938383222.jpg','## Spring Framework\r\nspringmvc.txt\r\nThe Spring Framework provides a comprehensive programming and configuration model for modern\r\nJava-based enterprise applications - on any kind of deployment platform. A key element of Spring is\r\ninfrastructural support at the application level: Spring focuses on the \"plumbing\" of enterprise\r\napplications so that teams can focus on application-level business logic, without unnecessary ties\r\nto specific deployment environments.\r\n\r\nThe framework also serves as the foundation for\r\n[Spring Integration](https://github.com/SpringSource/spring-integration),\r\n[Spring Batch](https://github.com/SpringSource/spring-batch) and the rest of the Spring\r\n[family of projects](http://springsource.org/projects). Browse the repositories under the\r\n[SpringSource organization](https://github.com/SpringSource) on GitHub for a full list.\r\n\r\n[.NET](https://github.com/SpringSource/spring-net) and\r\n[Python](https://github.com/SpringSource/spring-python) variants are available as well.\r\n\r\n## Downloading artifacts\r\nInstructions on\r\n[downloading Spring artifacts](https://github.com/SpringSource/spring-framework/wiki/Downloading-Spring-artifacts)\r\nvia Maven and other build systems are available via the project wiki.\r\n\r\n## Documentation\r\nSee the current [Javadoc](http://static.springsource.org/spring-framework/docs/current/api)\r\nand [Reference docs](http://static.springsource.org/spring-framework/docs/current/reference).\r\n\r\n## Getting support\r\nCheck out the [Spring forums](http://forum.springsource.org) and the\r\n[Spring tag](http://stackoverflow.com/questions/tagged/spring) on StackOverflow.\r\n[Commercial support](http://springsource.com/support/springsupport) is available too.\r\n\r\n## Issue Tracking\r\nSpring\'s JIRA issue tracker can be found [here](http://jira.springsource.org/browse/SPR). Think\r\nyou\'ve found a bug? Please consider submitting a reproduction project via the\r\n[spring-framework-issues](https://github.com/springsource/spring-framework-issues) repository. The\r\n[readme](https://github.com/springsource/spring-framework-issues#readme) provides simple\r\nstep-by-step instructions.\r\n\r\n## Building from source\r\nInstructions on\r\n[building Spring from source](https://github.com/SpringSource/spring-framework/wiki/Building-from-source)\r\nare available via the project wiki.\r\n\r\n## Contributing\r\n[Pull requests](http://help.github.com/send-pull-requests) are welcome; you\'ll be asked to sign our\r\ncontributor license agreement ([CLA](https://support.springsource.com/spring_committer_signup)).\r\nTrivial changes like typo fixes are especially appreciated (just\r\n[fork and edit!](https://github.com/blog/844-forking-with-the-edit-button)). For larger changes,\r\nplease search through JIRA for similiar issues, creating a new one if necessary, and discuss your\r\nideas with the Spring team.\r\n\r\n## Staying in touch\r\nFollow [@springframework](http://twitter.com/springframework) and its\r\n[team members](http://twitter.com/springframework/team/members) on Twitter. In-depth articles can be\r\nfound at the SpringSource [team blog](http://blog.springsource.org), and releases are announced via\r\nour [news feed](http://www.springsource.org/news-events).\r\n\r\n## License\r\nThe Spring Framework is released under version 2.0 of the\r\n[Apache License](http://www.apache.org/licenses/LICENSE-2.0).\r\n');
insert into `book` (`id`, `name`, `price`, `pic`, `description`) values('5','solr','78.0','99999229292.jpg','solr是一个全文检索服务\r\n# Licensed to the Apache Software Foundation (ASF) under one or more\r\n# contributor license agreements.  See the NOTICE file distributed with\r\n# this work for additional information regarding copyright ownership.\r\n# The ASF licenses this file to You under the Apache License, Version 2.0\r\n# (the \"License\"); you may not use this file except in compliance with\r\n# the License.  You may obtain a copy of the License at\r\n#\r\n#     http://www.apache.org/licenses/LICENSE-2.0\r\n#\r\n# Unless required by applicable law or agreed to in writing, software\r\n# distributed under the License is distributed on an \"AS IS\" BASIS,\r\n# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\r\n# See the License for the specific language governing permissions and\r\n# limitations under the License.\r\n\r\n\r\nWelcome to the Apache Solr project!\r\n-----------------------------------\r\n\r\nSolr is the popular, blazing fast open source enterprise search platform\r\nfrom the Apache Lucene project.\r\n\r\nFor a complete description of the Solr project, team composition, source\r\ncode repositories, and other details, please see the Solr web site at\r\nhttp://lucene.apache.org/solr\r\n\r\n\r\nGetting Started\r\n---------------\r\n\r\nSee the \"example\" directory for an example Solr setup.  A tutorial\r\nusing the example setup can be found at\r\n   http://lucene.apache.org/solr/tutorial.html\r\nor linked from \"docs/index.html\" in a binary distribution.\r\nAlso, there are Solr clients for many programming languages, see \r\n   http://wiki.apache.org/solr/IntegratingSolr\r\n\r\n\r\nFiles included in an Apache Solr binary distribution\r\n----------------------------------------------------\r\n\r\nexample/\r\n  A self-contained example Solr instance, complete with a sample\r\n  configuration, documents to index, and the Jetty Servlet container.\r\n  Please see example/README.txt for information about running this\r\n  example.\r\n\r\ndist/solr-XX.war\r\n  The Apache Solr Application.  Deploy this WAR file to any servlet\r\n  container to run Apache Solr.\r\n\r\ndist/solr-<component>-XX.jar\r\n  The Apache Solr libraries.  To compile Apache Solr Plugins,\r\n  one or more of these will be required.  The core library is\r\n  required at a minimum. (see http://wiki.apache.org/solr/SolrPlugins\r\n  for more information).\r\n\r\ndocs/index.html\r\n  The Apache Solr Javadoc API documentation and Tutorial\r\n\r\n\r\nInstructions for Building Apache Solr from Source\r\n-------------------------------------------------\r\n\r\n1. Download the Java SE 7 JDK (Java Development Kit) or later from http://java.sun.com/\r\n   You will need the JDK installed, and the $JAVA_HOME/bin (Windows: %JAVA_HOME%\\bin) \r\n   folder included on your command path. To test this, issue a \"java -version\" command \r\n   from your shell (command prompt) and verify that the Java version is 1.7 or later.\r\n\r\n2. Download the Apache Ant binary distribution (1.8.2+) from \r\n   http://ant.apache.org/  You will need Ant installed and the $ANT_HOME/bin (Windows: \r\n   %ANT_HOME%\\bin) folder included on your command path. To test this, issue a \r\n   \"ant -version\" command from your shell (command prompt) and verify that Ant is \r\n   available. \r\n\r\n   You will also need to install Apache Ivy binary distribution (2.2.0) from \r\n   http://ant.apache.org/ivy/ and place ivy-2.2.0.jar file in ~/.ant/lib -- if you skip \r\n   this step, the Solr build system will offer to do it for you.\r\n\r\n3. Download the Apache Solr distribution, linked from the above web site. \r\n   Unzip the distribution to a folder of your choice, e.g. C:\\solr or ~/solr\r\n   Alternately, you can obtain a copy of the latest Apache Solr source code\r\n   directly from the Subversion repository:\r\n\r\n     http://lucene.apache.org/solr/versioncontrol.html\r\n\r\n4. Navigate to the \"solr\" folder and issue an \"ant\" command to see the available options\r\n   for building, testing, and packaging Solr.\r\n  \r\n   NOTE: \r\n   To see Solr in action, you may want to use the \"ant example\" command to build\r\n   and package Solr into the example/webapps directory. See also example/README.txt.\r\n\r\n\r\nExport control\r\n-------------------------------------------------\r\nThis distribution includes cryptographic software.  The country in\r\nwhich you currently reside may have restrictions on the import,\r\npossession, use, and/or re-export to another country, of\r\nencryption software.  BEFORE using any encryption software, please\r\ncheck your country\'s laws, regulations and policies concerning the\r\nimport, possession, or use, and re-export of encryption software, to\r\nsee if this is permitted.  See <http://www.wassenaar.org/> for more\r\ninformation.\r\n\r\nThe U.S. Government Department of Commerce, Bureau of Industry and\r\nSecurity (BIS), has classified this software as Export Commodity\r\nControl Number (ECCN) 5D002.C.1, which includes information security\r\nsoftware using or performing cryptographic functions with asymmetric\r\nalgorithms.  The form and manner of this Apache Software Foundation\r\ndistribution makes it eligible for export under the License Exception\r\nENC Technology Software Unrestricted (TSU) exception (see the BIS\r\nExport Administration Regulations, Section 740.13) for both object\r\ncode and source code.\r\n\r\nThe following provides more details on the included cryptographic\r\nsoftware:\r\n    Apache Solr uses the Apache Tika which uses the Bouncy Castle generic encryption libraries for\r\n    extracting text content and metadata from encrypted PDF files.\r\n    See http://www.bouncycastle.org/ for more details on Bouncy Castle.\r\n');
