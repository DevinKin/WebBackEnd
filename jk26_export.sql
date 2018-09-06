/*
SQLyog Enterprise - MySQL GUI v6.03
Host - 5.0.22-community-nt : Database - jk_export
*********************************************************************
Server version : 5.0.22-community-nt
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

create database if not exists `jk_export`;

USE `jk_export`;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table `export_c` */

DROP TABLE IF EXISTS `export_c`;

CREATE TABLE `export_c` (
  `EID` varchar(40) NOT NULL,
  `EXPORT_ID` varchar(40) NOT NULL,
  `INPUT_DATE` datetime default NULL,
  `SHIPMENT_PORT` varchar(100) default NULL,
  `DESTINATION_PORT` varchar(100) default NULL,
  `TRANSPORT_MODE` varchar(10) default NULL COMMENT 'SEA/AIR',
  `PRICE_CONDITION` varchar(10) default NULL COMMENT 'FBO/CIF',
  `BOX_NUMS` int(11) default NULL COMMENT '冗余，为委托服务，一个报运的总箱数',
  `GROSS_WEIGHTS` decimal(10,2) default NULL COMMENT '冗余，为委托服务，一个报运的总毛重',
  `MEASUREMENTS` decimal(10,2) default NULL COMMENT '冗余，为委托服务，一个报运的总体积',
  `STATE` int(11) default NULL,
  `REASON` varchar(200) default NULL,
  `ORDER_NO` int(11) default NULL,
  `EXPORT_DATE` datetime default NULL COMMENT '申批时间',
  PRIMARY KEY  (`EID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `export_c` */

/*Table structure for table `export_product_c` */

DROP TABLE IF EXISTS `export_product_c`;

CREATE TABLE `export_product_c` (
  `EP_ID` varchar(40) NOT NULL,
  `EID` varchar(40) default NULL,
  `EXPORT_PRODUCT_ID` varchar(40) NOT NULL,
  `EXPORT_ID` varchar(40) default NULL,
  `FACTORY_ID` varchar(40) default NULL,
  `PRODUCT_NO` varchar(50) default NULL,
  `PACKING_UNIT` varchar(10) default NULL COMMENT 'PCS/SETS',
  `CNUMBER` int(11) default NULL,
  `BOX_NUM` int(11) default NULL,
  `GROSS_WEIGHT` decimal(10,2) default NULL,
  `NET_WEIGHT` decimal(10,2) default NULL,
  `SIZE_LENGTH` decimal(10,2) default NULL,
  `SIZE_WIDTH` decimal(10,2) default NULL,
  `SIZE_HEIGHT` decimal(10,2) default NULL,
  `EX_PRICE` decimal(10,2) default NULL COMMENT 'sales confirmation 中的价格（手填）',
  `PRICE` decimal(10,2) default NULL,
  `TAX` decimal(10,2) default NULL COMMENT '收购单价=合同单价',
  `ORDER_NO` int(11) default NULL,
  PRIMARY KEY  (`EP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `export_product_c` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
