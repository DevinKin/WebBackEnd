# Ngninx
1. Nginx是一款轻量级的Web服务器/反向代理及电子邮件(IMAP/POP3)代理服务器
## 特点
1. 反向代理
2. 负载均衡
3. 动静分离

### 反向代理
1. 正向代理：
    1. 代理服务器替代访问方去访问目标服务器
    2. 需要用户手动设置代理服务器的ip和端口号
    3. 代理对象是用户

2. 反向代理(Reverse Proxy)
    1. 代理原始资源的服务器，用户不需要设置ip和端口号
    2. 代理对象是服务器

### 负载均衡(Load Balance)
    1. 建立在现有网络结构之上，并提供了一种廉价有效透明的方法扩展网络设备和服务器的贷款、增加吞吐量、加强网络数据处理能力、提高网络的灵活性和可用性
    2. 原理就是将数据流量分摊到多个服务器上执行，减轻每台服务器的压力，多台服务器共同完成工作任务，从而提高数据的吞吐量

### 反向代理配置
1. 在`http{}`中
```
	upstream tomcat_server1{
    	    server 127.0.0.1:8090;
	}

	upstream tomcat_server2{
    	    server 127.0.0.1:8100;
	}

	server {
	   listen 80;
	   server_name www.test1.com;
	   location / {
	     proxy_pass http://tomcat_server1;
	     index 	index.jsp index.html index.htm;
	   }
	}

	server {
	   listen 82;
	   server_name www.test2.com;
	   location / {
	     proxy_pass http://tomcat_server2;
	     index 	index.jsp index.html index.htm;
	   }
	}
```
