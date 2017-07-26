-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.16-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for parking
DROP DATABASE IF EXISTS `parking`;
CREATE DATABASE IF NOT EXISTS `parking` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `parking`;

-- Dumping structure for table parking.slot
DROP TABLE IF EXISTS `slot`;
CREATE TABLE IF NOT EXISTS `slot` (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `s_size` enum('REGULAR','LITTLE') DEFAULT 'REGULAR',
  `s_covered` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`s_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- Dumping data for table parking.slot: ~41 rows (approximately)
DELETE FROM `slot`;
/*!40000 ALTER TABLE `slot` DISABLE KEYS */;
INSERT INTO `slot` (`s_id`, `s_size`, `s_covered`) VALUES
	(1, 'REGULAR', 1),
	(2, 'REGULAR', 1),
	(3, 'REGULAR', 1),
	(4, 'REGULAR', 1),
	(5, 'REGULAR', 1),
	(6, 'REGULAR', 1),
	(7, 'REGULAR', 1),
	(8, 'LITTLE', 1),
	(9, 'LITTLE', 1),
	(10, 'LITTLE', 0),
	(11, 'REGULAR', 1),
	(12, 'REGULAR', 0),
	(13, 'REGULAR', 1),
	(14, 'REGULAR', 0),
	(15, 'REGULAR', 0),
	(16, 'REGULAR', 0),
	(17, 'REGULAR', 0),
	(18, 'REGULAR', 0),
	(19, 'REGULAR', 0),
	(20, 'REGULAR', 0),
	(21, 'LITTLE', 1),
	(22, 'LITTLE', 1),
	(23, 'LITTLE', 1),
	(24, 'LITTLE', 0),
	(25, 'LITTLE', 0),
	(26, 'REGULAR', 0),
	(27, 'LITTLE', 0),
	(28, 'REGULAR', 0),
	(29, 'REGULAR', 0),
	(30, 'REGULAR', 0),
	(31, 'REGULAR', 0),
	(32, 'REGULAR', 0),
	(33, 'REGULAR', 0),
	(34, 'REGULAR', 1),
	(35, 'REGULAR', 1),
	(36, 'REGULAR', 0),
	(37, 'REGULAR', 0),
	(38, 'REGULAR', 1),
	(39, 'REGULAR', 1),
	(40, 'LITTLE', 1),
	(41, 'LITTLE', 1);
/*!40000 ALTER TABLE `slot` ENABLE KEYS */;

-- Dumping structure for table parking.slot_has_vehicle
DROP TABLE IF EXISTS `slot_has_vehicle`;
CREATE TABLE IF NOT EXISTS `slot_has_vehicle` (
  `sl_id` int(11) NOT NULL AUTO_INCREMENT,
  `s_id` int(11) NOT NULL,
  `v_id` int(11) NOT NULL,
  `sl_start` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `sl_end` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`sl_id`,`s_id`,`v_id`),
  UNIQUE KEY `fk_slot_has_vehicle_slot_idx_UNIQUE` (`s_id`),
  UNIQUE KEY `fk_slot_has_vehicle_vehicle1_idx_UNIQUE` (`v_id`),
  KEY `fk_slot_has_vehicle_vehicle1_idx` (`v_id`),
  KEY `fk_slot_has_vehicle_slot_idx` (`s_id`),
  CONSTRAINT `fk_slot_has_vehicle_slot` FOREIGN KEY (`s_id`) REFERENCES `slot` (`s_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_slot_has_vehicle_vehicle1` FOREIGN KEY (`v_id`) REFERENCES `vehicle` (`v_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- Dumping data for table parking.slot_has_vehicle: ~17 rows (approximately)
DELETE FROM `slot_has_vehicle`;
/*!40000 ALTER TABLE `slot_has_vehicle` DISABLE KEYS */;
INSERT INTO `slot_has_vehicle` (`sl_id`, `s_id`, `v_id`, `sl_start`, `sl_end`) VALUES
	(1, 1, 4, '2017-07-21 18:51:00', '2017-07-24 18:33:55'),
	(2, 2, 5, '2017-07-13 15:51:05', '2017-07-24 17:31:22'),
	(3, 3, 6, '2017-07-17 18:51:19', '2017-07-24 18:36:05'),
	(4, 4, 7, '2017-07-21 08:53:35', '2017-07-24 18:37:06'),
	(5, 5, 8, '2017-07-21 18:23:36', '2017-07-24 18:38:06'),
	(8, 8, 12, '2017-07-17 00:53:37', '2017-07-24 19:34:07'),
	(9, 9, 14, '2017-07-12 18:35:38', '2017-07-24 19:53:14'),
	(11, 6, 13, '2017-07-22 21:11:07', '2017-07-24 19:58:18'),
	(32, 11, 74, '2017-07-25 13:37:58', NULL),
	(34, 13, 76, '2017-07-25 13:38:18', NULL),
	(39, 21, 85, '2017-07-25 13:39:17', NULL),
	(40, 22, 86, '2017-07-25 13:39:23', NULL),
	(41, 23, 87, '2017-07-25 13:39:28', NULL),
	(52, 7, 79, '2017-07-25 17:47:35', NULL);
/*!40000 ALTER TABLE `slot_has_vehicle` ENABLE KEYS */;

-- Dumping structure for table parking.vehicle
DROP TABLE IF EXISTS `vehicle`;
CREATE TABLE IF NOT EXISTS `vehicle` (
  `v_id` int(11) NOT NULL AUTO_INCREMENT,
  `v_reg_num` varchar(45) DEFAULT NULL,
  `v_type` enum('CAR','MOTOCYCLE') DEFAULT 'CAR',
  PRIMARY KEY (`v_id`),
  UNIQUE KEY `v_reg_num_UNIQUE` (`v_reg_num`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;

-- Dumping data for table parking.vehicle: ~29 rows (approximately)
DELETE FROM `vehicle`;
/*!40000 ALTER TABLE `vehicle` DISABLE KEYS */;
INSERT INTO `vehicle` (`v_id`, `v_reg_num`, `v_type`) VALUES
	(1, 'B912A0', 'CAR'),
	(2, '8064NC', 'CAR'),
	(3, '27821PF', 'CAR'),
	(4, '2GAT123', 'CAR'),
	(5, '5X21206', 'CAR'),
	(6, 'W2C0R', 'CAR'),
	(7, '144HUY', 'CAR'),
	(8, 'J30545', 'CAR'),
	(9, '1E25693', 'CAR'),
	(10, 'FH6079', 'CAR'),
	(11, '5633W', 'MOTOCYCLE'),
	(12, '822BR', 'MOTOCYCLE'),
	(13, 'CP94447', 'MOTOCYCLE'),
	(14, '1SAM123', 'MOTOCYCLE'),
	(15, 'BFF146', 'MOTOCYCLE'),
	(74, 'TEST1CAR', 'CAR'),
	(75, 'TEST2CAR', 'CAR'),
	(76, 'TESTCAR3', 'CAR'),
	(77, 'TESTCAR4', 'CAR'),
	(79, 'TESTCAR5', 'CAR'),
	(80, 'TESTCAR6', 'CAR'),
	(81, 'TESTCAR7', 'CAR'),
	(82, 'TESTCAR8', 'CAR'),
	(83, 'TESTCAR9', 'CAR'),
	(85, 'TESTMOTO1', 'MOTOCYCLE'),
	(86, 'TESTMOTO2', 'MOTOCYCLE'),
	(87, 'TESTMOTO3', 'MOTOCYCLE'),
	(88, 'TESTMOTO4', 'MOTOCYCLE'),
	(89, 'TESTMOTO5', 'MOTOCYCLE');
/*!40000 ALTER TABLE `vehicle` ENABLE KEYS */;

-- Dumping structure for trigger parking.update_slot_status_on_delete_reservation
DROP TRIGGER IF EXISTS `update_slot_status_on_delete_reservation`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `update_slot_status_on_delete_reservation` AFTER DELETE ON `slot_has_vehicle` FOR EACH ROW begin
       DECLARE id_exists Boolean;

       SELECT 1
       INTO @id_exists
       FROM parking.slot
       WHERE slot.s_id = OLD.s_id;

       IF @id_exists = 1
       THEN
           UPDATE parking.slot
           SET slot.s_covered = 0
           WHERE slot.s_id = OLD.s_id;
        END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger parking.update_slot_status_on_reservation
DROP TRIGGER IF EXISTS `update_slot_status_on_reservation`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `update_slot_status_on_reservation` AFTER INSERT ON `slot_has_vehicle` FOR EACH ROW begin
       DECLARE id_exists Boolean;

       SELECT 1
       INTO @id_exists
       FROM parking.slot
       WHERE slot.s_id = NEW.s_id;

       IF @id_exists = 1
       THEN
           UPDATE parking.slot
           SET slot.s_covered = 1
           WHERE slot.s_id = NEW.s_id;
        END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
