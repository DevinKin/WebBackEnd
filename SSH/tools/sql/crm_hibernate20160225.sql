/*
SQLyog v10.2 
MySQL - 5.1.72-community : Database - crm_hibernate
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `base_dict` */

CREATE TABLE `base_dict` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据字典id(主键)',
  `dict_type_code` varchar(10) NOT NULL COMMENT '数据字典类别代码',
  `dict_type_name` varchar(64) NOT NULL COMMENT '数据字典类别名称',
  `dict_item_name` varchar(64) NOT NULL COMMENT '数据字典项目名称',
  `dict_item_code` varchar(10) DEFAULT NULL COMMENT '数据字典项目(可为空)',
  `dict_sort` int(10) DEFAULT NULL COMMENT '排序字段',
  `dict_enable` char(1) NOT NULL COMMENT '1:使用 0:停用',
  `dict_memo` varchar(64) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

/*Data for the table `base_dict` */

LOCK TABLES `base_dict` WRITE;

insert  into `base_dict`(`dict_id`,`dict_type_code`,`dict_type_name`,`dict_item_name`,`dict_item_code`,`dict_sort`,`dict_enable`,`dict_memo`) values (1,'001','客户行业','教育培训 ',NULL,1,'1',NULL),(2,'001','客户行业','电子商务',NULL,2,'1',NULL),(3,'001','客户行业','对外贸易',NULL,3,'1',NULL),(4,'001','客户行业','酒店旅游',NULL,4,'1',NULL),(5,'001','客户行业','房地产',NULL,5,'1',NULL),(6,'002','客户信息来源','电话营销',NULL,1,'1',NULL),(7,'002','客户信息来源','网络营销',NULL,2,'1',NULL),(8,'003','公司性质','合资',NULL,1,'1',NULL),(9,'003','公司性质','国企',NULL,2,'1',NULL),(10,'003','公司性质','民企',NULL,3,'1',NULL),(12,'004','年营业额','1-10万',NULL,1,'1',NULL),(13,'004','年营业额','10-20万',NULL,2,'1',NULL),(14,'004','年营业额','20-50万',NULL,3,'1',NULL),(15,'004','年营业额','50-100万',NULL,4,'1',NULL),(16,'004','年营业额','100-500万',NULL,5,'1',NULL),(17,'004','年营业额','500-1000万',NULL,6,'1',NULL),(18,'005','客户状态','基础客户',NULL,1,'1',NULL),(19,'005','客户状态','潜在客户',NULL,2,'1',NULL),(20,'005','客户状态','成功客户',NULL,3,'1',NULL),(21,'005','客户状态','无效客户',NULL,4,'1',NULL),(22,'006','客户级别','普通客户',NULL,1,'1',NULL),(23,'006','客户级别','VIP客户',NULL,2,'1',NULL),(24,'007','商机状态','意向客户',NULL,1,'1',NULL),(25,'007','商机状态','初步沟通',NULL,2,'1',NULL),(26,'007','商机状态','深度沟通',NULL,3,'1',NULL),(27,'007','商机状态','签订合同',NULL,4,'1',NULL),(30,'008','商机类型','新业务',NULL,1,'1',NULL),(31,'008','商机类型','现有业务',NULL,2,'1',NULL),(32,'009','商机来源','电话营销',NULL,1,'1',NULL),(33,'009','商机来源','网络营销',NULL,2,'1',NULL),(34,'009','商机来源','推广活动',NULL,3,'1',NULL);

UNLOCK TABLES;

/*Table structure for table `cst_customer` */

CREATE TABLE `cst_customer` (
  `cust_id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '客户编号(主键)',
  `cust_name` varchar(32) NOT NULL COMMENT '客户名称(公司名称)',
  `cust_user_id` bigint(32) DEFAULT NULL COMMENT '负责人id',
  `cust_create_id` bigint(32) DEFAULT NULL COMMENT '创建人id',
  `cust_source` varchar(32) DEFAULT NULL COMMENT '客户信息来源',
  `cust_industry` varchar(32) DEFAULT NULL COMMENT '客户所属行业',
  `cust_level` varchar(32) DEFAULT NULL COMMENT '客户级别',
  `cust_linkman` varchar(64) DEFAULT NULL COMMENT '联系人',
  `cust_phone` varchar(64) DEFAULT NULL COMMENT '固定电话',
  `cust_mobile` varchar(16) DEFAULT NULL COMMENT '移动电话',
  PRIMARY KEY (`cust_id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8;

/*Data for the table `cst_customer` */

LOCK TABLES `cst_customer` WRITE;

insert  into `cst_customer`(`cust_id`,`cust_name`,`cust_user_id`,`cust_create_id`,`cust_source`,`cust_industry`,`cust_level`,`cust_linkman`,`cust_phone`,`cust_mobile`) values (2,'小军',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,'小军',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(27,'小红',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(28,'北',NULL,NULL,NULL,NULL,NULL,'','',''),(63,'小',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(64,'小',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(68,'小',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(69,'小',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(70,'小',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(73,'小',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(74,'小',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(75,'小',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(76,'小',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(77,'小',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(78,'小',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(79,'小',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(80,'小',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(82,'小红',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(83,'小红',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(84,'小红',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(85,'小军',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(86,'小军',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(93,'小军',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

UNLOCK TABLES;

/*Table structure for table `cst_customer_detail` */

CREATE TABLE `cst_customer_detail` (
  `cust_id` bigint(32) NOT NULL,
  `cust_region` varchar(64) DEFAULT NULL COMMENT '客户地区',
  `cust_zip` varchar(16) DEFAULT NULL COMMENT '客户邮政编码',
  `cust_address` varchar(128) DEFAULT NULL COMMENT '客户联系地址',
  `cust_fax` varchar(64) DEFAULT NULL COMMENT '客户传真',
  `cust_website` varchar(128) DEFAULT NULL COMMENT '客户网址',
  `cust_licence` varchar(64) DEFAULT NULL COMMENT '客户营业执照注册号',
  `cust_corporation` varchar(64) DEFAULT NULL COMMENT '企业法人',
  `cust_capital` bigint(16) DEFAULT NULL COMMENT '客户注册资金',
  `cust_bank` varchar(512) DEFAULT NULL COMMENT '开户银行及账号',
  `cust_memo` longtext COMMENT '客户简介',
  PRIMARY KEY (`cust_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `cst_customer_detail` */

LOCK TABLES `cst_customer_detail` WRITE;

insert  into `cst_customer_detail`(`cust_id`,`cust_region`,`cust_zip`,`cust_address`,`cust_fax`,`cust_website`,`cust_licence`,`cust_corporation`,`cust_capital`,`cust_bank`,`cust_memo`) values (19,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(27,NULL,NULL,'郑',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(28,NULL,'','北',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(47,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(48,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(50,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(51,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(53,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(55,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(56,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(58,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(59,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(60,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(68,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(69,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(70,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(72,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(73,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(74,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(75,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(76,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(77,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(78,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(79,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(82,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(83,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(84,NULL,NULL,'郑州',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(93,NULL,NULL,'北京昌平区',NULL,NULL,NULL,NULL,NULL,NULL,NULL);

UNLOCK TABLES;

/*Table structure for table `cst_linkman` */

CREATE TABLE `cst_linkman` (
  `lkm_id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '联系人编号(主键)',
  `lkm_name` varchar(16) DEFAULT NULL COMMENT '联系人姓名',
  `lkm_cust_id` bigint(32) NOT NULL COMMENT '客户id',
  `lkm_gender` char(1) DEFAULT NULL COMMENT '联系人性别',
  `lkm_phone` varchar(16) DEFAULT NULL COMMENT '联系人办公电话',
  `lkm_mobile` varchar(16) DEFAULT NULL COMMENT '联系人手机',
  `lkm_email` varchar(64) DEFAULT NULL COMMENT '联系人邮箱',
  `lkm_qq` varchar(16) DEFAULT NULL COMMENT '联系人qq',
  `lkm_position` varchar(16) DEFAULT NULL COMMENT '联系人职位',
  `lkm_memo` varchar(512) DEFAULT NULL COMMENT '联系人备注',
  PRIMARY KEY (`lkm_id`),
  KEY `FK_cst_linkman_lkm_cust_id` (`lkm_cust_id`),
  CONSTRAINT `FK_cst_linkman_lkm_cust_id` FOREIGN KEY (`lkm_cust_id`) REFERENCES `cst_customer` (`cust_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `cst_linkman` */

LOCK TABLES `cst_linkman` WRITE;

insert  into `cst_linkman`(`lkm_id`,`lkm_name`,`lkm_cust_id`,`lkm_gender`,`lkm_phone`,`lkm_mobile`,`lkm_email`,`lkm_qq`,`lkm_position`,`lkm_memo`) values (2,'王总',27,NULL,'4354353',NULL,NULL,NULL,NULL,NULL);

UNLOCK TABLES;

/*Table structure for table `hibernate_sequence` */

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `hibernate_sequence` */

LOCK TABLES `hibernate_sequence` WRITE;

insert  into `hibernate_sequence`(`next_val`) values (4);

UNLOCK TABLES;

/*Table structure for table `sale_chance` */

CREATE TABLE `sale_chance` (
  `chan_id` varchar(32) NOT NULL COMMENT '商机id',
  `chan_name` varchar(64) NOT NULL COMMENT '商机名称',
  `chan_cust_id` bigint(32) NOT NULL COMMENT '客户id',
  `chan_user_id` bigint(32) NOT NULL COMMENT '负责人id',
  `chan_money` int(16) DEFAULT NULL COMMENT '商机金额',
  `chan_type` varchar(32) DEFAULT NULL COMMENT '商机类型',
  `chan_source` varchar(32) DEFAULT NULL COMMENT '商机来源',
  `chan_state` varchar(32) DEFAULT NULL COMMENT '商机状态',
  `chan_lkm_id` bigint(32) DEFAULT NULL COMMENT '联系人id',
  `chan_linktime` date DEFAULT NULL COMMENT '联系时间',
  `chan_desc` varchar(1024) DEFAULT NULL COMMENT '机会描述',
  PRIMARY KEY (`chan_id`),
  KEY `FK_sale_chance_chan_user_id` (`chan_user_id`),
  KEY `FK_sale_chance_chan_cust_id` (`chan_cust_id`),
  CONSTRAINT `FK_sale_chance_chan_cust_id` FOREIGN KEY (`chan_cust_id`) REFERENCES `cst_customer` (`cust_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_sale_chance_chan_user_id` FOREIGN KEY (`chan_user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sale_chance` */

LOCK TABLES `sale_chance` WRITE;

UNLOCK TABLES;

/*Table structure for table `sale_visit` */

CREATE TABLE `sale_visit` (
  `visit_id` varchar(32) NOT NULL,
  `visit_cust_id` bigint(32) DEFAULT NULL COMMENT '客户id',
  `visit_user_id` bigint(32) DEFAULT NULL COMMENT '负责人id',
  `visit_lkm_id` bigint(32) DEFAULT NULL COMMENT '联系人id',
  `visit_time` date DEFAULT NULL COMMENT '拜访时间',
  `visit_addr` varchar(128) DEFAULT NULL COMMENT '拜访地点',
  `visit_detail` varchar(256) DEFAULT NULL COMMENT '拜访详情',
  `visit_nexttime` date DEFAULT NULL COMMENT '下次拜访时间',
  PRIMARY KEY (`visit_id`),
  KEY `FK_sale_visit_cust_id` (`visit_cust_id`),
  KEY `FK_sale_visit_user_id` (`visit_user_id`),
  KEY `FK_sale_visit_lkm_id` (`visit_lkm_id`),
  CONSTRAINT `FK_sale_visit_cust_id` FOREIGN KEY (`visit_cust_id`) REFERENCES `cst_customer` (`cust_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_sale_visit_lkm_id` FOREIGN KEY (`visit_lkm_id`) REFERENCES `cst_linkman` (`lkm_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_sale_visit_user_id` FOREIGN KEY (`visit_user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sale_visit` */

LOCK TABLES `sale_visit` WRITE;

insert  into `sale_visit`(`visit_id`,`visit_cust_id`,`visit_user_id`,`visit_lkm_id`,`visit_time`,`visit_addr`,`visit_detail`,`visit_nexttime`) values ('2c909190530346f001530346f3680000',27,5,NULL,NULL,NULL,NULL,NULL);

UNLOCK TABLES;

/*Table structure for table `sys_role` */

CREATE TABLE `sys_role` (
  `role_id` bigint(32) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) NOT NULL COMMENT '角色名称',
  `role_memo` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

LOCK TABLES `sys_role` WRITE;

insert  into `sys_role`(`role_id`,`role_name`,`role_memo`) values (1,'员工',NULL),(2,'部门经理',NULL),(3,'部门经理',NULL),(4,'部门经理',NULL),(5,'员工',NULL);

UNLOCK TABLES;

/*Table structure for table `sys_user` */

CREATE TABLE `sys_user` (
  `user_id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_code` varchar(32) NOT NULL COMMENT '用户账号',
  `user_name` varchar(64) NOT NULL COMMENT '用户名称',
  `user_password` varchar(32) NOT NULL COMMENT '用户密码',
  `user_state` char(1) NOT NULL COMMENT '1:正常,0:暂停',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

LOCK TABLES `sys_user` WRITE;

insert  into `sys_user`(`user_id`,`user_code`,`user_name`,`user_password`,`user_state`) values (5,'m0003','小军','123','1'),(6,'m0001','小红','123','1'),(7,'m0001','小明','123','1'),(8,'m0001','小红','123','1');

UNLOCK TABLES;

/*Table structure for table `sys_user_detail` */

CREATE TABLE `sys_user_detail` (
  `user_detail_id` bigint(32) NOT NULL,
  `user_age` int(2) DEFAULT NULL COMMENT '年龄',
  `user_addr` varchar(128) DEFAULT NULL COMMENT '员工住址',
  `user_resume` longtext COMMENT '简历',
  PRIMARY KEY (`user_detail_id`),
  CONSTRAINT `FK_user_detail_id` FOREIGN KEY (`user_detail_id`) REFERENCES `sys_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_detail` */

LOCK TABLES `sys_user_detail` WRITE;

insert  into `sys_user_detail`(`user_detail_id`,`user_age`,`user_addr`,`user_resume`) values (5,NULL,'北京昌平',NULL);

UNLOCK TABLES;

/*Table structure for table `sys_user_role` */

CREATE TABLE `sys_user_role` (
  `role_id` bigint(32) NOT NULL COMMENT '角色id',
  `user_id` bigint(32) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FK_user_role_user_id` (`user_id`),
  CONSTRAINT `FK_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

LOCK TABLES `sys_user_role` WRITE;

insert  into `sys_user_role`(`role_id`,`user_id`) values (4,7),(5,8);

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
