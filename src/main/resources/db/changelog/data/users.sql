--liquibase formatted sql

--changeset rashidul:1
INSERT INTO users (id, username, password, email, first_name, middle_name, last_name, father_name, mother_name, spouse_name, dob, phone_number, gender_id, image, image_url, nid, provider, role, force_change_password)
VALUES ('b5b4e36a-3a10-4c5a-96b7-1c94d273c4b3', 'rashidul@mail.com', '$2a$10$EAhIsScj6gDkaeAFt1dtOebKfEqby3B9Xp8Hu7DCBX7n/Nk8kkNXq', 'rashidul@mail.com', 'Rashidul', '', 'Islam', 'Samsul Haque', 'Rabeya Begum', '', '1998-12-13', '01302482643', 0, 'rashidul_19845858585', 'https://res.cloudinary.com/dneeqhmmc/image/upload/v1715954238/restaurant/user/rashidul_19845858585.jpg', '27834747384757', 'Hungry Brunch', 'ADMIN', false);

--admin_login credentials
--username: rashidul@mail.com
--password: Rashidul@123