-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: emsdb
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `attendee_info`
--

DROP TABLE IF EXISTS `attendee_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendee_info` (
  `attendee_id` int NOT NULL AUTO_INCREMENT,
  `attendee_name` varchar(45) NOT NULL,
  `e_mail` varchar(45) NOT NULL,
  `contact_number` int NOT NULL,
  `event_selection` varchar(45) NOT NULL,
  PRIMARY KEY (`attendee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendee_info`
--

LOCK TABLES `attendee_info` WRITE;
/*!40000 ALTER TABLE `attendee_info` DISABLE KEYS */;
INSERT INTO `attendee_info` VALUES (6,'LEO','ZAZA@GMAIL',595884,'DA Clpp'),(7,'ZANELE','TRIR@GMAIL',95873848,'NONO');
/*!40000 ALTER TABLE `attendee_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_details`
--

DROP TABLE IF EXISTS `event_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_details` (
  `event_id` int NOT NULL AUTO_INCREMENT,
  `event_name` varchar(45) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `description` varchar(255) NOT NULL,
  `organiser_details` varchar(100) NOT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_details`
--

LOCK TABLES `event_details` WRITE;
/*!40000 ALTER TABLE `event_details` DISABLE KEYS */;
INSERT INTO `event_details` VALUES (9,'NONO','2024-11-21','12:57:00','FKDF,M,','DG;,FG,'),(10,'TRIO FEST','2024-11-14','18:32:00','FEJOKKFDLE','DWKMMSL'),(12,'MAKUBENJALO','2024-12-15','13:00:00','ALL WHITE PARTY','CREATION CREW'),(14,'GLOBAL FEST','2025-05-09','10:26:00','FIFA MAZZA','BIGQOH.ORG');
/*!40000 ALTER TABLE `event_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venue_bookings`
--

DROP TABLE IF EXISTS `venue_bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venue_bookings` (
  `venue_id` int NOT NULL AUTO_INCREMENT,
  `venue_name` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `capacity` int NOT NULL,
  `availability` varchar(10) NOT NULL,
  PRIMARY KEY (`venue_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venue_bookings`
--

LOCK TABLES `venue_bookings` WRITE;
/*!40000 ALTER TABLE `venue_bookings` DISABLE KEYS */;
INSERT INTO `venue_bookings` VALUES (1,'JHB','772 RORO',205,'NO'),(6,'GLGFG','DFLKDL',443,'YES'),(9,'FGG','FGGF',100,'YES');
/*!40000 ALTER TABLE `venue_bookings` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-07 16:54:10
