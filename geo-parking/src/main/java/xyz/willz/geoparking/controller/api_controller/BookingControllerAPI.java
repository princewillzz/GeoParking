package xyz.willz.geoparking.controller.api_controller;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.willz.geoparking.dto.BookingDTO;
import xyz.willz.geoparking.model.Booking;
import xyz.willz.geoparking.model.Parking;
import xyz.willz.geoparking.model.RazorPayEntity;
import xyz.willz.geoparking.service.BookingService;
import xyz.willz.geoparking.service.ParkingService;
import xyz.willz.geoparking.service.RazorpayPaymentService;
import xyz.willz.geoparking.utilities.ParkingAvailabilityForm;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/")
public class BookingControllerAPI {


    private final BookingService bookingService;
    private final RazorpayPaymentService razorpayPaymentService;
    private final ParkingService parkingService;

    

    @PostMapping(value="/booking/book-slot")
    public ResponseEntity<?> createBookingAndGeneratePaymentOptions(@RequestBody ParkingAvailabilityForm parkingAvailabilityForm) {
        
        try {
            // Check if the parking slot is available currently or not
            // ...........

            // Create Booking Entity
            Booking booking = bookingService.createBookingForParking(parkingAvailabilityForm, 1l);

            // Generate razorpay order to make payment
            // final Parking parking = parkingService.getParking(UUID.fromString(parkingAvailabilityForm.getParkingId()));
           
            booking = razorpayPaymentService.createRazorPayOrder(booking);
            final RazorPayEntity rEntity = razorpayPaymentService.getRazorPayEntityForNewOrder(booking, null);

            return ResponseEntity.ok().body(rEntity);


        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ResponseEntity.badRequest().build();
    }




}
