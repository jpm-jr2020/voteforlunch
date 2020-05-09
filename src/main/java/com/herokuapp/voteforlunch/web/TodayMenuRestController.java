package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.model.Restaurant;
import com.herokuapp.voteforlunch.model.Vote;
import com.herokuapp.voteforlunch.repository.RestaurantRepository;
import com.herokuapp.voteforlunch.repository.VoteRepository;
import com.herokuapp.voteforlunch.to.RestaurantTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.herokuapp.voteforlunch.util.DateTimeUtil.dateToStartOfDay;
import static com.herokuapp.voteforlunch.util.DateTimeUtil.dateToStartOfNextDay;
import static com.herokuapp.voteforlunch.util.ValidationUtil.*;

@RestController
@RequestMapping(value = TodayMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class TodayMenuRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/restaurants";

    @Autowired
    private RestaurantRepository repository;

    @Autowired
    private VoteRepository voteRepository;

    @Cacheable("restaurants")
    @GetMapping
    public List<RestaurantTo> getAll(@RequestParam long userId) {
//        LocalDate today = LocalDate.now();
        LocalDate today = LocalDate.of(2020, 5, 1);
        log.info("today {} menus - getAll", today);
        List<Restaurant> restaurants = repository.getAllWithMenu(today);
        List<Vote> votes = voteRepository.getBetween(userId, dateToStartOfDay(today), dateToStartOfNextDay(today));
        List<RestaurantTo> restaurantTos = new ArrayList<>();
        restaurants.forEach(restaurant -> restaurantTos.add(new RestaurantTo(restaurant, votes.isEmpty() ? false : votes.get(0).getRestaurant().equals(restaurant))));
        return restaurantTos;
    }

    @GetMapping(value = "/{id}")
    public RestaurantTo getWithMenu(@PathVariable long id, @RequestParam long userId) {
//        int userId = SecurityUtil.authUserId();
//        LocalDate today = LocalDate.now();
        LocalDate today = LocalDate.of(2020, 5, 1);
        log.info("today {} menus - get by restaurant {}", today, id);
        Restaurant restaurant = checkNotFoundWithArg(repository.getWithMenu(id, today), id);
        List<Vote> votes = voteRepository.getBetween(userId, dateToStartOfDay(today), dateToStartOfNextDay(today));
        return new RestaurantTo(restaurant, votes.isEmpty() ? false : votes.get(0).getRestaurant().equals(restaurant));
    }
}
