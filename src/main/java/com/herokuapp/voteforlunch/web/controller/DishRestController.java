package com.herokuapp.voteforlunch.web.controller;

import com.herokuapp.voteforlunch.model.Dish;
import com.herokuapp.voteforlunch.service.DishService;
import com.herokuapp.voteforlunch.to.DishTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/admin/restaurants/{restaurantId}/menu/{date}/dishes";

    private final DishService dishService;

    public DishRestController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping(value = "/{id}")
    public DishTo get(@PathVariable long id, @PathVariable LocalDate date, @PathVariable long restaurantId) {
        log.info("dishes - get {}", id);
        return dishService.get(id, restaurantId, date);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishTo> createWithLocation(@Valid @RequestBody DishTo dishTo,
                                                   @PathVariable LocalDate date, @PathVariable long restaurantId) {
        log.info("dishes - create {}", dishTo);
        Dish created = dishService.create(dishTo, restaurantId, date);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getDate(), created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(new DishTo(created));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable long id,
                       @PathVariable LocalDate date, @PathVariable long restaurantId) {
        log.info("dishes - update {} with id={}", dishTo, id);
        dishService.update(dishTo, id, restaurantId, date);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id, @PathVariable LocalDate date, @PathVariable long restaurantId) {
        log.info("dishes - delete {}", id);
        dishService.delete(id, restaurantId, date);
    }
}
