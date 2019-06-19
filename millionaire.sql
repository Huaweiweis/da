/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : millionaire

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-06-19 10:20:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for accountdetails
-- ----------------------------
DROP TABLE IF EXISTS `accountdetails`;
CREATE TABLE `accountdetails` (
  `account_details_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账户明细id',
  `userid_id` int(11) DEFAULT NULL COMMENT '上分id',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `money` varchar(255) DEFAULT NULL,
  `add_time` varchar(255) DEFAULT NULL,
  `symbol` int(11) DEFAULT NULL COMMENT '加减符号    1加号，2减号',
  `beishangfenid` int(11) DEFAULT NULL COMMENT '被上分id',
  PRIMARY KEY (`account_details_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4407 DEFAULT CHARSET=utf8 COMMENT='账户明细记录';

-- ----------------------------
-- Records of accountdetails
-- ----------------------------
INSERT INTO `accountdetails` VALUES ('4391', null, '8', '10000.0', '2019-06-14 17:48:05', null, null);
INSERT INTO `accountdetails` VALUES ('4392', null, '8', '10000.0', '2019-06-14 17:49:22', null, null);
INSERT INTO `accountdetails` VALUES ('4393', null, '8', '10000.0', '2019-06-14 17:51:22', null, null);
INSERT INTO `accountdetails` VALUES ('4394', null, '8', '100.0', '2019-06-14 17:56:48', null, null);
INSERT INTO `accountdetails` VALUES ('4395', null, '8', '100.0', '2019-06-14 18:02:56', null, null);
INSERT INTO `accountdetails` VALUES ('4396', null, '8', '100.0', '2019-06-14 18:03:47', null, null);
INSERT INTO `accountdetails` VALUES ('4397', null, '8', '4100.0', '2019-06-14 18:04:02', null, null);
INSERT INTO `accountdetails` VALUES ('4398', null, '8', '100.0', '2019-06-14 18:07:08', null, null);
INSERT INTO `accountdetails` VALUES ('4399', null, '8', '100.0', '2019-06-14 18:09:11', null, null);
INSERT INTO `accountdetails` VALUES ('4400', null, '8', '100.0', '2019-06-14 18:10:55', null, null);
INSERT INTO `accountdetails` VALUES ('4401', null, '8', '100.0', '2019-06-14 18:12:17', null, null);
INSERT INTO `accountdetails` VALUES ('4402', '6', '9', '10000.0', '2019-06-14 18:12:36', null, null);
INSERT INTO `accountdetails` VALUES ('4403', null, '8', '100.0', '2019-06-14 18:12:45', null, null);
INSERT INTO `accountdetails` VALUES ('4404', null, '8', '1111.0', '2019-06-14 18:12:57', null, null);
INSERT INTO `accountdetails` VALUES ('4405', null, '8', '1111.0', '2019-06-14 18:13:09', null, null);
INSERT INTO `accountdetails` VALUES ('4406', null, '8', '20000.0', '2019-06-14 18:13:31', null, null);

-- ----------------------------
-- Table structure for agency
-- ----------------------------
DROP TABLE IF EXISTS `agency`;
CREATE TABLE `agency` (
  `agency_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '几级代理',
  `choushui_bili` double(11,2) DEFAULT NULL COMMENT '抽水比例',
  `top_up` double(11,2) DEFAULT NULL COMMENT '充值金额',
  `person_num` int(11) DEFAULT NULL COMMENT '线下人数',
  `add_time` varchar(255) DEFAULT NULL COMMENT '添加时间',
  `upd_time` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `ageny_class` int(11) DEFAULT NULL COMMENT '代理等级',
  PRIMARY KEY (`agency_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of agency
-- ----------------------------
INSERT INTO `agency` VALUES ('1', '0.05', '50000.00', '50', '2019-05-14 13:56:32', '2019-05-16 15:23:32', '1');
INSERT INTO `agency` VALUES ('2', '0.05', '30000.00', '30', '2019-05-14 16:48:20', null, '2');
INSERT INTO `agency` VALUES ('3', '0.05', '20000.00', '20', '2019-05-14 16:48:35', null, '3');
INSERT INTO `agency` VALUES ('4', '0.05', '10000.00', '10', '2019-05-14 16:48:54', null, '4');
INSERT INTO `agency` VALUES ('5', '0.25', '5000.00', '5', '2019-05-14 16:49:27', '2019-05-14 17:01:01', '5');
INSERT INTO `agency` VALUES ('6', '0.00', '0.00', '0', '2019-05-14 16:49:27', null, '6');

-- ----------------------------
-- Table structure for agreement
-- ----------------------------
DROP TABLE IF EXISTS `agreement`;
CREATE TABLE `agreement` (
  `agreement_id` int(11) NOT NULL,
  `agreement_name` longtext COMMENT '协议内容',
  `agreement_time` varchar(255) DEFAULT NULL COMMENT '添加时间',
  `agreement_update` varchar(255) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`agreement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='协议';

-- ----------------------------
-- Records of agreement
-- ----------------------------
INSERT INTO `agreement` VALUES ('1', '注册时请注意，如果在游戏中强退，可能会继续扣钱，直到破产。', '123', '2019-05-29 21:46:42');

-- ----------------------------
-- Table structure for applys
-- ----------------------------
DROP TABLE IF EXISTS `applys`;
CREATE TABLE `applys` (
  `applys_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '提出申请人',
  `money` double(11,2) DEFAULT NULL COMMENT '需要上分金额',
  `add_time` varchar(255) DEFAULT NULL COMMENT '申请时间',
  `upd_time` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `type` int(11) DEFAULT NULL COMMENT '1上分，2下分',
  `yaoqingma` varchar(255) DEFAULT NULL COMMENT '上级代理邀请码',
  PRIMARY KEY (`applys_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='申请表';

-- ----------------------------
-- Records of applys
-- ----------------------------

-- ----------------------------
-- Table structure for bonus
-- ----------------------------
DROP TABLE IF EXISTS `bonus`;
CREATE TABLE `bonus` (
  `bonus_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '存放id',
  `Bonus_mony` double(11,2) DEFAULT NULL COMMENT '随机选取金额',
  `room_id` int(255) DEFAULT NULL COMMENT '房间id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `add_time` varchar(255) DEFAULT NULL COMMENT '添加时间',
  `bonus_state` int(255) DEFAULT NULL COMMENT '红包状态，未领取0，已经领取1',
  `hongbao_id` int(11) DEFAULT NULL COMMENT '红包id',
  `zuida_money` int(11) DEFAULT NULL COMMENT '最大值   1',
  `dianshu` int(11) DEFAULT NULL COMMENT '红包点数',
  PRIMARY KEY (`bonus_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1295 DEFAULT CHARSET=utf8 COMMENT='发红包，红包金额存放表。';

-- ----------------------------
-- Records of bonus
-- ----------------------------
INSERT INTO `bonus` VALUES ('1216', '2.05', '478', '146', '2019-06-14 14:24:06', '1', '499', null, '146');
INSERT INTO `bonus` VALUES ('1217', '1.24', '478', '148', '2019-06-14 14:24:06', '1', '499', null, '148');
INSERT INTO `bonus` VALUES ('1218', '0.88', '478', '149', '2019-06-14 14:24:06', '1', '499', null, '149');
INSERT INTO `bonus` VALUES ('1219', '1.36', '478', '150', '2019-06-14 14:24:06', '1', '499', null, '150');
INSERT INTO `bonus` VALUES ('1220', '1.05', '478', '151', '2019-06-14 14:24:06', '1', '499', null, '151');
INSERT INTO `bonus` VALUES ('1221', '1.23', '478', '152', '2019-06-14 14:24:06', '1', '499', null, '152');
INSERT INTO `bonus` VALUES ('1222', '2.19', '478', '147', '2019-06-14 14:24:06', '1', '499', null, '147');
INSERT INTO `bonus` VALUES ('1223', '0.16', '478', '146', '2019-06-14 14:25:23', '1', '500', null, '146');
INSERT INTO `bonus` VALUES ('1224', '2.28', '478', '148', '2019-06-14 14:25:23', '1', '500', null, '148');
INSERT INTO `bonus` VALUES ('1225', '0.68', '478', '149', '2019-06-14 14:25:23', '1', '500', null, '149');
INSERT INTO `bonus` VALUES ('1226', '0.10', '478', '150', '2019-06-14 14:25:23', '1', '500', null, '150');
INSERT INTO `bonus` VALUES ('1227', '3.22', '478', '151', '2019-06-14 14:25:23', '1', '500', null, '151');
INSERT INTO `bonus` VALUES ('1228', '3.56', '478', '152', '2019-06-14 14:25:23', '1', '500', null, '152');
INSERT INTO `bonus` VALUES ('1229', '0.06', '478', '146', '2019-06-14 14:26:29', '1', '501', null, '146');
INSERT INTO `bonus` VALUES ('1230', '2.33', '478', '148', '2019-06-14 14:26:29', '1', '501', null, '148');
INSERT INTO `bonus` VALUES ('1231', '1.69', '478', '149', '2019-06-14 14:26:29', '1', '501', null, '149');
INSERT INTO `bonus` VALUES ('1232', '0.58', '478', '150', '2019-06-14 14:26:29', '1', '501', null, '150');
INSERT INTO `bonus` VALUES ('1233', '0.30', '478', '151', '2019-06-14 14:26:29', '1', '501', null, '151');
INSERT INTO `bonus` VALUES ('1234', '2.33', '478', '152', '2019-06-14 14:26:29', '1', '501', null, '152');
INSERT INTO `bonus` VALUES ('1235', '2.71', '478', '147', '2019-06-14 14:26:29', '1', '501', null, '147');
INSERT INTO `bonus` VALUES ('1236', '4.15', '489', '146', '2019-06-14 15:16:48', '1', '502', null, '146');
INSERT INTO `bonus` VALUES ('1237', '5.85', '489', '147', '2019-06-14 15:16:48', '1', '502', null, '147');
INSERT INTO `bonus` VALUES ('1238', '3.83', '489', '146', '2019-06-14 15:17:03', '1', '503', null, '146');
INSERT INTO `bonus` VALUES ('1239', '6.17', '489', '147', '2019-06-14 15:17:03', '1', '503', null, '147');
INSERT INTO `bonus` VALUES ('1240', '5.82', '489', '146', '2019-06-14 15:17:17', '1', '504', null, '146');
INSERT INTO `bonus` VALUES ('1241', '4.18', '489', '147', '2019-06-14 15:17:17', '1', '504', null, '147');
INSERT INTO `bonus` VALUES ('1242', '8.63', '489', '146', '2019-06-14 15:17:35', '1', '505', null, '146');
INSERT INTO `bonus` VALUES ('1243', '1.37', '489', '147', '2019-06-14 15:17:35', '1', '505', null, '147');
INSERT INTO `bonus` VALUES ('1244', '4.75', '489', '146', '2019-06-14 15:17:52', '1', '506', null, '146');
INSERT INTO `bonus` VALUES ('1245', '5.25', '489', '147', '2019-06-14 15:17:52', '1', '506', null, '147');
INSERT INTO `bonus` VALUES ('1246', '6.12', '489', '146', '2019-06-14 15:18:28', '1', '507', null, '146');
INSERT INTO `bonus` VALUES ('1247', '3.88', '489', '147', '2019-06-14 15:18:28', '1', '507', null, '147');
INSERT INTO `bonus` VALUES ('1248', '9.50', '490', '146', '2019-06-14 15:25:51', '1', '508', null, '146');
INSERT INTO `bonus` VALUES ('1249', '0.50', '490', '147', '2019-06-14 15:25:51', '1', '508', null, '147');
INSERT INTO `bonus` VALUES ('1250', '0.20', '491', '146', '2019-06-14 15:29:22', '1', '509', null, '146');
INSERT INTO `bonus` VALUES ('1251', '9.80', '491', '147', '2019-06-14 15:29:22', '1', '509', null, '147');
INSERT INTO `bonus` VALUES ('1252', '4.83', '493', '146', '2019-06-14 15:33:50', '1', '510', null, '146');
INSERT INTO `bonus` VALUES ('1253', '5.17', '493', '147', '2019-06-14 15:33:50', '1', '510', null, '147');
INSERT INTO `bonus` VALUES ('1254', '4.74', '493', '146', '2019-06-14 15:34:00', '1', '511', null, '146');
INSERT INTO `bonus` VALUES ('1255', '5.26', '493', '147', '2019-06-14 15:34:00', '1', '511', null, '147');
INSERT INTO `bonus` VALUES ('1256', '5.71', '494', '146', '2019-06-14 15:40:36', '1', '512', null, '146');
INSERT INTO `bonus` VALUES ('1257', '4.29', '494', '147', '2019-06-14 15:40:36', '1', '512', null, '147');
INSERT INTO `bonus` VALUES ('1258', '5.55', '494', '146', '2019-06-14 15:40:44', '1', '513', null, '146');
INSERT INTO `bonus` VALUES ('1259', '4.45', '494', '147', '2019-06-14 15:40:44', '1', '513', null, '147');
INSERT INTO `bonus` VALUES ('1260', '3.88', '494', '147', '2019-06-14 15:41:32', '1', '514', null, '147');
INSERT INTO `bonus` VALUES ('1261', '6.12', '494', '146', '2019-06-14 15:41:33', '1', '514', null, '146');
INSERT INTO `bonus` VALUES ('1262', '8.33', '494', '147', '2019-06-14 15:41:48', '1', '515', null, '147');
INSERT INTO `bonus` VALUES ('1263', '1.67', '494', '146', '2019-06-14 15:41:48', '1', '515', null, '146');
INSERT INTO `bonus` VALUES ('1264', '6.71', '494', '147', '2019-06-14 15:42:02', '1', '516', null, '147');
INSERT INTO `bonus` VALUES ('1265', '3.29', '494', '146', '2019-06-14 15:42:02', '1', '516', null, '146');
INSERT INTO `bonus` VALUES ('1266', '6.51', '495', '147', '2019-06-14 15:57:28', '1', '517', null, '147');
INSERT INTO `bonus` VALUES ('1267', '3.49', '495', '146', '2019-06-14 15:57:28', '1', '517', null, '146');
INSERT INTO `bonus` VALUES ('1268', '3.05', '495', '147', '2019-06-14 15:57:37', '1', '518', null, '147');
INSERT INTO `bonus` VALUES ('1269', '6.95', '495', '146', '2019-06-14 15:57:37', '1', '518', null, '146');
INSERT INTO `bonus` VALUES ('1270', '1.45', '496', '147', '2019-06-14 15:59:08', '1', '519', null, '147');
INSERT INTO `bonus` VALUES ('1271', '1.25', '496', '146', '2019-06-14 15:59:08', '1', '519', null, '146');
INSERT INTO `bonus` VALUES ('1272', '1.16', '496', '148', '2019-06-14 15:59:08', '1', '519', null, '148');
INSERT INTO `bonus` VALUES ('1273', '1.77', '496', '149', '2019-06-14 15:59:08', '1', '519', null, '149');
INSERT INTO `bonus` VALUES ('1274', '0.68', '496', '150', '2019-06-14 15:59:08', '1', '519', null, '150');
INSERT INTO `bonus` VALUES ('1275', '1.94', '496', '151', '2019-06-14 15:59:08', '1', '519', null, '151');
INSERT INTO `bonus` VALUES ('1276', '1.75', '496', '152', '2019-06-14 15:59:08', '1', '519', null, '152');
INSERT INTO `bonus` VALUES ('1277', '1.70', '496', '147', '2019-06-14 15:59:55', '1', '520', null, '147');
INSERT INTO `bonus` VALUES ('1278', '2.87', '496', '146', '2019-06-14 15:59:55', '1', '520', null, '146');
INSERT INTO `bonus` VALUES ('1279', '2.05', '496', '148', '2019-06-14 15:59:55', '1', '520', null, '148');
INSERT INTO `bonus` VALUES ('1280', '0.04', '496', '149', '2019-06-14 15:59:55', '1', '520', null, '149');
INSERT INTO `bonus` VALUES ('1281', '0.13', '496', '150', '2019-06-14 15:59:55', '1', '520', null, '150');
INSERT INTO `bonus` VALUES ('1282', '0.48', '496', '151', '2019-06-14 15:59:55', '1', '520', null, '151');
INSERT INTO `bonus` VALUES ('1283', '2.73', '496', '152', '2019-06-14 15:59:55', '1', '520', null, '152');
INSERT INTO `bonus` VALUES ('1284', '1.45', '496', '147', '2019-06-14 16:00:41', '1', '521', null, '147');
INSERT INTO `bonus` VALUES ('1285', '1.27', '496', '146', '2019-06-14 16:00:41', '1', '521', null, '146');
INSERT INTO `bonus` VALUES ('1286', '1.89', '496', '148', '2019-06-14 16:00:41', '1', '521', null, '148');
INSERT INTO `bonus` VALUES ('1287', '1.40', '496', '149', '2019-06-14 16:00:41', '1', '521', null, '149');
INSERT INTO `bonus` VALUES ('1288', '0.86', '496', '150', '2019-06-14 16:00:41', '1', '521', null, '150');
INSERT INTO `bonus` VALUES ('1289', '1.39', '496', '151', '2019-06-14 16:00:41', '1', '521', null, '151');
INSERT INTO `bonus` VALUES ('1290', '1.74', '496', '152', '2019-06-14 16:00:41', '1', '521', null, '152');
INSERT INTO `bonus` VALUES ('1291', '2.89', '497', '147', '2019-06-14 16:05:52', '1', '522', null, '147');
INSERT INTO `bonus` VALUES ('1292', '7.11', '497', '146', '2019-06-14 16:05:52', '1', '522', null, '146');
INSERT INTO `bonus` VALUES ('1293', '3.97', '497', '147', '2019-06-14 16:06:53', '1', '523', null, '147');
INSERT INTO `bonus` VALUES ('1294', '6.03', '497', '146', '2019-06-14 16:06:53', '1', '523', null, '146');

-- ----------------------------
-- Table structure for code
-- ----------------------------
DROP TABLE IF EXISTS `code`;
CREATE TABLE `code` (
  `code_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '验证码id',
  `code_name` varchar(255) DEFAULT NULL COMMENT '验证码',
  `add_tine` varchar(255) DEFAULT NULL COMMENT '添加时间',
  `up_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`code_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='验证码';

-- ----------------------------
-- Records of code
-- ----------------------------
INSERT INTO `code` VALUES ('28', '216922', '2019-05-30 19:29:36', null);
INSERT INTO `code` VALUES ('29', '211597', '2019-05-30 19:37:49', null);
INSERT INTO `code` VALUES ('30', '273884', '2019-06-03 14:06:05', null);
INSERT INTO `code` VALUES ('31', '361708', '2019-06-03 14:07:52', null);
INSERT INTO `code` VALUES ('32', '268247', '2019-06-03 14:14:33', null);
INSERT INTO `code` VALUES ('33', '761997', '2019-06-12 20:47:02', null);
INSERT INTO `code` VALUES ('34', '214212', '2019-06-12 20:48:12', null);
INSERT INTO `code` VALUES ('35', '604903', '2019-06-13 11:16:08', null);
INSERT INTO `code` VALUES ('36', '855727', '2019-06-13 11:20:36', null);
INSERT INTO `code` VALUES ('37', '734950', '2019-06-13 15:38:07', null);
INSERT INTO `code` VALUES ('38', '677464', '2019-06-13 15:39:42', null);

-- ----------------------------
-- Table structure for concurnaysay
-- ----------------------------
DROP TABLE IF EXISTS `concurnaysay`;
CREATE TABLE `concurnaysay` (
  `concurnaysay_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userid` int(11) DEFAULT NULL COMMENT '用户id',
  `leixing` int(11) DEFAULT NULL COMMENT '操作类型，上分，下分',
  `zhuangtai` int(11) DEFAULT NULL COMMENT '状态（同意、拒绝）',
  `shangxiatime` varchar(255) DEFAULT NULL COMMENT '上下分时间',
  `caozuoren` int(11) DEFAULT NULL COMMENT '操作人（具体是谁同意或拒绝）',
  `caozuotime` varchar(255) DEFAULT NULL COMMENT '操作时间（操作人操作时间）',
  `money` double(11,2) DEFAULT NULL COMMENT '申请金额',
  PRIMARY KEY (`concurnaysay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of concurnaysay
-- ----------------------------

-- ----------------------------
-- Table structure for customerservice
-- ----------------------------
DROP TABLE IF EXISTS `customerservice`;
CREATE TABLE `customerservice` (
  `customerservice_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客服id',
  `customerservice_phone` varchar(255) DEFAULT NULL COMMENT '客服电话',
  `customerservice_name` varchar(255) DEFAULT NULL COMMENT '客服名称',
  `add_time` varchar(255) DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`customerservice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customerservice
-- ----------------------------
INSERT INTO `customerservice` VALUES ('1', '17775071178', '周润发', '2019-05-05');

-- ----------------------------
-- Table structure for difen
-- ----------------------------
DROP TABLE IF EXISTS `difen`;
CREATE TABLE `difen` (
  `id` int(11) NOT NULL,
  `zuidi` int(255) DEFAULT NULL,
  `zuida` int(255) DEFAULT NULL,
  `addtime` varchar(255) DEFAULT NULL,
  `uptime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of difen
-- ----------------------------
INSERT INTO `difen` VALUES ('1', '1', '1000', '2019-06-01', null);

-- ----------------------------
-- Table structure for getredenvelopes
-- ----------------------------
DROP TABLE IF EXISTS `getredenvelopes`;
CREATE TABLE `getredenvelopes` (
  `get_redenvelopes_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '领取红包id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `redenvelopes_id` int(11) DEFAULT NULL COMMENT '红包id',
  `add_time` varchar(255) DEFAULT NULL COMMENT '领取红包时间',
  `get_redenvelopes_money` double(11,2) DEFAULT NULL COMMENT '领取红包金额',
  `shuying` int(11) DEFAULT NULL COMMENT '输赢',
  PRIMARY KEY (`get_redenvelopes_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1422 DEFAULT CHARSET=utf8 COMMENT='领取红包记录表';

-- ----------------------------
-- Records of getredenvelopes
-- ----------------------------
INSERT INTO `getredenvelopes` VALUES ('1343', '146', '499', '2019-06-14 14:24:36', '2.05', null);
INSERT INTO `getredenvelopes` VALUES ('1344', '148', '499', '2019-06-14 14:24:36', '1.24', '1');
INSERT INTO `getredenvelopes` VALUES ('1345', '149', '499', '2019-06-14 14:24:36', '0.88', '1');
INSERT INTO `getredenvelopes` VALUES ('1346', '150', '499', '2019-06-14 14:24:36', '1.36', null);
INSERT INTO `getredenvelopes` VALUES ('1347', '151', '499', '2019-06-14 14:24:36', '1.05', '2');
INSERT INTO `getredenvelopes` VALUES ('1348', '152', '499', '2019-06-14 14:24:36', '1.23', '2');
INSERT INTO `getredenvelopes` VALUES ('1349', '147', '499', '2019-06-14 14:24:36', '2.19', '2');
INSERT INTO `getredenvelopes` VALUES ('1350', '146', '500', '2019-06-14 14:25:53', '0.16', null);
INSERT INTO `getredenvelopes` VALUES ('1351', '148', '500', '2019-06-14 14:25:53', '2.28', '2');
INSERT INTO `getredenvelopes` VALUES ('1352', '149', '500', '2019-06-14 14:25:53', '0.68', '2');
INSERT INTO `getredenvelopes` VALUES ('1353', '150', '500', '2019-06-14 14:25:53', '0.10', '2');
INSERT INTO `getredenvelopes` VALUES ('1354', '151', '500', '2019-06-14 14:25:53', '3.22', '2');
INSERT INTO `getredenvelopes` VALUES ('1355', '152', '500', '2019-06-14 14:25:53', '3.56', '2');
INSERT INTO `getredenvelopes` VALUES ('1356', '146', '501', '2019-06-14 14:26:59', '0.06', null);
INSERT INTO `getredenvelopes` VALUES ('1357', '148', '501', '2019-06-14 14:26:59', '2.33', '1');
INSERT INTO `getredenvelopes` VALUES ('1358', '149', '501', '2019-06-14 14:26:59', '1.69', '2');
INSERT INTO `getredenvelopes` VALUES ('1359', '150', '501', '2019-06-14 14:26:59', '0.58', '2');
INSERT INTO `getredenvelopes` VALUES ('1360', '151', '501', '2019-06-14 14:26:59', '0.30', '2');
INSERT INTO `getredenvelopes` VALUES ('1361', '152', '501', '2019-06-14 14:26:59', '2.33', '1');
INSERT INTO `getredenvelopes` VALUES ('1362', '147', '501', '2019-06-14 14:26:59', '2.71', '1');
INSERT INTO `getredenvelopes` VALUES ('1363', '146', '502', '2019-06-14 15:16:50', '4.15', null);
INSERT INTO `getredenvelopes` VALUES ('1364', '147', '502', '2019-06-14 15:16:52', '5.85', '2');
INSERT INTO `getredenvelopes` VALUES ('1365', '147', '503', '2019-06-14 15:17:06', '6.17', '1');
INSERT INTO `getredenvelopes` VALUES ('1366', '146', '503', '2019-06-14 15:17:07', '3.83', null);
INSERT INTO `getredenvelopes` VALUES ('1367', '147', '504', '2019-06-14 15:17:25', '4.18', '1');
INSERT INTO `getredenvelopes` VALUES ('1368', '146', '504', '2019-06-14 15:17:27', '5.82', null);
INSERT INTO `getredenvelopes` VALUES ('1369', '147', '505', '2019-06-14 15:17:38', '1.37', '2');
INSERT INTO `getredenvelopes` VALUES ('1370', '146', '505', '2019-06-14 15:17:39', '8.63', null);
INSERT INTO `getredenvelopes` VALUES ('1371', '147', '506', '2019-06-14 15:17:54', '5.25', '1');
INSERT INTO `getredenvelopes` VALUES ('1372', '146', '506', '2019-06-14 15:18:22', '4.75', null);
INSERT INTO `getredenvelopes` VALUES ('1373', '146', '507', '2019-06-14 15:18:29', '6.12', null);
INSERT INTO `getredenvelopes` VALUES ('1374', '147', '507', '2019-06-14 15:18:58', '3.88', '1');
INSERT INTO `getredenvelopes` VALUES ('1375', '146', '508', '2019-06-14 15:25:52', '9.50', null);
INSERT INTO `getredenvelopes` VALUES ('1376', '147', '508', '2019-06-14 15:26:21', '0.50', '2');
INSERT INTO `getredenvelopes` VALUES ('1377', '146', '509', '2019-06-14 15:29:25', '0.20', null);
INSERT INTO `getredenvelopes` VALUES ('1378', '147', '509', '2019-06-14 15:29:42', '9.80', '1');
INSERT INTO `getredenvelopes` VALUES ('1379', '147', '510', '2019-06-14 15:33:53', '5.17', '1');
INSERT INTO `getredenvelopes` VALUES ('1380', '146', '510', '2019-06-14 15:33:55', '4.83', null);
INSERT INTO `getredenvelopes` VALUES ('1381', '147', '511', '2019-06-14 15:34:02', '5.26', '1');
INSERT INTO `getredenvelopes` VALUES ('1382', '146', '511', '2019-06-14 15:34:04', '4.74', null);
INSERT INTO `getredenvelopes` VALUES ('1383', '147', '512', '2019-06-14 15:40:38', '4.29', '2');
INSERT INTO `getredenvelopes` VALUES ('1384', '146', '512', '2019-06-14 15:40:39', '5.71', null);
INSERT INTO `getredenvelopes` VALUES ('1385', '147', '513', '2019-06-14 15:40:46', '4.45', '2');
INSERT INTO `getredenvelopes` VALUES ('1386', '146', '513', '2019-06-14 15:40:47', '5.55', null);
INSERT INTO `getredenvelopes` VALUES ('1387', '146', '514', '2019-06-14 15:41:33', '6.12', null);
INSERT INTO `getredenvelopes` VALUES ('1388', '147', '514', '2019-06-14 15:41:41', '3.88', '1');
INSERT INTO `getredenvelopes` VALUES ('1389', '147', '515', '2019-06-14 15:41:52', '8.33', '1');
INSERT INTO `getredenvelopes` VALUES ('1390', '146', '515', '2019-06-14 15:41:54', '1.67', null);
INSERT INTO `getredenvelopes` VALUES ('1391', '146', '516', '2019-06-14 15:42:03', '3.29', null);
INSERT INTO `getredenvelopes` VALUES ('1392', '147', '516', '2019-06-14 15:42:03', '6.71', '1');
INSERT INTO `getredenvelopes` VALUES ('1393', '146', '517', '2019-06-14 15:57:31', '3.49', null);
INSERT INTO `getredenvelopes` VALUES ('1394', '147', '517', '2019-06-14 15:57:33', '6.51', '1');
INSERT INTO `getredenvelopes` VALUES ('1395', '147', '518', '2019-06-14 15:57:40', '3.05', '1');
INSERT INTO `getredenvelopes` VALUES ('1396', '146', '518', '2019-06-14 15:57:41', '6.95', null);
INSERT INTO `getredenvelopes` VALUES ('1397', '147', '519', '2019-06-14 15:59:38', '1.45', '1');
INSERT INTO `getredenvelopes` VALUES ('1398', '146', '519', '2019-06-14 15:59:38', '1.25', null);
INSERT INTO `getredenvelopes` VALUES ('1399', '148', '519', '2019-06-14 15:59:38', '1.16', '2');
INSERT INTO `getredenvelopes` VALUES ('1400', '149', '519', '2019-06-14 15:59:38', '1.77', '2');
INSERT INTO `getredenvelopes` VALUES ('1401', '150', '519', '2019-06-14 15:59:38', '0.68', '2');
INSERT INTO `getredenvelopes` VALUES ('1402', '151', '519', '2019-06-14 15:59:38', '1.94', '2');
INSERT INTO `getredenvelopes` VALUES ('1403', '152', '519', '2019-06-14 15:59:38', '1.75', '2');
INSERT INTO `getredenvelopes` VALUES ('1404', '147', '520', '2019-06-14 16:00:25', '1.70', '1');
INSERT INTO `getredenvelopes` VALUES ('1405', '146', '520', '2019-06-14 16:00:25', '2.87', null);
INSERT INTO `getredenvelopes` VALUES ('1406', '148', '520', '2019-06-14 16:00:25', '2.05', '2');
INSERT INTO `getredenvelopes` VALUES ('1407', '149', '520', '2019-06-14 16:00:25', '0.04', '2');
INSERT INTO `getredenvelopes` VALUES ('1408', '150', '520', '2019-06-14 16:00:25', '0.13', '2');
INSERT INTO `getredenvelopes` VALUES ('1409', '151', '520', '2019-06-14 16:00:25', '0.48', '2');
INSERT INTO `getredenvelopes` VALUES ('1410', '152', '520', '2019-06-14 16:00:25', '2.73', '2');
INSERT INTO `getredenvelopes` VALUES ('1411', '147', '521', '2019-06-14 16:01:11', '1.45', '2');
INSERT INTO `getredenvelopes` VALUES ('1412', '146', '521', '2019-06-14 16:01:11', '1.27', null);
INSERT INTO `getredenvelopes` VALUES ('1413', '148', '521', '2019-06-14 16:01:11', '1.89', '2');
INSERT INTO `getredenvelopes` VALUES ('1414', '149', '521', '2019-06-14 16:01:11', '1.40', '2');
INSERT INTO `getredenvelopes` VALUES ('1415', '150', '521', '2019-06-14 16:01:11', '0.86', '2');
INSERT INTO `getredenvelopes` VALUES ('1416', '151', '521', '2019-06-14 16:01:11', '1.39', '2');
INSERT INTO `getredenvelopes` VALUES ('1417', '152', '521', '2019-06-14 16:01:11', '1.74', '2');
INSERT INTO `getredenvelopes` VALUES ('1418', '147', '522', '2019-06-14 16:06:22', '2.89', null);
INSERT INTO `getredenvelopes` VALUES ('1419', '146', '522', '2019-06-14 16:06:22', '7.11', '2');
INSERT INTO `getredenvelopes` VALUES ('1420', '146', '523', '2019-06-14 16:06:58', '6.03', null);
INSERT INTO `getredenvelopes` VALUES ('1421', '147', '523', '2019-06-14 16:07:01', '3.97', '1');

-- ----------------------------
-- Table structure for htlogin
-- ----------------------------
DROP TABLE IF EXISTS `htlogin`;
CREATE TABLE `htlogin` (
  `ht_id` int(11) NOT NULL AUTO_INCREMENT,
  `ht_phone` varchar(255) DEFAULT NULL,
  `ht_password` varchar(255) DEFAULT NULL,
  `add_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ht_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of htlogin
-- ----------------------------
INSERT INTO `htlogin` VALUES ('1', '17775071172', '6B1AA0BAB50A623B5CDE34224EB67573', '2019-05-16 09:56:15');

-- ----------------------------
-- Table structure for redenvelopes
-- ----------------------------
DROP TABLE IF EXISTS `redenvelopes`;
CREATE TABLE `redenvelopes` (
  `red_envelopes_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '红包id',
  `room_id` int(11) DEFAULT NULL COMMENT '房间id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `value_of_pack` double(11,2) DEFAULT NULL COMMENT '红包金额',
  `red_envelopes_num` int(11) DEFAULT NULL,
  `add_time` varchar(255) DEFAULT NULL,
  `red_envelopes_state` int(11) DEFAULT NULL COMMENT '红包状态',
  PRIMARY KEY (`red_envelopes_id`)
) ENGINE=InnoDB AUTO_INCREMENT=524 DEFAULT CHARSET=utf8 COMMENT='红包';

-- ----------------------------
-- Records of redenvelopes
-- ----------------------------
INSERT INTO `redenvelopes` VALUES ('522', '497', '147', '10.00', '2', '2019-06-14 16:05:52', '2');
INSERT INTO `redenvelopes` VALUES ('523', '497', '146', '10.00', '2', '2019-06-14 16:06:53', '1');

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `room_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `room_password` varchar(255) DEFAULT NULL COMMENT '房间密码',
  `room_mulriple` int(11) DEFAULT NULL COMMENT '放假倍数',
  `add_time` varchar(255) DEFAULT NULL COMMENT '创建房间时间',
  `room_num` int(11) DEFAULT NULL COMMENT '房间人数',
  `user_id` int(11) DEFAULT NULL,
  `whether_to_end` int(11) DEFAULT NULL COMMENT '是否已经结束房间',
  `whether_password` int(11) DEFAULT NULL COMMENT '是否有密码',
  `room_integral` double(11,2) DEFAULT NULL COMMENT '房间积分',
  `residue` int(11) DEFAULT NULL COMMENT '房间倍数',
  `youxizhong` int(11) DEFAULT '0' COMMENT '1游戏中2未开始游戏',
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=498 DEFAULT CHARSET=utf8 COMMENT='房间';

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES ('497', '007', null, '10', '2019-06-14 16:04:13', '0', '146', '1', '1', '30.00', '10', '1');

-- ----------------------------
-- Table structure for userroom
-- ----------------------------
DROP TABLE IF EXISTS `userroom`;
CREATE TABLE `userroom` (
  `user_room_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  `add_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userroom
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户姓名',
  `user_password` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `user_invitation_code` varchar(255) DEFAULT '0' COMMENT '用户邀请码',
  `user_grade` int(11) DEFAULT '6' COMMENT '用户会员等级',
  `user_recharge_money` double(11,2) DEFAULT '0.00' COMMENT '用户充值金额',
  `user_recharge_integral` double(11,2) DEFAULT '0.00' COMMENT '用户账户积分',
  `add_time` varchar(255) DEFAULT NULL,
  `up_time` varchar(255) DEFAULT NULL,
  `user_phone` varchar(255) DEFAULT '0' COMMENT '0',
  `user_nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `user_robot` int(11) DEFAULT NULL COMMENT '是否机器人',
  `user_img` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `the_higher_the_agent` varchar(255) DEFAULT '111111111111' COMMENT '上级代理代理邀请码',
  `user_num` int(11) DEFAULT NULL,
  `changci` int(11) DEFAULT '0' COMMENT '胜利场次',
  `user_wx` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '微信',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=272 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('2', '周润发', '6B1AA0BAB50A623B5CDE34224EB67573', '685293', '6', '51200.00', '100890.13', '2019-04-30 15:42:11', null, '17775071173', '周润发', '0', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '685294', '50', '0', null);
INSERT INTO `users` VALUES ('3', '刘翔', '9337B6D01BAAB58F4B8828326D90AC07', '685294', '5', '50122.00', '99991.12', '2019-04-30 15:42:11', null, '17775071174', '上级', '0', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '685293', '39', '0', null);
INSERT INTO `users` VALUES ('4', '迪丽热巴', '6B1AA0BAB50A623B5CDE34224EB67573', '685295', '2', '50000.00', '99882.64', null, null, '17775071175', '小迪', '0', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '685293', '50', '0', null);
INSERT INTO `users` VALUES ('6', '迪丽热巴', null, '', '1', '34900.00', '124900.00', '2019-05-13 09:19:00', null, '177750711726', '古力娜扎', '0', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '685293', null, '0', null);
INSERT INTO `users` VALUES ('7', '迪丽热巴', null, '', '5', '2222.00', '102222.00', '2019-05-13 09:19:00', null, '177750711726', '古力娜扎', '0', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '685293', null, '0', null);
INSERT INTO `users` VALUES ('8', '迪丽热巴', null, '', '2', '20000.00', '120000.00', '2019-05-13 09:19:00', null, '177750711726', '古力娜扎', '0', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '685293', null, '0', null);
INSERT INTO `users` VALUES ('9', '迪丽热巴', null, '', '5', '0.00', '100000.00', '2019-05-13 09:19:00', null, '177750711726', '古力娜扎', '0', 'http://192.168.31.176:8080/files/images/weiduoli.jpg', '685293', null, '0', null);
INSERT INTO `users` VALUES ('11', '迪丽热巴', null, '', '5', '0.00', '100000.00', '2019-05-13 09:19:00', null, '177750711726', '古力娜扎', '0', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '685294', null, '0', null);
INSERT INTO `users` VALUES ('12', '迪丽热巴', null, null, '5', '0.00', '100000.00', '2019-05-13 09:19:00', null, '177750711726', '古力娜扎', '0', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '685294', null, '0', null);
INSERT INTO `users` VALUES ('13', '迪丽热巴', null, null, '6', '100.00', '100100.00', '2019-05-13 09:19:00', null, '177750711726', '古力娜扎', '0', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '685294', null, '0', null);
INSERT INTO `users` VALUES ('14', '迪丽热巴', null, null, '5', '0.00', '100000.00', '2019-05-13 09:19:00', null, '177750711726', '古力娜扎', '0', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '685294', null, '0', null);
INSERT INTO `users` VALUES ('21', null, null, null, '5', '0.00', '100000.00', '2019-05-13 09:19:00', null, '177750711726', '古力娜扎', '0', 'http://192.168.31.186:8080/files/images/weiduoli.jpg', '6OF45E259', null, '0', null);
INSERT INTO `users` VALUES ('105', null, '6B1AA0BAB50A623B5CDE34224EB67573', 'W9OSQI305', '5', '10000000.00', '100000.00', null, null, '177750711726', '请修改昵称', '0', null, '0', null, '0', null);
INSERT INTO `users` VALUES ('108', null, 'A448410BDCBB4D7CFB32830909F6AA08', '2ROMDW465', '6', null, '100000.00', null, null, '123456789', '请修改昵称', '0', null, null, null, '0', null);
INSERT INTO `users` VALUES ('114', null, '6B1AA0BAB50A623B5CDE34224EB67573', 'KOSBLP985', '6', '0.00', '100000.00', '2019-05-30 19:38:09', null, '177750711722', '下级', '0', 'http://192.168.31.176:8080/files/images/7f5a30a9b8f8434c832d2c03fba0b6e4.jpeg', '685294', null, '0', null);
INSERT INTO `users` VALUES ('144', null, '6B1AA0BAB50A623B5CDE34224EB67573', 'AZOKIG666', '5', '6.00', '977.43', '2019-06-13 11:16:33', null, '18051365075', '曹，上级', '0', 'http://192.168.28.176:8088/files/images/54a69e479cdc4d88966e82e76b13b2bc.jpeg', '11111111', null, '0', '0');
INSERT INTO `users` VALUES ('145', null, '6B1AA0BAB50A623B5CDE34224EB67573', 'EPOZS4621', '6', '6.00', '641.97', '2019-06-13 11:21:50', null, '18353870309', '马，下级', '0', 'http://192.168.28.176:8088/files/images/7e0a31dd419941629f2399b351a425b0.jpeg', 'AZOKIG666', null, '0', '0');
INSERT INTO `users` VALUES ('146', null, '6B1AA0BAB50A623B5CDE34224EB67573', 'E3OGWH849', '5', '0.00', '1038.87', '2019-06-13 15:38:16', null, '15238500547', '冯上', '0', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('147', null, '6B1AA0BAB50A623B5CDE34224EB67573', '3QO5JT469', '6', '0.00', '1206.71', '2019-06-13 15:40:20', null, '17775071172', '华下', '0', 'http://192.168.28.176:8088/files/images/336743009d5e44b1bd358e84e607affd.jpeg', 'E3OGWH849', null, '0', '0');
INSERT INTO `users` VALUES ('148', null, null, '0', '6', '0.00', '966.94', '2019-06-13 19:48:49', null, '0', '明民', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('149', null, null, '0', '6', '0.00', '914.47', '2019-06-13 19:48:49', null, '0', '许榕', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('150', null, null, '0', '6', '0.00', '907.78', '2019-06-13 19:48:49', null, '0', '孔元全', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('151', null, null, '0', '6', '0.00', '903.68', '2019-06-13 19:48:49', null, '0', '平诚先', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('152', null, null, '0', '6', '0.00', '949.59', '2019-06-13 19:48:49', null, '0', '何宁', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('153', null, null, '0', '6', '0.00', '981.19', '2019-06-13 19:48:49', null, '0', '卞庆', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('154', null, null, '0', '6', '0.00', '992.30', '2019-06-13 19:48:49', null, '0', '范兴', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('155', null, null, '0', '6', '0.00', '1012.80', '2019-06-13 19:48:49', null, '0', '湛悦昭', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('156', null, null, '0', '6', '0.00', '942.32', '2019-06-13 19:48:49', null, '0', '苏邦', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('157', null, null, '0', '6', '0.00', '1000.88', '2019-06-13 19:48:49', null, '0', '窦勇毅', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('159', null, '6B1AA0BAB50A623B5CDE34224EB67573', 'A4OS48189', '6', '0.00', '0.00', null, null, '15655773919', '请修改昵称', '0', null, '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('160', null, null, '0', '6', '0.00', '1002.47', '2019-06-14 13:37:32', null, '0', '杨咏卿', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('161', null, null, '0', '6', '0.00', '1000.53', '2019-06-14 13:37:32', null, '0', '谈诚先', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('162', null, null, '0', '6', '0.00', '1003.47', '2019-06-14 13:37:32', null, '0', '何希', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('163', null, null, '0', '6', '0.00', '1000.05', '2019-06-14 13:37:32', null, '0', '罗绍功', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('164', null, null, '0', '6', '0.00', '1002.81', '2019-06-14 13:37:32', null, '0', '谢功', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('165', null, null, '0', '6', '0.00', '1003.89', '2019-06-14 13:37:32', null, '0', '俞生龙', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('166', null, null, '0', '6', '0.00', '983.95', '2019-06-14 13:37:32', null, '0', '舒家', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('167', null, null, '0', '6', '0.00', '994.21', '2019-06-14 13:37:32', null, '0', '茅会思', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('168', null, null, '0', '6', '0.00', '1004.65', '2019-06-14 13:37:32', null, '0', '时子', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('169', null, null, '0', '6', '0.00', '1015.73', '2019-06-14 13:37:32', null, '0', '卜颖', '2', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('170', null, '6B1AA0BAB50A623B5CDE34224EB67573', '23OUYW594', '6', '10000.00', '10133.25', '2019-06-14 16:29:35', null, '15162252679', '上级史努比', '0', 'http://39.98.219.33:8015/images/images/467496e6c041421e968009ed58e5f234.jpeg', '111111111111', null, '0', '0');
INSERT INTO `users` VALUES ('271', null, '6B1AA0BAB50A623B5CDE34224EB67573', 'J3O7C9465', '6', '0.00', '2166.74', '2019-06-14 16:56:23', null, '13123858456', '下级。红', '0', 'http://39.98.219.33:8015/images/images/84c6d41ef1ff4d9eb370244ebc8010a4.jpeg', '23OUYW594', null, '80', '0');
