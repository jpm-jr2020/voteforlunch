package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.herokuapp.voteforlunch.web.RestaurantTestData.*;
import static com.herokuapp.voteforlunch.web.TestUtil.userHttpBasic;
import static com.herokuapp.voteforlunch.web.UserTestData.ADMIN_INGA;
import static com.herokuapp.voteforlunch.web.UserTestData.USER_PETR;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TodayMenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL = TodayMenuRestController.REST_URL + '/';

    @Autowired
    private RestaurantService service;

    // GET ALL tests

    @Test
    void getAllByAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RESTAURANTTO_BK, RESTAURANTTO_DD, RESTAURANTTO_MD_VOTED, RESTAURANTTO_PR, RESTAURANTTO_HI))
                .andDo(print());
    }

    @Test
    void getAllByUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER_PETR)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RESTAURANTTO_BK, RESTAURANTTO_DD_VOTED, RESTAURANTTO_MD, RESTAURANTTO_PR, RESTAURANTTO_HI))
                .andDo(print());
    }

    @Test
    void getAllByUnAuth() throws Exception {
        super.getByUnAuth(REST_URL);
    }

//    // GET tests

    @Test
    void getByAdminVoted() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_MD_ID)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RESTAURANTTO_MD_VOTED))
                .andDo(print());
    }

    @Test
    void getByAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_PR_ID)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RESTAURANTTO_PR))
                .andDo(print());
    }

    @Test
    void getByUserVoted() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_DD_ID)
                .with(userHttpBasic(USER_PETR)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RESTAURANTTO_DD_VOTED))
                .andDo(print());
    }

    @Test
    void getByUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_PR_ID)
                .with(userHttpBasic(USER_PETR)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RESTAURANTTO_PR))
                .andDo(print());
    }

    @Test
    void getByUnAuth() throws Exception {
        super.getByUnAuth(REST_URL + RESTAURANT_MD_ID);
    }

    @Test
    void getNotFound() throws Exception {
        super.getNotFound(REST_URL + 1);
    }

    @Test
    void getInvalidId() throws Exception {
        super.getInvalidUrlParameter(REST_URL + "abc");
    }
}