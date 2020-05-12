package com.herokuapp.voteforlunch.service;

import com.herokuapp.voteforlunch.model.Restaurant;
import com.herokuapp.voteforlunch.model.Vote;
import com.herokuapp.voteforlunch.repository.RestaurantRepository;
import com.herokuapp.voteforlunch.repository.VoteRepository;
import com.herokuapp.voteforlunch.to.RestaurantTo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.herokuapp.voteforlunch.util.DateTimeUtil.dateToStartOfDay;
import static com.herokuapp.voteforlunch.util.DateTimeUtil.dateToStartOfNextDay;
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

//    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    public Restaurant get(long id) {
        return checkNotFoundWithArg(restaurantRepository.get(id), ENTITY_NAME, id);
    }

//    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNew(restaurant);
        return restaurantRepository.save(restaurant);
    }

//    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(Restaurant restaurant, long id) {
        Assert.notNull(restaurant, "restaurant must not be null");
        assureIdConsistent(restaurant, id);
        checkNotFoundWithArg(restaurantRepository.existsById(id), ENTITY_NAME, id);
        restaurantRepository.save(restaurant);
    }

//    @CacheEvict(value = "users", allEntries = true)
    public void delete(long id) {
        checkNotFoundWithArg(restaurantRepository.delete(id), ENTITY_NAME, id);
    }

    public List<RestaurantTo> getAllWithMenu(long userId, LocalDate today) {
        List<Restaurant> restaurants = restaurantRepository.getAllWithMenu(today);
        List<Vote> votes = voteRepository.getBetween(userId, dateToStartOfDay(today), dateToStartOfNextDay(today));
        Restaurant votedRestaurant = votes.isEmpty() ? null : votes.get(0).getRestaurant();

        return restaurants.stream()
                .map(restaurant -> new RestaurantTo(restaurant, restaurant.equals(votedRestaurant)))
                .collect(Collectors.toList());
    }

    public RestaurantTo getWithMenu(long userId, long restaurantId, LocalDate today) {
        Restaurant restaurant = checkNotFoundWithArg(restaurantRepository.getWithMenu(restaurantId, today), ENTITY_NAME, restaurantId);
        List<Vote> votes = voteRepository.getBetween(userId, dateToStartOfDay(today), dateToStartOfNextDay(today));
        Restaurant votedRestaurant = votes.isEmpty() ? null : votes.get(0).getRestaurant();

        return new RestaurantTo(restaurant, restaurant.equals(votedRestaurant));
    }
}