package com.geoparking.bookingservice.service;

import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.geoparking.bookingservice.controller.PublicBookingController;
import com.geoparking.bookingservice.model.Booking;
import com.geoparking.bookingservice.model.Customer;
import com.geoparking.bookingservice.model.DecodedUserInfo;
import com.geoparking.bookingservice.model.Parking;
import com.geoparking.bookingservice.repository.BookingRepository;
import com.geoparking.bookingservice.util.CheckAvailabilityForm;
import com.razorpay.RazorpayException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.NotAcceptableStatusException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@Service
@Qualifier("bookingService")
public class BookingService {

    // Depended repository
    private final BookingRepository bookingRepository;

    // Depended services
    private final UtilityService utilityService;

    // Other dependencies
    private final ApplicationContext applicationContext;
    private RestTemplate restTemplate;

    @Autowired
    protected BookingService(final BookingRepository bookingRepository, final ApplicationContext applicationContext,
            @Qualifier("utilityService") final UtilityService utilityService, final RestTemplate restTemplate) {
        this.bookingRepository = bookingRepository;
        this.applicationContext = applicationContext;
        this.utilityService = utilityService;
        this.restTemplate = restTemplate;

    }

    /**
     * Check whether the booking for the given date time slot is available
     * 
     * @param checkAvailabilityForm
     * @return boolean value
     * @throws ParseException
     */
    public boolean checkSlotAvailability(final CheckAvailabilityForm checkAvailabilityForm) throws ParseException {

        final Booking booking = new Booking();

        // Format date and store in the booking model
        booking.setArrivalTimeDate(utilityService.convertDateTimeStringToDate(checkAvailabilityForm.getArrivalDate(),
                checkAvailabilityForm.getArrivalTime()));
        booking.setDepartureTimeDate(utilityService.convertDateTimeStringToDate(
                checkAvailabilityForm.getDepartureDate(), checkAvailabilityForm.getDepartureTime()));

        return true;
    }

    public Booking loadBookingById(final UUID id) throws NoSuchElementException {

        return bookingRepository.findById(id).orElseThrow();

    }

    /**
     * Initiate booking process 1. Check Availability 2. Create Booking Entity
     * 
     * @throws NotAcceptableStatusException if parking slot is not available
     * @throws ParseException
     * @throws RazorpayException
     * @throws IllegalAccessException
     */
    // @CircuitBreaker(name = "backendA", fallbackMethod = "fetchParkingFallback")
    // @TimeLimiter(name = "backendA") // , fallbackMethod = "fetchParkingFallback")
    @Transactional(rollbackFor = { RazorpayException.class })
    public Booking initiateBookingProcess(final CheckAvailabilityForm checkAvailabilityForm, final Customer customer)
            throws NotAcceptableStatusException, ParseException, RazorpayException, IllegalAccessException {

        // 1. Check Availability
        final boolean isAvailable = applicationContext.getBean(PublicBookingController.class)
                .checkBookingAvailability(checkAvailabilityForm).getStatusCode().equals(HttpStatus.OK);

        if (!isAvailable) {
            throw new NotAcceptableStatusException("Slot unavailable");

        }

        // fetch parking entity from parking service
        Parking parking = restTemplate
                .getForObject("http://parking-service/parking/" + checkAvailabilityForm.getParkingId(), Parking.class);

        // 2. Create Booking entity
        Booking booking = new Booking();
        utilityService.copyBookingDetailsToSaveInDB(booking, customer, parking, checkAvailabilityForm);

        // Persist booking entity
        booking = bookingRepository.save(booking);

        // 3. Create Razorpay entity
        applicationContext.getBean(RazorpayPaymentService.class).createRazorPayOrder(booking);

        return booking;
    }

    // private Booking fetchParkingFallback(final CheckAvailabilityForm
    // checkAvailabilityForm, Exception e) {
    // System.err.println(checkAvailabilityForm);
    // System.err.println("here");

    // return null;
    // }

    // fetch Booking Model with given razorpay order id
    @Transactional(readOnly = true)
    public Booking getOrderWithRazorpayOrderId(final String razorpayOrderId) {

        return bookingRepository.findByRazorpayOrderId(razorpayOrderId).orElseThrow();
    }

}
