package com.geoparking.bookingservice.service;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.geoparking.bookingservice.configuration.HeaderRequestInterceptor;
import com.geoparking.bookingservice.controller.PublicBookingController;
import com.geoparking.bookingservice.dto.BookingDTO;
import com.geoparking.bookingservice.mapper.BookingMapper;
import com.geoparking.bookingservice.model.Booking;
import com.geoparking.bookingservice.model.BookingStatus;
import com.geoparking.bookingservice.model.Customer;
import com.geoparking.bookingservice.model.DecodedUserInfo;
import com.geoparking.bookingservice.model.Parking;
import com.geoparking.bookingservice.repository.BookingRepository;
import com.geoparking.bookingservice.util.CheckAvailabilityForm;
import com.razorpay.RazorpayException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.NotAcceptableStatusException;

import lombok.extern.slf4j.Slf4j;

@Service
@Qualifier("bookingService")
@Slf4j
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

    // APi calls

    private ResponseEntity<Parking> fetchParkingWithId(final String parkingId) {

        return restTemplate.getForEntity("http://parking-service/parking/" + parkingId, Parking.class);
    }

    /**
     * Fetch Booking modal from the database
     * 
     * @param id booking unique id
     * @return Booking modal
     * @throws NoSuchElementException
     */
    public Booking loadBookingById(final UUID id) throws NoSuchElementException {

        return bookingRepository.findById(id).orElseThrow();

    }

    public Set<BookingDTO> getAllBookingOfCustomer(final DecodedUserInfo customer) {

        return fetchBookingListOfCustomerFromDB(customer.getUserId()).stream().map(BookingMapper.INSTANCE::toBookingDTO)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    private List<Booking> fetchBookingListOfCustomerFromDB(final String customerId) {

        return bookingRepository.findByCustomerId(customerId, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    /**
     * Check whether the booking for the given date time slot is available
     * 
     * @param checkAvailabilityForm
     * @return boolean value
     * @throws ParseException
     */
    public boolean checkSlotAvailability(final CheckAvailabilityForm checkAvailabilityForm) throws ParseException {

        // Fetch parking with id
        final String parkingId = checkAvailabilityForm.getParkingId();

        final Date arrivalDateTime = utilityService.convertDateTimeStringToDate(checkAvailabilityForm.getArrivalDate(),
                checkAvailabilityForm.getArrivalTime());
        final Date departureDateTime = utilityService.convertDateTimeStringToDate(
                checkAvailabilityForm.getDepartureDate(), checkAvailabilityForm.getDepartureTime());

        final ResponseEntity<Parking> parkingResponse = fetchParkingWithId(parkingId);

        if (!parkingResponse.getStatusCode().equals(HttpStatus.OK)) {
            log.info("Couldn't fetch parking with id " + parkingId);
            return false;
        }

        final Parking parking = parkingResponse.getBody();

        // Fetch all the bookings with given parking id
        List<Booking> onGoingBookings = getAllOngoingAndFutureBookings(parkingId);

        onGoingBookings.parallelStream().forEach(System.err::println);
        System.err.println(onGoingBookings.size());

        final long countOfBookingInGivenDuration = onGoingBookings.parallelStream().filter(booking -> {
            Date bookingArrivateDateTime = booking.getArrivalTimeDate();
            Date bookingDepartureDateTime = booking.getDepartureTimeDate();

            if (bookingArrivateDateTime.compareTo(arrivalDateTime) == 0
                    || bookingDepartureDateTime.compareTo(departureDateTime) == 0) {
                return true;
            }

            // Case 1
            if (bookingArrivateDateTime.after(arrivalDateTime) && bookingDepartureDateTime.before(departureDateTime)) {
                return true;
            }

            // Case 2
            if (bookingArrivateDateTime.before(arrivalDateTime) && bookingDepartureDateTime.after(departureDateTime)) {
                return true;
            }

            // Case 3
            if (bookingDepartureDateTime.after(arrivalDateTime) && bookingDepartureDateTime.before(departureDateTime)) {
                return true;
            }

            // Case 4
            if (bookingArrivateDateTime.after(arrivalDateTime) && bookingArrivateDateTime.before(departureDateTime)) {
                return true;
            }

            return false;
        }).count();

        System.err.println(countOfBookingInGivenDuration + "<----->" + parking.getTotalSlots());
        if (countOfBookingInGivenDuration < parking.getTotalSlots()) {
            return true;
        }

        return false;
    }

    /**
     * Retreive all future booking from the database
     * 
     * @param parkingId
     * @return
     */
    @Transactional(readOnly = true)
    public List<Booking> getAllOngoingAndFutureBookings(final String parkingId) {

        return bookingRepository.findAllByParkingIdAndBookingStatusNotInAndDepartureTimeDateGreaterThan(parkingId,
                Arrays.asList(BookingStatus.CANCELLED, BookingStatus.COMPLETED), new Date());
    }

    // @Transactional(readOnly = true)
    // List<Booking> getBookingsForSchedular(final Date arrivalTimeDateIsAfter) {

    //     List<BookingStatus> excluded = Arrays.asList(BookingStatus.CANCELLED, BookingStatus.COMPLETED);
    //     // get all bookings which arrive before now() and also which have departed
    //     // currently
    //     return bookingRepository.findAllByArrivalTimeDateGreaterThanAndBookingStatusNotIn(arrivalTimeDateIsAfter,
    //             excluded);
    // }

    /**
     * Get list of future bookings
     * 
     * @return
     */
    @Transactional(readOnly = true)
    public List<Booking> getAllOngoingAndFutureBookings() {
        return bookingRepository.findAllByBookingStatusNotInAndDepartureTimeDateGreaterThan(
                Arrays.asList(BookingStatus.CANCELLED, BookingStatus.COMPLETED), new Date());
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

    // /**Admin services */

    @Transactional(readOnly = true)
    private List<Booking> getAllBookingsWithParkingId(final String parkingId) {
        return bookingRepository.findByParkingId(parkingId);
    }

    /**
     * 
     * @param parkingId
     * @param adminInfo
     * @return
     * @throws IllegalAccessException
     * @throws NotAcceptableStatusException
     */
    public Map<String, Object> getBookingsAndParkingInfoOfAdmin(final String parkingId, final DecodedUserInfo adminInfo)
            throws IllegalAccessException, NotAcceptableStatusException {

        // Fetch parking from parking service
        final ResponseEntity<Parking> parkingResponse = restTemplate
                .getForEntity("http://parking-service/parking/" + parkingId, Parking.class);

        if (!parkingResponse.getStatusCode().equals(HttpStatus.OK)) {
            throw new NotAcceptableStatusException("Parking was not fetched!");
        }

        // get parking model
        final Parking parking = parkingResponse.getBody();

        // check admin of the parking equal to who is trying to access bookings
        if (!parking.getOwnerId().equals(adminInfo.getUserId())) {
            throw new IllegalAccessException("You are Unauthorized");
        }

        final Map<String, Object> data = new HashMap<>(5);
        data.put("parking", parking);

        // get bookings from database, convert and return
        data.put("bookings", this.getAllBookingsWithParkingId(parkingId).parallelStream()
                .map(BookingMapper.INSTANCE::toBookingDTO).collect(Collectors.toList()));

        return data;

    }

    public Customer retrieveCustomerInfoForBooking(final String customerId, final String authorizationHeader) {

        restTemplate.getInterceptors().add(new HeaderRequestInterceptor("Authorization", authorizationHeader));

        final ResponseEntity<Customer> customerResponse = restTemplate
                .getForEntity("http://profile-service/internal/admin/profile/" + customerId, Customer.class);

        if (!customerResponse.getStatusCode().equals(HttpStatus.OK)) {
            throw new RuntimeException("unable to get customer info");
        }
        final Customer customer = customerResponse.getBody();

        return customer;
    }

    /**
     * Cancel booking for customer
     * 
     * @param bookingId
     * @param userInfo
     * @throws IllegalAccessException
     */
    @Transactional
    public void cancelBookingOfCustomer(final String bookingId, final DecodedUserInfo userInfo)
            throws IllegalAccessException {

        final Booking booking = this.loadBookingById(UUID.fromString(bookingId));
        if (!booking.getCustomerId().equals(userInfo.getUserId())) {
            throw new IllegalAccessException("Access denied");
        }

        List<BookingStatus> status = Arrays.asList(BookingStatus.CANCELLED, BookingStatus.COMPLETED);

        if(!status.contains(booking.getBookingStatus())) {            
            booking.setBookingStatus(BookingStatus.CANCELLED);
        }
    }


}
