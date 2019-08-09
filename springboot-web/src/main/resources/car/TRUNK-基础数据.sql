/*
Navicat MySQL Data Transfer

Source Server         : 192.168.19.121
Source Server Version : 50615
Source Host           : 192.168.19.121:3306
Source Database       : TRUNK

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2019-07-16 09:21:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ADMIN_REGION
-- ----------------------------
DROP TABLE IF EXISTS `ADMIN_REGION`;
CREATE TABLE `ADMIN_REGION` (
  `CODE` char(24) NOT NULL DEFAULT '' COMMENT '行政区域代码',
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `NAME` varchar(256) DEFAULT NULL COMMENT '行政区域名称',
  `SHORTNAME` varchar(256) DEFAULT NULL COMMENT '缩写',
  `FULLNAME` varchar(512) DEFAULT NULL COMMENT '全称',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '上级行政区域的ID',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_10` (`PARENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行政区域';

-- ----------------------------
-- Records of ADMIN_REGION
-- ----------------------------

-- ----------------------------
-- Table structure for ALARM_VOICE
-- ----------------------------
DROP TABLE IF EXISTS `ALARM_VOICE`;
CREATE TABLE `ALARM_VOICE` (
  `USER_ID` bigint(20) NOT NULL,
  `BLACKLIST_TYPE` int(11) NOT NULL,
  `VOICE_FILE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`,`BLACKLIST_TYPE`),
  KEY `FK_Reference_39` (`VOICE_FILE_ID`),
  KEY `FK_Reference_40` (`BLACKLIST_TYPE`),
  CONSTRAINT `FK_Reference_39` FOREIGN KEY (`VOICE_FILE_ID`) REFERENCES `VOICE_FILE` (`ID`),
  CONSTRAINT `FK_Reference_40` FOREIGN KEY (`BLACKLIST_TYPE`) REFERENCES `BLACK_LIST_TYPE_DIC` (`TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报警声音设置';

-- ----------------------------
-- Records of ALARM_VOICE
-- ----------------------------
INSERT INTO `ALARM_VOICE` VALUES ('1', '1', '1');
INSERT INTO `ALARM_VOICE` VALUES ('2', '1', '1');

-- ----------------------------
-- Table structure for AREA
-- ----------------------------
DROP TABLE IF EXISTS `AREA`;
CREATE TABLE `AREA` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `NAME` varchar(256) DEFAULT NULL COMMENT '名称',
  `ADMIN_REGION_CODE` char(32) DEFAULT NULL COMMENT '行政区域编码',
  `CITY_ID` bigint(20) DEFAULT NULL COMMENT '城市ID',
  `DELETED` tinyint(4) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_6` (`CITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域';

-- ----------------------------
-- Records of AREA
-- ----------------------------

-- ----------------------------
-- Table structure for AREA_ROAD_CROSS_POINT
-- ----------------------------
DROP TABLE IF EXISTS `AREA_ROAD_CROSS_POINT`;
CREATE TABLE `AREA_ROAD_CROSS_POINT` (
  `AREA_ID` bigint(20) NOT NULL COMMENT '区域ID',
  `ROAD_CROSS_POINT_ID` bigint(20) NOT NULL COMMENT '路口点位ID',
  PRIMARY KEY (`AREA_ID`,`ROAD_CROSS_POINT_ID`),
  KEY `FK_Reference_28` (`ROAD_CROSS_POINT_ID`),
  CONSTRAINT `FK_Reference_28` FOREIGN KEY (`ROAD_CROSS_POINT_ID`) REFERENCES `ROAD_CROSS_POINT` (`ID`),
  CONSTRAINT `FK_Reference_29` FOREIGN KEY (`AREA_ID`) REFERENCES `AREA` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域路口点位';

-- ----------------------------
-- Records of AREA_ROAD_CROSS_POINT
-- ----------------------------

-- ----------------------------
-- Table structure for AUTHORITY
-- ----------------------------
DROP TABLE IF EXISTS `AUTHORITY`;
CREATE TABLE `AUTHORITY` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `NAME` varchar(48) DEFAULT NULL COMMENT '名称',
  `ENABLE` tinyint(4) DEFAULT NULL COMMENT '是否启用(0:未启用; 1:启用)',
  `LEVEL_CODE` varchar(512) DEFAULT NULL COMMENT '权限级别代码',
  `POSITION_IN_GROUP` int(11) DEFAULT NULL COMMENT '组内显示顺序',
  `URL` varchar(256) DEFAULT NULL COMMENT '权限对应的页面地址',
  `MATCH_URL` varchar(256) DEFAULT NULL COMMENT '匹配的URL',
  `IS_DIRECT_URL` tinyint(4) DEFAULT NULL COMMENT '是否为直接URL',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '上级权限外键',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of AUTHORITY
-- ----------------------------
INSERT INTO `AUTHORITY` VALUES ('1', 'videoSurveillance', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('2', 'endorsement', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('3', 'overspeedControl', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('4', 'intervalSpeedControl', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('5', 'tailNumberlimitControl', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('6', 'limitedTimeForbiddingControl', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('7', 'oneWayLaneControl', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('8', 'alarm', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('9', 'vehicleBlacklist', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('10', 'whiteList', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('11', 'record', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('12', 'passed', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('13', 'track', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('14', 'trafficViolation', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('15', 'illegalTime', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('16', 'illegalPlace', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('17', 'illegalTrend', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('18', 'illegalPunishment', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('19', 'flow', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('20', 'fake', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('21', 'together', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('22', 'road', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('23', 'management', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('24', 'status', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('25', 'user', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('26', 'department', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('27', 'log', null, '', null, '', '', null, null);
INSERT INTO `AUTHORITY` VALUES ('28', 'param', null, '', null, '', '', null, null);

-- ----------------------------
-- Table structure for BLACK_LIST_BELL_DIC
-- ----------------------------
DROP TABLE IF EXISTS `BLACK_LIST_BELL_DIC`;
CREATE TABLE `BLACK_LIST_BELL_DIC` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `LEVEL` int(4) NOT NULL DEFAULT '0' COMMENT '报警级别',
  `BELL` int(4) NOT NULL DEFAULT '1' COMMENT '报警铃声',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of BLACK_LIST_BELL_DIC
-- ----------------------------
INSERT INTO `BLACK_LIST_BELL_DIC` VALUES ('1', '2', '4');
INSERT INTO `BLACK_LIST_BELL_DIC` VALUES ('2', '3', '2');
INSERT INTO `BLACK_LIST_BELL_DIC` VALUES ('3', '4', '3');
INSERT INTO `BLACK_LIST_BELL_DIC` VALUES ('4', '1', '3');

-- ----------------------------
-- Table structure for BLACK_LIST_TYPE_DIC
-- ----------------------------
DROP TABLE IF EXISTS `BLACK_LIST_TYPE_DIC`;
CREATE TABLE `BLACK_LIST_TYPE_DIC` (
  `TYPE` int(11) NOT NULL COMMENT '类型编码(1-套牌分析,2-同行车分析,3-被盗抢车,9-其他)',
  `NAME` varchar(64) DEFAULT NULL COMMENT '类型名称',
  `LEVEL` int(4) NOT NULL DEFAULT '1' COMMENT '报警级别（1：严重，2高，3中，4低）',
  `BELL` int(4) NOT NULL DEFAULT '1' COMMENT '报警铃声(1.明亮，2，高昂，3.平和，4.低层)',
  PRIMARY KEY (`TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='黑名单类型字典';

-- ----------------------------
-- Records of BLACK_LIST_TYPE_DIC
-- ----------------------------
INSERT INTO `BLACK_LIST_TYPE_DIC` VALUES ('1', 'Fake Plate', '1', '3');
INSERT INTO `BLACK_LIST_TYPE_DIC` VALUES ('2', 'Fellow Vehicle', '1', '3');
INSERT INTO `BLACK_LIST_TYPE_DIC` VALUES ('3', 'Stolen cars', '1', '3');
INSERT INTO `BLACK_LIST_TYPE_DIC` VALUES ('4', 'Overdue inspection', '1', '3');
INSERT INTO `BLACK_LIST_TYPE_DIC` VALUES ('5', 'Anecdotes', '1', '3');
INSERT INTO `BLACK_LIST_TYPE_DIC` VALUES ('6', 'Unpurchased insurance car', '1', '3');
INSERT INTO `BLACK_LIST_TYPE_DIC` VALUES ('7', 'Illegal unprocessed car', '1', '3');
INSERT INTO `BLACK_LIST_TYPE_DIC` VALUES ('9', 'other', '1', '3');

-- ----------------------------
-- Table structure for BLACKLIST
-- ----------------------------
DROP TABLE IF EXISTS `BLACKLIST`;
CREATE TABLE `BLACKLIST` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `PLATE_NUMBER` char(12) DEFAULT NULL COMMENT '车牌号',
  `VEHICLE_BRAND` int(11) DEFAULT NULL COMMENT '车辆品牌',
  `PLATE_COLOR` int(11) DEFAULT NULL COMMENT '车牌颜色白色(0)、黄色(1)、蓝色(2)、黑色(3)、绿色(4)、未识别(5)、其他(6)',
  `VEHICLE_COLOR` char(11) DEFAULT NULL COMMENT '车辆颜色白色(A)、灰色(B)、黄色(C)、粉色(D)、红色(E)、紫色(F)、绿色(G)、蓝色(H)、棕色(I)、黑色(J)、未识别(K)、其他(Z)',
  `LOSE_TIME` datetime DEFAULT NULL COMMENT '被盗时间',
  `LATITUDE` double DEFAULT NULL COMMENT '失踪地点经度',
  `LONGITUDE` double DEFAULT NULL COMMENT '失踪地点纬度',
  `LOSE_ADDRESS` varchar(256) DEFAULT NULL COMMENT '失踪地点',
  `FEATURE` varchar(256) DEFAULT NULL COMMENT '车辆特征',
  `TYPE` int(11) DEFAULT NULL COMMENT '黑名单类型',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DESCRIPTION` varchar(1024) DEFAULT NULL COMMENT '概况',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  `STATUS` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否启用0关闭，1开启',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_24` (`TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆黑名单';

-- ----------------------------
-- Records of BLACKLIST
-- ----------------------------

-- ----------------------------
-- Table structure for CITY
-- ----------------------------
DROP TABLE IF EXISTS `CITY`;
CREATE TABLE `CITY` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `NAME` varchar(256) DEFAULT NULL COMMENT '城市名称',
  `ADMIN_REGION_CODE` char(32) DEFAULT NULL COMMENT '行政区域编码',
  `MONITOR_CENTER_ID` bigint(20) DEFAULT NULL,
  `DELETED` tinyint(4) DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市';

-- ----------------------------
-- Records of CITY
-- ----------------------------

-- ----------------------------
-- Table structure for CONFIGURATION
-- ----------------------------
DROP TABLE IF EXISTS `CONFIGURATION`;
CREATE TABLE `CONFIGURATION` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `NUMBER` tinyint(4) DEFAULT NULL COMMENT '九宫格编号1-9',
  `USER_ID` bigint(20) DEFAULT NULL COMMENT '用户主键',
  `DEVICE_ID` bigint(20) DEFAULT NULL COMMENT '设备主键',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备表';

-- ----------------------------
-- Records of CONFIGURATION
-- ----------------------------

-- ----------------------------
-- Table structure for CONTROL_ALARM
-- ----------------------------
DROP TABLE IF EXISTS `CONTROL_ALARM`;
CREATE TABLE `CONTROL_ALARM` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `DEVICE_ID` bigint(20) DEFAULT NULL COMMENT '抓拍设备ID',
  `ROADWAY_NO` int(11) DEFAULT NULL COMMENT '车道编号',
  `ROADWAY_NAME` varchar(64) DEFAULT NULL COMMENT '车道名称',
  `CAPTURE_TIME` datetime DEFAULT NULL COMMENT '抓拍时间',
  `BLACKLIST_TYPE` int(11) DEFAULT NULL COMMENT '黑名单类型',
  `PLATE_NUMBER` char(12) DEFAULT NULL COMMENT '车牌号',
  `VEHICLE_COLOR` int(11) DEFAULT NULL COMMENT '车辆颜色',
  `NOTE` varchar(1024) DEFAULT NULL COMMENT '警情描述',
  `STATUS` int(11) NOT NULL DEFAULT '0' COMMENT '处置状态(0: 未确认; 1: 已确认报警; 2: 错误报警)',
  `PUSH_STATUS` int(11) NOT NULL DEFAULT '0' COMMENT '是否推送到辰安的接处警系统 (0: 未推送; 1: 推送成功; 2: 推送失败)',
  `BLACKLIST_ID` bigint(20) DEFAULT NULL COMMENT '黑名单记录ID',
  `PASSEDBY_VEHICLE_ID` bigint(20) DEFAULT NULL COMMENT '过车记录ID',
  `INCIDENT_ID` varchar(36) DEFAULT NULL COMMENT '警情ID',
  `ALARM_DESCRIPTION` varchar(2000) DEFAULT NULL COMMENT '报警描述',
  `ALARM_PERSON` varchar(256) DEFAULT NULL,
  `FTP_URL` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_22` (`PASSEDBY_VEHICLE_ID`),
  KEY `FK_Reference_23` (`BLACKLIST_ID`),
  KEY `FK_Reference_31` (`DEVICE_ID`),
  CONSTRAINT `FK_Reference_23` FOREIGN KEY (`BLACKLIST_ID`) REFERENCES `BLACKLIST` (`ID`),
  CONSTRAINT `FK_Reference_31` FOREIGN KEY (`DEVICE_ID`) REFERENCES `DEVICE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='布控报警';

-- ----------------------------
-- Records of CONTROL_ALARM
-- ----------------------------

-- ----------------------------
-- Table structure for DATA_TYPE
-- ----------------------------
DROP TABLE IF EXISTS `DATA_TYPE`;
CREATE TABLE `DATA_TYPE` (
  `TYPE` int(11) NOT NULL COMMENT '类型',
  `NAME` varchar(64) DEFAULT NULL COMMENT '数据名称',
  PRIMARY KEY (`TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据类型';

-- ----------------------------
-- Records of DATA_TYPE
-- ----------------------------

-- ----------------------------
-- Table structure for DAY_AREA_STATISTICS
-- ----------------------------
DROP TABLE IF EXISTS `DAY_AREA_STATISTICS`;
CREATE TABLE `DAY_AREA_STATISTICS` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `DATE` date DEFAULT NULL COMMENT '日期',
  `NUM_VEHICLE_PASSED` int(11) DEFAULT NULL COMMENT '过车数量',
  `NUM_VIOLATION` int(11) DEFAULT NULL COMMENT '违章数量',
  `NUM_DISPOSAL` int(11) DEFAULT NULL COMMENT '处罚数量',
  `AREA_ID` bigint(20) DEFAULT NULL COMMENT '区域id',
  `NUM_UNCHECKED` int(11) DEFAULT NULL COMMENT '未处理数量',
  `NUM_RUN_RED_LIGHT` int(11) DEFAULT NULL COMMENT '闯红灯数量预测',
  `NUM_OVER_SPEED` int(11) DEFAULT NULL COMMENT '超速数量预测(前端测速/区间测速)',
  `NUM_WRONG_DIRECTION` int(11) DEFAULT NULL COMMENT '逆行数量预测',
  `NUM_WRONG_POSITION` int(11) DEFAULT NULL COMMENT '压线类违章数量预测(压虚拟车道线/压停车线进入违停区域/压黄线/压分道线/不按规定车道行驶)',
  `NUM_VIOLATE_NUM_LIMIT` int(11) DEFAULT NULL COMMENT '违反尾号限行数量预测',
  `NUM_VIOLATE_TIME_LIMIT` int(11) DEFAULT NULL COMMENT '违反时间限行数量预测',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日区域统计';

-- ----------------------------
-- Records of DAY_AREA_STATISTICS
-- ----------------------------

-- ----------------------------
-- Table structure for DAY_CROSS_POINT_STATISTICS
-- ----------------------------
DROP TABLE IF EXISTS `DAY_CROSS_POINT_STATISTICS`;
CREATE TABLE `DAY_CROSS_POINT_STATISTICS` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `DATE` date DEFAULT NULL COMMENT '日期',
  `NUM_VEHICLE_PASSED` int(11) DEFAULT NULL COMMENT '过车数量',
  `NUM_VIOLATION` int(11) DEFAULT NULL COMMENT '违章数量',
  `NUM_DISPOSAL` int(11) DEFAULT NULL COMMENT '处罚数量',
  `ROAD_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '路口点位ID',
  `NUM_RUN_RED_LIGHT` int(11) DEFAULT NULL COMMENT '闯红灯数量预测',
  `NUM_OVER_SPEED` int(11) DEFAULT NULL COMMENT '超速数量预测(前端测速/区间测速)',
  `NUM_WRONG_DIRECTION` int(11) DEFAULT NULL COMMENT '逆行数量预测',
  `NUM_WRONG_POSITION` int(11) DEFAULT NULL COMMENT '压线类违章数量预测(压虚拟车道线/压停车线进入违停区域/压黄线/压分道线/不按规定车道行驶)',
  `NUM_VIOLATE_NUM_LIMIT` int(11) DEFAULT NULL COMMENT '违反尾号限行数量预测',
  `NUM_VIOLATE_TIME_LIMIT` int(11) DEFAULT NULL COMMENT '违反时间限行数量预测',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每天路口点位统计';

-- ----------------------------
-- Records of DAY_CROSS_POINT_STATISTICS
-- ----------------------------

-- ----------------------------
-- Table structure for DEVICE
-- ----------------------------
DROP TABLE IF EXISTS `DEVICE`;
CREATE TABLE `DEVICE` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `NAME` varchar(64) DEFAULT NULL COMMENT '名字',
  `TYPE` int(11) DEFAULT NULL COMMENT '类型(0:电警摄像机；1卡口摄像机； 2: 主机)',
  `LATITUDE` double DEFAULT NULL COMMENT '经度',
  `LONGITUDE` double DEFAULT NULL COMMENT '纬度',
  `ONLINE` tinyint(4) DEFAULT NULL COMMENT '是否在线（(0:不在线; 1:在线)',
  `MANUFACTURER` varchar(128) DEFAULT NULL COMMENT '所属厂商',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) DEFAULT NULL COMMENT '是否已删除',
  `DOMAIN_CODE` int(11) DEFAULT NULL COMMENT '域编码',
  `INSTALL_ADDRESS` varchar(512) DEFAULT NULL COMMENT '设备安装详细地址',
  `TERMINAL_ID` bigint(20) DEFAULT NULL COMMENT '终端主机ID',
  `ROAD_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '路口点位ID',
  `ROADWAY_NUM` int(11) DEFAULT NULL COMMENT '监控的车道数量',
  `ROADWAY1_NO` int(11) DEFAULT NULL COMMENT '车道1的编号',
  `ROADWAY1_NAME` varchar(64) DEFAULT NULL COMMENT '车道1的名称',
  `ROADWAY2_NO` int(11) DEFAULT NULL COMMENT '车道2的编号',
  `ROADWAY2_NAME` varchar(64) DEFAULT NULL COMMENT '车道2的名称',
  `ROADWAY3_NO` int(11) DEFAULT NULL COMMENT '车道3的编号',
  `ROADWAY3_NAME` varchar(64) DEFAULT NULL COMMENT '车道3的名称',
  `DEVICE_ID` varchar(64) DEFAULT NULL COMMENT '同步的设备id',
  `UPDATE_TIME` datetime DEFAULT NULL,
  `BAYONET_NO` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_REF_DEVICE_TO_TERMINAL` (`TERMINAL_ID`) USING BTREE,
  KEY `FK_Reference_2` (`ROAD_CROSS_POINT_ID`) USING BTREE,
  CONSTRAINT `DEVICE_ibfk_1` FOREIGN KEY (`ROAD_CROSS_POINT_ID`) REFERENCES `ROAD_CROSS_POINT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备表';

-- ----------------------------
-- Records of DEVICE
-- ----------------------------

-- ----------------------------
-- Table structure for DEVICE_STATUS_LOG
-- ----------------------------
DROP TABLE IF EXISTS `DEVICE_STATUS_LOG`;
CREATE TABLE `DEVICE_STATUS_LOG` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `TYPE` int(11) DEFAULT NULL COMMENT '类型(0:电警摄像机；1卡口摄像机； 2: 主机)',
  `DEVICE_ID` bigint(20) DEFAULT NULL COMMENT '设备ID',
  `TERMINAL_ID` bigint(20) DEFAULT NULL COMMENT '主机ID',
  `ONLINE` tinyint(4) DEFAULT NULL COMMENT '是否在线(0:不在线; 1:在线)',
  `LASTUPDATE_TIME` datetime DEFAULT NULL COMMENT '最后修改时间',
  `TOTAL_TIME` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_33` (`TERMINAL_ID`) USING BTREE,
  KEY `FK_Reference_34` (`DEVICE_ID`) USING BTREE,
  CONSTRAINT `DEVICE_STATUS_LOG_ibfk_1` FOREIGN KEY (`TERMINAL_ID`) REFERENCES `TERMINAL` (`ID`),
  CONSTRAINT `DEVICE_STATUS_LOG_ibfk_2` FOREIGN KEY (`DEVICE_ID`) REFERENCES `DEVICE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备状态日志';

-- ----------------------------
-- Records of DEVICE_STATUS_LOG
-- ----------------------------

-- ----------------------------
-- Table structure for DICT_CODE_DESC
-- ----------------------------
DROP TABLE IF EXISTS `DICT_CODE_DESC`;
CREATE TABLE `DICT_CODE_DESC` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `DICT_NAME` varchar(64) DEFAULT NULL COMMENT '编码名称',
  `DICT_CODE` varchar(32) DEFAULT NULL COMMENT '编码代码',
  `DICT_DESC` varchar(256) DEFAULT NULL COMMENT '描述描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Records of DICT_CODE_DESC
-- ----------------------------
INSERT INTO `DICT_CODE_DESC` VALUES ('1', 'DeviceType', '0', '电子警察摄像机');
INSERT INTO `DICT_CODE_DESC` VALUES ('2', 'DeviceType', '1', '卡口摄像机');
INSERT INTO `DICT_CODE_DESC` VALUES ('3', 'DeviceType', '2', '智能主机');
INSERT INTO `DICT_CODE_DESC` VALUES ('4', 'DeviceOnline', '0', '离线');
INSERT INTO `DICT_CODE_DESC` VALUES ('5', 'DeviceOnline', '1', '在线');
INSERT INTO `DICT_CODE_DESC` VALUES ('6', 'UserGender', '0', '男');
INSERT INTO `DICT_CODE_DESC` VALUES ('7', 'UserGender', '1', '女');
INSERT INTO `DICT_CODE_DESC` VALUES ('8', 'UserGender', '2', '未知');
INSERT INTO `DICT_CODE_DESC` VALUES ('9', 'ViolationCode', '0', '未按规定车道行驶');
INSERT INTO `DICT_CODE_DESC` VALUES ('10', 'ViolationCode', '1', '未按交通信号灯规定通行');
INSERT INTO `DICT_CODE_DESC` VALUES ('11', 'ViolationCode', '2', '不按导向线行驶');
INSERT INTO `DICT_CODE_DESC` VALUES ('12', 'ViolationCode', '3', '违章停车');
INSERT INTO `DICT_CODE_DESC` VALUES ('13', 'ViolationCode', '4', '违反禁止标线指示');
INSERT INTO `DICT_CODE_DESC` VALUES ('14', 'ViolationCode', '5', '违反禁令标志指示');
INSERT INTO `DICT_CODE_DESC` VALUES ('15', 'ViolationCode', '6', '超速');
INSERT INTO `DICT_CODE_DESC` VALUES ('16', 'ViolationCode', '7', '逆行');
INSERT INTO `DICT_CODE_DESC` VALUES ('17', 'ViolationCode', '8', '限号违章');
INSERT INTO `DICT_CODE_DESC` VALUES ('18', 'ViolationCode', '9', '限号违章');
INSERT INTO `DICT_CODE_DESC` VALUES ('19', 'ViolationCode', '10', '违反限制通行规定');
INSERT INTO `DICT_CODE_DESC` VALUES ('20', 'ViolationStatus', '0', '未确认');
INSERT INTO `DICT_CODE_DESC` VALUES ('21', 'ViolationStatus', '1', '已生成处罚记录');
INSERT INTO `DICT_CODE_DESC` VALUES ('22', 'ViolationStatus', '2', '不予处罚');
INSERT INTO `DICT_CODE_DESC` VALUES ('23', 'ViolationStatus', '3', '撤销处罚');
INSERT INTO `DICT_CODE_DESC` VALUES ('24', 'AlarmStatus', '0', '未确认');
INSERT INTO `DICT_CODE_DESC` VALUES ('25', 'AlarmStatus', '1', '已确认报警');
INSERT INTO `DICT_CODE_DESC` VALUES ('26', 'AlarmStatus', '2', '错误报警');
INSERT INTO `DICT_CODE_DESC` VALUES ('27', 'VehicleColor', 'A', '白色');
INSERT INTO `DICT_CODE_DESC` VALUES ('28', 'VehicleColor', 'B', '灰色');
INSERT INTO `DICT_CODE_DESC` VALUES ('29', 'VehicleColor', 'C', '黄色');
INSERT INTO `DICT_CODE_DESC` VALUES ('30', 'VehicleColor', 'D', '粉色');
INSERT INTO `DICT_CODE_DESC` VALUES ('31', 'VehicleColor', 'E', '红色');
INSERT INTO `DICT_CODE_DESC` VALUES ('32', 'VehicleColor', 'F', '紫色');
INSERT INTO `DICT_CODE_DESC` VALUES ('33', 'VehicleColor', 'G', '绿色');
INSERT INTO `DICT_CODE_DESC` VALUES ('34', 'VehicleColor', 'H', '蓝色');
INSERT INTO `DICT_CODE_DESC` VALUES ('35', 'VehicleColor', 'I', '棕色');
INSERT INTO `DICT_CODE_DESC` VALUES ('36', 'VehicleColor', 'J', '黑色');
INSERT INTO `DICT_CODE_DESC` VALUES ('37', 'VehicleColor', 'K', '未识别');
INSERT INTO `DICT_CODE_DESC` VALUES ('38', 'VehicleColor', 'Z', '其他');
INSERT INTO `DICT_CODE_DESC` VALUES ('39', 'PlateColor', '0', '白色');
INSERT INTO `DICT_CODE_DESC` VALUES ('40', 'PlateColor', '1', '黄色');
INSERT INTO `DICT_CODE_DESC` VALUES ('41', 'PlateColor', '2', '蓝色');
INSERT INTO `DICT_CODE_DESC` VALUES ('42', 'PlateColor', '3', '黑色');
INSERT INTO `DICT_CODE_DESC` VALUES ('43', 'PlateColor', '4', '绿色');
INSERT INTO `DICT_CODE_DESC` VALUES ('44', 'PlateColor', '5', '未识别');
INSERT INTO `DICT_CODE_DESC` VALUES ('45', 'PlateColor', '6', '其他');
INSERT INTO `DICT_CODE_DESC` VALUES ('46', 'DirectionCode', '0', '东');
INSERT INTO `DICT_CODE_DESC` VALUES ('47', 'DirectionCode', '1', '东南');
INSERT INTO `DICT_CODE_DESC` VALUES ('48', 'DirectionCode', '2', '南');
INSERT INTO `DICT_CODE_DESC` VALUES ('49', 'DirectionCode', '3', '西南');
INSERT INTO `DICT_CODE_DESC` VALUES ('50', 'DirectionCode', '4', '西');
INSERT INTO `DICT_CODE_DESC` VALUES ('51', 'DirectionCode', '5', '西北');
INSERT INTO `DICT_CODE_DESC` VALUES ('52', 'DirectionCode', '6', '北');
INSERT INTO `DICT_CODE_DESC` VALUES ('53', 'DirectionCode', '7', '东北');
INSERT INTO `DICT_CODE_DESC` VALUES ('54', 'ViolationPushStatus', '0', '未推送');
INSERT INTO `DICT_CODE_DESC` VALUES ('55', 'ViolationPushStatus', '1', '推送成功');
INSERT INTO `DICT_CODE_DESC` VALUES ('56', 'ViolationPushStatus', '2', '推送失败');
INSERT INTO `DICT_CODE_DESC` VALUES ('57', 'AlarmPushStatus', '0', '未推送');
INSERT INTO `DICT_CODE_DESC` VALUES ('58', 'AlarmPushStatus', '1', '推送成功');
INSERT INTO `DICT_CODE_DESC` VALUES ('59', 'AlarmPushStatus', '2', '推送失败');

-- ----------------------------
-- Table structure for DIRECTION_NO_DIC
-- ----------------------------
DROP TABLE IF EXISTS `DIRECTION_NO_DIC`;
CREATE TABLE `DIRECTION_NO_DIC` (
  `NO` int(11) NOT NULL COMMENT '方向编码',
  `NAME` int(11) DEFAULT NULL COMMENT '方向名称',
  PRIMARY KEY (`NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='方向字典';

-- ----------------------------
-- Records of DIRECTION_NO_DIC
-- ----------------------------

-- ----------------------------
-- Table structure for FAKE_PLATE_DETAIL
-- ----------------------------
DROP TABLE IF EXISTS `FAKE_PLATE_DETAIL`;
CREATE TABLE `FAKE_PLATE_DETAIL` (
  `ID` bigint(20) NOT NULL,
  `FAKE_RECORD_ID` bigint(20) DEFAULT NULL COMMENT '套牌记录主键',
  `VEHICLE_TYPE` varchar(20) DEFAULT NULL,
  `VEHICLE_COLOR` varchar(20) DEFAULT NULL,
  `CAPTURE_TIME` datetime DEFAULT NULL,
  `DEVICE_ID` bigint(20) DEFAULT NULL,
  `DEVICE_NAME` varchar(256) DEFAULT NULL,
  `FTP_URL` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of FAKE_PLATE_DETAIL
-- ----------------------------

-- ----------------------------
-- Table structure for FAKE_PLATE_RECORD
-- ----------------------------
DROP TABLE IF EXISTS `FAKE_PLATE_RECORD`;
CREATE TABLE `FAKE_PLATE_RECORD` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `PLATE_NUMBER` char(12) DEFAULT NULL COMMENT '车牌号',
  `DETECTION_TIME` datetime DEFAULT NULL COMMENT '检测时间',
  `RULE_BROKEN` int(11) DEFAULT NULL COMMENT '违反规则(0: 车辆有两种颜色; 1:车辆有两种车型; 2:车辆轨迹异常；3:车辆有两种颜色并且是两种车型；4:车辆有两种颜色并且车辆轨迹异常；5：车辆有两种类型并且车辆轨迹异常；6：三者都有。)',
  `STATUS` int(11) DEFAULT NULL COMMENT '处置状态(0:未确认; 1:已布控; 2:已排除)',
  `INNORMAL_PASSEDBY_ID_LIST` varchar(8000) DEFAULT NULL COMMENT '异常过车ID列表,每种冲突记录保存最近的10条',
  `AREA_ID` bigint(20) DEFAULT NULL,
  `CITY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='套牌车记录';

-- ----------------------------
-- Records of FAKE_PLATE_RECORD
-- ----------------------------

-- ----------------------------
-- Table structure for HOUR_AREA_STATISTICS
-- ----------------------------
DROP TABLE IF EXISTS `HOUR_AREA_STATISTICS`;
CREATE TABLE `HOUR_AREA_STATISTICS` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `DATE` date DEFAULT NULL COMMENT '日期',
  `HOUR` int(11) DEFAULT NULL COMMENT '第几小时',
  `NUM_VEHICLE_PASSED` int(11) DEFAULT NULL COMMENT '车流量',
  `NUM_VIOLATION` int(11) DEFAULT NULL COMMENT '违章数量',
  `NUM_DISPOSAL` int(11) DEFAULT NULL COMMENT '处罚处量',
  `AREA_ID` bigint(20) DEFAULT NULL COMMENT '区域id',
  `NUM_UNCHECKED` int(11) DEFAULT NULL COMMENT '未处理数量',
  `NUM_RUN_RED_LIGHT` int(11) DEFAULT NULL COMMENT '闯红灯数量预测',
  `NUM_OVER_SPEED` int(11) DEFAULT NULL COMMENT '超速数量预测(前端测速/区间测速)',
  `NUM_WRONG_DIRECTION` int(11) DEFAULT NULL COMMENT '逆行数量预测',
  `NUM_WRONG_POSITION` int(11) DEFAULT NULL COMMENT '压线类违章数量预测(压虚拟车道线/压停车线进入违停区域/压黄线/压分道线/不按规定车道行驶)',
  `NUM_VIOLATE_NUM_LIMIT` int(11) DEFAULT NULL COMMENT '违反尾号限行数量预测',
  `NUM_VIOLATE_TIME_LIMIT` int(11) DEFAULT NULL COMMENT '违反时间限行数量预测',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域小时统计';

-- ----------------------------
-- Records of HOUR_AREA_STATISTICS
-- ----------------------------

-- ----------------------------
-- Table structure for HOUR_CROSS_POINT_STATISTICS
-- ----------------------------
DROP TABLE IF EXISTS `HOUR_CROSS_POINT_STATISTICS`;
CREATE TABLE `HOUR_CROSS_POINT_STATISTICS` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `DATE` date DEFAULT NULL COMMENT '日期',
  `HOUR` int(11) DEFAULT NULL COMMENT '第几小时',
  `NUM_VEHICLE_PASSED` int(11) DEFAULT NULL COMMENT '过车数量',
  `NUM_VIOLATION` int(11) DEFAULT NULL COMMENT '违章数量',
  `NUM_DISPOSAL` int(11) DEFAULT NULL COMMENT '处罚数量',
  `ROAD_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '路口点位ID',
  `NUM_RUN_RED_LIGHT` int(11) DEFAULT NULL COMMENT '闯红灯数量预测',
  `NUM_OVER_SPEED` int(11) DEFAULT NULL COMMENT '超速数量预测(前端测速/区间测速)',
  `NUM_WRONG_DIRECTION` int(11) DEFAULT NULL COMMENT '逆行数量预测',
  `NUM_WRONG_POSITION` int(11) DEFAULT NULL COMMENT '压线类违章数量预测(压虚拟车道线/压停车线进入违停区域/压黄线/压分道线/不按规定车道行驶)',
  `NUM_VIOLATE_NUM_LIMIT` int(11) DEFAULT NULL COMMENT '违反尾号限行数量预测',
  `NUM_VIOLATE_TIME_LIMIT` int(11) DEFAULT NULL COMMENT '违反时间限行数量预测',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小时路口点位统计';

-- ----------------------------
-- Records of HOUR_CROSS_POINT_STATISTICS
-- ----------------------------

-- ----------------------------
-- Table structure for HOUR_DEVICE_STATISTICS
-- ----------------------------
DROP TABLE IF EXISTS `HOUR_DEVICE_STATISTICS`;
CREATE TABLE `HOUR_DEVICE_STATISTICS` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `DATE` date DEFAULT NULL COMMENT '日期',
  `HOUR` int(11) DEFAULT NULL COMMENT '第几小时',
  `NUM_VEHICLE_PASSED` int(11) DEFAULT NULL COMMENT '过车数量',
  `ROAD_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '路口点位ID',
  `DEVICE_ID` bigint(20) DEFAULT NULL COMMENT '设备ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小时点位统计';

-- ----------------------------
-- Records of HOUR_DEVICE_STATISTICS
-- ----------------------------

-- ----------------------------
-- Table structure for INTER_SPEED_CONTROL
-- ----------------------------
DROP TABLE IF EXISTS `INTER_SPEED_CONTROL`;
CREATE TABLE `INTER_SPEED_CONTROL` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `NAME` varchar(64) DEFAULT NULL COMMENT '区间布控名称',
  `CITY_ID` bigint(20) DEFAULT NULL COMMENT '城市ID',
  `AREA_ID` bigint(20) DEFAULT NULL COMMENT '区域ID',
  `START_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '起始路段路口ID',
  `END_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '结束路段路口ID',
  `MIN_SPEED` int(11) DEFAULT NULL COMMENT '最小速度',
  `MAX_SPEED` int(11) DEFAULT NULL COMMENT '最大速度',
  `DISTANCE` double DEFAULT NULL COMMENT '距离',
  `TRUCK_MIN_SPEED` int(11) DEFAULT NULL COMMENT '大车最小速度',
  `TRUCK_MAX_SPEED` int(11) DEFAULT NULL COMMENT '大车最大速度',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态(0:未启用; 1:已启用)',
  `DELETED` tinyint(4) DEFAULT NULL COMMENT '是否删除',
  `TRAJECTORY` varchar(512) DEFAULT NULL COMMENT '轨迹',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_14` (`AREA_ID`),
  CONSTRAINT `FK_Reference_14` FOREIGN KEY (`AREA_ID`) REFERENCES `AREA` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区间测速布控';

-- ----------------------------
-- Records of INTER_SPEED_CONTROL
-- ----------------------------

-- ----------------------------
-- Table structure for MONITOR_CENTER
-- ----------------------------
DROP TABLE IF EXISTS `MONITOR_CENTER`;
CREATE TABLE `MONITOR_CENTER` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `NAME` varchar(256) DEFAULT NULL COMMENT '行政机构名称',
  `ADDRESS` varchar(512) DEFAULT NULL COMMENT '地址',
  `PHONE` char(16) DEFAULT NULL COMMENT '联系电话',
  `ADMIN_REGION_CODE` char(20) DEFAULT NULL COMMENT '所管辖的行政区域的编码',
  `FILE_SERVER_IP` varchar(32) DEFAULT NULL COMMENT '文件服务器的IP',
  `FILE_SERVER_CONTEXT` varchar(256) DEFAULT NULL COMMENT '文件服务器的Context，含IP:port/AppName',
  `DELETED` tinyint(4) DEFAULT NULL COMMENT '是否已删除',
  `UNIFIED_ALARM_URL` varchar(128) DEFAULT NULL,
  `VIDEO_SERVER_IP` varchar(128) DEFAULT NULL,
  `VIDEO_SDK` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='监控中心';

-- ----------------------------
-- Records of MONITOR_CENTER
-- ----------------------------
INSERT INTO `MONITOR_CENTER` VALUES ('222', 'Luanda', 'Luanda', '78151156', '200000', '127.0.0.1', '65534', '0', 'http://192.168.19.97:8080/CAD_WebService/', 'http://192.168.19.236:8088/vision', '192.168.19.236,9999,192.168.19.236,6543,ttt,ttt');

-- ----------------------------
-- Table structure for MONITOR_CENTER_DEVICE
-- ----------------------------
DROP TABLE IF EXISTS `MONITOR_CENTER_DEVICE`;
CREATE TABLE `MONITOR_CENTER_DEVICE` (
  `MONITOR_CENTER_ID` bigint(20) NOT NULL COMMENT '监控中心ID',
  `DEVICE_ID` bigint(20) NOT NULL COMMENT '设备ID',
  `ID` bigint(20) DEFAULT NULL COMMENT '主键',
  PRIMARY KEY (`MONITOR_CENTER_ID`,`DEVICE_ID`),
  KEY `FK_Reference_7` (`ID`),
  KEY `FK_Reference_8` (`DEVICE_ID`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`MONITOR_CENTER_ID`) REFERENCES `MONITOR_CENTER` (`ID`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`DEVICE_ID`) REFERENCES `DEVICE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='监控中心可访问设备';

-- ----------------------------
-- Records of MONITOR_CENTER_DEVICE
-- ----------------------------

-- ----------------------------
-- Table structure for MONTH_AREA_STATISTICS
-- ----------------------------
DROP TABLE IF EXISTS `MONTH_AREA_STATISTICS`;
CREATE TABLE `MONTH_AREA_STATISTICS` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `YEAR` int(11) DEFAULT NULL COMMENT '年份',
  `MONTH` int(11) DEFAULT NULL COMMENT '月份',
  `NUM_VEHICLE_PASSED` int(11) DEFAULT NULL COMMENT '过车数量',
  `NUM_VIOLATION` int(11) DEFAULT NULL COMMENT '违章数量',
  `AREA_ID` bigint(20) DEFAULT NULL COMMENT '区域id',
  `NUM_UNCHECKED` int(11) DEFAULT NULL COMMENT '未处理数量',
  `NUM_DISPOSAL` int(11) DEFAULT NULL COMMENT '处罚数量',
  `NUM_RUN_RED_LIGHT` int(11) DEFAULT NULL COMMENT '闯红灯数量预测',
  `NUM_OVER_SPEED` int(11) DEFAULT NULL COMMENT '超速数量预测(前端测速/区间测速)',
  `NUM_WRONG_DIRECTION` int(11) DEFAULT NULL COMMENT '逆行数量预测',
  `NUM_WRONG_POSITION` int(11) DEFAULT NULL COMMENT '压线类违章数量预测(压虚拟车道线/压停车线进入违停区域/压黄线/压分道线/不按规定车道行驶)',
  `NUM_VIOLATE_NUM_LIMIT` int(11) DEFAULT NULL COMMENT '违反尾号限行数量预测',
  `NUM_VIOLATE_TIME_LIMIT` int(11) DEFAULT NULL COMMENT '违反时间限行数量预测',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='月区域统计';

-- ----------------------------
-- Records of MONTH_AREA_STATISTICS
-- ----------------------------

-- ----------------------------
-- Table structure for MONTH_CROSS_POINT_STATISTICS
-- ----------------------------
DROP TABLE IF EXISTS `MONTH_CROSS_POINT_STATISTICS`;
CREATE TABLE `MONTH_CROSS_POINT_STATISTICS` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `YEAR` int(11) DEFAULT NULL COMMENT '年份',
  `MONTH` int(11) DEFAULT NULL COMMENT '月份',
  `NUM_VEHICLE_PASSED` int(11) DEFAULT NULL COMMENT '过车数量',
  `NUM_VIOLATION` int(11) DEFAULT NULL COMMENT '违章数量',
  `NUM_DISPOSAL` int(11) DEFAULT NULL COMMENT '处罚数量',
  `ROAD_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '路口点位ID',
  `NUM_RUN_RED_LIGHT` int(11) DEFAULT NULL COMMENT '闯红灯数量预测',
  `NUM_OVER_SPEED` int(11) DEFAULT NULL COMMENT '超速数量预测(前端测速/区间测速)',
  `NUM_WRONG_DIRECTION` int(11) DEFAULT NULL COMMENT '逆行数量预测',
  `NUM_WRONG_POSITION` int(11) DEFAULT NULL COMMENT '压线类违章数量预测(压虚拟车道线/压停车线进入违停区域/压黄线/压分道线/不按规定车道行驶)',
  `NUM_VIOLATE_NUM_LIMIT` int(11) DEFAULT NULL COMMENT '违反尾号限行数量预测',
  `NUM_VIOLATE_TIME_LIMIT` int(11) DEFAULT NULL COMMENT '违反时间限行数量预测',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每月路口点位统计';

-- ----------------------------
-- Records of MONTH_CROSS_POINT_STATISTICS
-- ----------------------------

-- ----------------------------
-- Table structure for NEXTDAY_VIOLATION_PREDICTION
-- ----------------------------
DROP TABLE IF EXISTS `NEXTDAY_VIOLATION_PREDICTION`;
CREATE TABLE `NEXTDAY_VIOLATION_PREDICTION` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `DATE` date DEFAULT NULL COMMENT '日期',
  `HOUR` int(11) DEFAULT NULL COMMENT '小时',
  `NUM_VEHICLE_PASSED` int(11) DEFAULT NULL COMMENT '过车数量预测',
  `NUM_VIOLATION` int(11) DEFAULT NULL COMMENT '违章数量预测',
  `ROAD_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '路口点位ID',
  `NUM_RUN_RED_LIGHT` int(11) DEFAULT NULL COMMENT '闯红灯数量预测',
  `NUM_OVER_SPEED` int(11) DEFAULT NULL COMMENT '超速数量预测(前端测速/区间测速)',
  `NUM_WRONG_DIRECTION` int(11) DEFAULT NULL COMMENT '逆行数量预测',
  `NUM_WRONG_POSITION` int(11) DEFAULT NULL COMMENT '压线类违章数量预测(压虚拟车道线/压停车线进入违停区域/压黄线/压分道线/不按规定车道行驶)',
  `NUM_VIOLATE_NUM_LIMIT` int(11) DEFAULT NULL COMMENT '违反尾号限行数量预测',
  `NUM_VIOLATE_TIME_LIMIT` int(11) DEFAULT NULL COMMENT '违反时间限行数量预测',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='明天违法行为预测';

-- ----------------------------
-- Records of NEXTDAY_VIOLATION_PREDICTION
-- ----------------------------

-- ----------------------------
-- Table structure for NEXTWEEK_VIOLATION_PREDICTION
-- ----------------------------
DROP TABLE IF EXISTS `NEXTWEEK_VIOLATION_PREDICTION`;
CREATE TABLE `NEXTWEEK_VIOLATION_PREDICTION` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `DATE` date DEFAULT NULL COMMENT '日期',
  `NUM_VEHICLE_PASSED` int(11) DEFAULT NULL COMMENT '过车数量预测',
  `NUM_VIOLATION` int(11) DEFAULT NULL COMMENT '违章数量预测',
  `ROAD_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '路口点位ID',
  `NUM_RUN_RED_LIGHT` int(11) DEFAULT NULL COMMENT '闯红灯数量预测',
  `NUM_OVER_SPEED` int(11) DEFAULT NULL COMMENT '超速数量预测(前端测速/区间测速)',
  `NUM_WRONG_DIRECTION` int(11) DEFAULT NULL COMMENT '逆行数量预测',
  `NUM_WRONG_POSITION` int(11) DEFAULT NULL COMMENT '压线类违章数量预测(压虚拟车道线/压停车线进入违停区域/压黄线/压分道线/不按规定车道行驶)',
  `NUM_VIOLATE_NUM_LIMIT` int(11) DEFAULT NULL COMMENT '违反尾号限行数量预测',
  `NUM_VIOLATE_TIME_LIMIT` int(11) DEFAULT NULL COMMENT '违反时间限行数量预测',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='下周违法行为预测';

-- ----------------------------
-- Records of NEXTWEEK_VIOLATION_PREDICTION
-- ----------------------------

-- ----------------------------
-- Table structure for NUMBER_LIMIT_CONTROL
-- ----------------------------
DROP TABLE IF EXISTS `NUMBER_LIMIT_CONTROL`;
CREATE TABLE `NUMBER_LIMIT_CONTROL` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `CITY_ID` bigint(20) DEFAULT NULL COMMENT '城市ID',
  `AREA_ID` bigint(20) DEFAULT NULL COMMENT '区域ID',
  `ROAD_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '路口点位ID',
  `RULE_TYPE` int(11) DEFAULT NULL COMMENT '限号规则类型（0:单双号限行; 1:自定义规定限行）',
  `START_DATE` date DEFAULT NULL COMMENT '开始日期',
  `END_DATE` date DEFAULT NULL COMMENT '结束日期',
  `START_TIME` time DEFAULT NULL COMMENT '每日开始时间',
  `END_TIME` time DEFAULT NULL COMMENT '每日结束时间',
  `WHOLE_YEAR_EFFECTIVE` tinyint(4) DEFAULT NULL COMMENT '全年有效',
  `WHOLE_DAY_EFFECTIVE` tinyint(4) DEFAULT NULL COMMENT '全天有效',
  `MONDAY_LIMIT` char(30) DEFAULT NULL COMMENT '星期一限制出行尾号，逗号分隔（-1代表所有奇数，-2代表所有偶数）',
  `TUESDAY_LIMIT` char(30) DEFAULT NULL COMMENT '星期二限制出行尾号，逗号分隔（-1代表所有奇数，-2代表所有偶数）',
  `WEDNESDAY_LIMIT` char(30) DEFAULT NULL COMMENT '星期三限制出行尾号，逗号分隔（-1代表所有奇数，-2代表所有偶数）',
  `THURSDAY_LIMIT` char(30) DEFAULT NULL COMMENT '星期四限制出行尾号，逗号分隔（-1代表所有奇数，-2代表所有偶数）',
  `FRIDAY_LIMIT` char(30) DEFAULT NULL COMMENT '星期五限制出行尾号，逗号分隔（-1代表所有奇数，-2代表所有偶数）',
  `SATURDAY_LIMIT` char(30) DEFAULT NULL COMMENT '星期六限制出行尾号，逗号分隔（-1代表所有奇数，-2代表所有偶数）',
  `SUNDAY_LIMIT` char(30) DEFAULT NULL COMMENT '星期日限制出行尾号，逗号分隔（-1代表所有奇数，-2代表所有偶数）',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态(0: 未启用; 1:已启用)',
  `DELETED` tinyint(4) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_15` (`AREA_ID`),
  CONSTRAINT `FK_Reference_15` FOREIGN KEY (`AREA_ID`) REFERENCES `AREA` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='尾号限行布控';

-- ----------------------------
-- Records of NUMBER_LIMIT_CONTROL
-- ----------------------------

-- ----------------------------
-- Table structure for NUMBER_LIMIT_CONTROL_ROAD
-- ----------------------------
DROP TABLE IF EXISTS `NUMBER_LIMIT_CONTROL_ROAD`;
CREATE TABLE `NUMBER_LIMIT_CONTROL_ROAD` (
  `ID` bigint(20) NOT NULL COMMENT 'ID',
  `NUMBER_LIMIT_CONTROL_ID` bigint(20) NOT NULL COMMENT '尾号限行布控ID',
  `ROAD_CROSS_POINT_ID` bigint(20) NOT NULL COMMENT '路口点位ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='尾号限行布控关联的路口点位，尾号限行布控1-N路口点位';

-- ----------------------------
-- Records of NUMBER_LIMIT_CONTROL_ROAD
-- ----------------------------

-- ----------------------------
-- Table structure for OVERSPEED_CONTROL
-- ----------------------------
DROP TABLE IF EXISTS `OVERSPEED_CONTROL`;
CREATE TABLE `OVERSPEED_CONTROL` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `CITY_ID` bigint(20) DEFAULT NULL COMMENT '城市ID',
  `AREA_ID` bigint(20) DEFAULT NULL COMMENT '区域ID',
  `ROAD_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '路口点位ID',
  `DEVICE_ID` bigint(20) DEFAULT NULL COMMENT '设备ID',
  `ROADWAY` int(11) DEFAULT NULL COMMENT '车道编号，从1开始',
  `START_DATE` datetime DEFAULT NULL COMMENT '开始日期',
  `END_DATE` datetime DEFAULT NULL COMMENT '结束日期',
  `WHOLE_YEAR_EFFECTIVE` tinyint(4) DEFAULT NULL COMMENT '是否全年布控',
  `MIN_SPEED` int(11) DEFAULT NULL COMMENT '最小速度',
  `MAX_SPEED` int(11) DEFAULT NULL COMMENT '最大速度',
  `TRUCK_MIN_SPEED` int(11) DEFAULT NULL COMMENT '大车最小速度',
  `TRUCK_MAX_SPEED` int(11) DEFAULT NULL COMMENT '大车最大速度',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态（0: 未启用; 1: 已启用)',
  `DELETED` tinyint(4) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_13` (`AREA_ID`),
  CONSTRAINT `FK_Reference_13` FOREIGN KEY (`AREA_ID`) REFERENCES `AREA` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='超速布控';

-- ----------------------------
-- Records of OVERSPEED_CONTROL
-- ----------------------------

-- ----------------------------
-- Table structure for PASSEDBY_VEHICLE
-- ----------------------------
DROP TABLE IF EXISTS `PASSEDBY_VEHICLE`;
CREATE TABLE `PASSEDBY_VEHICLE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DRIVE_STATUS` int(11) DEFAULT NULL COMMENT '行驶状态-正常、违章等',
  `CAPTURE_TIME` datetime NOT NULL COMMENT '抓拍时间',
  `PLATE_NUMBER` char(12) DEFAULT NULL COMMENT '车牌号',
  `VEHICLE_COLOR` char(1) DEFAULT NULL COMMENT '车辆颜色',
  `PLATE_COLOR` int(11) DEFAULT NULL COMMENT '车牌颜色',
  `VEHICLE_BRAND` int(11) DEFAULT NULL COMMENT '车辆品牌',
  `VEHICLE_TYPE` char(20) DEFAULT NULL COMMENT '车辆类型',
  `SPEED` int(11) DEFAULT NULL COMMENT '车速',
  `ROADWAY_NO` int(11) DEFAULT NULL COMMENT '车道编号',
  `ROADWAY_NAME` char(20) DEFAULT NULL COMMENT '车道名称',
  `ROAD_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '路口点位ID',
  `DEVICE_ID` bigint(20) DEFAULT NULL COMMENT '抓拍设备的ID',
  `DIRECTION_CODE` int(11) DEFAULT NULL COMMENT '方向编码',
  `PLATE_PHOTO_ID` bigint(20) DEFAULT NULL COMMENT '车牌图片ID',
  `PHOTO_FASTDFS_URL` varchar(128) DEFAULT NULL COMMENT '车辆图片在FastDFS的URL',
  `CITY_NAME` varchar(256) DEFAULT NULL,
  `AREA_NAME` varchar(256) DEFAULT NULL,
  `ROAD_NAME` varchar(256) DEFAULT NULL,
  `CITY_ID` bigint(20) DEFAULT NULL,
  `AREA_ID` bigint(20) DEFAULT NULL,
  `DEVICE_NAME` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`ID`,`CAPTURE_TIME`),
  KEY `idx_PASSEDBY_VEHICLE_CAPTURE_TIME` (`CAPTURE_TIME`),
  KEY `idx_pv_DEVICE_ID_PLATE_NUMBER` (`DEVICE_ID`,`PLATE_NUMBER`),
  KEY `INDEX_PLATE_NUMBER` (`PLATE_NUMBER`)
) ENGINE=InnoDB AUTO_INCREMENT=749953457668097 DEFAULT CHARSET=utf8
/*!50500 PARTITION BY RANGE  COLUMNS(CAPTURE_TIME)
(PARTITION p20180100 VALUES LESS THAN ('2018-01-01') ENGINE = InnoDB,
 PARTITION p20180101 VALUES LESS THAN ('2018-01-02') ENGINE = InnoDB,
 PARTITION p20180102 VALUES LESS THAN ('2018-01-03') ENGINE = InnoDB,
 PARTITION p20180103 VALUES LESS THAN ('2018-01-04') ENGINE = InnoDB,
 PARTITION p20180104 VALUES LESS THAN ('2018-01-05') ENGINE = InnoDB,
 PARTITION p20180105 VALUES LESS THAN ('2018-01-06') ENGINE = InnoDB,
 PARTITION p20180106 VALUES LESS THAN ('2018-01-07') ENGINE = InnoDB,
 PARTITION p20180107 VALUES LESS THAN ('2018-01-08') ENGINE = InnoDB,
 PARTITION p20180108 VALUES LESS THAN ('2018-01-09') ENGINE = InnoDB,
 PARTITION p20180109 VALUES LESS THAN ('2018-01-10') ENGINE = InnoDB,
 PARTITION p20180110 VALUES LESS THAN ('2018-01-11') ENGINE = InnoDB,
 PARTITION p20180111 VALUES LESS THAN ('2018-01-12') ENGINE = InnoDB,
 PARTITION p20180112 VALUES LESS THAN ('2018-01-13') ENGINE = InnoDB,
 PARTITION p20180113 VALUES LESS THAN ('2018-01-14') ENGINE = InnoDB,
 PARTITION p20180114 VALUES LESS THAN ('2018-01-15') ENGINE = InnoDB,
 PARTITION p20180115 VALUES LESS THAN ('2018-01-16') ENGINE = InnoDB,
 PARTITION p20180116 VALUES LESS THAN ('2018-01-17') ENGINE = InnoDB,
 PARTITION p20180117 VALUES LESS THAN ('2018-01-18') ENGINE = InnoDB,
 PARTITION p20180118 VALUES LESS THAN ('2018-01-19') ENGINE = InnoDB,
 PARTITION p20180119 VALUES LESS THAN ('2018-01-20') ENGINE = InnoDB,
 PARTITION p20180120 VALUES LESS THAN ('2018-01-21') ENGINE = InnoDB,
 PARTITION p20180121 VALUES LESS THAN ('2018-01-22') ENGINE = InnoDB,
 PARTITION p20180122 VALUES LESS THAN ('2018-01-23') ENGINE = InnoDB,
 PARTITION p20180123 VALUES LESS THAN ('2018-01-24') ENGINE = InnoDB,
 PARTITION p20180124 VALUES LESS THAN ('2018-01-25') ENGINE = InnoDB,
 PARTITION p20180125 VALUES LESS THAN ('2018-01-26') ENGINE = InnoDB,
 PARTITION p20180126 VALUES LESS THAN ('2018-01-27') ENGINE = InnoDB,
 PARTITION p20180127 VALUES LESS THAN ('2018-01-28') ENGINE = InnoDB,
 PARTITION p20180128 VALUES LESS THAN ('2018-01-29') ENGINE = InnoDB,
 PARTITION p20180129 VALUES LESS THAN ('2018-01-30') ENGINE = InnoDB,
 PARTITION p20180130 VALUES LESS THAN ('2018-01-31') ENGINE = InnoDB,
 PARTITION p20180200 VALUES LESS THAN ('2018-02-01') ENGINE = InnoDB,
 PARTITION p20180201 VALUES LESS THAN ('2018-02-02') ENGINE = InnoDB,
 PARTITION p20180202 VALUES LESS THAN ('2018-02-03') ENGINE = InnoDB,
 PARTITION p20180203 VALUES LESS THAN ('2018-02-04') ENGINE = InnoDB,
 PARTITION p20180204 VALUES LESS THAN ('2018-02-05') ENGINE = InnoDB,
 PARTITION p20180205 VALUES LESS THAN ('2018-02-06') ENGINE = InnoDB,
 PARTITION p20180206 VALUES LESS THAN ('2018-02-07') ENGINE = InnoDB,
 PARTITION p20180207 VALUES LESS THAN ('2018-02-08') ENGINE = InnoDB,
 PARTITION p20180208 VALUES LESS THAN ('2018-02-09') ENGINE = InnoDB,
 PARTITION p20180209 VALUES LESS THAN ('2018-02-10') ENGINE = InnoDB,
 PARTITION p20180210 VALUES LESS THAN ('2018-02-11') ENGINE = InnoDB,
 PARTITION p20180211 VALUES LESS THAN ('2018-02-12') ENGINE = InnoDB,
 PARTITION p20180212 VALUES LESS THAN ('2018-02-13') ENGINE = InnoDB,
 PARTITION p20180213 VALUES LESS THAN ('2018-02-14') ENGINE = InnoDB,
 PARTITION p20180214 VALUES LESS THAN ('2018-02-15') ENGINE = InnoDB,
 PARTITION p20180215 VALUES LESS THAN ('2018-02-16') ENGINE = InnoDB,
 PARTITION p20180216 VALUES LESS THAN ('2018-02-17') ENGINE = InnoDB,
 PARTITION p20180217 VALUES LESS THAN ('2018-02-18') ENGINE = InnoDB,
 PARTITION p20180218 VALUES LESS THAN ('2018-02-19') ENGINE = InnoDB,
 PARTITION p20180219 VALUES LESS THAN ('2018-02-20') ENGINE = InnoDB,
 PARTITION p20180220 VALUES LESS THAN ('2018-02-21') ENGINE = InnoDB,
 PARTITION p20180221 VALUES LESS THAN ('2018-02-22') ENGINE = InnoDB,
 PARTITION p20180222 VALUES LESS THAN ('2018-02-23') ENGINE = InnoDB,
 PARTITION p20180223 VALUES LESS THAN ('2018-02-24') ENGINE = InnoDB,
 PARTITION p20180224 VALUES LESS THAN ('2018-02-25') ENGINE = InnoDB,
 PARTITION p20180225 VALUES LESS THAN ('2018-02-26') ENGINE = InnoDB,
 PARTITION p20180226 VALUES LESS THAN ('2018-02-27') ENGINE = InnoDB,
 PARTITION p20180227 VALUES LESS THAN ('2018-02-28') ENGINE = InnoDB,
 PARTITION p20180300 VALUES LESS THAN ('2018-03-01') ENGINE = InnoDB,
 PARTITION p20180301 VALUES LESS THAN ('2018-03-02') ENGINE = InnoDB,
 PARTITION p20180302 VALUES LESS THAN ('2018-03-03') ENGINE = InnoDB,
 PARTITION p20180303 VALUES LESS THAN ('2018-03-04') ENGINE = InnoDB,
 PARTITION p20180304 VALUES LESS THAN ('2018-03-05') ENGINE = InnoDB,
 PARTITION p20180305 VALUES LESS THAN ('2018-03-06') ENGINE = InnoDB,
 PARTITION p20180306 VALUES LESS THAN ('2018-03-07') ENGINE = InnoDB,
 PARTITION p20180307 VALUES LESS THAN ('2018-03-08') ENGINE = InnoDB,
 PARTITION p20180308 VALUES LESS THAN ('2018-03-09') ENGINE = InnoDB,
 PARTITION p20180309 VALUES LESS THAN ('2018-03-10') ENGINE = InnoDB,
 PARTITION p20180310 VALUES LESS THAN ('2018-03-11') ENGINE = InnoDB,
 PARTITION p20180311 VALUES LESS THAN ('2018-03-12') ENGINE = InnoDB,
 PARTITION p20180312 VALUES LESS THAN ('2018-03-13') ENGINE = InnoDB,
 PARTITION p20180313 VALUES LESS THAN ('2018-03-14') ENGINE = InnoDB,
 PARTITION p20180314 VALUES LESS THAN ('2018-03-15') ENGINE = InnoDB,
 PARTITION p20180315 VALUES LESS THAN ('2018-03-16') ENGINE = InnoDB,
 PARTITION p20180316 VALUES LESS THAN ('2018-03-17') ENGINE = InnoDB,
 PARTITION p20180317 VALUES LESS THAN ('2018-03-18') ENGINE = InnoDB,
 PARTITION p20180318 VALUES LESS THAN ('2018-03-19') ENGINE = InnoDB,
 PARTITION p20180319 VALUES LESS THAN ('2018-03-20') ENGINE = InnoDB,
 PARTITION p20180320 VALUES LESS THAN ('2018-03-21') ENGINE = InnoDB,
 PARTITION p20180321 VALUES LESS THAN ('2018-03-22') ENGINE = InnoDB,
 PARTITION p20180322 VALUES LESS THAN ('2018-03-23') ENGINE = InnoDB,
 PARTITION p20180323 VALUES LESS THAN ('2018-03-24') ENGINE = InnoDB,
 PARTITION p20180324 VALUES LESS THAN ('2018-03-25') ENGINE = InnoDB,
 PARTITION p20180325 VALUES LESS THAN ('2018-03-26') ENGINE = InnoDB,
 PARTITION p20180326 VALUES LESS THAN ('2018-03-27') ENGINE = InnoDB,
 PARTITION p20180327 VALUES LESS THAN ('2018-03-28') ENGINE = InnoDB,
 PARTITION p20180328 VALUES LESS THAN ('2018-03-29') ENGINE = InnoDB,
 PARTITION p20180329 VALUES LESS THAN ('2018-03-30') ENGINE = InnoDB,
 PARTITION p20180330 VALUES LESS THAN ('2018-03-31') ENGINE = InnoDB,
 PARTITION p20180331 VALUES LESS THAN ('2018-04-01') ENGINE = InnoDB,
 PARTITION p20180401 VALUES LESS THAN ('2018-04-02') ENGINE = InnoDB,
 PARTITION p20180402 VALUES LESS THAN ('2018-04-03') ENGINE = InnoDB,
 PARTITION p20180403 VALUES LESS THAN ('2018-04-04') ENGINE = InnoDB,
 PARTITION p20180404 VALUES LESS THAN ('2018-04-05') ENGINE = InnoDB,
 PARTITION p20180405 VALUES LESS THAN ('2018-04-06') ENGINE = InnoDB,
 PARTITION p20180406 VALUES LESS THAN ('2018-04-07') ENGINE = InnoDB,
 PARTITION p20180407 VALUES LESS THAN ('2018-04-08') ENGINE = InnoDB,
 PARTITION p20180408 VALUES LESS THAN ('2018-04-09') ENGINE = InnoDB,
 PARTITION p20180409 VALUES LESS THAN ('2018-04-10') ENGINE = InnoDB,
 PARTITION p20180410 VALUES LESS THAN ('2018-04-11') ENGINE = InnoDB,
 PARTITION p20180411 VALUES LESS THAN ('2018-04-12') ENGINE = InnoDB,
 PARTITION p20180412 VALUES LESS THAN ('2018-04-13') ENGINE = InnoDB,
 PARTITION p20180413 VALUES LESS THAN ('2018-04-14') ENGINE = InnoDB,
 PARTITION p20180414 VALUES LESS THAN ('2018-04-15') ENGINE = InnoDB,
 PARTITION p20180415 VALUES LESS THAN ('2018-04-16') ENGINE = InnoDB,
 PARTITION p20180416 VALUES LESS THAN ('2018-04-17') ENGINE = InnoDB,
 PARTITION p20180417 VALUES LESS THAN ('2018-04-18') ENGINE = InnoDB,
 PARTITION p20180418 VALUES LESS THAN ('2018-04-19') ENGINE = InnoDB,
 PARTITION p20180419 VALUES LESS THAN ('2018-04-20') ENGINE = InnoDB,
 PARTITION p20180420 VALUES LESS THAN ('2018-04-21') ENGINE = InnoDB,
 PARTITION p20180421 VALUES LESS THAN ('2018-04-22') ENGINE = InnoDB,
 PARTITION p20180422 VALUES LESS THAN ('2018-04-23') ENGINE = InnoDB,
 PARTITION p20180423 VALUES LESS THAN ('2018-04-24') ENGINE = InnoDB,
 PARTITION p20180424 VALUES LESS THAN ('2018-04-25') ENGINE = InnoDB,
 PARTITION p20180425 VALUES LESS THAN ('2018-04-26') ENGINE = InnoDB,
 PARTITION p20180426 VALUES LESS THAN ('2018-04-27') ENGINE = InnoDB,
 PARTITION p20180427 VALUES LESS THAN ('2018-04-28') ENGINE = InnoDB,
 PARTITION p20180428 VALUES LESS THAN ('2018-04-29') ENGINE = InnoDB,
 PARTITION p20180429 VALUES LESS THAN ('2018-04-30') ENGINE = InnoDB,
 PARTITION p20180430 VALUES LESS THAN ('2018-05-01') ENGINE = InnoDB,
 PARTITION p20180501 VALUES LESS THAN ('2018-05-02') ENGINE = InnoDB,
 PARTITION p20180502 VALUES LESS THAN ('2018-05-03') ENGINE = InnoDB,
 PARTITION p20180503 VALUES LESS THAN ('2018-05-04') ENGINE = InnoDB,
 PARTITION p20180504 VALUES LESS THAN ('2018-05-05') ENGINE = InnoDB,
 PARTITION p20180505 VALUES LESS THAN ('2018-05-06') ENGINE = InnoDB,
 PARTITION p20180506 VALUES LESS THAN ('2018-05-07') ENGINE = InnoDB,
 PARTITION p20180507 VALUES LESS THAN ('2018-05-08') ENGINE = InnoDB,
 PARTITION p20180508 VALUES LESS THAN ('2018-05-09') ENGINE = InnoDB,
 PARTITION p20180509 VALUES LESS THAN ('2018-05-10') ENGINE = InnoDB,
 PARTITION p20180510 VALUES LESS THAN ('2018-05-11') ENGINE = InnoDB,
 PARTITION p20180511 VALUES LESS THAN ('2018-05-12') ENGINE = InnoDB,
 PARTITION p20180512 VALUES LESS THAN ('2018-05-13') ENGINE = InnoDB,
 PARTITION p20180513 VALUES LESS THAN ('2018-05-14') ENGINE = InnoDB,
 PARTITION p20180514 VALUES LESS THAN ('2018-05-15') ENGINE = InnoDB,
 PARTITION p20180515 VALUES LESS THAN ('2018-05-16') ENGINE = InnoDB,
 PARTITION p20180516 VALUES LESS THAN ('2018-05-17') ENGINE = InnoDB,
 PARTITION p20180517 VALUES LESS THAN ('2018-05-18') ENGINE = InnoDB,
 PARTITION p20180518 VALUES LESS THAN ('2018-05-19') ENGINE = InnoDB,
 PARTITION p20180519 VALUES LESS THAN ('2018-05-20') ENGINE = InnoDB,
 PARTITION p20180520 VALUES LESS THAN ('2018-05-21') ENGINE = InnoDB,
 PARTITION p20180521 VALUES LESS THAN ('2018-05-22') ENGINE = InnoDB,
 PARTITION p20180522 VALUES LESS THAN ('2018-05-23') ENGINE = InnoDB,
 PARTITION p20180523 VALUES LESS THAN ('2018-05-24') ENGINE = InnoDB,
 PARTITION p20180524 VALUES LESS THAN ('2018-05-25') ENGINE = InnoDB,
 PARTITION p20180525 VALUES LESS THAN ('2018-05-26') ENGINE = InnoDB,
 PARTITION p20180526 VALUES LESS THAN ('2018-05-27') ENGINE = InnoDB,
 PARTITION p20180527 VALUES LESS THAN ('2018-05-28') ENGINE = InnoDB,
 PARTITION p20180528 VALUES LESS THAN ('2018-05-29') ENGINE = InnoDB,
 PARTITION p20180529 VALUES LESS THAN ('2018-05-30') ENGINE = InnoDB,
 PARTITION p20180530 VALUES LESS THAN ('2018-05-31') ENGINE = InnoDB,
 PARTITION p20180531 VALUES LESS THAN ('2018-06-01') ENGINE = InnoDB,
 PARTITION p20180601 VALUES LESS THAN ('2018-06-02') ENGINE = InnoDB,
 PARTITION p20180602 VALUES LESS THAN ('2018-06-03') ENGINE = InnoDB,
 PARTITION p20180603 VALUES LESS THAN ('2018-06-04') ENGINE = InnoDB,
 PARTITION p20180604 VALUES LESS THAN ('2018-06-05') ENGINE = InnoDB,
 PARTITION p20180605 VALUES LESS THAN ('2018-06-06') ENGINE = InnoDB,
 PARTITION p20180606 VALUES LESS THAN ('2018-06-07') ENGINE = InnoDB,
 PARTITION p20180607 VALUES LESS THAN ('2018-06-08') ENGINE = InnoDB,
 PARTITION p20180608 VALUES LESS THAN ('2018-06-09') ENGINE = InnoDB,
 PARTITION p20180609 VALUES LESS THAN ('2018-06-10') ENGINE = InnoDB,
 PARTITION p20180610 VALUES LESS THAN ('2018-06-11') ENGINE = InnoDB,
 PARTITION p20180611 VALUES LESS THAN ('2018-06-12') ENGINE = InnoDB,
 PARTITION p20180612 VALUES LESS THAN ('2018-06-13') ENGINE = InnoDB,
 PARTITION p20180613 VALUES LESS THAN ('2018-06-14') ENGINE = InnoDB,
 PARTITION p20180614 VALUES LESS THAN ('2018-06-15') ENGINE = InnoDB,
 PARTITION p20180615 VALUES LESS THAN ('2018-06-16') ENGINE = InnoDB,
 PARTITION p20180616 VALUES LESS THAN ('2018-06-17') ENGINE = InnoDB,
 PARTITION p20180617 VALUES LESS THAN ('2018-06-18') ENGINE = InnoDB,
 PARTITION p20180618 VALUES LESS THAN ('2018-06-19') ENGINE = InnoDB,
 PARTITION p20180619 VALUES LESS THAN ('2018-06-20') ENGINE = InnoDB,
 PARTITION p20180620 VALUES LESS THAN ('2018-06-21') ENGINE = InnoDB,
 PARTITION p20180621 VALUES LESS THAN ('2018-06-22') ENGINE = InnoDB,
 PARTITION p20180622 VALUES LESS THAN ('2018-06-23') ENGINE = InnoDB,
 PARTITION p20180623 VALUES LESS THAN ('2018-06-24') ENGINE = InnoDB,
 PARTITION p20180624 VALUES LESS THAN ('2018-06-25') ENGINE = InnoDB,
 PARTITION p20180625 VALUES LESS THAN ('2018-06-26') ENGINE = InnoDB,
 PARTITION p20180626 VALUES LESS THAN ('2018-06-27') ENGINE = InnoDB,
 PARTITION p20180627 VALUES LESS THAN ('2018-06-28') ENGINE = InnoDB,
 PARTITION p20180628 VALUES LESS THAN ('2018-06-29') ENGINE = InnoDB,
 PARTITION p20180629 VALUES LESS THAN ('2018-06-30') ENGINE = InnoDB,
 PARTITION p20180630 VALUES LESS THAN ('2018-07-01') ENGINE = InnoDB,
 PARTITION p20180701 VALUES LESS THAN ('2018-07-02') ENGINE = InnoDB,
 PARTITION p20180702 VALUES LESS THAN ('2018-07-03') ENGINE = InnoDB,
 PARTITION p20180703 VALUES LESS THAN ('2018-07-04') ENGINE = InnoDB,
 PARTITION p20180704 VALUES LESS THAN ('2018-07-05') ENGINE = InnoDB,
 PARTITION p20180705 VALUES LESS THAN ('2018-07-06') ENGINE = InnoDB,
 PARTITION p20180706 VALUES LESS THAN ('2018-07-07') ENGINE = InnoDB,
 PARTITION p20180707 VALUES LESS THAN ('2018-07-08') ENGINE = InnoDB,
 PARTITION p20180708 VALUES LESS THAN ('2018-07-09') ENGINE = InnoDB,
 PARTITION p20180709 VALUES LESS THAN ('2018-07-10') ENGINE = InnoDB,
 PARTITION p20180710 VALUES LESS THAN ('2018-07-11') ENGINE = InnoDB,
 PARTITION p20180711 VALUES LESS THAN ('2018-07-12') ENGINE = InnoDB,
 PARTITION p20180712 VALUES LESS THAN ('2018-07-13') ENGINE = InnoDB,
 PARTITION p20180713 VALUES LESS THAN ('2018-07-14') ENGINE = InnoDB,
 PARTITION p20180714 VALUES LESS THAN ('2018-07-15') ENGINE = InnoDB,
 PARTITION p20180715 VALUES LESS THAN ('2018-07-16') ENGINE = InnoDB,
 PARTITION p20180716 VALUES LESS THAN ('2018-07-17') ENGINE = InnoDB,
 PARTITION p20180717 VALUES LESS THAN ('2018-07-18') ENGINE = InnoDB,
 PARTITION p20180718 VALUES LESS THAN ('2018-07-19') ENGINE = InnoDB,
 PARTITION p20180719 VALUES LESS THAN ('2018-07-20') ENGINE = InnoDB,
 PARTITION p20180720 VALUES LESS THAN ('2018-07-21') ENGINE = InnoDB,
 PARTITION p20180721 VALUES LESS THAN ('2018-07-22') ENGINE = InnoDB,
 PARTITION p20180722 VALUES LESS THAN ('2018-07-23') ENGINE = InnoDB,
 PARTITION p20180723 VALUES LESS THAN ('2018-07-24') ENGINE = InnoDB,
 PARTITION p20180724 VALUES LESS THAN ('2018-07-25') ENGINE = InnoDB,
 PARTITION p20180725 VALUES LESS THAN ('2018-07-26') ENGINE = InnoDB,
 PARTITION p20180726 VALUES LESS THAN ('2018-07-27') ENGINE = InnoDB,
 PARTITION p20180727 VALUES LESS THAN ('2018-07-28') ENGINE = InnoDB,
 PARTITION p20180728 VALUES LESS THAN ('2018-07-29') ENGINE = InnoDB,
 PARTITION p20180729 VALUES LESS THAN ('2018-07-30') ENGINE = InnoDB,
 PARTITION p20180730 VALUES LESS THAN ('2018-07-31') ENGINE = InnoDB,
 PARTITION p20180731 VALUES LESS THAN ('2018-08-01') ENGINE = InnoDB,
 PARTITION p20180801 VALUES LESS THAN ('2018-08-02') ENGINE = InnoDB,
 PARTITION p20180802 VALUES LESS THAN ('2018-08-03') ENGINE = InnoDB,
 PARTITION p20180803 VALUES LESS THAN ('2018-08-04') ENGINE = InnoDB,
 PARTITION p20180804 VALUES LESS THAN ('2018-08-05') ENGINE = InnoDB,
 PARTITION p20180805 VALUES LESS THAN ('2018-08-06') ENGINE = InnoDB,
 PARTITION p20180806 VALUES LESS THAN ('2018-08-07') ENGINE = InnoDB,
 PARTITION p20180807 VALUES LESS THAN ('2018-08-08') ENGINE = InnoDB,
 PARTITION p20180808 VALUES LESS THAN ('2018-08-09') ENGINE = InnoDB,
 PARTITION p20180809 VALUES LESS THAN ('2018-08-10') ENGINE = InnoDB,
 PARTITION p20180810 VALUES LESS THAN ('2018-08-11') ENGINE = InnoDB,
 PARTITION p20180811 VALUES LESS THAN ('2018-08-12') ENGINE = InnoDB,
 PARTITION p20180812 VALUES LESS THAN ('2018-08-13') ENGINE = InnoDB,
 PARTITION p20180813 VALUES LESS THAN ('2018-08-14') ENGINE = InnoDB,
 PARTITION p20180814 VALUES LESS THAN ('2018-08-15') ENGINE = InnoDB,
 PARTITION p20180815 VALUES LESS THAN ('2018-08-16') ENGINE = InnoDB,
 PARTITION p20180816 VALUES LESS THAN ('2018-08-17') ENGINE = InnoDB,
 PARTITION p20180817 VALUES LESS THAN ('2018-08-18') ENGINE = InnoDB,
 PARTITION p20180818 VALUES LESS THAN ('2018-08-19') ENGINE = InnoDB,
 PARTITION p20180819 VALUES LESS THAN ('2018-08-20') ENGINE = InnoDB,
 PARTITION p20180820 VALUES LESS THAN ('2018-08-21') ENGINE = InnoDB,
 PARTITION p20180821 VALUES LESS THAN ('2018-08-22') ENGINE = InnoDB,
 PARTITION p20180822 VALUES LESS THAN ('2018-08-23') ENGINE = InnoDB,
 PARTITION p20180823 VALUES LESS THAN ('2018-08-24') ENGINE = InnoDB,
 PARTITION p20180824 VALUES LESS THAN ('2018-08-25') ENGINE = InnoDB,
 PARTITION p20180825 VALUES LESS THAN ('2018-08-26') ENGINE = InnoDB,
 PARTITION p20180826 VALUES LESS THAN ('2018-08-27') ENGINE = InnoDB,
 PARTITION p20180827 VALUES LESS THAN ('2018-08-28') ENGINE = InnoDB,
 PARTITION p20180828 VALUES LESS THAN ('2018-08-29') ENGINE = InnoDB,
 PARTITION p20180829 VALUES LESS THAN ('2018-08-30') ENGINE = InnoDB,
 PARTITION p20180830 VALUES LESS THAN ('2018-08-31') ENGINE = InnoDB,
 PARTITION p20180831 VALUES LESS THAN ('2018-09-01') ENGINE = InnoDB,
 PARTITION p20180901 VALUES LESS THAN ('2018-09-02') ENGINE = InnoDB,
 PARTITION p20180902 VALUES LESS THAN ('2018-09-03') ENGINE = InnoDB,
 PARTITION p20180903 VALUES LESS THAN ('2018-09-04') ENGINE = InnoDB,
 PARTITION p20180904 VALUES LESS THAN ('2018-09-05') ENGINE = InnoDB,
 PARTITION p20180905 VALUES LESS THAN ('2018-09-06') ENGINE = InnoDB,
 PARTITION p20180906 VALUES LESS THAN ('2018-09-07') ENGINE = InnoDB,
 PARTITION p20180907 VALUES LESS THAN ('2018-09-08') ENGINE = InnoDB,
 PARTITION p20180908 VALUES LESS THAN ('2018-09-09') ENGINE = InnoDB,
 PARTITION p20180909 VALUES LESS THAN ('2018-09-10') ENGINE = InnoDB,
 PARTITION p20180910 VALUES LESS THAN ('2018-09-11') ENGINE = InnoDB,
 PARTITION p20180911 VALUES LESS THAN ('2018-09-12') ENGINE = InnoDB,
 PARTITION p20180912 VALUES LESS THAN ('2018-09-13') ENGINE = InnoDB,
 PARTITION p20180913 VALUES LESS THAN ('2018-09-14') ENGINE = InnoDB,
 PARTITION p20180914 VALUES LESS THAN ('2018-09-15') ENGINE = InnoDB,
 PARTITION p20180915 VALUES LESS THAN ('2018-09-16') ENGINE = InnoDB,
 PARTITION p20180916 VALUES LESS THAN ('2018-09-17') ENGINE = InnoDB,
 PARTITION p20180917 VALUES LESS THAN ('2018-09-18') ENGINE = InnoDB,
 PARTITION p20180918 VALUES LESS THAN ('2018-09-19') ENGINE = InnoDB,
 PARTITION p20180919 VALUES LESS THAN ('2018-09-20') ENGINE = InnoDB,
 PARTITION p20180920 VALUES LESS THAN ('2018-09-21') ENGINE = InnoDB,
 PARTITION p20180921 VALUES LESS THAN ('2018-09-22') ENGINE = InnoDB,
 PARTITION p20180922 VALUES LESS THAN ('2018-09-23') ENGINE = InnoDB,
 PARTITION p20180923 VALUES LESS THAN ('2018-09-24') ENGINE = InnoDB,
 PARTITION p20180924 VALUES LESS THAN ('2018-09-25') ENGINE = InnoDB,
 PARTITION p20180925 VALUES LESS THAN ('2018-09-26') ENGINE = InnoDB,
 PARTITION p20180926 VALUES LESS THAN ('2018-09-27') ENGINE = InnoDB,
 PARTITION p20180927 VALUES LESS THAN ('2018-09-28') ENGINE = InnoDB,
 PARTITION p20180928 VALUES LESS THAN ('2018-09-29') ENGINE = InnoDB,
 PARTITION p20180929 VALUES LESS THAN ('2018-09-30') ENGINE = InnoDB,
 PARTITION p20180930 VALUES LESS THAN ('2018-10-01') ENGINE = InnoDB,
 PARTITION p20181001 VALUES LESS THAN ('2018-10-02') ENGINE = InnoDB,
 PARTITION p20181002 VALUES LESS THAN ('2018-10-03') ENGINE = InnoDB,
 PARTITION p20181003 VALUES LESS THAN ('2018-10-04') ENGINE = InnoDB,
 PARTITION p20181004 VALUES LESS THAN ('2018-10-05') ENGINE = InnoDB,
 PARTITION p20181005 VALUES LESS THAN ('2018-10-06') ENGINE = InnoDB,
 PARTITION p20181006 VALUES LESS THAN ('2018-10-07') ENGINE = InnoDB,
 PARTITION p20181007 VALUES LESS THAN ('2018-10-08') ENGINE = InnoDB,
 PARTITION p20181008 VALUES LESS THAN ('2018-10-09') ENGINE = InnoDB,
 PARTITION p20181009 VALUES LESS THAN ('2018-10-10') ENGINE = InnoDB,
 PARTITION p20181010 VALUES LESS THAN ('2018-10-11') ENGINE = InnoDB,
 PARTITION p20181011 VALUES LESS THAN ('2018-10-12') ENGINE = InnoDB,
 PARTITION p20181012 VALUES LESS THAN ('2018-10-13') ENGINE = InnoDB,
 PARTITION p20181013 VALUES LESS THAN ('2018-10-14') ENGINE = InnoDB,
 PARTITION p20181014 VALUES LESS THAN ('2018-10-15') ENGINE = InnoDB,
 PARTITION p20181015 VALUES LESS THAN ('2018-10-16') ENGINE = InnoDB,
 PARTITION p20181016 VALUES LESS THAN ('2018-10-17') ENGINE = InnoDB,
 PARTITION p20181017 VALUES LESS THAN ('2018-10-18') ENGINE = InnoDB,
 PARTITION p20181018 VALUES LESS THAN ('2018-10-19') ENGINE = InnoDB,
 PARTITION p20181019 VALUES LESS THAN ('2018-10-20') ENGINE = InnoDB,
 PARTITION p20181020 VALUES LESS THAN ('2018-10-21') ENGINE = InnoDB,
 PARTITION p20181021 VALUES LESS THAN ('2018-10-22') ENGINE = InnoDB,
 PARTITION p20181022 VALUES LESS THAN ('2018-10-23') ENGINE = InnoDB,
 PARTITION p20181023 VALUES LESS THAN ('2018-10-24') ENGINE = InnoDB,
 PARTITION p20181024 VALUES LESS THAN ('2018-10-25') ENGINE = InnoDB,
 PARTITION p20181025 VALUES LESS THAN ('2018-10-26') ENGINE = InnoDB,
 PARTITION p20181026 VALUES LESS THAN ('2018-10-27') ENGINE = InnoDB,
 PARTITION p20181027 VALUES LESS THAN ('2018-10-28') ENGINE = InnoDB,
 PARTITION p20181028 VALUES LESS THAN ('2018-10-29') ENGINE = InnoDB,
 PARTITION p20181029 VALUES LESS THAN ('2018-10-30') ENGINE = InnoDB,
 PARTITION p20181030 VALUES LESS THAN ('2018-10-31') ENGINE = InnoDB,
 PARTITION p20181031 VALUES LESS THAN ('2018-11-01') ENGINE = InnoDB,
 PARTITION p20181101 VALUES LESS THAN ('2018-11-02') ENGINE = InnoDB,
 PARTITION p20181102 VALUES LESS THAN ('2018-11-03') ENGINE = InnoDB,
 PARTITION p20181103 VALUES LESS THAN ('2018-11-04') ENGINE = InnoDB,
 PARTITION p20181104 VALUES LESS THAN ('2018-11-05') ENGINE = InnoDB,
 PARTITION p20181105 VALUES LESS THAN ('2018-11-06') ENGINE = InnoDB,
 PARTITION p20181106 VALUES LESS THAN ('2018-11-07') ENGINE = InnoDB,
 PARTITION p20181107 VALUES LESS THAN ('2018-11-08') ENGINE = InnoDB,
 PARTITION p20181108 VALUES LESS THAN ('2018-11-09') ENGINE = InnoDB,
 PARTITION p20181109 VALUES LESS THAN ('2018-11-10') ENGINE = InnoDB,
 PARTITION p20181110 VALUES LESS THAN ('2018-11-11') ENGINE = InnoDB,
 PARTITION p20181111 VALUES LESS THAN ('2018-11-12') ENGINE = InnoDB,
 PARTITION p20181112 VALUES LESS THAN ('2018-11-13') ENGINE = InnoDB,
 PARTITION p20181113 VALUES LESS THAN ('2018-11-14') ENGINE = InnoDB,
 PARTITION p20181114 VALUES LESS THAN ('2018-11-15') ENGINE = InnoDB,
 PARTITION p20181115 VALUES LESS THAN ('2018-11-16') ENGINE = InnoDB,
 PARTITION p20181116 VALUES LESS THAN ('2018-11-17') ENGINE = InnoDB,
 PARTITION p20181117 VALUES LESS THAN ('2018-11-18') ENGINE = InnoDB,
 PARTITION p20181118 VALUES LESS THAN ('2018-11-19') ENGINE = InnoDB,
 PARTITION p20181119 VALUES LESS THAN ('2018-11-20') ENGINE = InnoDB,
 PARTITION p20181120 VALUES LESS THAN ('2018-11-21') ENGINE = InnoDB,
 PARTITION p20181121 VALUES LESS THAN ('2018-11-22') ENGINE = InnoDB,
 PARTITION p20181122 VALUES LESS THAN ('2018-11-23') ENGINE = InnoDB,
 PARTITION p20181123 VALUES LESS THAN ('2018-11-24') ENGINE = InnoDB,
 PARTITION p20181124 VALUES LESS THAN ('2018-11-25') ENGINE = InnoDB,
 PARTITION p20181125 VALUES LESS THAN ('2018-11-26') ENGINE = InnoDB,
 PARTITION p20181126 VALUES LESS THAN ('2018-11-27') ENGINE = InnoDB,
 PARTITION p20181127 VALUES LESS THAN ('2018-11-28') ENGINE = InnoDB,
 PARTITION p20181128 VALUES LESS THAN ('2018-11-29') ENGINE = InnoDB,
 PARTITION p20181129 VALUES LESS THAN ('2018-11-30') ENGINE = InnoDB,
 PARTITION p20181130 VALUES LESS THAN ('2018-12-01') ENGINE = InnoDB,
 PARTITION p20181201 VALUES LESS THAN ('2018-12-02') ENGINE = InnoDB,
 PARTITION p20181202 VALUES LESS THAN ('2018-12-03') ENGINE = InnoDB,
 PARTITION p20181203 VALUES LESS THAN ('2018-12-04') ENGINE = InnoDB,
 PARTITION p20181204 VALUES LESS THAN ('2018-12-05') ENGINE = InnoDB,
 PARTITION p20181205 VALUES LESS THAN ('2018-12-06') ENGINE = InnoDB,
 PARTITION p20181206 VALUES LESS THAN ('2018-12-07') ENGINE = InnoDB,
 PARTITION p20181207 VALUES LESS THAN ('2018-12-08') ENGINE = InnoDB,
 PARTITION p20181208 VALUES LESS THAN ('2018-12-09') ENGINE = InnoDB,
 PARTITION p20181209 VALUES LESS THAN ('2018-12-10') ENGINE = InnoDB,
 PARTITION p20181210 VALUES LESS THAN ('2018-12-11') ENGINE = InnoDB,
 PARTITION p20181211 VALUES LESS THAN ('2018-12-12') ENGINE = InnoDB,
 PARTITION p20181212 VALUES LESS THAN ('2018-12-13') ENGINE = InnoDB,
 PARTITION p20181213 VALUES LESS THAN ('2018-12-14') ENGINE = InnoDB,
 PARTITION p20181214 VALUES LESS THAN ('2018-12-15') ENGINE = InnoDB,
 PARTITION p20181215 VALUES LESS THAN ('2018-12-16') ENGINE = InnoDB,
 PARTITION p20181216 VALUES LESS THAN ('2018-12-17') ENGINE = InnoDB,
 PARTITION p20181217 VALUES LESS THAN ('2018-12-18') ENGINE = InnoDB,
 PARTITION p20181218 VALUES LESS THAN ('2018-12-19') ENGINE = InnoDB,
 PARTITION p20181219 VALUES LESS THAN ('2018-12-20') ENGINE = InnoDB,
 PARTITION p20181220 VALUES LESS THAN ('2018-12-21') ENGINE = InnoDB,
 PARTITION p20181221 VALUES LESS THAN ('2018-12-22') ENGINE = InnoDB,
 PARTITION p20181222 VALUES LESS THAN ('2018-12-23') ENGINE = InnoDB,
 PARTITION p20181223 VALUES LESS THAN ('2018-12-24') ENGINE = InnoDB,
 PARTITION p20181224 VALUES LESS THAN ('2018-12-25') ENGINE = InnoDB,
 PARTITION p20181225 VALUES LESS THAN ('2018-12-26') ENGINE = InnoDB,
 PARTITION p20181226 VALUES LESS THAN ('2018-12-27') ENGINE = InnoDB,
 PARTITION p20181227 VALUES LESS THAN ('2018-12-28') ENGINE = InnoDB,
 PARTITION p20181228 VALUES LESS THAN ('2018-12-29') ENGINE = InnoDB,
 PARTITION p20181229 VALUES LESS THAN ('2018-12-30') ENGINE = InnoDB,
 PARTITION p20181230 VALUES LESS THAN ('2018-12-31') ENGINE = InnoDB,
 PARTITION p20181231 VALUES LESS THAN ('2019-01-01') ENGINE = InnoDB,
 PARTITION p20190101 VALUES LESS THAN ('2019-01-02') ENGINE = InnoDB,
 PARTITION p20190102 VALUES LESS THAN ('2019-01-03') ENGINE = InnoDB,
 PARTITION p20190103 VALUES LESS THAN ('2019-01-04') ENGINE = InnoDB,
 PARTITION p20190104 VALUES LESS THAN ('2019-01-05') ENGINE = InnoDB,
 PARTITION p20190105 VALUES LESS THAN ('2019-01-06') ENGINE = InnoDB,
 PARTITION p20190106 VALUES LESS THAN ('2019-01-07') ENGINE = InnoDB,
 PARTITION p20190107 VALUES LESS THAN ('2019-01-08') ENGINE = InnoDB,
 PARTITION p20190108 VALUES LESS THAN ('2019-01-09') ENGINE = InnoDB,
 PARTITION p20190109 VALUES LESS THAN ('2019-01-10') ENGINE = InnoDB,
 PARTITION p20190110 VALUES LESS THAN ('2019-01-11') ENGINE = InnoDB,
 PARTITION p20190111 VALUES LESS THAN ('2019-01-12') ENGINE = InnoDB,
 PARTITION p20190112 VALUES LESS THAN ('2019-01-13') ENGINE = InnoDB,
 PARTITION p20190113 VALUES LESS THAN ('2019-01-14') ENGINE = InnoDB,
 PARTITION p20190114 VALUES LESS THAN ('2019-01-15') ENGINE = InnoDB,
 PARTITION p20190115 VALUES LESS THAN ('2019-01-16') ENGINE = InnoDB,
 PARTITION p20190116 VALUES LESS THAN ('2019-01-17') ENGINE = InnoDB,
 PARTITION p20190117 VALUES LESS THAN ('2019-01-18') ENGINE = InnoDB,
 PARTITION p20190118 VALUES LESS THAN ('2019-01-19') ENGINE = InnoDB,
 PARTITION p20190119 VALUES LESS THAN ('2019-01-20') ENGINE = InnoDB,
 PARTITION p20190120 VALUES LESS THAN ('2019-01-21') ENGINE = InnoDB,
 PARTITION p20190121 VALUES LESS THAN ('2019-01-22') ENGINE = InnoDB,
 PARTITION p20190122 VALUES LESS THAN ('2019-01-23') ENGINE = InnoDB,
 PARTITION p20190123 VALUES LESS THAN ('2019-01-24') ENGINE = InnoDB,
 PARTITION p20190124 VALUES LESS THAN ('2019-01-25') ENGINE = InnoDB,
 PARTITION p20190125 VALUES LESS THAN ('2019-01-26') ENGINE = InnoDB,
 PARTITION p20190126 VALUES LESS THAN ('2019-01-27') ENGINE = InnoDB,
 PARTITION p20190127 VALUES LESS THAN ('2019-01-28') ENGINE = InnoDB,
 PARTITION p20190128 VALUES LESS THAN ('2019-01-29') ENGINE = InnoDB,
 PARTITION p20190129 VALUES LESS THAN ('2019-01-30') ENGINE = InnoDB,
 PARTITION p20190130 VALUES LESS THAN ('2019-01-31') ENGINE = InnoDB,
 PARTITION p20190131 VALUES LESS THAN ('2019-02-01') ENGINE = InnoDB,
 PARTITION p20190201 VALUES LESS THAN ('2019-02-02') ENGINE = InnoDB,
 PARTITION p20190202 VALUES LESS THAN ('2019-02-03') ENGINE = InnoDB,
 PARTITION p20190203 VALUES LESS THAN ('2019-02-04') ENGINE = InnoDB,
 PARTITION p20190204 VALUES LESS THAN ('2019-02-05') ENGINE = InnoDB,
 PARTITION p20190205 VALUES LESS THAN ('2019-02-06') ENGINE = InnoDB,
 PARTITION p20190206 VALUES LESS THAN ('2019-02-07') ENGINE = InnoDB,
 PARTITION p20190207 VALUES LESS THAN ('2019-02-08') ENGINE = InnoDB,
 PARTITION p20190208 VALUES LESS THAN ('2019-02-09') ENGINE = InnoDB,
 PARTITION p20190209 VALUES LESS THAN ('2019-02-10') ENGINE = InnoDB,
 PARTITION p20190210 VALUES LESS THAN ('2019-02-11') ENGINE = InnoDB,
 PARTITION p20190211 VALUES LESS THAN ('2019-02-12') ENGINE = InnoDB,
 PARTITION p20190212 VALUES LESS THAN ('2019-02-13') ENGINE = InnoDB,
 PARTITION p20190213 VALUES LESS THAN ('2019-02-14') ENGINE = InnoDB,
 PARTITION p20190214 VALUES LESS THAN ('2019-02-15') ENGINE = InnoDB,
 PARTITION p20190215 VALUES LESS THAN ('2019-02-16') ENGINE = InnoDB,
 PARTITION p20190216 VALUES LESS THAN ('2019-02-17') ENGINE = InnoDB,
 PARTITION p20190217 VALUES LESS THAN ('2019-02-18') ENGINE = InnoDB,
 PARTITION p20190218 VALUES LESS THAN ('2019-02-19') ENGINE = InnoDB,
 PARTITION p20190219 VALUES LESS THAN ('2019-02-20') ENGINE = InnoDB,
 PARTITION p20190220 VALUES LESS THAN ('2019-02-21') ENGINE = InnoDB,
 PARTITION p20190221 VALUES LESS THAN ('2019-02-22') ENGINE = InnoDB,
 PARTITION p20190222 VALUES LESS THAN ('2019-02-23') ENGINE = InnoDB,
 PARTITION p20190223 VALUES LESS THAN ('2019-02-24') ENGINE = InnoDB,
 PARTITION p20190224 VALUES LESS THAN ('2019-02-25') ENGINE = InnoDB,
 PARTITION p20190225 VALUES LESS THAN ('2019-02-26') ENGINE = InnoDB,
 PARTITION p20190226 VALUES LESS THAN ('2019-02-27') ENGINE = InnoDB,
 PARTITION p20190227 VALUES LESS THAN ('2019-02-28') ENGINE = InnoDB,
 PARTITION p20190228 VALUES LESS THAN ('2019-03-01') ENGINE = InnoDB,
 PARTITION p20190301 VALUES LESS THAN ('2019-03-02') ENGINE = InnoDB,
 PARTITION p20190302 VALUES LESS THAN ('2019-03-03') ENGINE = InnoDB,
 PARTITION p20190303 VALUES LESS THAN ('2019-03-04') ENGINE = InnoDB,
 PARTITION p20190304 VALUES LESS THAN ('2019-03-05') ENGINE = InnoDB,
 PARTITION p20190305 VALUES LESS THAN ('2019-03-06') ENGINE = InnoDB,
 PARTITION p20190306 VALUES LESS THAN ('2019-03-07') ENGINE = InnoDB,
 PARTITION p20190307 VALUES LESS THAN ('2019-03-08') ENGINE = InnoDB,
 PARTITION p20190308 VALUES LESS THAN ('2019-03-09') ENGINE = InnoDB,
 PARTITION p20190309 VALUES LESS THAN ('2019-03-10') ENGINE = InnoDB,
 PARTITION p20190310 VALUES LESS THAN ('2019-03-11') ENGINE = InnoDB,
 PARTITION p20190311 VALUES LESS THAN ('2019-03-12') ENGINE = InnoDB,
 PARTITION p20190312 VALUES LESS THAN ('2019-03-13') ENGINE = InnoDB,
 PARTITION p20190313 VALUES LESS THAN ('2019-03-14') ENGINE = InnoDB,
 PARTITION p20190314 VALUES LESS THAN ('2019-03-15') ENGINE = InnoDB,
 PARTITION p20190315 VALUES LESS THAN ('2019-03-16') ENGINE = InnoDB,
 PARTITION p20190316 VALUES LESS THAN ('2019-03-17') ENGINE = InnoDB,
 PARTITION p20190317 VALUES LESS THAN ('2019-03-18') ENGINE = InnoDB,
 PARTITION p20190318 VALUES LESS THAN ('2019-03-19') ENGINE = InnoDB,
 PARTITION p20190319 VALUES LESS THAN ('2019-03-20') ENGINE = InnoDB,
 PARTITION p20190320 VALUES LESS THAN ('2019-03-21') ENGINE = InnoDB,
 PARTITION p20190321 VALUES LESS THAN ('2019-03-22') ENGINE = InnoDB,
 PARTITION p20190322 VALUES LESS THAN ('2019-03-23') ENGINE = InnoDB,
 PARTITION p20190323 VALUES LESS THAN ('2019-03-24') ENGINE = InnoDB,
 PARTITION p20190324 VALUES LESS THAN ('2019-03-25') ENGINE = InnoDB,
 PARTITION p20190325 VALUES LESS THAN ('2019-03-26') ENGINE = InnoDB,
 PARTITION p20190326 VALUES LESS THAN ('2019-03-27') ENGINE = InnoDB,
 PARTITION p20190327 VALUES LESS THAN ('2019-03-28') ENGINE = InnoDB,
 PARTITION p20190328 VALUES LESS THAN ('2019-03-29') ENGINE = InnoDB,
 PARTITION p20190329 VALUES LESS THAN ('2019-03-30') ENGINE = InnoDB,
 PARTITION p20190330 VALUES LESS THAN ('2019-03-31') ENGINE = InnoDB,
 PARTITION p20190331 VALUES LESS THAN ('2019-04-01') ENGINE = InnoDB,
 PARTITION p20190401 VALUES LESS THAN ('2019-04-02') ENGINE = InnoDB,
 PARTITION p20190402 VALUES LESS THAN ('2019-04-03') ENGINE = InnoDB,
 PARTITION p20190403 VALUES LESS THAN ('2019-04-04') ENGINE = InnoDB,
 PARTITION p20190404 VALUES LESS THAN ('2019-04-05') ENGINE = InnoDB,
 PARTITION p20190405 VALUES LESS THAN ('2019-04-06') ENGINE = InnoDB,
 PARTITION p20190406 VALUES LESS THAN ('2019-04-07') ENGINE = InnoDB,
 PARTITION p20190407 VALUES LESS THAN ('2019-04-08') ENGINE = InnoDB,
 PARTITION p20190408 VALUES LESS THAN ('2019-04-09') ENGINE = InnoDB,
 PARTITION p20190409 VALUES LESS THAN ('2019-04-10') ENGINE = InnoDB,
 PARTITION p20190410 VALUES LESS THAN ('2019-04-11') ENGINE = InnoDB,
 PARTITION p20190411 VALUES LESS THAN ('2019-04-12') ENGINE = InnoDB,
 PARTITION p20190412 VALUES LESS THAN ('2019-04-13') ENGINE = InnoDB,
 PARTITION p20190413 VALUES LESS THAN ('2019-04-14') ENGINE = InnoDB,
 PARTITION p20190414 VALUES LESS THAN ('2019-04-15') ENGINE = InnoDB,
 PARTITION p20190415 VALUES LESS THAN ('2019-04-16') ENGINE = InnoDB,
 PARTITION p20190416 VALUES LESS THAN ('2019-04-17') ENGINE = InnoDB,
 PARTITION p20190417 VALUES LESS THAN ('2019-04-18') ENGINE = InnoDB,
 PARTITION p20190418 VALUES LESS THAN ('2019-04-19') ENGINE = InnoDB,
 PARTITION p20190419 VALUES LESS THAN ('2019-04-20') ENGINE = InnoDB,
 PARTITION p20190420 VALUES LESS THAN ('2019-04-21') ENGINE = InnoDB,
 PARTITION p20190421 VALUES LESS THAN ('2019-04-22') ENGINE = InnoDB,
 PARTITION p20190422 VALUES LESS THAN ('2019-04-23') ENGINE = InnoDB,
 PARTITION p20190423 VALUES LESS THAN ('2019-04-24') ENGINE = InnoDB,
 PARTITION p20190424 VALUES LESS THAN ('2019-04-25') ENGINE = InnoDB,
 PARTITION p20190425 VALUES LESS THAN ('2019-04-26') ENGINE = InnoDB,
 PARTITION p20190426 VALUES LESS THAN ('2019-04-27') ENGINE = InnoDB,
 PARTITION p20190427 VALUES LESS THAN ('2019-04-28') ENGINE = InnoDB,
 PARTITION p20190428 VALUES LESS THAN ('2019-04-29') ENGINE = InnoDB,
 PARTITION p20190429 VALUES LESS THAN ('2019-04-30') ENGINE = InnoDB,
 PARTITION p20190430 VALUES LESS THAN ('2019-05-01') ENGINE = InnoDB,
 PARTITION p20190501 VALUES LESS THAN ('2019-05-02') ENGINE = InnoDB,
 PARTITION p20190502 VALUES LESS THAN ('2019-05-03') ENGINE = InnoDB,
 PARTITION p20190503 VALUES LESS THAN ('2019-05-04') ENGINE = InnoDB,
 PARTITION p20190504 VALUES LESS THAN ('2019-05-05') ENGINE = InnoDB,
 PARTITION p20190505 VALUES LESS THAN ('2019-05-06') ENGINE = InnoDB,
 PARTITION p20190506 VALUES LESS THAN ('2019-05-07') ENGINE = InnoDB,
 PARTITION p20190507 VALUES LESS THAN ('2019-05-08') ENGINE = InnoDB,
 PARTITION p20190508 VALUES LESS THAN ('2019-05-09') ENGINE = InnoDB,
 PARTITION p20190509 VALUES LESS THAN ('2019-05-10') ENGINE = InnoDB,
 PARTITION p20190510 VALUES LESS THAN ('2019-05-11') ENGINE = InnoDB,
 PARTITION p20190511 VALUES LESS THAN ('2019-05-12') ENGINE = InnoDB,
 PARTITION p20190512 VALUES LESS THAN ('2019-05-13') ENGINE = InnoDB,
 PARTITION p20190513 VALUES LESS THAN ('2019-05-14') ENGINE = InnoDB,
 PARTITION p20190514 VALUES LESS THAN ('2019-05-15') ENGINE = InnoDB,
 PARTITION p20190515 VALUES LESS THAN ('2019-05-16') ENGINE = InnoDB,
 PARTITION p20190516 VALUES LESS THAN ('2019-05-17') ENGINE = InnoDB,
 PARTITION p20190517 VALUES LESS THAN ('2019-05-18') ENGINE = InnoDB,
 PARTITION p20190518 VALUES LESS THAN ('2019-05-19') ENGINE = InnoDB,
 PARTITION p20190519 VALUES LESS THAN ('2019-05-20') ENGINE = InnoDB,
 PARTITION p20190520 VALUES LESS THAN ('2019-05-21') ENGINE = InnoDB,
 PARTITION p20190521 VALUES LESS THAN ('2019-05-22') ENGINE = InnoDB,
 PARTITION p20190522 VALUES LESS THAN ('2019-05-23') ENGINE = InnoDB,
 PARTITION p20190523 VALUES LESS THAN ('2019-05-24') ENGINE = InnoDB,
 PARTITION p20190524 VALUES LESS THAN ('2019-05-25') ENGINE = InnoDB,
 PARTITION p20190525 VALUES LESS THAN ('2019-05-26') ENGINE = InnoDB,
 PARTITION p20190526 VALUES LESS THAN ('2019-05-27') ENGINE = InnoDB,
 PARTITION p20190527 VALUES LESS THAN ('2019-05-28') ENGINE = InnoDB,
 PARTITION p20190528 VALUES LESS THAN ('2019-05-29') ENGINE = InnoDB,
 PARTITION p20190529 VALUES LESS THAN ('2019-05-30') ENGINE = InnoDB,
 PARTITION p20190530 VALUES LESS THAN ('2019-05-31') ENGINE = InnoDB,
 PARTITION p20190531 VALUES LESS THAN ('2019-06-01') ENGINE = InnoDB,
 PARTITION p20190601 VALUES LESS THAN ('2019-06-02') ENGINE = InnoDB,
 PARTITION p20190602 VALUES LESS THAN ('2019-06-03') ENGINE = InnoDB,
 PARTITION p20190603 VALUES LESS THAN ('2019-06-04') ENGINE = InnoDB,
 PARTITION p20190604 VALUES LESS THAN ('2019-06-05') ENGINE = InnoDB,
 PARTITION p20190605 VALUES LESS THAN ('2019-06-06') ENGINE = InnoDB,
 PARTITION p20190606 VALUES LESS THAN ('2019-06-07') ENGINE = InnoDB,
 PARTITION p20190607 VALUES LESS THAN ('2019-06-08') ENGINE = InnoDB,
 PARTITION p20190608 VALUES LESS THAN ('2019-06-09') ENGINE = InnoDB,
 PARTITION p20190609 VALUES LESS THAN ('2019-06-10') ENGINE = InnoDB,
 PARTITION p20190610 VALUES LESS THAN ('2019-06-11') ENGINE = InnoDB,
 PARTITION p20190611 VALUES LESS THAN ('2019-06-12') ENGINE = InnoDB,
 PARTITION p20190612 VALUES LESS THAN ('2019-06-13') ENGINE = InnoDB,
 PARTITION p20190613 VALUES LESS THAN ('2019-06-14') ENGINE = InnoDB,
 PARTITION p20190614 VALUES LESS THAN ('2019-06-15') ENGINE = InnoDB,
 PARTITION p20190615 VALUES LESS THAN ('2019-06-16') ENGINE = InnoDB,
 PARTITION p20190616 VALUES LESS THAN ('2019-06-17') ENGINE = InnoDB,
 PARTITION p20190617 VALUES LESS THAN ('2019-06-18') ENGINE = InnoDB,
 PARTITION p20190618 VALUES LESS THAN ('2019-06-19') ENGINE = InnoDB,
 PARTITION p20190619 VALUES LESS THAN ('2019-06-20') ENGINE = InnoDB,
 PARTITION p20190620 VALUES LESS THAN ('2019-06-21') ENGINE = InnoDB,
 PARTITION p20190621 VALUES LESS THAN ('2019-06-22') ENGINE = InnoDB,
 PARTITION p20190622 VALUES LESS THAN ('2019-06-23') ENGINE = InnoDB,
 PARTITION p20190623 VALUES LESS THAN ('2019-06-24') ENGINE = InnoDB,
 PARTITION p20190624 VALUES LESS THAN ('2019-06-25') ENGINE = InnoDB,
 PARTITION p20190625 VALUES LESS THAN ('2019-06-26') ENGINE = InnoDB,
 PARTITION p20190626 VALUES LESS THAN ('2019-06-27') ENGINE = InnoDB,
 PARTITION p20190627 VALUES LESS THAN ('2019-06-28') ENGINE = InnoDB,
 PARTITION p20190628 VALUES LESS THAN ('2019-06-29') ENGINE = InnoDB,
 PARTITION p20190629 VALUES LESS THAN ('2019-06-30') ENGINE = InnoDB,
 PARTITION p20190630 VALUES LESS THAN ('2019-07-01') ENGINE = InnoDB,
 PARTITION p20190701 VALUES LESS THAN ('2019-07-02') ENGINE = InnoDB,
 PARTITION p20190702 VALUES LESS THAN ('2019-07-03') ENGINE = InnoDB,
 PARTITION p20190703 VALUES LESS THAN ('2019-07-04') ENGINE = InnoDB,
 PARTITION p20190704 VALUES LESS THAN ('2019-07-05') ENGINE = InnoDB,
 PARTITION p20190705 VALUES LESS THAN ('2019-07-06') ENGINE = InnoDB,
 PARTITION p20190706 VALUES LESS THAN ('2019-07-07') ENGINE = InnoDB,
 PARTITION p20190707 VALUES LESS THAN ('2019-07-08') ENGINE = InnoDB,
 PARTITION p20190708 VALUES LESS THAN ('2019-07-09') ENGINE = InnoDB,
 PARTITION p20190709 VALUES LESS THAN ('2019-07-10') ENGINE = InnoDB,
 PARTITION p20190710 VALUES LESS THAN ('2019-07-11') ENGINE = InnoDB,
 PARTITION p20190711 VALUES LESS THAN ('2019-07-12') ENGINE = InnoDB,
 PARTITION p20190712 VALUES LESS THAN ('2019-07-13') ENGINE = InnoDB,
 PARTITION p20190713 VALUES LESS THAN ('2019-07-14') ENGINE = InnoDB,
 PARTITION p20190714 VALUES LESS THAN ('2019-07-15') ENGINE = InnoDB,
 PARTITION p20190715 VALUES LESS THAN ('2019-07-16') ENGINE = InnoDB,
 PARTITION p20190716 VALUES LESS THAN ('2019-07-17') ENGINE = InnoDB,
 PARTITION p20190717 VALUES LESS THAN ('2019-07-18') ENGINE = InnoDB,
 PARTITION p20190718 VALUES LESS THAN ('2019-07-19') ENGINE = InnoDB,
 PARTITION p20190719 VALUES LESS THAN ('2019-07-20') ENGINE = InnoDB,
 PARTITION p20190720 VALUES LESS THAN ('2019-07-21') ENGINE = InnoDB,
 PARTITION p20190721 VALUES LESS THAN ('2019-07-22') ENGINE = InnoDB,
 PARTITION p20190722 VALUES LESS THAN ('2019-07-23') ENGINE = InnoDB,
 PARTITION p20190723 VALUES LESS THAN ('2019-07-24') ENGINE = InnoDB,
 PARTITION p20190724 VALUES LESS THAN ('2019-07-25') ENGINE = InnoDB,
 PARTITION p20190725 VALUES LESS THAN ('2019-07-26') ENGINE = InnoDB,
 PARTITION p20190726 VALUES LESS THAN ('2019-07-27') ENGINE = InnoDB,
 PARTITION p20190727 VALUES LESS THAN ('2019-07-28') ENGINE = InnoDB) */;

-- ----------------------------
-- Records of PASSEDBY_VEHICLE
-- ----------------------------

-- ----------------------------
-- Table structure for ROAD_CROSS_POINT
-- ----------------------------
DROP TABLE IF EXISTS `ROAD_CROSS_POINT`;
CREATE TABLE `ROAD_CROSS_POINT` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `NAME` varchar(256) DEFAULT NULL COMMENT '名称',
  `LATITUDE` double DEFAULT NULL COMMENT '纬度',
  `LONGITUDE` double DEFAULT NULL COMMENT '经度',
  `DIRECTION_CODE` int(11) DEFAULT NULL COMMENT '道路方向(可能是双向)',
  `ROADWAY_NUM` int(11) DEFAULT NULL COMMENT '车道数量',
  `AREA_ID` bigint(20) DEFAULT NULL,
  `DELETED` tinyint(4) DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_4` (`AREA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='路口点位';

-- ----------------------------
-- Records of ROAD_CROSS_POINT
-- ----------------------------

-- ----------------------------
-- Table structure for ROLE
-- ----------------------------
DROP TABLE IF EXISTS `ROLE`;
CREATE TABLE `ROLE` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `NAME` char(10) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of ROLE
-- ----------------------------
INSERT INTO `ROLE` VALUES ('0', 'Operator');
INSERT INTO `ROLE` VALUES ('1', 'Admin');
INSERT INTO `ROLE` VALUES ('2', 'SuperAdmin');

-- ----------------------------
-- Table structure for ROLE_AUTHORITY
-- ----------------------------
DROP TABLE IF EXISTS `ROLE_AUTHORITY`;
CREATE TABLE `ROLE_AUTHORITY` (
  `ROLE_ID` bigint(20) NOT NULL COMMENT '角色ID',
  `AUTHORITY_ID` bigint(20) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`ROLE_ID`,`AUTHORITY_ID`),
  KEY `FK_Reference_37` (`AUTHORITY_ID`),
  CONSTRAINT `FK_Reference_36` FOREIGN KEY (`ROLE_ID`) REFERENCES `ROLE` (`ID`),
  CONSTRAINT `FK_Reference_37` FOREIGN KEY (`AUTHORITY_ID`) REFERENCES `AUTHORITY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关系表';

-- ----------------------------
-- Records of ROLE_AUTHORITY
-- ----------------------------
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '1');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '1');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '1');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '2');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '2');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '2');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '3');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '3');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '3');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '4');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '4');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '4');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '5');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '5');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '5');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '6');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '6');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '6');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '7');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '7');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '7');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '8');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '8');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '8');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '9');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '9');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '9');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '10');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '10');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '10');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '11');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '11');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '11');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '12');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '12');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '12');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '13');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '13');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '13');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '14');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '14');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '14');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '15');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '15');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '15');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '16');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '16');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '16');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '17');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '17');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '17');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '18');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '18');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '18');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '19');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '19');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '19');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '20');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '20');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '20');
INSERT INTO `ROLE_AUTHORITY` VALUES ('0', '21');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '21');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '21');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '22');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '22');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '23');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '23');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '24');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '24');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '25');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '25');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '26');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '26');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '27');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '27');
INSERT INTO `ROLE_AUTHORITY` VALUES ('1', '28');
INSERT INTO `ROLE_AUTHORITY` VALUES ('2', '28');

-- ----------------------------
-- Table structure for SERVER_ADDRESS_RECORD
-- ----------------------------
DROP TABLE IF EXISTS `SERVER_ADDRESS_RECORD`;
CREATE TABLE `SERVER_ADDRESS_RECORD` (
  ` ID` bigint(20) NOT NULL COMMENT '主键',
  ` SERVER` varchar(64) DEFAULT NULL COMMENT '服务器标识',
  ` ADDRESS` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  PRIMARY KEY (` ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT=' 分服务对应IP表';

-- ----------------------------
-- Records of SERVER_ADDRESS_RECORD
-- ----------------------------

-- ----------------------------
-- Table structure for SYSTEM_DIC
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_DIC`;
CREATE TABLE `SYSTEM_DIC` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `zh` varchar(255) DEFAULT NULL,
  `en` varchar(255) DEFAULT NULL,
  `ang` varchar(255) DEFAULT NULL,
  `bol` varchar(255) DEFAULT NULL,
  `enable` int(11) DEFAULT '1',
  `pt` varchar(255) DEFAULT NULL COMMENT '葡语',
  `es` varchar(255) DEFAULT NULL COMMENT '西语',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of SYSTEM_DIC
-- ----------------------------
INSERT INTO `SYSTEM_DIC` VALUES ('1', '0', 'plateColor', '白色', '白色_en', '白色_ang', '白色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('2', '1', 'plateColor', '黄色', '黄色_en', '黄色_ang', '黄色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('3', '2', 'plateColor', '蓝色', '蓝色_en', '蓝色_ang', '蓝色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('4', '3', 'plateColor', '黑色', '黑色_en', '黑色_ang', '黑色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('5', '4', 'plateColor', '其他', '其他_en', '其他_ang', '其他_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('6', '5', 'plateColor', '绿色', '绿色_en', '绿色_ang', '绿色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('7', '6', 'plateColor', '红色', '红色_en', '红色_ang', '红色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('8', '7', 'plateColor', '黄绿双色', '黄绿双色_en', '黄绿双色_ang', '黄绿双色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('9', '8', 'plateColor', '渐变绿色', '渐变绿色_en', '渐变绿色_ang', '渐变绿色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('10', 'A', 'vehicleColor', '白色', '白色_en', '白色_ang', '白色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('11', 'B', 'vehicleColor', '灰色', '灰色_en', '灰色_ang', '灰色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('12', 'C', 'vehicleColor', '黄色', '黄色_en', '黄色_ang', '黄色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('13', 'D', 'vehicleColor', '粉色', '粉色_en', '粉色_ang', '粉色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('14', 'E', 'vehicleColor', '红色', '红色_en', '红色_ang', '红色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('15', 'F', 'vehicleColor', '紫色', '紫色_en', '紫色_ang', '紫色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('16', 'G', 'vehicleColor', '绿色', '绿色_en', '绿色_ang', '绿色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('17', 'H', 'vehicleColor', '蓝色', '蓝色_en', '蓝色_ang', '蓝色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('18', 'I', 'vehicleColor', '棕色', '棕色_en', '棕色_ang', '棕色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('19', 'J', 'vehicleColor', '黑色', '黑色_en', '黑色_ang', '黑色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('20', 'K', 'vehicleColor', '橙色', '橙色_en', '橙色_ang', '橙色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('21', 'L', 'vehicleColor', '青色', '青色_en', '青色_ang', '青色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('22', 'M', 'vehicleColor', '银色', '银色_en', '银色_ang', '银色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('23', 'N', 'vehicleColor', '银白色', '银白色_en', '银白色_ang', '银白色_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('24', 'Z', 'vehicleColor', '其他', '其他_en', '其他_ang', '其他_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('25', '1', 'carType', '小型车', '小型车_en', '小型车_ang', '小型车_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('26', '2', 'carType', '中型车', '中型车_en', '中型车_ang', '中型车_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('27', '3', 'carType', '大型车', '大型车_en', '大型车_ang', '大型车_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('28', '6', 'carType', '二轮车', '二轮车_en', '二轮车_ang', '二轮车_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('29', '7', 'carType', '三轮车', '三轮车_en', '三轮车_ang', '三轮车_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('30', '8', 'carType', '摩托车', '摩托车_en', '摩托车_ang', '摩托车_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('31', '9', 'carType', '拖拉机', '拖拉机_en', '拖拉机_ang', '拖拉机_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('32', '10', 'carType', '农用货车', '农用货车_en', '农用货车_ang', '农用货车_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('33', '11', 'carType', '轿车', '轿车_en', '轿车_ang', '轿车_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('34', '12', 'carType', 'SUV', 'SUV_en', 'SUV_ang', 'SUV_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('35', '13', 'carType', '面包车', '面包车_en', '面包车_ang', '面包车_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('36', '14', 'carType', '小货车', '小货车_en', '小货车_ang', '小货车_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('37', '15', 'carType', '中巴车', '中巴车_en', '中巴车_ang', '中巴车_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('38', '16', 'carType', '大客车', '大客车_en', '大客车_ang', '大客车_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('39', '17', 'carType', '大货车', '大货车_en', '大货车_ang', '大货车_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('40', '18', 'carType', '皮卡车', '皮卡车_en', '皮卡车_ang', '皮卡车_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('41', '19', 'carType', 'MPV 商务车', 'MPV 商务车_en', 'MPV 商务车_ang', 'MPV 商务车_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('42', '4', 'carType', '其他', '其他_en', '其他_ang', '其他_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('43', '0', 'carType', '未知', '未知_en', '未知_ang', '未知_bol', '1', null, null);
INSERT INTO `SYSTEM_DIC` VALUES ('44', '0', 'listTypeStr', '名单类型（必填：', 'TYPE(Required:', '', '', '1', 'Tipo(É obrigatório preencher:', 'Tipo(É obrigatório preencher:');
INSERT INTO `SYSTEM_DIC` VALUES ('45', '0', 'plateNumberForList', '车牌号(必填,长度=12)', 'plate_number(required,length=12)', '', '', '1', 'Número da matrícula(É obrigatório preencher,comprimento=12)', 'Número da matrícula(É obrigatório preencher,comprimento=12)');
INSERT INTO `SYSTEM_DIC` VALUES ('46', '0', 'vcehicleTypeStr', '车辆类型(1-小型车,2-中型车,3-大型车,4-其他)', 'vehicle type(1-small,2-medium,3-large,4-other)', '', '', '1', 'tipo de veículo (1-pequeno, 2-médio, 3-grande, 4-outro)', 'tipo de veículo (1-pequeno, 2-médio, 3-grande, 4-outro)');
INSERT INTO `SYSTEM_DIC` VALUES ('47', '0', 'plateColorStr', '车牌颜色(白色-0, 黄色-1, 蓝色-2, 黑色-3, 其他-4, 绿色-5, 红色-6)', 'plate color(white-0, yellow-1, blue-2, black-3, 4-other, 5-gree, 6-red)', '', '', '1', 'cor da placa (branco-0, amarelo-1, azul-2, preto-3, 4-outro, 5-gree, 6-vermelho)', 'cor da placa (branco-0, amarelo-1, azul-2, preto-3, 4-outro, 5-gree, 6-vermelho)');
INSERT INTO `SYSTEM_DIC` VALUES ('48', '0', 'vehicleColorStr', '车身颜色（白色-A,灰色-B,黄色-C,粉色-D,红色-E,紫色-F,绿色-G,蓝色-H）', 'VEHICLE COLOR(white-A,gray-B,yellow-C,pink-D,red-E,violet-F,green-G,blue-H,brown-I,black-J,unrecognized-K,other-Z)', '', '', '1', 'COR DO VEÍCULO (branco-A, cinza-B, amarelo-C, rosa-D, vermelho-E, violeta-F, verde-G, azul-H, marrom-I, preto-J, não reconhecido-K, outro-Z )', 'COR DO VEÍCULO (branco-A, cinza-B, amarelo-C, rosa-D, vermelho-E, violeta-F, verde-G, azul-H, marrom-I, preto-J, não reconhecido-K, outro-Z )');
INSERT INTO `SYSTEM_DIC` VALUES ('49', '0', 'casetime', '案发时间(必填;dd-MM-yyyy HH:mm:ss)', 'case time(Required;dd-MM-yyyy HH:mm:ss)', '', '', '1', 'tempo do caso (Necessário; dd-MM-aaaa HH: mm: ss)', 'tempo do caso (Necessário; dd-MM-aaaa HH: mm: ss)');
INSERT INTO `SYSTEM_DIC` VALUES ('50', '0', 'caseadress', '案发地址', 'case adress', '', '', '1', 'Endereço da caixa', 'Endereço da caixa');
INSERT INTO `SYSTEM_DIC` VALUES ('51', '0', 'feature', '特征', 'feature', '', '', '1', 'Característica', 'Característica');
INSERT INTO `SYSTEM_DIC` VALUES ('52', '0', 'description', '描述', 'description', '', '', '1', 'descrição', 'descrição');
INSERT INTO `SYSTEM_DIC` VALUES ('53', '0', 'blacklist', '黑名单', 'blacklist', '', '', '1', 'Lista negra dos veículos', 'Lista negra dos veículos');
INSERT INTO `SYSTEM_DIC` VALUES ('54', '0', 'whitelist', '白名单', 'whitelist', '', '', '1', 'Lista branca de veículos', 'Lista branca de veículos');
INSERT INTO `SYSTEM_DIC` VALUES ('55', '0', 'plateNumber', '车牌号', 'plate number', '', '', '1', 'número da placa', 'número da placa');
INSERT INTO `SYSTEM_DIC` VALUES ('56', '0', 'cityName', '城市', 'city', null, null, '1', 'cidade', 'cidade');
INSERT INTO `SYSTEM_DIC` VALUES ('57', '0', 'areaName', '区域', 'area', null, null, '1', 'área', 'área');
INSERT INTO `SYSTEM_DIC` VALUES ('58', '0', 'roadName', '路口', 'road', null, null, '1', 'estrada', 'estrada');
INSERT INTO `SYSTEM_DIC` VALUES ('59', '0', 'captureTime', '抓拍时间', 'capture time', null, null, '1', 'tempo de captura', 'tempo de captura');
INSERT INTO `SYSTEM_DIC` VALUES ('60', '0', 'violationType', '违章类型', 'violation type', null, null, '1', 'tipo de violação', 'tipo de violação');
INSERT INTO `SYSTEM_DIC` VALUES ('61', '0', 'speed', '速度', 'speed', null, null, '1', 'Rapidez', 'Rapidez');
INSERT INTO `SYSTEM_DIC` VALUES ('62', '0', 'note', '备注', 'note', null, null, '1', 'Nota', 'Nota');
INSERT INTO `SYSTEM_DIC` VALUES ('63', '0', 'operator', '操作者', 'operator', null, null, '1', 'Operador', 'Operador');
INSERT INTO `SYSTEM_DIC` VALUES ('64', '0', 'operateTime', '操作时间', 'operate time', null, null, '1', 'operar tempo', 'operar tempo');
INSERT INTO `SYSTEM_DIC` VALUES ('65', '0', 'status', '状态', 'status', null, null, '1', 'status', 'status');
INSERT INTO `SYSTEM_DIC` VALUES ('66', '0', 'violationConfirm', '确认违章', 'violation confirm', null, null, '1', 'violação confirmar', 'violação confirmar');
INSERT INTO `SYSTEM_DIC` VALUES ('67', '0', 'nopenalty', '不予处罚', 'no penalty', null, null, '1', 'Sem penalidade', 'Sem penalidade');
INSERT INTO `SYSTEM_DIC` VALUES ('68', '0', 'cancelPenalty', '取消处罚', 'cancel penalty', null, null, '1', 'cancelar a penalidade', 'cancelar a penalidade');
INSERT INTO `SYSTEM_DIC` VALUES ('69', '0', 'unconfirm', '未确认', 'unconfirm', null, null, '1', 'não confirmar', 'não confirmar');
INSERT INTO `SYSTEM_DIC` VALUES ('70', '0', 'violationlist', '违章列表', 'violation list', null, null, '1', 'lista de violações', 'lista de violações');
INSERT INTO `SYSTEM_DIC` VALUES ('71', '0', 'together', '同行车列表', 'together vehicle list', null, null, '1', 'junto lista de veículo', 'junto lista de veículo');
INSERT INTO `SYSTEM_DIC` VALUES ('72', '0', 'numberOfCoincidence', '重合次数', 'number of coincidence', null, null, '1', 'Número de coincidências', 'Número de coincidências');
INSERT INTO `SYSTEM_DIC` VALUES ('73', '0', 'unhandled', '未处置', 'unhandled', null, null, '1', 'Sem tratamento', 'Sem tratamento');
INSERT INTO `SYSTEM_DIC` VALUES ('74', '0', 'controlled', '已布控', 'controlled', null, null, '1', 'Controlado', 'Controlado');
INSERT INTO `SYSTEM_DIC` VALUES ('75', '0', 'manualTroubleshooting', '人工排除', 'manual troubleshooting', null, null, '1', 'Exclusão manual', 'Exclusão manual');

-- ----------------------------
-- Table structure for SYSTEM_VARIABLE
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_VARIABLE`;
CREATE TABLE `SYSTEM_VARIABLE` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `NAME` varchar(64) DEFAULT NULL COMMENT '变量名',
  `VALUE` int(11) DEFAULT NULL COMMENT '变量值 (-1永久）',
  `UNIT` char(16) DEFAULT NULL COMMENT '单位',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统变量表';

-- ----------------------------
-- Records of SYSTEM_VARIABLE
-- ----------------------------
INSERT INTO `SYSTEM_VARIABLE` VALUES ('0', 'Car record save time', '510', 'day');
INSERT INTO `SYSTEM_VARIABLE` VALUES ('1', 'Alarm information save time', '1051', 'day');

-- ----------------------------
-- Table structure for TERMINAL
-- ----------------------------
DROP TABLE IF EXISTS `TERMINAL`;
CREATE TABLE `TERMINAL` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `IP` char(16) DEFAULT NULL COMMENT '登陆IP地址',
  `PORT` int(11) DEFAULT NULL COMMENT '登陆端口',
  `USERNAME` char(32) DEFAULT NULL COMMENT '登陆用户名',
  `PASSWORD` char(32) DEFAULT NULL COMMENT '登陆密码',
  `DEVICE_NUM` int(11) DEFAULT NULL COMMENT '控制的设备数量',
  `ONLINE` tinyint(4) DEFAULT NULL COMMENT '是否在线',
  `ROAD_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '路口点位ID',
  `NAME` varchar(64) DEFAULT NULL COMMENT '主机名',
  `LATITUDE` double DEFAULT NULL COMMENT '经度',
  `LONGITUDE` double DEFAULT NULL COMMENT '纬度',
  `INSTALL_ADDRESS` varchar(512) DEFAULT NULL COMMENT '主机安装详细位置',
  `CREATE_TIME` datetime DEFAULT NULL,
  `DELETED` tinyint(4) DEFAULT NULL,
  `TYPE` int(11) DEFAULT '2',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_3` (`ROAD_CROSS_POINT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='终端主机';

-- ----------------------------
-- Records of TERMINAL
-- ----------------------------

-- ----------------------------
-- Table structure for TIME_INDEX_HOUR
-- ----------------------------
DROP TABLE IF EXISTS `TIME_INDEX_HOUR`;
CREATE TABLE `TIME_INDEX_HOUR` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `NOTE` varchar(64) DEFAULT NULL COMMENT '720条记录的中间表，用于统计一个月内小时查询'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of TIME_INDEX_HOUR
-- ----------------------------
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('9', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('10', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('11', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('12', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('13', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('14', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('15', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('16', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('17', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('18', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('19', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('20', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('21', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('22', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('23', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('24', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('25', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('26', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('27', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('28', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('29', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('30', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('31', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('32', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('33', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('34', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('35', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('36', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('37', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('38', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('39', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('40', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('41', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('42', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('43', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('44', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('45', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('46', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('47', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('48', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('49', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('50', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('51', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('52', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('53', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('54', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('55', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('56', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('57', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('58', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('59', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('60', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('61', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('62', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('63', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('64', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('65', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('66', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('67', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('68', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('69', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('70', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('71', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('72', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('73', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('74', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('75', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('76', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('77', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('78', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('79', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('80', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('81', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('82', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('83', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('84', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('85', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('86', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('87', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('88', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('89', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('90', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('91', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('92', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('93', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('94', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('95', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('96', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('97', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('98', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('99', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('100', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('101', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('102', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('103', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('104', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('105', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('106', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('107', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('108', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('109', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('110', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('111', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('112', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('113', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('114', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('115', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('116', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('117', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('118', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('119', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('120', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('121', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('122', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('123', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('124', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('125', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('126', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('127', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('128', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('129', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('130', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('131', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('132', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('133', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('134', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('135', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('136', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('137', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('138', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('139', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('140', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('141', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('142', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('143', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('144', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('145', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('146', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('147', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('148', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('149', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('150', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('151', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('152', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('153', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('154', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('155', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('156', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('157', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('158', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('159', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('160', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('161', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('162', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('163', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('164', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('165', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('166', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('167', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('168', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('169', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('170', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('171', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('172', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('173', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('174', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('175', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('176', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('177', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('178', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('179', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('180', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('181', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('182', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('183', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('184', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('185', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('186', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('187', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('188', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('189', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('190', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('191', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('192', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('193', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('194', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('195', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('196', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('197', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('198', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('199', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('200', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('201', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('202', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('203', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('204', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('205', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('206', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('207', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('208', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('209', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('210', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('211', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('212', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('213', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('214', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('215', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('216', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('217', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('218', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('219', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('220', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('221', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('222', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('223', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('224', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('225', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('226', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('227', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('228', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('229', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('230', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('231', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('232', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('233', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('234', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('235', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('236', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('237', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('238', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('239', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('240', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('241', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('242', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('243', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('244', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('245', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('246', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('247', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('248', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('249', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('250', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('251', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('252', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('253', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('254', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('255', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('256', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('257', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('258', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('259', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('260', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('261', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('262', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('263', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('264', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('265', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('266', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('267', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('268', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('269', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('270', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('271', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('272', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('273', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('274', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('275', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('276', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('277', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('278', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('279', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('280', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('281', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('282', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('283', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('284', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('285', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('286', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('287', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('288', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('289', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('290', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('291', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('292', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('293', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('294', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('295', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('296', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('297', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('298', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('299', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('300', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('301', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('302', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('303', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('304', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('305', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('306', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('307', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('308', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('309', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('310', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('311', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('312', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('313', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('314', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('315', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('316', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('317', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('318', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('319', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('320', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('321', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('322', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('323', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('324', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('325', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('326', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('327', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('328', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('329', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('330', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('331', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('332', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('333', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('334', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('335', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('336', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('337', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('338', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('339', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('340', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('341', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('342', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('343', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('344', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('345', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('346', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('347', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('348', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('349', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('350', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('351', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('352', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('353', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('354', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('355', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('356', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('357', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('358', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('359', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('360', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('361', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('362', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('363', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('364', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('365', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('366', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('367', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('368', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('369', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('370', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('371', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('372', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('373', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('374', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('375', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('376', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('377', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('378', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('379', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('380', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('381', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('382', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('383', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('384', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('385', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('386', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('387', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('388', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('389', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('390', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('391', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('392', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('393', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('394', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('395', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('396', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('397', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('398', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('399', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('400', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('401', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('402', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('403', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('404', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('405', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('406', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('407', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('408', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('409', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('410', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('411', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('412', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('413', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('414', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('415', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('416', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('417', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('418', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('419', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('420', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('421', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('422', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('423', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('424', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('425', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('426', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('427', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('428', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('429', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('430', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('431', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('432', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('433', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('434', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('435', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('436', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('437', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('438', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('439', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('440', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('441', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('442', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('443', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('444', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('445', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('446', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('447', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('448', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('449', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('450', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('451', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('452', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('453', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('454', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('455', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('456', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('457', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('458', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('459', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('460', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('461', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('462', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('463', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('464', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('465', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('466', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('467', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('468', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('469', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('470', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('471', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('472', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('473', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('474', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('475', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('476', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('477', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('478', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('479', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('480', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('481', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('482', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('483', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('484', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('485', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('486', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('487', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('488', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('489', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('490', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('491', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('492', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('493', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('494', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('495', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('496', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('497', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('498', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('499', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('500', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('501', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('502', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('503', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('504', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('505', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('506', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('507', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('508', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('509', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('510', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('511', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('512', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('513', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('514', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('515', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('516', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('517', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('518', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('519', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('520', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('521', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('522', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('523', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('524', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('525', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('526', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('527', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('528', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('529', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('530', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('531', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('532', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('533', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('534', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('535', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('536', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('537', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('538', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('539', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('540', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('541', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('542', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('543', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('544', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('545', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('546', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('547', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('548', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('549', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('550', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('551', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('552', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('553', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('554', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('555', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('556', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('557', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('558', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('559', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('560', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('561', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('562', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('563', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('564', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('565', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('566', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('567', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('568', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('569', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('570', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('571', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('572', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('573', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('574', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('575', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('576', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('577', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('578', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('579', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('580', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('581', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('582', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('583', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('584', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('585', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('586', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('587', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('588', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('589', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('590', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('591', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('592', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('593', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('594', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('595', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('596', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('597', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('598', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('599', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('600', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('601', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('602', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('603', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('604', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('605', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('606', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('607', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('608', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('609', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('610', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('611', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('612', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('613', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('614', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('615', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('616', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('617', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('618', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('619', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('620', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('621', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('622', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('623', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('624', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('625', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('626', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('627', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('628', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('629', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('630', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('631', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('632', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('633', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('634', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('635', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('636', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('637', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('638', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('639', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('640', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('641', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('642', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('643', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('644', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('645', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('646', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('647', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('648', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('649', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('650', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('651', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('652', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('653', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('654', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('655', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('656', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('657', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('658', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('659', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('660', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('661', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('662', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('663', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('664', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('665', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('666', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('667', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('668', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('669', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('670', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('671', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('672', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('673', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('674', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('675', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('676', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('677', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('678', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('679', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('680', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('681', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('682', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('683', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('684', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('685', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('686', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('687', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('688', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('689', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('690', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('691', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('692', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('693', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('694', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('695', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('696', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('697', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('698', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('699', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('700', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('701', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('702', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('703', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('704', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('705', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('706', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('707', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('708', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('709', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('710', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('711', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('712', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('713', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('714', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('715', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('716', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('717', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('718', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('719', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('720', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('721', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('722', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('723', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('724', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('725', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('726', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('727', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('728', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('729', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('730', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('731', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('732', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('733', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('734', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('735', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('736', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('737', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('738', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('739', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('740', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('741', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('742', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('743', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('744', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('745', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('746', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('747', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('748', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('749', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('750', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('751', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('752', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('753', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('754', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('755', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('756', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('757', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('758', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('759', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('760', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('761', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('762', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('763', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('764', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('765', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('766', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('767', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('768', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('769', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('770', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('771', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('772', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('773', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('774', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('775', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('776', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('777', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('778', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('779', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('780', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('781', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('782', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('783', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('784', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('785', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('786', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('787', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('788', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('789', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('790', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('791', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('792', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('793', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('794', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('795', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('796', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('797', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('798', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('799', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('800', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('801', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('802', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('803', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('804', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('805', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('806', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('807', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('808', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('809', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('810', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('811', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('812', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('813', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('814', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('815', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('816', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('817', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('818', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('819', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('820', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('821', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('822', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('823', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('824', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('825', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('826', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('827', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('828', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('829', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('830', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('831', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('832', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('833', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('834', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('835', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('836', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('837', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('838', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('839', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('840', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('841', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('842', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('843', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('844', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('845', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('846', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('847', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('848', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('849', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('850', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('851', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('852', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('853', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('854', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('855', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('856', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('857', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('858', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('859', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('860', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('861', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('862', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('863', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('864', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('865', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('866', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('867', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('868', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('869', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('870', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('871', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('872', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('873', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('874', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('875', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('876', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('877', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('878', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('879', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('880', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('881', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('882', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('883', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('884', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('885', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('886', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('887', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('888', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('889', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('890', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('891', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('892', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('893', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('894', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('895', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('896', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('897', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('898', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('899', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('900', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('901', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('902', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('903', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('904', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('905', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('906', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('907', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('908', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('909', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('910', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('911', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('912', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('913', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('914', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('915', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('916', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('917', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('918', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('919', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('920', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('921', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('922', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('923', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('924', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('925', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('926', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('927', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('928', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('929', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('930', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('931', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('932', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('933', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('934', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('935', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('936', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('937', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('938', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('939', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('940', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('941', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('942', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('943', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('944', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('945', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('946', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('947', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('948', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('949', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('950', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('951', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('952', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('953', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('954', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('955', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('956', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('957', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('958', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('959', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('960', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('961', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('962', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('963', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('964', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('965', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('966', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('967', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('968', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('969', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('970', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('971', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('972', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('973', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('974', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('975', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('976', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('977', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('978', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('979', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('980', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('981', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('982', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('983', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('984', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('985', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('986', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('987', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('988', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('989', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('990', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('991', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('992', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('993', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('994', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('995', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('996', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('997', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('998', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('999', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1000', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1001', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1002', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1003', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1004', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1005', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1006', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1007', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1008', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1009', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1010', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1011', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1012', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1013', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1014', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1015', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1016', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1017', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1018', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1019', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1020', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1021', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1022', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1023', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1024', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1025', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1026', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1027', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1028', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1029', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1030', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1031', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1032', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1033', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1034', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1035', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1036', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1037', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1038', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1039', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1040', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1041', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1042', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1043', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1044', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1045', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1046', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1047', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1048', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1049', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1050', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1051', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1052', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1053', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1054', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1055', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1056', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1057', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1058', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1059', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1060', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1061', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1062', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1063', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1064', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1065', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1066', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1067', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1068', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1069', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1070', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1071', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1072', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1073', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1074', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1075', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1076', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1077', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1078', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1079', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1080', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1081', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1082', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1083', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1084', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1085', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1086', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1087', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1088', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1089', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1090', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1091', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1092', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1093', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1094', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1095', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1096', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1097', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1098', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1099', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1100', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1101', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1102', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1103', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1104', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1105', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1106', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1107', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1108', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1109', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1110', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1111', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1112', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1113', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1114', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1115', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1116', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1117', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1118', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1119', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1120', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1121', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1122', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1123', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1124', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1125', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1126', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1127', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1128', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1129', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1130', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1131', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1132', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1133', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1134', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1135', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1136', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1137', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1138', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1139', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1140', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1141', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1142', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1143', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1144', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1145', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1146', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1147', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1148', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1149', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1150', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1151', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1152', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1153', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1154', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1155', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1156', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1157', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1158', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1159', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1160', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1161', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1162', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1163', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1164', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1165', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1166', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1167', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1168', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1169', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1170', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1171', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1172', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1173', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1174', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1175', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1176', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1177', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1178', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1179', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1180', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1181', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1182', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1183', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1184', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1185', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1186', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1187', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1188', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1189', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1190', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1191', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1192', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1193', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1194', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1195', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1196', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1197', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1198', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1199', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1200', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1201', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1202', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1203', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1204', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1205', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1206', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1207', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1208', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1209', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1210', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1211', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1212', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1213', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1214', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1215', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1216', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1217', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1218', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1219', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1220', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1221', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1222', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1223', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1224', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1225', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1226', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1227', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1228', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1229', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1230', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1231', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1232', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1233', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1234', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1235', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1236', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1237', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1238', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1239', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1240', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1241', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1242', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1243', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1244', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1245', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1246', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1247', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1248', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1249', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1250', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1251', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1252', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1253', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1254', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1255', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1256', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1257', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1258', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1259', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1260', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1261', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1262', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1263', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1264', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1265', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1266', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1267', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1268', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1269', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1270', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1271', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1272', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1273', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1274', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1275', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1276', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1277', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1278', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1279', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1280', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1281', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1282', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1283', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1284', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1285', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1286', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1287', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1288', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1289', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1290', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1291', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1292', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1293', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1294', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1295', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1296', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1297', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1298', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1299', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1300', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1301', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1302', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1303', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1304', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1305', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1306', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1307', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1308', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1309', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1310', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1311', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1312', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1313', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1314', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1315', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1316', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1317', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1318', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1319', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1320', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1321', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1322', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1323', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1324', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1325', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1326', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1327', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1328', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1329', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1330', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1331', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1332', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1333', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1334', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1335', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1336', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1337', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1338', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1339', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1340', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1341', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1342', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1343', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1344', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1345', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1346', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1347', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1348', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1349', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1350', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1351', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1352', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1353', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1354', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1355', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1356', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1357', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1358', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1359', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1360', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1361', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1362', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1363', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1364', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1365', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1366', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1367', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1368', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1369', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1370', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1371', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1372', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1373', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1374', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1375', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1376', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1377', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1378', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1379', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1380', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1381', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1382', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1383', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1384', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1385', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1386', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1387', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1388', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1389', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1390', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1391', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1392', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1393', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1394', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1395', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1396', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1397', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1398', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1399', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1400', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1401', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1402', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1403', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1404', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1405', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1406', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1407', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1408', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1409', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1410', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1411', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1412', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1413', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1414', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1415', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1416', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1417', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1418', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1419', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1420', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1421', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1422', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1423', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1424', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1425', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1426', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1427', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1428', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1429', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1430', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1431', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1432', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1433', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1434', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1435', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1436', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1437', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1438', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1439', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1440', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1441', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1442', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1443', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1444', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1445', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1446', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1447', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1448', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1449', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1450', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1451', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1452', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1453', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1454', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1455', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1456', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1457', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1458', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1459', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1460', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1461', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1462', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1463', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1464', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1465', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1466', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1467', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1468', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1469', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1470', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1471', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1472', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1473', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1474', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1475', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1476', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1477', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1478', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1479', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1480', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1481', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1482', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1483', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1484', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1485', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1486', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1487', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1488', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1489', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1490', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1491', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1492', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1493', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1494', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1495', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1496', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1497', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1498', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1499', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1500', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1501', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1502', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1503', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1504', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1505', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1506', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1507', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1508', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1509', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1510', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1511', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1512', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1513', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1514', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1515', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1516', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1517', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1518', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1519', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1520', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1521', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1522', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1523', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1524', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1525', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1526', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1527', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1528', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1529', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1530', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1531', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1532', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1533', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1534', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1535', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1536', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1537', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1538', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1539', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1540', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1541', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1542', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1543', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1544', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1545', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1546', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1547', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1548', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1549', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1550', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1551', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1552', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1553', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1554', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1555', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1556', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1557', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1558', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1559', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1560', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1561', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1562', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1563', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1564', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1565', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1566', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1567', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1568', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1569', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1570', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1571', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1572', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1573', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1574', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1575', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1576', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1577', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1578', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1579', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1580', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1581', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1582', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1583', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1584', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1585', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1586', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1587', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1588', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1589', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1590', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1591', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1592', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1593', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1594', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1595', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1596', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1597', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1598', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1599', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1600', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1601', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1602', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1603', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1604', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1605', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1606', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1607', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1608', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1609', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1610', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1611', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1612', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1613', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1614', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1615', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1616', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1617', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1618', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1619', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1620', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1621', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1622', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1623', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1624', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1625', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1626', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1627', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1628', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1629', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1630', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1631', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1632', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1633', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1634', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1635', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1636', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1637', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1638', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1639', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1640', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1641', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1642', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1643', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1644', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1645', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1646', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1647', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1648', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1649', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1650', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1651', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1652', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1653', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1654', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1655', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1656', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1657', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1658', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1659', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1660', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1661', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1662', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1663', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1664', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1665', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1666', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1667', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1668', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1669', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1670', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1671', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1672', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1673', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1674', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1675', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1676', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1677', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1678', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1679', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1680', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1681', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1682', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1683', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1684', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1685', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1686', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1687', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1688', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1689', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1690', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1691', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1692', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1693', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1694', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1695', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1696', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1697', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1698', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1699', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1700', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1701', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1702', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1703', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1704', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1705', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1706', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1707', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1708', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1709', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1710', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1711', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1712', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1713', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1714', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1715', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1716', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1717', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1718', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1719', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1720', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1721', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1722', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1723', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1724', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1725', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1726', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1727', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1728', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1729', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1730', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1731', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1732', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1733', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1734', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1735', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1736', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1737', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1738', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1739', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1740', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1741', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1742', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1743', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1744', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1745', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1746', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1747', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1748', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1749', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1750', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1751', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1752', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1753', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1754', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1755', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1756', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1757', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1758', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1759', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1760', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1761', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1762', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1763', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1764', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1765', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1766', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1767', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1768', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1769', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1770', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1771', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1772', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1773', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1774', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1775', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1776', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1777', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1778', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1779', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1780', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1781', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1782', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1783', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1784', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1785', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1786', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1787', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1788', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1789', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1790', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1791', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1792', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1793', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1794', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1795', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1796', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1797', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1798', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1799', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1800', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1801', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1802', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1803', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1804', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1805', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1806', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1807', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1808', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1809', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1810', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1811', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1812', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1813', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1814', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1815', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1816', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1817', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1818', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1819', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1820', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1821', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1822', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1823', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1824', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1825', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1826', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1827', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1828', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1829', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1830', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1831', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1832', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1833', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1834', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1835', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1836', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1837', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1838', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1839', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1840', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1841', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1842', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1843', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1844', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1845', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1846', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1847', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1848', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1849', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1850', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1851', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1852', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1853', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1854', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1855', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1856', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1857', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1858', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1859', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1860', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1861', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1862', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1863', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1864', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1865', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1866', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1867', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1868', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1869', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1870', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1871', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1872', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1873', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1874', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1875', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1876', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1877', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1878', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1879', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1880', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1881', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1882', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1883', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1884', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1885', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1886', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1887', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1888', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1889', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1890', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1891', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1892', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1893', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1894', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1895', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1896', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1897', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1898', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1899', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1900', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1901', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1902', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1903', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1904', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1905', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1906', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1907', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1908', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1909', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1910', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1911', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1912', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1913', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1914', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1915', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1916', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1917', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1918', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1919', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1920', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1921', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1922', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1923', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1924', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1925', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1926', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1927', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1928', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1929', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1930', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1931', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1932', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1933', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1934', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1935', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1936', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1937', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1938', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1939', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1940', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1941', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1942', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1943', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1944', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1945', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1946', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1947', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1948', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1949', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1950', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1951', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1952', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1953', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1954', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1955', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1956', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1957', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1958', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1959', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1960', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1961', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1962', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1963', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1964', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1965', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1966', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1967', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1968', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1969', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1970', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1971', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1972', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1973', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1974', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1975', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1976', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1977', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1978', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1979', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1980', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1981', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1982', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1983', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1984', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1985', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1986', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1987', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1988', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1989', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1990', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1991', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1992', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1993', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1994', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1995', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1996', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1997', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1998', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('1999', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2000', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2001', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2002', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2003', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2004', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2005', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2006', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2007', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2008', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2009', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2010', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2011', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2012', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2013', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2014', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2015', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2016', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2017', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2018', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2019', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2020', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2021', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2022', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2023', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2024', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2025', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2026', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2027', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2028', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2029', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2030', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2031', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2032', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2033', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2034', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2035', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2036', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2037', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2038', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2039', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2040', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2041', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2042', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2043', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2044', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2045', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2046', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2047', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2048', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2049', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2050', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2051', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2052', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2053', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2054', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2055', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2056', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2057', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2058', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2059', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2060', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2061', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2062', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2063', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2064', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2065', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2066', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2067', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2068', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2069', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2070', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2071', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2072', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2073', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2074', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2075', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2076', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2077', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2078', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2079', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2080', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2081', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2082', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2083', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2084', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2085', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2086', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2087', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2088', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2089', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2090', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2091', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2092', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2093', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2094', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2095', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2096', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2097', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2098', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2099', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2100', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2101', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2102', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2103', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2104', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2105', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2106', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2107', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2108', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2109', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2110', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2111', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2112', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2113', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2114', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2115', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2116', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2117', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2118', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2119', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2120', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2121', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2122', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2123', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2124', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2125', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2126', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2127', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2128', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2129', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2130', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2131', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2132', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2133', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2134', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2135', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2136', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2137', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2138', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2139', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2140', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2141', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2142', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2143', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2144', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2145', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2146', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2147', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2148', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2149', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2150', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2151', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2152', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2153', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2154', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2155', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2156', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2157', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2158', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2159', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2160', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2161', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2162', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2163', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2164', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2165', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2166', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2167', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2168', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2169', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2170', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2171', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2172', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2173', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2174', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2175', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2176', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2177', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2178', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2179', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2180', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2181', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2182', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2183', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2184', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2185', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2186', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2187', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2188', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2189', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2190', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2191', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2192', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2193', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2194', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2195', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2196', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2197', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2198', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2199', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2200', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2201', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2202', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2203', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2204', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2205', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2206', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2207', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2208', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2209', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2210', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2211', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2212', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2213', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2214', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2215', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2216', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2217', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2218', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2219', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2220', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2221', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2222', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2223', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2224', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2225', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2226', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2227', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2228', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2229', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2230', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2231', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2232', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2233', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2234', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2235', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2236', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2237', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2238', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2239', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2240', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2241', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2242', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2243', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2244', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2245', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2246', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2247', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2248', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2249', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2250', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2251', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2252', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2253', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2254', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2255', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2256', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2257', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2258', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2259', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2260', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2261', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2262', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2263', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2264', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2265', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2266', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2267', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2268', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2269', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2270', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2271', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2272', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2273', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2274', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2275', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2276', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2277', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2278', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2279', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2280', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2281', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2282', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2283', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2284', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2285', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2286', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2287', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2288', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2289', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2290', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2291', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2292', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2293', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2294', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2295', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2296', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2297', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2298', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2299', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2300', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2301', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2302', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2303', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2304', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2305', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2306', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2307', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2308', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2309', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2310', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2311', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2312', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2313', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2314', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2315', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2316', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2317', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2318', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2319', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2320', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2321', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2322', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2323', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2324', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2325', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2326', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2327', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2328', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2329', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2330', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2331', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2332', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2333', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2334', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2335', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2336', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2337', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2338', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2339', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2340', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2341', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2342', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2343', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2344', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2345', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2346', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2347', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2348', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2349', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2350', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2351', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2352', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2353', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2354', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2355', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2356', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2357', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2358', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2359', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2360', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2361', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2362', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2363', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2364', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2365', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2366', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2367', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2368', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2369', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2370', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2371', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2372', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2373', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2374', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2375', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2376', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2377', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2378', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2379', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2380', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2381', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2382', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2383', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2384', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2385', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2386', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2387', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2388', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2389', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2390', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2391', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2392', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2393', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2394', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2395', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2396', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2397', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2398', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2399', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2400', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2401', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2402', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2403', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2404', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2405', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2406', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2407', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2408', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2409', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2410', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2411', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2412', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2413', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2414', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2415', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2416', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2417', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2418', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2419', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2420', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2421', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2422', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2423', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2424', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2425', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2426', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2427', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2428', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2429', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2430', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2431', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2432', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2433', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2434', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2435', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2436', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2437', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2438', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2439', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2440', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2441', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2442', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2443', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2444', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2445', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2446', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2447', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2448', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2449', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2450', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2451', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2452', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2453', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2454', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2455', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2456', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2457', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2458', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2459', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2460', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2461', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2462', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2463', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2464', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2465', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2466', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2467', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2468', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2469', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2470', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2471', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2472', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2473', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2474', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2475', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2476', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2477', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2478', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2479', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2480', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2481', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2482', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2483', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2484', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2485', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2486', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2487', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2488', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2489', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2490', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2491', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2492', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2493', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2494', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2495', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2496', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2497', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2498', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2499', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2500', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2501', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2502', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2503', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2504', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2505', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2506', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2507', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2508', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2509', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2510', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2511', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2512', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2513', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2514', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2515', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2516', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2517', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2518', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2519', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2520', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2521', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2522', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2523', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2524', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2525', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2526', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2527', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2528', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2529', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2530', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2531', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2532', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2533', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2534', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2535', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2536', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2537', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2538', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2539', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2540', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2541', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2542', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2543', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2544', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2545', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2546', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2547', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2548', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2549', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2550', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2551', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2552', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2553', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2554', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2555', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2556', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2557', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2558', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2559', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2560', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2561', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2562', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2563', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2564', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2565', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2566', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2567', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2568', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2569', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2570', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2571', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2572', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2573', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2574', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2575', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2576', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2577', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2578', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2579', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2580', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2581', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2582', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2583', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2584', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2585', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2586', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2587', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2588', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2589', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2590', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2591', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2592', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2593', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2594', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2595', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2596', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2597', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2598', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2599', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2600', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2601', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2602', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2603', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2604', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2605', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2606', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2607', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2608', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2609', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2610', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2611', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2612', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2613', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2614', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2615', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2616', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2617', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2618', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2619', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2620', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2621', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2622', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2623', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2624', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2625', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2626', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2627', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2628', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2629', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2630', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2631', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2632', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2633', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2634', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2635', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2636', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2637', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2638', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2639', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2640', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2641', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2642', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2643', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2644', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2645', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2646', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2647', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2648', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2649', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2650', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2651', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2652', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2653', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2654', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2655', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2656', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2657', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2658', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2659', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2660', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2661', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2662', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2663', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2664', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2665', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2666', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2667', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2668', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2669', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2670', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2671', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2672', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2673', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2674', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2675', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2676', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2677', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2678', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2679', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2680', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2681', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2682', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2683', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2684', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2685', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2686', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2687', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2688', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2689', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2690', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2691', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2692', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2693', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2694', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2695', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2696', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2697', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2698', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2699', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2700', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2701', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2702', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2703', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2704', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2705', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2706', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2707', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2708', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2709', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2710', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2711', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2712', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2713', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2714', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2715', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2716', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2717', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2718', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2719', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2720', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2721', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2722', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2723', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2724', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2725', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2726', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2727', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2728', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2729', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2730', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2731', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2732', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2733', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2734', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2735', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2736', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2737', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2738', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2739', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2740', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2741', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2742', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2743', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2744', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2745', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2746', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2747', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2748', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2749', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2750', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2751', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2752', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2753', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2754', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2755', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2756', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2757', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2758', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2759', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2760', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2761', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2762', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2763', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2764', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2765', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2766', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2767', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2768', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2769', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2770', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2771', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2772', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2773', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2774', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2775', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2776', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2777', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2778', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2779', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2780', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2781', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2782', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2783', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2784', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2785', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2786', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2787', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2788', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2789', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2790', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2791', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2792', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2793', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2794', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2795', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2796', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2797', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2798', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2799', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2800', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2801', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2802', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2803', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2804', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2805', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2806', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2807', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2808', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2809', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2810', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2811', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2812', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2813', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2814', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2815', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2816', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2817', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2818', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2819', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2820', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2821', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2822', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2823', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2824', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2825', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2826', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2827', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2828', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2829', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2830', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2831', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2832', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2833', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2834', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2835', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2836', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2837', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2838', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2839', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2840', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2841', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2842', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2843', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2844', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2845', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2846', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2847', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2848', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2849', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2850', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2851', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2852', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2853', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2854', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2855', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2856', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2857', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2858', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2859', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2860', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2861', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2862', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2863', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2864', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2865', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2866', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2867', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2868', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2869', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2870', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2871', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2872', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2873', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2874', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2875', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2876', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2877', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2878', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2879', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2880', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2881', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2882', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2883', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2884', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2885', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2886', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2887', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2888', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2889', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2890', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2891', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2892', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2893', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2894', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2895', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2896', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2897', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2898', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2899', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2900', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2901', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2902', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2903', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2904', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2905', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2906', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2907', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2908', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2909', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2910', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2911', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2912', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2913', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2914', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2915', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2916', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2917', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2918', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2919', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2920', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2921', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2922', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2923', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2924', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2925', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2926', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2927', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2928', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2929', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2930', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2931', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2932', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2933', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2934', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2935', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2936', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2937', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2938', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2939', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2940', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2941', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2942', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2943', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2944', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2945', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2946', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2947', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2948', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2949', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2950', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2951', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2952', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2953', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2954', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2955', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2956', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2957', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2958', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2959', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2960', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2961', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2962', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2963', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2964', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2965', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2966', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2967', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2968', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2969', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2970', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2971', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2972', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2973', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2974', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2975', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2976', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2977', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2978', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2979', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2980', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2981', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2982', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2983', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2984', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2985', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2986', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2987', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2988', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2989', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2990', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2991', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2992', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2993', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2994', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2995', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2996', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2997', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2998', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('2999', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3000', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3001', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3002', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3003', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3004', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3005', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3006', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3007', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3008', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3009', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3010', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3011', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3012', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3013', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3014', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3015', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3016', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3017', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3018', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3019', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3020', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3021', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3022', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3023', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3024', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3025', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3026', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3027', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3028', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3029', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3030', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3031', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3032', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3033', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3034', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3035', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3036', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3037', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3038', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3039', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3040', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3041', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3042', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3043', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3044', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3045', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3046', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3047', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3048', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3049', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3050', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3051', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3052', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3053', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3054', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3055', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3056', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3057', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3058', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3059', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3060', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3061', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3062', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3063', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3064', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3065', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3066', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3067', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3068', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3069', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3070', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3071', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3072', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3073', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3074', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3075', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3076', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3077', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3078', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3079', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3080', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3081', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3082', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3083', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3084', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3085', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3086', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3087', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3088', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3089', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3090', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3091', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3092', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3093', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3094', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3095', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3096', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3097', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3098', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3099', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3100', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3101', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3102', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3103', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3104', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3105', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3106', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3107', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3108', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3109', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3110', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3111', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3112', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3113', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3114', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3115', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3116', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3117', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3118', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3119', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3120', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3121', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3122', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3123', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3124', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3125', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3126', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3127', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3128', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3129', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3130', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3131', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3132', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3133', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3134', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3135', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3136', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3137', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3138', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3139', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3140', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3141', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3142', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3143', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3144', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3145', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3146', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3147', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3148', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3149', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3150', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3151', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3152', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3153', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3154', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3155', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3156', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3157', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3158', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3159', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3160', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3161', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3162', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3163', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3164', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3165', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3166', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3167', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3168', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3169', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3170', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3171', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3172', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3173', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3174', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3175', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3176', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3177', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3178', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3179', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3180', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3181', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3182', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3183', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3184', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3185', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3186', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3187', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3188', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3189', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3190', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3191', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3192', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3193', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3194', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3195', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3196', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3197', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3198', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3199', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3200', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3201', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3202', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3203', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3204', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3205', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3206', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3207', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3208', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3209', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3210', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3211', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3212', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3213', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3214', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3215', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3216', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3217', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3218', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3219', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3220', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3221', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3222', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3223', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3224', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3225', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3226', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3227', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3228', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3229', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3230', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3231', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3232', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3233', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3234', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3235', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3236', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3237', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3238', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3239', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3240', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3241', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3242', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3243', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3244', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3245', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3246', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3247', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3248', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3249', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3250', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3251', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3252', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3253', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3254', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3255', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3256', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3257', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3258', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3259', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3260', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3261', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3262', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3263', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3264', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3265', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3266', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3267', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3268', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3269', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3270', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3271', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3272', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3273', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3274', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3275', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3276', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3277', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3278', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3279', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3280', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3281', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3282', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3283', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3284', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3285', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3286', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3287', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3288', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3289', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3290', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3291', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3292', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3293', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3294', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3295', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3296', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3297', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3298', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3299', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3300', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3301', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3302', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3303', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3304', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3305', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3306', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3307', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3308', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3309', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3310', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3311', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3312', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3313', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3314', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3315', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3316', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3317', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3318', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3319', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3320', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3321', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3322', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3323', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3324', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3325', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3326', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3327', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3328', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3329', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3330', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3331', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3332', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3333', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3334', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3335', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3336', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3337', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3338', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3339', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3340', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3341', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3342', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3343', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3344', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3345', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3346', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3347', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3348', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3349', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3350', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3351', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3352', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3353', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3354', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3355', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3356', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3357', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3358', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3359', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3360', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3361', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3362', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3363', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3364', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3365', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3366', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3367', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3368', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3369', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3370', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3371', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3372', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3373', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3374', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3375', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3376', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3377', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3378', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3379', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3380', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3381', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3382', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3383', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3384', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3385', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3386', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3387', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3388', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3389', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3390', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3391', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3392', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3393', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3394', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3395', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3396', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3397', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3398', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3399', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3400', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3401', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3402', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3403', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3404', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3405', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3406', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3407', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3408', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3409', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3410', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3411', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3412', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3413', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3414', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3415', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3416', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3417', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3418', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3419', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3420', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3421', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3422', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3423', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3424', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3425', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3426', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3427', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3428', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3429', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3430', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3431', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3432', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3433', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3434', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3435', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3436', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3437', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3438', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3439', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3440', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3441', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3442', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3443', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3444', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3445', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3446', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3447', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3448', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3449', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3450', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3451', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3452', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3453', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3454', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3455', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3456', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3457', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3458', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3459', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3460', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3461', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3462', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3463', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3464', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3465', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3466', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3467', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3468', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3469', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3470', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3471', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3472', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3473', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3474', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3475', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3476', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3477', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3478', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3479', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3480', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3481', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3482', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3483', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3484', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3485', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3486', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3487', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3488', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3489', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3490', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3491', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3492', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3493', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3494', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3495', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3496', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3497', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3498', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3499', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3500', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3501', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3502', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3503', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3504', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3505', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3506', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3507', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3508', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3509', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3510', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3511', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3512', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3513', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3514', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3515', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3516', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3517', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3518', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3519', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3520', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3521', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3522', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3523', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3524', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3525', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3526', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3527', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3528', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3529', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3530', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3531', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3532', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3533', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3534', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3535', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3536', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3537', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3538', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3539', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3540', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3541', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3542', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3543', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3544', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3545', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3546', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3547', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3548', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3549', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3550', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3551', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3552', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3553', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3554', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3555', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3556', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3557', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3558', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3559', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3560', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3561', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3562', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3563', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3564', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3565', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3566', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3567', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3568', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3569', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3570', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3571', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3572', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3573', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3574', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3575', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3576', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3577', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3578', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3579', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3580', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3581', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3582', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3583', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3584', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3585', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3586', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3587', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3588', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3589', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3590', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3591', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3592', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3593', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3594', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3595', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3596', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3597', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3598', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3599', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3600', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3601', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3602', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3603', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3604', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3605', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3606', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3607', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3608', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3609', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3610', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3611', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3612', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3613', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3614', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3615', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3616', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3617', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3618', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3619', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3620', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3621', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3622', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3623', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3624', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3625', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3626', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3627', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3628', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3629', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3630', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3631', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3632', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3633', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3634', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3635', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3636', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3637', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3638', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3639', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3640', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3641', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3642', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3643', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3644', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3645', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3646', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3647', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3648', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3649', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3650', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3651', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3652', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3653', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3654', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3655', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3656', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3657', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3658', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3659', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3660', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3661', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3662', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3663', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3664', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3665', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3666', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3667', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3668', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3669', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3670', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3671', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3672', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3673', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3674', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3675', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3676', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3677', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3678', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3679', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3680', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3681', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3682', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3683', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3684', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3685', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3686', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3687', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3688', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3689', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3690', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3691', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3692', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3693', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3694', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3695', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3696', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3697', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3698', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3699', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3700', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3701', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3702', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3703', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3704', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3705', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3706', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3707', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3708', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3709', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3710', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3711', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3712', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3713', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3714', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3715', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3716', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3717', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3718', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3719', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3720', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3721', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3722', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3723', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3724', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3725', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3726', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3727', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3728', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3729', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3730', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3731', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3732', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3733', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3734', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3735', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3736', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3737', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3738', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3739', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3740', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3741', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3742', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3743', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3744', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3745', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3746', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3747', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3748', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3749', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3750', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3751', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3752', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3753', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3754', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3755', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3756', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3757', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3758', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3759', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3760', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3761', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3762', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3763', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3764', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3765', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3766', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3767', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3768', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3769', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3770', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3771', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3772', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3773', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3774', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3775', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3776', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3777', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3778', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3779', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3780', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3781', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3782', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3783', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3784', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3785', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3786', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3787', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3788', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3789', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3790', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3791', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3792', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3793', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3794', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3795', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3796', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3797', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3798', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3799', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3800', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3801', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3802', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3803', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3804', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3805', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3806', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3807', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3808', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3809', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3810', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3811', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3812', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3813', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3814', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3815', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3816', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3817', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3818', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3819', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3820', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3821', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3822', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3823', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3824', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3825', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3826', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3827', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3828', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3829', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3830', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3831', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3832', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3833', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3834', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3835', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3836', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3837', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3838', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3839', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3840', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3841', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3842', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3843', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3844', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3845', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3846', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3847', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3848', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3849', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3850', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3851', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3852', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3853', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3854', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3855', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3856', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3857', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3858', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3859', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3860', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3861', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3862', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3863', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3864', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3865', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3866', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3867', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3868', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3869', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3870', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3871', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3872', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3873', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3874', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3875', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3876', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3877', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3878', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3879', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3880', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3881', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3882', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3883', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3884', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3885', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3886', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3887', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3888', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3889', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3890', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3891', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3892', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3893', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3894', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3895', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3896', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3897', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3898', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3899', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3900', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3901', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3902', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3903', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3904', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3905', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3906', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3907', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3908', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3909', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3910', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3911', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3912', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3913', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3914', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3915', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3916', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3917', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3918', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3919', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3920', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3921', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3922', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3923', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3924', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3925', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3926', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3927', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3928', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3929', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3930', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3931', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3932', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3933', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3934', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3935', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3936', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3937', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3938', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3939', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3940', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3941', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3942', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3943', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3944', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3945', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3946', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3947', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3948', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3949', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3950', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3951', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3952', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3953', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3954', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3955', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3956', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3957', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3958', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3959', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3960', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3961', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3962', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3963', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3964', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3965', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3966', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3967', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3968', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3969', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3970', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3971', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3972', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3973', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3974', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3975', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3976', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3977', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3978', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3979', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3980', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3981', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3982', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3983', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3984', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3985', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3986', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3987', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3988', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3989', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3990', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3991', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3992', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3993', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3994', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3995', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3996', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3997', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3998', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('3999', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4000', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4001', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4002', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4003', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4004', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4005', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4006', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4007', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4008', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4009', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4010', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4011', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4012', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4013', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4014', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4015', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4016', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4017', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4018', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4019', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4020', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4021', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4022', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4023', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4024', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4025', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4026', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4027', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4028', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4029', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4030', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4031', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4032', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4033', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4034', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4035', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4036', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4037', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4038', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4039', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4040', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4041', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4042', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4043', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4044', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4045', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4046', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4047', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4048', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4049', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4050', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4051', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4052', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4053', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4054', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4055', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4056', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4057', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4058', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4059', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4060', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4061', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4062', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4063', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4064', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4065', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4066', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4067', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4068', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4069', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4070', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4071', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4072', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4073', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4074', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4075', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4076', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4077', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4078', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4079', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4080', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4081', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4082', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4083', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4084', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4085', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4086', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4087', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4088', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4089', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4090', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4091', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4092', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4093', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4094', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4095', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4096', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4097', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4098', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4099', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4100', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4101', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4102', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4103', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4104', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4105', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4106', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4107', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4108', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4109', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4110', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4111', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4112', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4113', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4114', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4115', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4116', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4117', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4118', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4119', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4120', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4121', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4122', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4123', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4124', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4125', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4126', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4127', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4128', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4129', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4130', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4131', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4132', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4133', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4134', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4135', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4136', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4137', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4138', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4139', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4140', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4141', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4142', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4143', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4144', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4145', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4146', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4147', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4148', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4149', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4150', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4151', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4152', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4153', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4154', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4155', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4156', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4157', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4158', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4159', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4160', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4161', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4162', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4163', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4164', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4165', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4166', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4167', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4168', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4169', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4170', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4171', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4172', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4173', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4174', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4175', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4176', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4177', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4178', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4179', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4180', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4181', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4182', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4183', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4184', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4185', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4186', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4187', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4188', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4189', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4190', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4191', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4192', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4193', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4194', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4195', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4196', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4197', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4198', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4199', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4200', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4201', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4202', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4203', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4204', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4205', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4206', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4207', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4208', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4209', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4210', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4211', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4212', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4213', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4214', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4215', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4216', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4217', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4218', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4219', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4220', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4221', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4222', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4223', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4224', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4225', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4226', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4227', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4228', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4229', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4230', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4231', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4232', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4233', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4234', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4235', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4236', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4237', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4238', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4239', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4240', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4241', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4242', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4243', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4244', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4245', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4246', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4247', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4248', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4249', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4250', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4251', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4252', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4253', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4254', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4255', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4256', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4257', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4258', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4259', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4260', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4261', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4262', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4263', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4264', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4265', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4266', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4267', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4268', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4269', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4270', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4271', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4272', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4273', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4274', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4275', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4276', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4277', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4278', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4279', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4280', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4281', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4282', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4283', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4284', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4285', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4286', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4287', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4288', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4289', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4290', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4291', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4292', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4293', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4294', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4295', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4296', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4297', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4298', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4299', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4300', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4301', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4302', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4303', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4304', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4305', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4306', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4307', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4308', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4309', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4310', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4311', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4312', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4313', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4314', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4315', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4316', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4317', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4318', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4319', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4320', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4321', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4322', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4323', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4324', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4325', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4326', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4327', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4328', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4329', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4330', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4331', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4332', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4333', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4334', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4335', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4336', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4337', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4338', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4339', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4340', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4341', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4342', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4343', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4344', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4345', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4346', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4347', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4348', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4349', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4350', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4351', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4352', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4353', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4354', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4355', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4356', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4357', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4358', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4359', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4360', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4361', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4362', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4363', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4364', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4365', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4366', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4367', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4368', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4369', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4370', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4371', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4372', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4373', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4374', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4375', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4376', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4377', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4378', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4379', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4380', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4381', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4382', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4383', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4384', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4385', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4386', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4387', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4388', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4389', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4390', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4391', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4392', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4393', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4394', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4395', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4396', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4397', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4398', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4399', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4400', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4401', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4402', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4403', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4404', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4405', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4406', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4407', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4408', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4409', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4410', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4411', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4412', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4413', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4414', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4415', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4416', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4417', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4418', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4419', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4420', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4421', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4422', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4423', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4424', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4425', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4426', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4427', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4428', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4429', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4430', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4431', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4432', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4433', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4434', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4435', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4436', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4437', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4438', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4439', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4440', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4441', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4442', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4443', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4444', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4445', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4446', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4447', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4448', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4449', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4450', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4451', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4452', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4453', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4454', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4455', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4456', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4457', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4458', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4459', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4460', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4461', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4462', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4463', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4464', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4465', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4466', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4467', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4468', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4469', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4470', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4471', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4472', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4473', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4474', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4475', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4476', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4477', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4478', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4479', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4480', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4481', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4482', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4483', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4484', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4485', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4486', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4487', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4488', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4489', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4490', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4491', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4492', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4493', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4494', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4495', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4496', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4497', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4498', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4499', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4500', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4501', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4502', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4503', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4504', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4505', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4506', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4507', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4508', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4509', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4510', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4511', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4512', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4513', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4514', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4515', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4516', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4517', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4518', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4519', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4520', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4521', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4522', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4523', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4524', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4525', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4526', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4527', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4528', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4529', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4530', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4531', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4532', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4533', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4534', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4535', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4536', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4537', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4538', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4539', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4540', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4541', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4542', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4543', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4544', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4545', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4546', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4547', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4548', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4549', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4550', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4551', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4552', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4553', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4554', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4555', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4556', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4557', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4558', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4559', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4560', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4561', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4562', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4563', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4564', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4565', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4566', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4567', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4568', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4569', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4570', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4571', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4572', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4573', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4574', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4575', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4576', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4577', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4578', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4579', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4580', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4581', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4582', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4583', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4584', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4585', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4586', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4587', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4588', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4589', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4590', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4591', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4592', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4593', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4594', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4595', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4596', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4597', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4598', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4599', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4600', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4601', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4602', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4603', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4604', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4605', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4606', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4607', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4608', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4609', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4610', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4611', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4612', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4613', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4614', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4615', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4616', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4617', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4618', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4619', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4620', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4621', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4622', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4623', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4624', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4625', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4626', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4627', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4628', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4629', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4630', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4631', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4632', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4633', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4634', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4635', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4636', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4637', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4638', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4639', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4640', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4641', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4642', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4643', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4644', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4645', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4646', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4647', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4648', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4649', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4650', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4651', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4652', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4653', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4654', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4655', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4656', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4657', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4658', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4659', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4660', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4661', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4662', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4663', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4664', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4665', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4666', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4667', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4668', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4669', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4670', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4671', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4672', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4673', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4674', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4675', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4676', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4677', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4678', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4679', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4680', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4681', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4682', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4683', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4684', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4685', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4686', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4687', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4688', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4689', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4690', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4691', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4692', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4693', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4694', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4695', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4696', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4697', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4698', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4699', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4700', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4701', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4702', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4703', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4704', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4705', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4706', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4707', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4708', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4709', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4710', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4711', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4712', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4713', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4714', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4715', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4716', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4717', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4718', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4719', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4720', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4721', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4722', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4723', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4724', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4725', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4726', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4727', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4728', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4729', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4730', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4731', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4732', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4733', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4734', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4735', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4736', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4737', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4738', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4739', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4740', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4741', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4742', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4743', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4744', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4745', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4746', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4747', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4748', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4749', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4750', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4751', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4752', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4753', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4754', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4755', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4756', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4757', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4758', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4759', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4760', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4761', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4762', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4763', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4764', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4765', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4766', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4767', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4768', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4769', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4770', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4771', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4772', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4773', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4774', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4775', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4776', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4777', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4778', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4779', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4780', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4781', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4782', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4783', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4784', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4785', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4786', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4787', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4788', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4789', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4790', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4791', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4792', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4793', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4794', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4795', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4796', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4797', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4798', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4799', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4800', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4801', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4802', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4803', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4804', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4805', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4806', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4807', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4808', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4809', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4810', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4811', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4812', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4813', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4814', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4815', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4816', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4817', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4818', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4819', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4820', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4821', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4822', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4823', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4824', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4825', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4826', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4827', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4828', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4829', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4830', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4831', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4832', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4833', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4834', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4835', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4836', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4837', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4838', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4839', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4840', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4841', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4842', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4843', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4844', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4845', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4846', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4847', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4848', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4849', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4850', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4851', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4852', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4853', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4854', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4855', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4856', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4857', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4858', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4859', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4860', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4861', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4862', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4863', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4864', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4865', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4866', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4867', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4868', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4869', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4870', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4871', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4872', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4873', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4874', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4875', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4876', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4877', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4878', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4879', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4880', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4881', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4882', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4883', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4884', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4885', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4886', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4887', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4888', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4889', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4890', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4891', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4892', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4893', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4894', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4895', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4896', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4897', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4898', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4899', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4900', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4901', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4902', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4903', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4904', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4905', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4906', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4907', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4908', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4909', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4910', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4911', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4912', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4913', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4914', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4915', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4916', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4917', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4918', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4919', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4920', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4921', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4922', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4923', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4924', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4925', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4926', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4927', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4928', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4929', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4930', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4931', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4932', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4933', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4934', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4935', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4936', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4937', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4938', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4939', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4940', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4941', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4942', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4943', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4944', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4945', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4946', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4947', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4948', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4949', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4950', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4951', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4952', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4953', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4954', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4955', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4956', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4957', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4958', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4959', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4960', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4961', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4962', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4963', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4964', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4965', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4966', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4967', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4968', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4969', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4970', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4971', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4972', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4973', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4974', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4975', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4976', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4977', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4978', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4979', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4980', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4981', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4982', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4983', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4984', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4985', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4986', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4987', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4988', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4989', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4990', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4991', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4992', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4993', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4994', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4995', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4996', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4997', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4998', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('4999', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5000', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5001', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5002', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5003', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5004', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5005', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5006', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5007', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5008', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5009', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5010', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5011', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5012', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5013', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5014', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5015', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5016', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5017', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5018', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5019', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5020', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5021', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5022', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5023', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5024', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5025', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5026', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5027', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5028', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5029', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5030', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5031', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5032', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5033', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5034', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5035', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5036', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5037', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5038', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5039', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5040', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5041', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5042', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5043', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5044', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5045', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5046', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5047', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5048', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5049', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5050', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5051', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5052', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5053', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5054', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5055', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5056', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5057', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5058', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5059', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5060', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5061', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5062', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5063', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5064', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5065', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5066', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5067', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5068', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5069', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5070', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5071', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5072', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5073', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5074', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5075', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5076', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5077', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5078', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5079', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5080', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5081', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5082', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5083', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5084', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5085', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5086', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5087', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5088', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5089', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5090', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5091', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5092', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5093', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5094', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5095', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5096', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5097', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5098', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5099', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5100', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5101', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5102', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5103', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5104', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5105', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5106', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5107', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5108', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5109', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5110', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5111', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5112', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5113', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5114', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5115', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5116', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5117', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5118', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5119', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5120', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5121', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5122', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5123', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5124', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5125', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5126', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5127', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5128', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5129', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5130', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5131', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5132', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5133', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5134', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5135', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5136', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5137', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5138', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5139', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5140', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5141', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5142', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5143', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5144', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5145', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5146', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5147', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5148', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5149', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5150', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5151', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5152', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5153', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5154', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5155', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5156', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5157', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5158', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5159', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5160', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5161', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5162', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5163', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5164', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5165', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5166', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5167', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5168', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5169', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5170', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5171', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5172', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5173', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5174', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5175', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5176', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5177', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5178', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5179', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5180', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5181', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5182', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5183', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5184', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5185', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5186', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5187', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5188', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5189', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5190', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5191', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5192', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5193', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5194', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5195', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5196', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5197', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5198', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5199', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5200', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5201', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5202', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5203', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5204', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5205', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5206', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5207', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5208', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5209', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5210', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5211', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5212', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5213', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5214', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5215', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5216', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5217', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5218', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5219', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5220', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5221', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5222', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5223', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5224', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5225', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5226', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5227', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5228', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5229', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5230', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5231', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5232', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5233', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5234', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5235', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5236', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5237', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5238', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5239', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5240', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5241', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5242', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5243', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5244', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5245', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5246', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5247', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5248', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5249', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5250', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5251', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5252', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5253', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5254', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5255', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5256', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5257', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5258', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5259', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5260', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5261', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5262', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5263', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5264', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5265', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5266', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5267', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5268', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5269', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5270', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5271', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5272', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5273', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5274', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5275', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5276', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5277', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5278', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5279', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5280', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5281', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5282', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5283', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5284', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5285', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5286', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5287', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5288', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5289', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5290', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5291', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5292', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5293', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5294', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5295', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5296', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5297', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5298', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5299', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5300', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5301', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5302', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5303', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5304', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5305', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5306', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5307', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5308', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5309', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5310', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5311', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5312', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5313', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5314', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5315', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5316', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5317', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5318', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5319', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5320', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5321', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5322', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5323', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5324', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5325', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5326', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5327', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5328', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5329', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5330', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5331', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5332', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5333', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5334', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5335', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5336', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5337', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5338', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5339', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5340', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5341', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5342', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5343', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5344', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5345', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5346', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5347', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5348', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5349', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5350', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5351', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5352', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5353', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5354', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5355', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5356', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5357', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5358', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5359', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5360', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5361', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5362', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5363', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5364', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5365', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5366', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5367', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5368', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5369', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5370', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5371', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5372', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5373', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5374', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5375', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5376', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5377', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5378', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5379', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5380', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5381', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5382', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5383', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5384', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5385', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5386', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5387', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5388', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5389', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5390', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5391', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5392', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5393', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5394', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5395', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5396', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5397', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5398', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5399', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5400', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5401', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5402', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5403', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5404', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5405', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5406', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5407', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5408', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5409', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5410', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5411', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5412', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5413', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5414', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5415', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5416', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5417', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5418', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5419', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5420', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5421', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5422', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5423', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5424', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5425', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5426', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5427', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5428', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5429', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5430', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5431', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5432', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5433', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5434', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5435', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5436', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5437', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5438', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5439', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5440', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5441', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5442', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5443', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5444', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5445', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5446', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5447', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5448', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5449', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5450', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5451', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5452', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5453', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5454', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5455', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5456', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5457', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5458', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5459', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5460', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5461', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5462', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5463', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5464', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5465', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5466', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5467', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5468', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5469', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5470', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5471', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5472', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5473', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5474', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5475', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5476', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5477', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5478', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5479', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5480', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5481', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5482', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5483', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5484', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5485', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5486', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5487', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5488', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5489', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5490', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5491', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5492', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5493', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5494', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5495', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5496', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5497', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5498', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5499', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5500', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5501', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5502', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5503', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5504', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5505', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5506', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5507', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5508', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5509', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5510', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5511', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5512', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5513', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5514', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5515', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5516', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5517', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5518', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5519', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5520', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5521', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5522', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5523', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5524', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5525', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5526', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5527', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5528', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5529', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5530', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5531', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5532', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5533', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5534', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5535', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5536', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5537', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5538', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5539', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5540', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5541', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5542', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5543', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5544', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5545', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5546', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5547', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5548', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5549', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5550', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5551', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5552', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5553', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5554', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5555', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5556', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5557', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5558', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5559', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5560', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5561', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5562', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5563', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5564', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5565', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5566', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5567', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5568', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5569', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5570', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5571', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5572', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5573', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5574', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5575', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5576', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5577', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5578', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5579', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5580', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5581', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5582', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5583', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5584', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5585', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5586', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5587', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5588', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5589', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5590', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5591', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5592', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5593', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5594', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5595', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5596', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5597', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5598', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5599', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5600', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5601', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5602', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5603', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5604', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5605', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5606', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5607', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5608', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5609', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5610', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5611', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5612', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5613', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5614', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5615', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5616', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5617', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5618', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5619', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5620', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5621', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5622', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5623', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5624', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5625', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5626', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5627', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5628', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5629', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5630', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5631', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5632', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5633', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5634', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5635', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5636', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5637', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5638', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5639', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5640', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5641', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5642', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5643', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5644', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5645', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5646', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5647', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5648', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5649', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5650', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5651', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5652', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5653', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5654', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5655', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5656', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5657', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5658', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5659', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5660', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5661', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5662', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5663', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5664', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5665', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5666', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5667', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5668', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5669', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5670', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5671', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5672', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5673', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5674', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5675', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5676', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5677', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5678', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5679', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5680', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5681', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5682', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5683', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5684', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5685', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5686', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5687', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5688', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5689', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5690', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5691', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5692', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5693', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5694', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5695', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5696', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5697', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5698', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5699', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5700', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5701', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5702', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5703', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5704', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5705', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5706', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5707', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5708', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5709', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5710', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5711', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5712', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5713', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5714', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5715', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5716', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5717', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5718', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5719', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5720', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5721', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5722', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5723', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5724', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5725', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5726', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5727', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5728', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5729', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5730', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5731', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5732', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5733', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5734', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5735', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5736', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5737', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5738', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5739', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5740', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5741', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5742', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5743', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5744', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5745', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5746', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5747', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5748', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5749', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5750', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5751', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5752', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5753', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5754', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5755', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5756', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5757', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5758', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5759', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5760', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5761', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5762', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5763', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5764', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5765', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5766', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5767', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5768', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5769', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5770', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5771', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5772', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5773', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5774', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5775', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5776', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5777', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5778', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5779', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5780', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5781', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5782', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5783', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5784', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5785', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5786', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5787', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5788', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5789', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5790', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5791', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5792', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5793', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5794', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5795', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5796', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5797', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5798', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5799', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5800', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5801', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5802', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5803', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5804', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5805', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5806', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5807', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5808', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5809', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5810', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5811', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5812', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5813', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5814', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5815', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5816', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5817', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5818', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5819', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5820', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5821', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5822', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5823', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5824', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5825', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5826', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5827', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5828', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5829', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5830', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5831', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5832', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5833', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5834', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5835', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5836', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5837', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5838', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5839', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5840', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5841', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5842', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5843', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5844', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5845', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5846', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5847', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5848', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5849', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5850', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5851', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5852', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5853', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5854', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5855', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5856', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5857', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5858', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5859', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5860', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5861', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5862', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5863', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5864', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5865', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5866', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5867', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5868', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5869', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5870', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5871', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5872', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5873', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5874', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5875', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5876', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5877', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5878', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5879', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5880', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5881', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5882', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5883', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5884', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5885', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5886', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5887', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5888', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5889', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5890', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5891', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5892', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5893', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5894', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5895', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5896', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5897', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5898', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5899', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5900', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5901', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5902', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5903', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5904', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5905', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5906', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5907', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5908', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5909', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5910', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5911', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5912', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5913', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5914', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5915', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5916', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5917', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5918', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5919', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5920', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5921', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5922', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5923', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5924', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5925', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5926', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5927', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5928', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5929', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5930', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5931', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5932', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5933', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5934', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5935', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5936', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5937', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5938', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5939', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5940', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5941', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5942', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5943', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5944', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5945', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5946', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5947', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5948', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5949', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5950', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5951', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5952', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5953', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5954', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5955', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5956', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5957', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5958', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5959', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5960', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5961', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5962', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5963', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5964', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5965', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5966', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5967', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5968', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5969', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5970', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5971', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5972', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5973', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5974', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5975', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5976', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5977', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5978', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5979', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5980', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5981', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5982', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5983', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5984', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5985', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5986', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5987', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5988', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5989', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5990', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5991', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5992', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5993', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5994', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5995', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5996', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5997', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5998', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('5999', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6000', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6001', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6002', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6003', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6004', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6005', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6006', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6007', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6008', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6009', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6010', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6011', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6012', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6013', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6014', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6015', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6016', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6017', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6018', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6019', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6020', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6021', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6022', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6023', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6024', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6025', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6026', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6027', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6028', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6029', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6030', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6031', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6032', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6033', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6034', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6035', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6036', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6037', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6038', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6039', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6040', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6041', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6042', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6043', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6044', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6045', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6046', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6047', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6048', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6049', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6050', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6051', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6052', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6053', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6054', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6055', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6056', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6057', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6058', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6059', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6060', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6061', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6062', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6063', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6064', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6065', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6066', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6067', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6068', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6069', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6070', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6071', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6072', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6073', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6074', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6075', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6076', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6077', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6078', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6079', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6080', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6081', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6082', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6083', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6084', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6085', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6086', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6087', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6088', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6089', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6090', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6091', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6092', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6093', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6094', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6095', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6096', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6097', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6098', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6099', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6100', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6101', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6102', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6103', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6104', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6105', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6106', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6107', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6108', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6109', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6110', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6111', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6112', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6113', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6114', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6115', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6116', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6117', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6118', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6119', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6120', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6121', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6122', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6123', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6124', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6125', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6126', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6127', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6128', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6129', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6130', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6131', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6132', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6133', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6134', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6135', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6136', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6137', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6138', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6139', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6140', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6141', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6142', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6143', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6144', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6145', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6146', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6147', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6148', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6149', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6150', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6151', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6152', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6153', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6154', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6155', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6156', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6157', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6158', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6159', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6160', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6161', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6162', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6163', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6164', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6165', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6166', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6167', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6168', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6169', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6170', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6171', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6172', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6173', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6174', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6175', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6176', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6177', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6178', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6179', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6180', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6181', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6182', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6183', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6184', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6185', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6186', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6187', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6188', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6189', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6190', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6191', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6192', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6193', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6194', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6195', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6196', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6197', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6198', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6199', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6200', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6201', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6202', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6203', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6204', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6205', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6206', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6207', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6208', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6209', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6210', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6211', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6212', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6213', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6214', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6215', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6216', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6217', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6218', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6219', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6220', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6221', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6222', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6223', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6224', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6225', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6226', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6227', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6228', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6229', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6230', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6231', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6232', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6233', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6234', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6235', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6236', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6237', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6238', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6239', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6240', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6241', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6242', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6243', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6244', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6245', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6246', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6247', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6248', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6249', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6250', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6251', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6252', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6253', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6254', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6255', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6256', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6257', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6258', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6259', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6260', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6261', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6262', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6263', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6264', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6265', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6266', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6267', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6268', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6269', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6270', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6271', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6272', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6273', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6274', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6275', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6276', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6277', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6278', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6279', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6280', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6281', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6282', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6283', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6284', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6285', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6286', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6287', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6288', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6289', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6290', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6291', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6292', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6293', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6294', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6295', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6296', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6297', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6298', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6299', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6300', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6301', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6302', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6303', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6304', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6305', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6306', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6307', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6308', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6309', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6310', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6311', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6312', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6313', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6314', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6315', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6316', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6317', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6318', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6319', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6320', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6321', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6322', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6323', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6324', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6325', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6326', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6327', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6328', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6329', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6330', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6331', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6332', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6333', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6334', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6335', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6336', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6337', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6338', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6339', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6340', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6341', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6342', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6343', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6344', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6345', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6346', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6347', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6348', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6349', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6350', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6351', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6352', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6353', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6354', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6355', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6356', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6357', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6358', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6359', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6360', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6361', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6362', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6363', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6364', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6365', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6366', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6367', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6368', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6369', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6370', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6371', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6372', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6373', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6374', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6375', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6376', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6377', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6378', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6379', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6380', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6381', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6382', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6383', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6384', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6385', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6386', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6387', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6388', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6389', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6390', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6391', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6392', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6393', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6394', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6395', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6396', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6397', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6398', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6399', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6400', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6401', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6402', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6403', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6404', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6405', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6406', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6407', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6408', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6409', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6410', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6411', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6412', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6413', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6414', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6415', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6416', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6417', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6418', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6419', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6420', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6421', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6422', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6423', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6424', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6425', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6426', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6427', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6428', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6429', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6430', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6431', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6432', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6433', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6434', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6435', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6436', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6437', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6438', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6439', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6440', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6441', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6442', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6443', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6444', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6445', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6446', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6447', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6448', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6449', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6450', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6451', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6452', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6453', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6454', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6455', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6456', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6457', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6458', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6459', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6460', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6461', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6462', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6463', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6464', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6465', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6466', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6467', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6468', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6469', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6470', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6471', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6472', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6473', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6474', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6475', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6476', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6477', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6478', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6479', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6480', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6481', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6482', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6483', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6484', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6485', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6486', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6487', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6488', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6489', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6490', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6491', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6492', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6493', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6494', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6495', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6496', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6497', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6498', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6499', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6500', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6501', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6502', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6503', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6504', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6505', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6506', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6507', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6508', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6509', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6510', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6511', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6512', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6513', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6514', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6515', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6516', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6517', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6518', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6519', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6520', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6521', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6522', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6523', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6524', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6525', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6526', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6527', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6528', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6529', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6530', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6531', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6532', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6533', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6534', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6535', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6536', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6537', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6538', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6539', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6540', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6541', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6542', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6543', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6544', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6545', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6546', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6547', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6548', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6549', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6550', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6551', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6552', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6553', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6554', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6555', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6556', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6557', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6558', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6559', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6560', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6561', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6562', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6563', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6564', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6565', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6566', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6567', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6568', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6569', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6570', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6571', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6572', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6573', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6574', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6575', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6576', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6577', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6578', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6579', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6580', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6581', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6582', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6583', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6584', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6585', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6586', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6587', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6588', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6589', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6590', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6591', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6592', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6593', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6594', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6595', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6596', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6597', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6598', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6599', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6600', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6601', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6602', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6603', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6604', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6605', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6606', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6607', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6608', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6609', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6610', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6611', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6612', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6613', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6614', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6615', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6616', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6617', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6618', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6619', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6620', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6621', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6622', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6623', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6624', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6625', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6626', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6627', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6628', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6629', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6630', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6631', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6632', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6633', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6634', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6635', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6636', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6637', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6638', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6639', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6640', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6641', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6642', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6643', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6644', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6645', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6646', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6647', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6648', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6649', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6650', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6651', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6652', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6653', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6654', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6655', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6656', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6657', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6658', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6659', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6660', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6661', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6662', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6663', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6664', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6665', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6666', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6667', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6668', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6669', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6670', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6671', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6672', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6673', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6674', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6675', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6676', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6677', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6678', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6679', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6680', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6681', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6682', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6683', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6684', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6685', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6686', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6687', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6688', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6689', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6690', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6691', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6692', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6693', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6694', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6695', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6696', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6697', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6698', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6699', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6700', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6701', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6702', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6703', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6704', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6705', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6706', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6707', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6708', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6709', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6710', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6711', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6712', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6713', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6714', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6715', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6716', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6717', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6718', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6719', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6720', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6721', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6722', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6723', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6724', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6725', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6726', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6727', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6728', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6729', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6730', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6731', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6732', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6733', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6734', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6735', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6736', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6737', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6738', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6739', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6740', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6741', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6742', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6743', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6744', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6745', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6746', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6747', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6748', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6749', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6750', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6751', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6752', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6753', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6754', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6755', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6756', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6757', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6758', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6759', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6760', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6761', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6762', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6763', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6764', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6765', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6766', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6767', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6768', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6769', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6770', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6771', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6772', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6773', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6774', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6775', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6776', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6777', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6778', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6779', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6780', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6781', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6782', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6783', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6784', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6785', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6786', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6787', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6788', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6789', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6790', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6791', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6792', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6793', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6794', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6795', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6796', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6797', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6798', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6799', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6800', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6801', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6802', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6803', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6804', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6805', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6806', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6807', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6808', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6809', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6810', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6811', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6812', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6813', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6814', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6815', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6816', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6817', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6818', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6819', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6820', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6821', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6822', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6823', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6824', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6825', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6826', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6827', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6828', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6829', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6830', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6831', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6832', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6833', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6834', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6835', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6836', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6837', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6838', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6839', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6840', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6841', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6842', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6843', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6844', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6845', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6846', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6847', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6848', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6849', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6850', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6851', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6852', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6853', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6854', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6855', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6856', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6857', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6858', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6859', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6860', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6861', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6862', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6863', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6864', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6865', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6866', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6867', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6868', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6869', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6870', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6871', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6872', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6873', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6874', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6875', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6876', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6877', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6878', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6879', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6880', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6881', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6882', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6883', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6884', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6885', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6886', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6887', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6888', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6889', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6890', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6891', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6892', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6893', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6894', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6895', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6896', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6897', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6898', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6899', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6900', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6901', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6902', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6903', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6904', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6905', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6906', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6907', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6908', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6909', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6910', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6911', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6912', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6913', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6914', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6915', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6916', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6917', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6918', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6919', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6920', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6921', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6922', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6923', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6924', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6925', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6926', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6927', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6928', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6929', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6930', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6931', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6932', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6933', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6934', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6935', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6936', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6937', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6938', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6939', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6940', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6941', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6942', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6943', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6944', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6945', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6946', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6947', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6948', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6949', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6950', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6951', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6952', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6953', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6954', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6955', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6956', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6957', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6958', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6959', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6960', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6961', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6962', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6963', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6964', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6965', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6966', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6967', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6968', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6969', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6970', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6971', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6972', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6973', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6974', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6975', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6976', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6977', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6978', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6979', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6980', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6981', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6982', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6983', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6984', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6985', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6986', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6987', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6988', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6989', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6990', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6991', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6992', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6993', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6994', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6995', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6996', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6997', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6998', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('6999', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7000', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7001', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7002', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7003', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7004', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7005', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7006', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7007', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7008', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7009', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7010', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7011', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7012', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7013', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7014', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7015', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7016', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7017', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7018', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7019', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7020', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7021', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7022', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7023', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7024', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7025', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7026', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7027', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7028', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7029', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7030', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7031', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7032', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7033', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7034', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7035', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7036', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7037', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7038', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7039', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7040', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7041', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7042', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7043', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7044', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7045', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7046', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7047', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7048', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7049', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7050', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7051', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7052', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7053', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7054', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7055', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7056', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7057', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7058', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7059', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7060', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7061', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7062', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7063', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7064', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7065', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7066', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7067', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7068', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7069', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7070', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7071', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7072', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7073', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7074', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7075', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7076', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7077', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7078', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7079', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7080', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7081', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7082', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7083', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7084', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7085', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7086', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7087', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7088', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7089', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7090', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7091', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7092', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7093', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7094', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7095', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7096', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7097', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7098', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7099', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7100', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7101', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7102', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7103', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7104', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7105', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7106', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7107', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7108', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7109', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7110', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7111', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7112', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7113', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7114', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7115', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7116', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7117', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7118', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7119', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7120', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7121', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7122', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7123', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7124', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7125', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7126', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7127', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7128', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7129', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7130', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7131', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7132', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7133', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7134', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7135', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7136', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7137', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7138', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7139', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7140', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7141', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7142', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7143', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7144', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7145', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7146', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7147', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7148', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7149', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7150', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7151', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7152', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7153', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7154', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7155', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7156', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7157', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7158', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7159', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7160', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7161', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7162', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7163', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7164', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7165', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7166', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7167', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7168', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7169', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7170', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7171', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7172', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7173', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7174', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7175', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7176', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7177', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7178', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7179', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7180', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7181', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7182', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7183', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7184', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7185', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7186', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7187', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7188', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7189', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7190', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7191', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7192', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7193', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7194', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7195', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7196', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7197', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7198', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7199', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7200', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7201', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7202', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7203', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7204', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7205', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7206', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7207', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7208', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7209', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7210', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7211', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7212', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7213', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7214', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7215', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7216', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7217', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7218', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7219', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7220', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7221', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7222', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7223', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7224', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7225', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7226', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7227', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7228', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7229', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7230', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7231', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7232', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7233', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7234', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7235', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7236', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7237', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7238', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7239', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7240', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7241', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7242', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7243', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7244', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7245', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7246', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7247', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7248', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7249', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7250', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7251', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7252', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7253', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7254', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7255', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7256', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7257', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7258', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7259', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7260', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7261', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7262', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7263', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7264', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7265', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7266', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7267', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7268', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7269', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7270', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7271', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7272', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7273', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7274', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7275', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7276', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7277', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7278', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7279', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7280', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7281', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7282', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7283', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7284', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7285', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7286', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7287', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7288', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7289', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7290', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7291', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7292', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7293', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7294', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7295', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7296', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7297', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7298', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7299', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7300', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7301', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7302', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7303', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7304', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7305', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7306', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7307', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7308', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7309', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7310', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7311', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7312', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7313', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7314', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7315', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7316', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7317', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7318', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7319', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7320', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7321', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7322', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7323', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7324', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7325', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7326', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7327', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7328', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7329', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7330', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7331', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7332', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7333', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7334', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7335', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7336', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7337', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7338', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7339', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7340', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7341', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7342', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7343', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7344', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7345', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7346', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7347', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7348', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7349', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7350', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7351', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7352', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7353', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7354', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7355', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7356', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7357', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7358', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7359', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7360', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7361', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7362', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7363', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7364', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7365', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7366', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7367', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7368', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7369', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7370', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7371', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7372', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7373', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7374', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7375', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7376', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7377', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7378', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7379', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7380', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7381', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7382', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7383', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7384', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7385', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7386', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7387', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7388', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7389', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7390', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7391', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7392', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7393', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7394', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7395', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7396', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7397', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7398', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7399', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7400', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7401', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7402', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7403', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7404', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7405', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7406', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7407', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7408', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7409', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7410', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7411', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7412', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7413', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7414', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7415', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7416', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7417', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7418', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7419', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7420', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7421', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7422', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7423', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7424', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7425', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7426', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7427', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7428', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7429', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7430', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7431', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7432', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7433', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7434', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7435', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7436', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7437', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7438', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7439', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7440', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7441', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7442', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7443', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7444', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7445', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7446', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7447', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7448', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7449', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7450', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7451', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7452', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7453', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7454', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7455', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7456', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7457', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7458', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7459', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7460', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7461', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7462', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7463', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7464', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7465', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7466', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7467', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7468', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7469', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7470', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7471', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7472', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7473', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7474', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7475', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7476', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7477', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7478', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7479', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7480', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7481', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7482', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7483', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7484', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7485', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7486', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7487', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7488', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7489', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7490', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7491', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7492', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7493', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7494', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7495', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7496', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7497', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7498', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7499', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7500', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7501', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7502', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7503', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7504', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7505', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7506', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7507', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7508', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7509', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7510', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7511', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7512', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7513', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7514', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7515', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7516', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7517', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7518', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7519', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7520', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7521', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7522', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7523', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7524', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7525', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7526', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7527', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7528', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7529', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7530', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7531', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7532', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7533', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7534', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7535', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7536', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7537', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7538', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7539', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7540', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7541', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7542', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7543', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7544', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7545', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7546', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7547', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7548', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7549', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7550', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7551', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7552', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7553', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7554', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7555', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7556', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7557', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7558', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7559', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7560', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7561', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7562', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7563', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7564', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7565', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7566', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7567', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7568', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7569', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7570', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7571', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7572', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7573', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7574', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7575', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7576', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7577', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7578', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7579', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7580', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7581', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7582', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7583', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7584', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7585', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7586', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7587', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7588', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7589', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7590', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7591', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7592', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7593', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7594', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7595', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7596', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7597', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7598', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7599', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7600', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7601', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7602', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7603', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7604', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7605', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7606', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7607', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7608', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7609', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7610', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7611', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7612', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7613', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7614', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7615', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7616', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7617', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7618', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7619', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7620', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7621', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7622', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7623', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7624', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7625', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7626', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7627', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7628', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7629', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7630', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7631', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7632', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7633', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7634', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7635', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7636', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7637', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7638', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7639', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7640', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7641', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7642', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7643', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7644', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7645', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7646', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7647', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7648', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7649', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7650', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7651', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7652', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7653', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7654', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7655', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7656', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7657', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7658', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7659', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7660', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7661', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7662', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7663', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7664', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7665', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7666', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7667', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7668', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7669', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7670', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7671', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7672', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7673', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7674', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7675', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7676', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7677', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7678', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7679', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7680', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7681', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7682', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7683', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7684', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7685', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7686', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7687', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7688', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7689', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7690', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7691', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7692', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7693', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7694', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7695', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7696', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7697', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7698', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7699', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7700', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7701', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7702', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7703', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7704', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7705', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7706', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7707', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7708', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7709', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7710', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7711', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7712', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7713', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7714', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7715', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7716', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7717', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7718', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7719', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7720', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7721', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7722', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7723', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7724', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7725', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7726', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7727', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7728', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7729', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7730', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7731', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7732', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7733', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7734', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7735', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7736', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7737', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7738', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7739', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7740', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7741', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7742', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7743', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7744', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7745', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7746', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7747', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7748', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7749', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7750', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7751', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7752', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7753', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7754', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7755', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7756', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7757', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7758', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7759', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7760', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7761', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7762', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7763', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7764', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7765', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7766', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7767', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7768', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7769', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7770', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7771', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7772', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7773', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7774', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7775', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7776', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7777', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7778', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7779', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7780', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7781', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7782', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7783', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7784', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7785', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7786', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7787', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7788', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7789', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7790', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7791', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7792', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7793', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7794', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7795', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7796', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7797', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7798', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7799', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7800', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7801', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7802', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7803', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7804', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7805', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7806', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7807', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7808', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7809', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7810', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7811', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7812', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7813', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7814', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7815', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7816', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7817', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7818', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7819', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7820', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7821', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7822', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7823', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7824', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7825', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7826', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7827', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7828', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7829', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7830', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7831', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7832', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7833', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7834', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7835', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7836', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7837', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7838', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7839', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7840', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7841', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7842', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7843', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7844', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7845', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7846', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7847', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7848', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7849', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7850', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7851', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7852', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7853', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7854', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7855', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7856', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7857', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7858', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7859', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7860', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7861', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7862', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7863', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7864', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7865', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7866', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7867', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7868', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7869', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7870', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7871', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7872', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7873', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7874', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7875', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7876', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7877', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7878', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7879', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7880', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7881', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7882', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7883', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7884', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7885', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7886', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7887', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7888', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7889', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7890', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7891', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7892', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7893', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7894', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7895', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7896', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7897', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7898', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7899', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7900', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7901', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7902', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7903', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7904', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7905', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7906', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7907', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7908', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7909', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7910', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7911', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7912', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7913', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7914', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7915', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7916', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7917', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7918', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7919', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7920', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7921', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7922', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7923', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7924', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7925', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7926', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7927', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7928', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7929', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7930', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7931', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7932', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7933', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7934', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7935', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7936', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7937', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7938', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7939', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7940', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7941', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7942', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7943', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7944', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7945', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7946', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7947', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7948', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7949', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7950', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7951', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7952', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7953', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7954', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7955', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7956', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7957', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7958', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7959', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7960', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7961', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7962', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7963', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7964', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7965', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7966', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7967', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7968', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7969', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7970', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7971', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7972', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7973', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7974', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7975', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7976', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7977', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7978', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7979', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7980', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7981', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7982', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7983', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7984', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7985', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7986', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7987', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7988', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7989', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7990', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7991', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7992', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7993', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7994', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7995', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7996', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7997', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7998', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('7999', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8000', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8001', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8002', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8003', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8004', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8005', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8006', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8007', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8008', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8009', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8010', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8011', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8012', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8013', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8014', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8015', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8016', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8017', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8018', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8019', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8020', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8021', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8022', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8023', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8024', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8025', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8026', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8027', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8028', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8029', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8030', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8031', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8032', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8033', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8034', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8035', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8036', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8037', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8038', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8039', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8040', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8041', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8042', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8043', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8044', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8045', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8046', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8047', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8048', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8049', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8050', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8051', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8052', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8053', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8054', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8055', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8056', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8057', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8058', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8059', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8060', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8061', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8062', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8063', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8064', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8065', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8066', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8067', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8068', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8069', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8070', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8071', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8072', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8073', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8074', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8075', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8076', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8077', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8078', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8079', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8080', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8081', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8082', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8083', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8084', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8085', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8086', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8087', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8088', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8089', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8090', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8091', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8092', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8093', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8094', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8095', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8096', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8097', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8098', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8099', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8100', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8101', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8102', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8103', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8104', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8105', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8106', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8107', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8108', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8109', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8110', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8111', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8112', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8113', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8114', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8115', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8116', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8117', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8118', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8119', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8120', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8121', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8122', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8123', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8124', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8125', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8126', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8127', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8128', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8129', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8130', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8131', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8132', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8133', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8134', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8135', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8136', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8137', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8138', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8139', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8140', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8141', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8142', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8143', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8144', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8145', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8146', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8147', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8148', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8149', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8150', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8151', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8152', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8153', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8154', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8155', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8156', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8157', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8158', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8159', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8160', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8161', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8162', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8163', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8164', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8165', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8166', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8167', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8168', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8169', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8170', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8171', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8172', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8173', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8174', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8175', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8176', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8177', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8178', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8179', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8180', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8181', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8182', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8183', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8184', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8185', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8186', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8187', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8188', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8189', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8190', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8191', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8192', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8193', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8194', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8195', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8196', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8197', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8198', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8199', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8200', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8201', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8202', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8203', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8204', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8205', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8206', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8207', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8208', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8209', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8210', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8211', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8212', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8213', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8214', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8215', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8216', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8217', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8218', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8219', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8220', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8221', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8222', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8223', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8224', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8225', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8226', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8227', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8228', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8229', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8230', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8231', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8232', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8233', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8234', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8235', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8236', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8237', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8238', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8239', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8240', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8241', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8242', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8243', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8244', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8245', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8246', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8247', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8248', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8249', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8250', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8251', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8252', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8253', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8254', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8255', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8256', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8257', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8258', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8259', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8260', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8261', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8262', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8263', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8264', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8265', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8266', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8267', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8268', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8269', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8270', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8271', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8272', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8273', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8274', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8275', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8276', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8277', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8278', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8279', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8280', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8281', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8282', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8283', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8284', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8285', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8286', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8287', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8288', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8289', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8290', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8291', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8292', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8293', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8294', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8295', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8296', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8297', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8298', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8299', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8300', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8301', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8302', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8303', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8304', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8305', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8306', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8307', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8308', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8309', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8310', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8311', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8312', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8313', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8314', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8315', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8316', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8317', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8318', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8319', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8320', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8321', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8322', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8323', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8324', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8325', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8326', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8327', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8328', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8329', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8330', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8331', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8332', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8333', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8334', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8335', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8336', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8337', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8338', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8339', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8340', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8341', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8342', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8343', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8344', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8345', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8346', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8347', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8348', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8349', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8350', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8351', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8352', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8353', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8354', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8355', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8356', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8357', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8358', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8359', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8360', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8361', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8362', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8363', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8364', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8365', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8366', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8367', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8368', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8369', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8370', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8371', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8372', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8373', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8374', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8375', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8376', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8377', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8378', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8379', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8380', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8381', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8382', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8383', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8384', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8385', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8386', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8387', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8388', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8389', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8390', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8391', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8392', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8393', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8394', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8395', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8396', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8397', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8398', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8399', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8400', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8401', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8402', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8403', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8404', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8405', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8406', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8407', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8408', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8409', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8410', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8411', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8412', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8413', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8414', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8415', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8416', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8417', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8418', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8419', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8420', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8421', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8422', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8423', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8424', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8425', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8426', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8427', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8428', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8429', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8430', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8431', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8432', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8433', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8434', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8435', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8436', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8437', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8438', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8439', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8440', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8441', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8442', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8443', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8444', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8445', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8446', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8447', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8448', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8449', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8450', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8451', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8452', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8453', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8454', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8455', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8456', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8457', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8458', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8459', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8460', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8461', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8462', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8463', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8464', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8465', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8466', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8467', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8468', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8469', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8470', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8471', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8472', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8473', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8474', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8475', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8476', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8477', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8478', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8479', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8480', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8481', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8482', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8483', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8484', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8485', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8486', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8487', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8488', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8489', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8490', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8491', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8492', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8493', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8494', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8495', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8496', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8497', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8498', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8499', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8500', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8501', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8502', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8503', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8504', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8505', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8506', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8507', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8508', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8509', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8510', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8511', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8512', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8513', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8514', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8515', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8516', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8517', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8518', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8519', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8520', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8521', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8522', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8523', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8524', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8525', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8526', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8527', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8528', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8529', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8530', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8531', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8532', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8533', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8534', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8535', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8536', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8537', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8538', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8539', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8540', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8541', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8542', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8543', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8544', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8545', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8546', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8547', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8548', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8549', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8550', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8551', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8552', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8553', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8554', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8555', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8556', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8557', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8558', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8559', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8560', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8561', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8562', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8563', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8564', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8565', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8566', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8567', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8568', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8569', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8570', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8571', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8572', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8573', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8574', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8575', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8576', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8577', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8578', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8579', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8580', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8581', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8582', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8583', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8584', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8585', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8586', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8587', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8588', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8589', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8590', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8591', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8592', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8593', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8594', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8595', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8596', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8597', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8598', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8599', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8600', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8601', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8602', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8603', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8604', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8605', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8606', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8607', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8608', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8609', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8610', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8611', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8612', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8613', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8614', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8615', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8616', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8617', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8618', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8619', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8620', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8621', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8622', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8623', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8624', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8625', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8626', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8627', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8628', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8629', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8630', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8631', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8632', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8633', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8634', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8635', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8636', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8637', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8638', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8639', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8640', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8641', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8642', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8643', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8644', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8645', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8646', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8647', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8648', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8649', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8650', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8651', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8652', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8653', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8654', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8655', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8656', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8657', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8658', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8659', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8660', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8661', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8662', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8663', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8664', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8665', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8666', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8667', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8668', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8669', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8670', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8671', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8672', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8673', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8674', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8675', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8676', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8677', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8678', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8679', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8680', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8681', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8682', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8683', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8684', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8685', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8686', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8687', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8688', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8689', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8690', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8691', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8692', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8693', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8694', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8695', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8696', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8697', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8698', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8699', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8700', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8701', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8702', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8703', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8704', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8705', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8706', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8707', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8708', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8709', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8710', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8711', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8712', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8713', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8714', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8715', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8716', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8717', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8718', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8719', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8720', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8721', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8722', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8723', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8724', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8725', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8726', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8727', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8728', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8729', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8730', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8731', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8732', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8733', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8734', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8735', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8736', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8737', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8738', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8739', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8740', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8741', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8742', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8743', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8744', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8745', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8746', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8747', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8748', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8749', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8750', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8751', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8752', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8753', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8754', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8755', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8756', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8757', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8758', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8759', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8760', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8761', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8762', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8763', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8764', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8765', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8766', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8767', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8768', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8769', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8770', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8771', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8772', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8773', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8774', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8775', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8776', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8777', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8778', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8779', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8780', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8781', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8782', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8783', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8784', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8785', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8786', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8787', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8788', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8789', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8790', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8791', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8792', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8793', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8794', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8795', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8796', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8797', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8798', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8799', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8800', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8801', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8802', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8803', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8804', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8805', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8806', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8807', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8808', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8809', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8810', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8811', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8812', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8813', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8814', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8815', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8816', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8817', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8818', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8819', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8820', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8821', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8822', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8823', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8824', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8825', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8826', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8827', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8828', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8829', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8830', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8831', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8832', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8833', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8834', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8835', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8836', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8837', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8838', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8839', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8840', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8841', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8842', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8843', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8844', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8845', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8846', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8847', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8848', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8849', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8850', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8851', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8852', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8853', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8854', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8855', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8856', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8857', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8858', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8859', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8860', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8861', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8862', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8863', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8864', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8865', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8866', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8867', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8868', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8869', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8870', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8871', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8872', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8873', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8874', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8875', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8876', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8877', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8878', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8879', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8880', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8881', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8882', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8883', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8884', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8885', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8886', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8887', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8888', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8889', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8890', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8891', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8892', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8893', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8894', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8895', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8896', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8897', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8898', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8899', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8900', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8901', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8902', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8903', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8904', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8905', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8906', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8907', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8908', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8909', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8910', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8911', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8912', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8913', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8914', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8915', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8916', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8917', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8918', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8919', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8920', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8921', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8922', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8923', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8924', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8925', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8926', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8927', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8928', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8929', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8930', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8931', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8932', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8933', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8934', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8935', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8936', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8937', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8938', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8939', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8940', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8941', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8942', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8943', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8944', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8945', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8946', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8947', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8948', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8949', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8950', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8951', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8952', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8953', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8954', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8955', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8956', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8957', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8958', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8959', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8960', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8961', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8962', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8963', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8964', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8965', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8966', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8967', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8968', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8969', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8970', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8971', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8972', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8973', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8974', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8975', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8976', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8977', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8978', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8979', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8980', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8981', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8982', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8983', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8984', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8985', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8986', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8987', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8988', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8989', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8990', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8991', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8992', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8993', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8994', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8995', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8996', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8997', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8998', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('8999', null);
INSERT INTO `TIME_INDEX_HOUR` VALUES ('9000', null);

-- ----------------------------
-- Table structure for TIME_LIMIT_CONTROL
-- ----------------------------
DROP TABLE IF EXISTS `TIME_LIMIT_CONTROL`;
CREATE TABLE `TIME_LIMIT_CONTROL` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `CITY_ID` bigint(20) DEFAULT NULL COMMENT '城市ID',
  `AREA_ID` bigint(20) DEFAULT NULL COMMENT '区域ID',
  `ROAD_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '路口点位ID',
  `LIMIT_VEHICLE_TYPE` int(11) DEFAULT NULL COMMENT '限时禁行车辆类型（0-所有，1-货车）',
  `START_DATE` date DEFAULT NULL COMMENT '开始日期',
  `END_DATE` date DEFAULT NULL COMMENT '结束日期',
  `START_TIME` time DEFAULT NULL COMMENT '每日开始时间',
  `END_TIME` time DEFAULT NULL COMMENT '每日结束时间',
  `WHOLE_YEAR_EFFECTIVE` int(11) DEFAULT NULL COMMENT '是否全年有效(0:否; 1:是)',
  `WHOLE_DAY_EFFECTIVE` int(11) DEFAULT NULL COMMENT '是否全天有效(0:否; 1:是)',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态(0:未启用; 1:已启用)',
  `DELETED` tinyint(4) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='限时禁行布控';

-- ----------------------------
-- Records of TIME_LIMIT_CONTROL
-- ----------------------------

-- ----------------------------
-- Table structure for TIME_LIMIT_CONTROL_ROAD
-- ----------------------------
DROP TABLE IF EXISTS `TIME_LIMIT_CONTROL_ROAD`;
CREATE TABLE `TIME_LIMIT_CONTROL_ROAD` (
  `ID` bigint(20) NOT NULL COMMENT 'ID',
  `TIME_LIMIT_CONTROL_ID` bigint(20) NOT NULL COMMENT '限时禁行布控ID',
  `ROAD_CROSS_POINT_ID` bigint(20) NOT NULL COMMENT '路口点位ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='限时禁行布控关联路口点位，限时禁行布控1-N路口点位';

-- ----------------------------
-- Records of TIME_LIMIT_CONTROL_ROAD
-- ----------------------------

-- ----------------------------
-- Table structure for UNIDIRECTION_CONTROL
-- ----------------------------
DROP TABLE IF EXISTS `UNIDIRECTION_CONTROL`;
CREATE TABLE `UNIDIRECTION_CONTROL` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `ROAD_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '路口点位ID',
  `DIRECTION_CODE` int(11) DEFAULT NULL COMMENT '允许单向行驶的方向编码',
  `START_DATE` date DEFAULT NULL COMMENT '开始日期',
  `END_DATE` date DEFAULT NULL COMMENT '结束日期',
  `START_TIME` time DEFAULT NULL COMMENT '开始时间',
  `END_TIME` time DEFAULT NULL COMMENT '结束时间',
  `WHOLE_YEAR_EFFECTIVE` tinyint(4) DEFAULT NULL COMMENT '是否全年有效(0:否;1:是)',
  `WHOLE_DAY_EFFECTIVE` tinyint(4) DEFAULT NULL COMMENT '是否全天有效(0:否;1:是)',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态(0:未启用; 1:已启用)',
  `DELETED` tinyint(4) DEFAULT NULL COMMENT '是否删除',
  `CITY_ID` bigint(20) DEFAULT NULL COMMENT '城市id',
  `AREA_ID` bigint(20) DEFAULT NULL COMMENT '区域id',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单行线布控';

-- ----------------------------
-- Records of UNIDIRECTION_CONTROL
-- ----------------------------

-- ----------------------------
-- Table structure for UNIFIED_ALARM
-- ----------------------------
DROP TABLE IF EXISTS `UNIFIED_ALARM`;
CREATE TABLE `UNIFIED_ALARM` (
  `INCIDENT_ID` varchar(36) NOT NULL DEFAULT '' COMMENT '警情ID',
  `ALARM_ADDRESS` varchar(256) DEFAULT NULL COMMENT '报警地址',
  `INCIDENT_SUB_TYPE` varchar(11) DEFAULT NULL COMMENT '警情子类型',
  `INCIDENT_SUB_TYPE_STR` varchar(36) DEFAULT NULL,
  `INCIDENT_TYPE` varchar(11) DEFAULT NULL COMMENT '警情类型',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '生成警情时间',
  `DISPOSAL_STATUS` int(11) NOT NULL DEFAULT '0' COMMENT '警情处置状态',
  `DISPOSAL_STATUS_STR` varchar(36) NOT NULL DEFAULT '',
  `IS_FALSE_ALARM` int(2) NOT NULL DEFAULT '0' COMMENT '0:真警;1:假警',
  `INCIDENT_HANDLE_TIME` datetime DEFAULT NULL COMMENT '警情处置时间',
  PRIMARY KEY (`INCIDENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of UNIFIED_ALARM
-- ----------------------------

-- ----------------------------
-- Table structure for USER
-- ----------------------------
DROP TABLE IF EXISTS `USER`;
CREATE TABLE `USER` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `USERNAME` char(16) DEFAULT NULL COMMENT '用户名',
  `PASSWORD` varchar(64) DEFAULT NULL,
  `NAME` varchar(64) DEFAULT NULL COMMENT '姓名',
  `GENDER` int(11) DEFAULT NULL COMMENT '性别(1:男; 2:女)',
  `PHONE` char(16) DEFAULT NULL COMMENT '电话',
  `EMAIL` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `MONITOR_CENTER_ID` bigint(20) DEFAULT NULL COMMENT '监控中心ID',
  `ROLE_ID` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `DELETED` tinyint(4) DEFAULT NULL COMMENT '是否已删除',
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_35` (`MONITOR_CENTER_ID`),
  KEY `FK_Reference_38` (`ROLE_ID`),
  CONSTRAINT `FK_Reference_35` FOREIGN KEY (`MONITOR_CENTER_ID`) REFERENCES `MONITOR_CENTER` (`ID`),
  CONSTRAINT `FK_Reference_38` FOREIGN KEY (`ROLE_ID`) REFERENCES `ROLE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of USER
-- ----------------------------
INSERT INTO `USER` VALUES ('1', 'superAdmin', '83eebac535d14f791f6ee4dbefe689dc', 'Super administrador', '1', '123456789', 'test', '222', '2', '0', null);

-- ----------------------------
-- Table structure for USER_CITY_AREA
-- ----------------------------
DROP TABLE IF EXISTS `USER_CITY_AREA`;
CREATE TABLE `USER_CITY_AREA` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `USER_ID` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `CITY_ID` bigint(20) DEFAULT NULL COMMENT '城市ID',
  `AREA_ID` bigint(20) DEFAULT NULL COMMENT '区域ID',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk1` (`USER_ID`,`CITY_ID`,`AREA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户城市区域数据权限关联表';

-- ----------------------------
-- Records of USER_CITY_AREA
-- ----------------------------

-- ----------------------------
-- Table structure for USER_LOG
-- ----------------------------
DROP TABLE IF EXISTS `USER_LOG`;
CREATE TABLE `USER_LOG` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `USER_ID` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `DATA_TYPE` int(11) DEFAULT NULL COMMENT '数据类型（0，违章数据变更；1，车辆布控数据变更，2，设备数据变更，3，系统数据变更）',
  `ACTION_TYPE` int(11) DEFAULT NULL COMMENT '操作类型(0,添加；1，修改；2，删除)',
  `DESCRIPTION` varchar(1024) DEFAULT NULL COMMENT '操作参数',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_41` (`DATA_TYPE`),
  KEY `FK_Reference_42` (`USER_ID`),
  CONSTRAINT `FK_Reference_42` FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户日志';

-- ----------------------------
-- Records of USER_LOG
-- ----------------------------

-- ----------------------------
-- Table structure for VEHICLE_BRAND
-- ----------------------------
DROP TABLE IF EXISTS `VEHICLE_BRAND`;
CREATE TABLE `VEHICLE_BRAND` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `NAME` varchar(256) DEFAULT NULL COMMENT '品牌名称',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆品牌';

-- ----------------------------
-- Records of VEHICLE_BRAND
-- ----------------------------

-- ----------------------------
-- Table structure for VEHICLE_PROFILE
-- ----------------------------
DROP TABLE IF EXISTS `VEHICLE_PROFILE`;
CREATE TABLE `VEHICLE_PROFILE` (
  `PLATE_NUMBER` char(12) NOT NULL COMMENT '车牌号',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `VEHICLE_COLOR` int(11) DEFAULT NULL COMMENT '车辆颜色',
  `PLATE_COLOR` int(11) DEFAULT NULL COMMENT '车牌颜色',
  `VEHICLE_BRAND` int(11) DEFAULT NULL COMMENT '车辆品牌',
  `LAST_CAPTURE_LONGITUDE` bigint(20) DEFAULT NULL COMMENT '上次抓拍经度',
  `LAST_CAPTURE_LATITUDE` bigint(20) DEFAULT NULL COMMENT '上次抓拍纬度',
  `LAST_CAPTURE_TIME` datetime DEFAULT NULL COMMENT '上次抓拍时间',
  `LAST_PASSEDBY_VEHICLE_ID` bigint(20) DEFAULT NULL COMMENT '上次抓拍记录',
  `Column_10` char(10) DEFAULT NULL,
  PRIMARY KEY (`PLATE_NUMBER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆画像';

-- ----------------------------
-- Records of VEHICLE_PROFILE
-- ----------------------------

-- ----------------------------
-- Table structure for VIOLATION_CODE
-- ----------------------------
DROP TABLE IF EXISTS `VIOLATION_CODE`;
CREATE TABLE `VIOLATION_CODE` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `VIOLATION_CODE` char(8) DEFAULT NULL COMMENT '代码（1-未按规定车道行驶，2-未按交通信号灯规定通行，3-不按导向线行驶，4-违章停车，5-违反禁止标线指示，6-违反禁令标志指示，7-超速，8-逆行，9-限号违章，10-区间测速超速，11-违反限制通行规定）',
  `VIOLATION_DESC` varchar(256) DEFAULT NULL COMMENT '描述',
  `SORT` int(2) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='违章代码字典';

-- ----------------------------
-- Records of VIOLATION_CODE
-- ----------------------------
INSERT INTO `VIOLATION_CODE` VALUES ('1', '1', 'Não dirija na via prescrita', '11');
INSERT INTO `VIOLATION_CODE` VALUES ('2', '2', 'Não dirija de acordo com os regulamentos de semáforos', '10');
INSERT INTO `VIOLATION_CODE` VALUES ('3', '3', 'Não siga a orientação', '9');
INSERT INTO `VIOLATION_CODE` VALUES ('4', '4', 'Estacionamento ilegal', '8');
INSERT INTO `VIOLATION_CODE` VALUES ('5', '5', 'Violou a indicação de linha de proibição', '6');
INSERT INTO `VIOLATION_CODE` VALUES ('6', '6', 'Violou a indicação de sinal de proibição', '5');
INSERT INTO `VIOLATION_CODE` VALUES ('7', '7', 'Excesso de velocidade', '1');
INSERT INTO `VIOLATION_CODE` VALUES ('8', '8', 'Condução em direcção não permitida pelos regulamentos de trânsito', '7');
INSERT INTO `VIOLATION_CODE` VALUES ('9', '9', 'Regulamentos da violação de restrição com número', '3');
INSERT INTO `VIOLATION_CODE` VALUES ('10', '10', 'Excesso de velocidade na teste da região', '2');
INSERT INTO `VIOLATION_CODE` VALUES ('11', '11', 'Violou os regulamentos de acesso de restrição', '4');

-- ----------------------------
-- Table structure for VIOLATION_HOTSPOT
-- ----------------------------
DROP TABLE IF EXISTS `VIOLATION_HOTSPOT`;
CREATE TABLE `VIOLATION_HOTSPOT` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `ROAD_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '路口点位ID',
  `TOTAL_VIOLATION` int(11) DEFAULT NULL COMMENT '历史违章总数',
  `TOTAL_RUN_RED_LIGHT` char(10) DEFAULT NULL COMMENT '历史闯红灯总数',
  `TOTAL_OVER_SPEED` char(10) DEFAULT NULL COMMENT '历史超数总数',
  `TOTAL_WRONG_DIRECTION` char(10) DEFAULT NULL COMMENT '历史逆行总数',
  `TOTAL_WRONG_POSITION` char(10) DEFAULT NULL COMMENT '历史压线类违停数量(压虚拟车道线/压停车线进入违停区域/压黄线/压分道线/不按规定车道行驶)',
  `TOTAL_VIOLATE_NUM_LIMIT` char(10) DEFAULT NULL COMMENT '历史违反限号总数',
  `TOTAL_VIOLATE_TIME_LIMIT` char(10) DEFAULT NULL COMMENT '历史违反时间限行总数',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT=' 违章多发地点分析';

-- ----------------------------
-- Records of VIOLATION_HOTSPOT
-- ----------------------------

-- ----------------------------
-- Table structure for VIOLATION_RECORD
-- ----------------------------
DROP TABLE IF EXISTS `VIOLATION_RECORD`;
CREATE TABLE `VIOLATION_RECORD` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `PLATE_NUMBER` char(12) DEFAULT NULL COMMENT '车牌号',
  `ROAD_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '路口点位ID',
  `VIOLATION_CODE` char(8) DEFAULT NULL COMMENT '违章代码',
  `OCCUR_TIME` datetime DEFAULT NULL COMMENT '发生时间',
  `ROADWAY_NO` int(11) DEFAULT NULL COMMENT '车道编号',
  `ROADWAY_NAME` varchar(64) DEFAULT NULL COMMENT '车道名称',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态（0: 未确认; 1: 已生成处罚记录; 2:不予处罚; 3: 撤销处罚）',
  `PUSH_STATUS` tinyint(4) DEFAULT NULL COMMENT '是否已推送至交通处罚系统 (0: 未推送; 1: 推送成功; 2: 推送失败)',
  `NOTE` varchar(512) DEFAULT NULL COMMENT '备注',
  `DEVICE_ID` bigint(20) DEFAULT NULL COMMENT '抓拍设备ID',
  `PASSEDBY_VEHICLE_ID` bigint(20) DEFAULT NULL COMMENT '过车记录ID',
  `DISPOSE_USER_ID` bigint(20) DEFAULT NULL COMMENT '处理人ID',
  `DISPOSE_TIME` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '处理时间',
  `DETAIL` varchar(512) DEFAULT NULL,
  `FTP_URL` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_11` (`DEVICE_ID`),
  KEY `FK_Reference_25` (`PASSEDBY_VEHICLE_ID`),
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`DEVICE_ID`) REFERENCES `DEVICE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='违章记录';

-- ----------------------------
-- Records of VIOLATION_RECORD
-- ----------------------------

-- ----------------------------
-- Table structure for VIOLATION_RECORD_DISPOSE_LOG
-- ----------------------------
DROP TABLE IF EXISTS `VIOLATION_RECORD_DISPOSE_LOG`;
CREATE TABLE `VIOLATION_RECORD_DISPOSE_LOG` (
  `ID` bigint(20) NOT NULL COMMENT 'ID',
  `VIOLATION_RECORD_ID` bigint(20) NOT NULL COMMENT '违章记录ID',
  `PREVIOUS_STATUS` int(11) DEFAULT NULL COMMENT '前一个状态',
  `PREVIOUS_NOTE` varchar(512) DEFAULT NULL COMMENT '处理前备注',
  `AFTER_STATUS` int(11) DEFAULT NULL COMMENT '处理后状态',
  `AFTER_NOTE` varchar(512) DEFAULT NULL COMMENT '处理后备注',
  `DISPOSE_USER_ID` bigint(20) DEFAULT NULL COMMENT '处理人ID',
  `DISPOSE_USER_NAME` varchar(64) DEFAULT NULL COMMENT '处理人名称',
  `DISPOSE_TIME` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '处理时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='违章记录处理日志';

-- ----------------------------
-- Records of VIOLATION_RECORD_DISPOSE_LOG
-- ----------------------------

-- ----------------------------
-- Table structure for VOICE_FILE
-- ----------------------------
DROP TABLE IF EXISTS `VOICE_FILE`;
CREATE TABLE `VOICE_FILE` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `NAME` varchar(128) DEFAULT NULL COMMENT '声音名称',
  `FILE_PATH` varchar(256) DEFAULT NULL COMMENT '声音文件存储路径',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='声音文件';

-- ----------------------------
-- Records of VOICE_FILE
-- ----------------------------
INSERT INTO `VOICE_FILE` VALUES ('1', '声音1', '/mnt/data/voice');

-- ----------------------------
-- Table structure for WHITE_LIST
-- ----------------------------
DROP TABLE IF EXISTS `WHITE_LIST`;
CREATE TABLE `WHITE_LIST` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `PLATE_NUMBER` char(12) DEFAULT NULL COMMENT '车牌号',
  `VEHICLE_BRAND` int(11) DEFAULT NULL COMMENT '车牌品牌',
  `VEHICLE_COLOR` char(11) DEFAULT NULL COMMENT '车辆颜色白色(A)、灰色(B)、黄色(C)、粉色(D)、红色(E)、紫色(F)、绿色(G)、蓝色(H)、棕色(I)、黑色(J)、未识别(K)、其他(Z)',
  `PLATE_COLOR` int(11) DEFAULT NULL COMMENT '车牌颜色',
  `TYPE` int(11) DEFAULT NULL COMMENT '类型',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DESCRIPTION` varchar(1024) DEFAULT NULL COMMENT '描述',
  `DELETED` tinyint(4) DEFAULT NULL,
  `STATUS` tinyint(4) DEFAULT NULL COMMENT '是否启用0关闭，1开启',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='白名单';

-- ----------------------------
-- Records of WHITE_LIST
-- ----------------------------

-- ----------------------------
-- Table structure for WHITE_LIST_TYPE_DIC
-- ----------------------------
DROP TABLE IF EXISTS `WHITE_LIST_TYPE_DIC`;
CREATE TABLE `WHITE_LIST_TYPE_DIC` (
  `TYPE` int(11) NOT NULL COMMENT '类型编码(0-警用车辆，1-军用车辆，2-政府特殊车辆)',
  `NAME` varchar(64) DEFAULT NULL COMMENT '类型名称',
  PRIMARY KEY (`TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆白名单';

-- ----------------------------
-- Records of WHITE_LIST_TYPE_DIC
-- ----------------------------
INSERT INTO `WHITE_LIST_TYPE_DIC` VALUES ('0', 'Police vehicle');
INSERT INTO `WHITE_LIST_TYPE_DIC` VALUES ('1', 'Military vehicle');
INSERT INTO `WHITE_LIST_TYPE_DIC` VALUES ('2', 'Government special vehicle');

-- ----------------------------
-- Table structure for WHITE_VEHICLE_RECORD
-- ----------------------------
DROP TABLE IF EXISTS `WHITE_VEHICLE_RECORD`;
CREATE TABLE `WHITE_VEHICLE_RECORD` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `PLATE_NUMBER` char(12) DEFAULT NULL COMMENT '车牌号',
  `ROAD_CROSS_POINT_ID` bigint(20) DEFAULT NULL COMMENT '路口点位ID',
  `DEVICE_ID` bigint(20) DEFAULT NULL COMMENT '设备ID',
  `CAPTURE_TIME` datetime DEFAULT NULL COMMENT '抓拍时间',
  `ROADWAY_NO` int(11) DEFAULT NULL COMMENT '车道编号',
  `WHITE_LIST_TYPE` bigint(20) DEFAULT NULL COMMENT '白名单类型',
  `PASSEDBY_VEHICLE_ID` bigint(20) DEFAULT NULL COMMENT '过车记录ID',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_26` (`PASSEDBY_VEHICLE_ID`),
  KEY `FK_Reference_30` (`DEVICE_ID`),
  CONSTRAINT `FK_Reference_30` FOREIGN KEY (`DEVICE_ID`) REFERENCES `DEVICE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='白名单过车记录';

-- ----------------------------
-- Records of WHITE_VEHICLE_RECORD
-- ----------------------------

-- ----------------------------
-- Procedure structure for inserpass20190613
-- ----------------------------
DROP PROCEDURE IF EXISTS `inserpass20190613`;
DELIMITER ;;
CREATE DEFINER=`system`@`10.15%` PROCEDURE `inserpass20190613`()
BEGIN
		-- 声明变量
	  declare v int default 0;
    declare j int default 1;
	  declare deviceid bigint;
	  declare roadId bigint;
		declare plateNum varchar(20);
    declare deviceName varchar(20);
    declare roadname varchar(20);
		declare cityname varchar(20);
		declare areaname varchar(20);
	  declare cityId bigint;
		declare areaId bigint;
-- 开启事务
START TRANSACTION;
	while v <= 800000 do
				-- 随机生成设备id
				SET deviceid = (SELECT 100100029+FLOOR(RAND()*61));
        -- 通过SQL动态给变量赋值
				SELECT d.`NAME` deviceName,d.ROAD_CROSS_POINT_ID roadId,r.`NAME` roadname,a.ID areaId,a.`NAME` areaname,c.ID cityId ,c.`NAME` cityname 
				INTO deviceName,roadId,roadname,areaId,areaname,cityId,cityname
				FROM DEVICE d 
				LEFT JOIN ROAD_CROSS_POINT r ON r.ID = d.ROAD_CROSS_POINT_ID
				LEFT JOIN AREA a ON a.ID = r.AREA_ID
				LEFT JOIN CITY c ON c.ID = a.CITY_ID
				WHERE d.ID = deviceid;

				-- 构造车牌号码
        IF cityId = 666515420643328 THEN
					SET plateNum = '京A';
				ELSEIF cityId = 670201525862400 THEN
					SET plateNum = '渝A';
				ELSEIF cityId = 605046716055552 THEN
					SET plateNum = '川A';
				ELSEIF cityId = 651784405614592 THEN
					SET plateNum = '川B';
        END IF;

				-- 插入过车表
			  INSERT INTO PASSEDBY_VEHICLE (DRIVE_STATUS,CAPTURE_TIME,PLATE_NUMBER,VEHICLE_COLOR,PLATE_COLOR,VEHICLE_BRAND,VEHICLE_TYPE,
			  SPEED,ROADWAY_NO,ROADWAY_NAME,ROAD_CROSS_POINT_ID,DEVICE_ID,DIRECTION_CODE,PLATE_PHOTO_ID,PHOTO_FASTDFS_URL,CITY_NAME,AREA_NAME,
            ROAD_NAME,CITY_ID,AREA_ID,DEVICE_NAME)
        values (0,DATE_ADD('2018-03-29 00:00:00',  INTERVAL  FLOOR(1 + (RAND() *24 ))   HOUR ),concat(plateNum,(CEILING(RAND()*10000))),1,2,0,1,
          floor(RAND() * 130),floor(RAND() * 5),'roadwayname',roadId,deviceid,1,null,'group1/M00/14/11/wKgTF1z3gGyAZTfmAB0dWRQIHso220.jpg',cityname,areaname,
          roadname,cityId,areaId,deviceName);

         set v = v + 1;
         set j = j + 1;
			-- 10000次提交一次
			if j = 10000 then
			   set j = 0 ;
			   commit;
			end if;
	end while ;
   commit;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for INSERT_PASS
-- ----------------------------
DROP PROCEDURE IF EXISTS `INSERT_PASS`;
DELIMITER ;;
CREATE DEFINER=`system`@`192.168.%` PROCEDURE `INSERT_PASS`()
BEGIN
	  declare v int default 0;
    declare j int default 1;
	  declare deviceid bigint;
	  declare roadId bigint default 1;
		declare plateNum varchar(20);
		declare cityname varchar(20);
		declare areaname varchar(20);
	  declare cityId bigint;
		declare areaId bigint;
START TRANSACTION;
	while v<=60000000 do
     SET roadId=(SELECT 1+FLOOR(RAND()*60));
			 IF roadId<=20 THEN
				 SET deviceid=(SELECT 10001+FLOOR(RAND()*30));
				 SET plateNum = '京P';
				 SET cityname = '北京';
				 SET areaname = '朝阳区';
				 SET cityId = 306504363130880;
				 SET areaId = 306504945238016;
			 ELSEIF  roadId <=40 THEN
				 SET deviceid=(SELECT 10031+FLOOR(RAND()*30));
				 SET plateNum = '川A';
				 SET cityname = '成都';
				 SET areaname = '青羊区';
				 SET cityId = 306504618377216;		
				 SET areaId = 306505214214144;
			 ELSEIF   roadId <=60 THEN
				 SET deviceid=(SELECT 10061+FLOOR(RAND()*20));
				 SET plateNum = '罗A';
				 SET cityname = '罗安达';
				 SET areaname = '罗安达区';
				 SET cityId = 306504177139712;		
				 SET areaId = 306505411526656;
			 END IF;
			  INSERT INTO PASSEDBY_VEHICLE (DRIVE_STATUS,CAPTURE_TIME,PLATE_NUMBER,VEHICLE_COLOR,PLATE_COLOR,VEHICLE_BRAND,VEHICLE_TYPE,
			  SPEED,ROADWAY_NO,ROADWAY_NAME,ROAD_CROSS_POINT_ID,DEVICE_ID,DIRECTION_CODE,PLATE_PHOTO_ID,PHOTO_FASTDFS_URL,CITY_NAME,AREA_NAME,
            ROAD_NAME,CITY_ID,AREA_ID,DEVICE_NAME)
        values (0,DATE_ADD('2018-04-01 00:00:00',  INTERVAL  FLOOR(1 + (RAND() *720 ))   HOUR ),concat(plateNum,(CEILING(RAND()*100000))),'Z',2,0,1,
          floor(RAND() * 130),floor(RAND() * 5),'roadname',roadId,deviceid,1,null,'group1/M00/00/00/wKgTgluN7zOAEbX1AAJkyNyzLIc119.jpg',cityname,areaname,
          concat('路口',roadId),cityId,areaId,deviceid);

         set v = v + 1;
         set j = j + 1;
			if j = 10000 then
			   set j = 0 ;
			   commit;
			end if;
	end
	while ;
   commit;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for INSERTDEVICE
-- ----------------------------
DROP PROCEDURE IF EXISTS `INSERTDEVICE`;
DELIMITER ;;
CREATE DEFINER=`system`@`192.168.%` PROCEDURE `INSERTDEVICE`()
BEGIN
	#Routine body goes here...
	declare num int;
	declare roadId bigint;
	declare lon DOUBLE;
	DECLARE la DOUBLE;
	set num =10061 ;
	while num <=10080 do
  SET roadId=(SELECT 41+FLOOR(RAND()*20));
  SET lon=(SELECT -(8.8+RAND()*0.1));
  SET la=(SELECT (13.2+RAND()*0.1));
	INSERT INTO `DEVICE` VALUES (num, num, '0', lon, la, '1', 'MS', '2018-08-20 00:00:00', '0', '0', '',
  '306700244221952',roadId, null, null, null, null, null, null, null, num,'2018-08-20 00:00:00');
  INSERT INTO `DEVICE_STATUS_LOG` VALUES (num, '2018-08-20 00:00:00', '0', num, null, '1', '2018-08-20 00:00:00',0);
	set num = num + 1 ;
	end
	while ;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PARTITIONBYDAY
-- ----------------------------
DROP PROCEDURE IF EXISTS `PARTITIONBYDAY`;
DELIMITER ;;
CREATE DEFINER=`system`@`10.15%` PROCEDURE `PARTITIONBYDAY`()
BEGIN  
  DECLARE v_sysdate date;  
  DECLARE v_mindate date;  
  DECLARE v_maxdate date;  
  DECLARE v_pt varchar(20);  
  DECLARE v_maxval varchar(20);  
  DECLARE i int;  
    
  /*增加新分区*/  
  SELECT max(cast(replace(partition_description, '''', '') AS date)) AS val  
  INTO   v_maxdate  
  FROM   INFORMATION_SCHEMA.PARTITIONS  
  WHERE  TABLE_NAME = 'PASSEDBY_VEHICLE' AND TABLE_SCHEMA = 'TRUNK';  
    
  set v_sysdate = sysdate();  
    
  WHILE v_maxdate <= (v_sysdate + INTERVAL 102 DAY) DO  
    SET v_pt = date_format(v_maxdate ,'%Y%m%d');  
    SET v_maxval = date_format(v_maxdate + INTERVAL 1 DAY, '%Y-%m-%d');  
    SET @sql = concat('alter table PASSEDBY_VEHICLE add partition (partition p', v_pt, ' values less than(''', v_maxval, '''))');  
    -- SELECT @sql;  
    PREPARE stmt FROM @sql;  
    EXECUTE stmt;  
    DEALLOCATE PREPARE stmt;  
    SET v_maxdate = v_maxdate + INTERVAL 1 DAY;  
  END WHILE;  
  
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for TIME_INDEX_HOUR
-- ----------------------------
DROP PROCEDURE IF EXISTS `TIME_INDEX_HOUR`;
DELIMITER ;;
CREATE DEFINER=`system`@`10.15%` PROCEDURE `TIME_INDEX_HOUR`()
BEGIN
	#Routine body goes here...
	declare num int;
	set num =1 ;
	while num <=9000 do
			INSERT INTO TIME_INDEX_HOUR  
	values
			(num,null) ;
	set num = num + 1 ;
	end
	while ;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for TONGXINGCHE1
-- ----------------------------
DROP PROCEDURE IF EXISTS `TONGXINGCHE1`;
DELIMITER ;;
CREATE DEFINER=`system`@`%` PROCEDURE `TONGXINGCHE1`()
BEGIN
	#Routine body goes here...
	  DECLARE v int default 0;
		DECLARE rid bigint;
		DECLARE pn varchar(20); 
		START TRANSACTION;
	while v<8 do
	    SET rid=230261964637093+v*10;
			SET pn=(SELECT concat('南C',(CEILING(RAND()*100000))));
			INSERT INTO `Trunk`.`PASSEDBY_VEHICLE` (`ID`, `DRIVE_STATUS`, `CAPTURE_TIME`, `PLATE_NUMBER`, `VEHICLE_COLOR`, `PLATE_COLOR`, `VEHICLE_BRAND`, `VEHICLE_TYPE`, `SPEED`, `ROADWAY_NO`, `ROADWAY_NAME`, `ROAD_CROSS_POINT_ID`, `DEVICE_ID`, `DIRECTION_CODE`, `PLATE_PHOTO_ID`, `PHOTO_ID`, `CITY_NAME`, `AREA_NAME`, `ROAD_NAME`, `CITY_ID`, `AREA_ID`, `DEVICE_NAME`) VALUES (rid+1, '0', '2018-06-13 13:25:00', pn, 'Z', '2', '0', '1', '113', '2', 'roadname', '21', '1000000101', '1', NULL, '160955599159296', '南充市', '南山区', '路口21', '190253031309312', '190253314768896', '1000000101');
			INSERT INTO `Trunk`.`PASSEDBY_VEHICLE` (`ID`, `DRIVE_STATUS`, `CAPTURE_TIME`, `PLATE_NUMBER`, `VEHICLE_COLOR`, `PLATE_COLOR`, `VEHICLE_BRAND`, `VEHICLE_TYPE`, `SPEED`, `ROADWAY_NO`, `ROADWAY_NAME`, `ROAD_CROSS_POINT_ID`, `DEVICE_ID`, `DIRECTION_CODE`, `PLATE_PHOTO_ID`, `PHOTO_ID`, `CITY_NAME`, `AREA_NAME`, `ROAD_NAME`, `CITY_ID`, `AREA_ID`, `DEVICE_NAME`) VALUES (rid+2, '0', '2018-06-13 13:56:00', pn, 'Z', '2', '0', '1', '80', '3', 'roadname', '29', '1000000102', '1', NULL, '160955599159296', '南充市', '南山区', '路口29', '190253031309312', '190253314768896', '1000000102');
			INSERT INTO `Trunk`.`PASSEDBY_VEHICLE` (`ID`, `DRIVE_STATUS`, `CAPTURE_TIME`, `PLATE_NUMBER`, `VEHICLE_COLOR`, `PLATE_COLOR`, `VEHICLE_BRAND`, `VEHICLE_TYPE`, `SPEED`, `ROADWAY_NO`, `ROADWAY_NAME`, `ROAD_CROSS_POINT_ID`, `DEVICE_ID`, `DIRECTION_CODE`, `PLATE_PHOTO_ID`, `PHOTO_ID`, `CITY_NAME`, `AREA_NAME`, `ROAD_NAME`, `CITY_ID`, `AREA_ID`, `DEVICE_NAME`) VALUES (rid+3, '0', '2018-06-13 14:13:00', pn, 'Z', '2', '0', '1', '112', '0', 'roadname', '26', '1000000104', '1', NULL, '160955599159296', '南充市', '南山区', '路口26', '190253031309312', '190253314768896', '1000000104');
			INSERT INTO `Trunk`.`PASSEDBY_VEHICLE` (`ID`, `DRIVE_STATUS`, `CAPTURE_TIME`, `PLATE_NUMBER`, `VEHICLE_COLOR`, `PLATE_COLOR`, `VEHICLE_BRAND`, `VEHICLE_TYPE`, `SPEED`, `ROADWAY_NO`, `ROADWAY_NAME`, `ROAD_CROSS_POINT_ID`, `DEVICE_ID`, `DIRECTION_CODE`, `PLATE_PHOTO_ID`, `PHOTO_ID`, `CITY_NAME`, `AREA_NAME`, `ROAD_NAME`, `CITY_ID`, `AREA_ID`, `DEVICE_NAME`) VALUES (rid+4, '0', '2018-06-13 14:28:00', pn, 'Z', '2', '0', '1', '109', '4', 'roadname', '23', '1000000105', '1', NULL, '160955599159296', '南充市', '南山区', '路口23', '190253031309312', '190253314768896', '1000000105');
			INSERT INTO `Trunk`.`PASSEDBY_VEHICLE` (`ID`, `DRIVE_STATUS`, `CAPTURE_TIME`, `PLATE_NUMBER`, `VEHICLE_COLOR`, `PLATE_COLOR`, `VEHICLE_BRAND`, `VEHICLE_TYPE`, `SPEED`, `ROADWAY_NO`, `ROADWAY_NAME`, `ROAD_CROSS_POINT_ID`, `DEVICE_ID`, `DIRECTION_CODE`, `PLATE_PHOTO_ID`, `PHOTO_ID`, `CITY_NAME`, `AREA_NAME`, `ROAD_NAME`, `CITY_ID`, `AREA_ID`, `DEVICE_NAME`) VALUES (rid+5, '0', '2018-06-13 14:51:00', pn, 'Z', '2', '0', '1', '32', '1', 'roadname', '22', '1000000106', '1', NULL, '160955599159296', '南充市', '南山区', '路口22', '190253031309312', '190253314768896', '1000000106');
			INSERT INTO `Trunk`.`PASSEDBY_VEHICLE` (`ID`, `DRIVE_STATUS`, `CAPTURE_TIME`, `PLATE_NUMBER`, `VEHICLE_COLOR`, `PLATE_COLOR`, `VEHICLE_BRAND`, `VEHICLE_TYPE`, `SPEED`, `ROADWAY_NO`, `ROADWAY_NAME`, `ROAD_CROSS_POINT_ID`, `DEVICE_ID`, `DIRECTION_CODE`, `PLATE_PHOTO_ID`, `PHOTO_ID`, `CITY_NAME`, `AREA_NAME`, `ROAD_NAME`, `CITY_ID`, `AREA_ID`, `DEVICE_NAME`) VALUES (rid+6, '0', '2018-06-13 15:14:00', pn, 'Z', '2', '0', '1', '14', '2', 'roadname', '28', '1000000107', '1', NULL, '160955599159296', '南充市', '南山区', '路口28', '190253031309312', '190253314768896', '1000000107');
			INSERT INTO `Trunk`.`PASSEDBY_VEHICLE` (`ID`, `DRIVE_STATUS`, `CAPTURE_TIME`, `PLATE_NUMBER`, `VEHICLE_COLOR`, `PLATE_COLOR`, `VEHICLE_BRAND`, `VEHICLE_TYPE`, `SPEED`, `ROADWAY_NO`, `ROADWAY_NAME`, `ROAD_CROSS_POINT_ID`, `DEVICE_ID`, `DIRECTION_CODE`, `PLATE_PHOTO_ID`, `PHOTO_ID`, `CITY_NAME`, `AREA_NAME`, `ROAD_NAME`, `CITY_ID`, `AREA_ID`, `DEVICE_NAME`) VALUES (rid+7, '0', '2018-06-13 15:39:00', pn, 'Z', '2', '0', '1', '115', '0', 'roadname', '27', '1000000108', '1', NULL, '160955599159296', '南充市', '南山区', '路口27', '190253031309312', '190253314768896', '1000000108');
			INSERT INTO `Trunk`.`PASSEDBY_VEHICLE` (`ID`, `DRIVE_STATUS`, `CAPTURE_TIME`, `PLATE_NUMBER`, `VEHICLE_COLOR`, `PLATE_COLOR`, `VEHICLE_BRAND`, `VEHICLE_TYPE`, `SPEED`, `ROADWAY_NO`, `ROADWAY_NAME`, `ROAD_CROSS_POINT_ID`, `DEVICE_ID`, `DIRECTION_CODE`, `PLATE_PHOTO_ID`, `PHOTO_ID`, `CITY_NAME`, `AREA_NAME`, `ROAD_NAME`, `CITY_ID`, `AREA_ID`, `DEVICE_NAME`) VALUES (rid+8, '0', '2018-06-13 15:44:00', pn, 'Z', '2', '0', '1', '118', '1', 'roadname', '22', '1000000109', '1', NULL, '160955599159296', '南充市', '南山区', '路口22', '190253031309312', '190253314768896', '1000000109');
			INSERT INTO `Trunk`.`PASSEDBY_VEHICLE` (`ID`, `DRIVE_STATUS`, `CAPTURE_TIME`, `PLATE_NUMBER`, `VEHICLE_COLOR`, `PLATE_COLOR`, `VEHICLE_BRAND`, `VEHICLE_TYPE`, `SPEED`, `ROADWAY_NO`, `ROADWAY_NAME`, `ROAD_CROSS_POINT_ID`, `DEVICE_ID`, `DIRECTION_CODE`, `PLATE_PHOTO_ID`, `PHOTO_ID`, `CITY_NAME`, `AREA_NAME`, `ROAD_NAME`, `CITY_ID`, `AREA_ID`, `DEVICE_NAME`) VALUES (rid+9, '0', '2018-06-13 15:55:00', pn, 'Z', '2', '0', '1', '48', '1', 'roadname', '25', '1000000110', '1', NULL, '160955599159296', '南充市', '南山区', '路口25', '190253031309312', '190253314768896', '1000000110');
			INSERT INTO `Trunk`.`PASSEDBY_VEHICLE` (`ID`, `DRIVE_STATUS`, `CAPTURE_TIME`, `PLATE_NUMBER`, `VEHICLE_COLOR`, `PLATE_COLOR`, `VEHICLE_BRAND`, `VEHICLE_TYPE`, `SPEED`, `ROADWAY_NO`, `ROADWAY_NAME`, `ROAD_CROSS_POINT_ID`, `DEVICE_ID`, `DIRECTION_CODE`, `PLATE_PHOTO_ID`, `PHOTO_ID`, `CITY_NAME`, `AREA_NAME`, `ROAD_NAME`, `CITY_ID`, `AREA_ID`, `DEVICE_NAME`) VALUES (rid+10, '0', '2018-06-13 16:38:00', pn, 'Z', '2', '0', '1', '101', '0', 'roadname', '30', '100000111', '1', NULL, '160955599159296', '南充市', '南山区', '路口30', '190253031309312', '190253314768896', '100000111');
			COMMIT;         
			SET v = v + 1;

	end
	while ;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for TONGXINGCHE2
-- ----------------------------
DROP PROCEDURE IF EXISTS `TONGXINGCHE2`;
DELIMITER ;;
CREATE DEFINER=`system`@`%` PROCEDURE `TONGXINGCHE2`()
BEGIN
	#Routine body goes here...
	  declare v int default 0;
    declare j int default 1;
	declare deviceid bigint;
	declare roadId bigint;
START TRANSACTION;
	while v<1 do
     SET roadId= 30;
			SET deviceid=100000113;
			  INSERT INTO PASSEDBY_VEHICLE (DRIVE_STATUS,CAPTURE_TIME,PLATE_NUMBER,VEHICLE_COLOR,PLATE_COLOR,VEHICLE_BRAND,VEHICLE_TYPE,
			SPEED,ROADWAY_NO,ROADWAY_NAME,ROAD_CROSS_POINT_ID,DEVICE_ID,DIRECTION_CODE,PLATE_PHOTO_ID,PHOTO_ID,CITY_NAME,AREA_NAME,
            ROAD_NAME,CITY_ID,AREA_ID,DEVICE_NAME)
        values (0,'2018-06-13 20:30:00','南C111111','Z',2,0,1,
          floor(RAND() * 130),floor(RAND() * 5),'roadname',roadId,deviceid,1,null,160955599159296,'南充市','南山区',
          concat('路口',roadId),190253031309312,190253314768896,deviceid);
         set v = v + 1;
         set j = j + 1;
			if j = 10000 then
			   set j = 0 ;
			   commit;
			end if;
	end
	while ;
   commit;
END
;;
DELIMITER ;
