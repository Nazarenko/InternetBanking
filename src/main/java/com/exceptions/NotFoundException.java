package com.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: tnazar
 * Date: 3/1/13
 * Time: 11:08 AM
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
