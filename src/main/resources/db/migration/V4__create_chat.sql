CREATE TABLE chat_room
(
    `id`            BIGINT AUTO_INCREMENT PRIMARY KEY,
    `room_id`       VARCHAR(255),
    `is_group_chat` BOOL,
    `lesson_id`     BIGINT,
    `created_date`  DATETIME,
    `modified_date` DATETIME,
    FOREIGN KEY (lesson_id) REFERENCES lesson (id)
);

CREATE TABLE chat_participant
(
    `id`            BIGINT AUTO_INCREMENT PRIMARY KEY,
    `member_id`     BIGINT,
    `chat_room_id`  BIGINT,
    `created_date`  DATETIME,
    `modified_date` DATETIME,
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (chat_room_id) REFERENCES chat_room (id)
);

CREATE TABLE chat_message
(
    `id`            BIGINT AUTO_INCREMENT PRIMARY KEY,
    `member_id`     BIGINT,
    `chat_room_id`  BIGINT,
    `created_date`  DATETIME,
    `modified_date` DATETIME,
    `content`       VARCHAR(255),
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (chat_room_id) REFERENCES chat_room (id)
);

CREATE TABLE chat_read_status
(
    `id`              BIGINT AUTO_INCREMENT PRIMARY KEY,
    `member_id`       BIGINT,
    `chat_message_id` BIGINT,
    `created_date`    DATETIME,
    `modified_date`   DATETIME,
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (chat_message_id) REFERENCES chat_message (id)
);

ALTER TABLE lesson DROP COLUMN `day`;

ALTER TABLE lesson ADD COLUMN days INT;

ALTER TABLE lesson
    ADD COLUMN chat_room_id BIGINT NOT NULL;

ALTER TABLE lesson
    ADD CONSTRAINT fk_lesson_chat_room FOREIGN KEY (chat_room_id) REFERENCES chat_room(id);