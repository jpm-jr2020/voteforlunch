package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.model.Vote;
import com.herokuapp.voteforlunch.repository.DishRepository;
import com.herokuapp.voteforlunch.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.herokuapp.voteforlunch.util.DateTimeUtil.*;
import static com.herokuapp.voteforlunch.util.ValidationUtil.checkMenuPresent;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/votes/{userId}";

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private DishRepository dishRepository;

    @GetMapping
    public List<Vote> getAll(@PathVariable long userId) {
//        int userId = SecurityUtil.authUserId();
        log.info("votes - user {} getAll", userId);
        return voteRepository.getAll(userId);
    }

    @GetMapping(value = "/filter")
    public List<Vote> getBetween(@PathVariable long userId,
                                 @RequestParam @Nullable LocalDate startDate,
                                 @RequestParam @Nullable LocalDate endDate) {
//        int userId = SecurityUtil.authUserId();
        log.info("votes - user {} getBetween dates ({} - {})", userId, startDate, endDate);
        return voteRepository.getBetween(userId, dateToStartOfDay(startDate), dateToStartOfNextDay(endDate));
    }

    @GetMapping(value = "/{date}")
    public List<Vote> getByDate(@PathVariable long userId, @PathVariable LocalDate date) {
//        int userId = SecurityUtil.authUserId();
        log.info("votes - user {} getByDate {}", userId, date);
        return voteRepository.getBetween(userId, dateToStartOfDay(date), dateToStartOfNextDay(date));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void vote(@PathVariable long userId, @RequestParam long restaurantId) {

//        int userId = SecurityUtil.authUserId();

//        LocalDate date = LocalDate.now();
        LocalDate date = LocalDate.of(2020, 5, 1);
//        LocalTime time = LocalTime.now();
        LocalTime time = LocalTime.of(10, 0);
//        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime dateTime = LocalDateTime.of(2020, 5, 1, 10, 0);

        log.info("vote - user {} for restaurant {} at {}", userId, restaurantId, dateTime);

        checkMenuPresent(dishRepository.getByDate(restaurantId, date), restaurantId, date);

        List<Vote> votes = voteRepository.getBetween(userId, dateToStartOfDay(date), dateToStartOfNextDay(date));

        if (votes.isEmpty()) {
            Vote vote = new Vote();
            vote.setDateTime(dateTime);
            vote.setUserId(userId);
            voteRepository.save(vote, restaurantId);
        } else {
            checkCanRevote(time);
            Vote vote = votes.get(0);
            if (vote.getRestaurant().getId() != restaurantId) {
                vote.setDateTime(dateTime);
                voteRepository.save(vote, restaurantId);
            }
        }
    }
}
