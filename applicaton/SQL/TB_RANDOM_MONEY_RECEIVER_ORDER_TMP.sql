CREATE TABLE `TB_RANDOM_MONEY_RECEIVER_ORDER_TMP` (
  `SEQ` int(11) NOT NULL AUTO_INCREMENT,
  `META_INFO_SEQ` int(11) NOT NULL,
  `RECEIVER_ID` varchar(45) COLLATE latin1_general_ci NOT NULL,
  `CREATE_DATETIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`SEQ`),
  UNIQUE KEY `META_SEQ_RECEIVER_ID` (`META_INFO_SEQ`,`RECEIVER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;