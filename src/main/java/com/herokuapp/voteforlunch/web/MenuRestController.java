package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.model.Dish;
import com.herokuapp.voteforlunch.model.Restaurant;
import com.herokuapp.voteforlunch.repository.DishRepository;
import com.herokuapp.voteforlunch.repository.RestaurantRepository;
import com.herokuapp.voteforlunch.to.MenuTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.herokuapp.voteforlunch.util.DateTimeUtil.nullDateToMax;
import static com.herokuapp.voteforlunch.util.DateTimeUtil.nullDateToMin;
import static com.herokuapp.voteforlunch.util.ValidationUtil.checkNotFoundWithArg;

@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/admin/restaurants/{restaurantId}/menu";

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping
    public MenuTo getAll(@PathVariable long restaurantId) {
        log.info("menus - getAll for restaurant {}", restaurantId);
        Restaurant restaurant = checkNotFoundWithArg(restaurantRepository.get(restaurantId), restaurantId);
        return new MenuTo(restaurant, dishRepository.getAll(restaurantId));
    }

    @GetMapping(value = "/filter")
    public MenuTo getBetween(@PathVariable long restaurantId,
                                 @RequestParam @Nullable LocalDate startDate,
                                 @RequestParam @Nullable LocalDate endDate) {
        log.info("menus - getBetween dates({} - {}) for restaurant {}", startDate, endDate, restaurantId);
        Restaurant restaurant = checkNotFoundWithArg(restaurantRepository.get(restaurantId), restaurantId);
        return new MenuTo(restaurant, dishRepository.getBetween(restaurantId, nullDateToMin(startDate), nullDateToMax(endDate)));
    }

    @GetMapping(value = "/{date}")
    public MenuTo getByDate(@PathVariable long restaurantId, @PathVariable LocalDate date) {
        log.info("menus - getByDate {} for restaurant {}", date, restaurantId);
        Restaurant restaurant = checkNotFoundWithArg(restaurantRepository.get(restaurantId), restaurantId);
        return new MenuTo(restaurant, dishRepository.getByDate(restaurantId, date));
    }
}
