DELETE FROM votes;
DELETE FROM dishes;
-- DELETE FROM menus;
DELETE FROM restaurants;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('Иван', 'ivan@yandex.ru', '{noop}ivan'),         /*100_000*/
  ('Петр', 'petr@yandex.ru', '{noop}petr'),         /*100_001*/
  ('Анна', 'anna@yandex.ru', '{noop}anna'),         /*100_002*/
  ('Дарья', 'daria@yandex.ru', '{noop}daria'),      /*100_003*/
  ('Джон', 'john@gmail.com', '{noop}passJohn'),     /*100_004*/
  ('Инга', 'inga@gmail.com', '{noop}passInga');     /*100_005*/

INSERT INTO user_roles (role, user_id) VALUES
  ('USER', 100000),
  ('USER', 100001),
  ('USER', 100002),
  ('USER', 100003),
  ('ADMIN', 100004),
  ('USER', 100004),
  ('ADMIN', 100005),
  ('USER', 100005);

INSERT INTO restaurants (name, address) VALUES
  ('МакДональдс', 'Москва, Тверская, 22'),              /*100_006*/
  ('Бургер Кинг', 'Москва, Новый Арбат, 10'),           /*100_007*/
  ('Прага', 'Москва, Арбат, 1'),                        /*100_008*/
  ('Джонджоли', 'Москва, Авиамоторная, 34/2, стр. 1'),  /*100_009*/
  ('Хинкальная', 'Москва, Павелецкая, 13к4');           /*100_010*/

-- INSERT INTO menus (fk_restaurant_id, date) VALUES
-- (100006, '2020-04-29'),     /*100_011*/
-- (100006, '2020-04-30'),     /*100_012*/
-- (100006, '2020-05-01'),     /*100_013*/
--
-- (100007, '2020-04-29'),     /*100_014*/
-- (100007, '2020-04-30'),     /*100_015*/
-- (100007, '2020-05-01'),     /*100_016*/
--
-- (100008, '2020-04-29'),     /*100_017*/
-- (100008, '2020-04-30'),     /*100_018*/
-- (100008, '2020-05-01'),     /*100_019*/
--
-- (100009, '2020-04-29'),     /*100_020*/
-- (100009, '2020-04-30'),     /*100_021*/
-- (100009, '2020-05-01'),     /*100_022*/
--
-- (100010, '2020-04-29'),     /*100_023*/
-- (100010, '2020-04-30'),     /*100_024*/
-- (100010, '2020-05-01');     /*100_025*/

INSERT INTO dishes (name, price, date, fk_restaurant_id) VALUES
  ('Кофе', 7000, '2020-04-29', 100006),           /*100_011*/
  ('Гамбургер', 11000, '2020-04-29', 100006),     /*100_012*/
  ('Мороженное', 3000, '2020-04-29', 100006),     /*100_013*/
  ('Кофе', 7500, '2020-04-30', 100006),           /*100_014*/
  ('Чизбургер', 9900, '2020-04-30', 100006),      /*100_015*/
  ('Кола', 10000, '2020-05-01', 100006),          /*100_016*/
  ('Хэппи-мил', 18000, '2020-05-01', 100006),     /*100_017*/

  ('Воппер', 9900, '2020-04-29', 100007),         /*100_018*/
  ('Пиво', 7500, '2020-04-29', 100007),           /*100_019*/
  ('Картошка', 7000, '2020-04-30', 100007),       /*100_020*/
  ('Пиво', 9000, '2020-04-30', 100007),           /*100_021*/
  ('Кола', 8000, '2020-05-01', 100007),           /*100_022*/
  ('Соус', 2000, '2020-05-01', 100007),           /*100_023*/
  ('Бургер', 10500, '2020-05-01', 100007),        /*100_024*/

  ('Салат', 40000, '2020-04-29', 100008),         /*100_025*/
  ('Бифштекс', 80000, '2020-04-29', 100008),      /*100_026*/
  ('Салат', 40000, '2020-04-30', 100008),         /*100_027*/
  ('Котлета', 75000, '2020-04-30', 100008),       /*100_028*/
  ('Гарнир', 70000, '2020-05-01', 100008),        /*100_029*/
  ('Стейк', 95000, '2020-05-01', 100008),         /*100_030*/

  ('Пиво', 12000, '2020-04-29', 100009),          /*100_031*/
  ('Пицца', 14000, '2020-04-29', 100009),         /*100_032*/
  ('Чай', 9000, '2020-04-30', 100009),            /*100_033*/
  ('Аджапсандал', 17000, '2020-04-30', 100009),   /*100_034*/
  ('Сок', 11000, '2020-05-01', 100009),           /*100_035*/
  ('Джонджоли', 7000, '2020-05-01', 100009),      /*100_036*/

  ('Водка', 14000, '2020-04-29', 100010),         /*100_037*/
  ('Хинкали', 5000, '2020-04-29', 100010),        /*100_038*/
  ('Компот', 6000, '2020-04-30', 100010),         /*100_039*/
  ('Хачапури', 35000, '2020-04-30', 100010),      /*100_040*/
  ('Сациви', 12000, '2020-04-30', 100010),        /*100_041*/
  ('Вино', 15000, '2020-05-01', 100010),          /*100_042*/
  ('Пицца-дзе', 40000, '2020-05-01', 100010);     /*100_043*/

INSERT INTO votes (date_time, fk_user_id, fk_restaurant_id) VALUES
  ('2020-04-29 10:00:00', 100000, 100006),          /*100_044*/
  ('2020-04-29 15:00:00', 100002, 100008),          /*100_045*/
  ('2020-04-29 14:00:00', 100004, 100010),          /*100_046*/
  ('2020-04-29 20:00:00', 100005, 100007),          /*100_047*/
  ('2020-04-30 09:00:00', 100000, 100007),          /*100_048*/
  ('2020-04-30 14:00:00', 100001, 100009),          /*100_049*/
  ('2020-04-30 10:00:00', 100003, 100008),          /*100_050*/
  ('2020-04-30 10:00:00', 100004, 100009),          /*100_051*/
  ('2020-04-30 21:00:00', 100005, 100006),          /*100_052*/
  ('2020-05-01 21:00:00', 100001, 100006),          /*100_053*/
  ('2020-05-01 19:00:00', 100002, 100008),          /*100_054*/
  ('2020-05-01 15:00:00', 100005, 100010);          /*100_055*/
