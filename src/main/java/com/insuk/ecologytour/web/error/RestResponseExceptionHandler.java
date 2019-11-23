package com.insuk.ecologytour.web.error;

import com.insuk.ecologytour.domain.exception.NotFoundTourIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ NotFoundTourIdException.class })
    public ResponseEntity<Object> handleNotFoundTourIdException(final RuntimeException ex, final WebRequest request){
        log.error("NotFoundTourIdException", ex);
        final ErrorResponse res = new ErrorResponse(ex.getMessage(), "Not Found Tour Id Exception");
        return handleExceptionInternal(ex, res, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
