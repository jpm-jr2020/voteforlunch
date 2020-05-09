package com.herokuapp.voteforlunch.util;

import com.herokuapp.voteforlunch.model.AbstractEntity;
import com.herokuapp.voteforlunch.model.Dish;
import com.herokuapp.voteforlunch.to.AbstractTo;
import com.herokuapp.voteforlunch.util.exception.IllegalRequestDataException;
import com.herokuapp.voteforlunch.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithArg(T object, long id) {
        checkNotFound(object != null, "id=" + id);
        return object;
    }

    public static <T> T checkNotFoundWithArg(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String arg) {
        if (!found) {
            throw new NotFoundException(arg);
        }
    }

    public static void checkNew(AbstractEntity bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void checkNew(AbstractTo bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(AbstractEntity bean, long id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    public static void assureIdConsistent(AbstractTo bean, long id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
       if (bean.getId() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    public static void checkMenuPresent(List<Dish> menu, long restaurantId, LocalDate date) {
        if (menu.isEmpty()) {
            throw new IllegalRequestDataException("restaurant " + restaurantId + " has no menu for " + date);
        }
    }
}