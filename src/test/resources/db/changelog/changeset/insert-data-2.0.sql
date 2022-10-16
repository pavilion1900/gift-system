-- liquibase formatted sql

-- changeset Korolenko Sergey:1
INSERT INTO users (id, name)
VALUES (1, 'Ivanov'),
       (2, 'Petrov'),
       (3, 'Sidorov');
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

-- changeset Korolenko Sergey:2
INSERT INTO orders (id, cost, purchase_date, user_id, certificate_id)
VALUES (1, 20.83, current_timestamp(3), 1, 3),
       (2, 5.27, current_timestamp(3), 2, 5),
       (3, 15.85, current_timestamp(3), 3, 2),
       (4, 5.27, current_timestamp(3), 1, 5);
SELECT SETVAL('orders_id_seq', (SELECT MAX(id) FROM orders));
