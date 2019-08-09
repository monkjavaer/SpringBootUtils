/*
Navicat MySQL Data Transfer

Source Server         : 192.168.19.139-车辆分析
Source Server Version : 50638
Source Host           : 192.168.19.139:3306
Source Database       : orbit

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2019-04-22 16:22:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for orbit_control_alarm
-- ----------------------------
DROP TABLE IF EXISTS `orbit_control_alarm`;
CREATE TABLE `orbit_control_alarm` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `TASK_ID` varchar(32) DEFAULT NULL COMMENT '任务ID',
  `DEVICE_ID` varchar(32) DEFAULT NULL COMMENT '抓拍设备ID',
  `ROADWAY_NO` int(11) DEFAULT NULL COMMENT '车道编号',
  `ROADWAY_NAME` varchar(64) DEFAULT NULL COMMENT '车道名称',
  `CAPTURE_TIME` datetime DEFAULT NULL COMMENT '抓拍时间',
  `PLATE_NUMBER` varchar(12) DEFAULT NULL COMMENT '车牌号',
  `ALARM_DESCRIPTION` varchar(1000) DEFAULT NULL COMMENT '预警信息',
  `ALARM_TIME` datetime DEFAULT NULL COMMENT '预警时间',
  `STATUS` int(11) NOT NULL DEFAULT '0' COMMENT '处置状态(1: 未处置; 2: 已推送; 3不予处理 )',
  `BLACKLIST_ID` varchar(32) DEFAULT NULL COMMENT '黑名单记录ID',
  `BLACKLIST_TYPE` varchar(32) DEFAULT NULL COMMENT '黑名单类型',
  `PHOTO_FASTDFS_URL` varchar(128) DEFAULT NULL COMMENT '车辆图片在FastDFS的URL',
  `CITY_NAME` varchar(64) DEFAULT NULL,
  `AREA_NAME` varchar(64) DEFAULT NULL,
  `ROAD_NAME` varchar(64) DEFAULT NULL,
  `CITY_ID` varchar(32) DEFAULT NULL COMMENT '城市ID',
  `AREA_ID` varchar(32) DEFAULT NULL COMMENT '区域ID',
  `ROAD_CROSS_POINT_ID` varchar(32) DEFAULT NULL COMMENT '路口点位ID',
  `PASS_VEHICLE_ID` varchar(32) DEFAULT NULL COMMENT '过车记录id',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预警表';

-- ----------------------------
-- Records of orbit_control_alarm
-- ----------------------------

-- ----------------------------
-- Table structure for orbit_control_alarm_voice
-- ----------------------------
DROP TABLE IF EXISTS `orbit_control_alarm_voice`;
CREATE TABLE `orbit_control_alarm_voice` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `LEVEL` int(4) NOT NULL DEFAULT '0' COMMENT '报警级别（1、一级，2、二级，3、三级，4、四级',
  `BELL` int(4) NOT NULL DEFAULT '1' COMMENT '报警铃声（1、明亮，2、高昂，3、平和，4、低沉）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报警声音';

-- ----------------------------
-- Records of orbit_control_alarm_voice
-- ----------------------------
INSERT INTO `orbit_control_alarm_voice` VALUES ('80A21264F47D41FAB06D0231363D7921', '1', '1');
INSERT INTO `orbit_control_alarm_voice` VALUES ('80A21264F47D41FAB06D0231363D7922', '2', '2');
INSERT INTO `orbit_control_alarm_voice` VALUES ('80A21264F47D41FAB06D0231363D7923', '3', '3');
INSERT INTO `orbit_control_alarm_voice` VALUES ('80A21264F47D41FAB06D0231363D7924', '4', '4');

-- ----------------------------
-- Table structure for orbit_control_blacklist
-- ----------------------------
DROP TABLE IF EXISTS `orbit_control_blacklist`;
CREATE TABLE `orbit_control_blacklist` (
  `VID` varchar(32) NOT NULL COMMENT '主键',
  `PLATE_NUMBER` varchar(12) DEFAULT NULL COMMENT '车牌号',
  `VEHICLE_TYPE` varchar(11) DEFAULT NULL,
  `VEHICLE_BRAND` varchar(11) DEFAULT NULL COMMENT '车辆品牌',
  `VEHICLE_BRAND_CHILD` varchar(11) DEFAULT NULL COMMENT '车辆子品牌',
  `VEHICLE_BRAND_CHILD_YEAR` varchar(11) DEFAULT NULL COMMENT '车辆年份 ',
  `PLATE_COLOR` varchar(11) DEFAULT NULL COMMENT '车牌颜色',
  `VEHICLE_COLOR` varchar(11) DEFAULT NULL COMMENT '车辆颜色',
  `FEATURE` varchar(128) DEFAULT NULL COMMENT '车辆特征',
  `TYPE` varchar(32) DEFAULT NULL COMMENT '黑名单类型',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(1024) DEFAULT NULL COMMENT '概况',
  `DELETED` int(4) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  `STATUS` int(4) NOT NULL DEFAULT '1' COMMENT '是否启用0关闭，1开启',
  `PHOTO_URL` varchar(128) DEFAULT NULL,
  `CASE_TIME` datetime DEFAULT NULL COMMENT '案发时间',
  `CASE_ADRESS` varchar(128) DEFAULT NULL COMMENT '案发地点',
  PRIMARY KEY (`VID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆黑名单';

-- ----------------------------
-- Records of orbit_control_blacklist
-- ----------------------------

-- ----------------------------
-- Table structure for orbit_control_blacklist_type
-- ----------------------------
DROP TABLE IF EXISTS `orbit_control_blacklist_type`;
CREATE TABLE `orbit_control_blacklist_type` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `NAME` varchar(64) DEFAULT NULL COMMENT '类型名称',
  `LEVEL` int(4) NOT NULL DEFAULT '1' COMMENT '报警级别（1：严重，2高，3中，4低）',
  `BELL` int(4) NOT NULL DEFAULT '1' COMMENT '报警铃声(1.明亮，2，高昂，3.平和，4.低层)',
  `TYPE` varchar(32) DEFAULT NULL COMMENT '类型编码(内置1-套牌分析,2-同行车分析,3-被盗抢车,9-其他)',
  `DELETED` int(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='黑名单类型字典';

-- ----------------------------
-- Records of orbit_control_blacklist_type
-- ----------------------------

-- ----------------------------
-- Table structure for orbit_control_task
-- ----------------------------
DROP TABLE IF EXISTS `orbit_control_task`;
CREATE TABLE `orbit_control_task` (
  `ID` varchar(32) NOT NULL COMMENT '任务id',
  `TASK_NAME` varchar(64) NOT NULL COMMENT '任务名称',
  `STATUS` int(4) NOT NULL COMMENT '任务状态（1，布控中，2结束，3，审批中，4，未通过）',
  `TASK_LEVEL` int(4) DEFAULT NULL COMMENT '任务等级（1一级，2二级，3三级，4四级）',
  `TASK_DESCRIPTION` varchar(500) DEFAULT NULL COMMENT '任务简要描述',
  `MESSAGE_TYPE` int(4) NOT NULL COMMENT '联动方式',
  `RECEIVOR_ID` varchar(6145) NOT NULL COMMENT '接收人ID',
  `START_TIME` datetime DEFAULT NULL COMMENT '布控开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '布控结束时间',
  `CREATOR` varchar(64) NOT NULL COMMENT '创建人',
  `CREATOR_ID` varchar(32) NOT NULL COMMENT '创建人id',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `DELETED` int(4) NOT NULL COMMENT '删除标志(1未删除2删除)',
  `APPROVER` varchar(64) DEFAULT NULL COMMENT '审批人',
  `APPROVER_ID` varchar(32) DEFAULT NULL COMMENT '审批人id',
  `APPROVE_TIME` datetime DEFAULT NULL COMMENT '审批时间',
  `APPROVE_NOTE` varchar(512) DEFAULT NULL COMMENT '审批意见',
  `DEVICES` varchar(6145) DEFAULT NULL COMMENT '布控的设备列表，逗号分隔',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='黑名单布控任务表';

-- ----------------------------
-- Records of orbit_control_task
-- ----------------------------

-- ----------------------------
-- Table structure for orbit_control_task_detail
-- ----------------------------
DROP TABLE IF EXISTS `orbit_control_task_detail`;
CREATE TABLE `orbit_control_task_detail` (
  `ID` varchar(32) NOT NULL COMMENT '任务id',
  `TASK_ID` varchar(32) NOT NULL COMMENT '任务ID',
  `PLATE_COLOR` varchar(12) DEFAULT NULL COMMENT '车牌号',
  `PLATE_NUMBER` varchar(500) DEFAULT NULL COMMENT '车牌号',
  `FUZZY` int(1) NOT NULL COMMENT '模糊的标识位（1-精确匹配，2-模糊匹配）',
  `VEHICLE_BRAND` varchar(11) DEFAULT NULL COMMENT '车辆品牌',
  `VEHICLE_BRAND_CHILD` varchar(11) DEFAULT NULL COMMENT '车辆子品牌',
  `VEHICLE_COLOR` varchar(11) DEFAULT NULL COMMENT '车辆颜色',
  `VEHICLE_TYPE` varchar(11) DEFAULT NULL COMMENT '车辆类型',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='黑名单布控任务详情表';

-- ----------------------------
-- Records of orbit_control_task_detail
-- ----------------------------

-- ----------------------------
-- Table structure for orbit_control_white_record
-- ----------------------------
DROP TABLE IF EXISTS `orbit_control_white_record`;
CREATE TABLE `orbit_control_white_record` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `PLATE_NUMBER` varchar(12) DEFAULT NULL COMMENT '车牌号',
  `DEVICE_ID` varchar(32) DEFAULT NULL COMMENT '设备ID',
  `CAPTURE_TIME` datetime DEFAULT NULL COMMENT '抓拍时间',
  `ROADWAY_NO` int(11) DEFAULT NULL COMMENT '车道编号',
  `WHITE_LIST_TYPE` varchar(32) DEFAULT NULL COMMENT '白名单类型',
  `PHOTO_FASTDFS_URL` varchar(128) DEFAULT NULL COMMENT '车辆图片在FastDFS的URL',
  `CITY_NAME` varchar(64) DEFAULT NULL,
  `AREA_NAME` varchar(64) DEFAULT NULL,
  `ROAD_NAME` varchar(64) DEFAULT NULL,
  `CITY_ID` varchar(32) DEFAULT NULL COMMENT '城市ID',
  `AREA_ID` varchar(32) DEFAULT NULL COMMENT '区域ID',
  `ROAD_CROSS_POINT_ID` varchar(32) DEFAULT NULL COMMENT '路口点位ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='白名单过车记录';

-- ----------------------------
-- Records of orbit_control_white_record
-- ----------------------------

-- ----------------------------
-- Table structure for orbit_control_whitelist
-- ----------------------------
DROP TABLE IF EXISTS `orbit_control_whitelist`;
CREATE TABLE `orbit_control_whitelist` (
  `VID` varchar(32) NOT NULL COMMENT '主键',
  `PLATE_NUMBER` varchar(12) DEFAULT NULL COMMENT '车牌号',
  `VEHICLE_BRAND` varchar(11) DEFAULT NULL COMMENT '车辆品牌',
  `VEHICLE_BRAND_CHILD` varchar(11) DEFAULT NULL COMMENT '车辆子品牌',
  `VEHICLE_BRAND_CHILD_YEAR` varchar(11) DEFAULT NULL COMMENT '车辆年份 ',
  `PLATE_COLOR` varchar(11) DEFAULT NULL COMMENT '车牌颜色',
  `VEHICLE_COLOR` varchar(11) DEFAULT NULL COMMENT '车辆颜色',
  `TYPE` varchar(32) DEFAULT NULL COMMENT '白名单类型',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DESCRIPTION` varchar(1000) DEFAULT NULL COMMENT '描述',
  `DELETED` int(4) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  `STATUS` int(4) DEFAULT NULL COMMENT '是否启用0关闭，1开启',
  `VEHICLE_TYPE` varchar(11) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `PHOTO_URL` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`VID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='白名单';

-- ----------------------------
-- Records of orbit_control_whitelist
-- ----------------------------

-- ----------------------------
-- Table structure for orbit_control_whitelist_type
-- ----------------------------
DROP TABLE IF EXISTS `orbit_control_whitelist_type`;
CREATE TABLE `orbit_control_whitelist_type` (
  `ID` varchar(32) NOT NULL,
  `TYPE` varchar(32) NOT NULL COMMENT '类型编码(0-警用车辆，1-军用车辆，2-政府特殊车辆)',
  `NAME` varchar(64) DEFAULT NULL COMMENT '类型名称',
  `DELETED` int(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆白名单类型字典';

-- ----------------------------
-- Records of orbit_control_whitelist_type
-- ----------------------------

-- ----------------------------
-- Table structure for orbit_export
-- ----------------------------
DROP TABLE IF EXISTS `orbit_export`;
CREATE TABLE `orbit_export` (
  `CODE` varchar(64) NOT NULL COMMENT '导出模块代码,10-根据路口聚合导出;11-根据品牌聚合;12-综合搜车（开一车一档）;13-综合搜车（未开一车一档）;14-以图搜车根据路口聚合;15-以图搜车根据品牌聚合;16-以图搜车（开一车一档）;17-以图搜车（未开一车一档）;',
  `ZH_NAME` varchar(1000) DEFAULT NULL COMMENT '中文表头',
  `EN_NAME` varchar(1000) DEFAULT NULL COMMENT '英文表头',
  `ES_NAME` varchar(1000) DEFAULT NULL COMMENT '西班牙语表头',
  `REMARK` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='导出表头配置表';

-- ----------------------------
-- Records of orbit_export
-- ----------------------------
INSERT INTO `orbit_export` VALUES ('10', '路名,总数', 'road name,total', 'Nombre de la carretera,total', '10-根据路口聚合导出');
INSERT INTO `orbit_export` VALUES ('11', '品牌名,总数', 'Brand name,total', 'Marca,total', '11-根据品牌聚合');
INSERT INTO `orbit_export` VALUES ('12', '抓拍时间,车牌号,车辆类型,抓拍相机', 'capture time,License plate number,vehicle type,snap camera', 'tiempo de captura,Número de matrícula,tipo de vehículo,cámara instantánea', '12-综合搜车（未开一车一档）');
INSERT INTO `orbit_export` VALUES ('13', '车牌号,总数', 'License plate number,total', 'Número de matrícula,total', '13-综合搜车（开一车一档）');
INSERT INTO `orbit_export` VALUES ('14', '路名,总数', 'road name,total', 'Nombre de la carretera,total', '14-以图搜车根据路口聚合');
INSERT INTO `orbit_export` VALUES ('15', '品牌名,总数', 'Brand name,total', 'Marca,total', '15-以图搜车根据品牌聚合');
INSERT INTO `orbit_export` VALUES ('16', '抓拍时间,车牌号,车辆类型,抓拍相机', 'capture time,License plate number,vehicle type,snap camera', 'tiempo de captura,Número de matrícula,tipo de vehículo,cámara instantánea', '16-以图搜车（开一车一档）');
INSERT INTO `orbit_export` VALUES ('17', '车牌号,总数', 'License plate number,total', 'Número de matrícula,total', '17-以图搜车（未开一车一档）');
INSERT INTO `orbit_export` VALUES ('18', '抓拍时间,车牌号,车辆类型,抓拍相机,图片', 'capture time,License plate number,vehicle type,snap camera,picture', 'tiempo de captura,Número de matrícula,tipo de vehículo,cámara instantánea,picture', '18-综合搜车带图片（未开一车一档）');
INSERT INTO `orbit_export` VALUES ('19', '车牌号,总数,图片', 'License plate number,total,picture', 'Número de matrícula,total,picture', '19-综合搜车带图片（开一车一档）');
INSERT INTO `orbit_export` VALUES ('20', '抓拍时间,车牌号,车辆类型,抓拍相机,图片', 'capture time,License plate number,vehicle type,snap camera,picture', 'tiempo de captura,Número de matrícula,tipo de vehículo,cámara instantánea,picture', '18-综合搜车带图片（未开一车一档）');
INSERT INTO `orbit_export` VALUES ('21', '车牌号,总数,图片', 'License plate number,total,picture', 'Número de matrícula,total,picture', '19-综合搜车带图片（开一车一档）');
INSERT INTO `orbit_export` VALUES ('22', '车牌号,车牌颜色,车辆类型,车辆颜色,品牌,白名单类型', 'License plate number,plate color,vehicle type,vehicle color,brand,whitelist type', 'License plate number,plate color,vehicle type,vehicle color,brand,whitelist type', '20-白名单管理');
INSERT INTO `orbit_export` VALUES ('23', '车牌号,车牌颜色,车辆类型,车辆颜色,品牌,黑名单类型', 'License plate number,plate color,vehicle type,vehicle color,brand,blacklist type', 'License plate number,plate color,vehicle type,vehicle color,brand,blacklist type', '21-黑名单管理');

-- ----------------------------
-- Table structure for orbit_res_area
-- ----------------------------
DROP TABLE IF EXISTS `orbit_res_area`;
CREATE TABLE `orbit_res_area` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `NAME` varchar(64) DEFAULT NULL COMMENT '名称',
  `ADMIN_REGION_CODE` varchar(32) DEFAULT NULL COMMENT '行政区域编码',
  `CITY_ID` varchar(32) DEFAULT NULL COMMENT '城市ID',
  `DELETED` int(4) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='区域';

-- ----------------------------
-- Records of orbit_res_area
-- ----------------------------

-- ----------------------------
-- Table structure for orbit_res_city
-- ----------------------------
DROP TABLE IF EXISTS `orbit_res_city`;
CREATE TABLE `orbit_res_city` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `NAME` varchar(64) DEFAULT NULL COMMENT '城市名称',
  `ADMIN_REGION_CODE` varchar(32) DEFAULT NULL COMMENT '行政区域编码',
  `DELETED` int(4) DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='城市';

-- ----------------------------
-- Records of orbit_res_city
-- ----------------------------

-- ----------------------------
-- Table structure for orbit_res_device
-- ----------------------------
DROP TABLE IF EXISTS `orbit_res_device`;
CREATE TABLE `orbit_res_device` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `NAME` varchar(64) DEFAULT NULL COMMENT '名字',
  `TYPE` int(11) DEFAULT NULL COMMENT '设备类型code',
  `LATITUDE` double DEFAULT NULL COMMENT '经度',
  `LONGITUDE` double DEFAULT NULL COMMENT '纬度',
  `ONLINE` int(4) DEFAULT NULL COMMENT '是否在线（(0:不在线; 1:在线)',
  `MANUFACTURER` int(11) DEFAULT NULL COMMENT '所属厂商代码',
  `DOMAIN_CODE` int(11) DEFAULT NULL COMMENT '域编码',
  `INSTALL_ADDRESS` varchar(512) DEFAULT NULL COMMENT '设备安装详细地址',
  `TERMINAL_ID` varchar(32) DEFAULT NULL COMMENT '智能主机ID',
  `ROAD_CROSS_POINT_ID` varchar(32) DEFAULT NULL COMMENT '路口点位ID',
  `ROADWAY_NUM` int(11) DEFAULT NULL COMMENT '监控的车道数量',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '设备更新时间',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `IP` varchar(32) DEFAULT NULL COMMENT '设备相机ip',
  `PORT` int(11) DEFAULT NULL COMMENT '设备端口',
  `USERNAME` varchar(32) DEFAULT NULL COMMENT '设备登录名',
  `PASSWORD` varchar(64) DEFAULT NULL COMMENT '登录密码',
  `DELETED` int(4) DEFAULT NULL COMMENT '是否已删除',
  `VIDEO_DEVICE_ID` varchar(32) DEFAULT NULL COMMENT '添加设备后视频监控平台返回ID,用于播放',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备表';

-- ----------------------------
-- Records of orbit_res_device
-- ----------------------------

-- ----------------------------
-- Table structure for orbit_res_device_manufacturer
-- ----------------------------
DROP TABLE IF EXISTS `orbit_res_device_manufacturer`;
CREATE TABLE `orbit_res_device_manufacturer` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `CODE` int(4) DEFAULT NULL COMMENT '设备厂商代号',
  `NAME` varchar(64) DEFAULT NULL COMMENT '设备厂商名字',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备厂商';

-- ----------------------------
-- Records of orbit_res_device_manufacturer
-- ----------------------------
INSERT INTO `orbit_res_device_manufacturer` VALUES ('1', '99', 'HKVISION');
INSERT INTO `orbit_res_device_manufacturer` VALUES ('2', '100', 'LD');
INSERT INTO `orbit_res_device_manufacturer` VALUES ('3', '103', 'MS');
INSERT INTO `orbit_res_device_manufacturer` VALUES ('4', '102', 'TIANDY');

-- ----------------------------
-- Table structure for orbit_res_device_type
-- ----------------------------
DROP TABLE IF EXISTS `orbit_res_device_type`;
CREATE TABLE `orbit_res_device_type` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `CODE` int(4) DEFAULT NULL COMMENT '设备类型代号',
  `NAME` varchar(64) DEFAULT NULL COMMENT '设备类型名字',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备类型';

-- ----------------------------
-- Records of orbit_res_device_type
-- ----------------------------
INSERT INTO `orbit_res_device_type` VALUES ('1', '1', 'IPC');

-- ----------------------------
-- Table structure for orbit_res_roadcross_point
-- ----------------------------
DROP TABLE IF EXISTS `orbit_res_roadcross_point`;
CREATE TABLE `orbit_res_roadcross_point` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `NAME` varchar(64) DEFAULT NULL COMMENT '名称',
  `LATITUDE` double DEFAULT NULL COMMENT '纬度',
  `LONGITUDE` double DEFAULT NULL COMMENT '经度',
  `DIRECTION_CODE` int(11) DEFAULT NULL COMMENT '道路方向(可能是双向)',
  `ROADWAY_NUM` int(11) DEFAULT NULL COMMENT '车道数量',
  `AREA_ID` varchar(32) DEFAULT NULL,
  `DELETED` int(4) DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='路口点位';

-- ----------------------------
-- Records of orbit_res_roadcross_point
-- ----------------------------

-- ----------------------------
-- Table structure for orbit_sys_authority
-- ----------------------------
DROP TABLE IF EXISTS `orbit_sys_authority`;
CREATE TABLE `orbit_sys_authority` (
  `ID` varchar(32) NOT NULL COMMENT '权限id',
  `AUTHORITY_NAME` varchar(64) NOT NULL COMMENT '权限名用于国际化映射',
  `ZH_NAME` varchar(64) NOT NULL COMMENT '权限中文名',
  `NODE` varchar(64) NOT NULL COMMENT '权限代码',
  `PARENT_NODE` varchar(64) NOT NULL COMMENT '父级权限',
  `URL` varchar(64) NOT NULL COMMENT '页面的URL',
  `PATH` varchar(64) NOT NULL COMMENT '页面的标识值',
  `ICON` varchar(64) NOT NULL COMMENT '权限模块图标',
  `OPERATE_ADD` int(4) NOT NULL COMMENT '添加权限：1有，2无 ',
  `OPERATE_UPDATE` int(4) NOT NULL COMMENT '修改权限：1有，2无',
  `OPERATE_DELETE` int(4) NOT NULL COMMENT '删除权限：1有，2无',
  `COMMENT` varchar(1000) DEFAULT NULL COMMENT ' 备注',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk_sys_authority_name` (`AUTHORITY_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统权限表';

-- ----------------------------
-- Records of orbit_sys_authority
-- ----------------------------
INSERT INTO `orbit_sys_authority` VALUES ('01', 'vehicleSearch', '综合搜索', '0', '0', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('02', 'searchByPicture', '以图搜图', '0', '0', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('03', 'carInquiry', '过车查询', '0', '0', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('04', 'firstCarInCity', '首次入城', '0', '1', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('05', 'peerVehicle', '同行车辆', '0', '1', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('06', 'crouchingAtNight', '昼伏夜出', '0', '1', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('07', 'frequentAtNight', '夜间频出', '0', '1', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('08', 'hiddenVehicle', '隐匿车辆', '0', '1', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('09', 'analysisOfTheFoothold', '落脚点分析', '0', '1', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('10', 'frequentDriving', '频繁过车', '0', '1', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('11', 'trajectoryAnalysis', '轨迹分析', '0', '1', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('12', 'deckAnalysis', '套牌分析', '0', '1', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('13', 'oneCarOneFile', '一车一档', '0', '1', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('14', 'vehicleControl', '车辆布控', '0', '2', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('15', 'blacklistManagement', '黑名单管理', '0', '2', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('16', 'violationManagement', '违章管理', '0', '4', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('17', 'specialVehicleManagement', '特殊车辆管理', '0', '2', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('18', 'realTimeAlarm', '实时预警', '0', '2', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('19', 'historyAlarm', '历史预警', '0', '2', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('20', 'controlApproval', '布控审批', '0', '2', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('21', 'userManagement', '用户管理', '0', '3', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('22', 'equipmentManagement', '设备管理', '0', '3', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('23', 'systemLog', '系统日志', '0', '3', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('24', 'systemParameters', '系统参数', '0', '3', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('25', 'divisionManagement', '行政区划管理', '0', '3', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('26', 'institutionalManagement', '机构管理', '0', '3', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('27', 'roleManagement', '角色管理', '0', '1000', ' ', ' ', ' ', '0', '0', '0', ' ');
INSERT INTO `orbit_sys_authority` VALUES ('28', 'listTypeManagement', '名单类型管理', '0', '3', '', '', '', '0', '0', '0', '');

-- ----------------------------
-- Table structure for orbit_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `orbit_sys_config`;
CREATE TABLE `orbit_sys_config` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `USER_ID` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `AUTHORITY_IDSTR` varchar(2000) DEFAULT NULL COMMENT '权限ID字符串以，隔开',
  `TOPIC` int(4) DEFAULT NULL COMMENT '主题（1、black,2、white）',
  `LANGUAGE` int(4) DEFAULT NULL COMMENT '语言/1、chinese/2、english）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- ----------------------------
-- Records of orbit_sys_config
-- ----------------------------
INSERT INTO `orbit_sys_config` VALUES ('115EF6B75EE4463DAEEC7BAE948844EF', 'A73E825CFC004CAAA481556473573E03', '01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20', null, null);

-- ----------------------------
-- Table structure for orbit_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `orbit_sys_log`;
CREATE TABLE `orbit_sys_log` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `USER_ID` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `DATA_TYPE` int(4) DEFAULT NULL COMMENT '数据类型（0，违章数据变更；1，车辆布控数据变更，2，设备数据变更，3，系统数据变更）',
  `ACTION_TYPE` int(4) DEFAULT NULL COMMENT '操作类型(0,添加；1，修改；2，删除)',
  `DESCRIPTION` varchar(1000) DEFAULT NULL COMMENT '操作参数',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统操作日志表';

-- ----------------------------
-- Records of orbit_sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for orbit_sys_message
-- ----------------------------
DROP TABLE IF EXISTS `orbit_sys_message`;
CREATE TABLE `orbit_sys_message` (
  `ID` varchar(32) NOT NULL COMMENT '消息id',
  `BUSINESS_ID` varchar(32) NOT NULL COMMENT '业务id',
  `MESSAGE_COMMENT` varchar(1000) NOT NULL COMMENT '消息内容',
  `MESSAGE_TYPE` int(4) NOT NULL COMMENT '消息类型（1审批）',
  `MESSAGE_STATUS` int(4) NOT NULL COMMENT '消息状态（1已读，2未读）初始时为未读',
  `MESSAGE_TIME` datetime NOT NULL COMMENT '消息时间',
  `READ_TIME` datetime DEFAULT NULL COMMENT '已读时间',
  `READER` varchar(64) DEFAULT NULL COMMENT '已读人',
  `READER_ID` varchar(32) DEFAULT NULL COMMENT '已读人id',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统消息表';

-- ----------------------------
-- Records of orbit_sys_message
-- ----------------------------

-- ----------------------------
-- Table structure for orbit_sys_monitor_center
-- ----------------------------
DROP TABLE IF EXISTS `orbit_sys_monitor_center`;
CREATE TABLE `orbit_sys_monitor_center` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `NAME` varchar(64) DEFAULT NULL COMMENT '行政机构名称',
  `ADDRESS` varchar(128) DEFAULT NULL COMMENT '地址',
  `PHONE` varchar(16) DEFAULT NULL COMMENT '联系电话',
  `ADMIN_REGION_CODE` varchar(20) DEFAULT NULL COMMENT '所管辖的行政区域的编码',
  `IP` varchar(32) DEFAULT NULL COMMENT '文件服务器的IP',
  `PORT` int(11) DEFAULT NULL COMMENT '文件服务器的Context，含IP:port/AppName',
  `DELETED` int(4) DEFAULT NULL COMMENT '是否已删除',
  `UNIFIED_ALARM_URL` varchar(128) DEFAULT NULL,
  `VIDEO_SERVER_IP` varchar(128) DEFAULT NULL,
  `VIDEO_SDK` varchar(255) DEFAULT NULL,
  `CITY_NAME` varchar(1000) DEFAULT NULL COMMENT '管辖城市名，逗号隔开',
  `CITY_ID` varchar(1000) DEFAULT NULL COMMENT '管辖城市主键，以，分割',
  `AREA_NAME` varchar(1000) DEFAULT NULL COMMENT '区域名，逗号隔开',
  `AREA_ID` varchar(1000) DEFAULT NULL COMMENT '管辖区域主键，以，分割',
  `ROAD_NAME` varchar(1000) DEFAULT NULL COMMENT '区域名，逗号隔开',
  `ROAD_ID` varchar(1000) DEFAULT NULL COMMENT '管辖路口主键，以，分割',
  `LEVEL_NAME` varchar(1000) DEFAULT NULL COMMENT '行政区划级别名，逗号隔开',
  `LEVEL_ID` varchar(1000) DEFAULT NULL COMMENT '级别id，（选择城市或者区域就用相应的ID逗号隔开，选择全部不传值）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='监控中心';

-- ----------------------------
-- Records of orbit_sys_monitor_center
-- ----------------------------
INSERT INTO `orbit_sys_monitor_center` VALUES ('49F9EAAE82B14A8C83A5975B43B6F2C9', '交通执法', '741852963', '123456789', null, null, null, '2', null, null, null, '', '', '', '', '', '', '', '');

-- ----------------------------
-- Table structure for orbit_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `orbit_sys_role`;
CREATE TABLE `orbit_sys_role` (
  `ID` varchar(32) NOT NULL COMMENT '角色id',
  `ROLE_NAME` varchar(64) NOT NULL COMMENT '角色名',
  `CREATOR` varchar(64) NOT NULL COMMENT '创建人',
  `CREATOR_ID` varchar(32) NOT NULL COMMENT '创建人id',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATOR` varchar(64) DEFAULT NULL COMMENT '更新人',
  `UPDATOR_ID` varchar(32) DEFAULT NULL COMMENT '更新人id',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DELETED` int(4) NOT NULL COMMENT '删除标志(1未删除2删除)',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表(默认角色：超级管理员、管理员、普通用户；)';

-- ----------------------------
-- Records of orbit_sys_role
-- ----------------------------
INSERT INTO `orbit_sys_role` VALUES ('ECFA53379A3F4D4AAC6C21F2FD2424DB', '管理员角色组', 'test', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '2019-03-20 10:00:36', 'test', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '2019-03-20 10:00:36', '1');

-- ----------------------------
-- Table structure for orbit_sys_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `orbit_sys_role_authority`;
CREATE TABLE `orbit_sys_role_authority` (
  `ID` varchar(32) NOT NULL COMMENT 'id',
  `ROLE_ID` varchar(32) NOT NULL COMMENT '角色id',
  `AUTHORITY_ID` varchar(32) NOT NULL COMMENT '权限id',
  `OPERATE_ADD` int(4) NOT NULL COMMENT '添加权限：1有，2无 ',
  `OPERATE_UPDATE` int(4) NOT NULL COMMENT '修改权限：1有，2无',
  `OPERATE_DELETE` int(4) NOT NULL COMMENT '删除权限：1有，2无',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色权限映射表';

-- ----------------------------
-- Records of orbit_sys_role_authority
-- ----------------------------
INSERT INTO `orbit_sys_role_authority` VALUES ('19CC999B913A4D0FB01A285B187EFF5F', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '01', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CE90C11BB7115704A4B507E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '13', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C10BB7115704A4B587E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '21', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C11587115704A4B507E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '12', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C11BB7115704A4B507E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '03', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C11BB7115704A4B507F', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '10', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C11BB7115704A4B508E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '08', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C11BB7115704A4B512E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '09', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C11BB7115704A4B587E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '16', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C11BB7115704AAA507E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '11', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C11BB7115705A4B507E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '07', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C21BB7115704A4B587E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '17', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C22BB7115704A4B587E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '18', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C23BB7115704A4B587E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '19', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C24BB7115704A4B587E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '20', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C28BB7115704A4B587E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '22', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C33BB7115704A4B587E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '23', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C34BB7115704A4B587E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '24', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C35BB7115704A4B587E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '25', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C36BB7115704A4B397E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '27', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C36BB7115704A4B407E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '28', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E2CA1CEF4C36BB7115704A4B587E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '26', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E36A1CEF4C11BB7115704A4B507E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '14', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('3A84E57A1CEF4C11BB7115704A4B507E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '15', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('4A84E2CA1CEF4C11BB7115704A4B507E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '04', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('5A84E2CA1CEF4C11BB7115704A4B507E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '05', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('6A84E2CA1CEF4C11BB7115704A4B507E', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '06', '0', '0', '0');
INSERT INTO `orbit_sys_role_authority` VALUES ('7708142DDAEC41E1A41F66145A2C83C3', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', '02', '0', '0', '0');

-- ----------------------------
-- Table structure for orbit_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `orbit_sys_user`;
CREATE TABLE `orbit_sys_user` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `USERNAME` varchar(32) DEFAULT NULL COMMENT '用户名',
  `PASSWORD` varchar(64) DEFAULT NULL,
  `NAME` varchar(64) DEFAULT NULL COMMENT '姓名',
  `GENDER` int(11) DEFAULT NULL COMMENT '性别(1:男; 2:女)',
  `PHONE` varchar(16) DEFAULT NULL COMMENT '电话',
  `EMAIL` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `MONITOR_CENTER_ID` varchar(32) DEFAULT NULL COMMENT '监控中心ID',
  `ROLE_ID` varchar(32) DEFAULT NULL COMMENT '角色ID',
  `photo_url` varchar(128) DEFAULT NULL COMMENT '头像路径',
  `DELETED` int(4) DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of orbit_sys_user
-- ----------------------------
INSERT INTO `orbit_sys_user` VALUES ('A73E825CFC004CAAA481556473573E03', 'admin', '123456', 'admin', '1', '13666284460', '', '49F9EAAE82B14A8C83A5975B43B6F2C9', 'ECFA53379A3F4D4AAC6C21F2FD2424DB', 'http://172.16.3.31:8888/group1/M00/00/19/rBADH1yPNomAC92KAALTgJeqhoI719.jpg', '1');

-- ----------------------------
-- Table structure for orbit_sys_variable
-- ----------------------------
DROP TABLE IF EXISTS `orbit_sys_variable`;
CREATE TABLE `orbit_sys_variable` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `NAME` varchar(64) DEFAULT NULL COMMENT '变量名',
  `VALUE` int(11) DEFAULT NULL COMMENT '变量值 (-1永久）',
  `UNIT` varchar(16) DEFAULT NULL COMMENT '单位',
  `TYPE` int(4) DEFAULT NULL COMMENT '类型(0,过车数据，1报警数据)',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统变量表';

-- ----------------------------
-- Records of orbit_sys_variable
-- ----------------------------
INSERT INTO `orbit_sys_variable` VALUES ('80A21264F47D41FAB06D0231363D7934', 'Alarm information save time', '221', 'day', '2');
INSERT INTO `orbit_sys_variable` VALUES ('80A21264F47D41FAB06D0231363D7935', 'Car record save time', '111', 'day', '1');

-- ----------------------------
-- Table structure for orbit_vehicle_brand
-- ----------------------------
DROP TABLE IF EXISTS `orbit_vehicle_brand`;
CREATE TABLE `orbit_vehicle_brand` (
  `CODE` varchar(64) NOT NULL COMMENT '车辆品牌代码',
  `ZH_NAME` varchar(64) DEFAULT NULL COMMENT '中文名',
  `EN_NAME` varchar(64) DEFAULT NULL COMMENT '英文名',
  `ES_NAME` varchar(64) DEFAULT NULL COMMENT '西班牙语',
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆品牌表';

-- ----------------------------
-- Records of orbit_vehicle_brand
-- ----------------------------
INSERT INTO `orbit_vehicle_brand` VALUES ('0000', 'CONQUEST', 'CONQUEST', 'CONQUEST');
INSERT INTO `orbit_vehicle_brand` VALUES ('0001', 'DACIA', 'DACIA', 'DACIA');
INSERT INTO `orbit_vehicle_brand` VALUES ('0002', 'DATSUN', 'DATSUN', 'DATSUN');
INSERT INTO `orbit_vehicle_brand` VALUES ('0003', 'DS', 'DS', 'DS');
INSERT INTO `orbit_vehicle_brand` VALUES ('0004', 'FISKER', 'FISKER', 'FISKER');
INSERT INTO `orbit_vehicle_brand` VALUES ('0005', 'GMC', 'GMC', 'GMC');
INSERT INTO `orbit_vehicle_brand` VALUES ('0006', 'GUMPERT', 'GUMPERT', 'GUMPERT');
INSERT INTO `orbit_vehicle_brand` VALUES ('0007', 'HENNESSEY', 'HENNESSEY', 'HENNESSEY');
INSERT INTO `orbit_vehicle_brand` VALUES ('0008', 'JEEP', 'JEEP', 'JEEP');
INSERT INTO `orbit_vehicle_brand` VALUES ('0009', 'KTM', 'KTM', 'KTM');
INSERT INTO `orbit_vehicle_brand` VALUES ('0010', 'LOCAL.MOTORS', 'LOCAL.MOTORS', 'LOCAL.MOTORS');
INSERT INTO `orbit_vehicle_brand` VALUES ('0011', 'MAZZANTI', 'MAZZANTI', 'MAZZANTI');
INSERT INTO `orbit_vehicle_brand` VALUES ('0012', 'MG', 'MG', 'MG');
INSERT INTO `orbit_vehicle_brand` VALUES ('0013', 'MINI', 'MINI', 'MINI');
INSERT INTO `orbit_vehicle_brand` VALUES ('0014', 'NOBLE', 'NOBLE', 'NOBLE');
INSERT INTO `orbit_vehicle_brand` VALUES ('0015', 'REZVANI', 'REZVANI', 'REZVANI');
INSERT INTO `orbit_vehicle_brand` VALUES ('0016', 'RIMAC', 'RIMAC', 'RIMAC');
INSERT INTO `orbit_vehicle_brand` VALUES ('0017', 'RINSPEED', 'RINSPEED', 'RINSPEED');
INSERT INTO `orbit_vehicle_brand` VALUES ('0018', 'SCION', 'SCION', 'SCION');
INSERT INTO `orbit_vehicle_brand` VALUES ('0019', 'SMART', 'SMART', 'SMART');
INSERT INTO `orbit_vehicle_brand` VALUES ('0020', 'TVR', 'TVR', 'TVR');
INSERT INTO `orbit_vehicle_brand` VALUES ('0021', 'UD', 'UD', 'UD');
INSERT INTO `orbit_vehicle_brand` VALUES ('0022', 'WEY', 'WEY', 'WEY');
INSERT INTO `orbit_vehicle_brand` VALUES ('0023', 'Zenvo', 'Zenvo', 'Zenvo');
INSERT INTO `orbit_vehicle_brand` VALUES ('0024', 'nanoFLOWCELL', 'nanoFLOWCELL', 'nanoFLOWCELL');
INSERT INTO `orbit_vehicle_brand` VALUES ('0025', '一汽', '一汽', '一汽');
INSERT INTO `orbit_vehicle_brand` VALUES ('0026', '一汽凌源', '一汽凌源', '一汽凌源');
INSERT INTO `orbit_vehicle_brand` VALUES ('0027', '一汽奔腾', '一汽奔腾', '一汽奔腾');
INSERT INTO `orbit_vehicle_brand` VALUES ('0028', '一汽柳特', '一汽柳特', '一汽柳特');
INSERT INTO `orbit_vehicle_brand` VALUES ('0029', '一汽红塔', '一汽红塔', '一汽红塔');
INSERT INTO `orbit_vehicle_brand` VALUES ('0030', '一汽解放', '一汽解放', '一汽解放');
INSERT INTO `orbit_vehicle_brand` VALUES ('0031', '一汽解放轻卡', '一汽解放轻卡', '一汽解放轻卡');
INSERT INTO `orbit_vehicle_brand` VALUES ('0032', '一汽通用', '一汽通用', '一汽通用');
INSERT INTO `orbit_vehicle_brand` VALUES ('0033', '三一重工', '三一重工', '三一重工');
INSERT INTO `orbit_vehicle_brand` VALUES ('0034', '三江航天', '三江航天', '三江航天');
INSERT INTO `orbit_vehicle_brand` VALUES ('0035', '三环', '三环', '三环');
INSERT INTO `orbit_vehicle_brand` VALUES ('0036', '三环十通', '三环十通', '三环十通');
INSERT INTO `orbit_vehicle_brand` VALUES ('0037', '三菱', '三菱', '三菱');
INSERT INTO `orbit_vehicle_brand` VALUES ('0038', '三轮车', '三轮车', '三轮车');
INSERT INTO `orbit_vehicle_brand` VALUES ('0039', '上汽大通', '上汽大通', '上汽大通');
INSERT INTO `orbit_vehicle_brand` VALUES ('0040', '上汽跃进', '上汽跃进', '上汽跃进');
INSERT INTO `orbit_vehicle_brand` VALUES ('0041', '世爵', '世爵', '世爵');
INSERT INTO `orbit_vehicle_brand` VALUES ('0042', '东南', '东南', '东南');
INSERT INTO `orbit_vehicle_brand` VALUES ('0043', '东方曼', '东方曼', '东方曼');
INSERT INTO `orbit_vehicle_brand` VALUES ('0044', '东方红', '东方红', '东方红');
INSERT INTO `orbit_vehicle_brand` VALUES ('0045', '东风', '东风', '东风');
INSERT INTO `orbit_vehicle_brand` VALUES ('0046', '东风华神', '东风华神', '东风华神');
INSERT INTO `orbit_vehicle_brand` VALUES ('0047', '东风南充', '东风南充', '东风南充');
INSERT INTO `orbit_vehicle_brand` VALUES ('0048', '东风小康', '东风小康', '东风小康');
INSERT INTO `orbit_vehicle_brand` VALUES ('0049', '东风柳汽', '东风柳汽', '东风柳汽');
INSERT INTO `orbit_vehicle_brand` VALUES ('0050', '东风神宇', '东风神宇', '东风神宇');
INSERT INTO `orbit_vehicle_brand` VALUES ('0051', '东风雪铁龙', '东风雪铁龙', '东风雪铁龙');
INSERT INTO `orbit_vehicle_brand` VALUES ('0052', '东风风光', '东风风光', '东风风光');
INSERT INTO `orbit_vehicle_brand` VALUES ('0053', '东风风度', '东风风度', '东风风度');
INSERT INTO `orbit_vehicle_brand` VALUES ('0054', '东风风神', '东风风神', '东风风神');
INSERT INTO `orbit_vehicle_brand` VALUES ('0055', '东风风行', '东风风行', '东风风行');
INSERT INTO `orbit_vehicle_brand` VALUES ('0056', '中兴', '中兴', '中兴');
INSERT INTO `orbit_vehicle_brand` VALUES ('0057', '中华', '中华', '中华');
INSERT INTO `orbit_vehicle_brand` VALUES ('0058', '中国重汽', '中国重汽', '中国重汽');
INSERT INTO `orbit_vehicle_brand` VALUES ('0059', '中大', '中大', '中大');
INSERT INTO `orbit_vehicle_brand` VALUES ('0060', '中通', '中通', '中通');
INSERT INTO `orbit_vehicle_brand` VALUES ('0061', '中顺', '中顺', '中顺');
INSERT INTO `orbit_vehicle_brand` VALUES ('0062', '丰田', '丰田', '丰田');
INSERT INTO `orbit_vehicle_brand` VALUES ('0063', '丽驰', '丽驰', '丽驰');
INSERT INTO `orbit_vehicle_brand` VALUES ('0064', '之诺', '之诺', '之诺');
INSERT INTO `orbit_vehicle_brand` VALUES ('0065', '乔治巴顿', '乔治巴顿', '乔治巴顿');
INSERT INTO `orbit_vehicle_brand` VALUES ('0066', '九龙', '九龙', '九龙');
INSERT INTO `orbit_vehicle_brand` VALUES ('0067', '五十铃', '五十铃', '五十铃');
INSERT INTO `orbit_vehicle_brand` VALUES ('0068', '五征', '五征', '五征');
INSERT INTO `orbit_vehicle_brand` VALUES ('0069', '五菱', '五菱', '五菱');
INSERT INTO `orbit_vehicle_brand` VALUES ('0070', '亚星', '亚星', '亚星');
INSERT INTO `orbit_vehicle_brand` VALUES ('0071', '京华客车', '京华客车', '京华客车');
INSERT INTO `orbit_vehicle_brand` VALUES ('0072', '伊利萨尔', '伊利萨尔', '伊利萨尔');
INSERT INTO `orbit_vehicle_brand` VALUES ('0073', '众泰', '众泰', '众泰');
INSERT INTO `orbit_vehicle_brand` VALUES ('0074', '佩奇奥', '佩奇奥', '佩奇奥');
INSERT INTO `orbit_vehicle_brand` VALUES ('0075', '依维柯', '依维柯', '依维柯');
INSERT INTO `orbit_vehicle_brand` VALUES ('0076', '保斐利', '保斐利', '保斐利');
INSERT INTO `orbit_vehicle_brand` VALUES ('0077', '保时捷', '保时捷', '保时捷');
INSERT INTO `orbit_vehicle_brand` VALUES ('0078', '光冈', '光冈', '光冈');
INSERT INTO `orbit_vehicle_brand` VALUES ('0079', '克莱斯勒', '克莱斯勒', '克莱斯勒');
INSERT INTO `orbit_vehicle_brand` VALUES ('0080', '兰博基尼', '兰博基尼', '兰博基尼');
INSERT INTO `orbit_vehicle_brand` VALUES ('0081', '凌宇客车', '凌宇客车', '凌宇客车');
INSERT INTO `orbit_vehicle_brand` VALUES ('0082', '凯佰赫', '凯佰赫', '凯佰赫');
INSERT INTO `orbit_vehicle_brand` VALUES ('0083', '凯翼', '凯翼', '凯翼');
INSERT INTO `orbit_vehicle_brand` VALUES ('0084', '凯迪拉克', '凯迪拉克', '凯迪拉克');
INSERT INTO `orbit_vehicle_brand` VALUES ('0085', '凯马', '凯马', '凯马');
INSERT INTO `orbit_vehicle_brand` VALUES ('0086', '别克', '别克', '别克');
INSERT INTO `orbit_vehicle_brand` VALUES ('0087', '前途', '前途', '前途');
INSERT INTO `orbit_vehicle_brand` VALUES ('0088', '力帆', '力帆', '力帆');
INSERT INTO `orbit_vehicle_brand` VALUES ('0089', '力神', '力神', '力神');
INSERT INTO `orbit_vehicle_brand` VALUES ('0090', '劳斯莱斯', '劳斯莱斯', '劳斯莱斯');
INSERT INTO `orbit_vehicle_brand` VALUES ('0091', '北京', '北京', '北京');
INSERT INTO `orbit_vehicle_brand` VALUES ('0092', '北京汽车', '北京汽车', '北京汽车');
INSERT INTO `orbit_vehicle_brand` VALUES ('0093', '北奔重卡', '北奔重卡', '北奔重卡');
INSERT INTO `orbit_vehicle_brand` VALUES ('0094', '北方尼奥普兰', '北方尼奥普兰', '北方尼奥普兰');
INSERT INTO `orbit_vehicle_brand` VALUES ('0095', '北汽', '北汽', '北汽');
INSERT INTO `orbit_vehicle_brand` VALUES ('0096', '北汽制造', '北汽制造', '北汽制造');
INSERT INTO `orbit_vehicle_brand` VALUES ('0097', '北汽威旺', '北汽威旺', '北汽威旺');
INSERT INTO `orbit_vehicle_brand` VALUES ('0098', '北汽幻速', '北汽幻速', '北汽幻速');
INSERT INTO `orbit_vehicle_brand` VALUES ('0099', '北汽新能源', '北汽新能源', '北汽新能源');
INSERT INTO `orbit_vehicle_brand` VALUES ('0100', '北汽绅宝', '北汽绅宝', '北汽绅宝');
INSERT INTO `orbit_vehicle_brand` VALUES ('0101', '华凯', '华凯', '华凯');
INSERT INTO `orbit_vehicle_brand` VALUES ('0102', '华利', '华利', '华利');
INSERT INTO `orbit_vehicle_brand` VALUES ('0103', '华夏', '华夏', '华夏');
INSERT INTO `orbit_vehicle_brand` VALUES ('0104', '华普', '华普', '华普');
INSERT INTO `orbit_vehicle_brand` VALUES ('0105', '华泰', '华泰', '华泰');
INSERT INTO `orbit_vehicle_brand` VALUES ('0106', '华泰新能源', '华泰新能源', '华泰新能源');
INSERT INTO `orbit_vehicle_brand` VALUES ('0107', '华神', '华神', '华神');
INSERT INTO `orbit_vehicle_brand` VALUES ('0108', '华菱', '华菱', '华菱');
INSERT INTO `orbit_vehicle_brand` VALUES ('0109', '华颂', '华颂', '华颂');
INSERT INTO `orbit_vehicle_brand` VALUES ('0110', '华骐', '华骐', '华骐');
INSERT INTO `orbit_vehicle_brand` VALUES ('0111', '南汽', '南汽', '南汽');
INSERT INTO `orbit_vehicle_brand` VALUES ('0112', '南骏', '南骏', '南骏');
INSERT INTO `orbit_vehicle_brand` VALUES ('0113', '卡升', '卡升', '卡升');
INSERT INTO `orbit_vehicle_brand` VALUES ('0114', '卡威', '卡威', '卡威');
INSERT INTO `orbit_vehicle_brand` VALUES ('0115', '卡尔森', '卡尔森', '卡尔森');
INSERT INTO `orbit_vehicle_brand` VALUES ('0116', '双力', '双力', '双力');
INSERT INTO `orbit_vehicle_brand` VALUES ('0117', '双环', '双环', '双环');
INSERT INTO `orbit_vehicle_brand` VALUES ('0118', '双龙', '双龙', '双龙');
INSERT INTO `orbit_vehicle_brand` VALUES ('0119', '吉利', '吉利', '吉利');
INSERT INTO `orbit_vehicle_brand` VALUES ('0120', '吊车', '吊车', '吊车');
INSERT INTO `orbit_vehicle_brand` VALUES ('0121', '启辰', '启辰', '启辰');
INSERT INTO `orbit_vehicle_brand` VALUES ('0122', '哈弗', '哈弗', '哈弗');
INSERT INTO `orbit_vehicle_brand` VALUES ('0123', '哈飞', '哈飞', '哈飞');
INSERT INTO `orbit_vehicle_brand` VALUES ('0124', '唐骏', '唐骏', '唐骏');
INSERT INTO `orbit_vehicle_brand` VALUES ('0125', '嘉川', '嘉川', '嘉川');
INSERT INTO `orbit_vehicle_brand` VALUES ('0126', '四川现代', '四川现代', '四川现代');
INSERT INTO `orbit_vehicle_brand` VALUES ('0127', '大众', '大众', '大众');
INSERT INTO `orbit_vehicle_brand` VALUES ('0128', '大发', '大发', '大发');
INSERT INTO `orbit_vehicle_brand` VALUES ('0129', '大宇', '大宇', '大宇');
INSERT INTO `orbit_vehicle_brand` VALUES ('0130', '大河', '大河', '大河');
INSERT INTO `orbit_vehicle_brand` VALUES ('0131', '大运', '大运', '大运');
INSERT INTO `orbit_vehicle_brand` VALUES ('0132', '大迪', '大迪', '大迪');
INSERT INTO `orbit_vehicle_brand` VALUES ('0133', '大阳', '大阳', '大阳');
INSERT INTO `orbit_vehicle_brand` VALUES ('0134', '天马', '天马', '天马');
INSERT INTO `orbit_vehicle_brand` VALUES ('0135', '奇瑞', '奇瑞', '奇瑞');
INSERT INTO `orbit_vehicle_brand` VALUES ('0136', '奔腾', '奔腾', '奔腾');
INSERT INTO `orbit_vehicle_brand` VALUES ('0137', '奔马', '奔马', '奔马');
INSERT INTO `orbit_vehicle_brand` VALUES ('0138', '奔驰', '奔驰', '奔驰');
INSERT INTO `orbit_vehicle_brand` VALUES ('0139', '奔驰重卡', '奔驰重卡', '奔驰重卡');
INSERT INTO `orbit_vehicle_brand` VALUES ('0140', '奥迪', '奥迪', '奥迪');
INSERT INTO `orbit_vehicle_brand` VALUES ('0141', '奥驰', '奥驰', '奥驰');
INSERT INTO `orbit_vehicle_brand` VALUES ('0142', '如虎', '如虎', '如虎');
INSERT INTO `orbit_vehicle_brand` VALUES ('0143', '威兹曼', '威兹曼', '威兹曼');
INSERT INTO `orbit_vehicle_brand` VALUES ('0144', '威麟', '威麟', '威麟');
INSERT INTO `orbit_vehicle_brand` VALUES ('0145', '宇通', '宇通', '宇通');
INSERT INTO `orbit_vehicle_brand` VALUES ('0146', '安凯', '安凯', '安凯');
INSERT INTO `orbit_vehicle_brand` VALUES ('0147', '安源', '安源', '安源');
INSERT INTO `orbit_vehicle_brand` VALUES ('0148', '宝沃', '宝沃', '宝沃');
INSERT INTO `orbit_vehicle_brand` VALUES ('0149', '宝石', '宝石', '宝石');
INSERT INTO `orbit_vehicle_brand` VALUES ('0150', '宝雅', '宝雅', '宝雅');
INSERT INTO `orbit_vehicle_brand` VALUES ('0151', '宝马', '宝马', '宝马');
INSERT INTO `orbit_vehicle_brand` VALUES ('0152', '宝骏', '宝骏', '宝骏');
INSERT INTO `orbit_vehicle_brand` VALUES ('0153', '宾利', '宾利', '宾利');
INSERT INTO `orbit_vehicle_brand` VALUES ('0154', '富路', '富路', '富路');
INSERT INTO `orbit_vehicle_brand` VALUES ('0155', '小鹏汽车', '小鹏汽车', '小鹏汽车');
INSERT INTO `orbit_vehicle_brand` VALUES ('0156', '少林', '少林', '少林');
INSERT INTO `orbit_vehicle_brand` VALUES ('0157', '川交', '川交', '川交');
INSERT INTO `orbit_vehicle_brand` VALUES ('0158', '川江', '川江', '川江');
INSERT INTO `orbit_vehicle_brand` VALUES ('0159', '布加迪', '布加迪', '布加迪');
INSERT INTO `orbit_vehicle_brand` VALUES ('0160', '广汽传祺', '广汽传祺', '广汽传祺');
INSERT INTO `orbit_vehicle_brand` VALUES ('0161', '广汽吉奥', '广汽吉奥', '广汽吉奥');
INSERT INTO `orbit_vehicle_brand` VALUES ('0162', '广汽日野', '广汽日野', '广汽日野');
INSERT INTO `orbit_vehicle_brand` VALUES ('0163', '广通', '广通', '广通');
INSERT INTO `orbit_vehicle_brand` VALUES ('0164', '庆铃', '庆铃', '庆铃');
INSERT INTO `orbit_vehicle_brand` VALUES ('0165', '康迪全球鹰', '康迪全球鹰', '康迪全球鹰');
INSERT INTO `orbit_vehicle_brand` VALUES ('0166', '开瑞', '开瑞', '开瑞');
INSERT INTO `orbit_vehicle_brand` VALUES ('0167', '弗那萨利', '弗那萨利', '弗那萨利');
INSERT INTO `orbit_vehicle_brand` VALUES ('0168', '徐工', '徐工', '徐工');
INSERT INTO `orbit_vehicle_brand` VALUES ('0169', '御捷', '御捷', '御捷');
INSERT INTO `orbit_vehicle_brand` VALUES ('0170', '微卡', '微卡', '微卡');
INSERT INTO `orbit_vehicle_brand` VALUES ('0171', '恒天汽车', '恒天汽车', '恒天汽车');
INSERT INTO `orbit_vehicle_brand` VALUES ('0172', '悍马', '悍马', '悍马');
INSERT INTO `orbit_vehicle_brand` VALUES ('0173', '成功', '成功', '成功');
INSERT INTO `orbit_vehicle_brand` VALUES ('0174', '扬子', '扬子', '扬子');
INSERT INTO `orbit_vehicle_brand` VALUES ('0175', '扬子江', '扬子江', '扬子江');
INSERT INTO `orbit_vehicle_brand` VALUES ('0176', '拉达', '拉达', '拉达');
INSERT INTO `orbit_vehicle_brand` VALUES ('0177', '拖拉机', '拖拉机', '拖拉机');
INSERT INTO `orbit_vehicle_brand` VALUES ('0178', '捷豹', '捷豹', '捷豹');
INSERT INTO `orbit_vehicle_brand` VALUES ('0179', '摩根', '摩根', '摩根');
INSERT INTO `orbit_vehicle_brand` VALUES ('0180', '斯堪尼亚', '斯堪尼亚', '斯堪尼亚');
INSERT INTO `orbit_vehicle_brand` VALUES ('0181', '斯威汽车', '斯威汽车', '斯威汽车');
INSERT INTO `orbit_vehicle_brand` VALUES ('0182', '斯巴鲁', '斯巴鲁', '斯巴鲁');
INSERT INTO `orbit_vehicle_brand` VALUES ('0183', '斯柯达', '斯柯达', '斯柯达');
INSERT INTO `orbit_vehicle_brand` VALUES ('0184', '斯达泰克', '斯达泰克', '斯达泰克');
INSERT INTO `orbit_vehicle_brand` VALUES ('0185', '新凯', '新凯', '新凯');
INSERT INTO `orbit_vehicle_brand` VALUES ('0186', '日产', '日产', '日产');
INSERT INTO `orbit_vehicle_brand` VALUES ('0187', '时代', '时代', '时代');
INSERT INTO `orbit_vehicle_brand` VALUES ('0188', '时风', '时风', '时风');
INSERT INTO `orbit_vehicle_brand` VALUES ('0189', '时骏', '时骏', '时骏');
INSERT INTO `orbit_vehicle_brand` VALUES ('0190', '昌河', '昌河', '昌河');
INSERT INTO `orbit_vehicle_brand` VALUES ('0191', '昌河铃木', '昌河铃木', '昌河铃木');
INSERT INTO `orbit_vehicle_brand` VALUES ('0192', '春洲', '春洲', '春洲');
INSERT INTO `orbit_vehicle_brand` VALUES ('0193', '曼', '曼', '曼');
INSERT INTO `orbit_vehicle_brand` VALUES ('0194', '朗世', '朗世', '朗世');
INSERT INTO `orbit_vehicle_brand` VALUES ('0195', '本田', '本田', '本田');
INSERT INTO `orbit_vehicle_brand` VALUES ('0196', '林肯', '林肯', '林肯');
INSERT INTO `orbit_vehicle_brand` VALUES ('0197', '标致', '标致', '标致');
INSERT INTO `orbit_vehicle_brand` VALUES ('0198', '楚风', '楚风', '楚风');
INSERT INTO `orbit_vehicle_brand` VALUES ('0199', '欧宝', '欧宝', '欧宝');
INSERT INTO `orbit_vehicle_brand` VALUES ('0200', '比亚迪', '比亚迪', '比亚迪');
INSERT INTO `orbit_vehicle_brand` VALUES ('0201', '比速', '比速', '比速');
INSERT INTO `orbit_vehicle_brand` VALUES ('0202', '永源', '永源', '永源');
INSERT INTO `orbit_vehicle_brand` VALUES ('0203', '汉江', '汉江', '汉江');
INSERT INTO `orbit_vehicle_brand` VALUES ('0204', '汉腾', '汉腾', '汉腾');
INSERT INTO `orbit_vehicle_brand` VALUES ('0205', '江淮', '江淮', '江淮');
INSERT INTO `orbit_vehicle_brand` VALUES ('0206', '江淮客车', '江淮客车', '江淮客车');
INSERT INTO `orbit_vehicle_brand` VALUES ('0207', '江铃', '江铃', '江铃');
INSERT INTO `orbit_vehicle_brand` VALUES ('0208', '江铃重汽', '江铃重汽', '江铃重汽');
INSERT INTO `orbit_vehicle_brand` VALUES ('0209', '沃克斯豪尔', '沃克斯豪尔', '沃克斯豪尔');
INSERT INTO `orbit_vehicle_brand` VALUES ('0210', '沃尔沃', '沃尔沃', '沃尔沃');
INSERT INTO `orbit_vehicle_brand` VALUES ('0211', '沃尔沃重卡', '沃尔沃重卡', '沃尔沃重卡');
INSERT INTO `orbit_vehicle_brand` VALUES ('0212', '法拉利', '法拉利', '法拉利');
INSERT INTO `orbit_vehicle_brand` VALUES ('0213', '泰卡特', '泰卡特', '泰卡特');
INSERT INTO `orbit_vehicle_brand` VALUES ('0214', '海格', '海格', '海格');
INSERT INTO `orbit_vehicle_brand` VALUES ('0215', '海马', '海马', '海马');
INSERT INTO `orbit_vehicle_brand` VALUES ('0216', '潍柴英致', '潍柴英致', '潍柴英致');
INSERT INTO `orbit_vehicle_brand` VALUES ('0217', '牡丹', '牡丹', '牡丹');
INSERT INTO `orbit_vehicle_brand` VALUES ('0218', '特斯拉', '特斯拉', '特斯拉');
INSERT INTO `orbit_vehicle_brand` VALUES ('0219', '猎豹', '猎豹', '猎豹');
INSERT INTO `orbit_vehicle_brand` VALUES ('0220', '玛莎拉蒂', '玛莎拉蒂', '玛莎拉蒂');
INSERT INTO `orbit_vehicle_brand` VALUES ('0221', '现代', '现代', '现代');
INSERT INTO `orbit_vehicle_brand` VALUES ('0222', '理念', '理念', '理念');
INSERT INTO `orbit_vehicle_brand` VALUES ('0223', '瑞易', '瑞易', '瑞易');
INSERT INTO `orbit_vehicle_brand` VALUES ('0224', '瑞麒', '瑞麒', '瑞麒');
INSERT INTO `orbit_vehicle_brand` VALUES ('0225', '申沃', '申沃', '申沃');
INSERT INTO `orbit_vehicle_brand` VALUES ('0226', '申龙', '申龙', '申龙');
INSERT INTO `orbit_vehicle_brand` VALUES ('0227', '电动车', '电动车', '电动车');
INSERT INTO `orbit_vehicle_brand` VALUES ('0228', '电咖', '电咖', '电咖');
INSERT INTO `orbit_vehicle_brand` VALUES ('0229', '皮卡', '皮卡', '皮卡');
INSERT INTO `orbit_vehicle_brand` VALUES ('0230', '知豆', '知豆', '知豆');
INSERT INTO `orbit_vehicle_brand` VALUES ('0231', '福汽启腾', '福汽启腾', '福汽启腾');
INSERT INTO `orbit_vehicle_brand` VALUES ('0232', '福特', '福特', '福特');
INSERT INTO `orbit_vehicle_brand` VALUES ('0233', '福田', '福田', '福田');
INSERT INTO `orbit_vehicle_brand` VALUES ('0234', '福迪', '福迪', '福迪');
INSERT INTO `orbit_vehicle_brand` VALUES ('0235', '科尼塞克', '科尼塞克', '科尼塞克');
INSERT INTO `orbit_vehicle_brand` VALUES ('0236', '穗通', '穗通', '穗通');
INSERT INTO `orbit_vehicle_brand` VALUES ('0237', '精功', '精功', '精功');
INSERT INTO `orbit_vehicle_brand` VALUES ('0238', '红岩', '红岩', '红岩');
INSERT INTO `orbit_vehicle_brand` VALUES ('0239', '红旗', '红旗', '红旗');
INSERT INTO `orbit_vehicle_brand` VALUES ('0240', '纳智捷', '纳智捷', '纳智捷');
INSERT INTO `orbit_vehicle_brand` VALUES ('0241', '缔途', '缔途', '缔途');
INSERT INTO `orbit_vehicle_brand` VALUES ('0242', '联合卡车', '联合卡车', '联合卡车');
INSERT INTO `orbit_vehicle_brand` VALUES ('0243', '腾势', '腾势', '腾势');
INSERT INTO `orbit_vehicle_brand` VALUES ('0244', '舒驰', '舒驰', '舒驰');
INSERT INTO `orbit_vehicle_brand` VALUES ('0245', '航天万山', '航天万山', '航天万山');
INSERT INTO `orbit_vehicle_brand` VALUES ('0246', '英田', '英田', '英田');
INSERT INTO `orbit_vehicle_brand` VALUES ('0247', '英菲尼迪', '英菲尼迪', '英菲尼迪');
INSERT INTO `orbit_vehicle_brand` VALUES ('0248', '荣威', '荣威', '荣威');
INSERT INTO `orbit_vehicle_brand` VALUES ('0249', '莲花', '莲花', '莲花');
INSERT INTO `orbit_vehicle_brand` VALUES ('0250', '菲亚特', '菲亚特', '菲亚特');
INSERT INTO `orbit_vehicle_brand` VALUES ('0251', '萨博', '萨博', '萨博');
INSERT INTO `orbit_vehicle_brand` VALUES ('0252', '蓝旗亚', '蓝旗亚', '蓝旗亚');
INSERT INTO `orbit_vehicle_brand` VALUES ('0253', '蔚来', '蔚来', '蔚来');
INSERT INTO `orbit_vehicle_brand` VALUES ('0254', '衡山', '衡山', '衡山');
INSERT INTO `orbit_vehicle_brand` VALUES ('0255', '西沃', '西沃', '西沃');
INSERT INTO `orbit_vehicle_brand` VALUES ('0256', '西雅特', '西雅特', '西雅特');
INSERT INTO `orbit_vehicle_brand` VALUES ('0257', '观致', '观致', '观致');
INSERT INTO `orbit_vehicle_brand` VALUES ('0258', '解放', '解放', '解放');
INSERT INTO `orbit_vehicle_brand` VALUES ('0259', '讴歌', '讴歌', '讴歌');
INSERT INTO `orbit_vehicle_brand` VALUES ('0260', '讴歌(进口)', '讴歌(进口)', '讴歌(进口)');
INSERT INTO `orbit_vehicle_brand` VALUES ('0261', '赛特', '赛特', '赛特');
INSERT INTO `orbit_vehicle_brand` VALUES ('0262', '赛麟', '赛麟', '赛麟');
INSERT INTO `orbit_vehicle_brand` VALUES ('0263', '起亚', '起亚', '起亚');
INSERT INTO `orbit_vehicle_brand` VALUES ('0264', '跃进', '跃进', '跃进');
INSERT INTO `orbit_vehicle_brand` VALUES ('0265', '路特斯', '路特斯', '路特斯');
INSERT INTO `orbit_vehicle_brand` VALUES ('0266', '路虎', '路虎', '路虎');
INSERT INTO `orbit_vehicle_brand` VALUES ('0267', '轻骑', '轻骑', '轻骑');
INSERT INTO `orbit_vehicle_brand` VALUES ('0268', '达夫', '达夫', '达夫');
INSERT INTO `orbit_vehicle_brand` VALUES ('0269', '迈凯伦', '迈凯伦', '迈凯伦');
INSERT INTO `orbit_vehicle_brand` VALUES ('0270', '迈巴赫', '迈巴赫', '迈巴赫');
INSERT INTO `orbit_vehicle_brand` VALUES ('0271', '道奇', '道奇', '道奇');
INSERT INTO `orbit_vehicle_brand` VALUES ('0272', '道爵', '道爵', '道爵');
INSERT INTO `orbit_vehicle_brand` VALUES ('0273', '重汽王牌', '重汽王牌', '重汽王牌');
INSERT INTO `orbit_vehicle_brand` VALUES ('0274', '重汽豪沃', '重汽豪沃', '重汽豪沃');
INSERT INTO `orbit_vehicle_brand` VALUES ('0275', '野马', '野马', '野马');
INSERT INTO `orbit_vehicle_brand` VALUES ('0276', '野马汽车', '野马汽车', '野马汽车');
INSERT INTO `orbit_vehicle_brand` VALUES ('0277', '金旅', '金旅', '金旅');
INSERT INTO `orbit_vehicle_brand` VALUES ('0278', '金杯', '金杯', '金杯');
INSERT INTO `orbit_vehicle_brand` VALUES ('0279', '金龙', '金龙', '金龙');
INSERT INTO `orbit_vehicle_brand` VALUES ('0280', '铂骏重卡', '铂骏重卡', '铂骏重卡');
INSERT INTO `orbit_vehicle_brand` VALUES ('0281', '铃木', '铃木', '铃木');
INSERT INTO `orbit_vehicle_brand` VALUES ('0282', '长城', '长城', '长城');
INSERT INTO `orbit_vehicle_brand` VALUES ('0283', '长安', '长安', '长安');
INSERT INTO `orbit_vehicle_brand` VALUES ('0284', '长安跨越', '长安跨越', '长安跨越');
INSERT INTO `orbit_vehicle_brand` VALUES ('0285', '长安重汽', '长安重汽', '长安重汽');
INSERT INTO `orbit_vehicle_brand` VALUES ('0286', '长征', '长征', '长征');
INSERT INTO `orbit_vehicle_brand` VALUES ('0287', '长江', '长江', '长江');
INSERT INTO `orbit_vehicle_brand` VALUES ('0288', '阿尔法罗密欧', '阿尔法罗密欧', '阿尔法罗密欧');
INSERT INTO `orbit_vehicle_brand` VALUES ('0289', '阿斯顿马丁', '阿斯顿马丁', '阿斯顿马丁');
INSERT INTO `orbit_vehicle_brand` VALUES ('0290', '陆地方舟', '陆地方舟', '陆地方舟');
INSERT INTO `orbit_vehicle_brand` VALUES ('0291', '陆风', '陆风', '陆风');
INSERT INTO `orbit_vehicle_brand` VALUES ('0292', '陕汽', '陕汽', '陕汽');
INSERT INTO `orbit_vehicle_brand` VALUES ('0293', '陕汽通家', '陕汽通家', '陕汽通家');
INSERT INTO `orbit_vehicle_brand` VALUES ('0294', '雪佛兰', '雪佛兰', '雪佛兰');
INSERT INTO `orbit_vehicle_brand` VALUES ('0295', '雪铁龙', '雪铁龙', '雪铁龙');
INSERT INTO `orbit_vehicle_brand` VALUES ('0296', '雷丁', '雷丁', '雷丁');
INSERT INTO `orbit_vehicle_brand` VALUES ('0297', '雷克萨斯', '雷克萨斯', '雷克萨斯');
INSERT INTO `orbit_vehicle_brand` VALUES ('0298', '雷诺', '雷诺', '雷诺');
INSERT INTO `orbit_vehicle_brand` VALUES ('0299', '霍顿', '霍顿', '霍顿');
INSERT INTO `orbit_vehicle_brand` VALUES ('0300', '青岛解放', '青岛解放', '青岛解放');
INSERT INTO `orbit_vehicle_brand` VALUES ('0301', '青年', '青年', '青年');
INSERT INTO `orbit_vehicle_brand` VALUES ('0302', '领志', '领志', '领志');
INSERT INTO `orbit_vehicle_brand` VALUES ('0303', '飞碟', '飞碟', '飞碟');
INSERT INTO `orbit_vehicle_brand` VALUES ('0304', '飞碟奥驰', '飞碟奥驰', '飞碟奥驰');
INSERT INTO `orbit_vehicle_brand` VALUES ('0305', '飞驰', '飞驰', '飞驰');
INSERT INTO `orbit_vehicle_brand` VALUES ('0306', '马自达', '马自达', '马自达');
INSERT INTO `orbit_vehicle_brand` VALUES ('0307', '黄海', '黄海', '黄海');
INSERT INTO `orbit_vehicle_brand` VALUES ('0308', '黑豹', '黑豹', '黑豹');
INSERT INTO `orbit_vehicle_brand` VALUES ('0309', '齐鲁客车', '齐鲁客车', '齐鲁客车');
INSERT INTO `orbit_vehicle_brand` VALUES ('9999', '未知', '未知', '未知');

-- ----------------------------
-- Table structure for orbit_vehicle_brand_child
-- ----------------------------
DROP TABLE IF EXISTS `orbit_vehicle_brand_child`;
CREATE TABLE `orbit_vehicle_brand_child` (
  `CODE` varchar(64) NOT NULL COMMENT '代码',
  `ZH_NAME` varchar(64) DEFAULT NULL COMMENT '中文名',
  `EN_NAME` varchar(64) DEFAULT NULL COMMENT '英文名',
  `ES_NAME` varchar(64) DEFAULT NULL COMMENT '西班牙语',
  `PARENT_CODE` varchar(64) NOT NULL COMMENT '父品牌code',
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆子品牌表';

-- ----------------------------
-- Records of orbit_vehicle_brand_child
-- ----------------------------
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00000000', 'KNIGHTXV', 'KNIGHTXV', 'KNIGHTXV', '0000');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00009999', '未知', '未知', '未知', '0000');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00010000', 'Dokker', 'Dokker', 'Dokker', '0001');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00010001', 'LODGY', 'LODGY', 'LODGY', '0001');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00019999', '未知', '未知', '未知', '0001');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00020000', 'GOCROSS', 'GOCROSS', 'GOCROSS', '0002');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00020001', 'MIDO', 'MIDO', 'MIDO', '0002');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00020002', 'ONDO', 'ONDO', 'ONDO', '0002');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00029999', '未知', '未知', '未知', '0002');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00030000', '3', '3', '3', '0003');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00030001', '4', '4', '4', '0003');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00030002', '4S', '4S', '4S', '0003');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00030003', '5', '5', '5', '0003');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00030004', '5(含进口版)', '5(含进口版)', '5(含进口版)', '0003');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00030005', '5LS', '5LS', '5LS', '0003');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00030006', '6', '6', '6', '0003');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00030007', '7', '7', '7', '0003');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00039999', '未知', '未知', '未知', '0003');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00040000', 'KARMA', 'KARMA', 'KARMA', '0004');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00049999', '未知', '未知', '未知', '0004');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050000', 'ACADIA', 'ACADIA', 'ACADIA', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050001', 'CANYON', 'CANYON', 'CANYON', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050002', 'SAVANA', 'SAVANA', 'SAVANA', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050003', 'SAVANA(两驱版)', 'SAVANA(两驱版)', 'SAVANA(两驱版)', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050004', 'SAVANA(公务版)', 'SAVANA(公务版)', 'SAVANA(公务版)', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050005', 'SAVANA(商务之星)', 'SAVANA(商务之星)', 'SAVANA(商务之星)', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050006', 'SAVANA(豪华隐私屏版)', 'SAVANA(豪华隐私屏版)', 'SAVANA(豪华隐私屏版)', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050007', 'SAVANA(运动版)', 'SAVANA(运动版)', 'SAVANA(运动版)', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050008', 'SAVANA(长轴领袖版)', 'SAVANA(长轴领袖版)', 'SAVANA(长轴领袖版)', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050009', 'SAVANA(领袖级商务车)', 'SAVANA(领袖级商务车)', 'SAVANA(领袖级商务车)', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050010', 'SAVANA(领袖级至尊版)', 'SAVANA(领袖级至尊版)', 'SAVANA(领袖级至尊版)', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050011', 'SIERRA(ALLTERRAIN)', 'SIERRA(ALLTERRAIN)', 'SIERRA(ALLTERRAIN)', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050012', 'SIERRA(DANALI)', 'SIERRA(DANALI)', 'SIERRA(DANALI)', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050013', 'SIERRA(Danali)', 'SIERRA(Danali)', 'SIERRA(Danali)', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050014', 'SIERRA(HD)', 'SIERRA(HD)', 'SIERRA(HD)', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050015', 'SIERRA(基本型)', 'SIERRA(基本型)', 'SIERRA(基本型)', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050016', 'SIERRA(基本款)', 'SIERRA(基本款)', 'SIERRA(基本款)', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050017', 'SIERRA(豪华版)', 'SIERRA(豪华版)', 'SIERRA(豪华版)', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050018', 'TERRAIN', 'TERRAIN', 'TERRAIN', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050019', 'TERRAIN(基本款)', 'TERRAIN(基本款)', 'TERRAIN(基本款)', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050020', 'YUKON', 'YUKON', 'YUKON', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00050021', 'YUKON(SLT)', 'YUKON(SLT)', 'YUKON(SLT)', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00059999', '未知', '未知', '未知', '0005');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00060000', 'APOLLO', 'APOLLO', 'APOLLO', '0006');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00069999', '未知', '未知', '未知', '0006');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00070000', 'VELOCIRAPTOR', 'VELOCIRAPTOR', 'VELOCIRAPTOR', '0007');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00079999', '未知', '未知', '未知', '0007');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00080000', '北京JEEP', '北京JEEP', '北京JEEP', '0008');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00080001', '大切诺基', '大切诺基', '大切诺基', '0008');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00080002', '大切诺基(进口)', '大切诺基(进口)', '大切诺基(进口)', '0008');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00080003', '大切诺基(进口)(SUMMIT)', '大切诺基(进口)(SUMMIT)', '大切诺基(进口)(SUMMIT)', '0008');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00080004', '指南者', '指南者', '指南者', '0008');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00080005', '指南者(进口)', '指南者(进口)', '指南者(进口)', '0008');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00080006', '指挥官', '指挥官', '指挥官', '0008');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00080007', '牧马人', '牧马人', '牧马人', '0008');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00080008', '自由人', '自由人', '自由人', '0008');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00080009', '自由侠', '自由侠', '自由侠', '0008');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00080010', '自由光', '自由光', '自由光', '0008');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00080011', '自由客', '自由客', '自由客', '0008');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00089999', '未知', '未知', '未知', '0008');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00090000', 'XBOW', 'XBOW', 'XBOW', '0009');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00099999', '未知', '未知', '未知', '0009');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00100000', 'RELLY.FIGHTER', 'RELLY.FIGHTER', 'RELLY.FIGHTER', '0010');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00109999', '未知', '未知', '未知', '0010');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00110000', 'EVANTRA', 'EVANTRA', 'EVANTRA', '0011');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00119999', '未知', '未知', '未知', '0011');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00120000', 'IGS', 'IGS', 'IGS', '0012');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00120001', 'MG3', 'MG3', 'MG3', '0012');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00120002', 'MG3SW', 'MG3SW', 'MG3SW', '0012');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00120003', 'MG3Xross', 'MG3Xross', 'MG3Xross', '0012');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00120004', 'MG5', 'MG5', 'MG5', '0012');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00120005', 'MG6', 'MG6', 'MG6', '0012');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00120006', 'MG7', 'MG7', 'MG7', '0012');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00120007', 'MG7(自动加长旗舰型)', 'MG7(自动加长旗舰型)', 'MG7(自动加长旗舰型)', '0012');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00120008', 'MGTF', 'MGTF', 'MGTF', '0012');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00120009', '名爵ZS', '名爵ZS', '名爵ZS', '0012');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00120010', '锐腾', '锐腾', '锐腾', '0012');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00120011', '锐行', '锐行', '锐行', '0012');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00129999', '未知', '未知', '未知', '0012');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00130000', 'CLUBMAN.COOPER.S', 'CLUBMAN.COOPER.S', 'CLUBMAN.COOPER.S', '0013');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00130001', 'COOPER', 'COOPER', 'COOPER', '0013');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00130002', 'COOPER(改款)', 'COOPER(改款)', 'COOPER(改款)', '0013');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00130003', 'COOPER.E', 'COOPER.E', 'COOPER.E', '0013');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00130004', 'COOPER.S', 'COOPER.S', 'COOPER.S', '0013');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00130005', 'COOPER.S(中国任务版)', 'COOPER.S(中国任务版)', 'COOPER.S(中国任务版)', '0013');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00130006', 'COOPER.S(五门版)', 'COOPER.S(五门版)', 'COOPER.S(五门版)', '0013');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00130007', 'COOPER.S.CABRIO', 'COOPER.S.CABRIO', 'COOPER.S.CABRIO', '0013');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00130008', 'COOPER.SGP', 'COOPER.SGP', 'COOPER.SGP', '0013');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00130009', 'COUNTRYMAN', 'COUNTRYMAN', 'COUNTRYMAN', '0013');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00130010', 'COUNTRYMAN.COOPER.S', 'COUNTRYMAN.COOPER.S', 'COUNTRYMAN.COOPER.S', '0013');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00130011', 'COUNTRYMAN.COOPER.S(插电式混动版)', 'COUNTRYMAN.COOPER.S(插电式混动版)', 'COUNTRYMAN.COOPER.S(插电式混动版)', '0013');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00130012', 'COUNTRYMAN.COOPER.S.ALL4', 'COUNTRYMAN.COOPER.S.ALL4', 'COUNTRYMAN.COOPER.S.ALL4', '0013');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00130013', 'GOODWOOD', 'GOODWOOD', 'GOODWOOD', '0013');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00130014', 'MINI', 'MINI', 'MINI', '0013');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00130015', 'ONE', 'ONE', 'ONE', '0013');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00139999', '未知', '未知', '未知', '0013');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00140000', 'M14', 'M14', 'M14', '0014');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00140001', 'M600', 'M600', 'M600', '0014');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00149999', '未知', '未知', '未知', '0014');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00150000', 'BEAST', 'BEAST', 'BEAST', '0015');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00159999', '未知', '未知', '未知', '0015');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00160000', 'ONE', 'ONE', 'ONE', '0016');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00169999', '未知', '未知', '未知', '0016');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00170000', 'BUDII', 'BUDII', 'BUDII', '0017');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00170001', 'SQUBA', 'SQUBA', 'SQUBA', '0017');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00170002', 'XCHANGEE', 'XCHANGEE', 'XCHANGEE', '0017');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00179999', '未知', '未知', '未知', '0017');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00180000', 'FRS', 'FRS', 'FRS', '0018');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00180001', 'IA', 'IA', 'IA', '0018');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00180002', 'IM', 'IM', 'IM', '0018');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00180003', 'IQ', 'IQ', 'IQ', '0018');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00180004', 'XB', 'XB', 'XB', '0018');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00189999', '未知', '未知', '未知', '0018');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00190000', 'FORFOUR', 'FORFOUR', 'FORFOUR', '0019');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00190001', 'FORFOUR(先锋版)', 'FORFOUR(先锋版)', 'FORFOUR(先锋版)', '0019');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00190002', 'FORTWO', 'FORTWO', 'FORTWO', '0019');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00190003', 'FORTWO(博速XCLUSIVE版)', 'FORTWO(博速XCLUSIVE版)', 'FORTWO(博速XCLUSIVE版)', '0019');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00190004', 'FORTWO(硬顶激情版)', 'FORTWO(硬顶激情版)', 'FORTWO(硬顶激情版)', '0019');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00199999', '未知', '未知', '未知', '0019');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00200000', 'SAGARIS', 'SAGARIS', 'SAGARIS', '0020');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00200001', 'TUSCAN', 'TUSCAN', 'TUSCAN', '0020');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00209999', '未知', '未知', '未知', '0020');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00210000', '优迪狮重卡', '优迪狮重卡', '优迪狮重卡', '0021');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00210001', '优迪骐', '优迪骐', '优迪骐', '0021');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00210002', '酷腾重卡(牵引车)', '酷腾重卡(牵引车)', '酷腾重卡(牵引车)', '0021');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00210003', '酷腾重卡(自卸车)', '酷腾重卡(自卸车)', '酷腾重卡(自卸车)', '0021');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00219999', '未知', '未知', '未知', '0021');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00220000', 'VV7', 'VV7', 'VV7', '0022');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00229999', '未知', '未知', '未知', '0022');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00230000', 'Zenvo', 'Zenvo', 'Zenvo', '0023');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00239999', '未知', '未知', '未知', '0023');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00240000', 'QUANTF', 'QUANTF', 'QUANTF', '0024');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00240001', 'QUANTINO', 'QUANTINO', 'QUANTINO', '0024');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00249999', '未知', '未知', '未知', '0024');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250000', '佳宝T51', '佳宝T51', '佳宝T51', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250001', '佳宝V51', '佳宝V51', '佳宝V51', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250002', '佳宝V52', '佳宝V52', '佳宝V52', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250003', '佳宝V70', '佳宝V70', '佳宝V70', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250004', '佳宝V75(含佳宝V77)', '佳宝V75(含佳宝V77)', '佳宝V75(含佳宝V77)', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250005', '坤程轿卡', '坤程轿卡', '坤程轿卡', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250006', '夏利2000', '夏利2000', '夏利2000', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250007', '夏利N3', '夏利N3', '夏利N3', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250008', '夏利N3加', '夏利N3加', '夏利N3加', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250009', '夏利N5', '夏利N5', '夏利N5', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250010', '夏利N7', '夏利N7', '夏利N7', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250011', '夏利TJ7100', '夏利TJ7100', '夏利TJ7100', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250012', '夏利TJ7100A', '夏利TJ7100A', '夏利TJ7100A', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250013', '夏利绅雅', '夏利绅雅', '夏利绅雅', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250014', '威乐', '威乐', '威乐', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250015', '威姿', '威姿', '威姿', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250016', '威志', '威志', '威志', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250017', '威志(改丰田标)', '威志(改丰田标)', '威志(改丰田标)', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250018', '威志V2', '威志V2', '威志V2', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250019', '威志V5', '威志V5', '威志V5', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250020', '新赛宝', '新赛宝', '新赛宝', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250021', '校车01', '校车01', '校车01', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250022', '森雅M80', '森雅M80', '森雅M80', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250023', '森雅R7', '森雅R7', '森雅R7', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250024', '森雅S80', '森雅S80', '森雅S80', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250025', '欧朗', '欧朗', '欧朗', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250026', '自由风', '自由风', '自由风', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250027', '解放T80', '解放T80', '解放T80', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250028', '骏派A70', '骏派A70', '骏派A70', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00250029', '骏派D60', '骏派D60', '骏派D60', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00259999', '未知', '未知', '未知', '0025');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00260000', 'CAL1041DCRE5', 'CAL1041DCRE5', 'CAL1041DCRE5', '0026');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00260001', '牵引车(特大货)', '牵引车(特大货)', '牵引车(特大货)', '0026');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00269999', '未知', '未知', '未知', '0026');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00270000', 'X80', 'X80', 'X80', '0027');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00279999', '未知', '未知', '未知', '0027');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00280000', '安捷重卡', '安捷重卡', '安捷重卡', '0028');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00280001', '神力L4R中卡LZT3161K2E5A95', '神力L4R中卡LZT3161K2E5A95', '神力L4R中卡LZT3161K2E5A95', '0028');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00280002', '腾威L5M重卡LZT3250PK2E3BT3A90', '腾威L5M重卡LZT3250PK2E3BT3A90', '腾威L5M重卡LZT3250PK2E3BT3A90', '0028');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00280003', '金陆中卡', '金陆中卡', '金陆中卡', '0028');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00289999', '未知', '未知', '未知', '0028');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00290000', '解放霸铃2代', '解放霸铃2代', '解放霸铃2代', '0029');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00290001', '解放霸铃轻卡', '解放霸铃轻卡', '解放霸铃轻卡', '0029');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00299999', '未知', '未知', '未知', '0029');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00300000', '501', '501', '501', '0030');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00300001', 'J6F', 'J6F', 'J6F', '0030');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00300002', 'J6L载货车', 'J6L载货车', 'J6L载货车', '0030');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00300003', 'J6P混凝土车(大货)', 'J6P混凝土车(大货)', 'J6P混凝土车(大货)', '0030');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00300004', 'J6P牵引车(牵引车头)', 'J6P牵引车(牵引车头)', 'J6P牵引车(牵引车头)', '0030');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00300005', 'J6P牵引车(特大货)', 'J6P牵引车(特大货)', 'J6P牵引车(特大货)', '0030');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00300006', 'J6P牵引车2(特大货)', 'J6P牵引车2(特大货)', 'J6P牵引车2(特大货)', '0030');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00300007', 'J6P牵引车头(大货)', 'J6P牵引车头(大货)', 'J6P牵引车头(大货)', '0030');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00300008', '华凯', '华凯', '华凯', '0030');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00300009', '金铃', '金铃', '金铃', '0030');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00309999', '未知', '未知', '未知', '0030');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00310000', '虎V', '虎V', '虎V', '0031');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00319999', '未知', '未知', '未知', '0031');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00320000', '小解放1', '小解放1', '小解放1', '0032');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00320001', '小解放498', '小解放498', '小解放498', '0032');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00320002', '福瑞', '福瑞', '福瑞', '0032');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00320003', '霸铃', '霸铃', '霸铃', '0032');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00329999', '未知', '未知', '未知', '0032');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00330000', 'SY5252GJB1D', 'SY5252GJB1D', 'SY5252GJB1D', '0033');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00339999', '未知', '未知', '未知', '0033');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00340000', '万山重卡', '万山重卡', '万山重卡', '0034');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00349999', '未知', '未知', '未知', '0034');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00350000', '昊龙T260', '昊龙T260', '昊龙T260', '0035');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00350001', '昊龙T310', '昊龙T310', '昊龙T310', '0035');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00359999', '未知', '未知', '未知', '0035');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00360000', '120小卡', '120小卡', '120小卡', '0036');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00360001', 'T3创客', 'T3创客', 'T3创客', '0036');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00360002', 'T3豪华版', 'T3豪华版', 'T3豪华版', '0036');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00360003', '御龙重卡STQ3314L16Y4B5', '御龙重卡STQ3314L16Y4B5', '御龙重卡STQ3314L16Y4B5', '0036');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00360004', '御龙重卡STQ3316L16Y4B14(中货)', '御龙重卡STQ3316L16Y4B14(中货)', '御龙重卡STQ3316L16Y4B14(中货)', '0036');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00360005', '御龙重卡STQ3316L16Y4B14(大货)', '御龙重卡STQ3316L16Y4B14(大货)', '御龙重卡STQ3316L16Y4B14(大货)', '0036');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00360006', '昊龙轻卡', '昊龙轻卡', '昊龙轻卡', '0036');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00360007', '福星卡1049', '福星卡1049', '福星卡1049', '0036');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00369999', '未知', '未知', '未知', '0036');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370000', 'LANCER(EVOLUTION X)', 'LANCER(EVOLUTION X)', 'LANCER(EVOLUTION X)', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370001', 'SUV', 'SUV', 'SUV', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370002', '三菱I', '三菱I', '三菱I', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370003', '三菱I(IMIEV)', '三菱I(IMIEV)', '三菱I(IMIEV)', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370004', '三菱I(改款2)', '三菱I(改款2)', '三菱I(改款2)', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370005', '三菱I(改款3)', '三菱I(改款3)', '三菱I(改款3)', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370006', '劲炫ASX', '劲炫ASX', '劲炫ASX', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370007', '劲炫ASX(进口)', '劲炫ASX(进口)', '劲炫ASX(进口)', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370008', '君阁', '君阁', '君阁', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370009', '帕杰罗', '帕杰罗', '帕杰罗', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370010', '帕杰罗(改装)', '帕杰罗(改装)', '帕杰罗(改装)', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370011', '帕杰罗(进口)', '帕杰罗(进口)', '帕杰罗(进口)', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370012', '帕杰罗劲畅', '帕杰罗劲畅', '帕杰罗劲畅', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370013', '帕杰罗劲畅(进口)', '帕杰罗劲畅(进口)', '帕杰罗劲畅(进口)', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370014', '帕杰罗速跑', '帕杰罗速跑', '帕杰罗速跑', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370015', '戈蓝', '戈蓝', '戈蓝', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370016', '格蓝迪', '格蓝迪', '格蓝迪', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370017', '欧蓝德', '欧蓝德', '欧蓝德', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370018', '欧蓝德(进口)', '欧蓝德(进口)', '欧蓝德(进口)', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370019', '翼神', '翼神', '翼神', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370020', '菱绅', '菱绅', '菱绅', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370021', '蓝瑟', '蓝瑟', '蓝瑟', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00370022', '风迪思', '风迪思', '风迪思', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00379999', '未知', '未知', '未知', '0037');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380000', '1', '1', '1', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380001', '11', '11', '11', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380002', '12', '12', '12', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380003', '13', '13', '13', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380004', '14', '14', '14', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380005', '15', '15', '15', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380006', '16', '16', '16', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380007', '2', '2', '2', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380008', '3', '3', '3', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380009', '4', '4', '4', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380010', '5', '5', '5', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380011', '6', '6', '6', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380012', '7', '7', '7', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380013', '8', '8', '8', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380014', '9', '9', '9', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380015', '五征01', '五征01', '五征01', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380016', '五征02', '五征02', '五征02', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380017', '五征03', '五征03', '五征03', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380018', '力之星', '力之星', '力之星', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380019', '时风华庆', '时风华庆', '时风华庆', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380020', '时风巨星', '时风巨星', '时风巨星', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380021', '时风新巨星', '时风新巨星', '时风新巨星', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380022', '时风风云', '时风风云', '时风风云', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00380023', '时风风云2', '时风风云2', '时风风云2', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00389999', '未知', '未知', '未知', '0038');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00390000', 'G10', 'G10', 'G10', '0039');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00390001', 'G10(豪华版)', 'G10(豪华版)', 'G10(豪华版)', '0039');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00390002', 'T60', 'T60', 'T60', '0039');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00390003', 'V80', 'V80', 'V80', '0039');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00390004', '伊思坦纳', '伊思坦纳', '伊思坦纳', '0039');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00390005', '伊思坦纳(改款)', '伊思坦纳(改款)', '伊思坦纳(改款)', '0039');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00399999', '未知', '未知', '未知', '0039');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00400000', '帅虎H500(33)', '帅虎H500(33)', '帅虎H500(33)', '0040');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00400001', '开拓X500(SH3042VEDCMZ)', '开拓X500(SH3042VEDCMZ)', '开拓X500(SH3042VEDCMZ)', '0040');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00400002', '跃进(NJ1042DCFT)', '跃进(NJ1042DCFT)', '跃进(NJ1042DCFT)', '0040');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00400003', '跃进(NJ1050HDFL3)', '跃进(NJ1050HDFL3)', '跃进(NJ1050HDFL3)', '0040');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00409999', '未知', '未知', '未知', '0040');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00410000', 'B6', 'B6', 'B6', '0041');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00410001', 'C8', 'C8', 'C8', '0041');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00410002', 'D12', 'D12', 'D12', '0041');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00419999', '未知', '未知', '未知', '0041');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420000', 'DX3', 'DX3', 'DX3', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420001', 'DX7', 'DX7', 'DX7', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420002', 'DX7(改装中网)', 'DX7(改装中网)', 'DX7(改装中网)', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420003', 'V3菱悦', 'V3菱悦', 'V3菱悦', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420004', 'V3菱悦(幸福版)', 'V3菱悦(幸福版)', 'V3菱悦(幸福版)', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420005', 'V3菱悦(改三菱标)', 'V3菱悦(改三菱标)', 'V3菱悦(改三菱标)', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420006', 'V3菱悦(改装)', 'V3菱悦(改装)', 'V3菱悦(改装)', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420007', 'V3菱悦(改装中网)', 'V3菱悦(改装中网)', 'V3菱悦(改装中网)', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420008', 'V3菱悦(精明版)', 'V3菱悦(精明版)', 'V3菱悦(精明版)', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420009', 'V3菱悦(风采版)', 'V3菱悦(风采版)', 'V3菱悦(风采版)', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420010', 'V5菱致', 'V5菱致', 'V5菱致', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420011', 'V5菱致(plus智尊型)', 'V5菱致(plus智尊型)', 'V5菱致(plus智尊型)', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420012', 'V5菱致(plus智控型)', 'V5菱致(plus智控型)', 'V5菱致(plus智控型)', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420013', 'V5菱致(改三菱标)', 'V5菱致(改三菱标)', 'V5菱致(改三菱标)', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420014', 'V6菱仕', 'V6菱仕', 'V6菱仕', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420015', 'V6菱仕(CROSS版)', 'V6菱仕(CROSS版)', 'V6菱仕(CROSS版)', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420016', 'V6菱仕(改装中网)', 'V6菱仕(改装中网)', 'V6菱仕(改装中网)', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420017', '富利卡', '富利卡', '富利卡', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420018', '富利卡(改三菱标)', '富利卡(改三菱标)', '富利卡(改三菱标)', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420019', '希旺', '希旺', '希旺', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420020', '得利卡', '得利卡', '得利卡', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420021', '菱利', '菱利', '菱利', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420022', '菱帅(GXLi)', '菱帅(GXLi)', '菱帅(GXLi)', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00420023', '菱帅(Sei,Exi)', '菱帅(Sei,Exi)', '菱帅(Sei,Exi)', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00429999', '未知', '未知', '未知', '0042');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00430000', '威斯曼V3', '威斯曼V3', '威斯曼V3', '0043');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00439999', '未知', '未知', '未知', '0043');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00440000', 'LT1030JBC2E型载货汽车', 'LT1030JBC2E型载货汽车', 'LT1030JBC2E型载货汽车', '0044');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00440001', 'LT1082PB6E', 'LT1082PB6E', 'LT1082PB6E', '0044');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00440002', '单桥载货汽车', '单桥载货汽车', '单桥载货汽车', '0044');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00449999', '未知', '未知', '未知', '0044');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450000', '140垃圾车', '140垃圾车', '140垃圾车', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450001', 'DFA1030L30D4', 'DFA1030L30D4', 'DFA1030L30D4', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450002', 'DFA6570KJ4B', 'DFA6570KJ4B', 'DFA6570KJ4B', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450003', 'DFA6720K4A', 'DFA6720K4A', 'DFA6720K4A', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450004', 'DFA6720KB05', 'DFA6720KB05', 'DFA6720KB05', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450005', 'DFA6750K3BG', 'DFA6750K3BG', 'DFA6750K3BG', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450006', 'EQ1030T37DAC', 'EQ1030T37DAC', 'EQ1030T37DAC', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450007', 'EQ1120GF1', 'EQ1120GF1', 'EQ1120GF1', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450008', 'EQ1120GL1', 'EQ1120GL1', 'EQ1120GL1', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450009', 'EQ1141G7D', 'EQ1141G7D', 'EQ1141G7D', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450010', 'EQ1240W', 'EQ1240W', 'EQ1240W', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450011', 'EQ1342GE1', 'EQ1342GE1', 'EQ1342GE1', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450012', 'EQ3243VB', 'EQ3243VB', 'EQ3243VB', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450013', 'EQ3243VB3G(大货)', 'EQ3243VB3G(大货)', 'EQ3243VB3G(大货)', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450014', 'EQ6121L4D', 'EQ6121L4D', 'EQ6121L4D', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450015', 'EQ6600HD3G', 'EQ6600HD3G', 'EQ6600HD3G', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450016', 'EQ6600PCN50', 'EQ6600PCN50', 'EQ6600PCN50', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450017', 'EQ6607LT5', 'EQ6607LT5', 'EQ6607LT5', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450018', 'EQ6662PC', 'EQ6662PC', 'EQ6662PC', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450019', 'EQ6668LT1', 'EQ6668LT1', 'EQ6668LT1', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450020', 'EQ6730P3G', 'EQ6730P3G', 'EQ6730P3G', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450021', 'EQ6790PT7', 'EQ6790PT7', 'EQ6790PT7', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450022', 'P68', 'P68', 'P68', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450023', '东风专底', '东风专底', '东风专底', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450024', '东风特商', '东风特商', '东风特商', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450025', '乘龙H5牵引车(特大货)', '乘龙H5牵引车(特大货)', '乘龙H5牵引车(特大货)', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450026', '俊风', '俊风', '俊风', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450027', '俊风CV03', '俊风CV03', '俊风CV03', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450028', '凯普特C系列', '凯普特C系列', '凯普特C系列', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450029', '凯普特N系列', '凯普特N系列', '凯普特N系列', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450030', '创普中卡', '创普中卡', '创普中卡', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450031', '创普准重卡', '创普准重卡', '创普准重卡', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450032', '创普新类', '创普新类', '创普新类', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450033', '力拓4110系列', '力拓4110系列', '力拓4110系列', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450034', '力拓T15(EQ3041L8GDAAC)', '力拓T15(EQ3041L8GDAAC)', '力拓T15(EQ3041L8GDAAC)', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450035', '劲卡(中货)', '劲卡(中货)', '劲卡(中货)', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450036', '劲卡(小货)', '劲卡(小货)', '劲卡(小货)', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450037', '华神T3', '华神T3', '华神T3', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450038', '嘉运', '嘉运', '嘉运', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450039', '多利卡D5', '多利卡D5', '多利卡D5', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450040', '多利卡D7', '多利卡D7', '多利卡D7', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450041', '多利卡D9', '多利卡D9', '多利卡D9', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450042', '多利卡EQ1056T3AC', '多利卡EQ1056T3AC', '多利卡EQ1056T3AC', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450043', '多利卡XL(DFA1160L15D7)', '多利卡XL(DFA1160L15D7)', '多利卡XL(DFA1160L15D7)', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450044', '多利卡锐铃', '多利卡锐铃', '多利卡锐铃', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450045', '大力神', '大力神', '大力神', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450046', '天锦DFL5160XXYBX2A1', '天锦DFL5160XXYBX2A1', '天锦DFL5160XXYBX2A1', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450047', '天锦中卡自卸车', '天锦中卡自卸车', '天锦中卡自卸车', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450048', '天锦中卡载货车', '天锦中卡载货车', '天锦中卡载货车', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450049', '天龙(大货)', '天龙(大货)', '天龙(大货)', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450050', '天龙(改款)', '天龙(改款)', '天龙(改款)', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450051', '天龙(特大货)', '天龙(特大货)', '天龙(特大货)', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450052', '天龙旗舰', '天龙旗舰', '天龙旗舰', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450053', '天龙载货车', '天龙载货车', '天龙载货车', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450054', '奥丁', '奥丁', '奥丁', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450055', '小卡王', '小卡王', '小卡王', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450056', '小霸王V', '小霸王V', '小霸王V', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450057', '小霸王栏板轻卡', '小霸王栏板轻卡', '小霸王栏板轻卡', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450058', '尖头EQ5120XXYL5', '尖头EQ5120XXYL5', '尖头EQ5120XXYL5', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450059', '帅客', '帅客', '帅客', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450060', '康明斯', '康明斯', '康明斯', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450061', '康霸', '康霸', '康霸', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450062', '康霸老款', '康霸老款', '康霸老款', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450063', '御轩', '御轩', '御轩', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450064', '御风', '御风', '御风', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450065', '御龙CNG牵引车', '御龙CNG牵引车', '御龙CNG牵引车', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450066', '特商EQ3160GFV2(小货)', '特商EQ3160GFV2(小货)', '特商EQ3160GFV2(小货)', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450067', '特商自卸车', '特商自卸车', '特商自卸车', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450068', '皮卡P65', '皮卡P65', '皮卡P65', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450069', '福瑞卡490', '福瑞卡490', '福瑞卡490', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450070', '福瑞卡S', '福瑞卡S', '福瑞卡S', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450071', '老款小霸王', '老款小霸王', '老款小霸王', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450072', '虎视', '虎视', '虎视', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450073', '金霸', '金霸', '金霸', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450074', '锐骐皮卡', '锐骐皮卡', '锐骐皮卡', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450075', '锐骐皮卡(领航版)', '锐骐皮卡(领航版)', '锐骐皮卡(领航版)', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00450076', '龙卡EQ4150AE', '龙卡EQ4150AE', '龙卡EQ4150AE', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00459999', '未知', '未知', '未知', '0045');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00460000', 'T5(EQ5168CCYL)', 'T5(EQ5168CCYL)', 'T5(EQ5168CCYL)', '0046');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00460001', '御虎吊车', '御虎吊车', '御虎吊车', '0046');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00460002', '御虎自卸车', '御虎自卸车', '御虎自卸车', '0046');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00460003', '御龙EQ3258GLJ1', '御龙EQ3258GLJ1', '御龙EQ3258GLJ1', '0046');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00460004', '御龙EQ4160GLN', '御龙EQ4160GLN', '御龙EQ4160GLN', '0046');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00460005', '擎宇轻卡', '擎宇轻卡', '擎宇轻卡', '0046');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00469999', '未知', '未知', '未知', '0046');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00470000', '嘉龙龙驹(EQ5041XXYN40)', '嘉龙龙驹(EQ5041XXYN40)', '嘉龙龙驹(EQ5041XXYN40)', '0047');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00470001', '龙腾', '龙腾', '龙腾', '0047');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00470002', '龙驹', '龙驹', '龙驹', '0047');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00479999', '未知', '未知', '未知', '0047');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00480000', 'C37', 'C37', 'C37', '0048');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00480001', 'K01', 'K01', 'K01', '0048');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00480002', 'K02', 'K02', 'K02', '0048');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00480003', 'K05S', 'K05S', 'K05S', '0048');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00480004', 'K07', 'K07', 'K07', '0048');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00480005', 'K07(微面)', 'K07(微面)', 'K07(微面)', '0048');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00480006', 'K07(改装)', 'K07(改装)', 'K07(改装)', '0048');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00480007', 'K07II', 'K07II', 'K07II', '0048');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00480008', 'K07S', 'K07S', 'K07S', '0048');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00480009', 'K17', 'K17', 'K17', '0048');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00480010', 'V07S', 'V07S', 'V07S', '0048');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00480011', 'V21', 'V21', 'V21', '0048');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00480012', 'V29', 'V29', 'V29', '0048');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00489999', '未知', '未知', '未知', '0048');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00490000', '乘龙H7', '乘龙H7', '乘龙H7', '0049');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00490001', '乘龙LZ3311REFA', '乘龙LZ3311REFA', '乘龙LZ3311REFA', '0049');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00490002', '乘龙LZ4241QCA', '乘龙LZ4241QCA', '乘龙LZ4241QCA', '0049');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00490003', '乘龙M3', '乘龙M3', '乘龙M3', '0049');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00490004', '乘龙M5', '乘龙M5', '乘龙M5', '0049');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00490005', '乘龙T7', '乘龙T7', '乘龙T7', '0049');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00490006', '乘龙王子', '乘龙王子', '乘龙王子', '0049');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00490007', '新乘龙M3', '新乘龙M3', '新乘龙M3', '0049');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00490008', '新乘龙M3牵引车', '新乘龙M3牵引车', '新乘龙M3牵引车', '0049');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00490009', '重卡', '重卡', '重卡', '0049');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00490010', '霸龙', '霸龙', '霸龙', '0049');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00490011', '霸龙M7', '霸龙M7', '霸龙M7', '0049');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00490012', '霸龙重卡', '霸龙重卡', '霸龙重卡', '0049');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00490013', '龙卡', '龙卡', '龙卡', '0049');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00490014', '龙卡开山王', '龙卡开山王', '龙卡开山王', '0049');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00499999', '未知', '未知', '未知', '0049');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00500000', '御虎', '御虎', '御虎', '0050');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00500001', '御虎EQ3168GL', '御虎EQ3168GL', '御虎EQ3168GL', '0050');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00509999', '未知', '未知', '未知', '0050');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00510000', 'C3XR', 'C3XR', 'C3XR', '0051');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00510001', 'C4L', 'C4L', 'C4L', '0051');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00510002', 'C4世嘉', 'C4世嘉', 'C4世嘉', '0051');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00510003', 'C5', 'C5', 'C5', '0051');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00510004', '世嘉', '世嘉', '世嘉', '0051');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00510005', '凯旋', '凯旋', '凯旋', '0051');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00510006', '富康', '富康', '富康', '0051');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00510007', '毕加索', '毕加索', '毕加索', '0051');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00510008', '爱丽舍', '爱丽舍', '爱丽舍', '0051');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00510009', '赛纳', '赛纳', '赛纳', '0051');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00519999', '未知', '未知', '未知', '0051');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00520000', '330', '330', '330', '0052');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00520001', '360', '360', '360', '0052');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00520002', '370', '370', '370', '0052');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00520003', '580', '580', '580', '0052');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00520004', '风光330', '风光330', '风光330', '0052');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00529999', '未知', '未知', '未知', '0052');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00530000', 'MX5', 'MX5', 'MX5', '0053');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00530001', 'MX6', 'MX6', 'MX6', '0053');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00530002', '锐骐皮卡', '锐骐皮卡', '锐骐皮卡', '0053');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00539999', '未知', '未知', '未知', '0053');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00540000', 'A30', 'A30', 'A30', '0054');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00540001', 'A60', 'A60', 'A60', '0054');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00540002', 'A9', 'A9', 'A9', '0054');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00540003', 'AX3', 'AX3', 'AX3', '0054');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00540004', 'AX5', 'AX5', 'AX5', '0054');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00540005', 'AX7', 'AX7', 'AX7', '0054');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00540006', 'AX7(改装)', 'AX7(改装)', 'AX7(改装)', '0054');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00540007', 'AX7(改装中网)', 'AX7(改装中网)', 'AX7(改装中网)', '0054');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00540008', 'E30', 'E30', 'E30', '0054');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00540009', 'H30', 'H30', 'H30', '0054');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00540010', 'L60', 'L60', 'L60', '0054');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00540011', 'S30', 'S30', 'S30', '0054');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00540012', 'S30(加装LED行车灯)', 'S30(加装LED行车灯)', 'S30(加装LED行车灯)', '0054');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00549999', '未知', '未知', '未知', '0054');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550000', 'F600', 'F600', 'F600', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550001', '景逸', '景逸', '景逸', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550002', '景逸LV', '景逸LV', '景逸LV', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550003', '景逸S50', '景逸S50', '景逸S50', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550004', '景逸X3', '景逸X3', '景逸X3', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550005', '景逸X3(改装中网)', '景逸X3(改装中网)', '景逸X3(改装中网)', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550006', '景逸X5', '景逸X5', '景逸X5', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550007', '景逸X5(1.6L手动)', '景逸X5(1.6L手动)', '景逸X5(1.6L手动)', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550008', '菱智', '菱智', '菱智', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550009', '菱智M3', '菱智M3', '菱智M3', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550010', '菱智M3L', '菱智M3L', '菱智M3L', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550011', '菱智M5', '菱智M5', '菱智M5', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550012', '菱智M5Q3', '菱智M5Q3', '菱智M5Q3', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550013', '菱智V3', '菱智V3', '菱智V3', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550014', '菱越', '菱越', '菱越', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550015', '风行CM7', '风行CM7', '风行CM7', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550016', '风行S500', '风行S500', '风行S500', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00550017', '风行SX6', '风行SX6', '风行SX6', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00559999', '未知', '未知', '未知', '0055');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00560000', 'GX3', 'GX3', 'GX3', '0056');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00560001', '中兴C3', '中兴C3', '中兴C3', '0056');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00560002', '威虎G3', '威虎G3', '威虎G3', '0056');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00560003', '威虎TUV', '威虎TUV', '威虎TUV', '0056');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00560004', '客车02', '客车02', '客车02', '0056');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00560005', '小老虎', '小老虎', '小老虎', '0056');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00560006', '旗舰A9', '旗舰A9', '旗舰A9', '0056');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00560007', '无限V7', '无限V7', '无限V7', '0056');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00560008', '昌铃', '昌铃', '昌铃', '0056');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00560009', '校车01', '校车01', '校车01', '0056');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00560010', '田野', '田野', '田野', '0056');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00560011', '田野(改三菱标)', '田野(改三菱标)', '田野(改三菱标)', '0056');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00560012', '田野SUV', '田野SUV', '田野SUV', '0056');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00560013', '领主', '领主', '领主', '0056');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00569999', '未知', '未知', '未知', '0056');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00570000', 'H220', 'H220', 'H220', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00570001', 'H230', 'H230', 'H230', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00570002', 'H3', 'H3', 'H3', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00570003', 'H320', 'H320', 'H320', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00570004', 'H330', 'H330', 'H330', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00570005', 'H530', 'H530', 'H530', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00570006', 'V3', 'V3', 'V3', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00570007', 'V5', 'V5', 'V5', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00570008', '中华豚', '中华豚', '中华豚', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00570009', '尊驰', '尊驰', '尊驰', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00570010', '尊驰(改款)', '尊驰(改款)', '尊驰(改款)', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00570011', '酷宝', '酷宝', '酷宝', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00570012', '骏捷', '骏捷', '骏捷', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00570013', '骏捷(EX)', '骏捷(EX)', '骏捷(EX)', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00570014', '骏捷CROSS', '骏捷CROSS', '骏捷CROSS', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00570015', '骏捷FRV', '骏捷FRV', '骏捷FRV', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00570016', '骏捷FSV', '骏捷FSV', '骏捷FSV', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00579999', '未知', '未知', '未知', '0057');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580000', 'HOKA', 'HOKA', 'HOKA', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580001', 'HOWO悍将', 'HOWO悍将', 'HOWO悍将', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580002', 'HOWO统帅', 'HOWO统帅', 'HOWO统帅', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580003', 'SITRAK(C5H)', 'SITRAK(C5H)', 'SITRAK(C5H)', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580004', '成都王牌', '成都王牌', '成都王牌', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580005', '斯太尔', '斯太尔', '斯太尔', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580006', '斯太尔D7B(大货)', '斯太尔D7B(大货)', '斯太尔D7B(大货)', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580007', '斯太尔D7B(特大货)', '斯太尔D7B(特大货)', '斯太尔D7B(特大货)', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580008', '斯太尔M5G', '斯太尔M5G', '斯太尔M5G', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580009', '斯太尔改装', '斯太尔改装', '斯太尔改装', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580010', '斯太尔王(特大货)', '斯太尔王(特大货)', '斯太尔王(特大货)', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580011', '斯太尔王重卡', '斯太尔王重卡', '斯太尔王重卡', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580012', '斯达斯太尔重卡', '斯达斯太尔重卡', '斯达斯太尔重卡', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580013', '新黄河', '新黄河', '新黄河', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580014', '汕德卡', '汕德卡', '汕德卡', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580015', '汕德卡C7H重卡', '汕德卡C7H重卡', '汕德卡C7H重卡', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580016', '福泺H5', '福泺H5', '福泺H5', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580017', '豪曼', '豪曼', '豪曼', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580018', '豪沃(大货)', '豪沃(大货)', '豪沃(大货)', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580019', '豪沃(特大货)', '豪沃(特大货)', '豪沃(特大货)', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580020', '豪沃A7', '豪沃A7', '豪沃A7', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580021', '豪沃T5G', '豪沃T5G', '豪沃T5G', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580022', '豪沃T7G', '豪沃T7G', '豪沃T7G', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580023', '豪沃T7H', '豪沃T7H', '豪沃T7H', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580024', '豪沃牵引车头(大货)', '豪沃牵引车头(大货)', '豪沃牵引车头(大货)', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580025', '豪瀚', '豪瀚', '豪瀚', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580026', '豪瀚J7B', '豪瀚J7B', '豪瀚J7B', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580027', '豪运(大货)', '豪运(大货)', '豪运(大货)', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580028', '豪运(特大货)', '豪运(特大货)', '豪运(特大货)', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580029', '重汽王牌W1', '重汽王牌W1', '重汽王牌W1', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580030', '金王子(大货)', '金王子(大货)', '金王子(大货)', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580031', '金王子(特大货)', '金王子(特大货)', '金王子(特大货)', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580032', '黄河中卡ZZ3167F3615C1', '黄河中卡ZZ3167F3615C1', '黄河中卡ZZ3167F3615C1', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00580033', '黄河少帅', '黄河少帅', '黄河少帅', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00589999', '未知', '未知', '未知', '0058');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00590000', 'CYK6126HC4', 'CYK6126HC4', 'CYK6126HC4', '0059');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00590001', 'YCK6126HL', 'YCK6126HL', 'YCK6126HL', '0059');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00590002', '大巴01', '大巴01', '大巴01', '0059');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00599999', '未知', '未知', '未知', '0059');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600000', 'E100', 'E100', 'E100', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600001', 'LCK6125HGC', 'LCK6125HGC', 'LCK6125HGC', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600002', 'LCK6551D3X', 'LCK6551D3X', 'LCK6551D3X', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600003', 'LCK6660D1A', 'LCK6660D1A', 'LCK6660D1A', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600004', 'LCK6770D3GH', 'LCK6770D3GH', 'LCK6770D3GH', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600005', 'LCK6809H1', 'LCK6809H1', 'LCK6809H1', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600006', '世纪', '世纪', '世纪', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600007', '凯旋', '凯旋', '凯旋', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600008', '凯旋大巴', '凯旋大巴', '凯旋大巴', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600009', '凯越老款', '凯越老款', '凯越老款', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600010', '凯驰LCK6900H', '凯驰LCK6900H', '凯驰LCK6900H', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600011', '客车01', '客车01', '客车01', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600012', '巨龙', '巨龙', '巨龙', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600013', '校车01', '校车01', '校车01', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600014', '校车2', '校车2', '校车2', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600015', '领御', '领御', '领御', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600016', '领秀LCK6935H', '领秀LCK6935H', '领秀LCK6935H', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600017', '领航001', '领航001', '领航001', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600018', '领航003', '领航003', '领航003', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00600019', '领骏客车', '领骏客车', '领骏客车', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00609999', '未知', '未知', '未知', '0060');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00610000', '世纪', '世纪', '世纪', '0061');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00619999', '未知', '未知', '未知', '0061');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620000', 'Auris', 'Auris', 'Auris', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620001', 'Auris(HSD)', 'Auris(HSD)', 'Auris(HSD)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620002', 'Auris(Hybrid)', 'Auris(Hybrid)', 'Auris(Hybrid)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620003', 'Avalon', 'Avalon', 'Avalon', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620004', 'Avensis', 'Avensis', 'Avensis', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620005', 'ESTIMA', 'ESTIMA', 'ESTIMA', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620006', 'FJ酷路泽', 'FJ酷路泽', 'FJ酷路泽', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620007', 'FORTUNER', 'FORTUNER', 'FORTUNER', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620008', 'HILUX', 'HILUX', 'HILUX', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620009', 'IQ', 'IQ', 'IQ', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620010', 'IQEV', 'IQEV', 'IQEV', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620011', 'NS4', 'NS4', 'NS4', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620012', 'RAV4', 'RAV4', 'RAV4', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620013', 'RAV4(特享版)', 'RAV4(特享版)', 'RAV4(特享版)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620014', 'RAV4(进口)', 'RAV4(进口)', 'RAV4(进口)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620015', 'RAV4(进口)(EV)', 'RAV4(进口)(EV)', 'RAV4(进口)(EV)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620016', 'RAV4(进口)(V6)', 'RAV4(进口)(V6)', 'RAV4(进口)(V6)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620017', 'RAV4(进口)(五门版)', 'RAV4(进口)(五门版)', 'RAV4(进口)(五门版)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620018', 'RAV4(进口)(标准款)', 'RAV4(进口)(标准款)', 'RAV4(进口)(标准款)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620019', 'RAV4(进口)(豪华型)', 'RAV4(进口)(豪华型)', 'RAV4(进口)(豪华型)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620020', 'SIENNA', 'SIENNA', 'SIENNA', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620021', 'SUV', 'SUV', 'SUV', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620022', 'Sienna', 'Sienna', 'Sienna', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620023', 'TOCOMA系列', 'TOCOMA系列', 'TOCOMA系列', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620024', 'WISH', 'WISH', 'WISH', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620025', 'YARIS(海外)(HYBRID)', 'YARIS(海外)(HYBRID)', 'YARIS(海外)(HYBRID)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620026', 'YARiS(海外)', 'YARiS(海外)', 'YARiS(海外)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620027', 'YARiS(海外)(HSD)', 'YARiS(海外)(HSD)', 'YARiS(海外)(HSD)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620028', 'YARiS(海外)(HYBRID)', 'YARiS(海外)(HYBRID)', 'YARiS(海外)(HYBRID)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620029', 'YARiS(海外)(HYBRIDR)', 'YARiS(海外)(HYBRIDR)', 'YARiS(海外)(HYBRIDR)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620030', 'YARiS(海外)(改款)', 'YARiS(海外)(改款)', 'YARiS(海外)(改款)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620031', 'YARiSL致炫', 'YARiSL致炫', 'YARiSL致炫', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620032', 'YARiSL致炫(改款)', 'YARiSL致炫(改款)', 'YARiSL致炫(改款)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620033', '丰田86', '丰田86', '丰田86', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620034', '佳美', '佳美', '佳美', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620035', '兰德酷路泽', '兰德酷路泽', '兰德酷路泽', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620036', '兰德酷路泽(GX R)', '兰德酷路泽(GX R)', '兰德酷路泽(GX R)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620037', '兰德酷路泽(改装中网)', '兰德酷路泽(改装中网)', '兰德酷路泽(改装中网)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620038', '凯美瑞', '凯美瑞', '凯美瑞', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620039', '凯美瑞(尊瑞)', '凯美瑞(尊瑞)', '凯美瑞(尊瑞)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620040', '凯美瑞(骏瑞)', '凯美瑞(骏瑞)', '凯美瑞(骏瑞)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620041', '卡罗拉', '卡罗拉', '卡罗拉', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620042', '卡罗拉(加装LED行车灯)', '卡罗拉(加装LED行车灯)', '卡罗拉(加装LED行车灯)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620043', '卡罗拉(双擎)', '卡罗拉(双擎)', '卡罗拉(双擎)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620044', '厢卡', '厢卡', '厢卡', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620045', '坦途', '坦途', '坦途', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620046', '坦途(双门版)', '坦途(双门版)', '坦途(双门版)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620047', '坦途(改装中网)', '坦途(改装中网)', '坦途(改装中网)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620048', '坦途(白金版)', '坦途(白金版)', '坦途(白金版)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620049', '埃尔法', '埃尔法', '埃尔法', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620050', '威飒', '威飒', '威飒', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620051', '威驰', '威驰', '威驰', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620052', '威驰FS', '威驰FS', '威驰FS', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620053', '巡洋舰霸道', '巡洋舰霸道', '巡洋舰霸道', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620054', '普拉多', '普拉多', '普拉多', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620055', '普拉多(3400中高配)', '普拉多(3400中高配)', '普拉多(3400中高配)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620056', '普拉多(改装中网)', '普拉多(改装中网)', '普拉多(改装中网)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620057', '普拉多(进口)(中东版改装中网)', '普拉多(进口)(中东版改装中网)', '普拉多(进口)(中东版改装中网)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620058', '普瑞维亚', '普瑞维亚', '普瑞维亚', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620059', '普锐斯', '普锐斯', '普锐斯', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620060', '普锐斯(海外)', '普锐斯(海外)', '普锐斯(海外)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620061', '普锐斯(海外)(C)', '普锐斯(海外)(C)', '普锐斯(海外)(C)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620062', '普锐斯(海外)(Plugin Hybrid)', '普锐斯(海外)(Plugin Hybrid)', '普锐斯(海外)(Plugin Hybrid)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620063', '普锐斯(海外)(Prius+)', '普锐斯(海外)(Prius+)', '普锐斯(海外)(Prius+)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620064', '普锐斯(海外)(V)', '普锐斯(海外)(V)', '普锐斯(海外)(V)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620065', '杰路驰', '杰路驰', '杰路驰', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620066', '柯斯达', '柯斯达', '柯斯达', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620067', '柯斯达(豪华版)', '柯斯达(豪华版)', '柯斯达(豪华版)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620068', '汉兰达', '汉兰达', '汉兰达', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620069', '汉兰达(改装)', '汉兰达(改装)', '汉兰达(改装)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620070', '汉兰达(改装中网)', '汉兰达(改装中网)', '汉兰达(改装中网)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620071', '海狮', '海狮', '海狮', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620072', '特锐', '特锐', '特锐', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620073', '特锐(改装)', '特锐(改装)', '特锐(改装)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620074', '皇冠', '皇冠', '皇冠', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620075', '皇冠(改装中网)', '皇冠(改装中网)', '皇冠(改装中网)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620076', '红杉', '红杉', '红杉', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620077', '花冠', '花冠', '花冠', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620078', '逸致', '逸致', '逸致', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620079', '锐志', '锐志', '锐志', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620080', '锐志(改装)', '锐志(改装)', '锐志(改装)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620081', '锐志(海外)', '锐志(海外)', '锐志(海外)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620082', '锐志(海外)(1)', '锐志(海外)(1)', '锐志(海外)(1)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620083', '锐志(风尚版)', '锐志(风尚版)', '锐志(风尚版)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620084', '锐志(风度版)', '锐志(风度版)', '锐志(风度版)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620085', '雅力士', '雅力士', '雅力士', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620086', '雅力士致炫', '雅力士致炫', '雅力士致炫', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620087', '雷凌', '雷凌', '雷凌', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620088', '雷凌(双擎)', '雷凌(双擎)', '雷凌(双擎)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00620089', '雷凌(新锐版)', '雷凌(新锐版)', '雷凌(新锐版)', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00629999', '未知', '未知', '未知', '0062');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00630000', 'A01', 'A01', 'A01', '0063');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00630001', 'B01', 'B01', 'B01', '0063');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00630002', 'E9', 'E9', 'E9', '0063');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00630003', 'V5', 'V5', 'V5', '0063');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00630004', '吉瑞', '吉瑞', '吉瑞', '0063');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00630005', '福瑞', '福瑞', '福瑞', '0063');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00630006', '金瑞', '金瑞', '金瑞', '0063');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00639999', '未知', '未知', '未知', '0063');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00640000', '1E', '1E', '1E', '0064');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00640001', '60H', '60H', '60H', '0064');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00640002', 'NEXT', 'NEXT', 'NEXT', '0064');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00649999', '未知', '未知', '未知', '0064');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00650000', '战剑', '战剑', '战剑', '0065');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00650001', '战车', '战车', '战车', '0065');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00659999', '未知', '未知', '未知', '0065');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00660000', 'A4', 'A4', 'A4', '0066');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00660001', 'A6', 'A6', 'A6', '0066');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00660002', '艾菲', '艾菲', '艾菲', '0066');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00669999', '未知', '未知', '未知', '0066');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00670000', '600P', '600P', '600P', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00670001', 'CYZ', 'CYZ', 'CYZ', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00670002', 'DMAX', 'DMAX', 'DMAX', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00670003', 'ELF(中货)', 'ELF(中货)', 'ELF(中货)', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00670004', 'ELF(小货)', 'ELF(小货)', 'ELF(小货)', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00670005', 'F系列重卡', 'F系列重卡', 'F系列重卡', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00670006', 'MUX游牧侠', 'MUX游牧侠', 'MUX游牧侠', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00670007', 'N系列轻卡', 'N系列轻卡', 'N系列轻卡', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00670008', 'VC46重卡', 'VC46重卡', 'VC46重卡', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00670009', '五十铃皮卡', '五十铃皮卡', '五十铃皮卡', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00670010', '五十铃皮卡(2.5L)', '五十铃皮卡(2.5L)', '五十铃皮卡(2.5L)', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00670011', '五十铃皮卡(3.0T)', '五十铃皮卡(3.0T)', '五十铃皮卡(3.0T)', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00670012', '瑞迈', '瑞迈', '瑞迈', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00670013', '瑞迈(改装中网)', '瑞迈(改装中网)', '瑞迈(改装中网)', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00670014', '皮卡', '皮卡', '皮卡', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00670015', '皮卡01', '皮卡01', '皮卡01', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00670016', '竞技者', '竞技者', '竞技者', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00679999', '未知', '未知', '未知', '0067');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00680000', '低速货车(WL4020PD7)', '低速货车(WL4020PD7)', '低速货车(WL4020PD7)', '0068');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00680001', '低速货车02', '低速货车02', '低速货车02', '0068');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00680002', '低速货车03', '低速货车03', '低速货车03', '0068');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00680003', '低速货车04', '低速货车04', '低速货车04', '0068');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00680004', '低速货车05', '低速货车05', '低速货车05', '0068');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00680005', '奥威1500', '奥威1500', '奥威1500', '0068');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00680006', '奥驰D系', '奥驰D系', '奥驰D系', '0068');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00689999', '未知', '未知', '未知', '0068');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690000', 'GL6505XQ', 'GL6505XQ', 'GL6505XQ', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690001', 'PN2009(微卡)', 'PN2009(微卡)', 'PN2009(微卡)', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690002', 'PN2009(微面)', 'PN2009(微面)', 'PN2009(微面)', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690003', '五菱之光', '五菱之光', '五菱之光', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690004', '五菱之光(加装前杠)', '五菱之光(加装前杠)', '五菱之光(加装前杠)', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690005', '五菱之光(基本型)', '五菱之光(基本型)', '五菱之光(基本型)', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690006', '五菱之光(实用型)', '五菱之光(实用型)', '五菱之光(实用型)', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690007', '五菱之光(标准型)', '五菱之光(标准型)', '五菱之光(标准型)', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690008', '五菱之光V', '五菱之光V', '五菱之光V', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690009', '五菱之光小卡', '五菱之光小卡', '五菱之光小卡', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690010', '五菱扬光', '五菱扬光', '五菱扬光', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690011', '五菱扬光(加装前杠)', '五菱扬光(加装前杠)', '五菱扬光(加装前杠)', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690012', '兴旺', '兴旺', '兴旺', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690013', '宏光', '宏光', '宏光', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690014', '宏光(加装前杠)', '宏光(加装前杠)', '宏光(加装前杠)', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690015', '宏光(基本型)', '宏光(基本型)', '宏光(基本型)', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690016', '宏光(改装中网)', '宏光(改装中网)', '宏光(改装中网)', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690017', '宏光S', '宏光S', '宏光S', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690018', '宏光S(基本型)', '宏光S(基本型)', '宏光S(基本型)', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690019', '宏光S(改装)', '宏光S(改装)', '宏光S(改装)', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690020', '宏光S1', '宏光S1', '宏光S1', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690021', '宏光V', '宏光V', '宏光V', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690022', '小卡', '小卡', '小卡', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690023', '小旋风', '小旋风', '小旋风', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690024', '征程', '征程', '征程', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690025', '荣光', '荣光', '荣光', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690026', '荣光(舒适型)', '荣光(舒适型)', '荣光(舒适型)', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690027', '荣光小卡', '荣光小卡', '荣光小卡', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690028', '荣光小卡(加装前杠)', '荣光小卡(加装前杠)', '荣光小卡(加装前杠)', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00690029', '鸿途', '鸿途', '鸿途', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00699999', '未知', '未知', '未知', '0069');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00700000', 'JS6851GHJ', 'JS6851GHJ', 'JS6851GHJ', '0070');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00700001', 'JS6971H1', 'JS6971H1', 'JS6971H1', '0070');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00700002', 'YBL6125H1QCJ1', 'YBL6125H1QCJ1', 'YBL6125H1QCJ1', '0070');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00700003', 'YBL6990HE3', 'YBL6990HE3', 'YBL6990HE3', '0070');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00709999', '未知', '未知', '未知', '0070');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00710000', 'BK6117', 'BK6117', 'BK6117', '0071');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00710001', 'BK6125DK', 'BK6125DK', 'BK6125DK', '0071');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00719999', '未知', '未知', '未知', '0071');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00720000', 'TJR6110DKA1', 'TJR6110DKA1', 'TJR6110DKA1', '0072');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00720001', '大巴', '大巴', '大巴', '0072');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00729999', '未知', '未知', '未知', '0072');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730000', '2008', '2008', '2008', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730001', '2008(改丰田标)', '2008(改丰田标)', '2008(改丰田标)', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730002', '5008', '5008', '5008', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730003', 'E200', 'E200', 'E200', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730004', 'M300', 'M300', 'M300', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730005', 'SR7', 'SR7', 'SR7', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730006', 'SR7(改装中网)', 'SR7(改装中网)', 'SR7(改装中网)', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730007', 'SR9', 'SR9', 'SR9', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730008', 'T200', 'T200', 'T200', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730009', 'T600', 'T600', 'T600', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730010', 'T600(改路虎标)', 'T600(改路虎标)', 'T600(改路虎标)', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730011', 'T600(运动版)', 'T600(运动版)', 'T600(运动版)', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730012', 'V10', 'V10', 'V10', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730013', 'Z100', 'Z100', 'Z100', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730014', 'Z200', 'Z200', 'Z200', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730015', 'Z200HB', 'Z200HB', 'Z200HB', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730016', 'Z300', 'Z300', 'Z300', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730017', 'Z300(改装)', 'Z300(改装)', 'Z300(改装)', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730018', 'Z300(新视界版)', 'Z300(新视界版)', 'Z300(新视界版)', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730019', 'Z500', 'Z500', 'Z500', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730020', 'Z500(改装中网)', 'Z500(改装中网)', 'Z500(改装中网)', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730021', 'Z560', 'Z560', 'Z560', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730022', 'Z700', 'Z700', 'Z700', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730023', 'Z700(改装中网)', 'Z700(改装中网)', 'Z700(改装中网)', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730024', '大迈X5', '大迈X5', '大迈X5', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730025', '大迈X7', '大迈X7', '大迈X7', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730026', '江南TT', '江南TT', '江南TT', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00730027', '芝麻E30', '芝麻E30', '芝麻E30', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00739999', '未知', '未知', '未知', '0073');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00740000', 'CEVENNES', 'CEVENNES', 'CEVENNES', '0074');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00740001', 'Coastline', 'Coastline', 'Coastline', '0074');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00749999', '未知', '未知', '未知', '0074');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00750000', 'Daily(欧胜)', 'Daily(欧胜)', 'Daily(欧胜)', '0075');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00750001', 'EuroCargo中卡', 'EuroCargo中卡', 'EuroCargo中卡', '0075');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00750002', 'StralisHiWay重卡', 'StralisHiWay重卡', 'StralisHiWay重卡', '0075');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00750003', 'Trakker重卡', 'Trakker重卡', 'Trakker重卡', '0075');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00750004', '上骏X300', '上骏X300', '上骏X300', '0075');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00750005', '威尼斯(新款)', '威尼斯(新款)', '威尼斯(新款)', '0075');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00750006', '威尼斯A49', '威尼斯A49', '威尼斯A49', '0075');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00750007', '宝迪', '宝迪', '宝迪', '0075');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00750008', '得意', '得意', '得意', '0075');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00750009', '得意(小货)', '得意(小货)', '得意(小货)', '0075');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00750010', '超越C300', '超越C300', '超越C300', '0075');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00750011', '超越C500', '超越C500', '超越C500', '0075');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00750012', '都灵', '都灵', '都灵', '0075');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00759999', '未知', '未知', '未知', '0075');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00760000', 'LaJOYA', 'LaJOYA', 'LaJOYA', '0076');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00769999', '未知', '未知', '未知', '0076');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770000', '718', '718', '718', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770001', '911', '911', '911', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770002', '911(Carrera)', '911(Carrera)', '911(Carrera)', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770003', '911(Targa)', '911(Targa)', '911(Targa)', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770004', '918Spyder', '918Spyder', '918Spyder', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770005', 'Boxster', 'Boxster', 'Boxster', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770006', 'Cayenne', 'Cayenne', 'Cayenne', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770007', 'Cayenne(TURBO)', 'Cayenne(TURBO)', 'Cayenne(TURBO)', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770008', 'Cayenne(Turbo)', 'Cayenne(Turbo)', 'Cayenne(Turbo)', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770009', 'Cayenne(柴油版)', 'Cayenne(柴油版)', 'Cayenne(柴油版)', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770010', 'Cayman', 'Cayman', 'Cayman', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770011', 'Cayman(改装前杠)', 'Cayman(改装前杠)', 'Cayman(改装前杠)', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770012', 'Macan', 'Macan', 'Macan', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770013', 'Macan(GTS)', 'Macan(GTS)', 'Macan(GTS)', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770014', 'Macan(Turbo)', 'Macan(Turbo)', 'Macan(Turbo)', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770015', 'Panamera', 'Panamera', 'Panamera', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770016', 'Panamera(Turbo)', 'Panamera(Turbo)', 'Panamera(Turbo)', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00770017', 'Panamera(改装)', 'Panamera(改装)', 'Panamera(改装)', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00779999', '未知', '未知', '未知', '0077');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00780000', '嘉路', '嘉路', '嘉路', '0078');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00780001', '女王', '女王', '女王', '0078');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00789999', '未知', '未知', '未知', '0078');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00790000', '200', '200', '200', '0079');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00790001', '300C(含进口款)', '300C(含进口款)', '300C(含进口款)', '0079');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00790002', '300C(改装)', '300C(改装)', '300C(改装)', '0079');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00790003', '300C(进口)', '300C(进口)', '300C(进口)', '0079');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00790004', 'Aspen', 'Aspen', 'Aspen', '0079');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00790005', 'PT漫步者', 'PT漫步者', 'PT漫步者', '0079');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00790006', 'TOWNANDCOUNTRY', 'TOWNANDCOUNTRY', 'TOWNANDCOUNTRY', '0079');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00790007', '大捷龙', '大捷龙', '大捷龙', '0079');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00790008', '大捷龙(进口)', '大捷龙(进口)', '大捷龙(进口)', '0079');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00790009', '铂锐', '铂锐', '铂锐', '0079');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00799999', '未知', '未知', '未知', '0079');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00800000', 'Aventador(Superveloce)', 'Aventador(Superveloce)', 'Aventador(Superveloce)', '0080');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00800001', 'Gallardo', 'Gallardo', 'Gallardo', '0080');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00800002', 'Huracan(RWDCoupe)', 'Huracan(RWDCoupe)', 'Huracan(RWDCoupe)', '0080');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00800003', 'Huracan(Spyder)', 'Huracan(Spyder)', 'Huracan(Spyder)', '0080');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00809999', '未知', '未知', '未知', '0080');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00810000', 'CLY6660DEA', 'CLY6660DEA', 'CLY6660DEA', '0081');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00819999', '未知', '未知', '未知', '0081');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00820000', '战盾', '战盾', '战盾', '0082');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00829999', '未知', '未知', '未知', '0082');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00830000', 'C3', 'C3', 'C3', '0083');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00830001', 'C3R', 'C3R', 'C3R', '0083');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00830002', 'V3', 'V3', 'V3', '0083');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00830003', 'X3', 'X3', 'X3', '0083');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00830004', 'X3(发烧友版)', 'X3(发烧友版)', 'X3(发烧友版)', '0083');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00830005', 'X5', 'X5', 'X5', '0083');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00830006', 'iCX', 'iCX', 'iCX', '0083');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00839999', '未知', '未知', '未知', '0083');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840000', 'ATS(进口)', 'ATS(进口)', 'ATS(进口)', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840001', 'ATSL', 'ATSL', 'ATSL', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840002', 'CT6', 'CT6', 'CT6', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840003', 'CT6(铂金版)', 'CT6(铂金版)', 'CT6(铂金版)', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840004', 'CTS', 'CTS', 'CTS', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840005', 'CTS(进口)(CTS V)', 'CTS(进口)(CTS V)', 'CTS(进口)(CTS V)', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840006', 'CTS(进口)(改装中网)', 'CTS(进口)(改装中网)', 'CTS(进口)(改装中网)', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840007', 'ELR', 'ELR', 'ELR', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840008', 'SLS赛威', 'SLS赛威', 'SLS赛威', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840009', 'SRX', 'SRX', 'SRX', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840010', 'XT5', 'XT5', 'XT5', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840011', 'XTS(技术型)', 'XTS(技术型)', 'XTS(技术型)', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840012', 'XTS(科技型,精英型,豪华型)', 'XTS(科技型,精英型,豪华型)', 'XTS(科技型,精英型,豪华型)', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840013', 'XTS(精英型,豪华型)', 'XTS(精英型,豪华型)', 'XTS(精英型,豪华型)', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840014', 'XTS(舒适型)', 'XTS(舒适型)', 'XTS(舒适型)', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840015', 'XTS(铂金版)', 'XTS(铂金版)', 'XTS(铂金版)', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840016', '凯雷德', '凯雷德', '凯雷德', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00840017', '凯雷德ESCALADE', '凯雷德ESCALADE', '凯雷德ESCALADE', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00849999', '未知', '未知', '未知', '0084');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00850000', 'W01', 'W01', 'W01', '0085');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00850001', 'W01EV', 'W01EV', 'W01EV', '0085');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00850002', '凯捷', '凯捷', '凯捷', '0085');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00850003', '奥峰SD2810D', '奥峰SD2810D', '奥峰SD2810D', '0085');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00850004', '奥峰SD2810P3', '奥峰SD2810P3', '奥峰SD2810P3', '0085');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00850005', '奥峰SD2815W', '奥峰SD2815W', '奥峰SD2815W', '0085');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00850006', '好运来', '好运来', '好运来', '0085');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00850007', '好运来330', '好运来330', '好运来330', '0085');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00850008', '福来卡', '福来卡', '福来卡', '0085');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00850009', '聚宝', '聚宝', '聚宝', '0085');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00850010', '聚宝JBC4015', '聚宝JBC4015', '聚宝JBC4015', '0085');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00850011', '轻卡', '轻卡', '轻卡', '0085');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00850012', '金运卡', '金运卡', '金运卡', '0085');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00850013', '锐菱', '锐菱', '锐菱', '0085');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00859999', '未知', '未知', '未知', '0085');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860000', 'GL8', 'GL8', 'GL8', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860001', 'GL8(25S)', 'GL8(25S)', 'GL8(25S)', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860002', 'GL8(Avenir)', 'GL8(Avenir)', 'GL8(Avenir)', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860003', 'GL8(ES)', 'GL8(ES)', 'GL8(ES)', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860004', 'GL8(标准型)', 'GL8(标准型)', 'GL8(标准型)', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860005', 'GL8(豪华型)', 'GL8(豪华型)', 'GL8(豪华型)', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860006', 'GL8(陆尊)', 'GL8(陆尊)', 'GL8(陆尊)', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860007', 'VELITE5', 'VELITE5', 'VELITE5', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860008', '凯越', '凯越', '凯越', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860009', '凯越(HRV)', '凯越(HRV)', '凯越(HRV)', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860010', '君威', '君威', '君威', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860011', '君威(GS)', '君威(GS)', '君威(GS)', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860012', '君威(海外)', '君威(海外)', '君威(海外)', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860013', '君越', '君越', '君越', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860014', '威朗', '威朗', '威朗', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860015', '威朗(15S)', '威朗(15S)', '威朗(15S)', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860016', '威朗(GS)', '威朗(GS)', '威朗(GS)', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860017', '昂科威', '昂科威', '昂科威', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860018', '昂科拉', '昂科拉', '昂科拉', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860019', '昂科雷', '昂科雷', '昂科雷', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860020', '林荫大道', '林荫大道', '林荫大道', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860021', '英朗', '英朗', '英朗', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860022', '英朗GT', '英朗GT', '英朗GT', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860023', '英朗XT', '英朗XT', '英朗XT', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860024', '荣御', '荣御', '荣御', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00860025', '赛欧', '赛欧', '赛欧', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00869999', '未知', '未知', '未知', '0086');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00870000', 'K50', 'K50', 'K50', '0087');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00879999', '未知', '未知', '未知', '0087');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880000', '320', '320', '320', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880001', '330', '330', '330', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880002', '520', '520', '520', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880003', '530', '530', '530', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880004', '620', '620', '620', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880005', '630', '630', '630', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880006', '720', '720', '720', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880007', '820', '820', '820', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880008', 'X50', 'X50', 'X50', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880009', 'X60', 'X60', 'X60', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880010', 'X80', 'X80', 'X80', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880011', '丰顺', '丰顺', '丰顺', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880012', '乐途', '乐途', '乐途', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880013', '兴顺', '兴顺', '兴顺', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880014', '斯卡特吉卡', '斯卡特吉卡', '斯卡特吉卡', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880015', '时骏凯沃达中卡', '时骏凯沃达中卡', '时骏凯沃达中卡', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880016', '时骏斯卡特(LFJ1040N1)', '时骏斯卡特(LFJ1040N1)', '时骏斯卡特(LFJ1040N1)', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880017', '时骏格奥雷重卡', '时骏格奥雷重卡', '时骏格奥雷重卡', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880018', '时骏王子', '时骏王子', '时骏王子', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880019', '欧式战马A6', '欧式战马A6', '欧式战马A6', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880020', '欧式战龙V9重卡', '欧式战龙V9重卡', '欧式战龙V9重卡', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880021', '福顺', '福顺', '福顺', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880022', '轩朗', '轩朗', '轩朗', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00880023', '迈威', '迈威', '迈威', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00889999', '未知', '未知', '未知', '0088');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00890000', 'XC5820PD', 'XC5820PD', 'XC5820PD', '0089');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00899999', '未知', '未知', '未知', '0089');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00900000', '古斯特', '古斯特', '古斯特', '0090');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00900001', '幻影', '幻影', '幻影', '0090');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00900002', '曜影', '曜影', '曜影', '0090');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00900003', '魅影', '魅影', '魅影', '0090');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00909999', '未知', '未知', '未知', '0090');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00910000', '1041轻卡', '1041轻卡', '1041轻卡', '0091');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00910001', 'BJ20', 'BJ20', 'BJ20', '0091');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00910002', 'BJ40(改装)', 'BJ40(改装)', 'BJ40(改装)', '0091');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00910003', 'NPR', 'NPR', 'NPR', '0091');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00910004', '自卸农用车BJ1405D', '自卸农用车BJ1405D', '自卸农用车BJ1405D', '0091');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00910005', '金星', '金星', '金星', '0091');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00919999', '未知', '未知', '未知', '0091');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00920000', 'E系列', 'E系列', 'E系列', '0092');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00920001', 'E系列(改奔驰标)', 'E系列(改奔驰标)', 'E系列(改奔驰标)', '0092');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00920002', '北京40', '北京40', '北京40', '0092');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00920003', '北京80', '北京80', '北京80', '0092');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00929999', '未知', '未知', '未知', '0092');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00930000', 'NG80(ND5310GFLZ01)', 'NG80(ND5310GFLZ01)', 'NG80(ND5310GFLZ01)', '0093');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00930001', 'NG80(大货)', 'NG80(大货)', 'NG80(大货)', '0093');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00930002', 'NG80(特大货)', 'NG80(特大货)', 'NG80(特大货)', '0093');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00930003', 'NG80牵引车(ND12502B41J)', 'NG80牵引车(ND12502B41J)', 'NG80牵引车(ND12502B41J)', '0093');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00930004', 'NG80牵引车(ND4243L25J)', 'NG80牵引车(ND4243L25J)', 'NG80牵引车(ND4243L25J)', '0093');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00930005', 'NG80高顶(特大货)', 'NG80高顶(特大货)', 'NG80高顶(特大货)', '0093');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00930006', 'V3', 'V3', 'V3', '0093');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00930007', 'V3M', 'V3M', 'V3M', '0093');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00939999', '未知', '未知', '未知', '0093');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00940000', 'BFC6107H', 'BFC6107H', 'BFC6107H', '0094');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00940001', 'BFC6127H', 'BFC6127H', 'BFC6127H', '0094');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00949999', '未知', '未知', '未知', '0094');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00950000', '旗铃厢式轻卡', '旗铃厢式轻卡', '旗铃厢式轻卡', '0095');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00950001', '旗铃栏板轻卡', '旗铃栏板轻卡', '旗铃栏板轻卡', '0095');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00950002', '旗龙', '旗龙', '旗龙', '0095');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00959999', '未知', '未知', '未知', '0095');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00960000', 'BJ212', 'BJ212', 'BJ212', '0096');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00960001', 'BW007', 'BW007', 'BW007', '0096');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00960002', '勇士', '勇士', '勇士', '0096');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00960003', '战旗', '战旗', '战旗', '0096');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00960004', '越铃', '越铃', '越铃', '0096');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00960005', '陆铃', '陆铃', '陆铃', '0096');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00960006', '陆铃(豪华型)', '陆铃(豪华型)', '陆铃(豪华型)', '0096');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00960007', '骑士S12', '骑士S12', '骑士S12', '0096');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00969999', '未知', '未知', '未知', '0096');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00970000', '205', '205', '205', '0097');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00970001', '205(加长旺业型)', '205(加长旺业型)', '205(加长旺业型)', '0097');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00970002', '205(加长版)', '205(加长版)', '205(加长版)', '0097');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00970003', '306', '306', '306', '0097');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00970004', '307', '307', '307', '0097');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00970005', '608EV', '608EV', '608EV', '0097');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00970006', 'M20', 'M20', 'M20', '0097');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00970007', 'M30', 'M30', 'M30', '0097');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00970008', 'M35', 'M35', 'M35', '0097');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00970009', 'M50F', 'M50F', 'M50F', '0097');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00970010', 'M50F(1.3T)', 'M50F(1.3T)', 'M50F(1.3T)', '0097');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00970011', 'S50', 'S50', 'S50', '0097');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00970012', 'S50(乐动版)', 'S50(乐动版)', 'S50(乐动版)', '0097');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00970013', '北汽007', '北汽007', '北汽007', '0097');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00979999', '未知', '未知', '未知', '0097');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00980000', 'H2', 'H2', 'H2', '0098');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00980001', 'H2V', 'H2V', 'H2V', '0098');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00980002', 'H3', 'H3', 'H3', '0098');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00980003', 'H3F', 'H3F', 'H3F', '0098');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00980004', 'H6', 'H6', 'H6', '0098');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00980005', 'S2', 'S2', 'S2', '0098');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00980006', 'S3', 'S3', 'S3', '0098');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00980007', 'S3L', 'S3L', 'S3L', '0098');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00980008', 'S5', 'S5', 'S5', '0098');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00980009', 'S6', 'S6', 'S6', '0098');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00989999', '未知', '未知', '未知', '0098');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00990000', 'C90EV', 'C90EV', 'C90EV', '0099');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00990001', 'EC', 'EC', 'EC', '0099');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00990002', 'EH系列', 'EH系列', 'EH系列', '0099');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00990003', 'ES210', 'ES210', 'ES210', '0099');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00990004', 'EU系列', 'EU系列', 'EU系列', '0099');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00990005', 'EV系列', 'EV系列', 'EV系列', '0099');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00990006', 'EX系列', 'EX系列', 'EX系列', '0099');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('00999999', '未知', '未知', '未知', '0099');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01000000', 'CC', 'CC', 'CC', '0100');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01000001', 'D20', 'D20', 'D20', '0100');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01000002', 'D50', 'D50', 'D50', '0100');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01000003', 'D50(标准版)', 'D50(标准版)', 'D50(标准版)', '0100');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01000004', 'D50(舒适版,精英版,豪华版)', 'D50(舒适版,精英版,豪华版)', 'D50(舒适版,精英版,豪华版)', '0100');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01000005', 'D60', 'D60', 'D60', '0100');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01000006', 'D70', 'D70', 'D70', '0100');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01000007', 'D80', 'D80', 'D80', '0100');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01000008', 'X25', 'X25', 'X25', '0100');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01000009', 'X35', 'X35', 'X35', '0100');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01000010', 'X55', 'X55', 'X55', '0100');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01000011', 'X65', 'X65', 'X65', '0100');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01000012', 'X65(手动舒适版)', 'X65(手动舒适版)', 'X65(手动舒适版)', '0100');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01009999', '未知', '未知', '未知', '0100');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01010000', '皮卡', '皮卡', '皮卡', '0101');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01019999', '未知', '未知', '未知', '0101');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01020000', '天津大发TJ110', '天津大发TJ110', '天津大发TJ110', '0102');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01029999', '未知', '未知', '未知', '0102');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01030000', 'AC6608KJ2', 'AC6608KJ2', 'AC6608KJ2', '0103');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01039999', '未知', '未知', '未知', '0103');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01040000', '海域', '海域', '海域', '0104');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01040001', '海尚', '海尚', '海尚', '0104');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01040002', '海峰', '海峰', '海峰', '0104');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01040003', '海悦', '海悦', '海悦', '0104');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01040004', '海迅', '海迅', '海迅', '0104');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01040005', '海迅(AA)', '海迅(AA)', '海迅(AA)', '0104');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01049999', '未知', '未知', '未知', '0104');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01050000', 'B11', 'B11', 'B11', '0105');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01050001', '圣达菲', '圣达菲', '圣达菲', '0105');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01050002', '圣达菲(高配版)', '圣达菲(高配版)', '圣达菲(高配版)', '0105');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01050003', '圣达菲经典', '圣达菲经典', '圣达菲经典', '0105');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01050004', '圣达菲经典(C9型)', '圣达菲经典(C9型)', '圣达菲经典(C9型)', '0105');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01050005', '宝利格', '宝利格', '宝利格', '0105');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01050006', '宝利格(改装中网)', '宝利格(改装中网)', '宝利格(改装中网)', '0105');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01050007', '特拉卡', '特拉卡', '特拉卡', '0105');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01050008', '特拉卡(改现代标)', '特拉卡(改现代标)', '特拉卡(改现代标)', '0105');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01050009', '路盛E70', '路盛E70', '路盛E70', '0105');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01050010', '路盛E80', '路盛E80', '路盛E80', '0105');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01059999', '未知', '未知', '未知', '0105');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01060000', 'EV160B', 'EV160B', 'EV160B', '0106');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01060001', 'XEV260', 'XEV260', 'XEV260', '0106');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01060002', 'iEV230', 'iEV230', 'iEV230', '0106');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01069999', '未知', '未知', '未知', '0106');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01070000', 'LGHGLJUM2CH032767', 'LGHGLJUM2CH032767', 'LGHGLJUM2CH032767', '0107');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01079999', '未知', '未知', '未知', '0107');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01080000', '380', '380', '380', '0108');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01080001', '华菱之星自卸车', '华菱之星自卸车', '华菱之星自卸车', '0108');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01080002', '华菱之星载货车', '华菱之星载货车', '华菱之星载货车', '0108');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01080003', '华菱重卡', '华菱重卡', '华菱重卡', '0108');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01080004', '华菱重卡牵引车', '华菱重卡牵引车', '华菱重卡牵引车', '0108');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01080005', '星凯马', '星凯马', '星凯马', '0108');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01080006', '汉马', '汉马', '汉马', '0108');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01080007', '汉马重卡', '汉马重卡', '汉马重卡', '0108');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01089999', '未知', '未知', '未知', '0108');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01090000', '华颂7', '华颂7', '华颂7', '0109');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01099999', '未知', '未知', '未知', '0109');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01100000', 'HORKI1', 'HORKI1', 'HORKI1', '0110');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01109999', '未知', '未知', '未知', '0110');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01110000', '畅达小福星', '畅达小福星', '畅达小福星', '0111');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01119999', '未知', '未知', '未知', '0111');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01120000', 'CNJ1040ES33M', 'CNJ1040ES33M', 'CNJ1040ES33M', '0112');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01120001', 'CNJ3040ZGP38B', 'CNJ3040ZGP38B', 'CNJ3040ZGP38B', '0112');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01120002', 'CNJ5040CCQZP33B2', 'CNJ5040CCQZP33B2', 'CNJ5040CCQZP33B2', '0112');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01120003', 'NJP3060ZQP39B1', 'NJP3060ZQP39B1', 'NJP3060ZQP39B1', '0112');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01120004', '新鸿运', '新鸿运', '新鸿运', '0112');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01120005', '瑞宝', '瑞宝', '瑞宝', '0112');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01120006', '瑞康', '瑞康', '瑞康', '0112');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01120007', '瑞逸栏板微卡', '瑞逸栏板微卡', '瑞逸栏板微卡', '0112');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01129999', '未知', '未知', '未知', '0112');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01130000', 'C10', 'C10', 'C10', '0113');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01139999', '未知', '未知', '未知', '0113');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01140000', 'K1', 'K1', 'K1', '0114');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01140001', 'K150', 'K150', 'K150', '0114');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01140002', 'W1', 'W1', 'W1', '0114');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01149999', '未知', '未知', '未知', '0114');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01150000', 'C25', 'C25', 'C25', '0115');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01150001', 'GL级', 'GL级', 'GL级', '0115');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01150002', 'S级', 'S级', 'S级', '0115');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01159999', '未知', '未知', '未知', '0115');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01160000', '低速载货车', '低速载货车', '低速载货车', '0116');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01169999', '未知', '未知', '未知', '0116');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01170000', 'SCEO', 'SCEO', 'SCEO', '0117');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01170001', 'SCEO(改装)', 'SCEO(改装)', 'SCEO(改装)', '0117');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01170002', '小贵族', '小贵族', '小贵族', '0117');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01170003', '来宝SRV', '来宝SRV', '来宝SRV', '0117');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01179999', '未知', '未知', '未知', '0117');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01180000', 'C200', 'C200', 'C200', '0118');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01180001', 'XIVAdventure', 'XIVAdventure', 'XIVAdventure', '0118');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01180002', 'XIVAir', 'XIVAir', 'XIVAir', '0118');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01180003', '主席', '主席', '主席', '0118');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01180004', '享御', '享御', '享御', '0118');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01180005', '柯兰多', '柯兰多', '柯兰多', '0118');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01180006', '爱腾', '爱腾', '爱腾', '0118');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01180007', '蒂维拉', '蒂维拉', '蒂维拉', '0118');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01180008', '路帝', '路帝', '路帝', '0118');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01180009', '雷斯特', '雷斯特', '雷斯特', '0118');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01180010', '雷斯特W', '雷斯特W', '雷斯特W', '0118');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01189999', '未知', '未知', '未知', '0118');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190000', 'EC8', 'EC8', 'EC8', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190001', 'EC8(改装中网)', 'EC8(改装中网)', 'EC8(改装中网)', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190002', 'GC6', 'GC6', 'GC6', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190003', 'GC7', 'GC7', 'GC7', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190004', 'GX2', 'GX2', 'GX2', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190005', 'GX7', 'GX7', 'GX7', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190006', 'GX7(改装)', 'GX7(改装)', 'GX7(改装)', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190007', 'SC3', 'SC3', 'SC3', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190008', 'SC5RV', 'SC5RV', 'SC5RV', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190009', '博瑞', '博瑞', '博瑞', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190010', '博越', '博越', '博越', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190011', '帝豪', '帝豪', '帝豪', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190012', '帝豪(三厢版)', '帝豪(三厢版)', '帝豪(三厢版)', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190013', '帝豪(两厢RS)', '帝豪(两厢RS)', '帝豪(两厢RS)', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190014', '帝豪EC8', '帝豪EC8', '帝豪EC8', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190015', '帝豪GL', '帝豪GL', '帝豪GL', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190016', '帝豪GS(优雅版)', '帝豪GS(优雅版)', '帝豪GS(优雅版)', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190017', '帝豪GS(运动版)', '帝豪GS(运动版)', '帝豪GS(运动版)', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190018', '帝豪新能源', '帝豪新能源', '帝豪新能源', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190019', '海景', '海景', '海景', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190020', '熊猫', '熊猫', '熊猫', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190021', '经典帝豪', '经典帝豪', '经典帝豪', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190022', '经典帝豪(三厢)', '经典帝豪(三厢)', '经典帝豪(三厢)', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190023', '经典帝豪(两厢)', '经典帝豪(两厢)', '经典帝豪(两厢)', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190024', '美人豹', '美人豹', '美人豹', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190025', '美日', '美日', '美日', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190026', '自由舰', '自由舰', '自由舰', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190027', '英伦C5', '英伦C5', '英伦C5', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190028', '英伦SC7海景(改装)', '英伦SC7海景(改装)', '英伦SC7海景(改装)', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190029', '英伦TX4', '英伦TX4', '英伦TX4', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190030', '豪情', '豪情', '豪情', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190031', '豪情(203A型)', '豪情(203A型)', '豪情(203A型)', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190032', '豪情SUV', '豪情SUV', '豪情SUV', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190033', '远景', '远景', '远景', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190034', '远景SUV', '远景SUV', '远景SUV', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190035', '远景X1', '远景X1', '远景X1', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190036', '金刚', '金刚', '金刚', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190037', '金刚(2代)', '金刚(2代)', '金刚(2代)', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01190038', '金刚财富', '金刚财富', '金刚财富', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01199999', '未知', '未知', '未知', '0119');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01200000', '1', '1', '1', '0120');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01209999', '未知', '未知', '未知', '0120');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01210000', 'D50', 'D50', 'D50', '0121');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01210001', 'D50(改尼桑标)', 'D50(改尼桑标)', 'D50(改尼桑标)', '0121');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01210002', 'R30', 'R30', 'R30', '0121');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01210003', 'R50X', 'R50X', 'R50X', '0121');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01210004', 'T70', 'T70', 'T70', '0121');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01210005', 'T70X', 'T70X', 'T70X', '0121');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01210006', 'T90', 'T90', 'T90', '0121');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01210007', 'VOW', 'VOW', 'VOW', '0121');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01210008', 'ViWa', 'ViWa', 'ViWa', '0121');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01210009', '晨风', '晨风', '晨风', '0121');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01219999', '未知', '未知', '未知', '0121');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220000', 'H1', 'H1', 'H1', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220001', 'H1(红标)', 'H1(红标)', 'H1(红标)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220002', 'H1(蓝标)', 'H1(蓝标)', 'H1(蓝标)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220003', 'H1(蓝标改款)', 'H1(蓝标改款)', 'H1(蓝标改款)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220004', 'H2', 'H2', 'H2', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220005', 'H2(红标)', 'H2(红标)', 'H2(红标)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220006', 'H2(蓝标精英型,蓝标豪华型)', 'H2(蓝标精英型,蓝标豪华型)', 'H2(蓝标精英型,蓝标豪华型)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220007', 'H2s(红标版)', 'H2s(红标版)', 'H2s(红标版)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220008', 'H2s(蓝标版)', 'H2s(蓝标版)', 'H2s(蓝标版)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220009', 'H3', 'H3', 'H3', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220010', 'H3(改装)', 'H3(改装)', 'H3(改装)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220011', 'H3(锐意版)', 'H3(锐意版)', 'H3(锐意版)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220012', 'H3(领先版)', 'H3(领先版)', 'H3(领先版)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220013', 'H3(领先版豪华型)', 'H3(领先版豪华型)', 'H3(领先版豪华型)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220014', 'H5', 'H5', 'H5', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220015', 'H5(智尊版)', 'H5(智尊版)', 'H5(智尊版)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220016', 'H5(智尊版加装前杠)', 'H5(智尊版加装前杠)', 'H5(智尊版加装前杠)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220017', 'H5(欧风版)', 'H5(欧风版)', 'H5(欧风版)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220018', 'H5(经典版)', 'H5(经典版)', 'H5(经典版)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220019', 'H6', 'H6', 'H6', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220020', 'H6(升级版)', 'H6(升级版)', 'H6(升级版)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220021', 'H6(红标运动版)', 'H6(红标运动版)', 'H6(红标运动版)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220022', 'H6(经典版)', 'H6(经典版)', 'H6(经典版)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220023', 'H6(蓝标)', 'H6(蓝标)', 'H6(蓝标)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220024', 'H6(运动版)', 'H6(运动版)', 'H6(运动版)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220025', 'H6Coupe', 'H6Coupe', 'H6Coupe', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220026', 'H6Coupe(红标)', 'H6Coupe(红标)', 'H6Coupe(红标)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220027', 'H7', 'H7', 'H7', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220028', 'H7(红标)', 'H7(红标)', 'H7(红标)', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220029', 'H8', 'H8', 'H8', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01220030', 'H9', 'H9', 'H9', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01229999', '未知', '未知', '未知', '0122');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230000', '中意', '中意', '中意', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230001', '中意(微卡)', '中意(微卡)', '中意(微卡)', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230002', '中意(微面)', '中意(微面)', '中意(微面)', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230003', '中意V5', '中意V5', '中意V5', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230004', '小霸王', '小霸王', '小霸王', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230005', '松花江', '松花江', '松花江', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230006', '松花江V', '松花江V', '松花江V', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230007', '民意', '民意', '民意', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230008', '民意(微卡)', '民意(微卡)', '民意(微卡)', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230009', '民意(更换前杠)', '民意(更换前杠)', '民意(更换前杠)', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230010', '赛豹III', '赛豹III', '赛豹III', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230011', '赛豹V', '赛豹V', '赛豹V', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230012', '赛豹V(1.8L)', '赛豹V(1.8L)', '赛豹V(1.8L)', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230013', '赛马', '赛马', '赛马', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230014', '路宝', '路宝', '路宝', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230015', '路尊大霸王', '路尊大霸王', '路尊大霸王', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230016', '骏意', '骏意', '骏意', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230017', '黑豹1027系列', '黑豹1027系列', '黑豹1027系列', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230018', '黑豹1030系列', '黑豹1030系列', '黑豹1030系列', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230019', '黑豹SM1020E', '黑豹SM1020E', '黑豹SM1020E', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01230020', '黑豹YTQ1035D20GV', '黑豹YTQ1035D20GV', '黑豹YTQ1035D20GV', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01239999', '未知', '未知', '未知', '0123');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01240000', '01', '01', '01', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01240001', 'K3', 'K3', 'K3', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01240002', '天使', '天使', '天使', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01240003', '小宝马', '小宝马', '小宝马', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01240004', '欧冠', '欧冠', '欧冠', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01240005', '欧冠(旗舰版)', '欧冠(旗舰版)', '欧冠(旗舰版)', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01240006', '欧嘉', '欧嘉', '欧嘉', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01240007', '欧玲', '欧玲', '欧玲', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01240008', '欧铃T6', '欧铃T6', '欧铃T6', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01240009', '欧铃T7', '欧铃T7', '欧铃T7', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01240010', '王子', '王子', '王子', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01240011', '福星', '福星', '福星', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01240012', '赛菱A6', '赛菱A6', '赛菱A6', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01240013', '赛菱微卡', '赛菱微卡', '赛菱微卡', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01240014', '超级轻卡王', '超级轻卡王', '超级轻卡王', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01240015', '阿凡达', '阿凡达', '阿凡达', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01240016', '骏骐旗舰', '骏骐旗舰', '骏骐旗舰', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01249999', '未知', '未知', '未知', '0124');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01250000', '夏特利栏板轻卡', '夏特利栏板轻卡', '夏特利栏板轻卡', '0125');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01259999', '未知', '未知', '未知', '0125');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01260000', 'Mighty', 'Mighty', 'Mighty', '0126');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01260001', '创虎Xcient', '创虎Xcient', '创虎Xcient', '0126');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01260002', '瑞宇', '瑞宇', '瑞宇', '0126');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01260003', '瑞康', '瑞康', '瑞康', '0126');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01260004', '瑞贝', '瑞贝', '瑞贝', '0126');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01260005', '瑞越', '瑞越', '瑞越', '0126');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01260006', '瑞逸', '瑞逸', '瑞逸', '0126');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01260007', '盛图', '盛图', '盛图', '0126');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01260008', '致道', '致道', '致道', '0126');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01260009', '致道800W', '致道800W', '致道800W', '0126');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01260010', '锐捷', '锐捷', '锐捷', '0126');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01269999', '未知', '未知', '未知', '0126');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270000', 'CC', 'CC', 'CC', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270001', 'CC(改装)', 'CC(改装)', 'CC(改装)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270002', 'CC(豪华型)', 'CC(豪华型)', 'CC(豪华型)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270003', 'CROSSPOLO', 'CROSSPOLO', 'CROSSPOLO', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270004', 'CrossGOLF', 'CrossGOLF', 'CrossGOLF', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270005', 'CrossPolo', 'CrossPolo', 'CrossPolo', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270006', 'Eos(进口)', 'Eos(进口)', 'Eos(进口)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270007', 'POLO', 'POLO', 'POLO', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270008', 'POLO(GTI)', 'POLO(GTI)', 'POLO(GTI)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270009', 'POLO(改装)', 'POLO(改装)', 'POLO(改装)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270010', 'POLO(致酷版)', 'POLO(致酷版)', 'POLO(致酷版)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270011', 'Polo', 'Polo', 'Polo', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270012', 'Polo(Sporty)', 'Polo(Sporty)', 'Polo(Sporty)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270013', 'Polo(劲取)', 'Polo(劲取)', 'Polo(劲取)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270014', 'Polo(劲情)', 'Polo(劲情)', 'Polo(劲情)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270015', 'TERAMONT', 'TERAMONT', 'TERAMONT', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270016', 'TIGUAN', 'TIGUAN', 'TIGUAN', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270017', 'UP', 'UP', 'UP', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270018', 'XL1', 'XL1', 'XL1', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270019', 'XLSPORT', 'XLSPORT', 'XLSPORT', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270020', '凌度(GTS)', '凌度(GTS)', '凌度(GTS)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270021', '凌度(豪华版)', '凌度(豪华版)', '凌度(豪华版)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270022', '凌渡', '凌渡', '凌渡', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270023', '凯路威', '凯路威', '凯路威', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270024', '夏朗', '夏朗', '夏朗', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270025', '宝来', '宝来', '宝来', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270026', '宝来(HS)', '宝来(HS)', '宝来(HS)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270027', '宝来(改款)', '宝来(改款)', '宝来(改款)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270028', '宝来(电动型)', '宝来(电动型)', '宝来(电动型)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270029', '宝来(经典)', '宝来(经典)', '宝来(经典)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270030', '宝来(豪华版)', '宝来(豪华版)', '宝来(豪华版)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270031', '尚酷', '尚酷', '尚酷', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270032', '帕萨特', '帕萨特', '帕萨特', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270033', '帕萨特(改款)', '帕萨特(改款)', '帕萨特(改款)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270034', '帕萨特领驭', '帕萨特领驭', '帕萨特领驭', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270035', '开迪', '开迪', '开迪', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270036', '开迪(功能车)', '开迪(功能车)', '开迪(功能车)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270037', '捷达', '捷达', '捷达', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270038', '捷达(CIF伙伴)', '捷达(CIF伙伴)', '捷达(CIF伙伴)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270039', '捷达(改款)', '捷达(改款)', '捷达(改款)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270040', '捷达(改装)', '捷达(改装)', '捷达(改装)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270041', '捷达(质惠版)', '捷达(质惠版)', '捷达(质惠版)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270042', '朗境', '朗境', '朗境', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270043', '朗行', '朗行', '朗行', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270044', '朗行(豪华版)', '朗行(豪华版)', '朗行(豪华版)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270045', '朗行(风尚型)', '朗行(风尚型)', '朗行(风尚型)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270046', '朗逸', '朗逸', '朗逸', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270047', '朗逸(GTI)', '朗逸(GTI)', '朗逸(GTI)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270048', '朗逸(改款)', '朗逸(改款)', '朗逸(改款)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270049', '朗逸(舒适版)', '朗逸(舒适版)', '朗逸(舒适版)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270050', '朗逸(豪华版)', '朗逸(豪华版)', '朗逸(豪华版)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270051', '桑塔纳', '桑塔纳', '桑塔纳', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270052', '桑塔纳(手动豪华版)', '桑塔纳(手动豪华版)', '桑塔纳(手动豪华版)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270053', '桑塔纳(改款)', '桑塔纳(改款)', '桑塔纳(改款)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270054', '桑塔纳(高配版)', '桑塔纳(高配版)', '桑塔纳(高配版)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270055', '桑塔纳2000', '桑塔纳2000', '桑塔纳2000', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270056', '桑塔纳2000(改款)', '桑塔纳2000(改款)', '桑塔纳2000(改款)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270057', '桑塔纳志俊', '桑塔纳志俊', '桑塔纳志俊', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270058', '桑塔纳志俊(豪华型)', '桑塔纳志俊(豪华型)', '桑塔纳志俊(豪华型)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270059', '桑塔纳浩纳', '桑塔纳浩纳', '桑塔纳浩纳', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270060', '桑塔纳经典', '桑塔纳经典', '桑塔纳经典', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270061', '甲壳虫', '甲壳虫', '甲壳虫', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270062', '甲壳虫(E Bugster)', '甲壳虫(E Bugster)', '甲壳虫(E Bugster)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270063', '甲壳虫(沙丘越野版)', '甲壳虫(沙丘越野版)', '甲壳虫(沙丘越野版)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270064', '蔚揽', '蔚揽', '蔚揽', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270065', '蔚领', '蔚领', '蔚领', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270066', '辉腾', '辉腾', '辉腾', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270067', '迈特威', '迈特威', '迈特威', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270068', '迈腾', '迈腾', '迈腾', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270069', '迈腾(改款)', '迈腾(改款)', '迈腾(改款)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270070', '途安', '途安', '途安', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270071', '途安(改款)', '途安(改款)', '途安(改款)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270072', '途昂', '途昂', '途昂', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270073', '途观', '途观', '途观', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270074', '途观(改款)', '途观(改款)', '途观(改款)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270075', '途观(标配)', '途观(标配)', '途观(标配)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270076', '途锐', '途锐', '途锐', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270077', '速腾', '速腾', '速腾', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270078', '速腾(GLI)', '速腾(GLI)', '速腾(GLI)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270079', '速腾(改款)', '速腾(改款)', '速腾(改款)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270080', '速腾(改装)', '速腾(改装)', '速腾(改装)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270081', '速腾(旗舰型)', '速腾(旗舰型)', '速腾(旗舰型)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270082', '高尔', '高尔', '高尔', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270083', '高尔夫', '高尔夫', '高尔夫', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270084', '高尔夫(GTI)', '高尔夫(GTI)', '高尔夫(GTI)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270085', '高尔夫(R.LINE)', '高尔夫(R.LINE)', '高尔夫(R.LINE)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270086', '高尔夫(改款)', '高尔夫(改款)', '高尔夫(改款)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270087', '高尔夫(蓝驱版)', '高尔夫(蓝驱版)', '高尔夫(蓝驱版)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270088', '高尔夫(进口)', '高尔夫(进口)', '高尔夫(进口)', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270089', '高尔夫GTI', '高尔夫GTI', '高尔夫GTI', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01270090', '高尔夫R', '高尔夫R', '高尔夫R', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01279999', '未知', '未知', '未知', '0127');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01280000', 'MIRA', 'MIRA', 'MIRA', '0128');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01289999', '未知', '未知', '未知', '0128');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01290000', 'GDW6117HKD1', 'GDW6117HKD1', 'GDW6117HKD1', '0129');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01290001', 'GDW6119H', 'GDW6119H', 'GDW6119H', '0129');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01290002', 'GDW6128HK2', 'GDW6128HK2', 'GDW6128HK2', '0129');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01290003', 'GL6120GR1', 'GL6120GR1', 'GL6120GR1', '0129');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01290004', '典雅', '典雅', '典雅', '0129');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01290005', '蓝龙', '蓝龙', '蓝龙', '0129');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01299999', '未知', '未知', '未知', '0129');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01300000', 'D330', 'D330', 'D330', '0130');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01300001', 'DH270', 'DH270', 'DH270', '0130');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01300002', 'DH340', 'DH340', 'DH340', '0130');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01309999', '未知', '未知', '未知', '0130');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310000', 'CGC1161D4TAB', 'CGC1161D4TAB', 'CGC1161D4TAB', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310001', 'N6重卡(CGC1160D5BAEA)', 'N6重卡(CGC1160D5BAEA)', 'N6重卡(CGC1160D5BAEA)', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310002', 'N8C', 'N8C', 'N8C', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310003', 'N8H牵引车', 'N8H牵引车', 'N8H牵引车', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310004', 'N8V', 'N8V', 'N8V', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310005', 'N8牵引车(4181WD32A EB35A12A2EA1F)', 'N8牵引车(4181WD32A EB35A12A2EA1F)', 'N8牵引车(4181WD32A EB35A12A2EA1F)', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310006', 'N9重卡', 'N9重卡', 'N9重卡', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310007', '奥普力(CGC1041HBB33D)', '奥普力(CGC1041HBB33D)', '奥普力(CGC1041HBB33D)', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310008', '奥普力(CGC2043CHDE33E)', '奥普力(CGC2043CHDE33E)', '奥普力(CGC2043CHDE33E)', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310009', '奥普力(CGC5041CCYHBB33D)', '奥普力(CGC5041CCYHBB33D)', '奥普力(CGC5041CCYHBB33D)', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310010', '奥普力(CGC5041XXYHBB33D)', '奥普力(CGC5041XXYHBB33D)', '奥普力(CGC5041XXYHBB33D)', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310011', '新N8E', '新N8E', '新N8E', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310012', '祺运单排栏板轻卡', '祺运单排栏板轻卡', '祺运单排栏板轻卡', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310013', '致胜', '致胜', '致胜', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310014', '运途', '运途', '运途', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310015', '运隆混凝土搅拌车', '运隆混凝土搅拌车', '运隆混凝土搅拌车', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310016', '风度', '风度', '风度', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01310017', '风驰重卡', '风驰重卡', '风驰重卡', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01319999', '未知', '未知', '未知', '0131');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01320000', '商务皮卡', '商务皮卡', '商务皮卡', '0132');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01329999', '未知', '未知', '未知', '0132');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01330000', 'CHOKS系', 'CHOKS系', 'CHOKS系', '0133');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01339999', '未知', '未知', '未知', '0133');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01340000', '风驰', '风驰', '风驰', '0134');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01349999', '未知', '未知', '未知', '0134');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350000', 'A1', 'A1', 'A1', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350001', 'A3', 'A3', 'A3', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350002', 'A5', 'A5', 'A5', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350003', 'E3', 'E3', 'E3', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350004', 'E3(改装中网)', 'E3(改装中网)', 'E3(改装中网)', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350005', 'E5', 'E5', 'E5', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350006', 'EQ1', 'EQ1', 'EQ1', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350007', 'QQ', 'QQ', 'QQ', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350008', 'QQ3', 'QQ3', 'QQ3', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350009', 'QQ3(运动版)', 'QQ3(运动版)', 'QQ3(运动版)', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350010', 'QQ6', 'QQ6', 'QQ6', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350011', '东方之子', '东方之子', '东方之子', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350012', '东方之子Cross', '东方之子Cross', '东方之子Cross', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350013', '威麟X5', '威麟X5', '威麟X5', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350014', '旗云', '旗云', '旗云', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350015', '旗云(改装中网)', '旗云(改装中网)', '旗云(改装中网)', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350016', '旗云1', '旗云1', '旗云1', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350017', '旗云2', '旗云2', '旗云2', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350018', '旗云2(舒适型)', '旗云2(舒适型)', '旗云2(舒适型)', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350019', '旗云2(豪华型)', '旗云2(豪华型)', '旗云2(豪华型)', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350020', '旗云3', '旗云3', '旗云3', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350021', '瑞虎', '瑞虎', '瑞虎', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350022', '瑞虎3', '瑞虎3', '瑞虎3', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350023', '瑞虎3X', '瑞虎3X', '瑞虎3X', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350024', '瑞虎5', '瑞虎5', '瑞虎5', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350025', '瑞虎5(改装中网)', '瑞虎5(改装中网)', '瑞虎5(改装中网)', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350026', '瑞虎7', '瑞虎7', '瑞虎7', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350027', '艾瑞泽3', '艾瑞泽3', '艾瑞泽3', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350028', '艾瑞泽5', '艾瑞泽5', '艾瑞泽5', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350029', '艾瑞泽7', '艾瑞泽7', '艾瑞泽7', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350030', '艾瑞泽M7', '艾瑞泽M7', '艾瑞泽M7', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350031', '艾瑞泽M7(改装中网)', '艾瑞泽M7(改装中网)', '艾瑞泽M7(改装中网)', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350032', '风云', '风云', '风云', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01350033', '风云2', '风云2', '风云2', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01359999', '未知', '未知', '未知', '0135');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01360000', 'B30', 'B30', 'B30', '0136');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01360001', 'B50', 'B50', 'B50', '0136');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01360002', 'B50(运动型)', 'B50(运动型)', 'B50(运动型)', '0136');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01360003', 'B70', 'B70', 'B70', '0136');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01360004', 'B70(运动型)', 'B70(运动型)', 'B70(运动型)', '0136');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01360005', 'B90', 'B90', 'B90', '0136');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01360006', 'X4', 'X4', 'X4', '0136');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01360007', 'X80', 'X80', 'X80', '0136');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01360008', 'X80(手动舒适型)', 'X80(手动舒适型)', 'X80(手动舒适型)', '0136');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01360009', 'X80(改装中网)', 'X80(改装中网)', 'X80(改装中网)', '0136');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01360010', 'X80(运动型)', 'X80(运动型)', 'X80(运动型)', '0136');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01369999', '未知', '未知', '未知', '0136');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01370000', 'BM2815PD2', 'BM2815PD2', 'BM2815PD2', '0137');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01370001', 'BM5820PDF3E', 'BM5820PDF3E', 'BM5820PDF3E', '0137');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01370002', '载货车', '载货车', '载货车', '0137');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01379999', '未知', '未知', '未知', '0137');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380000', 'ACTROS重卡', 'ACTROS重卡', 'ACTROS重卡', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380001', 'AMGGT', 'AMGGT', 'AMGGT', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380002', 'AMGGTR', 'AMGGTR', 'AMGGTR', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380003', 'A级(运动型)', 'A级(运动型)', 'A级(运动型)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380004', 'A级(进口)', 'A级(进口)', 'A级(进口)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380005', 'A级(进口)(运动型)', 'A级(进口)(运动型)', 'A级(进口)(运动型)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380006', 'A级AMG', 'A级AMG', 'A级AMG', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380007', 'B级', 'B级', 'B级', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380008', 'B级(进口)', 'B级(进口)', 'B级(进口)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380009', 'B级(进口)(B180)', 'B级(进口)(B180)', 'B级(进口)(B180)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380010', 'CLA级', 'CLA级', 'CLA级', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380011', 'CLA级(进口)', 'CLA级(进口)', 'CLA级(进口)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380012', 'CLK级(进口)', 'CLK级(进口)', 'CLK级(进口)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380013', 'CLS级', 'CLS级', 'CLS级', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380014', 'CLS级(改装中网)', 'CLS级(改装中网)', 'CLS级(改装中网)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380015', 'C级', 'C级', 'C级', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380016', 'C级(C200L)', 'C级(C200L)', 'C级(C200L)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380017', 'C级(GrandEdition)', 'C级(GrandEdition)', 'C级(GrandEdition)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380018', 'C级(改装中网)', 'C级(改装中网)', 'C级(改装中网)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380019', 'C级(时尚型)', 'C级(时尚型)', 'C级(时尚型)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380020', 'C级(时尚型GrandEdition)', 'C级(时尚型GrandEdition)', 'C级(时尚型GrandEdition)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380021', 'C级(经典型)', 'C级(经典型)', 'C级(经典型)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380022', 'C级(经典型,优雅型)', 'C级(经典型,优雅型)', 'C级(经典型,优雅型)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380023', 'C级(经典型GrandEdition)', 'C级(经典型GrandEdition)', 'C级(经典型GrandEdition)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380024', 'C级(经典版,标准型)', 'C级(经典版,标准型)', 'C级(经典版,标准型)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380025', 'C级(运动型)', 'C级(运动型)', 'C级(运动型)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380026', 'C级(进口)', 'C级(进口)', 'C级(进口)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380027', 'C级(进口)(轿跑版)', 'C级(进口)(轿跑版)', 'C级(进口)(轿跑版)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380028', 'C级AMG', 'C级AMG', 'C级AMG', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380029', 'E系列(优雅型)', 'E系列(优雅型)', 'E系列(优雅型)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380030', 'E系列(时尚型)', 'E系列(时尚型)', 'E系列(时尚型)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380031', 'E级', 'E级', 'E级', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380032', 'E级(E260L)', 'E级(E260L)', 'E级(E260L)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380033', 'E级(E300L)', 'E级(E300L)', 'E级(E300L)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380034', 'E级(改装中网)', 'E级(改装中网)', 'E级(改装中网)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380035', 'E级(豪华型)', 'E级(豪华型)', 'E级(豪华型)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380036', 'E级(运动型)', 'E级(运动型)', 'E级(运动型)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380037', 'E级(运动型豪华型)', 'E级(运动型豪华型)', 'E级(运动型豪华型)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380038', 'E级(运动时尚型)', 'E级(运动时尚型)', 'E级(运动时尚型)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380039', 'E级(运动豪华型)', 'E级(运动豪华型)', 'E级(运动豪华型)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380040', 'E级(进口)', 'E级(进口)', 'E级(进口)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380041', 'E级(进口)(COUPE)', 'E级(进口)(COUPE)', 'E级(进口)(COUPE)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380042', 'E级(进口)(Coupe版)', 'E级(进口)(Coupe版)', 'E级(进口)(Coupe版)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380043', 'E级(非运动款)', 'E级(非运动款)', 'E级(非运动款)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380044', 'E级AMG', 'E级AMG', 'E级AMG', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380045', 'E级AMG(改装中网)', 'E级AMG(改装中网)', 'E级AMG(改装中网)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380046', 'GLA', 'GLA', 'GLA', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380047', 'GLA(进口)', 'GLA(进口)', 'GLA(进口)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380048', 'GLA级', 'GLA级', 'GLA级', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380049', 'GLC级', 'GLC级', 'GLC级', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380050', 'GLE(轿跑SUV)', 'GLE(轿跑SUV)', 'GLE(轿跑SUV)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380051', 'GLE级', 'GLE级', 'GLE级', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380052', 'GLK级', 'GLK级', 'GLK级', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380053', 'GLK级(豪华版)', 'GLK级(豪华版)', 'GLK级(豪华版)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380054', 'GLK级(进口)', 'GLK级(进口)', 'GLK级(进口)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380055', 'GL级', 'GL级', 'GL级', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380056', 'GL级(GL350)', 'GL级(GL350)', 'GL级(GL350)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380057', 'GL级(GL500)', 'GL级(GL500)', 'GL级(GL500)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380058', 'GL级(加装前杠)', 'GL级(加装前杠)', 'GL级(加装前杠)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380059', 'GL级(尊贵型GRANDEDITION)', 'GL级(尊贵型GRANDEDITION)', 'GL级(尊贵型GRANDEDITION)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380060', 'GL级AMG', 'GL级AMG', 'GL级AMG', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380061', 'G级', 'G级', 'G级', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380062', 'G级(进口)', 'G级(进口)', 'G级(进口)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380063', 'MB100', 'MB100', 'MB100', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380064', 'M级', 'M级', 'M级', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380065', 'M级(ML63)', 'M级(ML63)', 'M级(ML63)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380066', 'M级(改装雾灯)', 'M级(改装雾灯)', 'M级(改装雾灯)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380067', 'M级(特别豪华版)', 'M级(特别豪华版)', 'M级(特别豪华版)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380068', 'M级(豪华版)', 'M级(豪华版)', 'M级(豪华版)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380069', 'M级(进口)', 'M级(进口)', 'M级(进口)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380070', 'M级(进口)(加装前杠)', 'M级(进口)(加装前杠)', 'M级(进口)(加装前杠)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380071', 'M级AMG', 'M级AMG', 'M级AMG', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380072', 'R级', 'R级', 'R级', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380073', 'R级(进口)', 'R级(进口)', 'R级(进口)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380074', 'SL500AMG(改装)', 'SL500AMG(改装)', 'SL500AMG(改装)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380075', 'SLK级', 'SLK级', 'SLK级', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380076', 'SLK级(时尚型)', 'SLK级(时尚型)', 'SLK级(时尚型)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380077', 'SLK级AMG', 'SLK级AMG', 'SLK级AMG', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380078', 'SLSAMG', 'SLSAMG', 'SLSAMG', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380079', 'SL级', 'SL级', 'SL级', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380080', 'SL级(改装)', 'SL级(改装)', 'SL级(改装)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380081', 'S级', 'S级', 'S级', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380082', 'S级AMG', 'S级AMG', 'S级AMG', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380083', 'S级AMG(S63)', 'S级AMG(S63)', 'S级AMG(S63)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380084', 'ZETROS', 'ZETROS', 'ZETROS', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380085', '中巴03', '中巴03', '中巴03', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380086', '凌特', '凌特', '凌特', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380087', '唯雅诺', '唯雅诺', '唯雅诺', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380088', '唯雅诺(含进口版)', '唯雅诺(含进口版)', '唯雅诺(含进口版)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380089', '唯雅诺(进口)', '唯雅诺(进口)', '唯雅诺(进口)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380090', '威霆', '威霆', '威霆', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380091', '威霆(进口)', '威霆(进口)', '威霆(进口)', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380092', '安凯客车01', '安凯客车01', '安凯客车01', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380093', '客车01', '客车01', '客车01', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01380094', '迈巴赫S级', '迈巴赫S级', '迈巴赫S级', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01389999', '未知', '未知', '未知', '0138');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01390000', 'ACTROS', 'ACTROS', 'ACTROS', '0139');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01390001', 'ACTROS(牵引车)', 'ACTROS(牵引车)', 'ACTROS(牵引车)', '0139');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01390002', 'ACTROS1851', 'ACTROS1851', 'ACTROS1851', '0139');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01399999', '未知', '未知', '未知', '0139');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400000', '100', '100', '100', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400001', 'A1', 'A1', 'A1', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400002', 'A3(Limousine)', 'A3(Limousine)', 'A3(Limousine)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400003', 'A3(Sportback)', 'A3(Sportback)', 'A3(Sportback)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400004', 'A3(进口)', 'A3(进口)', 'A3(进口)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400005', 'A3(进口)(CABRIOLET)', 'A3(进口)(CABRIOLET)', 'A3(进口)(CABRIOLET)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400006', 'A3(进口)(SPORTBACK.ETRON)', 'A3(进口)(SPORTBACK.ETRON)', 'A3(进口)(SPORTBACK.ETRON)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400007', 'A3(进口)(中高配)', 'A3(进口)(中高配)', 'A3(进口)(中高配)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400008', 'A4', 'A4', 'A4', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400009', 'A4(个性风格版)', 'A4(个性风格版)', 'A4(个性风格版)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400010', 'A4(敞篷版)', 'A4(敞篷版)', 'A4(敞篷版)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400011', 'A4(进口)', 'A4(进口)', 'A4(进口)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400012', 'A4(进口)(AVANT)', 'A4(进口)(AVANT)', 'A4(进口)(AVANT)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400013', 'A4L', 'A4L', 'A4L', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400014', 'A4L(quattro个性运动型)', 'A4L(quattro个性运动型)', 'A4L(quattro个性运动型)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400015', 'A4L(风尚型)', 'A4L(风尚型)', 'A4L(风尚型)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400016', 'A5', 'A5', 'A5', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400017', 'A5(COUPE)', 'A5(COUPE)', 'A5(COUPE)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400018', 'A5(SPORTBACK)', 'A5(SPORTBACK)', 'A5(SPORTBACK)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400019', 'A6', 'A6', 'A6', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400020', 'A6(改款)', 'A6(改款)', 'A6(改款)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400021', 'A6(进口)', 'A6(进口)', 'A6(进口)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400022', 'A6(进口)(Avant)', 'A6(进口)(Avant)', 'A6(进口)(Avant)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400023', 'A6(进口)(TDI)', 'A6(进口)(TDI)', 'A6(进口)(TDI)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400024', 'A6L', 'A6L', 'A6L', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400025', 'A6L(改款)', 'A6L(改款)', 'A6L(改款)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400026', 'A7', 'A7', 'A7', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400027', 'A8', 'A8', 'A8', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400028', 'A8L', 'A8L', 'A8L', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400029', 'Q2', 'Q2', 'Q2', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400030', 'Q3', 'Q3', 'Q3', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400031', 'Q3(进口)(越野型)', 'Q3(进口)(越野型)', 'Q3(进口)(越野型)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400032', 'Q5', 'Q5', 'Q5', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400033', 'Q5(豪华型)', 'Q5(豪华型)', 'Q5(豪华型)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400034', 'Q5(进口)', 'Q5(进口)', 'Q5(进口)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400035', 'Q5(进口)(hybrid)', 'Q5(进口)(hybrid)', 'Q5(进口)(hybrid)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400036', 'Q5(进口)(运动型)', 'Q5(进口)(运动型)', 'Q5(进口)(运动型)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400037', 'Q7', 'Q7', 'Q7', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400038', 'Q7(1)', 'Q7(1)', 'Q7(1)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400039', 'Q7(2)', 'Q7(2)', 'Q7(2)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400040', 'Q7(豪华型)', 'Q7(豪华型)', 'Q7(豪华型)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400041', 'Q8', 'Q8', 'Q8', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400042', 'RS3', 'RS3', 'RS3', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400043', 'RS3(LMS)', 'RS3(LMS)', 'RS3(LMS)', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400044', 'RS4', 'RS4', 'RS4', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400045', 'RS5', 'RS5', 'RS5', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400046', 'RS6', 'RS6', 'RS6', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400047', 'RS7', 'RS7', 'RS7', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400048', 'RSQ3', 'RSQ3', 'RSQ3', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400049', 'S3', 'S3', 'S3', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400050', 'S5', 'S5', 'S5', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400051', 'S6', 'S6', 'S6', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400052', 'S7', 'S7', 'S7', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400053', 'S8', 'S8', 'S8', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400054', 'SQ5', 'SQ5', 'SQ5', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400055', 'TT', 'TT', 'TT', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01400056', 'TTRS', 'TTRS', 'TTRS', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01409999', '未知', '未知', '未知', '0140');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01410000', 'A3系列', 'A3系列', 'A3系列', '0141');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01410001', 'D5系列', 'D5系列', 'D5系列', '0141');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01410002', '悦菱微卡', '悦菱微卡', '悦菱微卡', '0141');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01419999', '未知', '未知', '未知', '0141');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01420000', 'CTR3', 'CTR3', 'CTR3', '0142');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01420001', 'XL', 'XL', 'XL', '0142');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01429999', '未知', '未知', '未知', '0142');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01430000', 'GT', 'GT', 'GT', '0143');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01439999', '未知', '未知', '未知', '0143');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01440000', 'H3', 'H3', 'H3', '0144');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01440001', 'H5', 'H5', 'H5', '0144');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01440002', 'V5', 'V5', 'V5', '0144');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01440003', 'V8', 'V8', 'V8', '0144');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01449999', '未知', '未知', '未知', '0144');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450000', 'XML6807A23', 'XML6807A23', 'XML6807A23', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450001', 'XML6887J15', 'XML6887J15', 'XML6887J15', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450002', 'ZK5115XYL1', 'ZK5115XYL1', 'ZK5115XYL1', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450003', 'ZK5140XLJAA', 'ZK5140XLJAA', 'ZK5140XLJAA', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450004', 'ZK6100DX1', 'ZK6100DX1', 'ZK6100DX1', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450005', 'ZK6105CHEVNPG26', 'ZK6105CHEVNPG26', 'ZK6105CHEVNPG26', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450006', 'ZK6106BEV2', 'ZK6106BEV2', 'ZK6106BEV2', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450007', 'ZK6108H', 'ZK6108H', 'ZK6108H', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450008', 'ZK6108HGA', 'ZK6108HGA', 'ZK6108HGA', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450009', 'ZK6112WD', 'ZK6112WD', 'ZK6112WD', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450010', 'ZK6118HB', 'ZK6118HB', 'ZK6118HB', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450011', 'ZK6118HQY8Y', 'ZK6118HQY8Y', 'ZK6118HQY8Y', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450012', 'ZK6122H', 'ZK6122H', 'ZK6122H', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450013', 'ZK6126HNY5Y', 'ZK6126HNY5Y', 'ZK6126HNY5Y', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450014', 'ZK6127H', 'ZK6127H', 'ZK6127H', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450015', 'ZK6127HC', 'ZK6127HC', 'ZK6127HC', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450016', 'ZK6127HQB9', 'ZK6127HQB9', 'ZK6127HQB9', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450017', 'ZK6127HS', 'ZK6127HS', 'ZK6127HS', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450018', 'ZK6127H中巴', 'ZK6127H中巴', 'ZK6127H中巴', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450019', 'ZK6129H', 'ZK6129H', 'ZK6129H', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450020', 'ZK6129HD', 'ZK6129HD', 'ZK6129HD', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450021', 'ZK6146HSC9', 'ZK6146HSC9', 'ZK6146HSC9', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450022', 'ZK6180HGC', 'ZK6180HGC', 'ZK6180HGC', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450023', 'ZK6559DX3', 'ZK6559DX3', 'ZK6559DX3', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450024', 'ZK6579DX', 'ZK6579DX', 'ZK6579DX', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450025', 'ZK6609NG5', 'ZK6609NG5', 'ZK6609NG5', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450026', 'ZK6662NX1', 'ZK6662NX1', 'ZK6662NX1', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450027', 'ZK6708DH1', 'ZK6708DH1', 'ZK6708DH1', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450028', 'ZK6729DT', 'ZK6729DT', 'ZK6729DT', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450029', 'ZK6732GF', 'ZK6732GF', 'ZK6732GF', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450030', 'ZK6790H', 'ZK6790H', 'ZK6790H', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450031', 'ZK6792D1', 'ZK6792D1', 'ZK6792D1', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450032', 'ZK6802DA9', 'ZK6802DA9', 'ZK6802DA9', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450033', 'ZK6831HD', 'ZK6831HD', 'ZK6831HD', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450034', 'ZK6852HNG2', 'ZK6852HNG2', 'ZK6852HNG2', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450035', 'ZK6876H', 'ZK6876H', 'ZK6876H', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450036', 'ZK6930H', 'ZK6930H', 'ZK6930H', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450037', 'ZK6932D', 'ZK6932D', 'ZK6932D', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01450038', 'ZK6936HGN', 'ZK6936HGN', 'ZK6936HGN', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01459999', '未知', '未知', '未知', '0145');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01460000', 'HFF6110K06D', 'HFF6110K06D', 'HFF6110K06D', '0146');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01460001', 'HFF6110LK10D', 'HFF6110LK10D', 'HFF6110LK10D', '0146');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01460002', 'HFF6120K82D', 'HFF6120K82D', 'HFF6120K82D', '0146');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01460003', 'HFF6122KZ', 'HFF6122KZ', 'HFF6122KZ', '0146');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01460004', 'HFF6124K06D', 'HFF6124K06D', 'HFF6124K06D', '0146');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01460005', 'HFF6124K40D1', 'HFF6124K40D1', 'HFF6124K40D1', '0146');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01460006', 'HFF6127K46EV', 'HFF6127K46EV', 'HFF6127K46EV', '0146');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01460007', 'HFF6129HK', 'HFF6129HK', 'HFF6129HK', '0146');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01469999', '未知', '未知', '未知', '0146');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01470000', 'K6530HQD3', 'K6530HQD3', 'K6530HQD3', '0147');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01470001', 'PK6112A', 'PK6112A', 'PK6112A', '0147');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01470002', 'PK6121A4', 'PK6121A4', 'PK6121A4', '0147');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01470003', 'PK8808HQ3', 'PK8808HQ3', 'PK8808HQ3', '0147');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01479999', '未知', '未知', '未知', '0147');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01480000', 'BX5', 'BX5', 'BX5', '0148');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01480001', 'BX6TS', 'BX6TS', 'BX6TS', '0148');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01480002', 'BX7', 'BX7', 'BX7', '0148');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01489999', '未知', '未知', '未知', '0148');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01490000', '农用车', '农用车', '农用车', '0149');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01490001', '富兴', '富兴', '富兴', '0149');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01499999', '未知', '未知', '未知', '0149');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01500000', '雅睿', '雅睿', '雅睿', '0150');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01500001', '雅贝', '雅贝', '雅贝', '0150');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01509999', '未知', '未知', '未知', '0150');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510000', '1系', '1系', '1系', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510001', '1系(M135)', '1系(M135)', '1系(M135)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510002', '1系(运动版)', '1系(运动版)', '1系(运动版)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510003', '1系(进口)(ActiveEConcept)', '1系(进口)(ActiveEConcept)', '1系(进口)(ActiveEConcept)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510004', '1系(进口)(双门轿跑)', '1系(进口)(双门轿跑)', '1系(进口)(双门轿跑)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510005', '1系(进口)(运动型)', '1系(进口)(运动型)', '1系(进口)(运动型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510006', '1系(进口)(运动设计套装)', '1系(进口)(运动设计套装)', '1系(进口)(运动设计套装)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510007', '1系(进口)(运动限量版)', '1系(进口)(运动限量版)', '1系(进口)(运动限量版)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510008', '1系(进口)(都市型)', '1系(进口)(都市型)', '1系(进口)(都市型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510009', '1系(进口)(都市设计套装)', '1系(进口)(都市设计套装)', '1系(进口)(都市设计套装)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510010', '1系(进口)(领先型)', '1系(进口)(领先型)', '1系(进口)(领先型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510011', '2系', '2系', '2系', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510012', '2系(M)', '2系(M)', '2系(M)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510013', '2系(运动版)', '2系(运动版)', '2系(运动版)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510014', '2系旅行车', '2系旅行车', '2系旅行车', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510015', '3系', '3系', '3系', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510016', '3系(320i运动设计套装)', '3系(320i运动设计套装)', '3系(320i运动设计套装)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510017', '3系(325i豪华运动型)', '3系(325i豪华运动型)', '3系(325i豪华运动型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510018', '3系(LI时尚型)', '3系(LI时尚型)', '3系(LI时尚型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510019', '3系(LI豪华设计套装)', '3系(LI豪华设计套装)', '3系(LI豪华设计套装)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510020', '3系(M运动型)', '3系(M运动型)', '3系(M运动型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510021', '3系(M运动版)', '3系(M运动版)', '3系(M运动版)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510022', '3系(改装M3)', '3系(改装M3)', '3系(改装M3)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510023', '3系(时尚型)', '3系(时尚型)', '3系(时尚型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510024', '3系(豪华设计套装)', '3系(豪华设计套装)', '3系(豪华设计套装)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510025', '3系(运动型)', '3系(运动型)', '3系(运动型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510026', '3系(进取型)', '3系(进取型)', '3系(进取型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510027', '3系(进口)', '3系(进口)', '3系(进口)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510028', '3系(进口)(325I)', '3系(进口)(325I)', '3系(进口)(325I)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510029', '3系(进口)(330Ci)', '3系(进口)(330Ci)', '3系(进口)(330Ci)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510030', '3系(进口)(330i)', '3系(进口)(330i)', '3系(进口)(330i)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510031', '3系(进口)(ActiveHybird)', '3系(进口)(ActiveHybird)', '3系(进口)(ActiveHybird)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510032', '3系(进口)(COUPE)', '3系(进口)(COUPE)', '3系(进口)(COUPE)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510033', '3系(进口)(Coupe)', '3系(进口)(Coupe)', '3系(进口)(Coupe)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510034', '3系(进口)(双门轿跑版)', '3系(进口)(双门轿跑版)', '3系(进口)(双门轿跑版)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510035', '3系(进口)(旅行版)', '3系(进口)(旅行版)', '3系(进口)(旅行版)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510036', '3系(进口)(轿跑)', '3系(进口)(轿跑)', '3系(进口)(轿跑)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510037', '3系(进口)(运动设计套装)', '3系(进口)(运动设计套装)', '3系(进口)(运动设计套装)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510038', '3系(领先型)', '3系(领先型)', '3系(领先型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510039', '3系(风尚设计套装)', '3系(风尚设计套装)', '3系(风尚设计套装)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510040', '3系GT', '3系GT', '3系GT', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510041', '3系GT(领先型)', '3系GT(领先型)', '3系GT(领先型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510042', '4系', '4系', '4系', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510043', '4系(420i)', '4系(420i)', '4系(420i)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510044', '4系(豪华设计套装)', '4系(豪华设计套装)', '4系(豪华设计套装)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510045', '4系(运动设计套装)', '4系(运动设计套装)', '4系(运动设计套装)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510046', '5系', '5系', '5系', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510047', '5系(中低配型)', '5系(中低配型)', '5系(中低配型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510048', '5系(进口)', '5系(进口)', '5系(进口)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510049', '5系(进口)(M运动版)', '5系(进口)(M运动版)', '5系(进口)(M运动版)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510050', '5系GT', '5系GT', '5系GT', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510051', '6系', '6系', '6系', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510052', '6系(四门版)', '6系(四门版)', '6系(四门版)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510053', '6系COUPE', '6系COUPE', '6系COUPE', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510054', '7系', '7系', '7系', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510055', '7系(M)', '7系(M)', '7系(M)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510056', '7系(XDRIVE)', '7系(XDRIVE)', '7系(XDRIVE)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510057', 'I3', 'I3', 'I3', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510058', 'I8', 'I8', 'I8', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510059', 'M3', 'M3', 'M3', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510060', 'M5', 'M5', 'M5', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510061', 'X1', 'X1', 'X1', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510062', 'X1(新能源)', 'X1(新能源)', 'X1(新能源)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510063', 'X3', 'X3', 'X3', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510064', 'X3(基本型)', 'X3(基本型)', 'X3(基本型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510065', 'X3(进口)', 'X3(进口)', 'X3(进口)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510066', 'X4', 'X4', 'X4', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510067', 'X4(X设计套装)', 'X4(X设计套装)', 'X4(X设计套装)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510068', 'X4(领先型)', 'X4(领先型)', 'X4(领先型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510069', 'X5', 'X5', 'X5', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510070', 'X5(3.0L)', 'X5(3.0L)', 'X5(3.0L)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510071', 'X5(4.6L)', 'X5(4.6L)', 'X5(4.6L)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510072', 'X5(M运动型)', 'X5(M运动型)', 'X5(M运动型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510073', 'X5(领先型)', 'X5(领先型)', 'X5(领先型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510074', 'X5M', 'X5M', 'X5M', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510075', 'X6', 'X6', 'X6', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510076', 'X6(M50d)', 'X6(M50d)', 'X6(M50d)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510077', 'X6(XDRIVE)', 'X6(XDRIVE)', 'X6(XDRIVE)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510078', 'X6(尊享型)', 'X6(尊享型)', 'X6(尊享型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510079', 'X6(运动型)', 'X6(运动型)', 'X6(运动型)', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510080', 'Z4', 'Z4', 'Z4', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01510081', 'Z8', 'Z8', 'Z8', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01519999', '未知', '未知', '未知', '0151');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01520000', '310(手动舒适型)', '310(手动舒适型)', '310(手动舒适型)', '0152');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01520001', '310(时尚型)', '310(时尚型)', '310(时尚型)', '0152');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01520002', '330', '330', '330', '0152');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01520003', '510', '510', '510', '0152');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01520004', '560', '560', '560', '0152');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01520005', '560(改装)', '560(改装)', '560(改装)', '0152');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01520006', '610', '610', '610', '0152');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01520007', '630', '630', '630', '0152');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01520008', '630(改装)', '630(改装)', '630(改装)', '0152');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01520009', '730', '730', '730', '0152');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01520010', '乐驰', '乐驰', '乐驰', '0152');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01529999', '未知', '未知', '未知', '0152');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01530000', '慕尚', '慕尚', '慕尚', '0153');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01530001', '欧陆', '欧陆', '欧陆', '0153');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01530002', '添越', '添越', '添越', '0153');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01530003', '雅俊', '雅俊', '雅俊', '0153');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01530004', '飞驰', '飞驰', '飞驰', '0153');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01539999', '未知', '未知', '未知', '0153');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01540000', '乐途', '乐途', '乐途', '0154');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01540001', '新乐驰', '新乐驰', '新乐驰', '0154');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01540002', '美客', '美客', '美客', '0154');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01540003', '途瑞', '途瑞', '途瑞', '0154');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01549999', '未知', '未知', '未知', '0154');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01550000', 'BETA', 'BETA', 'BETA', '0155');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01559999', '未知', '未知', '未知', '0155');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01560000', 'SLG6531XC5E', 'SLG6531XC5E', 'SLG6531XC5E', '0156');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01560001', 'SLG6602C5E', 'SLG6602C5E', 'SLG6602C5E', '0156');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01560002', 'SLG6605CXGJ', 'SLG6605CXGJ', 'SLG6605CXGJ', '0156');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01560003', 'SLG6661XC4Z', 'SLG6661XC4Z', 'SLG6661XC4Z', '0156');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01560004', 'SLG6690C4Z', 'SLG6690C4Z', 'SLG6690C4Z', '0156');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01560005', 'SLG6730T5GF', 'SLG6730T5GF', 'SLG6730T5GF', '0156');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01560006', 'SLG6780HCE', 'SLG6780HCE', 'SLG6780HCE', '0156');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01560007', 'SLG6840T5E', 'SLG6840T5E', 'SLG6840T5E', '0156');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01560008', 'SLG6900C4FR', 'SLG6900C4FR', 'SLG6900C4FR', '0156');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01560009', 'SLG6930T4E', 'SLG6930T4E', 'SLG6930T4E', '0156');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01569999', '未知', '未知', '未知', '0156');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01570000', '中卡', '中卡', '中卡', '0157');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01570001', '自卸车260', '自卸车260', '自卸车260', '0157');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01570002', '自卸车270', '自卸车270', '自卸车270', '0157');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01570003', '自卸车2702', '自卸车2702', '自卸车2702', '0157');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01579999', '未知', '未知', '未知', '0157');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01580000', 'CJQ6120KJ', 'CJQ6120KJ', 'CJQ6120KJ', '0158');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01589999', '未知', '未知', '未知', '0158');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01590000', 'CHIRON', 'CHIRON', 'CHIRON', '0159');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01590001', '威航', '威航', '威航', '0159');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01599999', '未知', '未知', '未知', '0159');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01600000', 'GA3', 'GA3', 'GA3', '0160');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01600001', 'GA3S视界', 'GA3S视界', 'GA3S视界', '0160');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01600002', 'GA5', 'GA5', 'GA5', '0160');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01600003', 'GA6', 'GA6', 'GA6', '0160');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01600004', 'GA8', 'GA8', 'GA8', '0160');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01600005', 'GS4', 'GS4', 'GS4', '0160');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01600006', 'GS5', 'GS5', 'GS5', '0160');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01600007', 'GS5SUPER', 'GS5SUPER', 'GS5SUPER', '0160');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01600008', 'GS8', 'GS8', 'GS8', '0160');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01609999', '未知', '未知', '未知', '0160');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01610000', 'E美', 'E美', 'E美', '0161');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01610001', 'GP150', 'GP150', 'GP150', '0161');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01610002', 'GX6', 'GX6', 'GX6', '0161');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01610003', '奥轩', '奥轩', '奥轩', '0161');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01610004', '奥轩G5', '奥轩G5', '奥轩G5', '0161');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01610005', '奥轩GX5', '奥轩GX5', '奥轩GX5', '0161');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01610006', '帅舰', '帅舰', '帅舰', '0161');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01610007', '帅豹', '帅豹', '帅豹', '0161');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01610008', '星旺', '星旺', '星旺', '0161');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01610009', '星旺CL', '星旺CL', '星旺CL', '0161');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01610010', '星朗', '星朗', '星朗', '0161');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01610011', '星福栏板轻卡', '星福栏板轻卡', '星福栏板轻卡', '0161');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01610012', '财运100', '财运100', '财运100', '0161');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01610013', '财运300', '财运300', '财运300', '0161');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01610014', '财运500', '财运500', '财运500', '0161');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01619999', '未知', '未知', '未知', '0161');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01620000', '270Y', '270Y', '270Y', '0162');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01620001', '700', '700', '700', '0162');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01620002', '700重卡(265)', '700重卡(265)', '700重卡(265)', '0162');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01620003', '700重卡(265)(轿运车)', '700重卡(265)(轿运车)', '700重卡(265)(轿运车)', '0162');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01620004', '700重卡(450)', '700重卡(450)', '700重卡(450)', '0162');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01620005', '大巴SFQ6123PSHL', '大巴SFQ6123PSHL', '大巴SFQ6123PSHL', '0162');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01629999', '未知', '未知', '未知', '0162');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01630000', 'GTQ6109E3B3', 'GTQ6109E3B3', 'GTQ6109E3B3', '0163');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01630001', 'GTQ6117N4GJ5', 'GTQ6117N4GJ5', 'GTQ6117N4GJ5', '0163');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01630002', 'GTQ6605N5GJ', 'GTQ6605N5GJ', 'GTQ6605N5GJ', '0163');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01630003', 'GTQ6858BEVB2', 'GTQ6858BEVB2', 'GTQ6858BEVB2', '0163');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01639999', '未知', '未知', '未知', '0163');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01640000', '五十铃FVR', '五十铃FVR', '五十铃FVR', '0164');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01640001', '五十铃VC46', '五十铃VC46', '五十铃VC46', '0164');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01649999', '未知', '未知', '未知', '0164');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01650000', 'K10D', 'K10D', 'K10D', '0165');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01650001', 'K11D', 'K11D', 'K11D', '0165');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01650002', 'K12', 'K12', 'K12', '0165');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01650003', 'K17A', 'K17A', 'K17A', '0165');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01659999', '未知', '未知', '未知', '0165');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01660000', 'K50', 'K50', 'K50', '0166');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01660001', 'K60', 'K60', 'K60', '0166');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01660002', '优优', '优优', '优优', '0166');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01660003', '优劲', '优劲', '优劲', '0166');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01660004', '优派', '优派', '优派', '0166');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01660005', '优派(基本型)', '优派(基本型)', '优派(基本型)', '0166');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01660006', '优派(豪华型)', '优派(豪华型)', '优派(豪华型)', '0166');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01660007', '优胜2代', '优胜2代', '优胜2代', '0166');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01660008', '优雅', '优雅', '优雅', '0166');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01660009', '优雅2代', '优雅2代', '优雅2代', '0166');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01660010', '杰虎', '杰虎', '杰虎', '0166');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01660011', '绿卡', '绿卡', '绿卡', '0166');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01660012', '绿卡T', '绿卡T', '绿卡T', '0166');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01669999', '未知', '未知', '未知', '0166');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01670000', '99', '99', '99', '0167');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01679999', '未知', '未知', '未知', '0167');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01680000', 'QY16D汽车起重机', 'QY16D汽车起重机', 'QY16D汽车起重机', '0168');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01680001', 'XCT25L5起重机', 'XCT25L5起重机', 'XCT25L5起重机', '0168');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01680002', '吊车01', '吊车01', '吊车01', '0168');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01680003', '旗龙重卡', '旗龙重卡', '旗龙重卡', '0168');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01680004', '汉风G7(重卡500)(大货)', '汉风G7(重卡500)(大货)', '汉风G7(重卡500)(大货)', '0168');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01680005', '汉风G7(重卡500)(特大货)', '汉风G7(重卡500)(特大货)', '汉风G7(重卡500)(特大货)', '0168');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01680006', '琪龙牵引车', '琪龙牵引车', '琪龙牵引车', '0168');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01680007', '瑞龙重卡170厢式载货车', '瑞龙重卡170厢式载货车', '瑞龙重卡170厢式载货车', '0168');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01680008', '祺龙重卡(NXG3310D3KEL)', '祺龙重卡(NXG3310D3KEL)', '祺龙重卡(NXG3310D3KEL)', '0168');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01689999', '未知', '未知', '未知', '0168');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01690000', 'A280', 'A280', 'A280', '0169');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01690001', 'E330', 'E330', 'E330', '0169');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01690002', 'Q', 'Q', 'Q', '0169');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01690003', 'S325', 'S325', 'S325', '0169');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01690004', 'X6320', 'X6320', 'X6320', '0169');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01699999', '未知', '未知', '未知', '0169');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01700000', '1', '1', '1', '0170');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01709999', '未知', '未知', '未知', '0170');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01710000', '途腾T1', '途腾T1', '途腾T1', '0171');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01710001', '途腾T2', '途腾T2', '途腾T2', '0171');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01710002', '途腾T3', '途腾T3', '途腾T3', '0171');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01719999', '未知', '未知', '未知', '0171');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01720000', 'H2', 'H2', 'H2', '0172');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01720001', 'H3', 'H3', 'H3', '0172');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01729999', '未知', '未知', '未知', '0172');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01730000', 'K2', 'K2', 'K2', '0173');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01730001', 'V1', 'V1', 'V1', '0173');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01730002', 'V2', 'V2', 'V2', '0173');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01730003', 'X1', 'X1', 'X1', '0173');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01739999', '未知', '未知', '未知', '0173');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01740000', 'YZK6730EQB4', 'YZK6730EQB4', 'YZK6730EQB4', '0174');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01740001', 'YZK6950NJB4', 'YZK6950NJB4', 'YZK6950NJB4', '0174');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01749999', '未知', '未知', '未知', '0174');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01750000', 'WG6101N', 'WG6101N', 'WG6101N', '0175');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01750001', 'WG6600CQN', 'WG6600CQN', 'WG6600CQN', '0175');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01750002', '大巴01', '大巴01', '大巴01', '0175');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01759999', '未知', '未知', '未知', '0175');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01760000', 'VESTA', 'VESTA', 'VESTA', '0176');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01760001', 'XRAY', 'XRAY', 'XRAY', '0176');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01769999', '未知', '未知', '未知', '0176');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01770000', '1', '1', '1', '0177');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01779999', '未知', '未知', '未知', '0177');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780000', 'CX16', 'CX16', 'CX16', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780001', 'CX75', 'CX75', 'CX75', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780002', 'ETYPE', 'ETYPE', 'ETYPE', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780003', 'F.PACE', 'F.PACE', 'F.PACE', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780004', 'FPACE(两驱尊享型)', 'FPACE(两驱尊享型)', 'FPACE(两驱尊享型)', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780005', 'FPACE(四驱高性能限量首发版)', 'FPACE(四驱高性能限量首发版)', 'FPACE(四驱高性能限量首发版)', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780006', 'FTYPE(Project 7 Concept)', 'FTYPE(Project 7 Concept)', 'FTYPE(Project 7 Concept)', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780007', 'FTYPE(R四驱敞篷版)', 'FTYPE(R四驱敞篷版)', 'FTYPE(R四驱敞篷版)', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780008', 'FTYPE(SVR)', 'FTYPE(SVR)', 'FTYPE(SVR)', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780009', 'S.TYPE', 'S.TYPE', 'S.TYPE', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780010', 'XE(基本型)', 'XE(基本型)', 'XE(基本型)', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780011', 'XE(运动款)', 'XE(运动款)', 'XE(运动款)', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780012', 'XF', 'XF', 'XF', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780013', 'XF(2.0版)', 'XF(2.0版)', 'XF(2.0版)', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780014', 'XF(3.0版)', 'XF(3.0版)', 'XF(3.0版)', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780015', 'XJ', 'XJ', 'XJ', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780016', 'XK', 'XK', 'XK', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01780017', 'XTYPE', 'XTYPE', 'XTYPE', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01789999', '未知', '未知', '未知', '0178');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01790000', '44', '44', '44', '0179');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01790001', 'AERO', 'AERO', 'AERO', '0179');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01799999', '未知', '未知', '未知', '0179');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01800000', 'G380', 'G380', 'G380', '0180');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01800001', 'G420', 'G420', 'G420', '0180');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01800002', 'G440', 'G440', 'G440', '0180');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01800003', 'P360', 'P360', 'P360', '0180');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01800004', 'P380', 'P380', 'P380', '0180');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01800005', 'R560', 'R560', 'R560', '0180');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01809999', '未知', '未知', '未知', '0180');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01810000', 'X7', 'X7', 'X7', '0181');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01819999', '未知', '未知', '未知', '0181');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01820000', 'BRZ', 'BRZ', 'BRZ', '0182');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01820001', 'EXIGA', 'EXIGA', 'EXIGA', '0182');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01820002', 'LEVORG', 'LEVORG', 'LEVORG', '0182');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01820003', 'VIZIV', 'VIZIV', 'VIZIV', '0182');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01820004', 'WRX', 'WRX', 'WRX', '0182');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01820005', 'XV', 'XV', 'XV', '0182');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01820006', '傲虎', '傲虎', '傲虎', '0182');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01820007', '力狮', '力狮', '力狮', '0182');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01820008', '森林人', '森林人', '森林人', '0182');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01820009', '森林人(尊贵及纪念版)', '森林人(尊贵及纪念版)', '森林人(尊贵及纪念版)', '0182');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01820010', '翼豹WRX', '翼豹WRX', '翼豹WRX', '0182');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01820011', '翼豹WRX(2.5T STi)', '翼豹WRX(2.5T STi)', '翼豹WRX(2.5T STi)', '0182');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01820012', '驰鹏', '驰鹏', '驰鹏', '0182');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01829999', '未知', '未知', '未知', '0182');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830000', 'CITIGO', 'CITIGO', 'CITIGO', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830001', 'YETI', 'YETI', 'YETI', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830002', 'YETI(进口)', 'YETI(进口)', 'YETI(进口)', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830003', 'Yeti', 'Yeti', 'Yeti', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830004', '昊锐', '昊锐', '昊锐', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830005', '明锐', '明锐', '明锐', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830006', '明锐RS', '明锐RS', '明锐RS', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830007', '昕动(改款)', '昕动(改款)', '昕动(改款)', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830008', '昕锐', '昕锐', '昕锐', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830009', '昕锐(中高配版)', '昕锐(中高配版)', '昕锐(中高配版)', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830010', '昕锐(低配版)', '昕锐(低配版)', '昕锐(低配版)', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830011', '晶锐', '晶锐', '晶锐', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830012', '晶锐(Scout版)', '晶锐(Scout版)', '晶锐(Scout版)', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830013', '晶锐(Sport版)', '晶锐(Sport版)', '晶锐(Sport版)', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830014', '晶锐(中高配版)', '晶锐(中高配版)', '晶锐(中高配版)', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830015', '晶锐(低配版)', '晶锐(低配版)', '晶锐(低配版)', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830016', '晶锐(高配版)', '晶锐(高配版)', '晶锐(高配版)', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830017', '欧雅', '欧雅', '欧雅', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830018', '速尊', '速尊', '速尊', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01830019', '速派', '速派', '速派', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01839999', '未知', '未知', '未知', '0183');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01840000', '卫士', '卫士', '卫士', '0184');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01840001', '揽胜', '揽胜', '揽胜', '0184');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01849999', '未知', '未知', '未知', '0184');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01850000', '凯胜', '凯胜', '凯胜', '0185');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01859999', '未知', '未知', '未知', '0185');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860000', '370Z', '370Z', '370Z', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860001', '370Z(Black Edition)', '370Z(Black Edition)', '370Z(Black Edition)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860002', '370Z(NISMO)', '370Z(NISMO)', '370Z(NISMO)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860003', 'D22', 'D22', 'D22', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860004', 'D22(多功能商用车)', 'D22(多功能商用车)', 'D22(多功能商用车)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860005', 'GTR', 'GTR', 'GTR', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860006', 'GTR(NISMO)', 'GTR(NISMO)', 'GTR(NISMO)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860007', 'GTR(Premium)', 'GTR(Premium)', 'GTR(Premium)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860008', 'INVITATION', 'INVITATION', 'INVITATION', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860009', 'LANNIA蓝鸟', 'LANNIA蓝鸟', 'LANNIA蓝鸟', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860010', 'NV200', 'NV200', 'NV200', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860011', 'NV200(尊贵及尊享型)', 'NV200(尊贵及尊享型)', 'NV200(尊贵及尊享型)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860012', 'NV200(尊贵型)', 'NV200(尊贵型)', 'NV200(尊贵型)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860013', 'NV200(尊雅及豪华型)', 'NV200(尊雅及豪华型)', 'NV200(尊雅及豪华型)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860014', '公爵', '公爵', '公爵', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860015', '凯普斯达', '凯普斯达', '凯普斯达', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860016', '天籁', '天籁', '天籁', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860017', '天籁(公爵)', '天籁(公爵)', '天籁(公爵)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860018', '奇骏', '奇骏', '奇骏', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860019', '奇骏(基本型)', '奇骏(基本型)', '奇骏(基本型)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860020', '奇骏(改装)', '奇骏(改装)', '奇骏(改装)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860021', '帕拉丁', '帕拉丁', '帕拉丁', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860022', '帕拉丁(改装)', '帕拉丁(改装)', '帕拉丁(改装)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860023', '帕拉丁(皮卡)', '帕拉丁(皮卡)', '帕拉丁(皮卡)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860024', '楼兰', '楼兰', '楼兰', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860025', '玛驰', '玛驰', '玛驰', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860026', '玛驰(海外)', '玛驰(海外)', '玛驰(海外)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860027', '碧莲', '碧莲', '碧莲', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860028', '聆风', '聆风', '聆风', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860029', '蓝鸟', '蓝鸟', '蓝鸟', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860030', '蓝鸟(智尊版)', '蓝鸟(智尊版)', '蓝鸟(智尊版)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860031', '西玛', '西玛', '西玛', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860032', '贵士', '贵士', '贵士', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860033', '轩逸', '轩逸', '轩逸', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860034', '轩逸(尊享,智尊型)', '轩逸(尊享,智尊型)', '轩逸(尊享,智尊型)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860035', '轩逸(改装)', '轩逸(改装)', '轩逸(改装)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860036', '轿车老款', '轿车老款', '轿车老款', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860037', '逍客', '逍客', '逍客', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860038', '逍客(海外)(N Sport特别版)', '逍客(海外)(N Sport特别版)', '逍客(海外)(N Sport特别版)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860039', '途乐', '途乐', '途乐', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860040', '途乐(PATROL)', '途乐(PATROL)', '途乐(PATROL)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860041', '阳光', '阳光', '阳光', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860042', '颐达', '颐达', '颐达', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860043', '风度', '风度', '风度', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860044', '骊威', '骊威', '骊威', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860045', '骊威(劲悦版)', '骊威(劲悦版)', '骊威(劲悦版)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860046', '骊威(劲锐版)', '骊威(劲锐版)', '骊威(劲锐版)', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860047', '骏逸', '骏逸', '骏逸', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01860048', '骐达', '骐达', '骐达', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01869999', '未知', '未知', '未知', '0186');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870000', '4', '4', '4', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870001', 'BJ1083VDJFA', 'BJ1083VDJFA', 'BJ1083VDJFA', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870002', 'BJ3163DJPFB', 'BJ3163DJPFB', 'BJ3163DJPFB', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870003', 'BJ3165DJPHA', 'BJ3165DJPHA', 'BJ3165DJPHA', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870004', 'BJ5036V3DB3', 'BJ5036V3DB3', 'BJ5036V3DB3', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870005', 'BJ5143GJB1', 'BJ5143GJB1', 'BJ5143GJB1', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870006', 'ROWOR', 'ROWOR', 'ROWOR', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870007', '中驰', '中驰', '中驰', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870008', '中驰搅拌车', '中驰搅拌车', '中驰搅拌车', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870009', '小卡之星', '小卡之星', '小卡之星', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870010', '小卡之星2', '小卡之星2', '小卡之星2', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870011', '小卡之星5', '小卡之星5', '小卡之星5', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870012', '小卡之星福锐', '小卡之星福锐', '小卡之星福锐', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870013', '小卡之星福锐微卡', '小卡之星福锐微卡', '小卡之星福锐微卡', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870014', '康瑞H2', '康瑞H2', '康瑞H2', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870015', '康瑞H3', '康瑞H3', '康瑞H3', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870016', '康瑞K1', '康瑞K1', '康瑞K1', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870017', '康瑞K2', '康瑞K2', '康瑞K2', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870018', '康瑞五十铃', '康瑞五十铃', '康瑞五十铃', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870019', '欧优', '欧优', '欧优', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870020', '瑞沃2系', '瑞沃2系', '瑞沃2系', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870021', '瑞沃5系', '瑞沃5系', '瑞沃5系', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870022', '瑞沃9', '瑞沃9', '瑞沃9', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870023', '瑞沃II2P25P4110ZY', '瑞沃II2P25P4110ZY', '瑞沃II2P25P4110ZY', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870024', '瑞沃Q9', '瑞沃Q9', '瑞沃Q9', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870025', '赛奥轻卡', '赛奥轻卡', '赛奥轻卡', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870026', '轻卡北京', '轻卡北京', '轻卡北京', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870027', '金刚568轻卡', '金刚568轻卡', '金刚568轻卡', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870028', '金刚588', '金刚588', '金刚588', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870029', '金刚726', '金刚726', '金刚726', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870030', '领航3', '领航3', '领航3', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870031', '领航3(BJ1120VHPFG S)', '领航3(BJ1120VHPFG S)', '领航3(BJ1120VHPFG S)', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870032', '领航小卡', '领航小卡', '领航小卡', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870033', '风驰', '风驰', '风驰', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870034', '驭菱', '驭菱', '驭菱', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870035', '驭菱V5', '驭菱V5', '驭菱V5', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870036', '驭菱VQ1', '驭菱VQ1', '驭菱VQ1', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870037', '驭菱轻卡', '驭菱轻卡', '驭菱轻卡', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01870038', '骁云', '骁云', '骁云', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01879999', '未知', '未知', '未知', '0187');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01880000', 'D102', 'D102', 'D102', '0188');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01880001', 'D202', 'D202', 'D202', '0188');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01880002', 'D206', 'D206', 'D206', '0188');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01880003', 'D301', 'D301', 'D301', '0188');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01880004', 'SF1705PD12', 'SF1705PD12', 'SF1705PD12', '0188');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01880005', 'SF2810P1F2', 'SF2810P1F2', 'SF2810P1F2', '0188');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01880006', '丰顺I系列', '丰顺I系列', '丰顺I系列', '0188');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01880007', '电动车D306', '电动车D306', '电动车D306', '0188');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01880008', '风菱自卸车', '风菱自卸车', '风菱自卸车', '0188');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01880009', '风驰', '风驰', '风驰', '0188');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01880010', '风驰120马力单排栏板轻卡', '风驰120马力单排栏板轻卡', '风驰120马力单排栏板轻卡', '0188');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01880011', '风驰2', '风驰2', '风驰2', '0188');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01880012', '风驰自卸车', '风驰自卸车', '风驰自卸车', '0188');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01889999', '未知', '未知', '未知', '0188');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01890000', 'D750', 'D750', 'D750', '0189');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01890001', 'F150', 'F150', 'F150', '0189');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01890002', '格奥雷重卡', '格奥雷重卡', '格奥雷重卡', '0189');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01899999', '未知', '未知', '未知', '0189');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01900000', 'M50', 'M50', 'M50', '0190');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01900001', 'M70', 'M70', 'M70', '0190');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01900002', 'Q25', 'Q25', 'Q25', '0190');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01900003', 'Q35', 'Q35', 'Q35', '0190');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01900004', '爱迪尔', '爱迪尔', '爱迪尔', '0190');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01900005', '爱迪尔II', '爱迪尔II', '爱迪尔II', '0190');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01900006', '爱迪尔II(改款)', '爱迪尔II(改款)', '爱迪尔II(改款)', '0190');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01900007', '福瑞达', '福瑞达', '福瑞达', '0190');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01900008', '福瑞达(小货)', '福瑞达(小货)', '福瑞达(小货)', '0190');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01900009', '福瑞达(微面)', '福瑞达(微面)', '福瑞达(微面)', '0190');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01900010', '福瑞达K22', '福瑞达K22', '福瑞达K22', '0190');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01900011', '福瑞达M50', '福瑞达M50', '福瑞达M50', '0190');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01900012', '福瑞达M50(改款)', '福瑞达M50(改款)', '福瑞达M50(改款)', '0190');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01900013', '福运(改标)', '福运(改标)', '福运(改标)', '0190');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01909999', '未知', '未知', '未知', '0190');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01910000', '利亚纳', '利亚纳', '利亚纳', '0191');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01910001', '利亚纳A6', '利亚纳A6', '利亚纳A6', '0191');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01919999', '未知', '未知', '未知', '0191');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01920000', 'JNQ6600DK41', 'JNQ6600DK41', 'JNQ6600DK41', '0192');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01929999', '未知', '未知', '未知', '0192');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01930000', 'LIONRegio', 'LIONRegio', 'LIONRegio', '0193');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01930001', 'TGA(430)', 'TGA(430)', 'TGA(430)', '0193');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01930002', 'TGS33', 'TGS33', 'TGS33', '0193');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01930003', 'TGX(480)', 'TGX(480)', 'TGX(480)', '0193');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01939999', '未知', '未知', '未知', '0193');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01940000', '朗世', '朗世', '朗世', '0194');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01949999', '未知', '未知', '未知', '0194');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950000', 'CLARITY', 'CLARITY', 'CLARITY', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950001', 'CRV', 'CRV', 'CRV', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950002', 'CRV(四驱尊贵版)', 'CRV(四驱尊贵版)', 'CRV(四驱尊贵版)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950003', 'CRV(改装)', 'CRV(改装)', 'CRV(改装)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950004', 'CRZ', 'CRZ', 'CRZ', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950005', 'CRZ(改装)', 'CRZ(改装)', 'CRZ(改装)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950006', 'FCV', 'FCV', 'FCV', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950007', 'LEGEND', 'LEGEND', 'LEGEND', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950008', 'NBOX', 'NBOX', 'NBOX', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950009', 'NONE', 'NONE', 'NONE', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950010', 'NSX', 'NSX', 'NSX', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950011', 'PILOT', 'PILOT', 'PILOT', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950012', 'RIDGELINE', 'RIDGELINE', 'RIDGELINE', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950013', 'S2000', 'S2000', 'S2000', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950014', 'STEPWGN(进口)', 'STEPWGN(进口)', 'STEPWGN(进口)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950015', 'SUT', 'SUT', 'SUT', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950016', 'URV', 'URV', 'URV', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950017', 'XRV', 'XRV', 'XRV', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950018', '元素', '元素', '元素', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950019', '冠道', '冠道', '冠道', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950020', '凌派', '凌派', '凌派', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950021', '哥瑞', '哥瑞', '哥瑞', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950022', '奥德赛', '奥德赛', '奥德赛', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950023', '奥德赛(智酷版)', '奥德赛(智酷版)', '奥德赛(智酷版)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950024', '奥德赛(豪华版)', '奥德赛(豪华版)', '奥德赛(豪华版)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950025', '奥德赛(运动版)', '奥德赛(运动版)', '奥德赛(运动版)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950026', '思域', '思域', '思域', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950027', '思域(自动款)', '思域(自动款)', '思域(自动款)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950028', '思域(进口)', '思域(进口)', '思域(进口)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950029', '思迪', '思迪', '思迪', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950030', '思铂睿', '思铂睿', '思铂睿', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950031', '思铂睿(改标)', '思铂睿(改标)', '思铂睿(改标)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950032', '思铭', '思铭', '思铭', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950033', '思铭(改装)', '思铭(改装)', '思铭(改装)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950034', '时韵', '时韵', '时韵', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950035', '杰德', '杰德', '杰德', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950036', '杰德(高配版)', '杰德(高配版)', '杰德(高配版)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950037', '歌诗图', '歌诗图', '歌诗图', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950038', '歌诗图(改装讴歌)', '歌诗图(改装讴歌)', '歌诗图(改装讴歌)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950039', '理念S1(高配版)', '理念S1(高配版)', '理念S1(高配版)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950040', '竞瑞', '竞瑞', '竞瑞', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950041', '缤智', '缤智', '缤智', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950042', '艾力绅', '艾力绅', '艾力绅', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950043', '锋范', '锋范', '锋范', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950044', '雅阁', '雅阁', '雅阁', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950045', '雅阁(尊贵版)', '雅阁(尊贵版)', '雅阁(尊贵版)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950046', '雅阁(新春限量版)', '雅阁(新春限量版)', '雅阁(新春限量版)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950047', '雅阁(智尊版)', '雅阁(智尊版)', '雅阁(智尊版)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950048', '雅阁(精典版)', '雅阁(精典版)', '雅阁(精典版)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950049', '飞度', '飞度', '飞度', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950050', '飞度(三厢)', '飞度(三厢)', '飞度(三厢)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01950051', '飞度(改装)', '飞度(改装)', '飞度(改装)', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01959999', '未知', '未知', '未知', '0195');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01960000', 'MKC', 'MKC', 'MKC', '0196');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01960001', 'MKS', 'MKS', 'MKS', '0196');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01960002', 'MKT', 'MKT', 'MKT', '0196');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01960003', 'MKX', 'MKX', 'MKX', '0196');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01960004', 'MKZ', 'MKZ', 'MKZ', '0196');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01960005', '城市', '城市', '城市', '0196');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01960006', '大陆', '大陆', '大陆', '0196');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01960007', '领航员', '领航员', '领航员', '0196');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01969999', '未知', '未知', '未知', '0196');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970000', '107', '107', '107', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970001', '108', '108', '108', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970002', '2008', '2008', '2008', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970003', '2008(基础版)', '2008(基础版)', '2008(基础版)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970004', '206', '206', '206', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970005', '207', '207', '207', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970006', '207(进口)', '207(进口)', '207(进口)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970007', '208', '208', '208', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970008', '3008', '3008', '3008', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970009', '3008(进口)', '3008(进口)', '3008(进口)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970010', '301', '301', '301', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970011', '301(手动舒适版)', '301(手动舒适版)', '301(手动舒适版)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970012', '301(自动豪华版)', '301(自动豪华版)', '301(自动豪华版)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970013', '307', '307', '307', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970014', '308', '308', '308', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970015', '308(进口)', '308(进口)', '308(进口)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970016', '308(进口)(GTI)', '308(进口)(GTI)', '308(进口)(GTI)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970017', '308(进口)(R HVbrid Concept)', '308(进口)(R HVbrid Concept)', '308(进口)(R HVbrid Concept)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970018', '308(进口)(SW)', '308(进口)(SW)', '308(进口)(SW)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970019', '308(进口)(两厢基本型)', '308(进口)(两厢基本型)', '308(进口)(两厢基本型)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970020', '308(进口)(自动豪华型)', '308(进口)(自动豪华型)', '308(进口)(自动豪华型)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970021', '308S', '308S', '308S', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970022', '4008', '4008', '4008', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970023', '4008(豪华GT版)', '4008(豪华GT版)', '4008(豪华GT版)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970024', '4008(豪华版)', '4008(豪华版)', '4008(豪华版)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970025', '4008(进口)', '4008(进口)', '4008(进口)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970026', '406(进口)', '406(进口)', '406(进口)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970027', '407', '407', '407', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970028', '408', '408', '408', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970029', '5008(豪华GT版)', '5008(豪华GT版)', '5008(豪华GT版)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970030', '508', '508', '508', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970031', '508(进口)', '508(进口)', '508(进口)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970032', '807(进口)', '807(进口)', '807(进口)', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970033', 'ION', 'ION', 'ION', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970034', 'RCZ', 'RCZ', 'RCZ', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01970035', 'SXC', 'SXC', 'SXC', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01979999', '未知', '未知', '未知', '0197');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01980000', 'HQG6520EXC5', 'HQG6520EXC5', 'HQG6520EXC5', '0198');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01980001', 'HQG6660EN5', 'HQG6660EN5', 'HQG6660EN5', '0198');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01980002', 'HQG6750EXC4', 'HQG6750EXC4', 'HQG6750EXC4', '0198');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01989999', '未知', '未知', '未知', '0198');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990000', 'AGILA', 'AGILA', 'AGILA', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990001', 'AMPERA', 'AMPERA', 'AMPERA', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990002', 'Adam', 'Adam', 'Adam', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990003', 'AdamRockS', 'AdamRockS', 'AdamRockS', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990004', 'COMBO', 'COMBO', 'COMBO', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990005', 'CORSA', 'CORSA', 'CORSA', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990006', 'CORSA(三门版)', 'CORSA(三门版)', 'CORSA(三门版)', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990007', 'KARL', 'KARL', 'KARL', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990008', 'MONZA', 'MONZA', 'MONZA', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990009', '威达', '威达', '威达', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990010', '安德拉', '安德拉', '安德拉', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990011', '欧美佳', '欧美佳', '欧美佳', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990012', '英速亚', '英速亚', '英速亚', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990013', '赛飞利', '赛飞利', '赛飞利', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990014', '雅特', '雅特', '雅特', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990015', '雅特(GTC)', '雅特(GTC)', '雅特(GTC)', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990016', '雅特GTC', '雅特GTC', '雅特GTC', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01990017', '麦瑞纳', '麦瑞纳', '麦瑞纳', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('01999999', '未知', '未知', '未知', '0199');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000000', 'E5', 'E5', 'E5', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000001', 'E6', 'E6', 'E6', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000002', 'F0', 'F0', 'F0', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000003', 'F0(改装)', 'F0(改装)', 'F0(改装)', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000004', 'F3', 'F3', 'F3', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000005', 'F3R', 'F3R', 'F3R', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000006', 'F6', 'F6', 'F6', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000007', 'G3', 'G3', 'G3', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000008', 'G5', 'G5', 'G5', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000009', 'G6', 'G6', 'G6', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000010', 'L3', 'L3', 'L3', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000011', 'M6', 'M6', 'M6', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000012', 'S6', 'S6', 'S6', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000013', 'S6(中网改装)', 'S6(中网改装)', 'S6(中网改装)', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000014', 'S6(改装)', 'S6(改装)', 'S6(改装)', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000015', 'S6(白金手动尊贵版)', 'S6(白金手动尊贵版)', 'S6(白金手动尊贵版)', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000016', 'S7', 'S7', 'S7', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000017', 'T5纯电动轻卡', 'T5纯电动轻卡', 'T5纯电动轻卡', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000018', '元', '元', '元', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000019', '唐', '唐', '唐', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000020', '商', '商', '商', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000021', '宋', '宋', '宋', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000022', '宋(改装保险杠)', '宋(改装保险杠)', '宋(改装保险杠)', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000023', '思锐', '思锐', '思锐', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000024', '福莱尔(标准型)', '福莱尔(标准型)', '福莱尔(标准型)', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000025', '福莱尔(豪华型)', '福莱尔(豪华型)', '福莱尔(豪华型)', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000026', '秦', '秦', '秦', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02000027', '速锐', '速锐', '速锐', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02009999', '未知', '未知', '未知', '0200');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02010000', 'M3', 'M3', 'M3', '0201');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02010001', 'T3', 'T3', 'T3', '0201');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02010002', 'T5', 'T5', 'T5', '0201');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02019999', '未知', '未知', '未知', '0201');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02020000', 'A380', 'A380', 'A380', '0202');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02020001', '五星', '五星', '五星', '0202');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02020002', '猎鹰', '猎鹰', '猎鹰', '0202');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02029999', '未知', '未知', '未知', '0202');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02030000', '伊美', '伊美', '伊美', '0203');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02039999', '未知', '未知', '未知', '0203');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02040000', 'X5', 'X5', 'X5', '0204');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02040001', 'X7', 'X7', 'X7', '0204');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02049999', '未知', '未知', '未知', '0204');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050000', 'A60', 'A60', 'A60', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050001', 'HFC6108H', 'HFC6108H', 'HFC6108H', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050002', 'HFC6602KF', 'HFC6602KF', 'HFC6602KF', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050003', 'HK6108H', 'HK6108H', 'HK6108H', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050004', 'HK6581KY4型客车', 'HK6581KY4型客车', 'HK6581KY4型客车', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050005', 'HK6608K', 'HK6608K', 'HK6608K', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050006', 'HK6801C1', 'HK6801C1', 'HK6801C1', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050007', 'HK6818K', 'HK6818K', 'HK6818K', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050008', 'IEV', 'IEV', 'IEV', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050009', 'IEV6S', 'IEV6S', 'IEV6S', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050010', 'iEV5', 'iEV5', 'iEV5', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050011', '凌铃', '凌铃', '凌铃', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050012', '同悦', '同悦', '同悦', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050013', '同悦RS(CROSS版)', '同悦RS(CROSS版)', '同悦RS(CROSS版)', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050014', '和悦', '和悦', '和悦', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050015', '和悦A13', '和悦A13', '和悦A13', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050016', '和悦A30', '和悦A30', '和悦A30', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050017', '和悦RS', '和悦RS', '和悦RS', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050018', '好微', '好微', '好微', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050019', '好运轻卡', '好运轻卡', '好运轻卡', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050020', '威铃', '威铃', '威铃', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050021', '威铃II', '威铃II', '威铃II', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050022', '安驰K3', '安驰K3', '安驰K3', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050023', '安驰K5', '安驰K5', '安驰K5', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050024', '安驰瑞玲', '安驰瑞玲', '安驰瑞玲', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050025', '宾悦', '宾悦', '宾悦', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050026', '宾悦(改款)', '宾悦(改款)', '宾悦(改款)', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050027', '帅铃II', '帅铃II', '帅铃II', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050028', '帅铃T6', '帅铃T6', '帅铃T6', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050029', '康玲微卡(X3)', '康玲微卡(X3)', '康玲微卡(X3)', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050030', '康铃(024)', '康铃(024)', '康铃(024)', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050031', '康铃808系列', '康铃808系列', '康铃808系列', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050032', '康铃808载货车', '康铃808载货车', '康铃808载货车', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050033', '康铃H5轻卡', '康铃H5轻卡', '康铃H5轻卡', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050034', '康铃K5', '康铃K5', '康铃K5', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050035', '康铃K系列', '康铃K系列', '康铃K系列', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050036', '康铃X3', '康铃X3', '康铃X3', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050037', '康铃X5', '康铃X5', '康铃X5', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050038', '康铃鼎力', '康铃鼎力', '康铃鼎力', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050039', '悦悦', '悦悦', '悦悦', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050040', '新帅铃', '新帅铃', '新帅铃', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050041', '新帅铃H', '新帅铃H', '新帅铃H', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050042', '新康玲J6', '新康玲J6', '新康玲J6', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050043', '星锐', '星锐', '星锐', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050044', '星锐(改奔驰中网)', '星锐(改奔驰中网)', '星锐(改奔驰中网)', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050045', '格尔发A3', '格尔发A3', '格尔发A3', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050046', '格尔发A3W重卡牵引车', '格尔发A3W重卡牵引车', '格尔发A3W重卡牵引车', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050047', '格尔发A3重卡', '格尔发A3重卡', '格尔发A3重卡', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050048', '格尔发A5(大货)', '格尔发A5(大货)', '格尔发A5(大货)', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050049', '格尔发A5(特大货)', '格尔发A5(特大货)', '格尔发A5(特大货)', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050050', '格尔发A5L中卡', '格尔发A5L中卡', '格尔发A5L中卡', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050051', '格尔发A5L中卡仓栅载货车', '格尔发A5L中卡仓栅载货车', '格尔发A5L中卡仓栅载货车', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050052', '格尔发A6LII中卡', '格尔发A6LII中卡', '格尔发A6LII中卡', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050053', '格尔发E', '格尔发E', '格尔发E', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050054', '格尔发H', '格尔发H', '格尔发H', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050055', '格尔发H02', '格尔发H02', '格尔发H02', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050056', '格尔发K3牵引车', '格尔发K3牵引车', '格尔发K3牵引车', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050057', '格尔发K5L', '格尔发K5L', '格尔发K5L', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050058', '格尔发K6L中卡仓栅式载货车', '格尔发K6L中卡仓栅式载货车', '格尔发K6L中卡仓栅式载货车', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050059', '格尔发K7', '格尔发K7', '格尔发K7', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050060', '格尔发中卡', '格尔发中卡', '格尔发中卡', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050061', '瑞玲', '瑞玲', '瑞玲', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050062', '瑞铃', '瑞铃', '瑞铃', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050063', '瑞风', '瑞风', '瑞风', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050064', '瑞风(祥和版)', '瑞风(祥和版)', '瑞风(祥和版)', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050065', '瑞风A33', '瑞风A33', '瑞风A33', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050066', '瑞风A60', '瑞风A60', '瑞风A60', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050067', '瑞风M2', '瑞风M2', '瑞风M2', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050068', '瑞风M3', '瑞风M3', '瑞风M3', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050069', '瑞风M4', '瑞风M4', '瑞风M4', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050070', '瑞风M5', '瑞风M5', '瑞风M5', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050071', '瑞风S2', '瑞风S2', '瑞风S2', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050072', '瑞风S2MINI', '瑞风S2MINI', '瑞风S2MINI', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050073', '瑞风S3', '瑞风S3', '瑞风S3', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050074', '瑞风S5', '瑞风S5', '瑞风S5', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050075', '瑞风S7', '瑞风S7', '瑞风S7', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050076', '瑞鹰', '瑞鹰', '瑞鹰', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050077', '皮卡', '皮卡', '皮卡', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050078', '箱式运输车', '箱式运输车', '箱式运输车', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050079', '骏铃E7', '骏铃E7', '骏铃E7', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050080', '骏铃II', '骏铃II', '骏铃II', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050081', '骏铃M', '骏铃M', '骏铃M', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050082', '骏铃V6', '骏铃V6', '骏铃V6', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02050083', '鼎力', '鼎力', '鼎力', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02059999', '未知', '未知', '未知', '0205');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02060000', '新类', '新类', '新类', '0206');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02069999', '未知', '未知', '未知', '0206');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070000', 'E100', 'E100', 'E100', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070001', 'E200', 'E200', 'E200', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070002', 'JX6606VDFA', 'JX6606VDFA', 'JX6606VDFA', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070003', 'NKR', 'NKR', 'NKR', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070004', '凯运', '凯运', '凯运', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070005', '凯锐', '凯锐', '凯锐', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070006', '域虎3', '域虎3', '域虎3', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070007', '宝典', '宝典', '宝典', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070008', '宝典(改装)', '宝典(改装)', '宝典(改装)', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070009', '宝典PLUS', '宝典PLUS', '宝典PLUS', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070010', '宝威', '宝威', '宝威', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070011', '新凯运', '新凯运', '新凯运', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070012', '新款凯锐', '新款凯锐', '新款凯锐', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070013', '皮卡01', '皮卡01', '皮卡01', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070014', '经典顺达', '经典顺达', '经典顺达', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070015', '顺达', '顺达', '顺达', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070016', '驭胜', '驭胜', '驭胜', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070017', '驭胜S330', '驭胜S330', '驭胜S330', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070018', '驭胜S350', '驭胜S350', '驭胜S350', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070019', '骐铃T3', '骐铃T3', '骐铃T3', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070020', '骐铃T5', '骐铃T5', '骐铃T5', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02070021', '骐铃T7', '骐铃T7', '骐铃T7', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02079999', '未知', '未知', '未知', '0207');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02080000', '远威准重卡', '远威准重卡', '远威准重卡', '0208');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02080001', '远威重卡', '远威重卡', '远威重卡', '0208');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02089999', '未知', '未知', '未知', '0208');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02090000', 'ADM', 'ADM', 'ADM', '0209');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02090001', 'ASTRA', 'ASTRA', 'ASTRA', '0209');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02090002', 'CORSA', 'CORSA', 'CORSA', '0209');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02090003', 'INSIGNIA', 'INSIGNIA', 'INSIGNIA', '0209');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02099999', '未知', '未知', '未知', '0209');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100000', 'C30', 'C30', 'C30', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100001', 'C70', 'C70', 'C70', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100002', 'C70(Convertible)', 'C70(Convertible)', 'C70(Convertible)', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100003', 'FH16', 'FH16', 'FH16', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100004', 'S40', 'S40', 'S40', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100005', 'S60', 'S60', 'S60', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100006', 'S60L', 'S60L', 'S60L', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100007', 'S80', 'S80', 'S80', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100008', 'S80L', 'S80L', 'S80L', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100009', 'S90', 'S90', 'S90', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100010', 'V40', 'V40', 'V40', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100011', 'V60', 'V60', 'V60', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100012', 'V60(运动版)', 'V60(运动版)', 'V60(运动版)', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100013', 'XC60', 'XC60', 'XC60', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100014', 'XC60(智雅版)', 'XC60(智雅版)', 'XC60(智雅版)', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100015', 'XC60(进口)', 'XC60(进口)', 'XC60(进口)', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100016', 'XC90', 'XC90', 'XC90', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02100017', 'XW6122DC', 'XW6122DC', 'XW6122DC', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02109999', '未知', '未知', '未知', '0210');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02110000', 'FE', 'FE', 'FE', '0211');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02110001', 'FE新类', 'FE新类', 'FE新类', '0211');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02110002', 'FH', 'FH', 'FH', '0211');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02110003', 'FH新', 'FH新', 'FH新', '0211');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02110004', 'FL', 'FL', 'FL', '0211');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02110005', 'FM', 'FM', 'FM', '0211');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02110006', 'FM(400马力4×2牵引车)', 'FM(400马力4×2牵引车)', 'FM(400马力4×2牵引车)', '0211');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02110007', 'FM400', 'FM400', 'FM400', '0211');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02110008', 'FMX420', 'FMX420', 'FMX420', '0211');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02110009', 'FMX440', 'FMX440', 'FMX440', '0211');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02110010', 'FM重卡', 'FM重卡', 'FM重卡', '0211');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02119999', '未知', '未知', '未知', '0211');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02120000', '456', '456', '456', '0212');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02120001', '458', '458', '458', '0212');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02120002', '488', '488', '488', '0212');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02120003', '575M', '575M', '575M', '0212');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02120004', '599', '599', '599', '0212');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02120005', '612', '612', '612', '0212');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02120006', 'CALIFORNIAT', 'CALIFORNIAT', 'CALIFORNIAT', '0212');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02120007', 'F12', 'F12', 'F12', '0212');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02120008', 'F430', 'F430', 'F430', '0212');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02120009', 'FF', 'FF', 'FF', '0212');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02129999', '未知', '未知', '未知', '0212');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02130000', 'T7', 'T7', 'T7', '0213');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02130001', 'T9', 'T9', 'T9', '0213');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02139999', '未知', '未知', '未知', '0213');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140000', 'H4E(KLQ5020XXYEV5)', 'H4E(KLQ5020XXYEV5)', 'H4E(KLQ5020XXYEV5)', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140001', 'H5C', 'H5C', 'H5C', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140002', 'H5V', 'H5V', 'H5V', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140003', 'H6C', 'H6C', 'H6C', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140004', 'H7V', 'H7V', 'H7V', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140005', 'KLQ6112HAE41', 'KLQ6112HAE41', 'KLQ6112HAE41', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140006', 'KLQ6112HDE31', 'KLQ6112HDE31', 'KLQ6112HDE31', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140007', 'KLQ6116TE3', 'KLQ6116TE3', 'KLQ6116TE3', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140008', 'KLQ6122B', 'KLQ6122B', 'KLQ6122B', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140009', 'KLQ6122BA', 'KLQ6122BA', 'KLQ6122BA', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140010', 'KLQ6125D', 'KLQ6125D', 'KLQ6125D', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140011', 'KLQ6125HAC51', 'KLQ6125HAC51', 'KLQ6125HAC51', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140012', 'KLQ6129KAE51', 'KLQ6129KAE51', 'KLQ6129KAE51', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140013', 'KLQ6145B', 'KLQ6145B', 'KLQ6145B', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140014', 'KLQ6608D1', 'KLQ6608D1', 'KLQ6608D1', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140015', 'KLQ6702E4', 'KLQ6702E4', 'KLQ6702E4', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140016', 'KLQ6759A', 'KLQ6759A', 'KLQ6759A', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140017', 'KLQ6759AE4', 'KLQ6759AE4', 'KLQ6759AE4', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140018', 'KLQ6770G', 'KLQ6770G', 'KLQ6770G', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140019', 'KLQ6796C', 'KLQ6796C', 'KLQ6796C', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140020', 'KLQ6798', 'KLQ6798', 'KLQ6798', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140021', 'KLQ6856KQC51', 'KLQ6856KQC51', 'KLQ6856KQC51', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140022', 'KLQ6858QE4', 'KLQ6858QE4', 'KLQ6858QE4', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140023', '御骏', '御骏', '御骏', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140024', '校车', '校车', '校车', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02140025', '龙威', '龙威', '龙威', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02149999', '未知', '未知', '未知', '0214');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150000', '3', '3', '3', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150001', 'M3', 'M3', 'M3', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150002', 'M3(标准型,精英型)', 'M3(标准型,精英型)', 'M3(标准型,精英型)', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150003', 'M3(舒适型,旗舰型)', 'M3(舒适型,旗舰型)', 'M3(舒适型,旗舰型)', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150004', 'M6', 'M6', 'M6', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150005', 'M8', 'M8', 'M8', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150006', 'ME', 'ME', 'ME', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150007', 'S5', 'S5', 'S5', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150008', 'S5YOUNG', 'S5YOUNG', 'S5YOUNG', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150009', 'S7', 'S7', 'S7', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150010', 'S7(改款)', 'S7(改款)', 'S7(改款)', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150011', 'V70', 'V70', 'V70', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150012', '丘比特', '丘比特', '丘比特', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150013', '普力马', '普力马', '普力马', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150014', '欢动', '欢动', '欢动', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150015', '海福星', '海福星', '海福星', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150016', '海马S7', '海马S7', '海马S7', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150017', '海马王子', '海马王子', '海马王子', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150018', '爱尚', '爱尚', '爱尚', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150019', '白马王子', '白马王子', '白马王子', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150020', '福仕达福卡', '福仕达福卡', '福仕达福卡', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150021', '福仕达鸿达', '福仕达鸿达', '福仕达鸿达', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150022', '福美来', '福美来', '福美来', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150023', '福美来(1.5T版)', '福美来(1.5T版)', '福美来(1.5T版)', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150024', '福美来(改款)', '福美来(改款)', '福美来(改款)', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150025', '福美来(经典版)', '福美来(经典版)', '福美来(经典版)', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150026', '福美来M5', '福美来M5', '福美来M5', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150027', '福美来MPV', '福美来MPV', '福美来MPV', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150028', '青蛙王子', '青蛙王子', '青蛙王子', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150029', '骑士(中高配版)', '骑士(中高配版)', '骑士(中高配版)', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150030', '骑士(低配版)', '骑士(低配版)', '骑士(低配版)', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02150031', '骑士(智能领航)', '骑士(智能领航)', '骑士(智能领航)', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02159999', '未知', '未知', '未知', '0215');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02160000', '727', '727', '727', '0216');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02160001', '737', '737', '737', '0216');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02160002', 'G3', 'G3', 'G3', '0216');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02160003', 'G5', 'G5', 'G5', '0216');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02169999', '未知', '未知', '未知', '0216');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02170000', 'MD6601KH', 'MD6601KH', 'MD6601KH', '0217');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02179999', '未知', '未知', '未知', '0217');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02180000', 'MODEL3', 'MODEL3', 'MODEL3', '0218');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02180001', 'MODELS', 'MODELS', 'MODELS', '0218');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02180002', 'MODELX', 'MODELX', 'MODELX', '0218');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02180003', 'ROADSTER', 'ROADSTER', 'ROADSTER', '0218');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02189999', '未知', '未知', '未知', '0218');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02190000', '6481(改三菱标)', '6481(改三菱标)', '6481(改三菱标)', '0219');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02190001', 'CS10', 'CS10', 'CS10', '0219');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02190002', 'CS6', 'CS6', 'CS6', '0219');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02190003', 'CS7', 'CS7', 'CS7', '0219');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02190004', 'CT5', 'CT5', 'CT5', '0219');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02190005', 'Q6', 'Q6', 'Q6', '0219');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02190006', '猎豹6481', '猎豹6481', '猎豹6481', '0219');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02190007', '飞扬皮卡', '飞扬皮卡', '飞扬皮卡', '0219');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02190008', '飞腾', '飞腾', '飞腾', '0219');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02190009', '飞腾(经典版)', '飞腾(经典版)', '飞腾(经典版)', '0219');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02190010', '飞腾C5', '飞腾C5', '飞腾C5', '0219');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02190011', '飞铃皮卡', '飞铃皮卡', '飞铃皮卡', '0219');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02190012', '骐菱', '骐菱', '骐菱', '0219');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02190013', '黑金刚', '黑金刚', '黑金刚', '0219');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02190014', '黑金刚(改三菱标)', '黑金刚(改三菱标)', '黑金刚(改三菱标)', '0219');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02199999', '未知', '未知', '未知', '0219');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02200000', 'COUPE', 'COUPE', 'COUPE', '0220');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02200001', 'GRANCABRIO', 'GRANCABRIO', 'GRANCABRIO', '0220');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02200002', 'Ghibli', 'Ghibli', 'Ghibli', '0220');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02200003', 'GranTurismo', 'GranTurismo', 'GranTurismo', '0220');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02200004', 'Levante', 'Levante', 'Levante', '0220');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02200005', 'MC12', 'MC12', 'MC12', '0220');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02200006', 'Spyder', 'Spyder', 'Spyder', '0220');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02200007', '总裁', '总裁', '总裁', '0220');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02209999', '未知', '未知', '未知', '0220');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210000', 'Genesisg80(Sport)', 'Genesisg80(Sport)', 'Genesisg80(Sport)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210001', 'Genesisg80(基本款)', 'Genesisg80(基本款)', 'Genesisg80(基本款)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210002', 'Genesisg90', 'Genesisg90', 'Genesisg90', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210003', 'HB20', 'HB20', 'HB20', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210004', 'HK6124AM1', 'HK6124AM1', 'HK6124AM1', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210005', 'HK6900', 'HK6900', 'HK6900', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210006', 'NUVIS', 'NUVIS', 'NUVIS', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210007', 'R8', 'R8', 'R8', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210008', 'TUCCON', 'TUCCON', 'TUCCON', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210009', 'ZY6710A', 'ZY6710A', 'ZY6710A', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210010', 'i30', 'i30', 'i30', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210011', 'ix25', 'ix25', 'ix25', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210012', 'ix35', 'ix35', 'ix35', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210013', 'ix35(改装中网)', 'ix35(改装中网)', 'ix35(改装中网)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210014', '伊兰特', '伊兰特', '伊兰特', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210015', '伊兰特(两厢)', '伊兰特(两厢)', '伊兰特(两厢)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210016', '伊兰特(改装中网)', '伊兰特(改装中网)', '伊兰特(改装中网)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210017', '全新胜达', '全新胜达', '全新胜达', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210018', '全新胜达(进口)', '全新胜达(进口)', '全新胜达(进口)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210019', '劳恩斯', '劳恩斯', '劳恩斯', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210020', '劳恩斯(进口)', '劳恩斯(进口)', '劳恩斯(进口)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210021', '劳恩斯酷派', '劳恩斯酷派', '劳恩斯酷派', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210022', '名图', '名图', '名图', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210023', '名驭', '名驭', '名驭', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210024', '君爵', '君爵', '君爵', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210025', '圣达菲(进口)', '圣达菲(进口)', '圣达菲(进口)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210026', '大巴WK6102', '大巴WK6102', '大巴WK6102', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210027', '康恩迪', '康恩迪', '康恩迪', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210028', '御翔', '御翔', '御翔', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210029', '悦动', '悦动', '悦动', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210030', '悦动(手动)', '悦动(手动)', '悦动(手动)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210031', '悦动(改款)', '悦动(改款)', '悦动(改款)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210032', '悦纳', '悦纳', '悦纳', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210033', '悦纳RV', '悦纳RV', '悦纳RV', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210034', '捷恩斯', '捷恩斯', '捷恩斯', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210035', '朗动', '朗动', '朗动', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210036', '格越', '格越', '格越', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210037', '格锐', '格锐', '格锐', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210038', '瑞奕', '瑞奕', '瑞奕', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210039', '瑞纳', '瑞纳', '瑞纳', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210040', '瑞纳(改装)', '瑞纳(改装)', '瑞纳(改装)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210041', '索纳塔', '索纳塔', '索纳塔', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210042', '索纳塔(进口)', '索纳塔(进口)', '索纳塔(进口)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210043', '索纳塔(进口基本型)', '索纳塔(进口基本型)', '索纳塔(进口基本型)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210044', '索纳塔九', '索纳塔九', '索纳塔九', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210045', '索纳塔九(中配版)', '索纳塔九(中配版)', '索纳塔九(中配版)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210046', '索纳塔九(舒适智能型)', '索纳塔九(舒适智能型)', '索纳塔九(舒适智能型)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210047', '索纳塔八', '索纳塔八', '索纳塔八', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210048', '索纳塔八(改装)', '索纳塔八(改装)', '索纳塔八(改装)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210049', '维拉克斯', '维拉克斯', '维拉克斯', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210050', '美佳', '美佳', '美佳', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210051', '胜达', '胜达', '胜达', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210052', '辉翼H1', '辉翼H1', '辉翼H1', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210053', '途胜', '途胜', '途胜', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210054', '途胜(改装)', '途胜(改装)', '途胜(改装)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210055', '酷派', '酷派', '酷派', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210056', '酷派FX', '酷派FX', '酷派FX', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210057', '雅尊', '雅尊', '雅尊', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210058', '雅科仕(普通标)', '雅科仕(普通标)', '雅科仕(普通标)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210059', '雅科仕(立标)', '雅科仕(立标)', '雅科仕(立标)', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210060', '雅绅特', '雅绅特', '雅绅特', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210061', '领动', '领动', '领动', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210062', '领翔', '领翔', '领翔', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02210063', '飞思', '飞思', '飞思', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02219999', '未知', '未知', '未知', '0221');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02220000', 'S1', 'S1', 'S1', '0222');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02229999', '未知', '未知', '未知', '0222');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02230000', '电动', '电动', '电动', '0223');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02239999', '未知', '未知', '未知', '0223');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02240000', 'G3', 'G3', 'G3', '0224');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02240001', 'G5', 'G5', 'G5', '0224');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02240002', 'G6', 'G6', 'G6', '0224');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02240003', 'M1', 'M1', 'M1', '0224');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02240004', 'M5', 'M5', 'M5', '0224');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02240005', 'M5(自动豪华型)', 'M5(自动豪华型)', 'M5(自动豪华型)', '0224');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02240006', 'X1', 'X1', 'X1', '0224');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02240007', 'X5', 'X5', 'X5', '0224');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02249999', '未知', '未知', '未知', '0224');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02250000', 'SWB6120', 'SWB6120', 'SWB6120', '0225');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02259999', '未知', '未知', '未知', '0225');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02260000', 'SLK6840F5G3', 'SLK6840F5G3', 'SLK6840F5G3', '0226');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02260001', '大巴01', '大巴01', '大巴01', '0226');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02260002', '大巴02', '大巴02', '大巴02', '0226');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02269999', '未知', '未知', '未知', '0226');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270000', '001', '001', '001', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270001', '12', '12', '12', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270002', 'DJ01', 'DJ01', 'DJ01', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270003', 'RT01', 'RT01', 'RT01', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270004', 'U', 'U', 'U', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270005', '东方曼', '东方曼', '东方曼', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270006', '东风001', '东风001', '东风001', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270007', '丽颖三轮02', '丽颖三轮02', '丽颖三轮02', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270008', '丽颖三轮03', '丽颖三轮03', '丽颖三轮03', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270009', '丽颖四轮', '丽颖四轮', '丽颖四轮', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270010', '乐唯V2', '乐唯V2', '乐唯V2', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270011', '乐唯V3S', '乐唯V3S', '乐唯V3S', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270012', '乐唯V6', '乐唯V6', '乐唯V6', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270013', '五环龙X1', '五环龙X1', '五环龙X1', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270014', '五羊01', '五羊01', '五羊01', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270015', '代步车01', '代步车01', '代步车01', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270016', '代步车02', '代步车02', '代步车02', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270017', '代步车03', '代步车03', '代步车03', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270018', '代步车04', '代步车04', '代步车04', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270019', '代步车05', '代步车05', '代步车05', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270020', '代步车06', '代步车06', '代步车06', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270021', '代步车07', '代步车07', '代步车07', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270022', '代步车08', '代步车08', '代步车08', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270023', '代步车09', '代步车09', '代步车09', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270024', '代步车10', '代步车10', '代步车10', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270025', '仿JEEP', '仿JEEP', '仿JEEP', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270026', '仿QQ', '仿QQ', '仿QQ', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270027', '仿东风', '仿东风', '仿东风', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270028', '仿五菱01', '仿五菱01', '仿五菱01', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270029', '仿五菱02', '仿五菱02', '仿五菱02', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270030', '仿四轮', '仿四轮', '仿四轮', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270031', '仿宝马', '仿宝马', '仿宝马', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270032', '仿牛头', '仿牛头', '仿牛头', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270033', '仿福特', '仿福特', '仿福特', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270034', '仿胜达01', '仿胜达01', '仿胜达01', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270035', '众新E6', '众新E6', '众新E6', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270036', '众新E7', '众新E7', '众新E7', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270037', '兴达01', '兴达01', '兴达01', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270038', '冠航', '冠航', '冠航', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270039', '凯玛利K50', '凯玛利K50', '凯玛利K50', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270040', '嘉远01', '嘉远01', '嘉远01', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270041', '四兄弟', '四兄弟', '四兄弟', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270042', '大U', '大U', '大U', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270043', '大V', '大V', '大V', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270044', '大名', '大名', '大名', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270045', '大名02', '大名02', '大名02', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270046', '大阳', '大阳', '大阳', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270047', '威龙灵龙310', '威龙灵龙310', '威龙灵龙310', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270048', '宏宝莱01', '宏宝莱01', '宏宝莱01', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270049', '宗申(X6米兰)', '宗申(X6米兰)', '宗申(X6米兰)', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270050', '宝雅童年', '宝雅童年', '宝雅童年', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270051', '富平飞虎', '富平飞虎', '富平飞虎', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270052', '建设01', '建设01', '建设01', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270053', '御捷A260', '御捷A260', '御捷A260', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270054', '御捷C300', '御捷C300', '御捷C300', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270055', '御捷E330', '御捷E330', '御捷E330', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270056', '御捷E捷', '御捷E捷', '御捷E捷', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270057', '御捷S25', '御捷S25', '御捷S25', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270058', '御捷SUV', '御捷SUV', '御捷SUV', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270059', '微米E28', '微米E28', '微米E28', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270060', '恒阔四轮01', '恒阔四轮01', '恒阔四轮01', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270061', '悦顺梦想', '悦顺梦想', '悦顺梦想', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270062', '无格栅', '无格栅', '无格栅', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270063', '时风D101', '时风D101', '时风D101', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270064', '时风D207', '时风D207', '时风D207', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270065', '时风D208', '时风D208', '时风D208', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270066', '时风D305', '时风D305', '时风D305', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270067', '时风D306', '时风D306', '时风D306', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270068', '时风D307', '时风D307', '时风D307', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270069', '时风D501', '时风D501', '时风D501', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270070', '昌佳01', '昌佳01', '昌佳01', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270071', '昌佳02', '昌佳02', '昌佳02', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270072', '昌佳03', '昌佳03', '昌佳03', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270073', '易咖X1', '易咖X1', '易咖X1', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270074', '易咖X3', '易咖X3', '易咖X3', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270075', '易咖X5', '易咖X5', '易咖X5', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270076', '易咖灵动', '易咖灵动', '易咖灵动', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270077', '智轩智腾', '智轩智腾', '智轩智腾', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270078', '欧派合家欢', '欧派合家欢', '欧派合家欢', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270079', '欧陆汽车路虎', '欧陆汽车路虎', '欧陆汽车路虎', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270080', '比1', '比1', '比1', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270081', '比德文02', '比德文02', '比德文02', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270082', '比德文M3', '比德文M3', '比德文M3', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270083', '比德文M6', '比德文M6', '比德文M6', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270084', '永冠', '永冠', '永冠', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270085', '永冠02', '永冠02', '永冠02', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270086', '汉唐汉东A3', '汉唐汉东A3', '汉唐汉东A3', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270087', '海宝Q3', '海宝Q3', '海宝Q3', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270088', '瑞易01', '瑞易01', '瑞易01', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270089', '瑞易V8', '瑞易V8', '瑞易V8', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270090', '福利来', '福利来', '福利来', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270091', '福田01', '福田01', '福田01', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270092', '福莱沃', '福莱沃', '福莱沃', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270093', '福莱沃天王星', '福莱沃天王星', '福莱沃天王星', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270094', '福莱沃小福星', '福莱沃小福星', '福莱沃小福星', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270095', '红L', '红L', '红L', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270096', '舜泰梦600', '舜泰梦600', '舜泰梦600', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270097', '跃迪T60', '跃迪T60', '跃迪T60', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270098', '跃迪T80', '跃迪T80', '跃迪T80', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270099', '轿车01', '轿车01', '轿车01', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270100', '轿车03', '轿车03', '轿车03', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270101', '轿车05', '轿车05', '轿车05', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270102', '速派奇超越', '速派奇超越', '速派奇超越', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270103', '速派奇超越三号', '速派奇超越三号', '速派奇超越三号', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270104', '金彭S7', '金彭S7', '金彭S7', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270105', '金彭S70', '金彭S70', '金彭S70', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270106', '银泰01', '银泰01', '银泰01', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270107', '银泰T32', '银泰T32', '银泰T32', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270108', '银泰熊猫', '银泰熊猫', '银泰熊猫', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270109', '银泰皓月', '银泰皓月', '银泰皓月', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270110', '陆地方舟04', '陆地方舟04', '陆地方舟04', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270111', '陆地方舟06', '陆地方舟06', '陆地方舟06', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270112', '陆地方舟JO', '陆地方舟JO', '陆地方舟JO', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270113', '雷丁V60', '雷丁V60', '雷丁V60', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270114', '鲁星01', '鲁星01', '鲁星01', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02270115', '龙瑞LEV6', '龙瑞LEV6', '龙瑞LEV6', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02279999', '未知', '未知', '未知', '0227');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02280000', 'EV10', 'EV10', 'EV10', '0228');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02289999', '未知', '未知', '未知', '0228');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02290000', '1', '1', '1', '0229');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02299999', '未知', '未知', '未知', '0229');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02300000', 'D1', 'D1', 'D1', '0230');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02300001', 'D2', 'D2', 'D2', '0230');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02300002', 'D2(D2S)', 'D2(D2S)', 'D2(D2S)', '0230');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02309999', '未知', '未知', '未知', '0230');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02310000', 'EX80', 'EX80', 'EX80', '0231');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02310001', 'M70', 'M70', 'M70', '0231');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02310002', 'M70(进取型)', 'M70(进取型)', 'M70(进取型)', '0231');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02310003', 'V60', 'V60', 'V60', '0231');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02319999', '未知', '未知', '未知', '0231');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320000', 'CMAX', 'CMAX', 'CMAX', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320001', 'CMAX(ENERGI)', 'CMAX(ENERGI)', 'CMAX(ENERGI)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320002', 'CMAX(HYBRID)', 'CMAX(HYBRID)', 'CMAX(HYBRID)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320003', 'CMAX(基本款)', 'CMAX(基本款)', 'CMAX(基本款)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320004', 'E350', 'E350', 'E350', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320005', 'F150', 'F150', 'F150', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320006', 'F150(HARLEYDAVIDSON)', 'F150(HARLEYDAVIDSON)', 'F150(HARLEYDAVIDSON)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320007', 'F150(基本型)', 'F150(基本型)', 'F150(基本型)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320008', 'F150猛禽', 'F150猛禽', 'F150猛禽', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320009', 'F150猛禽(SVT)', 'F150猛禽(SVT)', 'F150猛禽(SVT)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320010', 'F150猛禽版', 'F150猛禽版', 'F150猛禽版', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320011', 'F250', 'F250', 'F250', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320012', 'F350', 'F350', 'F350', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320013', 'F450', 'F450', 'F450', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320014', 'F650', 'F650', 'F650', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320015', 'GRANDCMAX', 'GRANDCMAX', 'GRANDCMAX', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320016', 'MUSTANG', 'MUSTANG', 'MUSTANG', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320017', '全顺', '全顺', '全顺', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320018', '嘉年华', '嘉年华', '嘉年华', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320019', '嘉年华(三厢)(1)', '嘉年华(三厢)(1)', '嘉年华(三厢)(1)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320020', '嘉年华(手动风尚版)', '嘉年华(手动风尚版)', '嘉年华(手动风尚版)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320021', '嘉年华(运动版)', '嘉年华(运动版)', '嘉年华(运动版)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320022', '嘉年华(进口)', '嘉年华(进口)', '嘉年华(进口)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320023', '嘉年华(进口)(三门版)', '嘉年华(进口)(三门版)', '嘉年华(进口)(三门版)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320024', '嘉年华(进口)ST', '嘉年华(进口)ST', '嘉年华(进口)ST', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320025', '探险者', '探险者', '探险者', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320026', '撼路者', '撼路者', '撼路者', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320027', '新世代全顺', '新世代全顺', '新世代全顺', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320028', '福克斯', '福克斯', '福克斯', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320029', '福克斯(SVP限量版)', '福克斯(SVP限量版)', '福克斯(SVP限量版)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320030', '福克斯(三厢)', '福克斯(三厢)', '福克斯(三厢)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320031', '福克斯(两厢)', '福克斯(两厢)', '福克斯(两厢)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320032', '福克斯(进口)', '福克斯(进口)', '福克斯(进口)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320033', '福克斯ST', '福克斯ST', '福克斯ST', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320034', '福睿斯', '福睿斯', '福睿斯', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320035', '经典全顺', '经典全顺', '经典全顺', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320036', '经典福克斯', '经典福克斯', '经典福克斯', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320037', '经典福克斯(两厢)', '经典福克斯(两厢)', '经典福克斯(两厢)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320038', '翼搏', '翼搏', '翼搏', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320039', '翼虎', '翼虎', '翼虎', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320040', '翼虎(翼尊型)', '翼虎(翼尊型)', '翼虎(翼尊型)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320041', '翼虎(运动型)', '翼虎(运动型)', '翼虎(运动型)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320042', '致胜', '致胜', '致胜', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320043', '致胜(改款)', '致胜(改款)', '致胜(改款)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320044', '蒙迪欧', '蒙迪欧', '蒙迪欧', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320045', '蒙迪欧(中低配版)', '蒙迪欧(中低配版)', '蒙迪欧(中低配版)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320046', '蒙迪欧(尊贵型)', '蒙迪欧(尊贵型)', '蒙迪欧(尊贵型)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320047', '蒙迪欧(改装中网)', '蒙迪欧(改装中网)', '蒙迪欧(改装中网)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320048', '蒙迪欧(海外)', '蒙迪欧(海外)', '蒙迪欧(海外)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320049', '蒙迪欧(经典型)', '蒙迪欧(经典型)', '蒙迪欧(经典型)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320050', '蒙迪欧致胜', '蒙迪欧致胜', '蒙迪欧致胜', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320051', '蒙迪欧致胜(时尚型)', '蒙迪欧致胜(时尚型)', '蒙迪欧致胜(时尚型)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320052', '蒙迪欧致胜(豪华运动型)', '蒙迪欧致胜(豪华运动型)', '蒙迪欧致胜(豪华运动型)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320053', '途睿欧', '途睿欧', '途睿欧', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320054', '野马', '野马', '野马', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320055', '野马(50周年纪念)', '野马(50周年纪念)', '野马(50周年纪念)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320056', '野马(GT)', '野马(GT)', '野马(GT)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320057', '野马(GT500)', '野马(GT500)', '野马(GT500)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320058', '野马(手动标准版)', '野马(手动标准版)', '野马(手动标准版)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320059', '野马(手动豪华版)', '野马(手动豪华版)', '野马(手动豪华版)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320060', '野马(自动标准型)', '野马(自动标准型)', '野马(自动标准型)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320061', '金牛座', '金牛座', '金牛座', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320062', '金牛座(海外)', '金牛座(海外)', '金牛座(海外)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320063', '金牛座(限量版)', '金牛座(限量版)', '金牛座(限量版)', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320064', '锐界', '锐界', '锐界', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02320065', '麦柯斯', '麦柯斯', '麦柯斯', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02329999', '未知', '未知', '未知', '0232');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330000', 'BJ610107LHB', 'BJ610107LHB', 'BJ610107LHB', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330001', 'BJ6115U7AJB', 'BJ6115U7AJB', 'BJ6115U7AJB', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330002', 'BJ6120U8', 'BJ6120U8', 'BJ6120U8', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330003', 'BJ6121C7MTB', 'BJ6121C7MTB', 'BJ6121C7MTB', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330004', 'BJ6125U8BKB', 'BJ6125U8BKB', 'BJ6125U8BKB', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330005', 'BJ6126', 'BJ6126', 'BJ6126', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330006', 'BJ6127C8MJB', 'BJ6127C8MJB', 'BJ6127C8MJB', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330007', 'BJ6850U6AHB', 'BJ6850U6AHB', 'BJ6850U6AHB', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330008', 'G7', 'G7', 'G7', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330009', 'LJ469Q1AE9', 'LJ469Q1AE9', 'LJ469Q1AE9', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330010', '伽途T3', '伽途T3', '伽途T3', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330011', '伽途im6', '伽途im6', '伽途im6', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330012', '伽途im8', '伽途im8', '伽途im8', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330013', '伽途ix5', '伽途ix5', '伽途ix5', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330014', '伽途ix7', '伽途ix7', '伽途ix7', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330015', '图雅诺', '图雅诺', '图雅诺', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330016', '大巴01', '大巴01', '大巴01', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330017', '大巴02', '大巴02', '大巴02', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330018', '奥铃CTX中卡重载版', '奥铃CTX中卡重载版', '奥铃CTX中卡重载版', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330019', '奥铃EST', '奥铃EST', '奥铃EST', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330020', '奥铃T3', '奥铃T3', '奥铃T3', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330021', '奥铃TS', '奥铃TS', '奥铃TS', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330022', '奥铃TX', '奥铃TX', '奥铃TX', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330023', '拓路者', '拓路者', '拓路者', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330024', '时代微卡', '时代微卡', '时代微卡', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330025', '欧曼CTX6系重卡', '欧曼CTX6系重卡', '欧曼CTX6系重卡', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330026', '欧曼EST超级卡车', '欧曼EST超级卡车', '欧曼EST超级卡车', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330027', '欧曼ETX5系(特大货)', '欧曼ETX5系(特大货)', '欧曼ETX5系(特大货)', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330028', '欧曼ETX5系牵引车(特大货)', '欧曼ETX5系牵引车(特大货)', '欧曼ETX5系牵引车(特大货)', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330029', '欧曼ETX5系自卸车(大货)', '欧曼ETX5系自卸车(大货)', '欧曼ETX5系自卸车(大货)', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330030', '欧曼ETX5系载货车(大货)', '欧曼ETX5系载货车(大货)', '欧曼ETX5系载货车(大货)', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330031', '欧曼ETX6系重卡', '欧曼ETX6系重卡', '欧曼ETX6系重卡', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330032', '欧曼ETX6系重卡牵引车头(大货)', '欧曼ETX6系重卡牵引车头(大货)', '欧曼ETX6系重卡牵引车头(大货)', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330033', '欧曼ETX6系重卡载货车(特大货)', '欧曼ETX6系重卡载货车(特大货)', '欧曼ETX6系重卡载货车(特大货)', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330034', '欧曼ETX9系重卡(大货)', '欧曼ETX9系重卡(大货)', '欧曼ETX9系重卡(大货)', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330035', '欧曼ETX9系重卡(特大货)', '欧曼ETX9系重卡(特大货)', '欧曼ETX9系重卡(特大货)', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330036', '欧曼GTL(特大货)', '欧曼GTL(特大货)', '欧曼GTL(特大货)', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330037', '欧曼GTL9系', '欧曼GTL9系', '欧曼GTL9系', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330038', '欧曼GTL牵引车头(大货)', '欧曼GTL牵引车头(大货)', '欧曼GTL牵引车头(大货)', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330039', '欧曼GTL超能版', '欧曼GTL超能版', '欧曼GTL超能版', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330040', '欧曼VT9系(大货)', '欧曼VT9系(大货)', '欧曼VT9系(大货)', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330041', '欧曼VT9系(特大货)', '欧曼VT9系(特大货)', '欧曼VT9系(特大货)', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330042', '欧曼新ETX', '欧曼新ETX', '欧曼新ETX', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330043', '欧曼雄狮', '欧曼雄狮', '欧曼雄狮', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330044', '欧马可', '欧马可', '欧马可', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330045', '欧马可5系', '欧马可5系', '欧马可5系', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330046', '欧马可5系(BJ5169XXY)', '欧马可5系(BJ5169XXY)', '欧马可5系(BJ5169XXY)', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330047', '欧马可BEV电动轻卡', '欧马可BEV电动轻卡', '欧马可BEV电动轻卡', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330048', '欧马可N系列', '欧马可N系列', '欧马可N系列', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330049', '欧马可S3', '欧马可S3', '欧马可S3', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330050', '欧马可S5', '欧马可S5', '欧马可S5', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330051', '瑞沃E3自卸车', '瑞沃E3自卸车', '瑞沃E3自卸车', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330052', '瑞沃Q5', '瑞沃Q5', '瑞沃Q5', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330053', '瑞沃Q5(大货)', '瑞沃Q5(大货)', '瑞沃Q5(大货)', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330054', '皮卡', '皮卡', '皮卡', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330055', '箱卡', '箱卡', '箱卡', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330056', '萨普', '萨普', '萨普', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330057', '萨普T', '萨普T', '萨普T', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330058', '萨普V', '萨普V', '萨普V', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330059', '萨普四驱征服者', '萨普四驱征服者', '萨普四驱征服者', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330060', '萨普开拓者', '萨普开拓者', '萨普开拓者', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330061', '萨瓦纳', '萨瓦纳', '萨瓦纳', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330062', '蒙派克E', '蒙派克E', '蒙派克E', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330063', '蒙派克E(改奔驰中网)', '蒙派克E(改奔驰中网)', '蒙派克E(改奔驰中网)', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330064', '蒙派克S', '蒙派克S', '蒙派克S', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330065', '迷迪', '迷迪', '迷迪', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330066', '风景', '风景', '风景', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330067', '风景G7', '风景G7', '风景G7', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330068', '风景G9', '风景G9', '风景G9', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330069', '风景V3', '风景V3', '风景V3', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330070', '风景V5', '风景V5', '风景V5', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02330071', '骁运Q3', '骁运Q3', '骁运Q3', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02339999', '未知', '未知', '未知', '0233');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02340000', '小超人皮卡', '小超人皮卡', '小超人皮卡', '0234');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02340001', '探索者2', '探索者2', '探索者2', '0234');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02340002', '探索者3', '探索者3', '探索者3', '0234');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02340003', '探索者6', '探索者6', '探索者6', '0234');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02340004', '揽福', '揽福', '揽福', '0234');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02340005', '雄狮F16', '雄狮F16', '雄狮F16', '0234');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02340006', '雄狮F22', '雄狮F22', '雄狮F22', '0234');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02340007', '雄狮皮卡', '雄狮皮卡', '雄狮皮卡', '0234');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02349999', '未知', '未知', '未知', '0234');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02350000', 'CCR', 'CCR', 'CCR', '0235');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02359999', '未知', '未知', '未知', '0235');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02360000', 'RX6120A3', 'RX6120A3', 'RX6120A3', '0236');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02369999', '未知', '未知', '未知', '0236');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02370000', 'ZJZ5240CCYDPG7AZ3', 'ZJZ5240CCYDPG7AZ3', 'ZJZ5240CCYDPG7AZ3', '0237');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02370001', '新远征系列', '新远征系列', '新远征系列', '0237');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02370002', '远征重卡', '远征重卡', '远征重卡', '0237');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02370003', '远程重卡', '远程重卡', '远程重卡', '0237');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02379999', '未知', '未知', '未知', '0237');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02380000', '斯太尔', '斯太尔', '斯太尔', '0238');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02380001', '斯太尔重卡CQ3314XRG366', '斯太尔重卡CQ3314XRG366', '斯太尔重卡CQ3314XRG366', '0238');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02380002', '斯太尔霸王', '斯太尔霸王', '斯太尔霸王', '0238');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02380003', '新大康', '新大康', '新大康', '0238');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02380004', '新大康(特大货)', '新大康(特大货)', '新大康(特大货)', '0238');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02380005', '新金刚M500', '新金刚M500', '新金刚M500', '0238');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02380006', '新金刚重卡', '新金刚重卡', '新金刚重卡', '0238');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02380007', '杰狮C500重卡', '杰狮C500重卡', '杰狮C500重卡', '0238');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02380008', '杰狮M100轿运车(CQ5186TCLHMVG561)', '杰狮M100轿运车(CQ5186TCLHMVG561)', '杰狮M100轿运车(CQ5186TCLHMVG561)', '0238');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02380009', '杰狮M500(CQ5316GFLHTVG466H)', '杰狮M500(CQ5316GFLHTVG466H)', '杰狮M500(CQ5316GFLHTVG466H)', '0238');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02380010', '杰狮重卡', '杰狮重卡', '杰狮重卡', '0238');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02380011', '杰狮重卡(CQ4185HMG361)', '杰狮重卡(CQ4185HMG361)', '杰狮重卡(CQ4185HMG361)', '0238');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02380012', '杰狮重卡(CQ4256HTG303T)', '杰狮重卡(CQ4256HTG303T)', '杰狮重卡(CQ4256HTG303T)', '0238');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02380013', '杰狮重卡(CQ4256HTG384T)', '杰狮重卡(CQ4256HTG384T)', '杰狮重卡(CQ4256HTG384T)', '0238');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02380014', '金刚', '金刚', '金刚', '0238');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02389999', '未知', '未知', '未知', '0238');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02390000', 'H7', 'H7', 'H7', '0239');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02390001', 'L5', 'L5', 'L5', '0239');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02390002', '世纪星', '世纪星', '世纪星', '0239');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02390003', '明仕', '明仕', '明仕', '0239');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02390004', '盛世', '盛世', '盛世', '0239');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02390005', '轿车', '轿车', '轿车', '0239');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02399999', '未知', '未知', '未知', '0239');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02400000', 'MASTERCEO', 'MASTERCEO', 'MASTERCEO', '0240');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02400001', '优6SUV', '优6SUV', '优6SUV', '0240');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02400002', '大7MPV', '大7MPV', '大7MPV', '0240');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02400003', '大7MPV(电动款)', '大7MPV(电动款)', '大7MPV(电动款)', '0240');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02400004', '大7SUV', '大7SUV', '大7SUV', '0240');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02400005', '大7SUV(改装)', '大7SUV(改装)', '大7SUV(改装)', '0240');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02400006', '纳5', '纳5', '纳5', '0240');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02400007', '锐3', '锐3', '锐3', '0240');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02409999', '未知', '未知', '未知', '0240');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02410000', 'GX', 'GX', 'GX', '0241');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02419999', '未知', '未知', '未知', '0241');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02420000', 'E420', 'E420', 'E420', '0242');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02420001', 'U260', 'U260', 'U260', '0242');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02420002', 'U270', 'U270', 'U270', '0242');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02420003', 'U340', 'U340', 'U340', '0242');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02420004', 'U380(大货)', 'U380(大货)', 'U380(大货)', '0242');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02420005', 'U380(特大货)', 'U380(特大货)', 'U380(特大货)', '0242');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02420006', 'U420', 'U420', 'U420', '0242');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02420007', 'U系260', 'U系260', 'U系260', '0242');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02420008', 'V系340', 'V系340', 'V系340', '0242');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02429999', '未知', '未知', '未知', '0242');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02430000', '腾势', '腾势', '腾势', '0243');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02439999', '未知', '未知', '未知', '0243');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02440000', 'YTK6900HET', 'YTK6900HET', 'YTK6900HET', '0244');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02449999', '未知', '未知', '未知', '0244');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02450000', '载货车', '载货车', '载货车', '0245');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02459999', '未知', '未知', '未知', '0245');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02460000', 'YT4020PD', 'YT4020PD', 'YT4020PD', '0246');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02469999', '未知', '未知', '未知', '0246');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02470000', 'ESQ', 'ESQ', 'ESQ', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02470001', 'EX系(含2013款QX50)', 'EX系(含2013款QX50)', 'EX系(含2013款QX50)', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02470002', 'FX系', 'FX系', 'FX系', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02470003', 'FX系(含2013款QX70)', 'FX系(含2013款QX70)', 'FX系(含2013款QX70)', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02470004', 'G系', 'G系', 'G系', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02470005', 'JX系(含2013款QX60)', 'JX系(含2013款QX60)', 'JX系(含2013款QX60)', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02470006', 'M系', 'M系', 'M系', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02470007', 'M系(含2013款Q70)', 'M系(含2013款Q70)', 'M系(含2013款Q70)', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02470008', 'Q50', 'Q50', 'Q50', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02470009', 'Q50L(运动版)', 'Q50L(运动版)', 'Q50L(运动版)', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02470010', 'Q70', 'Q70', 'Q70', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02470011', 'QX4', 'QX4', 'QX4', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02470012', 'QX50', 'QX50', 'QX50', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02470013', 'QX56', 'QX56', 'QX56', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02470014', 'QX70', 'QX70', 'QX70', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02470015', 'QX系(含2013款QX80)', 'QX系(含2013款QX80)', 'QX系(含2013款QX80)', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02470016', '英菲尼迪FX', '英菲尼迪FX', '英菲尼迪FX', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02479999', '未知', '未知', '未知', '0247');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02480000', '350', '350', '350', '0248');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02480001', '360', '360', '360', '0248');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02480002', '550', '550', '550', '0248');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02480003', '750', '750', '750', '0248');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02480004', '950', '950', '950', '0248');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02480005', 'E50', 'E50', 'E50', '0248');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02480006', 'E950', 'E950', 'E950', '0248');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02480007', 'ERX5', 'ERX5', 'ERX5', '0248');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02480008', 'RX5', 'RX5', 'RX5', '0248');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02480009', 'W5', 'W5', 'W5', '0248');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02480010', 'ei6', 'ei6', 'ei6', '0248');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02480011', 'i6', 'i6', 'i6', '0248');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02489999', '未知', '未知', '未知', '0248');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02490000', 'L3', 'L3', 'L3', '0249');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02490001', 'L5', 'L5', 'L5', '0249');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02490002', 'L6', 'L6', 'L6', '0249');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02490003', '竞悦', '竞悦', '竞悦', '0249');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02490004', '竞速', '竞速', '竞速', '0249');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02499999', '未知', '未知', '未知', '0249');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500000', '128P', '128P', '128P', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500001', '500', '500', '500', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500002', '500(Abarth)', '500(Abarth)', '500(Abarth)', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500003', '500(基本型)', '500(基本型)', '500(基本型)', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500004', '500C', '500C', '500C', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500005', '500L', '500L', '500L', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500006', 'Panda', 'Panda', 'Panda', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500007', 'Panda(4X4)', 'Panda(4X4)', 'Panda(4X4)', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500008', 'Panda(TREKKING)', 'Panda(TREKKING)', 'Panda(TREKKING)', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500009', 'SEDICI', 'SEDICI', 'SEDICI', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500010', 'STRADA', 'STRADA', 'STRADA', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500011', 'TIPO', 'TIPO', 'TIPO', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500012', 'Uno', 'Uno', 'Uno', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500013', '博悦', '博悦', '博悦', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500014', '周末风', '周末风', '周末风', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500015', '多宝', '多宝', '多宝', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500016', '朋多', '朋多', '朋多', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500017', '派力奥', '派力奥', '派力奥', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500018', '派朗', '派朗', '派朗', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500019', '致悦', '致悦', '致悦', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500020', '致悦(夜行者版)', '致悦(夜行者版)', '致悦(夜行者版)', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500021', '菲亚特500', '菲亚特500', '菲亚特500', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500022', '菲翔', '菲翔', '菲翔', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500023', '菲跃', '菲跃', '菲跃', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02500024', '西耶那', '西耶那', '西耶那', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02509999', '未知', '未知', '未知', '0250');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02510000', '9000', '9000', '9000', '0251');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02510001', 'Saab93', 'Saab93', 'Saab93', '0251');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02510002', 'Saab93(VECTOR)', 'Saab93(VECTOR)', 'Saab93(VECTOR)', '0251');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02510003', 'Saab93(敞篷)', 'Saab93(敞篷)', 'Saab93(敞篷)', '0251');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02510004', 'Saab94X', 'Saab94X', 'Saab94X', '0251');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02510005', 'Saab95', 'Saab95', 'Saab95', '0251');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02510006', 'Saab97', 'Saab97', 'Saab97', '0251');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02519999', '未知', '未知', '未知', '0251');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02520000', 'Delta', 'Delta', 'Delta', '0252');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02520001', 'Flavia', 'Flavia', 'Flavia', '0252');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02520002', 'Thema', 'Thema', 'Thema', '0252');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02520003', 'Voyager', 'Voyager', 'Voyager', '0252');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02520004', 'Ypsilon', 'Ypsilon', 'Ypsilon', '0252');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02529999', '未知', '未知', '未知', '0252');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02530000', 'EP9', 'EP9', 'EP9', '0253');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02539999', '未知', '未知', '未知', '0253');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02540000', 'HSZ6730', 'HSZ6730', 'HSZ6730', '0254');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02549999', '未知', '未知', '未知', '0254');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02550000', 'XW6122DA', 'XW6122DA', 'XW6122DA', '0255');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02559999', '未知', '未知', '未知', '0255');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02560000', '20V20', '20V20', '20V20', '0256');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02560001', 'Altea', 'Altea', 'Altea', '0256');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02560002', 'Ateca', 'Ateca', 'Ateca', '0256');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02560003', 'Exeo', 'Exeo', 'Exeo', '0256');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02560004', 'LEON', 'LEON', 'LEON', '0256');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02560005', 'LEON(CUPRA)', 'LEON(CUPRA)', 'LEON(CUPRA)', '0256');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02560006', 'MII', 'MII', 'MII', '0256');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02560007', 'Toledo', 'Toledo', 'Toledo', '0256');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02560008', '伊比飒', '伊比飒', '伊比飒', '0256');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02560009', '伊比飒(低配版)', '伊比飒(低配版)', '伊比飒(低配版)', '0256');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02560010', '欧悦搏', '欧悦搏', '欧悦搏', '0256');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02569999', '未知', '未知', '未知', '0256');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02570000', '观致2', '观致2', '观致2', '0257');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02570001', '观致3', '观致3', '观致3', '0257');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02570002', '观致5', '观致5', '观致5', '0257');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02579999', '未知', '未知', '未知', '0257');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580000', 'CA1140P1K2L7', 'CA1140P1K2L7', 'CA1140P1K2L7', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580001', 'CA4251K2E3R5T1A92', 'CA4251K2E3R5T1A92', 'CA4251K2E3R5T1A92', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580002', 'F330速豹', 'F330速豹', 'F330速豹', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580003', 'J4', 'J4', 'J4', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580004', 'J4(2代)', 'J4(2代)', 'J4(2代)', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580005', 'J4(3代)', 'J4(3代)', 'J4(3代)', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580006', 'J4R自卸车', 'J4R自卸车', 'J4R自卸车', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580007', 'LJC3041D', 'LJC3041D', 'LJC3041D', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580008', 'MT1系列', 'MT1系列', 'MT1系列', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580009', 'S230公狮', 'S230公狮', 'S230公狮', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580010', '五征', '五征', '五征', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580011', '大威重卡', '大威重卡', '大威重卡', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580012', '天V重卡', '天V重卡', '天V重卡', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580013', '奥威J5P(大货)', '奥威J5P(大货)', '奥威J5P(大货)', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580014', '奥威J5P(特大货)', '奥威J5P(特大货)', '奥威J5P(特大货)', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580015', '奥威J5P重卡', '奥威J5P重卡', '奥威J5P重卡', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580016', '安捷', '安捷', '安捷', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580017', '安捷(牵引车头)', '安捷(牵引车头)', '安捷(牵引车头)', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580018', '小解放', '小解放', '小解放', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580019', '悍威J5M', '悍威J5M', '悍威J5M', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580020', '悍威J5M重卡', '悍威J5M重卡', '悍威J5M重卡', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580021', '新安捷L5R重卡', '新安捷L5R重卡', '新安捷L5R重卡', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580022', '新悍威J5M重卡(CA4183P1K2E4A80)', '新悍威J5M重卡(CA4183P1K2E4A80)', '新悍威J5M重卡(CA4183P1K2E4A80)', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580023', '新金玲', '新金玲', '新金玲', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580024', '神力L4K', '神力L4K', '神力L4K', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580025', '红塔金卡', '红塔金卡', '红塔金卡', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580026', '赛麒麟', '赛麒麟', '赛麒麟', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580027', '赛龙载货', '赛龙载货', '赛龙载货', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580028', '载货车', '载货车', '载货车', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580029', '途V', '途V', '途V', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580030', '金大陆重卡', '金大陆重卡', '金大陆重卡', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02580031', '金铃(红塔)', '金铃(红塔)', '金铃(红塔)', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02589999', '未知', '未知', '未知', '0258');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02590000', 'CDX', 'CDX', 'CDX', '0259');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02590001', 'MDX', 'MDX', 'MDX', '0259');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02590002', 'NSX', 'NSX', 'NSX', '0259');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02590003', 'PRECISION', 'PRECISION', 'PRECISION', '0259');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02590004', 'TL', 'TL', 'TL', '0259');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02590005', 'TSX', 'TSX', 'TSX', '0259');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02599999', '未知', '未知', '未知', '0259');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02600000', 'ILX', 'ILX', 'ILX', '0260');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02600001', 'INTEGRA', 'INTEGRA', 'INTEGRA', '0260');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02600002', 'MDX', 'MDX', 'MDX', '0260');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02600003', 'MDX(基本型)', 'MDX(基本型)', 'MDX(基本型)', '0260');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02600004', 'MDX(基本款)', 'MDX(基本款)', 'MDX(基本款)', '0260');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02600005', 'MDX(豪华版)', 'MDX(豪华版)', 'MDX(豪华版)', '0260');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02600006', 'NSX', 'NSX', 'NSX', '0260');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02600007', 'RDX', 'RDX', 'RDX', '0260');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02600008', 'RL', 'RL', 'RL', '0260');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02600009', 'RLX', 'RLX', 'RLX', '0260');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02600010', 'SUVX', 'SUVX', 'SUVX', '0260');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02600011', 'TL', 'TL', 'TL', '0260');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02600012', 'TLX', 'TLX', 'TLX', '0260');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02600013', 'TSX', 'TSX', 'TSX', '0260');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02600014', 'ZDX', 'ZDX', 'ZDX', '0260');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02609999', '未知', '未知', '未知', '0260');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02610000', 'HS6600', 'HS6600', 'HS6600', '0261');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02610001', 'HS6720A', 'HS6720A', 'HS6720A', '0261');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02619999', '未知', '未知', '未知', '0261');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02620000', '科迈罗', '科迈罗', '科迈罗', '0262');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02629999', '未知', '未知', '未知', '0262');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630000', 'K2', 'K2', 'K2', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630001', 'K3', 'K3', 'K3', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630002', 'K3S', 'K3S', 'K3S', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630003', 'K5', 'K5', 'K5', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630004', 'K5(改装)', 'K5(改装)', 'K5(改装)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630005', 'K5(进口)', 'K5(进口)', 'K5(进口)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630006', 'K9(进口)', 'K9(进口)', 'K9(进口)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630007', 'KX3', 'KX3', 'KX3', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630008', 'KX5', 'KX5', 'KX5', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630009', 'RAYEV', 'RAYEV', 'RAYEV', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630010', 'SOUL(进口)(基本款)', 'SOUL(进口)(基本款)', 'SOUL(进口)(基本款)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630011', 'SOULEV(进口)(Concept)', 'SOULEV(进口)(Concept)', 'SOULEV(进口)(Concept)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630012', 'SPORTAGE(进口)', 'SPORTAGE(进口)', 'SPORTAGE(进口)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630013', 'VQ', 'VQ', 'VQ', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630014', '佳乐', '佳乐', '佳乐', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630015', '佳乐(进口)', '佳乐(进口)', '佳乐(进口)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630016', '凯尊(进口)', '凯尊(进口)', '凯尊(进口)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630017', '千里马', '千里马', '千里马', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630018', '嘉华', '嘉华', '嘉华', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630019', '嘉华(进口)', '嘉华(进口)', '嘉华(进口)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630020', '普莱特(两厢)', '普莱特(两厢)', '普莱特(两厢)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630021', '智跑', '智跑', '智跑', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630022', '智跑(自动)', '智跑(自动)', '智跑(自动)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630023', '极睿(进口)', '极睿(进口)', '极睿(进口)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630024', '欧菲莱斯', '欧菲莱斯', '欧菲莱斯', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630025', '欧菲莱斯(进口)', '欧菲莱斯(进口)', '欧菲莱斯(进口)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630026', '狮跑', '狮跑', '狮跑', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630027', '狮跑(手动)', '狮跑(手动)', '狮跑(手动)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630028', '狮跑(手动改)', '狮跑(手动改)', '狮跑(手动改)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630029', '狮跑(自动)', '狮跑(自动)', '狮跑(自动)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630030', '狮跑(进口)', '狮跑(进口)', '狮跑(进口)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630031', '福瑞迪', '福瑞迪', '福瑞迪', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630032', '福瑞迪(改装)', '福瑞迪(改装)', '福瑞迪(改装)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630033', '秀尔', '秀尔', '秀尔', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630034', '秀尔(ATGL)', '秀尔(ATGL)', '秀尔(ATGL)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630035', '秀尔(顶配版)', '秀尔(顶配版)', '秀尔(顶配版)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630036', '索兰尼(进口)', '索兰尼(进口)', '索兰尼(进口)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630037', '索兰尼(进口)(5座汽油舒适版)', '索兰尼(进口)(5座汽油舒适版)', '索兰尼(进口)(5座汽油舒适版)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630038', '索兰托', '索兰托', '索兰托', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630039', '索兰托(改装中网)', '索兰托(改装中网)', '索兰托(改装中网)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630040', '索兰托(进口)', '索兰托(进口)', '索兰托(进口)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630041', '索兰托(进口)(5座汽油豪华版)', '索兰托(进口)(5座汽油豪华版)', '索兰托(进口)(5座汽油豪华版)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630042', '索兰托(进口)(改)', '索兰托(进口)(改)', '索兰托(进口)(改)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630043', '索兰托(进口)(改1)', '索兰托(进口)(改1)', '索兰托(进口)(改1)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630044', '索兰托(进口)(改2)', '索兰托(进口)(改2)', '索兰托(进口)(改2)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630045', '索兰托(进口)(精英版)', '索兰托(进口)(精英版)', '索兰托(进口)(精英版)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630046', '索兰托(进口)(运动版)', '索兰托(进口)(运动版)', '索兰托(进口)(运动版)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630047', '赛拉图', '赛拉图', '赛拉图', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630048', '起亚K2', '起亚K2', '起亚K2', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630049', '起亚K3', '起亚K3', '起亚K3', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630050', '起亚K4', '起亚K4', '起亚K4', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630051', '起亚K5', '起亚K5', '起亚K5', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630052', '起亚KX3', '起亚KX3', '起亚KX3', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630053', '起亚KX7(低配版)', '起亚KX7(低配版)', '起亚KX7(低配版)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630054', '起亚KX7(高配版)', '起亚KX7(高配版)', '起亚KX7(高配版)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630055', '起亚VQ(进口)', '起亚VQ(进口)', '起亚VQ(进口)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630056', '远舰', '远舰', '远舰', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630057', '速迈', '速迈', '速迈', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630058', '速迈(进口)', '速迈(进口)', '速迈(进口)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630059', '锐欧', '锐欧', '锐欧', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630060', '霸锐', '霸锐', '霸锐', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02630061', '霸锐(进口)', '霸锐(进口)', '霸锐(进口)', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02639999', '未知', '未知', '未知', '0263');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02640000', 'NJ1041DBFT3', 'NJ1041DBFT3', 'NJ1041DBFT3', '0264');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02640001', 'NJ1050HDFL3改', 'NJ1050HDFL3改', 'NJ1050HDFL3改', '0264');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02640002', 'NJ1052DCHZ2', 'NJ1052DCHZ2', 'NJ1052DCHZ2', '0264');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02640003', '上骏X500', '上骏X500', '上骏X500', '0264');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02640004', '仓棚式货车', '仓棚式货车', '仓棚式货车', '0264');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02640005', '凌野', '凌野', '凌野', '0264');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02640006', '厢式货车02', '厢式货车02', '厢式货车02', '0264');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02640007', '厢式运输货车', '厢式运输货车', '厢式运输货车', '0264');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02640008', '小福星', '小福星', '小福星', '0264');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02640009', '小福星(2)', '小福星(2)', '小福星(2)', '0264');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02640010', '轻卡', '轻卡', '轻卡', '0264');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02640011', '载货汽车', '载货汽车', '载货汽车', '0264');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02649999', '未知', '未知', '未知', '0264');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02650000', 'ELAN', 'ELAN', 'ELAN', '0265');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02650001', 'ELISE', 'ELISE', 'ELISE', '0265');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02650002', 'ESPRIT', 'ESPRIT', 'ESPRIT', '0265');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02650003', 'EUROPA', 'EUROPA', 'EUROPA', '0265');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02650004', 'EVORA', 'EVORA', 'EVORA', '0265');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02650005', 'EVORA(3.5V6IPS)', 'EVORA(3.5V6IPS)', 'EVORA(3.5V6IPS)', '0265');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02650006', 'EVORA(3.5V6四座标准版)', 'EVORA(3.5V6四座标准版)', 'EVORA(3.5V6四座标准版)', '0265');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02650007', 'EXIGE', 'EXIGE', 'EXIGE', '0265');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02650008', 'EXIGE(3.5LCupR)', 'EXIGE(3.5LCupR)', 'EXIGE(3.5LCupR)', '0265');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02659999', '未知', '未知', '未知', '0265');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660000', '卫士', '卫士', '卫士', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660001', '卫士(LS3)', '卫士(LS3)', '卫士(LS3)', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660002', '卫士(特别版)', '卫士(特别版)', '卫士(特别版)', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660003', '发现3', '发现3', '发现3', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660004', '发现4', '发现4', '发现4', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660005', '发现Vision(进口)', '发现Vision(进口)', '发现Vision(进口)', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660006', '发现神行', '发现神行', '发现神行', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660007', '揽胜', '揽胜', '揽胜', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660008', '揽胜(进口)', '揽胜(进口)', '揽胜(进口)', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660009', '揽胜(进口)(Autobiography)', '揽胜(进口)(Autobiography)', '揽胜(进口)(Autobiography)', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660010', '揽胜(进口)(Overfinch)', '揽胜(进口)(Overfinch)', '揽胜(进口)(Overfinch)', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660011', '揽胜(进口)(顶配版)', '揽胜(进口)(顶配版)', '揽胜(进口)(顶配版)', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660012', '揽胜星脉(进口)(R380)', '揽胜星脉(进口)(R380)', '揽胜星脉(进口)(R380)', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660013', '揽胜极光(进口)', '揽胜极光(进口)', '揽胜极光(进口)', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660014', '揽胜运动', '揽胜运动', '揽胜运动', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660015', '揽胜运动版(进口)', '揽胜运动版(进口)', '揽胜运动版(进口)', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660016', '神行者2', '神行者2', '神行者2', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02660017', '神行者2(进口)', '神行者2(进口)', '神行者2(进口)', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02669999', '未知', '未知', '未知', '0266');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02670000', '欧铃', '欧铃', '欧铃', '0267');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02679999', '未知', '未知', '未知', '0267');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02680000', 'LF', 'LF', 'LF', '0268');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02689999', '未知', '未知', '未知', '0268');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02690000', '12C', '12C', '12C', '0269');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02690001', '540C', '540C', '540C', '0269');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02690002', '570S', '570S', '570S', '0269');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02690003', '625C', '625C', '625C', '0269');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02690004', '650S', '650S', '650S', '0269');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02690005', '675LT', '675LT', '675LT', '0269');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02690006', 'P1', 'P1', 'P1', '0269');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02690007', 'X1', 'X1', 'X1', '0269');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02699999', '未知', '未知', '未知', '0269');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02700000', 'EXELERO', 'EXELERO', 'EXELERO', '0270');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02700001', '迈巴赫', '迈巴赫', '迈巴赫', '0270');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02700002', '迈巴赫(中国版)', '迈巴赫(中国版)', '迈巴赫(中国版)', '0270');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02700003', '迈巴赫(齐柏林)', '迈巴赫(齐柏林)', '迈巴赫(齐柏林)', '0270');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02709999', '未知', '未知', '未知', '0270');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02710000', 'CHARGERSRT', 'CHARGERSRT', 'CHARGERSRT', '0271');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02710001', 'CIRCUITEV(进口)', 'CIRCUITEV(进口)', 'CIRCUITEV(进口)', '0271');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02710002', 'DURANGO(进口)(RT)', 'DURANGO(进口)(RT)', 'DURANGO(进口)(RT)', '0271');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02710003', 'RAM', 'RAM', 'RAM', '0271');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02710004', 'RAM(进口)', 'RAM(进口)', 'RAM(进口)', '0271');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02710005', '东南道奇(凯领)', '东南道奇(凯领)', '东南道奇(凯领)', '0271');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02710006', '凯领', '凯领', '凯领', '0271');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02710007', '挑战者', '挑战者', '挑战者', '0271');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02710008', '翼龙(进口)', '翼龙(进口)', '翼龙(进口)', '0271');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02710009', '酷威', '酷威', '酷威', '0271');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02710010', '酷威(进口)', '酷威(进口)', '酷威(进口)', '0271');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02710011', '酷搏', '酷搏', '酷搏', '0271');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02719999', '未知', '未知', '未知', '0271');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02720000', '圆梦者', '圆梦者', '圆梦者', '0272');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02720001', '开拓者', '开拓者', '开拓者', '0272');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02720002', '开拓者S', '开拓者S', '开拓者S', '0272');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02720003', '酷跑', '酷跑', '酷跑', '0272');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02729999', '未知', '未知', '未知', '0272');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02730000', 'CDW王牌', 'CDW王牌', 'CDW王牌', '0273');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02739999', '未知', '未知', '未知', '0273');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02740000', 'T6G', 'T6G', 'T6G', '0274');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02749999', '未知', '未知', '未知', '0274');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02750000', 'T70', 'T70', 'T70', '0275');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02759999', '未知', '未知', '未知', '0275');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02760000', '野马F10', '野马F10', '野马F10', '0276');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02760001', '野马F12', '野马F12', '野马F12', '0276');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02760002', '野马F14', '野马F14', '野马F14', '0276');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02760003', '野马F99', '野马F99', '野马F99', '0276');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02760004', '野马M302', '野马M302', '野马M302', '0276');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02760005', '野马T80', '野马T80', '野马T80', '0276');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02769999', '未知', '未知', '未知', '0276');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770000', 'XML6102J', 'XML6102J', 'XML6102J', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770001', 'XML6103J12', 'XML6103J12', 'XML6103J12', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770002', 'XML6105', 'XML6105', 'XML6105', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770003', 'XML6113J63', 'XML6113J63', 'XML6113J63', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770004', 'XML6117J28', 'XML6117J28', 'XML6117J28', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770005', 'XML6118E21H', 'XML6118E21H', 'XML6118E21H', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770006', 'XML6121E5G', 'XML6121E5G', 'XML6121E5G', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770007', 'XML6125', 'XML6125', 'XML6125', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770008', 'XML6125天骄', 'XML6125天骄', 'XML6125天骄', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770009', 'XML6126', 'XML6126', 'XML6126', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770010', 'XML6127E2', 'XML6127E2', 'XML6127E2', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770011', 'XML6127J15', 'XML6127J15', 'XML6127J15', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770012', 'XML6127J93', 'XML6127J93', 'XML6127J93', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770013', 'XML6129E51', 'XML6129E51', 'XML6129E51', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770014', 'XML6146', 'XML6146', 'XML6146', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770015', 'XML6792E5A', 'XML6792E5A', 'XML6792E5A', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770016', 'XML6807A23', 'XML6807A23', 'XML6807A23', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770017', 'XML6807雪狐', 'XML6807雪狐', 'XML6807雪狐', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770018', 'XML6837E5A', 'XML6837E5A', 'XML6837E5A', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770019', 'XML6893E3G', 'XML6893E3G', 'XML6893E3G', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770020', 'XML6907J15Y', 'XML6907J15Y', 'XML6907J15Y', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770021', 'XML6998', 'XML6998', 'XML6998', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770022', '大海师G6', '大海师G6', '大海师G6', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770023', '海狮', '海狮', '海狮', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02770024', '考斯特', '考斯特', '考斯特', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02779999', '未知', '未知', '未知', '0277');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780000', 'S50', 'S50', 'S50', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780001', 'T30(微卡)', 'T30(微卡)', 'T30(微卡)', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780002', 'T30(微面)', 'T30(微面)', 'T30(微面)', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780003', 'T32载货车', 'T32载货车', 'T32载货车', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780004', '华晨金杯750', '华晨金杯750', '华晨金杯750', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780005', '大力神', '大力神', '大力神', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780006', '大力神K5', '大力神K5', '大力神K5', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780007', '小海狮X30', '小海狮X30', '小海狮X30', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780008', '小金牛', '小金牛', '小金牛', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780009', '快运', '快运', '快运', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780010', '智尚S30', '智尚S30', '智尚S30', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780011', '智尚S35', '智尚S35', '智尚S35', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780012', '核动力', '核动力', '核动力', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780013', '海星A7', '海星A7', '海星A7', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780014', '海星A9', '海星A9', '海星A9', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780015', '海狮', '海狮', '海狮', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780016', '海狮X30L', '海狮X30L', '海狮X30L', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780017', '海狮第六代', '海狮第六代', '海狮第六代', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780018', '西部牛仔微卡', '西部牛仔微卡', '西部牛仔微卡', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780019', '金典', '金典', '金典', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780020', '金典007', '金典007', '金典007', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780021', '金杯S70', '金杯S70', '金杯S70', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780022', '金杯之星', '金杯之星', '金杯之星', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780023', '金杯大海狮', '金杯大海狮', '金杯大海狮', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780024', '金杯海狮', '金杯海狮', '金杯海狮', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780025', '金杯霸道', '金杯霸道', '金杯霸道', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780026', '金运', '金运', '金运', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780027', '金运栏板轻卡', '金运栏板轻卡', '金运栏板轻卡', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780028', '金骐', '金骐', '金骐', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780029', '阁瑞斯', '阁瑞斯', '阁瑞斯', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780030', '阁瑞斯(头等商务型)', '阁瑞斯(头等商务型)', '阁瑞斯(头等商务型)', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780031', '阁瑞斯(金色之旅)', '阁瑞斯(金色之旅)', '阁瑞斯(金色之旅)', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780032', '雷龙', '雷龙', '雷龙', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780033', '领驰', '领驰', '领驰', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780034', '领骐', '领骐', '领骐', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02780035', '骐运', '骐运', '骐运', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02789999', '未知', '未知', '未知', '0278');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790000', 'KLQ6108G', 'KLQ6108G', 'KLQ6108G', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790001', 'KLQ6601E2', 'KLQ6601E2', 'KLQ6601E2', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790002', 'MPV', 'MPV', 'MPV', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790003', 'PK6128A', 'PK6128A', 'PK6128A', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790004', 'XMQ6106G', 'XMQ6106G', 'XMQ6106G', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790005', 'XMQ6111Y5', 'XMQ6111Y5', 'XMQ6111Y5', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790006', 'XMQ6113AYD4C', 'XMQ6113AYD4C', 'XMQ6113AYD4C', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790007', 'XMQ6113E2', 'XMQ6113E2', 'XMQ6113E2', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790008', 'XMQ6116F2Q', 'XMQ6116F2Q', 'XMQ6116F2Q', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790009', 'XMQ6116G4', 'XMQ6116G4', 'XMQ6116G4', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790010', 'XMQ6117Y', 'XMQ6117Y', 'XMQ6117Y', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790011', 'XMQ6118F1B', 'XMQ6118F1B', 'XMQ6118F1B', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790012', 'XMQ6118G', 'XMQ6118G', 'XMQ6118G', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790013', 'XMQ6119T', 'XMQ6119T', 'XMQ6119T', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790014', 'XMQ6120UY', 'XMQ6120UY', 'XMQ6120UY', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790015', 'XMQ6121Y', 'XMQ6121Y', 'XMQ6121Y', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790016', 'XMQ6122PWSC', 'XMQ6122PWSC', 'XMQ6122PWSC', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790017', 'XMQ6122Y', 'XMQ6122Y', 'XMQ6122Y', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790018', 'XMQ6125CYD4C', 'XMQ6125CYD4C', 'XMQ6125CYD4C', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790019', 'XMQ6126Y3', 'XMQ6126Y3', 'XMQ6126Y3', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790020', 'XMQ6127', 'XMQ6127', 'XMQ6127', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790021', 'XMQ6128Y2', 'XMQ6128Y2', 'XMQ6128Y2', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790022', 'XMQ6129Y8', 'XMQ6129Y8', 'XMQ6129Y8', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790023', 'XMQ6730ASD3', 'XMQ6730ASD3', 'XMQ6730ASD3', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790024', 'XMQ6759Y', 'XMQ6759Y', 'XMQ6759Y', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790025', 'XMQ6798', 'XMQ6798', 'XMQ6798', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790026', 'XMQ6830HB2', 'XMQ6830HB2', 'XMQ6830HB2', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790027', 'XMQ6830HBS', 'XMQ6830HBS', 'XMQ6830HBS', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790028', 'XMQ6840HE', 'XMQ6840HE', 'XMQ6840HE', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790029', 'XMQ6858Y', 'XMQ6858Y', 'XMQ6858Y', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790030', 'XMQ6901AYD4C', 'XMQ6901AYD4C', 'XMQ6901AYD4C', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790031', '中大YCK6107HP', '中大YCK6107HP', '中大YCK6107HP', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790032', '凯歌', '凯歌', '凯歌', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790033', '凯特', '凯特', '凯特', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790034', '大巴XQM6125AYN4B', '大巴XQM6125AYN4B', '大巴XQM6125AYN4B', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790035', '大巴XQM6140G', '大巴XQM6140G', '大巴XQM6140G', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790036', '开沃D11', '开沃D11', '开沃D11', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790037', '海狮', '海狮', '海狮', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02790038', '金威', '金威', '金威', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02799999', '未知', '未知', '未知', '0279');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02800000', '天际', '天际', '天际', '0280');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02800001', '翼骏', '翼骏', '翼骏', '0280');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02809999', '未知', '未知', '未知', '0280');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810000', 'AWIND(进口)', 'AWIND(进口)', 'AWIND(进口)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810001', 'BALENO(进口)', 'BALENO(进口)', 'BALENO(进口)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810002', 'CELERIO', 'CELERIO', 'CELERIO', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810003', 'EQUATOR', 'EQUATOR', 'EQUATOR', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810004', 'IV4', 'IV4', 'IV4', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810005', 'SCROSS(进口)', 'SCROSS(进口)', 'SCROSS(进口)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810006', 'SX(海外)(进口)', 'SX(海外)(进口)', 'SX(海外)(进口)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810007', 'SX4(海外)(进口)', 'SX4(海外)(进口)', 'SX4(海外)(进口)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810008', 'VITARA(海外)', 'VITARA(海外)', 'VITARA(海外)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810009', '利亚纳', '利亚纳', '利亚纳', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810010', '北斗星', '北斗星', '北斗星', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810011', '北斗星(e加版)', '北斗星(e加版)', '北斗星(e加版)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810012', '北斗星(进口版)', '北斗星(进口版)', '北斗星(进口版)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810013', '北斗星X5', '北斗星X5', '北斗星X5', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810014', '吉姆尼', '吉姆尼', '吉姆尼', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810015', '启悦(尊享版)', '启悦(尊享版)', '启悦(尊享版)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810016', '启悦(海外)(进口)', '启悦(海外)(进口)', '启悦(海外)(进口)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810017', '启悦(舒享版)', '启悦(舒享版)', '启悦(舒享版)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810018', '天语SX4', '天语SX4', '天语SX4', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810019', '天语尚悦', '天语尚悦', '天语尚悦', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810020', '奥拓', '奥拓', '奥拓', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810021', '奥拓(加装前杠)', '奥拓(加装前杠)', '奥拓(加装前杠)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810022', '奥拓(改款)', '奥拓(改款)', '奥拓(改款)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810023', '派喜', '派喜', '派喜', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810024', '浪迪', '浪迪', '浪迪', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810025', '浪迪(加装前杠)', '浪迪(加装前杠)', '浪迪(加装前杠)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810026', '维特拉', '维特拉', '维特拉', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810027', '羚羊', '羚羊', '羚羊', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810028', '超级维特拉', '超级维特拉', '超级维特拉', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810029', '超级维特拉(进口)', '超级维特拉(进口)', '超级维特拉(进口)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810030', '速翼特(进口)(基本型)', '速翼特(进口)(基本型)', '速翼特(进口)(基本型)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810031', '速翼特(进口)(豪华版)', '速翼特(进口)(豪华版)', '速翼特(进口)(豪华版)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810032', '锋驭', '锋驭', '锋驭', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810033', '锋驭(改装)', '锋驭(改装)', '锋驭(改装)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810034', '雨燕', '雨燕', '雨燕', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810035', '雨燕(手动标准版)', '雨燕(手动标准版)', '雨燕(手动标准版)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02810036', '雨燕(手动超值版)', '雨燕(手动超值版)', '雨燕(手动超值版)', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02819999', '未知', '未知', '未知', '0281');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820000', 'C20R', 'C20R', 'C20R', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820001', 'C30', 'C30', 'C30', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820002', 'C30新能源(豪华型)', 'C30新能源(豪华型)', 'C30新能源(豪华型)', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820003', 'C50', 'C50', 'C50', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820004', 'CC6828G1', 'CC6828G1', 'CC6828G1', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820005', 'M1', 'M1', 'M1', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820006', 'M2', 'M2', 'M2', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820007', 'M2(改装)', 'M2(改装)', 'M2(改装)', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820008', 'M4', 'M4', 'M4', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820009', 'M4(改款)', 'M4(改款)', 'M4(改款)', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820010', 'M4(改装)', 'M4(改装)', 'M4(改装)', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820011', 'V80', 'V80', 'V80', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820012', '凌傲', '凌傲', '凌傲', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820013', '凌傲(改装)', '凌傲(改装)', '凌傲(改装)', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820014', '欧拉', '欧拉', '欧拉', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820015', '炫丽', '炫丽', '炫丽', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820016', '炫丽(车尾图)', '炫丽(车尾图)', '炫丽(车尾图)', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820017', '精灵', '精灵', '精灵', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820018', '赛弗', '赛弗', '赛弗', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820019', '赛影', '赛影', '赛影', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820020', '赛酷', '赛酷', '赛酷', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820021', '赛铃', '赛铃', '赛铃', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820022', '酷熊', '酷熊', '酷熊', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820023', '金迪尔', '金迪尔', '金迪尔', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820024', '风骏3', '风骏3', '风骏3', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820025', '风骏5', '风骏5', '风骏5', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820026', '风骏5(保险杠)', '风骏5(保险杠)', '风骏5(保险杠)', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820027', '风骏5(改装)', '风骏5(改装)', '风骏5(改装)', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820028', '风骏5小双排', '风骏5小双排', '风骏5小双排', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02820029', '风骏6', '风骏6', '风骏6', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02829999', '未知', '未知', '未知', '0282');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830000', '2007', '2007', '2007', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830001', 'CS15', 'CS15', 'CS15', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830002', 'CS35', 'CS35', 'CS35', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830003', 'CS75', 'CS75', 'CS75', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830004', 'CS75(改装)', 'CS75(改装)', 'CS75(改装)', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830005', 'CS95', 'CS95', 'CS95', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830006', 'CX20', 'CX20', 'CX20', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830007', 'CX30', 'CX30', 'CX30', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830008', 'CX70', 'CX70', 'CX70', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830009', 'CX70(改装)', 'CX70(改装)', 'CX70(改装)', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830010', 'SC6100', 'SC6100', 'SC6100', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830011', 'V5', 'V5', 'V5', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830012', '凌轩', '凌轩', '凌轩', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830013', '勋龙', '勋龙', '勋龙', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830014', '奔奔', '奔奔', '奔奔', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830015', '奔奔I(运动版)', '奔奔I(运动版)', '奔奔I(运动版)', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830016', '奔奔LOVE', '奔奔LOVE', '奔奔LOVE', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830017', '奔奔MINI', '奔奔MINI', '奔奔MINI', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830018', '威豹轻卡', '威豹轻卡', '威豹轻卡', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830019', '客车01', '客车01', '客车01', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830020', '尊行', '尊行', '尊行', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830021', '帅豹', '帅豹', '帅豹', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830022', '微卡', '微卡', '微卡', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830023', '志翔', '志翔', '志翔', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830024', '悦翔', '悦翔', '悦翔', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830025', '悦翔V3', '悦翔V3', '悦翔V3', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830026', '悦翔V5', '悦翔V5', '悦翔V5', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830027', '悦翔V7', '悦翔V7', '悦翔V7', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830028', '新星卡', '新星卡', '新星卡', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830029', '新豹', '新豹', '新豹', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830030', '星卡', '星卡', '星卡', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830031', '星韵', '星韵', '星韵', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830032', '杰勋', '杰勋', '杰勋', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830033', '校车01', '校车01', '校车01', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830034', '校车02', '校车02', '校车02', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830035', '欧力威', '欧力威', '欧力威', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830036', '欧尚', '欧尚', '欧尚', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830037', '欧诺', '欧诺', '欧诺', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830038', '欧诺(基本型)', '欧诺(基本型)', '欧诺(基本型)', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830039', '欧诺(幸福型)', '欧诺(幸福型)', '欧诺(幸福型)', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830040', '欧雅', '欧雅', '欧雅', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830041', '睿行', '睿行', '睿行', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830042', '睿行M90', '睿行M90', '睿行M90', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830043', '睿行S50', '睿行S50', '睿行S50', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830044', '睿骋', '睿骋', '睿骋', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830045', '神童', '神童', '神童', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830046', '神骐', '神骐', '神骐', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830047', '神骐F30', '神骐F30', '神骐F30', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830048', '神骐F50', '神骐F50', '神骐F50', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830049', '神骐T20', '神骐T20', '神骐T20', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830050', '致尚XT', '致尚XT', '致尚XT', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830051', '跨越者', '跨越者', '跨越者', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830052', '逸动', '逸动', '逸动', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830053', '逸动XT(改装中网)', '逸动XT(改装中网)', '逸动XT(改装中网)', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830054', '逸动新能源', '逸动新能源', '逸动新能源', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830055', '金牛星', '金牛星', '金牛星', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830056', '镭蒙', '镭蒙', '镭蒙', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830057', '长安CM8', '长安CM8', '长安CM8', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830058', '长安之星', '长安之星', '长安之星', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830059', '长安之星(改款)', '长安之星(改款)', '长安之星(改款)', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830060', '长安之星(改装)', '长安之星(改装)', '长安之星(改装)', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830061', '长安之星2', '长安之星2', '长安之星2', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830062', '长安之星2(加装前杠)', '长安之星2(加装前杠)', '长安之星2(加装前杠)', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830063', '长安之星2(改款)', '长安之星2(改款)', '长安之星2(改款)', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830064', '长安之星3', '长安之星3', '长安之星3', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830065', '长安之星7', '长安之星7', '长安之星7', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830066', '长安之星9', '长安之星9', '长安之星9', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830067', '长安之星S460', '长安之星S460', '长安之星S460', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830068', '长安之星S460(1.0L版)', '长安之星S460(1.0L版)', '长安之星S460(1.0L版)', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830069', '长安星光4500', '长安星光4500', '长安星光4500', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830070', '长安星卡', '长安星卡', '长安星卡', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830071', '长安星卡S201(微卡)', '长安星卡S201(微卡)', '长安星卡S201(微卡)', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830072', '长安星卡S201(微面)', '长安星卡S201(微面)', '长安星卡S201(微面)', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02830073', '面包车', '面包车', '面包车', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02839999', '未知', '未知', '未知', '0283');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02840000', '新豹MINI', '新豹MINI', '新豹MINI', '0284');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02840001', '新豹王', '新豹王', '新豹王', '0284');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02840002', '跨越王', '跨越王', '跨越王', '0284');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02849999', '未知', '未知', '未知', '0284');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02850000', '2008', '2008', '2008', '0285');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02850001', '2010', '2010', '2010', '0285');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02859999', '未知', '未知', '未知', '0285');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02860000', '重卡', '重卡', '重卡', '0286');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02869999', '未知', '未知', '未知', '0286');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02870000', '逸酷EV', '逸酷EV', '逸酷EV', '0287');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02879999', '未知', '未知', '未知', '0287');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02880000', 'ALFA.4C', 'ALFA.4C', 'ALFA.4C', '0288');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02880001', 'GIULIA', 'GIULIA', 'GIULIA', '0288');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02880002', 'GIULIETTA', 'GIULIETTA', 'GIULIETTA', '0288');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02889999', '未知', '未知', '未知', '0288');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02890000', 'DB9', 'DB9', 'DB9', '0289');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02890001', 'RAPIDE', 'RAPIDE', 'RAPIDE', '0289');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02890002', 'TARAF', 'TARAF', 'TARAF', '0289');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02890003', 'V8.VANTAGE', 'V8.VANTAGE', 'V8.VANTAGE', '0289');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02890004', 'VANQUISH', 'VANQUISH', 'VANQUISH', '0289');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02899999', '未知', '未知', '未知', '0289');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02900000', 'V5', 'V5', 'V5', '0290');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02900001', '劲马', '劲马', '劲马', '0290');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02900002', '艾威', '艾威', '艾威', '0290');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02900003', '风尚', '风尚', '风尚', '0290');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02909999', '未知', '未知', '未知', '0290');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02910000', 'X5', 'X5', 'X5', '0291');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02910001', 'X6', 'X6', 'X6', '0291');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02910002', 'X7', 'X7', 'X7', '0291');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02910003', 'X8', 'X8', 'X8', '0291');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02910004', 'X9', 'X9', 'X9', '0291');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02910005', '逍遥', '逍遥', '逍遥', '0291');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02910006', '风华', '风华', '风华', '0291');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02910007', '风尚', '风尚', '风尚', '0291');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02919999', '未知', '未知', '未知', '0291');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920000', '华山', '华山', '华山', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920001', '华康', '华康', '华康', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920002', '奥龙加强版', '奥龙加强版', '奥龙加强版', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920003', '奥龙重卡', '奥龙重卡', '奥龙重卡', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920004', '奥龙重卡(牵引车)', '奥龙重卡(牵引车)', '奥龙重卡(牵引车)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920005', '奥龙重卡(自卸车)', '奥龙重卡(自卸车)', '奥龙重卡(自卸车)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920006', '德龙F2000(牵引车)', '德龙F2000(牵引车)', '德龙F2000(牵引车)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920007', '德龙F2000(牵引车)改', '德龙F2000(牵引车)改', '德龙F2000(牵引车)改', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920008', '德龙F2000(自卸车)', '德龙F2000(自卸车)', '德龙F2000(自卸车)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920009', '德龙F3000重卡(大货)', '德龙F3000重卡(大货)', '德龙F3000重卡(大货)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920010', '德龙F3000重卡(特大货)', '德龙F3000重卡(特大货)', '德龙F3000重卡(特大货)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920011', '德龙F3000重卡牵引车头(大货)', '德龙F3000重卡牵引车头(大货)', '德龙F3000重卡牵引车头(大货)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920012', '德龙L3000(大货)', '德龙L3000(大货)', '德龙L3000(大货)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920013', '德龙L3000(特大货)', '德龙L3000(特大货)', '德龙L3000(特大货)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920014', '德龙M3000重卡', '德龙M3000重卡', '德龙M3000重卡', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920015', '德龙X3000', '德龙X3000', '德龙X3000', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920016', '德龙X3000重载版', '德龙X3000重载版', '德龙X3000重载版', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920017', '德龙X3000黄金版', '德龙X3000黄金版', '德龙X3000黄金版', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920018', '德龙X3000黄金版(550马力)', '德龙X3000黄金版(550马力)', '德龙X3000黄金版(550马力)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920019', '德龙X3000黄金版(550马力牵引车头)', '德龙X3000黄金版(550马力牵引车头)', '德龙X3000黄金版(550马力牵引车头)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920020', '德龙新M3000(特大货)', '德龙新M3000(特大货)', '德龙新M3000(特大货)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920021', '德龙新M3000牵引车头(大货)', '德龙新M3000牵引车头(大货)', '德龙新M3000牵引车头(大货)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920022', '德龙新M3000自卸车(大货)', '德龙新M3000自卸车(大货)', '德龙新M3000自卸车(大货)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920023', '德龙新M3000重卡(大货)', '德龙新M3000重卡(大货)', '德龙新M3000重卡(大货)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920024', '德龙新M3000重卡(特大货)', '德龙新M3000重卡(特大货)', '德龙新M3000重卡(特大货)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920025', '轩德', '轩德', '轩德', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920026', '轩德E9', '轩德E9', '轩德E9', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920027', '轩德X6自卸车(大货)', '轩德X6自卸车(大货)', '轩德X6自卸车(大货)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920028', '轩德X6自卸车(小货)', '轩德X6自卸车(小货)', '轩德X6自卸车(小货)', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02920029', '轩德X9', '轩德X9', '轩德X9', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02929999', '未知', '未知', '未知', '0292');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02930000', '福家', '福家', '福家', '0293');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02930001', '龙锐', '龙锐', '龙锐', '0293');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02939999', '未知', '未知', '未知', '0293');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940000', 'C4L(精英型)', 'C4L(精英型)', 'C4L(精英型)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940001', 'CITYEXPRESS(进口)', 'CITYEXPRESS(进口)', 'CITYEXPRESS(进口)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940002', 'ORLANDO(进口)', 'ORLANDO(进口)', 'ORLANDO(进口)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940003', 'SONIC(进口)', 'SONIC(进口)', 'SONIC(进口)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940004', 'TRAILBLAZER(进口)(PremierConcept)', 'TRAILBLAZER(进口)(PremierConcept)', 'TRAILBLAZER(进口)(PremierConcept)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940005', 'TRAILBLAZER(进口)(基本型)', 'TRAILBLAZER(进口)(基本型)', 'TRAILBLAZER(进口)(基本型)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940006', 'TRU(进口)', 'TRU(进口)', 'TRU(进口)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940007', '乐风', '乐风', '乐风', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940008', '乐风RV', '乐风RV', '乐风RV', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940009', '乐骋', '乐骋', '乐骋', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940010', '创酷', '创酷', '创酷', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940011', '库罗德(进口)', '库罗德(进口)', '库罗德(进口)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940012', '开拓者', '开拓者', '开拓者', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940013', '探界者(进口)', '探界者(进口)', '探界者(进口)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940014', '斯帕可', '斯帕可', '斯帕可', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940015', '斯帕可(进口)', '斯帕可(进口)', '斯帕可(进口)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940016', '景程', '景程', '景程', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940017', '景程(改装)', '景程(改装)', '景程(改装)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940018', '景程(致真版)', '景程(致真版)', '景程(致真版)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940019', '爱唯欧', '爱唯欧', '爱唯欧', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940020', '科尔维特', '科尔维特', '科尔维特', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940021', '科尔维特(Z06)', '科尔维特(Z06)', '科尔维特(Z06)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940022', '科尔维特(进口)', '科尔维特(进口)', '科尔维特(进口)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940023', '科帕奇', '科帕奇', '科帕奇', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940024', '科帕奇(进口)', '科帕奇(进口)', '科帕奇(进口)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940025', '科沃兹', '科沃兹', '科沃兹', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940026', '科迈罗', '科迈罗', '科迈罗', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940027', '科迈罗(进口)', '科迈罗(进口)', '科迈罗(进口)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940028', '科鲁兹', '科鲁兹', '科鲁兹', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940029', '科鲁兹(先锋版)', '科鲁兹(先锋版)', '科鲁兹(先锋版)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940030', '科鲁兹(手动时尚版)', '科鲁兹(手动时尚版)', '科鲁兹(手动时尚版)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940031', '科鲁兹(手动精英)', '科鲁兹(手动精英)', '科鲁兹(手动精英)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940032', '科鲁兹(掀背)', '科鲁兹(掀背)', '科鲁兹(掀背)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940033', '科鲁兹(改款)', '科鲁兹(改款)', '科鲁兹(改款)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940034', '科鲁兹(改装)', '科鲁兹(改装)', '科鲁兹(改装)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940035', '科鲁兹(改装中网)', '科鲁兹(改装中网)', '科鲁兹(改装中网)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940036', '科鲁兹(炫锋版,领锋版)', '科鲁兹(炫锋版,领锋版)', '科鲁兹(炫锋版,领锋版)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940037', '科鲁兹(经典)', '科鲁兹(经典)', '科鲁兹(经典)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940038', '科鲁兹(经典版)', '科鲁兹(经典版)', '科鲁兹(经典版)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940039', '索罗德(改款)', '索罗德(改款)', '索罗德(改款)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940040', '索罗德(进口)', '索罗德(进口)', '索罗德(进口)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940041', '赛欧', '赛欧', '赛欧', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940042', '赛欧未知', '赛欧未知', '赛欧未知', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940043', '迈锐宝', '迈锐宝', '迈锐宝', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940044', '迈锐宝(改款)', '迈锐宝(改款)', '迈锐宝(改款)', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940045', '迈锐宝XL', '迈锐宝XL', '迈锐宝XL', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02940046', '雪崩', '雪崩', '雪崩', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02949999', '未知', '未知', '未知', '0294');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950000', 'C2', 'C2', 'C2', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950001', 'C3XR', 'C3XR', 'C3XR', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950002', 'C4L', 'C4L', 'C4L', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950003', 'C4PICASSO(进口)', 'C4PICASSO(进口)', 'C4PICASSO(进口)', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950004', 'C4SUV', 'C4SUV', 'C4SUV', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950005', 'C4世嘉', 'C4世嘉', 'C4世嘉', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950006', 'C4毕加索', 'C4毕加索', 'C4毕加索', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950007', 'C5', 'C5', 'C5', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950008', 'C6(进口)', 'C6(进口)', 'C6(进口)', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950009', 'CZERO(进口)', 'CZERO(进口)', 'CZERO(进口)', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950010', 'GT(进口)', 'GT(进口)', 'GT(进口)', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950011', 'Technospace(进口)', 'Technospace(进口)', 'Technospace(进口)', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950012', '世嘉', '世嘉', '世嘉', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950013', '凯旋', '凯旋', '凯旋', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950014', '凯旋(改款)', '凯旋(改款)', '凯旋(改款)', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950015', '毕加索', '毕加索', '毕加索', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950016', '爱丽舍', '爱丽舍', '爱丽舍', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950017', '爱丽舍(改款)', '爱丽舍(改款)', '爱丽舍(改款)', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02950018', '爱丽舍(海外)(进口)', '爱丽舍(海外)(进口)', '爱丽舍(海外)(进口)', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02959999', '未知', '未知', '未知', '0295');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02960000', 'D50', 'D50', 'D50', '0296');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02960001', 'D70', 'D70', 'D70', '0296');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02960002', 'D80', 'D80', 'D80', '0296');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02960003', 'S50', 'S50', 'S50', '0296');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02960004', '小王子', '小王子', '小王子', '0296');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02960005', '小骑士', '小骑士', '小骑士', '0296');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02969999', '未知', '未知', '未知', '0296');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970000', '2012', '2012', '2012', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970001', '2014', '2014', '2014', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970002', 'CT', 'CT', 'CT', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970003', 'CT(精英版)', 'CT(精英版)', 'CT(精英版)', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970004', 'ES', 'ES', 'ES', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970005', 'GS', 'GS', 'GS', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970006', 'GS(250FSPORT)', 'GS(250FSPORT)', 'GS(250FSPORT)', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970007', 'GS(450h)', 'GS(450h)', 'GS(450h)', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970008', 'GSF', 'GSF', 'GSF', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970009', 'GX', 'GX', 'GX', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970010', 'IS', 'IS', 'IS', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970011', 'IS(FSPORT)', 'IS(FSPORT)', 'IS(FSPORT)', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970012', 'ISF', 'ISF', 'ISF', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970013', 'LC', 'LC', 'LC', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970014', 'LS', 'LS', 'LS', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970015', 'LX', 'LX', 'LX', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970016', 'NX', 'NX', 'NX', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970017', 'RC', 'RC', 'RC', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970018', 'RCF', 'RCF', 'RCF', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970019', 'RX', 'RX', 'RX', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970020', 'RX(450h)', 'RX(450h)', 'RX(450h)', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02970021', 'RX(运动版)', 'RX(运动版)', 'RX(运动版)', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02979999', '未知', '未知', '未知', '0297');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980000', 'Alaskan', 'Alaskan', 'Alaskan', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980001', 'Clio(RS)', 'Clio(RS)', 'Clio(RS)', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980002', 'Clio(基本款)', 'Clio(基本款)', 'Clio(基本款)', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980003', 'DUSTER', 'DUSTER', 'DUSTER', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980004', 'KERAX', 'KERAX', 'KERAX', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980005', 'KERAX系列重卡', 'KERAX系列重卡', 'KERAX系列重卡', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980006', 'Kangoobebop', 'Kangoobebop', 'Kangoobebop', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980007', 'RSpace', 'RSpace', 'RSpace', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980008', 'ZOE', 'ZOE', 'ZOE', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980009', '卡缤', '卡缤', '卡缤', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980010', '塔利斯曼', '塔利斯曼', '塔利斯曼', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980011', '威赛帝', '威赛帝', '威赛帝', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980012', '拉古娜(古贝)', '拉古娜(古贝)', '拉古娜(古贝)', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980013', '拉古那', '拉古那', '拉古那', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980014', '梅甘娜', '梅甘娜', '梅甘娜', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980015', '梅甘娜(CC)', '梅甘娜(CC)', '梅甘娜(CC)', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980016', '梅甘娜(Coupe)', '梅甘娜(Coupe)', '梅甘娜(Coupe)', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980017', '梅甘娜(GT)', '梅甘娜(GT)', '梅甘娜(GT)', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980018', '梅甘娜(GT220)', '梅甘娜(GT220)', '梅甘娜(GT220)', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980019', '梅甘娜(RS)', '梅甘娜(RS)', '梅甘娜(RS)', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980020', '梅甘娜(基本款)', '梅甘娜(基本款)', '梅甘娜(基本款)', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980021', '科雷傲', '科雷傲', '科雷傲', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980022', '科雷嘉', '科雷嘉', '科雷嘉', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980023', '纬度', '纬度', '纬度', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980024', '雷诺4Luxe', '雷诺4Luxe', '雷诺4Luxe', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980025', '风景', '风景', '风景', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980026', '风景(XMOD)', '风景(XMOD)', '风景(XMOD)', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980027', '风景(四驱)', '风景(四驱)', '风景(四驱)', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02980028', '风朗', '风朗', '风朗', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02989999', '未知', '未知', '未知', '0298');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02990000', 'Commodore', 'Commodore', 'Commodore', '0299');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02990001', '霍顿Trailblazer', '霍顿Trailblazer', '霍顿Trailblazer', '0299');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('02999999', '未知', '未知', '未知', '0299');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000000', 'JH6(大货)', 'JH6(大货)', 'JH6(大货)', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000001', 'JH6(特大货)', 'JH6(特大货)', 'JH6(特大货)', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000002', '天V', '天V', '天V', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000003', '悍V重卡', '悍V重卡', '悍V重卡', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000004', '悍威J5M重卡', '悍威J5M重卡', '悍威J5M重卡', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000005', '新大威', '新大威', '新大威', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000006', '新大威重卡', '新大威重卡', '新大威重卡', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000007', '新大威重卡336', '新大威重卡336', '新大威重卡336', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000008', '赛虎', '赛虎', '赛虎', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000009', '赛麒麟', '赛麒麟', '赛麒麟', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000010', '赛龙中卡', '赛龙中卡', '赛龙中卡', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000011', '陆V中卡', '陆V中卡', '陆V中卡', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000012', '骏威J5K', '骏威J5K', '骏威J5K', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000013', '骏威J5K中卡(中货)', '骏威J5K中卡(中货)', '骏威J5K中卡(中货)', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000014', '骏威J5K中卡(大货)', '骏威J5K中卡(大货)', '骏威J5K中卡(大货)', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000015', '龙V', '龙V', '龙V', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000016', '龙V中卡(大货)', '龙V中卡(大货)', '龙V中卡(大货)', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000017', '龙V中卡(特大货)', '龙V中卡(特大货)', '龙V中卡(特大货)', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03000018', '龙V中卡牵引车头(大货)', '龙V中卡牵引车头(大货)', '龙V中卡牵引车头(大货)', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03009999', '未知', '未知', '未知', '0300');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03010000', 'JNP6120FNV3', 'JNP6120FNV3', 'JNP6120FNV3', '0301');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03010001', 'JNP6120L', 'JNP6120L', 'JNP6120L', '0301');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03010002', 'JNP6121KE', 'JNP6121KE', 'JNP6121KE', '0301');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03010003', 'JNP6122DNV1', 'JNP6122DNV1', 'JNP6122DNV1', '0301');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03010004', 'JNP6127V1', 'JNP6127V1', 'JNP6127V1', '0301');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03010005', 'JNP6128WK', 'JNP6128WK', 'JNP6128WK', '0301');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03010006', 'JNP6140FM3', 'JNP6140FM3', 'JNP6140FM3', '0301');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03010007', 'JNP6850', 'JNP6850', 'JNP6850', '0301');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03010008', '大巴03', '大巴03', '大巴03', '0301');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03019999', '未知', '未知', '未知', '0301');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03020000', 'i1', 'i1', 'i1', '0302');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03029999', '未知', '未知', '未知', '0302');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03030000', 'FD3163P8K4', 'FD3163P8K4', 'FD3163P8K4', '0303');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03039999', '未知', '未知', '未知', '0303');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03040000', 'X6系列', 'X6系列', 'X6系列', '0304');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03049999', '未知', '未知', '未知', '0304');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03050000', 'FSQ6129DYW', 'FSQ6129DYW', 'FSQ6129DYW', '0305');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03059999', '未知', '未知', '未知', '0305');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060000', '323', '323', '323', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060001', 'CX4', 'CX4', 'CX4', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060002', 'CX5', 'CX5', 'CX5', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060003', 'CX5(改装中网)', 'CX5(改装中网)', 'CX5(改装中网)', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060004', 'CX7', 'CX7', 'CX7', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060005', 'CX7(进口)', 'CX7(进口)', 'CX7(进口)', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060006', 'CX9', 'CX9', 'CX9', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060007', 'Familia323', 'Familia323', 'Familia323', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060008', 'Hazumi', 'Hazumi', 'Hazumi', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060009', 'KOERU越', 'KOERU越', 'KOERU越', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060010', 'MX5', 'MX5', 'MX5', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060011', 'MX5(GT)', 'MX5(GT)', 'MX5(GT)', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060012', 'Millenia', 'Millenia', 'Millenia', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060013', 'RX8', 'RX8', 'RX8', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060014', 'Shinari', 'Shinari', 'Shinari', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060015', 'Takeri', 'Takeri', 'Takeri', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060016', '昂克赛拉', '昂克赛拉', '昂克赛拉', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060017', '昂科塞拉', '昂科塞拉', '昂科塞拉', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060018', '睿翼', '睿翼', '睿翼', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060019', '睿翼(改装中网)', '睿翼(改装中网)', '睿翼(改装中网)', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060020', '睿翼(轿跑)', '睿翼(轿跑)', '睿翼(轿跑)', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060021', '睿翼(轿跑改装中网)', '睿翼(轿跑改装中网)', '睿翼(轿跑改装中网)', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060022', '福美来', '福美来', '福美来', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060023', '阿特兹', '阿特兹', '阿特兹', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060024', '阿特兹(改装中网)', '阿特兹(改装中网)', '阿特兹(改装中网)', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060025', '马自达2', '马自达2', '马自达2', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060026', '马自达2(三厢)', '马自达2(三厢)', '马自达2(三厢)', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060027', '马自达3', '马自达3', '马自达3', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060028', '马自达3(进口)', '马自达3(进口)', '马自达3(进口)', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060029', '马自达3(进口)(标准型)', '马自达3(进口)(标准型)', '马自达3(进口)(标准型)', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060030', '马自达3星骋', '马自达3星骋', '马自达3星骋', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060031', '马自达5', '马自达5', '马自达5', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060032', '马自达6', '马自达6', '马自达6', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060033', '马自达6(改装)', '马自达6(改装)', '马自达6(改装)', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060034', '马自达6(轿跑)', '马自达6(轿跑)', '马自达6(轿跑)', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060035', '马自达6(轿跑改装)', '马自达6(轿跑改装)', '马自达6(轿跑改装)', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060036', '马自达8', '马自达8', '马自达8', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060037', '马自达8(精英版)', '马自达8(精英版)', '马自达8(精英版)', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03060038', '马自达8(进口)', '马自达8(进口)', '马自达8(进口)', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03069999', '未知', '未知', '未知', '0306');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03070000', 'DD3140BCK1', 'DD3140BCK1', 'DD3140BCK1', '0307');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03070001', 'DD6129K70', 'DD6129K70', 'DD6129K70', '0307');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03070002', 'DD6896K13', 'DD6896K13', 'DD6896K13', '0307');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03070003', 'N1', 'N1', 'N1', '0307');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03070004', 'N1S', 'N1S', 'N1S', '0307');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03070005', 'N2(1)', 'N2(1)', 'N2(1)', '0307');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03070006', 'N2(2)', 'N2(2)', 'N2(2)', '0307');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03070007', '傲骏', '傲骏', '傲骏', '0307');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03070008', '傲骏(2.2L)', '傲骏(2.2L)', '傲骏(2.2L)', '0307');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03070009', '大柴神', '大柴神', '大柴神', '0307');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03070010', '小柴神', '小柴神', '小柴神', '0307');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03070011', '挑战者SUV', '挑战者SUV', '挑战者SUV', '0307');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03070012', '旗胜F1', '旗胜F1', '旗胜F1', '0307');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03070013', '旗胜V3(豪华版)', '旗胜V3(豪华版)', '旗胜V3(豪华版)', '0307');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03070014', '翱龙CUV', '翱龙CUV', '翱龙CUV', '0307');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03070015', '领航者', '领航者', '领航者', '0307');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03079999', '未知', '未知', '未知', '0307');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03080000', '低速货车', '低速货车', '低速货车', '0308');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03089999', '未知', '未知', '未知', '0308');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03090000', '中巴BWC6602G', '中巴BWC6602G', '中巴BWC6602G', '0309');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('03099999', '未知', '未知', '未知', '0309');
INSERT INTO `orbit_vehicle_brand_child` VALUES ('99999999', '未知', '未知', '未知', '9999');

-- ----------------------------
-- Table structure for orbit_vehicle_color
-- ----------------------------
DROP TABLE IF EXISTS `orbit_vehicle_color`;
CREATE TABLE `orbit_vehicle_color` (
  `CODE` varchar(64) NOT NULL COMMENT '颜色代码',
  `ZH_NAME` varchar(64) DEFAULT NULL COMMENT '中文名',
  `EN_NAME` varchar(64) DEFAULT NULL COMMENT '英文名',
  `ES_NAME` varchar(64) DEFAULT NULL COMMENT '西班牙语',
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='颜色表';

-- ----------------------------
-- Records of orbit_vehicle_color
-- ----------------------------
INSERT INTO `orbit_vehicle_color` VALUES ('0', '棕', 'Brown', null);
INSERT INTO `orbit_vehicle_color` VALUES ('1', '橙', 'Orange', null);
INSERT INTO `orbit_vehicle_color` VALUES ('10', '银', 'Silvery', null);
INSERT INTO `orbit_vehicle_color` VALUES ('11', '青', 'Cyan', null);
INSERT INTO `orbit_vehicle_color` VALUES ('12', '黄', 'Yellow', null);
INSERT INTO `orbit_vehicle_color` VALUES ('13', '黑', 'Black', null);
INSERT INTO `orbit_vehicle_color` VALUES ('14', '黄绿', 'Yellow-green', null);
INSERT INTO `orbit_vehicle_color` VALUES ('15', '白绿', 'White-green', null);
INSERT INTO `orbit_vehicle_color` VALUES ('2', '灰', 'Gray', null);
INSERT INTO `orbit_vehicle_color` VALUES ('3', '白', 'White', null);
INSERT INTO `orbit_vehicle_color` VALUES ('4', '粉', 'Pink', null);
INSERT INTO `orbit_vehicle_color` VALUES ('5', '紫', 'Purple', null);
INSERT INTO `orbit_vehicle_color` VALUES ('6', '红', 'Red', null);
INSERT INTO `orbit_vehicle_color` VALUES ('7', '绿', 'Green', null);
INSERT INTO `orbit_vehicle_color` VALUES ('8', '蓝', 'Blue', null);
INSERT INTO `orbit_vehicle_color` VALUES ('9', '金', 'Golden', null);
INSERT INTO `orbit_vehicle_color` VALUES ('9999', '未知', 'UnKnow', null);

-- ----------------------------
-- Table structure for orbit_vehicle_feature
-- ----------------------------
DROP TABLE IF EXISTS `orbit_vehicle_feature`;
CREATE TABLE `orbit_vehicle_feature` (
  `CODE` varchar(64) NOT NULL COMMENT '车辆特征代码',
  `ZH_NAME` varchar(64) DEFAULT NULL COMMENT '中文名',
  `EN_NAME` varchar(64) DEFAULT NULL COMMENT '英文名',
  `ES_NAME` varchar(64) DEFAULT NULL COMMENT '西班牙语',
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆特征表';

-- ----------------------------
-- Records of orbit_vehicle_feature
-- ----------------------------
INSERT INTO `orbit_vehicle_feature` VALUES ('0', '司机', 'driver', null);
INSERT INTO `orbit_vehicle_feature` VALUES ('1', '人脸', 'face', null);
INSERT INTO `orbit_vehicle_feature` VALUES ('10', '行李架', 'holder', null);
INSERT INTO `orbit_vehicle_feature` VALUES ('11', '抽烟', 'smoke', null);
INSERT INTO `orbit_vehicle_feature` VALUES ('2', '安全带', 'belt', null);
INSERT INTO `orbit_vehicle_feature` VALUES ('3', '遮阳板', 'sunShield', null);
INSERT INTO `orbit_vehicle_feature` VALUES ('4', '年检标', 'tag', null);
INSERT INTO `orbit_vehicle_feature` VALUES ('5', '装饰物', 'decoration', null);
INSERT INTO `orbit_vehicle_feature` VALUES ('6', '餐巾盒', 'napkinBox', null);
INSERT INTO `orbit_vehicle_feature` VALUES ('7', '转经筒', 'zhuanjt', null);
INSERT INTO `orbit_vehicle_feature` VALUES ('8', '电话', 'callPhone', null);
INSERT INTO `orbit_vehicle_feature` VALUES ('9', '天窗', 'sunRoof', null);

-- ----------------------------
-- Table structure for orbit_vehicle_type
-- ----------------------------
DROP TABLE IF EXISTS `orbit_vehicle_type`;
CREATE TABLE `orbit_vehicle_type` (
  `CODE` varchar(64) NOT NULL COMMENT '车辆类型代码',
  `ZH_NAME` varchar(64) DEFAULT NULL COMMENT '中文名',
  `EN_NAME` varchar(64) DEFAULT NULL COMMENT '英文名',
  `ES_NAME` varchar(64) DEFAULT NULL COMMENT '西班牙语',
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆类型表';

-- ----------------------------
-- Records of orbit_vehicle_type
-- ----------------------------
INSERT INTO `orbit_vehicle_type` VALUES ('0', '轿车', '轿车', '轿车');
INSERT INTO `orbit_vehicle_type` VALUES ('1', 'SUV', 'SUV', 'SUV');
INSERT INTO `orbit_vehicle_type` VALUES ('10', '三轮汽车', '三轮汽车', '三轮汽车');
INSERT INTO `orbit_vehicle_type` VALUES ('11', '微型货车', '微型货车', '微型货车');
INSERT INTO `orbit_vehicle_type` VALUES ('12', '轻型货车', '轻型货车', '轻型货车');
INSERT INTO `orbit_vehicle_type` VALUES ('13', '中型货车', '中型货车', '中型货车');
INSERT INTO `orbit_vehicle_type` VALUES ('14', '重型货车', '重型货车', '重型货车');
INSERT INTO `orbit_vehicle_type` VALUES ('15', '吊车', '吊车', '吊车');
INSERT INTO `orbit_vehicle_type` VALUES ('2', 'MPV', 'MPV', 'MPV');
INSERT INTO `orbit_vehicle_type` VALUES ('3', '微型面包车', '微型面包车', '微型面包车');
INSERT INTO `orbit_vehicle_type` VALUES ('4', '皮卡', '皮卡', '皮卡');
INSERT INTO `orbit_vehicle_type` VALUES ('5', '新能源车', '新能源车', '新能源车');
INSERT INTO `orbit_vehicle_type` VALUES ('6', '老年代步车', '老年代步车', '老年代步车');
INSERT INTO `orbit_vehicle_type` VALUES ('7', '小型客车', '小型客车', '小型客车');
INSERT INTO `orbit_vehicle_type` VALUES ('8', '中型客车', '中型客车', '中型客车');
INSERT INTO `orbit_vehicle_type` VALUES ('9', '大型客车', '大型客车', '大型客车');
