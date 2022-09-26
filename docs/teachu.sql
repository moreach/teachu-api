-- database
DROP DATABASE IF EXISTS teachu;
CREATE DATABASE teachu;
USE teachu;
-- user
CREATE USER IF NOT EXISTS 'dev'@'%' IDENTIFIED WITH caching_sha2_password BY 'dev';
GRANT ALL PRIVILEGES ON *.* TO 'dev'@'%' WITH GRANT OPTION;

-- tables
DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id               BINARY(16) PRIMARY KEY,
    email            VARCHAR(255),
    password         VARCHAR(255),
    role             VARCHAR(255),
    first_name       VARCHAR(255),
    last_name        VARCHAR(255),
    birthday         DATE,
    sex              VARCHAR(255),
    language         VARCHAR(255),
    dark_theme       BOOLEAN,
    city             VARCHAR(255),
    postal_code      VARCHAR(255),
    street           VARCHAR(255),
    phone            VARCHAR(255),
    img              BINARY(16),
    last_login       TIMESTAMP,
    creation_date    DATE,
    termination_date DATE,
    notes            VARCHAR(4096),
    active           BOOLEAN
);

DROP TABLE IF EXISTS token;
CREATE TABLE token
(
    user_id         BINARY(16),
    access          VARCHAR(255) PRIMARY KEY,
    refresh         VARCHAR(255),
    access_expires  TIMESTAMP,
    refresh_expires TIMESTAMP
);

DROP TABLE IF EXISTS school_class;
CREATE TABLE school_class
(
    id         BINARY(16) PRIMARY KEY,
    name       VARCHAR(255),
    teacher_id BINARY(16)
);

DROP TABLE IF EXISTS school_class_user;
CREATE TABLE school_class_user
(
    school_class_id BINARY(16),
    user_id         BINARY(16)
);

DROP TABLE IF EXISTS subject;
CREATE TABLE subject
(
    id     BINARY(16) PRIMARY KEY,
    name   VARCHAR(255),
    weight FLOAT
);

DROP TABLE IF EXISTS school_class_subject;
CREATE TABLE school_class_subject
(
    id              BINARY(16) PRIMARY KEY,
    school_class_id BINARY(16),
    subject_id      BINARY(16),
    teacher_id      BINARY(16),
    start_date      DATE,
    end_date        DATE,
    note            VARCHAR(4096)
);

DROP TABLE IF EXISTS timetable;
CREATE TABLE timetable
(
    id         BINARY(16) PRIMARY KEY,
    start_time TIME,
    end_time   TIME
);

DROP TABLE IF EXISTS lesson;
CREATE TABLE lesson
(
    id                      BINARY(16) PRIMARY KEY,
    school_class_subject_id BINARY(16),
    timetable_id            BINARY(16),
    weekday                 VARCHAR(255),
    room_id                 BINARY(16)
);

DROP TABLE IF EXISTS room;
CREATE TABLE room
(
    id   BINARY(16) PRIMARY KEY,
    name VARCHAR(255),
    note VARCHAR(4096)
);

DROP TABLE IF EXISTS exam;
CREATE TABLE exam
(
    id                      BINARY(16) PRIMARY KEY,
    school_class_subject_id BINARY(16),
    name                    VARCHAR(100),
    description             VARCHAR(250),
    weight                  FLOAT,
    date                    DATE,
    view_date               DATE
);

DROP TABLE IF EXISTS grade;
CREATE TABLE grade
(
    id         BINARY(16) PRIMARY KEY,
    student_id BINARY(16),
    exam_id    BINARY(16),
    mark       FLOAT,
    note       VARCHAR(4096)
);

DROP TABLE IF EXISTS user_event;
CREATE TABLE user_event
(
    id               BINARY(16) PRIMARY KEY,
    user_id          BINARY(16),
    date_from        TIMESTAMP,
    date_to          TIMESTAMP,
    title            VARCHAR(255),
    description      VARCHAR(4096),
    user_event_state VARCHAR(255),
    user_event_type  VARCHAR(255)
);

DROP TABLE IF EXISTS lesson_event;
CREATE TABLE lesson_event
(
    id                BINARY(16) PRIMARY KEY,
    lesson_id         BINARY(16),
    date              DATE,
    title             VARCHAR(255),
    description       VARCHAR(4096),
    lesson_event_type VARCHAR(255)
);

DROP TABLE IF EXISTS school_class_event;
CREATE TABLE school_class_event
(
    id                      BINARY(16) PRIMARY KEY,
    school_class_id         BINARY(16),
    date_from               DATE,
    date_to                 DATE,
    title                   VARCHAR(255),
    description             VARCHAR(4096),
    school_class_event_type VARCHAR(255)
);

DROP TABLE IF EXISTS school_event;
CREATE TABLE school_event
(
    id                BINARY(16) PRIMARY KEY,
    date_from         DATE,
    date_to           DATE,
    title             VARCHAR(255),
    description       VARCHAR(4096),
    school_event_type VARCHAR(255)
);

DROP TABLE IF EXISTS parent_student;
CREATE TABLE parent_student
(
    parent_id  BINARY(16),
    student_id BINARY(16)
);

DROP TABLE IF EXISTS chat;
CREATE TABLE chat
(
    id          BINARY(16) PRIMARY KEY,
    title       VARCHAR(255),
    description VARCHAR(255),
    creator_id  BINARY(16)
);

DROP TABLE IF EXISTS chat_message;
CREATE TABLE chat_message
(
    id         BINARY(16) PRIMARY KEY,
    chat_id    BINARY(16),
    user_id    BINARY(16),
    message    VARCHAR(4096),
    img        BINARY(16),
    timestamp  TIMESTAMP,
    chat_state VARCHAR(255)
);

DROP TABLE IF EXISTS chat_user;
CREATE TABLE chat_user
(
    chat_id BINARY(16),
    user_id BINARY(16)
);

DROP TABLE IF EXISTS school_info;
CREATE TABLE school_info
(
    id        BINARY(16) PRIMARY KEY,
    title     VARCHAR(255),
    message   VARCHAR(4096),
    date      DATE,
    img       BINARY(16),
    user_id   BINARY(16),
    important BOOLEAN,
    pinned    BOOLEAN,
    active    BOOLEAN
);

DROP TABLE IF EXISTS semester;
CREATE TABLE semester
(
    id        BINARY(16) PRIMARY KEY,
    name      VARCHAR(250),
    date_from TIMESTAMP,
    date_to   TIMESTAMP
);

DROP TABLE IF EXISTS school_class_semester;
CREATE TABLE school_class_semester
(
    school_class_id BINARY(16),
    semester_id     BINARY(16)
);

DROP TABLE IF EXISTS school_config;
CREATE TABLE school_config
(
    name      VARCHAR(250),
    value     VARCHAR(250),
    code_type VARCHAR(255)
);

DROP TABLE IF EXISTS image;
CREATE TABLE image
(
    id    BINARY(16) PRIMARY KEY,
    image MEDIUMBLOB
);
