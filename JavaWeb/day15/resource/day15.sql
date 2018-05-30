CREATE DATABASE IF NOT EXISTS day15;
use day15;
CREATE TABLE IF NOT EXISTS `user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) DEFAULT NULL,
  `password` VARCHAR(20) DEFAULT NULL,
  `email` VARCHAR(20) DEFAULT NULL,
  `name` VARCHAR(20) DEFAULT NULL,
  `sex` VARCHAR(10) DEFAULT NULL,
  `birthday` DATE DEFAULT NULL,
  `hobby` VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
INSERT  INTO `user`(`id`,`username`,`password`,`email`,`name`,`sex`,`birthday`,`hobby`) VALUES (1,'bbb','123','123@163.com','张三','男','1992-01-02','篮球, 足球, 排球'),(2,'ccc','123','ccc@itcast.cn','李四','女','1992-03-20','排球, 乒乓球'),(3,'aaa','123','aaa@itcast.cn','王守义','男','1990-08-11','足球, 排球'),(5,'tom','123','haha@qq.com','提莫','男',NULL,'篮球');