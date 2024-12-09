package com.branch_app.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
import java.util.concurrent.TimeoutException;

/**
 * This handler class is responsible for handling different expected exceptions.
 *
 * @author Michael Glaser
 * @since 2024-12-08
 */
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestExeption(ApiRequestException e){
        ApiException apiException = new ApiException( e.getMessage(),
                e,
                HttpStatus.BAD_REQUEST.value(),
                ZonedDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {TimeoutException.class})
    public ResponseEntity<Object> handleApiTimeoutExeption(TimeoutException e){
        ApiException apiException = new ApiException( e.getMessage(),
                e,
                HttpStatus.REQUEST_TIMEOUT.value(),
                ZonedDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.REQUEST_TIMEOUT);
    }
}
