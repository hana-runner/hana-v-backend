-- schema 생성
CREATE DATABASE IF NOT EXISTS hanaVdb;
USE hanaVdb;

drop table if exists users;
CREATE TABLE `users` (
                         `id`	bigint PRIMARY KEY AUTO_INCREMENT,
                         `username`	varchar(32)	NOT NULL unique,
                         `pw`	varchar(255)	NOT NULL,
                         `name`	varchar(255)	NOT NULL,
                         `email`	varchar(255)	NOT NULL unique,
                         `birthday`	date	NOT NULL,
                         `gender`	int	NOT NULL,
                         `created_at`	datetime	NOT NULL,
                         `updated_at`	datetime	NOT NULL,
                         `is_deleted`	boolean	NULL,
                         `is_receive_email`	boolean	NOT NULL,
                         `is_receive_alarm`	boolean	NOT NULL
);

drop table if exists accounts;
CREATE TABLE `accounts` (
                            `id`	bigint PRIMARY KEY	AUTO_INCREMENT,
                            `user_id`	bigint	NOT NULL,
                            `account_number`	varchar(255)	NOT NULL unique,
                            `balance`	bigint	NOT NULL,
                            `registered_at`	datetime	NOT NULL,
                            `is_deleted`	boolean	NULL,
                            `bank_name`	varchar(50)	NOT NULL,
                            CONSTRAINT fk_users_to_accounts FOREIGN KEY (user_id) REFERENCES users(id)
);

drop table if exists categories;
CREATE TABLE `categories` (
                              `id`	bigint PRIMARY KEY	AUTO_INCREMENT,
                              `title`	varchar(50)	NOT NULL unique,
                              `color`	varchar(50)	NOT NULL
);

drop table if exists interests;
CREATE TABLE `interests` (
                             `id`	bigint PRIMARY KEY	AUTO_INCREMENT,
                             `title`	varchar(30)	NOT NULL unique,
                             `description`	varchar(255)	NULL,
                             `base_image_url`	varchar(255)	NOT NULL,
                             `color`	varchar(50)	NOT NULL
);

drop table if exists cards;
CREATE TABLE `cards` (
                         `id`	bigint PRIMARY KEY	AUTO_INCREMENT,
                         `name`	varchar(255)	NOT NULL unique
);

drop table if exists cards_benefits;
CREATE TABLE `cards_benefits` (
                                  `id`	bigint	primary key auto_increment,
                                  `card_id`	bigint	NOT NULL,
                                  `group`	varchar(30)	NULL,
                                  `title`	varchar(30)	NOT NULL,
                                  `description`	varchar(255)	NULL,
                                  CONSTRAINT fk_cards_to_cb FOREIGN KEY (card_id) REFERENCES cards(id)
);

drop table if exists card_interests;
CREATE TABLE `card_interests` (
                                  `id`	bigint PRIMARY KEY	auto_increment,
                                  `card_id`	bigint	NOT NULL,
                                  `interest_id`	bigint	NOT NULL,
                                  CONSTRAINT fk_cards_to_ci FOREIGN KEY (card_id) REFERENCES cards(id),
                                  CONSTRAINT fk_interests_to_ci FOREIGN KEY (interest_id) REFERENCES interests(id)
);

drop table if exists user_interests;
CREATE TABLE `user_interests` (
                                  `id`	bigint PRIMARY KEY	auto_increment,
                                  `user_id`	bigint	NOT NULL,
                                  `interest_id`	bigint	NOT NULL,
                                  `subtitle`	varchar(30)	NULL,
                                  `image_url`	varchar(255)	NOT NULL,
                                  CONSTRAINT unique_user_interest_pair UNIQUE (user_id, interest_id),
                                  CONSTRAINT fk_users_to_user_interests FOREIGN KEY (user_id) REFERENCES users(id),
                                  CONSTRAINT fk_interests_to_user_interests FOREIGN KEY (interest_id) REFERENCES interests(id)
);

drop table if exists transaction_histories;
CREATE TABLE `transaction_histories` (
                                         `id`	bigint PRIMARY KEY	auto_increment,
                                         `account_id`	bigint	NOT NULL,
                                         `user_id`	bigint	NOT NULL,
                                         `category_id`	bigint	NOT NULL,
                                         `approval_number`	int	NOT NULL,
                                         `type`	varchar(50)	NOT NULL,
                                         `description`	varchar(255)	 NOT NULL,
                                         `action`	varchar(10)	NOT NULL,
                                         `amount`	bigint	NOT NULL,
                                         `balance`	int	NOT NULL,
                                         `created_at`	datetime	NOT NULL,
                                         `updated_at`	datetime	NOT NULL,
                                         CONSTRAINT fk_users_to_th FOREIGN KEY (user_id) REFERENCES users(id),
                                         CONSTRAINT fk_accounts_to_th FOREIGN KEY (account_id) REFERENCES accounts(id),
                                         CONSTRAINT fk_categories_to_th FOREIGN KEY (category_id) REFERENCES categories(id)
);

drop table if exists transaction_history_details;
CREATE TABLE `transaction_history_details` (
                                               `id`	bigint PRIMARY KEY	auto_increment,
                                               `transaction_history_id`	bigint	NOT NULL,
                                               `user_id`	bigint	NOT NULL,
                                               `interest_id`	bigint	NOT NULL,
                                               `description`	varchar(255)	NOT NULL,
                                               `amount`	bigint	NOT NULL,
                                               `created_at`	datetime	NOT NULL,
                                               `updated_at`	datetime	NOT NULL,
                                               `is_deleted`	boolean	NOT NULL,
                                               CONSTRAINT fk_users_to_thd FOREIGN KEY (user_id) REFERENCES users(id),
                                               CONSTRAINT fk_interests_to_thd FOREIGN KEY (interest_id) REFERENCES interests(id),
                                               CONSTRAINT fk_transaction_histories_to_thd FOREIGN KEY (transaction_history_id) REFERENCES transaction_histories(id)
);

drop table if exists account_api;
CREATE TABLE `account_api` (
                               `id`	bigint PRIMARY KEY	auto_increment,
                               `bank_name`	varchar(50)	NOT NULL,
                               `account_number`	varchar(255)	NOT NULL unique,
                               `balance`	bigint	NOT NULL
);