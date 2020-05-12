package com.herokuapp.voteforlunch.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.herokuapp.voteforlunch.model.Vote;

import java.time.LocalDateTime;

public class VoteTo {
    private LocalDateTime dateTime;
    private RestaurantTo restaurantTo;

    @JsonCreator
    public VoteTo(@JsonProperty("dateTime") LocalDateTime dateTime, @JsonProperty("restaurantTo") RestaurantTo restaurantTo) {
        this.dateTime = dateTime;
        this.restaurantTo = restaurantTo;
    }

    public VoteTo(Vote vote) {
        this.dateTime = vote.getDateTime();
        restaurantTo = new RestaurantTo(vote.getRestaurant(), true);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
