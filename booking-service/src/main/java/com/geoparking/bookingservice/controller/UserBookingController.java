package com.geoparking.bookingservice.controller;

import com.geoparking.bookingservice.util.CheckAvailabilityForm;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserBookingController {

    @PostMapping("/initiate/payment")
    public ResponseEntity<?> initiatePayment(@RequestBody CheckAvailabilityForm checkAvailabilityForm) {

        // 1. Check Availability
        // 2. Generate payments options

        return ResponseEntity.ok().build();
    }

}
