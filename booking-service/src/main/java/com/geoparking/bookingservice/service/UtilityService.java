package com.geoparking.bookingservice.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.geoparking.bookingservice.model.Booking;
import com.geoparking.bookingservice.model.BookingBillEmbeddable;
import com.geoparking.bookingservice.model.BookingStatus;
import com.geoparking.bookingservice.model.Customer;
import com.geoparking.bookingservice.model.Parking;
import com.geoparking.bookingservice.util.CheckAvailabilityForm;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("utilityService")
final public class UtilityService {

    private final static SimpleDateFormat dateFormatToBeStoredInDB = new SimpleDateFormat("yyyy-MM-ddHH:mm");

    Date convertDateTimeStringToDate(final String date, final String time) throws ParseException {
        // Format date and store in the booking model

        return dateFormatToBeStoredInDB.parse(date + time);

    }

    void copyBookingDetailsToSaveInDB(final Booking booking, final Customer customer, final Parking parking,
            final CheckAvailabilityForm checkAvailabilityForm) throws ParseException, IllegalAccessException {

        // Copy Arrival Departure date time
        booking.setArrivalTimeDate(this.convertDateTimeStringToDate(checkAvailabilityForm.getArrivalDate(),
                checkAvailabilityForm.getArrivalTime()));
        booking.setDepartureTimeDate(this.convertDateTimeStringToDate(checkAvailabilityForm.getDepartureDate(),
                checkAvailabilityForm.getDepartureTime()));

        // copy info related to parking
        booking.setParkingId(parking.getId());

        if (!customer.getIsActive() || !customer.getRole().equalsIgnoreCase("role_user")) {
            throw new IllegalAccessException("Not Authorized");
        }
        booking.setCustomerId(customer.getId().toString());

        final BookingBillEmbeddable bookingBill = new BookingBillEmbeddable();
        bookingBill.setTotalAmount(parking.getHourlyRent());
        bookingBill.setAmountToPay(bookingBill.getTotalAmount() - bookingBill.getDiscount() - bookingBill.getWallet());

        booking.setBill(bookingBill);

        booking.setBookingStatus(BookingStatus.PAYMENT_PENDING);

    }


    // BookingStatus calculateBookingStatus(final Booking booking) throws InterruptedException {

    //     final Date currentDateTime = new Date();

    //     // when booking arrival time has already passed
    //     if (booking.getArrivalTimeDate().before(currentDateTime)) {

    //         // If the payment is not done and it is not already CANCELLED than cancel the
    //         // booking
    //         if (!booking.isPaymentDone() && !booking.getBookingStatus().equals(BookingStatus.CANCELLED)) {
    //             booking.setBookingStatus(BookingStatus.CANCELLED);
    //         }
    //         // If the payment is done and the booking status is still pending
    //         else if (booking.isPaymentDone() && booking.getBookingStatus().equals(BookingStatus.PENDING)) {
    //             booking.setBookingStatus(BookingStatus.ONGOING);
    //         }

    //     }

    //     // On booking departure time becoming already before current time
    //     if (booking.getDepartureTimeDate().before(currentDateTime)) {

    //         // if the payment is made and it is not already updated then update
    //         if (booking.isPaymentDone() && !booking.getBookingStatus().equals(BookingStatus.COMPLETED)) {
    //             booking.setBookingStatus(BookingStatus.COMPLETED);
    //         }

    //     }

    //     return booking.getBookingStatus();

    // }

}
