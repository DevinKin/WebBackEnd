# 项目总结
## 第一天
1. 电商行业的背景
    - b2b
    - b2c
    - b2b2c
    - c2c
    - o2o2
2. 系统的架构
    - 基于soa的架构:面向服务的架构
3. 工程搭建,使用maven管理工程

## 第二天
1. SSM框架整合
2. 使用dubbo进行通信
    - 服务提供者
    - 服务消费者
    - 注册中心:Zookeeper
    - 监控中心:dubbo提供
3. 商品列表查询
    - PageHelper分页插件
    - EasyUI的DataGrid控件

## 第三天
1. 商品添加
    - 商品分类选择:EasyUI的Tree控件
2. 图片上传
    - 图片服务器:FastDFS,Tracker,Storage
    - 实现图片上传:使用KindEditor的插件
3. 富文本编辑器        
4. 商品添加功能实现
        
## 第四天
1. 商城首页展示
2. cms内容分类管理
    - 内容分类管理
    - 内容管理
3. 前台从数据库中取内容信息实现动态展示

## 第五天
1. redis的安装
2. redis的启动
3. redis的5种数据类型
4. redisCluster
    - 至少有3个节点
    - JedisCluster对象操作集群
5. 向业务逻辑中添加缓存
6. 缓存同步
    
## 第六天
1. 搜索功能的实现
    - 使用solr做搜索
    - 配置业务域中中文分析器
    - 商品数据导入索引库
    - 搜索的实现
## 第七天
1. solrCloud
    - zookeeper集群
    - Solr集群
2. 使用solrJ连接洁群
    - 使用CloudSolrServer对象连接集群
    
    
## 第八天
1. Activemq
2. queue:点到点通信
    - 默认缓存
3. topic:广播
    - 默认不缓存
4. Producer
5. Consumer
6. 作用是系统之间解耦使用的,实现数据的最终一致
    
## 第九天
1. 商品详情页面展示
    - jsp+redis
    - 缓存设置有效期
2. 网页静态化
    - freemarker
    - 创建模板
    - 使用freemarker生成静态页面
    
## 第十天
1. nginx
    - 访问静态资源
    - 配置虚拟主机
    - 反向代理
    - 实现负载均衡

## 第十一天
1. sso系统
    - 主要解决分布式环境下Session共享的问题
2. 使用redis保存Sesison
3. token相当于jSessionId,要保存到cookie中

## 第十二天
1. 购物车
    - 把购物车保存到cookie中
    - 把购物车保存到服务端
2. 订单系统
    - 拦截器,判断用户是否登录
    - 订单确认页面
3. 生成订单,订单号可以使用redis的incr命令生成    

## 第十三天
1. 项目部署
2. 项目总结