package com.geoparking.bookingservice.repository;

import java.util.UUID;

import com.geoparking.bookingservice.model.Booking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

}
