# SVN的概述和安装 
	
## 技术分析之svn的概述
1. svn的概述
	- SVN是Subversion的简称，是一个开放源代码的版本控制系统，相较于RCS、CVS，它采用了分支管理系统，它的设计目标就是取代CVS。
	- 互联网上很多版本控制服务已从CVS迁移到Subversion
	- 说得简单一点SVN就是用于多个人共同开发同一个项目，共用资源的目的。
	- 解决团队开发中的代码管理的问题
	
2. 如果没有svn，一般会遇到的问题
	- 代码管理混乱
	- 备份多个版本，占用磁盘空间大
	- 解决代码冲突困难
	- 容易引发BUG
	- 难于追溯问题代码的修改人和修改时间
	- 难于恢复至以前正确版本
	- 无法进行权限控制
	- 项目版本发布困难
	- 使用SVN工具，就可以管理上述的这一类问题！！
	
3. 简单的术语
	- 仓库
	- 提交
	- 检出/更新
	
4. 常用的版本的控制工具
	- VSS、CVS、SVN、GIT

## 技术分析之svn的体系结构
1. 体系结构如图（看SVN的使用手册的图）
	- SVN的仓库
		- DB：使用数据库作为SVN的仓库
		- FSFS：使用系统文件方式作为SVN的仓库
		
	- SVN仓库的访问(SVN的服务器)
		- 整合Apache形式:http协议访问仓库
		- svnserve的方式:使用命令开启SVN的服务,使用SVN的协议访问
		
	- SVN的客户端
		- 命令行访问
		- 图形化界面
	
![](../pictures/01-svn的体系结构.bmp)

## 技术分析之创建SVN的仓库（必须要先创建仓库，才能启动svn服务）
1. SVN已经安装完成了，下面需要创建SVN的仓库
	- 在某一个盘下创建文件夹，随意创建。例如：在C盘下创建Repositories文件，一个仓库可以存放多个项目，在Repositories文件夹中再创建多个子文件夹。代表不同的项目
	
2. 可以使用命令的方式或者图形化界面的方式来创建
	- 命令的方式：svnadmin create c:\Repositories\crm
	
3. 仓库创建好后，有如下的一些目录结构
	- conf：配置文件（重点的配置文件）
	- svnserve.conf：SVN的配置文件
	- passwd：用户名和密码的文件
	- authz：权限认证的文件
		
	- db：版本数据存储目录
	- hooks：存放版本库勾子目录
	- locks：存储库锁目录，用来跟踪库的访问者
	
4. 启动仓库
	1. 启动仓库分成两种主要的形式
		- 单仓库启动（只启动其中的一个仓库）
		- 多仓库启动（启动所有的仓库）
		
	2. 启动仓库的方式有三种
		- 命令行启动
			- 单仓库启动:
				- `svnserve -d -r C:\Repositories\crm`
			- 多仓库启动:
				- `svnserve -d -r C:\Repositories`
			
		- 使用批处理文件的方式启动，先创建一个批处理的文件，在该文件中复制如下命令
			- 单仓库启动:
				- `svnserve -d -r C:\Repositories\crm`
			- 多仓库启动:
				- `svnserve -d -r C:\Repositories`
			
		- 在系统上注册服务的方式，以后只要电脑一开机就自动启动了，注意：以下命令不要复制错误了！！
			- 命令：`sc create SVN-Service binpath= "C:\Program Files (x86)\Subversion\bin\svnserve.exe --service -r C:\Repositories" displayname= "SVN-Service" start= auto depend= Tcpip`
				- 注意修改的地方1：C:\Program Files (x86)\Subversion\bin是自己的安装路径
				- 注意修改的地方2：C:\Repositories创建的路径（多仓库启动的方式）
			
	3. 总结：采用服务的方式启动仓库和如果删除服务
		- 命令：`sc create SVN-Service binpath= "C:\Program Files (x86)\Subversion\bin\svnserve.exe --service -r C:\Repositories" displayname= "SVN-Service" start= auto depend= Tcpip`
			
		- 如果想要删除服务：`sc delete SVN-Service`

## 技术分析之设置权限
- 步骤一:找仓库中的conf/svnserve.conf
- 步骤二:修改配置文件
	- `anon-access = none`：匿名用户没有权限。(取值:none/read/write)		
	- `auth-access = write`：认证用户有读写权限
	- `password-db = passwd`：让passwd的文件生效
	- `authz-db = authz`：让authz的文件生效
	
- 步骤三 :打开passwd文件:设置用户名和密码
	- xx=123
	- yy=123
	
- 步骤四	:打开authz文件:设置用户的权限
```
[groups]
crmGroup=xx,yy

[/]
@crmGroup=rw
* = 
```

## 技术分析之解决代码冲突问题（冲突一定会存在，必须要会解决）
1. 两个人都修改了同一个文件，然后一个先提交了，后提交的用户就会产生冲突。
2. 后提交的人需要来解决冲突
	- 先更新，然后删除掉多余的内容
	- 再重新提交