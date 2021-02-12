package xyz.willz.geoparking.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.willz.geoparking.dao.BookingRepository;
import xyz.willz.geoparking.model.Booking;

@Service
@Qualifier("bookingService")
public class BookingService {


    private final BookingRepository bookingRepository;

    @Autowired
    protected BookingService(
        final BookingRepository bookingRepository
    ) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional(readOnly = true)
    public Booking getBooking(final UUID id) {
        return bookingRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    

}
