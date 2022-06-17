-- database
USE teachu;

INSERT INTO user(id, email, password, role, first_name, last_name, birthday, sex, language, dark_theme, city,
                 postal_code, street, phone, notes, last_login, creation_date, termination_date, active)
VALUES (UUID_TO_BIN(UUID()), 'student@test.ch', '$2a$10$nNMD8fkGEfKSttXD.jwPP.cE9oeTUvzeWnMM8DZD0rEVRV2N2avOm',
        'student',
        'student_firstname', 'student_lastname', CURRENT_TIME, 'other', 'english', false, 'Schönenwerd', '5012',
        'Belchenstrasse 16', '696 969 69 69', 'no noted', CURRENT_TIMESTAMP,
        CURRENT_DATE, CURRENT_DATE + INTERVAL 1 YEAR, TRUE),
       (UUID_TO_BIN(UUID()), 'teacher@test.ch', '$2a$10$nNMD8fkGEfKSttXD.jwPP.cE9oeTUvzeWnMM8DZD0rEVRV2N2avOm',
        'teacher',
        'teacher_firstname', 'teacher_lastname', CURRENT_TIME, 'other', 'english', false, 'Schönenwerd', '5012',
        'Belchenstrasse 16', '696 969 69 69', 'no noted', CURRENT_TIMESTAMP,
        CURRENT_DATE, CURRENT_DATE + INTERVAL 1 YEAR, TRUE),
       (UUID_TO_BIN(UUID()), 'parent@test.ch', '$2a$10$nNMD8fkGEfKSttXD.jwPP.cE9oeTUvzeWnMM8DZD0rEVRV2N2avOm',
        'parent',
        'parent_firstname', 'parent_lastname', CURRENT_TIME, 'other', 'english', false, 'Schönenwerd', '5012',
        'Belchenstrasse 16', '696 969 69 69', 'no noted', CURRENT_TIMESTAMP,
        CURRENT_DATE, CURRENT_DATE + INTERVAL 1 YEAR, TRUE),
        (UUID_TO_BIN(UUID()), 'student2@test.ch', '$2a$10$nNMD8fkGEfKSttXD.jwPP.cE9oeTUvzeWnMM8DZD0rEVRV2N2avOm',
        'student',
        'student2_firstname', 'student2_lastname', CURRENT_TIME, 'other', 'english', false, 'Schönenwerd', '5012',
        'Belchenstrasse 16', '696 969 69 69', 'no noted', CURRENT_TIMESTAMP,
        CURRENT_DATE, CURRENT_DATE + INTERVAL 1 YEAR, TRUE),
        (UUID_TO_BIN(UUID()), 'parent2@test.ch', '$2a$10$nNMD8fkGEfKSttXD.jwPP.cE9oeTUvzeWnMM8DZD0rEVRV2N2avOm',
        'parent',
        'parent2_firstname', 'parent2_lastname', CURRENT_TIME, 'other', 'english', false, 'Schönenwerd', '5012',
        'Belchenstrasse 16', '696 969 69 69', 'no noted', CURRENT_TIMESTAMP,
        CURRENT_DATE, CURRENT_DATE + INTERVAL 1 YEAR, TRUE),
        (UUID_TO_BIN(UUID()), 'teacher2@test.ch', '$2a$10$nNMD8fkGEfKSttXD.jwPP.cE9oeTUvzeWnMM8DZD0rEVRV2N2avOm',
        'teacher',
        'teacher2_firstname', 'teacher2_lastname', CURRENT_TIME, 'other', 'english', false, 'Schönenwerd', '5012',
        'Belchenstrasse 16', '696 969 69 69', 'no noted', CURRENT_TIMESTAMP,
        CURRENT_DATE, CURRENT_DATE + INTERVAL 1 YEAR, TRUE);

INSERT INTO school_class(id, name, teacher_id)
VALUES (UUID_TO_BIN(UUID()), 'IN19a', (SELECT id FROM user WHERE email = 'teacher@test.ch')),
       (UUID_TO_BIN(UUID()), 'BM19c', (SELECT id FROM user WHERE email = 'teacher2@test.ch'));

INSERT INTO school_class_user(school_class_id, user_id)
VALUES ((SELECT id FROM school_class WHERE name = 'IN19a'), (SELECT id FROM user WHERE email = 'student@test.ch')),
((SELECT id FROM school_class WHERE name = 'IN19a'), (SELECT id FROM user WHERE email = 'student2@test.ch')),
((SELECT id FROM school_class WHERE name = 'BM19c'), (SELECT id FROM user WHERE email = 'student@test.ch'));

INSERT INTO subject(id, name, weight)
VALUES (UUID_TO_BIN(UUID()), 'Informatik', 1.0),
(UUID_TO_BIN(UUID()), 'Mathe', 2.0);

INSERT INTO school_class_subject(id, school_class_id, teacher_id, subject_id, note, start_date, end_date, `interval`,
                                 active)
VALUES (UUID_TO_BIN(UUID()), (SELECT id FROM school_class WHERE name = 'IN19a'),
        (SELECT id FROM user WHERE email = 'teacher@test.ch'), (SELECT id FROM subject WHERE name = 'Informatik'),
        'no notes', CURRENT_DATE, CURRENT_DATE + INTERVAL 1 YEAR, 'weekly', TRUE),
       (UUID_TO_BIN(UUID()), (SELECT id FROM school_class WHERE name = 'IN19a'),
        (SELECT id FROM user WHERE email = 'teacher2@test.ch'), (SELECT id FROM subject WHERE name = 'Mathe'),
        'no notes2', CURRENT_DATE, CURRENT_DATE + INTERVAL 1 YEAR, 'daily', TRUE),
       (UUID_TO_BIN(UUID()), (SELECT id FROM school_class WHERE name = 'BM19c'),
        (SELECT id FROM user WHERE email = 'teacher2@test.ch'), (SELECT id FROM subject WHERE name = 'Mathe'),
        'no notes2', CURRENT_DATE, CURRENT_DATE + INTERVAL 1 YEAR, 'weekly', TRUE);

INSERT INTO timetable(id, lesson_index, start_time, end_time)
VALUES (UUID_TO_BIN(UUID()), 1, CURRENT_TIME, CURRENT_TIME + INTERVAL 1 HOUR),
       (UUID_TO_BIN(UUID()), 2, CURRENT_TIME, CURRENT_TIME + INTERVAL 1 HOUR);


INSERT INTO room(id, name, note)
VALUES (UUID_TO_BIN(UUID()), 'B427', 'no note');

INSERT INTO lesson(id, school_class_subject_id, start_time, end_time, weekday, room)
VALUES (UUID_TO_BIN(UUID()), (SELECT id FROM school_class WHERE name = 'IN19a'), CURRENT_TIME,
        CURRENT_TIME + INTERVAL 1 HOUR, 'monday', (SELECT id FROM room WHERE name = 'B427')),
       (UUID_TO_BIN(UUID()), (SELECT id FROM school_class WHERE name = 'IN19a'), CURRENT_TIME,
        CURRENT_TIME + INTERVAL 1 HOUR, NULL, (SELECT id FROM room WHERE name = 'B427')),
       (UUID_TO_BIN(UUID()), (SELECT id FROM school_class WHERE name = 'BM19c'), CURRENT_TIME,
        CURRENT_TIME + INTERVAL 1 HOUR, NULL, (SELECT id FROM room WHERE name = 'B427'));

INSERT INTO semester(id, name, `from`, `to`)
VALUES (UUID_TO_BIN(UUID()), 'Semester 1', CURRENT_DATE - INTERVAL 6 MONTH, CURRENT_DATE),
       (UUID_TO_BIN(UUID()), 'Semester 2', CURRENT_DATE, CURRENT_DATE + INTERVAL 6 MONTH);

INSERT INTO exam(id, school_class_subject_id, name, description, weight, date, view_date, semester_id)
VALUES (UUID_TO_BIN(UUID()),
        (SELECT id
         FROM school_class_subject
         WHERE school_class_id = (SELECT id FROM school_class WHERE name = 'IN19a')
         AND subject_id = (SELECT id FROM subject WHERE subject.name = 'Informatik')),
        'Prüfung IDPA', 'Prüfungs beschreibung', 1.0, CURRENT_DATE - INTERVAL 1 DAY, CURRENT_DATE,
        (SELECT id
         FROM semester
         WHERE semester.name = 'Semester 1')),
       (UUID_TO_BIN(UUID()),
        (SELECT id
         FROM school_class_subject
         WHERE school_class_id = (SELECT id FROM school_class WHERE name = 'IN19a')
           AND subject_id = (SELECT id FROM subject WHERE subject.name = 'Mathe')),
        'Algebra I', 'Prüfungs beschreibung', 1.0, CURRENT_DATE - INTERVAL 2 DAY, CURRENT_DATE,
        (SELECT id
         FROM semester
         WHERE semester.name = 'Semester 1')),
       (UUID_TO_BIN(UUID()),
        (SELECT id
         FROM school_class_subject
         WHERE school_class_id = (SELECT id FROM school_class WHERE name = 'IN19a')
           AND subject_id = (SELECT id FROM subject WHERE subject.name = 'Mathe')),
        'Logarithmus', 'Prüfungs beschreibung', 0.5, CURRENT_DATE - INTERVAL 4 DAY, CURRENT_DATE,
        (SELECT id
         FROM semester
         WHERE semester.name = 'Semester 1')),
       (UUID_TO_BIN(UUID()),
        (SELECT id
         FROM school_class_subject
         WHERE school_class_id = (SELECT id FROM school_class WHERE name = 'IN19a')
           AND subject_id = (SELECT id FROM subject WHERE subject.name = 'Informatik')),
        'Prüfung IDPA II', 'Prüfungs beschreibung', 1.0, CURRENT_DATE - INTERVAL 3 DAY, CURRENT_DATE,
        (SELECT id
         FROM semester
         WHERE semester.name = 'Semester 2')),
       (UUID_TO_BIN(UUID()),
        (SELECT id
         FROM school_class_subject
         WHERE school_class_id = (SELECT id FROM school_class WHERE name = 'IN19a')
           AND subject_id = (SELECT id FROM subject WHERE subject.name = 'Mathe')),
        'Geometrie', 'Prüfungs beschreibung', 1.0, CURRENT_DATE - INTERVAL 1 DAY, CURRENT_DATE,
        (SELECT id
         FROM semester
         WHERE semester.name = 'Semester 2')),
       (UUID_TO_BIN(UUID()),
        (SELECT id
         FROM school_class_subject
         WHERE school_class_id = (SELECT id FROM school_class WHERE name = 'IN19a')
           AND subject_id = (SELECT id FROM subject WHERE subject.name = 'Mathe')),
        'Planimetrie', 'Prüfungs beschreibung', 1.0, CURRENT_DATE, CURRENT_DATE,
        (SELECT id
         FROM semester
         WHERE semester.name = 'Semester 2'));

INSERT INTO grade(id, student_id, mark, note, exam_id)
VALUES (UUID_TO_BIN(UUID()), (SELECT id FROM user WHERE email = 'student@test.ch'), 6.0, 'no note',
        (SELECT id FROM exam WHERE name = 'Prüfung IDPA')),
       (UUID_TO_BIN(UUID()), (SELECT id FROM user WHERE email = 'student2@test.ch'), 5.0, 'no note',
        (SELECT id FROM exam WHERE name = 'Prüfung IDPA')),
       (UUID_TO_BIN(UUID()), (SELECT id FROM user WHERE email = 'student@test.ch'), 5.6, 'no note',
        (SELECT id FROM exam WHERE name = 'Algebra I')),
       (UUID_TO_BIN(UUID()), (SELECT id FROM user WHERE email = 'student2@test.ch'), 5.0, 'no note',
        (SELECT id FROM exam WHERE name = 'Algebra I')),
       (UUID_TO_BIN(UUID()), (SELECT id FROM user WHERE email = 'student@test.ch'), 5.8, 'no note',
        (SELECT id FROM exam WHERE name = 'Logarithmus')),
       (UUID_TO_BIN(UUID()), (SELECT id FROM user WHERE email = 'student2@test.ch'), 4.8, 'no note',
        (SELECT id FROM exam WHERE name = 'Logarithmus')),
       (UUID_TO_BIN(UUID()), (SELECT id FROM user WHERE email = 'student@test.ch'), 5.4, 'no note',
        (SELECT id FROM exam WHERE name = 'Prüfung IDPA II')),
       (UUID_TO_BIN(UUID()), (SELECT id FROM user WHERE email = 'student@test.ch'), 5.8, 'no note',
        (SELECT id FROM exam WHERE name = 'Geometrie')),
       (UUID_TO_BIN(UUID()), (SELECT id FROM user WHERE email = 'student2@test.ch'), 5.1, 'no note',
        (SELECT id FROM exam WHERE name = 'Geometrie')),
       (UUID_TO_BIN(UUID()), (SELECT id FROM user WHERE email = 'student@test.ch'), 5.0, 'no note',
        (SELECT id FROM exam WHERE name = 'Planimetrie')),
       (UUID_TO_BIN(UUID()), (SELECT id FROM user WHERE email = 'student2@test.ch'), 5.0, 'no note',
        (SELECT id FROM exam WHERE name = 'Planimetrie'));

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
VALUES ((SELECT id FROM user WHERE email = 'parent@test.ch'), (SELECT id FROM user WHERE email = 'student@test.ch')),
       ((SELECT id FROM user WHERE email = 'parent@test.ch'), (SELECT id FROM user WHERE email = 'student2@test.ch'));

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

INSERT INTO school_class_semester(school_class_id, semester_id)
VALUES ((SELECT id FROM school_class WHERE name = 'IN19a'), (SELECT  id FROM semester WHERE name = 'Semester 1')),
       ((SELECT id FROM school_class WHERE name = 'IN19a'), (SELECT  id FROM semester WHERE name = 'Semester 2')),
       ((SELECT id FROM school_class WHERE name = 'BM19c'), (SELECT  id FROM semester WHERE name = 'Semester 1')),
       ((SELECT id FROM school_class WHERE name = 'BM19c'), (SELECT  id FROM semester WHERE name = 'Semester 2'));

INSERT INTO token(user_id, access, refresh, access_expires, refresh_expires)
VALUES ((SELECT id FROM user WHERE email = 'teacher@test.ch'), 'teacher', 'teacher', CURRENT_TIMESTAMP + INTERVAL 2 YEAR, CURRENT_TIMESTAMP + INTERVAL 2 YEAR),
       ((SELECT id FROM user WHERE email = 'student@test.ch'), 'student', 'student', CURRENT_TIMESTAMP + INTERVAL 2 YEAR, CURRENT_TIMESTAMP + INTERVAL 2 YEAR),
       ((SELECT id FROM user WHERE email = 'parent@test.ch'), 'parent', 'parent', CURRENT_TIMESTAMP + INTERVAL 2 YEAR, CURRENT_TIMESTAMP + INTERVAL 2 YEAR),
       ((SELECT id FROM user WHERE email = 'teacher2@test.ch'), 'teacher2', 'teacher2', CURRENT_TIMESTAMP + INTERVAL 2 YEAR, CURRENT_TIMESTAMP + INTERVAL 2 YEAR),
       ((SELECT id FROM user WHERE email = 'student2@test.ch'), 'student2', 'student2', CURRENT_TIMESTAMP + INTERVAL 2 YEAR, CURRENT_TIMESTAMP + INTERVAL 2 YEAR),
       ((SELECT id FROM user WHERE email = 'parent2@test.ch'), 'parent2', 'parent2', CURRENT_TIMESTAMP + INTERVAL 2 YEAR, CURRENT_TIMESTAMP + INTERVAL 2 YEAR)