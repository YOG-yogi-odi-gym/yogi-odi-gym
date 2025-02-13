-- 헬스장 데이터 테이블
CREATE TABLE Data_Gym (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 헬스장 번호 (기본키)
    name VARCHAR(255) NOT NULL,  -- 헬스장 이름
    oldAddress VARCHAR(255),  -- 헬스장 옛 주소
    streetAddress VARCHAR(255),  -- 헬스장 도로명 주소
    latitude DECIMAL(10, 7),  -- 위도
    longitude DECIMAL(10, 7),  -- 경도
    phoneNum VARCHAR(50),  -- 연락처
    totalArea INT,  -- 건축 연면적
    trainers INT,  -- 지도자 수
    approvalDate DATE  -- 인허가 일자
);

-- 음식 데이터 테이블
CREATE TABLE Data_Food (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 음식 번호 (기본키)
    name VARCHAR(255) NOT NULL,  -- 음식명
    calories INT NOT NULL,  -- 칼로리 (100g 당)
    protein FLOAT,  -- 단백질 함량
    fat FLOAT,  -- 지방 함량
    carbohydrates FLOAT  -- 탄수화물 함량
);

-- 운동 데이터 테이블
CREATE TABLE Data_Exercise (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 운동 번호 (기본키)
    name VARCHAR(255) NOT NULL,  -- 운동명
    energyConsumption FLOAT NOT NULL  -- 단위체중당 에너지 소비량
);

-- 평균 정보 테이블(그래프에 그려지는 정보)
CREATE TABLE GraphAverage (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    maleExercise FLOAT NOT NULL,
    femaleExercise FLOAT NOT NULL,
    maleCalorie FLOAT NOT NULL,
    femaleCalorie FLOAT NOT NULL
);

-- 회원 테이블
CREATE TABLE Member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 회원 번호 (기본키)
    name VARCHAR(255) NOT NULL,  -- 이름
    email VARCHAR(255) UNIQUE NOT NULL,  -- 아이디 (고유값)
    pwd VARCHAR(255),  -- 비밀번호
    gender VARCHAR(10),  -- 성별
    weight FLOAT,  -- 몸무게
    height FLOAT,  -- 키
    addr VARCHAR(255),  -- 주소
    latitude DECIMAL(10, 7),  -- 위도
    longitude DECIMAL(10, 7),  -- 경도
    joinDate DATE,  -- 가입일자
    dropDate DATE,  -- 탈퇴일자
    authority VARCHAR(50),  -- 권한 (일반, 강사, 관리자)
    profile VARCHAR(255),  -- 프로필 사진
    status VARCHAR(255)  -- 회원 상태(활성화 회원, 탈퇴 회원... (ex 비활성화))
);

-- 강사 신청 테이블
CREATE TABLE MemberToMaster (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 신청 번호 (기본키)
    memberid BIGINT NOT NULL,  -- 회원 번호 (외래키)
    file VARCHAR(255),  -- 신청 파일
    FOREIGN KEY (memberid) REFERENCES Member(id) ON DELETE CASCADE
);

-- 게시판 카테고리 테이블
CREATE TABLE Category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 카테고리 번호 (기본키)
    name VARCHAR(255) NOT NULL,  -- 카테고리 이름
    code VARCHAR(255) NOT NULL  -- 카테고리 분류(강의, 게시판)
);

-- 게시판 테이블
CREATE TABLE Board (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 게시글 번호 (기본키)
    categoryId BIGINT NOT NULL,  -- 카테고리 번호 (외래키)
    title VARCHAR(255) NOT NULL,  -- 제목
    context TEXT NOT NULL,  -- 내용
    createDateTime DATETIME,  -- 작성 시간
    view INT DEFAULT 0,  -- 조회수
    edit BOOLEAN DEFAULT FALSE, -- 편집 여부
    memberId BIGINT NOT NULL,  -- 작성자 (회원번호 참조),
    FOREIGN KEY (categoryId) REFERENCES Category(id) ON DELETE CASCADE,
    FOREIGN KEY (memberId) REFERENCES Member(id) ON DELETE CASCADE
);

-- 댓글 테이블
CREATE TABLE Comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 댓글 번호 (기본키)
    boardId BIGINT NOT NULL,  -- 게시글 번호 (외래키)
    memberId BIGINT NOT NULL,  -- 작성자 (회원번호 참조)
    content TEXT NOT NULL,  -- 댓글 내용
    createDateTime DATETIME,  -- 작성 시간
    edit BOOLEAN DEFAULT FALSE, -- 편집 여부
    FOREIGN KEY (boardId) REFERENCES Board(id) ON DELETE CASCADE,
    FOREIGN KEY (memberId) REFERENCES Member(id) ON DELETE CASCADE
);

-- 캘린더 테이블
-- 캘린더 식단 테이블
CREATE TABLE Calendar_Food (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 식단 번호 (기본키)
    dFoodId BIGINT NOT NULL, -- DataFood 음식 번호
    name VARCHAR(255) NOT NULL,  -- 음식 이름 (음식 데이터 참조)
    hundredGram FLOAT NOT NULL,  -- 섭취량 (100g 단위)
    calories FLOAT NOT NULL,  -- 음식 칼로리
    date DATE NOT NULL,  -- 날짜
    memberId BIGINT NOT NULL,  -- 회원 아이디 (외래키)
    FOREIGN KEY (memberId) REFERENCES Member(id) ON DELETE CASCADE,
    FOREIGN KEY (dFoodId) REFERENCES Data_Food(id) ON DELETE CASCADE
);

-- 캘린더 운동 테이블
CREATE TABLE Calendar_Exercise (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 운동 번호 (기본키)
    dExerciseId BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,  -- 운동명 (운동 데이터 참조)
    time FLOAT NOT NULL,  -- 운동 시간
    calories FLOAT NOT NULL,  -- 운동 칼로리
    date DATE NOT NULL,  -- 날짜
    memberId BIGINT NOT NULL,  -- 회원 아이디 (외래키)
    FOREIGN KEY (memberId) REFERENCES Member(id) ON DELETE CASCADE,
    FOREIGN KEY (dExerciseId) REFERENCES Data_Exercise(id) ON DELETE CASCADE
);

-- 캘린더 메모 테이블
CREATE TABLE Calendar_Memo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 메모 번호 (기본키)
    title VARCHAR(255) NOT NULL,  -- 제목
    context TEXT NOT NULL,  -- 내용
    date DATE NOT NULL,  -- 날짜
    memberId BIGINT NOT NULL,  -- 회원 아이디 (외래키)
    FOREIGN KEY (memberId) REFERENCES Member(id) ON DELETE CASCADE
);

-- 강의 테이블
CREATE TABLE Lesson (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 강의 번호 (기본키)
    title VARCHAR(255) NOT NULL,  -- 강의 제목
    categoryId BIGINT NOT NULL,  -- 카테고리 번호 (외래키)
    day VARCHAR(255) NOT NULL,  -- 강의일
    location VARCHAR(255),  -- 위치
    latitude DECIMAL(10, 7),  -- 위도
    longitude DECIMAL(10, 7),  -- 경도
    detailedLocation VARCHAR(255),  -- 상세 위치
    startTime TIME NOT NULL,  -- 강의 시작 시간
    endTime TIME NOT NULL,  -- 강의 종료 시간
    startDay DATE NOT NULL,  -- 강의 시작 일
    endDay DATE NOT NULL,  -- 강의 종료 일
    description TEXT,  -- 강의 소개
    current INT DEFAULT 0,  -- 현재 수강 인원
    max INT NOT NULL,  -- 강의 정원 (최대 인원)
    masterId BIGINT NOT NULL,  -- 강사 아이디 (회원 참조)
    createDateTime DATETIME,  -- 생성 날짜
    FOREIGN KEY (categoryId) REFERENCES Category(id) ON DELETE CASCADE,
    FOREIGN KEY (masterId) REFERENCES Member(id) ON DELETE CASCADE
);

-- 수강 테이블
CREATE TABLE Lesson_Enrollment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 수강 번호 (기본키)
    lessonId BIGINT NOT NULL,  -- 강의 번호 (외래키)
    memberId BIGINT NOT NULL,  -- 수강생 아이디 (회원 참조)
    unreadMessage INT DEFAULT 0,  -- 읽지 않은 채팅 수
    FOREIGN KEY (lessonId) REFERENCES Lesson(id) ON DELETE CASCADE,
    FOREIGN KEY (memberId) REFERENCES Member(id) ON DELETE CASCADE
);

-- 채팅 기록 테이블
CREATE TABLE Lesson_Chat (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 채팅 번호 (기본키)
    lessonId BIGINT NOT NULL,  -- 강의 번호 (외래키)
    memberId BIGINT NOT NULL,  -- 회원 아이디 (외래키)
    chatMsg TEXT NOT NULL,  -- 채팅 내용
    chatTime DATETIME,  -- 채팅 시간
    FOREIGN KEY (lessonId) REFERENCES Lesson(id) ON DELETE CASCADE,
    FOREIGN KEY (memberId) REFERENCES Member(id) ON DELETE CASCADE
);
