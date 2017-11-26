-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 26, 2017 at 10:32 AM
-- Server version: 5.7.19
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `GoldenAge`
--

-- --------------------------------------------------------

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
CREATE TABLE IF NOT EXISTS `clients` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `IC` varchar(20) NOT NULL,
  `Contact` varchar(20) DEFAULT NULL,
  `Birthyear` int(4) DEFAULT NULL,
  `Address` varchar(200) DEFAULT NULL,
  `Gender` varchar(1) DEFAULT NULL,
  `RegisDate` date DEFAULT NULL,
  `RegisType` varchar(1) DEFAULT NULL,
  `Password` varchar(255) NOT NULL,
  `Patient_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `clients`
--

INSERT INTO `clients` (`ID`, `Name`, `IC`, `Contact`, `Birthyear`, `Address`, `Gender`, `RegisDate`, `RegisType`, `Password`, `Patient_ID`) VALUES
(9, 'Maggie', '123123', '22668855', 1989, 'M', 'M', '2017-11-19', 'C', '4297f44b13955235245b2497399d7a93', 2),
(10, 'Peter Parker', '345345', '666634534', 1964, 'M', 'M', '2017-11-19', 'C', '0c0b3da4ac402bd86191d959be081114', 31),
(19, 'Thor', '5441', '899882883', 1972, 'adfadfad', 'F', '2017-11-26', 'C', 'd96eed18098da6ab5c15dd856998e4bd', 2);

-- --------------------------------------------------------

--
-- Table structure for table `medical`
--

DROP TABLE IF EXISTS `medical`;
CREATE TABLE IF NOT EXISTS `medical` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Date` date NOT NULL,
  `Nurse_ID` int(11) NOT NULL,
  `Patient_ID` int(11) NOT NULL,
  `Blood_Pressure` decimal(10,3) NOT NULL,
  `Heart_Rate` decimal(10,3) NOT NULL,
  `Sugar_Level` decimal(10,3) NOT NULL,
  `Temperature` decimal(5,2) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `medical`
--

INSERT INTO `medical` (`ID`, `Date`, `Nurse_ID`, `Patient_ID`, `Blood_Pressure`, `Heart_Rate`, `Sugar_Level`, `Temperature`) VALUES
(1, '2017-11-08', 3, 29, '12.900', '43.000', '0.000', '0.00'),
(2, '2017-11-15', 1, 29, '9.000', '9.000', '9.000', '9.00'),
(3, '2017-11-15', 1, 2, '98.000', '7.000', '9.000', '9.00'),
(4, '2017-11-18', 1, 2, '8.000', '9.000', '0.000', '8.00'),
(5, '2017-11-19', 1, 2, '5.000', '4.000', '6.000', '43.00'),
(6, '2017-11-26', 1, 2, '0.000', '0.000', '0.000', '0.00'),
(7, '2017-11-26', 1, 31, '0.000', '0.000', '0.000', '0.00'),
(8, '2017-11-26', 1, 31, '0.000', '0.000', '0.000', '0.00'),
(9, '2017-11-26', 1, 2, '6.000', '774.000', '32.000', '52.00'),
(10, '2017-11-26', 1, 23, '0.000', '0.000', '0.000', '0.00'),
(11, '2017-11-26', 1, 35, '0.000', '0.000', '0.000', '30.00'),
(12, '2017-11-26', 1, 2, '4.000', '45.000', '3.500', '5.00');

-- --------------------------------------------------------

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
CREATE TABLE IF NOT EXISTS `patients` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `IC` varchar(20) NOT NULL,
  `Contact` varchar(20) DEFAULT NULL,
  `Birthyear` int(4) DEFAULT NULL,
  `Address` varchar(200) DEFAULT NULL,
  `Gender` varchar(1) DEFAULT NULL,
  `RegisDate` date DEFAULT NULL,
  `RegisType` varchar(200) DEFAULT NULL,
  `BloodType` varchar(5) DEFAULT NULL,
  `Meals` varchar(200) DEFAULT NULL,
  `Allergic` varchar(200) DEFAULT NULL,
  `Sickness` varchar(200) DEFAULT NULL,
  `Margin` varchar(10) DEFAULT NULL,
  `Image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `patients`
--

INSERT INTO `patients` (`ID`, `Name`, `IC`, `Contact`, `Birthyear`, `Address`, `Gender`, `RegisDate`, `RegisType`, `BloodType`, `Meals`, `Allergic`, `Sickness`, `Margin`, `Image`) VALUES
(23, '234', '234', '123456', 1783, '2erter', 'M', '2017-11-17', 'P', 'O+', '', 'llll', 'werwer', '234.0', 'Patient234.png'),
(37, 'IRON MAN', '80098', '349492', 1699, 'Address', 'M', '2017-11-20', 'P', 'O+', '', 'Allergic', 'Sickness', '60.0', '31'),
(2, 'john', '991002994', '99339944', 1941, 'gdfgdfgdfg', 'M', '2017-11-20', 'P', 'O+', '', '', 'alzimer', '600.0', 'Patient991002994.png'),
(35, 'Hulk', '80098', '349492', 1699, 'Address', 'M', '2017-11-20', 'P', 'O+', 'Pork Fish ', 'Allergic', 'Sickness', '60.0', '31'),
(31, 'Harry', '80098', '349492', 1699, 'Address', 'M', '2017-11-20', 'P', 'O+', '', 'Allergic', 'Sickness', '60.0', 'Patient80098.png'),
(38, 'f', '2', '90039', 1952, '920', 'M', '2017-11-26', 'P', 'O+', 'Fish ', '9499', 'aoihf', '99', 'Patient2.png');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `IC` varchar(255) NOT NULL,
  `Contact` varchar(255) DEFAULT NULL,
  `Birthyear` int(4) DEFAULT NULL,
  `Address` varchar(200) DEFAULT NULL,
  `Gender` varchar(1) DEFAULT NULL,
  `RegisDate` date DEFAULT NULL,
  `RegisType` varchar(1) DEFAULT NULL,
  `Password` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `Name`, `IC`, `Contact`, `Birthyear`, `Address`, `Gender`, `RegisDate`, `RegisType`, `Password`) VALUES
(1, 'Facehugger', '123123', '123123', 1800, 'aasdasd', 'M', '2017-10-22', 'A', '4297f44b13955235245b2497399d7a93'),
(4, 'LOL', '1234567890', '99988', 2017, 'Address', 'M', '2017-12-23', 'A', '12345'),
(23, 'kof', '89', '90', 1984, '882', 'M', '2017-11-22', 'A', '7647966b7343c29048673252e490f736'),
(24, 'kok', '6795', '62454268866', 1994, 'fadgfwew', 'F', '2017-11-26', 'N', 'e074a2975740cdf3948cfc063892260e'),
(21, 'lo', '990', '99679696', 1939, 'Jalan bla bla bla bla, bla bla', 'M', '2017-11-22', 'N', '4fac9ba115140ac4f1c22da82aa0bc7f');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
