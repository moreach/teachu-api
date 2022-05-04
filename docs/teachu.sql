-- database
DROP DATABASE IF EXISTS teachu;
CREATE DATABASE teachu;
USE teachu;

-- tables
DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id               BINARY(16) PRIMARY KEY,
    email            VARCHAR(100),
    password         VARCHAR(100),
    role             VARCHAR(100),
    first_name       VARCHAR(100),
    last_name        VARCHAR(100),
    birthday         DATE,
    sex              VARCHAR(100),
    language         VARCHAR(100),
    dark_theme       BOOLEAN,
    city             VARCHAR(100),
    postal_code      VARCHAR(100),
    street           VARCHAR(100),
    phone            VARCHAR(100),
    profile_img      VARCHAR(100),
    notes            VARCHAR(1000),
    last_login       TIMESTAMP,
    creation_date    DATE,
    termination_date DATE,
    active           BOOLEAN
);

DROP TABLE IF EXISTS token;
CREATE TABLE token
(
    user_id BINARY(16),
    access VARCHAR(100),
    refresh VARCHAR(100),
    expires TIMESTAMP
)

DROP TABLE IF EXISTS class;
CREATE TABLE class
(
    id      BINARY(16) PRIMARY KEY,
    name    VARCHAR(100),
    teacher_id BINARY(16)
);

DROP TABLE IF EXISTS class_user;
CREATE TABLE class_user
(
    class_id BINARY(16),
    user_id  BINARY(16)
);

DROP TABLE IF EXISTS subject;
CREATE TABLE subject
(
    id       BINARY(16) PRIMARY KEY,
    name VARCHAR(100),
    weight   FLOAT
);

DROP TABLE IF EXISTS class_subject;
CREATE TABLE class_subject
(
    id       BINARY(16) PRIMARY KEY,
    class_id BINARY(16),
    teacher_id BINARY(16),
    subject_id     BINARY(16),
    note     VARCHAR(100),
    start_date DATE,
    end_date DATE,
    interval VARCHAR(100),
    active BOOLEAN
);

DROP TABLE IF EXISTS timetable;
CREATE TABLE timetable
(
    id         BINARY(16) PRIMARY KEY,
    number     int(1),
    start_time TIME,
    end_time   TIME
);

DROP TABLE IF EXISTS lesson;
CREATE TABLE lesson
(
    id         BINARY(16) PRIMARY KEY,
    class_subject_id BINARY(16),
    start_time TIME,
    end_time   TIME,
    weekday    VARCHAR(100),
    room       BINARY(16)
);

DROP TABLE IF EXISTS room;
CREATE TABLE room
(
    id         BINARY(16) PRIMARY KEY,
    name    VARCHAR(100),
    note       VARCHAR(1000)
);

DROP TABLE IF EXISTS exam;
CREATE TABLE exam
(
    id          BINARY(16) PRIMARY KEY,
    user_id     BINARY(16),
    class_subject_id  BINARY(16),
    name        VARCHAR(100),
    description VARCHAR(250),
    weight      FLOAT,
    date        DATE,
    view_date   DATE
);

DROP TABLE IF EXISTS grade;
CREATE TABLE grade
(
    id          BINARY(16) PRIMARY KEY,
    user_id     BINARY(16),
    mark        FLOAT,
    note        VARCHAR(1000)
);

DROP TABLE IF EXISTS user_event;
CREATE TABLE user_event
(
    id BINARY(16) PRIMARY KEY,
    user_id BINARY(16),
    lesson_id BINARY(16),
    description VARCHAR(1000),
    date DATE,
    state VARCHAR(100)
);

DROP TABLE IF EXISTS lesson_event;
CREATE TABLE lesson_event
(
    id BINARY(16) PRIMARY KEY,
    lesson_id BINARY(16),
    title VARCHAR(100),
    description VARCHAR(1000),
    date DATE,
    isTest BOOLEAN
);

DROP TABLE IF EXISTS class_event;
CREATE TABLE class_event
(
    id BINARY(16) PRIMARY KEY,
    class_id BINARY(16),
    title VARCHAR(100),
    description VARCHAR(1000),
    date DATE,
    noSchool BOOLEAN
);

DROP TABLE IF EXISTS school_event;
CREATE TABLE school_event
(
    id BINARY(16) PRIMARY KEY,
    title VARCHAR(100),
    description VARCHAR(1000),
    date_from DATE,
    date_to DATE,
    noSchool BOOLEAN
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
    id BINARY(16) PRIMARY KEY,
    title VARCHAR(100),
    description VARCHAR(250),
    creator_id BINARY(16)
);

DROP TABLE IF EXISTS chat_message;
CREATE TABLE chat_message
(
    id BINARY(16) PRIMARY KEY,
    chat_id BINARY(16),
    message VARCHAR(1000),
    user_id BINARY(16),
    timestamp TIMESTAMP,
    state VARCHAR(100)
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
    id BINARY(16) PRIMARY KEY,
    title VARCHAR(100),
    message VARCHAR(1000),
    date DATE,
    img VARCHAR(100),
    user_id BINARY(16),
    state VARCHAR(100),
    important BOOLEAN,
    pinned BOOLEAN
);

DROP TABLE IF EXISTS log;
CREATE TABLE log
(
    id        BINARY(16) PRIMARY KEY,
    message   VARCHAR(250),
    origin    VARCHAR(100),
    level     VARCHAR(100),
    timestamp TIMESTAMP
);

-- data