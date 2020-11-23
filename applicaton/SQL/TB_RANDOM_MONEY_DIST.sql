CREATE TABLE `tb_random_money_dist` (
  `META_INFO_SEQ` int(11) NOT NULL,
  `DIST_SEQ` varchar(45) COLLATE latin1_general_ci NOT NULL,
  `AMOUNT` varchar(45) COLLATE latin1_general_ci NOT NULL,
  `CRETE_DATETIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `META_DIST_SEQ` (`META_INFO_SEQ`,`DIST_SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
