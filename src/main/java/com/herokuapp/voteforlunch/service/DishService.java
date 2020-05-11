package com.herokuapp.voteforlunch.service;

import com.herokuapp.voteforlunch.model.Dish;
import com.herokuapp.voteforlunch.repository.DishRepository;
import com.herokuapp.voteforlunch.to.DishTo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;

import static com.herokuapp.voteforlunch.util.ValidationUtil.*;

@Service
public class DishService {

    private final DishRepository repository;

    private static final String ENTITY_NAME = "dish";

    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public DishTo get(long id, long restaurantId, LocalDate date) {
        Dish dish = checkNotFoundWithArg(repository.get(id, restaurantId, date), ENTITY_NAME, id);
        return new DishTo(dish);
    }

    public Dish create(DishTo dishTo, long restaurantId, LocalDate date) {
        Assert.notNull(dishTo, "dish must not be null");
        checkNew(dishTo);
        return checkNotFoundWithArg(repository.save(new Dish(dishTo, date), restaurantId, date), "restaurant", restaurantId);
    }

    public void update(DishTo dishTo, long id, long restaurantId, LocalDate date) {
        Assert.notNull(dishTo, "dish must not be null");
        assureIdConsistent(dishTo, id);
        checkNotFoundWithArg(repository.save(new Dish(dishTo, date), restaurantId, date), ENTITY_NAME, id);
    }

    public void delete(long id, long restaurantId, LocalDate date) {
        checkNotFoundWithArg(repository.delete(id, restaurantId, date), ENTITY_NAME, id);
    }

}