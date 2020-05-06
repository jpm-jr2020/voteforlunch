package com.herokuapp.voteforlunch.util.exception;

public class IllegalRequestDataException extends ApplicationException {
    public static final String ILLEGAL_REQUEST_EXCEPTION = "exception.common.illegalRequest";

    public IllegalRequestDataException(String arg) {
        super(ErrorType.WRONG_REQUEST, ILLEGAL_REQUEST_EXCEPTION, arg);
    }
}