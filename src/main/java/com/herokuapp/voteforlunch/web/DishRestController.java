package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.model.Dish;
import com.herokuapp.voteforlunch.repository.DishRepository;
import com.herokuapp.voteforlunch.to.DishTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

import static com.herokuapp.voteforlunch.util.ValidationUtil.*;

@RestController
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/admin/restaurants/{restaurantId}/menu/{date}/dishes";

    @Autowired
    private DishRepository repository;

    @GetMapping(value = "/{id}")
    public DishTo get(@PathVariable long id) {
        log.info("dishes - get {}", id);
        return new DishTo(checkNotFoundWithArg(repository.get(id), id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishTo> createWithLocation(@Validated @RequestBody DishTo dishTo,
                                                   @PathVariable LocalDate date, @PathVariable long restaurantId) {
        log.info("dishes - create {}", dishTo);
        Assert.notNull(dishTo, "dish must not be null");
        checkNew(dishTo);
        Dish created = repository.save(new Dish(dishTo, date), restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getDate(), created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(new DishTo(created));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody DishTo dishTo, @PathVariable long id,
                       @PathVariable LocalDate date, @PathVariable long restaurantId) {
        log.info("dishes - update {} with id={}", dishTo, id);
        Assert.notNull(dishTo, "dish must not be null");
        assureIdConsistent(dishTo, id);
        repository.save(new Dish(dishTo, date), restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        log.info("dishes - delete {}", id);
        checkNotFoundWithArg(repository.delete(id), id);
    }
}
