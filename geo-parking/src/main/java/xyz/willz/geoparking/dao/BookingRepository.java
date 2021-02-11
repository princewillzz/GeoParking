package xyz.willz.geoparking.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.willz.geoparking.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

}
