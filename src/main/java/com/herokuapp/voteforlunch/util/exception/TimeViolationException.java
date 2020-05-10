package com.herokuapp.voteforlunch.util.exception;

public class TimeViolationException extends ApplicationException {
    public static final String TIME_VIOLATION_EXCEPTION = "Too late to change vote: now is ";

    //  http://stackoverflow.com/a/22358422/548473
    public TimeViolationException(String arg) {
        super(ErrorType.WRONG_REQUEST, TIME_VIOLATION_EXCEPTION, arg);
    }
}