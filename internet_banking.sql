CREATE DATABASE  IF NOT EXISTS `internet_banking` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `internet_banking`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: internet_banking
-- ------------------------------------------------------
-- Server version	5.5.30

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

--
-- Table structure for table `client_balance`
--

DROP TABLE IF EXISTS `client_balance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client_balance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client_id` int(11) NOT NULL,
  `balance` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_UNIQUE` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_balance`
--

LOCK TABLES `client_balance` WRITE;
/*!40000 ALTER TABLE `client_balance` DISABLE KEYS */;
INSERT INTO `client_balance` VALUES (1,1,450.00),(2,3,966.59),(3,4,10.00),(4,5,2152.50),(5,6,987.80),(6,7,20.00),(7,8,2370.01),(8,9,0.00),(9,10,0.00),(10,11,0.00),(11,12,437.00),(12,13,25.00),(13,14,260.60),(14,15,0.00),(15,16,3000.00),(16,17,1500.00),(17,18,442.50),(18,19,0.00),(19,20,140.00),(20,21,70.00),(21,22,5675.00);
/*!40000 ALTER TABLE `client_balance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clients` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `status` enum('NEW','ACTIVE','BLOCKED') NOT NULL,
  `number` varchar(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'Sergey','Ivanov','ACTIVE','4115418816152131'),(3,'Alex','Gray','ACTIVE','4115335446556131'),(4,'Kate','Smith','ACTIVE','4235418816152145'),(5,'Hanna','Morgan','ACTIVE','4335415856152367'),(6,'James','Brown','ACTIVE','4115418816152138'),(7,'John','Milton','BLOCKED','4115418816152139'),(8,'Emma','Mærsk','ACTIVE','4145418816152130'),(9,'Dennis','Young','NEW','4145418816152140'),(10,'Emily','White','NEW','4145418816152141'),(11,'Jeffery','Hall','NEW','4145418816152142'),(12,'Michael','Cooper','ACTIVE','4145418816152143'),(13,'Ryan','Scott','ACTIVE','4145418816152144'),(14,'Nicole','Evans','ACTIVE','4145418816152145'),(15,'Monica','Bell','NEW','4145418816152155'),(16,'George','Morris','ACTIVE','4145418816152146'),(17,'Howard','Turner','BLOCKED','4145418816152147'),(18,'Kevin','Ross','ACTIVE','4145418816152148'),(19,'Rose','Foster','NEW','4145418816152149'),(20,'Tyler','Jenkins','ACTIVE','4145418816152150'),(21,'Zoe','Johnson','ACTIVE','4145418816152151'),(22,'Alex','King','BLOCKED','4145418816152152');
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `source_id` int(11) NOT NULL,
  `destination_id` int(11) NOT NULL,
  `transaction_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sum` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,3,5,'2013-02-13 10:27:03',550.00),(2,3,6,'2013-02-13 10:27:17',10.00),(3,7,3,'2013-02-13 10:27:33',1001.00),(6,3,4,'2013-02-16 14:20:36',10.00),(7,3,5,'2013-02-16 20:07:21',2.50),(8,3,6,'2013-02-17 09:18:27',15.85),(9,3,8,'2013-02-17 09:20:07',20.11),(10,1,5,'2013-02-17 09:23:48',150.00),(11,8,14,'2013-02-17 14:38:02',250.10),(12,8,18,'2013-02-17 14:38:39',400.00),(13,12,3,'2013-02-17 14:40:18',98.00),(14,12,18,'2013-02-17 14:40:55',40.00),(15,12,21,'2013-02-17 14:41:07',5.00),(16,20,14,'2013-02-17 14:42:36',10.00),(17,20,3,'2013-02-17 14:42:53',50.00),(18,3,14,'2013-02-17 14:43:39',30.50),(19,3,21,'2013-02-17 14:44:25',15.00),(20,13,3,'2013-02-17 14:45:40',1000.00),(21,13,12,'2013-02-17 14:46:26',25.00),(22,3,6,'2013-02-17 14:47:37',187.45),(23,14,12,'2013-02-17 16:26:54',55.00);
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `role` enum('ROLE_USER','ROLE_ADMIN') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'test','202cb962ac59075b964b07152d234b70','ROLE_USER'),(2,'admin','202cb962ac59075b964b07152d234b70','ROLE_ADMIN'),(3,'client1','202cb962ac59075b964b07152d234b70','ROLE_USER'),(4,'client2','202cb962ac59075b964b07152d234b70','ROLE_USER'),(5,'client3','202cb962ac59075b964b07152d234b70','ROLE_USER'),(6,'client4','202cb962ac59075b964b07152d234b70','ROLE_USER'),(7,'client5','202cb962ac59075b964b07152d234b70','ROLE_USER'),(8,'client6','202cb962ac59075b964b07152d234b70','ROLE_USER'),(9,'client7','202cb962ac59075b964b07152d234b70','ROLE_USER'),(10,'client8','202cb962ac59075b964b07152d234b70','ROLE_USER'),(11,'client9','202cb962ac59075b964b07152d234b70','ROLE_USER'),(12,'client10','202cb962ac59075b964b07152d234b70','ROLE_USER'),(13,'client11','202cb962ac59075b964b07152d234b70','ROLE_USER'),(14,'client12','202cb962ac59075b964b07152d234b70','ROLE_USER'),(15,'client13','202cb962ac59075b964b07152d234b70','ROLE_USER'),(16,'client14','202cb962ac59075b964b07152d234b70','ROLE_USER'),(17,'client15','202cb962ac59075b964b07152d234b70','ROLE_USER'),(18,'client16','202cb962ac59075b964b07152d234b70','ROLE_USER'),(19,'client17','202cb962ac59075b964b07152d234b70','ROLE_USER'),(20,'client18','202cb962ac59075b964b07152d234b70','ROLE_USER'),(21,'client19','202cb962ac59075b964b07152d234b70','ROLE_USER'),(22,'client20','202cb962ac59075b964b07152d234b70','ROLE_USER');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

