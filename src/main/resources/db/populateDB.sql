DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password
)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id
)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

ALTER SEQUENCE meals_id_seq RESTART WITH 1;

INSERT INTO meals(date_time, description, calories, user_id)
VALUES ('2019-05-30 10:00', 'Завтрак', 500, 100000)
     , ('2019-05-30 13:00', 'Обед', 1000, 100000)
     , ('2019-05-30 18:00', 'Ужин', 500, 100000)
     , ('2019-05-31 10:00', 'Завтрак', 1000, 100000)
     , ('2019-05-31 13:00', 'Обед', 500, 100000)
     , ('2019-05-31 18:00', 'Ужин', 510, 100000)
     , ('2019-05-31 10:00', 'Завтрак для Админа', 900, 100001)
     , ('2019-05-31 13:00', 'Обед для Админа', 600, 100001)
     , ('2019-05-31 18:00', 'Ужин для Админа', 600, 100001);
