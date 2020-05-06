package com.herokuapp.voteforlunch.repository;

import com.herokuapp.voteforlunch.model.User;

import java.util.List;

public interface UserRepository {
    // null if not found
    User get(long id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();
}