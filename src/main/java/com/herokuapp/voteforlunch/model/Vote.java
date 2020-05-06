package com.herokuapp.voteforlunch.model;

import java.time.LocalDateTime;

public class Vote extends AbstractEntity {
    private LocalDateTime dateTime;
    private User user;
    private Restaurant restaurant;
}
