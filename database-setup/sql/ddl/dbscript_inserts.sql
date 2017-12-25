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
-- Dumping data for table db_examsignal.offer: ~1 rows (approximately)
/*!40000 ALTER TABLE `offer` DISABLE KEYS */;
INSERT INTO `offer` (`id`, `description`) VALUES
	(1, 'Opening Offer !!');
/*!40000 ALTER TABLE `offer` ENABLE KEYS */;

-- Dumping data for table db_examsignal.super_admin_conf: ~1 rows (approximately)
/*!40000 ALTER TABLE `super_admin_conf` DISABLE KEYS */;
INSERT INTO `super_admin_conf` (`id`, `email`, `password`) VALUES
	(1, 'alinagilbart28@gmail.com', '3a23051b485fda8ae24adafd9e124d5791ff2125c32319c9e958abedf2d7e97ac3f8e989c5eb805af01a0d52866bd6f164fa5c31368a25c27d50845855081793');
/*!40000 ALTER TABLE `super_admin_conf` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
