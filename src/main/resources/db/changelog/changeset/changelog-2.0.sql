-- liquibase formatted sql

-- changeset Korolenko Sergey:1
CREATE TABLE IF NOT EXISTS users
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL
);

-- changeset Korolenko Sergey:2
CREATE TABLE IF NOT EXISTS orders
(
    id             SERIAL PRIMARY KEY,
    cost           NUMERIC(10, 2) CHECK ( cost > 0 ) NOT NULL,
    purchase_date  TIMESTAMP                         NOT NULL,
    user_id        INT REFERENCES users (id),
    certificate_id INT REFERENCES gift_certificate (id)
);
