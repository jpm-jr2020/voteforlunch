package com.herokuapp.voteforlunch.service;

import com.herokuapp.voteforlunch.model.Vote;
import com.herokuapp.voteforlunch.repository.dish.DishRepository;
import com.herokuapp.voteforlunch.repository.vote.VoteRepository;
import com.herokuapp.voteforlunch.to.VoteTo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.herokuapp.voteforlunch.util.DateTimeUtil.*;
import static com.herokuapp.voteforlunch.util.ValidationUtil.*;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    private final DishRepository dishRepository;

    public VoteService(VoteRepository voteRepository, DishRepository dishRepository) {
        this.voteRepository = voteRepository;
        this.dishRepository = dishRepository;
    }

    @Cacheable("votes")
    public List<Vote> getAll(long userId) {
        return voteRepository.getAll(userId);
    }

    public List<Vote> getBetween(long userId, LocalDate startDate, LocalDate endDate) {
        return voteRepository.getBetween(userId, startDate, endDate);
    }

    public VoteTo get(long userId, LocalDate date) {
        Vote vote = voteRepository.getWithMenu(userId, date);
        checkNotFoundWithArg(vote, "vote of user with id = " + userId + " for date = " + date);
        return new VoteTo(vote);
    }

    @CacheEvict(value = {"restaurants", "restaurantTos", "votes"}, allEntries = true)
    @Transactional
    public VoteTo vote(long userId, long restaurantId, LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        checkMenuPresent(dishRepository.isMenuPresent(restaurantId, date), restaurantId, date);
        checkVoteAbsent(voteRepository.get(userId, date));

        Vote vote = new Vote();
        vote.setUserId(userId);
        vote.setDateTime(dateTime);
        return new VoteTo(voteRepository.save(vote, restaurantId));
    }

    @CacheEvict(value = {"restaurants", "restaurantTos", "votes"}, allEntries = true)
    @Transactional
    public void revote(long userId, long restaurantId, LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        checkMenuPresent(dishRepository.isMenuPresent(restaurantId, date), restaurantId, date);
        Vote vote = voteRepository.get(userId, date);

        checkNotFoundWithArg(vote, "no vote exists for restaurant with id = " + restaurantId);
        checkCanRevote(dateTime.toLocalTime());

        vote.setDateTime(dateTime);
        voteRepository.save(vote, restaurantId);
    }
}