package com.my.repairagency007.exception;

public class IncorrectPasswordException extends ServiceException {

    public IncorrectPasswordException() {
        super("error.wrongPassword");
    }
}
