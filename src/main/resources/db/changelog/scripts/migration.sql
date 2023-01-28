--liquibase formatted sql

--changeset yukov:1

CREATE TABLE IF NOT EXISTS socks
(
    socks_id SERIAL PRIMARY KEY,
    color VARCHAR(50) NOT NULL,
    cotton_part INT NOT NULL,
    quantity INT NOT NULL
)

--changeset yukov:2

ALTER TABLE IF EXISTS socks
    ADD CONSTRAINT quantity_constrant CHECK (quantity >= 0),
    ADD CONSTRAINT cotton_part_constrant CHECK (cotton_part >= 0 AND cotton_part <= 100);



