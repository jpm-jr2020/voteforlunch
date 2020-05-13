package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.model.Dish;
import com.herokuapp.voteforlunch.to.MenuTo;
import com.herokuapp.voteforlunch.util.DateTimeUtil;

import java.util.*;

import static com.herokuapp.voteforlunch.web.DishTestData.*;
import static com.herokuapp.voteforlunch.web.RestaurantTestData.RESTAURANT_HI;

public class MenuTestData {
    public static TestMatcher<MenuTo> MENU_TO_MATCHER = TestMatcher.usingFieldsComparator(MenuTo.class);

    private static final Dish DISH__HI_YESTERDAY1 = new Dish(DISH_HI_YESTERDAY1, DateTimeUtil.YESTERDAY);
    private static final Dish DISH__HI_YESTERDAY2 = new Dish(DISH_HI_YESTERDAY2, DateTimeUtil.YESTERDAY);
    private static final Dish DISH__HI_TODAY1 = new Dish(DISH_HI_TODAY1, DateTimeUtil.TODAY);
    private static final Dish DISH__HI_TODAY2 = new Dish(DISH_HI_TODAY2, DateTimeUtil.TODAY);
    private static final Dish DISH__HI_TOMORROW1 = new Dish(DISH_HI_TOMORROW1, DateTimeUtil.TOMORROW);
    private static final Dish DISH__HI_TOMORROW2 = new Dish(DISH_HI_TOMORROW2, DateTimeUtil.TOMORROW);

    private static final List<Dish> DISHES_HI_YESTERDAY = new ArrayList<>();
    private static final List<Dish> DISHES_HI_TODAY = new ArrayList<>();
    private static final List<Dish> DISHES_HI_TOMORROW = new ArrayList<>();
    static {
        DISHES_HI_YESTERDAY.add(DISH__HI_YESTERDAY1);
        DISHES_HI_YESTERDAY.add(DISH__HI_YESTERDAY2);
        DISHES_HI_TODAY.add(DISH__HI_TODAY2);
        DISHES_HI_TODAY.add(DISH__HI_TODAY1);
        DISHES_HI_TOMORROW.add(DISH__HI_TOMORROW1);
        DISHES_HI_TOMORROW.add(DISH__HI_TOMORROW2);
    }

    private static final List<Dish> DISHES_LIST_HI_ALL_DAYS = new ArrayList<>();
    private static final List<Dish> DISHES_LIST_HI_NO_DAYS = new ArrayList<>();
    private static final List<Dish> DISHES_LIST_HI_TODAY = new ArrayList<>();
    private static final List<Dish> DISHES_LIST_HI_TODAY_TOMORROW = new ArrayList<>();
    private static final List<Dish> DISHES_LIST_HI_TOMORROW = new ArrayList<>();
    private static final List<Dish> DISHES_LIST_HI_YESTERDAY_TODAY = new ArrayList<>();
    
    static {
        DISHES_LIST_HI_ALL_DAYS.addAll(DISHES_HI_YESTERDAY);
        DISHES_LIST_HI_ALL_DAYS.addAll(DISHES_HI_TODAY);
        DISHES_LIST_HI_ALL_DAYS.addAll(DISHES_HI_TOMORROW);
        
        DISHES_LIST_HI_TODAY.addAll(DISHES_HI_TODAY);

        DISHES_LIST_HI_TODAY_TOMORROW.addAll(DISHES_HI_TODAY);
        DISHES_LIST_HI_TODAY_TOMORROW.addAll(DISHES_HI_TOMORROW);

        DISHES_LIST_HI_TOMORROW.addAll(DISHES_HI_TOMORROW);

        DISHES_LIST_HI_YESTERDAY_TODAY.addAll(DISHES_HI_YESTERDAY);
        DISHES_LIST_HI_YESTERDAY_TODAY.addAll(DISHES_HI_TODAY);
    }
    
    public static final MenuTo MENU_HI_ALL_DAYS = new MenuTo(RESTAURANT_HI, DISHES_LIST_HI_ALL_DAYS);
    public static final MenuTo MENU_HI_NO_DAYS = new MenuTo(RESTAURANT_HI, DISHES_LIST_HI_NO_DAYS);
    public static final MenuTo MENU_HI_TODAY = new MenuTo(RESTAURANT_HI, DISHES_LIST_HI_TODAY);
    public static final MenuTo MENU_HI_TODAY_TOMORROW = new MenuTo(RESTAURANT_HI, DISHES_LIST_HI_TODAY_TOMORROW);
    public static final MenuTo MENU_HI_TOMORROW = new MenuTo(RESTAURANT_HI, DISHES_LIST_HI_TOMORROW);
    public static final MenuTo MENU_HI_YESTERDAY_TODAY = new MenuTo(RESTAURANT_HI, DISHES_LIST_HI_YESTERDAY_TODAY);
}
