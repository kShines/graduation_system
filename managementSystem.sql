/*
 Navicat Premium Data Transfer

 Source Server         : manage
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : managementSystem

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 28/12/2020 15:04:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for achievement
-- ----------------------------
DROP TABLE IF EXISTS `achievement`;
CREATE TABLE `achievement` (
  `achievement_id` int NOT NULL AUTO_INCREMENT,
  `creator` int DEFAULT NULL,
  `year` int DEFAULT NULL,
  `quarter` int DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `month` tinyint DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `text_1` varchar(255) DEFAULT NULL,
  `text_2` varchar(255) DEFAULT NULL,
  `text_3` varchar(255) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  PRIMARY KEY (`achievement_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of achievement
-- ----------------------------
BEGIN;
INSERT INTO `achievement` VALUES (1, 1, 2020, 4, '2020-11-08', 10, '111', '222', '333', '333', '2020-11-25');
INSERT INTO `achievement` VALUES (2, 1, 2020, 4, '2020-11-08', 10, '111', '222', '333', '333', '2020-11-25');
INSERT INTO `achievement` VALUES (3, 1, 2020, 4, '2020-11-08', 10, '1', '1', '1', '1', '2020-11-25');
INSERT INTO `achievement` VALUES (6, 5, 2020, 4, '2020-11-09', NULL, '33', '33', '33', '33', '2020-11-25');
INSERT INTO `achievement` VALUES (7, 8, 2020, 4, '2020-11-26', NULL, '我做成功了一件事情', '我做成功了一件事情', '', '', '2020-11-26');
INSERT INTO `achievement` VALUES (9, 8, 2020, 4, '2020-11-26', NULL, 'test', 'akdhfak', 'askdfhjak', 'asdkfhaksdfh', '2020-11-26');
COMMIT;

-- ----------------------------
-- Table structure for assess_batch
-- ----------------------------
DROP TABLE IF EXISTS `assess_batch`;
CREATE TABLE `assess_batch` (
  `group_id` int DEFAULT NULL,
  `assess_batch_id` int NOT NULL AUTO_INCREMENT,
  `year` int DEFAULT NULL,
  `quarter` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `is_use` int DEFAULT NULL,
  PRIMARY KEY (`assess_batch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of assess_batch
-- ----------------------------
BEGIN;
INSERT INTO `assess_batch` VALUES (1, 2, 2020, 4, '12313', 1);
INSERT INTO `assess_batch` VALUES (1, 4, 2020, 4, '20200303', 1);
INSERT INTO `assess_batch` VALUES (1, 8, 2020, 4, '这是标题', 1);
INSERT INTO `assess_batch` VALUES (1, 11, 2020, 4, '标题2', 1);
INSERT INTO `assess_batch` VALUES (2, 12, 2020, 4, '标题3', 1);
INSERT INTO `assess_batch` VALUES (3, 13, 2020, 4, '这是标题', 1);
COMMIT;

-- ----------------------------
-- Table structure for assess_group
-- ----------------------------
DROP TABLE IF EXISTS `assess_group`;
CREATE TABLE `assess_group` (
  `group_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `admin` varchar(255) DEFAULT NULL,
  `member` varchar(5550) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `superior` int DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of assess_group
-- ----------------------------
BEGIN;
INSERT INTO `assess_group` VALUES (1, '考核1组', '5', '5,7,9,20,24,25,26,27,507', '2020-11-04', 1);
INSERT INTO `assess_group` VALUES (2, '考核2组', '1', '5,10,22', '2020-11-17', 1);
INSERT INTO `assess_group` VALUES (3, '考核3组', '1', '5,16,17,18,1,19,21', '2020-11-17', 4);
INSERT INTO `assess_group` VALUES (4, '考核4组', '', '13,23', '2020-11-28', 5);
INSERT INTO `assess_group` VALUES (5, '考核5组', '', '8', '2020-11-29', 2);
INSERT INTO `assess_group` VALUES (6, '文创-考核1组', '', '59,60,61,62,63,64,65,66,67,68,69', '2020-12-27', 1016);
INSERT INTO `assess_group` VALUES (7, '社科-考核1组', '', '70,71,72', '2020-12-27', 1017);
INSERT INTO `assess_group` VALUES (8, '活动中心-考核1组', '', '73,74,75,76,77,78,79,80,81', '2020-12-27', 1019);
INSERT INTO `assess_group` VALUES (9, '人力社保-考核1组', '', '82,83,84,85,86,87,88,89', '2020-12-27', 1020);
INSERT INTO `assess_group` VALUES (10, '创业管理-考核1组', '', '90,91,92,93,94,95,96', '2020-12-27', 1021);
INSERT INTO `assess_group` VALUES (11, '考试鉴定-考核1组', '', '107,108,109,110,111,112,113,114,115,116', '2020-12-27', 1022);
INSERT INTO `assess_group` VALUES (12, '技师学院-考核1组', '', '311,312,313,314,315,316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,337,338,339,340,341,342,343,344,345,346,347,348,349,350,351,352,353,354,355,356,357,358,359,360,361,362,363,364,365,366,367,368,369,370,371,372,373,374,375,376,377,378,379,380,381,382,383,384,385,386,387,388,389,390,391,392,393,394,395,396,397,398,399,400,401,402,403,404,405,406,407,408,409,410,411,412,413,414,415,416,417,418,419,420,421,422,423,424,425,426,427,428,429,430,431,432,433,434,435,436,437,438,439,440,441,442,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,468,469,470,471,472,473,474,475,476,477,478,479,480,481,482,483,484,485,486,487,488,489,490,491,492,493,494,495,496,497,498,499,500,501,502,503,504', '2020-12-27', 1023);
INSERT INTO `assess_group` VALUES (13, '档案服务中心-考核1组', '', '505,506', '2020-12-27', 1024);
INSERT INTO `assess_group` VALUES (14, '服务中心-考核1组', '', '29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58', '2020-12-27', 1026);
COMMIT;

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `organization_id` int NOT NULL AUTO_INCREMENT,
  `admin_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `superior` int DEFAULT NULL,
  `sub_organization` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `sub_group` varchar(255) DEFAULT NULL,
  `manage_organization_area` varchar(255) DEFAULT NULL,
  `manage_assess_group_area` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`organization_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1027 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of organization
-- ----------------------------
BEGIN;
INSERT INTO `organization` VALUES (1, '24', 'root', 0, '5', '', NULL, NULL);
INSERT INTO `organization` VALUES (2, '1', '测试1', 5, '', '5', '', '');
INSERT INTO `organization` VALUES (3, '5,26', '测试3', 4, '', '1,2', NULL, NULL);
INSERT INTO `organization` VALUES (4, '1,25', '测试2', 5, '3,6', '3', '', '');
INSERT INTO `organization` VALUES (5, '5,7,9,1,8', '萧山区', 1, '2,4,1013,1014,1015,1018,1025', '4', '', '');
INSERT INTO `organization` VALUES (6, '5', '测试4', 4, '', '', NULL, NULL);
INSERT INTO `organization` VALUES (999, NULL, '', NULL, '', '', NULL, NULL);
INSERT INTO `organization` VALUES (1013, '', '测试5', 5, '', '', '', '');
INSERT INTO `organization` VALUES (1014, '', '测试6', 5, '', '', '', '');
INSERT INTO `organization` VALUES (1015, '', '区委宣传部', 5, '1016,1017', '', '', '');
INSERT INTO `organization` VALUES (1016, '', '文化创意产业发展中心', 1015, '', '6', '', '');
INSERT INTO `organization` VALUES (1017, '', '社会科学界联合会', 1015, '', '7', '', '');
INSERT INTO `organization` VALUES (1018, '', '人力资源和社会保障局', 5, '1019,1020,1021,1022,1023,1024', '', '', '');
INSERT INTO `organization` VALUES (1019, '', '退休干部活动中心', 1018, '', '8', '', '');
INSERT INTO `organization` VALUES (1020, '', '人力资源和社会保障信息咨询服务中心', 1018, '', '9', '', '');
INSERT INTO `organization` VALUES (1021, '', '留学人员（大学生）创业管理服务中心', 1018, '', '10', '', '');
INSERT INTO `organization` VALUES (1022, '', '人力资源考试培训和技能鉴定中心', 1018, '', '11', '', '');
INSERT INTO `organization` VALUES (1023, '', '杭州萧山技师学院', 1018, '', '12', '', '');
INSERT INTO `organization` VALUES (1024, '', '干部人事档案服务中心', 1018, '', '13', '', '');
INSERT INTO `organization` VALUES (1025, '', '河上镇委员会、人民政府', 5, '1026', '', '', '');
INSERT INTO `organization` VALUES (1026, '', '党群服务中心', 1025, '', '14', '', '');
COMMIT;

-- ----------------------------
-- Table structure for plan
-- ----------------------------
DROP TABLE IF EXISTS `plan`;
CREATE TABLE `plan` (
  `plan_id` int NOT NULL AUTO_INCREMENT,
  `creator` int DEFAULT NULL,
  `year` int DEFAULT NULL,
  `quarter` int DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  PRIMARY KEY (`plan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of plan
-- ----------------------------
BEGIN;
INSERT INTO `plan` VALUES (3, 5, 2020, 2, '2020-11-04', '2222', '22222', '2020-11-04');
INSERT INTO `plan` VALUES (4, 1, 2020, 4, '2020-11-07', '2020年第四季度计划', '计划1计划2', '2020-11-07');
INSERT INTO `plan` VALUES (8, 1, 2020, 4, '2020-11-07', '1', '1', '2020-11-23');
INSERT INTO `plan` VALUES (9, 1, 2020, 4, '2020-11-07', '1212', '1212', '2020-11-23');
INSERT INTO `plan` VALUES (11, 1, 2020, 4, '2020-11-07', '1212', '1212', '2020-11-23');
INSERT INTO `plan` VALUES (13, 1, 2020, 4, '2020-11-07', '计划1', '计划2', '2020-11-23');
INSERT INTO `plan` VALUES (14, 5, 2020, 4, '2020-11-09', '4', '2', '2020-11-23');
INSERT INTO `plan` VALUES (15, 5, 2020, 4, '2020-11-09', '5', '3', '2020-11-23');
INSERT INTO `plan` VALUES (16, 1, 2020, 4, '2020-11-07', '222', '333', '2020-11-07');
INSERT INTO `plan` VALUES (17, 1, 2020, 4, '2020-11-08', '1', '1', '2020-11-08');
INSERT INTO `plan` VALUES (18, 1, 2020, 4, '2020-11-08', '2', '2', '2020-11-08');
INSERT INTO `plan` VALUES (19, 1, 2020, 4, '2020-11-08', '1', '23', '2020-11-08');
INSERT INTO `plan` VALUES (20, 1, 2020, 4, '2020-11-08', '1', '1', '2020-11-08');
INSERT INTO `plan` VALUES (21, 1, 2020, 4, '2020-11-08', '2', '222', '2020-11-08');
INSERT INTO `plan` VALUES (22, 1, 2020, 4, '2020-11-08', 'dsada', 'dasd', '2020-11-08');
INSERT INTO `plan` VALUES (23, 1, 2020, 4, '2020-11-08', '44', '44', '2020-11-08');
INSERT INTO `plan` VALUES (24, 5, 2020, 4, '2020-11-09', '222', '2222', '2020-11-09');
INSERT INTO `plan` VALUES (25, 5, 2020, 4, '2020-11-09', '202020计划', '计划', '2020-11-09');
INSERT INTO `plan` VALUES (26, 5, 2020, 4, '2020-11-09', '1', '2', '2020-11-09');
INSERT INTO `plan` VALUES (27, 5, 2020, 4, '2020-11-09', '测试', '测试', '2020-11-09');
INSERT INTO `plan` VALUES (28, 5, 2020, 4, '2020-11-09', '123', '312', '2020-11-09');
INSERT INTO `plan` VALUES (29, 8, 2020, 4, '2020-11-26', '第二季度计划', '1.\n2.\n3.', '2020-11-26');
INSERT INTO `plan` VALUES (31, 8, 2020, 4, '2020-11-26', '第四季度工作', '第四季度工作', '2020-11-26');
INSERT INTO `plan` VALUES (32, 8, 2020, 4, '2020-11-26', '第三季度计划', 'test', '2020-11-26');
COMMIT;

-- ----------------------------
-- Table structure for summary
-- ----------------------------
DROP TABLE IF EXISTS `summary`;
CREATE TABLE `summary` (
  `summary_id` int NOT NULL AUTO_INCREMENT,
  `creator` int DEFAULT NULL,
  `year` int DEFAULT NULL,
  `quarter` int DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  PRIMARY KEY (`summary_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of summary
-- ----------------------------
BEGIN;
INSERT INTO `summary` VALUES (1, 1, 2020, 4, '2020-11-08', '1', '1', '2020-11-25');
INSERT INTO `summary` VALUES (2, 1, 2020, 4, '2020-11-08', '2020年总结', '总结1', '2020-10-21');
INSERT INTO `summary` VALUES (3, 1, 2020, 4, '2020-11-08', '222', '222', '2020-11-25');
INSERT INTO `summary` VALUES (4, 5, 2020, 4, '2020-11-08', '总结2020', '2020总结', '2020-12-11');
INSERT INTO `summary` VALUES (7, 5, 2020, 4, '2020-11-26', '1', '1', '2020-11-26');
INSERT INTO `summary` VALUES (8, 8, 2020, 3, '2020-11-26', '第三季度总结', '1.\n2.\n3.', '2020-07-08');
INSERT INTO `summary` VALUES (9, 8, 2020, 4, '2020-11-26', '11月工作总结', '11月工作总结', '2020-11-26');
INSERT INTO `summary` VALUES (10, 8, 2020, 4, '2020-11-26', '测试总结', '测试总结', '2020-11-26');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `id_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `organization` int DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `sex` int DEFAULT NULL,
  `assess_organization` int DEFAULT NULL,
  `assess_group` int DEFAULT NULL,
  `group_start_time` date DEFAULT NULL,
  `is_assess` int DEFAULT NULL,
  `admin_organization` varchar(255) DEFAULT NULL,
  `admin_assess_group` varchar(255) DEFAULT NULL,
  `assess_position` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=508 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '18100178891', '123456', '3326241996', '陈泽群', 2, '1', '1', 1, 3, 3, '2020-12-01', 1, '2,5', '1,2,3', '1');
INSERT INTO `user` VALUES (5, '123456', '654321', '123123123', '单振宇', 4, '1', '1', 1, 4, 1, '2020-12-01', 1, '3,5,6', '', '1');
INSERT INTO `user` VALUES (7, '111111', '111111', '1111', '谭洁', 4, '1', '1', 1, 4, 1, '2020-12-01', 1, '4,5', '', '1');
INSERT INTO `user` VALUES (8, '222222', '222222', '2222', '小王', 1014, '1', '1', 1, 4, 5, '2020-12-01', 1, '5', '', '1');
INSERT INTO `user` VALUES (9, '333333', '333333', '3333', '小李', 4, '1', '1', 1, 4, 1, '2020-12-01', 1, '5', '', '1');
INSERT INTO `user` VALUES (10, '444444', '444444', '332244', '小张', 4, '1', '1', 1, 4, 2, '2020-12-01', 1, '', '', '1');
INSERT INTO `user` VALUES (13, '999999', '999999', '3232323', '小红', 6, NULL, '1', 0, 3, 4, '2020-12-01', 1, '5', '', '1');
INSERT INTO `user` VALUES (19, '100001', '123456', '331234197801234000', '张三', 999, NULL, '无', 1, 999, 3, '2020-12-04', 1, '4', '', '无');
INSERT INTO `user` VALUES (20, '100002', '123456', '331234197801234000', '张三', 999, NULL, '无', 1, 999, 1, '2020-12-04', 1, '', '', '无');
INSERT INTO `user` VALUES (21, '100003', '123456', '331234197801234000', '张三', 999, NULL, '无', 1, 999, 3, '2020-12-04', 1, '', '', '无');
INSERT INTO `user` VALUES (22, '1818', '1818', '32131', '王五', 6, NULL, '1', 1, 6, 2, '2020-12-04', 1, '', '', '1');
INSERT INTO `user` VALUES (23, 'chenzequn', 'chenzequn', '2313', '陈泽群2', 3, NULL, '1', 1, 3, 4, '2020-12-01', 1, '', '', '1');
INSERT INTO `user` VALUES (24, 'root', 'root', '123123', 'admin', 1, '1', '1', 1, 1, 1, '2020-12-04', 1, '1', '', '1');
INSERT INTO `user` VALUES (25, 'mzj', 'mzj', '123123', 'mzjadmin', 1, '1', '1', 1, 1, 1, '2020-12-04', 1, '4', '', '1');
INSERT INTO `user` VALUES (26, 'hydj', 'hydj', '123123', 'hydjadmin', 1, '1', '1', 1, 1, 1, '2020-12-04', 1, '3', '', '1');
INSERT INTO `user` VALUES (27, 'khyz', 'khyz', '123123', 'khyzadmin', 1, '1', '1', 1, 1, 1, '2020-12-04', 1, '', '1', '1');
INSERT INTO `user` VALUES (28, '123123123', '123456', '331234197801234050', '张四', 1013, NULL, '无', 1, 1013, NULL, NULL, NULL, '', '', '无');
INSERT INTO `user` VALUES (29, '13588339327', '339327', '', '陈阳', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (30, '13758205822', '205822', '', '顾超林', 999, NULL, '无', 0, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (31, '13968087902', '087902', '', '张兴盛', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (32, '18967139898', '139898', '', '楼加正', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (33, '18058736060', '736060', '', '胡燕丽', 999, NULL, '无', 0, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (34, '15869138080', '138080', '', '华佳丽', 999, NULL, '无', 0, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (35, '13738022518', '022518', '', '黄丹青', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (36, '13858102800', '102800', '', '娄波', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (37, '13675877306', '877306', '', '舒军', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (38, '13395813098', '813098', '', '张铁奇', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (39, '13967127630', '127630', '', '董玉燕', 999, NULL, '无', 0, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (40, '13806503053', '503053', '', '俞金贤', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (41, '15858296146', '296146', '', '陈渊', 999, NULL, '无', 0, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (42, '13867417036', '417036', '', '汪立峰', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (43, '13967114056', '114056', '', '董科峰', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (44, '13456736668', '736668', '', '王军良', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (45, '15906662179', '662179', '', '史超群', 999, NULL, '无', 0, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (46, '13606709578', '709578', '', '吴观海', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (47, '13735439914', '439914', '', '韩惠娜', 999, NULL, '无', 0, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (48, '15868149011', '149011', '', '李金儿', 999, NULL, '无', 0, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (49, '13575717568', '717568', '', '俞柏林', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (50, '13858137955', '137955', '', '魏磊红', 999, NULL, '无', 0, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (51, '13486361305', '361305', '', '黄国宁', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (52, '13588405979', '405979', '', '丁俊杰', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (53, '13606705631', '705631', '', '管轶群', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (54, '13575462186', '462186', '', '楼可学', 999, NULL, '无', 0, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (55, '13957167756', '167756', '', '傅汤迪', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (56, '17706711691', '711691', '', '周益清', 999, NULL, '无', 0, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (57, '13588296178', '296178', '', '陈芬香', 999, NULL, '无', 0, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (58, '13867418882', '418882', '', '朱成', 999, NULL, '无', 1, 999, 14, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (59, '18258827077', '827077', '', '沈之玥', 1016, NULL, '无', 0, 1016, 6, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (60, '13867140700', '140700', '', '陶梁', 1016, NULL, '无', 1, 1016, 6, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (61, '13588001120', '001120', '', '孔铁军', 1016, NULL, '无', 1, 1016, 6, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (62, '13738093275', '093275', '', '戴亮', 1016, NULL, '无', 1, 1016, 6, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (63, '13588793589', '793589', '', '蒋宏君', 1016, NULL, '无', 1, 1016, 6, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (64, '13336115955', '115955', '', '来颖浩', 1016, NULL, '无', 1, 1016, 6, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (65, '18368885836', '885836', '', '邵舒骏', 1016, NULL, '无', 1, 1016, 6, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (66, '18668430288', '430288', '', '倪建尧', 1016, NULL, '无', 1, 1016, 6, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (67, '13616555058', '555058', '', '来燕妮', 1016, NULL, '无', 0, 1016, 6, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (68, '13606636709', '636709', '', '王小青', 1016, NULL, '无', 1, 1016, 6, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (69, '13738107507', '107507', '', '胡谌昊', 1016, NULL, '无', 1, 1016, 6, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (70, '18355352017', '352017', '', '李文香', 999, NULL, '无', 0, 999, 7, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (71, '15927157709', '157709', '', '张文杰', 999, NULL, '无', 0, 999, 7, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (72, '13958107180', '107180', '', '郭安琪', 999, NULL, '无', 0, 999, 7, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (73, '13777403705', '403705', '', '楼乐飞', 999, NULL, '无', 0, 999, 8, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (74, '13735569610', '569610', '', '张正卿', 999, NULL, '无', 0, 999, 8, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (75, '13575770801', '770801', '', '颜春芳', 999, NULL, '无', 0, 999, 8, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (76, '13567137265', '137265', '', '盛苑菁', 999, NULL, '无', 0, 999, 8, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (77, '15868161118', '161118', '', '金文通', 999, NULL, '无', 1, 999, 8, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (78, '13003635793', '635793', '', '盛华丽', 999, NULL, '无', 0, 999, 8, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (79, '13868148998', '148998', '', '孙虹翔', 999, NULL, '无', 1, 999, 8, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (80, '13606719273', '719273', '', '管军', 999, NULL, '无', 1, 999, 8, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (81, '13588439259', '439259', '', '戴燕红', 999, NULL, '无', 0, 999, 8, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (82, '15888831268', '831268', '', '余刚', 999, NULL, '无', 1, 999, 9, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (83, '13758165799', '165799', '', '李炫', 999, NULL, '无', 0, 999, 9, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (84, '13685777746', '777746', '', '顾丽娜', 999, NULL, '无', 0, 999, 9, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (85, '13777842078', '842078', '', '王耀杰', 999, NULL, '无', 1, 999, 9, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (86, '15867145060', '145060', '', '王轶敏', 999, NULL, '无', 0, 999, 9, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (87, '13477086027', '086027', '', '杨艳茜', 999, NULL, '无', 0, 999, 9, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (88, '18268831815', '831815', '', '孔潇文', 999, NULL, '无', 0, 999, 9, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (89, '18268170579', '170579', '', '张洪静', 999, NULL, '无', 0, 999, 9, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (90, '13567119796', '119796', '', '沈莉', 999, NULL, '无', 0, 999, 10, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (91, '15057188288', '188288', '', '金明', 999, NULL, '无', 1, 999, 10, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (92, '13588051299', '051299', '', '周梅红', 999, NULL, '无', 0, 999, 10, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (93, '13588866123', '866123', '', '王凤丹', 999, NULL, '无', 1, 999, 10, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (94, '13867179982', '179982', '', '詹文斌', 999, NULL, '无', 1, 999, 10, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (95, '13968018880', '018880', '', '陈兴泉', 999, NULL, '无', 1, 999, 10, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (96, '13758128882', '128882', '', '黄振彪', 999, NULL, '无', 1, 999, 10, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (107, '13588775218', '775218', '', '来伟文', 999, NULL, '无', 0, 999, 11, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (108, '18868717965', '717965', '', '王沈丹', 999, NULL, '无', 0, 999, 11, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (109, '13567165557', '165557', '', '陈飞', 999, NULL, '无', 0, 999, 11, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (110, '13968185726', '185726', '', '陆丹艳', 999, NULL, '无', 0, 999, 11, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (111, '13758263258', '263258', '', '俞劭妍', 999, NULL, '无', 0, 999, 11, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (112, '13867118616', '118616', '', '陈曼丽', 999, NULL, '无', 0, 999, 11, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (113, '13858137779', '137779', '', '盛强', 999, NULL, '无', 1, 999, 11, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (114, '15857180088', '180088', '', '李婕', 999, NULL, '无', 0, 999, 11, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (115, '13588189678', '189678', '', '苏益青', 999, NULL, '无', 0, 999, 11, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (116, '13967103079', '103079', '', '徐江宁', 999, NULL, '无', 1, 999, 11, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (311, '15555566036', '566036', '', '孙彦卿', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (312, '13757433843', '433843', '', '戴雁丽', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (313, '15824503795', '503795', '', '任珠峰', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (314, '18357001993', '001993', '', '沈如玥', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (315, '18817288657', '288657', '', '宣艺田', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (316, '18368891148', '891148', '', '高泽锋', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (317, '15268122651', '122651', '', '雷晓罗', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (318, '13738175196', '175196', '', '陈天昊', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (319, '13018982726', '982726', '', '张帆', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (320, '18758365902', '365902', '', '王旭芳', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (321, '15167154849', '154849', '', '诸沈磊', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (322, '18758112961', '112961', '', '傅丹婷', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (323, '13355715989', '715989', '', '倪洁', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (324, '15868827196', '827196', '', '李娟', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (325, '18801902150', '902150', '', '马一丁', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (326, '18368102688', '102688', '', '胡马涛', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (327, '15869160076', '160076', '', '吴新雨', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (328, '18967189387', '189387', '', '谢永炳', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (329, '15967139472', '139472', '', '季育立', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (330, '15868449776', '449776', '', '朱华阳', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (331, '13967135563', '135563', '', '邓碧波', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (332, '15757131822', '131822', '', '黄益飞', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (333, '13906718183', '718183', '', '金荣尧', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (334, '13967164540', '164540', '', '徐建余', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (335, '18969113919', '113919', '', '王建云', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (336, '13867191275', '191275', '', '周洋', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (337, '13758110073', '110073', '', '陈晓丹', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (338, '13588281682', '281682', '', '郑日华', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (339, '18967175990', '175990', '', '庞幼芳', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (340, '13588433557', '433557', '', '何林夫', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (341, '13967193990', '193990', '', '俞晓琴', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (342, '13867173086', '173086', '', '董建平', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (343, '13867187371', '187371', '', '陈幼芬', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (344, '15336594852', '594852', '', '屠旭鹏', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (345, '15858263932', '263932', '', '魏晓丰', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (346, '13656711556', '711556', '', '周月红', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (347, '13567133336', '133336', '', '钱国英', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (348, '18268048062', '048062', '', '韦思炜', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (349, '15267040321', '040321', '', '杨建生', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (350, '13757100398', '100398', '', '施旋', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (351, '15068172721', '172721', '', '徐顺和', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (352, '13819107092', '107092', '', '林瑞蕊', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (353, '13588251122', '251122', '', '钱锋', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (354, '15988128512', '128512', '', '傅凯杰', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (355, '15824114606', '114606', '', '王雨婷', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (356, '13685771480', '771480', '', '吴宏霞', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (357, '13645718440', '718440', '', '吴敏军', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (358, '13355818900', '818900', '', '韩叶忠', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (359, '13646850800', '850800', '', '徐巨峰', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (360, '13867105559', '105559', '', '吴海明', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (361, '15168191311', '191311', '', '李林', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (362, '15824439800', '439800', '', '韩在伟', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (363, '13967105616', '105616', '', '鲁建峰', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (364, '15705811688', '811688', '', '范建锋', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (365, '18506820500', '820500', '', '周辰', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (366, '13588424901', '424901', '', '宣进', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (367, '13867154840', '154840', '', '沈梁', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (368, '13819163951', '163951', '', '丁永丽', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (369, '18268176362', '176362', '', '蒋金伟', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (370, '13738122331', '122331', '', '马晓君', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (371, '13588002375', '002375', '', '吴垠舟', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (372, '13805700024', '700024', '', '王海青', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (373, '15268821365', '821365', '', '韩瑞生', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (374, '13456853516', '853516', '', '吕伟', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (375, '18758180120', '180120', '', '陈冲', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (376, '13735877372', '877372', '', '林春苗', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (377, '15088680772', '680772', '', '顾灿飞', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (378, '13777380295', '380295', '', '王梁华', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (379, '18967136978', '136978', '', '顾美芳', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (380, '13606609850', '609850', '', '张玉芳', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (381, '13645817447', '817447', '', '蒋亦楠', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (382, '15906685168', '685168', '', '诸悦', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (383, '13067995445', '995445', '', '陈建军', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (384, '13082825898', '825898', '', '戚江强', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (385, '15267168102', '168102', '', '王卓琼', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (386, '13867104088', '104088', '', '王金芳', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (387, '13967106366', '106366', '', '王尧林', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (388, '13958028501', '028501', '', '钱永康', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (389, '15858219940', '219940', '', '马新', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (390, '15356173255', '173255', '', '施加恩', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (391, '13777459722', '459722', '', '刘爽', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (392, '15824114676', '114676', '', '孙莹', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (393, '13967168899', '168899', '', '许红平', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (394, '15267040589', '040589', '', '金伟成', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (395, '13429155955', '155955', '', '田钱军', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (396, '13616504013', '504013', '', '钟皖生', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (397, '15925658780', '658780', '', '王健', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (398, '15022626257', '626257', '', '郭龙伟', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (399, '15858138243', '138243', '', '曾玉平', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (400, '18969117023', '117023', '', '胡玲笑', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (401, '15168224305', '224305', '', '徐帅', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (402, '18969096288', '096288', '', '赵莹', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (403, '13967185095', '185095', '', '魏金灵', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (404, '18758009938', '009938', '', '张寅锋', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (405, '13634128229', '128229', '', '谢伟芳', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (406, '18958118856', '118856', '', '蒋明红', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (407, '15922090133', '090133', '', '王树亮', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (408, '13735886657', '886657', '', '杨细芬', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (409, '13979354995', '354995', '', '顾磊', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (410, '13968194455', '194455', '', '冯启荣', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (411, '18267907086', '907086', '', '郑炎栋', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (412, '18668129695', '129695', '', '陈晓磊', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (413, '13588776554', '776554', '', '金凌芳', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (414, '18268106667', '106667', '', '刘斌', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (415, '15268802006', '802006', '', '张祺', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (416, '13646830092', '830092', '', '陈建荣', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (417, '15715813053', '813053', '', '兰新花', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (418, '13285819341', '819341', '', '魏克辉', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (419, '18958118859', '118859', '', '李震球', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (420, '13989825257', '825257', '', '傅岳民', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (421, '13358035750', '035750', '', '徐智松', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (422, '18158939687', '939687', '', '郑凯', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (423, '13957151203', '151203', '', '郭姣懂', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (424, '13777598363', '598363', '', '喻婷', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (425, '13666681513', '681513', '', '王佳', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (426, '13758164101', '164101', '', '李四明', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (427, '13967181880', '181880', '', '何颖丽', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (428, '15988838881', '838881', '', '沈剑光', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (429, '13396594639', '594639', '', '俞宁之', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (430, '18768137002', '137002', '', '陈聪玮', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (431, '13093732015', '732015', '', '应桃园', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (432, '83208907', '907', '', '沈幸', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (433, '13456900196', '900196', '', '詹菁', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (434, '15168354628', '354628', '', '邓宁宁', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (435, '18358137198', '137198', '', '方晓慧', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (436, '13666672110', '672110', '', '张国维', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (437, '13806520327', '520327', '', '杨烨', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (438, '13588765569', '765569', '', '谢诚', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (439, '13666642168', '642168', '', '范小亮', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (440, '13386509968', '509968', '', '肖星荣', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (441, '18806819373', '819373', '', '陈春凤', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (442, '18868756394', '756394', '', '施春燕', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (443, '15067189292', '189292', '', '朱佳莹', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (444, '15906719031', '719031', '', '朱芳', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (445, '15088665605', '665605', '', '王思怡', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (446, '13777389918', '389918', '', '刘志明', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (447, '18768193043', '193043', '', '臧华辉', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (448, '18042487551', '487551', '', '毛永成', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (449, '15068058089', '058089', '', '黄小巧', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (450, '13750880282', '880282', '', '张飞', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (451, '13606716623', '716623', '', '倪腾飞', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (452, '13867181436', '181436', '', '孙丹', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (453, '13588295071', '295071', '', '沈小雁', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (454, '13429157341', '157341', '', '任芳', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (455, '18967111002', '111002', '', '胡凤琴', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (456, '13967148088', '148088', '', '何颖', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (457, '17855848570', '848570', '', '杨学坡', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (458, '13867105066', '105066', '', '袁掌珠', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (459, '15728046871', '046871', '', '赵媛媛', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (460, '15268567608', '567608', '', '金秀梅', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (461, '18698557741', '557741', '', '张璐', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (462, '15867156580', '156580', '', '李丽', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (463, '13867461380', '461380', '', '井代玲', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (464, '15988897811', '897811', '', '陈玲', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (465, '13857106897', '106897', '', '邵树峰', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (466, '15824471278', '471278', '', '潜浪', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (467, '18857104232', '104232', '', '沈丹', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (468, '13588258585', '258585', '', '张军', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (469, '13867167477', '167477', '', '励洁', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (470, '13588783835', '783835', '', '陈峰', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (471, '13064777023', '777023', '', '王晓桦', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (472, '15058100800', '100800', '', '屠国峰', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (473, '15268637893', '637893', '', '沈杭艳', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (474, '13093747252', '747252', '', '丁美', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (475, '13185011687', '011687', '', '周雪枫', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (476, '15258874804', '874804', '', '冯晓雯', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (477, '18969110286', '110286', '', '姜弘', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (478, '18072871620', '871620', '', '刘云艳', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (479, '13588890001', '890001', '', '周梅君', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (480, '18359630067', '630067', '', '钟杭娣', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (481, '15270091681', '091681', '', '秦丹凤', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (482, '18368187369', '187369', '', '杜心玉', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (483, '13805754535', '754535', '', '曹飞颖', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (484, '13625811668', '811668', '', '翁一飞', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (485, '18906713310', '713310', '', '黄祖锋', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (486, '15868166660', '166660', '', '戚春来', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (487, '13588293608', '293608', '', '戴晓燕', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (488, '13967114671', '114671', '', '林萍', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (489, '13758103566', '103566', '', '王爱娟', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (490, '18768416518', '416518', '', '叶仁杰', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (491, '13806504553', '504553', '', '徐兴潮', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (492, '15868119039', '119039', '', '徐岳峰', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (493, '18058185312', '185312', '', '颜敏娟', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (494, '15958163140', '163140', '', '郭亚茹', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (495, '13575450478', '450478', '', '黄德超', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (496, '13516778803', '778803', '', '韩延杰', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (497, '15906815118', '815118', '', '张均杰', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (498, '15024546520', '546520', '', '严智慧', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (499, '13735843753', '843753', '', '孙欢龙', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (500, '13575765985', '765985', '', '瞿红飞', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (501, '13735899522', '899522', '', '邵华美', 999, NULL, '无', 0, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (502, '13625819666', '819666', '', '章国尧', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (503, '13606634889', '634889', '', '庾谷成', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (504, '13093791679', '791679', '', '朱奕明', 999, NULL, '无', 1, 999, 12, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (505, '13871458704', '458704', '工作人员', '汪瑞琪', 999, NULL, '无', 0, 999, 13, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (506, '13868043414', '043414', '主任', '丁洪云', 999, NULL, '无', 1, 999, 13, '2020-12-27', NULL, '', '', '无');
INSERT INTO `user` VALUES (507, '', '', '', '', 999, NULL, '无', 0, 999, 1, '2020-12-27', NULL, '', '', '无');
COMMIT;

-- ----------------------------
-- Table structure for user_grade
-- ----------------------------
DROP TABLE IF EXISTS `user_grade`;
CREATE TABLE `user_grade` (
  `grade_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `grade_rank` varchar(255) DEFAULT NULL,
  `leader_grade` varchar(255) DEFAULT NULL,
  `democracy_grade` varchar(255) DEFAULT NULL,
  `assess_batch_id` int DEFAULT NULL,
  PRIMARY KEY (`grade_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_grade
-- ----------------------------
BEGIN;
INSERT INTO `user_grade` VALUES (1, 1, '优秀', '1', '1', 4);
INSERT INTO `user_grade` VALUES (2, 5, '良好', '100', '100', 4);
INSERT INTO `user_grade` VALUES (4, 5, '及格', '50', '50', 2);
INSERT INTO `user_grade` VALUES (6, 19, '优秀', NULL, NULL, 2);
INSERT INTO `user_grade` VALUES (7, 19, '优秀', NULL, NULL, 4);
INSERT INTO `user_grade` VALUES (10, 10, '优秀', NULL, NULL, 11);
INSERT INTO `user_grade` VALUES (12, 10, '优秀', NULL, NULL, 12);
INSERT INTO `user_grade` VALUES (27, 10, '优秀', NULL, NULL, 13);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
