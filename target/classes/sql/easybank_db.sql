-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: easybank_db
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `account_type` varchar(10) NOT NULL,
  `balance` decimal(15,2) NOT NULL,
  `account_number` varchar(12) NOT NULL,
  `rate_of_interest` decimal(5,2) NOT NULL,
  `branch_id` varchar(10) NOT NULL,
  `opening_date` date NOT NULL,
  `closing_date` date DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `branch_id` (`branch_id`)
) ENGINE=MyISAM AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (2,1,'Current',7190.00,'111411150002',5.20,'EB124256','2024-07-20','2074-07-20','2024-07-20 02:05:17',1,'2024-07-20 02:05:17',1),(40,46,'Saving',6650.00,'111411200001',3.40,'EB124256','2024-07-20','2074-07-20','2024-07-20 15:00:07',1,'2024-07-20 15:27:54',1),(1,1,'Saving',4950.00,'111411150001',3.40,'EB124256','2024-07-20','2074-07-20','2024-07-20 02:05:17',1,'2024-07-20 02:05:17',1);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `address` text,
  `pincode` varchar(7) NOT NULL,
  `city_id` int NOT NULL,
  `district_id` int NOT NULL,
  `state_id` int NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` int NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `city_id` (`city_id`),
  KEY `district_id` (`district_id`),
  KEY `state_id` (`state_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (11,46,'koradiii','440002',11,11,11,'2024-07-20 15:00:07',1,'2024-07-20 15:27:54',1),(1,1,'friends colony katol roads','440013',1,1,1,'2024-07-20 02:05:17',1,'2024-07-24 13:02:56',1);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `middle_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) NOT NULL,
  `mobile_number` varchar(10) NOT NULL,
  `email` varchar(50) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `date_of_birth` date NOT NULL,
  `adhaar_card` varchar(12) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` int NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'Vinay','Charandas','Thaware','9383737376','vinay@gmail.com','Male','2000-07-26','124262626262','2024-06-15 17:15:05',1,'2024-08-02 01:11:54',1);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_address`
--

DROP TABLE IF EXISTS `admin_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `admin_id` int NOT NULL,
  `address` text,
  `state` varchar(20) NOT NULL,
  `city` varchar(20) NOT NULL,
  `pincode` varchar(7) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` int NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `admin_id` (`admin_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_address`
--

LOCK TABLES `admin_address` WRITE;
/*!40000 ALTER TABLE `admin_address` DISABLE KEYS */;
INSERT INTO `admin_address` VALUES (1,1,'friends colony','Maharashtra','Nagurr','440012','2024-06-15 17:15:05',1,'2024-08-02 01:11:54',1);
/*!40000 ALTER TABLE `admin_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_credential`
--

DROP TABLE IF EXISTS `admin_credential`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_credential` (
  `id` int NOT NULL AUTO_INCREMENT,
  `admin_id` int NOT NULL,
  `user_name` varchar(10) NOT NULL,
  `password` varchar(200) NOT NULL,
  `password_salt` varchar(100) NOT NULL,
  `login_date_time` datetime NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` int NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `admin_id` (`admin_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_credential`
--

LOCK TABLES `admin_credential` WRITE;
/*!40000 ALTER TABLE `admin_credential` DISABLE KEYS */;
INSERT INTO `admin_credential` VALUES (1,1,'Admin','Adimn@123','gfhdfhfghdgsghds','2024-06-15 17:15:05','2024-06-15 17:15:05',1,'2024-08-02 01:11:54',1);
/*!40000 ALTER TABLE `admin_credential` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `code` int NOT NULL,
  `ifsc_code` varchar(10) NOT NULL,
  `city_id` int NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` int NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `city_id` (`city_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `code` int NOT NULL,
  `district_id` int NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` int NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `district_id` (`district_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (11,'Nagpur',14,11,'2024-07-20 15:00:07',1,'2024-07-20 15:00:07',1),(12,'Surat',16,12,'2024-07-24 12:56:17',1,'2024-07-24 12:56:17',1),(10,'Nagpur',14,10,'2024-07-20 14:39:00',1,'2024-07-20 14:39:00',1),(1,'Nagpur',14,1,'2024-07-20 02:05:17',1,'2024-07-20 02:05:17',1);
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `district`
--

DROP TABLE IF EXISTS `district`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `district` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `code` int NOT NULL,
  `state_id` int NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` int NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `state_id` (`state_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `district`
--

LOCK TABLES `district` WRITE;
/*!40000 ALTER TABLE `district` DISABLE KEYS */;
INSERT INTO `district` VALUES (11,'kamptee',1120,11,'2024-07-20 15:00:07',1,'2024-07-20 15:00:07',1),(12,'surat city',2223,12,'2024-07-24 12:56:17',1,'2024-07-24 12:56:17',1),(10,'nagpur city',1115,10,'2024-07-20 14:39:00',1,'2024-07-20 14:39:00',1),(1,'nagpur city',1115,1,'2024-07-20 02:05:17',1,'2024-07-20 02:05:17',1);
/*!40000 ALTER TABLE `district` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `middle_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `mobile_number` varchar(10) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `gender` varchar(6) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `adhaar_card` varchar(12) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Vinay','Charandas','Thaware','9827272827','vinay@gmail.com','Male','2000-07-26','123456789239','2024-06-15 17:15:05',1,'2024-07-25 20:05:44',1),(2,'Pranav','S','Armarkar','7262728282','pranav@gmail.com','m','2002-06-12','213456457264','2024-06-15 17:15:06',1,'2024-06-15 17:15:06',1);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_address`
--

DROP TABLE IF EXISTS `employee_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_id` int NOT NULL,
  `address` text,
  `state` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `pincode` varchar(7) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `employee_id` (`employee_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_address`
--

LOCK TABLES `employee_address` WRITE;
/*!40000 ALTER TABLE `employee_address` DISABLE KEYS */;
INSERT INTO `employee_address` VALUES (1,1,'friends  colony','Gujarat','Surat','440013','2024-06-15 17:15:05',1,'2024-07-25 20:05:44',1),(2,2,'Mankapur','Maharashtra','Nagpur','440012','2024-07-09 14:08:59',1,'2024-07-09 14:08:59',1);
/*!40000 ALTER TABLE `employee_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_credential`
--

DROP TABLE IF EXISTS `employee_credential`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_credential` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_id` int DEFAULT NULL,
  `user_name` varchar(10) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `password_salt` varchar(100) DEFAULT NULL,
  `login_date_time` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `employee_id` (`employee_id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_credential`
--

LOCK TABLES `employee_credential` WRITE;
/*!40000 ALTER TABLE `employee_credential` DISABLE KEYS */;
INSERT INTO `employee_credential` VALUES (1,1,'vinay12','Vinay@1234','jkshshhsshjshs','2024-06-15 17:15:05','2024-06-15 17:15:05',1,'2024-07-25 20:05:44',1),(2,2,'pranav23','pranav@123','qhjhudhssusujs','2024-06-15 17:15:08','2024-06-15 17:15:08',1,'2024-06-15 17:15:08',1);
/*!40000 ALTER TABLE `employee_credential` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pending_transaction_request`
--

DROP TABLE IF EXISTS `pending_transaction_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pending_transaction_request` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `account_number` varchar(14) NOT NULL,
  `amount` decimal(15,2) NOT NULL,
  `transaction_type` varchar(255) NOT NULL,
  `request_date_time` timestamp NOT NULL,
  `status` varchar(12) NOT NULL,
  `created_at` timestamp NOT NULL,
  `created_by` int NOT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pending_transaction_request`
--

LOCK TABLES `pending_transaction_request` WRITE;
/*!40000 ALTER TABLE `pending_transaction_request` DISABLE KEYS */;
INSERT INTO `pending_transaction_request` VALUES (1,1,'111411150002',200.00,'Credit','2024-07-27 15:30:00','APPROVED','2024-07-27 15:30:00',1,NULL,NULL),(2,1,'111411150002',100.00,'Credit','2024-07-27 15:38:18','APPROVED','2024-07-27 15:38:18',1,NULL,NULL),(3,1,'111411150002',250.00,'Debit','2024-07-27 15:45:22','APPROVED','2024-07-27 15:45:22',1,NULL,NULL),(4,1,'111411150002',120.00,'Debit','2024-07-27 15:48:14','APPROVED','2024-07-27 15:48:14',1,NULL,NULL),(5,1,'111411150002',140.00,'Debit','2024-07-27 15:49:59','APPROVED','2024-07-27 15:49:59',1,NULL,NULL),(6,1,'111411150002',800.00,'Credit','2024-07-27 15:53:14','APPROVED','2024-07-27 15:53:14',1,NULL,NULL),(7,1,'111411150002',200.00,'Credit','2024-07-29 06:59:11','APPROVED','2024-07-29 06:59:11',1,NULL,NULL);
/*!40000 ALTER TABLE `pending_transaction_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `state` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `code` int NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` int NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `state`
--

LOCK TABLES `state` WRITE;
/*!40000 ALTER TABLE `state` DISABLE KEYS */;
INSERT INTO `state` VALUES (12,'Gujarat',13,'2024-07-24 12:56:17',1,'2024-07-24 12:56:17',1),(11,'Maharashtra',11,'2024-07-20 15:00:07',1,'2024-07-20 15:00:07',1),(10,'Maharashtra',11,'2024-07-20 14:39:00',1,'2024-07-20 14:39:00',1),(1,'Maharashtra',11,'2024-07-20 02:05:17',1,'2024-07-20 02:05:17',1);
/*!40000 ALTER TABLE `state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `account_number` varchar(14) NOT NULL,
  `amount` decimal(15,2) NOT NULL,
  `transaction_date_time` datetime NOT NULL,
  `transaction_type` enum('Credit','Debit') NOT NULL,
  `transaction_status` varchar(12) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` int NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,1,'132456789123',1000.00,'2024-06-15 17:15:05','Credit','completed','2024-06-15 17:15:05',1,'2024-06-15 17:15:05',1),(22,1,'111411150002',200.00,'2024-07-27 21:04:24','Credit','SUCCESS','2024-07-27 21:04:24',1,NULL,NULL),(21,1,'111411150001',200.00,'2024-07-25 22:35:36','Credit','SUCCESS','2024-07-25 22:35:36',1,NULL,NULL),(20,1,'111411150002',400.00,'2024-07-25 22:34:50','Credit','SUCCESS','2024-07-25 22:34:50',1,NULL,NULL),(19,1,'111411150002',100.00,'2024-07-25 22:16:02','Debit','SUCCESS','2024-07-25 22:16:02',1,NULL,NULL),(18,1,'111411150002',200.00,'2024-07-25 22:15:46','Credit','SUCCESS','2024-07-25 22:15:46',1,NULL,NULL),(7,1,'132456789123',231.00,'2024-07-16 21:27:37','Credit','SUCCESS','2024-07-16 21:27:37',1,NULL,NULL),(8,1,'132456789123',231.00,'2024-07-16 21:41:08','Credit','SUCCESS','2024-07-16 21:41:08',1,NULL,NULL),(9,1,'132456789123',231.00,'2024-07-16 21:42:19','Credit','SUCCESS','2024-07-16 21:42:19',1,NULL,NULL),(10,1,'132456789123',355.00,'2024-07-16 21:57:55','Debit','SUCCESS','2024-07-16 21:57:55',1,NULL,NULL),(11,1,'132456789123',1000.00,'2024-07-16 21:59:20','Credit','SUCCESS','2024-07-16 21:59:20',1,NULL,NULL),(12,1,'132456789123',938.00,'2024-07-16 21:59:41','Debit','SUCCESS','2024-07-16 21:59:41',1,NULL,NULL),(13,1,'111411150001',500.00,'2024-07-20 15:40:15','Credit','SUCCESS','2024-07-20 15:40:15',1,NULL,NULL),(14,1,'111411150001',100.00,'2024-07-22 00:52:35','Debit','SUCCESS','2024-07-22 00:52:35',1,NULL,NULL),(15,1,'111411150002',600.00,'2024-07-22 00:54:04','Credit','SUCCESS','2024-07-22 00:54:04',1,NULL,NULL),(16,1,'111411150002',200.00,'2024-07-22 00:54:28','Debit','SUCCESS','2024-07-22 00:54:28',1,NULL,NULL),(17,1,'111411150002',500.00,'2024-07-24 13:01:22','Credit','SUCCESS','2024-07-24 13:01:22',1,NULL,NULL),(23,1,'111411150002',100.00,'2024-07-27 21:08:27','Credit','SUCCESS','2024-07-27 21:08:27',1,NULL,NULL),(24,1,'111411150002',250.00,'2024-07-27 21:15:33','Debit','SUCCESS','2024-07-27 21:15:33',1,NULL,NULL),(25,1,'111411150002',120.00,'2024-07-27 21:18:26','Debit','SUCCESS','2024-07-27 21:18:26',1,NULL,NULL),(26,1,'111411150002',140.00,'2024-07-27 21:20:05','Debit','SUCCESS','2024-07-27 21:20:05',1,NULL,NULL),(27,1,'111411150002',800.00,'2024-07-27 21:23:19','Credit','SUCCESS','2024-07-27 21:23:19',1,NULL,NULL),(28,1,'111411150002',200.00,'2024-07-29 12:29:51','Credit','SUCCESS','2024-07-29 12:29:51',1,NULL,NULL);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transfer_transaction`
--

DROP TABLE IF EXISTS `transfer_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transfer_transaction` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sender_id` int DEFAULT NULL,
  `sender_account_number` varchar(12) DEFAULT NULL,
  `receiver_id` int DEFAULT NULL,
  `receiver_account_number` varchar(12) DEFAULT NULL,
  `transfer_amount` double DEFAULT NULL,
  `sender_balance` double DEFAULT NULL,
  `receiver_balance` double DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transfer_transaction`
--

LOCK TABLES `transfer_transaction` WRITE;
/*!40000 ALTER TABLE `transfer_transaction` DISABLE KEYS */;
INSERT INTO `transfer_transaction` VALUES (1,1,'132456789123',6,'134740487605',500,9500,1000,1,'2024-06-15 17:15:05',1,'2024-06-15 17:15:05'),(3,1,'132456789123',9,'146273146124',700,1000,1000,1,'2024-06-15 17:15:05',1,'2024-06-15 17:15:05'),(5,1,'132456789123',35,'111411150003',200,9800,5650,1,'2024-07-16 20:54:16',1,'2024-07-16 20:54:16'),(6,1,'132456789123',35,'111411150003',200,9600,5850,1,'2024-07-16 20:56:16',1,'2024-07-16 20:56:16'),(7,1,'111411150001',46,'111411200001',500,5000,6500,1,'2024-07-20 15:59:45',1,'2024-07-20 15:59:45'),(8,1,'111411150001',46,'111411200001',100,4900,6600,1,'2024-07-20 16:03:17',1,'2024-07-20 16:03:17'),(9,1,'111411150001',46,'111411200001',50,4850,6650,1,'2024-07-20 16:04:37',1,'2024-07-20 16:04:37');
/*!40000 ALTER TABLE `transfer_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `middle_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `mobile_number` varchar(10) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `gender` char(6) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `cin` varchar(20) DEFAULT NULL,
  `adhaar_card` varchar(12) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (46,'Pranavv','D','Armarkar','8763536379','pranav@gmail.com','Male','2024-07-08','20240720150006','123424563459','2024-07-20 15:00:07',1,'2024-07-20 15:27:54',1),(1,'Vinay','Charandas','Thaware','9373007087','vinay142@gmail.com','Male','2000-07-26','20240720020516','123415625219','2024-07-20 02:05:17',1,'2024-07-24 13:02:56',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_credential`
--

DROP TABLE IF EXISTS `user_credential`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_credential` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `user_name` varchar(10) NOT NULL,
  `password` varchar(200) NOT NULL,
  `password_salt` varchar(100) NOT NULL,
  `login_date_time` datetime NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` int NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_credential`
--

LOCK TABLES `user_credential` WRITE;
/*!40000 ALTER TABLE `user_credential` DISABLE KEYS */;
INSERT INTO `user_credential` VALUES (46,46,'pranav13','Pranav@123','2db13899-7b06-4bca-9c44-5af1675b73fe','2024-07-20 15:00:07','2024-07-20 15:00:07',1,'2024-07-20 15:27:54',1),(1,1,'vinay12','Vinay@123','e65abdb0-64a5-4f65-bfef-422308c75439','2024-07-20 02:05:17','2024-07-20 02:05:17',1,'2024-07-24 13:02:56',1);
/*!40000 ALTER TABLE `user_credential` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-02 16:52:45
