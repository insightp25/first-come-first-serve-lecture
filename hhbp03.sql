CREATE TABLE `session` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `host_name` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `capacity` int NOT NULL,
  `held_at` timestamp NOT NULL,
  `num_registered_applicants` int NOT NULL,
  `is_closed` tinyint(1) NOT NULL,
  `created_at` timestamp NOT NULL,
  INDEX `idx_session_created_at` (`created_at` DESC)
) ENGINE=InnoDB;

CREATE TABLE `session_application_history` (
  `session_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `is_registered` tinyint(1) NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`session_id`, `user_id`),
  INDEX `idx_session_application_history_created_at` (`created_at` DESC),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `user` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NOT NULL
) ENGINE=InnoDB;