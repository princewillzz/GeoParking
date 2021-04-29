package com.geoparking.bookingservice.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.geoparking.bookingservice.model.Booking;
import com.geoparking.bookingservice.model.BookingStatus;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

        Optional<Booking> findByRazorpayOrderId(String razorpayOrderId);

        List<Booking> findByCustomerId(String customerId, Sort sort);

        List<Booking> findByParkingId(String parkingId);

        List<Booking> findAllByParkingId(String parkingId);

        List<Booking> findAllByParkingIdAndBookingStatusNotInAndDepartureTimeDateGreaterThan(String parkingId,
                        List<BookingStatus> status, Date departureDateTime);

        List<Booking> findAllByBookingStatusNotInAndDepartureTimeDateGreaterThan(List<BookingStatus> status,
                        Date departureDateTime);

        List<Booking> findAllByArrivalTimeDateGreaterThanAndBookingStatusNotIn(Date arrival,
                        List<BookingStatus> excluded);
}
