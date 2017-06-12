/*
SQLyog Community v12.16 (32 bit)
MySQL - 5.1.44-community : Database - busreservationsystem
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`busreservationsystem` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `busreservationsystem`;

/*Table structure for table `conductor` */

DROP TABLE IF EXISTS `conductor`;

CREATE TABLE `conductor` (
  `cId` int(11) NOT NULL AUTO_INCREMENT,
  `busId` int(11) DEFAULT NULL,
  `routeId` int(11) DEFAULT NULL,
  `firstName` varchar(10) DEFAULT NULL,
  `lastName` varchar(10) DEFAULT NULL,
  `source` varchar(20) NOT NULL,
  `destination` varchar(20) DEFAULT NULL,
  `wallet` int(11) DEFAULT '0',
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(16) DEFAULT NULL,
  `noOftickets` int(11) DEFAULT '0',
  `routeName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`cId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `conductor` */

insert  into `conductor`(`cId`,`busId`,`routeId`,`firstName`,`lastName`,`source`,`destination`,`wallet`,`username`,`password`,`noOftickets`,`routeName`) values 
(1,1,1,'Abc','Def','Katraj','Swargate',240,'conductor@gmail.com','conductor',0,NULL),
(2,2,2,'Pqr','Stu','S.Nagar','Swargate',0,'conductor2@gmail.com','conductor2',0,NULL);

/*Table structure for table `map_location` */

DROP TABLE IF EXISTS `map_location`;

CREATE TABLE `map_location` (
  `locationId` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `cId` int(11) NOT NULL,
  PRIMARY KEY (`locationId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `map_location` */

insert  into `map_location`(`locationId`,`latitude`,`longitude`,`cId`) values 
(1,21.3998,12.4564,1),
(2,4,1,2),
(3,5,2,0),
(4,4,1,2);

/*Table structure for table `passanger` */

DROP TABLE IF EXISTS `passanger`;

CREATE TABLE `passanger` (
  `pId` int(3) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(16) NOT NULL,
  `firstName` varchar(10) DEFAULT NULL,
  `lastName` varchar(10) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `wallet` int(11) DEFAULT '0',
  PRIMARY KEY (`pId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `passanger` */

insert  into `passanger`(`pId`,`username`,`password`,`firstName`,`lastName`,`age`,`wallet`) values 
(1,'varsha_ajane@gmail.com','varsha_ajane','Varsha','Ajane',NULL,1100),
(2,'varsha_ajane1@gmail.com','varsha_ajane','Varsha','Ajane',NULL,50),
(3,'omkar_nene@gmail.com','omkar_nene','Omkar','Nene',NULL,70),
(4,'nikhil_daund@gmail.com','varsha_ajane','Nikhil','xxx',NULL,0),
(5,'nikhil_d6666aund@gmail.com','varsha_ajane','Nikhil','xxx',0,0),
(6,'nikhil_d66aund@gmail.com','varsha_ajane','Nikhil','xxx',0,0),
(7,'nikhil_d347aund@gmail.com','varsha_ajane','Nikhil','xxx',23,0),
(8,'v8798arsha_ajane@gmail.com','varsha_ajane','Varsha','Ajane',25,0);

/*Table structure for table `passanger_tickets` */

DROP TABLE IF EXISTS `passanger_tickets`;

CREATE TABLE `passanger_tickets` (
  `ticketId` int(4) NOT NULL AUTO_INCREMENT,
  `source` varchar(20) DEFAULT NULL,
  `destination` varchar(20) DEFAULT NULL,
  `routeId` int(11) DEFAULT NULL,
  `noOfTickets` int(11) DEFAULT NULL,
  `totalCost` int(11) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ticketId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `passanger_tickets` */

insert  into `passanger_tickets`(`ticketId`,`source`,`destination`,`routeId`,`noOfTickets`,`totalCost`,`username`,`isActive`) values 
(1,'Swargate','Katraj',1,4,40,'varsha_ajane@gmail.com',1),
(2,'Swargate','Katraj',1,5,50,'omkar_nene@gmail.com',1),
(3,'Swargate','Katraj',1,5,60,'nikhil_daund@gmail.com',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
