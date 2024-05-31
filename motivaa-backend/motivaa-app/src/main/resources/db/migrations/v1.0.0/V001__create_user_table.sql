 -- liquibase formatted sql

CREATE TABLE IF NOT EXISTS user (
    uuid VARCHAR(36) NOT NULL PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    last_login_time_stamp TIMESTAMP,
    status VARCHAR(255)
);

-- rollback DROP TABLE user;
