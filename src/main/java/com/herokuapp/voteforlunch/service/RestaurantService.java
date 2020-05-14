package com.herokuapp.voteforlunch.service;

import com.herokuapp.voteforlunch.model.Restaurant;
import com.herokuapp.voteforlunch.repository.restaurant.RestaurantRepository;
import com.herokuapp.voteforlunch.repository.vote.VoteRepository;
import com.herokuapp.voteforlunch.to.RestaurantTo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.herokuapp.voteforlunch.util.ValidationUtil.*;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;

    private static final String ENTITY_NAME = "restaurant";

    public RestaurantService(RestaurantRepository restaurantRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @Cacheable("restaurants")
    public Restaurant get(long id) {
        return checkNotFoundWithArg(restaurantRepository.get(id), ENTITY_NAME, id);
    }

    @CacheEvict(value = {"restaurants", "restaurantTos", "menus", "votes"}, allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNew(restaurant);
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = {"restaurants", "restaurantTos", "menus", "votes"}, allEntries = true)
    @Transactional
    public void update(Restaurant restaurant, long id) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithArg(restaurantRepository.existsById(id), ENTITY_NAME, id);
        assureIdConsistent(restaurant, id);
        restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = {"restaurants", "restaurantTos", "menus", "votes"}, allEntries = true)
    public void delete(long id) {
        checkNotFoundWithArg(restaurantRepository.delete(id), ENTITY_NAME, id);
    }

    @Cacheable("restaurantTos")
    @Transactional
    public List<RestaurantTo> getAllWithMenu(long userId, LocalDate today) {
        List<Restaurant> restaurants = restaurantRepository.getAllWithMenu(today);
        Long votedRestaurantId = voteRepository.getRestaurantId(userId, today);

        return restaurants.stream()
                .map(restaurant -> new RestaurantTo(restaurant, restaurant.getId().equals(votedRestaurantId)))
                .collect(Collectors.toList());
    }

    @Cacheable("restaurantTos")
    @Transactional
    public RestaurantTo getWithMenu(long userId, long restaurantId, LocalDate today) {
        Restaurant restaurant = checkNotFoundWithArg(restaurantRepository.getWithMenu(restaurantId, today), ENTITY_NAME, restaurantId);
        Long votedRestaurantId = voteRepository.getRestaurantId(userId, today);

        return new RestaurantTo(restaurant, restaurant.getId().equals(votedRestaurantId));
    }
}