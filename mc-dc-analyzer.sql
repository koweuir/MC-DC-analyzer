/*
Navicat MySQL Data Transfer

Source Server         : Myself
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : mc-dc-analyzer

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2024-04-26 20:46:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admininfo
-- ----------------------------
DROP TABLE IF EXISTS `admininfo`;
CREATE TABLE `admininfo` (
  `AdminID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `Username` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `PhoneNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`AdminID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admininfo
-- ----------------------------

-- ----------------------------
-- Table structure for dataconfiguration
-- ----------------------------
DROP TABLE IF EXISTS `dataconfiguration`;
CREATE TABLE `dataconfiguration` (
  `DataID` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Address` varchar(255) NOT NULL,
  `Remarks` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DataID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dataconfiguration
-- ----------------------------

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `ProjectID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `Version` varchar(255) NOT NULL,
  `Introduction` varchar(255) NOT NULL,
  `Address` varchar(255) NOT NULL,
  PRIMARY KEY (`ProjectID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------

-- ----------------------------
-- Table structure for project-case
-- ----------------------------
DROP TABLE IF EXISTS `project-case`;
CREATE TABLE `project-case` (
  `ProjectID` int(11) NOT NULL,
  `CaseID` int(11) NOT NULL,
  PRIMARY KEY (`ProjectID`,`CaseID`),
  KEY `CaseID` (`CaseID`),
  CONSTRAINT `CaseID` FOREIGN KEY (`CaseID`) REFERENCES `tsetcase` (`CaseID`),
  CONSTRAINT `ProjectID` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ProjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project-case
-- ----------------------------

-- ----------------------------
-- Table structure for project-data
-- ----------------------------
DROP TABLE IF EXISTS `project-data`;
CREATE TABLE `project-data` (
  `ProjectID` int(11) NOT NULL,
  `DataID` int(11) NOT NULL,
  PRIMARY KEY (`ProjectID`,`DataID`),
  KEY `DataID2` (`DataID`),
  CONSTRAINT `DataID2` FOREIGN KEY (`DataID`) REFERENCES `dataconfiguration` (`DataID`),
  CONSTRAINT `ProjectID2` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ProjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project-data
-- ----------------------------

-- ----------------------------
-- Table structure for tsetcase
-- ----------------------------
DROP TABLE IF EXISTS `tsetcase`;
CREATE TABLE `tsetcase` (
  `CaseID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `Address` varchar(255) NOT NULL,
  `Remarks` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CaseID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsetcase
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `Username` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `PhoneNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
