--liquibase formatted sql

--changeset rashidul:1
 UPDATE foods SET discount_type=0, discount=0 where id=3;