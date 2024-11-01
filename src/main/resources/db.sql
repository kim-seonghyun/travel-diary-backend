-- 데이터베이스 및 테이블 생성
CREATE DATABASE IF NOT EXISTS tripspring;
USE tripspring;

-- 기존 테이블 삭제
DROP TABLE IF EXISTS `post_hashtag`;
DROP TABLE IF EXISTS `hashtag`;
DROP TABLE IF EXISTS `post`;
DROP TABLE IF EXISTS `travel_diary`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `travel_graph`;

use tripspring;

CREATE TABLE `user` (
    `id`    bigint primary key    NOT NULL auto_increment,
    `name`    varchar(50)    NOT NULL,
    `email`    varchar(100)    NOT NULL,
    `password`    varchar(50)    NOT NULL,
    `role`    varchar(10)    NOT NULL,
    `created_at`    Date    NOT NULL
);


CREATE TABLE `travel_graph` (
    `id`    bigint    primary key NOT NULL auto_increment,
    `user_id` bigint NOT NULL,
    `mountain`    bigint    NOT NULL,
    `sea`    bigint    NOT NULL,
    `valley`    bigint    NOT NULL,
    `city`    bigint    NOT NULL,
    `festival`    bigint    NOT NULL,
    foreign key(`user_id`) references `user`(`id`)
);

select * from travel_graph;

-- `travel_diary` 테이블 생성
CREATE TABLE `travel_diary` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `field` VARCHAR(255) NULL,
    `title` VARCHAR(255) NULL,
    `description` TEXT NULL,
    `created_at` DATE NULL,
    `updated_at` DATE NULL,
    `user_id` BIGINT NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
);

-- `post` 테이블 생성
CREATE TABLE `post` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NULL,
    `content` VARCHAR(255) NULL,
    `created_at` DATE NULL,
    `updated_at` DATE NULL,
    `diary_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) on delete cascade
);

select * from post;

-- `hashtag` 테이블 생성
CREATE TABLE `hashtag` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `tag` VARCHAR(255) NULL
);

-- `post_hashtag` 테이블 생성
CREATE TABLE `post_hashtag` (
    `post_id` BIGINT NOT NULL,
    `hashtag_id` BIGINT NOT NULL,
    PRIMARY KEY (`post_id`, `hashtag_id`),
    FOREIGN KEY (`post_id`) REFERENCES `post`(`id`),
    FOREIGN KEY (`hashtag_id`) REFERENCES `hashtag`(`id`)
);

CREATE TABLE `location` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(30) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `place` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `road_address` varchar(100)  NULL,
    `street_number_address` varchar(100) NULL,
    `latitude` decimal(10,8) NOT NULL,
    `longitude` decimal(10,7) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `camp_trip` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `location_id` bigint NOT NULL,
    `place_id` bigint NOT NULL,
    `facility_name` varchar(50) NOT NULL,
    `facility_introduction` text NULL,
    `phone_number` varchar(15) NULL,
    `web_page_url` text NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`location_id`) REFERENCES `location`(`id`),
    FOREIGN KEY (`place_id`) REFERENCES `place`(`id`) 
);

INSERT INTO `location` (`name`) VALUES
('서울특별시'),
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
VALUES
('서울특별시 성동구 서울숲2길 30', '서울 성동구 서울숲 2길 30', 37.544579, 127.037218),
('부산광역시 해운대구 해운대로 570', '부산 해운대구 해운대로 570', 35.158698, 129.160384),
('대구광역시 수성구 신매로 82', '대구 수성구 신매로 82', 35.853171, 128.570041),
('인천광역시 중구 연안부두로 33', '인천 중구 연안부두로 33', 37.451518, 126.600524),
('광주광역시 북구 비엔날레로 111', '광주 북구 비엔날레로 111', 35.180590, 126.878490),
('대전광역시 유성구 온천로 31', '대전 유성구 온천로 31', 36.354574, 127.381653),
('울산광역시 남구 돋질로 170', '울산 남구 돋질로 170', 35.537077, 129.311360),
('세종특별자치시 도움3로 27', '세종 도움3로 27', 36.480925, 127.289079),
('경기도 가평군 가평읍 북한강변로 1037', '경기 가평군 가평읍 북한강변로 1037', 37.832540, 127.513661),
('강원도 춘천시 스포츠타운길 245', '강원 춘천시 스포츠타운길 245', 37.899265, 127.723082);

INSERT INTO `camp_trip` (`location_id`, `place_id`, `facility_name`, `facility_introduction`, `phone_number`, `web_page_url`)
VALUES
(1, 1, '서울 숲 캠핑장', '서울 도심 속에서 숲과 함께 즐길 수 있는 힐링 캠핑장입니다.', '010-1111-1111', 'http://seoulforestcamp.com'),
(2, 2, '부산 바닷가 캠핑장', '부산 해변과 가까워 여름철 인기 있는 캠핑 장소입니다.', '010-2222-2222', 'http://busanseacamp.com'),
(3, 3, '대구 힐링 캠핑장', '자연을 만끽할 수 있는 한적한 캠핑장입니다.', '010-3333-3333', 'http://daeguhealingcamp.com'),
(4, 4, '인천 갯벌 캠핑장', '갯벌 체험과 캠핑을 동시에 즐길 수 있는 곳입니다.', '010-4444-4444', 'http://incheontidelandcamp.com'),
(5, 5, '광주 수목원 캠핑장', '수목원과 함께하는 친환경 캠핑장입니다.', '010-5555-5555', 'http://gwangjuarboretumcamp.com'),
(6, 6, '대전 별빛 캠핑장', '밤하늘 별을 보며 힐링할 수 있는 캠핑장입니다.', '010-6666-6666', 'http://daejeonstarlightcamp.com'),
(7, 7, '울산 계곡 캠핑장', '맑은 계곡과 함께하는 시원한 캠핑장입니다.', '010-7777-7777', 'http://ulsanvalleycamp.com'),
(8, 8, '세종 자연 캠핑장', '자연과 하나 되어 힐링할 수 있는 캠핑장입니다.', '010-8888-8888', 'http://sejongnaturecamp.com'),
(9, 9, '경기 호수 캠핑장', '호수 옆에서 캠핑을 즐길 수 있는 낭만적인 장소입니다.', '010-9999-9999', 'http://gyeonggilakecamp.com'),
(10, 10, '강원 산속 캠핑장', '깊은 산 속에서 자연을 느낄 수 있는 캠핑장입니다.', '010-1010-1010', 'http://gangwonnaturecamp.com');
