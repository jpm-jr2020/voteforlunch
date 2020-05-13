package com.herokuapp.voteforlunch.repository.dish;

import com.herokuapp.voteforlunch.model.Dish;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository {
    List<Dish> getAll(long restaurantId);

    List<Dish> getBetween(long restaurantId, LocalDate startDate, LocalDate endDate);

    List<Dish> getByDate(long restaurantId, LocalDate date);

    Dish get(long id, long restaurantId, LocalDate date);

    Dish save(Dish dish, long restaurantId, LocalDate date);

    boolean delete(long id, long restaurantId, LocalDate date);

    boolean isMenuPresent(long restaurantId, LocalDate date);
}

