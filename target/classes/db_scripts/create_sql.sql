create database if not exists online_lib;
use online_lib;

CREATE TABLE `author` (
  `id_author` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `middle_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_author`)
);

CREATE TABLE `city` (
  `id_city` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id_city`)
);

CREATE TABLE `genre` (
  `id_genre` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id_genre`)
);

CREATE TABLE `role` (
  `id_role` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(32) NOT NULL,
  PRIMARY KEY (`id_role`)
);

CREATE TABLE `person` (
  `id_person` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `middle_name` varchar(45) DEFAULT NULL,
  `phone` varchar(30) NOT NULL,
  `birthday` date DEFAULT NULL,
  `address` varchar(120) NOT NULL,
  `id_city` int(11) NOT NULL,
  PRIMARY KEY (`id_person`),
  KEY `city_person_idx` (`id_city`),
  CONSTRAINT `city_person` FOREIGN KEY (`id_city`) REFERENCES `city` (`id_city`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `customer` (
  `id_customer` int(11) NOT NULL AUTO_INCREMENT,
  `register_date` datetime NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `id_person` int(11) NOT NULL,
  `id_role` int(11) NOT NULL,
  PRIMARY KEY (`id_customer`),
  KEY `person_idx` (`id_person`),
  KEY `rool_idx` (`id_role`),
  CONSTRAINT `person` FOREIGN KEY (`id_person`) REFERENCES `person` (`id_person`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `role` FOREIGN KEY (`id_role`) REFERENCES `role` (`id_role`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `book` (
  `id_book` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `year` date NOT NULL,
  `isbn` varchar(45) NOT NULL,
  `description` varchar(500) NOT NULL,
  `id_genre` int(11) NOT NULL,
  `id_author` int(11) NOT NULL,
  PRIMARY KEY (`id_book`),
  KEY `author_idx` (`id_author`),
  KEY `genre_idx` (`id_genre`),
  CONSTRAINT `author` FOREIGN KEY (`id_author`) REFERENCES `author` (`id_author`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `genre` FOREIGN KEY (`id_genre`) REFERENCES `genre` (`id_genre`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `book_info` (
  `id_book_info` int(11) NOT NULL AUTO_INCREMENT,
  `amount` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `id_book` int(11) NOT NULL,
  PRIMARY KEY (`id_book_info`),
  KEY `book_idx` (`id_book`),
  CONSTRAINT `book` FOREIGN KEY (`id_book`) REFERENCES `book` (`id_book`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `transaction` (
  `id_transaction` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_date` timestamp(6) NULL DEFAULT NULL,
  `id_book_info` int(11) NOT NULL,
  `id_customer` int(11) NOT NULL,
  PRIMARY KEY (`id_transaction`),
  KEY `book_take_idx` (`id_book_info`),
  KEY `book_customer_idx` (`id_customer`),
  CONSTRAINT `book_customer` FOREIGN KEY (`id_customer`) REFERENCES `customer` (`id_customer`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `book_take` FOREIGN KEY (`id_book_info`) REFERENCES `book_info` (`id_book_info`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `management` (
  `id_management` int(11) NOT NULL AUTO_INCREMENT,
  `return_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `id_transaction` int(11) NOT NULL,
  PRIMARY KEY (`id_management`),
  KEY `transaction_idx` (`id_transaction`),
  CONSTRAINT `transaction` FOREIGN KEY (`id_transaction`) REFERENCES `transaction` (`id_transaction`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
