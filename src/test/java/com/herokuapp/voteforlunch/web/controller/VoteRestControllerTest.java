package com.herokuapp.voteforlunch.web.controller;

import com.herokuapp.voteforlunch.model.Vote;
import com.herokuapp.voteforlunch.service.DishService;
import com.herokuapp.voteforlunch.service.VoteService;
import com.herokuapp.voteforlunch.to.VoteTo;
import com.herokuapp.voteforlunch.util.DateTimeUtil;
import com.herokuapp.voteforlunch.web.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static com.herokuapp.voteforlunch.util.DateTimeUtil.*;
import static com.herokuapp.voteforlunch.web.DishTestData.*;
import static com.herokuapp.voteforlunch.web.RestaurantTestData.*;
import static com.herokuapp.voteforlunch.web.TestUtil.userHttpBasic;
import static com.herokuapp.voteforlunch.web.UserTestData.*;
import static com.herokuapp.voteforlunch.web.VoteTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteRestController.REST_URL + '/';

    @Autowired
    private VoteService service;

    @Autowired
    private DishService dishService;

    // GET ALL tests

    @Test
    void getAllByAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE_TOMORROW_ADMIN, VOTE_YESTERDAY_ADMIN))
                .andDo(print());
    }

    @Test
    void getAllByUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER_PETR)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE_TOMORROW_PETR, VOTE_TODAY_PETR))
                .andDo(print());
    }

    @Test
    void getAllByUnAuth() throws Exception {
        super.getByUnAuth(REST_URL);
    }

    // GET BETWEEN tests

    @Test
    void getByAdminAllDays() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE_TOMORROW_ADMIN, VOTE_YESTERDAY_ADMIN))
                .andDo(print());
    }

    @Test
    void getByAdminTillToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter?endDate=" + TODAY)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(new Vote[] {VOTE_YESTERDAY_ADMIN}))
                .andDo(print());
    }

    @Test
    void getByAdminFromToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter?startDate=" + TODAY)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(new Vote[] {VOTE_TOMORROW_ADMIN}))
                .andDo(print());
    }

    @Test
    void getByAdminFromTodayTillTomorrow() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter?startDate=" + TODAY + "&endDate=" + TOMORROW)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(new Vote[] {VOTE_TOMORROW_ADMIN}))
                .andDo(print());
    }

    @Test
    void getByAdminNoDays() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter?startDate=" + TOMORROW + "&endDate=" + YESTERDAY)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson())
                .andDo(print());
    }

    @Test
    void getByUserAllDays() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .with(userHttpBasic(USER_PETR)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE_TOMORROW_PETR, VOTE_TODAY_PETR))
                .andDo(print());
    }

    @Test
    void getByUnAuthAllDays() throws Exception {
        super.getByUnAuth(REST_URL + "filter");
    }

    @Test
    void getInvalidStart() throws Exception {
        super.getWithValidationError(REST_URL + "filter&startDate=abc");
    }

    @Test
    void getInvalidEnd() throws Exception {
        super.getWithValidationError(REST_URL + "filter&endDate=abc");
    }

    // GET DATE tests

    @Test
    void getByUserToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + TODAY)
                .with(userHttpBasic(USER_PETR)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VOTE_TO_TODAY_PETR))
                .andDo(print());
    }

    @Test
    void getByAdminOtherDay() throws Exception {
        super.getWithNotFoundError(REST_URL + TOMORROW.plus(1, ChronoUnit.DAYS));
    }

    @Test
    void getByUserTomorrow() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + TOMORROW)
                .with(userHttpBasic(USER_PETR)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VOTE_TO_TOMORROW_PETR))
                .andDo(print());
    }

    @Test
    void getByUnAuthToday() throws Exception {
        super.getByUnAuth(REST_URL + TODAY);
    }

    @Test
    void getInvalidDate() throws Exception {
        super.getWithValidationError(REST_URL + "abc");
    }

    // VOTE tests

    @Test
    void voteByAdmin() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=" + RESTAURANT_MD_ID)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

        VoteTo voted = JsonUtil.readValue(action.andReturn().getResponse().getContentAsString(), VoteTo.class);
        LocalDate date = voted.getDateTime().toLocalDate();
        VoteTo newVoteTo = VOTE_TO_TODAY_ADMIN;
        VOTE_TO_MATCHER_NO_DATETIME.assertMatch(voted, newVoteTo);
        VOTE_TO_MATCHER_NO_DATETIME.assertMatch(service.get(ADMIN_INGA_ID, date), newVoteTo);
    }

    @Test
    void voteByUser() throws Exception {
        DateTimeUtil.setNoRevoteTime(LocalTime.of(23,59));
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=" + RESTAURANT_PR_ID)
                .with(userHttpBasic(USER_IVAN)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

        VoteTo voted = JsonUtil.readValue(action.andReturn().getResponse().getContentAsString(), VoteTo.class);
        LocalDate date = voted.getDateTime().toLocalDate();
        VoteTo newVoteTo = VOTE_TO_TODAY_IVAN;
        VOTE_TO_MATCHER_NO_DATETIME.assertMatch(voted, newVoteTo);
        VOTE_TO_MATCHER_NO_DATETIME.assertMatch(service.get(USER_IVAN_ID, date), newVoteTo);
        DateTimeUtil.setNoRevoteTime(LocalTime.of(11,0));
    }

    @Test
    void voteByUnAuth() throws Exception {
        super.postByUnAuth(REST_URL + "?restaurantId=" + RESTAURANT_PR_ID);
    }

    @Test
    void voteInvalidRestarant() throws Exception {
        super.postWithValidationError(REST_URL + "?restaurantId=abc", USER_IVAN);
    }

    @Test
    void voteNullRestarant() throws Exception {
        super.postWithBadRequest(REST_URL, USER_IVAN);
    }

    @Test
    void voteNoRestaurant() throws Exception {
        super.postWithNotFoundError(REST_URL + "?restaurantId=1", USER_IVAN);
    }

    @Test
    void voteNoMenu() throws Exception {
       dishService.delete(DISH_HI_TODAY1_ID, RESTAURANT_HI_ID, TODAY);
       dishService.delete(DISH_HI_TODAY2_ID, RESTAURANT_HI_ID, TODAY);

       super.postWithNotFoundError(REST_URL + "?restaurantId=" + RESTAURANT_HI_ID,USER_IVAN);
    }

    @Test
    void voteAlreadyVoted() throws Exception {
        super.postWithValidationError(REST_URL + "?restaurantId=" + RESTAURANT_HI_ID, USER_PETR);
    }

    @Test
    void revoteByUser() throws Exception {
        DateTimeUtil.setNoRevoteTime(LocalTime.of(23,59));
        perform(MockMvcRequestBuilders.put(REST_URL + "?restaurantId=" + RESTAURANT_PR_ID)
                .with(userHttpBasic(USER_PETR)))
                .andExpect(status().isNoContent())
                .andDo(print());

        VOTE_TO_MATCHER_NO_DATETIME.assertMatch(service.get(USER_PETR_ID, TODAY), REVOTE_TO_TODAY_PETR);
        DateTimeUtil.setNoRevoteTime(LocalTime.of(11,0));
    }

    @Test
    void revoteByUnAuth() throws Exception {
        super.putByUnAuth(REST_URL + "?restaurantId=" + RESTAURANT_PR_ID);
    }

    @Test
    void revoteInvalidRestarant() throws Exception {
        super.putWithValidationError(REST_URL + "?restaurantId=abc", USER_PETR);
    }

    @Test
    void revoteNullRestarant() throws Exception {
        super.putWithBadRequest(REST_URL, USER_PETR);
    }

    @Test
    void revoteNoRestaurant() throws Exception {
        super.putWithNotFoundError(REST_URL + "?restaurantId=1", USER_PETR);
    }

    @Test
    void revoteNoMenu() throws Exception {
        dishService.delete(DISH_HI_TODAY1_ID, RESTAURANT_HI_ID, TODAY);
        dishService.delete(DISH_HI_TODAY2_ID, RESTAURANT_HI_ID, TODAY);

        super.putWithNotFoundError(REST_URL + "?restaurantId=" + RESTAURANT_HI_ID, USER_PETR);
    }

    @Test
    void revoteNotVoted() throws Exception {
        super.putWithNotFoundError(REST_URL + "?restaurantId=" + RESTAURANT_HI_ID, ADMIN_INGA);
    }

    @Test
    void revoteTooLate() throws Exception {
        DateTimeUtil.setNoRevoteTime(LocalTime.of(0,1));
        super.putWithTimeViolation(REST_URL + "?restaurantId=" + RESTAURANT_HI_ID, USER_PETR);
        DateTimeUtil.setNoRevoteTime(LocalTime.of(11,0));
    }

}