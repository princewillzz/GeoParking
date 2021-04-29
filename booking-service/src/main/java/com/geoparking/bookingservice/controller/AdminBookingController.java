package com.geoparking.bookingservice.controller;

import javax.servlet.http.HttpServletRequest;

import com.geoparking.bookingservice.model.Customer;
import com.geoparking.bookingservice.model.DecodedUserInfo;
import com.geoparking.bookingservice.service.BookingService;
import com.geoparking.bookingservice.util.WithUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.NotAcceptableStatusException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminBookingController {

    private final BookingService bookingService;

    @Autowired
    public AdminBookingController(@Qualifier("bookingService") final BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping(value = "/parking/{parking-id}")
    public ResponseEntity<?> getMyBookingsRelatedToParking(@PathVariable("parking-id") final String parkingId,
            @WithUser final DecodedUserInfo profile) {

        try {

            return ResponseEntity.ok(bookingService.getBookingsAndParkingInfoOfAdmin(parkingId, profile));
        } catch (NotAcceptableStatusException e) {
            log.error(e.getMessage());
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/customer/info")
    public ResponseEntity<Customer> getCustomerInfoForBooking(@RequestParam("customerId") final String customerId,
            final HttpServletRequest request) {

        return ResponseEntity.ok()
                .body(bookingService.retrieveCustomerInfoForBooking(customerId, request.getHeader("Authorization")));
    }

}
