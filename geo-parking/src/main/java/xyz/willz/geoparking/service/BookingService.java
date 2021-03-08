package xyz.willz.geoparking.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import xyz.willz.geoparking.dao.BookingRepository;
import xyz.willz.geoparking.dto.BookingDTO;
import xyz.willz.geoparking.model.Booking;
import xyz.willz.geoparking.model.BookingBillEmbeddable;
import xyz.willz.geoparking.model.Customer;
import xyz.willz.geoparking.model.Parking;
import xyz.willz.geoparking.utilities.ParkingAvailabilityForm;

@Service
@Qualifier("bookingService")
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final CustomerService customerService;
    private final ParkingService parkingService;

    @Transactional(readOnly = true)
    public Booking getBooking(final UUID id) {
        return bookingRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Transactional
    public Booking createBookingForParking(final ParkingAvailabilityForm parkingAvailabilityForm,
            final Customer customer) throws ParseException {

        // final boolean isParkingAvailable =
        // parkingService.isParkingAvailable(parkingAvailabilityForm);
        // if(!isParkingAvailable) {
        // throw new RuntimeException("Parking Not available");
        // }

        final Parking parking = parkingService.getParking(UUID.fromString(parkingAvailabilityForm.getParkingId()));

        parking.setVacant(parking.getVacant() - 1);
        parking.setOccupied(parking.getOccupied() + 1);

        final Booking booking = new Booking();

        // Format date and store in the booking model
        final SimpleDateFormat formatDateToBeStored = new SimpleDateFormat("yyyy-MM-ddHH:mm");
        booking.setFromTimeDate(formatDateToBeStored
                .parse(parkingAvailabilityForm.getArrivalDate() + parkingAvailabilityForm.getArrivalTime()));
        booking.setToTimeDate(formatDateToBeStored
                .parse(parkingAvailabilityForm.getDepartureDate() + parkingAvailabilityForm.getDepartureTime()));

        // Creating bill for the order
        final BookingBillEmbeddable bill = new BookingBillEmbeddable();
        bill.setTotalAmount(parking.getHourlyRate());

        bill.setAmountToPay(bill.getTotalAmount() - bill.getDiscount() - bill.getWallet());
        booking.setBill(bill);

        // Set customer for the booking
        booking.setCustomer(customer);
        // Set Parking for which the booking is done
        booking.setParking(parking);

        return bookingRepository.save(booking);

    }

    // Find booking on the basis of razorpayorderid
    public Booking getOrderWithRazorpayOrderId(String razorpayOrderId) {
        return bookingRepository.findByRazorpayOrderId(razorpayOrderId).orElseThrow();
    }

    @Transactional
    public Booking makeBookingSuccessfull(final BookingDTO bookingDTO) {

        // check signature

        final Booking booking = this.getOrderWithRazorpayOrderId(bookingDTO.getRazorpayOrderId());
        booking.setPaymentDone(true);
        booking.setRazorpayPaymentId(bookingDTO.getRazorpayPaymentId());

        booking.setRazorpaySignature(bookingDTO.getRazorpaySignature());

        return booking;
    }

    @Transactional(readOnly = true)
    public List<Booking> bookingsForCustomer(final Customer customer) {
        return bookingRepository.findByCustomer(customer);
    }

}
