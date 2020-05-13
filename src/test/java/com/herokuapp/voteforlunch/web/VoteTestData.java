package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.model.Vote;
import com.herokuapp.voteforlunch.to.VoteTo;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.herokuapp.voteforlunch.model.AbstractEntity.*;
import static com.herokuapp.voteforlunch.util.DateTimeUtil.*;
import static com.herokuapp.voteforlunch.web.RestaurantTestData.*;
import static com.herokuapp.voteforlunch.web.UserTestData.*;

public class VoteTestData {
    public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingFieldsComparator(Vote.class, "id", "userId");
    public static TestMatcher<VoteTo> VOTE_TO_MATCHER = TestMatcher.usingFieldsComparator(VoteTo.class);

    private static final long START_SEQ_VOTE = START_SEQ + NUM_OF_USERS + NUM_OF_RESTAURANTS + NUM_OF_DISHES;

    public static Vote VOTE_YESTERDAY_IVAN = new Vote(START_SEQ_VOTE,
            LocalDateTime.of(YESTERDAY, LocalTime.of(10, 0)), USER_IVAN_ID, RESTAURANT_TO_MD);
    public static Vote VOTE_YESTERDAY_ADMIN = new Vote(START_SEQ_VOTE + 1,
            LocalDateTime.of(YESTERDAY, LocalTime.of(10, 30)), ADMIN_INGA_ID, RESTAURANT_TO_PR);

    public static Vote VOTE_TODAY_IVAN = new Vote(START_SEQ_VOTE + 2,
            LocalDateTime.of(TODAY, LocalTime.of(9, 0)), USER_IVAN_ID, RESTAURANT_TO_PR);
    public static Vote VOTE_TODAY_PETR = new Vote(START_SEQ_VOTE + 3,
            LocalDateTime.of(TODAY, LocalTime.of(14, 0)), USER_PETR_ID, RESTAURANT_TO_HI);
    public static Vote VOTE_TODAY_ADMIN = new Vote(START_SEQ_VOTE + 4,
            LocalDateTime.of(TODAY, LocalTime.of(10, 10)), ADMIN_INGA_ID, RESTAURANT_TO_PR);

    public static Vote VOTE_TOMORROW_PETR = new Vote(START_SEQ_VOTE + 5,
            LocalDateTime.of(TOMORROW, LocalTime.of(12, 0)), USER_PETR_ID, RESTAURANT_TO_MD);
    public static Vote VOTE_TOMORROW_ADMIN = new Vote(START_SEQ_VOTE + 6,
            LocalDateTime.of(TOMORROW, LocalTime.of(7, 0)), ADMIN_INGA_ID, RESTAURANT_TO_HI);

    public static Vote REVOTE_TODAY_ADMIN = new Vote(START_SEQ_VOTE + 7,
            LocalDateTime.of(TODAY, LocalTime.of(10, 0)), ADMIN_INGA_ID, RESTAURANT_TO_MD);
    public static Vote REVOTE_TODAY_PETR = new Vote(START_SEQ_VOTE + 7,
            LocalDateTime.of(TODAY, LocalTime.of(10, 0)), USER_PETR_ID, RESTAURANT_TO_PR);


    public static VoteTo VOTE_TO_TODAY_ADMIN = new VoteTo(VOTE_TODAY_ADMIN);
    public static VoteTo VOTE_TO_TOMORROW_PETR = new VoteTo(VOTE_TOMORROW_PETR);

    public static VoteTo REVOTE_TO_TODAY_ADMIN = new VoteTo(REVOTE_TODAY_ADMIN);
    public static VoteTo REVOTE_TO_TODAY_PETR = new VoteTo(REVOTE_TODAY_PETR);
}
