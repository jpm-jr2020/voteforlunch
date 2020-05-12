package com.herokuapp.voteforlunch.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.herokuapp.voteforlunch.model.Dish;
import com.herokuapp.voteforlunch.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantTo extends AbstractTo {
    private String address;
    private boolean voted;
    private List<DishTo> todayMenu;

    @JsonCreator
    public RestaurantTo(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("address") String address,
                        @JsonProperty("todayMenu") List<Dish> todayMenu, @JsonProperty("voted") boolean voted) {
        super(id, name);
        this.address = address;
        this.voted = voted;
        this.todayMenu = loadMenu(todayMenu);
    }

    public RestaurantTo(Restaurant restaurant, boolean voted) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getDishes(), voted);
    }

    private List<DishTo> loadMenu(List<Dish> dishes) {
        todayMenu = new ArrayList<>();
        dishes.forEach(dish -> todayMenu.add(new DishTo(dish)));
        return todayMenu;
    }

    @Override
    public String toString() {
        return super.toString() +
                " '" + name + '\'' +
                " по адресу: " + address +
                (voted ? ", ЗА!" : "")  +
                ", меню: " + todayMenu;
    }
}
