package com.herokuapp.voteforlunch.repository;

import com.herokuapp.voteforlunch.model.Restaurant;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaRestaurantRepository implements RestaurantRepository {
    private static final Sort SORT_NAME_ADDRESS = Sort.by(Sort.Direction.ASC, "name", "address");

    private final CrudRestaurantRepository crudRepository;

    public DataJpaRestaurantRepository(CrudRestaurantRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public List<Restaurant> getAll() {
        return crudRepository.findAll(SORT_NAME_ADDRESS);
    }

    @Override
    public Restaurant get(long id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return crudRepository.save(restaurant);
    }

    @Override
    public boolean delete(long id) {
        return crudRepository.delete(id) != 0;
    }
}
