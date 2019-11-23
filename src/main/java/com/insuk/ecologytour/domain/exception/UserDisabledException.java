package com.insuk.ecologytour.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UserDisabledException extends RuntimeException{
    public UserDisabledException(String message) {
        super(message);
    }
    public UserDisabledException(String message, Throwable exception){
        super(message, exception);
    }
}
