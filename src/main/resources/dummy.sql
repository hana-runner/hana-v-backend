-- 더미 데이터 삽입
CREATE DATABASE IF NOT EXISTS hanaVdb;
USE hanaVdb;

-- users 테이블
INSERT INTO users (username, pw, name, email, birthday, gender, created_at, updated_at, is_deleted, is_receive_email, is_receive_alarm) VALUES
                            ('user1', 'password1', '김철수', 'user1@example.com', '1990-01-01', 1, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user2', 'password2', '이영희', 'user2@example.com', '1985-05-15', 0, NOW(), NOW(), FALSE, TRUE, FALSE),
                            ('user3', 'password3', '박민준', 'user3@example.com', '1992-07-23', 1, NOW(), NOW(), FALSE, FALSE, TRUE),
                            ('user4', 'password4', '최수민', 'user4@example.com', '1988-11-30', 0, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user5', 'password5', '한서준', 'user5@example.com', '1995-03-10', 1, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user6', 'password6', '정하은', 'user6@example.com', '1982-06-17', 0, NOW(), NOW(), FALSE, TRUE, FALSE),
                            ('user7', 'password7', '윤지후', 'user7@example.com', '1993-09-25', 1, NOW(), NOW(), FALSE, FALSE, TRUE),
                            ('user8', 'password8', '장예준', 'user8@example.com', '1989-12-05', 0, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user9', 'password9', '서다인', 'user9@example.com', '1991-04-20', 1, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user10', 'password10', '홍유진', 'user10@example.com', '1987-08-15', 0, NOW(), NOW(), FALSE, TRUE, FALSE),
                            ('user11', 'password11', '김지훈', 'user11@example.com', '1990-11-21', 1, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user12', 'password12', '박서연', 'user12@example.com', '1985-03-19', 0, NOW(), NOW(), FALSE, TRUE, FALSE),
                            ('user13', 'password13', '이준서', 'user13@example.com', '1992-09-17', 1, NOW(), NOW(), FALSE, FALSE, TRUE),
                            ('user14', 'password14', '최하늘', 'user14@example.com', '1988-07-13', 0, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user15', 'password15', '강민지', 'user15@example.com', '1995-01-28', 1, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user16', 'password16', '조은비', 'user16@example.com', '1982-04-14', 0, NOW(), NOW(), FALSE, TRUE, FALSE),
                            ('user17', 'password17', '김민호', 'user17@example.com', '1993-10-03', 1, NOW(), NOW(), FALSE, FALSE, TRUE),
                            ('user18', 'password18', '이서현', 'user18@example.com', '1989-02-25', 0, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user19', 'password19', '박지민', 'user19@example.com', '1991-05-05', 1, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user20', 'password20', '최우진', 'user20@example.com', '1987-12-11', 0, NOW(), NOW(), FALSE, TRUE, FALSE),
                            ('user21', 'password21', '한지훈', 'user21@example.com', '1990-06-29', 1, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user22', 'password22', '정예린', 'user22@example.com', '1985-08-16', 0, NOW(), NOW(), FALSE, TRUE, FALSE),
                            ('user23', 'password23', '윤동현', 'user23@example.com', '2002-03-21', 1, NOW(), NOW(), FALSE, FALSE, TRUE),
                            ('user24', 'password24', '장수빈', 'user24@example.com', '2003-10-30', 0, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user25', 'password25', '서윤아', 'user25@example.com', '1995-12-19', 1, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user26', 'password26', '홍민준', 'user26@example.com', '1982-01-07', 0, NOW(), NOW(), FALSE, TRUE, FALSE),
                            ('user27', 'password27', '김서준', 'user27@example.com', '1993-06-12', 1, NOW(), NOW(), FALSE, FALSE, TRUE),
                            ('user28', 'password28', '이수민', 'user28@example.com', '1989-09-01', 0, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user29', 'password29', '박지훈', 'user29@example.com', '1991-11-11', 1, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user30', 'password30', '최윤서', 'user30@example.com', '1987-04-22', 0, NOW(), NOW(), FALSE, TRUE, FALSE),
                            ('user31', 'password31', '강민호', 'user31@example.com', '1950-02-18', 1, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user32', 'password32', '조하은', 'user32@example.com', '1985-11-30', 0, NOW(), NOW(), FALSE, TRUE, FALSE),
                            ('user33', 'password33', '김동현', 'user33@example.com', '1992-08-13', 1, NOW(), NOW(), FALSE, FALSE, TRUE),
                            ('user34', 'password34', '이예진', 'user34@example.com', '1988-03-08', 0, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user35', 'password35', '박민아', 'user35@example.com', '1995-09-27', 1, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user36', 'password36', '최현우', 'user36@example.com', '1982-06-03', 0, NOW(), NOW(), FALSE, TRUE, FALSE),
                            ('user37', 'password37', '한수민', 'user37@example.com', '1993-01-22', 1, NOW(), NOW(), FALSE, FALSE, TRUE),
                            ('user38', 'password38', '정지훈', 'user38@example.com', '1989-07-19', 0, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user39', 'password39', '윤서영', 'user39@example.com', '1991-10-28', 1, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user40', 'password40', '장민준', 'user40@example.com', '1987-05-07', 0, NOW(), NOW(), FALSE, TRUE, FALSE),
                            ('user41', 'password41', '서지훈', 'user41@example.com', '1990-03-29', 1, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user42', 'password42', '홍수민', 'user42@example.com', '1985-12-25', 0, NOW(), NOW(), FALSE, TRUE, FALSE),
                            ('user43', 'password43', '김동준', 'user43@example.com', '1992-02-02', 1, NOW(), NOW(), FALSE, FALSE, TRUE),
                            ('user44', 'password44', '이민지', 'user44@example.com', '1988-08-15', 0, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user45', 'password45', '박서윤', 'user45@example.com', '1995-04-20', 1, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user46', 'password46', '최지훈', 'user46@example.com', '1982-05-14', 0, NOW(), NOW(), FALSE, TRUE, FALSE),
                            ('user47', 'password47', '강하은', 'user47@example.com', '1993-11-05', 1, NOW(), NOW(), FALSE, FALSE, TRUE),
                            ('user48', 'password48', '조현우', 'user48@example.com', '1989-01-12', 0, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user49', 'password49', '김서연', 'user49@example.com', '1991-09-21', 1, NOW(), NOW(), FALSE, TRUE, TRUE),
                            ('user50', 'password50', '이준호', 'user50@example.com', '1987-02-09', 0, NOW(), NOW(), FALSE, TRUE, FALSE);

-- accounts 테이블
INSERT INTO accounts (user_id, account_number, balance, registered_at, is_deleted, bank_name, account_name, account_type) VALUES
                            (1, '123-45-6789-0', 1000000, NOW(), FALSE, '국민은행', '국민 SUPER 적금', '적금'),
                            (2, '0987-65-4321', 2500000, NOW(), FALSE, '우리은행', '우리 DREAM 입출금', '입출금'),
                            (3, '1122-33-4455', 500000, NOW(), FALSE, 'SC제일은행', 'SC 프라임 사업자', '사업자'),
                            (4, '5566-77-8899', 750000, NOW(), FALSE, '한국씨티은행', '씨티 퍼스널 통장', '개인'),
                            (5, '6677-88-9900', 1500000, NOW(), FALSE, '하나은행', '하나 PLUS 적금', '적금'),
                            (6, '7788-99-0011', 1750000, NOW(), FALSE, '신한은행', '신한 SMART 입출금', '입출금'),
                            (7, '8899-00-1122', 2000000, NOW(), FALSE, '케이뱅크', '케이뱅크 비즈니스 통장', '사업자'),
                            (8, '9900-11-2233', 300000, NOW(), FALSE, '카카오뱅크', '카카오 개인 통장', '개인'),
                            (9, '0011-22-3344', 900000, NOW(), FALSE, '토스뱅크', '토스 SAVE 적금', '적금'),
                            (10, '1122-33-4456', 1250000, NOW(), FALSE, '한국산업은행', '산은 PLUS 입출금', '입출금'),
                            (1, '2233-44-5566', 1100000, NOW(), FALSE, '중소기업은행', '중기은행 개인 통장', '개인'),
                            (2, '3344-55-6677', 2600000, NOW(), FALSE, '한국수출입은행', '수출입은행 비즈니스 통장', '사업자'),
                            (3, '4455-66-7788', 600000, NOW(), FALSE, '수협은행', '수협 DREAM 적금', '적금'),
                            (4, '5566-77-8890', 800000, NOW(), FALSE, 'NH농협은행', 'NH 입출금 통장', '입출금'),
                            (5, '6677-88-9901', 1400000, NOW(), FALSE, '대구은행', '대구은행 비즈니스 통장', '사업자'),
                            (11, '1133-44-5578', 1200000, NOW(), FALSE, '부산은행', '부산은행 PLUS 입출금', '입출금'),
                            (12, '2244-55-6638', 1800000, NOW(), FALSE, '경남은행', '경남은행 DREAM 적금', '적금'),
                            (13, '3355-66-1799', 900000, NOW(), FALSE, '광주은행', '광주은행 개인 통장', '개인'),
                            (14, '4466-77-8820', 1400000, NOW(), FALSE, '전북은행', '전북은행 비즈니스 통장', '사업자'),
                            (15, '5577-88-9911', 1100000, NOW(), FALSE, '제주은행', '제주은행 SAVE 적금', '적금'),
                            (16, '6688-99-0022', 1600000, NOW(), FALSE, '경기은행', '경기은행 SMART 입출금', '입출금'),
                            (17, '7799-00-2143', 800000, NOW(), FALSE, '강원은행', '강원은행 개인 통장', '개인'),
                            (18, '8800-11-3244', 1300000, NOW(), FALSE, '충북은행', '충북은행 PLUS 사업자', '사업자'),
                            (19, '9911-22-3553', 1000000, NOW(), FALSE, '충남은행', '충남은행 DREAM 적금', '적금'),
                            (20, '0022-33-6466', 1500000, NOW(), FALSE, '전남은행', '전남은행 SMART 입출금', '입출금'),
                            (21, '1133-44-7587', 900000, NOW(), FALSE, '경북은행', '경북은행 개인 통장', '개인'),
                            (22, '2244-55-6688', 1700000, NOW(), FALSE, '경북은행', '경북은행 비즈니스 통장', '사업자'),
                            (23, '3355-66-7399', 1100000, NOW(), FALSE, '울산은행', '울산은행 SAVE 적금', '적금'),
                            (24, '4466-77-8801', 1200000, NOW(), FALSE, '세종은행', '세종은행 PLUS 입출금', '입출금'),
                            (25, '5577-88-1911', 1000000, NOW(), FALSE, '인천은행', '인천은행 개인 통장', '개인'),
                            (26, '6688-99-0023', 1800000, NOW(), FALSE, '대전은행', '대전은행 PLUS 사업자', '사업자'),
                            (27, '7799-00-1133', 900000, NOW(), FALSE, '경기은행', '경기은행 DREAM 적금', '적금'),
                            (28, '8800-11-2244', 1400000, NOW(), FALSE, '강원은행', '강원은행 SMART 입출금', '입출금'),
                            (29, '9911-22-1355', 1100000, NOW(), FALSE, '충북은행', '충북은행 개인 통장', '개인'),
                            (30, '0022-33-4466', 1600000, NOW(), FALSE, '충남은행', '충남은행 비즈니스 통장', '사업자'),
                            (31, '1133-44-5677', 1000000, NOW(), FALSE, '전남은행', '전남은행 SAVE 적금', '적금'),
                            (32, '2244-55-6188', 1500000, NOW(), FALSE, '경북은행', '경북은행 PLUS 입출금', '입출금'),
                            (33, '3355-66-7799', 900000, NOW(), FALSE, '울산은행', '울산은행 개인 통장', '개인'),
                            (34, '4466-77-8500', 1300000, NOW(), FALSE, '세종은행', '세종은행 PLUS 사업자', '사업자'),
                            (35, '5577-88-1011', 1100000, NOW(), FALSE, '인천은행', '인천은행 DREAM 적금', '적금'),
                            (36, '6688-99-1122', 1700000, NOW(), FALSE, '대전은행', '대전은행 SMART 입출금', '입출금'),
                            (37, '7799-00-2133', 1000000, NOW(), FALSE, '경기은행', '경기은행 개인 통장', '개인'),
                            (38, '8800-11-3344', 1400000, NOW(), FALSE, '강원은행', '강원은행 비즈니스 통장', '사업자'),
                            (39, '9911-22-4355', 900000, NOW(), FALSE, '충북은행', '충북은행 SAVE 적금', '적금'),
                            (40, '0022-33-5466', 1600000, NOW(), FALSE, '충남은행', '충남은행 PLUS 입출금', '입출금'),
                            (41, '1133-44-3677', 1100000, NOW(), FALSE, '전남은행', '전남은행 개인 통장', '개인'),
                            (42, '2244-55-6388', 1800000, NOW(), FALSE, '경북은행', '경북은행 PLUS 사업자', '사업자'),
                            (43, '3355-66-7199', 1000000, NOW(), FALSE, '울산은행', '울산은행 DREAM 적금', '적금'),
                            (44, '4466-77-8800', 1400000, NOW(), FALSE, '세종은행', '세종은행 SMART 입출금', '입출금'),
                            (45, '5577-88-0911', 900000, NOW(), FALSE, '인천은행', '인천은행 개인 통장', '개인'),
                            (46, '6688-99-1022', 1600000, NOW(), FALSE, '대전은행', '대전은행 비즈니스 통장', '사업자'),
                            (47, '7799-00-1339', 1100000, NOW(), FALSE, '경기은행', '경기은행 SAVE 적금', '적금'),
                            (48, '8800-11-3644', 1300000, NOW(), FALSE, '강원은행', '강원은행 PLUS 입출금', '입출금'),
                            (49, '9911-22-4375', 1000000, NOW(), FALSE, '충북은행', '충북은행 개인 통장', '개인'),
                            (50, '0022-33-5461', 1700000, NOW(), FALSE, '충남은행', '충남은행 PLUS 사업자', '사업자');

-- categories 테이블
INSERT INTO categories (title, color) VALUES
                            ('식당', '#FF5733'),
                            ('카페', '#FFC300'),
                            ('배달', '#900C3F'),
                            ('편의점', '#581845'),
                            ('마트', '#FF5733'),
                            ('유흥', '#FFC300'),
                            ('쇼핑', '#DAF7A6'),
                            ('교육', '#33FF57'),
                            ('취미', '#FF33FF'),
                            ('여가', '#33FFF6'),
                            ('영화', '#8E44AD'),
                            ('게임', '#2E4053'),
                            ('의료', '#1ABC9C'),
                            ('피트니스', '#F1C40F'),
                            ('미용', '#E74C3C'),
                            ('주거', '#3498DB'),
                            ('통신', '#9B59B6'),
                            ('보험', '#34495E'),
                            ('세금', '#16A085'),
                            ('금융', '#27AE60'),
                            ('급여', '#2980B9'),
                            ('이체', '#8E44AD'),
                            ('저축', '#2C3E50'),
                            ('투자', '#7D3C98'),
                            ('후불결제', '#C0392B'),
                            ('카드대금', '#D35400'),
                            ('교통', '#E67E22'),
                            ('유류', '#F39C12'),
                            ('생활', '#E74C3C'),
                            ('여행', '#9B59B6'),
                            ('숙박', '#34495E'),
                            ('육아', '#16A085'),
                            ('반려동물', '#27AE60'),
                            ('기타', '#2980B9');

-- interests 테이블
INSERT INTO interests (title, description, base_image_url, color) VALUES
                            ('영끌', '영혼까지 끌어모아 최대한 금융 자원을 활용하는 것을 의미합니다. 부동산이나 주식 등 자산 가치 상승에 대한 기대감으로 인해 과도한 대출을 통해 투자하는 행태를 말합니다.', 'http://example.com/1.jpg', '#FF5733'),
                            ('사회생활', '사회 구성원으로서 다른 사람들과 더불어 살아가는 생활을 의미합니다. 인간, 개미, 벌 등 다양한 생물체가 집단으로 모여 질서를 유지하며 살아가는 공동생활을 포함합니다.', 'http://example.com/2.jpg', '#C70039'),
                            ('기프트', '선물을 주고받는 행위와 관련된 소비 활동을 포함합니다. 최근에는 문화상품권 등 다양한 형태의 선물 상품이 등장하고 있습니다.', 'http://example.com/3.jpg', '#900C3F'),
                            ('자기관리', '자신의 능력과 자질을 개발하고 발전시키는 활동을 의미합니다. 자기 성찰, 자기 계발, 건강 관리 등 개인의 성장과 발전을 위한 다양한 노력을 포함합니다.', 'http://example.com/4.jpg', '#581845'),
                            ('덕질소비', '특정 분야에 대한 열정적인 관심과 애착으로 인해 이루어지는 소비 활동을 말합니다. 취미, 관심사, 팬덤 등과 관련된 상품 및 서비스에 대한 지출을 포함합니다.', 'http://example.com/5.jpg', '#FF5733'),
                            ('기부', '자발적으로 금전적, 물질적 또는 시간적 자원을 사회에 환원하는 활동을 의미합니다. 개인, 단체, 기관 등에 대한 기부 행위를 포함합니다.', 'http://example.com/6.jpg', '#FFC300'),
                            ('홧김비', '감정적 충동이나 즉흥적인 욕구에 의해 이루어지는 소비 활동을 말합니다. 화가 나거나 기분이 좋을 때 충동적으로 지출하는 경향을 포함합니다.', 'http://example.com/7.jpg', '#DAF7A6'),
                            ('자기계발', '개인의 능력과 자질을 향상시키기 위한 노력을 의미합니다. 교육, 훈련, 경험 등을 통해 자신의 역량을 개선하고 발전시키는 활동을 포함합니다.', 'http://example.com/8.jpg', '#33FF57'),
                            ('재테크', '개인의 재정 관리와 투자 활동을 의미합니다. 저축, 투자, 보험, 부동산 등 다양한 금융 수단을 활용하여 자산을 늘리고 재무 상태를 개선하는 것을 포함합니다.', 'http://example.com/9.jpg', '#FF33FF'),
                            ('디토', '디지털 기술과 테크놀로지 관련 활동을 의미합니다. 스마트폰, 컴퓨터, 인터넷, 소프트웨어 등 디지털 기기와 서비스에 대한 관심과 소비를 포함합니다.', 'http://example.com/10.jpg', '#33FFF6'),
                            ('친환경', '환경 보호와 지속가능성을 고려한 활동을 의미합니다. 재활용, 에너지 절감, 친환경 제품 구매 등 환경 친화적인 소비 행태를 포함합니다.', 'http://example.com/11.jpg', '#8E44AD'),
                            ('도파밍', '기분 전환과 즐거움을 얻기 위한 활동을 의미합니다. 게임, 소셜미디어, 쇼핑 등 도파민 분비를 자극하는 행위를 통해 일시적인 만족감을 얻는 것을 포함합니다.', 'http://example.com/12.jpg', '#2E4053'),
                            ('럭셔리', '고급 및 명품 소비에 대한 관심과 행태를 의미합니다. 고가의 제품이나 서비스를 선호하고 소비하는 경향을 포함합니다.', 'http://example.com/13.jpg', '#1ABC9C'),
                            ('소셜', '사회 구성원으로서 다른 사람들과 더불어 살아가는 생활을 의미합니다. 인간, 개미, 벌 등 다양한 생물체가 집단으로 모여 질서를 유지하며 살아가는 공동생활을 포함합니다.', 'http://example.com/14.jpg', '#F1C40F'),
                            ('효도', '부모님 및 어른에 대한 존경과 예의를 나타내는 행동을 의미합니다. 부모님에 대한 감사와 경의를 표현하는 행태를 포함합니다.', 'http://example.com/15.jpg', '#E74C3C'),
                            ('종교', '종교 활동과 관련된 다양한 신앙실행, 의식, 의례, 예배 등을 포함합니다. 종교적 신념과 신앙실행에 대한 관심과 참여를 나타냅니다.', 'http://example.com/16.jpg', '#3498DB'),
                            ('구독비', '정기적으로 구독하는 서비스와 관련된 소비 활동을 의미합니다. 영화, 음악, 독서, 게임 등 다양한 구독 서비스를 이용하는 행태를 포함합니다.', 'http://example.com/17.jpg', '#9B59B6'),
                            ('컬렉션', '수집과 취미 관련 활동을 의미합니다. 동전, 우표, 인형, 음반 등 다양한 아이템을 수집하고 보관하는 행태를 포함합니다.', 'http://example.com/18.jpg', '#34495E');

-- user_interests 테이블
INSERT INTO user_interests (user_id, interest_id, subtitle, image_url) VALUES
                            (1, 1, '최대의 금융 활용', 'http://example.com/1.jpg'),
                            (1, 2, '사회적 관계 형성', 'http://example.com/2.jpg'),
                            (2, 3, '선물 고르기', 'http://example.com/3.jpg'),
                            (3, 4, '자기 발전 계획', 'http://example.com/4.jpg'),
                            (4, 5, '열정적인 취미 생활', 'http://example.com/5.jpg'),
                            (5, 6, '기부로 나누는 기쁨', 'http://example.com/6.jpg'),
                            (6, 7, '감정 조절하기', 'http://example.com/7.jpg'),
                            (7, 8, '자기계발 목표 세우기', 'http://example.com/8.jpg'),
                            (8, 9, '재무 관리 노하우', 'http://example.com/9.jpg'),
                            (9, 10, '디지털 기술 활용하기', 'http://example.com/10.jpg'),
                            (10, 11, '환경 보호 실천하기', 'http://example.com/11.jpg'),
                            (11, 12, '기분 전환 활동 즐기기', 'http://example.com/12.jpg'),
                            (12, 13, '럭셔리 제품 탐색하기', 'http://example.com/13.jpg'),
                            (13, 14, '사회적 관계 유지하기', 'http://example.com/14.jpg'),
                            (14, 15, '부모님께 효도하기', 'http://example.com/15.jpg'),
                            (15, 16, '종교 활동 참여하기', 'http://example.com/16.jpg'),
                            (16, 17, '구독 서비스 활용하기', 'http://example.com/17.jpg'),
                            (17, 18, '취미 컬렉션 관리하기', 'http://example.com/18.jpg'),
                            (18, 1, '금융 활용 전략 수립', 'http://example.com/1.jpg'),
                            (19, 2, '사회생활 스킬 기르기', 'http://example.com/2.jpg'),
                            (20, 3, '선물 아이디어 찾기', 'http://example.com/3.jpg'),
                            (21, 4, '자기관리 습관 만들기', 'http://example.com/4.jpg'),
                            (22, 5, '열정적인 덕질 생활', 'http://example.com/5.jpg'),
                            (23, 6, '기부 문화 확산하기', 'http://example.com/6.jpg'),
                            (24, 7, '충동 구매 자제하기', 'http://example.com/7.jpg'),
                            (25, 8, '자기계발 실천하기', 'http://example.com/8.jpg'),
                            (26, 9, '재테크 노하우 공유', 'http://example.com/9.jpg'),
                            (27, 10, '디지털 기술 활용도 높이기', 'http://example.com/10.jpg'),
                            (28, 11, '친환경 생활 습관 기르기', 'http://example.com/11.jpg'),
                            (29, 12, '기분 전환 활동 즐기기', 'http://example.com/12.jpg'),
                            (30, 13, '럭셔리 제품 구매 계획', 'http://example.com/13.jpg'),
                            (31, 14, '사회적 관계 돈독히 하기', 'http://example.com/14.jpg'),
                            (32, 15, '부모님께 감사 표현하기', 'http://example.com/15.jpg'),
                            (33, 16, '종교 활동 참여하기', 'http://example.com/16.jpg'),
                            (34, 17, '구독 서비스 활용하기', 'http://example.com/17.jpg'),
                            (35, 18, '취미 컬렉션 관리하기', 'http://example.com/18.jpg'),
                            (36, 1, '금융 활용 노하우 공유', 'http://example.com/1.jpg'),
                            (37, 2, '사회생활 스킬 향상하기', 'http://example.com/2.jpg'),
                            (38, 3, '선물 고르는 센스 기르기', 'http://example.com/3.jpg'),
                            (39, 4, '자기관리 계획 수립하기', 'http://example.com/4.jpg'),
                            (40, 5, '열정적인 덕질 생활 즐기기', 'http://example.com/5.jpg'),
                            (41, 6, '기부 문화 확산에 동참하기', 'http://example.com/6.jpg'),
                            (42, 7, '충동 구매 자제하기', 'http://example.com/7.jpg'),
                            (43, 8, '자기계발 목표 달성하기', 'http://example.com/8.jpg'),
                            (44, 9, '재테크 전략 수립하기', 'http://example.com/9.jpg'),
                            (45, 10, '디지털 기술 활용도 높이기', 'http://example.com/10.jpg'),
                            (46, 11, '친환경 생활 습관 기르기', 'http://example.com/11.jpg'),
                            (47, 12, '기분 전환 활동 즐기기', 'http://example.com/12.jpg'),
                            (48, 13, '럭셔리 제품 구매 계획', 'http://example.com/13.jpg'),
                            (49, 14, '사회적 관계 돈독히 하기', 'http://example.com/14.jpg'),
                            (50, 15, '부모님께 감사 표현하기', 'http://example.com/15.jpg');

-- transaction_histories 테이블
INSERT INTO transaction_histories (account_id, user_id, category_id, approval_number, type, description, action, amount, balance, created_at, updated_at) VALUES
                            (1, 1, 1, 1001, 'Payment', 'Dinner at restaurant', 'debit', 50000, 950000, NOW(), NOW()),
                            (2, 2, 2, 1002, 'Payment', 'Coffee at cafe', 'debit', 15000, 2485000, NOW(), NOW()),
                            (3, 3, 3, 1003, 'Payment', 'Online delivery', 'debit', 30000, 470000, NOW(), NOW()),
                            (4, 4, 4, 1004, 'Payment', 'Convenience store', 'debit', 20000, 730000, NOW(), NOW()),
                            (5, 5, 5, 1005, 'Payment', 'Grocery shopping', 'debit', 50000, 1450000, NOW(), NOW()),
                            (6, 6, 6, 1006, 'Payment', 'Night out', 'debit', 60000, 1690000, NOW(), NOW()),
                            (7, 7, 7, 1007, 'Payment', 'Online shopping', 'debit', 70000, 1930000, NOW(), NOW()),
                            (8, 8, 8, 1008, 'Payment', 'Hobby supplies', 'debit', 80000, 220000, NOW(), NOW()),
                            (9, 9, 9, 1009, 'Payment', 'Fitness membership', 'debit', 90000, 810000, NOW(), NOW()),
                            (10, 10, 10, 1010, 'Payment', 'Beauty salon', 'debit', 100000, 1150000, NOW(), NOW()),
                            (11, 1, 11, 1011, 'Payment', 'Medical expenses', 'debit', 110000, 840000, NOW(), NOW()),
                            (12, 2, 12, 1012, 'Payment', 'Transportation', 'debit', 120000, 2365000, NOW(), NOW()),
                            (13, 3, 13, 1013, 'Payment', 'Fuel', 'debit', 130000, 340000, NOW(), NOW()),
                            (14, 4, 14, 1014, 'Payment', 'Rent', 'debit', 140000, 590000, NOW(), NOW()),
                            (15, 5, 15, 1015, 'Payment', 'Utilities', 'debit', 150000, 1300000, NOW(), NOW()),
                            (16, 6, 16, 1016, 'Payment', 'Insurance', 'debit', 160000, 1530000, NOW(), NOW()),
                            (17, 7, 17, 1017, 'Payment', 'Taxes', 'debit', 170000, 1760000, NOW(), NOW()),
                            (18, 8, 18, 1018, 'Payment', 'Education', 'debit', 180000, 40000, NOW(), NOW()),
                            (19, 9, 19, 1019, 'Payment', 'Subscriptions', 'debit', 190000, 710000, NOW(), NOW()),
                            (20, 10, 20, 1020, 'Payment', 'Charity', 'debit', 200000, 950000, NOW(), NOW()),
                            (1, 1, 1, 1021, 'Payment', 'Car maintenance', 'debit', 210000, 640000, NOW(), NOW()),
                            (2, 2, 2, 1022, 'Payment', 'Home improvement', 'debit', 220000, 2145000, NOW(), NOW()),
                            (3, 3, 3, 1023, 'Payment', 'Vacation', 'debit', 230000, 110000, NOW(), NOW()),
                            (4, 4, 4, 1024, 'Payment', 'Pet care', 'debit', 240000, 350000, NOW(), NOW()),
                            (5, 5, 5, 1025, 'Payment', 'Event tickets', 'debit', 250000, 1050000, NOW(), NOW()),
                            (6, 6, 6, 1026, 'Payment', 'Dining out', 'debit', 260000, 1270000, NOW(), NOW()),
                            (7, 7, 7, 1027, 'Payment', 'Gift', 'debit', 270000, 1490000, NOW(), NOW()),
                            (8, 8, 8, 1028, 'Payment', 'Clothing', 'debit', 280000, 60000, NOW(), NOW()),
                            (9, 9, 9, 1029, 'Payment', 'Electronics', 'debit', 290000, 420000, NOW(), NOW()),
                            (10, 10, 10, 1030, 'Payment', 'Books', 'debit', 300000, 850000, NOW(), NOW());

-- transaction_history_details 테이블
INSERT INTO transaction_history_details (transaction_history_id, user_id, interest_id, description, amount, created_at, updated_at, is_deleted) VALUES
                            (1, 1, 1, 'Maximum financial utilization', 50000, NOW(), NOW(), FALSE),
                            (2, 2, 2, 'Social activities and relations', 15000, NOW(), NOW(), FALSE),
                            (3, 3, 3, 'Gift related consumption', 30000, NOW(), NOW(), FALSE),
                            (4, 4, 4, 'Self management and development', 20000, NOW(), NOW(), FALSE),
                            (5, 5, 5, 'Hobby and passion consumption', 50000, NOW(), NOW(), FALSE),
                            (6, 6, 6, 'Charitable activities', 60000, NOW(), NOW(), FALSE),
                            (7, 7, 7, 'Impulsive consumption', 70000, NOW(), NOW(), FALSE),
                            (8, 8, 8, 'Personal skill development', 80000, NOW(), NOW(), FALSE),
                            (9, 9, 9, 'Financial management and investment', 90000, NOW(), NOW(), FALSE),
                            (10, 10, 10, 'Digital and technology', 100000, NOW(), NOW(), FALSE),
                            (11, 1, 11, 'Environmental protection activities', 110000, NOW(), NOW(), FALSE),
                            (12, 2, 12, 'Mood-enhancing activities', 120000, NOW(), NOW(), FALSE),
                            (13, 3, 13, 'Luxury and high-end consumption', 130000, NOW(), NOW(), FALSE),
                            (14, 4, 14, 'Social activities and relations', 140000, NOW(), NOW(), FALSE),
                            (15, 5, 15, 'Respect for parents and elders', 150000, NOW(), NOW(), FALSE),
                            (16, 6, 16, 'Religious activities', 160000, NOW(), NOW(), FALSE),
                            (17, 7, 17, 'Subscription services', 170000, NOW(), NOW(), FALSE),
                            (18, 8, 18, 'Collection and hobby-related', 180000, NOW(), NOW(), FALSE),
                            (19, 9, 19, 'Environmental protection activities', 190000, NOW(), NOW(), FALSE),
                            (20, 10, 20, 'Mood-enhancing activities', 200000, NOW(), NOW(), FALSE),
                            (21, 1, 1, 'Luxury and high-end consumption', 210000, NOW(), NOW(), FALSE),
                            (22, 2, 2, 'Social activities and relations', 220000, NOW(), NOW(), FALSE),
                            (23, 3, 3, 'Respect for parents and elders', 230000, NOW(), NOW(), FALSE),
                            (24, 4, 4, 'Religious activities', 240000, NOW(), NOW(), FALSE),
                            (25, 5, 5, 'Subscription services', 250000, NOW(), NOW(), FALSE),
                            (26, 6, 6, 'Collection and hobby-related', 260000, NOW(), NOW(), FALSE),
                            (27, 7, 7, 'Maximum financial utilization', 270000, NOW(), NOW(), FALSE),
                            (28, 8, 8, 'Social activities and relations', 280000, NOW(), NOW(), FALSE),
                            (29, 9, 9, 'Gift related consumption', 290000, NOW(), NOW(), FALSE),
                            (30, 10, 10, 'Self management and development', 300000, NOW(), NOW(), FALSE),
                            (31, 1, 11, 'Hobby and passion consumption', 310000, NOW(), NOW(), FALSE),
                            (32, 2, 12, 'Charitable activities', 320000, NOW(), NOW(), FALSE),
                            (33, 3, 13, 'Impulsive consumption', 330000, NOW(), NOW(), FALSE),
                            (34, 4, 14, 'Personal skill development', 340000, NOW(), NOW(), FALSE),
                            (35, 5, 15, 'Financial management and investment', 350000, NOW(), NOW(), FALSE),
                            (36, 6, 16, 'Digital and technology', 360000, NOW(), NOW(), FALSE),
                            (37, 7, 17, 'Environmental protection activities', 370000, NOW(), NOW(), FALSE),
                            (38, 8, 18, 'Mood-enhancing activities', 380000, NOW(), NOW(), FALSE),
                            (39, 9, 19, 'Luxury and high-end consumption', 390000, NOW(), NOW(), FALSE),
                            (40, 10, 20, 'Social activities and relations', 400000, NOW(), NOW(), FALSE);