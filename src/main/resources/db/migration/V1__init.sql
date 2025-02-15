CREATE TABLE data_gym (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    old_address VARCHAR(255),
    street_address VARCHAR(255),
    latitude FLOAT,
    longitude FLOAT,
    phone_num VARCHAR(50),
    total_area INT,
    trainers INT,
    approval_date DATE
);

CREATE TABLE data_food (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    calories INT NOT NULL,
    protein FLOAT,
    fat FLOAT,
    carbohydrates FLOAT
);

CREATE TABLE data_exercise (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    energy_consumption FLOAT NOT NULL
);

CREATE TABLE graph_average (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    male_exercise FLOAT NOT NULL,
    female_exercise FLOAT NOT NULL,
    male_calorie FLOAT NOT NULL,
    female_calorie FLOAT NOT NULL
);

CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    pwd VARCHAR(255),
    gender VARCHAR(10),
    weight FLOAT,
    height FLOAT,
    addr VARCHAR(255),
    latitude float,
    longitude float,
    join_date DATE,
    drop_date DATE,
    authority VARCHAR(50),
    profile VARCHAR(255),
    status VARCHAR(255)
);

CREATE TABLE member_to_master (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    file VARCHAR(255),
    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE
);

CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(255) NOT NULL
);

CREATE TABLE board (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    context TEXT NOT NULL,
    create_date_time DATETIME,
    view INT DEFAULT 0,
    edit BOOLEAN DEFAULT FALSE,
    member_id BIGINT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE
);

CREATE TABLE comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    board_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    create_date_time DATETIME,
    edit BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (board_id) REFERENCES board(id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE
);

CREATE TABLE calendar_food (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    d_food_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    hundred_gram FLOAT NOT NULL,
    calories FLOAT NOT NULL,
    date DATE NOT NULL,
    member_id BIGINT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE,
    FOREIGN KEY (d_food_id) REFERENCES data_food(id) ON DELETE CASCADE
);

CREATE TABLE calendar_exercise (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    d_exercise_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    time FLOAT NOT NULL,
    calories FLOAT NOT NULL,
    date DATE NOT NULL,
    member_id BIGINT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE,
    FOREIGN KEY (d_exercise_id) REFERENCES data_exercise(id) ON DELETE CASCADE
);

CREATE TABLE calendar_memo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    context TEXT NOT NULL,
    date DATE NOT NULL,
    member_id BIGINT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE
);

CREATE TABLE lesson (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    category_id BIGINT NOT NULL,
    day VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    latitude float,
    longitude float,
    detailed_location VARCHAR(255),
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    start_day DATE NOT NULL,
    end_day DATE NOT NULL,
    description TEXT,
    current INT DEFAULT 0,
    max INT NOT NULL,
    master_id BIGINT NOT NULL,
    create_date_time DATETIME,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE,
    FOREIGN KEY (master_id) REFERENCES member(id) ON DELETE CASCADE
);

CREATE TABLE lesson_enrollment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lesson_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    unread_message INT DEFAULT 0,
    FOREIGN KEY (lesson_id) REFERENCES lesson(id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE
);

CREATE TABLE lesson_chat (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lesson_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    chat_msg TEXT NOT NULL,
    chat_time DATETIME,
    FOREIGN KEY (lesson_id) REFERENCES lesson(id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE
);
