# TDD와 클린 아키텍처로 구현한 선착순 특강 신청 서비스

### ERD 및 SQL 스크립트(MySQL InnoDB) - STEP04

![step04](./hhpb04b.png)

```sql
# version: step04
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

CREATE TABLE `session_application_history` (
  `session_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `is_registered` tinyint(1) NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`session_id`, `user_id`),
  FOREIGN KEY (`session_id`) REFERENCES `session` (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  INDEX `idx_session_application_history_session_id` (`session_id`),
  INDEX `idx_session_application_history_user_id` (`user_id`),
  INDEX `idx_session_application_history_created_at` (`created_at` DESC)  
) ENGINE=InnoDB;

CREATE TABLE `user` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL
) ENGINE=InnoDB;
```