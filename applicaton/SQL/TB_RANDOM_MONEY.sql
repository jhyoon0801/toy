CREATE TABLE `TB_RANDOM_MONEY` (
  `SEQ` int(11) NOT NULL AUTO_INCREMENT,
  `OWNER_ID` varchar(45) COLLATE latin1_general_ci NOT NULL,
  `ROOM_ID` varchar(45) COLLATE latin1_general_ci NOT NULL,
  `TOKEN` char(3) COLLATE latin1_general_ci NOT NULL,
  `TOTAL_MONEY` int(11) NOT NULL,
  `DISTRIBUTION_SIZE` int(11) DEFAULT NULL,
  `CRETE_DATETIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`SEQ`),
  UNIQUE KEY `TOKEN_ROOM_ID` (`ROOM_ID`,`TOKEN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;