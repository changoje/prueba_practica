CREATE DATABASE  IF NOT EXISTS `db_bank_operations`
USE `db_bank_operations`;


DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `person_id` bigint NOT NULL AUTO_INCREMENT,
  `documentNumber` varchar(10) NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `age` int NOT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `documentNumber_UNIQUE` (`documentNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `person_id` bigint NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `register_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `customer_unique` (`user_name`),
  UNIQUE KEY `person_unique` (`person_id`),
  KEY `FKsoivl0m3o2bj2axj0mp2oe5tb` (`person_id`),
  CONSTRAINT `FKsoivl0m3o2bj2axj0mp2oe5tb` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `account_id` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(255) NOT NULL,
  `account_number` varchar(10) NOT NULL,
  `available_balance` double NOT NULL,
  `initial_balance` double NOT NULL,
  `account_type` varchar(255) NOT NULL,
  `customer_id` bigint NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `account_number_UNIQUE` (`account_number`),
  KEY `FK2f4mvc7ap24og8e17nantwu6x` (`customer_id`),
  CONSTRAINT `FK2f4mvc7ap24og8e17nantwu6x` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `account_transaction`;
CREATE TABLE `account_transaction` (
  `account_transaction_id` bigint NOT NULL AUTO_INCREMENT,
  `transaction_date` datetime NOT NULL,
  `transaction` varchar(50) NOT NULL,
  `transaction_type` varchar(255) NOT NULL,
  `amount` double NOT NULL,
  `balance` double NOT NULL,
  `account_id` bigint NOT NULL,
  PRIMARY KEY (`account_transaction_id`),
  KEY `FKnoaow552o0ie6uep4h4awybe7` (`account_id`),
  CONSTRAINT `FKnoaow552o0ie6uep4h4awybe7` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--Usuario que se debe crear por defecto -> pass desencriptada   "password": "jkl*poryt6"
INSERT INTO db_bank_operations.person
(person_id, documentNumber, name, address, age, gender, phone)
VALUES(1, '3473698276', 'Carlos Gael Torres Kapo', 'Mapasingue este', 33, 'MALE', '0996397688');

INSERT INTO db_bank_operations.customer
(customer_id, password, status, person_id, user_name, register_date)
VALUES(1, '$2a$10$NJu.3sClHdsWsOdr0NUGduspjhxOGjBZXIJaM1ovRjFyFO6qSuutW', 'ENABLED', 1, 'jchango42765', '2025-03-13 22:19:17');

INSERT INTO db_bank_operations.account
(account_id, status, account_number, available_balance, initial_balance, account_type, customer_id)
VALUES(1, 'ENABLED', '2268394268', 500.0, 500.0, 'AHORRO', 1);