CREATE TABLE `user_profile` (
	`id` BIGINT(20) NOT NULL,
	`user_id` VARCHAR(20) NOT NULL,
	`first_name` VARCHAR(50) NULL DEFAULT NULL,
	`last_name` VARCHAR(50) NULL DEFAULT NULL,
	`gender` VARCHAR(10) NULL DEFAULT NULL,
	`phone` VARCHAR(20) NULL DEFAULT NULL,
	`address` VARCHAR(200) NULL DEFAULT NULL,
	`highest_education` VARCHAR(50) NULL DEFAULT NULL,
	`referral_code` VARCHAR(20) NULL DEFAULT NULL,
	`preferred_course` VARCHAR(50) NULL DEFAULT NULL,
	`is_deleted` SMALLINT(1) NULL DEFAULT '0',
	`created_ts` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
)