/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50629
 Source Host           : localhost
 Source Database       : douban

 Target Server Version : 50629
 File Encoding         : utf-8

 Date: 04/01/2016 11:27:33 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tb_bookcomment`
-- ----------------------------
DROP TABLE IF EXISTS `tb_bookcomment`;
CREATE TABLE `tb_bookcomment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookno` varchar(20) DEFAULT NULL COMMENT '豆瓣图书编号',
  `bookname` varchar(200) DEFAULT NULL COMMENT '书名',
  `username` varchar(200) DEFAULT NULL COMMENT '评论者昵称',
  `rating` int(4) DEFAULT NULL COMMENT '评论者评分',
  `content` varchar(2000) DEFAULT NULL COMMENT '评论内容',
  `time` date DEFAULT NULL COMMENT '评论时间',
  `usefulnum` int(20) DEFAULT NULL COMMENT '认为该评论有用的人数',
  `userurl` varchar(400) DEFAULT NULL COMMENT '评论者个人主页',
  `commenturl` varchar(400) DEFAULT NULL COMMENT '当前评论地址',
  `userno` varchar(40) DEFAULT NULL COMMENT '豆瓣用户标识',
  `isbn` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3358702 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tb_bookinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tb_bookinfo`;
CREATE TABLE `tb_bookinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '图书主键',
  `no` varchar(20) NOT NULL COMMENT '豆瓣图书编号',
  `title` varchar(200) DEFAULT NULL COMMENT '书名',
  `authorname` varchar(200) DEFAULT NULL COMMENT '作者姓名',
  `press` varchar(200) DEFAULT NULL COMMENT '出版社',
  `orititle` varchar(200) DEFAULT NULL COMMENT '图书原名',
  `translator` varchar(200) DEFAULT NULL COMMENT '译者',
  `publishtime` date DEFAULT NULL COMMENT '出版年份',
  `pages` int(11) DEFAULT NULL COMMENT '页数',
  `price` double DEFAULT NULL COMMENT '价格',
  `binding` varchar(200) DEFAULT NULL COMMENT '装帧',
  `series` varchar(200) DEFAULT NULL COMMENT '丛书',
  `isbn` varchar(60) DEFAULT NULL COMMENT 'ISBN',
  `rating` double(11,1) DEFAULT NULL COMMENT '豆瓣评分',
  `comments` int(11) DEFAULT NULL COMMENT '评价数',
  `contentinfo` varchar(2000) DEFAULT NULL COMMENT '内容简介',
  `authorinfo` varchar(2000) DEFAULT NULL COMMENT '作者简介',
  `usertags` varchar(200) DEFAULT NULL COMMENT '豆瓣成员常用标签',
  `alsolikeebook` varchar(400) DEFAULT NULL COMMENT '喜欢这本书的人也喜欢的电子书',
  `alsolikebook` varchar(400) DEFAULT NULL COMMENT '喜欢这本书的人也喜欢读',
  `shortcommentsnum` int(11) DEFAULT NULL COMMENT '短评数',
  `bookcommentsnum` int(11) DEFAULT NULL COMMENT '书评数',
  `readingnotesnum` int(11) DEFAULT NULL COMMENT '读书笔记数',
  `bookurl` varchar(200) DEFAULT NULL COMMENT '图书地址',
  `discussnum` int(11) DEFAULT NULL COMMENT '正在豆瓣App讨论这本书的人数',
  `readingsnum` int(11) DEFAULT NULL COMMENT '正在读书人数',
  `hasreadnum` int(11) DEFAULT NULL COMMENT '读过人数',
  `wantreadnum` int(11) DEFAULT NULL COMMENT '想读人数',
  `tag` varchar(20) DEFAULT NULL COMMENT '原始标签',
  PRIMARY KEY (`id`),
  UNIQUE KEY `no` (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=1539 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tb_booktag`
-- ----------------------------
DROP TABLE IF EXISTS `tb_booktag`;
CREATE TABLE `tb_booktag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签主键',
  `tagname` varchar(20) NOT NULL COMMENT '标签名',
  `url` varchar(200) NOT NULL COMMENT '标签地址',
  `category` varchar(20) DEFAULT NULL COMMENT '种类',
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tb_simplebookinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tb_simplebookinfo`;
CREATE TABLE `tb_simplebookinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `author` varchar(200) DEFAULT NULL,
  `translator` varchar(200) DEFAULT NULL,
  `press` varchar(200) DEFAULT NULL,
  `publishtime` varchar(200) DEFAULT NULL,
  `price` varchar(200) DEFAULT NULL,
  `rating` double(11,2) DEFAULT NULL,
  `url` varchar(400) NOT NULL,
  `tag` varchar(200) DEFAULT NULL,
  `img` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`(255)) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=64786 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tb_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tb_userinfo`;
CREATE TABLE `tb_userinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userno` varchar(40) DEFAULT NULL COMMENT '用户编号',
  `name` varchar(40) DEFAULT NULL COMMENT '用户昵称',
  `signature` varchar(100) DEFAULT NULL COMMENT '签名',
  `location` varchar(100) DEFAULT NULL COMMENT '长居地',
  `time` date DEFAULT NULL COMMENT '注册豆瓣时间',
  `introduction` varchar(2000) DEFAULT NULL COMMENT '个人简介',
  `reading` int(11) DEFAULT NULL COMMENT '正在读',
  `hasread` int(11) DEFAULT NULL COMMENT '读过',
  `wantread` int(11) DEFAULT NULL COMMENT '想读',
  `followees` int(11) DEFAULT NULL COMMENT '关注了',
  `followers` int(11) DEFAULT NULL COMMENT '关注者',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userno` (`userno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `test`
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` int(11) NOT NULL,
  `bookid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
