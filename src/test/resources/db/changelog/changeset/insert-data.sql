-- liquibase formatted sql

-- changeset Korolenko Sergey:1
INSERT INTO tag (id, name)
VALUES (1, 'new'),
       (2, 'old'),
       (3, 'expensive'),
       (4, 'cheap'),
       (5, 'short'),
       (6, 'long');
SELECT SETVAL('tag_id_seq', (SELECT MAX(id) FROM tag));

-- changeset Korolenko Sergey:2
INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date)
VALUES (1, 'first', 'first description', 10.23, 10, current_timestamp(3), current_timestamp(3)),
       (2, 'second', 'second description', 15.85, 20, current_timestamp(3), current_timestamp(3)),
       (3, 'third', 'third description', 20.83, 8, current_timestamp(3), current_timestamp(3)),
       (4, 'fourth', 'fourth description', 25.58, 15, current_timestamp(3), current_timestamp(3)),
       (5, 'fifth', 'fifth description', 5.27, 30, current_timestamp(3), current_timestamp(3));
SELECT SETVAL('gift_certificate_id_seq', (SELECT MAX(id) FROM gift_certificate));

-- changeset Korolenko Sergey:3
INSERT INTO certificate_tag (certificate_id, tag_id)
VALUES (1, 1),
       (1, 5),
       (2, 1),
       (2, 6),
       (3, 1),
       (3, 3),
       (3, 5),
       (4, 1),
       (4, 3),
       (5, 1),
       (5, 4),
       (5, 6);
