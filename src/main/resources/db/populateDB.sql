DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

CREATE FUNCTION date_to_str (dat DATE) RETURNS VARCHAR(255)
    RETURN cast(dat as VARCHAR(255));

CREATE FUNCTION yesterday_date () RETURNS DATE
    RETURN current_date - 1 DAY;

CREATE FUNCTION today_date () RETURNS DATE
    RETURN current_date;

CREATE FUNCTION tomorrow_date () RETURNS DATE
    RETURN current_date + 1 DAY;

INSERT INTO users (name, email, password) VALUES
  ('Иван', 'ivan@yandex.ru', '{noop}ivan'),         /*100_000*/
  ('Петр', 'petr@yandex.ru', '{noop}petr'),         /*100_001*/
  ('Инга', 'inga@gmail.com', '{noop}passInga');     /*100_002*/

INSERT INTO user_roles (role, user_id) VALUES
  ('USER', 100000),
  ('USER', 100001),
  ('ADMIN', 100002),
  ('USER', 100002);

INSERT INTO restaurants (name, address) VALUES
  ('МакДональдс', 'Москва, Тверская, 22'),              /*100_003*/
  ('Прага', 'Москва, Арбат, 1'),                        /*100_004*/
  ('Хинкальная', 'Москва, Павелецкая, 13к4');           /*100_005*/

INSERT INTO dishes (name, price, date, fk_restaurant_id) VALUES
  ('Кофе', 7000, date_to_str(yesterday_date()), 100003),           /*100_006*/
  ('Гамбургер', 11000, date_to_str(yesterday_date()), 100003),     /*100_007*/
  ('Кофе', 7500, date_to_str(today_date()), 100003),           /*100_008*/
  ('Чизбургер', 9900, date_to_str(today_date()), 100003),      /*100_009*/
  ('Кола', 10000, date_to_str(tomorrow_date()), 100003),          /*100_010*/
  ('Хэппи-мил', 18000, date_to_str(tomorrow_date()), 100003),     /*100_011*/

  ('Салат', 40000, date_to_str(yesterday_date()), 100004),         /*100_012*/
  ('Бифштекс', 80000, date_to_str(yesterday_date()), 100004),      /*100_013*/
  ('Салат', 40000, date_to_str(today_date()), 100004),         /*100_014*/
  ('Котлета', 75000, date_to_str(today_date()), 100004),       /*100_015*/
  ('Гарнир', 70000, date_to_str(tomorrow_date()), 100004),        /*100_016*/
  ('Стейк', 95000, date_to_str(tomorrow_date()), 100004),         /*100_017*/

  ('Водка', 14000, date_to_str(yesterday_date()), 100005),         /*100_018*/
  ('Хинкали', 5000, date_to_str(yesterday_date()), 100005),        /*100_019*/
  ('Хачапури', 35000, date_to_str(today_date()), 100005),      /*100_020*/
  ('Сациви', 12000, date_to_str(today_date()), 100005),        /*100_021*/
  ('Вино', 15000, date_to_str(tomorrow_date()), 100005),          /*100_022*/
  ('Пицца-дзе', 40000, date_to_str(tomorrow_date()), 100005);     /*100_023*/

INSERT INTO votes (date_time, fk_user_id, fk_restaurant_id) VALUES
  (date_to_str(yesterday_date()) + ' 10:00:00', 100000, 100003),          /*100_024*/
  (date_to_str(yesterday_date()) + ' 10:30:00', 100002, 100004),          /*100_025*/

  (date_to_str(today_date()) + ' 14:00:00', 100001, 100005),          /*100_026*/

  (date_to_str(tomorrow_date()) + ' 12:00:00', 100001, 100003),          /*100_027*/
  (date_to_str(tomorrow_date()) + ' 07:00:00', 100002, 100005);          /*100_028*/
