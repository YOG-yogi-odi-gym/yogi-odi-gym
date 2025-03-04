ALTER TABLE lesson_enrollment
    DROP COLUMN unread_message;

DROP TABLE IF EXISTS chat_read_status;

ALTER TABLE chat_participant
    ADD COLUMN last_read_message_id BIGINT;

ALTER TABLE chat_participant
    ADD CONSTRAINT uq_member_chat_room UNIQUE (member_id, chat_room_id);
