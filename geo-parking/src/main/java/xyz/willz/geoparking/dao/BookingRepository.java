package xyz.willz.geoparking.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.willz.geoparking.model.Booking;
import xyz.willz.geoparking.model.Customer;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    Optional<Booking> findByRazorpayOrderId(String razorpayOrderId);

    List<Booking> findByCustomer(Customer customer);

}
