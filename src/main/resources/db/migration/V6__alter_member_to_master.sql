ALTER TABLE member_to_master
    DROP COLUMN file;

ALTER TABLE member_to_master
    ADD COLUMN enroll_date DATE DEFAULT (CURRENT_DATE) NOT NULL;

ALTER TABLE member_to_master
    ADD COLUMN approval_status VARCHAR(20) DEFAULT 'WAIT' NOT NULL;

CREATE TABLE certificate
(
    `id`        BIGINT AUTO_INCREMENT PRIMARY KEY,
    `enroll_id` BIGINT       NOT NULL,
    `file_name` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`enroll_id`) REFERENCES member_to_master (`id`) ON DELETE CASCADE
);