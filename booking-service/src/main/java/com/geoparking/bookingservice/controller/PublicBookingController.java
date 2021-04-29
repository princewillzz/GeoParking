package com.geoparking.bookingservice.controller;

import javax.validation.Valid;

import com.geoparking.bookingservice.service.BookingService;
import com.geoparking.bookingservice.util.CheckAvailabilityForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicBookingController {

    private final BookingService bookingService;

    @Autowired
    public PublicBookingController(@Qualifier("bookingService") final BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(value = "/check-availability")
    public ResponseEntity<?> checkBookingAvailability(@RequestBody @Valid CheckAvailabilityForm checkAvailabilityForm) {

        try {
            System.out.println(checkAvailabilityForm);
            final boolean isSlotAvailable = bookingService.checkSlotAvailability(checkAvailabilityForm);
            if (isSlotAvailable) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ResponseEntity.badRequest().build();

    }

}
