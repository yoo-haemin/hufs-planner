# DC schema

# --- !Ups
-- MySQL Script generated by MySQL Workbench
-- 2017년 05월 15일 (월) 오후 01시 17분 39초
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema jamioni
-- -----------------------------------------------------
-- DROP SCHEMA IF EXISTS `jamioni` ;

-- -----------------------------------------------------
-- Schema jamioni
-- -----------------------------------------------------
-- CREATE SCHEMA IF NOT EXISTS `jamioni` DEFAULT CHARACTER SET utf8mb4 ;
-- USE `jamioni` ;

-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `users` ;

CREATE TABLE IF NOT EXISTS `users` (
  `id` BINARY(16) NOT NULL,
  `logininfo` TEXT NULL,
  `username` CHAR(20) NOT NULL,
  `email` VARCHAR(255) NULL,
  `password` VARCHAR(32) NOT NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `userscol` SMALLINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `departments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `departments` ;

CREATE TABLE IF NOT EXISTS `departments` (
  `id` CHAR(10) NOT NULL,
  `name` VARCHAR(45) NULL,
  `campus` TINYINT NULL,
  `affiliation` VARCHAR(10) NULL,
  PRIMARY KEY (`id`))
ENGINE = Aria;


-- -----------------------------------------------------
-- Table `departments_with_time`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `departments_with_time` ;

CREATE TABLE IF NOT EXISTS `departments_with_time` (
  `year` TINYINT NOT NULL,
  `semester` TINYINT NOT NULL,
  `department_id` CHAR(10) NOT NULL,
  PRIMARY KEY (`year`, `semester`),
  INDEX `fk_department_time_department1_idx` (`department_id` ASC))
ENGINE = Aria;


-- -----------------------------------------------------
-- Table `subjects`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subjects` ;

CREATE TABLE IF NOT EXISTS `subjects` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `department_id` CHAR(10) NOT NULL,
  PRIMARY KEY (`id`, `department_id`),
  INDEX `fk_subject_department1_idx` (`department_id` ASC))
ENGINE = Aria;


-- -----------------------------------------------------
-- Table `course_times`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `course_times` ;

CREATE TABLE IF NOT EXISTS `course_times` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `time_mon` INT NULL,
  `time_tue` INT NULL,
  `time_wed` INT NULL,
  `time_thu` INT NULL,
  `time_fri` INT NULL,
  `time_sat` INT NULL,
  `time_sun` INT NULL,
  `room_mon` VARCHAR(20) NULL,
  `room_tue` VARCHAR(45) NULL,
  `room_wed` VARCHAR(45) NULL,
  `room_thu` VARCHAR(45) NULL,
  `room_fri` VARCHAR(45) NULL,
  `room_sat` VARCHAR(45) NULL,
  `room_sun` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = Aria;


-- -----------------------------------------------------
-- Table `courses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courses` ;

CREATE TABLE IF NOT EXISTS `courses` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(45) NOT NULL,
  `department_id` CHAR(10) NOT NULL,
  `course_time_id` INT NOT NULL,
  `department_time_year` TINYINT NOT NULL,
  `department_time_semester` TINYINT NOT NULL,
  `subject_department_id` CHAR(10) NOT NULL,
  `subject_id` INT NOT NULL,
  `area` VARCHAR(45) NULL,
  `type` VARCHAR(20) NULL,
  `name_1` VARCHAR(45) NULL,
  `name_2` VARCHAR(45) NULL,
  `required` TINYINT NULL,
  `online` TINYINT NULL,
  `foreign_language` TINYINT NULL,
  `team_teaching` TINYINT NULL,
  `professor_name_1` VARCHAR(45) NULL,
  `professor_name_2` VARCHAR(45) NULL,
  `credit_hours` TINYINT NULL,
  `course_hours` TINYINT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_course_department_time1_idx` (`department_time_year` ASC, `department_time_semester` ASC),
  INDEX `fk_course_subject1_idx` (`subject_id` ASC, `subject_department_id` ASC),
  INDEX `fk_course_course_time2_idx` (`course_time_id` ASC))
ENGINE = Aria;


-- -----------------------------------------------------
-- Table `major_types`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `major_types` ;

CREATE TABLE IF NOT EXISTS `major_types` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `majors`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `majors` ;

CREATE TABLE IF NOT EXISTS `majors` (
  `id` CHAR(8) NOT NULL,
  `major_type_id` INT NOT NULL,
  `year` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`, `major_type_id`, `year`),
  INDEX `fk_major_major_type1_idx` (`major_type_id` ASC))
ENGINE = Aria;


-- -----------------------------------------------------
-- Table `users_has_majors`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `users_has_majors` ;

CREATE TABLE IF NOT EXISTS `users_has_majors` (
  `user_id` INT NOT NULL,
  `major_id` CHAR(8) NOT NULL,
  `major_major_type_id` INT NOT NULL,
  `major_year` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`, `major_id`, `major_major_type_id`, `major_year`),
  INDEX `fk_user_has_major_major1_idx` (`major_id` ASC, `major_major_type_id` ASC, `major_year` ASC),
  INDEX `fk_user_has_major_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_has_major_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_major_major1`
    FOREIGN KEY (`major_id` , `major_major_type_id` , `major_year`)
    REFERENCES `majors` (`id` , `major_type_id` , `year`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `majors_has_subjects`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `majors_has_subjects` ;

CREATE TABLE IF NOT EXISTS `majors_has_subjects` (
  `major_id` CHAR(8) NOT NULL,
  `major_major_type_id` INT NOT NULL,
  `major_year` VARCHAR(45) NOT NULL,
  `subject_id` INT NOT NULL,
  `required` TINYINT NULL,
  PRIMARY KEY (`major_id`, `major_major_type_id`, `major_year`, `subject_id`),
  INDEX `fk_major_has_subject_subject1_idx` (`subject_id` ASC),
  INDEX `fk_major_has_subject_major1_idx` (`major_id` ASC, `major_major_type_id` ASC, `major_year` ASC),
  CONSTRAINT `fk_major_has_subject_major1`
    FOREIGN KEY (`major_id` , `major_major_type_id` , `major_year`)
    REFERENCES `majors` (`id` , `major_type_id` , `year`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_major_has_subject_subject1`
    FOREIGN KEY (`subject_id`)
    REFERENCES `subjects` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `users_has_courses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `users_has_courses` ;

CREATE TABLE IF NOT EXISTS `users_has_courses` (
  `user_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  `retake` TINYINT NULL,
  `score` DECIMAL(1,1) NULL,
  PRIMARY KEY (`user_id`, `course_id`),
  INDEX `fk_user_has_course_course1_idx` (`course_id` ASC),
  INDEX `fk_user_has_course_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_has_course_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_course_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `courses` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `users_has_courses_future`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `users_has_courses_future` ;

CREATE TABLE IF NOT EXISTS `users_has_courses_future` (
  `user_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  `retake` TINYINT NULL,
  `score` DECIMAL(1,1) NULL,
  PRIMARY KEY (`user_id`, `course_id`),
  INDEX `fk_user_has_course_course1_idx` (`course_id` ASC),
  INDEX `fk_user_has_course_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_has_course_user10`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_course_course10`
    FOREIGN KEY (`course_id`)
    REFERENCES `courses` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `auth_tokens`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `auth_tokens` ;

CREATE TABLE IF NOT EXISTS `auth_tokens` (
  `authtoken_id` BINARY(16) NOT NULL,
  `user_id` BINARY(16) NOT NULL,
  `expiry` DATETIME NULL,
  PRIMARY KEY (`authtoken_id`),
  INDEX `fk_auth_tokens_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_auth_tokens_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = XtraDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

# --- !Downs
-- -----------------------------------------------------
-- Schema jamioni
-- -----------------------------------------------------
-- DROP SCHEMA IF EXISTS `jamioni` ;

DROP TABLE IF EXISTS `user` ;
DROP TABLE IF EXISTS `department` ;
DROP TABLE IF EXISTS `department_time` ;
DROP TABLE IF EXISTS `subject` ;
DROP TABLE IF EXISTS `course_time` ;
DROP TABLE IF EXISTS `course` ;
DROP TABLE IF EXISTS `major_type` ;
DROP TABLE IF EXISTS `major` ;
DROP TABLE IF EXISTS `user_has_major` ;
DROP TABLE IF EXISTS `major_has_subject` ;
DROP TABLE IF EXISTS `user_has_course` ;
DROP TABLE IF EXISTS `user_has_course_future` ;
