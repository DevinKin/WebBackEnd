# SpringBoot与Docker

## 环境搭建

- 安装mysql
- 安装redis
- 安装rabbitmq
- 安装elasticsearch

## 安装mysql

- 安装mysql

  ```shell
  docker pull mysql
  ```

- 启动mysql服务示例

  ```shell
  docker run --name mysql01 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
  ```

  