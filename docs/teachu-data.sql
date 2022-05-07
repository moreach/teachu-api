-- database
USE teachu;

INSERT INTO user(id, email, password, role, first_name, last_name, birthday, sex, language, dark_theme, city,
                 postal_code, street, phone, profile_img, notes, last_login, creation_date, termination_date, active)
VALUES (UUID_TO_BIN(UUID()), 'student@test.ch', '$2a$10$nNMD8fkGEfKSttXD.jwPP.cE9oeTUvzeWnMM8DZD0rEVRV2N2avOm',
        'student',
        'student_firstname', 'student_lastname', CURRENT_TIME, 'other', 'english', false, 'Schönenwerd', '5012',
        'Belchenstrasse 16', '696 969 69 69', 'micha/setzt/de/fileserver/uf.png', 'no noted', CURRENT_TIMESTAMP,
        CURRENT_DATE, CURRENT_DATE + INTERVAL 1 YEAR, TRUE),
       (UUID_TO_BIN(UUID()), 'teacher@test.ch', '$2a$10$nNMD8fkGEfKSttXD.jwPP.cE9oeTUvzeWnMM8DZD0rEVRV2N2avOm',
        'teacher',
        'teacher_firstname', 'teacher_lastname', CURRENT_TIME, 'other', 'english', false, 'Schönenwerd', '5012',
        'Belchenstrasse 16', '696 969 69 69', 'micha/setzt/de/fileserver/uf.png', 'no noted', CURRENT_TIMESTAMP,
        CURRENT_DATE, CURRENT_DATE + INTERVAL 1 YEAR, TRUE),
       (UUID_TO_BIN(UUID()), 'parent@test.ch', '$2a$10$nNMD8fkGEfKSttXD.jwPP.cE9oeTUvzeWnMM8DZD0rEVRV2N2avOm',
        'parent',
        'parent_firstname', 'parent_lastname', CURRENT_TIME, 'other', 'english', false, 'Schönenwerd', '5012',
        'Belchenstrasse 16', '696 969 69 69', 'micha/setzt/de/fileserver/uf.png', 'no noted', CURRENT_TIMESTAMP,
        CURRENT_DATE, CURRENT_DATE + INTERVAL 1 YEAR, TRUE);

INSERT INTO school_class(id, name, teacher_id)
VALUES (UUID_TO_BIN(UUID()), 'IN19a', (SELECT id FROM user WHERE email = 'teacher@test.ch'));

INSERT INTO school_class_user(school_class_id, user_id)
VALUES ((SELECT id FROM school_class WHERE name = 'IN19a'), (SELECT id FROM user WHERE email = 'student@test.ch'));

INSERT INTO subject(id, name, weight)
VALUES (UUID_TO_BIN(UUID()), 'Informatik', 1.0);

INSERT INTO school_class_subject(id, school_class_id, teacher_id, subject_id, note, start_date, end_date, `interval`,
                                 active)
VALUES (UUID_TO_BIN(UUID()), (SELECT id FROM school_class WHERE name = 'IN19a'),
        (SELECT id FROM user WHERE email = 'teacher@test.ch'), (SELECT id FROM subject WHERE name = 'Informatik'),
        'no notes', CURRENT_DATE, CURRENT_DATE + INTERVAL 1 YEAR, 'weekly', TRUE);

INSERT INTO timetable(id, number, start_time, end_time)
VALUES (UUID_TO_BIN(UUID()), 1, CURRENT_TIME, CURRENT_TIME + INTERVAL 1 HOUR);


INSERT INTO room(id, name, note)
VALUES (UUID_TO_BIN(UUID()), 'B427', 'no note');

INSERT INTO lesson(id, school_class_subject_id, start_time, end_time, weekday, room)
VALUES (UUID_TO_BIN(UUID()), (SELECT id FROM school_class WHERE name = 'IN19a'), CURRENT_TIME,
        CURRENT_TIME + INTERVAL 1 HOUR, 'monday', (SELECT id FROM room WHERE name = 'B427'));

INSERT INTO exam(id, school_class_subject_id, name, description, weight, date, view_date)
VALUES (UUID_TO_BIN(UUID()),
        (SELECT id
         FROM school_class_subject
         WHERE school_class_id = (SELECT id FROM school_class WHERE name = 'IN19a')),
        'Prüfung IDPA', 'Prüfungs beschreibung', 1.0, CURRENT_DATE, CURRENT_DATE);

INSERT INTO grade(id, student_id, mark, note)
VALUES (UUID_TO_BIN(UUID()), (SELECT id FROM user WHERE email = 'student@test.ch'), 6.0, 'no note');

INSERT INTO user_event(id, user_id, lesson_id, description, date, user_event_state)
VALUES (UUID_TO_BIN(UUID()), (SELECT id FROM user WHERE email = 'student@test.ch'),
        (SELECT id FROM lesson WHERE weekday = 'monday'), 'Absenz oder Ferienantrag', CURRENT_DATE, 'pending');

INSERT INTO lesson_event(id, lesson_id, title, description, date, isTest)
VALUES (UUID_TO_BIN(UUID()), (SELECT id FROM lesson WHERE weekday = 'monday'), 'Prüfung', 'Mathematik fuck me daddy',
        CURRENT_DATE, TRUE);

INSERT INTO school_class_event(id, school_class_id, title, description, date, noSchool)
VALUES (UUID_TO_BIN(UUID()), (SELECT id FROM school_class WHERE name = 'IN19a'), 'Schulreise',
        'Züri HB isch wunderschön', CURRENT_DATE, TRUE);

INSERT INTO school_event(id, title, description, date_from, date_to, noSchool)
VALUES (UUID_TO_BIN(UUID()), 'Ferien', 'Mer Lehrer hend eus die 13 Wuche Ferie scho verdient', CURRENT_DATE,
        CURRENT_DATE + INTERVAL 14 DAY, TRUE);

INSERT INTO parent_student(parent_id, student_id)
VALUES ((SELECT id FROM user WHERE email = 'parent@test.ch'), (SELECT id FROM user WHERE email = 'student@test.ch'));

INSERT INTO chat(id, title, description, creator_id)
VALUES (UUID_TO_BIN(UUID()), 'Schüler Lehrer Chatroom', 'Wer schribt cho ned gern sim lehrer',
        (SELECT id FROM user WHERE email = 'teacher@test.ch'));

INSERT INTO chat_user(chat_id, user_id)
VALUES ((SELECT id FROM chat WHERE title = 'Schüler Lehrer Chatroom'),
        (SELECT id FROM user WHERE email = 'teacher@test.ch')),
       ((SELECT id FROM chat WHERE title = 'Schüler Lehrer Chatroom'),
        (SELECT id FROM user WHERE email = 'student@test.ch'));

INSERT INTO school_info(id, title, message, date, img, user_id, school_info_state, important, pinned)
VALUES (UUID_TO_BIN(UUID()), 'Corona Infos', 'Neuste Informationen zur Corona Pandemie', CURRENT_DATE,
        'micha/setzt/de/fileserver/uf.png', (SELECT id FROM user WHERE email = 'teacher@test.ch'), 'public', TRUE,
        TRUE);
