/*DROP TABLE IF EXISTS LU_Admin;
CREATE TABLE LU_Admin (
  id INT(4) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(20) NOT NULL,
  password VARCHAR(20) NOT NULL,
  name VARCHAR(20) NOT NULL
)DEFAULT CHARSET=utf8;*/

DROP TABLE IF EXISTS LU_VersionInfo;
CREATE TABLE LU_VersionInfo (
  id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `version` VARCHAR(20) COMMENT '版本号',
  `packetSize` VARCHAR(50) COMMENT '安装包大小',
  `noticeIconUri` VARCHAR(50) COMMENT '通知图标',
  `apkIconUri` VARCHAR(50) COMMENT 'apk图标',
  `installUri` VARCHAR(50) COMMENT 'apk地址',
  `desc` VARCHAR(50) COMMENT '备注'
)ENGINE=`InnoDB` DEFAULT CHARACTER SET utf8 COMMENT='版本管理表';


DROP TABLE IF EXISTS LU_Strategy;
CREATE TABLE LU_Strategy (
  id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `clientVersion` VARCHAR(50) COMMENT '客户端版本',
  `whiteListTname` VARCHAR(50) COMMENT '白名单表名',
  `blackListTname` VARCHAR(50) COMMENT '黑名单表明',
  `phoneModel` VARCHAR(50) COMMENT '手机型号',
  `phoneSysVersion` VARCHAR(50) COMMENT '手机系统版本',
  `area` VARCHAR(50) COMMENT '优先级',
  `frequency` VARCHAR(50) COMMENT '可升级高版本',
  `priority` VARCHAR(50) COMMENT '优先级',
  `highVersion`  VARCHAR(50) COMMENT '可升级高版本',
  `compatibleVersion`  VARCHAR(50) COMMENT '兼容版本号'
)ENGINE=`InnoDB` DEFAULT CHARACTER SET utf8 COMMENT='升级策略管理表';

DROP TABLE IF EXISTS LU_TableList;
CREATE TABLE LU_TableList (
  id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `tablename` VARCHAR(50) COMMENT '表名',
  `type` VARCHAR(1) COMMENT '类型 0:白名单 1:黑名单',
  `operator` VARCHAR(50) COMMENT '操作人员',
  `inserttime` DATETIME COMMENT '导入时间',
  `updatetime` DATETIME COMMENT '更新时间'
)ENGINE=`InnoDB` DEFAULT CHARACTER SET utf8 COMMENT='白名单，黑名单管理表';

DROP TABLE IF EXISTS operate_log;
CREATE TABLE operate_log (
  id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `userid` bigint COMMENT '用户ID',
  `name` VARCHAR(50) COMMENT '姓名',
  `operate` VARCHAR(50) COMMENT '操作人',
  `inserttime` DATETIME COMMENT '创建时间'
)ENGINE=`InnoDB` DEFAULT CHARACTER SET utf8 COMMENT='系统配置信息表';