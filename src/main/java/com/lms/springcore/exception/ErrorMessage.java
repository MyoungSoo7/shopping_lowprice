package com.lms.springcore.exception;

public class ErrorMessage extends RuntimeException {
    public ErrorMessage(String message) {
        super(message);
    }
}
