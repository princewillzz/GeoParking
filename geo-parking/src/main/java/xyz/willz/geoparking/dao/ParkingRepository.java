package xyz.willz.geoparking.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xyz.willz.geoparking.model.Parking;

public interface ParkingRepository extends JpaRepository<Parking, UUID> {

    List<Parking> findAllByAddressContainingIgnoreCase(String address);

    @Query(value = "SELECT * FROM parking ORDER BY no_of_times_booked DESC LIMIT ?1", nativeQuery = true)
	List<Parking> findByNoOfTimesBooked(int limit);

}
