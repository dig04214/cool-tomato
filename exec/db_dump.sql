-- MySQL dump 10.13  Distrib 5.7.43, for Win64 (x86_64)
--
-- Host: MASKING_URL    Database: wp
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- Table structure for table `alarm_manage`
--

DROP TABLE IF EXISTS `alarm_manage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alarm_manage` (
  `alarm_manage_id` bigint NOT NULL AUTO_INCREMENT,
  `alarm_content` varchar(255) DEFAULT NULL,
  `alarm_title` varchar(255) DEFAULT NULL,
  `alarm_url` varchar(255) DEFAULT NULL,
  `register_date` datetime(6) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`alarm_manage_id`),
  KEY `FKl6g9phpue6rdtv9nend6np8kl` (`user_id`),
  CONSTRAINT `FKl6g9phpue6rdtv9nend6np8kl` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `block_manage`
--

DROP TABLE IF EXISTS `block_manage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `block_manage` (
  `block_manage_id` bigint NOT NULL AUTO_INCREMENT,
  `register_date` datetime(6) DEFAULT NULL,
  `blocked_id` bigint DEFAULT NULL,
  `seller_id` bigint DEFAULT NULL,
  PRIMARY KEY (`block_manage_id`),
  KEY `FKfq1g7l76u16lkvn5o20rwybaw` (`blocked_id`),
  KEY `FK1d611cbil143t6r7fb8kehydp` (`seller_id`),
  CONSTRAINT `FK1d611cbil143t6r7fb8kehydp` FOREIGN KEY (`seller_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKfq1g7l76u16lkvn5o20rwybaw` FOREIGN KEY (`blocked_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `broadcast_analyze`
--

DROP TABLE IF EXISTS `broadcast_analyze`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `broadcast_analyze` (
  `broadcast_analyze_id` bigint NOT NULL AUTO_INCREMENT,
  `live_broadcast_id` bigint DEFAULT NULL,
  `content` mediumtext,
  PRIMARY KEY (`broadcast_analyze_id`),
  KEY `broadcast_analyze_live_broadcast_FK` (`live_broadcast_id`),
  CONSTRAINT `broadcast_analyze_live_broadcast_FK` FOREIGN KEY (`live_broadcast_id`) REFERENCES `live_broadcast` (`live_broadcast_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `category_id` bigint NOT NULL AUTO_INCREMENT,
  `category_master_id` bigint NOT NULL,
  `category_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `category_content` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `seq` int DEFAULT '0',
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_un` (`category_code`),
  KEY `category_category_master_FK` (`category_master_id`),
  CONSTRAINT `category_category_master_FK` FOREIGN KEY (`category_master_id`) REFERENCES `category_master` (`category_master_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category_master`
--

DROP TABLE IF EXISTS `category_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_master` (
  `category_master_id` bigint NOT NULL AUTO_INCREMENT,
  `category_master_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `category_master_content` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `seq` int DEFAULT '0',
  PRIMARY KEY (`category_master_id`),
  UNIQUE KEY `category_master_un` (`category_master_code`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chatbot`
--

DROP TABLE IF EXISTS `chatbot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chatbot` (
  `chatbot_id` bigint NOT NULL AUTO_INCREMENT,
  `room_id` bigint DEFAULT NULL,
  `seller_id` bigint DEFAULT NULL,
  `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `register_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modification_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`chatbot_id`),
  KEY `chatbot_question_id_IDX` (`chatbot_id`) USING BTREE,
  KEY `chatbot_user_FK` (`seller_id`),
  CONSTRAINT `chatbot_user_FK` FOREIGN KEY (`seller_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `follow_manage`
--

DROP TABLE IF EXISTS `follow_manage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `follow_manage` (
  `follow_alarm_setting` bit(1) NOT NULL,
  `follow_manage_id` bigint NOT NULL AUTO_INCREMENT,
  `follower_id` bigint DEFAULT NULL,
  `following_id` bigint DEFAULT NULL,
  `register_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`follow_manage_id`),
  KEY `FKixcmhxhlmu9m5vrwcaefcuh5t` (`follower_id`),
  KEY `FKff70gfxn5od5xu10xk2ugpwdy` (`following_id`),
  CONSTRAINT `FKff70gfxn5od5xu10xk2ugpwdy` FOREIGN KEY (`following_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKixcmhxhlmu9m5vrwcaefcuh5t` FOREIGN KEY (`follower_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `live_broadcast`
--

DROP TABLE IF EXISTS `live_broadcast`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `live_broadcast` (
  `live_broadcast_id` bigint NOT NULL AUTO_INCREMENT,
  `broadcast_end_date` datetime(6) DEFAULT NULL,
  `broadcast_start_date` datetime(6) DEFAULT NULL,
  `broadcast_status` bit(1) DEFAULT NULL,
  `broadcast_title` varchar(255) DEFAULT NULL,
  `chatbot_setting` bit(1) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `modification_time` datetime(6) DEFAULT NULL,
  `register_date` datetime(6) DEFAULT NULL,
  `script` varchar(255) DEFAULT NULL,
  `session_id` varchar(255) DEFAULT NULL,
  `share_url` varchar(255) DEFAULT NULL,
  `topic_id` varchar(255) DEFAULT NULL,
  `tts_setting` bit(1) DEFAULT NULL,
  `view_count` bigint DEFAULT NULL,
  `seller_id` bigint DEFAULT NULL,
  PRIMARY KEY (`live_broadcast_id`),
  KEY `FKh2wpgm5vml93rb2mdqcw47son` (`seller_id`),
  CONSTRAINT `FKh2wpgm5vml93rb2mdqcw47son` FOREIGN KEY (`seller_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `live_product`
--

DROP TABLE IF EXISTS `live_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `live_product` (
  `live_product_id` bigint NOT NULL AUTO_INCREMENT,
  `live_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `live_flat_price` int DEFAULT '0',
  `live_rate_price` int DEFAULT '0',
  `live_price_start_date` timestamp NULL DEFAULT NULL,
  `live_price_end_date` timestamp NULL DEFAULT NULL,
  `main_product_setting` tinyint(1) DEFAULT '0',
  `register_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `seq` int DEFAULT '0',
  PRIMARY KEY (`live_product_id`),
  KEY `live_product_product_FK` (`product_id`),
  KEY `live_product_live_broadcast_FK` (`live_id`),
  CONSTRAINT `live_product_product_FK` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `product_id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` bigint NOT NULL,
  `seller_id` bigint NOT NULL,
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `product_content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `payment_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `price` int NOT NULL DEFAULT '0',
  `delivery_charge` int NOT NULL DEFAULT '0',
  `quantity` int NOT NULL DEFAULT '0',
  `register_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `img_src` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  PRIMARY KEY (`product_id`),
  KEY `product_category_FK` (`category_id`),
  KEY `product_user_FK` (`seller_id`),
  CONSTRAINT `product_user_FK` FOREIGN KEY (`seller_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_question_board`
--

DROP TABLE IF EXISTS `product_question_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_question_board` (
  `product_question_board_id` bigint NOT NULL AUTO_INCREMENT,
  `writer_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `question_content` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  `answer_content` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  `question_register_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `answer_register_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`product_question_board_id`),
  KEY `product_question_board_FK` (`product_id`),
  CONSTRAINT `product_question_board_product_FK` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `seller_info`
--

DROP TABLE IF EXISTS `seller_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seller_info` (
  `approval_status` bit(1) NOT NULL,
  `register_date` datetime(6) DEFAULT NULL,
  `seller_info_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `business_number` varchar(100) DEFAULT NULL,
  `mail_order_sales_number` varchar(100) DEFAULT NULL,
  `business_address` varchar(255) DEFAULT NULL,
  `business_content` tinytext,
  PRIMARY KEY (`seller_info_id`),
  KEY `FKhih84mu2qhr8bl2qg5w6hsdyw` (`user_id`),
  CONSTRAINT `FKhih84mu2qhr8bl2qg5w6hsdyw` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `birthday` date NOT NULL,
  `join_date` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `login_id` varchar(50) NOT NULL,
  `nickname` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `profile_img` varchar(255) DEFAULT NULL,
  `auth` enum('ADMIN','SELLER','BUYER') NOT NULL,
  `sex` enum('M','F') NOT NULL,
  `modify_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_6ntlp6n5ltjg6hhxl66jj5u0l` (`login_id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'wp'
--

--
-- GTID state at the end of the backup 
--

SET @@GLOBAL.GTID_PURGED='';
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-15 16:14:13
