INSERT INTO user (user_id, email, enabled, first_name, initials, last_name, middle_name, password, username)
VALUES (1, '', 1, 'Администратор', 'Администратор', 'Администратор', 'Администратор',
        '$2b$10$c76wK.K4g1q/JetQLhiGBOBPX75JLj6MkDSF7k/dEqkXEyxayun9e', 'admin'),
       (2, '', 1, 'Наталья', 'Мальченко Н.М.', 'Мальченко', 'Михайловна',
        '$2y$12$HrufbIMCDbzC4peqjlw6zOfIXhxX1jHa8HSsPj8eFIEo57S1Awh/S', 'МальченкоНМ'),
       (3, '', 1, 'Марина', 'Ахмедова М.Ю.', 'Ахмедова', 'Юрьевна',
        '$2y$12$HrufbIMCDbzC4peqjlw6zOfIXhxX1jHa8HSsPj8eFIEo57S1Awh/S', 'АхмедоваМЮ'),
       (4, '', 1, 'Александр', 'Назаренко А.А.', 'Назаренко', 'Анатольевич',
        '$2y$12$HrufbIMCDbzC4peqjlw6zOfIXhxX1jHa8HSsPj8eFIEo57S1Awh/S', 'НазаренкоАА'),
       (5, '', 1, 'Александр', 'Ахмедов А.А.', 'Ахмедов', 'Анатольевич',
        '$2y$12$HrufbIMCDbzC4peqjlw6zOfIXhxX1jHa8HSsPj8eFIEo57S1Awh/S', 'АхмедовАА'),
       (6, '', 1, 'Елена', 'Попова Е.С.', 'Попова', 'Сергеевна',
        '$2y$12$HrufbIMCDbzC4peqjlw6zOfIXhxX1jHa8HSsPj8eFIEo57S1Awh/S', 'ПоповаЕС');

INSERT INTO user_role (user_id, roles)
VALUES (1, 'ROLE_ADMIN'),
       (1, 'ROLE_USER'),
       (1, 'ROLE_WATCH'),
       (2, 'ROLE_ADMIN'),
       (2, 'ROLE_USER'),
       (2, 'ROLE_WATCH'),
       (3, 'ROLE_ADMIN'),
       (3, 'ROLE_USER'),
       (3, 'ROLE_WATCH'),
       (4, 'ROLE_ADMIN'),
       (4, 'ROLE_USER'),
       (4, 'ROLE_WATCH'),
       (5, 'ROLE_WATCH'),
       (6, 'ROLE_WATCH');
