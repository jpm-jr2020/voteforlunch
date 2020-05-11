package com.herokuapp.voteforlunch.util;

import com.herokuapp.voteforlunch.model.AbstractEntity;
import com.herokuapp.voteforlunch.model.Dish;
import com.herokuapp.voteforlunch.to.AbstractTo;
import com.herokuapp.voteforlunch.util.exception.ErrorType;
import com.herokuapp.voteforlunch.util.exception.IllegalRequestDataException;
import com.herokuapp.voteforlunch.util.exception.NotFoundException;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void checkNotFoundWithArg(boolean found, String entityName, long id) {
        checkNotFound(found,  entityName + " with id = " + id);
    }

    public static <T> T checkNotFoundWithArg(T object, String entityName, long id) {
        checkNotFound(object != null,  entityName + " with id = " + id);
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
            throw new IllegalRequestDataException(bean + " must be new (id = null)");
        }
    }

    public static void checkNew(AbstractTo bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id = null)");
        }
    }

    public static void assureIdConsistent(AbstractEntity bean, long id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean + " must be with id = " + id);
        }
    }

    public static void assureIdConsistent(AbstractTo bean, long id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
       if (bean.getId() != id) {
            throw new IllegalRequestDataException(bean + " must be with id = " + id);
        }
    }

    public static void checkMenuPresent(List<Dish> menu, long restaurantId, LocalDate date) {
        if (menu.isEmpty()) {
            throw new NotFoundException("menu of restaurant with id = " + restaurantId + " for date = " + date);
        }
    }

    public static Throwable logAndGetRootCause(Logger log, HttpServletRequest req, Exception e, boolean logException, ErrorType errorType) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return rootCause;
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    public static String getMessage(Throwable e) {
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }
}