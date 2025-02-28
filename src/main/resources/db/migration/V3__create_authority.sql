ALTER TABLE member
    DROP COLUMN authority;

CREATE TABLE authority(
    `id`        BIGINT AUTO_INCREMENT PRIMARY KEY,
    `member_id` BIGINT      NULL,
    `role`      VARCHAR(50) NULL,
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE
);