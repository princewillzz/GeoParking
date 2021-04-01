package com.geoparking.parkingservice.exceptionhandler;

import java.util.HashMap;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ConstraintViolationExceptionHandler {

    @ExceptionHandler(value = { ConstraintViolationException.class })
    public ResponseEntity<?> handleBeanValidationException(ConstraintViolationException exception) {

        final HashMap<String, String> errors = new HashMap<>();

        exception.getConstraintViolations().parallelStream().forEach(violation -> {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        });

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errors);

    }

}
