package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.model.Dish;
import com.herokuapp.voteforlunch.model.Restaurant;
import com.herokuapp.voteforlunch.model.User;
import com.herokuapp.voteforlunch.to.RestaurantTo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.herokuapp.voteforlunch.model.AbstractEntity.START_SEQ;
import static com.herokuapp.voteforlunch.util.DateTimeUtil.*;

public class RestaurantTestData {
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingFieldsComparator(Restaurant.class, "dishes");
    public static TestMatcher<RestaurantTo> RESTAURANT_TO_MATCHER = TestMatcher.usingFieldsComparator(RestaurantTo.class);

    public static final long RESTAURANT_MD_ID = START_SEQ + 6;
    public static final long RESTAURANT_BK_ID = START_SEQ + 7;
    public static final long RESTAURANT_PR_ID = START_SEQ + 8;
    public static final long RESTAURANT_DD_ID = START_SEQ + 9;
    public static final long RESTAURANT_HI_ID = START_SEQ + 10;

    public static final Restaurant RESTAURANT_MD = new Restaurant(RESTAURANT_MD_ID, "МакДональдс", "Москва, Тверская, 22");
    public static final Restaurant RESTAURANT_BK = new Restaurant(RESTAURANT_BK_ID, "Бургер Кинг", "Москва, Новый Арбат, 10");
    public static final Restaurant RESTAURANT_PR = new Restaurant(RESTAURANT_PR_ID, "Прага", "Москва, Арбат, 1");
    public static final Restaurant RESTAURANT_DD = new Restaurant(RESTAURANT_DD_ID, "Джонджоли", "Москва, Авиамоторная, 34/2, стр. 1");
    public static final Restaurant RESTAURANT_HI = new Restaurant(RESTAURANT_HI_ID, "Хинкальная", "Москва, Павелецкая, 13к4");

    public static final Restaurant RESTAURANT_TO_MD = new Restaurant(RESTAURANT_MD_ID, "МакДональдс", "Москва, Тверская, 22");
    public static final Restaurant RESTAURANT_TO_BK = new Restaurant(RESTAURANT_BK_ID, "Бургер Кинг", "Москва, Новый Арбат, 10");
    public static final Restaurant RESTAURANT_TO_PR = new Restaurant(RESTAURANT_PR_ID, "Прага", "Москва, Арбат, 1");
    public static final Restaurant RESTAURANT_TO_DD = new Restaurant(RESTAURANT_DD_ID, "Джонджоли", "Москва, Авиамоторная, 34/2, стр. 1");
    public static final Restaurant RESTAURANT_TO_HI = new Restaurant(RESTAURANT_HI_ID, "Хинкальная", "Москва, Павелецкая, 13к4");

    private static final List<Dish> DISHES_MD = new ArrayList<>();
    private static final List<Dish> DISHES_BK = new ArrayList<>();
    private static final List<Dish> DISHES_PR = new ArrayList<>();
    private static final List<Dish> DISHES_DD = new ArrayList<>();
    private static final List<Dish> DISHES_HI = new ArrayList<>();

    static {
//        DISHES_MD.add(new Dish(START_SEQ + 11L, "Кофе", 7000, YESTERDAY));
//        DISHES_MD.add(new Dish(START_SEQ + 12L, "Гамбургер", 11000, YESTERDAY));
//        DISHES_MD.add(new Dish(START_SEQ + 13L, "Мороженное", 3000, YESTERDAY));
        DISHES_MD.add(new Dish(START_SEQ + 14L, "Кофе", 7500, TODAY));
        DISHES_MD.add(new Dish(START_SEQ + 15L, "Чизбургер", 9900, TODAY));
//        DISHES_MD.add(new Dish(START_SEQ + 16L, "Кола", 10000, TOMORROW));
//        DISHES_MD.add(new Dish(START_SEQ + 17L, "Хэппи-мил", 18000, TOMORROW));

//        DISHES_BK.add(new Dish(START_SEQ + 18L, "Воппер", 9900, YESTERDAY));
//        DISHES_BK.add(new Dish(START_SEQ + 19L, "Пиво", 7500, YESTERDAY));
        DISHES_BK.add(new Dish(START_SEQ + 20L, "Картошка", 7000, TODAY));
        DISHES_BK.add(new Dish(START_SEQ + 21L, "Пиво", 9000, TODAY));
//        DISHES_BK.add(new Dish(START_SEQ + 22L, "Кола", 8000, TOMORROW));
//        DISHES_BK.add(new Dish(START_SEQ + 23L, "Соус", 2000, TOMORROW));
//        DISHES_BK.add(new Dish(START_SEQ + 24L, "Бургер", 10500, TOMORROW));

//        DISHES_PR.add(new Dish(START_SEQ + 25L, "Салат", 40000, YESTERDAY));
//        DISHES_PR.add(new Dish(START_SEQ + 26L, "Бифштекс", 80000, YESTERDAY));
        DISHES_PR.add(new Dish(START_SEQ + 27L, "Салат", 40000, TODAY));
        DISHES_PR.add(new Dish(START_SEQ + 28L, "Котлета", 75000, TODAY));
//        DISHES_PR.add(new Dish(START_SEQ + 29L, "Гарнир", 70000, TOMORROW));
//        DISHES_PR.add(new Dish(START_SEQ + 30L, "Стейк", 95000, TOMORROW));

//        DISHES_DD.add(new Dish(START_SEQ + 31L, "Пиво", 12000, YESTERDAY));
//        DISHES_DD.add(new Dish(START_SEQ + 32L, "Пицца", 14000, YESTERDAY));
        DISHES_DD.add(new Dish(START_SEQ + 33L, "Чай", 9000, TODAY));
        DISHES_DD.add(new Dish(START_SEQ + 34L, "Аджапсандал", 17000, TODAY));
//        DISHES_DD.add(new Dish(START_SEQ + 35L, "Сок", 11000, TOMORROW));
//        DISHES_DD.add(new Dish(START_SEQ + 36L, "Джонджоли", 7000, TOMORROW));

//        DISHES_HI.add(new Dish(START_SEQ + 37L, "Водка", 14000, YESTERDAY));
//        DISHES_HI.add(new Dish(START_SEQ + 38L, "Хинкали", 5000, YESTERDAY));
        DISHES_HI.add(new Dish(START_SEQ + 39L, "Компот", 6000, TODAY));
        DISHES_HI.add(new Dish(START_SEQ + 40L, "Хачапури", 35000, TODAY));
        DISHES_HI.add(new Dish(START_SEQ + 41L, "Сациви", 12000, TODAY));
//        DISHES_HI.add(new Dish(START_SEQ + 42L, "Вино", 15000, TOMORROW));
//        DISHES_HI.add(new Dish(START_SEQ + 43L, "Пицца-дзе", 40000, TOMORROW));
    }

    static {
        RESTAURANT_TO_MD.setDishes(DISHES_MD);
        RESTAURANT_TO_BK.setDishes(DISHES_BK);
        RESTAURANT_TO_PR.setDishes(DISHES_PR);
        RESTAURANT_TO_DD.setDishes(DISHES_DD);
        RESTAURANT_TO_HI.setDishes(DISHES_HI);
    }

    public static final RestaurantTo RESTAURANTTO_MD = new RestaurantTo(RESTAURANT_TO_MD, false);
    public static final RestaurantTo RESTAURANTTO_BK = new RestaurantTo(RESTAURANT_TO_BK, false);
    public static final RestaurantTo RESTAURANTTO_PR = new RestaurantTo(RESTAURANT_TO_PR, false);
    public static final RestaurantTo RESTAURANTTO_DD = new RestaurantTo(RESTAURANT_TO_DD, false);
    public static final RestaurantTo RESTAURANTTO_HI = new RestaurantTo(RESTAURANT_TO_HI, false);
    public static final RestaurantTo RESTAURANTTO_MD_VOTED = new RestaurantTo(RESTAURANT_TO_MD, true);
    public static final RestaurantTo RESTAURANTTO_BK_VOTED = new RestaurantTo(RESTAURANT_TO_BK, true);
    public static final RestaurantTo RESTAURANTTO_PR_VOTED = new RestaurantTo(RESTAURANT_TO_PR, true);
    public static final RestaurantTo RESTAURANTTO_DD_VOTED = new RestaurantTo(RESTAURANT_TO_DD, true);
    public static final RestaurantTo RESTAURANTTO_HI_VOTED = new RestaurantTo(RESTAURANT_TO_HI, true);

    public static Restaurant getNew() {
        return new Restaurant(null, "Новый ресторан", "Москва, Красная площадь, д. 1");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(RESTAURANT_PR_ID, RESTAURANT_PR.getName(), RESTAURANT_PR.getAddress());
        updated.setName("Прага СПб");
        updated.setAddress("СПб, Невский проспект, 1");
        return updated;
    }
}
