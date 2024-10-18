--liquibase formatted sql

--changeset rashidul:1
CREATE TABLE IF NOT EXISTS users (
    id UUID DEFAULT gen_random_uuid() NOT NULL,
    username VARCHAR(255),
    password VARCHAR(255),
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    last_name VARCHAR(255) NOT NULL,
    image VARCHAR(255) NOT NULL,
    image_url VARCHAR(255),
    spouse_name VARCHAR(255),
    father_name VARCHAR(255) NOT NULL,
    mother_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(50) NOT NULL,
    gender_id INTEGER NOT NULL,
    dob DATE NOT NULL,
    nid VARCHAR(50) NOT NULL,
    force_change_password BOOLEAN DEFAULT FALSE,
    role VARCHAR(50),
    provider VARCHAR(50),
    CONSTRAINT pk_users PRIMARY KEY (id)
);