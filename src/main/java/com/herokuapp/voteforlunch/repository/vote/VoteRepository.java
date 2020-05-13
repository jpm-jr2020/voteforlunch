package com.herokuapp.voteforlunch.repository.vote;

import com.herokuapp.voteforlunch.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {

    List<Vote> getAll(long userId);

    List<Vote> getBetween(long userId, LocalDate startDate, LocalDate endDate);

    Vote getWithMenu(long userId, LocalDate date);

    Vote get(long userId, LocalDate date);

    Vote save(Vote vote, long restaurantId);

    Long getRestaurantId(long userId, LocalDate date);
}

