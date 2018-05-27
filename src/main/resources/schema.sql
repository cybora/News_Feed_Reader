CREATE TABLE IF NOT EXISTS `news` (
  `guid` varchar(255) NOT NULL,
  `news_source_id` int(11) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `description` varchar(10000) DEFAULT NULL,
  `image_path` varchar(300) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `publication_date` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`guid`,`news_source_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `news_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `active_flag` bit(1) DEFAULT NULL,
  `source_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;