-- MySQL dump 10.13 Distrib 5.7.26, for osx10.10 (x86_64)
-- Host: 127.0.0.1 Database: catering
-- ------------------------------------------------------
-- Server version 5.7.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

-- Create the database catering
CREATE DATABASE IF NOT EXISTS catering CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE catering;

-- Table structure for table `Events`
DROP TABLE IF EXISTS `Events`;
CREATE TABLE `Events`
(
    `id`                    int(11) NOT NULL AUTO_INCREMENT,
    `name`                  varchar(128) DEFAULT NULL,
    `date_start`            date         DEFAULT NULL,
    `date_end`              date         DEFAULT NULL,
    `expected_participants` int(11)      DEFAULT NULL,
    `organizer_id`          int(11) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Table structure for table `Users`
DROP TABLE IF EXISTS `Users`;
CREATE TABLE `Users`
(
    `id`       int(11)      NOT NULL AUTO_INCREMENT,
    `username` varchar(128) NOT NULL DEFAULT '',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Table structure for table `UserRoles`
DROP TABLE IF EXISTS `UserRoles`;
CREATE TABLE `UserRoles`
(
    `user_id` int(11) NOT NULL,
    `role_id` char(1) NOT NULL DEFAULT 's',
    FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Table structure for table `Roles`
DROP TABLE IF EXISTS `Roles`;
CREATE TABLE `Roles`
(
    `id`   char(1)      NOT NULL,
    `role` varchar(128) NOT NULL DEFAULT 'servizio',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Table structure for table `Preparations`
DROP TABLE IF EXISTS `Preparations`;
CREATE TABLE `Preparations`
(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Table structure for table `Recipes`
DROP TABLE IF EXISTS `Recipes`;
CREATE TABLE `Recipes`
(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Table structure for table `RecipePreparations`
DROP TABLE IF EXISTS `RecipePreparations`;
CREATE TABLE `RecipePreparations`
(
    `recipe_id`      int(11) NOT NULL,
    `preparation_id` int(11) NOT NULL,
    PRIMARY KEY (`recipe_id`, `preparation_id`),
    FOREIGN KEY (`recipe_id`) REFERENCES `Recipes` (`id`),
    FOREIGN KEY (`preparation_id`) REFERENCES `Preparations` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Table structure for table `CookingProcedures`
DROP TABLE IF EXISTS `CookingProcedures`;
CREATE TABLE `CookingProcedures`
(
    `id`                        int(11) NOT NULL AUTO_INCREMENT,
    `name`                      tinytext,
    `type`                      ENUM ('preparation', 'recipe'),
    `fk_referenced_recipe`      int(11) DEFAULT NULL,
    `fk_referenced_preparation` int(11) DEFAULT NULL,
    FOREIGN KEY (`fk_referenced_recipe`) REFERENCES `Recipes` (`id`),
    FOREIGN KEY (`fk_referenced_preparation`) REFERENCES `Preparations` (`id`),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Table structure for table `Menus`
DROP TABLE IF EXISTS `Menus`;
CREATE TABLE `Menus`
(
    `id`        int(11) NOT NULL AUTO_INCREMENT,
    `title`     tinytext,
    `owner_id`  int(11)    DEFAULT NULL,
    `published` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Table structure for table `MenuSections`
DROP TABLE IF EXISTS `MenuSections`;
CREATE TABLE `MenuSections`
(
    `id`       int(11) NOT NULL AUTO_INCREMENT,
    `menu_id`  int(11) NOT NULL,
    `name`     tinytext,
    `position` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`menu_id`) REFERENCES `Menus` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Table structure for table `MenuItems`
DROP TABLE IF EXISTS `MenuItems`;
CREATE TABLE `MenuItems`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `menu_id`     int(11) NOT NULL,
    `section_id`  int(11) DEFAULT NULL,
    `description` tinytext,
    `recipe_id`   int(11) NOT NULL,
    `position`    int(11) DEFAULT NULL,
    FOREIGN KEY (`menu_id`) REFERENCES `Menus` (`id`),
    FOREIGN KEY (`section_id`) REFERENCES `MenuSections` (`id`),
    FOREIGN KEY (`recipe_id`) REFERENCES `Recipes` (`id`),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Table structure for table `MenuFeatures`
DROP TABLE IF EXISTS `MenuFeatures`;
CREATE TABLE `MenuFeatures`
(
    `menu_id` int(11)      NOT NULL,
    `name`    varchar(128) NOT NULL DEFAULT '',
    `value`   tinyint(1)            DEFAULT '0',
    PRIMARY KEY (`menu_id`, `name`),
    FOREIGN KEY (`menu_id`) REFERENCES `Menus` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Table structure for table `Services`
DROP TABLE IF EXISTS `Services`;
CREATE TABLE `Services`
(
    `id`                    int(11) NOT NULL AUTO_INCREMENT,
    `event_id`              int(11) NOT NULL,
    `name`                  varchar(128) DEFAULT NULL,
    `used_menu_id`          int(11)      DEFAULT NULL,
    `chef_id`               int(11)      DEFAULT NULL,
    `summary_sheet_id`     int(11)       DEFAULT NULL,
    `service_date`          date         DEFAULT NULL,
    `time_start`            time         DEFAULT NULL,
    `time_end`              time         DEFAULT NULL,
    `expected_participants` int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`event_id`) REFERENCES `Events` (`id`),
    FOREIGN KEY (`used_menu_id`) REFERENCES `Menus` (`id`),
    FOREIGN KEY (`chef_id`) REFERENCES `Users` (`id`),
    FOREIGN KEY (`summary_sheet_id`) references SummarySheets(`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Table structure for Tasks
DROP TABLE IF EXISTS `Tasks`;
CREATE TABLE `Tasks`
(
    `id`                   int(11) NOT NULL AUTO_INCREMENT,
    `cooking_procedure_id` int(11) NOT NULL,
    `cook_id`              int(11)      DEFAULT NULL,
    `shift_id`             int(11) NOT NULL,
    `initial_task`         int(11)      DEFAULT NULL,
    `time_to_complete`     varchar(20)  DEFAULT NULL,
    `completed`            boolean      DEFAULT false,
    `amount`               varchar(255) DEFAULT NULL,
    `doses`                varchar(255) DEFAULT NULL,
    `to_prepare`           boolean      DEFAULT true,
    FOREIGN KEY (`cooking_procedure_id`) references CookingProcedures (`id`),
    FOREIGN KEY (`cook_id`) references Users (`id`),
    -- FOREIGN KEY (`shift_id`) references CookingShift(`id`),
    FOREIGN KEY (`initial_task`) references Tasks (`id`),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Table structure for SummarySheets
DROP TABLE IF EXISTS `SummarySheets`;
CREATE TABLE `SummarySheets`
(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Table structure for ListedTasks (in SummarySheet)
DROP TABLE IF EXISTS `ListedTasks`;
CREATE TABLE `ListedTasks`
(
    `summary_sheet_id` int(11) NOT NULL,
    `task_id`          int(11) NOT NULL,
    FOREIGN KEY (`summary_sheet_id`) REFERENCES SummarySheets (`id`),
    FOREIGN KEY (`task_id`) REFERENCES Tasks (`id`),
    PRIMARY KEY (`summary_sheet_id`, `task_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Table structure for ListedProcedures (in SummarySheet)
DROP TABLE IF EXISTS `ListedProcedures`;
CREATE TABLE `ListedProcedures`
(
    `summary_sheet_id` int(11) NOT NULL,
    `procedure_id`     int(11) NOT NULL,
    `position`         int(11) NOT NULL,
    FOREIGN KEY (`summary_sheet_id`) REFERENCES SummarySheets (`id`),
    FOREIGN KEY (`procedure_id`) REFERENCES CookingProcedures (`id`),
    PRIMARY KEY (`summary_sheet_id`, `procedure_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

