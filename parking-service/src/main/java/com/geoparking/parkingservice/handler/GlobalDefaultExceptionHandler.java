package com.geoparking.parkingservice.handler;

import com.geoparking.parkingservice.Exception.InvalidParkingAddressException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(value = { InvalidParkingAddressException.class })
    public ResponseEntity<?> handlerInvalidAddress(Exception exception) {

        return ResponseEntity.badRequest().body(exception.getMessage());
    }

}
