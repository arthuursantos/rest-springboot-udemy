CREATE TABLE `book` (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `author` varchar(100),
  `launch_date` datetime NOT NULL,
  `price` decimal NOT NULL,
  `title` varchar(200)
);
