package com.herokuapp.voteforlunch.repository.user;

import com.herokuapp.voteforlunch.model.User;

public interface UserRepository {
    User getByEmail(String email);
}
