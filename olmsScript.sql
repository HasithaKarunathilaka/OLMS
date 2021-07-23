-- MySQL Script generated by MySQL Workbench
-- Fri Jul 23 23:18:16 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema olms
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema olms
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `olms` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `olms` ;

-- -----------------------------------------------------
-- Table `olms`.`staff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `olms`.`staff` (
  `staffID` VARCHAR(45) NOT NULL,
  `fName` VARCHAR(45) NULL DEFAULT NULL,
  `lName` VARCHAR(45) NULL DEFAULT NULL,
  `gender` VARCHAR(45) NULL DEFAULT NULL,
  `dob` DATE NULL DEFAULT NULL,
  `telephone` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `addLine1` VARCHAR(45) NULL DEFAULT NULL,
  `addLine2` VARCHAR(45) NULL DEFAULT NULL,
  `addLine3` VARCHAR(45) NULL DEFAULT NULL,
  `state` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`staffID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `olms`.`approval`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `olms`.`approval` (
  `itemID` VARCHAR(45) NULL DEFAULT NULL,
  `approvedBy` VARCHAR(45) NULL DEFAULT NULL,
  `approvedDate` DATE NULL DEFAULT NULL,
  INDEX `approvedBy_idx` (`approvedBy` ASC) VISIBLE,
  CONSTRAINT `approvedBy`
    FOREIGN KEY (`approvedBy`)
    REFERENCES `olms`.`staff` (`staffID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `olms`.`book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `olms`.`book` (
  `bookID` VARCHAR(45) NOT NULL,
  `isbn` VARCHAR(45) NULL DEFAULT NULL,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  `author` VARCHAR(45) NULL DEFAULT NULL,
  `category` VARCHAR(45) NULL DEFAULT NULL,
  `addedBy` VARCHAR(45) NULL DEFAULT NULL,
  `addedDate` DATE NULL DEFAULT NULL,
  `availability` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`bookID`),
  INDEX `addedBy_idx` (`addedBy` ASC) VISIBLE,
  CONSTRAINT `addedBy`
    FOREIGN KEY (`addedBy`)
    REFERENCES `olms`.`staff` (`staffID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `olms`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `olms`.`member` (
  `memberID` VARCHAR(45) NOT NULL,
  `fName` VARCHAR(45) NULL DEFAULT NULL,
  `lName` VARCHAR(45) NULL DEFAULT NULL,
  `gender` VARCHAR(45) NULL DEFAULT NULL,
  `dob` DATE NULL DEFAULT NULL,
  `telephone` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `addLine1` VARCHAR(45) NULL DEFAULT NULL,
  `addline2` VARCHAR(45) NULL DEFAULT NULL,
  `addLine3` VARCHAR(45) NULL DEFAULT NULL,
  `membershipDate` DATE NULL DEFAULT NULL,
  `expireDate` DATE NULL DEFAULT NULL,
  `state` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`memberID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `olms`.`booking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `olms`.`booking` (
  `bookIDBooking` VARCHAR(45) NULL DEFAULT NULL,
  `memberIDBooking` VARCHAR(45) NULL DEFAULT NULL,
  `bookingDate` DATE NULL DEFAULT NULL,
  `bokingTime` VARCHAR(45) NULL DEFAULT NULL,
  `state` VARCHAR(45) NULL DEFAULT NULL,
  INDEX `bookID_idx` (`bookIDBooking` ASC) VISIBLE,
  INDEX `memberID_idx` (`memberIDBooking` ASC) VISIBLE,
  CONSTRAINT `bookIDBooking`
    FOREIGN KEY (`bookIDBooking`)
    REFERENCES `olms`.`book` (`bookID`),
  CONSTRAINT `memberIDBooking`
    FOREIGN KEY (`memberIDBooking`)
    REFERENCES `olms`.`member` (`memberID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `olms`.`ebook`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `olms`.`ebook` (
  `bookID` VARCHAR(45) NOT NULL,
  `isbn` VARCHAR(45) NULL DEFAULT NULL,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  `author` VARCHAR(45) NULL DEFAULT NULL,
  `category` VARCHAR(45) NULL DEFAULT NULL,
  `pages` VARCHAR(45) NULL DEFAULT NULL,
  `availability` VARCHAR(45) NULL DEFAULT NULL,
  `pdfPath` VARCHAR(255) NULL DEFAULT NULL,
  `imagePath` VARCHAR(255) NULL DEFAULT NULL,
  `publisherID` VARCHAR(50) NULL DEFAULT NULL,
  `publishedDate` DATE NULL DEFAULT NULL,
  `avgRate` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`bookID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `olms`.`bookstatistic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `olms`.`bookstatistic` (
  `bookIDRead` VARCHAR(45) NULL DEFAULT NULL,
  `totReadPages` INT NULL DEFAULT NULL,
  `avgTimeForPage` INT NULL DEFAULT NULL,
  `totNumberOfViews` INT NULL DEFAULT NULL,
  `maxPage` INT NULL DEFAULT NULL,
  `maxTime` INT NULL DEFAULT NULL,
  INDEX `bookID_idx` (`bookIDRead` ASC) VISIBLE,
  CONSTRAINT `bookIDRead`
    FOREIGN KEY (`bookIDRead`)
    REFERENCES `olms`.`ebook` (`bookID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `olms`.`borrow`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `olms`.`borrow` (
  `bookIDBorrow` VARCHAR(45) NULL DEFAULT NULL,
  `memberIDBorrow` VARCHAR(45) NULL DEFAULT NULL,
  `borrowedDate` DATE NULL DEFAULT NULL,
  `dueDate` DATE NULL DEFAULT NULL,
  `issuedBy` VARCHAR(45) NULL DEFAULT NULL,
  `returnedDate` DATE NULL DEFAULT NULL,
  `fine` INT NULL DEFAULT '0',
  `acceptedBy` VARCHAR(45) NULL DEFAULT NULL,
  INDEX `bookID_idx` (`bookIDBorrow` ASC) VISIBLE,
  INDEX `memberID_idx` (`memberIDBorrow` ASC) VISIBLE,
  INDEX `issuedBy_idx` (`issuedBy` ASC) VISIBLE,
  INDEX `acceptedBy_idx` (`acceptedBy` ASC) INVISIBLE,
  CONSTRAINT `acceptedBy`
    FOREIGN KEY (`acceptedBy`)
    REFERENCES `olms`.`staff` (`staffID`),
  CONSTRAINT `bookIDBorrow`
    FOREIGN KEY (`bookIDBorrow`)
    REFERENCES `olms`.`book` (`bookID`),
  CONSTRAINT `issuedBy`
    FOREIGN KEY (`issuedBy`)
    REFERENCES `olms`.`staff` (`staffID`),
  CONSTRAINT `memberIDBorrow`
    FOREIGN KEY (`memberIDBorrow`)
    REFERENCES `olms`.`member` (`memberID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `olms`.`publisher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `olms`.`publisher` (
  `publisherID` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `telephone` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `state` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`publisherID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `olms`.`rate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `olms`.`rate` (
  `bookIDRate` VARCHAR(45) NULL DEFAULT NULL,
  `memberIDRate` VARCHAR(45) NULL DEFAULT NULL,
  `rate` INT NULL DEFAULT NULL,
  `time` TIME NULL DEFAULT NULL,
  `page` INT NULL DEFAULT NULL,
  INDEX `bookID_idx` (`bookIDRate` ASC) VISIBLE,
  INDEX `memberID_idx` (`memberIDRate` ASC) VISIBLE,
  CONSTRAINT `bookIDRate`
    FOREIGN KEY (`bookIDRate`)
    REFERENCES `olms`.`ebook` (`bookID`),
  CONSTRAINT `memberIDRate`
    FOREIGN KEY (`memberIDRate`)
    REFERENCES `olms`.`member` (`memberID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `olms`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `olms`.`user` (
  `id` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `type` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
