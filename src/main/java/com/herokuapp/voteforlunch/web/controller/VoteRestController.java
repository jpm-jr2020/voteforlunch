package com.herokuapp.voteforlunch.web.controller;

import com.herokuapp.voteforlunch.model.Vote;
import com.herokuapp.voteforlunch.service.VoteService;
import com.herokuapp.voteforlunch.to.VoteTo;
import com.herokuapp.voteforlunch.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/votes";

    private final VoteService service;

    public VoteRestController(VoteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Vote> getAll() {
        long userId = SecurityUtil.authUserId();
        log.info("votes - user {} getAll", userId);
        return service.getAll(userId);
    }

    @GetMapping(value = "/filter")
    public List<Vote> getBetween(@RequestParam @Nullable LocalDate startDate,
                                 @RequestParam @Nullable LocalDate endDate) {
        long userId = SecurityUtil.authUserId();
        log.info("votes - user {} getBetween dates ({} - {})", userId, startDate, endDate);
        return service.getBetween(userId, startDate, endDate);
    }

    @GetMapping(value = "/{date}")
    public VoteTo getByDate(@PathVariable LocalDate date) {
        long userId = SecurityUtil.authUserId();
        log.info("votes - user {} getByDate {}", userId, date);
        return service.get(userId, date);
    }

    @PostMapping
    public ResponseEntity<VoteTo> vote(@RequestParam long restaurantId) {
        long userId = SecurityUtil.authUserId();
//        LocalDate date = LocalDate.now();
//        LocalDate date = DateTimeUtil.TODAY;
//        LocalTime time = LocalTime.now();
//        LocalTime time = LocalTime.of(10, 0);
        LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
//        LocalDateTime dateTime = LocalDateTime.of(date, time);
        log.info("vote - user {} for restaurant {} at {}", userId, restaurantId, dateTime);
        VoteTo voteTo = service.vote(userId, restaurantId, dateTime);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{date}")
                .buildAndExpand(voteTo.getDateTime().toLocalDate()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(voteTo);
    }
}
