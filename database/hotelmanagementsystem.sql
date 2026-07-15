-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jul 06, 2026 at 03:56 PM
-- Server version: 8.0.41
-- PHP Version: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `hotelmanagementsystem` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `hotelmanagementsystem`;


--
-- Database: `hotelmanagementsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `activitylog`
--

DROP TABLE IF EXISTS `activitylog`;
CREATE TABLE IF NOT EXISTS `activitylog` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nic` varchar(12) NOT NULL,
  `activity` varchar(255) NOT NULL,
  `result` varchar(255) DEFAULT NULL,
  `activity_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `name` varchar(100) DEFAULT NULL,
  `jobrole` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Initial activity log is empty. New login/activity records are created by the application.
--

-- --------------------------------------------------------

--
-- Table structure for table `checkin_details`
--

DROP TABLE IF EXISTS `checkin_details`;
CREATE TABLE IF NOT EXISTS `checkin_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` varchar(20) DEFAULT NULL,
  `room_no` varchar(20) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `checkin_time` datetime DEFAULT NULL,
  `price` varchar(20) DEFAULT NULL,
  `nights` varchar(10) DEFAULT NULL,
  `total` varchar(20) DEFAULT NULL,
  `paid_amount` varchar(20) DEFAULT NULL,
  `pending_amount` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `checkout`
--

DROP TABLE IF EXISTS `checkout`;
CREATE TABLE IF NOT EXISTS `checkout` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cus_id` varchar(20) DEFAULT NULL,
  `room_no` varchar(20) DEFAULT NULL,
  `price` varchar(20) DEFAULT NULL,
  `pending` varchar(100) DEFAULT NULL,
  `paid` varchar(10) DEFAULT NULL,
  `checkin_time` datetime DEFAULT NULL,
  `checkout_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
CREATE TABLE IF NOT EXISTS `customers` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `NIC` varchar(20) NOT NULL,
  `check_in_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `room_number` varchar(10) NOT NULL,
  `payment_status` enum('Pending','Paid') NOT NULL DEFAULT 'Pending',
  `room_type` varchar(15) DEFAULT NULL,
  `room_price` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `NIC` (`NIC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `nic` varchar(12) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `age` varchar(10) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `job` varchar(50) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `datejoined` varchar(20) DEFAULT NULL,
  `salary` int DEFAULT NULL,
  PRIMARY KEY (`nic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`nic`, `name`, `age`, `gender`, `job`, `address`, `phone`, `email`, `password`, `datejoined`, `salary`) VALUES
('111111111111', 'Admin User', '35', 'Male', 'Admin', 'Colombo', '0710000000', 'admin@example.com', '000000', NULL, NULL),
('134289036V', 'Reception User', '32', 'Female', 'Receptionist', 'Colombo', '0710000001', 'reception@example.com', '123456', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
CREATE TABLE IF NOT EXISTS `event` (
  `eventnumber` int NOT NULL,
  `description` varchar(50) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  `duration` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`eventnumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`eventnumber`, `description`, `date`, `time`, `duration`) VALUES
(1, 'Meeting', '2025-05-06', '11:00 PM', '2 hr 30 min'),
(2, 'Annual Meeting', '2025-05-10', '10:00 AM', '0 hr 45 min'),
(3, 'Product Launch Function', '2025-05-07', '12:00 PM', '4 hr 45 min'),
(4, 'Wedding Function', '2025-05-11', '04:00 PM', '5 hr 00 min'),
(5, 'Team Building Event', '2025-05-12', '09:30 AM', '2 hr 45 min'),
(6, 'Client Appreciation Function', '2025-05-13', '01:30 PM', '3 hr 00 min'),
(7, 'Dinner Function', '2025-05-14', '07:00 PM', '6 hr 00 min'),
(8, 'Networking Event', '2025-05-15', '10:00 AM', '2 hr 00 min'),
(9, 'Board Meeting', '2025-06-01', '10:00 AM', '3 hr 30 min'),
(10, 'VIP Gala Dinner', '2025-06-03', '08:00 PM', '4 hr 00 min'),
(11, 'Team Training Workshop', '2025-06-07', '09:00 AM', '2 hr 15 min'),
(12, 'Annual Charity Event', '2025-06-10', '06:00 PM', '5 hr 00 min'),
(13, 'Holiday Party', '2025-06-12', '07:30 PM', '3 hr 00 min'),
(14, 'Product Demonstration', '2025-07-02', '11:00 AM', '2 hr 45 min'),
(15, 'Conference Workshop', '2025-07-05', '09:00 AM', '3 hr 15 min'),
(16, 'Networking Mixer', '2025-07-10', '06:30 PM', '1 hr 30 min'),
(17, 'Executive Meeting', '2025-07-15', '10:30 AM', '2 hr 00 min'),
(18, 'Wedding Reception', '2025-07-20', '06:00 PM', '4 hr 00 min'),
(19, 'Sales Event', '2025-08-02', '08:30 AM', '5 hr 30 min'),
(20, 'Business Luncheon', '2025-08-05', '12:00 PM', '2 hr 00 min'),
(21, 'Customer Appreciation Dinner', '2025-08-08', '07:00 PM', '3 hr 30 min'),
(22, 'Client Conference', '2025-08-12', '09:00 AM', '6 hr 00 min'),
(23, 'Annual Awards Ceremony', '2025-08-15', '05:00 PM', '4 hr 00 min'),
(24, 'Seminar', '2025-09-01', '10:30 AM', '3 hr 00 min'),
(25, 'Fashion Show', '2025-09-03', '08:00 PM', '2 hr 45 min'),
(26, 'Business Meeting', '2025-09-06', '02:00 PM', '3 hr 30 min'),
(27, 'Product Launch', '2025-09-09', '06:30 PM', '5 hr 00 min'),
(28, 'HR Training Seminar', '2025-09-15', '09:00 AM', '2 hr 00 min'),
(29, 'Corporate Retreat', '2025-09-18', '10:00 AM', '4 hr 30 min'),
(30, 'Networking Breakfast', '2025-10-01', '07:30 AM', '1 hr 30 min');

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
CREATE TABLE IF NOT EXISTS `inventory` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `item_name` varchar(255) NOT NULL,
  `category` varchar(100) DEFAULT NULL,
  `quantity` int NOT NULL DEFAULT '0',
  `price` decimal(10,2) NOT NULL,
  `low_stock_alert` int DEFAULT '10',
  `supplier_name` varchar(20) NOT NULL,
  `supplier_phone` varchar(10) NOT NULL,
  `date_added` datetime DEFAULT CURRENT_TIMESTAMP,
  `orders` int DEFAULT '0',
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`item_id`, `item_name`, `category`, `quantity`, `price`, `low_stock_alert`, `supplier_name`, `supplier_phone`, `date_added`, `orders`) VALUES
(1, 'Carrot', 'Food', 340, 100.00, 10, 'John', '0745678564', '2026-07-06 21:23:10', 0),
(2, 'Rice', 'Grains', 100, 50.00, 15, 'AgriSuppliers', '0771234567', '2026-07-06 21:23:10', 0),
(3, 'Wheat Flour', 'Grains', 80, 45.50, 10, 'FarmGoods', '0779876543', '2026-07-06 21:23:10', 0),
(4, 'Sugar', 'Sweeteners', 60, 120.00, 12, 'SweetFarm', '0765432198', '2026-07-06 21:23:10', 0),
(5, 'Salt', 'Spices', 90, 25.00, 10, 'OceanSalt', '0756789123', '2026-07-06 21:23:10', 0),
(6, 'Olive Oil', 'Oils', 40, 750.00, 8, 'HealthyHarvest', '0743217890', '2026-07-06 21:23:10', 0),
(7, 'Milk Powder', 'Dairy', 50, 950.00, 10, 'DairyBest', '0772345678', '2026-07-06 21:23:10', 0),
(8, 'Green Tea', 'Beverages', 70, 350.00, 10, 'TeaMasters', '0767890123', '2026-07-06 21:23:10', 0),
(9, 'Coffee Beans', 'Beverages', 30, 1250.00, 5, 'CoffeeWorld', '0754321987', '2026-07-06 21:23:10', 0),
(10, 'Tomato Ketchup', 'Condiments', 55, 280.00, 10, 'FreshSauce', '0745678912', '2026-07-06 21:23:10', 0),
(11, 'Canned Tuna', 'Seafood', 45, 520.00, 7, 'OceanCatch', '0778765432', '2026-07-06 21:23:10', 0),
(12, 'Cheddar Cheese', 'Dairy', 25, 1450.00, 5, 'CheeseHeaven', '0769876543', '2026-07-06 21:23:10', 0),
(13, 'Butter', 'Dairy', 35, 800.00, 6, 'DairyLand', '0753456789', '2026-07-06 21:23:10', 0);

-- --------------------------------------------------------

--
-- Table structure for table `inventory_orders`
--

DROP TABLE IF EXISTS `inventory_orders`;
CREATE TABLE IF NOT EXISTS `inventory_orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `item_name` varchar(255) NOT NULL,
  `order_quantity` varchar(50) NOT NULL,
  `order_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `cost` double DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
CREATE TABLE IF NOT EXISTS `room` (
  `roomnumber` varchar(10) DEFAULT NULL,
  `availability` varchar(20) DEFAULT NULL,
  `cleaning_status` varchar(20) DEFAULT NULL,
  `price` varchar(20) DEFAULT NULL,
  `bed_type` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`roomnumber`, `availability`, `cleaning_status`, `price`, `bed_type`) VALUES
('001', 'Available', 'Cleaned', '35000', 'Double Bed'),
('002', 'Available', 'Cleaned', '35000', 'Double Bed'),
('003', 'Available', 'Cleaned', '35000', 'Double Bed'),
('004', 'Available', 'Cleaned', '25000', 'Single Bed'),
('005', 'Available', 'Cleaned', '25000', 'Single Bed'),
('006', 'Available', 'Dirty', '25000', 'Single Bed');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
