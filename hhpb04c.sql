CREATE TABLE `lecture` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `host_name` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `capacity` int NOT NULL,
  `created_at` timestamp NOT NULL,
  INDEX `idx_lecture_created_at` (`created_at` DESC)
) ENGINE=InnoDB;

CREATE TABLE `session` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `held_at` timestamp NOT NULL,
  `num_registered_applicants` int NOT NULL,
  `created_at` timestamp NOT NULL,
  `lecture_id` bigint NOT NULL,
  INDEX `idx_session_created_at` (`created_at` DESC)
) ENGINE=InnoDB;

CREATE TABLE `user_session` (
  `session_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `is_registered` tinyint(1) NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`session_id`, `user_id`),
  FOREIGN KEY (`session_id`) REFERENCES `session` (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  INDEX `idx_user_session_session_id` (`session_id`),
  INDEX `idx_user_session_user_id` (`user_id`),
  INDEX `idx_user_session_created_at` (`created_at` DESC)  
) ENGINE=InnoDB;

CREATE TABLE `user` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL
) ENGINE=InnoDB;
