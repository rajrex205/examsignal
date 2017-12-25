-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.42-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for db_examsignal
CREATE DATABASE IF NOT EXISTS `db_examsignal` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `db_examsignal`;


-- Dumping structure for table db_examsignal.admin_profile
CREATE TABLE IF NOT EXISTS `admin_profile` (
  `id` bigint(20) unsigned NOT NULL,
  `unique_id` varchar(50) NOT NULL DEFAULT '0',
  `description` varchar(100) DEFAULT '0',
  `is_public` varchar(1) NOT NULL DEFAULT '0',
  `course_role` varchar(10) NOT NULL DEFAULT '0',
  `subject_role` varchar(10) NOT NULL DEFAULT '0',
  `is_deleted` smallint(1) NOT NULL DEFAULT '0',
  `create_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table db_examsignal.auth_access
CREATE TABLE IF NOT EXISTS `auth_access` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `password` varchar(200) DEFAULT NULL,
  `auth_mode` varchar(10) NOT NULL,
  `type_of_user` varchar(10) NOT NULL,
  `is_deleted` smallint(1) NOT NULL DEFAULT '0',
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table db_examsignal.course
CREATE TABLE IF NOT EXISTS `course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) NOT NULL DEFAULT '0',
  `course_name` varchar(50) NOT NULL DEFAULT '0',
  `is_deleted` smallint(1) NOT NULL DEFAULT '0',
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table db_examsignal.exam
CREATE TABLE IF NOT EXISTS `exam` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `course_id` bigint(20) NOT NULL DEFAULT '0',
  `exam_name` varchar(50) NOT NULL DEFAULT '0',
  `exam_type` varchar(20) NOT NULL DEFAULT '0',
  `duration` int(11) NOT NULL DEFAULT '0',
  `is_deleted` smallint(1) NOT NULL DEFAULT '0',
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table db_examsignal.facebook_access
CREATE TABLE IF NOT EXISTS `facebook_access` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fb_id` varchar(30) NOT NULL,
  `email` varchar(50) NOT NULL,
  `is_deleted` smallint(6) NOT NULL DEFAULT '0',
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table db_examsignal.higher_education
CREATE TABLE IF NOT EXISTS `higher_education` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `education` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table db_examsignal.offer
CREATE TABLE IF NOT EXISTS `offer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(1024) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table db_examsignal.points
CREATE TABLE IF NOT EXISTS `points` (
  `user_id` bigint(20) NOT NULL,
  `points` int(11) NOT NULL,
  `reason_code` varchar(20) NOT NULL,
  `reason_description` varchar(50) NOT NULL,
  `is_deleted` smallint(6) NOT NULL DEFAULT '0',
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table db_examsignal.preferred_course
CREATE TABLE IF NOT EXISTS `preferred_course` (
  `user_id` bigint(20) NOT NULL,
  `course_id` bigint(20) NOT NULL,
  `is_deleted` smallint(6) NOT NULL DEFAULT '0',
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table db_examsignal.question
CREATE TABLE IF NOT EXISTS `question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exam_id` bigint(20) NOT NULL,
  `subject_id` bigint(20) NOT NULL,
  `question_number` int(11) NOT NULL,
  `question_text` varchar(500) NOT NULL,
  `marks` int(11) NOT NULL,
  `option_1` varchar(200) NOT NULL,
  `option_2` varchar(200) NOT NULL,
  `option_3` varchar(200) NOT NULL,
  `option_4` varchar(200) NOT NULL,
  `answer` varchar(20) NOT NULL,
  `is_deleted` smallint(1) NOT NULL DEFAULT '0',
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `question_for_exam` (`exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table db_examsignal.subject
CREATE TABLE IF NOT EXISTS `subject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `course_id` bigint(20) NOT NULL DEFAULT '0',
  `subject_name` varchar(50) NOT NULL DEFAULT '0',
  `is_deleted` smallint(1) NOT NULL DEFAULT '0',
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table db_examsignal.subject_name
CREATE TABLE IF NOT EXISTS `subject_name` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) NOT NULL,
  `subject_name` varchar(50) NOT NULL DEFAULT '0',
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` smallint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table db_examsignal.subscription_detail
CREATE TABLE IF NOT EXISTS `subscription_detail` (
  `admin_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `status` varchar(20) NOT NULL,
  `requested_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table db_examsignal.super_admin_conf
CREATE TABLE IF NOT EXISTS `super_admin_conf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `password` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table db_examsignal.testimonial
CREATE TABLE IF NOT EXISTS `testimonial` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `testimonial` varchar(500) NOT NULL,
  `user_point` varchar(10) NOT NULL,
  `photoid` varchar(50) NOT NULL,
  `insert_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` smallint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table db_examsignal.user_exam
CREATE TABLE IF NOT EXISTS `user_exam` (
  `score_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exam_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `start_time` datetime NOT NULL,
  `max_marks` INT(11) NOT NULL DEFAULT '0',
  `total_marks` INT(11) NOT NULL DEFAULT '0',
  `is_deleted` smallint(6) NOT NULL DEFAULT '0',
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`score_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table db_examsignal.user_profile
CREATE TABLE IF NOT EXISTS `user_profile` (
  `id` bigint(20) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `highest_education` varchar(50) DEFAULT NULL,
  `referral_code` varchar(20) DEFAULT NULL,
  `is_deleted` smallint(1) DEFAULT '0',
  `created_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table db_examsignal.user_score
CREATE TABLE IF NOT EXISTS `user_score` (
  `score_id` bigint(20) NOT NULL,
  `q_id` bigint(20) NOT NULL,
  `points_scored` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
