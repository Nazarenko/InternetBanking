package com.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: tnazar
 * Date: 2/27/13
 * Time: 1:07 PM
 */
public class ServiceException extends RuntimeException {
    private String message;

    public ServiceException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
