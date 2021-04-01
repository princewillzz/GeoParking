package com.geoparking.parkingservice.repository;

import java.util.List;

import com.geoparking.parkingservice.model.Parking;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ParkingRepository extends MongoRepository<Parking, String> {

    @Query(value = "{ 'address': { $regex: ?0, $options: 'i' } }")
    List<Parking> findBySimiliarAddress(String address, Pageable pageable);

}
