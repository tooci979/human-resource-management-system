/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : back_employee

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 17/06/2024 20:06:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for apartment
-- ----------------------------
DROP TABLE IF EXISTS `apartment`;
CREATE TABLE `apartment`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `principal` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `telephone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sex` int NULL DEFAULT NULL,
  `created_date` datetime NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apartment
-- ----------------------------
INSERT INTO `apartment` VALUES (35, '软件学院', 'jack', '15673842354', 1, '2024-05-17 20:51:47', '1234856456');
INSERT INTO `apartment` VALUES (36, '计算机学院', '1231876', '17683356954', 1, '2024-05-17 20:51:47', '123786');
INSERT INTO `apartment` VALUES (37, '机械学院', '23', '15396635783', 2, '2024-05-17 20:51:47', '123123');
INSERT INTO `apartment` VALUES (45, '电器学院', '李四', '18358863565', 1, '2024-05-18 10:07:18', '1237846456000012000');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `job_id` int NULL DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_date` datetime NULL DEFAULT NULL,
  `telephone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `state` int NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 134 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (42, 'uu15378869356', '李四', 73, '男', '2024-05-18 10:05:55', '15378869356', 1, '123@qq.com');
INSERT INTO `employee` VALUES (139, 'uu15377768555', '123', 74, '女', '2024-06-17 12:05:28', '15377768555', 2, '123@qq.com');

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `apartment_id` int NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `maxnum` int NULL DEFAULT NULL,
  `available` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 81 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of job
-- ----------------------------
INSERT INTO `job` VALUES (73, 35, '教授', 5, 1);
INSERT INTO `job` VALUES (74, 36, '教授', 10, 1);
INSERT INTO `job` VALUES (75, 36, '副教授', 2, 0);
INSERT INTO `job` VALUES (76, 35, '院长', 10, 0);
INSERT INTO `job` VALUES (77, 37, '院长', 8, 0);
INSERT INTO `job` VALUES (82, 45, '院长', 5, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `state` int NULL DEFAULT NULL,
  `telephone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `created_date` datetime NULL DEFAULT NULL,
  `role` int NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '123456', 1, '18054399790', '2024-05-03 19:45:31', 1, '超级管理员拥有最高权限');
INSERT INTO `user` VALUES (2, '张三', '123456', 1, '18221822474', '2024-05-03 19:45:34', 2, '普通用户仅可查看');
INSERT INTO `user` VALUES (32, '123123651123', '123123', 1, '', '2024-05-17 14:34:18', 1, '超级管理员拥有最高权限');
INSERT INTO `user` VALUES (53, '123', '123', 1, '15344435235', '2024-05-31 08:57:43', 1, '123213');
INSERT INTO `user` VALUES (54, '2003552', '123456', 1, '15388874563', '2024-05-31 09:51:59', 2, '普通用户仅可查看');
INSERT INTO `user` VALUES (57, '1232', '123456', 1, '15578885551', '2024-05-31 09:59:22', 2, '普通用户仅可查看');
INSERT INTO `user` VALUES (58, '123123', '123456', 1, '15633366222', '2024-06-17 11:50:41', 2, '普通用户仅可查看');

SET FOREIGN_KEY_CHECKS = 1;
