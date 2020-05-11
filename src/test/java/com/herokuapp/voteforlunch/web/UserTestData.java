package com.herokuapp.voteforlunch.web;

import com.herokuapp.voteforlunch.model.Role;
import com.herokuapp.voteforlunch.model.User;

import java.util.Collections;
import java.util.Date;

import static com.herokuapp.voteforlunch.model.AbstractEntity.START_SEQ;

public class UserTestData {
    public static final long USER_PETR_ID = START_SEQ + 1;
    public static final long ADMIN_INGA_ID = START_SEQ + 5;
    public static final long ALIEN_ID = START_SEQ + 12345;

    public static final User USER_PETR = new User(USER_PETR_ID, "Петр", "petr@yandex.ru", "petr", true, Role.USER);
    public static final User ADMIN_INGA = new User(ADMIN_INGA_ID, "Инга", "inga@gmail.com", "passInga", true, Role.ADMIN, Role.USER);
    public static final User ALIEN = new User(ALIEN_ID, "Неизвестный", "alien@yahoo.com", "alien", true, Role.ADMIN, Role.USER);
}
