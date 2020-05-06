package com.herokuapp.voteforlunch.util;

import com.herokuapp.voteforlunch.model.Dish;
import com.herokuapp.voteforlunch.model.Restaurant;
import com.herokuapp.voteforlunch.to.RestaurantTo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RestaurantTestData {
    public static final Restaurant R1 = new Restaurant(1001L, "McDonalds", "Moscow, Tverskaya, 23");
    public static final Restaurant R2 = new Restaurant(1002L, "Burger King", "Moscow, Noviy Arbat, 10");
    public static final Restaurant R3 = new Restaurant(1003L, "Praha", "Moscow, Arbat, 1");
    public static final Restaurant R4 = new Restaurant(1004L, "Dzondzoli", "Moscow, Aviamotornaya, 15");

    private static final Set<Dish> RT1Menu = new HashSet<>();
    private static final Set<Dish> RT2Menu = new HashSet<>();
    private static final Set<Dish> RT3Menu = new HashSet<>();
    private static final Set<Dish> RT4Menu = new HashSet<>();

    static {
//        RT1Menu.add(new Dish(2001L, "Coffee", 7000));
//        RT1Menu.add(new Dish(2002L, "Hamburger", 12000));
//        RT1Menu.add(new Dish(2003L, "Icecream", 40000));
//
//        RT2Menu.add(new Dish(2011L, "Beer", 9900));
//        RT2Menu.add(new Dish(2012L, "Vopper", 7500));
//        RT2Menu.add(new Dish(2013L, "Cola", 4900));
//        RT2Menu.add(new Dish(2014L, "Potato free", 10000));
//
//        RT3Menu.add(new Dish(2021L, "Salad", 90000));
//        RT3Menu.add(new Dish(2022L, "Stake", 230000));
//
//        RT4Menu.add(new Dish(2031L, "Vodka", 12000));
//        RT4Menu.add(new Dish(2032L, "Hinkals", 50000));
//        RT4Menu.add(new Dish(2033L, "Hachapuri", 39000));
//        RT4Menu.add(new Dish(2034L, "Dzondzoli", 12000));
//        RT4Menu.add(new Dish(2035L, "Harcho", 25000));
    }

    public static final RestaurantTo RT1 = new RestaurantTo(1001L, "McDonalds", "Moscow, Tverskaya, 23", false, RT1Menu);
    public static final RestaurantTo RT2 = new RestaurantTo(1002L, "Burger King", "Moscow, Noviy Arbat, 10", false, RT2Menu);
    public static final RestaurantTo RT3 = new RestaurantTo(1003L, "Praha", "Moscow, Arbat, 1", false, RT3Menu);
    public static final RestaurantTo RT4 = new RestaurantTo(1004L, "Dzondzoli", "Moscow, Aviamotornaya, 15", true, RT4Menu);

    public static final RestaurantTo RT11 = new RestaurantTo(1001L, "McDonalds", "Moscow, Tverskaya, 23", false, null);
    public static final RestaurantTo RT12 = new RestaurantTo(1002L, "Burger King", "Moscow, Noviy Arbat, 10", false, Collections.emptySet());
    public static final RestaurantTo RT13 = new RestaurantTo(1003L, "Praha", "Moscow, Arbat, 1", false, null);
    public static final RestaurantTo RT14 = new RestaurantTo(1004L, "Dzondzoli", "Moscow, Aviamotornaya, 15", true, Collections.emptySet());

}
