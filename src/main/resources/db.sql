-- 데이터베이스 및 테이블 생성
CREATE DATABASE IF NOT EXISTS tripspring;
USE tripspring;

-- 기존 테이블 삭제
DROP TABLE IF EXISTS `post_hashtag`;
DROP TABLE IF EXISTS `hashtag`;
drop table if EXISTS `purchase`;
DROP TABLE IF EXISTS `travel_diary`;
DROP TABLE IF EXISTS `travel_graph`;
drop table if exists `comment`;
drop table if exists `post_like`;
drop table if exists `post_view`;
DROP TABLE IF EXISTS `post`;
drop table if exists `answers`;
drop table if exists `questions`;
drop table if EXISTS `cash_to_dotori`;
drop table if EXISTS `refresh_token`;
DROP TABLE IF EXISTS `user`;
drop table if exists `trip`;
drop table if EXISTS `location`;
drop table if EXISTS `place`;


use tripspring;

CREATE TABLE `user`
(
    `id`         bigint primary key NOT NULL auto_increment,
    `name`       varchar(50)        NOT NULL,
    `email`      varchar(100)       NOT NULL,
    `password`   varchar(50)        NOT NULL,
    `role`       varchar(10)        NOT NULL,
    `created_at` Date               NOT NULL,
    `dotori`     bigint             NOT NULL
);


CREATE TABLE `travel_graph`
(
    `id`       bigint primary key NOT NULL auto_increment,
    `user_id`  bigint             NOT NULL,
    `mountain` bigint             NOT NULL,
    `sea`      bigint             NOT NULL,
    `valley`   bigint             NOT NULL,
    `city`     bigint             NOT NULL,
    `festival` bigint             NOT NULL,
    foreign key (`user_id`) references `user` (`id`)
);

-- `travel_diary` 테이블 생성
CREATE TABLE `travel_diary`
(
    `id`           BIGINT AUTO_INCREMENT PRIMARY KEY,
    `field`        VARCHAR(255)             NULL,
    `title`        VARCHAR(255)             NULL,
    `description`  TEXT                     NULL,
    `created_at`   DATE                     NULL,
    `updated_at`   DATE                     NULL,
    `user_id`      BIGINT                   NOT NULL,
    `for_sale`     ENUM ('sale', 'notsale') NOT NULL,
    `dotori_price` BIGINT                   NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);


-- `hashtag` 테이블 생성
CREATE TABLE `hashtag`
(
    `id`  BIGINT AUTO_INCREMENT PRIMARY KEY,
    `tag` VARCHAR(255) NOT NULL
);

CREATE TABLE `location`
(
    `id`   bigint      NOT NULL AUTO_INCREMENT,
    `name` varchar(30) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `place`
(
    `id`                    bigint         NOT NULL AUTO_INCREMENT,
    `road_address`          varchar(100)   NULL,
    `street_number_address` varchar(100)   NULL,
    `latitude`              decimal(10, 8) NOT NULL,
    `longitude`             decimal(10, 7) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `trip`
(
    `id`                    bigint      NOT NULL AUTO_INCREMENT,
    `location_id`           bigint      NOT NULL,
    `place_id`              bigint      NOT NULL,
    `facility_name`         varchar(50) NOT NULL,
    `facility_introduction` text        NULL,
    `phone_number`          varchar(15) NULL,
    `web_page_url`          text        NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
    FOREIGN KEY (`place_id`) REFERENCES `place` (`id`)
);

-- `post` 테이블 생성
CREATE TABLE `post`
(
    `id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
    `content`    VARCHAR(255) NULL,
    `created_at` DATETIME     NULL default NOW(),
    `user_id`    BIGINT       NOT NULL,
    `trip_id`    BIGINT       NOT NULL,
    `post_image` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    FOREIGN KEY (`trip_id`) REFERENCES trip (`id`)
);

-- `post_hashtag` 테이블 생성
CREATE TABLE `post_hashtag`
(
    `post_id`    BIGINT NOT NULL,
    `hashtag_id` BIGINT NOT NULL,
    PRIMARY KEY (`post_id`, `hashtag_id`),
    FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
    FOREIGN KEY (`hashtag_id`) REFERENCES `hashtag` (`id`)
);

CREATE TABLE post_like
(
    user_id bigint NOT NULL,
    post_id bigint NOT NULL,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE
);

CREATE TABLE post_view
(
    user_id     bigint NOT NULL,
    post_id     bigint NOT NULL,
    views_count INT DEFAULT 1,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE
);

CREATE TABLE `comment`
(
    `id`         bigint   NOT NULL primary key auto_increment,
    `post_id`    bigint   NOT NULL,
    `user_id`    bigint   NOT NULL,
    `content`    TEXT     NULL,
    `created_at` DATETIME NULL default CURRENT_TIMESTAMP,
    FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `purchase`
(
    `id`             bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `traveldiary_id` bigint             NOT NULL,
    `user_id`        bigint             NOT NULL,
    `purchase_at`    Date               NOT NULL,
    `buy_price`      bigint             NOT NULL,
    FOREIGN KEY (`traveldiary_id`) REFERENCES `travel_diary` (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) on delete cascade
);

CREATE TABLE `cash_to_dotori`
(
    `id`              bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `user_id`         bigint             NOT NULL,
    `recharge_at`     Date               NOT NULL,
    `quantity`        bigint             NOT NULL,
    `order_id`        varchar(255)       NOT NULL,
    `order_name`      varchar(255)       NOT NULL,
    `status`          varchar(30)        NOT NULL,
    `provider`        varchar(30)        NOT NULL,
    `amount`          bigint             NOT NULL,
    `discount_amount` bigint             NOT NULL,
    `payment_id`      varchar(255)       NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) on delete cascade
);


CREATE TABLE `questions`
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT       NOT NULL,
    title      VARCHAR(200) NOT NULL,
    category   VARCHAR(50)  NOT NULL,
    body       TEXT         NOT NULL,
    image_url  VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
);

CREATE TABLE `answers`
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_id BIGINT NOT NULL,
    user_id     BIGINT NOT NULL,
    body        TEXT   NOT NULL,
    image_url   VARCHAR(255),
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (question_id) REFERENCES questions (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
);

CREATE TABLE refresh_token
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT       NOT NULL,
    token      VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    expires_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE reset_token
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    token      VARCHAR(255) NOT NULL,
);


insert into user(name, email, password, role, created_at, dotori)
values ("테스트유저1", "ssafy1@naver.com", "1234", "user", "2024-11-16", 23);

insert into user(name, email, password, role, created_at, dotori)
values ("테스트유저2", "ssafy2@naver.com", "1234", "user", "2024-11-16", 23);

insert into user(name, email, password, role, created_at, dotori)
values ("테스트유저3", "ssafy3@naver.com", "1234", "user", "2024-11-16", 23);

insert into user(name, email, password, role, created_at, dotori)
values ("테스트유저4", "ssafy4@naver.com", "1234", "user", "2024-11-16", 23);

insert into user(name, email, password, role, created_at, dotori)
values ("테스트유저5", "ssafy5@naver.com", "1234", "user", "2024-11-16", 23);

insert into travel_graph(user_id, mountain, sea, valley, city, festival)
values (1, 23, 10, 40, 32, 60);

insert into travel_graph(user_id, mountain, sea, valley, city, festival)
values (2, 100, 100, 40, 32, 60);

insert into travel_graph(user_id, mountain, sea, valley, city, festival)
values (3, 23, 66, 40, 80, 60);

insert into travel_graph(user_id, mountain, sea, valley, city, festival)
values (4, 1, 1, 1, 32, 60);

insert into travel_graph(user_id, mountain, sea, valley, city, festival)
values (5, 53, 90, 40, 10, 80);


INSERT INTO questions (user_id, title, category, body, image_url)
VALUES (1, 'Vue.js 질문', 'Frontend', 'Vue.js 관련 질문', NULL),
       (2, 'Node.js 이슈', 'Backend', '오류 해결 방법', NULL);

INSERT INTO answers (question_id, user_id, body, image_url)
VALUES (1, 2, 'Vue CLI 사용해 보세요', NULL),
       (2, 1, 'npm 업데이트 하세요', NULL);



INSERT INTO `location` (`name`)
VALUES ('서울특별시'),
       ('부산광역시'),
       ('대구광역시'),
       ('인천광역시'),
       ('광주광역시'),
       ('대전광역시'),
       ('울산광역시'),
       ('세종특별자치시'),
       ('경기도'),
       ('강원도'),
       ('충청북도'),
       ('충청남도'),
       ('전라북도'),
       ('전라남도'),
       ('경상북도'),
       ('경상남도'),
       ('제주특별자치도')
;

INSERT INTO `place` (`road_address`, `street_number_address`, `latitude`, `longitude`)
VALUES ('서울특별시 성동구 서울숲2길 30', '서울 성동구 서울숲 2길 30', 37.544579, 127.037218),
       ('부산광역시 해운대구 해운대로 570', '부산 해운대구 해운대로 570', 35.158698, 129.160384),
       ('대구광역시 수성구 신매로 82', '대구 수성구 신매로 82', 35.853171, 128.570041),
       ('인천광역시 중구 연안부두로 33', '인천 중구 연안부두로 33', 37.451518, 126.600524),
       ('광주광역시 북구 비엔날레로 111', '광주 북구 비엔날레로 111', 35.180590, 126.878490),
       ('대전광역시 유성구 온천로 31', '대전 유성구 온천로 31', 36.354574, 127.381653),
       ('울산광역시 남구 돋질로 170', '울산 남구 돋질로 170', 35.537077, 129.311360),
       ('세종특별자치시 도움3로 27', '세종 도움3로 27', 36.480925, 127.289079),
       ('경기도 가평군 가평읍 북한강변로 1037', '경기 가평군 가평읍 북한강변로 1037', 37.832540, 127.513661),
       ('강원도 춘천시 스포츠타운길 245', '강원 춘천시 스포츠타운길 245', 37.899265, 127.723082);

INSERT INTO `trip` (`location_id`, `place_id`, `facility_name`, `facility_introduction`, `phone_number`, `web_page_url`)
VALUES (1, 1, '서울 숲 캠핑장', '서울 도심 속에서 숲과 함께 즐길 수 있는 힐링 캠핑장입니다.', '010-1111-1111',
        'https://cdn.pixabay.com/photo/2017/08/20/01/33/jeju-2660438_1280.jpg'),
       (2, 2, '부산 바닷가 캠핑장', '부산 해변과 가까워 여름철 인기 있는 캠핑 장소입니다.', '010-2222-2222',
        'https://cdn.pixabay.com/photo/2018/07/05/11/31/lake-3518141_1280.jpg'),
       (3, 3, '대구 힐링 캠핑장', '자연을 만끽할 수 있는 한적한 캠핑장입니다.', '010-3333-3333',
        'https://images.unsplash.com/photo-1699155759120-ce739f8a365a?q=80&w=2871&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
       (4, 4, '인천 갯벌 캠핑장', '갯벌 체험과 캠핑을 동시에 즐길 수 있는 곳입니다.', '010-4444-4444',
        'https://plus.unsplash.com/premium_photo-1663954865235-48f409396fe3?q=80&w=2942&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
       (5, 5, '광주 수목원 캠핑장', '수목원과 함께하는 친환경 캠핑장입니다.', '010-5555-5555',
        'https://images.unsplash.com/photo-1565149394348-c56457810899?q=80&w=2874&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
       (6, 6, '대전 별빛 캠핑장', '밤하늘 별을 보며 힐링할 수 있는 캠핑장입니다.', '010-6666-6666',
        'https://images.unsplash.com/photo-1673179559763-eefd6251e662?q=80&w=2874&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
       (7, 7, '울산 계곡 캠핑장', '맑은 계곡과 함께하는 시원한 캠핑장입니다.', '010-7777-7777',
        'https://images.unsplash.com/photo-1441084473581-e5aa160f8e0f?q=80&w=1918&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
       (8, 8, '세종 자연 캠핑장', '자연과 하나 되어 힐링할 수 있는 캠핑장입니다.', '010-8888-8888',
        'https://images.unsplash.com/photo-1651375562197-fddb0348a55a?q=80&w=2874&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
       (9, 9, '경기 호수 캠핑장', '호수 옆에서 캠핑을 즐길 수 있는 낭만적인 장소입니다.', '010-9999-9999',
        'https://cdn.pixabay.com/photo/2018/09/09/08/24/sunset-3664096_1280.jpg'),
       (10, 10, '강원 산속 캠핑장', '깊은 산 속에서 자연을 느낄 수 있는 캠핑장입니다.', '010-1010-1010',
        'https://cdn.pixabay.com/photo/2018/04/30/10/24/mountain-3362342_1280.jpg');


-- travel_diary sql query

INSERT INTO `travel_diary` (`field`, `title`, `description`, `created_at`, `updated_at`, `user_id`, `for_sale`,
                            `dotori_price`)
VALUES ('Nature', 'Exploring the Alps', 'A detailed account of my hiking trip in the Alps.', '2024-11-01', '2024-11-01',
        1, 'sale', 500);

INSERT INTO `travel_diary` (`field`, `title`, `description`, `created_at`, `updated_at`, `user_id`, `for_sale`,
                            `dotori_price`)
VALUES ('City', 'Tokyo Adventures', 'A guide to the best ramen shops and hidden gems in Tokyo.', '2024-10-25',
        '2024-11-10', 2, 'notsale', NULL);

INSERT INTO `travel_diary` (`field`, `title`, `description`, `created_at`, `updated_at`, `user_id`, `for_sale`,
                            `dotori_price`)
VALUES ('Beach', 'Maldives Paradise', 'My relaxing vacation in the Maldives with tips for travelers.', '2024-09-15',
        '2024-09-18', 3, 'sale', 1000);

INSERT INTO `travel_diary` (`field`, `title`, `description`, `created_at`, `updated_at`, `user_id`, `for_sale`,
                            `dotori_price`)
VALUES ('Culture', 'Experiencing Bali', 'A dive into Bali\'s culture, including temples and traditions.', '2024-08-05',
        '2024-08-07', 4, 'sale', 750);

INSERT INTO `travel_diary` (`field`, `title`, `description`, `created_at`, `updated_at`, `user_id`, `for_sale`,
                            `dotori_price`)
VALUES ('Adventure', 'Amazon Rainforest Expedition', 'A thrilling adventure through the Amazon rainforest.',
        '2024-07-20', '2024-07-22', 5, 'notsale', NULL);


-- post insert query

INSERT INTO `post` (`content`, `created_at`, `user_id`, `trip_id`, `post_image`)
VALUES ('Exploring the mountains', NOW(), 1, 1, 'image1.jpg'),
       ('Night in the city', NOW(), 2, 2, 'image2.jpg'),
       ('Relaxing by the sea', NOW(), 3, 3, 'image3.jpg'),
       ('Found a hidden waterfall', NOW(), 4, 4, 'image4.jpg'),
       ('Journey through the dunes', NOW(), 5, 5, 'image5.jpg'),
       ('Snowy landscapes and hot cocoa', NOW(), 3, 1, 'image6.jpg'),
       ('Camping in the woods', NOW(), 5, 2, 'image7.jpg'),
       ('Exploring tropical islands', NOW(), 4, 3, 'image8.jpg'),
       ('Visited historical landmarks', NOW(), 3, 4, 'image9.jpg'),
       ('Driving across the country', NOW(), 1, 5, 'image10.jpg');
-- hashtag insert query

INSERT INTO `hashtag` (`tag`)
VALUES ('Travel');

INSERT INTO `hashtag` (`tag`)
VALUES ('Adventure');

INSERT INTO `hashtag` (`tag`)
VALUES ('Nature');

INSERT INTO `hashtag` (`tag`)
VALUES ('Culture');

INSERT INTO `hashtag` (`tag`)
VALUES ('Beach');

-- post hashtag insert query

INSERT INTO `post_hashtag` (`post_id`, `hashtag_id`)
VALUES (1, 1);

INSERT INTO `post_hashtag` (`post_id`, `hashtag_id`)
VALUES (1, 2);

INSERT INTO `post_hashtag` (`post_id`, `hashtag_id`)
VALUES (2, 3);

INSERT INTO `post_hashtag` (`post_id`, `hashtag_id`)
VALUES (3, 3);

INSERT INTO `post_hashtag` (`post_id`, `hashtag_id`)
VALUES (4, 5);

