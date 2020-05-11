package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.model.Restaurant;
import com.herokuapp.voteforlunch.repository.DishRepository;
import com.herokuapp.voteforlunch.to.DishTo;
import com.herokuapp.voteforlunch.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.herokuapp.voteforlunch.web.DishTestData.*;
import static com.herokuapp.voteforlunch.web.RestaurantTestData.*;
import static com.herokuapp.voteforlunch.web.TestUtil.userHttpBasic;
import static com.herokuapp.voteforlunch.web.UserTestData.ADMIN_INGA;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DishRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = DishRestController.REST_URL + '/';

    @Autowired
    private DishRepository repository;

    // GET tests

    @Test
    void get() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW) + DISH_HI_TOMORROW2_ID;
        perform(MockMvcRequestBuilders.get(url)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(DISH_HI_TOMORROW2))
                .andDo(print());
    }

    @Test
    void getNotFound() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW) + 1;
        super.getNotFound(url);
    }

    @Test
    void getInvalidId() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW) + "abc";
        super.getInvalidUrlParameter(url);
    }

    @Test
    void getByUser() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW) + DISH_HI_TOMORROW2_ID;
        super.getByUser(url);
    }

    @Test
    void getByUnAuth() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW) + DISH_HI_TOMORROW2_ID;
        super.getByUnAuth(url);
    }

    @Test
    void getInvalidRestaurant() throws Exception {
        String url = composeUrl(REST_URL, "abc", DateTimeUtil.TOMORROW) + DISH_HI_TOMORROW2_ID;
        super.getInvalidUrlParameter(url);
    }

    @Test
    void getInvalidDate() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, "abc") + DISH_HI_TOMORROW2_ID;
        super.getInvalidUrlParameter(url);
    }

    @Test
    void getOtherRestaurant() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_DD_ID, DateTimeUtil.TOMORROW) + DISH_HI_TOMORROW2_ID;
        super.getNotFound(url);
    }

    @Test
    void getOtherDate() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.YESTERDAY) + DISH_HI_TOMORROW2_ID;
        super.getNotFound(url);
    }

    // CREATE tests

    @Test
    void create() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW);
        DishTo newDish = DishTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish))
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

        DishTo created = JsonUtil.readValue(action.andReturn().getResponse().getContentAsString(), DishTo.class);
        long newId = created.getId();
        newDish = DishTestData.getNewWithId(newId);
        DISH_TO_MATCHER.assertMatch(created, newDish);
        DISH_TO_MATCHER.assertMatch(new DishTo(repository.get(newId, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW)), newDish);
    }

    @Test
    void createByUser() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW);
        DishTo newDish = DishTestData.getNew();
        super.createByUser(url, newDish);
    }

    @Test
    void createByUnAuth() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW);
        DishTo newDish = DishTestData.getNew();
        super.createByUnAuth(url, newDish);
    }

    @Test
    void createInvalidPrice() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW);
        DishTo newDish = DishTestData.getNewWithInvalidPrice();
        super.createInvalidField(url, newDish);
    }

    @Test
    void createInvalidName() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW);
        DishTo newDish = DishTestData.getNewWithInvalidName();
        super.createInvalidField(url, newDish);
    }

    @Test
    void createNotNullId() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW);
        DishTo newDish = DishTestData.getNewWithId(1L);
        super.createNotNullId(url, newDish);
        assertNull(repository.get(1L, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicated() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW);
        DishTo newDish = DishTestData.getNewDuplicated();
        super.createDuplicated(url, newDish, ExceptionInfoHandler.EXCEPTION_DUPLICATE_DISH);
    }

    @Test
    void createInvalidJson() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW);
        DishTo newDish = DishTestData.getNew();
        super.createInvalidJson(url, newDish);
    }

    @Test
    void createInvalidRestaurant() throws Exception {
        String url = composeUrl(REST_URL, "abc", DateTimeUtil.TOMORROW);
        DishTo newDish = DishTestData.getNew();
        super.createInvalidField(url, newDish);
    }

    @Test
    void createNotFoundRestaurant() throws Exception {
        String url = composeUrl(REST_URL, 1L, DateTimeUtil.TOMORROW);
        DishTo newDish = DishTestData.getNew();
        super.createNotFound(url, newDish);
    }

    @Test
    void createInvalidDate() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, "abc");
        DishTo newDish = DishTestData.getNew();
        super.createInvalidField(url, newDish);
    }

    @Test
    void createNewDate() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW.plus(1, ChronoUnit.DAYS));
        DishTo newDish = DishTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish))
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

        DishTo created = JsonUtil.readValue(action.andReturn().getResponse().getContentAsString(), DishTo.class);
        long newId = created.getId();
        newDish = DishTestData.getNewWithId(newId);
        DISH_TO_MATCHER.assertMatch(created, newDish);
        DISH_TO_MATCHER.assertMatch(new DishTo(repository.get(newId, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW.plus(1, ChronoUnit.DAYS))), newDish);
    }

    // UPDATE tests

    @Test
    void update() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TODAY) + DISH_HI_TODAY2_ID;
        DishTo updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isNoContent())
                .andDo(print());

        DishTo saved = new DishTo(repository.get(DISH_HI_TODAY2_ID, RESTAURANT_HI_ID, DateTimeUtil.TODAY));
        DISH_TO_MATCHER.assertMatch(saved, updated);
    }

    @Test
    void updateNotFound() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TODAY) + 1;
        DishTo updated = DishTestData.getUpdated();
        super.updateInvalidUrlParameter(url, updated);
        assertNull(repository.get(1L, RESTAURANT_HI_ID, DateTimeUtil.TODAY));
        DishTo remained = new DishTo(repository.get(DISH_HI_TODAY2_ID, RESTAURANT_HI_ID, DateTimeUtil.TODAY));
        DISH_TO_MATCHER.assertMatch(remained, DISH_HI_TODAY2);
    }

    @Test
    void updateInvalidId() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TODAY) + "abc";
        DishTo updated = DishTestData.getUpdated();
        super.updateInvalidUrlParameter(url, updated);
        DishTo remained = new DishTo(repository.get(DISH_HI_TODAY2_ID, RESTAURANT_HI_ID, DateTimeUtil.TODAY));
        DISH_TO_MATCHER.assertMatch(remained, DISH_HI_TODAY2);
    }

    @Test
    void updateByUser() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TODAY) + DISH_HI_TODAY2_ID;
        DishTo updated = DishTestData.getUpdated();
        super.updateByUser(url, updated);
        DishTo remained = new DishTo(repository.get(DISH_HI_TODAY2_ID, RESTAURANT_HI_ID, DateTimeUtil.TODAY));
        DISH_TO_MATCHER.assertMatch(remained, DISH_HI_TODAY2);
    }

    @Test
    void updateByUnAuth() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TODAY) + DISH_HI_TODAY2_ID;
        DishTo updated = DishTestData.getUpdated();
        super.updateByUnAuth(url, updated);
        DishTo remained = new DishTo(repository.get(DISH_HI_TODAY2_ID, RESTAURANT_HI_ID, DateTimeUtil.TODAY));
        DISH_TO_MATCHER.assertMatch(remained, DISH_HI_TODAY2);
    }

    @Test
    void updateInvalidPrice() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TODAY) + DISH_HI_TODAY2_ID;
        DishTo updated = DishTestData.getUpdatedWithInvalidPrice();
        super.updateInvalidField(url, updated);
        DishTo remained = new DishTo(repository.get(DISH_HI_TODAY2_ID, RESTAURANT_HI_ID, DateTimeUtil.TODAY));
        DISH_TO_MATCHER.assertMatch(remained, DISH_HI_TODAY2);
    }

    @Test
    void updateInvalidName() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TODAY) + DISH_HI_TODAY2_ID;
        DishTo updated = DishTestData.getUpdatedWithInvalidName();
        super.updateInvalidField(url, updated);
        DishTo remained = new DishTo(repository.get(DISH_HI_TODAY2_ID, RESTAURANT_HI_ID, DateTimeUtil.TODAY));
        DISH_TO_MATCHER.assertMatch(remained, DISH_HI_TODAY2);
    }

    @Test
    void updateDifferentIds() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TODAY) + DISH_HI_TODAY2_ID;
        DishTo updated = DishTestData.getUpdatedWithDifferentId();
        super.updateDifferentIds(url, updated);
        DishTo remained = new DishTo(repository.get(DISH_HI_TODAY2_ID, RESTAURANT_HI_ID, DateTimeUtil.TODAY));
        DISH_TO_MATCHER.assertMatch(remained, DISH_HI_TODAY2);
    }

    @Test
    void updateNullId() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TODAY) + DISH_HI_TODAY2_ID;
        DishTo updated = DishTestData.getUpdatedWithNullId();
        perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isNoContent())
                .andDo(print());

        DishTo saved = new DishTo(repository.get(DISH_HI_TODAY2_ID, RESTAURANT_HI_ID, DateTimeUtil.TODAY));
        DISH_TO_MATCHER.assertMatch(saved, DishTestData.getUpdated());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicated() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TODAY) + DISH_HI_TODAY2_ID;
        DishTo updated = DishTestData.getUpdatedDuplicated();
        super.updateDuplicated(url, updated, ExceptionInfoHandler.EXCEPTION_DUPLICATE_DISH);
        DishTo remained = new DishTo(repository.get(DISH_HI_TODAY2_ID, RESTAURANT_HI_ID, DateTimeUtil.TODAY));
        DISH_TO_MATCHER.assertMatch(remained, DISH_HI_TODAY2);
    }

    @Test
    void updateInvalidJson() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TODAY) + DISH_HI_TODAY2_ID;
        DishTo updated = DishTestData.getUpdated();
        super.updateInvalidJson(url, updated);
        DishTo remained = new DishTo(repository.get(DISH_HI_TODAY2_ID, RESTAURANT_HI_ID, DateTimeUtil.TODAY));
        DISH_TO_MATCHER.assertMatch(remained, DISH_HI_TODAY2);
    }

    @Test
    void updateInvalidRestaurant() throws Exception {
        String url = composeUrl(REST_URL, "abc", DateTimeUtil.TODAY) + DISH_HI_TODAY2_ID;
        DishTo updated = DishTestData.getUpdated();
        super.updateInvalidField(url, updated);
        DishTo remained = new DishTo(repository.get(DISH_HI_TODAY2_ID, RESTAURANT_HI_ID, DateTimeUtil.TODAY));
        DISH_TO_MATCHER.assertMatch(remained, DISH_HI_TODAY2);
    }

    @Test
    void updateNotFoundRestaurant() throws Exception {
        String url = composeUrl(REST_URL, 1L, DateTimeUtil.TODAY) + DISH_HI_TODAY2_ID;
        DishTo updated = DishTestData.getUpdated();
        super.updateNotFound(url, updated);
        DishTo remained = new DishTo(repository.get(DISH_HI_TODAY2_ID, RESTAURANT_HI_ID, DateTimeUtil.TODAY));
        DISH_TO_MATCHER.assertMatch(remained, DISH_HI_TODAY2);
    }

    @Test
    void updateInvalidDate() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, "abc") + DISH_HI_TODAY2_ID;
        DishTo updated = DishTestData.getUpdated();
        super.updateInvalidField(url, updated);
        DishTo remained = new DishTo(repository.get(DISH_HI_TODAY2_ID, RESTAURANT_HI_ID, DateTimeUtil.TODAY));
        DISH_TO_MATCHER.assertMatch(remained, DISH_HI_TODAY2);
    }

    @Test
    void updateOtherDate() throws Exception {
        String url = composeUrl(REST_URL, RESTAURANT_HI_ID, DateTimeUtil.TOMORROW) + DISH_HI_TODAY2_ID;
        DishTo updated = DishTestData.getUpdated();
        super.updateNotFound(url, updated);
        DishTo remained = new DishTo(repository.get(DISH_HI_TODAY2_ID, RESTAURANT_HI_ID, DateTimeUtil.TODAY));
        DISH_TO_MATCHER.assertMatch(remained, DISH_HI_TODAY2);
    }

    //    // DELETE tests
//
//    @Test
//    void delete() throws Exception {
//        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_BK_ID)
//                .with(userHttpBasic(ADMIN_INGA)))
//                .andExpect(status().isNoContent())
//                .andDo(print());
////        assertThrows(NotFoundException.class, () -> repository.get(RESTAURANT_BK_ID));
//        assertNull(repository.get(RESTAURANT_BK_ID));
//    }
//
//    @Test
//    void deleteNotFound() throws Exception {
//        super.deleteNotFound(REST_URL + 1);
//    }
//
//    @Test
//    void deleteInvalidId() throws Exception {
//        super.deleteInvalidUrlParameter(REST_URL + "abc");
//    }
//
//    @Test
//    void deleteByUser() throws Exception {
//        super.deleteByUser(REST_URL + RESTAURANT_BK_ID);
//        RESTAURANT_MATCHER.assertMatch(repository.get(RESTAURANT_BK_ID), RESTAURANT_BK);
//    }
//
//    @Test
//    void deleteByUnAuth() throws Exception {
//        super.deleteByUnAuth(REST_URL + RESTAURANT_BK_ID);
//        RESTAURANT_MATCHER.assertMatch(repository.get(RESTAURANT_BK_ID), RESTAURANT_BK);
//    }

    private String composeUrl(String url, Long restaurantId, LocalDate date) {
        return REST_URL.replace("{restaurantId}", restaurantId.toString())
                       .replace("{date}", date.format(DateTimeUtil.DATE_FORMATTER));
    }

    private String composeUrl(String url, String restaurantId, LocalDate date) {
        return REST_URL.replace("{restaurantId}", restaurantId)
                .replace("{date}", date.format(DateTimeUtil.DATE_FORMATTER));
    }

    private String composeUrl(String url, Long restaurantId, String date) {
        return REST_URL.replace("{restaurantId}", restaurantId.toString())
                .replace("{date}", date);
    }
}