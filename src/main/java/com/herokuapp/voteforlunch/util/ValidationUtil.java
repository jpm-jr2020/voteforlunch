package com.herokuapp.voteforlunch.util;

import com.herokuapp.voteforlunch.model.AbstractEntity;
import com.herokuapp.voteforlunch.util.exception.IllegalRequestDataException;
import com.herokuapp.voteforlunch.util.exception.NotFoundException;

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

    public static void assureIdConsistent(AbstractEntity bean, long id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }
}