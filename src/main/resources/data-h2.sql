-- @PASSWORD = user
SET @PASSWORD = '$2a$10$Qji2/icFWIGGQEAv8bbwNuKGrSZyiUUPqE/0SNO2M.BpU.NA6xPhW';


-- EMPLOYEE PHOTOS
insert into file(id, content) values
(0,  FILE_READ('./src/main/resources/static/photos/face.jpg')),
(1,  FILE_READ('./src/main/resources/static/photos/darth.jpeg')),
(2,  FILE_READ('./src/main/resources/static/photos/bb8.jpeg'));

insert into file(id, content) values
(100, FILE_READ('./src/main/resources/static/photos/dogs.jpg'));

--AUTHENTICATION
INSERT into user(id, username, password, active,  name, email, address, zip_code, neighborhood, city, state , address_number, residential_phone, cel_phone) VALUES
(100, 'user', @PASSWORD, 'true' ,'Baby Yoda','yoda@stars.wars', 'Garro', '92032380',
'Tattooine', 'StarWars', 'RS' ,544,'51982656565', '');

--ROLES
insert into role(id, role) values
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

-- USER_ROLES
insert into user_roles (user_id, roles_id) values
(100, 1);

-- ANIMAL_TYPE
insert into animal_type (id, name) values
(101, 'Cachorro'),
(102, 'gato');

-- ANIMAL_GENDER
insert into animal_gender (id, name) values
(101, 'Macho'),
(102, 'Fêmea');

-- ANIMAL_SIZE
insert into animal_size (id, name) values
(101, 'Pequeno'),
(102, 'Médio'),
(103, 'Grande');

-- ANIMAL_CASTRATED
insert into animal_castrated (id, name) values
(101, 'Sim'),
(102, 'Não'),
(103, 'Não sei');

-- ANIMAL_AGE
insert into animal_age (id, name) values
(101, 'Filhote'),
(102, 'Adulto'),
(103, 'Idoso');

-- CITY
-- insert into city (id, description) values
--(101, 'Canoas'),
--(102, 'Esteio'),
--(103, 'Sapucaia do Sul'),
--(104, 'Porto Alegre'),
--(105, 'São Leopoldo'),
--(106, 'Novo Hamburgo'),
--(107, 'Cachoeirinha'),
--(108, 'Viamão');


