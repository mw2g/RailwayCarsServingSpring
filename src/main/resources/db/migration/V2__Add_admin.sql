INSERT INTO user (user_id, email, enabled, first_name, initials, last_name, middle_name, password, username)
VALUES (1, '', 1, 'Администратор', 'Администратор', 'Администратор', 'Администратор',
        '$2b$10$c76wK.K4g1q/JetQLhiGBOBPX75JLj6MkDSF7k/dEqkXEyxayun9e', 'admin');

INSERT INTO user_role (user_id, roles)
VALUES (1, 'ROLE_ADMIN'),
       (1, 'ROLE_USER');
