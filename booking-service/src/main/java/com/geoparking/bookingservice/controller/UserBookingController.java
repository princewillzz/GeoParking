package com.geoparking.bookingservice.controller;

import javax.validation.Valid;

import com.geoparking.bookingservice.model.Booking;
import com.geoparking.bookingservice.model.RazorPayEntity;
import com.geoparking.bookingservice.service.BookingService;
import com.geoparking.bookingservice.service.RazorpayPaymentService;
import com.geoparking.bookingservice.util.CheckAvailabilityForm;
import com.geoparking.bookingservice.util.RazorpayCallbackOnSuccessRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.NotAcceptableStatusException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserBookingController {

    private final BookingService bookingService;
    private final RazorpayPaymentService razorpayPaymentService;

    @Autowired
    public UserBookingController(@Qualifier("bookingService") final BookingService bookingService,
            @Qualifier("razorpayPaymentService") final RazorpayPaymentService razorpayPaymentService) {
        this.bookingService = bookingService;
        this.razorpayPaymentService = razorpayPaymentService;
    }

    @PostMapping("/initiate/payment")
    public ResponseEntity<?> initiatePayment(@RequestBody CheckAvailabilityForm checkAvailabilityForm) {

        // Create booking and Generate payments link

        try {
            // Get Razorpay entity to be sent to the client
            final Booking booking = bookingService.initiateBookingProcess(checkAvailabilityForm);
            final RazorPayEntity razorPayEntity = razorpayPaymentService.getRazorPayEntityForNewOrder(booking, null);

            return ResponseEntity.ok().body(razorPayEntity);
        } catch (NotAcceptableStatusException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/payment/success")
    public ResponseEntity<?> paymentSuccess(
            @RequestBody @Valid final RazorpayCallbackOnSuccessRequest razorpayCallbackOnSuccessRequest) {

        try {
            razorpayPaymentService.saveCredentialOnPaymentSuccess(razorpayCallbackOnSuccessRequest.getRazorpayOrderId(),
                    razorpayCallbackOnSuccessRequest.getRazorpayPaymentId(),
                    razorpayCallbackOnSuccessRequest.getRazorpaySignature());

            return ResponseEntity.ok().body(razorpayCallbackOnSuccessRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
