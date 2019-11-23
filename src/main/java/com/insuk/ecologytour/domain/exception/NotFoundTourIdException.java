package com.insuk.ecologytour.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotFoundTourIdException extends RuntimeException{
    public NotFoundTourIdException(String message) {
        super(message);
    }
    public NotFoundTourIdException(String message, Throwable exception){
        super(message, exception);
    }
}
