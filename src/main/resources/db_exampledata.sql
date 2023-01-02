-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema repair-agency
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `repair-agency` ;

-- -----------------------------------------------------
-- Schema repair-agency
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `repair-agency` DEFAULT CHARACTER SET utf8 ;
USE `repair-agency` ;

-- -----------------------------------------------------
-- Table `repair-agency`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repair-agency`.`role` (
                                                      `role_id` INT NOT NULL AUTO_INCREMENT,
                                                      `role_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`role_id`),
    UNIQUE INDEX `role_id_UNIQUE` (`role_id` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `repair-agency`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repair-agency`.`users` (
                                                       `u_id` INT NOT NULL AUTO_INCREMENT,
                                                       `notification` VARCHAR(45) NULL,
    `phone_number` VARCHAR(45) NULL,
    `account` INT NULL,
    `status` VARCHAR(45) NULL,
    `password` VARCHAR(200) NULL,
    `first_name` VARCHAR(45) NULL,
    `last_name` VARCHAR(45) NULL,
    `email` VARCHAR(45) NULL,
    `role_id` INT NOT NULL,
    PRIMARY KEY (`u_id`, `role_id`),
    UNIQUE INDEX `u_id_UNIQUE` (`u_id` ASC) VISIBLE,
    UNIQUE INDEX `phone_number_UNIQUE` (`phone_number` ASC) VISIBLE,
    INDEX `fk_user_role1_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `fk_user_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `repair-agency`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `repair-agency`.`request`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repair-agency`.`request` (
                                                         `r_id` INT NOT NULL,
                                                         `descriptions` VARCHAR(10000) NULL,
    `date` DATETIME NULL,
    `completion_status` VARCHAR(45) NULL,
    `payment_status` VARCHAR(45) NULL,
    `total_cost` INT NULL,
    PRIMARY KEY (`r_id`),
    UNIQUE INDEX `r_id_UNIQUE` (`r_id` ASC) VISIBLE,
    INDEX `idx_request_date` (`date` ASC) INVISIBLE,
    INDEX `idx_request_completion_status` (`completion_status` ASC) INVISIBLE,
    INDEX `idx_request_paymentstatus` (`payment_status` ASC) INVISIBLE,
    INDEX `idx_request_total_cost` (`total_cost` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `repair-agency`.`request_has_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repair-agency`.`request_has_user` (
                                                                  `request_r_id` INT NOT NULL,
                                                                  `user_u_id` INT NOT NULL,
                                                                  PRIMARY KEY (`request_r_id`, `user_u_id`),
    INDEX `fk_request_has_user_user1_idx` (`user_u_id` ASC) VISIBLE,
    INDEX `fk_request_has_user_request_idx` (`request_r_id` ASC) VISIBLE,
    CONSTRAINT `fk_request_has_user_request`
    FOREIGN KEY (`request_r_id`)
    REFERENCES `repair-agency`.`request` (`r_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_request_has_user_user1`
    FOREIGN KEY (`user_u_id`)
    REFERENCES `repair-agency`.`users` (`u_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `repair-agency`.`feedback`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repair-agency`.`feedback` (
                                                          `feedback_id` INT NOT NULL AUTO_INCREMENT,
                                                          `repairer_id` INT NULL,
                                                          `date_time` DATETIME NULL,
                                                          `feedback_text` VARCHAR(10000) NULL,
    `rating` INT NULL,
    `request_id` INT NOT NULL,
    PRIMARY KEY (`feedback_id`, `request_id`),
    UNIQUE INDEX `idfeedback_id_UNIQUE` (`feedback_id` ASC) VISIBLE,
    INDEX `fk_feedback_request1_idx` (`request_id` ASC) VISIBLE,
    CONSTRAINT `fk_feedback_request1`
    FOREIGN KEY (`request_id`)
    REFERENCES `repair-agency`.`request` (`r_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;

insert into role
(role_id, role_name)
values (1, 'manager'),
       (2, 'craftsman'),
       (3, 'registred user'),
       (4, 'unregistred user');

insert into users
(u_id, status, role_id, phone_number, password, notification, last_name, first_name, email, account)
values (1, 'registred', 1, '+380995246875', 'password1', 'notification1', 'Петренко', 'Александр', 'h1@i.ua', 5000000),
       (2, 'registred', 2, '+380975246875', 'password2', 'notification2', 'Костенко', 'Егор', 'h2@i.ua', 2000000),
       (3, 'registred', 3, '+380935246875', 'password3', 'notification3', 'Иванов', 'Сидор', 'h3@ukr.net', 100000),
       (4, 'unregistred user', 4, null, null, null, null,null, null, 0);

insert into request
(r_id, descriptions, date, completion_status, payment_status, total_cost)
values (1, 'описание 1', '2022-12-01', 'статус выполнения 1', 'статус оплаты 1', 500000),
       (2, 'описание 2', '2022-12-02','статус выполнения 2','статус оплаты 2', 400000),
       (3, 'описание 3', '2022-12-03', 'статус выполнения 3', 'статус оплаты 3', 300000),
       (4, 'описание 4','2022-12-04', 'статус выполнения 4', 'статус оплаты 4', 700000);

insert into feedback
(feedback_id, repairer_id, date_time, feedback_text, rating, request_id)
values (1, 2, '2022-12-06', 'текст отзыва 1', 10, 1),
       (2, 2, '2022-12-07', 'текст отзыва 2', 10, 2),
       (3, 2, '2022-12-08', 'текст отзыва 3', 10, 3),
       (4, 2, '2022-12-09', 'текст отзыва 4', 10, 4)

