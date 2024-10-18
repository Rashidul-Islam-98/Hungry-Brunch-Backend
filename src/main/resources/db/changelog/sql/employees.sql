--liquibase formatted sql

--changeset rashidul:1
CREATE TABLE IF NOT EXISTS employees (
   id UUID DEFAULT gen_random_uuid() NOT NULL,
   designation VARCHAR(255) NOT NULL,
   join_date DATE NOT NULL,
   amount_sold INTEGER,
   user_id UUID UNIQUE,
   CONSTRAINT pk_employees PRIMARY KEY (id),
   CONSTRAINT fk_employee_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);