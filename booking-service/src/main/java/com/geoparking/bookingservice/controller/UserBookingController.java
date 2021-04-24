package com.geoparking.bookingservice.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.geoparking.bookingservice.configuration.HeaderRequestInterceptor;
import com.geoparking.bookingservice.model.Booking;
import com.geoparking.bookingservice.model.Customer;
import com.geoparking.bookingservice.model.DecodedUserInfo;
import com.geoparking.bookingservice.model.RazorPayEntity;
import com.geoparking.bookingservice.service.BookingService;
import com.geoparking.bookingservice.service.RazorpayPaymentService;
import com.geoparking.bookingservice.util.CheckAvailabilityForm;
import com.geoparking.bookingservice.util.RazorpayCallbackOnSuccessRequest;
import com.geoparking.bookingservice.util.WithUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.NotAcceptableStatusException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserBookingController {

    private final BookingService bookingService;
    private final RazorpayPaymentService razorpayPaymentService;
    private final RestTemplate restTemplate;

    @Autowired
    public UserBookingController(@Qualifier("bookingService") final BookingService bookingService,
            @Qualifier("razorpayPaymentService") final RazorpayPaymentService razorpayPaymentService,
            final RestTemplate restTemplate) {
        this.bookingService = bookingService;
        this.razorpayPaymentService = razorpayPaymentService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyBookings(@WithUser final DecodedUserInfo profile) {

        final HashMap<String, Object> response = new HashMap<>(5);
        response.put("bookings", bookingService.getAllBookingOfCustomer(profile));

        return ResponseEntity.ok().body(response);

    }

    @PostMapping("/initiate/payment")
    public ResponseEntity<?> initiatePayment(@RequestBody CheckAvailabilityForm checkAvailabilityForm,
            @WithUser final DecodedUserInfo decodedUserInfo, final HttpServletRequest request) {

        // System.err.println(decodedUserInfo);
        // System.out.println(request.getHeader("Authorization"));

        // Create booking and Generate payments link

        try {

            restTemplate.getInterceptors()
                    .add(new HeaderRequestInterceptor("Authorization", request.getHeader("Authorization")));

            final ResponseEntity<Customer> customerResponse = restTemplate
                    .getForEntity("http://profile-service/auth/profile", Customer.class);

            if (customerResponse.getStatusCode() == HttpStatus.OK) {
                final Customer customer = customerResponse.getBody();

                System.out.println(customer);

                final Booking booking = bookingService.initiateBookingProcess(checkAvailabilityForm, customer);
                final RazorPayEntity razorPayEntity = razorpayPaymentService.getRazorPayEntityForNewOrder(booking,
                        customer);

                return ResponseEntity.ok().body(razorPayEntity);
            }
        } catch (NotAcceptableStatusException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
