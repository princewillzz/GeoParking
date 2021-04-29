package com.geoparking.bookingservice.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.geoparking.bookingservice.model.Booking;
import com.geoparking.bookingservice.model.BookingStatus;
import com.geoparking.bookingservice.repository.BookingRepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CronJob {

    private final BookingRepository bookingRepository;

    public CronJob( final BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Scheduled(initialDelayString = "PT10S", fixedDelayString = "PT20S")
    @Transactional
    void reconfigureBookingStatus() throws InterruptedException {

        final Date currentDateTime = new Date();

        System.err.println(currentDateTime + " cron job");

        final Date arrivalDateTimeBottomThreshold = new Date(System.currentTimeMillis() - 1000 * 60 * 60);
        List<BookingStatus> excluded = Arrays.asList(BookingStatus.CANCELLED, BookingStatus.COMPLETED);

        final List<Booking> bookings = this.bookingRepository
                .findAllByArrivalTimeDateGreaterThanAndBookingStatusNotIn(arrivalDateTimeBottomThreshold, excluded);

        Iterable<Booking> updatedBookings = bookings.stream().filter(booking -> {

            System.err.print("Booking ======> uid: " + booking.getUid() + "  before status: " + booking.getBookingStatus());

            boolean isUpdated = false;
            // when booking arrival time has already passed
            if (booking.getArrivalTimeDate().before(currentDateTime)) {

                // If the payment is not done and it is not already CANCELLED than cancel the
                // booking
                if (!booking.isPaymentDone() && !booking.getBookingStatus().equals(BookingStatus.CANCELLED)) {
                    booking.setBookingStatus(BookingStatus.CANCELLED);
                    isUpdated = true;
                }
                // If the payment is done and the booking status is still pending
                else if (booking.isPaymentDone() && booking.getBookingStatus().equals(BookingStatus.PENDING)) {
                    booking.setBookingStatus(BookingStatus.ONGOING);
                    isUpdated = true;
                }

            }

            // On booking departure time becoming already before current time
            if (booking.getDepartureTimeDate().before(currentDateTime)) {

                // if the payment is made and it is not already updated then update
                if (booking.isPaymentDone() && !booking.getBookingStatus().equals(BookingStatus.COMPLETED)) {
                    booking.setBookingStatus(BookingStatus.COMPLETED);
                    isUpdated = true;
                }

            }

            System.err.println(" After status: " + booking.getBookingStatus());
            return isUpdated;
        }).collect(Collectors.toList());

        bookingRepository.saveAll(updatedBookings);

    }

}
