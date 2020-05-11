package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.service.DishService;
import com.herokuapp.voteforlunch.to.MenuTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/admin/restaurants/{restaurantId}/menu";

    @Autowired
    private DishService dishService;

    @GetMapping
    public MenuTo getAll(@PathVariable long restaurantId) {
        log.info("menus - getAll for restaurant {}", restaurantId);
        return dishService.getAll(restaurantId);
    }

    @GetMapping(value = "/filter")
    public MenuTo getBetween(@PathVariable long restaurantId,
                                 @RequestParam @Nullable LocalDate startDate,
                                 @RequestParam @Nullable LocalDate endDate) {
        log.info("menus - getBetween dates({} - {}) for restaurant {}", startDate, endDate, restaurantId);
        return dishService.getBetween(restaurantId, startDate, endDate);
    }

    @GetMapping(value = "/{date}")
    public MenuTo getByDate(@PathVariable long restaurantId, @PathVariable LocalDate date) {
        log.info("menus - getByDate {} for restaurant {}", date, restaurantId);
        return dishService.getByDate(restaurantId, date);
    }
}
