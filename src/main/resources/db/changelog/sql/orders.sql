--liquibase formatted sql

--changeset rashidul:1
CREATE TABLE IF NOT EXISTS orders (
   id UUID DEFAULT gen_random_uuid() NOT NULL,
   order_number VARCHAR(255) NOT NULL,
   amount INTEGER,
   order_status INTEGER,
   table_id BIGINT,
   CONSTRAINT pk_orders PRIMARY KEY (id),
   CONSTRAINT fk_order_table FOREIGN KEY (table_id) REFERENCES tables(id) ON DELETE SET NULL
);