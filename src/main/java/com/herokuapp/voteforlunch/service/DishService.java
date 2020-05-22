package com.herokuapp.voteforlunch.service;

import com.herokuapp.voteforlunch.model.Dish;
import com.herokuapp.voteforlunch.model.Restaurant;
import com.herokuapp.voteforlunch.repository.CrudRestaurantRepository;
import com.herokuapp.voteforlunch.repository.CrudDishRepository;
import com.herokuapp.voteforlunch.to.DishTo;
import com.herokuapp.voteforlunch.to.MenuTo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;

import static com.herokuapp.voteforlunch.util.DateTimeUtil.nullDateToMax;
import static com.herokuapp.voteforlunch.util.DateTimeUtil.nullDateToMin;
import static com.herokuapp.voteforlunch.util.ValidationUtil.*;

@Service
public class DishService {

    private final CrudDishRepository dishRepository;
    private final CrudRestaurantRepository restaurantRepository;

    private static final String ENTITY_NAME = "dish";

    public DishService(CrudDishRepository dishRepository, CrudRestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Cacheable(value = "menus")
    @Transactional
    public MenuTo getAll(long restaurantId) {
        Restaurant restaurant = checkNotFoundWithArg(restaurantRepository.findById(restaurantId).orElse(null), "restaurant", restaurantId);
        return new MenuTo(restaurant, dishRepository.getAll(restaurantId));
    }

    @Transactional
    public MenuTo getBetween(long restaurantId, LocalDate startDate, LocalDate endDate) {
        Restaurant restaurant = checkNotFoundWithArg(restaurantRepository.findById(restaurantId).orElse(null), "restaurant", restaurantId);
        return new MenuTo(restaurant, dishRepository.getBetween(restaurantId, nullDateToMin(startDate), nullDateToMax(endDate)));
    }

    @Cacheable(value = "menus")
    @Transactional
    public MenuTo getByDate(long restaurantId, LocalDate date) {
        Restaurant restaurant = checkNotFoundWithArg(restaurantRepository.findById(restaurantId).orElse(null), "restaurant", restaurantId);
        return new MenuTo(restaurant, dishRepository.getByDate(restaurantId, date));
    }

    @Cacheable(value = "menus")
    public DishTo get(long id, long restaurantId, LocalDate date) {
        Dish dish = checkNotFoundWithArg(dishRepository.findById(id)
                        .filter(d -> d.getRestaurant().getId() == restaurantId)
                        .filter(d -> d.getDate().isEqual(date))
                        .orElse(null),
                    ENTITY_NAME, id);
        return new DishTo(dish);
    }

    @Transactional
    @CacheEvict(value = {"restaurantTos", "menus"}, allEntries = true)
    public Dish create(DishTo dishTo, long restaurantId, LocalDate date) {
        Assert.notNull(dishTo, "dish must not be null");
        checkNew(dishTo);
        return checkNotFoundWithArg(save(new Dish(dishTo, date), restaurantId, date), "restaurant", restaurantId);
    }

    @Transactional
    @CacheEvict(value = {"restaurantTos", "menus"}, allEntries = true)
    public void update(DishTo dishTo, long id, long restaurantId, LocalDate date) {
        Assert.notNull(dishTo, "dish must not be null");
        assureIdConsistent(dishTo, id);
        checkNotFoundWithArg(save(new Dish(dishTo, date), restaurantId, date), ENTITY_NAME, id);
    }

    @CacheEvict(value = {"restaurantTos", "menus"}, allEntries = true)
    public void delete(long id, long restaurantId, LocalDate date) {
        checkNotFoundWithArg(dishRepository.delete(id, restaurantId, date) != 0, ENTITY_NAME, id);
    }

    private Dish save(Dish dish, long restaurantId, LocalDate date) {
        if (dish.isNew() && !restaurantRepository.existsById(restaurantId)) {
            return null;
        }

        if (!dish.isNew() && get(dish.id(), restaurantId, date) == null) {
            return null;
        }

        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        return dishRepository.save(dish);
    }

}