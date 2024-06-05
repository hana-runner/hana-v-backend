CREATE TABLE IF NOT EXISTS `users` (
                            `id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT '유저 ID',
                            `username` varchar(32) NOT NULL unique COMMENT '유저 이름',
                            `pw` varchar(255) NOT NULL COMMENT '비밀번호',
                            `name` varchar(255) NOT NULL COMMENT '이름',
                            `email` varchar(255) NOT NULL unique COMMENT '이메일',
                            `birthday` date NOT NULL COMMENT '생일',
                            `gender` int NOT NULL COMMENT '성별',
                            `created_at` datetime NOT NULL COMMENT '생성 일시',
                            `updated_at` datetime NOT NULL COMMENT '수정 일시',
                            `is_deleted` boolean NULL COMMENT '삭제 여부',
                            `is_receive_email` boolean NOT NULL COMMENT '이메일 수신 여부',
                            `is_receive_alarm` boolean NOT NULL COMMENT '알림 수신 여부'
) COMMENT '유저 테이블';

CREATE TABLE IF NOT EXISTS `accounts` (
                            `id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT '계좌 ID',
                            `user_id` bigint NOT NULL COMMENT '유저 ID',
                            `account_number` varchar(255) NOT NULL unique COMMENT '계좌 번호',
                            `balance` bigint NOT NULL COMMENT '잔액',
                            `registered_at` datetime NOT NULL COMMENT '등록 일시',
                            `is_deleted` boolean NULL COMMENT '삭제 여부',
                            `bank_name` varchar(50) NOT NULL COMMENT '은행 이름',
                            `account_name` varchar(50) NOT NULL COMMENT '계좌 이름',
                            `account_type` varchar(50) NOT NULL COMMENT '계좌 유형',
                            CONSTRAINT fk_users_to_accounts FOREIGN KEY (user_id) REFERENCES users(id)
) COMMENT '계좌 테이블';

CREATE TABLE IF NOT EXISTS `categories` (
                            `id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT '카테고리 ID',
                            `title` varchar(50) NOT NULL unique COMMENT '제목',
                            `color` varchar(50) NOT NULL COMMENT '색상'
) COMMENT '카테고리 테이블';

CREATE TABLE IF NOT EXISTS `interests` (
                            `id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT '관심사 ID',
                            `title` varchar(30) NOT NULL unique COMMENT '제목',
                            `description` varchar(255) NULL COMMENT '설명',
                            `base_image_url` varchar(255) NOT NULL COMMENT '기본 이미지 URL',
                            `color` varchar(50) NOT NULL COMMENT '색상'
) COMMENT '관심사 테이블';

CREATE TABLE IF NOT EXISTS `cards` (
                            `id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT '카드 ID',
                            `name` varchar(255) NOT NULL unique COMMENT '카드 이름'
) COMMENT '카드 테이블';

CREATE TABLE IF NOT EXISTS `cards_benefits` (
                            `id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT '카드 혜택 ID',
                            `card_id` bigint NOT NULL COMMENT '카드 ID',
                            `group` varchar(30) NULL COMMENT '그룹',
                            `title` varchar(30) NOT NULL COMMENT '제목',
                            `description` varchar(255) NULL COMMENT '설명',
                            CONSTRAINT fk_cards_to_cb FOREIGN KEY (card_id) REFERENCES cards(id)
) COMMENT '카드 혜택 테이블';

CREATE TABLE IF NOT EXISTS `card_interests` (
                            `id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT '카드 관심사 ID',
                            `card_id` bigint NOT NULL COMMENT '카드 ID',
                            `interest_id` bigint NOT NULL COMMENT '관심사 ID',
                            CONSTRAINT fk_cards_to_ci FOREIGN KEY (card_id) REFERENCES cards(id),
                            CONSTRAINT fk_interests_to_ci FOREIGN KEY (interest_id) REFERENCES interests(id)
) COMMENT '카드 관심사 테이블';

CREATE TABLE IF NOT EXISTS `user_interests` (
                            `id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT '유저 관심사 ID',
                            `user_id` bigint NOT NULL COMMENT '유저 ID',
                            `interest_id` bigint NOT NULL COMMENT '관심사 ID',
                            `subtitle` varchar(30) NULL COMMENT '부제목',
                            `image_url` varchar(255) NOT NULL COMMENT '이미지 URL',
                            CONSTRAINT unique_user_interest_pair UNIQUE (user_id, interest_id),
                            CONSTRAINT fk_users_to_user_interests FOREIGN KEY (user_id) REFERENCES users(id),
                            CONSTRAINT fk_interests_to_user_interests FOREIGN KEY (interest_id) REFERENCES interests(id)
) COMMENT '유저 관심사 테이블';

CREATE TABLE IF NOT EXISTS `transaction_histories` (
                            `id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT '거래 내역 ID',
                            `account_id` bigint NOT NULL COMMENT '계좌 ID',
                            `user_id` bigint NOT NULL COMMENT '유저 ID',
                            `category_id` bigint NOT NULL COMMENT '카테고리 ID',
                            `approval_number` int NOT NULL COMMENT '승인 번호',
                            `type` varchar(50) NOT NULL COMMENT '유형',
                            `description` varchar(255) NOT NULL COMMENT '설명',
                            `action` varchar(10) NOT NULL COMMENT '행동',
                            `amount` bigint NOT NULL COMMENT '금액',
                            `balance` bigint NOT NULL COMMENT '잔액',
                            `created_at` datetime NOT NULL COMMENT '생성 일시',
                            `updated_at` datetime NOT NULL COMMENT '수정 일시',
                            CONSTRAINT fk_users_to_th FOREIGN KEY (user_id) REFERENCES users(id),
                            CONSTRAINT fk_accounts_to_th FOREIGN KEY (account_id) REFERENCES accounts(id),
                            CONSTRAINT fk_categories_to_th FOREIGN KEY (category_id) REFERENCES categories(id)
) COMMENT '거래 내역 테이블';

CREATE TABLE IF NOT EXISTS `transaction_history_details` (
                            `id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT '거래 내역 상세 ID',
                            `transaction_history_id` bigint NOT NULL COMMENT '거래 내역 ID',
                            `user_id` bigint NOT NULL COMMENT '유저 ID',
                            `interest_id` bigint NOT NULL COMMENT '관심사 ID',
                            `description` varchar(255) NOT NULL COMMENT '설명',
                            `amount` bigint NOT NULL COMMENT '금액',
                            `created_at` datetime NOT NULL COMMENT '생성 일시',
                            `updated_at` datetime NOT NULL COMMENT '수정 일시',
                            `is_deleted` boolean NOT NULL COMMENT '삭제 여부',
                            CONSTRAINT fk_users_to_thd FOREIGN KEY (user_id) REFERENCES users(id),
                            CONSTRAINT fk_interests_to_thd FOREIGN KEY (interest_id) REFERENCES interests(id),
                            CONSTRAINT fk_transaction_histories_to_thd FOREIGN KEY (transaction_history_id) REFERENCES transaction_histories(id)
) COMMENT '거래 내역 상세 테이블';

CREATE TABLE IF NOT EXISTS `account_api` (
                            `id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT '계좌 API ID',
                            `bank_name` varchar(50) NOT NULL COMMENT '은행 이름',
                            `account_number` varchar(255) NOT NULL unique COMMENT '계좌 번호',
                            `balance` bigint NOT NULL COMMENT '잔액',
                            `account_name` varchar(50) NOT NULL COMMENT '계좌 이름',
                            `account_type` varchar(50) NOT NULL COMMENT '계좌 유형'
) COMMENT '계좌 API 테이블';
