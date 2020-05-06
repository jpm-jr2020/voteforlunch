package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.model.User;
import com.herokuapp.voteforlunch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = UserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {
    static final String REST_URL = "/users";

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @GetMapping(value = "/{id}")
    public User get(@PathVariable long id) {
        return userRepository.get(id);
    }

}
