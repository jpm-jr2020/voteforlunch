package com.herokuapp.voteforlunch.repository.restaurant;

import com.herokuapp.voteforlunch.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepository {

    List<Restaurant> getAll();

    Restaurant get(long id);

    Restaurant save(Restaurant restaurant);

    boolean delete(long id);

    Restaurant getWithMenu(long id, LocalDate date);

    List<Restaurant> getAllWithMenu(LocalDate date);

    boolean existsById(long id);
}
