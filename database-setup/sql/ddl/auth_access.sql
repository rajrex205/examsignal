CREATE TABLE `auth_access` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	`email` VARCHAR(50) NOT NULL,
	`password` VARCHAR(200) NULL DEFAULT NULL,
	`auth_mode` VARCHAR(10) NOT NULL,
	`is_deleted` SMALLINT(1) NOT NULL DEFAULT '0',
	`created_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
)