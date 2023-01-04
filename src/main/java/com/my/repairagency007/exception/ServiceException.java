package com.my.repairagency007.exception;

public class ServiceException extends Exception{

    public ServiceException() {}
    public ServiceException(String message) {
        super(message);
    }
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
