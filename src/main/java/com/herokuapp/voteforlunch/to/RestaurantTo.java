package com.herokuapp.voteforlunch.to;

import com.herokuapp.voteforlunch.model.Dish;
import com.herokuapp.voteforlunch.model.Restaurant;

import java.util.Set;
import java.util.TreeSet;

public class RestaurantTo extends AbstractTo {
    private String address;
    private boolean voted;
    private Set<DishTo> todayMenu;

    public RestaurantTo(Restaurant restaurant, boolean voted) {
        super(restaurant.getId(), restaurant.getName());
        this.address = restaurant.getAddress();
        this.voted = voted;
        this.todayMenu = loadMenu(restaurant.getDishes());
    }

    private Set<DishTo> loadMenu(Set<Dish> dishes) {
        todayMenu = new TreeSet<>();
        dishes.forEach(dish -> todayMenu.add(new DishTo(dish)));
        return todayMenu;
    }

}
