package com.geoparking.bookingservice.controller;

import com.geoparking.bookingservice.service.BookingService;
import com.geoparking.bookingservice.util.CheckAvailabilityForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicBookingController {

    private final BookingService bookingService;

    @Autowired
    public PublicBookingController(@Qualifier("bookingService") final BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(value = "/check-availability")
    public ResponseEntity<?> checkBookingAvailability(@RequestBody CheckAvailabilityForm checkAvailabilityForm) {

        if (bookingService.checkSlotAvailability(checkAvailabilityForm)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();

    }

}
