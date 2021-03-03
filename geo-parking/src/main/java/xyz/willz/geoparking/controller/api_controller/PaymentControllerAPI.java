package xyz.willz.geoparking.controller.api_controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xyz.willz.geoparking.dto.BookingDTO;
import xyz.willz.geoparking.service.BookingService;
import xyz.willz.geoparking.service.RazorpayPaymentService;

@RestController
@RequestMapping(value = "/api/")
@Slf4j
@RequiredArgsConstructor
public class PaymentControllerAPI {

    private final BookingService bookingService;
    private final RazorpayPaymentService razorpayPaymentService;


    @PutMapping("/payment/success")
    public ResponseEntity<?> paymentSuccessfull(@RequestBody final BookingDTO bookingDTO) {

        try {
            
            return ResponseEntity.ok().body(bookingService.makeBookingSuccessfull(bookingDTO));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ResponseEntity.badRequest().build();

    }

    
}
