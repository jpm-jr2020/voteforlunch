package com.herokuapp.voteforlunch.repository.vote;

import com.herokuapp.voteforlunch.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {

    // 1.5. История своих голосований
    List<Vote> getAll(long userId);

    // 1.6. История своих голосований за период
    // 1.7. Свое голосование за конкретную дату
     List<Vote> getBetween(long userId, LocalDate startDate, LocalDate endDate);

    Vote save(Vote vote, long restaurantId);

    Vote get(long userId, LocalDate date);
}

