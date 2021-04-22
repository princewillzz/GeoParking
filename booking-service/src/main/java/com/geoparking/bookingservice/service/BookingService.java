package com.geoparking.bookingservice.service;

import java.util.NoSuchElementException;
import java.util.UUID;

import com.geoparking.bookingservice.model.Booking;
import com.geoparking.bookingservice.repository.BookingRepository;
import com.geoparking.bookingservice.util.CheckAvailabilityForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("bookingService")
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    protected BookingService(final BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Check whether the booking for the given date time slot is available
     * 
     * @param checkAvailabilityForm
     * @return boolean value
     */
    public boolean checkSlotAvailability(final CheckAvailabilityForm checkAvailabilityForm) {

        return true;
    }

    public Booking loadBookingById(final UUID id) throws NoSuchElementException {

        return bookingRepository.findById(id).orElseThrow();

    }

}
