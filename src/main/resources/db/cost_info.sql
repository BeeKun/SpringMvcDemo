/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50557
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50557
File Encoding         : 65001

Date: 2018-01-10 10:30:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cost_info
-- ----------------------------
DROP TABLE IF EXISTS `cost_info`;
CREATE TABLE `cost_info` (
  `id` varchar(50) NOT NULL COMMENT '主键UUID',
  `costMoney` int(10) DEFAULT NULL COMMENT '金额',
  `costDescription` varchar(255) DEFAULT NULL COMMENT '消费明细',
  `costAddress` varchar(255) DEFAULT NULL,
  `costTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `costPictureName` varchar(255) DEFAULT NULL,
  `EXT1` varchar(255) DEFAULT NULL,
  `EXT2` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `account` varchar(25) DEFAULT NULL COMMENT '用户账号',
  `password` varchar(50) DEFAULT NULL COMMENT '用户密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` varchar(20) DEFAULT '1' COMMENT '1:有效，0:禁止登录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

