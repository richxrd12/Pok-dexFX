drop database pokemon_db;
create database pokemon_db;

-- Secuencias

create sequence id_pokedex start with 1
	increment by 1
	maxvalue 9999
	minvalue 1;

create sequence id_usuario start with 1
	increment by 1
	maxvalue 999
	minvalue 1;

create sequence id_mega start with 1
	increment by 1
	maxvalue 999
	minvalue 1;

-- Types y enums

create type categoria_enum as enum ('Fisico', 'Especial', 'Estado');

create type direccion_type as (
	calle varchar(30),
	ciudad varchar(15),
	cod_postal char(5)
);

create type movimiento_type as (
	poder integer,
	categoria categoria_enum,
	tipo varchar(10)
);

-- Usuarios

create table usuarios(
	id_usuario integer primary key default nextval('id_usuario'),
	nombre varchar(15),
	telefono varchar(12),
	direccion direccion_type,
	email varchar(40),
	sexo varchar(25),
	user_password varchar(30)
);

-- Pokemons y megas

create table pokemons(
	id_pokemon integer primary key default nextval('id_pokedex'),
	nombre varchar(20),
	tipos varchar(20)[]
);

create table mega_pokes(
	id_mega integer default nextval('id_mega'),
	mega_piedra varchar(20)
)inherits(pokemons);

alter table mega_pokes add
		primary key(id_mega);

alter table mega_pokes add
	foreign key(id_pokemon) references pokemons(id_pokemon);

-- Pokemon y megas favoritos

create table pokemon_favs(
	id_pokemon integer,
	id_usuario integer,
	primary key(id_pokemon, id_usuario),
	foreign key (id_pokemon) references pokemons(id_pokemon),
	foreign key (id_usuario) references usuarios(id_usuario)
);

create table mega_favs(
	id_mega integer,
	id_usuario integer,
	primary key(id_mega, id_usuario),
	foreign key (id_mega) references mega_pokes(id_mega),
	foreign key (id_usuario) references usuarios(id_usuario)
);

-- Stats de pokemon y megas

create table stats_pokemon(
	id_pokemon integer primary key,
	vida integer,
	ataque integer,
	defensa integer,
	ataque_esp integer,
	defensa_esp integer,
	velocidad integer,
	foreign key (id_pokemon) references pokemons(id_pokemon)
);

create table stats_megas(
	id_mega integer primary key,
	vida integer,
	ataque integer,
	defensa integer,
	ataque_esp integer,
	defensa_esp integer,
	velocidad integer,
	foreign key (id_mega) references mega_pokes(id_mega)
);

-- Movimientos

create table movimientos(
	id_movimiento integer primary key,
	nombre varchar(30),
	movimiento movimiento_type
);

-- Iba a relacionar los Pokémon a sus movimientos pero literalmente
-- se me iba a más de 1000 registros muy fácilmente y ChatGPT no da para tanto

insert into usuarios (nombre, telefono, direccion, email, sexo, user_password) values
	('Richi', '123456789', row('Calle Polvorin', 'Springfield', '12345'), 'richi@gmail.com', 'Hombre', '1234'),
	('María', '987654321', row('Avenida Prince', 'Barcelona', '54321'), 'maria@example.com', 'Mujer', '1234'),
	('Alex', '555123456', row('Calle Princito', 'Pueblucho', '67890'), 'alex@example.com', 'No lo sé', '1234'),
	('Patricia', '333333333', row('Carrera 10', 'A big city', '13579'), 'patricia@example.com', 'Mujer', '1234'),
	('Sam', '111111111', row('Sunset Boulevard 1234', 'Los Angeles', '90210'), 'sam@example.com', 'Helicóptero de combate', '1234');

insert into pokemons (nombre, tipos) values
	('Bulbasaur', ARRAY['Planta', 'Veneno']),
	('Ivysaur', ARRAY['Planta', 'Veneno']),
	('Venusaur', ARRAY['Planta', 'Veneno']),
	('Charmander', ARRAY['Fuego']),
	('Charmeleon', ARRAY['Fuego']),
	('Charizard', ARRAY['Fuego', 'Volador']),
	('Squirtle', ARRAY['Agua']),
	('Wartortle', ARRAY['Agua']),
	('Blastoise', ARRAY['Agua']),
	('Caterpie', ARRAY['Bicho']),
	('Metapod', ARRAY['Bicho']),
	('Butterfree', ARRAY['Bicho', 'Volador']),
	('Weedle', ARRAY['Bicho', 'Veneno']),
	('Kakuna', ARRAY['Bicho', 'Veneno']),
	('Beedrill', ARRAY['Bicho', 'Veneno']),
	('Pidgey', ARRAY['Normal', 'Volador']),
	('Pidgeotto', ARRAY['Normal', 'Volador']),
	('Pidgeot', ARRAY['Normal', 'Volador']),
	('Rattata', ARRAY['Normal']),
	('Raticate', ARRAY['Normal']),
	('Spearow', ARRAY['Normal', 'Volador']),
	('Fearow', ARRAY['Normal', 'Volador']),
	('Ekans', ARRAY['Veneno']),
	('Arbok', ARRAY['Veneno']),
	('Pikachu', ARRAY['Eléctrico']),
	('Raichu', ARRAY['Eléctrico']),
	('Sandshrew', ARRAY['Tierra']),
	('Sandslash', ARRAY['Tierra']),
	('Nidoran♀', ARRAY['Veneno']),
	('Nidorina', ARRAY['Veneno']),
	('Nidoqueen', ARRAY['Veneno', 'Tierra']),
	('Nidoran♂', ARRAY['Veneno']),
	('Nidorino', ARRAY['Veneno']),
	('Nidoking', ARRAY['Veneno', 'Tierra']),
	('Clefairy', ARRAY['Hada']),
	('Clefable', ARRAY['Hada']),
	('Vulpix', ARRAY['Fuego']),
	('Ninetales', ARRAY['Fuego']),
	('Jigglypuff', ARRAY['Normal', 'Hada']),
	('Wigglytuff', ARRAY['Normal', 'Hada']),
	('Zubat', ARRAY['Veneno', 'Volador']),
	('Golbat', ARRAY['Veneno', 'Volador']),
	('Oddish', ARRAY['Planta', 'Veneno']),
	('Gloom', ARRAY['Planta', 'Veneno']),
	('Vileplume', ARRAY['Planta', 'Veneno']),
	('Paras', ARRAY['Bicho', 'Planta']),
	('Parasect', ARRAY['Bicho', 'Planta']),
	('Venonat', ARRAY['Bicho', 'Veneno']),
	('Venomoth', ARRAY['Bicho', 'Veneno']),
	('Diglett', ARRAY['Tierra']),
	('Dugtrio', ARRAY['Tierra']),
	('Meowth', ARRAY['Normal']),
	('Persian', ARRAY['Normal']),
	('Psyduck', ARRAY['Agua']),
	('Golduck', ARRAY['Agua']),
	('Mankey', ARRAY['Lucha']),
	('Primeape', ARRAY['Lucha']),
	('Growlithe', ARRAY['Fuego']),
	('Arcanine', ARRAY['Fuego']),
	('Poliwag', ARRAY['Agua']),
	('Poliwhirl', ARRAY['Agua']),
	('Poliwrath', ARRAY['Agua', 'Lucha']),
	('Abra', ARRAY['Psíquico']),
	('Kadabra', ARRAY['Psíquico']),
	('Alakazam', ARRAY['Psíquico']),
	('Machop', ARRAY['Lucha']),
	('Machoke', ARRAY['Lucha']),
	('Machamp', ARRAY['Lucha']),
	('Bellsprout', ARRAY['Planta', 'Veneno']),
	('Weepinbell', ARRAY['Planta', 'Veneno']),
	('Victreebel', ARRAY['Planta', 'Veneno']),
	('Tentacool', ARRAY['Agua', 'Veneno']),
	('Tentacruel', ARRAY['Agua', 'Veneno']),
	('Geodude', ARRAY['Roca', 'Tierra']),
	('Graveler', ARRAY['Roca', 'Tierra']),
	('Golem', ARRAY['Roca', 'Tierra']),
	('Ponyta', ARRAY['Fuego']),
	('Rapidash', ARRAY['Fuego']),
	('Slowpoke', ARRAY['Agua', 'Psíquico']),
	('Slowbro', ARRAY['Agua', 'Psíquico']),
	('Magnemite', ARRAY['Eléctrico', 'Acero']),
	('Magneton', ARRAY['Eléctrico', 'Acero']),
	('Farfetchd', ARRAY['Normal', 'Volador']),
	('Doduo', ARRAY['Normal', 'Volador']),
	('Dodrio', ARRAY['Normal', 'Volador']),
	('Seel', ARRAY['Agua']),
	('Dewgong', ARRAY['Agua', 'Hielo']),
	('Grimer', ARRAY['Veneno']),
	('Muk', ARRAY['Veneno']),
	('Shellder', ARRAY['Agua']),
	('Cloyster', ARRAY['Agua', 'Hielo']),
	('Gastly', ARRAY['Fantasma', 'Veneno']),
	('Haunter', ARRAY['Fantasma', 'Veneno']),
	('Gengar', ARRAY['Fantasma', 'Veneno']),
	('Onix', ARRAY['Roca', 'Tierra']),
	('Drowzee', ARRAY['Psíquico']),
	('Hypno', ARRAY['Psíquico']),
	('Krabby', ARRAY['Agua']),
	('Kingler', ARRAY['Agua']),
	('Voltorb', ARRAY['Eléctrico']),
	('Electrode', ARRAY['Eléctrico']),
	('Exeggcute', ARRAY['Planta', 'Psíquico']),
	('Exeggutor', ARRAY['Planta', 'Psíquico']),
	('Cubone', ARRAY['Tierra']),
	('Marowak', ARRAY['Tierra']),
	('Hitmonlee', ARRAY['Lucha']),
	('Hitmonchan', ARRAY['Lucha']),
	('Lickitung', ARRAY['Normal']),
	('Koffing', ARRAY['Veneno']),
	('Weezing', ARRAY['Veneno']),
	('Rhyhorn', ARRAY['Tierra', 'Roca']),
	('Rhydon', ARRAY['Tierra', 'Roca']),
	('Chansey', ARRAY['Normal']),
	('Tangela', ARRAY['Planta']),
	('Kangaskhan', ARRAY['Normal']),
	('Horsea', ARRAY['Agua']),
	('Seadra', ARRAY['Agua']),
	('Goldeen', ARRAY['Agua']),
	('Seaking', ARRAY['Agua']),
	('Staryu', ARRAY['Agua']),
	('Starmie', ARRAY['Agua', 'Psíquico']),
	('Mr. Mime', ARRAY['Psíquico', 'Hada']),
	('Scyther', ARRAY['Bicho', 'Volador']),
	('Jynx', ARRAY['Hielo', 'Psíquico']),
	('Electabuzz', ARRAY['Eléctrico']),
	('Magmar', ARRAY['Fuego']),
	('Pinsir', ARRAY['Bicho']),
	('Tauros', ARRAY['Normal']),
	('Magikarp', ARRAY['Agua']),
	('Gyarados', ARRAY['Agua', 'Volador']),
	('Lapras', ARRAY['Agua', 'Hielo']),
	('Ditto', ARRAY['Normal']),
	('Eevee', ARRAY['Normal']),
	('Vaporeon', ARRAY['Agua']),
	('Jolteon', ARRAY['Eléctrico']),
	('Flareon', ARRAY['Fuego']),
	('Porygon', ARRAY['Normal']),
	('Omanyte', ARRAY['Roca', 'Agua']),
	('Omastar', ARRAY['Roca', 'Agua']),
	('Kabuto', ARRAY['Roca', 'Agua']),
	('Kabutops', ARRAY['Roca', 'Agua']),
	('Aerodactyl', ARRAY['Roca', 'Volador']),
	('Snorlax', ARRAY['Normal']),
	('Articuno', ARRAY['Hielo', 'Volador']),
	('Zapdos', ARRAY['Eléctrico', 'Volador']),
	('Moltres', ARRAY['Fuego', 'Volador']),
	('Dratini', ARRAY['Dragón']),
	('Dragonair', ARRAY['Dragón']),
	('Dragonite', ARRAY['Dragón', 'Volador']),
	('Mewtwo', ARRAY['Psíquico']),
	('Mew', ARRAY['Psíquico']);

insert into stats_pokemon (id_pokemon, vida, ataque, defensa, ataque_esp, defensa_esp, velocidad) values
	(1, 45, 49, 49, 65, 65, 45), -- Bulbasaur
	(2, 60, 62, 63, 80, 80, 60), -- Ivysaur
	(3, 80, 82, 83, 100, 100, 80), -- Venusaur
	(4, 39, 52, 43, 60, 50, 65), -- Charmander
	(5, 58, 64, 58, 80, 65, 80), -- Charmeleon
	(6, 78, 84, 78, 109, 85, 100), -- Charizard
	(7, 44, 48, 65, 50, 64, 43), -- Squirtle
	(8, 59, 63, 80, 65, 80, 58), -- Wartortle
	(9, 79, 83, 100, 85, 105, 78), -- Blastoise
	(10, 45, 30, 35, 20, 20, 45), -- Caterpie
	(11, 50, 20, 55, 25, 25, 30), -- Metapod
	(12, 60, 45, 50, 90, 80, 70), -- Butterfree
	(13, 40, 35, 30, 20, 20, 50), -- Weedle
	(14, 45, 25, 50, 25, 25, 35), -- Kakuna
	(15, 65, 90, 40, 45, 80, 75), -- Beedrill
	(16, 40, 45, 40, 35, 35, 56), -- Pidgey
	(17, 63, 60, 55, 50, 50, 71), -- Pidgeotto
	(18, 83, 80, 75, 70, 70, 101), -- Pidgeot
	(19, 30, 56, 35, 25, 35, 72), -- Rattata
	(20, 55, 81, 60, 50, 70, 97), -- Raticate
	(21, 40, 60, 30, 31, 31, 70), -- Spearow
	(22, 65, 90, 65, 61, 61, 100), -- Fearow
	(23, 35, 60, 44, 40, 54, 55), -- Ekans
	(24, 60, 85, 69, 65, 79, 80), -- Arbok
	(25, 35, 55, 40, 50, 50, 90), -- Pikachu
	(26, 60, 90, 55, 90, 80, 110), -- Raichu
	(27, 50, 75, 85, 20, 30, 40), -- Sandshrew
	(28, 75, 100, 110, 45, 55, 65), -- Sandslash
	(29, 55, 47, 52, 40, 40, 41), -- Nidoran♀
	(30, 70, 62, 67, 55, 55, 56), -- Nidorina
	(31, 90, 92, 87, 75, 85, 76), -- Nidoqueen
	(32, 46, 57, 40, 40, 40, 50), -- Nidoran♂
	(33, 61, 72, 57, 55, 55, 65), -- Nidorino
	(34, 81, 102, 77, 85, 75, 85), -- Nidoking
	(35, 70, 45, 48, 60, 65, 35), -- Clefairy
	(36, 95, 70, 73, 95, 90, 60), -- Clefable
	(37, 38, 41, 40, 50, 65, 65), -- Vulpix
	(38, 73, 76, 75, 81, 100, 100), -- Ninetales
	(39, 115, 45, 20, 45, 25, 20), -- Jigglypuff
	(40, 140, 70, 45, 85, 50, 45), -- Wigglytuff
	(41, 40, 45, 35, 30, 40, 55), -- Zubat
	(42, 75, 80, 70, 65, 75, 90), -- Golbat
	(43, 45, 50, 55, 75, 65, 30), -- Oddish
	(44, 60, 65, 70, 85, 75, 40), -- Gloom
	(45, 75, 80, 85, 110, 90, 50), -- Vileplume
	(46, 35, 70, 55, 45, 55, 25), -- Paras
	(47, 60, 95, 80, 60, 80, 30), -- Parasect
	(48, 60, 55, 50, 40, 55, 45), -- Venonat
	(49, 70, 65, 60, 90, 75, 90), -- Venomoth
	(50, 10, 55, 25, 35, 45, 95), -- Diglett
	(51, 35, 100, 50, 50, 70, 120), -- Dugtrio
	(52, 40, 45, 35, 40, 40, 90), -- Meowth
	(53, 65, 70, 60, 65, 65, 115), -- Persian
	(54, 50, 52, 48, 65, 50, 55), -- Psyduck
	(55, 80, 82, 78, 95, 80, 85), -- Golduck
	(56, 40, 80, 35, 35, 45, 70), -- Mankey
	(57, 65, 105, 60, 60, 70, 95), -- Primeape
	(58, 55, 70, 45, 70, 50, 60), -- Growlithe
	(59, 90, 110, 80, 100, 80, 95), -- Arcanine
	(60, 40, 50, 40, 40, 40, 90), -- Poliwag
	(61, 65, 65, 65, 50, 50, 90), -- Poliwhirl
	(62, 90, 85, 95, 70, 90, 70), -- Poliwrath
	(63, 25, 20, 15, 105, 55, 90), -- Abra
	(64, 40, 35, 30, 120, 70, 105), -- Kadabra
	(65, 55, 50, 45, 135, 95, 120), -- Alakazam
	(66, 70, 80, 50, 35, 35, 35), -- Machop
	(67, 80, 100, 70, 50, 60, 45), -- Machoke
	(68, 90, 130, 80, 65, 85, 55), -- Machamp
	(69, 50, 75, 35, 70, 30, 40), -- Bellsprout
	(70, 65, 90, 50, 85, 45, 55), -- Weepinbell
	(71, 80, 105, 65, 100, 70, 70), -- Victreebel
	(72, 40, 40, 35, 50, 100, 70), -- Tentacool
	(73, 80, 70, 65, 80, 120, 100), -- Tentacruel
	(74, 40, 80, 100, 30, 30, 20), -- Geodude
	(75, 55, 95, 115, 45, 45, 35), -- Graveler
	(76, 80, 120, 130, 55, 65, 45), -- Golem
	(77, 50, 85, 55, 65, 65, 90), -- Ponyta
	(78, 65, 100, 70, 80, 80, 105), -- Rapidash
	(79, 90, 65, 65, 40, 40, 15), -- Slowpoke
	(80, 95, 75, 110, 100, 80, 30), -- Slowbro
	(81, 25, 35, 70, 95, 55, 45), -- Magnemite
	(82, 50, 60, 95, 120, 70, 70), -- Magneton
	(83, 52, 65, 55, 58, 62, 60), -- Farfetch'd
	(84, 35, 85, 45, 35, 35, 75), -- Doduo
	(85, 60, 110, 70, 60, 60, 100), -- Dodrio
	(86, 65, 45, 55, 45, 70, 45), -- Seel
	(87, 90, 70, 80, 70, 95, 70), -- Dewgong
	(88, 80, 80, 50, 40, 50, 25), -- Grimer
	(89, 105, 105, 75, 65, 100, 50), -- Muk
	(90, 30, 65, 100, 45, 25, 40), -- Shellder
	(91, 50, 95, 180, 85, 45, 70), -- Cloyster
	(92, 30, 35, 30, 100, 35, 80), -- Gastly
	(93, 45, 50, 45, 115, 55, 95), -- Haunter
	(94, 60, 65, 60, 130, 75, 110), -- Gengar
	(95, 35, 45, 160, 30, 45, 70), -- Onix
	(96, 60, 48, 45, 43, 90, 42), -- Drowzee
	(97, 85, 73, 70, 73, 115, 67), -- Hypno
	(98, 30, 105, 90, 25, 25, 50), -- Krabby
	(99, 55, 130, 115, 50, 50, 75), -- Kingler
	(100, 40, 30, 50, 55, 55, 100), -- Voltorb
	(101, 60, 50, 70, 80, 80, 150), -- Electrode
	(102, 60, 40, 80, 60, 45, 40), -- Exeggcute
	(103, 95, 95, 85, 125, 65, 55), -- Exeggutor
	(104, 50, 50, 95, 40, 50, 35), -- Cubone
	(105, 60, 80, 110, 50, 80, 45), -- Marowak
	(106, 50, 120, 53, 35, 110, 87), -- Hitmonlee
	(107, 50, 105, 79, 35, 110, 76), -- Hitmonchan
	(108, 90, 55, 75, 60, 75, 30), -- Lickitung
	(109, 40, 65, 95, 60, 45, 35), -- Koffing
	(110, 65, 90, 120, 85, 70, 60), -- Weezing
	(111, 80, 85, 95, 30, 30, 25), -- Rhyhorn
	(112, 105, 130, 120, 45, 45, 40), -- Rhydon
	(113, 250, 5, 5, 35, 105, 50), -- Chansey
	(114, 65, 55, 115, 100, 40, 60), -- Tangela
	(115, 105, 95, 80, 40, 80, 90), -- Kangaskhan
	(116, 30, 40, 70, 70, 25, 60), -- Horsea
	(117, 55, 65, 95, 95, 45, 85), -- Seadra
	(118, 45, 67, 60, 35, 50, 63), -- Goldeen
	(119, 80, 92, 65, 65, 80, 68), -- Seaking
	(120, 30, 45, 55, 70, 55, 85), -- Staryu
	(121, 60, 75, 85, 100, 85, 115), -- Starmie
	(122, 40, 45, 65, 100, 120, 90), -- Mr. Mime
	(123, 70, 110, 80, 55, 80, 105), -- Scyther
	(124, 65, 50, 35, 115, 95, 95), -- Jynx
	(125, 65, 83, 57, 95, 85, 105), -- Electabuzz
	(126, 65, 95, 57, 100, 85, 93), -- Magmar
	(127, 65, 125, 100, 55, 70, 85), -- Pinsir
	(128, 75, 100, 95, 40, 70, 110), -- Tauros
	(129, 20, 10, 55, 15, 20, 80), -- Magikarp
	(130, 95, 125, 79, 60, 100, 81), -- Gyarados
	(131, 130, 85, 80, 85, 95, 60), -- Lapras
	(132, 48, 48, 48, 48, 48, 48), -- Ditto
	(133, 55, 55, 50, 45, 65, 55), -- Eevee
	(134, 130, 65, 60, 110, 95, 65), -- Vaporeon
	(135, 65, 65, 60, 110, 95, 130), -- Jolteon
	(136, 65, 130, 60, 95, 110, 65), -- Flareon
	(137, 65, 60, 70, 85, 75, 40), -- Porygon
	(138, 35, 40, 100, 90, 55, 35), -- Omanyte
	(139, 70, 60, 125, 115, 70, 55), -- Omastar
	(140, 30, 80, 90, 55, 45, 55), -- Kabuto
	(141, 60, 115, 105, 65, 70, 80), -- Kabutops
	(142, 80, 105, 65, 60, 75, 130), -- Aerodactyl
	(143, 160, 110, 65, 65, 110, 30), -- Snorlax
	(144, 90, 85, 100, 95, 125, 85), -- Articuno
	(145, 90, 90, 85, 125, 90, 100), -- Zapdos
	(146, 90, 100, 90, 125, 85, 90), -- Moltres
	(147, 41, 64, 45, 50, 50, 50), -- Dratini
	(148, 61, 84, 65, 70, 70, 70), -- Dragonair
	(149, 91, 134, 95, 100, 100, 80), -- Dragonite
	(150, 106, 110, 90, 154, 90, 130), -- Mewtwo
	(151, 100, 100, 100, 100, 100, 100); -- Mew

insert into mega_pokes (id_pokemon, nombre, tipos, mega_piedra) values 
	(3, 'Mega-Venusaur', ARRAY['Planta', 'Veneno'], 'Venusaurita'),
	(6, 'Mega-Charizard Y', ARRAY['Fuego', 'Volador'], 'Charizardita Y'),
	(6, 'Mega-Charizard X', ARRAY['Fuego', 'Dragón'], 'Charizardita X'),
	(9, 'Mega-Blastoise', ARRAY['Agua'], 'Blastoisita'),
	(15, 'Mega-Beedrill', ARRAY['Bicho', 'Veneno'], 'Beedrillita'),
	(18, 'Mega-Pidgeot', ARRAY['Normal', 'Volador'], 'Pidgeotita'),
	(65, 'Mega-Alakazam', ARRAY['Psíquico'], 'Alakazamita'),
	(80, 'Mega-Slowbro', ARRAY['Agua', 'Psíquico'], 'Slowbronita'),
	(94, 'Mega-Gengar', ARRAY['Fantasma', 'Veneno'], 'Gengarita'),
	(115, 'Mega-Kangaskhan', ARRAY['Normal'], 'Kangaskhanita'),
	(127, 'Mega-Pinsir', ARRAY['Bicho'], 'Pinsirita'),
	(130, 'Mega-Gyarados', ARRAY['Agua', 'Volador'], 'Gyaradosita'),
	(142, 'Mega-Aerodactyl', ARRAY['Roca', 'Volador'], 'Aerodactylita'),
	(150, 'Mega-Mewtwo X', ARRAY['Psíquico', 'Lucha'], 'Mewtwoita X'),
	(150, 'Mega-Mewtwo Y', ARRAY['Psíquico'], 'Mewtwoita Y');

insert into stats_megas (id_mega, vida, ataque, defensa, ataque_esp, defensa_esp, velocidad) values
    (1, 80, 100, 123, 122, 120, 80), -- Mega-Venusaur
    (2, 78, 104, 78, 159, 115, 100), -- Mega-Charizard Y
    (3, 78, 130, 111, 130, 85, 100), -- Mega-Charizard X
    (4, 79, 103, 120, 135, 115, 78), -- Mega-Blastoise
    (5, 65, 150, 40, 15, 80, 145),    -- Mega-Beedrill
    (6, 83, 80, 80, 135, 80, 121),    -- Mega-Pidgeot
    (7, 55, 50, 65, 175, 95, 150),    -- Mega-Alakazam
    (8, 95, 75, 180, 130, 80, 30),    -- Mega-Slowbro
    (9, 60, 65, 80, 170, 95, 130),    -- Mega-Gengar
    (10, 105, 125, 100, 60, 100, 100),-- Mega-Kangaskhan
    (11, 65, 155, 120, 65, 90, 105),  -- Mega-Pinsir
    (12, 95, 155, 109, 70, 130, 81),  -- Mega-Gyarados
    (13, 80, 135, 85, 70, 95, 150),    -- Mega-Aerodactyl
    (14, 106, 190, 100, 154, 100, 130),-- Mega-Mewtwo X
    (15, 106, 150, 70, 194, 120, 140); -- Mega-Mewtwo Y

insert into movimientos (id_movimiento, nombre, movimiento) values
	(1, 'Burbuja', row(40, 'Especial', 'Agua')),
	(2, 'Cascada', row(80, 'Fisico', 'Agua')),
	(3, 'Hidrobomba', row(110, 'Especial', 'Agua')),
	(4, 'Martillazo', row(100, 'Fisico', 'Agua')),
	(5, 'Pistola agua', row(40, 'Especial', 'Agua')),
	(6, 'Rayo burbuja', row(65, 'Especial', 'Agua')),
	(7, 'Refugio', row(null, 'Estado', 'Normal')),
	(8, 'Surf', row(90, 'Especial', 'Agua')),
	(9, 'Tenaza', row(35, 'Fisico', 'Agua')),
	(10, 'Chupavidas', row(80, 'Fisico', 'Bicho')),
	(11, 'Disparo demora', row(0, 'Estado', 'Bicho')),
	(12, 'Doble ataque', row(25, 'Fisico', 'Bicho')),
	(13, 'Pin misil', row(25, 'Fisico', 'Bicho')),
	(14, 'Furia dragón', row(0, 'Estado', 'Dragón')),
	(15, 'Impactrueno', row(40, 'Especial', 'Eléctrico')),
	(16, 'Onda trueno', row(0, 'Estado', 'Eléctrico')),
	(17, 'Puño trueno', row(75, 'Fisico', 'Eléctrico')),
	(18, 'Rayo', row(90, 'Especial', 'Eléctrico')),
	(19, 'Trueno', row(110, 'Especial', 'Eléctrico')),
	(20, 'Lengüetazo', row(30, 'Fisico', 'Fantasma')),
	(21, 'Rayo confuso', row(0, 'Estado', 'Fantasma')),
	(22, 'Tinieblas', row(null, 'Estado', 'Fantasma')),
	(23, 'Ascuas', row(40, 'Especial', 'Fuego')),
	(24, 'Giro fuego', row(35, 'Especial', 'Fuego')),
	(25, 'Lanzallamas', row(90, 'Especial', 'Fuego')),
	(26, 'Llamarada', row(110, 'Especial', 'Fuego')),
	(27, 'Puño fuego', row(75, 'Fisico', 'Fuego')),
	(28, 'Neblina', row(0, 'Estado', 'Hada')),
	(29, 'Niebla', row(0, 'Estado', 'Hada')),
	(30, 'Puño hielo', row(75, 'Fisico', 'Hielo')),
	(31, 'Rayo aurora', row(65, 'Especial', 'Hielo')),
	(32, 'Rayo hielo', row(90, 'Especial', 'Hielo')),
	(33, 'Ventisca', row(110, 'Especial', 'Hielo')),
	(34, 'Contraataque', row(0, 'Estado', 'Lucha')),
	(35, 'Doble patada', row(30, 'Fisico', 'Lucha')),
	(36, 'Golpe kárate', row(50, 'Fisico', 'Lucha')),
	(37, 'Patada baja', row(0, 'Fisico', 'Lucha')),
	(38, 'Patada giro', row(60, 'Fisico', 'Lucha')),
	(39, 'Patada salto', row(100, 'Fisico', 'Lucha')),
	(40, 'Patada salto alta', row(130, 'Fisico', 'Lucha')),
	(41, 'Sísmico', row(0, 'Estado', 'Lucha')),
	(42, 'Sumisión', row(80, 'Fisico', 'Lucha')),
	(43, 'Afilar', row(0, 'Estado', 'Normal')),
	(44, 'Agarre', row(55, 'Fisico', 'Normal')),
	(45, 'Anulación', row(0, 'Estado', 'Normal')),
	(46, 'Arañazo', row(40, 'Fisico', 'Normal')),
	(47, 'Atadura', row(15, 'Fisico', 'Normal')),
	(48, 'Ataque furia', row(15, 'Fisico', 'Normal')),
	(49, 'Ataque rápido', row(40, 'Fisico', 'Normal')),
	(50, 'Atizar', row(80, 'Fisico', 'Normal')),
	(51, 'Autodestrucción', row(200, 'Fisico', 'Normal')),
	(52, 'Beso amoroso', row(0, 'Estado', 'Normal')),
	(53, 'Bomba huevo', row(100, 'Fisico', 'Normal')),
	(54, 'Bomba sónica', row(0, 'Estado', 'Normal')),
	(55, 'Bombardeo', row(15, 'Especial', 'Normal')),
	(56, 'Cabezazo', row(130, 'Fisico', 'Normal')),
	(57, 'Canto', row(0, 'Estado', 'Normal')),
	(58, 'Chirrido', row(0, 'Estado', 'Normal')),
	(59, 'Clavo cañón', row(20, 'Fisico', 'Normal')),
	(60, 'Constricción', row(10, 'Fisico', 'Normal')),
	(61, 'Conversión', row(0, 'Estado', 'Normal')),
	(62, 'Conversión', row(0, 'Estado', 'Normal')),
	(63, 'Cornada', row(65, 'Fisico', 'Normal')),
	(64, 'Corte', row(50, 'Fisico', 'Normal')),
	(65, 'Cuchillada', row(70, 'Fisico', 'Normal')),
	(66, 'Danza espada', row(0, 'Estado', 'Normal')),
	(67, 'Derribo', row(90, 'Fisico', 'Normal')),
	(68, 'Desarrollo', row(0, 'Estado', 'Normal')),
	(69, 'Deslumbrar', row(0, 'Estado', 'Normal')),
	(70, 'Destello', row(0, 'Estado', 'Normal')),
	(71, 'Destructor', row(40, 'Fisico', 'Normal')),
	(72, 'Día de pago', row(40, 'Fisico', 'Normal')),
	(73, 'Doble bofetón', row(15, 'Fisico', 'Normal')),
	(74, 'Doble equipo', row(0, 'Estado', 'Normal')),
	(75, 'Doble filo', row(120, 'Fisico', 'Normal')),
	(76, 'Explosión', row(250, 'Fisico', 'Normal')),
	(77, 'Foco energía', row(0, 'Estado', 'Normal')),
	(78, 'Fortaleza', row(0, 'Estado', 'Normal')),
	(79, 'Fuerza', row(80, 'Fisico', 'Normal')),
	(80, 'Furia', row(0, 'Estado', 'Normal')),
	(81, 'Golpe cabeza', row(70, 'Fisico', 'Normal')),
	(82, 'Golpe cuerpo', row(85, 'Fisico', 'Normal')),
	(83, 'Golpes furia', row(18, 'Fisico', 'Normal')),
	(84, 'Gruñido', row(0, 'Estado', 'Normal')),
	(85, 'Guillotina', row(0, 'Estado', 'Normal')),
	(86, 'Hipercolmillo', row(80, 'Fisico', 'Normal')),
	(87, 'Hiperrayo', row(150, 'Especial', 'Normal')),
	(88, 'Látigo', row(0, 'Estado', 'Normal')),
	(89, 'Malicioso', row(0, 'Estado', 'Normal')),
	(90, 'Megapatada', row(120, 'Fisico', 'Normal')),
	(91, 'Megapuño', row(80, 'Fisico', 'Normal')),
	(92, 'Meteoros', row(60, 'Especial', 'Normal')),
	(93, 'Metrónomo', row(0, 'Estado', 'Normal')),
	(94, 'Mimético', row(0, 'Estado', 'Normal')),
	(95, 'Ovocuración', row(0, 'Estado', 'Normal')),
	(96, 'Pantalla de humo', row(0, 'Estado', 'Normal')),
	(97, 'Perforador', row(0, 'Estado', 'Normal')),
	(98, 'Pisotón', row(65, 'Fisico', 'Normal')),
	(99, 'Placaje', row(40, 'Fisico', 'Normal')),
	(100, 'Puño cometa', row(18, 'Fisico', 'Normal')),
	(101, 'Puño mareo', row(70, 'Fisico', 'Normal')),
	(102, 'Recuperación', row(0, 'Estado', 'Normal')),
	(103, 'Reducción', row(0, 'Estado', 'Normal')),
	(104, 'Remolino', row(0, 'Estado', 'Normal')),
	(105, 'Restricción', row(0, 'Estado', 'Normal')),
	(106, 'Rizo defensa', row(0, 'Estado', 'Normal')),
	(107, 'Rugido', row(0, 'Estado', 'Normal')),
	(108, 'Salpicadura', row(0, 'Estado', 'Normal')),
	(109, 'Saña', row(90, 'Fisico', 'Normal')),
	(110, 'Superdiente', row(80, 'Fisico', 'Normal')),
	(111, 'Supersónico', row(0, 'Estado', 'Normal')),
	(112, 'Sustituto', row(0, 'Estado', 'Normal')),
	(113, 'Transformación', row(0, 'Estado', 'Normal')),
	(114, 'Triataque', row(80, 'Especial', 'Normal')),
	(115, 'Venganza', row(0, 'Estado', 'Normal')),
	(116, 'Viento cortante', row(80, 'Especial', 'Normal')),
	(117, 'Absorber', row(20, 'Especial', 'Planta')),
	(118, 'Danza pétalo', row(120, 'Especial', 'Planta')),
	(119, 'Drenadoras', row(0, 'Estado', 'Planta')),
	(120, 'Espora', row(0, 'Estado', 'Planta')),
	(121, 'Hoja afilada', row(55, 'Fisico', 'Planta')),
	(122, 'Látigo cepa', row(45, 'Fisico', 'Planta')),
	(123, 'Megaagotar', row(40, 'Especial', 'Planta')),
	(124, 'Paralizador', row(0, 'Estado', 'Planta')),
	(125, 'Rayo solar', row(120, 'Especial', 'Planta')),
	(126, 'Somnífero', row(0, 'Estado', 'Planta')),
	(127, 'Agilidad', row(0, 'Estado', 'Psíquico')),
	(128, 'Amnesia', row(0, 'Estado', 'Psíquico')),
	(129, 'Barrera', row(0, 'Estado', 'Psíquico')),
	(130, 'Comesueños', row(100, 'Especial', 'Psíquico')),
	(131, 'Confusión', row(50, 'Especial', 'Psíquico')),
	(132, 'Descanso', row(0, 'Estado', 'Psíquico')),
	(133, 'Hipnosis', row(0, 'Estado', 'Psíquico')),
	(134, 'Kinético', row(0, 'Estado', 'Psíquico')),
	(135, 'Meditación', row(0, 'Estado', 'Psíquico')),
	(136, 'Pantalla de luz', row(0, 'Estado', 'Psíquico')),
	(137, 'Psicoonda', row(0, 'Estado', 'Psíquico')),
	(138, 'Psicorrayo', row(65, 'Especial', 'Psíquico')),
	(139, 'Psíquico', row(90, 'Especial', 'Psíquico')),
	(140, 'Reflejo', row(0, 'Estado', 'Psíquico')),
	(141, 'Teletransporte', row(0, 'Estado', 'Psíquico')),
	(142, 'Avalancha', row(75, 'Fisico', 'Roca')),
	(143, 'Lanzarrocas', row(50, 'Fisico', 'Roca')),
	(144, 'Mordisco', row(60, 'Fisico', 'Siniestro')),
	(145, 'Ataque arena', row(0, 'Estado', 'Tierra')),
	(146, 'Excavar', row(80, 'Fisico', 'Tierra')),
	(147, 'Fisura', row(0, 'Estado', 'Tierra')),
	(148, 'Hueso palo', row(65, 'Fisico', 'Tierra')),
	(149, 'Huesomerang', row(50, 'Fisico', 'Tierra')),
	(150, 'Terremoto', row(100, 'Fisico', 'Tierra')),
	(151, 'Ácido', row(40, 'Especial', 'Veneno')),
	(152, 'Armadura ácida', row(0, 'Estado', 'Veneno')),
	(153, 'Gas venenoso', row(0, 'Estado', 'Veneno')),
	(154, 'Picotazo veneno', row(15, 'Fisico', 'Veneno')),
	(155, 'Polución', row(20, 'Especial', 'Veneno')),
	(156, 'Polvo veneno', row(0, 'Estado', 'Veneno')),
	(157, 'Residuos', row(65, 'Especial', 'Veneno')),
	(158, 'Tóxico', row(0, 'Estado', 'Veneno')),
	(159, 'Ataque aéreo', row(140, 'Fisico', 'Volador')),
	(160, 'Ataque ala', row(60, 'Fisico', 'Volador')),
	(161, 'Espejo', row(0, 'Estado', 'Volador')),
	(162, 'Pico taladro', row(80, 'Fisico', 'Volador')),
	(163, 'Picotazo', row(35, 'Fisico', 'Volador')),
	(164, 'Tornado', row(40, 'Especial', 'Volador')),
	(165, 'Vuelo', row(90, 'Fisico', 'Volador'));