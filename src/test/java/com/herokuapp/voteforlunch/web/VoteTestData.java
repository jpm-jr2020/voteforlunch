package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.model.Vote;
import com.herokuapp.voteforlunch.to.VoteTo;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.herokuapp.voteforlunch.util.DateTimeUtil.*;
import static com.herokuapp.voteforlunch.web.RestaurantTestData.*;
import static com.herokuapp.voteforlunch.web.UserTestData.*;

public class VoteTestData {
    public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingFieldsComparator(Vote.class, "id", "userId");
    public static TestMatcher<VoteTo> VOTE_TO_MATCHER = TestMatcher.usingFieldsComparator(VoteTo.class);

    public static Vote VOTE_YESTERDAY_ADMIN = new Vote(100047L, LocalDateTime.of(YESTERDAY, LocalTime.of(20, 0)),
            ADMIN_INGA_ID, RESTAURANT_TO_BK);
    public static Vote VOTE_TODAY_ADMIN = new Vote(100052L, LocalDateTime.of(TODAY, LocalTime.of(21, 0)),
            ADMIN_INGA_ID, RESTAURANT_TO_MD);
    public static Vote VOTE_TODAY_USER = new Vote(100049L, LocalDateTime.of(TODAY, LocalTime.of(14, 0)),
            USER_PETR_ID, RESTAURANT_TO_DD);
    public static Vote VOTE_TOMORROW_ADMIN = new Vote(100055L, LocalDateTime.of(TOMORROW, LocalTime.of(15, 0)),
            ADMIN_INGA_ID, RESTAURANT_TO_HI);
    public static Vote VOTE_TOMORROW_USER = new Vote(100053L, LocalDateTime.of(TOMORROW, LocalTime.of(21, 0)),
            USER_PETR_ID, RESTAURANT_TO_MD);

    public static Vote REVOTE_TODAY_ADMIN = new Vote(100056L, LocalDateTime.of(TODAY, LocalTime.of(10, 0)),
            ADMIN_INGA_ID, RESTAURANT_TO_DD);
    public static Vote REVOTE_TODAY_USER = new Vote(100057L, LocalDateTime.of(TODAY, LocalTime.of(10, 0)),
            USER_PETR_ID, RESTAURANT_TO_BK);


    public static VoteTo VOTE_TO_TODAY_ADMIN = new VoteTo(VOTE_TODAY_ADMIN);
    public static VoteTo VOTE_TO_TOMORROW_USER = new VoteTo(VOTE_TOMORROW_USER);

    public static VoteTo REVOTE_TO_TODAY_ADMIN = new VoteTo(REVOTE_TODAY_ADMIN);
    public static VoteTo REVOTE_TO_TODAY_USER = new VoteTo(REVOTE_TODAY_USER);
}
