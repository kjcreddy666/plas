CREATE DATABASE plas2;

\c plas2;

CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    mobile BIGINT NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    role VARCHAR(50) NOT NULL
);

INSERT INTO users (id,name, email, mobile, password, address, role)
VALUES (
    'ba34c980-3d74-4f60-9c0e-e7a08adf33a5','Admin','admin@gmail.com',9999999999,'$2a$10$2WEMvO9A7a97k0yFdb/Lk.FvMeRlxEfqBJQq0FTwsMk8La1QykYVq','Admin Office','ADMIN'
);
