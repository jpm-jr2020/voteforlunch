package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.model.Restaurant;
import com.herokuapp.voteforlunch.model.User;

import java.util.Collections;
import java.util.Date;

import static com.herokuapp.voteforlunch.model.AbstractEntity.START_SEQ;

public class RestaurantTestData {
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingFieldsComparator(Restaurant.class, "dishes");
    public static TestMatcher<Restaurant> RESTAURANT_TO_MATCHER = TestMatcher.usingFieldsComparator(Restaurant.class, "todayMenu");

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
