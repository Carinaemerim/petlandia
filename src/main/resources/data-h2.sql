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
(101, 'Cachorro Macho'),
(102, 'Cachorro Fêmea'),
(103, 'Gato Macho'),
(104, 'Gato Fêmea');

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
   'tem cerca de 2 anos de idade.',101,'Medio',100,100),
(67, 'Lili', 104, CURRENT_TIMESTAMP, 'Estamos com esta gatinha linda no abrigo de PetFeliz. Ela chegou até nós muito ' ||
 'timida e magrinha, porém graças aos nossos voluntários,agora ela está forte, feliz e pronta para receber um lar'
 ,104,'Pequeno',100,100),
(68, 'Mary', 102, CURRENT_TIMESTAMP, 'Esta cadelinha simpática ainda é filhote e foi encontrada muito fraca por pessoas' ||
  'que transitavam pela BR-116. Ela é muito brincalhona e se da bem com crianças.' ,104,'Pequeno',100,100),
(69, 'Totó', 101, CURRENT_TIMESTAMP, '  Totó é um cachorrinho dócil e tranquilo, adora brincar com seu novo brinquedo, ' ||
 'a bolinha. Está se habituando aos passeios na guia, o qual tem se comportado, ele é muito querido e obediente, ama' ||
  ' um cafuné e um bom carinho em seu peito. Encontrei ele no centro de Canoas dias atrás, estava com muitas pulgas ' ||
   'e carrapatos, mas agora está limpinho. Gostaria muito de encontrar um lar permanente pra ele. Ele precisa tanto ' ||
    'de uma família.' ,101,'Medio',100,100),
(70, 'Nerinho', 101, CURRENT_TIMESTAMP, 'O Nerinho foi perdido ou abandonado no domingo dia 02/02/2013, ele é muito carinhoso ' ||
 'e brincalhão, adora outros cães e pessoas, ele é muito lindinho.' ,103,'Medio',100,200),
(71, 'Joey', 103, CURRENT_TIMESTAMP, 'O gato Joey apareceu no meu telhado no final de junho bem magrinho e miando muito. ' ||
 'Tiramos ele do telhado, demos água, comida e um lugar quentinho pra ele dormir, já que estava fazendo muito frio.
Ele é dócil, falante, um pouco medroso, mas adora um cafuné. Sabe usar a caixa de areia e come ração seca numa boa.
Ele é muito fofo e inteligente. Se dá super bem com cães e gatos.' ,104,'Pequeno', 100, 100),
(72, 'Coraline', 104, CURRENT_TIMESTAMP, 'Olá, estou procurando um dono para a gatinha Coraline, que seja muito afetuoso e' ||
 ' que tenha paciência. Ela é castrada e vermifugada recentemente. Ela é irmã de criação da Felicia que também está ' ||
  'para adoção.' ,103,'Medio',100, 100),
(73, 'Ronron', 103, CURRENT_TIMESTAMP, 'Gato calmo, bonito, gordo preciso doar pois trouxe para casa apenas para alimentar' ||
 ' e acabei ficando mas, como pátio não é meu, preciso doar' ,105,'Medio', 100, 200);
