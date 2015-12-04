/*
 Navicat MySQL Data Transfer

 Source Server         : Conn
 Source Server Type    : MySQL
 Source Server Version : 50163
 Source Host           : localhost
 Source Database       : wifimonitor

 Target Server Type    : MySQL
 Target Server Version : 50163
 File Encoding         : utf-8

 Date: 11/30/2015 11:24:48 AM
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `access_control`
-- ----------------------------
DROP TABLE IF EXISTS `access_control`;
CREATE TABLE `access_control` (
  `id`          INT(11) NOT NULL AUTO_INCREMENT,
  `ip`          VARCHAR(20)      DEFAULT NULL,
  `port`        INT(11)          DEFAULT NULL,
  `access_type` SMALLINT(6)      DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
--  Table structure for `client_list`
-- ----------------------------
DROP TABLE IF EXISTS `client_list`;
CREATE TABLE `client_list` (
  `id`        INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cmac`      VARCHAR(20)               DEFAULT NULL,
  `show_pkgs` VARCHAR(45)               DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
--  Table structure for `pkg`
-- ----------------------------
DROP TABLE IF EXISTS `pkg`;
CREATE TABLE `pkg` (
  `id`              INT(11) NOT NULL AUTO_INCREMENT,
  `native_mac`      VARCHAR(100)     DEFAULT NULL,
  `remote_ip`       VARCHAR(20)      DEFAULT NULL,
  `remote_port`     INT(11)          DEFAULT NULL,
  `protocol_type`   SMALLINT(6)      DEFAULT NULL,
  `flow_directioin` TINYINT(1)       DEFAULT NULL,
  `flow_amount`     VARCHAR(20)      DEFAULT NULL,
  `first_visitTime` DATETIME         DEFAULT NULL,
  `last_visitTime`  DATETIME         DEFAULT NULL,
  `date_visit`      DATETIME         DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
--  Table structure for `pkg_uri`
-- ----------------------------
DROP TABLE IF EXISTS `pkg_uri`;
CREATE TABLE `pkg_uri` (
  `id`         INT(11) NOT NULL AUTO_INCREMENT,
  `package_id` INT(11)          DEFAULT NULL,
  `uri_id`     INT(11)          DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Relationship_1` (`package_id`),
  KEY `FK_Relationship_2` (`uri_id`),
  CONSTRAINT `FK_Relationship_1` FOREIGN KEY (`package_id`) REFERENCES `pkg` (`id`),
  CONSTRAINT `FK_Relationship_2` FOREIGN KEY (`uri_id`) REFERENCES `uri` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
--  Table structure for `system_users`
-- ----------------------------
DROP TABLE IF EXISTS `system_users`;
CREATE TABLE `system_users` (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `user_name`   VARCHAR(32)  NOT NULL,
  `password`    VARCHAR(256) NOT NULL,
  `real_name`   VARCHAR(32)           DEFAULT NULL,
  `mailbox`     VARCHAR(64)           DEFAULT NULL,
  `user_phone`  VARCHAR(20)           DEFAULT NULL,
  `user_access` VARCHAR(20)           DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
--  Table structure for `tpkg`
-- ----------------------------
DROP TABLE IF EXISTS `tpkg`;
CREATE TABLE `tpkg` (
  `id`               INT(11) NOT NULL AUTO_INCREMENT,
  `native_mac`       VARCHAR(100)     DEFAULT NULL,
  `remote_ip`        VARCHAR(20)      DEFAULT NULL,
  `remote_port`      INT(11)          DEFAULT NULL,
  `protocol_type`    VARCHAR(20)      DEFAULT NULL,
  `flow_direction`   TINYINT(1)       DEFAULT NULL,
  `flow_amount`      VARCHAR(20)      DEFAULT NULL,
  `first_visit_time` DATETIME         DEFAULT NULL,
  `last_visit_time`  DATETIME         DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
--  Table structure for `tpkg_uri`
-- ----------------------------
DROP TABLE IF EXISTS `tpkg_uri`;
CREATE TABLE `tpkg_uri` (
  `id`         INT(11) NOT NULL AUTO_INCREMENT,
  `uri_id`     INT(11)          DEFAULT NULL,
  `package_id` INT(11)          DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Relationship_3` (`uri_id`),
  KEY `FK_Relationship_4` (`package_id`),
  CONSTRAINT `FK_Relationship_3` FOREIGN KEY (`uri_id`) REFERENCES `uri` (`id`),
  CONSTRAINT `FK_Relationship_4` FOREIGN KEY (`package_id`) REFERENCES `tpkg` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
--  Table structure for `uri`
-- ----------------------------
DROP TABLE IF EXISTS `uri`;
CREATE TABLE `uri` (
  `id`   INT(11) NOT NULL AUTO_INCREMENT,
  `host` VARCHAR(50)      DEFAULT NULL,
  `path` VARCHAR(5000)    DEFAULT NULL,
  `port` INT(11)          DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
--  Table structure for `user_t`
-- ----------------------------
DROP TABLE IF EXISTS `user_t`;
CREATE TABLE `user_t` (
  `id`        INT(11)      NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(40)  NOT NULL,
  `password`  VARCHAR(255) NOT NULL,
  `age`       INT(4)       NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
--  Table structure for `traffic_arff`
-- ----------------------------
DROP TABLE IF EXISTS `traffic_arff`;
CREATE TABLE traffic_arff (
  `id`              INT(11) NOT NULL AUTO_INCREMENT,
  `target_ip`       VARCHAR(20) NOT NULL
  COMMENT '所访问的ip地址',
  `link_count`      INT(11)          DEFAULT 0
  COMMENT '访问记录包含的URL链接数',
  `visit_frequency` INT COMMENT '访问次数',
  `up_traffic`      BIGINT(20)       DEFAULT 0
  COMMENT '上行流量总和',
  `down_traffic`    BIGINT(20)       DEFAULT 0
  COMMENT '下行流量总和',
  `begin_type`      VARCHAR(5)       DEFAULT NULL
  COMMENT '开始流量类型',
  `end_type`        VARCHAR(5)       DEFAULT NULL
  COMMENT '结束流量类型',
  PRIMARY KEY (`id`)
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;

-- ----------------------------
--  Table structure for `train_data`
-- ----------------------------
DROP TABLE IF EXISTS `train_data`;
CREATE TABLE `train_data` (
  `id`                  INT(11) NOT NULL AUTO_INCREMENT,
  `target_ip`           VARCHAR(20)      DEFAULT NULL,
  `link_num`            VARCHAR(10)      DEFAULT NULL,
  `visit_fre`           VARCHAR(10)      DEFAULT NULL,
  `up_down_traffic_pro` VARCHAR(10)      DEFAULT NULL,
  `traffic_total`       VARCHAR(10)      DEFAULT NULL,
  `traffic_type`        VARCHAR(10)      DEFAULT NULL
  COMMENT '分类前的结果',
  `classified_type`     VARCHAR(10)      DEFAULT NULL
  COMMENT '训练后分类结果',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
