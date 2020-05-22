package com.herokuapp.voteforlunch.web.controller;

import com.herokuapp.voteforlunch.model.User;
import com.herokuapp.voteforlunch.web.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import com.herokuapp.voteforlunch.util.exception.ErrorType;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;

import static com.herokuapp.voteforlunch.util.exception.ErrorType.*;
import static com.herokuapp.voteforlunch.web.TestUtil.userHttpBasic;
import static com.herokuapp.voteforlunch.web.UserTestData.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@Transactional
abstract public class AbstractControllerTest {
    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }

    public ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    public ResultMatcher errorType(ErrorType type) {
        return jsonPath("$.type").value(type.name());
    }

    public ResultMatcher detailMessage(String code) {
        return jsonPath("$.details").value(code);
    }

    // TESTS

    // ACCESS calls

    protected void getByUnAuth(String url) throws Exception {
        perform(MockMvcRequestBuilders.get(url)
                .with(userHttpBasic(ALIEN)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    protected <T> void createByUnAuth(String url, T entity) throws Exception {
        perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(entity))
                .with(userHttpBasic(ALIEN)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    protected <T> void postByUnAuth(String url) throws Exception {
        perform(MockMvcRequestBuilders.post(url)
                .with(userHttpBasic(ALIEN)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    protected <T> void updateByUnAuth(String url, T entity) throws Exception {
        perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(entity))
                .with(userHttpBasic(ALIEN)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    protected <T> void putByUnAuth(String url) throws Exception {
        perform(MockMvcRequestBuilders.put(url)
                .with(userHttpBasic(ALIEN)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    protected void deleteByUnAuth(String url) throws Exception {
        perform(MockMvcRequestBuilders.delete(url)
                .with(userHttpBasic(ALIEN)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    protected void getByUser(String url) throws Exception {
        perform(MockMvcRequestBuilders.get(url)
                .with(userHttpBasic(USER_PETR)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    protected <T> void createByUser(String url, T entity) throws Exception {
        perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(entity))
                .with(userHttpBasic(USER_PETR)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    protected <T> void updateByUser(String url, T entity) throws Exception {
        perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(entity))
                .with(userHttpBasic(USER_PETR)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    protected void deleteByUser(String url) throws Exception {
        perform(MockMvcRequestBuilders.delete(url)
                .with(userHttpBasic(USER_PETR)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    // INVALID DATA calls

    protected void getWithValidationError(String url) throws Exception {
        perform(MockMvcRequestBuilders.get(url)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    protected <T> void createWithValidationError(String url, T entity) throws Exception {
        perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(entity))
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    protected <T> void postWithValidationError(String url, User user) throws Exception {
        perform(MockMvcRequestBuilders.post(url)
                .with(userHttpBasic(user)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    protected <T> void updateWithValidationError(String url, T entity) throws Exception {
        perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(entity))
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    protected <T> void putWithValidationError(String url, User user) throws Exception {
        perform(MockMvcRequestBuilders.put(url)
                .with(userHttpBasic(user)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    protected void deleteWithValidationError(String url) throws Exception {
        perform(MockMvcRequestBuilders.delete(url)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    // DUPLICATED DATA calls

    protected <T> void createDuplicated(String url, T entity, String exceptionMsg) throws Exception {
        perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(entity))
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(exceptionMsg))
                .andDo(print());
    }

    protected <T> void updateDuplicated(String url, T entity, String exceptionMsg) throws Exception {
        perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(entity))
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(exceptionMsg))
                .andDo(print());
    }

    // INVALID JSON calls

    protected <T> void createInvalidJson(String url, T entity) throws Exception {
        perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(entity).replace(",", ";"))
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    protected <T> void updateInvalidJson(String url, T entity) throws Exception {
        perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(entity).replace(",", ";"))
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    // NOT FOUND calls

    protected void getWithNotFoundError(String url) throws Exception {
        perform(MockMvcRequestBuilders.get(url)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    protected <T> void createWithNotFoundError(String url, T entity) throws Exception {
        perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(entity))
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    protected <T> void postWithNotFoundError(String url, User user) throws Exception {
        perform(MockMvcRequestBuilders.post(url)
                .with(userHttpBasic(user)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    protected <T> void updateWithNotFoundError(String url, T entity) throws Exception {
        perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(entity))
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    protected <T> void putWithNotFoundError(String url, User user) throws Exception {
        perform(MockMvcRequestBuilders.put(url)
                .with(userHttpBasic(user)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    protected void deleteWithNotFoundError(String url) throws Exception {
        perform(MockMvcRequestBuilders.delete(url)
                .with(userHttpBasic(ADMIN_INGA)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    // BAD REQUEST calls

    protected void postWithBadRequest(String url, User user) throws Exception {
        perform(MockMvcRequestBuilders.post(url)
                .with(userHttpBasic(user)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    protected void putWithBadRequest(String url, User user) throws Exception {
        perform(MockMvcRequestBuilders.put(url)
                .with(userHttpBasic(user)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // TIME VIOLATION calls

    protected void putWithTimeViolation(String url, User user) throws Exception {
        perform(MockMvcRequestBuilders.put(url)
                .with(userHttpBasic(user)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(TIME_VIOLATION))
                .andDo(print());
    }
}
