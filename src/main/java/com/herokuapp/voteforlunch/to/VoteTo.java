package com.herokuapp.voteforlunch.to;

import com.herokuapp.voteforlunch.model.User;
import com.herokuapp.voteforlunch.model.Vote;

import java.time.LocalDateTime;

public class VoteTo {
    private LocalDateTime dateTime;
    private RestaurantTo restaurantTo;


    public VoteTo(Vote vote) {
        this.dateTime = vote.getDateTime();
        restaurantTo = new RestaurantTo(vote.getRestaurant(), true);
    }
}
