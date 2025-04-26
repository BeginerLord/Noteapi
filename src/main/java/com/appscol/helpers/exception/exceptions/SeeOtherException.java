package com.appscol.helpers.exception.exceptions;

public class SeeOtherException extends RuntimeException {
    public SeeOtherException(String message) {
        super(message);
    }

    public SeeOtherException(String message, Throwable cause) {
        super(message, cause);
    }
}
