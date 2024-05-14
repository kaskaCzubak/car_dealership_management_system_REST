ALTER TABLE salesman
ADD COLUMN user_id INT,
ADD FOREIGN KEY (user_id) REFERENCES car_dealership_user (user_id);

ALTER TABLE mechanic
ADD COLUMN user_id INT,
ADD FOREIGN KEY (user_id) REFERENCES car_dealership_user (user_id);

insert into car_dealership_user (user_id, user_name, email, password, active) values (1, 'jack_salesman', 'jack_salesman@hop.pl','$2a$12$sBy/.dKeXXSuWfti1sqyS./7WndvkSjleax6iD8tzyr5UIyKtd68W' , true);
insert into car_dealership_user (user_id, user_name, email, password, active) values (2, 'monic_currency', 'monic_currency@hop.pl','$2a$12$sBy/.dKeXXSuWfti1sqyS./7WndvkSjleax6iD8tzyr5UIyKtd68W' , true);
insert into car_dealership_user (user_id, user_name, email, password, active) values (3, 'tom_price', 'tom_price@hop.pl','$2a$12$sBy/.dKeXXSuWfti1sqyS./7WndvkSjleax6iD8tzyr5UIyKtd68W' , true);
insert into car_dealership_user (user_id, user_name, email, password, active) values (4, 'will_discount', 'will_discount@hop.pl','$2a$12$sBy/.dKeXXSuWfti1sqyS./7WndvkSjleax6iD8tzyr5UIyKtd68W' , true);

insert into car_dealership_user (user_id, user_name, email, password, active) values (5, 'robert_mechanic', 'robert_mechanic@hop.pl','$2a$12$sBy/.dKeXXSuWfti1sqyS./7WndvkSjleax6iD8tzyr5UIyKtd68W' , true);
insert into car_dealership_user (user_id, user_name, email, password, active) values (6, 'john_service', 'john_service@hop.pl','$2a$12$sBy/.dKeXXSuWfti1sqyS./7WndvkSjleax6iD8tzyr5UIyKtd68W' , true);
insert into car_dealership_user (user_id, user_name, email, password, active) values (7, 'ann_car', 'ann_car@hop.pl','$2a$12$sBy/.dKeXXSuWfti1sqyS./7WndvkSjleax6iD8tzyr5UIyKtd68W' , true);

UPDATE salesman SET user_id = 1 WHERE pesel = '67020499436';
UPDATE salesman SET user_id = 2 WHERE pesel = '73021314515';
UPDATE salesman SET user_id = 3 WHERE pesel = '55091699846';
UPDATE salesman SET user_id = 4 WHERE pesel = '62081825675';

UPDATE mechanic SET user_id = 5 WHERE pesel = '52070997836';
UPDATE mechanic SET user_id = 6 WHERE pesel = '83011863727';
UPDATE mechanic SET user_id = 7 WHERE pesel = '67111396321';

insert into car_dealership_role (role_id, role) values (1, 'SALESMAN'), (2, 'MECHANIC');

insert into car_dealership_user_role (user_id, role_id) values (1, 1), (2,1), (3,1), (4,1);
insert into car_dealership_user_role (user_id, role_id) values (5,2), (6,2), (7,2);

ALTER TABLE salesman
ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE mechanic
ALTER COLUMN user_id SET NOT NULL;