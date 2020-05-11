package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.service.DishService;
import com.herokuapp.voteforlunch.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.temporal.ChronoUnit;

import static com.herokuapp.voteforlunch.web.MenuTestData.MENU_TO_MATCHER;
import static com.herokuapp.voteforlunch.web.RestaurantTestData.*;
import static com.herokuapp.voteforlunch.web.TestUtil.userHttpBasic;
import static com.herokuapp.voteforlunch.web.UserTestData.ADMIN_INGA;
import static com.herokuapp.voteforlunch.util.DateTimeUtil.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MenuRestController.REST_URL + '/';

    @Autowired
    private DishService service;

    @Test
    void getAll() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID);
        perform(MockMvcRequestBuilders.get(url)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(MenuTestData.MENU_HI_ALL_DAYS))
                .andDo(print());
    }

    @Test
    void getAllByUser() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID);
        super.getByUser(url);
    }

    @Test
    void getAllByUnAuth() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID);
        super.getByUnAuth(url);
    }

    @Test
    void getAllInvalidRestaurant() throws Exception {
        String url = composeUrl("abc");
        super.getInvalidUrlParameter(url);
    }

    @Test
    void getAllNotFoundRestaurant() throws Exception {
        String url = composeUrl(1L);
        super.getNotFound(url);
    }

    @Test
    void getBetweenNullNull() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID) + "filter";
        perform(MockMvcRequestBuilders.get(url)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(MenuTestData.MENU_HI_ALL_DAYS))
                .andDo(print());
    }

    @Test
    void getBetweenNullToday() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID) + "filter?endDate=" + TODAY;
        perform(MockMvcRequestBuilders.get(url)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(MenuTestData.MENU_HI_YESTERDAY_TODAY))
                .andDo(print());
    }

    @Test
    void getBetweenTodayNull() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID) + "filter?startDate=" + TODAY;
        perform(MockMvcRequestBuilders.get(url)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(MenuTestData.MENU_HI_TODAY_TOMORROW))
                .andDo(print());
    }

    @Test
    void getBetweenTodayToday() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID) + "filter?startDate=" + TODAY + "&endDate=" + TODAY;
        perform(MockMvcRequestBuilders.get(url)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(MenuTestData.MENU_HI_TODAY))
                .andDo(print());
    }

    @Test
    void getBetweenEmpty1() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID) + "filter?startDate=" + TOMORROW + "&endDate=" + YESTERDAY;
        perform(MockMvcRequestBuilders.get(url)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(MenuTestData.MENU_HI_NO_DAYS))
                .andDo(print());
    }

    @Test
    void getBetweenEmpty2() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID) + "filter?startDate=" + TOMORROW.plus(1, ChronoUnit.DAYS);
        perform(MockMvcRequestBuilders.get(url)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(MenuTestData.MENU_HI_NO_DAYS))
                .andDo(print());
    }

    @Test
    void getBetweenByUser() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID) + "filter";
        super.getByUser(url);
    }

    @Test
    void getAllBetweenUnAuth() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID) + "filter";
        super.getByUnAuth(url);
    }

    @Test
    void getBetweenInvalidRestaurant() throws Exception {
        String url = composeUrl("abc") + "filter";
        super.getInvalidUrlParameter(url);
    }

    @Test
    void getBetweenNotFoundRestaurant() throws Exception {
        String url = composeUrl(1L) + "filter";
        super.getNotFound(url);
    }

    @Test
    void getBetweenInvalidStart() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID) + "filter?startDate=abc";
        super.getInvalidUrlParameter(url);
    }

    @Test
    void getBetweenInvalidEnd() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID) + "filter?endDate=abc";
        super.getInvalidUrlParameter(url);
    }

    @Test
    void getByDate() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID) + TOMORROW;
        perform(MockMvcRequestBuilders.get(url)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(MenuTestData.MENU_HI_TOMORROW))
                .andDo(print());
    }

    @Test
    void getByDateEmpty() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID) + YESTERDAY.minus(1, ChronoUnit.DAYS);
        perform(MockMvcRequestBuilders.get(url)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(MenuTestData.MENU_HI_NO_DAYS))
                .andDo(print());
    }

    @Test
    void getByDateByUser() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID) + TOMORROW;
        super.getByUser(url);
    }

    @Test
    void getAllByDateUnAuth() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID) + TOMORROW;
        super.getByUnAuth(url);
    }

    @Test
    void getByDateInvalidRestaurant() throws Exception {
        String url = composeUrl("abc") + TOMORROW;
        super.getInvalidUrlParameter(url);
    }

    @Test
    void getByDateNotFoundRestaurant() throws Exception {
        String url = composeUrl(1L) + TOMORROW;
        super.getNotFound(url);
    }

    @Test
    void getByDateInvalidDate() throws Exception {
        String url = composeUrl(RESTAURANT_HI_ID) + "abc";
        super.getInvalidUrlParameter(url);
    }

    // Helper methods

    private String composeUrl(Long restaurantId) {
        return REST_URL.replace("{restaurantId}", restaurantId.toString());
    }

    private String composeUrl(String restaurantId) {
        return REST_URL.replace("{restaurantId}", restaurantId);
    }
}