
-- Users roles
insert into user_roles (role_id, role) values (1, 'User');

insert into user_roles (role_id, role) values (2, 'Admin');

-- Users
insert into users (email, first_name, last_name, password, shipping_address, username, role_role_id) values ('email@123.com', 'first', 'last', 'pass', 'location', 'user', 1);

insert into users (email, first_name, last_name, password, shipping_address, username, role_role_id) values ('admin@123.com', 'boss', 'man', 'pass', 'new york', 'bossman', 2);


-- Custom Pokemon Products
INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (51, 'Dugtrio', 7, 333, 3333.33, 'Powerpuff Girls', 'Sugar, spice, and everything nice', true);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (65, 'Alakazam', 15, 480, 539.99, 'Magic Hands', 'Pure at heart, but will teleport away if spooked', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (147, 'Dratini', 18, 33, 19.99, 'Cool Worm', 'I found this on the sidewalk', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (37, 'Vulpix', 5, 80, 234.99, 'Ember', 'Your favorite six-tailed fox', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (137, 'Porygon', 10, 500, 1699.99, 'Windows XP', 'The superior operating system', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (138, 'Omanyte', 4, 75, 1999.99, 'Shelly', 'Loves to cuddle', true);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (80, 'Slowbro', 22, 98.5, 2199.99, 'Thanos', 'I am inevitable', true);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (123, 'Scyther', 15, 560, 659.99, 'Mr. Shiv', 'He is quite sharp for his age', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (146, 'Moltres', 20, 600, 6249.99, 'Fire Pigeon', 'Big Fire-Winged Boi', true);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (89, 'Muk', 12, 300, 45.00, 'Big Slime Boy', 'Bio Hazard', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (137, 'Porygon', 8, 365, 8499.99, 'Kevin', 'Literally lives in the cyberworld', true);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (133, 'Eevee', 3, 65, 3220.00, 'Chinni', 'Cute little Chinni!', true);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (136, 'Flareon', 9, 250, 3499.99, 'Hot Boy 5', 'This boys temperature is over 9000 degrees', true);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (43, 'Oddish', 5, 54, 949.99, 'Carl', 'Even though Carl is virtually silent, he is an amazing emotional companion', true);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (104, 'Cubone', 4, 65, 1249.99, 'Spike', 'Big troublemaker, needs a lot of training and patience', true);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (11, 'Metapod', 7, 99, 4.99, 'James Harden', 'Cant do anything, but is nice to have around!', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (123, 'Scyther', 15, 560, 549.99, 'Double Razor', 'The last sound they hear is my blade. The unseen blade is the deadliest', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (132, 'Ditto', 1, 12, 1.99, 'Wad of Gum', 'Im not even sure if this is a pokemon', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (129, 'Magikarp', 18, 400, 1249.99, 'Jahkoob', '1 trick wonder <3', true);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (6, 'Charizard', 17, 905, 4249.99, 'Unrivaled', 'Get him attached to you before he heals', true);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (27, 'Sandshrew', 6, 120, 120.00, 'Sandy', 'She got the dirt on you', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (107, 'Hitmonchan', 14, 502, 349.99, 'Drunken Master', 'Disciple of Jackie Chan', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (76, 'Golem', 14, 3000, 349.99, 'The Rock', 'Failures not an option. Its just a step', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (39, 'Jigglypuff', 5, 55, 229.99, 'The Puffster', 'Smooth operator who is full of hot air', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (112, 'Rhydon', 19, 1200, 1549.99, 'Chawls', 'Hmmmmm.........', true);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (151, 'Mew', 4, 40, 7249.99, 'Moon', 'This Mew has been lost for centuries but we managed to persuade it', false);


-- Basic Pokemon Products
INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (94, 'Gengar', 15, 40, 125.01, 'Paul Blart', 'Big Smile', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, shiny) VALUES (25, 'Pikachu', 4, 40, 25.99, 'Flash', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, shiny) VALUES (25, 'Pikachu', 5, 60, 35.99, 'Chonk', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (133, 'Eevee', 3, 50, 215.49, 'Eevee', 'Infinite Possibilities', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (133, 'Eevee', 3, 50, 3500.49, 'Eon', 'Top Price', true);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (133, 'Eevee', 5, 70, 245.49, 'Chonk', 'Hefty', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (95, 'Onix', 100, 2300, 770.23, 'Wiggles III', 'Mossy', true);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, shiny) VALUES (95, 'Onix', 88, 2100, 330.23, 'Wiggles IV', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (144, 'Articuno', 16, 550, 1337.05, 'Mr Beaksy', 'Literally Articuno', false);

INSERT INTO pokemon_product (pokemon_id, pokemon_name, prod_height, prod_weight, prod_price, nickname, description, shiny) VALUES (129, 'Magikarp', 9, 100, 3.25, 'Flippy', 'Mostly Bone', false);


-- Carts
insert into carts (submitted, total, user_id) values (false, 0, 1); -- cart 1 (empty cart)

insert into carts (submitted, total, user_id) values (false, 0, 2); -- cart 2 (empty cart)
