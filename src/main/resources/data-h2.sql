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
INSERT into user(id, username, password, name, email, experience, skill, active, picture_id) VALUES
(100, 'user', @PASSWORD, 'Master Yoda','yoda@stars.wars','Masters Unidentified Jedi, Garro, Qui-Gon Jinn',
'Deflect Force Lightning, Strategic Mastery, Acting Skills, Indomitable Will, Battle Meditation, Sensing Death ' ||
 'And Force-aided Acrobatics.',  TRUE, 0),
(200, 'darth', @PASSWORD, 'Darth Vader', 'vader@star.wars', 'Lorde Sith', 'The force is strong in him, leader skills',
 TRUE, 1),
 (300, 'bb8', @PASSWORD, 'BB-8', 'bb2@star.wars','Jedi Robot' ,'A robot that can be everywhere and helps everyone',
 TRUE, 2);

--ROLES
insert into role(id, role) values
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

-- USER_ROLES
insert into user_roles (user_id, roles_id) values
(100, 1),
(200, 1),
(300, 2);

-- ANIMAL_TYPE
insert into animal_type (id, name) values
(101, 'Cachorro'),
(102, 'gato');

-- ANIMAL_GENDER
insert into animal_gender (id, name) values
(101, 'Macho'),
(102, 'Fêmea');

-- ANIMAL_SIZE
insert into animal_gender (id, name) values
(101, 'Pequeno'),
(102, 'Médio'),
(103, 'Grande');

-- ANIMAL_CASTRATED
insert into animal_gender (id, name) values
(101, 'Sim'),
(102, 'Não'),
(103, 'Não sei');

-- CITY
insert into city (id, description) values
(101, 'Canoas'),
(102, 'Esteio'),
(103, 'Sapucaia do Sul'),
(104, 'Porto Alegre'),
(105, 'São Leopoldo'),
(106, 'Novo Hamburgo'),
(107, 'Cachoeirinha'),
(108, 'Viamão');

-- ANNOUNCE
insert into announce(id, name, type_id, date, description, city_id, size, photo_id, user_id) values
(66, 'Beijamin', 101, CURRENT_TIMESTAMP, 'Encontramos este cachorro perambulando pelas ruas do bairro Estancia Velha.' ||
 'No momento estamos cuidando dele, porém como já temos outros cinco cães, não poderemos ficar com ele e poder dar' ||
  'as condiçoes que ele precisa. è muito amigável e cheio de energia. Um companheiro sem igual! Pelos nossos cálculos' ||
   'tem cerca de 2 anos de idade.',101,'Medio',100,100);
