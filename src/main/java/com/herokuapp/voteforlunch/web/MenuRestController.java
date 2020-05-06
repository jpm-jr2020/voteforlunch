package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.model.Dish;
import com.herokuapp.voteforlunch.repository.DishRepository;
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

@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/admin/restaurants/{restaurantId}/menu";

    @Autowired
    private DishRepository repository;

    @GetMapping
    public List<Dish> getAll(@PathVariable long restaurantId) {
        log.info("menus - getAll for restaurant {}", restaurantId);
        return repository.getAll(restaurantId);
    }

    @GetMapping(value = "/filter")
    public List<Dish> getBetween(@PathVariable long restaurantId,
                                 @RequestParam @Nullable LocalDate startDate,
                                 @RequestParam @Nullable LocalDate endDate) {
        log.info("menus - getBetween dates({} - {}) for restaurant {}", startDate, endDate, restaurantId);
        return repository.getBetween(restaurantId, nullDateToMin(startDate), nullDateToMax(endDate));
    }

    @GetMapping(value = "/{date}")
    public List<Dish> getByDate(@PathVariable long restaurantId, @PathVariable LocalDate date) {
        log.info("menus - getByDate {} for restaurant {}", date, restaurantId);
        return repository.getByDate(restaurantId, date);
    }
}
