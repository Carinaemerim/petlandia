-- @PASSWORD = user
SET @PASSWORD = '$2a$10$Qji2/icFWIGGQEAv8bbwNuKGrSZyiUUPqE/0SNO2M.BpU.NA6xPhW';


-- EMPLOYEE PHOTOS
insert into file(id, content) values
(0,  FILE_READ('./src/main/resources/static/photos/face.jpg'));

insert into file(id, content) values
(100, FILE_READ('./src/main/resources/static/photos/dogs.jpg'));

--AUTHENTICATION
INSERT into user(id, username, password, name, email, experience, skill, active, picture_id) VALUES
(100, 'user', @PASSWORD, 'Master Yoda','yoda@stars.wars','Masters Unidentified Jedi, Garro, Qui-Gon Jinn', 'Deflect Force Lightning, Strategic Mastery, Acting Skills, Indomitable Will, Battle Meditation, Sensing Death And Force-aided Acrobatics.',  TRUE, 0);

--ROLES
insert into role(id, role) values
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

-- USER_ROLES
insert into user_roles (user_id, roles_id) values
(100, 1);

-- ANIMAL_TYPE
insert into animal_type (id, name) values
(1, 'Cachorro'),
(2, 'Gato');

-- ANNOUNCE
insert into announce(id, name, type_id, date, description, city, size, photo_id, user_id) values
(66, 'Beijamin', 1, CURRENT_TIMESTAMP, 'Encontramos este cachorro perambulando pelas ruas do bairro Estancia Velha.' ||
 'No momento estamos cuidando dele, porém como já temos outros cinco cães, não poderemos ficar com ele e poder dar' ||
  'as condiçoes que ele precisa. è muito amigável e cheio de energia. Um companheiro sem igual! Pelos nossos cálculos' ||
   'tem cerca de 2 anos de idade.','Canoas','Medio',100,100),
(67, 'Lili', 2, CURRENT_TIMESTAMP, 'Estamos com esta gatinha linda no abrigo de PetFeliz. Ela chegou até nós muito ' ||
 'timida e magrinha, porém graças aos nossos voluntários,agora ela está forte, feliz e pronta para receber um lar'
 ,'Porto Alegre','Pequeno',100,100),
(68, 'Mary', 1, CURRENT_TIMESTAMP, 'Esta cadelinha simpática ainda é filhote e foi encontrada muito fraca por pessoas' ||
  'que transitavam pela BR-116. Ela é muito brincalhona e se da bem com crianças.' ,'Porto Alegre','Pequeno',100,100),
(69, 'Totó', 1, CURRENT_TIMESTAMP, '  Totó é um cachorrinho dócil e tranquilo, adora brincar com seu novo brinquedo, ' ||
 'a bolinha. Está se habituando aos passeios na guia, o qual tem se comportado, ele é muito querido e obediente, ama' ||
  ' um cafuné e um bom carinho em seu peito. Encontrei ele no centro de Canoas dias atrás, estava com muitas pulgas ' ||
   'e carrapatos, mas agora está limpinho. Gostaria muito de encontrar um lar permanente pra ele. Ele precisa tanto ' ||
    'de uma família.' ,'Canoas','Medio',100,100),
(70, 'Nerinho', 1, CURRENT_TIMESTAMP, 'O Nerinho foi perdido ou abandonado no domingo dia 02/02/2013, ele é muito carinhoso ' ||
 'e brincalhão, adora outros cães e pessoas, ele é muito lindinho.' ,'Sapucaia do Sul','Medio',100,100),
(71, 'Joey', 2, CURRENT_TIMESTAMP, 'O gato Joey apareceu no meu telhado no final de junho bem magrinho e miando muito. ' ||
 'Tiramos ele do telhado, demos água, comida e um lugar quentinho pra ele dormir, já que estava fazendo muito frio.
Ele é dócil, falante, um pouco medroso, mas adora um cafuné. Sabe usar a caixa de areia e come ração seca numa boa.
Ele é muito fofo e inteligente. Se dá super bem com cães e gatos.' ,'Sapucaia do Sul','Pequeno', 100, 100),
(72, 'Coraline', 2, CURRENT_TIMESTAMP, 'Olá, estou procurando um dono para a gatinha Coraline, que seja muito afetuoso e' ||
 ' que tenha paciência. Ela é castrada e vermifugada recentemente. Ela é irmã, de criação, da Felicia que também está ' ||
  'para adoção.' ,'Sapucaia do Sul','Medio',100, 100),
(73, 'Ronron', 2, CURRENT_TIMESTAMP, 'Gato calmo, bonito, gordo preciso doar pois trouxe p casa apenas para alimentar' ||
 ' e acabei ficando mas como pátio não é meu preciso doar' ,'Sapucaia do Sul','Medio',
 100, 100);
