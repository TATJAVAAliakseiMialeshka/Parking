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

-- Data exporting was unselected.
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

-- Data exporting was unselected.
-- Dumping structure for table parking.vehicle
DROP TABLE IF EXISTS `vehicle`;
CREATE TABLE IF NOT EXISTS `vehicle` (
  `v_id` int(11) NOT NULL AUTO_INCREMENT,
  `v_reg_num` varchar(45) DEFAULT NULL,
  `v_type` enum('CAR','MOTOCYCLE') DEFAULT 'CAR',
  PRIMARY KEY (`v_id`),
  UNIQUE KEY `v_reg_num_UNIQUE` (`v_reg_num`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
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
