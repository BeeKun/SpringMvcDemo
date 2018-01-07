CREATE TABLE `cost_info` (
  `id` varchar(50) NOT NULL COMMENT '主键UUID',
  `costMoney` int(10) DEFAULT NULL COMMENT '金额',
  `costDescription` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '消费明细',
  `costAddress` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `costTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;