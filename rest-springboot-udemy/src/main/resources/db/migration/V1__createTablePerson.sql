CREATE TABLE IF NOT EXISTS `person` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `address` varchar(100) NOT NULL,
                          `email` varchar(50) NOT NULL,
                          `first_name` varchar(50) NOT NULL,
                          `last_name` varchar(50) NOT NULL,
                          PRIMARY KEY (`id`)
);