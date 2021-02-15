package xyz.willz.geoparking.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.willz.geoparking.model.Parking;

public interface ParkingRepository extends JpaRepository<Parking, UUID> {

    List<Parking> findAllByAddressContainingIgnoreCase(String address);

}
