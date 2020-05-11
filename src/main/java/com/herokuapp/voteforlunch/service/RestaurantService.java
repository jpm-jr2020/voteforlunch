package com.herokuapp.voteforlunch.service;

import com.herokuapp.voteforlunch.model.Restaurant;
import com.herokuapp.voteforlunch.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.herokuapp.voteforlunch.util.ValidationUtil.*;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    private static final String ENTITY_NAME = "restaurant";

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

//    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    public Restaurant get(long id) {
        return checkNotFoundWithArg(repository.get(id), ENTITY_NAME, id);
    }

//    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNew(restaurant);
        return repository.save(restaurant);
    }

//    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(Restaurant restaurant, long id) {
        Assert.notNull(restaurant, "restaurant must not be null");
        assureIdConsistent(restaurant, id);
        checkNotFoundWithArg(repository.existsById(id), ENTITY_NAME, id);
        repository.save(restaurant);
    }

//    @CacheEvict(value = "users", allEntries = true)
    public void delete(long id) {
        checkNotFoundWithArg(repository.delete(id), ENTITY_NAME, id);
    }
}