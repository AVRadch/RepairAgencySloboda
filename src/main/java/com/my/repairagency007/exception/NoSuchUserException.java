package com.my.repairagency007.exception;

public class NoSuchUserException extends ServiceException {
    public NoSuchUserException() {
        super("error.emailNoUser");
    }
}