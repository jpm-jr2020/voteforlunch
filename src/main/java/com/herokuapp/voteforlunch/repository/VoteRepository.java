package com.herokuapp.voteforlunch.repository;

import com.herokuapp.voteforlunch.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteRepository {

    // 1.5. История своих голосований
    List<Vote> getAll(long userId);

    // 1.6. История своих голосований за период
    // 1.7. Свое голосование за конкретную дату
     List<Vote> getBetween(long userId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Vote save(Vote vote, long restaurantId);

    Vote get(long userId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}

