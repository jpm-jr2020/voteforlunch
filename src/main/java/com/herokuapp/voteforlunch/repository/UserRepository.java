package com.herokuapp.voteforlunch.repository;

import com.herokuapp.voteforlunch.model.User;

public interface UserRepository {
    User getByEmail(String email);
}
