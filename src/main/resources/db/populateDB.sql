DELETE
FROM meals;
DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE global_seq2 RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT into meals (user_id, dateTime, description, calories)
VALUES (100000, timestamp '2015-5-30 10:10:10', 'breakfast', 666),
       (100000, timestamp '2015-5-30 20:10:10', 'dinner', 777),
       (100000, timestamp '2015-5-31 10:11:10', 'breakfast', 888),
       (100000, timestamp '2015-5-31 20:11:10', 'dinner', 999);
