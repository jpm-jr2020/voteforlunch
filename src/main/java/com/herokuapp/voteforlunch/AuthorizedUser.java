package com.herokuapp.voteforlunch;

import com.herokuapp.voteforlunch.model.User;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private long userId;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userId = user.getId();
    }

    public long getUserId() {
        return userId;
    }
}