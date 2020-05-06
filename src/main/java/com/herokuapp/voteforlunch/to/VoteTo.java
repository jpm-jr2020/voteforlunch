package com.herokuapp.voteforlunch.to;

import com.herokuapp.voteforlunch.model.User;

import java.time.LocalDateTime;

public class VoteTo {
    private Long id;
    private User user;
    private RestaurantTo restaurant;
    private LocalDateTime moment;

    public VoteTo(Long id, User user, RestaurantTo restaurant, LocalDateTime moment) {
        this.id = id;
        this.user = user;
        this.restaurant = restaurant;
        this.moment = moment;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public RestaurantTo getRestaurant() {
        return restaurant;
    }

    public LocalDateTime getMoment() {
        return moment;
    }
}
