package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantRestController.REST_URL + '/';

    @Autowired
    private RestaurantRepository repository;

    @Test
    void getAll() {
    }

    @Test
    void get() {
    }

    @Test
    void createWithLocation() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() throws Exception {
//        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_ID)
//                .with(userHttpBasic(ADMIN)))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//        assertThrows(NotFoundException.class, () -> userService.get(USER_ID));
    }
}