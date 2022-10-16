-- liquibase formatted sql

-- changeset Korolenko Sergey:1
CREATE TABLE gift_certificate
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR UNIQUE NOT NULL,
    description      VARCHAR        NOT NULL,
    price            NUMERIC(10, 2) NOT NULL,
    duration         INT            NOT NULL,
    create_date      TIMESTAMP      NOT NULL,
    last_update_date TIMESTAMP      NOT NULL
);

-- changeset Korolenko Sergey:2
CREATE TABLE tag
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL
);

-- changeset Korolenko Sergey:3
CREATE TABLE certificate_tag
(
    certificate_id INT REFERENCES gift_certificate (id),
    tag_id         INT REFERENCES tag (id),
    PRIMARY KEY (certificate_id, tag_id)
);
