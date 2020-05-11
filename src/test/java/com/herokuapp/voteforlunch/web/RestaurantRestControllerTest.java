package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.model.Restaurant;
import com.herokuapp.voteforlunch.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.herokuapp.voteforlunch.web.RestaurantTestData.*;
import static com.herokuapp.voteforlunch.web.TestUtil.userHttpBasic;
import static com.herokuapp.voteforlunch.web.UserTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantRestController.REST_URL + '/';

    @Autowired
    private RestaurantRepository repository;

    // GET ALL tests

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT_BK, RESTAURANT_DD, RESTAURANT_MD, RESTAURANT_PR, RESTAURANT_HI))
                .andDo(print());
    }

    @Test
    void getAllByUser() throws Exception {
        super.getByUser(REST_URL);
    }

    @Test
    void getAllByUnAuth() throws Exception {
        super.getByUnAuth(REST_URL);
    }

    // GET tests

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_DD_ID)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT_DD))
                .andDo(print());
    }

    @Test
    void getNotFound() throws Exception {
        super.getNotFound(REST_URL + 1);
    }

    @Test
    void getInvalidId() throws Exception {
        super.getInvalidUrlParameter(REST_URL + "abc");
    }

    @Test
    void getByUser() throws Exception {
        super.getByUser(REST_URL + RESTAURANT_DD_ID);
    }

    @Test
    void getByUnAuth() throws Exception {
        super.getByUnAuth(REST_URL + RESTAURANT_DD_ID);
    }

    // CREATE tests

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant))
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

        Restaurant created = JsonUtil.readValue(action.andReturn().getResponse().getContentAsString(), Restaurant.class);
        long newId = created.getId();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(repository.get(newId), newRestaurant);
    }

    @Test
    void createByUser() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        super.createByUser(REST_URL, newRestaurant);
    }

    @Test
    void createByUnAuth() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        super.createByUnAuth(REST_URL, newRestaurant);
    }

    @Test
    void createInvalidAddress() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        newRestaurant.setAddress(null);
        super.createInvalidField(REST_URL, newRestaurant);
    }

    @Test
    void createInvalidName() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        newRestaurant.setName("A");
        super.createInvalidField(REST_URL, newRestaurant);
    }

    @Test
    void createNotNullId() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        newRestaurant.setId(1L);
        super.createNotNullId(REST_URL, newRestaurant);
        assertNull(repository.get(1L));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicated() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        newRestaurant.setAddress(RESTAURANT_PR.getAddress());
        newRestaurant.setName(RESTAURANT_PR.getName());
        super.createDuplicated(REST_URL, newRestaurant, ExceptionInfoHandler.EXCEPTION_DUPLICATE_RESTAURANT);
    }

    @Test
    void createInvalidJson() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        super.createInvalidJson(REST_URL, newRestaurant);
    }

    // UPDATE tests

    @Test
    void update() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_PR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isNoContent())
                .andDo(print());

        RESTAURANT_MATCHER.assertMatch(repository.get(RESTAURANT_PR_ID), updated);
    }

    @Test
    void updateNotFound() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        super.updateInvalidUrlParameter(REST_URL + 1, updated);
        assertNull(repository.get(1L));
        RESTAURANT_MATCHER.assertMatch(repository.get(RESTAURANT_PR_ID), RESTAURANT_PR);
    }

    @Test
    void updateInvalidId() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        super.updateInvalidUrlParameter(REST_URL + "abc", updated);
        RESTAURANT_MATCHER.assertMatch(repository.get(RESTAURANT_PR_ID), RESTAURANT_PR);
    }

    @Test
    void updateByUser() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        super.updateByUser(REST_URL + RESTAURANT_PR_ID, updated);
        RESTAURANT_MATCHER.assertMatch(repository.get(RESTAURANT_PR_ID), RESTAURANT_PR);
    }

    @Test
    void updateByUnAuth() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        super.updateByUnAuth(REST_URL + RESTAURANT_PR_ID, updated);
        RESTAURANT_MATCHER.assertMatch(repository.get(RESTAURANT_PR_ID), RESTAURANT_PR);
    }

    @Test
    void updateInvalidAddress() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        updated.setAddress("A");
        super.updateInvalidField(REST_URL + RESTAURANT_PR_ID, updated);
        RESTAURANT_MATCHER.assertMatch(repository.get(RESTAURANT_PR_ID), RESTAURANT_PR);
    }

    @Test
    void updateInvalidName() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        updated.setName("A");
        super.updateInvalidField(REST_URL + RESTAURANT_PR_ID, updated);
        RESTAURANT_MATCHER.assertMatch(repository.get(RESTAURANT_PR_ID), RESTAURANT_PR);
    }

    @Test
    void updateDifferentIds() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        updated.setId(1L);
        super.updateDifferentIds(REST_URL + RESTAURANT_PR_ID, updated);
        RESTAURANT_MATCHER.assertMatch(repository.get(RESTAURANT_PR_ID), RESTAURANT_PR);
    }

    @Test
    void updateNullId() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_PR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isNoContent())
                .andDo(print());

        updated.setId(RESTAURANT_PR_ID);
        RESTAURANT_MATCHER.assertMatch(repository.get(RESTAURANT_PR_ID), updated);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicated() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        updated.setName(RESTAURANT_DD.getName());
        updated.setAddress(RESTAURANT_DD.getAddress());
        super.updateDuplicated(REST_URL + RESTAURANT_PR_ID, updated, ExceptionInfoHandler.EXCEPTION_DUPLICATE_RESTAURANT);
        RESTAURANT_MATCHER.assertMatch(repository.get(RESTAURANT_PR_ID), RESTAURANT_PR);
    }

    @Test
    void updateInvalidJson() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        super.updateInvalidJson(REST_URL + RESTAURANT_PR_ID, updated);
        RESTAURANT_MATCHER.assertMatch(repository.get(RESTAURANT_PR_ID), RESTAURANT_PR);
    }

    // DELETE tests

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_BK_ID)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isNoContent())
                .andDo(print());
//        assertThrows(NotFoundException.class, () -> repository.get(RESTAURANT_BK_ID));
        assertNull(repository.get(RESTAURANT_BK_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        super.deleteNotFound(REST_URL + 1);
    }

    @Test
    void deleteInvalidId() throws Exception {
        super.deleteInvalidUrlParameter(REST_URL + "abc");
    }

    @Test
    void deleteByUser() throws Exception {
        super.deleteByUser(REST_URL + RESTAURANT_BK_ID);
        RESTAURANT_MATCHER.assertMatch(repository.get(RESTAURANT_BK_ID), RESTAURANT_BK);
    }

    @Test
    void deleteByUnAuth() throws Exception {
        super.deleteByUnAuth(REST_URL + RESTAURANT_BK_ID);
        RESTAURANT_MATCHER.assertMatch(repository.get(RESTAURANT_BK_ID), RESTAURANT_BK);
    }
}