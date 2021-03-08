package xyz.willz.geoparking.controller.api_controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xyz.willz.geoparking.model.Booking;
import xyz.willz.geoparking.model.Customer;
import xyz.willz.geoparking.model.RazorPayEntity;
import xyz.willz.geoparking.service.BookingService;
import xyz.willz.geoparking.service.CustomerService;
import xyz.willz.geoparking.service.ParkingService;
import xyz.willz.geoparking.service.RazorpayPaymentService;
import xyz.willz.geoparking.utilities.ParkingAvailabilityForm;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/")
public class BookingControllerAPI {

    private final BookingService bookingService;
    private final RazorpayPaymentService razorpayPaymentService;
    private final ParkingService parkingService;
    private final CustomerService customerService;

    @PostMapping(value = "/secured/booking/book-slot")
    public ResponseEntity<?> createBookingAndGeneratePaymentOptions(
            @RequestBody ParkingAvailabilityForm parkingAvailabilityForm) {

        try {
            final DefaultOidcUser userDetails = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            final Customer customer = customerService.getCustomer(userDetails.getSubject());
            // Check if the parking slot is available currently or not
            // ...........

            // Create Booking Entity
            Booking booking = bookingService.createBookingForParking(parkingAvailabilityForm, customer);

            // Generate razorpay order to make payment
            // final Parking parking =
            // parkingService.getParking(UUID.fromString(parkingAvailabilityForm.getParkingId()));

            booking = razorpayPaymentService.createRazorPayOrder(booking);
            final RazorPayEntity razorPayEntity = razorpayPaymentService.getRazorPayEntityForNewOrder(booking,
                    customer);

            return ResponseEntity.ok().body(razorPayEntity);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ResponseEntity.badRequest().build();
    }

}
