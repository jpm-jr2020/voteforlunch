package com.herokuapp.voteforlunch.repository;

import com.herokuapp.voteforlunch.model.Dish;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaDishRepository implements DishRepository {
    private final CrudDishRepository crudDishRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaDishRepository(CrudDishRepository crudDishRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudDishRepository = crudDishRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public List<Dish> getAll(long restaurantId) {
        return crudDishRepository.getAll(restaurantId);
    }

    @Override
    public List<Dish> getBetween(long restaurantId, LocalDate startDate, LocalDate endDate) {
        return crudDishRepository.getBetween(restaurantId, startDate, endDate);
    }

    @Override
    public List<Dish> getByDate(long restaurantId, LocalDate date) {
        return crudDishRepository.getByDate(restaurantId, date);
    }

    @Override
    public Dish get(long id) {
        return crudDishRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Dish save(Dish dish, long restaurantId) {
        if (!dish.isNew() && get(dish.id()) == null) {
            return null;
        }
        dish.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudDishRepository.save(dish);
    }

    @Override
    public boolean delete(long id) {
        return crudDishRepository.delete(id) != 0;
    }
}
