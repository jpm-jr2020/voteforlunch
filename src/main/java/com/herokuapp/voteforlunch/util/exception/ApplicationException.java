package com.herokuapp.voteforlunch.util.exception;

import java.util.Arrays;

public class ApplicationException extends RuntimeException {

    private final ErrorType type;
    private final String msg;
    private final String[] args;

    public ApplicationException(String msg) {
        this(ErrorType.APP_ERROR, msg);
    }

    public ApplicationException(ErrorType type, String msg, String... args) {
        super(String.format("type=%s, msgCode=%s, args=%s", type, msg, Arrays.toString(args)));
        this.type = type;
        this.msg = msg;
        this.args = args;
    }

    public ErrorType getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }

    public String[] getArgs() {
        return args;
    }
}
