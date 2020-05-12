package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.model.Restaurant;
import com.herokuapp.voteforlunch.model.Vote;
import com.herokuapp.voteforlunch.repository.RestaurantRepository;
import com.herokuapp.voteforlunch.repository.VoteRepository;
import com.herokuapp.voteforlunch.service.RestaurantService;
import com.herokuapp.voteforlunch.to.RestaurantTo;
import com.herokuapp.voteforlunch.util.DateTimeUtil;
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

    private static final String ENTITY_NAME = "restaurant";

    @Autowired
    private RestaurantService service;

//    @Cacheable("restaurants")
    @GetMapping
    public List<RestaurantTo> getAll() {
        long userId = SecurityUtil.authUserId();
//        LocalDate today = LocalDate.now();
        LocalDate today = DateTimeUtil.TODAY;
        log.info("today {} menus - getAll", today);
        return service.getAllWithMenu(userId, today);
    }

    @GetMapping(value = "/{id}")
    public RestaurantTo getWithMenu(@PathVariable long id) {
        long userId = SecurityUtil.authUserId();
//        LocalDate today = LocalDate.now();
        LocalDate today = DateTimeUtil.TODAY;
        log.info("today {} menus - get by restaurant {}", today, id);
        return service.getWithMenu(userId, id, today);
    }
}
