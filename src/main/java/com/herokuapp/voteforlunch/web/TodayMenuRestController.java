package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.model.Restaurant;
import com.herokuapp.voteforlunch.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.herokuapp.voteforlunch.util.ValidationUtil.*;

@RestController
@RequestMapping(value = TodayMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class TodayMenuRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/restaurants";

    @Autowired
    private RestaurantRepository repository;

    @Cacheable("restaurants")
    @GetMapping
    public List<Restaurant> getAll() {
//        LocalDate today = LocalDate.now();
        LocalDate today = LocalDate.of(2020, 5, 1);
        log.info("today {} menus - getAll", today);
        return repository.getAllWithMenu(today);
    }

    @GetMapping(value = "/{id}")
    public Restaurant getWithMenu(@PathVariable long id) {
//        LocalDate today = LocalDate.now();
        LocalDate today = LocalDate.of(2020, 5, 1);
        log.info("today {} menus - get by restaurant {}", today, id);
        return checkNotFoundWithArg(repository.getWithMenu(id, today), id);
    }
}
