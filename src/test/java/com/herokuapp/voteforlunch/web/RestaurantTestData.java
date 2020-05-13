package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.model.Dish;
import com.herokuapp.voteforlunch.model.Restaurant;
import com.herokuapp.voteforlunch.to.RestaurantTo;

import java.util.ArrayList;
import java.util.List;

import static com.herokuapp.voteforlunch.model.AbstractEntity.*;
import static com.herokuapp.voteforlunch.util.DateTimeUtil.*;

public class RestaurantTestData {
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingFieldsComparator(Restaurant.class, "dishes");
    public static TestMatcher<RestaurantTo> RESTAURANT_TO_MATCHER = TestMatcher.usingFieldsComparator(RestaurantTo.class);

    private static final int START_SEQ_RESTAURANT = START_SEQ + NUM_OF_USERS;

    public static final long RESTAURANT_MD_ID = START_SEQ_RESTAURANT;
    public static final long RESTAURANT_PR_ID = START_SEQ_RESTAURANT + 1;
    public static final long RESTAURANT_HI_ID = START_SEQ_RESTAURANT + 2;

    public static final Restaurant RESTAURANT_MD = new Restaurant(RESTAURANT_MD_ID, "МакДональдс", "Москва, Тверская, 22");
    public static final Restaurant RESTAURANT_PR = new Restaurant(RESTAURANT_PR_ID, "Прага", "Москва, Арбат, 1");
    public static final Restaurant RESTAURANT_HI = new Restaurant(RESTAURANT_HI_ID, "Хинкальная", "Москва, Павелецкая, 13к4");

    public static final Restaurant RESTAURANT_TO_MD = new Restaurant(RESTAURANT_MD_ID, "МакДональдс", "Москва, Тверская, 22");
    public static final Restaurant RESTAURANT_TO_PR = new Restaurant(RESTAURANT_PR_ID, "Прага", "Москва, Арбат, 1");
    public static final Restaurant RESTAURANT_TO_HI = new Restaurant(RESTAURANT_HI_ID, "Хинкальная", "Москва, Павелецкая, 13к4");

    private static final List<Dish> DISHES_MD = new ArrayList<>();
    private static final List<Dish> DISHES_PR = new ArrayList<>();
    private static final List<Dish> DISHES_HI = new ArrayList<>();

    static {
        DISHES_MD.add(new Dish(START_SEQ + 8L, "Кофе", 7500, TODAY));
        DISHES_MD.add(new Dish(START_SEQ + 9L, "Чизбургер", 9900, TODAY));

        DISHES_PR.add(new Dish(START_SEQ + 15L, "Котлета", 75000, TODAY));
        DISHES_PR.add(new Dish(START_SEQ + 14L, "Салат", 40000, TODAY));

        DISHES_HI.add(new Dish(START_SEQ + 21L, "Сациви", 12000, TODAY));
        DISHES_HI.add(new Dish(START_SEQ + 20L, "Хачапури", 35000, TODAY));
    }

    static {
        RESTAURANT_TO_MD.setDishes(DISHES_MD);
        RESTAURANT_TO_PR.setDishes(DISHES_PR);
        RESTAURANT_TO_HI.setDishes(DISHES_HI);
    }

    public static final RestaurantTo RESTAURANTTO_MD = new RestaurantTo(RESTAURANT_TO_MD, false);
    public static final RestaurantTo RESTAURANTTO_PR = new RestaurantTo(RESTAURANT_TO_PR, false);
    public static final RestaurantTo RESTAURANTTO_HI = new RestaurantTo(RESTAURANT_TO_HI, false);
    public static final RestaurantTo RESTAURANTTO_MD_VOTED = new RestaurantTo(RESTAURANT_TO_MD, true);
    public static final RestaurantTo RESTAURANTTO_PR_VOTED = new RestaurantTo(RESTAURANT_TO_PR, true);
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
