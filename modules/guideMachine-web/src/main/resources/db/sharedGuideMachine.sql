DROP DATABASE IF EXISTS `sharedGuideMachine`;
CREATE DATABASE `sharedGuideMachine`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';
USE `sharedGuideMachine`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_base_cabinet`
-- ----------------------------
DROP TABLE IF EXISTS `t_base_cabinet`;
CREATE TABLE `t_base_cabinet` (
  `id` varchar(64) NOT NULL COMMENT '机柜id',
  `address` varchar(64) DEFAULT NULL COMMENT '地址',
  `longitude` varchar(64) DEFAULT NULL COMMENT '经度',
  `dimension` varchar(64) DEFAULT NULL COMMENT '维度',
  `scenicSpotId` varchar(64) DEFAULT NULL COMMENT '景点id',
  `installTime` datetime DEFAULT NULL COMMENT '安装日期',
  `cabinetStatus` varchar(64) DEFAULT NULL COMMENT '机柜状态(0正常、1异常)',
  `channelNumber` int(11) DEFAULT NULL  COMMENT '仓道数',
  `softVersion` varchar(64) DEFAULT NULL COMMENT 'APK版本',
  `hardwareVersion` varchar(64) DEFAULT NULL COMMENT '硬件版本',
  `createUser` varchar(64) DEFAULT NULL COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '1' COMMENT '状态(0正常、1废弃)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机柜表';

-- ----------------------------
-- Records of t_base_cabinet
-- ----------------------------

-- ----------------------------
-- Table structure for `t_base_channel`
-- ----------------------------
DROP TABLE IF EXISTS `t_base_channel`;
CREATE TABLE `t_base_channel` (
  `id` varchar(64) NOT NULL COMMENT '仓道id',
  `cabinetId` varchar(64) DEFAULT NULL COMMENT '机柜id',
  `channelStatus` varchar(64) DEFAULT NULL COMMENT '仓道状态(0正常、1异常)',
  `position` varchar(64) DEFAULT NULL COMMENT '仓道在机柜位置',
  `guideMachineStatus` varchar(64) DEFAULT NULL COMMENT '导游机在仓状态(在仓则显示导游机编号、2空仓)',
  `createUser` varchar(64) DEFAULT NULL COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '1' COMMENT '状态(0正常、1废弃)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓道表';

-- ----------------------------
-- Records of t_base_channel
-- ----------------------------

-- ----------------------------
-- Table structure for `t_base_guideMachine`
-- ----------------------------
DROP TABLE IF EXISTS `t_base_guideMachine`;
CREATE TABLE `t_base_guideMachine` (
  `id` varchar(64) NOT NULL COMMENT '导游机id',
  `cabinetId` varchar(64) DEFAULT NULL COMMENT '机柜id',
  `channelId` varchar(64) DEFAULT NULL COMMENT '仓道id',
  `imetNo` varchar(64) DEFAULT NULL COMMENT '导游机IMEI',
  `guideMachineNo` varchar(64) DEFAULT NULL COMMENT '导游机编号',
  `guideMachineType` varchar(64) DEFAULT NULL COMMENT '机型',
  `installTime` datetime DEFAULT NULL COMMENT '安装日期',
  `temperatureStatus` varchar(64) DEFAULT NULL COMMENT '温度状态(正常数值、-1异常)',
  `electricityStatus` varchar(64) DEFAULT NULL COMMENT '电量状态(正常数值、-1异常)',
  `machineStatus` varchar(64) DEFAULT NULL COMMENT '设备状态(0正常、2损坏、1异常、3丢失)',
  `rentStatus` varchar(64) DEFAULT NULL COMMENT '租用状态(0可租、2不可租、1已租)',
  `positionStatus` varchar(64) DEFAULT NULL COMMENT '位置状态(0正常、1异常、2空)',
  `createUser` varchar(64) DEFAULT NULL COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '1' COMMENT '状态(0正常、1废弃)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='导游机表';

-- ----------------------------
-- Records of t_base_guideMachine
-- ----------------------------

-- ----------------------------
-- Table structure for `t_base_scenicSpot`
-- ----------------------------
DROP TABLE IF EXISTS `t_base_scenicSpot`;
CREATE TABLE `t_base_scenicSpot` (
  `id` varchar(64) NOT NULL COMMENT '景点id',
  `province` varchar(64) DEFAULT NULL COMMENT '省份',
  `city` varchar(64) DEFAULT NULL COMMENT '城市',
  `county` varchar(64) DEFAULT NULL COMMENT '区县',
  `scenicSpotName` varchar(64) DEFAULT NULL COMMENT '景点名称',
  `address` varchar(64) DEFAULT NULL COMMENT '地址',
  `longitude` varchar(64) DEFAULT NULL COMMENT '经度',
  `dimension` varchar(64) DEFAULT NULL COMMENT '维度',
  `rentAmount` int(11) DEFAULT NULL COMMENT '租金',
  `depositAmount` int(11) DEFAULT NULL COMMENT '押金',
  `companyId` varchar(64) NOT NULL COMMENT '公司id',
  `createUser` varchar(64) DEFAULT NULL COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '1' COMMENT '状态(0正常、1废弃)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='景点主表';

-- ----------------------------
-- Records of t_base_scenicSpot
-- ----------------------------

-- ----------------------------
-- Table structure for `t_base_scenicSpotEntrance`
-- ----------------------------
DROP TABLE IF EXISTS `t_base_scenicSpotEntrance`;
CREATE TABLE `t_base_scenicSpotEntrance` (
  `id` varchar(64) NOT NULL COMMENT '景点出入口id',
  `scenicSpotId` varchar(64) NOT NULL COMMENT '景点id',
  `entranceType` varchar(64) DEFAULT NULL COMMENT '类型(0出口、1入口)',
  `address` varchar(64) DEFAULT NULL COMMENT '地址',
  `longitude` varchar(64) DEFAULT NULL COMMENT '经度',
  `dimension` varchar(64) DEFAULT NULL COMMENT '维度',
  `createUser` varchar(64) DEFAULT NULL COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '1' COMMENT '状态(0正常、1废弃)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='景点出入口表';

-- ----------------------------
-- Records of t_base_scenicSpotEntrance
-- ----------------------------

-- ----------------------------
-- Table structure for `t_biz_rentOrder`
-- ----------------------------
DROP TABLE IF EXISTS `t_biz_rentOrder`;
CREATE TABLE `t_biz_rentOrder` (
  `id` varchar(64) NOT NULL COMMENT '租借订单id',
  `orderNo` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `guideMachineNo` varchar(64) DEFAULT NULL COMMENT '导游机编号',
  `rentType` varchar(64) DEFAULT NULL COMMENT '租借方式(0小程序、1身份证)',
  `phone` varchar(64) DEFAULT NULL COMMENT '手机',
  `identityCard` varchar(64) DEFAULT NULL COMMENT '身份证',
  `rentAmount` int(11) DEFAULT NULL COMMENT '租金',
  `depositAmount` int(11) DEFAULT NULL COMMENT '押金',
  `rentTime` datetime DEFAULT NULL COMMENT '租借时间',
  `returnTime` datetime DEFAULT NULL COMMENT '归还时间',
  `returnStatus` varchar(64) DEFAULT NULL COMMENT '归还状态(0未归还、1已归还)',
  `refundDepositStatus` varchar(64) DEFAULT NULL COMMENT '押金退还状态(0未退还、1已退还)',
  `refundRentStatus` varchar(64) DEFAULT NULL COMMENT '租金退还状态(0未退还、1已退还)',
  `refundRentRemark` varchar(64) DEFAULT NULL COMMENT '已退租金备注',
  `refundDepositRemark` varchar(64) DEFAULT NULL COMMENT '已退押金备注',
  `orderPayStatus` varchar(64) DEFAULT NULL COMMENT '订单支付状态(0未支付、1已支付)',
  `rentUser` varchar(64) DEFAULT NULL COMMENT '租借人员',
  `transactionNo` varchar(64) DEFAULT NULL COMMENT '交易编号',
  `visitorsId` varchar(64) DEFAULT NULL COMMENT '游客Id',
  `scenicSpotId` varchar(64) DEFAULT NULL COMMENT '景点Id',
  `cabinetId` varchar(64) DEFAULT NULL COMMENT '机柜Id',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '0' COMMENT '状态(0锁定、1租借中、2完成、3失败)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='租借订单表';

-- ----------------------------
-- Records of t_biz_rentOrder
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_visitors`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_visitors`;
CREATE TABLE `t_sys_visitors` (
  `id` varchar(64) NOT NULL COMMENT '游客id',
  `visitorsType` varchar(64) DEFAULT NULL COMMENT '游客等级(0普通、1VIP)',
  `registerTime` datetime DEFAULT NULL COMMENT '注册日期',
  `province` varchar(64) DEFAULT NULL COMMENT '注册省份',
  `city` varchar(64) DEFAULT NULL COMMENT '注册城市',
  `county` varchar(64) DEFAULT NULL COMMENT '注册区县',
  `scenicSpotId` varchar(64) DEFAULT NULL COMMENT '注册景点id',
  `phone` varchar(64) DEFAULT NULL COMMENT '手机',
  `identityCard` varchar(64) DEFAULT NULL COMMENT '身份证',
  `wechatNo` varchar(64) DEFAULT NULL COMMENT '微信号',
  `alipayNo` varchar(64) DEFAULT NULL COMMENT '支付宝号',
  `visitorsName` varchar(64) DEFAULT NULL COMMENT '游客姓名',
  `sex` varchar(64) DEFAULT NULL COMMENT '性别',
  `nativePlace` varchar(64) DEFAULT NULL COMMENT '籍贯',
  `nation` varchar(64) DEFAULT NULL COMMENT '民族',
  `birthDate` varchar(64) DEFAULT NULL COMMENT '出生年月',
  `homeAddress` varchar(64) DEFAULT NULL COMMENT '家庭地址',
  `rentNumber` int(11) DEFAULT NULL COMMENT '租借次数',
  `isBlacklist` varchar(64) DEFAULT NULL COMMENT '是否黑名单(0是、1否)',
  `lastRentTime` datetime DEFAULT NULL COMMENT '最近租借日期',
  `guideMachineNo` varchar(64) DEFAULT NULL COMMENT '导游机编号',
  `returnStatus` varchar(64) DEFAULT NULL COMMENT '归还状态(0未归还、1已归还)',
  `vipRemark` varchar(64) DEFAULT NULL COMMENT 'VIP说明',
  `accountAmount` int(11) DEFAULT NULL COMMENT '账户',
  `openId` varchar(64) DEFAULT NULL COMMENT '小程序标识',
  `sessionKey` varchar(64) DEFAULT NULL COMMENT '用户小程序sessionKey',
  `sKey` varchar(64) DEFAULT NULL COMMENT '用户小程序自定义登录态',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '1' COMMENT '状态(0正常、1废弃)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='游客表';

-- ----------------------------
-- Records of t_sys_visitors
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_transactionDetail`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_transactionDetail`;
CREATE TABLE `t_sys_transactionDetail` (
  `id` varchar(64) NOT NULL COMMENT '交易明细id',
  `visitorsId` varchar(64) DEFAULT NULL COMMENT '游客id',
  `transactioAmount` int(11) DEFAULT NULL COMMENT '交易金额',
  `transactioType` varchar(64) DEFAULT NULL COMMENT '交易类型(0充值、1提现、2退款)',
  `transactioMode` varchar(64) DEFAULT NULL COMMENT '方式(0微信、1支付宝、2现金)',
  `transactionNo` varchar(64) DEFAULT NULL COMMENT '交易编号',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '1' COMMENT '状态(0正常、1废弃)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易明细表';

-- ----------------------------
-- Records of t_sys_transactionDetail
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_rentHistory`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_rentHistory`;
CREATE TABLE `t_sys_rentHistory` (
  `id` varchar(64) NOT NULL COMMENT '租借历史id',
  `visitorsId` varchar(64) DEFAULT NULL COMMENT '游客id',
  `orderNo` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `province` varchar(64) DEFAULT NULL COMMENT '省份',
  `city` varchar(64) DEFAULT NULL COMMENT '城市',
  `county` varchar(64) DEFAULT NULL COMMENT '区县',
  `scenicSpotId` varchar(64) DEFAULT NULL COMMENT '景点id',
  `guideMachineNo` varchar(64) DEFAULT NULL COMMENT '导游机编号',
  `rentAmount` int(11) DEFAULT NULL COMMENT '租金',
  `depositAmount` int(11) DEFAULT NULL COMMENT '押金',
  `rentTime` datetime DEFAULT NULL COMMENT '租借时间',
  `returnTime` datetime DEFAULT NULL COMMENT '归还时间',
  `refundRentRemark` varchar(64) DEFAULT NULL COMMENT '已退租金备注',
  `refundDepositRemark` varchar(64) DEFAULT NULL COMMENT '已退押金备注',
  `userIds` varchar(64) DEFAULT NULL COMMENT '服务人员',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '1' COMMENT '状态(0正常、1废弃)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='租借历史表';

-- ----------------------------
-- Records of t_sys_rentHistory
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `id` varchar(64) NOT NULL COMMENT '员工id',
  `userName` varchar(64) DEFAULT NULL COMMENT '姓名',
  `loginName` varchar(64) DEFAULT NULL COMMENT '账号',
  `phone` varchar(64) DEFAULT NULL COMMENT '手机',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `openId` varchar(64) DEFAULT NULL COMMENT '员工微信对应openId',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '1' COMMENT '状态(0暂存、1离职、2在职)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工表';

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_userCityManage`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_userCityManage`;
CREATE TABLE `t_sys_userCityManage` (
  `id` varchar(64) NOT NULL COMMENT '员工管理城市id',
  `userId` varchar(64) DEFAULT NULL COMMENT '员工id',
  `province` varchar(64) DEFAULT NULL COMMENT '省份',
  `city` varchar(64) DEFAULT NULL COMMENT '城市',
  `county` varchar(64) DEFAULT NULL COMMENT '区县',
  `scenicSpotId` varchar(64) DEFAULT NULL COMMENT '景点id',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '1' COMMENT '状态(0正常、1废弃)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工管理城市表';

-- ----------------------------
-- Records of t_sys_userCityManage
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_userRole`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_userRole`;
CREATE TABLE `t_sys_userRole` (
  `id` varchar(64) NOT NULL COMMENT '员工角色id',
  `userId` varchar(64) DEFAULT NULL COMMENT '员工id',
  `roleId` varchar(64) DEFAULT NULL COMMENT '角色id',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '1' COMMENT '状态(0正常、1废弃)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工角色表';

-- ----------------------------
-- Records of t_sys_userRole
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `id` varchar(64) NOT NULL COMMENT '角色id',
  `roleName` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '1' COMMENT '状态(0正常、1废弃)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_permission`;
CREATE TABLE `t_sys_permission` (
  `id` varchar(64) NOT NULL COMMENT '权限id',
  `url` varchar(64) DEFAULT NULL COMMENT 'URL',
  `name` varchar(64) DEFAULT NULL COMMENT '权限名称',
  `number` varchar(64) DEFAULT NULL COMMENT '权限编码',
  `parentNumber` varchar(64) DEFAULT NULL COMMENT '父权限编码',
  `level` int(11) DEFAULT NULL COMMENT '级数',
  `isLeaf` varchar(64) DEFAULT NULL COMMENT '是否叶子节点',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `weight` int(11) DEFAULT NULL COMMENT '权重',
  `content` varchar(64) DEFAULT NULL COMMENT '模块内容',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '1' COMMENT '状态(0正常、1废弃)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of t_sys_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_permissionDivide`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_permissionDivide`;
CREATE TABLE `t_sys_permissionDivide` (
  `id` varchar(64) NOT NULL COMMENT '权限操作id',
  `name` varchar(64) DEFAULT NULL COMMENT '标签',
  `keyName` varchar(64) DEFAULT NULL COMMENT '键值',
  `module` varchar(64) DEFAULT NULL COMMENT '所属模块',
  `content` varchar(64) DEFAULT NULL COMMENT '模块内容',
  `weight` int(11) DEFAULT NULL COMMENT '权重',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '1' COMMENT '状态(0正常、1废弃)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限操作表';

-- ----------------------------
-- Records of t_sys_permissionDivide
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_rolePermissionDivide`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_rolePermissionDivide`;
CREATE TABLE `t_sys_rolePermissionDivide` (
  `id` varchar(64) NOT NULL COMMENT '角色权限操作id',
  `rolePermissionId` varchar(64) DEFAULT NULL COMMENT '角色权限Id',
  `keyNameId` varchar(64) DEFAULT NULL COMMENT '键值Id(dataDictionary数据字典的ID)',
  `name` varchar(64) DEFAULT NULL COMMENT '标签',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '1' COMMENT '状态(0正常、1废弃)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限操作表';

-- ----------------------------
-- Records of t_sys_rolePermissionDivide
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_rolePermission`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_rolePermission`;
CREATE TABLE `t_sys_rolePermission` (
  `id` varchar(64) NOT NULL COMMENT '角色权限id',
  `roleId` varchar(64) DEFAULT NULL COMMENT '角色id',
  `permissionId` varchar(64) DEFAULT NULL COMMENT '权限id',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '1' COMMENT '状态(0正常、1废弃)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of t_sys_rolePermission
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_company`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_company`;
CREATE TABLE `t_sys_company` (
  `id` varchar(64) NOT NULL COMMENT '公司id',
  `companyNo` varchar(64) DEFAULT NULL COMMENT '公司编码',
  `parentCompanyNo` varchar(64) DEFAULT NULL COMMENT '上级公司编码',
  `companyName` varchar(64) DEFAULT NULL COMMENT '公司名称',
  `province` varchar(64) DEFAULT NULL COMMENT '省份',
  `city` varchar(64) DEFAULT NULL COMMENT '城市',
  `county` varchar(64) DEFAULT NULL COMMENT '区县',
  `address` varchar(64) DEFAULT NULL COMMENT '地点',
  `phone` varchar(64) DEFAULT NULL COMMENT '联系电话',
  `wechatNo` varchar(64) DEFAULT NULL COMMENT '微信商户号',
  `alipayNo` varchar(64) DEFAULT NULL COMMENT '支付宝商户号',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `status` varchar(64) DEFAULT '1' COMMENT '状态(0正常、1废弃)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司表';

-- ----------------------------
-- Records of t_sys_company
-- ----------------------------

-- ----------------------------
-- View structure for `v_sys_userPermission_one`
-- ----------------------------
DROP VIEW IF EXISTS `v_sys_userPermission_one`;
CREATE VIEW `v_sys_userPermission_one` AS 
SELECT
	ur.userId,
	rp.permissionId,
	1 AS flag
FROM
	t_sys_userRole ur,
	t_sys_rolePermission rp
WHERE
	ur.roleId = rp.roleId
AND ur. STATUS = '0'
AND rp. STATUS = '0'
UNION
	(
		SELECT
			b.id AS userId,
			a.id AS permissionId,
			1 AS flag
		FROM
			t_sys_permission a,
			t_sys_user b
		WHERE
			b. STATUS = '99'
		AND a. STATUS = '3'
	);

-- ----------------------------
-- View structure for `v_sys_userPermission_two`
-- ----------------------------
DROP VIEW IF EXISTS `v_sys_userPermission_two`;
CREATE VIEW `v_sys_userPermission_two` AS 
SELECT
	c.userId,
	c.permissionId,
	CASE
WHEN SUM(c.flag) > 0 THEN
	1
ELSE
	0
END AS flag
FROM
	v_sys_userPermission_one c
GROUP BY
	c.userId,
	c.permissionId;
	
-- ----------------------------
-- View structure for `v_sys_userPermission`
-- ----------------------------
DROP VIEW IF EXISTS `v_sys_userPermission`;
CREATE VIEW `v_sys_userPermission` AS 
SELECT
	-- 员工权限视图
	d.userId || d.permissionId AS id,
	d.userId,
	d.permissionId,
	d.flag
FROM
	v_sys_userPermission_two d;

-- ----------------------------
-- View structure for `v_sys_userTree_one`
-- ----------------------------
DROP VIEW IF EXISTS `v_sys_userTree_one`;
CREATE VIEW `v_sys_userTree_one` AS 
SELECT
	ur.userId,
	rp.permissionId,
	1 AS flag
FROM
	t_sys_userRole ur,
	t_sys_rolePermission rp,
	t_sys_permission pe
WHERE
	ur.roleId = rp.roleId
AND ur. STATUS = '0'
AND rp. STATUS = '0'
AND pe. STATUS <> '1'
AND rp.permissionId = pe.id
AND (
	(
		pe.url IS NOT NULL
		AND pe.url <> ''
	)
	OR (
		(pe.url IS NULL OR pe.url = '')
		AND pe.isLeaf = '0'
	)
)
UNION
	(
		SELECT
			b.id AS userId,
			a.id AS permissionId,
			1 AS flag
		FROM
			t_sys_permission a,
			t_sys_user b
		WHERE
			b. STATUS = '99'
		AND a. STATUS IN ('4', '3')
	);
	
-- ----------------------------
-- View structure for `v_sys_userTree_two`
-- ----------------------------
DROP VIEW IF EXISTS `v_sys_userTree_two`;
CREATE VIEW `v_sys_userTree_two` AS 
SELECT
	c.userId,
	c.permissionId,
	CASE
WHEN SUM(c.flag) > 0 THEN
	1
ELSE
	0
END AS flag
FROM v_sys_userTree_one c
GROUP BY
	c.userId,
	c.permissionId;

-- ----------------------------
-- View structure for `v_sys_userTree`
-- ----------------------------
DROP VIEW IF EXISTS `v_sys_userTree`;
CREATE VIEW `v_sys_userTree` AS 
SELECT
	-- 员工树视图
	d.userId || d.permissionId AS id,
	d.userId,
	d.permissionId,
	d.flag
FROM v_sys_userTree_two d;

-- ----------------------------
-- View structure for `v_sys_userPermissionDivide`
-- ----------------------------
DROP VIEW IF EXISTS `v_sys_userPermissionDivide`;
CREATE VIEW `v_sys_userPermissionDivide` AS 
SELECT
	-- 用户权限操作控制
	ur.userId || ur.roleId AS id,
	ur.userId,
	ur.roleId,
	rp.permissionId,
	pm.content,
	rm.keyNameId,
	dc.keyName,
	dc.name,
	dc.weight
FROM
	t_sys_userRole ur,
	t_sys_rolePermission rp,
	t_sys_rolePermissionDivide rm,
	t_sys_permission pm,
	t_sys_permissionDivide dc
WHERE
	rp.id = rm.rolePermissionId
AND ur.roleId = rp.roleId
AND rp.permissionId = pm.Id
AND rm.keyNameId = dc.Id
AND ur.status = '0'
AND rp.status = '0'
AND rm.status = '0'
AND pm.status <> '1'
AND dc.status = '0';

-- ----------------------------
-- View structure for `v_base_cabinetInfo`
-- ----------------------------
DROP VIEW IF EXISTS `v_base_cabinetInfo`;
CREATE VIEW `v_base_cabinetInfo` AS 
SELECT
	-- 机柜信息
	c.*,
	s.province,
	s.city,
	s.county,
	s.scenicSpotName
FROM
	t_base_cabinet c
LEFT JOIN t_base_scenicSpot s ON c.scenicSpotId = s.id AND s. STATUS = '0'
WHERE
	c. STATUS = '0'
	
-- ----------------------------
-- View structure for `v_base_channelInfo`
-- ----------------------------
DROP VIEW IF EXISTS `v_base_channelInfo`;
CREATE VIEW `v_base_channelInfo` AS 
SELECT
	-- 仓道导游机信息
	c.id,
	c.cabinetId,
	c.channelStatus,
	c.position,
	c.guideMachineStatus,
	g.imetNo,
	g.guideMachineNo,
	g.rentStatus,
	g.temperatureStatus,
	g.machineStatus,
	g.positionStatus,
	g.electricityStatus
FROM
	t_base_channel c
LEFT JOIN t_base_guidemachine g ON c.id = g.channelId
AND g. STATUS = '0'
AND c.guideMachineStatus = g.guideMachineNo
WHERE
	c. STATUS = '0'

-- ----------------------------
-- View structure for `v_sys_userRoleInfo`
-- ----------------------------
DROP VIEW IF EXISTS `v_sys_userRoleInfo`;
CREATE VIEW `v_sys_userRoleInfo` AS 
SELECT 
	ur.roleId,role.roleName,ur.userId
FROM t_sys_role role, t_sys_userrole ur
WHERE ur.roleId = role.id


-- ----------------------------
-- View structure for `v_sys_userCityManageInfo`
-- ----------------------------
DROP VIEW IF EXISTS `v_sys_userCityManageInfo`;
CREATE VIEW `v_sys_userCityManageInfo` AS 
SELECT 
	ucm.province,ucm.city,ucm.county,ucm.scenicSpotId,ss.scenicSpotName,ucm.userId
FROM t_sys_usercitymanage ucm,t_base_scenicspot ss
WHERE ucm.scenicSpotId = ss.id

-- ----------------------------
-- View structure for `v_base_userRoleCityManageInfo`
-- ----------------------------
DROP VIEW IF EXISTS `v_sys_userRoleCityManageInfo`;
CREATE VIEW `v_sys_userRoleCityManageInfo` AS 
SELECT 
	users.*,
	one.roleName,
	one.roleId,
	two.city,
	two.county,
	two.province,
	two.scenicSpotId,
	two.scenicSpotName
FROM t_sys_user users
LEFT JOIN v_sys_userRoleInfo one on users.id = one.userId
LEFT JOIN v_sys_userCityManageInfo two ON users.id = two.userId
WHERE users.status<>'1'