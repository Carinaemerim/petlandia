-- @PASSWORD = user
SET @PASSWORD = '$2a$10$Qji2/icFWIGGQEAv8bbwNuKGrSZyiUUPqE/0SNO2M.BpU.NA6xPhW';


-- EMPLOYEE PHOTOS
insert into file(id, content) values
(0,  FILE_READ('./src/main/resources/static/photos/face.jpg')),
(1,  FILE_READ('./src/main/resources/static/photos/darth.jpeg')),
(2,  FILE_READ('./src/main/resources/static/photos/bb8.jpeg'));

insert into file(id, content) values
(100, FILE_READ('./src/main/resources/static/photos/dogs.jpg'));

--ROLES
insert into role(id, role) values
(1, 'USER'),
(2, 'ADMIN'),
(3, 'MODERATOR');

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

--ANIMAL_COLOR
insert into animal_color(id, name) values
(101, 'Branco'),
(102, 'Preto'),
(103, 'Marrom'),
(104, 'Caramelo'),
(105, 'Cinza'),
(106, 'Creme');

--AUTHENTICATION
INSERT into user(id, username, password, active,  name, email, address, zip_code, neighborhood, city, state,
address_number, residential_phone, cel_phone, animal_age_id, animal_gender_id, animal_type_id, animal_castrated_id,
animal_color_id,animal_size_id) VALUES
(100, 'user', @PASSWORD, 'true' ,'Baby Yoda','yoda@stars.wars', 'Garro', '92032380',
'Tattooine', 'StarWars', 'RS' ,544,'51982656565', '', 101, 102,101,101,101,101);

-- USER_ROLES
insert into user_roles (user_id, roles_id) values
(100, 1);

--ANNOUNCE
insert into announce(id, address, address_number, city, date, description, main_photo, name, neighborhood, second_photo,
state, third_photo, title, zip_code, animal_age_id, animal_castrated_id, animal_gender_id, animal_size_id, animal_type_id, user_id, status)
values
(1,'Rua Tramandai',0,'Canoas','2020-03-28 18:13:16.42'	,'ijOjIOJJAOs AJS OIAJSoa soj soji aoi soiasoia josia js','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',
'Rodolfo','Estância Velha' ,null,'RS' ,null,'23423432dcfsd','92032-360',101,102,102,103,101,100, 'ACTIVE'),
(2,'Rua Tramandai',0,'Canoas','2020-03-28 18:13:16.42'	,'ijOjIOJJAOs AJS OIAJSoa soj soji aoi soiasoia josia js','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',
'Rogério','Estância Velha' ,null,'RS' ,null,'23423432dcfsd','92032-360',101,102,102,103,101,100, 'ACTIVE');

