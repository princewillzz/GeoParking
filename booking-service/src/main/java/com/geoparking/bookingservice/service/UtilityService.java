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

}
