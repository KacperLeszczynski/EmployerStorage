CREATE DATABASE  IF NOT EXISTS `employee_dir`;
USE `employee_dir`;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
DROP TABLE IF EXISTS `employer`;

CREATE TABLE `employer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL UNIQUE,
  `password` varchar(255) NOT NULL,
  `organization_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `employer_id` int,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`employer_id`) REFERENCES `employer`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Data for table `employee`
--

INSERT INTO `employer` VALUES 
	(1,'ArturTuczynski', '$2a$10$fQK2zLRhVLUTNfw2049vz.QIdut3zFWeeu030cNWjfbOijCYB47o2', 'Google'),
	(2,'FilipMajewski', '$2a$10$fQK2zLRhVLUTNfw2049vz.QIdut3zFWeeu030cNWjfbOijCYB47o2', 'Netflix');


INSERT INTO `employee` VALUES 
	(1,'Leslie','Andrews','leslie@luv2code.com', 41, 1),
	(2,'Emma','Baumgarten','emma@luv2code.com', 32, 1),
	(3,'Avani','Gupta','avani@luv2code.com', 35, 1),
	(4,'Yuri','Petrov','yuri@luv2code.com', 28, 2),
	(5,'Juan','Vega','juan@luv2code.com', 50, 2);

