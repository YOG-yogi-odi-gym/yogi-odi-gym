ALTER TABLE lesson
    CHANGE day days INT NOT NULL;

ALTER TABLE lesson_enrollment
    DROP COLUMN unread_message;