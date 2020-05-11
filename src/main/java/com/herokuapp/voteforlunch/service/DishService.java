package com.herokuapp.voteforlunch.service;

import com.herokuapp.voteforlunch.model.Dish;
import com.herokuapp.voteforlunch.model.Restaurant;
import com.herokuapp.voteforlunch.repository.DishRepository;
import com.herokuapp.voteforlunch.repository.RestaurantRepository;
import com.herokuapp.voteforlunch.to.DishTo;
import com.herokuapp.voteforlunch.to.MenuTo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;

import static com.herokuapp.voteforlunch.util.DateTimeUtil.nullDateToMax;
import static com.herokuapp.voteforlunch.util.DateTimeUtil.nullDateToMin;
import static com.herokuapp.voteforlunch.util.ValidationUtil.*;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    private static final String ENTITY_NAME = "dish";

    public DishService(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public MenuTo getAll(long restaurantId) {
        Restaurant restaurant = checkNotFoundWithArg(restaurantRepository.get(restaurantId), "restaurant", restaurantId);
        return new MenuTo(restaurant, dishRepository.getAll(restaurantId));
    }

    public MenuTo getBetween(long restaurantId, LocalDate startDate, LocalDate endDate) {
        Restaurant restaurant = checkNotFoundWithArg(restaurantRepository.get(restaurantId), "restaurant", restaurantId);
        return new MenuTo(restaurant, dishRepository.getBetween(restaurantId, nullDateToMin(startDate), nullDateToMax(endDate)));
    }

    public MenuTo getByDate(long restaurantId, LocalDate date) {
        Restaurant restaurant = checkNotFoundWithArg(restaurantRepository.get(restaurantId), "restaurant", restaurantId);
        return new MenuTo(restaurant, dishRepository.getByDate(restaurantId, date));
    }

    public DishTo get(long id, long restaurantId, LocalDate date) {
        Dish dish = checkNotFoundWithArg(dishRepository.get(id, restaurantId, date), ENTITY_NAME, id);
        return new DishTo(dish);
    }

    public Dish create(DishTo dishTo, long restaurantId, LocalDate date) {
        Assert.notNull(dishTo, "dish must not be null");
        checkNew(dishTo);
        return checkNotFoundWithArg(dishRepository.save(new Dish(dishTo, date), restaurantId, date), "restaurant", restaurantId);
    }

    public void update(DishTo dishTo, long id, long restaurantId, LocalDate date) {
        Assert.notNull(dishTo, "dish must not be null");
        assureIdConsistent(dishTo, id);
        checkNotFoundWithArg(dishRepository.save(new Dish(dishTo, date), restaurantId, date), ENTITY_NAME, id);
    }

    public void delete(long id, long restaurantId, LocalDate date) {
        checkNotFoundWithArg(dishRepository.delete(id, restaurantId, date), ENTITY_NAME, id);
    }

}