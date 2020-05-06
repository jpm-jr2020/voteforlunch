package com.herokuapp.voteforlunch.to;

import com.herokuapp.voteforlunch.model.Dish;

import java.util.Set;

public class RestaurantTo extends AbstractTo {
    private String address;
    private Boolean voted;
    private Set<Dish> todayMenu;

    public RestaurantTo(Long id, String name, String address, Boolean voted) {
        this(id, name, address, voted, null);
    }

    public RestaurantTo(Long id, String name, String address, Boolean voted, Set<Dish> todayMenu) {
        super(id, name);
        this.address = address;
        this.voted = voted;
        this.todayMenu = todayMenu;
    }

    public String getAddress() {
        return address;
    }

    public Boolean getVoted() {
        return voted;
    }

    public Set<Dish> getTodayMenu() {
        return todayMenu;
    }
}
